/*      */ package com.avaje.ebeaninternal.server.querydefn;
/*      */ 
/*      */ import com.avaje.ebean.EbeanServer;
/*      */ import com.avaje.ebean.Expression;
/*      */ import com.avaje.ebean.ExpressionFactory;
/*      */ import com.avaje.ebean.ExpressionList;
/*      */ import com.avaje.ebean.FetchConfig;
/*      */ import com.avaje.ebean.FutureIds;
/*      */ import com.avaje.ebean.FutureList;
/*      */ import com.avaje.ebean.FutureRowCount;
/*      */ import com.avaje.ebean.JoinConfig;
/*      */ import com.avaje.ebean.OrderBy;
/*      */ import com.avaje.ebean.PagingList;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.QueryIterator;
/*      */ import com.avaje.ebean.QueryListener;
/*      */ import com.avaje.ebean.QueryResultVisitor;
/*      */ import com.avaje.ebean.RawSql;
/*      */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*      */ import com.avaje.ebean.bean.CallStack;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.ObjectGraphNode;
/*      */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*      */ import com.avaje.ebean.bean.PersistenceContext;
/*      */ import com.avaje.ebean.event.BeanQueryRequest;
/*      */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*      */ import com.avaje.ebeaninternal.api.BindParams;
/*      */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*      */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*      */ import com.avaje.ebeaninternal.api.SpiQuery;
/*      */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*      */ import com.avaje.ebeaninternal.server.query.CancelableQuery;
/*      */ import com.avaje.ebeaninternal.util.DefaultExpressionList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.persistence.PersistenceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultOrmQuery<T>
/*      */   extends Object
/*      */   implements SpiQuery<T>
/*      */ {
/*      */   private static final long serialVersionUID = 6838006264714672460L;
/*      */   private final Class<T> beanType;
/*      */   private final EbeanServer server;
/*      */   private BeanCollectionTouched beanCollectionTouched;
/*      */   private final ExpressionFactory expressionFactory;
/*      */   private ArrayList<EntityBean> contextAdditions;
/*      */   private QueryListener<T> queryListener;
/*      */   private TableJoin includeTableJoin;
/*      */   private AutoFetchManager autoFetchManager;
/*      */   private BeanDescriptor<?> beanDescriptor;
/*      */   private boolean cancelled;
/*      */   private CancelableQuery cancelableQuery;
/*      */   private String name;
/*      */   private Query.UseIndex useIndex;
/*      */   private Query.Type type;
/*      */   private SpiQuery.Mode mode;
/*      */   private OrmQueryDetail detail;
/*      */   private int maxRows;
/*      */   private int firstRow;
/*      */   private String rawWhereClause;
/*      */   private OrderBy<T> orderBy;
/*      */   private String loadMode;
/*      */   private String loadDescription;
/*      */   private String generatedSql;
/*      */   private String query;
/*      */   private String additionalWhere;
/*      */   private String additionalHaving;
/*      */   
/*      */   public DefaultOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, String query) {
/*   94 */     this.mode = SpiQuery.Mode.NORMAL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  157 */     this.timeout = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     this.usageProfiling = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  220 */     this.beanType = beanType;
/*  221 */     this.server = server;
/*  222 */     this.expressionFactory = expressionFactory;
/*  223 */     this.detail = new OrmQueryDetail();
/*  224 */     this.name = "";
/*  225 */     if (query != null)
/*  226 */       setQuery(query); 
/*      */   }
/*      */   private String lazyLoadProperty; private String lazyLoadManyPath; private Boolean vanillaMode; private boolean distinct; private boolean futureFetch; private boolean sharedInstance; private List<Object> partialIds; private int backgroundFetchAfter; private int timeout; private String mapKey; private Object id; private BindParams bindParams; private DefaultExpressionList<T> whereExpressions; private DefaultExpressionList<T> havingExpressions; private int bufferFetchSizeHint; private boolean usageProfiling; private boolean loadBeanCache; private Boolean useBeanCache; private Boolean useQueryCache; private Boolean readOnly; private boolean sqlSelect; private Boolean autoFetch; private boolean autoFetchTuned; private ObjectGraphNode parentNode; private int queryPlanHash; private PersistenceContext persistenceContext;
/*      */   private ManyWhereJoins manyWhereJoins;
/*      */   private RawSql rawSql;
/*      */   
/*      */   public DefaultOrmQuery(Class<T> beanType, EbeanServer server, ExpressionFactory expressionFactory, DeployNamedQuery namedQuery) throws PersistenceException {
/*      */     this.mode = SpiQuery.Mode.NORMAL;
/*      */     this.timeout = -1;
/*      */     this.usageProfiling = true;
/*  236 */     this.beanType = beanType;
/*  237 */     this.server = server;
/*  238 */     this.expressionFactory = expressionFactory;
/*  239 */     this.detail = new OrmQueryDetail();
/*  240 */     if (namedQuery == null) {
/*  241 */       this.name = "";
/*      */     } else {
/*  243 */       this.name = namedQuery.getName();
/*  244 */       this.sqlSelect = namedQuery.isSqlSelect();
/*  245 */       if (this.sqlSelect) {
/*      */         
/*  247 */         DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
/*  248 */         this.additionalWhere = sqlSelect.getWhereClause();
/*  249 */         this.additionalHaving = sqlSelect.getHavingClause();
/*  250 */       } else if (namedQuery.isRawSql()) {
/*  251 */         this.rawSql = namedQuery.getRawSql();
/*      */       }
/*      */       else {
/*      */         
/*  255 */         setQuery(namedQuery.getQuery());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   public void setBeanDescriptor(BeanDescriptor<?> beanDescriptor) { this.beanDescriptor = beanDescriptor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean selectAllForLazyLoadProperty() {
/*  272 */     if (this.lazyLoadProperty != null && 
/*  273 */       !this.detail.containsProperty(this.lazyLoadProperty)) {
/*  274 */       this.detail.select("*");
/*  275 */       return true;
/*      */     } 
/*      */     
/*  278 */     return false;
/*      */   }
/*      */ 
/*      */   
/*  282 */   public RawSql getRawSql() { return this.rawSql; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setRawSql(RawSql rawSql) {
/*  286 */     this.rawSql = rawSql;
/*  287 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  291 */   public String getLazyLoadProperty() { return this.lazyLoadProperty; }
/*      */ 
/*      */ 
/*      */   
/*  295 */   public void setLazyLoadProperty(String lazyLoadProperty) { this.lazyLoadProperty = lazyLoadProperty; }
/*      */ 
/*      */ 
/*      */   
/*  299 */   public String getLazyLoadManyPath() { return this.lazyLoadManyPath; }
/*      */ 
/*      */ 
/*      */   
/*  303 */   public ExpressionFactory getExpressionFactory() { return this.expressionFactory; }
/*      */ 
/*      */   
/*      */   public void setParentState(int parentState) {
/*  307 */     if (parentState == 3) {
/*  308 */       setSharedInstance();
/*  309 */     } else if (parentState == 2) {
/*  310 */       setReadOnly(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public MetaAutoFetchStatistic getMetaAutoFetchStatistic() {
/*  315 */     if (this.parentNode != null && this.server != null) {
/*  316 */       ObjectGraphOrigin origin = this.parentNode.getOriginQueryPoint();
/*  317 */       return (MetaAutoFetchStatistic)this.server.find(MetaAutoFetchStatistic.class, origin.getKey());
/*      */     } 
/*  319 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initManyWhereJoins() {
/*  326 */     this.manyWhereJoins = new ManyWhereJoins();
/*  327 */     if (this.whereExpressions != null) {
/*  328 */       this.whereExpressions.containsMany(this.beanDescriptor, this.manyWhereJoins);
/*      */     }
/*  330 */     return !this.manyWhereJoins.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*  334 */   public ManyWhereJoins getManyWhereJoins() { return this.manyWhereJoins; }
/*      */ 
/*      */   
/*      */   public List<OrmQueryProperties> removeQueryJoins() {
/*  338 */     List<OrmQueryProperties> queryJoins = this.detail.removeSecondaryQueries();
/*  339 */     if (queryJoins != null && 
/*  340 */       this.orderBy != null)
/*      */     {
/*      */       
/*  343 */       for (int i = 0; i < queryJoins.size(); i++) {
/*  344 */         OrmQueryProperties joinPath = (OrmQueryProperties)queryJoins.get(i);
/*      */ 
/*      */ 
/*      */         
/*  348 */         List<OrderBy.Property> properties = this.orderBy.getProperties();
/*  349 */         Iterator<OrderBy.Property> it = properties.iterator();
/*  350 */         while (it.hasNext()) {
/*  351 */           OrderBy.Property property = (OrderBy.Property)it.next();
/*  352 */           if (property.getProperty().startsWith(joinPath.getPath())) {
/*      */ 
/*      */             
/*  355 */             it.remove();
/*  356 */             joinPath.addSecJoinOrderProperty(property);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  362 */     return queryJoins;
/*      */   }
/*      */ 
/*      */   
/*  366 */   public List<OrmQueryProperties> removeLazyJoins() { return this.detail.removeSecondaryLazyQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  370 */   public void setLazyLoadManyPath(String lazyLoadManyPath) { this.lazyLoadManyPath = lazyLoadManyPath; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  377 */   public void convertManyFetchJoinsToQueryJoins(boolean allowOne, int queryBatch) { this.detail.convertManyFetchJoinsToQueryJoins(this.beanDescriptor, this.lazyLoadManyPath, allowOne, queryBatch); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectId() {
/*  385 */     this.detail.clear();
/*      */     
/*  387 */     select(this.beanDescriptor.getIdBinder().getIdProperty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> copy() {
/*  399 */     DefaultOrmQuery<T> copy = new DefaultOrmQuery<T>(this.beanType, this.server, this.expressionFactory, (String)null);
/*  400 */     copy.name = this.name;
/*  401 */     copy.includeTableJoin = this.includeTableJoin;
/*  402 */     copy.autoFetchManager = this.autoFetchManager;
/*      */     
/*  404 */     copy.query = this.query;
/*  405 */     copy.additionalWhere = this.additionalWhere;
/*  406 */     copy.additionalHaving = this.additionalHaving;
/*  407 */     copy.distinct = this.distinct;
/*  408 */     copy.backgroundFetchAfter = this.backgroundFetchAfter;
/*  409 */     copy.timeout = this.timeout;
/*  410 */     copy.mapKey = this.mapKey;
/*  411 */     copy.id = this.id;
/*  412 */     copy.vanillaMode = this.vanillaMode;
/*  413 */     copy.loadBeanCache = this.loadBeanCache;
/*  414 */     copy.useBeanCache = this.useBeanCache;
/*  415 */     copy.useQueryCache = this.useQueryCache;
/*  416 */     copy.readOnly = this.readOnly;
/*  417 */     copy.sqlSelect = this.sqlSelect;
/*  418 */     if (this.detail != null) {
/*  419 */       copy.detail = this.detail.copy();
/*      */     }
/*      */     
/*  422 */     copy.firstRow = this.firstRow;
/*  423 */     copy.maxRows = this.maxRows;
/*  424 */     copy.rawWhereClause = this.rawWhereClause;
/*  425 */     if (this.orderBy != null) {
/*  426 */       copy.orderBy = this.orderBy.copy();
/*      */     }
/*  428 */     if (this.bindParams != null) {
/*  429 */       copy.bindParams = this.bindParams.copy();
/*      */     }
/*  431 */     if (this.whereExpressions != null) {
/*  432 */       copy.whereExpressions = this.whereExpressions.copy(copy);
/*      */     }
/*  434 */     if (this.havingExpressions != null) {
/*  435 */       copy.havingExpressions = this.havingExpressions.copy(copy);
/*      */     }
/*  437 */     copy.usageProfiling = this.usageProfiling;
/*  438 */     copy.autoFetch = this.autoFetch;
/*  439 */     copy.parentNode = this.parentNode;
/*      */     
/*  441 */     return copy;
/*      */   }
/*      */ 
/*      */   
/*  445 */   public Query.Type getType() { return this.type; }
/*      */ 
/*      */ 
/*      */   
/*  449 */   public void setType(Query.Type type) { this.type = type; }
/*      */ 
/*      */ 
/*      */   
/*  453 */   public Query.UseIndex getUseIndex() { return this.useIndex; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setUseIndex(Query.UseIndex useIndex) {
/*  457 */     this.useIndex = useIndex;
/*  458 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  462 */   public String getLoadDescription() { return this.loadDescription; }
/*      */ 
/*      */ 
/*      */   
/*  466 */   public String getLoadMode() { return this.loadMode; }
/*      */ 
/*      */   
/*      */   public void setLoadDescription(String loadMode, String loadDescription) {
/*  470 */     this.loadMode = loadMode;
/*  471 */     this.loadDescription = loadDescription;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  482 */   public PersistenceContext getPersistenceContext() { return this.persistenceContext; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  493 */   public void setPersistenceContext(PersistenceContext persistenceContext) { this.persistenceContext = persistenceContext; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  500 */   public boolean isDetailEmpty() { return this.detail.isEmpty(); }
/*      */ 
/*      */ 
/*      */   
/*  504 */   public boolean isAutofetchTuned() { return this.autoFetchTuned; }
/*      */ 
/*      */ 
/*      */   
/*  508 */   public void setAutoFetchTuned(boolean autoFetchTuned) { this.autoFetchTuned = autoFetchTuned; }
/*      */ 
/*      */ 
/*      */   
/*  512 */   public Boolean isAutofetch() { return this.sqlSelect ? Boolean.FALSE : this.autoFetch; }
/*      */ 
/*      */ 
/*      */   
/*  516 */   public DefaultOrmQuery<T> setAutoFetch(boolean autoFetch) { return setAutofetch(autoFetch); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setAutofetch(boolean autoFetch) {
/*  520 */     this.autoFetch = Boolean.valueOf(autoFetch);
/*  521 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  525 */   public AutoFetchManager getAutoFetchManager() { return this.autoFetchManager; }
/*      */ 
/*      */ 
/*      */   
/*  529 */   public void setAutoFetchManager(AutoFetchManager autoFetchManager) { this.autoFetchManager = autoFetchManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deriveSharedInstance() {
/*  536 */     if (!this.sharedInstance && (
/*  537 */       Boolean.TRUE.equals(this.useQueryCache) || (Boolean.TRUE.equals(this.readOnly) && (Boolean.TRUE.equals(this.useBeanCache) || Boolean.TRUE.equals(Boolean.valueOf(this.loadBeanCache))))))
/*      */     {
/*      */ 
/*      */       
/*  541 */       this.sharedInstance = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  547 */   public boolean isSharedInstance() { return this.sharedInstance; }
/*      */ 
/*      */ 
/*      */   
/*  551 */   public void setSharedInstance() { this.sharedInstance = true; }
/*      */ 
/*      */ 
/*      */   
/*  555 */   public SpiQuery.Mode getMode() { return this.mode; }
/*      */ 
/*      */ 
/*      */   
/*  559 */   public void setMode(SpiQuery.Mode mode) { this.mode = mode; }
/*      */ 
/*      */ 
/*      */   
/*  563 */   public boolean isUsageProfiling() { return this.usageProfiling; }
/*      */ 
/*      */ 
/*      */   
/*  567 */   public void setUsageProfiling(boolean usageProfiling) { this.usageProfiling = usageProfiling; }
/*      */ 
/*      */ 
/*      */   
/*  571 */   public void setParentNode(ObjectGraphNode parentNode) { this.parentNode = parentNode; }
/*      */ 
/*      */ 
/*      */   
/*  575 */   public ObjectGraphNode getParentNode() { return this.parentNode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectGraphNode setOrigin(CallStack callStack) {
/*  582 */     ObjectGraphOrigin o = new ObjectGraphOrigin(calculateOriginQueryHash(), callStack, this.beanType.getName());
/*  583 */     this.parentNode = new ObjectGraphNode(o, null);
/*  584 */     return this.parentNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int calculateOriginQueryHash() {
/*  599 */     hc = this.beanType.getName().hashCode();
/*  600 */     return hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int calculateHash(BeanQueryRequest<?> request) {
/*  614 */     int hc = this.beanType.getName().hashCode();
/*      */     
/*  616 */     hc = hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
/*  617 */     hc = hc * 31 + ((this.useIndex == null) ? 0 : this.useIndex.hashCode());
/*      */     
/*  619 */     hc = hc * 31 + ((this.rawSql == null) ? 0 : this.rawSql.queryHash());
/*      */     
/*  621 */     hc = hc * 31 + (this.autoFetchTuned ? 31 : 0);
/*  622 */     hc = hc * 31 + (this.distinct ? 31 : 0);
/*  623 */     hc = hc * 31 + ((this.query == null) ? 0 : this.query.hashCode());
/*  624 */     hc = hc * 31 + this.detail.queryPlanHash(request);
/*      */     
/*  626 */     hc = hc * 31 + ((this.firstRow == 0) ? 0 : this.firstRow);
/*  627 */     hc = hc * 31 + ((this.maxRows == 0) ? 0 : this.maxRows);
/*  628 */     hc = hc * 31 + ((this.orderBy == null) ? 0 : this.orderBy.hash());
/*  629 */     hc = hc * 31 + ((this.rawWhereClause == null) ? 0 : this.rawWhereClause.hashCode());
/*      */     
/*  631 */     hc = hc * 31 + ((this.additionalWhere == null) ? 0 : this.additionalWhere.hashCode());
/*  632 */     hc = hc * 31 + ((this.additionalHaving == null) ? 0 : this.additionalHaving.hashCode());
/*  633 */     hc = hc * 31 + ((this.mapKey == null) ? 0 : this.mapKey.hashCode());
/*  634 */     hc = hc * 31 + ((this.id == null) ? 0 : 1);
/*      */     
/*  636 */     if (this.bindParams != null) {
/*  637 */       hc = hc * 31 + this.bindParams.getQueryPlanHash();
/*      */     }
/*      */     
/*  640 */     if (request == null) {
/*      */       
/*  642 */       hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryAutoFetchHash());
/*  643 */       hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryAutoFetchHash());
/*      */     }
/*      */     else {
/*      */       
/*  647 */       hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryPlanHash(request));
/*  648 */       hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryPlanHash(request));
/*      */     } 
/*      */     
/*  651 */     return hc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  660 */   public int queryAutofetchHash() { return calculateHash(null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryPlanHash(BeanQueryRequest<?> request) {
/*  675 */     this.queryPlanHash = calculateHash(request);
/*  676 */     return this.queryPlanHash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryBindHash() {
/*  686 */     hc = (this.id == null) ? 0 : this.id.hashCode();
/*  687 */     hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryBindHash());
/*  688 */     hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryBindHash());
/*  689 */     hc = hc * 31 + ((this.bindParams == null) ? 0 : this.bindParams.queryBindHash());
/*  690 */     return hc * 31 + ((this.contextAdditions == null) ? 0 : this.contextAdditions.hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int queryHash() {
/*  705 */     hc = this.queryPlanHash;
/*  706 */     return hc * 31 + queryBindHash();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  714 */   public String getName() { return this.name; }
/*      */ 
/*      */ 
/*      */   
/*  718 */   public boolean isSqlSelect() { return this.sqlSelect; }
/*      */ 
/*      */ 
/*      */   
/*  722 */   public boolean isRawSql() { return (this.rawSql != null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  729 */   public String getAdditionalWhere() { return this.additionalWhere; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  736 */   public int getTimeout() { return this.timeout; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  743 */   public String getAdditionalHaving() { return this.additionalHaving; }
/*      */ 
/*      */ 
/*      */   
/*  747 */   public boolean hasMaxRowsOrFirstRow() { return (this.maxRows > 0 || this.firstRow > 0); }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVanillaMode(boolean defaultVanillaMode) {
/*  752 */     if (this.vanillaMode != null) {
/*  753 */       return this.vanillaMode.booleanValue();
/*      */     }
/*  755 */     return defaultVanillaMode;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setVanillaMode(boolean vanillaMode) {
/*  759 */     this.vanillaMode = Boolean.valueOf(vanillaMode);
/*  760 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  764 */   public Boolean isReadOnly() { return this.readOnly; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setReadOnly(boolean readOnly) {
/*  768 */     this.readOnly = Boolean.valueOf(readOnly);
/*  769 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  773 */   public Boolean isUseBeanCache() { return this.useBeanCache; }
/*      */ 
/*      */ 
/*      */   
/*  777 */   public boolean isUseQueryCache() { return Boolean.TRUE.equals(this.useQueryCache); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setUseCache(boolean useBeanCache) {
/*  781 */     this.useBeanCache = Boolean.valueOf(useBeanCache);
/*  782 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setUseQueryCache(boolean useQueryCache) {
/*  786 */     this.useQueryCache = Boolean.valueOf(useQueryCache);
/*  787 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  791 */   public boolean isLoadBeanCache() { return this.loadBeanCache; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setLoadBeanCache(boolean loadBeanCache) {
/*  795 */     this.loadBeanCache = loadBeanCache;
/*  796 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> setTimeout(int secs) {
/*  800 */     this.timeout = secs;
/*  801 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setQuery(String queryString) throws PersistenceException {
/*  806 */     this.query = queryString;
/*      */     
/*  808 */     OrmQueryDetailParser parser = new OrmQueryDetailParser(queryString);
/*  809 */     parser.parse();
/*  810 */     parser.assign(this);
/*      */     
/*  812 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  816 */   protected void setOrmQueryDetail(OrmQueryDetail detail) { this.detail = detail; }
/*      */ 
/*      */   
/*  819 */   protected void setRawWhereClause(String rawWhereClause) { this.rawWhereClause = rawWhereClause; }
/*      */ 
/*      */ 
/*      */   
/*  823 */   public DefaultOrmQuery<T> setProperties(String columns) throws PersistenceException { return select(columns); }
/*      */ 
/*      */ 
/*      */   
/*  827 */   public void setDefaultSelectClause() { this.detail.setDefaultSelectClause(this.beanDescriptor); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> select(String columns) throws PersistenceException {
/*  831 */     this.detail.select(columns);
/*  832 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  836 */   public DefaultOrmQuery<T> join(String property) throws PersistenceException { return join(property, null, null); }
/*      */ 
/*      */ 
/*      */   
/*  840 */   public DefaultOrmQuery<T> join(String property, JoinConfig joinConfig) { return join(property, null, joinConfig); }
/*      */ 
/*      */ 
/*      */   
/*  844 */   public DefaultOrmQuery<T> join(String property, String columns) { return join(property, columns, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> join(String property, String columns, JoinConfig joinConfig) {
/*      */     FetchConfig c;
/*  850 */     if (joinConfig == null) {
/*  851 */       c = null;
/*      */     } else {
/*  853 */       c = new FetchConfig();
/*  854 */       c.lazy(joinConfig.getLazyBatchSize());
/*  855 */       if (joinConfig.isQueryAll()) {
/*  856 */         c.query(joinConfig.getQueryBatchSize());
/*      */       } else {
/*  858 */         c.queryFirst(joinConfig.getQueryBatchSize());
/*      */       } 
/*      */     } 
/*      */     
/*  862 */     this.detail.addFetch(property, columns, c);
/*  863 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  867 */   public DefaultOrmQuery<T> fetch(String property) throws PersistenceException { return fetch(property, null, null); }
/*      */ 
/*      */ 
/*      */   
/*  871 */   public DefaultOrmQuery<T> fetch(String property, FetchConfig joinConfig) { return fetch(property, null, joinConfig); }
/*      */ 
/*      */ 
/*      */   
/*  875 */   public DefaultOrmQuery<T> fetch(String property, String columns) { return fetch(property, columns, null); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> fetch(String property, String columns, FetchConfig config) {
/*  879 */     this.detail.addFetch(property, columns, config);
/*  880 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  887 */   public List<Object> findIds() { return this.server.findIds(this, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  894 */   public int findRowCount() { return this.server.findRowCount(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  898 */   public void findVisit(QueryResultVisitor<T> visitor) { this.server.findVisit(this, visitor, null); }
/*      */ 
/*      */ 
/*      */   
/*  902 */   public QueryIterator<T> findIterate() { return this.server.findIterate(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  906 */   public List<T> findList() { return this.server.findList(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  910 */   public Set<T> findSet() { return this.server.findSet(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  914 */   public Map<?, T> findMap() { return this.server.findMap(this, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) {
/*  919 */     setMapKey(keyProperty);
/*  920 */     return findMap();
/*      */   }
/*      */ 
/*      */   
/*  924 */   public T findUnique() { return (T)this.server.findUnique(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  928 */   public FutureIds<T> findFutureIds() { return this.server.findFutureIds(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  932 */   public FutureList<T> findFutureList() { return this.server.findFutureList(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  936 */   public FutureRowCount<T> findFutureRowCount() { return this.server.findFutureRowCount(this, null); }
/*      */ 
/*      */ 
/*      */   
/*  940 */   public PagingList<T> findPagingList(int pageSize) { return this.server.findPagingList(this, null, pageSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setParameter(int position, Object value) {
/*  949 */     if (this.bindParams == null) {
/*  950 */       this.bindParams = new BindParams();
/*      */     }
/*  952 */     this.bindParams.setParameter(position, value);
/*  953 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setParameter(String name, Object value) {
/*  961 */     if (this.bindParams == null) {
/*  962 */       this.bindParams = new BindParams();
/*      */     }
/*  964 */     this.bindParams.setParameter(name, value);
/*  965 */     return this;
/*      */   }
/*      */ 
/*      */   
/*  969 */   public OrderBy<T> getOrderBy() { return this.orderBy; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  976 */   public String getRawWhereClause() { return this.rawWhereClause; }
/*      */ 
/*      */ 
/*      */   
/*  980 */   public OrderBy<T> orderBy() { return order(); }
/*      */ 
/*      */   
/*      */   public OrderBy<T> order() {
/*  984 */     if (this.orderBy == null) {
/*  985 */       this.orderBy = new OrderBy(this, null);
/*      */     }
/*  987 */     return this.orderBy;
/*      */   }
/*      */ 
/*      */   
/*  991 */   public DefaultOrmQuery<T> setOrderBy(String orderByClause) throws PersistenceException { return order(orderByClause); }
/*      */ 
/*      */ 
/*      */   
/*  995 */   public DefaultOrmQuery<T> orderBy(String orderByClause) throws PersistenceException { return order(orderByClause); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> order(String orderByClause) throws PersistenceException {
/*  999 */     if (orderByClause == null || orderByClause.trim().length() == 0) {
/* 1000 */       this.orderBy = null;
/*      */     } else {
/* 1002 */       this.orderBy = new OrderBy(this, orderByClause);
/*      */     } 
/* 1004 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1008 */   public DefaultOrmQuery<T> setOrderBy(OrderBy<T> orderBy) { return setOrder(orderBy); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setOrder(OrderBy<T> orderBy) {
/* 1012 */     this.orderBy = orderBy;
/* 1013 */     if (orderBy != null) {
/* 1014 */       orderBy.setQuery(this);
/*      */     }
/* 1016 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1024 */   public boolean isDistinct() { return this.distinct; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setDistinct(boolean isDistinct) {
/* 1031 */     this.distinct = isDistinct;
/* 1032 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1039 */   public QueryListener<T> getListener() { return this.queryListener; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setListener(QueryListener<T> queryListener) {
/* 1051 */     this.queryListener = queryListener;
/* 1052 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1056 */   public Class<T> getBeanType() { return this.beanType; }
/*      */ 
/*      */ 
/*      */   
/* 1060 */   public void setDetail(OrmQueryDetail detail) { this.detail = detail; }
/*      */ 
/*      */ 
/*      */   
/* 1064 */   public boolean tuneFetchProperties(OrmQueryDetail tunedDetail) { return this.detail.tuneFetchProperties(tunedDetail); }
/*      */ 
/*      */ 
/*      */   
/* 1068 */   public OrmQueryDetail getDetail() { return this.detail; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1076 */   public final ArrayList<EntityBean> getContextAdditions() { return this.contextAdditions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void contextAdd(EntityBean bean) {
/* 1086 */     if (this.contextAdditions == null) {
/* 1087 */       this.contextAdditions = new ArrayList();
/*      */     }
/* 1089 */     this.contextAdditions.add(bean);
/*      */   }
/*      */ 
/*      */   
/* 1093 */   public String toString() { return "Query [" + this.whereExpressions + "]"; }
/*      */ 
/*      */ 
/*      */   
/* 1097 */   public TableJoin getIncludeTableJoin() { return this.includeTableJoin; }
/*      */ 
/*      */ 
/*      */   
/* 1101 */   public void setIncludeTableJoin(TableJoin includeTableJoin) { this.includeTableJoin = includeTableJoin; }
/*      */ 
/*      */ 
/*      */   
/* 1105 */   public int getFirstRow() { return this.firstRow; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setFirstRow(int firstRow) {
/* 1109 */     this.firstRow = firstRow;
/* 1110 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1114 */   public int getMaxRows() { return this.maxRows; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setMaxRows(int maxRows) {
/* 1118 */     this.maxRows = maxRows;
/* 1119 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1123 */   public String getMapKey() { return this.mapKey; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setMapKey(String mapKey) throws PersistenceException {
/* 1127 */     this.mapKey = mapKey;
/* 1128 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1132 */   public int getBackgroundFetchAfter() { return this.backgroundFetchAfter; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 1136 */     this.backgroundFetchAfter = backgroundFetchAfter;
/* 1137 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1141 */   public Object getId() { return this.id; }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> setId(Object id) {
/* 1145 */     this.id = id;
/* 1146 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1150 */   public BindParams getBindParams() { return this.bindParams; }
/*      */ 
/*      */ 
/*      */   
/* 1154 */   public String getQuery() { return this.query; }
/*      */ 
/*      */ 
/*      */   
/* 1158 */   public DefaultOrmQuery<T> addWhere(String addToWhereClause) throws PersistenceException { return where(addToWhereClause); }
/*      */ 
/*      */ 
/*      */   
/* 1162 */   public DefaultOrmQuery<T> addWhere(Expression expression) { return where(expression); }
/*      */ 
/*      */ 
/*      */   
/* 1166 */   public ExpressionList<T> addWhere() { return where(); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> where(String addToWhereClause) throws PersistenceException {
/* 1170 */     if (this.additionalWhere == null) {
/* 1171 */       this.additionalWhere = addToWhereClause;
/*      */     } else {
/* 1173 */       this.additionalWhere += " " + addToWhereClause;
/*      */     } 
/* 1175 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> where(Expression expression) {
/* 1179 */     if (this.whereExpressions == null) {
/* 1180 */       this.whereExpressions = new DefaultExpressionList(this, null);
/*      */     }
/* 1182 */     this.whereExpressions.add(expression);
/* 1183 */     return this;
/*      */   }
/*      */   
/*      */   public ExpressionList<T> where() {
/* 1187 */     if (this.whereExpressions == null) {
/* 1188 */       this.whereExpressions = new DefaultExpressionList(this, null);
/*      */     }
/* 1190 */     return this.whereExpressions;
/*      */   }
/*      */ 
/*      */   
/*      */   public ExpressionList<T> filterMany(String prop) {
/* 1195 */     OrmQueryProperties chunk = this.detail.getChunk(prop, true);
/* 1196 */     return chunk.filterMany(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFilterMany(String prop, ExpressionList<?> filterMany) {
/* 1201 */     if (filterMany != null) {
/* 1202 */       OrmQueryProperties chunk = this.detail.getChunk(prop, true);
/* 1203 */       chunk.setFilterMany((SpiExpressionList)filterMany);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1208 */   public DefaultOrmQuery<T> addHaving(String addToHavingClause) throws PersistenceException { return having(addToHavingClause); }
/*      */ 
/*      */ 
/*      */   
/* 1212 */   public DefaultOrmQuery<T> addHaving(Expression expression) { return having(expression); }
/*      */ 
/*      */ 
/*      */   
/* 1216 */   public ExpressionList<T> addHaving() { return having(); }
/*      */ 
/*      */   
/*      */   public DefaultOrmQuery<T> having(String addToHavingClause) throws PersistenceException {
/* 1220 */     if (this.additionalHaving == null) {
/* 1221 */       this.additionalHaving = addToHavingClause;
/*      */     } else {
/* 1223 */       this.additionalHaving += " " + addToHavingClause;
/*      */     } 
/* 1225 */     return this;
/*      */   }
/*      */   
/*      */   public DefaultOrmQuery<T> having(Expression expression) {
/* 1229 */     if (this.havingExpressions == null) {
/* 1230 */       this.havingExpressions = new DefaultExpressionList(this, null);
/*      */     }
/* 1232 */     this.havingExpressions.add(expression);
/* 1233 */     return this;
/*      */   }
/*      */   
/*      */   public ExpressionList<T> having() {
/* 1237 */     if (this.havingExpressions == null) {
/* 1238 */       this.havingExpressions = new DefaultExpressionList(this, null);
/*      */     }
/* 1240 */     return this.havingExpressions;
/*      */   }
/*      */ 
/*      */   
/* 1244 */   public SpiExpressionList<T> getHavingExpressions() { return this.havingExpressions; }
/*      */ 
/*      */ 
/*      */   
/* 1248 */   public SpiExpressionList<T> getWhereExpressions() { return this.whereExpressions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean createOwnTransaction() {
/* 1255 */     if (this.futureFetch)
/*      */     {
/*      */       
/* 1258 */       return false;
/*      */     }
/* 1260 */     if (this.backgroundFetchAfter > 0 || this.queryListener != null)
/*      */     {
/*      */       
/* 1263 */       return true;
/*      */     }
/* 1265 */     return false;
/*      */   }
/*      */ 
/*      */   
/* 1269 */   public String getGeneratedSql() { return this.generatedSql; }
/*      */ 
/*      */ 
/*      */   
/* 1273 */   public void setGeneratedSql(String generatedSql) { this.generatedSql = generatedSql; }
/*      */ 
/*      */   
/*      */   public Query<T> setBufferFetchSizeHint(int bufferFetchSizeHint) {
/* 1277 */     this.bufferFetchSizeHint = bufferFetchSizeHint;
/* 1278 */     return this;
/*      */   }
/*      */ 
/*      */   
/* 1282 */   public int getBufferFetchSizeHint() { return this.bufferFetchSizeHint; }
/*      */ 
/*      */ 
/*      */   
/* 1286 */   public void setBeanCollectionTouched(BeanCollectionTouched notify) { this.beanCollectionTouched = notify; }
/*      */ 
/*      */ 
/*      */   
/* 1290 */   public BeanCollectionTouched getBeanCollectionTouched() { return this.beanCollectionTouched; }
/*      */ 
/*      */ 
/*      */   
/* 1294 */   public List<Object> getIdList() { return this.partialIds; }
/*      */ 
/*      */ 
/*      */   
/* 1298 */   public void setIdList(List<Object> partialIds) { this.partialIds = partialIds; }
/*      */ 
/*      */ 
/*      */   
/* 1302 */   public boolean isFutureFetch() { return this.futureFetch; }
/*      */ 
/*      */ 
/*      */   
/* 1306 */   public void setFutureFetch(boolean backgroundFetch) { this.futureFetch = backgroundFetch; }
/*      */ 
/*      */   
/*      */   public void setCancelableQuery(CancelableQuery cancelableQuery) {
/* 1310 */     synchronized (this) {
/* 1311 */       this.cancelableQuery = cancelableQuery;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cancel() {
/* 1316 */     synchronized (this) {
/* 1317 */       this.cancelled = true;
/* 1318 */       if (this.cancelableQuery != null) {
/* 1319 */         this.cancelableQuery.cancel();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isCancelled() {
/* 1325 */     synchronized (this) {
/* 1326 */       return this.cancelled;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\DefaultOrmQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */