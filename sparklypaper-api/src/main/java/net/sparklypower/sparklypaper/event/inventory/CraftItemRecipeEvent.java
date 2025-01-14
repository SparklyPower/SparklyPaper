package net.sparklypower.sparklypaper.event.inventory;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when the recipe of an Item is completed inside a crafting matrix.
 *
 * This is an alternate version of [org.bukkit.event.inventory.CraftItemEvent], where this one is called for player crafting items and crafters.
 */
public class CraftItemRecipeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Recipe recipe;
    private final ItemStack @Nullable [] matrix;
    private ItemStack result;
    private boolean isCancelled = false;

    public CraftItemRecipeEvent(@NotNull ItemStack @Nullable [] matrix, @NotNull Recipe recipe, @Nullable ItemStack result) {
        this.matrix = matrix;
        this.recipe = recipe;
        this.result = result;
    }

    public void setResult(@Nullable ItemStack result) {
        this.result = result;
    }

    @Nullable
    public ItemStack getResult() {
        return result;
    }

    /**
     * @return A copy of the current recipe on the crafting matrix.
     */
    @NotNull
    public Recipe getRecipe() {
        return recipe;
    }

    public @Nullable ItemStack[] getCraftingMatrix() {
        return matrix;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
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

