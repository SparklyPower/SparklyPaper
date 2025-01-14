package net.sparklypower.sparklypaper.configs

interface UpgradeableConfig {
    abstract val configVersion: Int
    fun isNewest(): Boolean
    fun upgradeToNext(): UpgradeableConfig
}