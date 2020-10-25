/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves2
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGenerator2[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGeneratorOctaves2(Random paramRandom, int paramInt) {
/* 14 */     this.b = paramInt;
/* 15 */     this.a = new NoiseGenerator2[paramInt];
/* 16 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 17 */       this.a[b1] = new NoiseGenerator2(paramRandom);
/*    */     }
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
/* 46 */   public double[] a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) { return a(paramArrayOfDouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4, paramDouble5, 0.5D); }
/*    */ 
/*    */   
/*    */   public double[] a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/* 50 */     paramDouble3 /= 1.5D;
/* 51 */     paramDouble4 /= 1.5D;
/*    */     
/* 53 */     if (paramArrayOfDouble == null || paramArrayOfDouble.length < paramInt1 * paramInt2) { paramArrayOfDouble = new double[paramInt1 * paramInt2]; }
/* 54 */     else { for (byte b2 = 0; b2 < paramArrayOfDouble.length; b2++) {
/* 55 */         paramArrayOfDouble[b2] = 0.0D;
/*    */       } }
/*    */     
/* 58 */     double d1 = 1.0D;
/* 59 */     double d2 = 1.0D;
/* 60 */     for (byte b1 = 0; b1 < this.b; b1++) {
/* 61 */       this.a[b1].a(paramArrayOfDouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3 * d2, paramDouble4 * d2, 0.55D / d1);
/* 62 */       d2 *= paramDouble5;
/* 63 */       d1 *= paramDouble6;
/*    */     } 
/*    */     
/* 66 */     return paramArrayOfDouble;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\NoiseGeneratorOctaves2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */