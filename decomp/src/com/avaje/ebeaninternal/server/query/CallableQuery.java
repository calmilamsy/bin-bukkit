/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
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
/*    */ public abstract class CallableQuery<T>
/*    */   extends Object
/*    */ {
/*    */   protected final Query<T> query;
/*    */   protected final SpiEbeanServer server;
/*    */   protected final Transaction t;
/*    */   
/*    */   public CallableQuery(SpiEbeanServer server, Query<T> query, Transaction t) {
/* 42 */     this.server = server;
/* 43 */     this.query = query;
/* 44 */     this.t = t;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CallableQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */