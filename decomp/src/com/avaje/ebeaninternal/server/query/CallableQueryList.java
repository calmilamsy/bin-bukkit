/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.Transaction;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import java.util.List;
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
/*    */ public class CallableQueryList<T>
/*    */   extends CallableQuery<T>
/*    */   implements Callable<List<T>>
/*    */ {
/* 38 */   public CallableQueryList(SpiEbeanServer server, Query<T> query, Transaction t) { super(server, query, t); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public List<T> call() throws Exception { return this.server.findList(this.query, this.t); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CallableQueryList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */