/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet18ArmAnimation
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   
/*    */   public Packet18ArmAnimation() {}
/*    */   
/*    */   public Packet18ArmAnimation(Entity paramEntity, int paramInt) {
/* 20 */     this.a = paramEntity.id;
/* 21 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 25 */     this.a = paramDataInputStream.readInt();
/* 26 */     this.b = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 30 */     paramDataOutputStream.writeInt(this.a);
/* 31 */     paramDataOutputStream.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/* 35 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public int a() { return 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet18ArmAnimation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */