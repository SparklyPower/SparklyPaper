package net.sparklypower.sparklypaper.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called after the server attempts to move the player, but before the PlayerMoveEvent is called.
 * <p>
 * In contrast to PlayerMoveEvent, this event happens on every movement instead of being throttled like PlayerMoveEvent,
 * and this event exposes the player's onGround/horizontalCollision status, allowing plugins to manipulate it.
 */
public class PlayerPreMoveEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Location from;
    private final Location to;
    private boolean onGround;
    private boolean horizontalCollision;
    private boolean resetFallDistance;

    public PlayerPreMoveEvent(@NotNull final Player player, @NotNull final Location from, @Nullable final Location to, boolean onGround, boolean horizontalCollision, boolean resetFallDistance) {
        super(player);
        this.from = from;
        this.to = to;
        this.onGround = onGround;
        this.horizontalCollision = horizontalCollision;
        this.resetFallDistance = resetFallDistance;
    }

    /**
     * Gets the location this player moved from
     *
     * @return Location the player moved from
     */
    @NotNull
    public Location getFrom() {
        return from;
    }

    /**
     * Gets the location this player moved to
     *
     * @return Location the player moved to
     */
    @NotNull // Paper
    public Location getTo() {
        return to;
    }

    // Paper start - PlayerMoveEvent improvements
    /**
     * Check if the player has changed position (even within the same block) in the event
     *
     * @return whether the player has changed position or not
     */
    public boolean hasChangedPosition() {
        return hasExplicitlyChangedPosition() || !from.getWorld().equals(to.getWorld());
    }

    /**
     * Check if the player has changed position (even within the same block) in the event, disregarding a possible world change
     *
     * @return whether the player has changed position or not
     */
    public boolean hasExplicitlyChangedPosition() {
        return from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ();
    }

    /**
     * Check if the player has moved to a new block in the event
     *
     * @return whether the player has moved to a new block or not
     */
    public boolean hasChangedBlock() {
        return hasExplicitlyChangedBlock() || !from.getWorld().equals(to.getWorld());
    }

    /**
     * Check if the player has moved to a new block in the event, disregarding a possible world change
     *
     * @return whether the player has moved to a new block or not
     */
    public boolean hasExplicitlyChangedBlock() {
        return from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ();
    }

    /**
     * Check if the player has changed orientation in the event
     *
     * @return whether the player has changed orientation or not
     */
    public boolean hasChangedOrientation() {
        return from.getPitch() != to.getPitch() || from.getYaw() != to.getYaw();
    }
    // Paper end

    /**
     * Gets if the client said that they are on ground, keep in mind that this value is controlled by the client, so it can
     * be spoofed by malicious clients or be out of sync.
     *
     * @return if the client said that the is on ground
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Sets if the player should be on ground.
     *
     * @param onGround true if the player should be on ground
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * Gets if the client said that they are horizontally colliding, keep in mind that this value is controlled by the client, so it can
     * be spoofed by malicious clients or be out of sync.
     *
     * @return if the player is horizontally colliding on a block
     */
    public boolean isHorizontalCollision() {
        return horizontalCollision;
    }

    /**
     * Sets if the player should be horizontally colliding on a block.
     *
     * @param horizontalCollision true if the player should be colliding horizontally be on ground
     */
    public void setHorizontalCollision(boolean horizontalCollision) {
        this.horizontalCollision = horizontalCollision;
    }

    /**
     * Gets if the player's fall distance should be reset. By default, the fall distance is reset every time the player moves upwards on the y axis.
     *
     * @return if the fall distance should be reset
     */
    public boolean isResetFallDistance() {
        return resetFallDistance;
    }

    /**
     * Sets if the player's fall distance should be reset.
     *
     * @param resetFallDistance true if the player fall distance should be reset
     */
    public void setResetFallDistance(boolean resetFallDistance) {
        this.resetFallDistance = resetFallDistance;
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

}
