package net.sparklypower.sparklypaper

import net.minecraft.server.MinecraftServer
import net.sparklypower.sparklypaper.commands.SparklyPaperCommand
import org.bukkit.command.Command
import org.checkerframework.checker.nullness.qual.NonNull
import org.checkerframework.framework.qual.DefaultQualifier

@DefaultQualifier(NonNull::class)
object SparklyPaperCommands {
    private val COMMANDS = mapOf(
        "sparklypaper" to SparklyPaperCommand("sparklypaper")
    )

    fun registerCommands(server: MinecraftServer) {
        COMMANDS.forEach { (s: String, command: Command) ->
            server.server.commandMap.register(
                s, "SparklyPaper", command
            )
        }
    }
}