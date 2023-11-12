# Parallel World Ticking Notes

Notes about the Parallel World Ticking implementation.

i'm stoopid so this may have a lot of dumb incorrect stuff pls don't hurt me spottedleaf :(

## Opening an inventory after a world switch

If you have an event that teleports the player, and somehow that event also opens an BlockEntity inventory, the server will lock up waiting for chunks on another world.

Example:
```kotlin
@EventHandler
fun onInteract(e: PlayerInteractEvent) {
    e.player.teleport(Location(Bukkit.getWorld("..."), 0.0, 0.0, 0.0))
}
```

If you right-click on a chest, the player will be teleported, the chest will open... and the server will lock up!

This happens because the player is teleported BEFORE the inventory has been opened, the inventory is only opened AFTER the player has been teleported.

In a normal Paper server, this ain't a huge deal: The inventory is closed when the player is ticked in the new world, since it will fail the `stillValid` check.

In a parallel environment however, the `stillValid` and plugins listening for `InventoryCloseEvent` while loading the `holder` may load chunks from other worlds in a different ServerLevel Tick Thread! This locks up since we CANNOT load chunks from other worlds, because well, you know, it attempts to load on the main thread, but because the main thread is blocked...

To work around this issue...
* `openMenu` will ignore any open menu requests until player `hasTickedAtLeastOnceInNewWorld`.
* There are additional checks in the `BaseContainerBlockEntity#canUnlock`, to reject containers that were attempted to be opened after the player switched worlds.

In the future, it would be nice if `openMenu` could check if the container is still valid in the new world and then decide if the container should be closed. Currently, to open inventories after a teleport, wait 1 tick.

## TickThread Checks in NMS Level `setBlockEntity` and `getBlockEntity`

I attempted to add TickThread checks to these two methods, however, it seems like StarLight DOES access these block entities in a different "Tuinity Chunk System Worker" thread. Heck, even CoreProtect accesses these block entities from an async thread!

I looked up what Folia does, and it seems that they do have thread checks there, but it seems that they only check if it is a tick thread instead of checking if the current thread is related to the current world.

To be honest, it seems that `getBlockEntity` is thread safe. EXCEPT if you are accessing block entities from a separate `ServerLevelTickThread`! This will cause a main thread chunk load, and that will freeze the server.

The `capturedTileEntities` is also sus, but the map itself doesn't seem to be ever iterated, the only time it is iterated is via `entrySet()`. Maybe just to be extra sure, synchronize it? (Folia doesn't do that)

So, instead of doing what Folia does, we check `getBlockEntity` it via `ensureTickThreadOrAsyncThread`.

However, `setBlockEntity` still has the `ensureTickThread` check.