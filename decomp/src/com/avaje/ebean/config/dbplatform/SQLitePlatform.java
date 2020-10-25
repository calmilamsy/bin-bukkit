/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import com.avaje.ebean.BackgroundExecutor;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SQLitePlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public SQLitePlatform() {
/* 13 */     this.name = "sqlite";
/*    */     
/* 15 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/* 16 */     this.dbIdentity.setSupportsGetGeneratedKeys(false);
/* 17 */     this.dbIdentity.setSelectLastInsertedIdTemplate("select last_insert_rowid()");
/* 18 */     this.openQuote = "\"";
/* 19 */     this.closeQuote = "\"";
/*    */     
/* 21 */     this.booleanDbType = 4;
/*    */     
/* 23 */     this.dbTypeMap.put(-7, new DbType("int default 0"));
/* 24 */     this.dbTypeMap.put(16, new DbType("int default 0"));
/*    */     
/* 26 */     this.dbDdlSyntax.setInlinePrimaryKeyConstraint(true);
/* 27 */     this.dbDdlSyntax.setIdentity("AUTOINCREMENT");
/* 28 */     this.dbDdlSyntax.setDisableReferentialIntegrity("PRAGMA foreign_keys = OFF");
/* 29 */     this.dbDdlSyntax.setEnableReferentialIntegrity("PRAGMA foreign_keys = ON");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\SQLitePlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */