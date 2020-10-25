/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapGenCaves
/*     */   extends MapGenBase
/*     */ {
/*  12 */   protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3) { a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D); }
/*     */ 
/*     */   
/*     */   protected void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, int paramInt4, double paramDouble4) {
/*  16 */     double d1 = (paramInt1 * 16 + 8);
/*  17 */     double d2 = (paramInt2 * 16 + 8);
/*     */     
/*  19 */     float f1 = 0.0F;
/*  20 */     float f2 = 0.0F;
/*     */ 
/*     */     
/*  23 */     Random random = new Random(this.b.nextLong());
/*     */     
/*  25 */     if (paramInt4 <= 0) {
/*  26 */       int j = this.a * 16 - 16;
/*  27 */       paramInt4 = j - random.nextInt(j / 4);
/*     */     } 
/*  29 */     boolean bool1 = false;
/*     */     
/*  31 */     if (paramInt3 == -1) {
/*  32 */       paramInt3 = paramInt4 / 2;
/*  33 */       bool1 = true;
/*     */     } 
/*     */ 
/*     */     
/*  37 */     int i = random.nextInt(paramInt4 / 2) + paramInt4 / 4;
/*  38 */     boolean bool2 = (random.nextInt(6) == 0) ? 1 : 0;
/*     */     
/*  40 */     for (; paramInt3 < paramInt4; paramInt3++) {
/*  41 */       double d3 = 1.5D + (MathHelper.sin(paramInt3 * 3.1415927F / paramInt4) * paramFloat1 * 1.0F);
/*  42 */       double d4 = d3 * paramDouble4;
/*     */       
/*  44 */       float f3 = MathHelper.cos(paramFloat3);
/*  45 */       float f4 = MathHelper.sin(paramFloat3);
/*  46 */       paramDouble1 += (MathHelper.cos(paramFloat2) * f3);
/*  47 */       paramDouble2 += f4;
/*  48 */       paramDouble3 += (MathHelper.sin(paramFloat2) * f3);
/*     */       
/*  50 */       if (bool2) {
/*  51 */         paramFloat3 *= 0.92F;
/*     */       } else {
/*  53 */         paramFloat3 *= 0.7F;
/*     */       } 
/*  55 */       paramFloat3 += f2 * 0.1F;
/*  56 */       paramFloat2 += f1 * 0.1F;
/*     */       
/*  58 */       f2 *= 0.9F;
/*  59 */       f1 *= 0.75F;
/*  60 */       f2 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  61 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */ 
/*     */       
/*  64 */       if (!bool1 && paramInt3 == i && paramFloat1 > 1.0F) {
/*  65 */         a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 - 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*  66 */         a(paramInt1, paramInt2, paramArrayOfByte, paramDouble1, paramDouble2, paramDouble3, random.nextFloat() * 0.5F + 0.5F, paramFloat2 + 1.5707964F, paramFloat3 / 3.0F, paramInt3, paramInt4, 1.0D);
/*     */         return;
/*     */       } 
/*  69 */       if (bool1 || random.nextInt(4) != 0) {
/*     */ 
/*     */         
/*  72 */         double d5 = paramDouble1 - d1;
/*  73 */         double d6 = paramDouble3 - d2;
/*  74 */         double d7 = (paramInt4 - paramInt3);
/*  75 */         double d8 = (paramFloat1 + 2.0F + 16.0F);
/*  76 */         if (d5 * d5 + d6 * d6 - d7 * d7 > d8 * d8) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  81 */         if (paramDouble1 >= d1 - 16.0D - d3 * 2.0D && paramDouble3 >= d2 - 16.0D - d3 * 2.0D && paramDouble1 <= d1 + 16.0D + d3 * 2.0D && paramDouble3 <= d2 + 16.0D + d3 * 2.0D) {
/*     */           
/*  83 */           int j = MathHelper.floor(paramDouble1 - d3) - paramInt1 * 16 - 1;
/*  84 */           int k = MathHelper.floor(paramDouble1 + d3) - paramInt1 * 16 + 1;
/*     */           
/*  86 */           int m = MathHelper.floor(paramDouble2 - d4) - 1;
/*  87 */           int n = MathHelper.floor(paramDouble2 + d4) + 1;
/*     */           
/*  89 */           int i1 = MathHelper.floor(paramDouble3 - d3) - paramInt2 * 16 - 1;
/*  90 */           int i2 = MathHelper.floor(paramDouble3 + d3) - paramInt2 * 16 + 1;
/*     */           
/*  92 */           if (j < 0) j = 0; 
/*  93 */           if (k > 16) k = 16;
/*     */           
/*  95 */           if (m < 1) m = 1; 
/*  96 */           if (n > 120) n = 120;
/*     */           
/*  98 */           if (i1 < 0) i1 = 0; 
/*  99 */           if (i2 > 16) i2 = 16;
/*     */           
/* 101 */           boolean bool = false; int i3;
/* 102 */           for (i3 = j; !bool && i3 < k; i3++) {
/* 103 */             for (int i4 = i1; !bool && i4 < i2; i4++) {
/* 104 */               for (int i5 = n + 1; !bool && i5 >= m - 1; i5--) {
/* 105 */                 int i6 = (i3 * 16 + i4) * 128 + i5;
/* 106 */                 if (i5 >= 0 && i5 < 128) {
/* 107 */                   if (paramArrayOfByte[i6] == Block.WATER.id || paramArrayOfByte[i6] == Block.STATIONARY_WATER.id) {
/* 108 */                     bool = true;
/*     */                   }
/* 110 */                   if (i5 != m - 1 && i3 != j && i3 != k - 1 && i4 != i1 && i4 != i2 - 1)
/* 111 */                     i5 = m; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/* 116 */           if (!bool) {
/*     */             
/* 118 */             for (i3 = j; i3 < k; i3++) {
/* 119 */               double d = ((i3 + paramInt1 * 16) + 0.5D - paramDouble1) / d3;
/* 120 */               for (int i4 = i1; i4 < i2; i4++) {
/* 121 */                 double d9 = ((i4 + paramInt2 * 16) + 0.5D - paramDouble3) / d3;
/* 122 */                 int i5 = (i3 * 16 + i4) * 128 + n;
/* 123 */                 boolean bool3 = false;
/* 124 */                 if (d * d + d9 * d9 < 1.0D) {
/* 125 */                   for (int i6 = n - 1; i6 >= m; i6--) {
/* 126 */                     double d10 = (i6 + 0.5D - paramDouble2) / d4;
/* 127 */                     if (d10 > -0.7D && d * d + d10 * d10 + d9 * d9 < 1.0D) {
/* 128 */                       byte b = paramArrayOfByte[i5];
/* 129 */                       if (b == Block.GRASS.id) bool3 = true; 
/* 130 */                       if (b == Block.STONE.id || b == Block.DIRT.id || b == Block.GRASS.id)
/* 131 */                         if (i6 < 10) {
/* 132 */                           paramArrayOfByte[i5] = (byte)Block.LAVA.id;
/*     */                         } else {
/* 134 */                           paramArrayOfByte[i5] = 0;
/* 135 */                           if (bool3 && paramArrayOfByte[i5 - 1] == Block.DIRT.id) paramArrayOfByte[i5 - 1] = (byte)Block.GRASS.id;
/*     */                         
/*     */                         }  
/*     */                     } 
/* 139 */                     i5--;
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/* 144 */             if (bool1) {
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
/*     */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte) {
/* 158 */     int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(40) + 1) + 1);
/* 159 */     if (this.b.nextInt(15) != 0) i = 0;
/*     */     
/* 161 */     for (byte b = 0; b < i; b++) {
/* 162 */       double d1 = (paramInt1 * 16 + this.b.nextInt(16));
/*     */ 
/*     */       
/* 165 */       double d2 = this.b.nextInt(this.b.nextInt(120) + 8);
/*     */       
/* 167 */       double d3 = (paramInt2 * 16 + this.b.nextInt(16));
/*     */       
/* 169 */       int j = 1;
/* 170 */       if (this.b.nextInt(4) == 0) {
/* 171 */         a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3);
/* 172 */         j += this.b.nextInt(4);
/*     */       } 
/*     */       
/* 175 */       for (byte b1 = 0; b1 < j; b1++) {
/*     */         
/* 177 */         float f1 = this.b.nextFloat() * 3.1415927F * 2.0F;
/* 178 */         float f2 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 179 */         float f3 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
/*     */         
/* 181 */         a(paramInt3, paramInt4, paramArrayOfByte, d1, d2, d3, f3, f1, f2, 0, 0, 1.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MapGenCaves.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */