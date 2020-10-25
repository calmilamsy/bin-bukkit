/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockContainer
/*    */   extends Block
/*    */ {
/*    */   protected BlockContainer(int paramInt, Material paramMaterial) {
/*  9 */     super(paramInt, paramMaterial);
/* 10 */     isTileEntity[paramInt] = true;
/*    */   }
/*    */   
/*    */   protected BlockContainer(int paramInt1, int paramInt2, Material paramMaterial) {
/* 14 */     super(paramInt1, paramInt2, paramMaterial);
/* 15 */     isTileEntity[paramInt1] = true;
/*    */   }
/*    */   
/*    */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     super.c(paramWorld, paramInt1, paramInt2, paramInt3);
/* 20 */     paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, a_());
/*    */   }
/*    */   
/*    */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 24 */     super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
/* 25 */     paramWorld.o(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   protected abstract TileEntity a_();
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */