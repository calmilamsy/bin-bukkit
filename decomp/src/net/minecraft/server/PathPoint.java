/*    */ package net.minecraft.server;public class PathPoint {
/*    */   public final int a;
/*    */   public final int b;
/*    */   public final int c;
/*    */   private final int j;
/*    */   int d;
/*    */   
/*    */   public PathPoint(int paramInt1, int paramInt2, int paramInt3) {
/*  9 */     this.d = -1;
/*    */ 
/*    */     
/* 12 */     this.i = false;
/*    */ 
/*    */     
/* 15 */     this.a = paramInt1;
/* 16 */     this.b = paramInt2;
/* 17 */     this.c = paramInt3;
/*    */     
/* 19 */     this.j = a(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   float e; float f; float g; PathPoint h; public boolean i;
/*    */   
/* 23 */   public static int a(int paramInt1, int paramInt2, int paramInt3) { return paramInt2 & 0xFF | (paramInt1 & 0x7FFF) << 8 | (paramInt3 & 0x7FFF) << 24 | ((paramInt1 < 0) ? Integer.MIN_VALUE : 0) | ((paramInt3 < 0) ? 32768 : 0); }
/*    */ 
/*    */   
/*    */   public float a(PathPoint paramPathPoint) {
/* 27 */     float f1 = (paramPathPoint.a - this.a);
/* 28 */     float f2 = (paramPathPoint.b - this.b);
/* 29 */     float f3 = (paramPathPoint.c - this.c);
/* 30 */     return MathHelper.c(f1 * f1 + f2 * f2 + f3 * f3);
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 34 */     if (paramObject instanceof PathPoint) {
/* 35 */       PathPoint pathPoint = (PathPoint)paramObject;
/* 36 */       return (this.j == pathPoint.j && this.a == pathPoint.a && this.b == pathPoint.b && this.c == pathPoint.c);
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public int hashCode() { return this.j; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public boolean a() { return (this.d >= 0); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String toString() { return this.a + ", " + this.b + ", " + this.c; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PathPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */