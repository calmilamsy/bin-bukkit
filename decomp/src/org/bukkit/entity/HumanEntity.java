package org.bukkit.entity;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permissible;

public interface HumanEntity extends LivingEntity, AnimalTamer, Permissible {
  String getName();
  
  PlayerInventory getInventory();
  
  ItemStack getItemInHand();
  
  void setItemInHand(ItemStack paramItemStack);
  
  boolean isSleeping();
  
  int getSleepTicks();
  
  GameMode getGameMode();
  
  void setGameMode(GameMode paramGameMode);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\entity\HumanEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */