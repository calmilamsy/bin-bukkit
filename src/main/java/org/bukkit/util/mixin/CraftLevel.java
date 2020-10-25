package org.bukkit.util.mixin;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.ChunkGenerator;

public interface CraftLevel {

    void setGeneratorAndEnvironment(ChunkGenerator gen, World.Environment env);

    CraftWorld getWorld();

    CraftServer getServer();
}
