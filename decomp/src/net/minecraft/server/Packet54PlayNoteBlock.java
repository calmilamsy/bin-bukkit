/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet54PlayNoteBlock
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   
/*    */   public Packet54PlayNoteBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 14 */     this.a = paramInt1;
/* 15 */     this.b = paramInt2;
/* 16 */     this.c = paramInt3;
/* 17 */     this.d = paramInt4;
/* 18 */     this.e = paramInt5;
/*    */   } public int d; public int e;
/*    */   public Packet54PlayNoteBlock() {}
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 22 */     this.a = paramDataInputStream.readInt();
/* 23 */     this.b = paramDataInputStream.readShort();
/* 24 */     this.c = paramDataInputStream.readInt();
/* 25 */     this.d = paramDataInputStream.read();
/* 26 */     this.e = paramDataInputStream.read();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 30 */     paramDataOutputStream.writeInt(this.a);
/* 31 */     paramDataOutputStream.writeShort(this.b);
/* 32 */     paramDataOutputStream.writeInt(this.c);
/* 33 */     paramDataOutputStream.write(this.d);
/* 34 */     paramDataOutputStream.write(this.e);
/*    */   }
/*    */ 
/*    */   
/* 38 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public int a() { return 12; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet54PlayNoteBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */