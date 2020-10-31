package org.bukkit.craftbukkit.util.mixin;

import net.minecraft.entity.EntityBase;
import net.minecraft.level.chunk.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.ChunkGenerator;

public interface CraftLevel {

    void setGeneratorAndEnvironment(ChunkGenerator gen, World.Environment env);

    CraftWorld getWorld();

    CraftServer getServer();

    Chunk getChunkAt(int i, int j);

    boolean addEntity(EntityBase entityBase, CreatureSpawnEvent.SpawnReason spawnReason);
}
