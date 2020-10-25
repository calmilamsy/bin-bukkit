/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class Packet2Handshake
/*    */   extends Packet
/*    */ {
/*    */   public String a;
/*    */   
/*    */   public Packet2Handshake() {}
/*    */   
/* 13 */   public Packet2Handshake(String paramString) { this.a = paramString; }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public void a(DataInputStream paramDataInputStream) { this.a = a(paramDataInputStream, 32); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void a(DataOutputStream paramDataOutputStream) { a(this.a, paramDataOutputStream); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public int a() { return 4 + this.a.length() + 4; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet2Handshake.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */