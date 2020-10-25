/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet61 extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   
/*    */   public Packet61() {}
/*    */   
/*    */   public Packet61(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 16 */     this.a = paramInt1;
/* 17 */     this.c = paramInt2;
/* 18 */     this.d = paramInt3;
/* 19 */     this.e = paramInt4;
/* 20 */     this.b = paramInt5;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readInt();
/* 25 */     this.c = paramDataInputStream.readInt();
/* 26 */     this.d = paramDataInputStream.readByte();
/* 27 */     this.e = paramDataInputStream.readInt();
/* 28 */     this.b = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 32 */     paramDataOutputStream.writeInt(this.a);
/* 33 */     paramDataOutputStream.writeInt(this.c);
/* 34 */     paramDataOutputStream.writeByte(this.d);
/* 35 */     paramDataOutputStream.writeInt(this.e);
/* 36 */     paramDataOutputStream.writeInt(this.b);
/*    */   }
/*    */ 
/*    */   
/* 40 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int a() { return 20; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet61.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */