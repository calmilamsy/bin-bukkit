/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.FutureRowCount;
/*    */ import com.avaje.ebean.Query;
/*    */ import java.util.concurrent.FutureTask;
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
/*    */ public class QueryFutureRowCount<T>
/*    */   extends BaseFuture<Integer>
/*    */   implements FutureRowCount<T>
/*    */ {
/*    */   private final Query<T> query;
/*    */   
/*    */   public QueryFutureRowCount(Query<T> query, FutureTask<Integer> futureTask) {
/* 35 */     super(futureTask);
/* 36 */     this.query = query;
/*    */   }
/*    */ 
/*    */   
/* 40 */   public Query<T> getQuery() { return this.query; }
/*    */ 
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 44 */     this.query.cancel();
/* 45 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\QueryFutureRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */