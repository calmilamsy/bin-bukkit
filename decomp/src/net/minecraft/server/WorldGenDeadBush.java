/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenDeadBush
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   
/* 13 */   public WorldGenDeadBush(int paramInt) { this.a = paramInt; }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 17 */     int i = 0;
/* 18 */     while (((i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3)) == 0 || i == Block.LEAVES.id) && paramInt2 > 0) {
/* 19 */       paramInt2--;
/*    */     }
/* 21 */     for (byte b = 0; b < 4; b++) {
/* 22 */       int j = paramInt1 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 23 */       int k = paramInt2 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 24 */       int m = paramInt3 + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 25 */       if (paramWorld.isEmpty(j, k, m) && (
/* 26 */         (BlockFlower)Block.byId[this.a]).f(paramWorld, j, k, m)) {
/* 27 */         paramWorld.setRawTypeId(j, k, m, this.a);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenDeadBush.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */