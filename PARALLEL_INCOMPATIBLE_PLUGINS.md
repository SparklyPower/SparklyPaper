# Plugins incompatible with Parallel World Ticking

A list of plugins that I found out that causes issues with Parallel World Ticking.

This is not a full comprehensive list because I only use a few public plugins, most of the plugins I use on SparklyPower are made by myself.

## NoCheatPlus (Updated)

The movement checks can crash the server due to concurrency issues (more below), disable them in the config.

```javastacktrace
[03:14:05] [serverlevel-tick-worker-8/ERROR]: THE SERVER IS GOING TO CRASH! - Thread serverlevel-tick-worker-8 failed main thread check: Cannot query another world's (world) chunk (25, 16) in a ServerLevelTickThread - Is tick thread? true; Is server level tick thread? true; Currently ticking level: ArenasPvP; Is iterating over levels? true; Are we going to hard throw? false
java.lang.Throwable: null
	at net.minecraft.server.level.ServerChunkCache.getChunk(ServerChunkCache.java:272) ~[?:?]
	at net.minecraft.world.level.Level.getChunk(Level.java:900) ~[?:?]
	at net.minecraft.world.level.Level.getBlockState(Level.java:1175) ~[?:?]
	at org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock.getType(CraftBlock.java:238) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at fr.neatmonster.nocheatplus.compat.bukkit.BlockCacheBukkit.fetchTypeId(BlockCacheBukkit.java:52) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.map.BlockCache.getOrCreateNode(BlockCache.java:317) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.map.BlockCache.getType(BlockCache.java:379) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.map.BlockProperties.collectFlagsSimple(BlockProperties.java:4454) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.location.RichBoundsLocation.collectBlockFlags(RichBoundsLocation.java:1327) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.location.RichBoundsLocation.collectBlockFlags(RichBoundsLocation.java:1309) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.checks.moving.model.LocationData.setExtraProperties(LocationData.java:92) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.checks.moving.model.MoveData.setWithExtraProperties(MoveData.java:207) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.checks.moving.MovingData.resetPlayerPositions(MovingData.java:585) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.checks.moving.util.AuxMoving.resetPositionsAndMediumProperties(AuxMoving.java:116) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.checks.moving.MovingListener.onPlayerTeleportMonitor(MovingListener.java:1989) ~[NoCheatPlus.jar:?]
	at jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103) ~[?:?]
	at java.lang.reflect.Method.invoke(Method.java:580) ~[?:?]
	at fr.neatmonster.nocheatplus.event.mini.MultiListenerRegistry$AutoListener.onEvent(MultiListenerRegistry.java:82) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.event.mini.MiniListenerNode.onEvent(MiniListenerNode.java:157) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.event.mini.EventRegistryBukkit$4.execute(EventRegistryBukkit.java:124) ~[NoCheatPlus.jar:?]
	at co.aikar.timings.TimedEventExecutor.execute(TimedEventExecutor.java:77) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:70) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at io.papermc.paper.plugin.manager.PaperEventManager.callEvent(PaperEventManager.java:54) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at io.papermc.paper.plugin.manager.PaperPluginManagerImpl.callEvent(PaperPluginManagerImpl.java:126) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:615) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer.teleport(CraftPlayer.java:1354) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer.teleport(CraftPlayer.java:1252) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at net.perfectdreams.dreamcore.utils.extensions.EntityExtensionsKt.teleportToServerSpawn(EntityExtensions.kt:13) ~[DreamCore-reobf.jar:?]
	at net.perfectdreams.dreamcore.utils.extensions.EntityExtensionsKt.teleportToServerSpawnWithEffects(EntityExtensions.kt:20) ~[DreamCore-reobf.jar:?]
	at net.perfectdreams.dreamcore.utils.extensions.EntityExtensionsKt.teleportToServerSpawnWithEffects$default(EntityExtensions.kt:19) ~[DreamCore-reobf.jar:?]
	at net.perfectdreams.dreamxizum.utils.ArenaXizum.finishArena(ArenaXizum.kt:185) ~[DreamXizum-reobf.jar:?]
	at net.perfectdreams.dreamxizum.listeners.XizumListener.onDeath(XizumListener.kt:55) ~[DreamXizum-reobf.jar:?]
	at com.destroystokyo.paper.event.executor.asm.generated.GeneratedEventExecutor732.execute(Unknown Source) ~[?:?]
	at org.bukkit.plugin.EventExecutor$2.execute(EventExecutor.java:77) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at co.aikar.timings.TimedEventExecutor.execute(TimedEventExecutor.java:77) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:70) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at io.papermc.paper.plugin.manager.PaperEventManager.callEvent(PaperEventManager.java:54) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at io.papermc.paper.plugin.manager.PaperPluginManagerImpl.callEvent(PaperPluginManagerImpl.java:126) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:615) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory.callPlayerDeathEvent(CraftEventFactory.java:984) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at net.minecraft.server.level.ServerPlayer.die(ServerPlayer.java:961) ~[?:?]
	at net.minecraft.world.entity.LivingEntity.hurt(LivingEntity.java:1548) ~[?:?]
	at net.minecraft.world.entity.player.Player.hurt(Player.java:973) ~[?:?]
	at net.minecraft.server.level.ServerPlayer.hurt(ServerPlayer.java:1130) ~[?:?]
	at net.minecraft.world.entity.projectile.AbstractArrow.onHitEntity(AbstractArrow.java:402) ~[?:?]
	at net.minecraft.world.entity.projectile.Projectile.onHit(Projectile.java:206) ~[?:?]
	at net.minecraft.world.entity.projectile.Projectile.preOnHit(Projectile.java:197) ~[?:?]
	at net.minecraft.world.entity.projectile.AbstractArrow.preOnHit(AbstractArrow.java:296) ~[?:?]
	at net.minecraft.world.entity.projectile.AbstractArrow.tick(AbstractArrow.java:232) ~[?:?]
	at net.minecraft.world.entity.projectile.Arrow.tick(Arrow.java:112) ~[?:?]
	at net.minecraft.server.level.ServerLevel.tickNonPassenger(ServerLevel.java:1392) ~[?:?]
	at net.minecraft.world.level.Level.guardEntityTick(Level.java:1314) ~[?:?]
	at net.minecraft.server.level.ServerLevel.lambda$tick$8(ServerLevel.java:906) ~[?:?]
	at net.minecraft.world.level.entity.EntityTickList.forEach(EntityTickList.java:49) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at net.minecraft.server.level.ServerLevel.tick(ServerLevel.java:886) ~[?:?]
	at net.minecraft.server.MinecraftServer.lambda$tickChildren$16(MinecraftServer.java:1600) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572) ~[?:?]
	at java.util.concurrent.FutureTask.run(FutureTask.java:317) ~[?:?]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[?:?]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[?:?]
	at java.lang.Thread.run(Thread.java:1583) ~[?:?]
```

The Change Tracker also has a concurrency bug. NoCheatPlus uses `setAccess` to control which world is being used in `BlockCacheBukkit`. This is fine in a sequential ticking server, but in a parallel ticking server, there is a race condition where one server level tick thread changes the world to `World1`, then another thread changes the world to `World2`, then whoops, a crash happens!

This also breaks the movement checks above: While the movement checks do have `synchronized` in their methods, the falling block checks don't, and both use the same BlockCache instance (handled by `setAccess`).

https://github.com/Updated-NoCheatPlus/NoCheatPlus/blob/64bf374fd39297bab5b7adb646063debbd12643e/NCPCompatBukkit/src/main/java/fr/neatmonster/nocheatplus/compat/bukkit/BlockCacheBukkit.java#L52

To work around this, disable `changetracker.active` and `changetracker.pistons`.

I think that NoCheatPlus may have other concurrency issues too due to its use of `setAccess` around the code. In fact, I think that NoCheatPlus has the same issues in Folia too, even tho NoCheatPlus is marked as "Folia supported".

```javastacktrace
[03:35:04] [serverlevel-tick-worker-6/ERROR]: THE SERVER IS GOING TO CRASH! - Thread serverlevel-tick-worker-6 failed main thread check: Cannot query another world's (world) chunk (-817, -45) in a ServerLevelTickThread - Is tick thread? true; Is server level tick thread? true; Currently ticking level: Resources; Is iterating over levels? true; Are we going to hard throw? false
java.lang.Throwable: null
	at net.minecraft.server.level.ServerChunkCache.getChunk(ServerChunkCache.java:272) ~[?:?]
	at net.minecraft.world.level.Level.getChunk(Level.java:900) ~[?:?]
	at net.minecraft.world.level.Level.getBlockState(Level.java:1175) ~[?:?]
	at org.bukkit.craftbukkit.v1_20_R2.block.CraftBlock.getType(CraftBlock.java:238) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at fr.neatmonster.nocheatplus.compat.bukkit.BlockCacheBukkit.fetchTypeId(BlockCacheBukkit.java:52) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.map.BlockCache.getOrCreateNode(BlockCache.java:317) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.utilities.map.BlockCache.getOrCreateBlockCacheNode(BlockCache.java:442) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeTracker.addBlock(BlockChangeTracker.java:571) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeTracker.addBlocks(BlockChangeTracker.java:433) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeTracker.addBlocks(BlockChangeTracker.java:394) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeListener.onEntityChangeBlock(BlockChangeListener.java:300) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeListener.access$200(BlockChangeListener.java:56) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeListener$2.onEvent(BlockChangeListener.java:99) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.compat.blocks.changetracker.BlockChangeListener$2.onEvent(BlockChangeListener.java:93) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.event.mini.MiniListenerNode.onEvent(MiniListenerNode.java:157) ~[NoCheatPlus.jar:?]
	at fr.neatmonster.nocheatplus.event.mini.EventRegistryBukkit$4.execute(EventRegistryBukkit.java:124) ~[NoCheatPlus.jar:?]
	at co.aikar.timings.TimedEventExecutor.execute(TimedEventExecutor.java:77) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:70) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at io.papermc.paper.plugin.manager.PaperEventManager.callEvent(PaperEventManager.java:54) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at io.papermc.paper.plugin.manager.PaperPluginManagerImpl.callEvent(PaperPluginManagerImpl.java:126) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:615) ~[sparklypaper-api-1.20.2-R0.1-SNAPSHOT.jar:?]
	at org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory.callEntityChangeBlockEvent(CraftEventFactory.java:1411) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at org.bukkit.craftbukkit.v1_20_R2.event.CraftEventFactory.callEntityChangeBlockEvent(CraftEventFactory.java:1403) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at net.minecraft.world.entity.item.FallingBlockEntity.fall(FallingBlockEntity.java:98) ~[?:?]
	at net.minecraft.world.entity.item.FallingBlockEntity.fall(FallingBlockEntity.java:92) ~[?:?]
	at net.minecraft.world.level.block.FallingBlock.tick(FallingBlock.java:37) ~[?:?]
	at net.minecraft.world.level.block.state.BlockBehaviour$BlockStateBase.tick(BlockBehaviour.java:1205) ~[?:?]
	at net.minecraft.server.level.ServerLevel.tickBlock(ServerLevel.java:1340) ~[?:?]
	at net.minecraft.world.ticks.LevelTicks.runCollectedTicks(LevelTicks.java:197) ~[?:?]
	at net.minecraft.world.ticks.LevelTicks.tick(LevelTicks.java:94) ~[?:?]
	at net.minecraft.server.level.ServerLevel.tick(ServerLevel.java:848) ~[?:?]
	at net.minecraft.server.MinecraftServer.lambda$tickChildren$16(MinecraftServer.java:1600) ~[sparklypaper-1.20.2.jar:git-SparklyPaper-"049a8e5"]
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572) ~[?:?]
	at java.util.concurrent.FutureTask.run(FutureTask.java:317) ~[?:?]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[?:?]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[?:?]
	at java.lang.Thread.run(Thread.java:1583) ~[?:?]
```

## MyPet

If a player mounted on a Warden teleports to another world, the server crashes.

This is caused by https://github.com/MyPetORG/MyPet/issues/1647 and can even cause issues in vanilla Paper. In vanilla Paper, instead of crashing the server, the player is teleported back to the Warden's location.

Fork that removes the affecting code: https://github.com/SparklyPower/MyPet