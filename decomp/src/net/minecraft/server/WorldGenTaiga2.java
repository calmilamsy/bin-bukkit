/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.BlockChangeDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenTaiga2
/*     */   extends WorldGenerator
/*     */ {
/*  17 */   public boolean a(World world, Random random, int i, int j, int k) { return generate((BlockChangeDelegate)world, random, i, j, k); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
/*  22 */     int l = random.nextInt(4) + 6;
/*  23 */     int i1 = 1 + random.nextInt(2);
/*  24 */     int j1 = l - i1;
/*  25 */     int k1 = 2 + random.nextInt(2);
/*  26 */     boolean flag = true;
/*     */     
/*  28 */     if (j >= 1 && j + l + 1 <= 128) {
/*     */       int l1;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  34 */       for (l1 = j; l1 <= j + 1 + l && flag; l1++) {
/*  35 */         int k2; boolean flag1 = true;
/*     */         
/*  37 */         if (l1 - j < i1) {
/*  38 */           k2 = 0;
/*     */         } else {
/*  40 */           k2 = k1;
/*     */         } 
/*     */         
/*  43 */         for (int i2 = i - k2; i2 <= i + k2 && flag; i2++) {
/*  44 */           for (int l2 = k - k2; l2 <= k + k2 && flag; l2++) {
/*  45 */             if (l1 >= 0 && l1 < 128) {
/*  46 */               int j2 = world.getTypeId(i2, l1, l2);
/*  47 */               if (j2 != 0 && j2 != Block.LEAVES.id) {
/*  48 */                 flag = false;
/*     */               }
/*     */             } else {
/*  51 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  57 */       if (!flag) {
/*  58 */         return false;
/*     */       }
/*  60 */       l1 = world.getTypeId(i, j - 1, k);
/*  61 */       if ((l1 == Block.GRASS.id || l1 == Block.DIRT.id) && j < 128 - l - 1) {
/*  62 */         world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
/*  63 */         int k2 = random.nextInt(2);
/*  64 */         int i2 = 1;
/*  65 */         byte b0 = 0;
/*     */ 
/*     */         
/*     */         int j2;
/*     */         
/*  70 */         for (j2 = 0; j2 <= j1; j2++) {
/*  71 */           byte b; int j3 = j + l - j2;
/*     */           
/*  73 */           for (int i3 = i - k2; i3 <= i + k2; i3++) {
/*  74 */             int k3 = i3 - i;
/*     */             
/*  76 */             for (int l3 = k - k2; l3 <= k + k2; l3++) {
/*  77 */               int i4 = l3 - k;
/*     */               
/*  79 */               if ((Math.abs(k3) != k2 || Math.abs(i4) != k2 || k2 <= 0) && !Block.o[world.getTypeId(i3, j3, l3)]) {
/*  80 */                 world.setRawTypeIdAndData(i3, j3, l3, Block.LEAVES.id, 1);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/*  85 */           if (k2 >= i2) {
/*  86 */             b = b0;
/*  87 */             b0 = 1;
/*  88 */             i2++;
/*  89 */             if (i2 > k1) {
/*  90 */               i2 = k1;
/*     */             }
/*     */           } else {
/*  93 */             b++;
/*     */           } 
/*     */         } 
/*     */         
/*  97 */         j2 = random.nextInt(3);
/*     */         
/*  99 */         for (int j3 = 0; j3 < l - j2; j3++) {
/* 100 */           int i3 = world.getTypeId(i, j + j3, k);
/* 101 */           if (i3 == 0 || i3 == Block.LEAVES.id) {
/* 102 */             world.setRawTypeIdAndData(i, j + j3, k, Block.LOG.id, 1);
/*     */           }
/*     */         } 
/*     */         
/* 106 */         return true;
/*     */       } 
/* 108 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenTaiga2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */