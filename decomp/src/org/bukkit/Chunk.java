package org.bukkit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

public interface Chunk {
  int getX();
  
  int getZ();
  
  World getWorld();
  
  Block getBlock(int paramInt1, int paramInt2, int paramInt3);
  
  ChunkSnapshot getChunkSnapshot();
  
  ChunkSnapshot getChunkSnapshot(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  Entity[] getEntities();
  
  BlockState[] getTileEntities();
  
  boolean isLoaded();
  
  boolean load(boolean paramBoolean);
  
  boolean load();
  
  boolean unload(boolean paramBoolean1, boolean paramBoolean2);
  
  boolean unload(boolean paramBoolean);
  
  boolean unload();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\Chunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */