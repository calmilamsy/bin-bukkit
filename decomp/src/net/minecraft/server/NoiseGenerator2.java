/*     */ package net.minecraft.server;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGenerator2 {
/*     */   private static int[][] d = { 
/*   6 */       { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*     */   
/*     */   private int[] e;
/*     */   
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*     */   
/*  14 */   public NoiseGenerator2() { this(new Random()); }
/*     */   
/*     */   public NoiseGenerator2(Random paramRandom) {
/*     */     this.e = new int[512];
/*  18 */     this.a = paramRandom.nextDouble() * 256.0D;
/*  19 */     this.b = paramRandom.nextDouble() * 256.0D;
/*  20 */     this.c = paramRandom.nextDouble() * 256.0D; int i;
/*  21 */     for (i = 0; i < 256; i++) {
/*  22 */       this.e[i] = i;
/*     */     }
/*     */     
/*  25 */     for (i = 0; i < 256; i++) {
/*  26 */       int j = paramRandom.nextInt('Ā' - i) + i;
/*  27 */       int k = this.e[i];
/*  28 */       this.e[i] = this.e[j];
/*  29 */       this.e[j] = k;
/*     */       
/*  31 */       this.e[i + 256] = this.e[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static int a(double paramDouble) { return (paramDouble > 0.0D) ? (int)paramDouble : ((int)paramDouble - 1); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static double a(int[] paramArrayOfInt, double paramDouble1, double paramDouble2) { return paramArrayOfInt[0] * paramDouble1 + paramArrayOfInt[1] * paramDouble2; }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   private static final double f = 0.5D * (Math.sqrt(3.0D) - 1.0D);
/* 232 */   private static final double g = (3.0D - Math.sqrt(3.0D)) / 6.0D;
/*     */   
/*     */   public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 235 */     byte b1 = 0;
/* 236 */     for (byte b2 = 0; b2 < paramInt1; b2++) {
/* 237 */       double d1 = (paramDouble1 + b2) * paramDouble3 + this.a;
/* 238 */       for (byte b3 = 0; b3 < paramInt2; b3++) {
/* 239 */         double d18, d16, d14; int m, k; double d2 = (paramDouble2 + b3) * paramDouble4 + this.b;
/*     */ 
/*     */         
/* 242 */         double d3 = (d1 + d2) * f;
/* 243 */         int i = a(d1 + d3);
/* 244 */         int j = a(d2 + d3);
/* 245 */         double d4 = (i + j) * g;
/* 246 */         double d5 = i - d4;
/* 247 */         double d6 = j - d4;
/* 248 */         double d7 = d1 - d5;
/* 249 */         double d8 = d2 - d6;
/*     */ 
/*     */ 
/*     */         
/* 253 */         if (d7 > d8) {
/* 254 */           k = 1;
/* 255 */           m = 0;
/*     */         } else {
/*     */           
/* 258 */           k = 0;
/* 259 */           m = 1;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 264 */         double d9 = d7 - k + g;
/* 265 */         double d10 = d8 - m + g;
/* 266 */         double d11 = d7 - 1.0D + 2.0D * g;
/* 267 */         double d12 = d8 - 1.0D + 2.0D * g;
/*     */         
/* 269 */         int n = i & 0xFF;
/* 270 */         int i1 = j & 0xFF;
/* 271 */         int i2 = this.e[n + this.e[i1]] % 12;
/* 272 */         int i3 = this.e[n + k + this.e[i1 + m]] % 12;
/* 273 */         int i4 = this.e[n + 1 + this.e[i1 + 1]] % 12;
/*     */         
/* 275 */         double d13 = 0.5D - d7 * d7 - d8 * d8;
/* 276 */         if (d13 < 0.0D) { d14 = 0.0D; }
/*     */         else
/* 278 */         { d13 *= d13;
/* 279 */           d14 = d13 * d13 * a(d[i2], d7, d8); }
/*     */         
/* 281 */         double d15 = 0.5D - d9 * d9 - d10 * d10;
/* 282 */         if (d15 < 0.0D) { d16 = 0.0D; }
/*     */         else
/* 284 */         { d15 *= d15;
/* 285 */           d16 = d15 * d15 * a(d[i3], d9, d10); }
/*     */         
/* 287 */         double d17 = 0.5D - d11 * d11 - d12 * d12;
/* 288 */         if (d17 < 0.0D) { d18 = 0.0D; }
/*     */         else
/* 290 */         { d17 *= d17;
/* 291 */           d18 = d17 * d17 * a(d[i4], d11, d12); }
/*     */ 
/*     */ 
/*     */         
/* 295 */         paramArrayOfDouble[b1++] = paramArrayOfDouble[b1++] + 70.0D * (d14 + d16 + d18) * paramDouble5;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NoiseGenerator2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */