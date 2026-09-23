package net.sparklypower.sparklypaper.threadedregions;

import io.papermc.paper.redstone.RedstoneWireTurbo;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class GenericWorldData {

    public final RedstoneWireTurbo turbo;

    public boolean shouldSignal = true;
    public boolean skipHopperEvents;
    public boolean skipPullModeEventFire;
    public boolean skipPushModeEventFire;

    public GenericWorldData() {
        this.turbo = new RedstoneWireTurbo((RedStoneWireBlock) Blocks.REDSTONE_WIRE);
    }

}
