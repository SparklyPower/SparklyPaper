# Borked Patches

List of *maybe* borked and *maybe* useless patches that I found in other fork's that may be borked or not do anything useful, so I annoted them here to remember about why I didn't cherry-pick them to SparklyPaper.

Keep in mind that I'm very naive when it comes to Minecraft Server internals, so I may be wrong!

## (Pufferfish) `Optimize-random-calls-in-chunk-ticking`

The ice improvements are... not there for some reason? They did implement the `currentIceAndSnowTick` variable and `resetIceAndSnowTick()` function that the original patch from Airplane had, but the variable is never used when ticking the chunks!

As a reference, here's Airplane's original patch: https://github.com/TECHNOVE/Airplane/blob/af3563c98bdd8b27123e3a656de261ed96652b3e/patches/server/0009-Optimize-random-calls-in-chunk-ticking.patch

## (Pufferfish) `Reduce-entity-allocations`

While not useless, the patch adds a `cachedBlockPos` variable that is never used by any other patch. Heck, not even in Airplane it was used!

## (Pufferfish) `Skip-cloning-loot-parameters`

Unnnecessarily wraps `parameters` and `dynamicDrops` into a unmodifiable map, causing unnecessary allocations.

This was useful back in 1.17 days, where the patch DID bring a meaningful benefit, since vanilla used `ImmutableMap.copyOf` instead.

As a reference, here's Airplane's original patch: https://github.com/TECHNOVE/Airplane/blob/af3563c98bdd8b27123e3a656de261ed96652b3e/patches/server/0030-Skip-cloning-loot-parameters.patch#L21