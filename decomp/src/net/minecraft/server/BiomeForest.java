/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeForest
/*    */   extends BiomeBase
/*    */ {
/* 13 */   public BiomeForest() { this.t.add(new BiomeMeta(EntityWolf.class, 2)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenerator a(Random paramRandom) {
/* 18 */     if (paramRandom.nextInt(5) == 0) {
/* 19 */       return new WorldGenForest();
/*    */     }
/* 21 */     if (paramRandom.nextInt(3) == 0) {
/* 22 */       return new WorldGenBigTree();
/*    */     }
/* 24 */     return new WorldGenTrees();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BiomeForest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */