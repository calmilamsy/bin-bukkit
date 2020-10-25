/*     */ package net.minecraft.server;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class WorldGenDungeons
/*     */   extends WorldGenerator
/*     */ {
/*     */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  19 */     int i = 3;
/*  20 */     int j = paramRandom.nextInt(2) + 2;
/*  21 */     int k = paramRandom.nextInt(2) + 2;
/*     */     
/*  23 */     byte b = 0; int m;
/*  24 */     for (m = paramInt1 - j - 1; m <= paramInt1 + j + 1; m++) {
/*  25 */       for (int n = paramInt2 - 1; n <= paramInt2 + i + 1; n++) {
/*  26 */         for (int i1 = paramInt3 - k - 1; i1 <= paramInt3 + k + 1; i1++) {
/*  27 */           Material material = paramWorld.getMaterial(m, n, i1);
/*  28 */           if (n == paramInt2 - 1 && !material.isBuildable()) return false; 
/*  29 */           if (n == paramInt2 + i + 1 && !material.isBuildable()) return false;
/*     */           
/*  31 */           if ((m == paramInt1 - j - 1 || m == paramInt1 + j + 1 || i1 == paramInt3 - k - 1 || i1 == paramInt3 + k + 1) && 
/*  32 */             n == paramInt2 && paramWorld.isEmpty(m, n, i1) && paramWorld.isEmpty(m, n + 1, i1)) {
/*  33 */             b++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  41 */     if (b < 1 || b > 5) return false;
/*     */     
/*  43 */     for (m = paramInt1 - j - 1; m <= paramInt1 + j + 1; m++) {
/*  44 */       for (int n = paramInt2 + i; n >= paramInt2 - 1; n--) {
/*  45 */         for (int i1 = paramInt3 - k - 1; i1 <= paramInt3 + k + 1; i1++) {
/*     */           
/*  47 */           if (m == paramInt1 - j - 1 || n == paramInt2 - 1 || i1 == paramInt3 - k - 1 || m == paramInt1 + j + 1 || n == paramInt2 + i + 1 || i1 == paramInt3 + k + 1) {
/*  48 */             if (n >= 0 && !paramWorld.getMaterial(m, n - 1, i1).isBuildable()) {
/*  49 */               paramWorld.setTypeId(m, n, i1, 0);
/*  50 */             } else if (paramWorld.getMaterial(m, n, i1).isBuildable()) {
/*  51 */               if (n == paramInt2 - 1 && paramRandom.nextInt(4) != 0) {
/*  52 */                 paramWorld.setTypeId(m, n, i1, Block.MOSSY_COBBLESTONE.id);
/*     */               } else {
/*  54 */                 paramWorld.setTypeId(m, n, i1, Block.COBBLESTONE.id);
/*     */               } 
/*     */             } 
/*     */           } else {
/*  58 */             paramWorld.setTypeId(m, n, i1, 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     for (m = 0; m < 2; m++) {
/*  65 */       for (byte b1 = 0; b1 < 3; b1++) {
/*  66 */         int n = paramInt1 + paramRandom.nextInt(j * 2 + 1) - j;
/*  67 */         int i1 = paramInt2;
/*  68 */         int i2 = paramInt3 + paramRandom.nextInt(k * 2 + 1) - k;
/*  69 */         if (paramWorld.isEmpty(n, i1, i2)) {
/*     */           
/*  71 */           byte b2 = 0;
/*  72 */           if (paramWorld.getMaterial(n - 1, i1, i2).isBuildable()) b2++; 
/*  73 */           if (paramWorld.getMaterial(n + 1, i1, i2).isBuildable()) b2++; 
/*  74 */           if (paramWorld.getMaterial(n, i1, i2 - 1).isBuildable()) b2++; 
/*  75 */           if (paramWorld.getMaterial(n, i1, i2 + 1).isBuildable()) b2++;
/*     */           
/*  77 */           if (b2 == 1) {
/*     */             
/*  79 */             paramWorld.setTypeId(n, i1, i2, Block.CHEST.id);
/*  80 */             TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(n, i1, i2);
/*  81 */             for (byte b3 = 0; b3 < 8; b3++) {
/*  82 */               ItemStack itemStack = a(paramRandom);
/*  83 */               if (itemStack != null) tileEntityChest.setItem(paramRandom.nextInt(tileEntityChest.getSize()), itemStack);
/*     */             
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  91 */     paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.MOB_SPAWNER.id);
/*  92 */     TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*  93 */     tileEntityMobSpawner.a(b(paramRandom));
/*     */ 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   private ItemStack a(Random paramRandom) {
/* 100 */     int i = paramRandom.nextInt(11);
/* 101 */     if (i == 0) return new ItemStack(Item.SADDLE); 
/* 102 */     if (i == 1) return new ItemStack(Item.IRON_INGOT, paramRandom.nextInt(4) + 1); 
/* 103 */     if (i == 2) return new ItemStack(Item.BREAD); 
/* 104 */     if (i == 3) return new ItemStack(Item.WHEAT, paramRandom.nextInt(4) + 1); 
/* 105 */     if (i == 4) return new ItemStack(Item.SULPHUR, paramRandom.nextInt(4) + 1); 
/* 106 */     if (i == 5) return new ItemStack(Item.STRING, paramRandom.nextInt(4) + 1); 
/* 107 */     if (i == 6) return new ItemStack(Item.BUCKET); 
/* 108 */     if (i == 7 && paramRandom.nextInt(100) == 0) return new ItemStack(Item.GOLDEN_APPLE); 
/* 109 */     if (i == 8 && paramRandom.nextInt(2) == 0) return new ItemStack(Item.REDSTONE, paramRandom.nextInt(4) + 1); 
/* 110 */     if (i == 9 && paramRandom.nextInt(10) == 0) return new ItemStack(Item.byId[Item.GOLD_RECORD.id + paramRandom.nextInt(2)]); 
/* 111 */     if (i == 10) return new ItemStack(Item.INK_SACK, true, 3);
/*     */     
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   private String b(Random paramRandom) {
/* 117 */     int i = paramRandom.nextInt(4);
/* 118 */     if (i == 0) return "Skeleton"; 
/* 119 */     if (i == 1) return "Zombie"; 
/* 120 */     if (i == 2) return "Zombie"; 
/* 121 */     if (i == 3) return "Spider"; 
/* 122 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenDungeons.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */