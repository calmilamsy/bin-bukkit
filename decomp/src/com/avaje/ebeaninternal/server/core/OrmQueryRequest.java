/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryResultVisitor;
/*     */ import com.avaje.ebean.RawSql;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebean.event.BeanFinder;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.CopyContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployParser;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployPropertyParserMap;
/*     */ import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.query.CQueryPlan;
/*     */ import com.avaje.ebeaninternal.server.query.CancelableQuery;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OrmQueryRequest<T>
/*     */   extends BeanRequest
/*     */   implements BeanQueryRequest<T>, SpiOrmQueryRequest<T>
/*     */ {
/*     */   private final BeanDescriptor<T> beanDescriptor;
/*     */   private final OrmQueryEngine queryEngine;
/*     */   private final SpiQuery<T> query;
/*     */   private final boolean vanillaMode;
/*     */   private final BeanFinder<T> finder;
/*     */   private final LoadContext graphContext;
/*     */   private final int parentState;
/*     */   private final RawSql rawSql;
/*     */   private PersistenceContext persistenceContext;
/*     */   private Integer cacheKey;
/*     */   private int queryPlanHash;
/*     */   private boolean backgroundFetching;
/*     */   private boolean useBeanCache;
/*     */   private boolean useBeanCacheReadOnly;
/*     */   private LuceneOrmQueryRequest luceneQueryRequest;
/*     */   
/*     */   public OrmQueryRequest(SpiEbeanServer server, OrmQueryEngine queryEngine, SpiQuery<T> query, BeanDescriptor<T> desc, SpiTransaction t) {
/* 101 */     super(server, t);
/*     */     
/* 103 */     this.beanDescriptor = desc;
/* 104 */     this.rawSql = query.getRawSql();
/* 105 */     this.finder = this.beanDescriptor.getBeanFinder();
/* 106 */     this.queryEngine = queryEngine;
/* 107 */     this.query = query;
/* 108 */     this.vanillaMode = query.isVanillaMode(server.isVanillaMode());
/* 109 */     this.parentState = determineParentState(query);
/*     */     
/* 111 */     int defaultBatchSize = server.getLazyLoadBatchSize();
/* 112 */     this.graphContext = new DLoadContext(this.ebeanServer, this.beanDescriptor, defaultBatchSize, this.parentState, query);
/*     */     
/* 114 */     this.graphContext.registerSecondaryQueries(query);
/*     */   }
/*     */   
/*     */   private int determineParentState(SpiQuery<T> query) {
/* 118 */     if (query.isSharedInstance()) {
/* 119 */       return 3;
/*     */     }
/*     */     
/* 122 */     Boolean queryReadOnly = query.isReadOnly();
/* 123 */     if (queryReadOnly != null) {
/*     */       
/* 125 */       if (Boolean.TRUE.equals(queryReadOnly)) {
/* 126 */         return 2;
/*     */       }
/* 128 */       return 1;
/*     */     } 
/*     */     
/* 131 */     if (query.getMode().equals(SpiQuery.Mode.NORMAL))
/*     */     {
/* 133 */       if (this.beanDescriptor.calculateReadOnly(query.isReadOnly())) {
/* 134 */         return 2;
/*     */       }
/*     */     }
/*     */     
/* 138 */     return 0;
/*     */   }
/*     */ 
/*     */   
/* 142 */   public void executeSecondaryQueries(int defaultQueryBatch) { this.graphContext.executeSecondaryQueries(this, defaultQueryBatch); }
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
/* 154 */   public int getSecondaryQueriesMinBatchSize(int defaultQueryBatch) { return this.graphContext.getSecondaryQueriesMinBatchSize(this, defaultQueryBatch); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public int getParentState() { return this.parentState; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public BeanDescriptor<T> getBeanDescriptor() { return this.beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public LIndex getLuceneIndex() { return this.beanDescriptor.getLuceneIndex(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public LoadContext getGraphContext() { return this.graphContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public void calculateQueryPlanHash() { this.queryPlanHash = this.query.queryPlanHash(this); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public boolean isRawSql() { return (this.rawSql != null); }
/*     */ 
/*     */   
/*     */   public DeployParser createDeployParser() {
/* 197 */     if (this.rawSql != null) {
/* 198 */       return new DeployPropertyParserMap(this.rawSql.getColumnMapping().getMapping());
/*     */     }
/* 200 */     return this.beanDescriptor.createDeployPropertyParser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public boolean isSqlSelect() { return (this.query.isSqlSelect() && this.query.getRawSql() == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public PersistenceContext getPersistenceContext() { return this.persistenceContext; }
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
/*     */   public void initTransIfRequired() {
/* 231 */     if (this.query.createOwnTransaction()) {
/*     */       
/* 233 */       this.transaction = this.ebeanServer.createQueryTransaction();
/* 234 */       this.createdTransaction = true;
/*     */     }
/* 236 */     else if (this.transaction == null) {
/*     */       
/* 238 */       this.transaction = this.ebeanServer.getCurrentServerTransaction();
/* 239 */       if (this.transaction == null) {
/*     */         
/* 241 */         this.transaction = this.ebeanServer.createQueryTransaction();
/* 242 */         this.createdTransaction = true;
/*     */       } 
/*     */     } 
/* 245 */     this.persistenceContext = getPersistenceContext(this.query, this.transaction);
/* 246 */     this.graphContext.setPersistenceContext(this.persistenceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PersistenceContext getPersistenceContext(SpiQuery<?> query, SpiTransaction t) {
/* 255 */     PersistenceContext ctx = query.getPersistenceContext();
/* 256 */     if (ctx == null) {
/* 257 */       ctx = t.getPersistenceContext();
/*     */     }
/* 259 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTransIfRequired() {
/* 270 */     if (this.createdTransaction && !this.backgroundFetching)
/*     */     {
/* 272 */       this.transaction.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public void setBackgroundFetching() { this.backgroundFetching = true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   public boolean isFindById() { return (this.query.getType() == Query.Type.BEAN); }
/*     */ 
/*     */ 
/*     */   
/* 291 */   public boolean isVanillaMode() { return this.vanillaMode; }
/*     */ 
/*     */ 
/*     */   
/* 295 */   public LuceneOrmQueryRequest getLuceneOrmQueryRequest() { return this.luceneQueryRequest; }
/*     */ 
/*     */ 
/*     */   
/* 299 */   public void setLuceneOrmQueryRequest(LuceneOrmQueryRequest luceneQueryRequest) { this.luceneQueryRequest = luceneQueryRequest; }
/*     */ 
/*     */ 
/*     */   
/* 303 */   public boolean isLuceneQuery() { return (this.luceneQueryRequest != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public Object findId() { return this.queryEngine.findId(this); }
/*     */ 
/*     */ 
/*     */   
/* 314 */   public int findRowCount() { return this.queryEngine.findRowCount(this); }
/*     */ 
/*     */   
/*     */   public List<Object> findIds() {
/* 318 */     BeanIdList idList = this.queryEngine.findIds(this);
/* 319 */     return idList.getIdList();
/*     */   }
/*     */   
/*     */   public void findVisit(QueryResultVisitor<T> visitor) {
/* 323 */     it = this.queryEngine.findIterate(this); try { do {
/*     */       
/* 325 */       } while (it.hasNext() && 
/* 326 */         visitor.accept(it.next()));
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 331 */     { it.close(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/* 336 */   public QueryIterator<T> findIterate() { return this.queryEngine.findIterate(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> findList() {
/* 344 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 345 */     return (List)(this.vanillaMode ? bc.getActualCollection() : bc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<?> findSet() {
/* 353 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 354 */     return (Set)(this.vanillaMode ? bc.getActualCollection() : bc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<?, ?> findMap() {
/* 361 */     String mapKey = this.query.getMapKey();
/* 362 */     if (mapKey == null) {
/* 363 */       BeanProperty[] ids = this.beanDescriptor.propertiesId();
/* 364 */       if (ids.length == 1) {
/* 365 */         this.query.setMapKey(ids[0].getName());
/*     */       } else {
/* 367 */         String msg = "No mapKey specified for query";
/* 368 */         throw new PersistenceException(msg);
/*     */       } 
/*     */     } 
/* 371 */     BeanCollection<T> bc = this.queryEngine.findMany(this);
/* 372 */     return (Map)(this.vanillaMode ? bc.getActualCollection() : bc);
/*     */   }
/*     */ 
/*     */   
/* 376 */   public Query.Type getQueryType() { return this.query.getType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public BeanFinder<T> getBeanFinder() { return this.finder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 390 */   public SpiQuery<T> getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 398 */   public BeanPropertyAssocMany<?> getManyProperty() { return this.beanDescriptor.getManyProperty(this.query); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 406 */   public CQueryPlan getQueryPlan() { return this.beanDescriptor.getQueryPlan(Integer.valueOf(this.queryPlanHash)); }
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
/* 418 */   public int getQueryPlanHash() { return this.queryPlanHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public void putQueryPlan(CQueryPlan queryPlan) { this.beanDescriptor.putQueryPlan(Integer.valueOf(this.queryPlanHash), queryPlan); }
/*     */ 
/*     */ 
/*     */   
/* 429 */   public boolean isUseBeanCache() { return this.useBeanCache; }
/*     */ 
/*     */ 
/*     */   
/* 433 */   public boolean isUseBeanCacheReadOnly() { return this.useBeanCacheReadOnly; }
/*     */ 
/*     */   
/*     */   private boolean calculateUseBeanCache() {
/* 437 */     this.useBeanCache = this.beanDescriptor.calculateUseCache(this.query.isUseBeanCache());
/* 438 */     if (this.useBeanCache) {
/* 439 */       this.useBeanCacheReadOnly = this.beanDescriptor.calculateReadOnly(this.query.isReadOnly());
/*     */     }
/* 441 */     return this.useBeanCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getFromPersistenceContextOrCache() {
/* 450 */     if (this.query.isLoadBeanCache())
/*     */     {
/*     */       
/* 453 */       return null;
/*     */     }
/*     */     
/* 456 */     SpiTransaction t = this.transaction;
/* 457 */     if (t == null) {
/* 458 */       t = this.ebeanServer.getCurrentServerTransaction();
/*     */     }
/* 460 */     if (t != null) {
/*     */       
/* 462 */       PersistenceContext context = t.getPersistenceContext();
/* 463 */       if (context != null) {
/* 464 */         Object o = context.get(this.beanDescriptor.getBeanType(), this.query.getId());
/* 465 */         if (o != null) {
/* 466 */           return (T)o;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 471 */     if (!calculateUseBeanCache())
/*     */     {
/* 473 */       return null;
/*     */     }
/*     */     
/* 476 */     Object cachedBean = this.beanDescriptor.cacheGet(this.query.getId());
/* 477 */     if (cachedBean == null)
/*     */     {
/* 479 */       return null;
/*     */     }
/* 481 */     if (this.useBeanCacheReadOnly)
/*     */     {
/*     */       
/* 484 */       return (T)cachedBean;
/*     */     }
/*     */ 
/*     */     
/* 488 */     return (T)this.beanDescriptor.createCopyForUpdate(cachedBean, this.vanillaMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<T> getFromQueryCache() {
/* 497 */     if (!this.query.isUseQueryCache()) {
/* 498 */       return null;
/*     */     }
/*     */     
/* 501 */     if (this.query.getType() == null) {
/*     */       
/* 503 */       this.cacheKey = Integer.valueOf(this.query.queryHash());
/*     */     }
/*     */     else {
/*     */       
/* 507 */       this.cacheKey = Integer.valueOf(31 * this.query.queryHash() + this.query.getType().hashCode());
/*     */     } 
/*     */     
/* 510 */     BeanCollection<T> bc = this.beanDescriptor.queryCacheGet(this.cacheKey);
/* 511 */     if (bc != null && Boolean.FALSE.equals(this.query.isReadOnly())) {
/*     */       
/* 513 */       CopyContext ctx = new CopyContext(this.vanillaMode, false);
/* 514 */       return (new CopyBeanCollection(bc, this.beanDescriptor, ctx, 5)).copy();
/*     */     } 
/* 516 */     return bc;
/*     */   }
/*     */ 
/*     */   
/* 520 */   public void putToQueryCache(BeanCollection<T> queryResult) { this.beanDescriptor.queryCachePut(this.cacheKey, queryResult); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 528 */   public void setCancelableQuery(CancelableQuery cancelableQuery) { this.query.setCancelableQuery(cancelableQuery); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logSql(String sql) {
/* 535 */     if (this.transaction.isLogSql())
/* 536 */       this.transaction.logInternal(sql); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\OrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */