/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.BlockChangeDelegate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTaiga1
/*    */   extends WorldGenerator
/*    */ {
/* 17 */   public boolean a(World world, Random random, int i, int j, int k) { return generate((BlockChangeDelegate)world, random, i, j, k); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
/* 22 */     int l = random.nextInt(5) + 7;
/* 23 */     int i1 = l - random.nextInt(2) - 3;
/* 24 */     int j1 = l - i1;
/* 25 */     int k1 = 1 + random.nextInt(j1 + 1);
/* 26 */     boolean flag = true;
/*    */     
/* 28 */     if (j >= 1 && j + l + 1 <= 128) {
/*    */       int l1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 35 */       for (l1 = j; l1 <= j + 1 + l && flag; l1++) {
/* 36 */         int l2; boolean flag1 = true;
/*    */         
/* 38 */         if (l1 - j < i1) {
/* 39 */           l2 = 0;
/*    */         } else {
/* 41 */           l2 = k1;
/*    */         } 
/*    */         
/* 44 */         for (int i2 = i - l2; i2 <= i + l2 && flag; i2++) {
/* 45 */           for (int j2 = k - l2; j2 <= k + l2 && flag; j2++) {
/* 46 */             if (l1 >= 0 && l1 < 128) {
/* 47 */               int k2 = world.getTypeId(i2, l1, j2);
/* 48 */               if (k2 != 0 && k2 != Block.LEAVES.id) {
/* 49 */                 flag = false;
/*    */               }
/*    */             } else {
/* 52 */               flag = false;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 58 */       if (!flag) {
/* 59 */         return false;
/*    */       }
/* 61 */       l1 = world.getTypeId(i, j - 1, k);
/* 62 */       if ((l1 == Block.GRASS.id || l1 == Block.DIRT.id) && j < 128 - l - 1) {
/* 63 */         world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
/* 64 */         int l2 = 0;
/*    */         int i2;
/* 66 */         for (i2 = j + l; i2 >= j + i1; i2--) {
/* 67 */           for (int j2 = i - l2; j2 <= i + l2; j2++) {
/* 68 */             int k2 = j2 - i;
/*    */             
/* 70 */             for (int i3 = k - l2; i3 <= k + l2; i3++) {
/* 71 */               int j3 = i3 - k;
/*    */               
/* 73 */               if ((Math.abs(k2) != l2 || Math.abs(j3) != l2 || l2 <= 0) && !Block.o[world.getTypeId(j2, i2, i3)]) {
/* 74 */                 world.setRawTypeIdAndData(j2, i2, i3, Block.LEAVES.id, 1);
/*    */               }
/*    */             } 
/*    */           } 
/*    */           
/* 79 */           if (l2 >= 1 && i2 == j + i1 + 1) {
/* 80 */             l2--;
/* 81 */           } else if (l2 < k1) {
/* 82 */             l2++;
/*    */           } 
/*    */         } 
/*    */         
/* 86 */         for (i2 = 0; i2 < l - 1; i2++) {
/* 87 */           int j2 = world.getTypeId(i, j + i2, k);
/* 88 */           if (j2 == 0 || j2 == Block.LEAVES.id) {
/* 89 */             world.setRawTypeIdAndData(i, j + i2, k, Block.LOG.id, 1);
/*    */           }
/*    */         } 
/*    */         
/* 93 */         return true;
/*    */       } 
/* 95 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 99 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenTaiga1.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */