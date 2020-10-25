/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*     */ import com.avaje.ebean.config.dbplatform.SqlLimiter;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class CQueryBuilder
/*     */   implements Constants
/*     */ {
/*     */   private final String tableAliasPlaceHolder;
/*     */   private final String columnAliasPrefix;
/*     */   private final SqlLimiter sqlLimiter;
/*     */   private final RawSqlSelectClauseBuilder sqlSelectBuilder;
/*     */   private final CQueryBuilderRawSql rawSqlHandler;
/*     */   private final Binder binder;
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   private final boolean selectCountWithAlias;
/*     */   private final boolean luceneAvailable;
/*     */   private final Query.UseIndex defaultUseIndex;
/*     */   
/*     */   public CQueryBuilder(BackgroundExecutor backgroundExecutor, DatabasePlatform dbPlatform, Binder binder, LuceneIndexManager luceneIndexManager) {
/*  83 */     this.luceneAvailable = luceneIndexManager.isLuceneAvailable();
/*  84 */     this.defaultUseIndex = luceneIndexManager.getDefaultUseIndex();
/*  85 */     this.backgroundExecutor = backgroundExecutor;
/*  86 */     this.binder = binder;
/*  87 */     this.tableAliasPlaceHolder = GlobalProperties.get("ebean.tableAliasPlaceHolder", "${ta}");
/*  88 */     this.columnAliasPrefix = GlobalProperties.get("ebean.columnAliasPrefix", "c");
/*  89 */     this.sqlSelectBuilder = new RawSqlSelectClauseBuilder(dbPlatform, binder);
/*     */     
/*  91 */     this.sqlLimiter = dbPlatform.getSqlLimiter();
/*  92 */     this.rawSqlHandler = new CQueryBuilderRawSql(this.sqlLimiter);
/*     */     
/*  94 */     this.selectCountWithAlias = dbPlatform.isSelectCountWithAlias();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getOrderBy(String orderBy, BeanPropertyAssocMany<?> many, BeanDescriptor<?> desc, boolean hasListener) {
/* 100 */     String manyOrderBy = null;
/*     */     
/* 102 */     if (many != null) {
/* 103 */       manyOrderBy = many.getFetchOrderBy();
/* 104 */       if (manyOrderBy != null) {
/* 105 */         manyOrderBy = prefixOrderByFields(many.getName(), manyOrderBy);
/*     */       }
/*     */     } 
/* 108 */     if (orderBy == null && (hasListener || manyOrderBy != null)) {
/*     */       
/* 110 */       StringBuffer sb = new StringBuffer();
/*     */       
/* 112 */       BeanProperty[] uids = desc.propertiesId();
/* 113 */       for (int i = 0; i < uids.length; i++) {
/* 114 */         if (i > 0) {
/* 115 */           sb.append(", ");
/*     */         }
/* 117 */         sb.append(uids[i].getName());
/*     */       } 
/* 119 */       orderBy = sb.toString();
/*     */     } 
/* 121 */     if (manyOrderBy != null)
/*     */     {
/* 123 */       orderBy = orderBy + " , " + manyOrderBy;
/*     */     }
/* 125 */     return orderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String prefixOrderByFields(String name, String orderBy) {
/* 132 */     StringBuilder sb = new StringBuilder();
/* 133 */     for (String token : orderBy.split(",")) {
/* 134 */       if (sb.length() > 0) {
/* 135 */         sb.append(", ");
/*     */       }
/*     */       
/* 138 */       sb.append(name);
/* 139 */       sb.append(".");
/* 140 */       sb.append(token.trim());
/*     */     } 
/*     */     
/* 143 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQueryFetchIds buildFetchIdsQuery(OrmQueryRequest<T> request) {
/*     */     String sql;
/* 151 */     SpiQuery<T> query = request.getQuery();
/*     */     
/* 153 */     query.setSelectId();
/*     */     
/* 155 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/* 156 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 157 */     if (queryPlan != null) {
/*     */       
/* 159 */       predicates.prepare(false);
/* 160 */       sql = queryPlan.getSql();
/* 161 */       return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (isLuceneSupported(request) && predicates.isLuceneResolvable()) {
/*     */       
/* 168 */       SqlTree sqlTree = createLuceneSqlTree(request, predicates);
/* 169 */       queryPlan = new CQueryPlanLucene(request, sqlTree);
/* 170 */       sql = "Lucene Index";
/*     */     }
/*     */     else {
/*     */       
/* 174 */       predicates.prepare(true);
/*     */       
/* 176 */       SqlTree sqlTree = createSqlTree(request, predicates);
/* 177 */       SqlLimitResponse s = buildSql(null, request, predicates, sqlTree);
/* 178 */       sql = s.getSql();
/*     */ 
/*     */       
/* 181 */       queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
/*     */     } 
/*     */     
/* 184 */     request.putQueryPlan(queryPlan);
/* 185 */     return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQueryRowCount buildRowCountQuery(OrmQueryRequest<T> request) {
/* 193 */     SpiQuery<T> query = request.getQuery();
/*     */ 
/*     */     
/* 196 */     query.setOrder(null);
/*     */     
/* 198 */     boolean hasMany = !query.getManyWhereJoins().isEmpty();
/*     */     
/* 200 */     query.setSelectId();
/*     */     
/* 202 */     String sqlSelect = "select count(*)";
/* 203 */     if (hasMany) {
/*     */       
/* 205 */       query.setDistinct(true);
/* 206 */       sqlSelect = null;
/*     */     } 
/*     */     
/* 209 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/* 210 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 211 */     if (queryPlan != null) {
/*     */       
/* 213 */       predicates.prepare(false);
/* 214 */       String sql = queryPlan.getSql();
/* 215 */       return new CQueryRowCount(request, predicates, sql);
/*     */     } 
/*     */     
/* 218 */     predicates.prepare(true);
/*     */     
/* 220 */     SqlTree sqlTree = createSqlTree(request, predicates);
/* 221 */     SqlLimitResponse s = buildSql(sqlSelect, request, predicates, sqlTree);
/* 222 */     String sql = s.getSql();
/* 223 */     if (hasMany) {
/* 224 */       sql = "select count(*) from ( " + sql + ")";
/* 225 */       if (this.selectCountWithAlias) {
/* 226 */         sql = sql + " as c";
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 231 */     queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
/* 232 */     request.putQueryPlan(queryPlan);
/*     */     
/* 234 */     return new CQueryRowCount(request, predicates, sql);
/*     */   }
/*     */   
/*     */   private boolean isLuceneSupported(OrmQueryRequest<?> request) {
/* 238 */     if (!this.luceneAvailable) {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     Query.UseIndex useIndex = request.getQuery().getUseIndex();
/* 243 */     if (useIndex == null) {
/*     */       
/* 245 */       useIndex = request.getBeanDescriptor().getUseIndex();
/* 246 */       if (useIndex == null)
/*     */       {
/* 248 */         useIndex = this.defaultUseIndex;
/*     */       }
/*     */     } 
/*     */     
/* 252 */     if (Query.UseIndex.NO.equals(useIndex)) {
/* 253 */       return false;
/*     */     }
/*     */     
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> CQuery<T> buildQuery(OrmQueryRequest<T> request) {
/* 265 */     if (request.isSqlSelect()) {
/* 266 */       return this.sqlSelectBuilder.build(request);
/*     */     }
/*     */     
/* 269 */     CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
/*     */     
/* 271 */     CQueryPlan queryPlan = request.getQueryPlan();
/* 272 */     if (queryPlan != null) {
/*     */ 
/*     */       
/* 275 */       if (queryPlan.isLucene()) {
/* 276 */         predicates.isLuceneResolvable();
/*     */       } else {
/* 278 */         predicates.prepare(false);
/*     */       } 
/* 280 */       return new CQuery(request, predicates, queryPlan);
/*     */     } 
/*     */     
/* 283 */     if (isLuceneSupported(request) && predicates.isLuceneResolvable()) {
/*     */       
/* 285 */       SqlTree sqlTree = createLuceneSqlTree(request, predicates);
/* 286 */       queryPlan = new CQueryPlanLucene(request, sqlTree);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       predicates.prepare(true);
/*     */ 
/*     */       
/* 300 */       SqlTree sqlTree = createSqlTree(request, predicates);
/* 301 */       SqlLimitResponse res = buildSql(null, request, predicates, sqlTree);
/*     */       
/* 303 */       boolean rawSql = request.isRawSql();
/* 304 */       if (rawSql) {
/* 305 */         queryPlan = new CQueryPlanRawSql(request, res, sqlTree, predicates.getLogWhereSql());
/*     */       } else {
/*     */         
/* 308 */         queryPlan = new CQueryPlan(request, res, sqlTree, rawSql, predicates.getLogWhereSql(), null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 314 */     request.putQueryPlan(queryPlan);
/*     */     
/* 316 */     return new CQuery(request, predicates, queryPlan);
/*     */   }
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
/*     */   private SqlTree createSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
/* 333 */     if (request.isRawSql()) {
/* 334 */       return createRawSqlSqlTree(request, predicates);
/*     */     }
/*     */     
/* 337 */     return (new SqlTreeBuilder(this.tableAliasPlaceHolder, this.columnAliasPrefix, request, predicates)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlTree createLuceneSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
/* 342 */     LIndex luceneIndex = request.getLuceneIndex();
/* 343 */     OrmQueryDetail ormQueryDetail = luceneIndex.getOrmQueryDetail();
/*     */ 
/*     */ 
/*     */     
/* 347 */     return (new SqlTreeBuilder(request, predicates, ormQueryDetail)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlTree createRawSqlSqlTree(OrmQueryRequest<?> request, CQueryPredicates predicates) {
/* 352 */     BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/* 353 */     RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
/*     */     
/* 355 */     PathProperties pathProps = new PathProperties();
/*     */ 
/*     */     
/* 358 */     Iterator<RawSql.ColumnMapping.Column> it = columnMapping.getColumns();
/* 359 */     while (it.hasNext()) {
/* 360 */       RawSql.ColumnMapping.Column column = (RawSql.ColumnMapping.Column)it.next();
/* 361 */       String propertyName = column.getPropertyName();
/* 362 */       if (!"$$_IGNORE_COLUMN_$$".equals(propertyName)) {
/*     */         
/* 364 */         ElPropertyValue el = descriptor.getElGetValue(propertyName);
/* 365 */         if (el == null) {
/* 366 */           String msg = "Property [" + propertyName + "] not found on " + descriptor.getFullName();
/* 367 */           throw new PersistenceException(msg);
/*     */         } 
/* 369 */         BeanProperty beanProperty = el.getBeanProperty();
/* 370 */         if (beanProperty.isId()) {
/*     */           
/* 372 */           propertyName = SplitName.parent(propertyName);
/* 373 */         } else if (beanProperty instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne) {
/* 374 */           String msg = "Column [" + column.getDbColumn() + "] mapped to complex Property[" + propertyName + "]";
/* 375 */           msg = msg + ". It should be mapped to a simple property (proably the Id property). ";
/* 376 */           throw new PersistenceException(msg);
/*     */         } 
/* 378 */         if (propertyName != null) {
/* 379 */           String[] pathProp = SplitName.split(propertyName);
/* 380 */           pathProps.addToPath(pathProp[0], pathProp[1]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     OrmQueryDetail detail = new OrmQueryDetail();
/*     */ 
/*     */     
/* 388 */     Iterator<String> pathIt = pathProps.getPaths().iterator();
/* 389 */     while (pathIt.hasNext()) {
/* 390 */       String path = (String)pathIt.next();
/* 391 */       Set<String> props = pathProps.get(path);
/* 392 */       detail.getChunk(path, true).setDefaultProperties(null, props);
/*     */     } 
/*     */ 
/*     */     
/* 396 */     return (new SqlTreeBuilder(request, predicates, detail)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlLimitResponse buildSql(String selectClause, OrmQueryRequest<?> request, CQueryPredicates predicates, SqlTree select) {
/* 401 */     SpiQuery<?> query = request.getQuery();
/*     */     
/* 403 */     RawSql rawSql = query.getRawSql();
/* 404 */     if (rawSql != null) {
/* 405 */       return this.rawSqlHandler.buildSql(request, predicates, rawSql.getSql());
/*     */     }
/*     */     
/* 408 */     BeanPropertyAssocMany<?> manyProp = select.getManyProperty();
/*     */     
/* 410 */     boolean useSqlLimiter = false;
/*     */     
/* 412 */     StringBuilder sb = new StringBuilder('Ǵ');
/*     */     
/* 414 */     if (selectClause != null) {
/* 415 */       sb.append(selectClause);
/*     */     }
/*     */     else {
/*     */       
/* 419 */       useSqlLimiter = (query.hasMaxRowsOrFirstRow() && manyProp == null);
/*     */       
/* 421 */       if (!useSqlLimiter) {
/* 422 */         sb.append("select ");
/* 423 */         if (query.isDistinct()) {
/* 424 */           sb.append("distinct ");
/*     */         }
/*     */       } 
/*     */       
/* 428 */       sb.append(select.getSelectSql());
/*     */     } 
/*     */     
/* 431 */     sb.append(" ").append('\n');
/* 432 */     sb.append("from ");
/*     */ 
/*     */ 
/*     */     
/* 436 */     sb.append(select.getFromSql());
/*     */     
/* 438 */     String inheritanceWhere = select.getInheritanceWhereSql();
/*     */     
/* 440 */     boolean hasWhere = false;
/* 441 */     if (inheritanceWhere.length() > 0) {
/* 442 */       sb.append(" ").append('\n').append("where");
/* 443 */       sb.append(inheritanceWhere);
/* 444 */       hasWhere = true;
/*     */     } 
/*     */     
/* 447 */     if (request.isFindById() || query.getId() != null) {
/* 448 */       if (hasWhere) {
/* 449 */         sb.append(" and ");
/*     */       } else {
/* 451 */         sb.append('\n').append("where ");
/*     */       } 
/*     */       
/* 454 */       BeanDescriptor<?> desc = request.getBeanDescriptor();
/* 455 */       String idSql = desc.getIdBinderIdSql();
/* 456 */       sb.append(idSql).append(" ");
/* 457 */       hasWhere = true;
/*     */     } 
/*     */     
/* 460 */     String dbWhere = predicates.getDbWhere();
/* 461 */     if (!isEmpty(dbWhere)) {
/* 462 */       if (!hasWhere) {
/* 463 */         hasWhere = true;
/* 464 */         sb.append(" ").append('\n').append("where ");
/*     */       } else {
/* 466 */         sb.append("and ");
/*     */       } 
/* 468 */       sb.append(dbWhere);
/*     */     } 
/*     */     
/* 471 */     String dbFilterMany = predicates.getDbFilterMany();
/* 472 */     if (!isEmpty(dbFilterMany)) {
/* 473 */       if (!hasWhere) {
/* 474 */         sb.append(" ").append('\n').append("where ");
/*     */       } else {
/* 476 */         sb.append("and ");
/*     */       } 
/* 478 */       sb.append(dbFilterMany);
/*     */     } 
/*     */     
/* 481 */     String dbOrderBy = predicates.getDbOrderBy();
/* 482 */     if (dbOrderBy != null) {
/* 483 */       sb.append(" ").append('\n');
/* 484 */       sb.append("order by ").append(dbOrderBy);
/*     */     } 
/*     */     
/* 487 */     if (useSqlLimiter) {
/*     */       
/* 489 */       OrmQueryLimitRequest ormQueryLimitRequest = new OrmQueryLimitRequest(sb.toString(), dbOrderBy, query);
/* 490 */       return this.sqlLimiter.limit(ormQueryLimitRequest);
/*     */     } 
/*     */ 
/*     */     
/* 494 */     return new SqlLimitResponse(sb.toString(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 500 */   private boolean isEmpty(String s) { return (s == null || s.length() == 0); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */