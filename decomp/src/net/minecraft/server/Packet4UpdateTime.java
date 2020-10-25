/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet4UpdateTime
/*    */   extends Packet {
/*    */   public long a;
/*    */   
/*    */   public Packet4UpdateTime() {}
/*    */   
/* 12 */   public Packet4UpdateTime(long paramLong) { this.a = paramLong; }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public void a(DataInputStream paramDataInputStream) { this.a = paramDataInputStream.readLong(); }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeLong(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public int a() { return 8; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet4UpdateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */