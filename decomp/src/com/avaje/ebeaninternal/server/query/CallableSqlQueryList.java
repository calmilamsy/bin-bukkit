/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.SqlQuery;
/*    */ import com.avaje.ebean.SqlRow;
/*    */ import com.avaje.ebean.Transaction;
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
/*    */ public class CallableSqlQueryList
/*    */   extends Object
/*    */   implements Callable<List<SqlRow>>
/*    */ {
/*    */   private final SqlQuery query;
/*    */   private final EbeanServer server;
/*    */   private final Transaction t;
/*    */   
/*    */   public CallableSqlQueryList(EbeanServer server, SqlQuery query, Transaction t) {
/* 42 */     this.server = server;
/* 43 */     this.query = query;
/* 44 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public List<SqlRow> call() throws Exception { return this.server.findList(this.query, this.t); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CallableSqlQueryList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */