package org.bukkit.inventory;

import java.util.HashMap;
import org.bukkit.Material;

public interface Inventory {
  int getSize();
  
  String getName();
  
  ItemStack getItem(int paramInt);
  
  void setItem(int paramInt, ItemStack paramItemStack);
  
  HashMap<Integer, ItemStack> addItem(ItemStack... paramVarArgs);
  
  HashMap<Integer, ItemStack> removeItem(ItemStack... paramVarArgs);
  
  ItemStack[] getContents();
  
  void setContents(ItemStack[] paramArrayOfItemStack);
  
  boolean contains(int paramInt);
  
  boolean contains(Material paramMaterial);
  
  boolean contains(ItemStack paramItemStack);
  
  boolean contains(int paramInt1, int paramInt2);
  
  boolean contains(Material paramMaterial, int paramInt);
  
  boolean contains(ItemStack paramItemStack, int paramInt);
  
  HashMap<Integer, ? extends ItemStack> all(int paramInt);
  
  HashMap<Integer, ? extends ItemStack> all(Material paramMaterial);
  
  HashMap<Integer, ? extends ItemStack> all(ItemStack paramItemStack);
  
  int first(int paramInt);
  
  int first(Material paramMaterial);
  
  int first(ItemStack paramItemStack);
  
  int firstEmpty();
  
  void remove(int paramInt);
  
  void remove(Material paramMaterial);
  
  void remove(ItemStack paramItemStack);
  
  void clear(int paramInt);
  
  void clear();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\inventory\Inventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */