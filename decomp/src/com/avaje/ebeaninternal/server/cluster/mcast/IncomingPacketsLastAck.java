/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IncomingPacketsLastAck
/*    */ {
/* 38 */   private HashMap<String, MessageAck> lastAckMap = new HashMap();
/*    */ 
/*    */   
/* 41 */   public String toString() { return this.lastAckMap.values().toString(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void remove(String memberHostPort) { this.lastAckMap.remove(memberHostPort); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public MessageAck getLastAck(String memberHostPort) { return (MessageAck)this.lastAckMap.get(memberHostPort); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastAck(AckResendMessages ackResendMessages) {
/* 63 */     List<Message> messages = ackResendMessages.getMessages();
/* 64 */     for (int i = 0; i < messages.size(); i++) {
/* 65 */       Message msg = (Message)messages.get(i);
/* 66 */       if (msg instanceof MessageAck) {
/* 67 */         MessageAck lastAck = (MessageAck)msg;
/* 68 */         this.lastAckMap.put(lastAck.getToHostPort(), lastAck);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\IncomingPacketsLastAck.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */