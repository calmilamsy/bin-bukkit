/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
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
/*     */ public class CQueryBuilderRawSql
/*     */   implements Constants
/*     */ {
/*     */   private final SqlLimiter sqlLimiter;
/*     */   
/*  37 */   CQueryBuilderRawSql(SqlLimiter sqlLimiter) { this.sqlLimiter = sqlLimiter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SqlLimitResponse buildSql(OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql rsql) {
/*  45 */     if (!rsql.isParsed()) {
/*  46 */       String sql = rsql.getUnparsedSql();
/*  47 */       BindParams bindParams = request.getQuery().getBindParams();
/*  48 */       if (bindParams != null && bindParams.requiresNamedParamsPrepare())
/*     */       {
/*  50 */         sql = BindParamsParser.parse(bindParams, sql);
/*     */       }
/*     */       
/*  53 */       return new SqlLimitResponse(sql, false);
/*     */     } 
/*     */     
/*  56 */     String orderBy = getOrderBy(predicates, rsql);
/*     */ 
/*     */     
/*  59 */     String sql = buildMainQuery(orderBy, request, predicates, rsql);
/*     */     
/*  61 */     SpiQuery<?> query = request.getQuery();
/*  62 */     if (query.hasMaxRowsOrFirstRow() && this.sqlLimiter != null)
/*     */     {
/*  64 */       return this.sqlLimiter.limit(new OrmQueryLimitRequest(sql, orderBy, query));
/*     */     }
/*     */ 
/*     */     
/*  68 */     sql = "select " + sql;
/*  69 */     return new SqlLimitResponse(sql, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildMainQuery(String orderBy, OrmQueryRequest<?> request, CQueryPredicates predicates, RawSql.Sql sql) {
/*  75 */     StringBuilder sb = new StringBuilder();
/*  76 */     sb.append(sql.getPreFrom());
/*  77 */     sb.append(" ");
/*  78 */     sb.append('\n');
/*     */     
/*  80 */     String s = sql.getPreWhere();
/*  81 */     BindParams bindParams = request.getQuery().getBindParams();
/*  82 */     if (bindParams != null && bindParams.requiresNamedParamsPrepare())
/*     */     {
/*     */ 
/*     */       
/*  86 */       s = BindParamsParser.parse(bindParams, s);
/*     */     }
/*  88 */     sb.append(s);
/*  89 */     sb.append(" ");
/*     */     
/*  91 */     String dynamicWhere = null;
/*  92 */     if (request.getQuery().getId() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/*     */ 
/*     */       
/* 100 */       dynamicWhere = descriptor.getIdBinderIdSql();
/*     */     } 
/*     */     
/* 103 */     String dbWhere = predicates.getDbWhere();
/* 104 */     if (!isEmpty(dbWhere)) {
/* 105 */       if (dynamicWhere == null) {
/* 106 */         dynamicWhere = dbWhere;
/*     */       } else {
/* 108 */         dynamicWhere = dynamicWhere + " and " + dbWhere;
/*     */       } 
/*     */     }
/*     */     
/* 112 */     if (!isEmpty(dynamicWhere)) {
/* 113 */       sb.append('\n');
/* 114 */       if (sql.isAndWhereExpr()) {
/* 115 */         sb.append("and ");
/*     */       } else {
/* 117 */         sb.append("where ");
/*     */       } 
/* 119 */       sb.append(dynamicWhere);
/* 120 */       sb.append(" ");
/*     */     } 
/*     */     
/* 123 */     String preHaving = sql.getPreHaving();
/* 124 */     if (!isEmpty(preHaving)) {
/* 125 */       sb.append('\n');
/* 126 */       sb.append(preHaving);
/* 127 */       sb.append(" ");
/*     */     } 
/*     */     
/* 130 */     String dbHaving = predicates.getDbHaving();
/* 131 */     if (!isEmpty(dbHaving)) {
/* 132 */       sb.append(" ");
/* 133 */       sb.append('\n');
/* 134 */       if (sql.isAndHavingExpr()) {
/* 135 */         sb.append("and ");
/*     */       } else {
/* 137 */         sb.append("having ");
/*     */       } 
/* 139 */       sb.append(dbHaving);
/* 140 */       sb.append(" ");
/*     */     } 
/*     */     
/* 143 */     if (!isEmpty(orderBy)) {
/* 144 */       sb.append('\n');
/* 145 */       sb.append(" order by ").append(orderBy);
/*     */     } 
/*     */     
/* 148 */     return sb.toString().trim();
/*     */   }
/*     */ 
/*     */   
/* 152 */   private boolean isEmpty(String s) { return (s == null || s.length() == 0); }
/*     */ 
/*     */   
/*     */   private String getOrderBy(CQueryPredicates predicates, RawSql.Sql sql) {
/* 156 */     String orderBy = predicates.getDbOrderBy();
/* 157 */     if (orderBy != null) {
/* 158 */       return orderBy;
/*     */     }
/* 160 */     return sql.getOrderBy();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryBuilderRawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */