/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingObjectPosition
/*    */ {
/*    */   public EnumMovingObjectType type;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int face;
/*    */   public Vec3D f;
/*    */   public Entity entity;
/*    */   
/*    */   public MovingObjectPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Vec3D paramVec3D) {
/* 17 */     this.type = EnumMovingObjectType.TILE;
/* 18 */     this.b = paramInt1;
/* 19 */     this.c = paramInt2;
/* 20 */     this.d = paramInt3;
/* 21 */     this.face = paramInt4;
/* 22 */     this.f = Vec3D.create(paramVec3D.a, paramVec3D.b, paramVec3D.c);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity paramEntity) {
/* 26 */     this.type = EnumMovingObjectType.ENTITY;
/* 27 */     this.entity = paramEntity;
/* 28 */     this.f = Vec3D.create(paramEntity.locX, paramEntity.locY, paramEntity.locZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MovingObjectPosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */