/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class RawSqlSelectClauseBuilder
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(RawSqlSelectClauseBuilder.class.getName());
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final SqlLimiter dbQueryLimiter;
/*     */ 
/*     */   
/*     */   public RawSqlSelectClauseBuilder(DatabasePlatform dbPlatform, Binder binder) {
/*  56 */     this.binder = binder;
/*  57 */     this.dbQueryLimiter = dbPlatform.getSqlLimiter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQuery<T> build(OrmQueryRequest<T> request) throws PersistenceException {
/*  65 */     SpiQuery<T> query = request.getQuery();
/*  66 */     BeanDescriptor<T> desc = request.getBeanDescriptor();
/*     */     
/*  68 */     DeployNamedQuery namedQuery = desc.getNamedQuery(query.getName());
/*  69 */     DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
/*     */ 
/*     */ 
/*     */     
/*  73 */     DeployParser parser = sqlSelect.createDeployPropertyParser();
/*     */     
/*  75 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/*     */     
/*  77 */     predicates.prepareRawSql(parser);
/*     */     
/*  79 */     SqlTreeAlias alias = new SqlTreeAlias(sqlSelect.getTableAlias());
/*  80 */     predicates.parseTableAlias(alias);
/*     */     
/*  82 */     String sql = null;
/*     */     
/*     */     try {
/*  85 */       boolean includeRowNumColumn = false;
/*  86 */       String orderBy = sqlSelect.getOrderBy(predicates);
/*     */ 
/*     */       
/*  89 */       sql = sqlSelect.buildSql(orderBy, predicates, request);
/*  90 */       if (query.hasMaxRowsOrFirstRow() && this.dbQueryLimiter != null) {
/*     */         
/*  92 */         SqlLimitResponse limitSql = this.dbQueryLimiter.limit(new OrmQueryLimitRequest(sql, orderBy, query));
/*  93 */         includeRowNumColumn = limitSql.isIncludesRowNumberColumn();
/*     */         
/*  95 */         sql = limitSql.getSql();
/*     */       }
/*     */       else {
/*     */         
/*  99 */         sql = "select " + sql;
/*     */       } 
/*     */       
/* 102 */       SqlTree sqlTree = sqlSelect.getSqlTree();
/*     */       
/* 104 */       CQueryPlan queryPlan = new CQueryPlan(sql, sqlTree, true, includeRowNumColumn, "");
/* 105 */       return new CQuery<T>(request, predicates, queryPlan);
/*     */ 
/*     */     
/*     */     }
/* 109 */     catch (Exception e) {
/*     */       
/* 111 */       String msg = "Error with " + desc.getFullName() + " query:\r" + sql;
/* 112 */       logger.log(Level.SEVERE, msg);
/* 113 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\RawSqlSelectClauseBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */