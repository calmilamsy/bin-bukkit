package org.bukkit.block;

import org.bukkit.entity.CreatureType;

public interface CreatureSpawner extends BlockState {
  CreatureType getCreatureType();
  
  void setCreatureType(CreatureType paramCreatureType);
  
  String getCreatureTypeId();
  
  void setCreatureTypeId(String paramString);
  
  int getDelay();
  
  void setDelay(int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\CreatureSpawner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */