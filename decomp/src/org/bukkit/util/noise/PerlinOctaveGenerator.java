/*    */ package org.bukkit.util.noise;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerlinOctaveGenerator
/*    */   extends OctaveGenerator
/*    */ {
/* 18 */   public PerlinOctaveGenerator(World world, int octaves) { this(new Random(world.getSeed()), octaves); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public PerlinOctaveGenerator(long seed, int octaves) { this(new Random(seed), octaves); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public PerlinOctaveGenerator(Random rand, int octaves) { super(createOctaves(rand, octaves)); }
/*    */ 
/*    */   
/*    */   private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
/* 42 */     NoiseGenerator[] result = new NoiseGenerator[octaves];
/*    */     
/* 44 */     for (int i = 0; i < octaves; i++) {
/* 45 */       result[i] = new PerlinNoiseGenerator(rand);
/*    */     }
/*    */     
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\PerlinOctaveGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */