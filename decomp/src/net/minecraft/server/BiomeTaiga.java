/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeTaiga
/*    */   extends BiomeBase
/*    */ {
/* 13 */   public BiomeTaiga() { this.t.add(new BiomeMeta(EntityWolf.class, 2)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenerator a(Random paramRandom) {
/* 18 */     if (paramRandom.nextInt(3) == 0) {
/* 19 */       return new WorldGenTaiga1();
/*    */     }
/* 21 */     return new WorldGenTaiga2();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\BiomeTaiga.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */