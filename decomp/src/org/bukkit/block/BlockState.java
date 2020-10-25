package org.bukkit.block;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;

public interface BlockState {
  Block getBlock();
  
  MaterialData getData();
  
  Material getType();
  
  int getTypeId();
  
  byte getLightLevel();
  
  World getWorld();
  
  int getX();
  
  int getY();
  
  int getZ();
  
  Chunk getChunk();
  
  void setData(MaterialData paramMaterialData);
  
  void setType(Material paramMaterial);
  
  boolean setTypeId(int paramInt);
  
  boolean update();
  
  boolean update(boolean paramBoolean);
  
  byte getRawData();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\BlockState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */