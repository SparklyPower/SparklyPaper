# Borked Patches

List of *maybe* borked and *maybe* useless patches that I found in other fork's that may be borked or not do anything useful, so I annoted them here to remember about why I didn't cherry-pick them to SparklyPaper.

Keep in mind that I'm very naive when it comes to Minecraft Server internals, so I may be wrong!

## (Pufferfish) `Optimize-random-calls-in-chunk-ticking`

The ice improvements are... not there for some reason? They did implement the `currentIceAndSnowTick` variable and `resetIceAndSnowTick()` function that the original patch from Airplane had, but the variable is never used when ticking the chunks!

As a reference, here's Airplane's original patch: https://github.com/TECHNOVE/Airplane/blob/af3563c98bdd8b27123e3a656de261ed96652b3e/patches/server/0009-Optimize-random-calls-in-chunk-ticking.patch

## (Pufferfish) `Flare-Profiler`

Honestly I'm even sure why Pufferfish included this patch... Yes, [I know what Flare is](https://blog.airplane.gg/flare/), but here's the thing:

1. The [Flare URL](https://flare.airplane.gg) in Pufferfish's configuration seems to be down (in fact, they are using Airplane's Flare URL)
2. Flare requires an authorization token that you get by subscribing to Airplane's Patreon, which [doesn't exist anymore](https://patreon.com/airplane) and required you to log in to Airplane's authentication servers, which [is also down](https://auth.airplane.gg/).

I do understand about why Airplane made this patch, but I don't get about why Pufferfish included this patch...  This would only be useful if someone is [hosting their own Flare instance](https://github.com/TECHNOVE/Flare) and wants to keep using Flare, but nowadays, I think that Flare can be replaced by [spark](https://spark.lucko.me/).

## (Pufferfish) `Reduce-entity-allocations`

While not useless, the patch adds a `cachedBlockPos` variable that is never used by any other patch. Heck, not even in Airplane it was used!

## (Pufferfish) `Skip-cloning-loot-parameters`

Unnnecessarily wraps `parameters` and `dynamicDrops` into a unmodifiable map, causing unnecessary allocations.

This was useful back in 1.17 days, where the patch DID bring a meaningful benefit, since vanilla used `ImmutableMap.copyOf` instead.

As a reference, here's Airplane's original patch: https://github.com/TECHNOVE/Airplane/blob/af3563c98bdd8b27123e3a656de261ed96652b3e/patches/server/0030-Skip-cloning-loot-parameters.patch#L21