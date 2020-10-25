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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet14BlockDig
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   public int c;
/*    */   public int face;
/*    */   public int e;
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 26 */     this.e = paramDataInputStream.read();
/* 27 */     this.a = paramDataInputStream.readInt();
/* 28 */     this.b = paramDataInputStream.read();
/* 29 */     this.c = paramDataInputStream.readInt();
/* 30 */     this.face = paramDataInputStream.read();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 34 */     paramDataOutputStream.write(this.e);
/* 35 */     paramDataOutputStream.writeInt(this.a);
/* 36 */     paramDataOutputStream.write(this.b);
/* 37 */     paramDataOutputStream.writeInt(this.c);
/* 38 */     paramDataOutputStream.write(this.face);
/*    */   }
/*    */ 
/*    */   
/* 42 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public int a() { return 11; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet14BlockDig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */