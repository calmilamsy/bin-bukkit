/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMinable
/*    */   extends WorldGenerator
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenMinable(int paramInt1, int paramInt2) {
/* 14 */     this.a = paramInt1;
/* 15 */     this.b = paramInt2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     float f = paramRandom.nextFloat() * 3.1415927F;
/*    */     
/* 22 */     double d1 = ((paramInt1 + 8) + MathHelper.sin(f) * this.b / 8.0F);
/* 23 */     double d2 = ((paramInt1 + 8) - MathHelper.sin(f) * this.b / 8.0F);
/* 24 */     double d3 = ((paramInt3 + 8) + MathHelper.cos(f) * this.b / 8.0F);
/* 25 */     double d4 = ((paramInt3 + 8) - MathHelper.cos(f) * this.b / 8.0F);
/*    */     
/* 27 */     double d5 = (paramInt2 + paramRandom.nextInt(3) + 2);
/* 28 */     double d6 = (paramInt2 + paramRandom.nextInt(3) + 2);
/*    */ 
/*    */     
/* 31 */     for (byte b1 = 0; b1 <= this.b; b1++) {
/* 32 */       double d7 = d1 + (d2 - d1) * b1 / this.b;
/* 33 */       double d8 = d5 + (d6 - d5) * b1 / this.b;
/* 34 */       double d9 = d3 + (d4 - d3) * b1 / this.b;
/*    */       
/* 36 */       double d10 = paramRandom.nextDouble() * this.b / 16.0D;
/* 37 */       double d11 = (MathHelper.sin(b1 * 3.1415927F / this.b) + 1.0F) * d10 + 1.0D;
/* 38 */       double d12 = (MathHelper.sin(b1 * 3.1415927F / this.b) + 1.0F) * d10 + 1.0D;
/*    */       
/* 40 */       int i = MathHelper.floor(d7 - d11 / 2.0D);
/* 41 */       int j = MathHelper.floor(d8 - d12 / 2.0D);
/* 42 */       int k = MathHelper.floor(d9 - d11 / 2.0D);
/*    */       
/* 44 */       int m = MathHelper.floor(d7 + d11 / 2.0D);
/* 45 */       int n = MathHelper.floor(d8 + d12 / 2.0D);
/* 46 */       int i1 = MathHelper.floor(d9 + d11 / 2.0D);
/*    */       
/* 48 */       for (int i2 = i; i2 <= m; i2++) {
/* 49 */         double d = (i2 + 0.5D - d7) / d11 / 2.0D;
/* 50 */         if (d * d < 1.0D) {
/* 51 */           for (int i3 = j; i3 <= n; i3++) {
/* 52 */             double d13 = (i3 + 0.5D - d8) / d12 / 2.0D;
/* 53 */             if (d * d + d13 * d13 < 1.0D) {
/* 54 */               for (int i4 = k; i4 <= i1; i4++) {
/* 55 */                 double d14 = (i4 + 0.5D - d9) / d11 / 2.0D;
/* 56 */                 if (d * d + d13 * d13 + d14 * d14 < 1.0D && 
/* 57 */                   paramWorld.getTypeId(i2, i3, i4) == Block.STONE.id) paramWorld.setRawTypeId(i2, i3, i4, this.a);
/*    */               
/*    */               } 
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\WorldGenMinable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */