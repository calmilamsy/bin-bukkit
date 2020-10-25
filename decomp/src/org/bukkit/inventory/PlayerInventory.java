package org.bukkit.inventory;

public interface PlayerInventory extends Inventory {
  ItemStack[] getArmorContents();
  
  ItemStack getHelmet();
  
  ItemStack getChestplate();
  
  ItemStack getLeggings();
  
  ItemStack getBoots();
  
  void setArmorContents(ItemStack[] paramArrayOfItemStack);
  
  void setHelmet(ItemStack paramItemStack);
  
  void setChestplate(ItemStack paramItemStack);
  
  void setLeggings(ItemStack paramItemStack);
  
  void setBoots(ItemStack paramItemStack);
  
  ItemStack getItemInHand();
  
  void setItemInHand(ItemStack paramItemStack);
  
  int getHeldItemSlot();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\PlayerInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */