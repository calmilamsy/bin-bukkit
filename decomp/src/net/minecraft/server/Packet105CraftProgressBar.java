/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet105CraftProgressBar
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   
/*    */   public Packet105CraftProgressBar() {}
/*    */   
/*    */   public Packet105CraftProgressBar(int paramInt1, int paramInt2, int paramInt3) {
/* 16 */     this.a = paramInt1;
/* 17 */     this.b = paramInt2;
/* 18 */     this.c = paramInt3;
/*    */   }
/*    */ 
/*    */   
/* 22 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 26 */     this.a = paramDataInputStream.readByte();
/* 27 */     this.b = paramDataInputStream.readShort();
/* 28 */     this.c = paramDataInputStream.readShort();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 32 */     paramDataOutputStream.writeByte(this.a);
/* 33 */     paramDataOutputStream.writeShort(this.b);
/* 34 */     paramDataOutputStream.writeShort(this.c);
/*    */   }
/*    */ 
/*    */   
/* 38 */   public int a() { return 5; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet105CraftProgressBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */