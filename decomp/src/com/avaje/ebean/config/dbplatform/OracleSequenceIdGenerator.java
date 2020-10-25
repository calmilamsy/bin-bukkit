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
/*    */ public class OracleSequenceIdGenerator
/*    */   extends SequenceIdGenerator
/*    */ {
/*    */   private final String baseSql;
/*    */   
/*    */   public OracleSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 18 */     super(be, ds, seqName, batchSize);
/* 19 */     this.baseSql = "select " + seqName + ".nextval, a from (select level as a FROM dual CONNECT BY level <= ";
/*    */   }
/*    */ 
/*    */   
/* 23 */   public String getSql(int batchSize) { return this.baseSql + batchSize + ")"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\OracleSequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */