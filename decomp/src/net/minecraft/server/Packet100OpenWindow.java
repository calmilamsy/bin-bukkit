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
/*    */ public class Packet100OpenWindow
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public String c;
/*    */   public int d;
/*    */   
/*    */   public Packet100OpenWindow() {}
/*    */   
/*    */   public Packet100OpenWindow(int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 22 */     this.a = paramInt1;
/* 23 */     this.b = paramInt2;
/* 24 */     this.c = paramString;
/* 25 */     this.d = paramInt3;
/*    */   }
/*    */ 
/*    */   
/* 29 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 33 */     this.a = paramDataInputStream.readByte();
/* 34 */     this.b = paramDataInputStream.readByte();
/* 35 */     this.c = paramDataInputStream.readUTF();
/* 36 */     this.d = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 40 */     paramDataOutputStream.writeByte(this.a);
/* 41 */     paramDataOutputStream.writeByte(this.b);
/* 42 */     paramDataOutputStream.writeUTF(this.c);
/* 43 */     paramDataOutputStream.writeByte(this.d);
/*    */   }
/*    */ 
/*    */   
/* 47 */   public int a() { return 3 + this.c.length(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet100OpenWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */