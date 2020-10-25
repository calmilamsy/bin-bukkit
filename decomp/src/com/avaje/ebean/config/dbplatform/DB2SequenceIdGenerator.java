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
/*    */ public class DB2SequenceIdGenerator
/*    */   extends SequenceIdGenerator
/*    */ {
/*    */   private final String baseSql;
/*    */   private final String unionBaseSql;
/*    */   
/*    */   public DB2SequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) {
/* 19 */     super(be, ds, seqName, batchSize);
/* 20 */     this.baseSql = "select nextval for " + seqName;
/* 21 */     this.unionBaseSql = " union " + this.baseSql;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSql(int batchSize) {
/* 26 */     StringBuilder sb = new StringBuilder();
/* 27 */     sb.append(this.baseSql);
/* 28 */     for (int i = 1; i < batchSize; i++) {
/* 29 */       sb.append(this.unionBaseSql);
/*    */     }
/* 31 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\DB2SequenceIdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */