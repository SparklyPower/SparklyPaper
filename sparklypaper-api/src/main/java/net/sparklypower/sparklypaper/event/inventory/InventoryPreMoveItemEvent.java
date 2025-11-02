package net.sparklypower.sparklypaper.event.inventory;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when some entity or block (e.g. hopper) tries to move items directly
 * from one inventory to another.
 * <p>
 * This event is called before InventoryMoveItemEvent, and is called before the
 * item within the container is modified to match the pull/push stack count.
 */
public class InventoryPreMoveItemEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Inventory sourceInventory;
    private final Inventory destinationInventory;
    private final boolean didSourceInitiate;

    private boolean cancelled;

    @ApiStatus.Internal
    public InventoryPreMoveItemEvent(@NotNull final Inventory sourceInventory, @NotNull final Inventory destinationInventory, final boolean didSourceInitiate) {
        this.sourceInventory = sourceInventory;
        this.destinationInventory = destinationInventory;
        this.didSourceInitiate = didSourceInitiate;
    }

    /**
     * Gets the Inventory that the ItemStack is being taken from
     *
     * @return Inventory that the ItemStack is being taken from
     */
    @NotNull
    public Inventory getSource() {
        return this.sourceInventory;
    }

    /**
     * Gets the Inventory that the ItemStack is being put into
     *
     * @return Inventory that the ItemStack is being put into
     */
    @NotNull
    public Inventory getDestination() {
        return this.destinationInventory;
    }

    /**
     * Gets the Inventory that initiated the transfer. This will always be
     * either the destination or source Inventory.
     *
     * @return Inventory that initiated the transfer
     */
    @NotNull
    public Inventory getInitiator() {
        return this.didSourceInitiate ? this.sourceInventory : this.destinationInventory;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
