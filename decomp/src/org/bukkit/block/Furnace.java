package org.bukkit.block;

public interface Furnace extends BlockState, ContainerBlock {
  short getBurnTime();
  
  void setBurnTime(short paramShort);
  
  short getCookTime();
  
  void setCookTime(short paramShort);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\Furnace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */