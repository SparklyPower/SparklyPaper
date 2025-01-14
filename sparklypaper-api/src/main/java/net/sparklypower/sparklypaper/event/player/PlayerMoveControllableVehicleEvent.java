package net.sparklypower.sparklypaper.event.player;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Raised when a player moves a controllable vehicle. Controllable vehicles are vehicles that the client can control, such as boats, horses, striders, pigs, etc.
 * <p>
 * Minecarts are NOT affected by this event!
 */
public class PlayerMoveControllableVehicleEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private final Vehicle vehicle;
    private Location from;
    private Location to;

    public PlayerMoveControllableVehicleEvent(@NotNull final Player player, @NotNull final Vehicle vehicle, @NotNull final Location from, @NotNull final Location to) {
        super(player);

        this.vehicle = vehicle;
        this.from = from;
        this.to = to;
    }

    /**
     * Get the previous position.
     *
     * @return Old position.
     */
    @NotNull
    public Location getFrom() {
        return from.clone(); // Paper - clone to avoid changes
    }

    /**
     * Sets the location to mark as where the player moved from
     *
     * @param from New location to mark as the players previous location
     */
    public void setFrom(@NotNull Location from) {
        validateLocation(from, this.from);
        this.from = from;
    }

    /**
     * Get the next position.
     *
     * @return New position.
     */
    @NotNull
    public Location getTo() {
        return to.clone(); // Paper - clone to avoid changes
    }

    /**
     * Sets the location that this player will move to
     *
     * @param to New Location this player will move to
     */
    public void setTo(@NotNull Location to) {
        validateLocation(to, this.to);
        this.to = to;
    }

    /**
     * Get the vehicle.
     *
     * @return the vehicle
     */
    @NotNull
    public final Entity getVehicle() {
        return vehicle;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     * <p>
     * If a move or teleport event is cancelled, the vehicle and player will be moved or
     * teleported back to the Location as defined by getFrom(). This will not
     * fire an event
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     * <p>
     * If a move or teleport event is cancelled, the vehicle and player will be moved or
     * teleported back to the Location as defined by getFrom(). This will not
     * fire an event
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private void validateLocation(@NotNull Location loc, @NotNull Location originalLoc) {
        Preconditions.checkArgument(loc != null, "Cannot use null location!");
        Preconditions.checkArgument(loc.getWorld() != null, "Cannot use null location with null world!");
        Preconditions.checkArgument(loc.getWorld() != originalLoc, "New location should be in the original world!");
    }
}
