package net.sparklypower.sparklypaper.event.entity;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a entity releases a bow, before the projectile is spawned
 * <p>
 * Compared to EntityShootBowEvent, this event is called before the projectile is spawned, before the force check is done, and before the bow release sound is played.
 * <p>
 * Currently this event is only called for players! To be more specific, it is only called for HumanEntity!!
 */
public class PreEntityShootBowEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack bow;
    private final EquipmentSlot hand;
    private final float force;
    private boolean cancelled;

    public PreEntityShootBowEvent(@NotNull final HumanEntity shooter, @Nullable final ItemStack bow, @NotNull final EquipmentSlot hand, final float force) {
        super(shooter);
        this.bow = bow;
        this.hand = hand;
        this.force = force;
    }

    /**
     * Gets the bow ItemStack used to fire the arrow.
     *
     * @return the bow involved in this event
     */
    @Nullable
    public ItemStack getBow() {
        return bow;
    }

    /**
     * Get the hand from which the bow was shot.
     *
     * @return the hand
     */
    @NotNull
    public EquipmentSlot getHand() {
        return hand;
    }

    /**
     * Gets the force the arrow was launched with
     *
     * @return bow shooting force, up to 1.0
     */
    public float getForce() {
        return force;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
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
