/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.QueryIterator;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ import java.sql.SQLException;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ class CQueryIteratorSimple<T>
/*    */   extends Object
/*    */   implements QueryIterator<T>
/*    */ {
/*    */   private final CQuery<T> cquery;
/*    */   private final OrmQueryRequest<T> request;
/*    */   
/*    */   CQueryIteratorSimple(CQuery<T> cquery, OrmQueryRequest<T> request) {
/* 43 */     this.cquery = cquery;
/* 44 */     this.request = request;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/*    */     try {
/* 49 */       return this.cquery.hasNextBean(true);
/* 50 */     } catch (SQLException e) {
/* 51 */       throw this.cquery.createPersistenceException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 56 */   public T next() { return (T)this.cquery.getLoadedBean(); }
/*    */ 
/*    */   
/*    */   public void close() {
/* 60 */     this.cquery.updateExecutionStatistics();
/* 61 */     this.cquery.close();
/* 62 */     this.request.endTransIfRequired();
/*    */   }
/*    */ 
/*    */   
/* 66 */   public void remove() { throw new PersistenceException("Remove not allowed"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryIteratorSimple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */