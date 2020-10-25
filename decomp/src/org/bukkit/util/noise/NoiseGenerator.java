/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NoiseGenerator
/*     */ {
/*   8 */   protected final int[] perm = new int[512];
/*     */ 
/*     */   
/*     */   protected double offsetX;
/*     */ 
/*     */   
/*     */   protected double offsetY;
/*     */ 
/*     */   
/*     */   protected double offsetZ;
/*     */ 
/*     */   
/*  20 */   public static int floor(double x) { return (x >= 0.0D) ? (int)x : ((int)x - 1); }
/*     */ 
/*     */ 
/*     */   
/*  24 */   protected static double fade(double x) { return x * x * x * (x * (x * 6.0D - 15.0D) + 10.0D); }
/*     */ 
/*     */ 
/*     */   
/*  28 */   protected static double lerp(double x, double y, double z) { return y + x * (z - y); }
/*     */ 
/*     */   
/*     */   protected static double grad(int hash, double x, double y, double z) {
/*  32 */     hash &= 0xF;
/*  33 */     double u = (hash < 8) ? x : y;
/*  34 */     double v = (hash < 4) ? y : ((hash == 12 || hash == 14) ? x : z);
/*  35 */     return (((hash & true) == 0) ? u : -u) + (((hash & 0x2) == 0) ? v : -v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public double noise(double x) { return noise(x, 0.0D, 0.0D); }
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
/*  56 */   public double noise(double x, double y) { return noise(x, y, 0.0D); }
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
/*     */   public abstract double noise(double paramDouble1, double paramDouble2, double paramDouble3);
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
/*  79 */   public double noise(double x, int octaves, double frequency, double amplitude) { return noise(x, 0.0D, 0.0D, octaves, frequency, amplitude); }
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
/*  93 */   public double noise(double x, int octaves, double frequency, double amplitude, boolean normalized) { return noise(x, 0.0D, 0.0D, octaves, frequency, amplitude, normalized); }
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
/* 107 */   public double noise(double x, double y, int octaves, double frequency, double amplitude) { return noise(x, y, 0.0D, octaves, frequency, amplitude); }
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
/* 122 */   public double noise(double x, double y, int octaves, double frequency, double amplitude, boolean normalized) { return noise(x, y, 0.0D, octaves, frequency, amplitude, normalized); }
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
/* 137 */   public double noise(double x, double y, double z, int octaves, double frequency, double amplitude) { return noise(x, y, z, octaves, frequency, amplitude, false); }
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
/*     */   public double noise(double x, double y, double z, int octaves, double frequency, double amplitude, boolean normalized) {
/* 153 */     double result = 0.0D;
/* 154 */     double amp = 1.0D;
/* 155 */     double freq = 1.0D;
/* 156 */     double max = 0.0D;
/*     */     
/* 158 */     for (int i = 0; i < octaves; i++) {
/* 159 */       result += noise(x * freq, y * freq, z * freq) * amp;
/* 160 */       max += amp;
/* 161 */       freq *= frequency;
/* 162 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 165 */     if (normalized) {
/* 166 */       result /= max;
/*     */     }
/*     */     
/* 169 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\NoiseGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */