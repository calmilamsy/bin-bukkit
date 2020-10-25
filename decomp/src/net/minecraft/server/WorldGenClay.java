/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenClay(int i) {
/* 11 */     this.a = Block.CLAY.id;
/* 12 */     this.b = i;
/*    */   }
/*    */   
/*    */   public boolean a(World world, Random random, int i, int j, int k) {
/* 16 */     if (world.getMaterial(i, j, k) != Material.WATER) {
/* 17 */       return false;
/*    */     }
/* 19 */     float f = random.nextFloat() * 3.1415927F;
/* 20 */     double d0 = ((i + 8) + MathHelper.sin(f) * this.b / 8.0F);
/* 21 */     double d1 = ((i + 8) - MathHelper.sin(f) * this.b / 8.0F);
/* 22 */     double d2 = ((k + 8) + MathHelper.cos(f) * this.b / 8.0F);
/* 23 */     double d3 = ((k + 8) - MathHelper.cos(f) * this.b / 8.0F);
/* 24 */     double d4 = (j + random.nextInt(3) + 2);
/* 25 */     double d5 = (j + random.nextInt(3) + 2);
/*    */     
/* 27 */     for (int l = 0; l <= this.b; l++) {
/* 28 */       double d6 = d0 + (d1 - d0) * l / this.b;
/* 29 */       double d7 = d4 + (d5 - d4) * l / this.b;
/* 30 */       double d8 = d2 + (d3 - d2) * l / this.b;
/* 31 */       double d9 = random.nextDouble() * this.b / 16.0D;
/* 32 */       double d10 = (MathHelper.sin(l * 3.1415927F / this.b) + 1.0F) * d9 + 1.0D;
/* 33 */       double d11 = (MathHelper.sin(l * 3.1415927F / this.b) + 1.0F) * d9 + 1.0D;
/* 34 */       int i1 = MathHelper.floor(d6 - d10 / 2.0D);
/* 35 */       int j1 = MathHelper.floor(d6 + d10 / 2.0D);
/* 36 */       int k1 = MathHelper.floor(d7 - d11 / 2.0D);
/* 37 */       int l1 = MathHelper.floor(d7 + d11 / 2.0D);
/* 38 */       int i2 = MathHelper.floor(d8 - d10 / 2.0D);
/* 39 */       int j2 = MathHelper.floor(d8 + d10 / 2.0D);
/*    */       
/* 41 */       for (int k2 = i1; k2 <= j1; k2++) {
/* 42 */         for (int l2 = k1; l2 <= l1; l2++) {
/* 43 */           for (int i3 = i2; i3 <= j2; i3++) {
/* 44 */             double d12 = (k2 + 0.5D - d6) / d10 / 2.0D;
/* 45 */             double d13 = (l2 + 0.5D - d7) / d11 / 2.0D;
/* 46 */             double d14 = (i3 + 0.5D - d8) / d10 / 2.0D;
/*    */             
/* 48 */             if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
/* 49 */               int j3 = world.getTypeId(k2, l2, i3);
/*    */               
/* 51 */               if (j3 == Block.SAND.id) {
/* 52 */                 world.setRawTypeId(k2, l2, i3, this.a);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenClay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */