<p align="center">
<img src="sparklypaper.png">
</p>

<p align="center">
<h1 align="center">✨ SparklyPaper ✨</h1>
</p>

SparklyPower's Paper fork, with a mix of weird & crazy patches from other forks!

While our fork is mostly cherry-picked patches from other forks, we do have some handmade patches too to add and optimize some of the things that we have in our server!

* Configurable Farm Land moisture tick rate when the block is already moisturised
  * The isNearWater check is costly, especially if you have a lot of farm lands. If the block is already moistured, we can change the tick rate of it to avoid these expensive isNearWater checks.
* Only check thundering once per world instead for every chunk
  * For some reason the isThundering check is consuming ~3% of CPU time when profiled so, instead of checking the thunder every chunk, we can cache the result and reuse on the same chunk tick.

We don't cherry-pick *everything* from other forks, only patches that I can see and think "yeah, I can see how this would improve performance" or patches that target specific performance/feature pain points in our server are cherry-picked! In fact, some patches that are used in other forks [may be actually borked](BORKED_PATCHES.md)...