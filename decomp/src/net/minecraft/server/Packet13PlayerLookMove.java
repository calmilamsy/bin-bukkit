/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet13PlayerLookMove extends Packet10Flying {
/*    */   public Packet13PlayerLookMove() {
/*  8 */     this.hasLook = true;
/*  9 */     this.h = true;
/*    */   }
/*    */   
/*    */   public Packet13PlayerLookMove(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, float paramFloat1, float paramFloat2, boolean paramBoolean) {
/* 13 */     this.x = paramDouble1;
/* 14 */     this.y = paramDouble2;
/* 15 */     this.stance = paramDouble3;
/* 16 */     this.z = paramDouble4;
/* 17 */     this.yaw = paramFloat1;
/* 18 */     this.pitch = paramFloat2;
/* 19 */     this.g = paramBoolean;
/* 20 */     this.hasLook = true;
/* 21 */     this.h = true;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 25 */     this.x = paramDataInputStream.readDouble();
/* 26 */     this.y = paramDataInputStream.readDouble();
/* 27 */     this.stance = paramDataInputStream.readDouble();
/* 28 */     this.z = paramDataInputStream.readDouble();
/* 29 */     this.yaw = paramDataInputStream.readFloat();
/* 30 */     this.pitch = paramDataInputStream.readFloat();
/* 31 */     super.a(paramDataInputStream);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 35 */     paramDataOutputStream.writeDouble(this.x);
/* 36 */     paramDataOutputStream.writeDouble(this.y);
/* 37 */     paramDataOutputStream.writeDouble(this.stance);
/* 38 */     paramDataOutputStream.writeDouble(this.z);
/* 39 */     paramDataOutputStream.writeFloat(this.yaw);
/* 40 */     paramDataOutputStream.writeFloat(this.pitch);
/* 41 */     super.a(paramDataOutputStream);
/*    */   }
/*    */ 
/*    */   
/* 45 */   public int a() { return 41; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet13PlayerLookMove.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */