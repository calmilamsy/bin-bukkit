/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.meta.MetaQueryStatistic;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import com.avaje.ebeaninternal.server.type.RsetDataReader;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ public class CQueryPlan
/*     */ {
/*     */   private final boolean autofetchTuned;
/*     */   private final int hash;
/*     */   private final boolean rawSql;
/*     */   private final boolean rowNumberIncluded;
/*     */   private final String sql;
/*     */   private final String logWhereSql;
/*     */   private final SqlTree sqlTree;
/*     */   private final BeanProperty[] encryptedProps;
/*     */   private CQueryStats queryStats;
/*     */   
/*     */   public CQueryPlan(OrmQueryRequest<?> request, SqlLimitResponse sqlRes, SqlTree sqlTree, boolean rawSql, String logWhereSql, String luceneQueryDescription) {
/*  54 */     this.queryStats = new CQueryStats();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.hash = request.getQueryPlanHash();
/*  63 */     this.autofetchTuned = request.getQuery().isAutofetchTuned();
/*  64 */     if (sqlRes != null) {
/*  65 */       this.sql = sqlRes.getSql();
/*  66 */       this.rowNumberIncluded = sqlRes.isIncludesRowNumberColumn();
/*     */     } else {
/*  68 */       this.sql = luceneQueryDescription;
/*  69 */       this.rowNumberIncluded = false;
/*     */     } 
/*  71 */     this.sqlTree = sqlTree;
/*  72 */     this.rawSql = rawSql;
/*  73 */     this.logWhereSql = logWhereSql;
/*  74 */     this.encryptedProps = sqlTree.getEncryptedProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryPlan(String sql, SqlTree sqlTree, boolean rawSql, boolean rowNumberIncluded, String logWhereSql) {
/*     */     this.queryStats = new CQueryStats();
/*  83 */     this.hash = 0;
/*  84 */     this.autofetchTuned = false;
/*  85 */     this.sql = sql;
/*  86 */     this.sqlTree = sqlTree;
/*  87 */     this.rawSql = rawSql;
/*  88 */     this.rowNumberIncluded = rowNumberIncluded;
/*  89 */     this.logWhereSql = logWhereSql;
/*  90 */     this.encryptedProps = sqlTree.getEncryptedProps();
/*     */   }
/*     */ 
/*     */   
/*  94 */   public boolean isLucene() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public DataReader createDataReader(ResultSet rset) { return new RsetDataReader(rset); }
/*     */ 
/*     */   
/*     */   public void bindEncryptedProperties(DataBind dataBind) throws SQLException {
/* 103 */     if (this.encryptedProps != null) {
/* 104 */       for (int i = 0; i < this.encryptedProps.length; i++) {
/* 105 */         String key = this.encryptedProps[i].getEncryptKey().getStringValue();
/* 106 */         dataBind.setString(key);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 112 */   public boolean isAutofetchTuned() { return this.autofetchTuned; }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public int getHash() { return this.hash; }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public SqlTree getSqlTree() { return this.sqlTree; }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public boolean isRawSql() { return this.rawSql; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean isRowNumberIncluded() { return this.rowNumberIncluded; }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public String getLogWhereSql() { return this.logWhereSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void resetStatistics() { this.queryStats = new CQueryStats(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void executionTime(int loadedBeanCount, int timeMicros) { this.queryStats = this.queryStats.add(loadedBeanCount, timeMicros); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public CQueryStats getQueryStats() { return this.queryStats; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public long getLastQueryTime() { return this.queryStats.getLastQueryTime(); }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public MetaQueryStatistic createMetaQueryStatistic(String beanName) { return this.queryStats.createMetaQueryStatistic(beanName, this); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryPlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */