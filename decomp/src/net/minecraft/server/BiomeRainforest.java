/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeRainforest
/*    */   extends BiomeBase
/*    */ {
/*    */   public WorldGenerator a(Random paramRandom) {
/* 15 */     if (paramRandom.nextInt(3) == 0) {
/* 16 */       return new WorldGenBigTree();
/*    */     }
/* 18 */     return new WorldGenTrees();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BiomeRainforest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */