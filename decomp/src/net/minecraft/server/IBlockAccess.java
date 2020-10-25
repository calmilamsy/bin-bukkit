package net.minecraft.server;

public interface IBlockAccess {
  int getTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);
  
  int getData(int paramInt1, int paramInt2, int paramInt3);
  
  Material getMaterial(int paramInt1, int paramInt2, int paramInt3);
  
  boolean e(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\IBlockAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */