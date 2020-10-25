/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet106Transaction
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public short b;
/*    */   public boolean c;
/*    */   
/*    */   public Packet106Transaction() {}
/*    */   
/*    */   public Packet106Transaction(int paramInt, short paramShort, boolean paramBoolean) {
/* 16 */     this.a = paramInt;
/* 17 */     this.b = paramShort;
/* 18 */     this.c = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/* 22 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 26 */     this.a = paramDataInputStream.readByte();
/* 27 */     this.b = paramDataInputStream.readShort();
/* 28 */     this.c = (paramDataInputStream.readByte() != 0);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 32 */     paramDataOutputStream.writeByte(this.a);
/* 33 */     paramDataOutputStream.writeShort(this.b);
/* 34 */     paramDataOutputStream.writeByte(this.c ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/* 38 */   public int a() { return 4; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet106Transaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */