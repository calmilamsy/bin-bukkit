/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenLakes
/*     */   extends WorldGenerator
/*     */ {
/*     */   private int a;
/*     */   
/*  14 */   public WorldGenLakes(int paramInt) { this.a = paramInt; }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  18 */     paramInt1 -= 8;
/*  19 */     paramInt3 -= 8;
/*  20 */     while (paramInt2 > 0 && paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) {
/*  21 */       paramInt2--;
/*     */     }
/*  23 */     paramInt2 -= 4;
/*     */     
/*  25 */     boolean[] arrayOfBoolean = new boolean[2048];
/*     */     
/*  27 */     int i = paramRandom.nextInt(4) + 4; int j;
/*  28 */     for (j = 0; j < i; j++) {
/*  29 */       double d1 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*  30 */       double d2 = paramRandom.nextDouble() * 4.0D + 2.0D;
/*  31 */       double d3 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*     */       
/*  33 */       double d4 = paramRandom.nextDouble() * (16.0D - d1 - 2.0D) + 1.0D + d1 / 2.0D;
/*  34 */       double d5 = paramRandom.nextDouble() * (8.0D - d2 - 4.0D) + 2.0D + d2 / 2.0D;
/*  35 */       double d6 = paramRandom.nextDouble() * (16.0D - d3 - 2.0D) + 1.0D + d3 / 2.0D;
/*     */       
/*  37 */       for (byte b = 1; b < 15; b++) {
/*  38 */         for (byte b1 = 1; b1 < 15; b1++) {
/*  39 */           for (byte b2 = 1; b2 < 7; b2++) {
/*  40 */             double d7 = (b - d4) / d1 / 2.0D;
/*  41 */             double d8 = (b2 - d5) / d2 / 2.0D;
/*  42 */             double d9 = (b1 - d6) / d3 / 2.0D;
/*  43 */             double d10 = d7 * d7 + d8 * d8 + d9 * d9;
/*  44 */             if (d10 < 1.0D) arrayOfBoolean[(b * 16 + b1) * 8 + b2] = true;
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  50 */     for (j = 0; j < 16; j++) {
/*  51 */       for (int k = 0; k < 16; k++) {
/*  52 */         for (int m = 0; m < 8; m++) {
/*  53 */           boolean bool = (!arrayOfBoolean[(j * 16 + k) * 8 + m] && ((j < 15 && arrayOfBoolean[((j + 1) * 16 + k) * 8 + m]) || (j > 0 && arrayOfBoolean[((j - 1) * 16 + k) * 8 + m]) || (k < 15 && arrayOfBoolean[(j * 16 + k + 1) * 8 + m]) || (k > 0 && arrayOfBoolean[(j * 16 + k - 1) * 8 + m]) || (m < 7 && arrayOfBoolean[(j * 16 + k) * 8 + m + 1]) || (m > 0 && arrayOfBoolean[(j * 16 + k) * 8 + m - 1]))) ? 1 : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  61 */           if (bool) {
/*  62 */             Material material = paramWorld.getMaterial(paramInt1 + j, paramInt2 + m, paramInt3 + k);
/*  63 */             if (m >= 4 && material.isLiquid()) return false; 
/*  64 */             if (m < 4 && !material.isBuildable() && paramWorld.getTypeId(paramInt1 + j, paramInt2 + m, paramInt3 + k) != this.a) return false;
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     for (j = 0; j < 16; j++) {
/*  72 */       for (int k = 0; k < 16; k++) {
/*  73 */         for (int m = 0; m < 8; m++) {
/*  74 */           if (arrayOfBoolean[(j * 16 + k) * 8 + m]) {
/*  75 */             paramWorld.setRawTypeId(paramInt1 + j, paramInt2 + m, paramInt3 + k, (m >= 4) ? 0 : this.a);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     for (j = 0; j < 16; j++) {
/*  82 */       for (int k = 0; k < 16; k++) {
/*  83 */         for (int m = 4; m < 8; m++) {
/*  84 */           if (arrayOfBoolean[(j * 16 + k) * 8 + m] && 
/*  85 */             paramWorld.getTypeId(paramInt1 + j, paramInt2 + m - 1, paramInt3 + k) == Block.DIRT.id && paramWorld.a(EnumSkyBlock.SKY, paramInt1 + j, paramInt2 + m, paramInt3 + k) > 0) {
/*  86 */             paramWorld.setRawTypeId(paramInt1 + j, paramInt2 + m - 1, paramInt3 + k, Block.GRASS.id);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  93 */     if ((Block.byId[this.a]).material == Material.LAVA) {
/*  94 */       for (j = 0; j < 16; j++) {
/*  95 */         for (int k = 0; k < 16; k++) {
/*  96 */           for (int m = 0; m < 8; m++) {
/*  97 */             boolean bool = (!arrayOfBoolean[(j * 16 + k) * 8 + m] && ((j < 15 && arrayOfBoolean[((j + 1) * 16 + k) * 8 + m]) || (j > 0 && arrayOfBoolean[((j - 1) * 16 + k) * 8 + m]) || (k < 15 && arrayOfBoolean[(j * 16 + k + 1) * 8 + m]) || (k > 0 && arrayOfBoolean[(j * 16 + k - 1) * 8 + m]) || (m < 7 && arrayOfBoolean[(j * 16 + k) * 8 + m + 1]) || (m > 0 && arrayOfBoolean[(j * 16 + k) * 8 + m - 1]))) ? 1 : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 105 */             if (bool && (
/* 106 */               m < 4 || paramRandom.nextInt(2) != 0) && paramWorld.getMaterial(paramInt1 + j, paramInt2 + m, paramInt3 + k).isBuildable()) {
/* 107 */               paramWorld.setRawTypeId(paramInt1 + j, paramInt2 + m, paramInt3 + k, Block.STONE.id);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenLakes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */