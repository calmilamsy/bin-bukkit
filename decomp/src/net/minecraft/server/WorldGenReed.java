/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 11 */     for (byte b = 0; b < 20; b++) {
/* 12 */       int i = paramInt1 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 13 */       int j = paramInt2;
/* 14 */       int k = paramInt3 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 15 */       if (paramWorld.isEmpty(i, j, k) && (
/* 16 */         paramWorld.getMaterial(i - true, j - true, k) == Material.WATER || paramWorld.getMaterial(i + true, j - true, k) == Material.WATER || paramWorld.getMaterial(i, j - true, k - true) == Material.WATER || paramWorld.getMaterial(i, j - true, k + true) == Material.WATER)) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 21 */         int m = 2 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 22 */         for (int n = 0; n < m; n++) {
/* 23 */           if (Block.SUGAR_CANE_BLOCK.f(paramWorld, i, j + n, k)) {
/* 24 */             paramWorld.setRawTypeId(i, j + n, k, Block.SUGAR_CANE_BLOCK.id);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenReed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */