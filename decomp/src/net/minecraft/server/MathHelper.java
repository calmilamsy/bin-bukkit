/*    */ package net.minecraft.server;
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
/*    */ public class MathHelper
/*    */ {
/* 18 */   private static float[] a = new float[65536]; static  {
/* 19 */     for (byte b = 0; b < 65536; b++) {
/* 20 */       a[b] = (float)Math.sin(b * Math.PI * 2.0D / 65536.0D);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 25 */   public static final float sin(float paramFloat) { return a[(int)(paramFloat * 10430.378F) & 0xFFFF]; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public static final float cos(float paramFloat) { return a[(int)(paramFloat * 10430.378F + 16384.0F) & 0xFFFF]; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public static final float c(float paramFloat) { return (float)Math.sqrt(paramFloat); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public static final float a(double paramDouble) { return (float)Math.sqrt(paramDouble); }
/*    */ 
/*    */   
/*    */   public static int d(float paramFloat) {
/* 41 */     int i = (int)paramFloat;
/* 42 */     return (paramFloat < i) ? (i - 1) : i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int floor(double paramDouble) {
/* 50 */     int i = (int)paramDouble;
/* 51 */     return (paramDouble < i) ? (i - 1) : i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static float abs(float paramFloat) { return (paramFloat >= 0.0F) ? paramFloat : -paramFloat; }
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
/*    */   
/*    */   public static double a(double paramDouble1, double paramDouble2) {
/* 88 */     if (paramDouble1 < 0.0D) paramDouble1 = -paramDouble1; 
/* 89 */     if (paramDouble2 < 0.0D) paramDouble2 = -paramDouble2; 
/* 90 */     return (paramDouble1 > paramDouble2) ? paramDouble1 : paramDouble2;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MathHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */