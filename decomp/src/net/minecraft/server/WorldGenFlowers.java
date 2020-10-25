/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   
/* 12 */   public WorldGenFlowers(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 16 */     for (byte b = 0; b < 64; b++) {
/* 17 */       int i = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 18 */       int j = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 19 */       int k = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 20 */       if (paramWorld.isEmpty(i, j, k) && (
/* 21 */         (BlockFlower)Block.byId[this.a]).f(paramWorld, i, j, k)) {
/* 22 */         paramWorld.setRawTypeId(i, j, k, this.a);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenFlowers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */