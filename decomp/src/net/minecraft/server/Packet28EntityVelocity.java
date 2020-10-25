/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ public class Packet28EntityVelocity
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   
/*    */   public Packet28EntityVelocity() {}
/*    */   
/* 17 */   public Packet28EntityVelocity(Entity paramEntity) { this(paramEntity.id, paramEntity.motX, paramEntity.motY, paramEntity.motZ); }
/*    */ 
/*    */   
/*    */   public Packet28EntityVelocity(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 21 */     this.a = paramInt;
/* 22 */     double d1 = 3.9D;
/* 23 */     if (paramDouble1 < -d1) paramDouble1 = -d1; 
/* 24 */     if (paramDouble2 < -d1) paramDouble2 = -d1; 
/* 25 */     if (paramDouble3 < -d1) paramDouble3 = -d1; 
/* 26 */     if (paramDouble1 > d1) paramDouble1 = d1; 
/* 27 */     if (paramDouble2 > d1) paramDouble2 = d1; 
/* 28 */     if (paramDouble3 > d1) paramDouble3 = d1; 
/* 29 */     this.b = (int)(paramDouble1 * 8000.0D);
/* 30 */     this.c = (int)(paramDouble2 * 8000.0D);
/* 31 */     this.d = (int)(paramDouble3 * 8000.0D);
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 35 */     this.a = paramDataInputStream.readInt();
/* 36 */     this.b = paramDataInputStream.readShort();
/* 37 */     this.c = paramDataInputStream.readShort();
/* 38 */     this.d = paramDataInputStream.readShort();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 42 */     paramDataOutputStream.writeInt(this.a);
/* 43 */     paramDataOutputStream.writeShort(this.b);
/* 44 */     paramDataOutputStream.writeShort(this.c);
/* 45 */     paramDataOutputStream.writeShort(this.d);
/*    */   }
/*    */ 
/*    */   
/* 49 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public int a() { return 10; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet28EntityVelocity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */