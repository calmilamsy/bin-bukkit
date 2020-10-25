/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet22Collect
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   
/*    */   public Packet22Collect(int paramInt1, int paramInt2) {
/* 12 */     this.a = paramInt1;
/* 13 */     this.b = paramInt2;
/*    */   } public int b;
/*    */   public Packet22Collect() {}
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 17 */     this.a = paramDataInputStream.readInt();
/* 18 */     this.b = paramDataInputStream.readInt();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 22 */     paramDataOutputStream.writeInt(this.a);
/* 23 */     paramDataOutputStream.writeInt(this.b);
/*    */   }
/*    */ 
/*    */   
/* 27 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public int a() { return 8; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet22Collect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */