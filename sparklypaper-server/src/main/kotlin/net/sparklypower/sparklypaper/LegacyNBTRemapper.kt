package net.sparklypower.sparklypaper

import ca.spottedleaf.dataconverter.types.MapType

object LegacyNBTRemapper {
    /**
     * Remaps hacky direct NBT storage used in SparklyPower to proper PersistentDataContainer data
     */
    fun remap(tag: MapType) {
        val perfectDreamsMap = tag.getMap("PerfectDreams")

        if (perfectDreamsMap != null) {
            val publicBukkitValuesMap = tag.getOrCreateMap("PublicBukkitValues")

            // The "setBoolean" functions do set bytes behind the scenes, just like how we do the things in SparklyPower
            perfectDreamsMap.getStringAndRemove("isJetpack")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_jetpack", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("disallowCrafting")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:disallow_crafting", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("poop")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_poop", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("renamedBySeuZe")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_renamed_by_seu_ze", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("isMonsterPickaxe")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_monster_tool", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("itemOwner")?.let {
                publicBukkitValuesMap.setString("sparklypower:item_owner", it)
            }

            perfectDreamsMap.getStringAndRemove("DreamFusca")?.let {
                publicBukkitValuesMap.setString("sparklypower:fusca_info", it)
            }

            perfectDreamsMap.getStringAndRemove("isFusca")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_fusca", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("fancyLeatherArmor")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_fancy_leather_armor", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("caixaSecretaLevel")?.let {
                publicBukkitValuesMap.setInt("sparklypower:caixa_secreta_level", it.toInt())
            }

            perfectDreamsMap.getStringAndRemove("caixaSecretaWorld")?.let {
                publicBukkitValuesMap.setString("sparklypower:caixa_secreta_world", it)
            }

            perfectDreamsMap.getStringAndRemove("isMoveSpawners")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_move_spawners_tool", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("spawnerType")?.let {
                publicBukkitValuesMap.setString("sparklypower:spawner_type", it)
            }

            perfectDreamsMap.getStringAndRemove("isMochila")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_mochila", it.toBoolean())
            }

            perfectDreamsMap.getStringAndRemove("mochilaId")?.let {
                publicBukkitValuesMap.setLong("sparklypower:mochila_id", it.toLong())
            }

            perfectDreamsMap.getStringAndRemove("customMapOwner")?.let {
                publicBukkitValuesMap.setString("sparklypower:map_custom_owner", it)
            }

            perfectDreamsMap.getStringAndRemove("quickTeleport")?.let {
                publicBukkitValuesMap.setBoolean("sparklypower:is_quick_resources_teleport", it.toBoolean())
            }

            // If it is empty, then it means that we have migrated everything and we can remove the old PerfectDreams tag, yay!
            if (perfectDreamsMap.isEmpty)
                tag.remove("PerfectDreams")
        }
    }

    private fun MapType.getStringAndRemove(key: String): String? {
        val v = getString(key)
        remove(key)
        return v
    }
}