/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Packet3Chat
/*    */   extends Packet
/*    */ {
/*    */   public String message;
/*    */   
/*    */   public Packet3Chat() {}
/*    */   
/* 21 */   public Packet3Chat(String s) { this.message = s; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void a(DataInputStream datainputstream) throws IOException { this.message = a(datainputstream, 119); }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void a(DataOutputStream dataoutputstream) throws IOException { a(this.message, dataoutputstream); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public void a(NetHandler nethandler) { nethandler.a(this); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public int a() { return this.message.length(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\Packet3Chat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */