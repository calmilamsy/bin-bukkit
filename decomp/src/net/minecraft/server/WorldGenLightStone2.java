/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class WorldGenLightStone2
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 10 */     if (!paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) return false; 
/* 11 */     if (paramWorld.getTypeId(paramInt1, paramInt2 + 1, paramInt3) != Block.NETHERRACK.id) return false; 
/* 12 */     paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.GLOWSTONE.id);
/*    */     
/* 14 */     for (byte b = 0; b < 'ל'; b++) {
/* 15 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 16 */       int j = paramInt2 - paramRandom.nextInt(12);
/* 17 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 18 */       if (paramWorld.getTypeId(i, j, k) == 0) {
/*    */         
/* 20 */         byte b1 = 0;
/* 21 */         for (byte b2 = 0; b2 < 6; b2++) {
/* 22 */           int m = 0;
/* 23 */           if (!b2) m = paramWorld.getTypeId(i - 1, j, k); 
/* 24 */           if (b2 == 1) m = paramWorld.getTypeId(i + 1, j, k); 
/* 25 */           if (b2 == 2) m = paramWorld.getTypeId(i, j - 1, k); 
/* 26 */           if (b2 == 3) m = paramWorld.getTypeId(i, j + 1, k); 
/* 27 */           if (b2 == 4) m = paramWorld.getTypeId(i, j, k - 1); 
/* 28 */           if (b2 == 5) m = paramWorld.getTypeId(i, j, k + 1);
/*    */           
/* 30 */           if (m == Block.GLOWSTONE.id) b1++;
/*    */         
/*    */         } 
/* 33 */         if (b1 == 1) paramWorld.setTypeId(i, j, k, Block.GLOWSTONE.id); 
/*    */       } 
/*    */     } 
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenLightStone2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */