/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet200Statistic
/*    */   extends Packet
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   
/*    */   public Packet200Statistic() {}
/*    */   
/*    */   public Packet200Statistic(int paramInt1, int paramInt2) {
/* 15 */     this.a = paramInt1;
/* 16 */     this.b = paramInt2;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */   
/*    */   public void a(DataInputStream paramDataInputStream) {
/* 24 */     this.a = paramDataInputStream.readInt();
/* 25 */     this.b = paramDataInputStream.readByte();
/*    */   }
/*    */   
/*    */   public void a(DataOutputStream paramDataOutputStream) {
/* 29 */     paramDataOutputStream.writeInt(this.a);
/* 30 */     paramDataOutputStream.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/* 34 */   public int a() { return 6; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet200Statistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */