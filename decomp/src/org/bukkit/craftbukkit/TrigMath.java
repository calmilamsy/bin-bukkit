/*    */ package org.bukkit.craftbukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrigMath
/*    */ {
/*    */   static final double sq2p1 = 2.414213562373095D;
/*    */   static final double sq2m1 = 0.41421356237309503D;
/*    */   static final double p4 = 16.15364129822302D;
/*    */   static final double p3 = 268.42548195503974D;
/*    */   static final double p2 = 1153.029351540485D;
/*    */   static final double p1 = 1780.406316433197D;
/*    */   static final double p0 = 896.7859740366387D;
/*    */   static final double q4 = 58.95697050844462D;
/*    */   static final double q3 = 536.2653740312153D;
/*    */   static final double q2 = 1666.7838148816338D;
/*    */   static final double q1 = 2079.33497444541D;
/*    */   static final double q0 = 896.7859740366387D;
/*    */   static final double PIO2 = 1.5707963267948966D;
/*    */   
/*    */   private static double mxatan(double arg) {
/* 24 */     double argsq = arg * arg;
/*    */     
/* 26 */     double value = (((16.15364129822302D * argsq + 268.42548195503974D) * argsq + 1153.029351540485D) * argsq + 1780.406316433197D) * argsq + 896.7859740366387D;
/* 27 */     value /= (((((argsq + 58.95697050844462D) * argsq + 536.2653740312153D) * argsq + 1666.7838148816338D) * argsq + 2079.33497444541D) * argsq + 896.7859740366387D);
/* 28 */     return value * arg;
/*    */   }
/*    */ 
/*    */   
/* 32 */   private static double msatan(double arg) { return (arg < 0.41421356237309503D) ? mxatan(arg) : ((arg > 2.414213562373095D) ? (1.5707963267948966D - mxatan(1.0D / arg)) : (0.7853981633974483D + mxatan((arg - 1.0D) / (arg + 1.0D)))); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public static double atan(double arg) { return (arg > 0.0D) ? msatan(arg) : -msatan(-arg); }
/*    */ 
/*    */   
/*    */   public static double atan2(double arg1, double arg2) {
/* 42 */     if (arg1 + arg2 == arg1)
/* 43 */       return (arg1 >= 0.0D) ? 1.5707963267948966D : -1.5707963267948966D; 
/* 44 */     arg1 = atan(arg1 / arg2);
/* 45 */     return (arg2 < 0.0D) ? ((arg1 <= 0.0D) ? (arg1 + Math.PI) : (arg1 - Math.PI)) : arg1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\TrigMath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */