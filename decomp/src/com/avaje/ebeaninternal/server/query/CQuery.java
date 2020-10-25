/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.bean.NodeUsageListener;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadContext;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelpFactory;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CQuery<T>
/*     */   extends Object
/*     */   implements DbReadContext, CancelableQuery
/*     */ {
/*  83 */   private static final Logger logger = Logger.getLogger(CQuery.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GLOBAL_ROW_LIMIT = 1000000;
/*     */ 
/*     */ 
/*     */   
/*     */   private int rowCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private int loadedBeanCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean noMoreRows;
/*     */ 
/*     */ 
/*     */   
/*     */   private Object loadedBeanId;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean loadedBeanChanged;
/*     */ 
/*     */   
/*     */   private Object loadedBean;
/*     */ 
/*     */   
/*     */   private Object prevLoadedBean;
/*     */ 
/*     */   
/*     */   private Object loadedManyBean;
/*     */ 
/*     */   
/*     */   private Object prevDetailCollection;
/*     */ 
/*     */   
/*     */   private Object currentDetailCollection;
/*     */ 
/*     */   
/*     */   private final BeanCollection<T> collection;
/*     */ 
/*     */   
/*     */   private final BeanCollectionHelp<T> help;
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<T> request;
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<T> desc;
/*     */ 
/*     */   
/*     */   private final SpiQuery<T> query;
/*     */ 
/*     */   
/*     */   private final OrmQueryDetail queryDetail;
/*     */ 
/*     */   
/*     */   private final QueryListener<T> queryListener;
/*     */ 
/*     */   
/*     */   private Map<String, String> currentPathMap;
/*     */ 
/*     */   
/*     */   private String currentPrefix;
/*     */ 
/*     */   
/*     */   private final boolean manyIncluded;
/*     */ 
/*     */   
/*     */   private final CQueryPredicates predicates;
/*     */ 
/*     */   
/*     */   private final SqlTree sqlTree;
/*     */ 
/*     */   
/*     */   private final boolean rawSql;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private final String logWhereSql;
/*     */ 
/*     */   
/*     */   private final boolean rowNumberIncluded;
/*     */ 
/*     */   
/*     */   private final SqlTreeNode rootNode;
/*     */ 
/*     */   
/*     */   private final BeanPropertyAssocMany<?> manyProperty;
/*     */ 
/*     */   
/*     */   private final ElPropertyValue manyPropertyEl;
/*     */ 
/*     */   
/*     */   private final int backgroundFetchAfter;
/*     */ 
/*     */   
/*     */   private final int maxRowsLimit;
/*     */ 
/*     */   
/*     */   private boolean hasHitBackgroundFetchAfter;
/*     */ 
/*     */   
/*     */   private final PersistenceContext persistenceContext;
/*     */ 
/*     */   
/*     */   private DataReader dataReader;
/*     */ 
/*     */   
/*     */   private PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private boolean cancelled;
/*     */ 
/*     */   
/*     */   private String bindLog;
/*     */ 
/*     */   
/*     */   private final CQueryPlan queryPlan;
/*     */ 
/*     */   
/*     */   private long startNano;
/*     */ 
/*     */   
/*     */   private final SpiQuery.Mode queryMode;
/*     */ 
/*     */   
/*     */   private final boolean autoFetchProfiling;
/*     */ 
/*     */   
/*     */   private final ObjectGraphNode autoFetchParentNode;
/*     */ 
/*     */   
/*     */   private final AutoFetchManager autoFetchManager;
/*     */ 
/*     */   
/*     */   private final WeakReference<NodeUsageListener> autoFetchManagerRef;
/*     */ 
/*     */   
/*     */   private final HashMap<String, ReferenceOptions> referenceOptionsMap;
/*     */ 
/*     */   
/*     */   private int executionTimeMicros;
/*     */ 
/*     */   
/*     */   private final int parentState;
/*     */ 
/*     */   
/*     */   private final SpiExpressionList<?> filterMany;
/*     */ 
/*     */   
/*     */   private BeanCollectionAdd currentDetailAdd;
/*     */ 
/*     */ 
/*     */   
/*     */   public CQuery(OrmQueryRequest<T> request, CQueryPredicates predicates, CQueryPlan queryPlan) {
/* 244 */     this.referenceOptionsMap = new HashMap();
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
/* 257 */     this.request = request;
/* 258 */     this.queryPlan = queryPlan;
/* 259 */     this.query = request.getQuery();
/* 260 */     this.queryDetail = this.query.getDetail();
/* 261 */     this.queryMode = this.query.getMode();
/*     */     
/* 263 */     this.parentState = request.getParentState();
/*     */     
/* 265 */     this.autoFetchManager = this.query.getAutoFetchManager();
/* 266 */     this.autoFetchProfiling = (this.autoFetchManager != null);
/* 267 */     this.autoFetchParentNode = this.autoFetchProfiling ? this.query.getParentNode() : null;
/* 268 */     this.autoFetchManagerRef = this.autoFetchProfiling ? new WeakReference(this.autoFetchManager) : null;
/*     */ 
/*     */ 
/*     */     
/* 272 */     this.query.setGeneratedSql(queryPlan.getSql());
/*     */     
/* 274 */     this.sqlTree = queryPlan.getSqlTree();
/* 275 */     this.rootNode = this.sqlTree.getRootNode();
/*     */     
/* 277 */     this.manyProperty = this.sqlTree.getManyProperty();
/* 278 */     this.manyPropertyEl = this.sqlTree.getManyPropertyEl();
/* 279 */     this.manyIncluded = this.sqlTree.isManyIncluded();
/* 280 */     if (this.manyIncluded) {
/*     */       
/* 282 */       String manyPropertyName = this.sqlTree.getManyPropertyName();
/* 283 */       OrmQueryProperties chunk = this.query.getDetail().getChunk(manyPropertyName, false);
/* 284 */       this.filterMany = chunk.getFilterMany();
/*     */     } else {
/* 286 */       this.filterMany = null;
/*     */     } 
/*     */     
/* 289 */     this.sql = queryPlan.getSql();
/* 290 */     this.rawSql = queryPlan.isRawSql();
/* 291 */     this.rowNumberIncluded = queryPlan.isRowNumberIncluded();
/* 292 */     this.logWhereSql = queryPlan.getLogWhereSql();
/* 293 */     this.desc = request.getBeanDescriptor();
/* 294 */     this.predicates = predicates;
/*     */     
/* 296 */     this.queryListener = this.query.getListener();
/* 297 */     if (this.queryListener == null) {
/*     */       
/* 299 */       this.persistenceContext = request.getPersistenceContext();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 304 */       this.persistenceContext = new DefaultPersistenceContext();
/*     */     } 
/*     */     
/* 307 */     this.maxRowsLimit = (this.query.getMaxRows() > 0) ? this.query.getMaxRows() : 1000000;
/* 308 */     this.backgroundFetchAfter = (this.query.getBackgroundFetchAfter() > 0) ? this.query.getBackgroundFetchAfter() : Integer.MAX_VALUE;
/*     */     
/* 310 */     this.help = createHelp(request);
/* 311 */     this.collection = (BeanCollection)((this.help != null) ? this.help.createEmpty(false) : null);
/*     */   }
/*     */   
/*     */   private BeanCollectionHelp<T> createHelp(OrmQueryRequest<T> request) {
/* 315 */     if (request.isFindById()) {
/* 316 */       return null;
/*     */     }
/* 318 */     Query.Type manyType = request.getQuery().getType();
/* 319 */     if (manyType == null)
/*     */     {
/* 321 */       return null;
/*     */     }
/* 323 */     return BeanCollectionHelpFactory.create(request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 328 */   public int getParentState() { return this.parentState; }
/*     */ 
/*     */   
/*     */   public void propagateState(Object e) {
/* 332 */     if (this.parentState != 0 && 
/* 333 */       e instanceof EntityBean) {
/* 334 */       ((EntityBean)e)._ebean_getIntercept().setState(this.parentState);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 340 */   public DataReader getDataReader() { return this.dataReader; }
/*     */ 
/*     */ 
/*     */   
/* 344 */   public SpiQuery.Mode getQueryMode() { return this.queryMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 351 */   public boolean isVanillaMode() { return this.request.isVanillaMode(); }
/*     */ 
/*     */ 
/*     */   
/* 355 */   public CQueryPredicates getPredicates() { return this.predicates; }
/*     */ 
/*     */ 
/*     */   
/* 359 */   public LoadContext getGraphContext() { return this.request.getGraphContext(); }
/*     */ 
/*     */ 
/*     */   
/* 363 */   public SpiOrmQueryRequest<?> getQueryRequest() { return this.request; }
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 367 */     synchronized (this) {
/* 368 */       this.cancelled = true;
/* 369 */       if (this.pstmt != null) {
/*     */         try {
/* 371 */           this.pstmt.cancel();
/* 372 */         } catch (SQLException e) {
/* 373 */           String msg = "Error cancelling query";
/* 374 */           throw new PersistenceException(msg, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean prepareBindExecuteQuery() {
/* 382 */     synchronized (this) {
/* 383 */       if (this.cancelled || this.query.isCancelled()) {
/*     */         
/* 385 */         this.cancelled = true;
/* 386 */         return false;
/*     */       } 
/*     */       
/* 389 */       this.startNano = System.nanoTime();
/*     */       
/* 391 */       if (this.request.isLuceneQuery()) {
/*     */         
/* 393 */         this.dataReader = this.queryPlan.createDataReader(null);
/*     */       }
/*     */       else {
/*     */         
/* 397 */         SpiTransaction t = this.request.getTransaction();
/* 398 */         Connection conn = t.getInternalConnection();
/* 399 */         this.pstmt = conn.prepareStatement(this.sql);
/*     */         
/* 401 */         if (this.query.getTimeout() > 0) {
/* 402 */           this.pstmt.setQueryTimeout(this.query.getTimeout());
/*     */         }
/* 404 */         if (this.query.getBufferFetchSizeHint() > 0) {
/* 405 */           this.pstmt.setFetchSize(this.query.getBufferFetchSizeHint());
/*     */         }
/*     */         
/* 408 */         DataBind dataBind = new DataBind(this.pstmt);
/*     */ 
/*     */         
/* 411 */         this.queryPlan.bindEncryptedProperties(dataBind);
/*     */         
/* 413 */         this.bindLog = this.predicates.bind(dataBind);
/*     */ 
/*     */         
/* 416 */         ResultSet rset = this.pstmt.executeQuery();
/* 417 */         this.dataReader = this.queryPlan.createDataReader(rset);
/*     */       } 
/* 419 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 432 */       if (this.dataReader != null) {
/* 433 */         this.dataReader.close();
/* 434 */         this.dataReader = null;
/*     */       } 
/* 436 */     } catch (SQLException e) {
/* 437 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */     try {
/* 440 */       if (this.pstmt != null) {
/* 441 */         this.pstmt.close();
/* 442 */         this.pstmt = null;
/*     */       } 
/* 444 */     } catch (SQLException e) {
/* 445 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOptions getReferenceOptionsFor(BeanPropertyAssocOne<?> beanProp) {
/* 454 */     String beanPropName = beanProp.getName();
/* 455 */     if (this.currentPrefix != null) {
/* 456 */       beanPropName = this.currentPrefix + "." + beanPropName;
/*     */     }
/* 458 */     ReferenceOptions opt = (ReferenceOptions)this.referenceOptionsMap.get(beanPropName);
/* 459 */     if (opt == null) {
/* 460 */       OrmQueryProperties chunk = this.queryDetail.getChunk(beanPropName, false);
/* 461 */       if (chunk != null)
/*     */       {
/* 463 */         opt = chunk.getReferenceOptions();
/*     */       }
/* 465 */       if (opt == null)
/*     */       {
/* 467 */         opt = beanProp.getTargetDescriptor().getReferenceOptions();
/*     */       }
/* 469 */       this.referenceOptionsMap.put(beanPropName, opt);
/*     */     } 
/*     */     
/* 472 */     return opt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 479 */   public PersistenceContext getPersistenceContext() { return this.persistenceContext; }
/*     */ 
/*     */   
/*     */   public void setLoadedBean(Object bean, Object id) {
/* 483 */     if (id == null || !id.equals(this.loadedBeanId)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 489 */       if (this.manyIncluded) {
/* 490 */         if (this.rowCount > 1) {
/* 491 */           this.loadedBeanChanged = true;
/*     */         }
/* 493 */         this.prevLoadedBean = this.loadedBean;
/* 494 */         this.loadedBeanId = id;
/*     */       } 
/* 496 */       this.loadedBean = bean;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 501 */   public void setLoadedManyBean(Object manyValue) { this.loadedManyBean = manyValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLoadedBean() {
/* 509 */     if (this.manyIncluded) {
/* 510 */       if (this.prevDetailCollection instanceof BeanCollection) {
/* 511 */         ((BeanCollection)this.prevDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
/*     */       }
/* 513 */       else if (this.currentDetailCollection instanceof BeanCollection) {
/* 514 */         ((BeanCollection)this.currentDetailCollection).setModifyListening(this.manyProperty.getModifyListenMode());
/*     */       } 
/*     */     }
/*     */     
/* 518 */     if (this.prevLoadedBean != null) {
/* 519 */       return (T)this.prevLoadedBean;
/*     */     }
/* 521 */     return (T)this.loadedBean;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasMoreRows() {
/* 526 */     synchronized (this) {
/* 527 */       if (this.cancelled) {
/* 528 */         return false;
/*     */       }
/* 530 */       return this.dataReader.next();
/*     */     } 
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
/*     */   private boolean readRow() {
/* 543 */     synchronized (this) {
/* 544 */       if (this.cancelled) {
/* 545 */         return false;
/*     */       }
/*     */       
/* 548 */       if (!this.dataReader.next()) {
/* 549 */         return false;
/*     */       }
/*     */       
/* 552 */       this.rowCount++;
/* 553 */       this.dataReader.resetColumnPosition();
/*     */       
/* 555 */       if (this.rowNumberIncluded)
/*     */       {
/* 557 */         this.dataReader.incrementPos(1);
/*     */       }
/*     */       
/* 560 */       this.rootNode.load(this, null, this.parentState);
/*     */       
/* 562 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 567 */   public int getQueryExecutionTimeMicros() { return this.executionTimeMicros; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readBean() {
/* 572 */     boolean result = readBeanInternal(true);
/*     */     
/* 574 */     updateExecutionStatistics();
/*     */     
/* 576 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readBeanInternal(boolean inForeground) throws SQLException {
/* 581 */     if (this.loadedBeanCount >= this.maxRowsLimit) {
/* 582 */       this.collection.setHasMoreRows(hasMoreRows());
/* 583 */       return false;
/*     */     } 
/*     */     
/* 586 */     if (inForeground && this.loadedBeanCount >= this.backgroundFetchAfter) {
/* 587 */       this.hasHitBackgroundFetchAfter = true;
/* 588 */       this.collection.setFinishedFetch(false);
/* 589 */       return false;
/*     */     } 
/*     */     
/* 592 */     if (!this.manyIncluded)
/*     */     {
/* 594 */       return readRow();
/*     */     }
/*     */     
/* 597 */     if (this.noMoreRows) {
/* 598 */       return false;
/*     */     }
/*     */     
/* 601 */     if (this.rowCount == 0) {
/* 602 */       if (!readRow())
/*     */       {
/* 604 */         return false;
/*     */       }
/* 606 */       createNewDetailCollection();
/*     */     } 
/*     */ 
/*     */     
/* 610 */     if (readIntoCurrentDetailCollection()) {
/* 611 */       createNewDetailCollection();
/*     */       
/* 613 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 617 */     this.prevDetailCollection = null;
/* 618 */     this.prevLoadedBean = null;
/* 619 */     this.noMoreRows = true;
/* 620 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean readIntoCurrentDetailCollection() {
/* 625 */     while (readRow()) {
/* 626 */       if (this.loadedBeanChanged) {
/* 627 */         this.loadedBeanChanged = false;
/* 628 */         return true;
/*     */       } 
/* 630 */       addToCurrentDetailCollection();
/*     */     } 
/*     */     
/* 633 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNewDetailCollection() {
/* 639 */     this.prevDetailCollection = this.currentDetailCollection;
/* 640 */     if (this.queryMode.equals(SpiQuery.Mode.LAZYLOAD_MANY)) {
/*     */       
/* 642 */       this.currentDetailCollection = this.manyPropertyEl.elGetValue(this.loadedBean);
/*     */     } else {
/*     */       
/* 645 */       this.currentDetailCollection = this.manyProperty.createEmpty(this.request.isVanillaMode());
/* 646 */       this.manyPropertyEl.elSetValue(this.loadedBean, this.currentDetailCollection, false, false);
/*     */     } 
/*     */     
/* 649 */     if (this.filterMany != null && !this.request.isVanillaMode())
/*     */     {
/* 651 */       ((BeanCollection)this.currentDetailCollection).setFilterMany(this.filterMany);
/*     */     }
/*     */ 
/*     */     
/* 655 */     this.currentDetailAdd = this.manyProperty.getBeanCollectionAdd(this.currentDetailCollection, null);
/* 656 */     addToCurrentDetailCollection();
/*     */   }
/*     */   
/*     */   private void addToCurrentDetailCollection() {
/* 660 */     if (this.loadedManyBean != null) {
/* 661 */       this.currentDetailAdd.addBean(this.loadedManyBean);
/*     */     }
/*     */   }
/*     */   
/*     */   public BeanCollection<T> continueFetchingInBackground() throws SQLException {
/* 666 */     readTheRows(false);
/* 667 */     this.collection.setFinishedFetch(true);
/* 668 */     return this.collection;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanCollection<T> readCollection() throws SQLException {
/* 673 */     readTheRows(true);
/*     */     
/* 675 */     updateExecutionStatistics();
/*     */     
/* 677 */     return this.collection;
/*     */   }
/*     */   
/*     */   protected void updateExecutionStatistics() {
/*     */     try {
/* 682 */       long exeNano = System.nanoTime() - this.startNano;
/* 683 */       this.executionTimeMicros = (int)exeNano / 1000;
/*     */       
/* 685 */       if (this.autoFetchProfiling) {
/* 686 */         this.autoFetchManager.collectQueryInfo(this.autoFetchParentNode, this.loadedBeanCount, this.executionTimeMicros);
/*     */       }
/* 688 */       this.queryPlan.executionTime(this.loadedBeanCount, this.executionTimeMicros);
/*     */     }
/* 690 */     catch (Exception e) {
/* 691 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public QueryIterator<T> readIterate(int bufferSize, OrmQueryRequest<T> request) {
/* 697 */     if (bufferSize > 0) {
/* 698 */       return new CQueryIteratorWithBuffer(this, request, bufferSize);
/*     */     }
/*     */     
/* 701 */     return new CQueryIteratorSimple(this, request);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readTheRows(boolean inForeground) throws SQLException {
/* 706 */     while (hasNextBean(inForeground)) {
/* 707 */       if (this.queryListener != null) {
/* 708 */         this.queryListener.process(getLoadedBean());
/*     */         
/*     */         continue;
/*     */       } 
/* 712 */       this.help.add(this.collection, getLoadedBean());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasNextBean(boolean inForeground) throws SQLException {
/* 720 */     if (!readBeanInternal(inForeground)) {
/* 721 */       return false;
/*     */     }
/*     */     
/* 724 */     this.loadedBeanCount++;
/* 725 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLoadedRowDetail() {
/* 730 */     if (!this.manyIncluded) {
/* 731 */       return String.valueOf(this.rowCount);
/*     */     }
/* 733 */     return this.loadedBeanCount + ":" + this.rowCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(String path, EntityBeanIntercept ebi) {
/* 739 */     path = getPath(path);
/* 740 */     this.request.getGraphContext().register(path, ebi);
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(String path, BeanCollection<?> bc) {
/* 745 */     path = getPath(path);
/* 746 */     this.request.getGraphContext().register(path, bc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 751 */   public boolean useBackgroundToContinueFetch() { return this.hasHitBackgroundFetchAfter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 758 */   public String getName() { return this.query.getName(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 765 */   public boolean isRawSql() { return this.rawSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 772 */   public String getLogWhereSql() { return this.logWhereSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 780 */   public BeanPropertyAssocMany<?> getManyProperty() { return this.manyProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 787 */   public String getSummary() { return this.sqlTree.getSummary(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 795 */   public SqlTree getSqlTree() { return this.sqlTree; }
/*     */ 
/*     */ 
/*     */   
/* 799 */   public String getBindLog() { return this.bindLog; }
/*     */ 
/*     */ 
/*     */   
/* 803 */   public SpiTransaction getTransaction() { return this.request.getTransaction(); }
/*     */ 
/*     */ 
/*     */   
/* 807 */   public String getBeanType() { return this.desc.getFullName(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 814 */   public String getBeanName() { return this.desc.getName(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 821 */   public String getGeneratedSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 829 */   public PersistenceException createPersistenceException(SQLException e) { return createPersistenceException(e, getTransaction(), this.bindLog, this.sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PersistenceException createPersistenceException(SQLException e, SpiTransaction t, String bindLog, String sql) {
/* 837 */     if (t.isLogSummary()) {
/*     */       
/* 839 */       String errMsg = StringHelper.replaceStringMulti(e.getMessage(), new String[] { "\r", "\n" }, "\\n ");
/* 840 */       String msg = "ERROR executing query:   bindLog[" + bindLog + "] error[" + errMsg + "]";
/* 841 */       t.logInternal(msg);
/*     */     } 
/*     */ 
/*     */     
/* 845 */     t.getConnection();
/*     */ 
/*     */     
/* 848 */     String m = Message.msg("fetch.sqlerror", e.getMessage(), bindLog, sql);
/* 849 */     return new PersistenceException(m, e);
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
/* 862 */   public boolean isAutoFetchProfiling() { return (this.autoFetchProfiling && this.query.isUsageProfiling()); }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPath(String propertyName) {
/* 867 */     if (this.currentPrefix == null)
/* 868 */       return propertyName; 
/* 869 */     if (propertyName == null) {
/* 870 */       return this.currentPrefix;
/*     */     }
/*     */     
/* 873 */     String path = (String)this.currentPathMap.get(propertyName);
/* 874 */     if (path != null) {
/* 875 */       return path;
/*     */     }
/* 877 */     return this.currentPrefix + "." + propertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void profileBean(EntityBeanIntercept ebi, String prefix) {
/* 884 */     ObjectGraphNode node = this.request.getGraphContext().getObjectGraphNode(prefix);
/*     */     
/* 886 */     ebi.setNodeUsageCollector(new NodeUsageCollector(node, this.autoFetchManagerRef));
/*     */   }
/*     */   
/*     */   public void setCurrentPrefix(String currentPrefix, Map<String, String> currentPathMap) {
/* 890 */     this.currentPrefix = currentPrefix;
/* 891 */     this.currentPathMap = currentPathMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */