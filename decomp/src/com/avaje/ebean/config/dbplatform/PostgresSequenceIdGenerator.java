/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PostgresSequenceIdGenerator
/*    */   extends SequenceIdGenerator
/*    */ {
/*    */   private final String baseSql;
/*    */   
/*    */   public PostgresSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 18 */     super(be, ds, seqName, batchSize);
/* 19 */     this.baseSql = "select nextval('" + seqName + "'), s.generate_series from (" + "select generate_series from generate_series(1,";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getSql(int batchSize) { return this.baseSql + batchSize + ") ) as s"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\PostgresSequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */