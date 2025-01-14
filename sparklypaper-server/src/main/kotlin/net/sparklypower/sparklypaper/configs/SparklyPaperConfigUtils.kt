package net.sparklypower.sparklypaper.configs

import com.charleskorn.kaml.*
import com.google.common.base.Throwables
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.sparklypower.sparklypaper.configs.previous.SparklyPaperConfigV1
import org.bukkit.Bukkit
import java.io.File
import java.util.logging.Level

object SparklyPaperConfigUtils {
    private const val CURRENT_CONFIG_VERSION = 2
    val yaml = Yaml(
        configuration = YamlConfiguration(
            strictMode = false
        )
    )
    val deserializationStrategiesForVersions = mapOf(
        1 to SparklyPaperConfigV1.serializer(),
        CURRENT_CONFIG_VERSION to SparklyPaperConfig.serializer()
    )

    lateinit var config: SparklyPaperConfig
    val logContainerCreationStacktraces = java.lang.Boolean.getBoolean("sparklypaper.logContainerCreationStacktraces")

    fun init(configFile: File) {
        // Write default config if the file doesn't exist
        if (!configFile.exists()) {
            configFile.writeText(
                yaml.encodeToString(
                    SparklyPaperConfig(
                        CURRENT_CONFIG_VERSION,
                        SparklyPaperConfig.ParallelWorldTicking(
                            threads = 8
                        ),
                        mapOf(
                            "default" to SparklyPaperConfig.SparklyPaperWorldConfig(
                                skipMapItemDataUpdatesIfMapDoesNotHaveCraftMapRenderer = true,
                                blazinglySimpleFarmChecks = SparklyPaperConfig.SparklyPaperWorldConfig.BlazinglySimpleFarmChecks(
                                    enabled = false,
                                    defaultGrowthSpeed = 1.0f,
                                    moistGrowthSpeed = 5.0f,
                                    skipMiddleAgingStagesForCrops = true
                                ),
                                SparklyPaperConfig.SparklyPaperWorldConfig.TicksPer(
                                    hopperCooldownWhenTargetContainerIsFull = 0
                                )
                            )
                        )
                    )
                )
            )
        }

        val loadedConfig = try {
            // Read the version file from the config before attempting to parse
            val yamlNode = yaml.parseToYamlNode(configFile.readText())
            var configVersion = yamlNode.yamlMap.getScalar("config-version")?.toInt() ?: 1 // The first config version didn't have the "config-version" key

            if (configVersion != CURRENT_CONFIG_VERSION) {
                var upgradedVersion: UpgradeableConfig? = null

                while (configVersion != CURRENT_CONFIG_VERSION) {
                    Bukkit.getLogger().log(Level.INFO, "Attempting to upgrade SparklyPaper Config from version $configVersion to the next version...")

                    val upgradeableConfig = yaml.decodeFromYamlNode(deserializationStrategiesForVersions[configVersion]!!, yamlNode)
                    if (!upgradeableConfig.isNewest()) {
                        upgradedVersion = upgradeableConfig.upgradeToNext()

                        Bukkit.getLogger().log(Level.INFO, "Upgraded SparklyPaper Config from version $configVersion to ${upgradedVersion.configVersion}")
                        configVersion = upgradedVersion.configVersion
                    }
                }

                upgradedVersion as SparklyPaperConfig
            } else {
                yaml.decodeFromYamlNode(SparklyPaperConfig.serializer(), yamlNode)
            }
        } catch (e: SerializationException) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load sparklypaper.yml, please correct your syntax errors", e)
            throw Throwables.propagate(e)
        }
        // Rewrite the config file to remove old fields and stuff
        // TODO: Maybe handle this in another way? This feels kinda bad
        configFile.writeText(yaml.encodeToString(loadedConfig))
        config = loadedConfig
    }

    fun getWorldSettings(levelName: String): SparklyPaperConfig.SparklyPaperWorldConfig {
        return config.worldSettings[levelName] ?: config.worldSettings["default"] ?: error("Missing default world-settings in sparklypaper.yml!")
    }
}