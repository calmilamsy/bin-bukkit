/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWorkbench
/*    */   extends Block
/*    */ {
/*    */   protected BlockWorkbench(int paramInt) {
/*  9 */     super(paramInt, Material.WOOD);
/* 10 */     this.textureId = 59;
/*    */   }
/*    */   
/*    */   public int a(int paramInt) {
/* 14 */     if (paramInt == 1) return this.textureId - 16; 
/* 15 */     if (paramInt == 0) return Block.WOOD.a(0); 
/* 16 */     if (paramInt == 2 || paramInt == 4) return this.textureId + 1; 
/* 17 */     return this.textureId;
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 21 */     if (paramWorld.isStatic) {
/* 22 */       return true;
/*    */     }
/* 24 */     paramEntityHuman.b(paramInt1, paramInt2, paramInt3);
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockWorkbench.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */