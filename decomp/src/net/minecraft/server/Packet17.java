/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet17 extends Packet {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int d;
/*    */   public int e;
/*    */   
/*    */   public Packet17() {}
/*    */   
/*    */   public Packet17(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 16 */     this.e = paramInt1;
/* 17 */     this.b = paramInt2;
/* 18 */     this.c = paramInt3;
/* 19 */     this.d = paramInt4;
/* 20 */     this.a = paramEntity.id;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readInt();
/* 25 */     this.e = paramDataInputStream.readByte();
/* 26 */     this.b = paramDataInputStream.readInt();
/* 27 */     this.c = paramDataInputStream.readByte();
/* 28 */     this.d = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 32 */     paramDataOutputStream.writeInt(this.a);
/* 33 */     paramDataOutputStream.writeByte(this.e);
/* 34 */     paramDataOutputStream.writeInt(this.b);
/* 35 */     paramDataOutputStream.writeByte(this.c);
/* 36 */     paramDataOutputStream.writeInt(this.d);
/*    */   }
/*    */ 
/*    */   
/* 40 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int a() { return 14; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet17.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */