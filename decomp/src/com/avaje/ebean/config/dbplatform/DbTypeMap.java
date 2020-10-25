/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class DbTypeMap
/*    */ {
/*    */   private final Map<Integer, DbType> typeMap;
/*    */   
/*    */   public DbTypeMap() {
/* 12 */     this.typeMap = new HashMap();
/*    */ 
/*    */     
/* 15 */     loadDefaults();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void loadDefaults() {
/* 24 */     put(16, new DbType("boolean"));
/* 25 */     put(-7, new DbType("bit"));
/*    */     
/* 27 */     put(4, new DbType("integer"));
/* 28 */     put(-5, new DbType("bigint"));
/* 29 */     put(7, new DbType("float"));
/* 30 */     put(8, new DbType("double"));
/* 31 */     put(5, new DbType("smallint"));
/* 32 */     put(-6, new DbType("tinyint"));
/* 33 */     put(3, new DbType("decimal", 38));
/*    */     
/* 35 */     put(12, new DbType("varchar", 'ÿ'));
/* 36 */     put(1, new DbType("char", true));
/*    */     
/* 38 */     put(2004, new DbType("blob"));
/* 39 */     put(2005, new DbType("clob"));
/* 40 */     put(-4, new DbType("longvarbinary"));
/* 41 */     put(-1, new DbType("lonvarchar"));
/* 42 */     put(-3, new DbType("varbinary", 'ÿ'));
/* 43 */     put(-2, new DbType("binary", 'ÿ'));
/*    */     
/* 45 */     put(91, new DbType("date"));
/* 46 */     put(92, new DbType("time"));
/* 47 */     put(93, new DbType("timestamp"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void put(int jdbcType, DbType dbType) { this.typeMap.put(Integer.valueOf(jdbcType), dbType); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DbType get(int jdbcType) {
/* 63 */     DbType dbType = (DbType)this.typeMap.get(Integer.valueOf(jdbcType));
/* 64 */     if (dbType == null) {
/* 65 */       String m = "No DB type for JDBC type " + jdbcType;
/* 66 */       throw new RuntimeException(m);
/*    */     } 
/*    */     
/* 69 */     return dbType;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\dbplatform\DbTypeMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */