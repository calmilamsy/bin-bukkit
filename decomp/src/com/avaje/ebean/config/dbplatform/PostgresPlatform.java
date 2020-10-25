/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import com.avaje.ebean.config.GlobalProperties;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PostgresPlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public PostgresPlatform() {
/* 40 */     this.name = "postgres";
/* 41 */     this.selectCountWithAlias = true;
/* 42 */     this.blobDbType = -4;
/* 43 */     this.clobDbType = 12;
/*    */     
/* 45 */     this.dbEncrypt = new PostgresDbEncrypt();
/*    */     
/* 47 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 48 */     this.dbIdentity.setIdType(IdType.SEQUENCE);
/* 49 */     this.dbIdentity.setSupportsSequence(true);
/*    */     
/* 51 */     String colAlias = GlobalProperties.get("ebean.columnAliasPrefix", null);
/* 52 */     if (colAlias == null)
/*    */     {
/* 54 */       GlobalProperties.put("ebean.columnAliasPrefix", "as c");
/*    */     }
/*    */     
/* 57 */     this.openQuote = "\"";
/* 58 */     this.closeQuote = "\"";
/*    */ 
/*    */ 
/*    */     
/* 62 */     this.dbTypeMap.put(4, new DbType("integer", false));
/* 63 */     this.dbTypeMap.put(8, new DbType("float"));
/* 64 */     this.dbTypeMap.put(-6, new DbType("smallint"));
/* 65 */     this.dbTypeMap.put(3, new DbType("decimal", 38));
/*    */     
/* 67 */     this.dbTypeMap.put(-2, new DbType("bytea", false));
/* 68 */     this.dbTypeMap.put(-3, new DbType("bytea", false));
/*    */     
/* 70 */     this.dbTypeMap.put(2004, new DbType("bytea", false));
/* 71 */     this.dbTypeMap.put(2005, new DbType("text"));
/* 72 */     this.dbTypeMap.put(-4, new DbType("bytea", false));
/* 73 */     this.dbTypeMap.put(-1, new DbType("text"));
/*    */     
/* 75 */     this.dbDdlSyntax.setDropTableCascade("cascade");
/* 76 */     this.dbDdlSyntax.setDropIfExists("if exists");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 87 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return new PostgresSequenceIdGenerator(be, ds, seqName, batchSize); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\PostgresPlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */