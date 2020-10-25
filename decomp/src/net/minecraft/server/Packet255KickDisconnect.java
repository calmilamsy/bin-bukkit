/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ 
/*    */ public class Packet255KickDisconnect
/*    */   extends Packet
/*    */ {
/*    */   public String a;
/*    */   
/*    */   public Packet255KickDisconnect() {}
/*    */   
/* 14 */   public Packet255KickDisconnect(String paramString) { this.a = paramString; }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public void a(DataInputStream paramDataInputStream) { this.a = a(paramDataInputStream, 100); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public void a(DataOutputStream paramDataOutputStream) { a(this.a, paramDataOutputStream); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void a(NetHandler paramNetHandler) { paramNetHandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public int a() { return this.a.length(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet255KickDisconnect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */