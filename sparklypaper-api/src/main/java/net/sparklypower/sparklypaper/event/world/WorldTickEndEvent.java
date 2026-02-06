package net.sparklypower.sparklypaper.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class WorldTickEndEvent extends WorldEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final int tickNumber;
    private final double tickDuration;

    @ApiStatus.Internal
    public WorldTickEndEvent(final World world, final int tickNumber, final double tickDuration) {
        super(world);
        this.tickNumber = tickNumber;
        this.tickDuration = tickDuration;
    }

    /**
     * @return What tick this was since start (first tick = 1)
     */
    public int getTickNumber() {
        return this.tickNumber;
    }

    /**
     * @return Time in milliseconds of how long this tick took
     */
    public double getTickDuration() {
        return this.tickDuration;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
