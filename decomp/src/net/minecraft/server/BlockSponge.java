/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSponge
/*    */   extends Block
/*    */ {
/*    */   protected BlockSponge(int paramInt) {
/* 12 */     super(paramInt, Material.SPONGE);
/* 13 */     this.textureId = 48;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 18 */     int i = 2;
/* 19 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 20 */       for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
/* 21 */         for (int m = paramInt3 - i; m <= paramInt3 + i; m++) {
/*    */           
/* 23 */           if (paramWorld.getMaterial(j, k, m) == Material.WATER);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 32 */     int i = 2;
/* 33 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 34 */       for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
/* 35 */         for (int m = paramInt3 - i; m <= paramInt3 + i; m++)
/*    */         {
/* 37 */           paramWorld.applyPhysics(j, k, m, paramWorld.getTypeId(j, k, m));
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BlockSponge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */