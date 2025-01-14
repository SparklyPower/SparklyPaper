package net.sparklypower.sparklypaper.configs.previous

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.sparklypower.sparklypaper.configs.SparklyPaperConfig
import net.sparklypower.sparklypaper.configs.UpgradeableConfig

@Serializable
class SparklyPaperConfigV1(
    @SerialName("parallel-world-ticking")
    val parallelWorldTicking: ParallelWorldTicking,
    @SerialName("world-settings")
    val worldSettings: Map<String, SparklyPaperWorldConfig>
) : UpgradeableConfig {
    override val configVersion = 1

    override fun isNewest() = false

    override fun upgradeToNext(): UpgradeableConfig {
        return SparklyPaperConfig(
            2,
            SparklyPaperConfig.ParallelWorldTicking(
                this.parallelWorldTicking.threads
            ),
            worldSettings.mapValues {
                SparklyPaperConfig.SparklyPaperWorldConfig(
                    it.value.skipMapItemDataUpdatesIfMapDoesNotHaveCraftMapRenderer,
                    SparklyPaperConfig.SparklyPaperWorldConfig.BlazinglySimpleFarmChecks(
                        it.value.blazinglySimpleFarmChecks.enabled,
                        it.value.blazinglySimpleFarmChecks.defaultGrowthSpeed,
                        it.value.blazinglySimpleFarmChecks.moistGrowthSpeed,
                        it.value.blazinglySimpleFarmChecks.skipMiddleAgingStagesForCrops,
                    ),
                    SparklyPaperConfig.SparklyPaperWorldConfig.TicksPer(
                        0
                    )
                )
            }
        )
    }

    @Serializable
    class ParallelWorldTicking(
        val threads: Int
    )

    @Serializable
    class SparklyPaperWorldConfig(
        @SerialName("skip-map-item-data-updates-if-map-does-not-have-craftmaprenderer")
        val skipMapItemDataUpdatesIfMapDoesNotHaveCraftMapRenderer: Boolean,
        @SerialName("blazingly-simple-farm-checks")
        val blazinglySimpleFarmChecks: BlazinglySimpleFarmChecks
    ) {
        @Serializable
        data class BlazinglySimpleFarmChecks(
            val enabled: Boolean,
            @SerialName("default-growth-speed")
            val defaultGrowthSpeed: Float,
            @SerialName("moist-growth-speed")
            val moistGrowthSpeed: Float,
            @SerialName("skip-middle-aging-stages-for-crops")
            val skipMiddleAgingStagesForCrops: Boolean
        )
    }
}