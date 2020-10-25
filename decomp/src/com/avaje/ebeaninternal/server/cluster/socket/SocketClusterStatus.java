/*    */ package com.avaje.ebeaninternal.server.cluster.socket;
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
/*    */ public class SocketClusterStatus
/*    */ {
/*    */   private final int currentGroupSize;
/*    */   private final int txnIncoming;
/*    */   private final int txtOutgoing;
/*    */   
/*    */   public SocketClusterStatus(int currentGroupSize, int txnIncoming, int txnOutgoing) {
/* 34 */     this.currentGroupSize = currentGroupSize;
/* 35 */     this.txnIncoming = txnIncoming;
/* 36 */     this.txtOutgoing = txnOutgoing;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public int getCurrentGroupSize() { return this.currentGroupSize; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public int getTxnIncoming() { return this.txnIncoming; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   public int getTxtOutgoing() { return this.txtOutgoing; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketClusterStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */