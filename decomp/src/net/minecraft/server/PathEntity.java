/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathEntity
/*    */ {
/*    */   private final PathPoint[] b;
/*    */   public final int a;
/*    */   private int c;
/*    */   
/*    */   public PathEntity(PathPoint[] paramArrayOfPathPoint) {
/* 12 */     this.b = paramArrayOfPathPoint;
/* 13 */     this.a = paramArrayOfPathPoint.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void a() { this.c++; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public boolean b() { return (this.c >= this.b.length); }
/*    */ 
/*    */   
/*    */   public PathPoint c() {
/* 29 */     if (this.a > 0) {
/* 30 */       return this.b[this.a - 1];
/*    */     }
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Vec3D a(Entity paramEntity) {
/* 40 */     double d1 = (this.b[this.c]).a + (int)(paramEntity.length + 1.0F) * 0.5D;
/* 41 */     double d2 = (this.b[this.c]).b;
/* 42 */     double d3 = (this.b[this.c]).c + (int)(paramEntity.length + 1.0F) * 0.5D;
/* 43 */     return Vec3D.create(d1, d2, d3);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\PathEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */