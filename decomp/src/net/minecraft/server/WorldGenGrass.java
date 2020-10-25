/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenGrass
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenGrass(int paramInt1, int paramInt2) {
/* 14 */     this.a = paramInt1;
/* 15 */     this.b = paramInt2;
/*    */   }
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     int i = 0;
/* 20 */     while (((i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3)) == 0 || i == Block.LEAVES.id) && paramInt2 > 0) {
/* 21 */       paramInt2--;
/*    */     }
/* 23 */     for (byte b1 = 0; b1 < ''; b1++) {
/* 24 */       int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 25 */       int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 26 */       int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 27 */       if (paramWorld.isEmpty(j, k, m) && (
/* 28 */         (BlockFlower)Block.byId[this.a]).f(paramWorld, j, k, m)) {
/* 29 */         paramWorld.setRawTypeIdAndData(j, k, m, this.a, this.b);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenGrass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */