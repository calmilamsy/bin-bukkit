/*    */ package com.avaje.ebeaninternal.server.lucene;
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
/*    */ public class LIndexSync
/*    */ {
/*    */   private final String masterHost;
/*    */   private final LIndex index;
/*    */   
/*    */   public LIndexSync(LIndex index, String masterHost) {
/* 30 */     this.index = index;
/* 31 */     this.masterHost = masterHost;
/*    */   }
/*    */ 
/*    */   
/* 35 */   public String getMasterHost() { return this.masterHost; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public LIndex getIndex() { return this.index; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexSync.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */