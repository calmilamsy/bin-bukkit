/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import java.util.concurrent.Callable;
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
/*    */ public class CallableQueryRowCount<T>
/*    */   extends CallableQuery<T>
/*    */   implements Callable<Integer>
/*    */ {
/* 37 */   public CallableQueryRowCount(SpiEbeanServer server, Query<T> query, Transaction t) { super(server, query, t); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public Integer call() throws Exception { return Integer.valueOf(this.server.findRowCountWithCopy(this.query, this.t)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CallableQueryRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */