package org.bukkit.entity;

import java.util.HashSet;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;

public interface LivingEntity extends Entity {
  int getHealth();
  
  void setHealth(int paramInt);
  
  double getEyeHeight();
  
  double getEyeHeight(boolean paramBoolean);
  
  Location getEyeLocation();
  
  List<Block> getLineOfSight(HashSet<Byte> paramHashSet, int paramInt);
  
  Block getTargetBlock(HashSet<Byte> paramHashSet, int paramInt);
  
  List<Block> getLastTwoTargetBlocks(HashSet<Byte> paramHashSet, int paramInt);
  
  Egg throwEgg();
  
  Snowball throwSnowball();
  
  Arrow shootArrow();
  
  boolean isInsideVehicle();
  
  boolean leaveVehicle();
  
  Vehicle getVehicle();
  
  int getRemainingAir();
  
  void setRemainingAir(int paramInt);
  
  int getMaximumAir();
  
  void setMaximumAir(int paramInt);
  
  void damage(int paramInt);
  
  void damage(int paramInt, Entity paramEntity);
  
  int getMaximumNoDamageTicks();
  
  void setMaximumNoDamageTicks(int paramInt);
  
  int getLastDamage();
  
  void setLastDamage(int paramInt);
  
  int getNoDamageTicks();
  
  void setNoDamageTicks(int paramInt);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\LivingEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */