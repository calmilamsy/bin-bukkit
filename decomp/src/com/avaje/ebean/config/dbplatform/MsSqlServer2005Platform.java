/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ public class MsSqlServer2005Platform
/*    */   extends DatabasePlatform
/*    */ {
/*    */   public MsSqlServer2005Platform() {
/* 41 */     this.name = "mssqlserver2005";
/* 42 */     this.dbIdentity.setIdType(IdType.IDENTITY);
/* 43 */     this.dbIdentity.setSupportsGetGeneratedKeys(true);
/* 44 */     this.dbIdentity.setSupportsIdentity(true);
/*    */     
/* 46 */     this.openQuote = "[";
/* 47 */     this.closeQuote = "]";
/*    */ 
/*    */     
/* 50 */     this.dbTypeMap.put(16, new DbType("bit default 0"));
/*    */     
/* 52 */     this.dbTypeMap.put(-5, new DbType("numeric", 19));
/* 53 */     this.dbTypeMap.put(7, new DbType("float(16)"));
/* 54 */     this.dbTypeMap.put(8, new DbType("float(32)"));
/* 55 */     this.dbTypeMap.put(-6, new DbType("smallint"));
/* 56 */     this.dbTypeMap.put(3, new DbType("numeric", 28));
/*    */     
/* 58 */     this.dbTypeMap.put(2004, new DbType("image"));
/* 59 */     this.dbTypeMap.put(2005, new DbType("text"));
/* 60 */     this.dbTypeMap.put(-4, new DbType("image"));
/* 61 */     this.dbTypeMap.put(-1, new DbType("text"));
/*    */     
/* 63 */     this.dbTypeMap.put(91, new DbType("datetime"));
/* 64 */     this.dbTypeMap.put(92, new DbType("datetime"));
/* 65 */     this.dbTypeMap.put(93, new DbType("datetime"));
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\MsSqlServer2005Platform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */