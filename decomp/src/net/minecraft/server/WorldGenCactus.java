/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 10 */     for (byte b = 0; b < 10; b++) {
/* 11 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 12 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 13 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 14 */       if (paramWorld.isEmpty(i, j, k)) {
/* 15 */         int m = 1 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 16 */         for (int n = 0; n < m; n++) {
/* 17 */           if (Block.CACTUS.f(paramWorld, i, j + n, k)) {
/* 18 */             paramWorld.setRawTypeId(i, j + n, k, Block.CACTUS.id);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenCactus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */