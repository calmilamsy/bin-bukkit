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
/*    */ public class WorldGenTrees
/*    */   extends WorldGenerator
/*    */ {
/* 17 */   public boolean a(World world, Random random, int i, int j, int k) { return generate((BlockChangeDelegate)world, random, i, j, k); }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
/* 22 */     int l = random.nextInt(3) + 4;
/* 23 */     boolean flag = true;
/*    */     
/* 25 */     if (j >= 1 && j + l + 1 <= 128) {
/*    */       int i1;
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 31 */       for (i1 = j; i1 <= j + 1 + l; i1++) {
/* 32 */         byte b0 = 1;
/*    */         
/* 34 */         if (i1 == j) {
/* 35 */           b0 = 0;
/*    */         }
/*    */         
/* 38 */         if (i1 >= j + 1 + l - 2) {
/* 39 */           b0 = 2;
/*    */         }
/*    */         
/* 42 */         for (int j1 = i - b0; j1 <= i + b0 && flag; j1++) {
/* 43 */           for (int k1 = k - b0; k1 <= k + b0 && flag; k1++) {
/* 44 */             if (i1 >= 0 && i1 < 128) {
/* 45 */               int l1 = world.getTypeId(j1, i1, k1);
/* 46 */               if (l1 != 0 && l1 != Block.LEAVES.id) {
/* 47 */                 flag = false;
/*    */               }
/*    */             } else {
/* 50 */               flag = false;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 56 */       if (!flag) {
/* 57 */         return false;
/*    */       }
/* 59 */       i1 = world.getTypeId(i, j - 1, k);
/* 60 */       if ((i1 == Block.GRASS.id || i1 == Block.DIRT.id) && j < 128 - l - 1) {
/* 61 */         world.setRawTypeId(i, j - 1, k, Block.DIRT.id);
/*    */         
/*    */         int i2;
/*    */         
/* 65 */         for (i2 = j - 3 + l; i2 <= j + l; i2++) {
/* 66 */           int j1 = i2 - j + l;
/* 67 */           int k1 = 1 - j1 / 2;
/*    */           
/* 69 */           for (int l1 = i - k1; l1 <= i + k1; l1++) {
/* 70 */             int j2 = l1 - i;
/*    */             
/* 72 */             for (int k2 = k - k1; k2 <= k + k1; k2++) {
/* 73 */               int l2 = k2 - k;
/*    */               
/* 75 */               if ((Math.abs(j2) != k1 || Math.abs(l2) != k1 || (random.nextInt(2) != 0 && j1 != 0)) && !Block.o[world.getTypeId(l1, i2, k2)]) {
/* 76 */                 world.setRawTypeId(l1, i2, k2, Block.LEAVES.id);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */         
/* 82 */         for (i2 = 0; i2 < l; i2++) {
/* 83 */           int j1 = world.getTypeId(i, j + i2, k);
/* 84 */           if (j1 == 0 || j1 == Block.LEAVES.id) {
/* 85 */             world.setRawTypeId(i, j + i2, k, Block.LOG.id);
/*    */           }
/*    */         } 
/*    */         
/* 89 */         return true;
/*    */       } 
/* 91 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 95 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenTrees.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */