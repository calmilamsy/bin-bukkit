package org.bukkit.block;

public interface Sign extends BlockState {
  String[] getLines();
  
  String getLine(int paramInt) throws IndexOutOfBoundsException;
  
  void setLine(int paramInt, String paramString) throws IndexOutOfBoundsException;
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\block\Sign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */