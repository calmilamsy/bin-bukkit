package org.bukkit.mixin;

import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.mixin.accessor.LevelPropertiesAccessor;
import org.bukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Level.class)
public abstract class MixinLevel implements CraftLevel {

    @Shadow public abstract long getSeed();

    @Shadow protected LevelProperties properties;
    @Shadow public boolean field_221;
    @Shadow @Final public Dimension dimension;
    public ChunkGenerator generator;
    @Mutable
    private @Final CraftWorld world;


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
}
