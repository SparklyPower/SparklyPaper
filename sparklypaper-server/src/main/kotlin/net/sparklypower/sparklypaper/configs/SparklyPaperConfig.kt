package net.sparklypower.sparklypaper.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SparklyPaperConfig(
    @SerialName("config-version")
    override val configVersion: Int,
    @SerialName("parallel-world-ticking")
    val parallelWorldTicking: ParallelWorldTicking,
    @SerialName("world-settings")
    val worldSettings: Map<String, SparklyPaperWorldConfig>
) : UpgradeableConfig {
    override fun isNewest() = true

    override fun upgradeToNext() = error("This config is already the newest version!")

    @Serializable
    class ParallelWorldTicking(
        val threads: Int
    )

    @Serializable
    class SparklyPaperWorldConfig(
        @SerialName("skip-map-item-data-updates-if-map-does-not-have-craftmaprenderer")
        val skipMapItemDataUpdatesIfMapDoesNotHaveCraftMapRenderer: Boolean,
        @SerialName("blazingly-simple-farm-checks")
        val blazinglySimpleFarmChecks: BlazinglySimpleFarmChecks,
        @SerialName("ticks-per")
        val ticksPer: TicksPer,
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

        @Serializable
        data class TicksPer(
            @SerialName("hopper-cooldown-when-target-container-is-full")
            val hopperCooldownWhenTargetContainerIsFull: Int
        )
    }
}