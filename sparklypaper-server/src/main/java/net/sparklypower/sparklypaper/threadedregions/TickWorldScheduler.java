package net.sparklypower.sparklypaper.threadedregions;

import ca.spottedleaf.moonrise.common.util.TickThread;
import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TickWorldScheduler {
    private TickWorldScheduler() {
    }

    public static GenericWorldData getCurrentRegionizedWorldData() {
        if (Thread.currentThread() instanceof TickThread.ServerLevelTickThread serverLevelThread) {
            return serverLevelThread.currentlyTickingServerLevel.regionizedWorldData;
        }
        return MinecraftServer.getServer().fallbackWorldData;
    }

}
