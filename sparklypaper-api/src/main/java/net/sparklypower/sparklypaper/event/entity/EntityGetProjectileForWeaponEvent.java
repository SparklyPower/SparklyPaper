package net.sparklypower.sparklypaper.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when the server attempts to get the projectiles for a weapon. This is called before any projectiles are decided for a specific weapon
 */
public class EntityGetProjectileForWeaponEvent extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();
    private final ItemStack bow;
    private ItemStack arrow;

    public EntityGetProjectileForWeaponEvent(@NotNull final Entity entity, @NotNull ItemStack bow) {
        super(entity);
        this.entity = entity;
        this.bow = bow;
    }

    /**
     * @return the item that the player is using to fire the arrow
     */
    @NotNull
    public ItemStack getBow() {
        return this.bow;
    }

    /**
     * @return the arrow that is attempting to be used, this is null if setArrow was not used
     */
    @Nullable
    public ItemStack getArrow() {
        return this.arrow;
    }

    /**
     * Sets the arrow that is going to be used
     */
    public void setArrow(@Nullable final ItemStack arrow) {
        this.arrow = arrow;
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
