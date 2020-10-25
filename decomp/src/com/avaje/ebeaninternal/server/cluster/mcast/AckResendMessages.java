/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AckResendMessages
/*    */ {
/* 32 */   ArrayList<Message> messages = new ArrayList();
/*    */ 
/*    */   
/* 35 */   public String toString() { return this.messages.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public int size() { return this.messages.size(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public void add(MessageAck ack) { this.messages.add(ack); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public void add(MessageResend resend) { this.messages.add(resend); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public List<Message> getMessages() { return this.messages; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\AckResendMessages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */