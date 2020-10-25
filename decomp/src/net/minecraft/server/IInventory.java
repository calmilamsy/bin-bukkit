package net.minecraft.server;

public interface IInventory {
  int getSize();
  
  ItemStack getItem(int paramInt);
  
  ItemStack splitStack(int paramInt1, int paramInt2);
  
  void setItem(int paramInt, ItemStack paramItemStack);
  
  String getName();
  
  int getMaxStackSize();
  
  void update();
  
  boolean a_(EntityHuman paramEntityHuman);
  
  ItemStack[] getContents();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\IInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */