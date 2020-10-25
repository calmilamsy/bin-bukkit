/*     */ package com.avaje.ebean.config.dbplatform;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabasePlatform
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(DatabasePlatform.class.getName());
/*     */ 
/*     */   
/*  39 */   protected String openQuote = "\"";
/*     */ 
/*     */   
/*  42 */   protected String closeQuote = "\"";
/*     */ 
/*     */   
/*  45 */   protected SqlLimiter sqlLimiter = new LimitOffsetSqlLimiter();
/*     */ 
/*     */   
/*  48 */   protected DbTypeMap dbTypeMap = new DbTypeMap();
/*     */ 
/*     */   
/*  51 */   protected DbDdlSyntax dbDdlSyntax = new DbDdlSyntax();
/*     */ 
/*     */   
/*  54 */   protected DbIdentity dbIdentity = new DbIdentity();
/*     */ 
/*     */   
/*  57 */   protected int booleanDbType = 16;
/*     */ 
/*     */   
/*  60 */   protected int blobDbType = 2004;
/*     */ 
/*     */   
/*  63 */   protected int clobDbType = 2005;
/*     */ 
/*     */   
/*     */   protected boolean treatEmptyStringsAsNull;
/*     */ 
/*     */   
/*  69 */   protected String name = "generic";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char BACK_TICK = '`';
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DbEncrypt dbEncrypt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean idInExpandedForm;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean selectCountWithAlias;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public IdGenerator createSequenceIdGenerator(BackgroundExecutor be, DataSource ds, String seqName, int batchSize) { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public DbEncrypt getDbEncrypt() { return this.dbEncrypt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void setDbEncrypt(DbEncrypt dbEncrypt) { this.dbEncrypt = dbEncrypt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public DbTypeMap getDbTypeMap() { return this.dbTypeMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public DbDdlSyntax getDbDdlSyntax() { return this.dbDdlSyntax; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public String getCloseQuote() { return this.closeQuote; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String getOpenQuote() { return this.openQuote; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public int getBooleanDbType() { return this.booleanDbType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public int getBlobDbType() { return this.blobDbType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public int getClobDbType() { return this.clobDbType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public boolean isTreatEmptyStringsAsNull() { return this.treatEmptyStringsAsNull; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public boolean isIdInExpandedForm() { return this.idInExpandedForm; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public DbIdentity getDbIdentity() { return this.dbIdentity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   public SqlLimiter getSqlLimiter() { return this.sqlLimiter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String convertQuotedIdentifiers(String dbName) {
/* 251 */     if (dbName != null && dbName.length() > 0 && 
/* 252 */       dbName.charAt(0) == '`') {
/* 253 */       if (dbName.charAt(dbName.length() - 1) == '`') {
/*     */         
/* 255 */         quotedName = getOpenQuote();
/* 256 */         quotedName = quotedName + dbName.substring(1, dbName.length() - 1);
/* 257 */         return quotedName + getCloseQuote();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 262 */       logger.log(Level.SEVERE, "Missing backquote on [" + dbName + "]");
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return dbName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public boolean isSelectCountWithAlias() { return this.selectCountWithAlias; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\DatabasePlatform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */