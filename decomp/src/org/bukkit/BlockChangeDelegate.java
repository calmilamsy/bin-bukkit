package org.bukkit;

public interface BlockChangeDelegate {
  boolean setRawTypeId(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  boolean setRawTypeIdAndData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  int getTypeId(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\BlockChangeDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */