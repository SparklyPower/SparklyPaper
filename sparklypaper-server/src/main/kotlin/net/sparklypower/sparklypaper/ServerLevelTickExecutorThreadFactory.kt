package net.sparklypower.sparklypaper

import ca.spottedleaf.moonrise.common.util.TickThread
import net.minecraft.server.level.ServerLevel
import java.util.concurrent.ThreadFactory

class ServerLevelTickExecutorThreadFactory(private val world: ServerLevel) : ThreadFactory {
    override fun newThread(p0: Runnable): Thread {
        val tickThread = TickThread.ServerLevelTickThread(p0, world)

        if (tickThread.isDaemon) {
            tickThread.isDaemon = false
        }

        if (tickThread.priority != 5) {
            tickThread.priority = 5
        }

        return tickThread
    }
}
