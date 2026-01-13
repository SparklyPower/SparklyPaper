package net.sparklypower.sparklypaper.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when the server attempts to apply a impulse to the entity due to enchantments, such as lunge.
 */
public class EntityApplyEnchantmentImpulseEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack item;
    private boolean cancelled;

    public EntityApplyEnchantmentImpulseEvent(@NotNull final Entity entity, @NotNull ItemStack item) {
        super(entity);
        this.entity = entity;
        this.item = item;
    }

    /**
     * @return the item that the player is using that caused the impulse
     */
    @NotNull
    public ItemStack getItem() {
        return this.item;
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
