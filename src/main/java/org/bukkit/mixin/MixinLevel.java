package org.bukkit.mixin;

import net.minecraft.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;

@Mixin(Level.class)
public class MixinLevel implements CraftLevel {

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
    public CraftServer getServer() {
        return (CraftServer) Bukkit.getServer();
    }
}
