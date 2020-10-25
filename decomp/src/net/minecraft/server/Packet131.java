/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet131
/*    */   extends Packet
/*    */ {
/*    */   public short a;
/*    */   public short b;
/*    */   public byte[] c;
/*    */   
/* 13 */   public Packet131() { this.k = true; }
/*    */ 
/*    */   
/*    */   public Packet131(short paramShort1, short paramShort2, byte[] paramArrayOfByte) {
/* 17 */     this.k = true;
/* 18 */     this.a = paramShort1;
/* 19 */     this.b = paramShort2;
/* 20 */     this.c = paramArrayOfByte;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readShort();
/* 25 */     this.b = paramDataInputStream.readShort();
/* 26 */     this.c = new byte[paramDataInputStream.readByte() & 0xFF];
/* 27 */     paramDataInputStream.readFully(this.c);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 31 */     paramDataOutputStream.writeShort(this.a);
/* 32 */     paramDataOutputStream.writeShort(this.b);
/* 33 */     paramDataOutputStream.writeByte(this.c.length);
/* 34 */     paramDataOutputStream.write(this.c);
/*    */   }
/*    */ 
/*    */   
/* 38 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public int a() { return 4 + this.c.length; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet131.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */