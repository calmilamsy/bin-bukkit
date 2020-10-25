/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGeneratorPerlin[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random paramRandom, int paramInt) {
/* 14 */     this.b = paramInt;
/* 15 */     this.a = new NoiseGeneratorPerlin[paramInt];
/* 16 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 17 */       this.a[b1] = new NoiseGeneratorPerlin(paramRandom);
/*    */     }
/*    */   }
/*    */   
/*    */   public double a(double paramDouble1, double paramDouble2) {
/* 22 */     double d1 = 0.0D;
/* 23 */     double d2 = 1.0D;
/*    */     
/* 25 */     for (byte b1 = 0; b1 < this.b; b1++) {
/* 26 */       d1 += this.a[b1].a(paramDouble1 * d2, paramDouble2 * d2) / d2;
/* 27 */       d2 /= 2.0D;
/*    */     } 
/*    */     
/* 30 */     return d1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, int paramInt3, double paramDouble4, double paramDouble5, double paramDouble6) {
/* 46 */     if (paramArrayOfDouble == null) { paramArrayOfDouble = new double[paramInt1 * paramInt2 * paramInt3]; }
/* 47 */     else { for (byte b2 = 0; b2 < paramArrayOfDouble.length; b2++) {
/* 48 */         paramArrayOfDouble[b2] = 0.0D;
/*    */       } }
/*    */     
/* 51 */     double d = 1.0D;
/*    */     
/* 53 */     for (byte b1 = 0; b1 < this.b; b1++) {
/*    */       
/* 55 */       this.a[b1].a(paramArrayOfDouble, paramDouble1, paramDouble2, paramDouble3, paramInt1, paramInt2, paramInt3, paramDouble4 * d, paramDouble5 * d, paramDouble6 * d, d);
/* 56 */       d /= 2.0D;
/*    */     } 
/*    */     
/* 59 */     return paramArrayOfDouble;
/*    */   }
/*    */ 
/*    */   
/* 63 */   public double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3) { return a(paramArrayOfDouble, paramInt1, 10.0D, paramInt2, paramInt3, 1, paramInt4, paramDouble1, 1.0D, paramDouble2); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NoiseGeneratorOctaves.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */