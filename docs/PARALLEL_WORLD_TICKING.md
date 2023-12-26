# Parallel World Ticking

"mom can we have folia?"

"we already have folia at home"

folia at home: *this*

## ⚠️⚠️⚠️ THIS IS EXPERIMENTAL ⚠️⚠️⚠️

DON'T COMPLAIN IF YOUR SERVER EXPLODES. AFTER ALL, SPARKLYPAPER WAS MADE FOR SPARKLYPOWER, ABSOUTELY NO SUPPORT FOR YOU!!!

In the Minecraft server world, there are various ways of implementing concurrent ticking, such as...

* The Vanilla Way™: All worlds are ticked sequentially.
* Parallel World Ticking: All worlds are ticked in parallel, but each tick waits for all worlds to be processed before proceeding.
* Asynchronous World Ticking: All worlds are ticked asynchronously, each world with its own tick rate. 
* Asynchronous Region Ticking: Chunks are split up in regions, and each region are ticked asynchronously. This is what [Folia](https://github.com/PaperMC/Folia) does.
* Truly independent servers: Each server has its own world, so each world is completely isolated from each other.

SparklyPaper moves world ticking to its first logical step after The Vanilla Way™, moving Vanilla's world ticking into separate threads, allowing worlds to be ticked in parallel. Every tick waits until all worlds finishes ticking. This means that your TPS is _mostly_ based off the MSPT of your heaviest world. Useful to spread out players to multiple worlds! (Example: Multiple Survival worlds)

We do run Parallel World Ticking in production @ [SparklyPower](https://sparklypower.net/). Our server was being bottlenecked by all the things our Survival world needed to tick (such as Villagers, blocks like farmland, those pesky Axolotls, etc) that we needed a solution. We first tried to go with the "one server per world" but after looking at so much complexity we would need to handle, such as...

* How are you going to do Inventory syncing?
* How are you going to maintain multiple servers?
* How are you going to query how many players are connected to *all* servers?
* How are you going to implement player name autocomplete, if players may be on different servers?
* Are you ready to reimplement vanilla commands such as `/tp` and `/give`?
 
That we decided that maybe it was time to pull off a crazy patch to do parallel world ticking instead.

Synchronization issues *are expected to happen*. Thread checks are still present and only the `ServerLevelTickThread` that is bound to the modified world, or the main `TickThread`, can modify world data. Plugins will break with Parallel World Ticking if they attempt to modify other worlds in a thread that isn't theirs! Plugins can work around this by scheduling a main thread task.

## Well, if Folia has a superior ticking system, why not use Folia?

Folia is amazing, in fact, a lot of the code used for parallel world ticking was heavily inspired by... and, uh, copied from... Folia.

However, due to the way Folia works, a lot of plugins *will* break and require updates from their maintainers to make them work in Folia. Which is why Folia, by default, does not allow plugins not marked as Folia compatible to work.

With Parallel World Ticking, not a lot of plugins *should* break since plugins mostly do stuff on the same world that the event has been triggered, and player actions and a bunch of other stuff are still processed on the main thread, so a lot of plugins should, hopefully, work out of the box. Of course, the downside is that Parallel World Ticking does not provide all the performance advantages that Folia has, and you are forced to break down your players into multiple worlds to get those sweet TPS improvements, while with Folia you only need to get players to be far from each other in the same world.

However, there are things that WILL BREAK, such as teleporting players/entities to another world on events called on the server level tick thread. You can work around these issues by scheduling these API calls to be run after all worlds are ticked.

Because Minecraft's vanilla mechanics do not interfer with other worlds, aside from portal/end portal respawn, maintaining the vanilla behavior for items is easier compared to Folia.

So this is a stopgap solution while Folia isn't ready for prime time yet, without requiring you to do the whole "one servers for each world" approach, which is way harder to develop, handle, and maintain.

Besides, it is fun!

## If this is possible, why Paper doesn't have it built-in?

Plugins, CraftBukkit, and the Minecraft Server itself, weren't really made with parallel world ticking in mind.

Adding this to Paper would ensure that a lot of angry users would complain to Paper that plugin xyz isn't working. This is also the reason about why Folia only allows loading plugins marked as Folia compatible.

## I've heard that anything async related in the Minecraft code is bad

Yes, attempts to do ✨ async magic ✨ in the Minecraft server aren't a new thing. This has been done in the past in Akarin, Yatopia, and other forks ([patch](https://github.com/YatopiaMC/Yatopia/blob/1a54ef2f995f049d4fcf1f2bd084691126f10046/patches/server/0046-Option-for-async-world-ticking.patch)).

However, the previous attempts were based on "`synchronize` and pray", which is why they were unstable and not recommended for production.

MrPowerGamerBR from 2018 had even made a meme making fun of these patches.

![https://cdn.discordapp.com/attachments/289587909051416579/482922902283485184/async_forks.png?ex=6558cd80&is=65465880&hm=28743988187da5dfa050c417ca9fa575c6924b6631c549f93e3186522a376c82&](https://cdn.discordapp.com/attachments/289587909051416579/482922902283485184/async_forks.png?ex=6558cd80&is=65465880&hm=28743988187da5dfa050c417ca9fa575c6924b6631c549f93e3186522a376c82&)

SparklyPaper follows Folia's footsteps and keeps everything in check, keeping all tick thread checks in the code. Most of the groundwork had already been done by Spottedleaf and the Paper team. thx leaf *pets the leaf*

But of course, that doesn't mean that SparklyPaper is perfect! If your server crashes, that ain't gonna be my fault xd.

## I live on the edge and I don't want random "not on main thread" throws

Off-main thread throws can be disabled with `-Dsparklypaper.disableHardThrow=true`, but THIS IS NOT RECOMMENDED because you SHOULD FIX THE ISSUES THEMSELVES instead of RISKING DATA CORRUPTION!!! The server will still log the stacktrace of where the exception happened.

In fact, disabling throws is not an easy way out: Yes, you avoid some functions borking out. But, if the tick thread check has failed, your server is probably going to crash anyway. Example: If a plugin is attempting to teleport a player to world X while they are in a TickThread of world Y, the server will lock up because loading chunks outside of the world's tick thread or from an async thread is not allowed but if you had kept hard throws enabled, your server wouldn't have crashed because the request would've been denied! Fix the dang issues instead!!!

## Profiling with Spark

By default, Spark will profile the `Server thread` thread, which ain't good for us if we want to profile what is being used in our worlds.

Spark has an undocumented configuration setting to configure what threads the background profiler will track.

In Spark's `config.json`, add `"backgroundProfilerThreadDumper": "all"` to dump all threads used in server.

When looking at the profiler result, the server level tick threads are named `serverlevel-tick-worker [WorldNameHere]`.

We use a single thread per world instead of a thread pool to be easier to track down what thing is lagging which world. However, parallel thread execution is limited by a semaphore based on the `parallel-world-ticking.threads` value.

## Can I disable this?

SparklyPaper was tailor-made for SparklyPower with features that we need, so... no.

I'm not even sure why this question is even here considering that the only real SparklyPaper user is myself. :3

But you can build, or download SparklyPaper builds, without the parallel world ticking added!

## Disabled Features and Plugin Incompatibilities

[Here's a list of plugins that have issues with parallel world ticking](PARALLEL_INCOMPATIBLE_PLUGINS.md)

### Disabled/Broken Features
* Parallel World Ticking does not work with Aikar's Timings because Timings wasn't really meant for asynchronous stuff, so the Timings stack becomes corrupted when the worlds are ticked and the server crashes, please use Spark instead!
* Non-player entities cannot cross a Nether/End Portal.

## Implementation Notes

If you are curious about things that I've learned while making this, I wrote [some notes about why some things were implemented the way that they are](PARALLEL_NOTES.md). 
