/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet38EntityStatus extends Packet {
/*    */   public int a;
/*    */   public byte b;
/*    */   
/*    */   public Packet38EntityStatus() {}
/*    */   
/*    */   public Packet38EntityStatus(int paramInt, byte paramByte) {
/* 13 */     this.a = paramInt;
/* 14 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 18 */     this.a = paramDataInputStream.readInt();
/* 19 */     this.b = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 23 */     paramDataOutputStream.writeInt(this.a);
/* 24 */     paramDataOutputStream.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/* 28 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public int a() { return 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet38EntityStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */