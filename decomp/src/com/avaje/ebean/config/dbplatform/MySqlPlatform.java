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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MySqlPlatform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public MySqlPlatform() {
/* 45 */     this.name = "mysql";
/* 46 */     this.selectCountWithAlias = true;
/* 47 */     this.dbEncrypt = new MySqlDbEncrypt();
/*    */     
/* 49 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/* 50 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 51 */     this.dbIdentity.setSupportsIdentity(true);
/* 52 */     this.dbIdentity.setSupportsSequence(false);
/*    */     
/* 54 */     this.openQuote = "`";
/* 55 */     this.closeQuote = "`";
/*    */     
/* 57 */     this.booleanDbType = -7;
/*    */     
/* 59 */     this.dbTypeMap.put(-7, new DbType("tinyint(1) default 0"));
/* 60 */     this.dbTypeMap.put(16, new DbType("tinyint(1) default 0"));
/* 61 */     this.dbTypeMap.put(93, new DbType("datetime"));
/* 62 */     this.dbTypeMap.put(2005, new MySqlClob());
/* 63 */     this.dbTypeMap.put(2004, new MySqlBlob());
/* 64 */     this.dbTypeMap.put(-2, new DbType("binary", 'ÿ'));
/* 65 */     this.dbTypeMap.put(-3, new DbType("varbinary", 'ÿ'));
/*    */     
/* 67 */     this.dbDdlSyntax.setDisableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=0");
/* 68 */     this.dbDdlSyntax.setEnableReferentialIntegrity("SET FOREIGN_KEY_CHECKS=1");
/* 69 */     this.dbDdlSyntax.setForeignKeySuffix("on delete restrict on update restrict");
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
/* 80 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return null; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\MySqlPlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */