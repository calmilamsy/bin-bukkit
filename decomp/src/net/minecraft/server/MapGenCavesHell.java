/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapGenCavesHell
/*     */   extends MapGenBase
/*     */ {
/*  13 */   protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3) { a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D); }
/*     */ 
/*     */   
/*     */   protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  17 */     double d1 = (paramInt1 * 16 + 8);
/*  18 */     double d2 = (paramInt2 * 16 + 8);
/*     */     
/*  20 */     float f1 = 0.0F;
/*  21 */     float f2 = 0.0F;
/*     */ 
/*     */     
/*  24 */     Random random = new Random(this.b.nextLong());
/*     */     
/*  26 */     if (paramInt4 <= 0) {
/*  27 */       int j = this.a * 16 - 16;
/*  28 */       paramInt4 = j - random.nextInt(j / 4);
/*     */     } 
/*  30 */     boolean bool1 = false;
/*     */     
/*  32 */     if (paramInt3 == -1) {
/*  33 */       paramInt3 = paramInt4 / 2;
/*  34 */       bool1 = true;
/*     */     } 
/*     */ 
/*     */     
/*  38 */     int i = random.nextInt(paramInt4 / 2) + paramInt4 / 4;
/*  39 */     boolean bool2 = (random.nextInt(6) == 0) ? 1 : 0;
/*     */     
/*  41 */     for (; paramInt3 < paramInt4; paramInt3++) {
/*  42 */       double d3 = 1.5D + (MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F);
/*  43 */       double d4 = d3 * paramDouble4;
/*     */       
/*  45 */       float f3 = MathHelper.cos(paramFloat3);
/*  46 */       float f4 = MathHelper.sin(paramFloat3);
/*  47 */       paramDouble1 += (MathHelper.cos(paramFloat2) * f3);
/*  48 */       paramDouble2 += f4;
/*  49 */       paramDouble3 += (MathHelper.sin(paramFloat2) * f3);
/*     */       
/*  51 */       if (bool2) {
/*  52 */         paramFloat3 *= 0.92F;
/*     */       } else {
/*  54 */         paramFloat3 *= 0.7F;
/*     */       } 
/*  56 */       paramFloat3 += f2 * 0.1F;
/*  57 */       paramFloat2 += f1 * 0.1F;
/*     */       
/*  59 */       f2 *= 0.9F;
/*  60 */       f1 *= 0.75F;
/*  61 */       f2 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  62 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */ 
/*     */       
/*  65 */       if (!bool1 && paramInt3 == i && paramFloat1 > 1.0F) {
/*  66 */         a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  67 */         a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*     */         return;
/*     */       } 
/*  70 */       if (bool1 || random.nextInt(4) != 0) {
/*     */ 
/*     */         
/*  73 */         double d5 = paramDouble1 - d1;
/*  74 */         double d6 = paramDouble3 - d2;
/*  75 */         double d7 = (paramInt4 - paramInt3);
/*  76 */         double d8 = (paramFloat1 + 2.0F + 16.0F);
/*  77 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  82 */         if (paramDouble1 >= d1 - 16.0D - d3 * 2.0D && paramDouble3 >= d2 - 16.0D - d3 * 2.0D && paramDouble1 <= d1 + 16.0D + d3 * 2.0D && paramDouble3 <= d2 + 16.0D + d3 * 2.0D) {
/*     */           
/*  84 */           int j = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  85 */           int k = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  87 */           int m = MathHelper.floor(paramDouble2 - d4) - 1;
/*  88 */           int n = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  90 */           int i1 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  91 */           int i2 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  93 */           if (j < 0) j = 0; 
/*  94 */           if (k > 16) k = 16;
/*     */           
/*  96 */           if (m < 1) m = 1; 
/*  97 */           if (n > 120) n = 120;
/*     */           
/*  99 */           if (i1 < 0) i1 = 0; 
/* 100 */           if (i2 > 16) i2 = 16;
/*     */           
/* 102 */           boolean bool = false; int i3;
/* 103 */           for (i3 = j; !bool && i3 < k; i3++) {
/* 104 */             for (int i4 = i1; !bool && i4 < i2; i4++) {
/* 105 */               for (int i5 = n + 1; !bool && i5 >= m - 1; i5--) {
/* 106 */                 int i6 = (i3 * 16 + i4) * 128 + i5;
/* 107 */                 if (i5 >= 0 && i5 < 128) {
/* 108 */                   if (paramArrayOfByte[i6] == Block.LAVA.id || paramArrayOfByte[i6] == Block.STATIONARY_LAVA.id) {
/* 109 */                     bool = true;
/*     */                   }
/* 111 */                   if (i5 != m - 1 && i3 != j && i3 != k - 1 && i4 != i1 && i4 != i2 - 1)
/* 112 */                     i5 = m; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 117 */           if (!bool) {
/*     */             
/* 119 */             for (i3 = j; i3 < k; i3++) {
/* 120 */               double d = ((i3 + paramInt1 * 16) + 0.5D - paramDouble1) / d3;
/* 121 */               for (int i4 = i1; i4 < i2; i4++) {
/* 122 */                 double d9 = ((i4 + paramInt2 * 16) + 0.5D - paramDouble3) / d3;
/* 123 */                 int i5 = (i3 * 16 + i4) * 128 + n;
/* 124 */                 for (int i6 = n - 1; i6 >= m; i6--) {
/* 125 */                   double d10 = (i6 + 0.5D - paramDouble2) / d4;
/* 126 */                   if (d10 > -0.7D && d * d + d10 * d10 + d9 * d9 < 1.0D) {
/* 127 */                     byte b = paramArrayOfByte[i5];
/* 128 */                     if (b == Block.NETHERRACK.id || b == Block.DIRT.id || b == Block.GRASS.id) {
/* 129 */                       paramArrayOfByte[i5] = 0;
/*     */                     }
/*     */                   } 
/* 132 */                   i5--;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 136 */             if (bool1) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte) {
/* 152 */     int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(10) + 1) + 1);
/* 153 */     if (this.b.nextInt(5) != 0) i = 0;
/*     */     
/* 155 */     for (byte b = 0; b < i; b++) {
/* 156 */       double d1 = (paramInt1 * 16 + this.b.nextInt(16));
/* 157 */       double d2 = this.b.nextInt(128);
/* 158 */       double d3 = (paramInt2 * 16 + this.b.nextInt(16));
/*     */       
/* 160 */       int j = 1;
/* 161 */       if (this.b.nextInt(4) == 0) {
/* 162 */         a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3);
/* 163 */         j += this.b.nextInt(4);
/*     */       } 
/*     */       
/* 166 */       for (byte b1 = 0; b1 < j; b1++) {
/*     */         
/* 168 */         float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 169 */         float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 170 */         float f3 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
/*     */         
/* 172 */         a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3, f3 * 2.0F, f1, f2, 0, 0, 0.5D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MapGenCavesHell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */