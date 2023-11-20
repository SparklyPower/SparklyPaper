<p align="center">
<img src="sparklypaper.png">
</p>

<p align="center">
<h1 align="center">✨ SparklyPaper ✨</h1>
</p>

SparklyPower's Paper fork, with a mix of weird & crazy patches from other forks!

While our fork is mostly cherry-picked patches from other forks, we do have some handmade patches too to add and optimize some of the things that we have in our server!

## Features

This does not include all patches included in SparklyPaper, only the patches exclusive to SparklyPaper! To see all patches, check out the ["patches" directory](patches).

SparklyPaper's config file is `sparklypaper.yml`, the file is, by default, placed on the root of your server.

* Configurable Farm Land moisture tick rate when the block is already moisturised
  * The `isNearWater` check is costly, especially if you have a lot of farm lands. If the block is already moistured, we can change the tick rate of it to avoid these expensive `isNearWater` checks.
* Skip `distanceToSqr` call in `ServerEntity#sendChanges` if the delta movement hasn't changed
  * The `distanceToSqr` call is a bit expensive, so avoiding it is pretty nice, around ~15% calls are skipped with this check. Currently, we only check if both Vec3 objects have the same identity, that means, if they are literally the same object. (that works because Minecraft's code reuses the Vec3 object when caching the current delta movement)
* Skip `MapItem#update()` if the map does not have the default `CraftMapRenderer` present
  * By default, maps, even those with custom renderers, fetch the world data to update the map data. With this change, "image in map" maps that have removed the default `CraftMapRenderer` can avoid these hefty updates, without requiring the map to be locked, which some old map plugins may not do.
  * This has the disadvantage that the vanilla map data will never be updated while the CraftMapRenderer is not present, so if you readd the default renderer, the server will need to update the map data, but that's not a huuuge problem, after all, it is a very rare circumstance that you may need the map data to always be up-to-date when you have a custom renderer on the map.
  * But still, if you made your own custom "image on map" plugin, don't forget to `mapView.isLocked = true` to get the same performance benefits in vanilla Paper!
* Fix concurrency issues when using `imageToBytes` in multiple threads
  * Useful if one of your plugins is parallelizng map creation on server startup
* Optimize `ServerStatsCounter`'s dirty set
  * Instead of using Java's HashSet, we will use fastutil's `ObjectOpenHashSet`, which has better performance
  * While this seems stupid, awardStat was using around ~0.14% when adding to the `HashSet`, and that's not good
  * We also optimized the `getDirty` calls. I mean, the *only* `getDirty` call. Because the map was only retrieved once, we don't actually need to create a copy of the map just to iterate it, we can just access it directly and clear it manually after use.
* ~~Avoid unnecessary `ItemFrame#getItem()` calls~~
  * ~~When ticking an item frame, on each tick, it checks if the item on the item frame is a map and, if it is, it adds the map to be carried by the entity player~~
  * ~~However, the `getItem()` call is a bit expensive, especially because this is only really used if the item in the item frame is a map~~
  * ~~We can avoid this call by checking if the `cachedMapId` is not null, if it is, then we get the item in the item frame, if not, then we ignore the `getItem()` call.~~
  * Replaced by [Warriorrrr's "Rewrite framed map tracker ticking" patch (Paper #9605)](https://github.com/PaperMC/Paper/pull/9605)
* Optimize `EntityScheduler`'s `executeTick`
  * On each tick, Paper runs `EntityScheduler`'s `executeTick` of each entity. This is a bit expensive, due to `ArrayDeque`'s `size()` call because it ain't a simple "get the current queue size" function, and due to the thread checks.
  * To avoid those hefty calls, we check if we *really* need to execute the `executeTick`, by doing two changes:
    * First, we check if the scheduler had a task scheduled to it at least once, if not, we bail out early and quickly. In this case, we don't even increase the scheduler's `tickCount`, nor do we check if the scheduler has been retired. This is not an issue, since `tickCount` is relative and not bound to the server's current tick, so it doesn't matter if we don't increase it.
    * Then, we check if the scheduler has any pending tasks by checking if `currentlyExecuting` is not empty or if `oneTimeDelayed` is not empty. This avoids the thread checks and unnecessary `size()` calls.
  * Most entities won't have any scheduled tasks, so this is a nice performance bonus. These optimizations, however, wouldn't work in a Folia environment, but because in SparklyPaper `executeTick` is always executed on the main thread, it ain't an issue for us (yay). With this change, the `executeTick` loop in `tickChildren` CPU % usage drops from 2.48% to 0.61% in a server with ~12k entities!
  * Of course, this doesn't mean that `ArrayDeque#size()` is slow! It is mostly that because the `executeTick` function is called each tick for each entity, it would be better for us to avoid as many useless calls as possible.
* Check how much MSPT (milliseconds per tick) each world is using in `/mspt`
  * Useful to figure out which worlds are lagging your server.
![Per World MSPT](docs/per-world-mspt.png)
* Parallel World Ticking
  * "mom can we have folia?" "we already have folia at home" folia at home: [Parallel World Ticking](docs/PARALLEL_WORLD_TICKING.md)

We don't cherry-pick *everything* from other forks, only patches that I can see and think "yeah, I can see how this would improve performance" or patches that target specific performance/feature pain points in our server are cherry-picked! In fact, some patches that are used in other forks [may be actually borked](docs/BORKED_PATCHES.md)...

## Support

Because this is a fork made for SparklyPower, we won't give support for any issues that may happen in your server when using SparklyPaper. We know that SparklyPaper may break some plugins, but unless we use these plugins on SparklyPower, we won't go out of our way to fix it!

If you only care about some of the patches included in SparklyPaper, it is better for you to [create your own fork](https://github.com/PaperMC/paperweight-examples) and cherry-pick the patches, this way you have full control of what patches you want to use in your server, and even create your own changes!

## Downloads

You can download SparklyPaper's Paperclip JAR [here](https://github.com/SparklyPower/SparklyPaper/actions/workflows/build.yml). Click on a workflow run, scroll down to the Artifacts, and download!