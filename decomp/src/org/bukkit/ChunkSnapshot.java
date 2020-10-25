package org.bukkit;

import org.bukkit.block.Biome;

public interface ChunkSnapshot {
  int getX();
  
  int getZ();
  
  String getWorldName();
  
  int getBlockTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockData(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockSkyLight(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockEmittedLight(int paramInt1, int paramInt2, int paramInt3);
  
  int getHighestBlockYAt(int paramInt1, int paramInt2);
  
  Biome getBiome(int paramInt1, int paramInt2);
  
  double getRawBiomeTemperature(int paramInt1, int paramInt2);
  
  double getRawBiomeRainfall(int paramInt1, int paramInt2);
  
  long getCaptureFullTime();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\ChunkSnapshot.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */