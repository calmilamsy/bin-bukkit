/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGeneratorPerlin
/*     */   extends NoiseGenerator {
/*     */   private int[] d;
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*     */   
/*  12 */   public NoiseGeneratorPerlin() { this(new Random()); }
/*     */   
/*     */   public NoiseGeneratorPerlin(Random paramRandom) {
/*     */     this.d = new int[512];
/*  16 */     this.a = paramRandom.nextDouble() * 256.0D;
/*  17 */     this.b = paramRandom.nextDouble() * 256.0D;
/*  18 */     this.c = paramRandom.nextDouble() * 256.0D; int i;
/*  19 */     for (i = 0; i < 256; i++) {
/*  20 */       this.d[i] = i;
/*     */     }
/*     */     
/*  23 */     for (i = 0; i < 256; i++) {
/*  24 */       int j = paramRandom.nextInt('Ā' - i) + i;
/*  25 */       int k = this.d[i];
/*  26 */       this.d[i] = this.d[j];
/*  27 */       this.d[j] = k;
/*     */       
/*  29 */       this.d[i + 256] = this.d[i];
/*     */     } 
/*     */   }
/*     */   
/*     */   public double a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  34 */     double d1 = paramDouble1 + this.a;
/*  35 */     double d2 = paramDouble2 + this.b;
/*  36 */     double d3 = paramDouble3 + this.c;
/*     */     
/*  38 */     int i = (int)d1;
/*  39 */     int j = (int)d2;
/*  40 */     int k = (int)d3;
/*     */     
/*  42 */     if (d1 < i) i--; 
/*  43 */     if (d2 < j) j--; 
/*  44 */     if (d3 < k) k--;
/*     */     
/*  46 */     int m = i & 0xFF;
/*  47 */     int n = j & 0xFF;
/*  48 */     int i1 = k & 0xFF;
/*     */     
/*  50 */     d1 -= i;
/*  51 */     d2 -= j;
/*  52 */     d3 -= k;
/*     */     
/*  54 */     double d4 = d1 * d1 * d1 * (d1 * (d1 * 6.0D - 15.0D) + 10.0D);
/*  55 */     double d5 = d2 * d2 * d2 * (d2 * (d2 * 6.0D - 15.0D) + 10.0D);
/*  56 */     double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0D - 15.0D) + 10.0D);
/*     */     
/*  58 */     int i2 = this.d[m] + n, i3 = this.d[i2] + i1, i4 = this.d[i2 + 1] + i1;
/*  59 */     int i5 = this.d[m + 1] + n, i6 = this.d[i5] + i1, i7 = this.d[i5 + 1] + i1;
/*     */     
/*  61 */     return b(d6, b(d5, b(d4, a(this.d[i3], d1, d2, d3), a(this.d[i6], d1 - 1.0D, d2, d3)), b(d4, a(this.d[i4], d1, d2 - 1.0D, d3), a(this.d[i7], d1 - 1.0D, d2 - 1.0D, d3))), b(d5, b(d4, a(this.d[i3 + 1], d1, d2, d3 - 1.0D), a(this.d[i6 + 1], d1 - 1.0D, d2, d3 - 1.0D)), b(d4, a(this.d[i4 + 1], d1, d2 - 1.0D, d3 - 1.0D), a(this.d[i7 + 1], d1 - 1.0D, d2 - 1.0D, d3 - 1.0D))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public final double b(double paramDouble1, double paramDouble2, double paramDouble3) { return paramDouble2 + paramDouble1 * (paramDouble3 - paramDouble2); }
/*     */ 
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2) {
/*  75 */     int i = paramInt & 0xF;
/*     */     
/*  77 */     double d1 = (1 - ((i & 0x8) >> 3)) * paramDouble1;
/*  78 */     double d2 = (i < 4) ? 0.0D : ((i == 12 || i == 14) ? paramDouble1 : paramDouble2);
/*     */     
/*  80 */     return (((i & true) == 0) ? d1 : -d1) + (((i & 0x2) == 0) ? d2 : -d2);
/*     */   }
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  84 */     int i = paramInt & 0xF;
/*     */     
/*  86 */     double d1 = (i < 8) ? paramDouble1 : paramDouble2;
/*  87 */     double d2 = (i < 4) ? paramDouble2 : ((i == 12 || i == 14) ? paramDouble1 : paramDouble3);
/*     */     
/*  89 */     return (((i & true) == 0) ? d1 : -d1) + (((i & 0x2) == 0) ? d2 : -d2);
/*     */   }
/*     */ 
/*     */   
/*  93 */   public double a(double paramDouble1, double paramDouble2) { return a(paramDouble1, paramDouble2, 0.0D); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, int paramInt3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7) {
/* 101 */     if (paramInt2 == 1) {
/* 102 */       int i3 = 0, i4 = 0, i5 = 0, i6 = 0;
/* 103 */       double d6 = 0.0D, d7 = 0.0D;
/* 104 */       byte b3 = 0;
/* 105 */       double d8 = 1.0D / paramDouble7;
/* 106 */       for (byte b4 = 0; b4 < paramInt1; b4++) {
/* 107 */         double d9 = (paramDouble1 + b4) * paramDouble4 + this.a;
/* 108 */         int i7 = (int)d9;
/* 109 */         if (d9 < i7) i7--; 
/* 110 */         int i8 = i7 & 0xFF;
/* 111 */         d9 -= i7;
/* 112 */         double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 114 */         for (byte b5 = 0; b5 < paramInt3; b5++) {
/* 115 */           double d11 = (paramDouble3 + b5) * paramDouble6 + this.c;
/* 116 */           int i9 = (int)d11;
/* 117 */           if (d11 < i9) i9--; 
/* 118 */           int i10 = i9 & 0xFF;
/* 119 */           d11 -= i9;
/* 120 */           double d12 = d11 * d11 * d11 * (d11 * (d11 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 122 */           i3 = this.d[i8] + 0;
/* 123 */           i4 = this.d[i3] + i10;
/* 124 */           i5 = this.d[i8 + 1] + 0;
/* 125 */           i6 = this.d[i5] + i10;
/* 126 */           d6 = b(d10, a(this.d[i4], d9, d11), a(this.d[i6], d9 - 1.0D, 0.0D, d11));
/* 127 */           d7 = b(d10, a(this.d[i4 + 1], d9, 0.0D, d11 - 1.0D), a(this.d[i6 + 1], d9 - 1.0D, 0.0D, d11 - 1.0D));
/*     */           
/* 129 */           double d13 = b(d12, d6, d7);
/*     */           
/* 131 */           paramArrayOfDouble[b3++] = paramArrayOfDouble[b3++] + d13 * d8;
/*     */         } 
/*     */       } 
/*     */       return;
/*     */     } 
/* 136 */     byte b1 = 0;
/* 137 */     double d3 = 1.0D / paramDouble7;
/* 138 */     int i = -1;
/* 139 */     int k = 0, m = 0, n = 0, i1 = 0, j = 0, i2 = 0;
/* 140 */     double d4 = 0.0D, d1 = 0.0D, d5 = 0.0D, d2 = 0.0D;
/*     */     
/* 142 */     for (byte b2 = 0; b2 < paramInt1; b2++) {
/* 143 */       double d6 = (paramDouble1 + b2) * paramDouble4 + this.a;
/* 144 */       int i3 = (int)d6;
/* 145 */       if (d6 < i3) i3--; 
/* 146 */       int i4 = i3 & 0xFF;
/* 147 */       d6 -= i3;
/* 148 */       double d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
/*     */ 
/*     */       
/* 151 */       for (byte b3 = 0; b3 < paramInt3; b3++) {
/* 152 */         double d8 = (paramDouble3 + b3) * paramDouble6 + this.c;
/* 153 */         int i5 = (int)d8;
/* 154 */         if (d8 < i5) i5--; 
/* 155 */         int i6 = i5 & 0xFF;
/* 156 */         d8 -= i5;
/* 157 */         double d9 = d8 * d8 * d8 * (d8 * (d8 * 6.0D - 15.0D) + 10.0D);
/*     */ 
/*     */         
/* 160 */         for (byte b4 = 0; b4 < paramInt2; b4++) {
/* 161 */           double d10 = (paramDouble2 + b4) * paramDouble5 + this.b;
/* 162 */           int i7 = (int)d10;
/* 163 */           if (d10 < i7) i7--; 
/* 164 */           int i8 = i7 & 0xFF;
/* 165 */           d10 -= i7;
/* 166 */           double d11 = d10 * d10 * d10 * (d10 * (d10 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 168 */           if (!b4 || i8 != i) {
/* 169 */             i = i8;
/* 170 */             k = this.d[i4] + i8;
/* 171 */             m = this.d[k] + i6;
/* 172 */             n = this.d[k + 1] + i6;
/* 173 */             i1 = this.d[i4 + 1] + i8;
/* 174 */             j = this.d[i1] + i6;
/* 175 */             i2 = this.d[i1 + 1] + i6;
/* 176 */             d4 = b(d7, a(this.d[m], d6, d10, d8), a(this.d[j], d6 - 1.0D, d10, d8));
/* 177 */             d1 = b(d7, a(this.d[n], d6, d10 - 1.0D, d8), a(this.d[i2], d6 - 1.0D, d10 - 1.0D, d8));
/* 178 */             d5 = b(d7, a(this.d[m + 1], d6, d10, d8 - 1.0D), a(this.d[j + 1], d6 - 1.0D, d10, d8 - 1.0D));
/* 179 */             d2 = b(d7, a(this.d[n + 1], d6, d10 - 1.0D, d8 - 1.0D), a(this.d[i2 + 1], d6 - 1.0D, d10 - 1.0D, d8 - 1.0D));
/*     */           } 
/*     */ 
/*     */           
/* 183 */           double d12 = b(d11, d4, d1);
/* 184 */           double d13 = b(d11, d5, d2);
/* 185 */           double d14 = b(d9, d12, d13);
/*     */           
/* 187 */           paramArrayOfDouble[b1++] = paramArrayOfDouble[b1++] + d14 * d3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NoiseGeneratorPerlin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */