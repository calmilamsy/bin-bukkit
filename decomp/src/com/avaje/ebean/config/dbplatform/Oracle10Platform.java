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
/*    */ public class Oracle10Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public Oracle10Platform() {
/* 36 */     this.name = "oracle";
/* 37 */     this.dbEncrypt = new Oracle10DbEncrypt();
/*    */     
/* 39 */     this.sqlLimiter = new RownumSqlLimiter();
/*    */ 
/*    */ 
/*    */     
/* 43 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 44 */     this.dbIdentity.setIdType(IdType.SEQUENCE);
/* 45 */     this.dbIdentity.setSupportsSequence(true);
/*    */     
/* 47 */     this.treatEmptyStringsAsNull = true;
/*    */     
/* 49 */     this.openQuote = "\"";
/* 50 */     this.closeQuote = "\"";
/*    */     
/* 52 */     this.booleanDbType = 4;
/* 53 */     this.dbTypeMap.put(16, new DbType("number(1) default 0"));
/*    */     
/* 55 */     this.dbTypeMap.put(4, new DbType("number", 10));
/* 56 */     this.dbTypeMap.put(-5, new DbType("number", 19));
/* 57 */     this.dbTypeMap.put(7, new DbType("number", 19, 4));
/* 58 */     this.dbTypeMap.put(8, new DbType("number", 19, 4));
/* 59 */     this.dbTypeMap.put(5, new DbType("number", 5));
/* 60 */     this.dbTypeMap.put(-6, new DbType("number", 3));
/* 61 */     this.dbTypeMap.put(3, new DbType("number", 38));
/*    */     
/* 63 */     this.dbTypeMap.put(12, new DbType("varchar2", 'ÿ'));
/*    */     
/* 65 */     this.dbTypeMap.put(-4, new DbType("blob"));
/* 66 */     this.dbTypeMap.put(-1, new DbType("clob"));
/* 67 */     this.dbTypeMap.put(-3, new DbType("raw", 'ÿ'));
/* 68 */     this.dbTypeMap.put(-2, new DbType("raw", 'ÿ'));
/*    */     
/* 70 */     this.dbTypeMap.put(92, new DbType("timestamp"));
/*    */ 
/*    */     
/* 73 */     this.dbDdlSyntax.setDropTableCascade("cascade constraints purge");
/* 74 */     this.dbDdlSyntax.setIdentity(null);
/* 75 */     this.dbDdlSyntax.setMaxConstraintNameLength(30);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return new OracleSequenceIdGenerator(be, ds, seqName, batchSize); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\Oracle10Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */