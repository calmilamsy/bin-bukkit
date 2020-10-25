/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet50PreChunk
/*    */   extends Packet {
/*    */   public int a;
/*    */   
/* 10 */   public Packet50PreChunk() { this.k = false; }
/*    */   public int b; public boolean c;
/*    */   
/*    */   public Packet50PreChunk(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 14 */     this.k = false;
/* 15 */     this.a = paramInt1;
/* 16 */     this.b = paramInt2;
/* 17 */     this.c = paramBoolean;
/*    */   }
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 21 */     this.a = paramDataInputStream.readInt();
/* 22 */     this.b = paramDataInputStream.readInt();
/* 23 */     this.c = (paramDataInputStream.read() != 0);
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 27 */     paramDataOutputStream.writeInt(this.a);
/* 28 */     paramDataOutputStream.writeInt(this.b);
/* 29 */     paramDataOutputStream.write(this.c ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/* 33 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public int a() { return 9; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet50PreChunk.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */