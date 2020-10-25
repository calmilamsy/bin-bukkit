/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.FutureIds;
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import java.util.List;
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
/*    */ public class QueryFutureIds<T>
/*    */   extends BaseFuture<List<Object>>
/*    */   implements FutureIds<T>
/*    */ {
/*    */   private final SpiQuery<T> query;
/*    */   
/*    */   public QueryFutureIds(SpiQuery<T> query, FutureTask<List<Object>> futureTask) {
/* 37 */     super(futureTask);
/* 38 */     this.query = query;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public Query<T> getQuery() { return this.query; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public List<Object> getPartialIds() { return this.query.getIdList(); }
/*    */ 
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 50 */     this.query.cancel();
/* 51 */     return super.cancel(mayInterruptIfRunning);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\QueryFutureIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */