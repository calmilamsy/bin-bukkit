/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ public class Packet9Respawn
/*    */   extends Packet
/*    */ {
/*    */   public byte a;
/*    */   
/*    */   public Packet9Respawn() {}
/*    */   
/* 14 */   public Packet9Respawn(byte paramByte) { this.a = paramByte; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public void a(DataInputStream paramDataInputStream) { this.a = paramDataInputStream.readByte(); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void a(DataOutputStream paramDataOutputStream) { paramDataOutputStream.writeByte(this.a); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public int a() { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet9Respawn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */