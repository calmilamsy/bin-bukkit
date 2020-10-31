package org.bukkit.craftbukkit.mixin;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.source.LevelSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tileentity.TileEntityBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.craftbukkit.mixin.accessor.LevelPropertiesAccessor;
import org.bukkit.craftbukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;

@Mixin(Level.class)
public abstract class MixinLevel implements CraftLevel {

    @Shadow public abstract long getSeed();

    @Shadow protected LevelProperties properties;
    @Shadow public boolean field_221;
    @Shadow @Final public Dimension dimension;

    @Shadow protected LevelSource cache;

    @Shadow public abstract int getTileId(int x, int y, int z);

    @Shadow protected abstract void method_235(int i, int j, int k, int i1);

    @Shadow public abstract boolean spawnEntity(EntityBase arg);

    @Mutable
    private @Final CraftWorld world;
    public boolean pvpMode;
    public boolean keepSpawnInMemory = true;
    public ChunkGenerator generator;
    Chunk lastChunkAccessed;
    int lastXAccessed = Integer.MIN_VALUE;
    int lastZAccessed = Integer.MIN_VALUE;
    final Object chunkLock = new Object();
    private List<TileEntityBase> tileEntitiesToUnload;


    @Override
    public void setGeneratorAndEnvironment(ChunkGenerator gen, World.Environment env) {
        generator = gen;
        world = new CraftWorld((ServerLevel) (Object) this, gen, env);
        getServer().addWorld(world);
    }

    @Override
    public CraftWorld getWorld() {
        return world;
    }

    @Override
    public CraftServer getServer() {
        return (CraftServer) Bukkit.getServer();
    }

    @Inject(method = "method_212()V", at = @At(value = "CONSTANT", args = "intValue=0", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    private void cb1(CallbackInfo ci) {
        if (generator != null) {
            Random rand = new Random(this.getSeed());
            Location spawn = generator.getFixedSpawnLocation(getWorld(), rand);

            if (spawn != null) {
                if (spawn.getWorld() != getWorld()) {
                    throw new IllegalStateException("Cannot set spawn point for " + ((LevelPropertiesAccessor) properties).getLevelName() + " to be in another world (" + spawn.getWorld().getName() + ")");
                } else {
                    properties.setSpawnPosition(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
                    field_221 = false;
                    ci.cancel();
                }
            }
        }
    }

    @Redirect(method = "method_212()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/dimension/Dimension;method_1770(II)Z"))
    private boolean cb2(Dimension dimension, int i, int j) {
        return canSpawn(i, j);
    }

    private boolean canSpawn(int x, int z) {
        if (generator != null)
            return generator.canSpawn(getWorld(), x, z);
        else
            return dimension.method_1770(x, z);
    }

    @Override
    public Chunk getChunkAt(int i, int j) {
        Chunk result;
        synchronized (this.chunkLock) {
            if (this.lastChunkAccessed == null || this.lastXAccessed != i || this.lastZAccessed != j) {
                this.lastXAccessed = i;
                this.lastZAccessed = j;
                this.lastChunkAccessed = cache.getChunk(i, j);
            }
            result = this.lastChunkAccessed;
        }
        return result;
    }

    @Inject(method = "setTile(IIII)Z", at = @At("HEAD"))
    private void cb3(int i, int j, int k, int i1, CallbackInfoReturnable<Boolean> cir) {
        old = getTileId(i, j, k);
    }

    @Redirect(method = {"setTile(IIII)Z", "placeBlockWithMetaData(IIIII)Z"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;method_235(IIII)V"))
    private void cb4(Level level, int i, int j, int k, int i1) {
        method_235(i, j, k, i1 == 0 ? old : i1);
    }

    @Inject(method = "placeBlockWithMetaData(IIIII)Z", at = @At("HEAD"))
    private void cb5(int i, int j, int k, int i1, int j1, CallbackInfoReturnable<Boolean> cir) {
        old = getTileId(i, j, k);
    }

    @Inject(method = "updateAdjacentBlock(IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockBase;onAdjacentBlockUpdate(Lnet/minecraft/level/Level;IIII)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void cb6(int x, int y, int z, int id, CallbackInfo ci, BlockBase var5) {
        CraftWorld world = getWorld();
        if (world != null) {
            BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(x, y, z), id);
            this.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "spawnEntity(Lnet/minecraft/entity/EntityBase;)Z", at = @At("HEAD"))
    private void cb7(EntityBase arg, CallbackInfoReturnable<Boolean> cir) {
        if (spawnReasonAbsent)
            spawnReason = CreatureSpawnEvent.SpawnReason.CUSTOM;
        else
            spawnReasonAbsent = true;
        if (arg instanceof Living && !(arg instanceof ServerPlayer)) {
            CreatureSpawnEvent event = CraftEventFactory.callCreatureSpawnEvent((Living) arg, spawnReason);

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        } else if (arg instanceof Item) {
            ItemSpawnEvent event = CraftEventFactory.callItemSpawnEvent((Item) arg);
            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Override
    public boolean addEntity(EntityBase entityBase, CreatureSpawnEvent.SpawnReason spawnReason) {
        spawnReasonAbsent = false;
        this.spawnReason = spawnReason;
        return spawnEntity(entityBase);
    }


    private int old;
    private CreatureSpawnEvent.SpawnReason spawnReason;
    private boolean spawnReasonAbsent = true;
}
