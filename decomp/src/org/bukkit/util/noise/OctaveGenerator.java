/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ public abstract class OctaveGenerator
/*     */ {
/*     */   protected final NoiseGenerator[] octaves;
/*     */   protected double xScale;
/*     */   
/*     */   protected OctaveGenerator(NoiseGenerator[] octaves) {
/*   9 */     this.xScale = 1.0D;
/*  10 */     this.yScale = 1.0D;
/*  11 */     this.zScale = 1.0D;
/*     */ 
/*     */     
/*  14 */     this.octaves = octaves;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double yScale;
/*     */   
/*     */   protected double zScale;
/*     */ 
/*     */   
/*     */   public void setScale(double scale) {
/*  25 */     setXScale(scale);
/*  26 */     setYScale(scale);
/*  27 */     setZScale(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public double getXScale() { return this.xScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public void setXScale(double scale) { this.xScale = scale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public double getYScale() { return this.yScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public void setYScale(double scale) { this.yScale = scale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public double getZScale() { return this.zScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void setZScale(double scale) { this.zScale = scale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public NoiseGenerator[] getOctaves() { return (NoiseGenerator[])this.octaves.clone(); }
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
/* 102 */   public double noise(double x, double frequency, double amplitude) { return noise(x, 0.0D, 0.0D, frequency, amplitude); }
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
/* 115 */   public double noise(double x, double frequency, double amplitude, boolean normalized) { return noise(x, 0.0D, 0.0D, frequency, amplitude, normalized); }
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
/* 128 */   public double noise(double x, double y, double frequency, double amplitude) { return noise(x, y, 0.0D, frequency, amplitude); }
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
/* 142 */   public double noise(double x, double y, double frequency, double amplitude, boolean normalized) { return noise(x, y, 0.0D, frequency, amplitude, normalized); }
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
/* 156 */   public double noise(double x, double y, double z, double frequency, double amplitude) { return noise(x, y, z, frequency, amplitude, false); }
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
/*     */   public double noise(double x, double y, double z, double frequency, double amplitude, boolean normalized) {
/* 171 */     double result = 0.0D;
/* 172 */     double amp = 1.0D;
/* 173 */     double freq = 1.0D;
/* 174 */     double max = 0.0D;
/*     */     
/* 176 */     x *= this.xScale;
/* 177 */     y *= this.yScale;
/* 178 */     z *= this.zScale;
/*     */     
/* 180 */     for (int i = 0; i < this.octaves.length; i++) {
/* 181 */       result += this.octaves[i].noise(x * freq, y * freq, z * freq) * amp;
/* 182 */       max += amp;
/* 183 */       freq *= frequency;
/* 184 */       amp *= amplitude;
/*     */     } 
/*     */     
/* 187 */     if (normalized) {
/* 188 */       result /= max;
/*     */     }
/*     */     
/* 191 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\noise\OctaveGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */