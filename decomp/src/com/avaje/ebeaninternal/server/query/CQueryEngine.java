/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import java.sql.SQLException;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.logging.Logger;
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
/*     */ public class CQueryEngine
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(CQueryEngine.class.getName());
/*     */   
/*     */   private final CQueryBuilder queryBuilder;
/*     */   private final MAdminLogging logControl;
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   private final int defaultSecondaryQueryBatchSize = 100;
/*     */   
/*     */   public CQueryEngine(DatabasePlatform dbPlatform, MAdminLogging logControl, Binder binder, BackgroundExecutor backgroundExecutor, LuceneIndexManager luceneIndexManager) {
/*  53 */     this.defaultSecondaryQueryBatchSize = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.logControl = logControl;
/*  59 */     this.backgroundExecutor = backgroundExecutor;
/*  60 */     this.queryBuilder = new CQueryBuilder(backgroundExecutor, dbPlatform, binder, luceneIndexManager);
/*     */   }
/*     */ 
/*     */   
/*  64 */   public <T> CQuery<T> buildQuery(OrmQueryRequest<T> request) { return this.queryBuilder.buildQuery(request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> BeanIdList findIds(OrmQueryRequest<T> request) {
/*  73 */     CQueryFetchIds rcQuery = this.queryBuilder.buildFetchIdsQuery(request);
/*     */     
/*     */     try {
/*  76 */       String sql = rcQuery.getGeneratedSql();
/*  77 */       sql = sql.replace('\n', ' ');
/*     */       
/*  79 */       if (this.logControl.isDebugGeneratedSql()) {
/*  80 */         System.out.println(sql);
/*     */       }
/*  82 */       request.logSql(sql);
/*     */ 
/*     */       
/*  85 */       BeanIdList list = rcQuery.findIds();
/*     */       
/*  87 */       if (request.isLogSummary()) {
/*  88 */         request.getTransaction().logInternal(rcQuery.getSummary());
/*     */       }
/*     */       
/*  91 */       if (!list.isFetchingInBackground() && request.getQuery().isFutureFetch()) {
/*     */         
/*  93 */         logger.fine("Future findIds completed!");
/*  94 */         request.getTransaction().end();
/*     */       } 
/*     */       
/*  97 */       return list;
/*     */     }
/*  99 */     catch (SQLException e) {
/* 100 */       throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> int findRowCount(OrmQueryRequest<T> request) {
/* 110 */     CQueryRowCount rcQuery = this.queryBuilder.buildRowCountQuery(request);
/*     */     
/*     */     try {
/* 113 */       String sql = rcQuery.getGeneratedSql();
/* 114 */       sql = sql.replace('\n', ' ');
/*     */       
/* 116 */       if (this.logControl.isDebugGeneratedSql()) {
/* 117 */         System.out.println(sql);
/*     */       }
/* 119 */       request.logSql(sql);
/*     */       
/* 121 */       int rowCount = rcQuery.findRowCount();
/*     */       
/* 123 */       if (request.isLogSummary()) {
/* 124 */         request.getTransaction().logInternal(rcQuery.getSummary());
/*     */       }
/*     */       
/* 127 */       if (request.getQuery().isFutureFetch()) {
/* 128 */         logger.fine("Future findRowCount completed!");
/* 129 */         request.getTransaction().end();
/*     */       } 
/*     */       
/* 132 */       return rowCount;
/*     */     }
/* 134 */     catch (SQLException e) {
/* 135 */       throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request) {
/* 144 */     CQuery<T> cquery = this.queryBuilder.buildQuery(request);
/* 145 */     request.setCancelableQuery(cquery);
/*     */ 
/*     */     
/*     */     try {
/* 149 */       if (this.logControl.isDebugGeneratedSql()) {
/* 150 */         logSqlToConsole(cquery);
/*     */       }
/*     */       
/* 153 */       if (request.isLogSql()) {
/* 154 */         logSql(cquery);
/*     */       }
/*     */       
/* 157 */       if (!cquery.prepareBindExecuteQuery()) {
/*     */         
/* 159 */         logger.finest("Future fetch already cancelled");
/* 160 */         return null;
/*     */       } 
/*     */       
/* 163 */       int iterateBufferSize = request.getSecondaryQueriesMinBatchSize(100);
/*     */       
/* 165 */       QueryIterator<T> readIterate = cquery.readIterate(iterateBufferSize, request);
/*     */       
/* 167 */       if (request.isLogSummary()) {
/* 168 */         logFindManySummary(cquery);
/*     */       }
/*     */       
/* 171 */       return readIterate;
/*     */     }
/* 173 */     catch (SQLException e) {
/* 174 */       throw cquery.createPersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> BeanCollection<T> findMany(OrmQueryRequest<T> request) {
/* 184 */     useBackgroundToContinueFetch = false;
/*     */     
/* 186 */     cquery = this.queryBuilder.buildQuery(request);
/* 187 */     request.setCancelableQuery(cquery);
/*     */ 
/*     */     
/*     */     try {
/* 191 */       if (this.logControl.isDebugGeneratedSql()) {
/* 192 */         logSqlToConsole(cquery);
/*     */       }
/* 194 */       if (request.isLogSql()) {
/* 195 */         logSql(cquery);
/*     */       }
/*     */       
/* 198 */       if (!cquery.prepareBindExecuteQuery()) {
/*     */         
/* 200 */         logger.finest("Future fetch already cancelled");
/* 201 */         return null;
/*     */       } 
/*     */       
/* 204 */       BeanCollection<T> beanCollection = cquery.readCollection();
/* 205 */       if (request.getParentState() != 0)
/*     */       {
/* 207 */         beanCollection.setSharedInstance();
/*     */       }
/*     */       
/* 210 */       BeanCollectionTouched collectionTouched = request.getQuery().getBeanCollectionTouched();
/* 211 */       if (collectionTouched != null)
/*     */       {
/*     */         
/* 214 */         beanCollection.setBeanCollectionTouched(collectionTouched);
/*     */       }
/*     */       
/* 217 */       if (cquery.useBackgroundToContinueFetch()) {
/*     */ 
/*     */         
/* 220 */         request.setBackgroundFetching();
/* 221 */         useBackgroundToContinueFetch = true;
/* 222 */         BackgroundFetch fetch = new BackgroundFetch(cquery);
/*     */         
/* 224 */         FutureTask<Integer> future = new FutureTask<Integer>(fetch);
/* 225 */         beanCollection.setBackgroundFetch(future);
/* 226 */         this.backgroundExecutor.execute(future);
/*     */       } 
/*     */       
/* 229 */       if (request.isLogSummary()) {
/* 230 */         logFindManySummary(cquery);
/*     */       }
/*     */       
/* 233 */       request.executeSecondaryQueries(100);
/*     */       
/* 235 */       return beanCollection;
/*     */     }
/* 237 */     catch (SQLException e) {
/* 238 */       throw cquery.createPersistenceException(e);
/*     */     } finally {
/*     */       
/* 241 */       if (!useBackgroundToContinueFetch) {
/*     */ 
/*     */         
/* 244 */         if (cquery != null) {
/* 245 */           cquery.close();
/*     */         }
/* 247 */         if (request.getQuery().isFutureFetch()) {
/*     */ 
/*     */           
/* 250 */           logger.fine("Future fetch completed!");
/* 251 */           request.getTransaction().end();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T find(OrmQueryRequest<T> request) {
/* 263 */     T bean = null;
/*     */     
/* 265 */     cquery = this.queryBuilder.buildQuery(request);
/*     */     
/*     */     try {
/* 268 */       if (this.logControl.isDebugGeneratedSql()) {
/* 269 */         logSqlToConsole(cquery);
/*     */       }
/* 271 */       if (request.isLogSql()) {
/* 272 */         logSql(cquery);
/*     */       }
/*     */       
/* 275 */       cquery.prepareBindExecuteQuery();
/*     */       
/* 277 */       if (cquery.readBean()) {
/* 278 */         bean = (T)cquery.getLoadedBean();
/*     */       }
/*     */       
/* 281 */       if (request.isLogSummary()) {
/* 282 */         logFindBeanSummary(cquery);
/*     */       }
/*     */       
/* 285 */       request.executeSecondaryQueries(100);
/*     */       
/* 287 */       return bean;
/*     */     }
/* 289 */     catch (SQLException e) {
/* 290 */       throw cquery.createPersistenceException(e);
/*     */     } finally {
/*     */       
/* 293 */       cquery.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSqlToConsole(CQuery<?> cquery) {
/* 302 */     SpiQuery<?> query = cquery.getQueryRequest().getQuery();
/* 303 */     String loadMode = query.getLoadMode();
/* 304 */     String loadDesc = query.getLoadDescription();
/*     */     
/* 306 */     String sql = cquery.getGeneratedSql();
/* 307 */     String summary = cquery.getSummary();
/*     */     
/* 309 */     StringBuilder sb = new StringBuilder('Ϩ');
/* 310 */     sb.append("<sql ");
/* 311 */     if (query.isAutofetchTuned()) {
/* 312 */       sb.append("tuned='true' ");
/*     */     }
/* 314 */     if (loadMode != null) {
/* 315 */       sb.append("mode='").append(loadMode).append("' ");
/*     */     }
/* 317 */     sb.append("summary='").append(summary);
/* 318 */     if (loadDesc != null) {
/* 319 */       sb.append("' load='").append(loadDesc);
/*     */     }
/* 321 */     sb.append("' >");
/* 322 */     sb.append('\n');
/* 323 */     sb.append(sql);
/* 324 */     sb.append('\n').append("</sql>");
/*     */     
/* 326 */     System.out.println(sb.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSql(CQuery<?> query) {
/* 334 */     String sql = query.getGeneratedSql();
/* 335 */     sql = sql.replace('\n', ' ');
/* 336 */     query.getTransaction().logInternal(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logFindBeanSummary(CQuery<?> q) {
/* 344 */     SpiQuery<?> query = q.getQueryRequest().getQuery();
/* 345 */     String loadMode = query.getLoadMode();
/* 346 */     String loadDesc = query.getLoadDescription();
/* 347 */     String lazyLoadProp = query.getLazyLoadProperty();
/* 348 */     ObjectGraphNode node = query.getParentNode();
/* 349 */     String originKey = (node == null) ? null : node.getOriginQueryPoint().getKey();
/*     */     
/* 351 */     StringBuilder msg = new StringBuilder('È');
/* 352 */     msg.append("FindBean ");
/* 353 */     if (loadMode != null) {
/* 354 */       msg.append("mode[").append(loadMode).append("] ");
/*     */     }
/* 356 */     msg.append("type[").append(q.getBeanName()).append("] ");
/* 357 */     if (query.isAutofetchTuned()) {
/* 358 */       msg.append("tuned[true] ");
/*     */     }
/* 360 */     if (originKey != null) {
/* 361 */       msg.append("origin[").append(originKey).append("] ");
/*     */     }
/* 363 */     if (lazyLoadProp != null) {
/* 364 */       msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
/*     */     }
/* 366 */     if (loadDesc != null) {
/* 367 */       msg.append("load[").append(loadDesc).append("] ");
/*     */     }
/* 369 */     msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
/* 370 */     msg.append("] rows[").append(q.getLoadedRowDetail());
/* 371 */     msg.append("] bind[").append(q.getBindLog()).append("]");
/*     */     
/* 373 */     q.getTransaction().logInternal(msg.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logFindManySummary(CQuery<?> q) {
/* 381 */     SpiQuery<?> query = q.getQueryRequest().getQuery();
/* 382 */     String loadMode = query.getLoadMode();
/* 383 */     String loadDesc = query.getLoadDescription();
/* 384 */     String lazyLoadProp = query.getLazyLoadProperty();
/* 385 */     ObjectGraphNode node = query.getParentNode();
/* 386 */     String originKey = (node == null) ? null : node.getOriginQueryPoint().getKey();
/*     */     
/* 388 */     StringBuilder msg = new StringBuilder('È');
/* 389 */     msg.append("FindMany ");
/* 390 */     if (loadMode != null) {
/* 391 */       msg.append("mode[").append(loadMode).append("] ");
/*     */     }
/* 393 */     msg.append("type[").append(q.getBeanName()).append("] ");
/* 394 */     if (query.isAutofetchTuned()) {
/* 395 */       msg.append("tuned[true] ");
/*     */     }
/* 397 */     if (originKey != null) {
/* 398 */       msg.append("origin[").append(originKey).append("] ");
/*     */     }
/* 400 */     if (lazyLoadProp != null) {
/* 401 */       msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
/*     */     }
/* 403 */     if (loadDesc != null) {
/* 404 */       msg.append("load[").append(loadDesc).append("] ");
/*     */     }
/* 406 */     msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
/* 407 */     msg.append("] rows[").append(q.getLoadedRowDetail());
/* 408 */     msg.append("] name[").append(q.getName());
/* 409 */     msg.append("] predicates[").append(q.getLogWhereSql());
/* 410 */     msg.append("] bind[").append(q.getBindLog()).append("]");
/*     */ 
/*     */     
/* 413 */     q.getTransaction().logInternal(msg.toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */