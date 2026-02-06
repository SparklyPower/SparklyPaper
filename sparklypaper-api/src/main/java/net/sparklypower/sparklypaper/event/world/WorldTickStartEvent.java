package net.sparklypower.sparklypaper.event.world;

import org.bukkit.World;

import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class WorldTickStartEvent extends WorldEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final int tickNumber;

    @ApiStatus.Internal
    public WorldTickStartEvent(final World world, final int tickNumber) {
        super(world);
        this.tickNumber = tickNumber;
    }

    /**
     * @return What tick this is going be since start (first tick = 1)
     */
    public int getTickNumber() {
        return this.tickNumber;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
