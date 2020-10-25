package org.bukkit.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public interface Block {
  byte getData();
  
  @Deprecated
  Block getFace(BlockFace paramBlockFace);
  
  @Deprecated
  Block getFace(BlockFace paramBlockFace, int paramInt);
  
  Block getRelative(int paramInt1, int paramInt2, int paramInt3);
  
  Block getRelative(BlockFace paramBlockFace);
  
  Block getRelative(BlockFace paramBlockFace, int paramInt);
  
  Material getType();
  
  int getTypeId();
  
  byte getLightLevel();
  
  World getWorld();
  
  int getX();
  
  int getY();
  
  int getZ();
  
  Location getLocation();
  
  Chunk getChunk();
  
  void setData(byte paramByte);
  
  void setData(byte paramByte, boolean paramBoolean);
  
  void setType(Material paramMaterial);
  
  boolean setTypeId(int paramInt);
  
  boolean setTypeId(int paramInt, boolean paramBoolean);
  
  boolean setTypeIdAndData(int paramInt, byte paramByte, boolean paramBoolean);
  
  BlockFace getFace(Block paramBlock);
  
  BlockState getState();
  
  Biome getBiome();
  
  boolean isBlockPowered();
  
  boolean isBlockIndirectlyPowered();
  
  boolean isBlockFacePowered(BlockFace paramBlockFace);
  
  boolean isBlockFaceIndirectlyPowered(BlockFace paramBlockFace);
  
  int getBlockPower(BlockFace paramBlockFace);
  
  int getBlockPower();
  
  boolean isEmpty();
  
  boolean isLiquid();
  
  double getTemperature();
  
  double getHumidity();
  
  PistonMoveReaction getPistonMoveReaction();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\Block.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */