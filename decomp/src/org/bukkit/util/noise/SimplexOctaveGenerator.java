/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexOctaveGenerator
/*     */   extends OctaveGenerator
/*     */ {
/*  11 */   private double wScale = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   public SimplexOctaveGenerator(World world, int octaves) { this(new Random(world.getSeed()), octaves); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   public SimplexOctaveGenerator(long seed, int octaves) { this(new Random(seed), octaves); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public SimplexOctaveGenerator(Random rand, int octaves) { super(createOctaves(rand, octaves)); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(double scale) {
/*  45 */     super.setScale(scale);
/*  46 */     setWScale(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public double getWScale() { return this.wScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public void setWScale(double scale) { this.wScale = scale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public double noise(double x, double y, double z, double w, double frequency, double amplitude) { return noise(x, y, z, w, frequency, amplitude, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double noise(double x, double y, double z, double w, double frequency, double amplitude, boolean normalized) {
/*  95 */     double result = 0.0D;
/*  96 */     double amp = 1.0D;
/*  97 */     double freq = 1.0D;
/*  98 */     double max = 0.0D;
/*     */     
/* 100 */     x *= this.xScale;
/* 101 */     y *= this.yScale;
/* 102 */     z *= this.zScale;
/* 103 */     w *= this.wScale;
/*     */     
/* 105 */     for (int i = 0; i < this.octaves.length; i++) {
/* 106 */       result += ((SimplexNoiseGenerator)this.octaves[i]).noise(x * freq, y * freq, z * freq, w * freq) * amp;
/* 107 */       max += amp;
/* 108 */       freq *= frequency;
/* 109 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 112 */     if (normalized) {
/* 113 */       result /= max;
/*     */     }
/*     */     
/* 116 */     return result;
/*     */   }
/*     */   
/*     */   private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
/* 120 */     NoiseGenerator[] result = new NoiseGenerator[octaves];
/*     */     
/* 122 */     for (int i = 0; i < octaves; i++) {
/* 123 */       result[i] = new SimplexNoiseGenerator(rand);
/*     */     }
/*     */     
/* 126 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\SimplexOctaveGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */