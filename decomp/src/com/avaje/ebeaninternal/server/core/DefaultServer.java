/*      */ package com.avaje.ebeaninternal.server.core;
/*      */ 
/*      */ import com.avaje.ebean.AdminAutofetch;
/*      */ import com.avaje.ebean.AdminLogging;
/*      */ import com.avaje.ebean.BackgroundExecutor;
/*      */ import com.avaje.ebean.BeanState;
/*      */ import com.avaje.ebean.CallableSql;
/*      */ import com.avaje.ebean.ExpressionFactory;
/*      */ import com.avaje.ebean.Filter;
/*      */ import com.avaje.ebean.FutureIds;
/*      */ import com.avaje.ebean.FutureList;
/*      */ import com.avaje.ebean.FutureRowCount;
/*      */ import com.avaje.ebean.InvalidValue;
/*      */ import com.avaje.ebean.PagingList;
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.QueryIterator;
/*      */ import com.avaje.ebean.QueryResultVisitor;
/*      */ import com.avaje.ebean.SqlFutureList;
/*      */ import com.avaje.ebean.SqlQuery;
/*      */ import com.avaje.ebean.SqlRow;
/*      */ import com.avaje.ebean.SqlUpdate;
/*      */ import com.avaje.ebean.Transaction;
/*      */ import com.avaje.ebean.TxCallable;
/*      */ import com.avaje.ebean.TxIsolation;
/*      */ import com.avaje.ebean.TxRunnable;
/*      */ import com.avaje.ebean.TxScope;
/*      */ import com.avaje.ebean.TxType;
/*      */ import com.avaje.ebean.Update;
/*      */ import com.avaje.ebean.ValuePair;
/*      */ import com.avaje.ebean.bean.BeanCollection;
/*      */ import com.avaje.ebean.bean.CallStack;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*      */ import com.avaje.ebean.bean.PersistenceContext;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.EncryptKeyManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.ldap.LdapConfig;
/*      */ import com.avaje.ebean.config.lucene.LuceneIndex;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebean.text.csv.CsvReader;
/*      */ import com.avaje.ebean.text.json.JsonContext;
/*      */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*      */ import com.avaje.ebeaninternal.api.LoadManyRequest;
/*      */ import com.avaje.ebeaninternal.api.ScopeTrans;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.SpiQuery;
/*      */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*      */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*      */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*      */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*      */ import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.DNativeQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*      */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*      */ import com.avaje.ebeaninternal.server.el.ElFilter;
/*      */ import com.avaje.ebeaninternal.server.jmx.MAdminAutofetch;
/*      */ import com.avaje.ebeaninternal.server.ldap.DefaultLdapOrmQuery;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryEngine;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryRequest;
/*      */ import com.avaje.ebeaninternal.server.ldap.expression.LdapExpressionFactory;
/*      */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*      */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*      */ import com.avaje.ebeaninternal.server.query.CQuery;
/*      */ import com.avaje.ebeaninternal.server.query.CQueryEngine;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryIds;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryList;
/*      */ import com.avaje.ebeaninternal.server.query.CallableQueryRowCount;
/*      */ import com.avaje.ebeaninternal.server.query.CallableSqlQueryList;
/*      */ import com.avaje.ebeaninternal.server.query.LimitOffsetPagingQuery;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureIds;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureList;
/*      */ import com.avaje.ebeaninternal.server.query.QueryFutureRowCount;
/*      */ import com.avaje.ebeaninternal.server.query.SqlQueryFutureList;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultOrmUpdate;
/*      */ import com.avaje.ebeaninternal.server.querydefn.DefaultRelationalQuery;
/*      */ import com.avaje.ebeaninternal.server.text.csv.TCsvReader;
/*      */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*      */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*      */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
/*      */ import com.avaje.ebeaninternal.server.transaction.TransactionScopeManager;
/*      */ import com.avaje.ebeaninternal.util.ParamTypeHelper;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.management.InstanceAlreadyExistsException;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
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
/*      */ public final class DefaultServer
/*      */   implements SpiEbeanServer
/*      */ {
/*  140 */   private static final Logger logger = Logger.getLogger(DefaultServer.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   private static final InvalidValue[] EMPTY_INVALID_VALUES = new InvalidValue[0];
/*      */   
/*      */   private final String serverName;
/*      */   
/*      */   private final DatabasePlatform databasePlatform;
/*      */   
/*      */   private final AdminLogging adminLogging;
/*      */   
/*      */   private final AdminAutofetch adminAutofetch;
/*      */   
/*      */   private final TransactionManager transactionManager;
/*      */   
/*      */   private final TransactionScopeManager transactionScopeManager;
/*      */   
/*      */   private final int maxCallStack;
/*      */   
/*      */   private final boolean rollbackOnChecked;
/*      */   private final boolean defaultDeleteMissingChildren;
/*      */   private final boolean defaultUpdateNullProperties;
/*      */   private final boolean vanillaMode;
/*      */   private final boolean vanillaRefMode;
/*      */   private final LdapOrmQueryEngine ldapQueryEngine;
/*      */   private final Persister persister;
/*      */   private final OrmQueryEngine queryEngine;
/*      */   private final RelationalQueryEngine relationalQueryEngine;
/*      */   private final ServerCacheManager serverCacheManager;
/*      */   private final BeanDescriptorManager beanDescriptorManager;
/*      */   private final DiffHelp diffHelp;
/*      */   private final AutoFetchManager autoFetchManager;
/*      */   private final CQueryEngine cqueryEngine;
/*      */   private final DdlGenerator ddlGenerator;
/*      */   private final ExpressionFactory ldapExpressionFactory;
/*      */   private final ExpressionFactory expressionFactory;
/*      */   private final BackgroundExecutor backgroundExecutor;
/*      */   private final DefaultBeanLoader beanLoader;
/*      */   private final EncryptKeyManager encryptKeyManager;
/*      */   private final JsonContext jsonContext;
/*      */   private final LuceneIndexManager luceneIndexManager;
/*      */   private String mbeanName;
/*      */   private MBeanServer mbeanServer;
/*      */   private int lazyLoadBatchSize;
/*      */   private int queryBatchSize;
/*      */   private PstmtBatch pstmtBatch;
/*      */   private static final int IGNORE_LEADING_ELEMENTS = 5;
/*      */   
/*      */   public DefaultServer(InternalConfiguration config, ServerCacheManager cache) {
/*  191 */     this.diffHelp = new DiffHelp();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     this.ldapExpressionFactory = new LdapExpressionFactory();
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
/*  242 */     this.vanillaMode = config.getServerConfig().isVanillaMode();
/*  243 */     this.vanillaRefMode = config.getServerConfig().isVanillaRefMode();
/*      */     
/*  245 */     this.serverCacheManager = cache;
/*  246 */     this.pstmtBatch = config.getPstmtBatch();
/*  247 */     this.databasePlatform = config.getDatabasePlatform();
/*  248 */     this.backgroundExecutor = config.getBackgroundExecutor();
/*  249 */     this.serverName = config.getServerConfig().getName();
/*  250 */     this.lazyLoadBatchSize = config.getServerConfig().getLazyLoadBatchSize();
/*  251 */     this.queryBatchSize = config.getServerConfig().getQueryBatchSize();
/*  252 */     this.cqueryEngine = config.getCQueryEngine();
/*  253 */     this.expressionFactory = config.getExpressionFactory();
/*  254 */     this.adminLogging = config.getLogControl();
/*  255 */     this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
/*      */     
/*  257 */     this.beanDescriptorManager = config.getBeanDescriptorManager();
/*  258 */     this.beanDescriptorManager.setEbeanServer(this);
/*      */     
/*  260 */     this.maxCallStack = GlobalProperties.getInt("ebean.maxCallStack", 5);
/*      */     
/*  262 */     this.defaultUpdateNullProperties = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultUpdateNullProperties", "false"));
/*  263 */     this.defaultDeleteMissingChildren = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultDeleteMissingChildren", "true"));
/*      */     
/*  265 */     this.rollbackOnChecked = GlobalProperties.getBoolean("ebean.transaction.rollbackOnChecked", true);
/*  266 */     this.transactionManager = config.getTransactionManager();
/*  267 */     this.transactionScopeManager = config.getTransactionScopeManager();
/*      */     
/*  269 */     this.persister = config.createPersister(this);
/*  270 */     this.queryEngine = config.createOrmQueryEngine();
/*  271 */     this.relationalQueryEngine = config.createRelationalQueryEngine();
/*      */     
/*  273 */     this.autoFetchManager = config.createAutoFetchManager(this);
/*  274 */     this.adminAutofetch = new MAdminAutofetch(this.autoFetchManager);
/*      */     
/*  276 */     this.ddlGenerator = new DdlGenerator(this, config.getDatabasePlatform(), config.getServerConfig());
/*  277 */     this.beanLoader = new DefaultBeanLoader(this, config.getDebugLazyLoad());
/*  278 */     this.jsonContext = config.createJsonContext(this);
/*      */     
/*  280 */     LdapConfig ldapConfig = config.getServerConfig().getLdapConfig();
/*  281 */     if (ldapConfig == null) {
/*  282 */       this.ldapQueryEngine = null;
/*      */     } else {
/*  284 */       this.ldapQueryEngine = new LdapOrmQueryEngine(ldapConfig.isVanillaMode(), ldapConfig.getContextFactory());
/*      */     } 
/*      */     
/*  287 */     this.luceneIndexManager = config.getLuceneIndexManager();
/*  288 */     this.luceneIndexManager.setServer(this);
/*      */     
/*  290 */     ShutdownManager.register(new Shutdown(null));
/*      */   }
/*      */ 
/*      */   
/*  294 */   public LuceneIndexManager getLuceneIndexManager() { return this.luceneIndexManager; }
/*      */ 
/*      */ 
/*      */   
/*  298 */   public LuceneIndex getLuceneIndex(Class<?> beanType) { return this.luceneIndexManager.getIndex(beanType.getName()); }
/*      */ 
/*      */ 
/*      */   
/*  302 */   public boolean isDefaultDeleteMissingChildren() { return this.defaultDeleteMissingChildren; }
/*      */ 
/*      */ 
/*      */   
/*  306 */   public boolean isDefaultUpdateNullProperties() { return this.defaultUpdateNullProperties; }
/*      */ 
/*      */ 
/*      */   
/*  310 */   public boolean isVanillaMode() { return this.vanillaMode; }
/*      */ 
/*      */ 
/*      */   
/*  314 */   public int getLazyLoadBatchSize() { return this.lazyLoadBatchSize; }
/*      */ 
/*      */ 
/*      */   
/*  318 */   public PstmtBatch getPstmtBatch() { return this.pstmtBatch; }
/*      */ 
/*      */ 
/*      */   
/*  322 */   public DatabasePlatform getDatabasePlatform() { return this.databasePlatform; }
/*      */ 
/*      */ 
/*      */   
/*  326 */   public BackgroundExecutor getBackgroundExecutor() { return this.backgroundExecutor; }
/*      */ 
/*      */ 
/*      */   
/*  330 */   public ExpressionFactory getExpressionFactory() { return this.expressionFactory; }
/*      */ 
/*      */ 
/*      */   
/*  334 */   public DdlGenerator getDdlGenerator() { return this.ddlGenerator; }
/*      */ 
/*      */ 
/*      */   
/*  338 */   public AdminLogging getAdminLogging() { return this.adminLogging; }
/*      */ 
/*      */ 
/*      */   
/*  342 */   public AdminAutofetch getAdminAutofetch() { return this.adminAutofetch; }
/*      */ 
/*      */ 
/*      */   
/*  346 */   public AutoFetchManager getAutoFetchManager() { return this.autoFetchManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialise() {
/*  354 */     if (this.encryptKeyManager != null) {
/*  355 */       this.encryptKeyManager.initialise();
/*      */     }
/*  357 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/*  358 */     for (int i = 0; i < list.size(); i++) {
/*  359 */       ((BeanDescriptor)list.get(i)).cacheInitialise();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  368 */   public void start() { this.luceneIndexManager.start(); }
/*      */ 
/*      */   
/*      */   public void registerMBeans(MBeanServer mbeanServer, int uniqueServerId) {
/*      */     ObjectName autofethcName, adminName;
/*  373 */     this.mbeanServer = mbeanServer;
/*  374 */     this.mbeanName = "Ebean:server=" + this.serverName + uniqueServerId;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  380 */       adminName = new ObjectName(this.mbeanName + ",function=Logging");
/*  381 */       autofethcName = new ObjectName(this.mbeanName + ",key=AutoFetch");
/*  382 */     } catch (Exception e) {
/*  383 */       String msg = "Failed to register the JMX beans for Ebean server [" + this.serverName + "].";
/*  384 */       logger.log(Level.SEVERE, msg, e);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  389 */       mbeanServer.registerMBean(this.adminLogging, adminName);
/*  390 */       mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
/*      */     }
/*  392 */     catch (InstanceAlreadyExistsException e) {
/*      */       
/*  394 */       String msg = "JMX beans for Ebean server [" + this.serverName + "] already registered. Will try unregister/register" + e.getMessage();
/*  395 */       logger.log(Level.WARNING, msg);
/*      */       try {
/*  397 */         mbeanServer.unregisterMBean(adminName);
/*  398 */         mbeanServer.unregisterMBean(autofethcName);
/*      */         
/*  400 */         mbeanServer.registerMBean(this.adminLogging, adminName);
/*  401 */         mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
/*      */       }
/*  403 */       catch (Exception ae) {
/*  404 */         String amsg = "Unable to unregister/register the JMX beans for Ebean server [" + this.serverName + "].";
/*  405 */         logger.log(Level.SEVERE, amsg, ae);
/*      */       } 
/*  407 */     } catch (Exception e) {
/*  408 */       String msg = "Error registering MBean[" + this.mbeanName + "]";
/*  409 */       logger.log(Level.SEVERE, msg, e);
/*      */     } 
/*      */   }
/*      */   private final class Shutdown implements Runnable { private Shutdown() {}
/*      */     
/*      */     public void run() {
/*      */       try {
/*  416 */         if (DefaultServer.this.mbeanServer != null) {
/*  417 */           DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",function=Logging"));
/*  418 */           DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",key=AutoFetch"));
/*      */         } 
/*  420 */       } catch (Exception e) {
/*  421 */         String msg = "Error unregistering Ebean " + DefaultServer.this.mbeanName;
/*  422 */         logger.log(Level.SEVERE, msg, e);
/*      */       } 
/*      */ 
/*      */       
/*  426 */       DefaultServer.this.transactionManager.shutdown();
/*  427 */       DefaultServer.this.autoFetchManager.shutdown();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  436 */   public String getName() { return this.serverName; }
/*      */ 
/*      */   
/*      */   public BeanState getBeanState(Object bean) {
/*  440 */     if (bean instanceof EntityBean) {
/*  441 */       return new DefaultBeanState((EntityBean)bean);
/*      */     }
/*      */ 
/*      */     
/*  445 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runCacheWarming() {
/*  452 */     List<BeanDescriptor<?>> descList = this.beanDescriptorManager.getBeanDescriptorList();
/*  453 */     for (int i = 0; i < descList.size(); i++) {
/*  454 */       ((BeanDescriptor)descList.get(i)).runCacheWarming();
/*      */     }
/*      */   }
/*      */   
/*      */   public void runCacheWarming(Class<?> beanType) {
/*  459 */     BeanDescriptor<?> desc = this.beanDescriptorManager.getBeanDescriptor(beanType);
/*  460 */     if (desc == null) {
/*  461 */       String msg = "Is " + beanType + " an entity? Could not find a BeanDescriptor";
/*  462 */       throw new PersistenceException(msg);
/*      */     } 
/*  464 */     desc.runCacheWarming();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> CQuery<T> compileQuery(Query<T> query, Transaction t) {
/*  472 */     SpiOrmQueryRequest<T> qr = createQueryRequest(Query.Type.SUBQUERY, query, t);
/*  473 */     OrmQueryRequest<T> orm = (OrmQueryRequest)qr;
/*  474 */     return this.cqueryEngine.buildQuery(orm);
/*      */   }
/*      */ 
/*      */   
/*  478 */   public CQueryEngine getQueryEngine() { return this.cqueryEngine; }
/*      */ 
/*      */ 
/*      */   
/*  482 */   public ServerCacheManager getServerCacheManager() { return this.serverCacheManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  489 */   public AutoFetchManager getProfileListener() { return this.autoFetchManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  496 */   public RelationalQueryEngine getRelationalQueryEngine() { return this.relationalQueryEngine; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  501 */   public void refreshMany(Object parentBean, String propertyName, Transaction t) { this.beanLoader.refreshMany(parentBean, propertyName, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  506 */   public void refreshMany(Object parentBean, String propertyName) { this.beanLoader.refreshMany(parentBean, propertyName); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  511 */   public void loadMany(LoadManyRequest loadRequest) { this.beanLoader.loadMany(loadRequest); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  516 */   public void loadMany(BeanCollection<?> bc, boolean onlyIds) { this.beanLoader.loadMany(bc, null, onlyIds); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  521 */   public void refresh(Object bean) { this.beanLoader.refresh(bean); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  526 */   public void loadBean(LoadBeanRequest loadRequest) { this.beanLoader.loadBean(loadRequest); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  531 */   public void loadBean(EntityBeanIntercept ebi) { this.beanLoader.loadBean(ebi); }
/*      */ 
/*      */   
/*      */   public InvalidValue validate(Object bean) {
/*  535 */     if (bean == null) {
/*  536 */       return null;
/*      */     }
/*  538 */     BeanDescriptor<?> beanDescriptor = getBeanDescriptor(bean.getClass());
/*  539 */     return beanDescriptor.validate(true, bean);
/*      */   }
/*      */   
/*      */   public InvalidValue[] validate(Object bean, String propertyName, Object value) {
/*  543 */     if (bean == null) {
/*  544 */       return null;
/*      */     }
/*  546 */     BeanDescriptor<?> beanDescriptor = getBeanDescriptor(bean.getClass());
/*  547 */     BeanProperty prop = beanDescriptor.getBeanProperty(propertyName);
/*  548 */     if (prop == null) {
/*  549 */       String msg = "property " + propertyName + " was not found?";
/*  550 */       throw new PersistenceException(msg);
/*      */     } 
/*  552 */     if (value == null) {
/*  553 */       value = prop.getValue(bean);
/*      */     }
/*  555 */     List<InvalidValue> errors = prop.validate(true, value);
/*  556 */     if (errors == null) {
/*  557 */       return EMPTY_INVALID_VALUES;
/*      */     }
/*  559 */     return InvalidValue.toArray(errors);
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<String, ValuePair> diff(Object a, Object b) {
/*  564 */     if (a == null) {
/*  565 */       return null;
/*      */     }
/*      */     
/*  568 */     BeanDescriptor<?> desc = getBeanDescriptor(a.getClass());
/*  569 */     return this.diffHelp.diff(a, b, desc);
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
/*      */   public void externalModification(TransactionEventTable tableEvent) {
/*  582 */     SpiTransaction t = this.transactionScopeManager.get();
/*  583 */     if (t != null) {
/*  584 */       t.getEvent().add(tableEvent);
/*      */     } else {
/*  586 */       this.transactionManager.externalModification(tableEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void externalModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
/*  596 */     TransactionEventTable evt = new TransactionEventTable();
/*  597 */     evt.add(tableName, inserts, updates, deletes);
/*      */     
/*  599 */     externalModification(evt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearQueryStatistics() {
/*  606 */     for (BeanDescriptor<?> desc : getBeanDescriptors()) {
/*  607 */       desc.clearQueryStatistics();
/*      */     }
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
/*      */   public <T> T createEntityBean(Class<T> type) {
/*  621 */     BeanDescriptor<T> desc = getBeanDescriptor(type);
/*  622 */     return (T)desc.createEntityBean();
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectInputStream createProxyObjectInputStream(InputStream is) {
/*      */     try {
/*  628 */       return new ProxyBeanObjectInputStream(is, this);
/*  629 */     } catch (IOException e) {
/*  630 */       throw new PersistenceException(e);
/*      */     } 
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
/*      */   public <T> T getReference(Class<T> type, Object id) {
/*  645 */     if (id == null) {
/*  646 */       throw new NullPointerException("The id is null");
/*      */     }
/*      */     
/*  649 */     BeanDescriptor desc = getBeanDescriptor(type);
/*      */     
/*  651 */     id = desc.convertId(id);
/*      */     
/*  653 */     Object ref = null;
/*  654 */     PersistenceContext ctx = null;
/*      */     
/*  656 */     SpiTransaction t = this.transactionScopeManager.get();
/*  657 */     if (t != null) {
/*      */       
/*  659 */       ctx = t.getPersistenceContext();
/*  660 */       ref = ctx.get(type, id);
/*      */     } 
/*  662 */     if (ref == null) {
/*      */       
/*  664 */       ReferenceOptions opts = desc.getReferenceOptions();
/*  665 */       if (opts != null && opts.isUseCache()) {
/*  666 */         ref = desc.cacheGet(id);
/*  667 */         if (ref != null && !opts.isReadOnly()) {
/*  668 */           ref = desc.createCopyForUpdate(ref, this.vanillaMode);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  673 */     if (ref == null) {
/*  674 */       InheritInfo inheritInfo = desc.getInheritInfo();
/*  675 */       if (inheritInfo != null) {
/*      */         String idNames;
/*      */ 
/*      */         
/*  679 */         BeanProperty[] idProps = desc.propertiesId();
/*      */         
/*  681 */         switch (idProps.length) {
/*      */           case 0:
/*  683 */             throw new PersistenceException("No ID properties for this type? " + desc);
/*      */           case 1:
/*  685 */             idNames = idProps[0].getName();
/*      */             break;
/*      */           default:
/*  688 */             idNames = Arrays.toString(idProps);
/*  689 */             idNames = idNames.substring(1, idNames.length() - 1);
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*  694 */         Query<T> query = createQuery(type);
/*  695 */         query.select(idNames).setId(id);
/*      */         
/*  697 */         ref = query.findUnique();
/*      */       }
/*      */       else {
/*      */         
/*  701 */         ref = desc.createReference(this.vanillaRefMode, id, null, desc.getReferenceOptions());
/*      */       } 
/*      */       
/*  704 */       if (ctx != null && ref instanceof EntityBean)
/*      */       {
/*  706 */         ctx.put(id, ref);
/*      */       }
/*      */     } 
/*  709 */     return (T)ref;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  718 */   public Transaction createTransaction() { return this.transactionManager.createTransaction(true, -1); }
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
/*  729 */   public Transaction createTransaction(TxIsolation isolation) { return this.transactionManager.createTransaction(true, isolation.getLevel()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logComment(String msg) {
/*  736 */     SpiTransaction spiTransaction = this.transactionScopeManager.get();
/*  737 */     if (spiTransaction != null) {
/*  738 */       spiTransaction.log(msg);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  743 */   public <T> T execute(TxCallable<T> c) { return (T)execute(null, c); }
/*      */ 
/*      */   
/*      */   public <T> T execute(TxScope scope, TxCallable<T> c) {
/*  747 */     scopeTrans = createScopeTrans(scope);
/*      */     
/*  749 */     try { object = c.call();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  759 */       return (T)object; }
/*      */     catch (Error e) { throw scopeTrans.caughtError(e); }
/*      */     catch (RuntimeException e) { throw (RuntimeException)scopeTrans.caughtThrowable(e); }
/*      */     finally { scopeTrans.onFinally(); }
/*  763 */      } public void execute(TxRunnable r) { execute(null, r); }
/*      */ 
/*      */   
/*      */   public void execute(TxScope scope, TxRunnable r) {
/*  767 */     scopeTrans = createScopeTrans(scope);
/*      */     try {
/*  769 */       r.run();
/*      */     }
/*  771 */     catch (Error e) {
/*  772 */       throw scopeTrans.caughtError(e);
/*      */     }
/*  774 */     catch (RuntimeException e) {
/*  775 */       throw (RuntimeException)scopeTrans.caughtThrowable(e);
/*      */     } finally {
/*      */       
/*  778 */       scopeTrans.onFinally();
/*      */     } 
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
/*      */   private boolean createNewTransaction(SpiTransaction t, TxScope scope) {
/*  791 */     TxType type = scope.getType();
/*  792 */     switch (type) {
/*      */       case SQL:
/*  794 */         return (t == null);
/*      */       
/*      */       case LDAP:
/*  797 */         return true;
/*      */       
/*      */       case null:
/*  800 */         if (t == null)
/*  801 */           throw new PersistenceException("Transaction missing when MANDATORY"); 
/*  802 */         return true;
/*      */       
/*      */       case null:
/*  805 */         if (t != null)
/*  806 */           throw new PersistenceException("Transaction exists for Transactional NEVER"); 
/*  807 */         return false;
/*      */       
/*      */       case null:
/*  810 */         return false;
/*      */       
/*      */       case null:
/*  813 */         throw new RuntimeException("NOT_SUPPORTED should already be handled?");
/*      */     } 
/*      */     
/*  816 */     throw new RuntimeException("Should never get here?");
/*      */   }
/*      */ 
/*      */   
/*      */   public ScopeTrans createScopeTrans(TxScope txScope) {
/*      */     boolean newTransaction;
/*  822 */     if (txScope == null)
/*      */     {
/*  824 */       txScope = new TxScope();
/*      */     }
/*      */     
/*  827 */     SpiTransaction suspended = null;
/*      */ 
/*      */     
/*  830 */     SpiTransaction t = this.transactionScopeManager.get();
/*      */ 
/*      */     
/*  833 */     if (txScope.getType().equals(TxType.NOT_SUPPORTED)) {
/*      */ 
/*      */       
/*  836 */       newTransaction = false;
/*  837 */       suspended = t;
/*  838 */       t = null;
/*      */     }
/*      */     else {
/*      */       
/*  842 */       newTransaction = createNewTransaction(t, txScope);
/*      */       
/*  844 */       if (newTransaction) {
/*      */         
/*  846 */         suspended = t;
/*      */ 
/*      */         
/*  849 */         int isoLevel = -1;
/*  850 */         TxIsolation isolation = txScope.getIsolation();
/*  851 */         if (isolation != null) {
/*  852 */           isoLevel = isolation.getLevel();
/*      */         }
/*  854 */         t = this.transactionManager.createTransaction(true, isoLevel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  860 */     this.transactionScopeManager.replace(t);
/*      */     
/*  862 */     return new ScopeTrans(this.rollbackOnChecked, newTransaction, t, txScope, suspended, this.transactionScopeManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  869 */   public SpiTransaction getCurrentServerTransaction() { return this.transactionScopeManager.get(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction beginTransaction() {
/*  880 */     SpiTransaction t = this.transactionManager.createTransaction(true, -1);
/*  881 */     this.transactionScopeManager.set(t);
/*  882 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transaction beginTransaction(TxIsolation isolation) {
/*  893 */     SpiTransaction t = this.transactionManager.createTransaction(true, isolation.getLevel());
/*  894 */     this.transactionScopeManager.set(t);
/*  895 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  903 */   public Transaction currentTransaction() { return this.transactionScopeManager.get(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  910 */   public void commitTransaction() { this.transactionScopeManager.commit(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  917 */   public void rollbackTransaction() { this.transactionScopeManager.rollback(); }
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
/*  949 */   public void endTransaction() { this.transactionScopeManager.end(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object nextId(Class<?> beanType) {
/*  960 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/*  961 */     return desc.nextId(null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> void sort(List<T> list, String sortByClause) {
/*  967 */     if (list == null) {
/*  968 */       throw new NullPointerException("list is null");
/*      */     }
/*  970 */     if (sortByClause == null) {
/*  971 */       throw new NullPointerException("sortByClause is null");
/*      */     }
/*  973 */     if (list.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  978 */     Class<T> beanType = list.get(0).getClass();
/*  979 */     BeanDescriptor<T> beanDescriptor = getBeanDescriptor(beanType);
/*  980 */     if (beanDescriptor == null) {
/*  981 */       String m = "BeanDescriptor not found, is [" + beanType + "] an entity bean?";
/*  982 */       throw new PersistenceException(m);
/*      */     } 
/*  984 */     beanDescriptor.sort(list, sortByClause);
/*      */   }
/*      */ 
/*      */   
/*  988 */   public <T> Query<T> createQuery(Class<T> beanType) throws PersistenceException { return createQuery(beanType, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Query<T> createNamedQuery(Class<T> beanType, String namedQuery) throws PersistenceException {
/*  993 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/*  994 */     if (desc == null) {
/*  995 */       throw new PersistenceException("Is " + beanType.getName() + " an Entity Bean? BeanDescriptor not found?");
/*      */     }
/*  997 */     DeployNamedQuery deployQuery = desc.getNamedQuery(namedQuery);
/*  998 */     if (deployQuery == null) {
/*  999 */       throw new PersistenceException("named query " + namedQuery + " was not found for " + desc.getFullName());
/*      */     }
/*      */ 
/*      */     
/* 1003 */     return new DefaultOrmQuery(beanType, this, this.expressionFactory, deployQuery);
/*      */   }
/*      */   
/*      */   public <T> Filter<T> filter(Class<T> beanType) {
/* 1007 */     BeanDescriptor<T> desc = getBeanDescriptor(beanType);
/* 1008 */     if (desc == null) {
/* 1009 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1010 */       throw new PersistenceException(m);
/*      */     } 
/* 1012 */     return new ElFilter(desc);
/*      */   }
/*      */   
/*      */   public <T> CsvReader<T> createCsvReader(Class<T> beanType) {
/* 1016 */     BeanDescriptor<T> descriptor = getBeanDescriptor(beanType);
/* 1017 */     if (descriptor == null) {
/* 1018 */       throw new NullPointerException("BeanDescriptor for " + beanType.getName() + " not found");
/*      */     }
/* 1020 */     return new TCsvReader(this, descriptor);
/*      */   }
/*      */ 
/*      */   
/* 1024 */   public <T> Query<T> find(Class<T> beanType) throws PersistenceException { return createQuery(beanType); }
/*      */   
/*      */   public <T> Query<T> createQuery(Class<T> beanType, String query) throws PersistenceException {
/*      */     DeployNamedQuery defaultSqlSelect;
/* 1028 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1029 */     if (desc == null) {
/* 1030 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1031 */       throw new PersistenceException(m);
/*      */     } 
/* 1033 */     switch (desc.getEntityType()) {
/*      */       case SQL:
/* 1035 */         if (query != null) {
/* 1036 */           throw new PersistenceException("You must used Named queries for this Entity " + desc.getFullName());
/*      */         }
/*      */         
/* 1039 */         defaultSqlSelect = desc.getNamedQuery("default");
/* 1040 */         return new DefaultOrmQuery(beanType, this, this.expressionFactory, defaultSqlSelect);
/*      */       
/*      */       case LDAP:
/* 1043 */         return new DefaultLdapOrmQuery(beanType, this, this.ldapExpressionFactory, query);
/*      */     } 
/*      */     
/* 1046 */     return new DefaultOrmQuery(beanType, this, this.expressionFactory, query);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Update<T> createNamedUpdate(Class<T> beanType, String namedUpdate) {
/* 1051 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1052 */     if (desc == null) {
/* 1053 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1054 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1057 */     DeployNamedUpdate deployUpdate = desc.getNamedUpdate(namedUpdate);
/* 1058 */     if (deployUpdate == null) {
/* 1059 */       throw new PersistenceException("named update " + namedUpdate + " was not found for " + desc.getFullName());
/*      */     }
/*      */     
/* 1062 */     return new DefaultOrmUpdate(beanType, this, desc.getBaseTable(), deployUpdate);
/*      */   }
/*      */   
/*      */   public <T> Update<T> createUpdate(Class<T> beanType, String ormUpdate) {
/* 1066 */     BeanDescriptor<?> desc = getBeanDescriptor(beanType);
/* 1067 */     if (desc == null) {
/* 1068 */       String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
/* 1069 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1072 */     return new DefaultOrmUpdate(beanType, this, desc.getBaseTable(), ormUpdate);
/*      */   }
/*      */ 
/*      */   
/* 1076 */   public SqlQuery createSqlQuery(String sql) { return new DefaultRelationalQuery(this, sql); }
/*      */ 
/*      */   
/*      */   public SqlQuery createNamedSqlQuery(String namedQuery) {
/* 1080 */     DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
/* 1081 */     if (nq == null) {
/* 1082 */       throw new PersistenceException("SqlQuery " + namedQuery + " not found.");
/*      */     }
/* 1084 */     return new DefaultRelationalQuery(this, nq.getQuery());
/*      */   }
/*      */ 
/*      */   
/* 1088 */   public SqlUpdate createSqlUpdate(String sql) { return new DefaultSqlUpdate(this, sql); }
/*      */ 
/*      */ 
/*      */   
/* 1092 */   public CallableSql createCallableSql(String sql) { return new DefaultCallableSql(this, sql); }
/*      */ 
/*      */   
/*      */   public SqlUpdate createNamedSqlUpdate(String namedQuery) {
/* 1096 */     DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
/* 1097 */     if (nq == null) {
/* 1098 */       throw new PersistenceException("SqlUpdate " + namedQuery + " not found.");
/*      */     }
/* 1100 */     return new DefaultSqlUpdate(this, nq.getQuery());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1105 */   public <T> T find(Class<T> beanType, Object uid) { return (T)find(beanType, uid, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T find(Class<T> beanType, Object id, Transaction t) {
/* 1113 */     if (id == null) {
/* 1114 */       throw new NullPointerException("The id is null");
/*      */     }
/*      */     
/* 1117 */     Query<T> query = createQuery(beanType).setId(id);
/* 1118 */     return (T)findId(query, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> SpiOrmQueryRequest<T> createQueryRequest(Query.Type type, Query<T> q, Transaction t) {
/* 1123 */     SpiQuery<T> query = (SpiQuery)q;
/* 1124 */     query.setType(type);
/*      */ 
/*      */     
/* 1127 */     query.deriveSharedInstance();
/*      */     
/* 1129 */     BeanDescriptor<T> desc = this.beanDescriptorManager.getBeanDescriptor(query.getBeanType());
/* 1130 */     query.setBeanDescriptor(desc);
/*      */     
/* 1132 */     if (desc.isLdapEntityType()) {
/* 1133 */       return new LdapOrmQueryRequest(query, desc, this.ldapQueryEngine);
/*      */     }
/*      */     
/* 1136 */     if (desc.isAutoFetchTunable() && !query.isSqlSelect())
/*      */     {
/* 1138 */       if (!this.autoFetchManager.tuneQuery(query))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1143 */         query.setDefaultSelectClause();
/*      */       }
/*      */     }
/*      */     
/* 1147 */     if (query.selectAllForLazyLoadProperty())
/*      */     {
/*      */       
/* 1150 */       if (logger.isLoggable(Level.FINE)) {
/* 1151 */         logger.log(Level.FINE, "Using selectAllForLazyLoadProperty");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1157 */     if (query.getParentNode() == null) {
/* 1158 */       CallStack callStack = createCallStack();
/* 1159 */       query.setOrigin(callStack);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     if (query.initManyWhereJoins())
/*      */     {
/* 1167 */       query.setDistinct(true);
/*      */     }
/*      */ 
/*      */     
/* 1171 */     boolean allowOneManyFetch = true;
/* 1172 */     if (SpiQuery.Mode.LAZYLOAD_MANY.equals(query.getMode())) {
/* 1173 */       allowOneManyFetch = false;
/*      */     }
/* 1175 */     else if (query.hasMaxRowsOrFirstRow() && !query.isRawSql() && !query.isSqlSelect() && query.getBackgroundFetchAfter() == 0) {
/*      */ 
/*      */       
/* 1178 */       allowOneManyFetch = false;
/*      */     } 
/*      */     
/* 1181 */     query.convertManyFetchJoinsToQueryJoins(allowOneManyFetch, this.queryBatchSize);
/*      */     
/* 1183 */     SpiTransaction serverTrans = (SpiTransaction)t;
/* 1184 */     OrmQueryRequest<T> request = new OrmQueryRequest<T>(this, this.queryEngine, query, desc, serverTrans);
/*      */     
/* 1186 */     BeanQueryAdapter queryAdapter = desc.getQueryAdapter();
/* 1187 */     if (queryAdapter != null)
/*      */     {
/*      */       
/* 1190 */       queryAdapter.preQuery(request);
/*      */     }
/*      */ 
/*      */     
/* 1194 */     request.calculateQueryPlanHash();
/*      */     
/* 1196 */     return request;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T findId(Query<T> query, Transaction t) {
/* 1202 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.BEAN, query, t);
/*      */ 
/*      */ 
/*      */     
/* 1206 */     T bean = (T)request.getFromPersistenceContextOrCache();
/* 1207 */     if (bean != null) {
/* 1208 */       return bean;
/*      */     }
/*      */     
/*      */     try {
/* 1212 */       request.initTransIfRequired();
/*      */       
/* 1214 */       bean = (T)request.findId();
/* 1215 */       request.endTransIfRequired();
/*      */       
/* 1217 */       return bean;
/*      */     }
/* 1219 */     catch (RuntimeException ex) {
/* 1220 */       request.rollbackTransIfRequired();
/* 1221 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T findUnique(Query<T> query, Transaction t) {
/* 1229 */     SpiQuery<T> q = (SpiQuery)query;
/* 1230 */     Object id = q.getId();
/* 1231 */     if (id != null) {
/* 1232 */       return (T)findId(query, t);
/*      */     }
/*      */ 
/*      */     
/* 1236 */     List<T> list = findList(query, t);
/*      */     
/* 1238 */     if (list.size() == 0)
/* 1239 */       return null; 
/* 1240 */     if (list.size() > 1) {
/* 1241 */       String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
/* 1242 */       throw new PersistenceException(m);
/*      */     } 
/* 1244 */     return (T)list.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Set<T> findSet(Query<T> query, Transaction t) {
/* 1251 */     SpiOrmQueryRequest request = createQueryRequest(Query.Type.SET, query, t);
/*      */     
/* 1253 */     Object result = request.getFromQueryCache();
/* 1254 */     if (result != null) {
/* 1255 */       return (Set)result;
/*      */     }
/*      */     
/*      */     try {
/* 1259 */       request.initTransIfRequired();
/* 1260 */       Set<T> set = request.findSet();
/* 1261 */       request.endTransIfRequired();
/*      */       
/* 1263 */       return set;
/*      */     }
/* 1265 */     catch (RuntimeException ex) {
/*      */       
/* 1267 */       request.rollbackTransIfRequired();
/* 1268 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> Map<?, T> findMap(Query<T> query, Transaction t) {
/* 1275 */     SpiOrmQueryRequest request = createQueryRequest(Query.Type.MAP, query, t);
/*      */     
/* 1277 */     Object result = request.getFromQueryCache();
/* 1278 */     if (result != null) {
/* 1279 */       return (Map)result;
/*      */     }
/*      */     
/*      */     try {
/* 1283 */       request.initTransIfRequired();
/* 1284 */       Map<?, T> map = request.findMap();
/* 1285 */       request.endTransIfRequired();
/*      */       
/* 1287 */       return map;
/*      */     }
/* 1289 */     catch (RuntimeException ex) {
/*      */       
/* 1291 */       request.rollbackTransIfRequired();
/* 1292 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> int findRowCount(Query<T> query, Transaction t) {
/* 1298 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/* 1299 */     return findRowCountWithCopy(copy, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> int findRowCountWithCopy(Query<T> query, Transaction t) {
/* 1304 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.ROWCOUNT, query, t);
/*      */     try {
/* 1306 */       request.initTransIfRequired();
/* 1307 */       int rowCount = request.findRowCount();
/* 1308 */       request.endTransIfRequired();
/*      */       
/* 1310 */       return rowCount;
/*      */     }
/* 1312 */     catch (RuntimeException ex) {
/* 1313 */       request.rollbackTransIfRequired();
/* 1314 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> List<Object> findIds(Query<T> query, Transaction t) {
/* 1320 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/*      */     
/* 1322 */     return findIdsWithCopy(copy, t);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> List<Object> findIdsWithCopy(Query<T> query, Transaction t) {
/* 1327 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.ID_LIST, query, t);
/*      */     try {
/* 1329 */       request.initTransIfRequired();
/* 1330 */       List<Object> list = request.findIds();
/* 1331 */       request.endTransIfRequired();
/*      */       
/* 1333 */       return list;
/*      */     }
/* 1335 */     catch (RuntimeException ex) {
/* 1336 */       request.rollbackTransIfRequired();
/* 1337 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureRowCount<T> findFutureRowCount(Query<T> q, Transaction t) {
/* 1343 */     SpiQuery<T> copy = ((SpiQuery)q).copy();
/* 1344 */     copy.setFutureFetch(true);
/*      */     
/* 1346 */     Transaction newTxn = createTransaction();
/*      */     
/* 1348 */     CallableQueryRowCount<T> call = new CallableQueryRowCount<T>(this, copy, newTxn);
/* 1349 */     FutureTask<Integer> futureTask = new FutureTask<Integer>(call);
/*      */     
/* 1351 */     QueryFutureRowCount<T> queryFuture = new QueryFutureRowCount<T>(copy, futureTask);
/* 1352 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1354 */     return queryFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureIds<T> findFutureIds(Query<T> query, Transaction t) {
/* 1359 */     SpiQuery<T> copy = ((SpiQuery)query).copy();
/* 1360 */     copy.setFutureFetch(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1365 */     List<Object> idList = Collections.synchronizedList(new ArrayList());
/* 1366 */     copy.setIdList(idList);
/*      */     
/* 1368 */     Transaction newTxn = createTransaction();
/*      */     
/* 1370 */     CallableQueryIds<T> call = new CallableQueryIds<T>(this, copy, newTxn);
/* 1371 */     FutureTask<List<Object>> futureTask = new FutureTask<List<Object>>(call);
/*      */     
/* 1373 */     QueryFutureIds<T> queryFuture = new QueryFutureIds<T>(copy, futureTask);
/*      */     
/* 1375 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1377 */     return queryFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> FutureList<T> findFutureList(Query<T> query, Transaction t) {
/* 1382 */     SpiQuery<T> spiQuery = (SpiQuery)query;
/* 1383 */     spiQuery.setFutureFetch(true);
/*      */     
/* 1385 */     if (spiQuery.getPersistenceContext() == null) {
/* 1386 */       if (t != null) {
/* 1387 */         spiQuery.setPersistenceContext(((SpiTransaction)t).getPersistenceContext());
/*      */       } else {
/* 1389 */         SpiTransaction st = getCurrentServerTransaction();
/* 1390 */         if (st != null) {
/* 1391 */           spiQuery.setPersistenceContext(st.getPersistenceContext());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1396 */     Transaction newTxn = createTransaction();
/* 1397 */     CallableQueryList<T> call = new CallableQueryList<T>(this, query, newTxn);
/*      */     
/* 1399 */     FutureTask<List<T>> futureTask = new FutureTask<List<T>>(call);
/*      */     
/* 1401 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1403 */     return new QueryFutureList(query, futureTask);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> PagingList<T> findPagingList(Query<T> query, Transaction t, int pageSize) {
/* 1408 */     SpiQuery<T> spiQuery = (SpiQuery)query;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1413 */     PersistenceContext pc = spiQuery.getPersistenceContext();
/* 1414 */     if (pc == null) {
/* 1415 */       DefaultPersistenceContext defaultPersistenceContext; SpiTransaction currentTransaction = getCurrentServerTransaction();
/* 1416 */       if (currentTransaction != null) {
/* 1417 */         pc = currentTransaction.getPersistenceContext();
/*      */       }
/* 1419 */       if (pc == null) {
/* 1420 */         defaultPersistenceContext = new DefaultPersistenceContext();
/*      */       }
/* 1422 */       spiQuery.setPersistenceContext(defaultPersistenceContext);
/*      */     } 
/*      */     
/* 1425 */     return new LimitOffsetPagingQuery(this, spiQuery, pageSize);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> void findVisit(Query<T> query, QueryResultVisitor<T> visitor, Transaction t) {
/* 1430 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.LIST, query, t);
/*      */     
/*      */     try {
/* 1433 */       request.initTransIfRequired();
/* 1434 */       request.findVisit(visitor);
/*      */     }
/* 1436 */     catch (RuntimeException ex) {
/* 1437 */       request.rollbackTransIfRequired();
/* 1438 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> QueryIterator<T> findIterate(Query<T> query, Transaction t) {
/* 1444 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.LIST, query, t);
/*      */     
/*      */     try {
/* 1447 */       request.initTransIfRequired();
/* 1448 */       return request.findIterate();
/*      */     
/*      */     }
/* 1451 */     catch (RuntimeException ex) {
/* 1452 */       request.rollbackTransIfRequired();
/* 1453 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> List<T> findList(Query<T> query, Transaction t) {
/* 1460 */     SpiOrmQueryRequest<T> request = createQueryRequest(Query.Type.LIST, query, t);
/*      */     
/* 1462 */     Object result = request.getFromQueryCache();
/* 1463 */     if (result != null) {
/* 1464 */       return (List)result;
/*      */     }
/*      */     
/*      */     try {
/* 1468 */       request.initTransIfRequired();
/* 1469 */       List<T> list = request.findList();
/* 1470 */       request.endTransIfRequired();
/*      */       
/* 1472 */       return list;
/*      */     }
/* 1474 */     catch (RuntimeException ex) {
/* 1475 */       request.rollbackTransIfRequired();
/* 1476 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SqlRow findUnique(SqlQuery query, Transaction t) {
/* 1484 */     List<SqlRow> list = findList(query, t);
/*      */     
/* 1486 */     if (list.size() == 0) {
/* 1487 */       return null;
/*      */     }
/* 1489 */     if (list.size() > 1) {
/* 1490 */       String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
/* 1491 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1494 */     return (SqlRow)list.get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SqlFutureList findFutureList(SqlQuery query, Transaction t) {
/* 1500 */     SpiSqlQuery spiQuery = (SpiSqlQuery)query;
/* 1501 */     spiQuery.setFutureFetch(true);
/*      */     
/* 1503 */     Transaction newTxn = createTransaction();
/* 1504 */     CallableSqlQueryList call = new CallableSqlQueryList(this, query, newTxn);
/*      */     
/* 1506 */     FutureTask<List<SqlRow>> futureTask = new FutureTask<List<SqlRow>>(call);
/*      */     
/* 1508 */     this.backgroundExecutor.execute(futureTask);
/*      */     
/* 1510 */     return new SqlQueryFutureList(query, futureTask);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<SqlRow> findList(SqlQuery query, Transaction t) {
/* 1515 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     
/*      */     try {
/* 1518 */       request.initTransIfRequired();
/* 1519 */       List<SqlRow> list = request.findList();
/* 1520 */       request.endTransIfRequired();
/*      */       
/* 1522 */       return list;
/*      */     }
/* 1524 */     catch (RuntimeException ex) {
/* 1525 */       request.rollbackTransIfRequired();
/* 1526 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<SqlRow> findSet(SqlQuery query, Transaction t) {
/* 1532 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     
/*      */     try {
/* 1535 */       request.initTransIfRequired();
/* 1536 */       Set<SqlRow> set = request.findSet();
/* 1537 */       request.endTransIfRequired();
/*      */       
/* 1539 */       return set;
/*      */     }
/* 1541 */     catch (RuntimeException ex) {
/* 1542 */       request.rollbackTransIfRequired();
/* 1543 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<?, SqlRow> findMap(SqlQuery query, Transaction t) {
/* 1549 */     RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
/*      */     try {
/* 1551 */       request.initTransIfRequired();
/* 1552 */       Map<?, SqlRow> map = request.findMap();
/* 1553 */       request.endTransIfRequired();
/*      */       
/* 1555 */       return map;
/*      */     }
/* 1557 */     catch (RuntimeException ex) {
/* 1558 */       request.rollbackTransIfRequired();
/* 1559 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1567 */   public void save(Object bean) { save(bean, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Object bean, Transaction t) {
/* 1574 */     if (bean == null) {
/* 1575 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1577 */     this.persister.save(bean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1584 */   public void update(Object bean) { update(bean, null, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1591 */   public void update(Object bean, Set<String> updateProps) { update(bean, updateProps, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1598 */   public void update(Object bean, Transaction t) { update(bean, null, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1605 */   public void update(Object bean, Set<String> updateProps, Transaction t) { update(bean, updateProps, t, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Object bean, Set<String> updateProps, Transaction t, boolean deleteMissingChildren, boolean updateNullProperties) {
/* 1612 */     if (bean == null) {
/* 1613 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1615 */     this.persister.forceUpdate(bean, updateProps, t, deleteMissingChildren, updateNullProperties);
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
/* 1628 */   public void insert(Object bean) { insert(bean, null); }
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
/*      */   public void insert(Object bean, Transaction t) {
/* 1641 */     if (bean == null) {
/* 1642 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1644 */     this.persister.forceInsert(bean, t);
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
/* 1655 */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName) { return deleteManyToManyAssociations(ownerBean, propertyName, null); }
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
/*      */   public int deleteManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/* 1667 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1669 */       SpiTransaction trans = wrap.transaction;
/* 1670 */       int rc = this.persister.deleteManyToManyAssociations(ownerBean, propertyName, trans);
/* 1671 */       wrap.commitIfCreated();
/* 1672 */       return rc;
/*      */     }
/* 1674 */     catch (RuntimeException e) {
/* 1675 */       wrap.rollbackIfCreated();
/* 1676 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1685 */   public void saveManyToManyAssociations(Object ownerBean, String propertyName) { saveManyToManyAssociations(ownerBean, propertyName, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveManyToManyAssociations(Object ownerBean, String propertyName, Transaction t) {
/* 1694 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1696 */       SpiTransaction trans = wrap.transaction;
/*      */       
/* 1698 */       this.persister.saveManyToManyAssociations(ownerBean, propertyName, trans);
/*      */       
/* 1700 */       wrap.commitIfCreated();
/*      */     }
/* 1702 */     catch (RuntimeException e) {
/* 1703 */       wrap.rollbackIfCreated();
/* 1704 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1709 */   public void saveAssociation(Object ownerBean, String propertyName) { saveAssociation(ownerBean, propertyName, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAssociation(Object ownerBean, String propertyName, Transaction t) {
/* 1714 */     if (ownerBean instanceof EntityBean) {
/* 1715 */       Set<String> loadedProps = ((EntityBean)ownerBean)._ebean_getIntercept().getLoadedProps();
/* 1716 */       if (loadedProps != null && !loadedProps.contains(propertyName)) {
/*      */ 
/*      */         
/* 1719 */         logger.fine("Skip saveAssociation as property " + propertyName + " is not loaded");
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1724 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1726 */       SpiTransaction trans = wrap.transaction;
/*      */       
/* 1728 */       this.persister.saveAssociation(ownerBean, propertyName, trans);
/*      */       
/* 1730 */       wrap.commitIfCreated();
/*      */     }
/* 1732 */     catch (RuntimeException e) {
/* 1733 */       wrap.rollbackIfCreated();
/* 1734 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1743 */   public int save(Iterator<?> it) { return save(it, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1751 */   public int save(Collection<?> c) { return save(c.iterator(), null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int save(Iterator<?> it, Transaction t) {
/* 1759 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1761 */       SpiTransaction trans = wrap.transaction;
/* 1762 */       int saveCount = 0;
/* 1763 */       while (it.hasNext()) {
/* 1764 */         Object bean = it.next();
/* 1765 */         this.persister.save(bean, trans);
/* 1766 */         saveCount++;
/*      */       } 
/*      */       
/* 1769 */       wrap.commitIfCreated();
/*      */       
/* 1771 */       return saveCount;
/*      */     }
/* 1773 */     catch (RuntimeException e) {
/* 1774 */       wrap.rollbackIfCreated();
/* 1775 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1780 */   public int delete(Class<?> beanType, Object id) { return delete(beanType, id, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Class<?> beanType, Object id, Transaction t) {
/* 1785 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1787 */       SpiTransaction trans = wrap.transaction;
/* 1788 */       int rowCount = this.persister.delete(beanType, id, trans);
/* 1789 */       wrap.commitIfCreated();
/*      */       
/* 1791 */       return rowCount;
/*      */     }
/* 1793 */     catch (RuntimeException e) {
/* 1794 */       wrap.rollbackIfCreated();
/* 1795 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1800 */   public void delete(Class<?> beanType, Collection<?> ids) { delete(beanType, ids, null); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Class<?> beanType, Collection<?> ids, Transaction t) {
/* 1805 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     try {
/* 1807 */       SpiTransaction trans = wrap.transaction;
/* 1808 */       this.persister.deleteMany(beanType, ids, trans);
/* 1809 */       wrap.commitIfCreated();
/*      */     }
/* 1811 */     catch (RuntimeException e) {
/* 1812 */       wrap.rollbackIfCreated();
/* 1813 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1821 */   public void delete(Object bean) { delete(bean, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(Object bean, Transaction t) {
/* 1828 */     if (bean == null) {
/* 1829 */       throw new NullPointerException(Message.msg("bean.isnull"));
/*      */     }
/* 1831 */     this.persister.delete(bean, t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1838 */   public int delete(Iterator<?> it) { return delete(it, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1845 */   public int delete(Collection<?> c) { return delete(c.iterator(), null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int delete(Iterator<?> it, Transaction t) {
/* 1853 */     TransWrapper wrap = initTransIfRequired(t);
/*      */     
/*      */     try {
/* 1856 */       SpiTransaction trans = wrap.transaction;
/* 1857 */       int deleteCount = 0;
/* 1858 */       while (it.hasNext()) {
/* 1859 */         Object bean = it.next();
/* 1860 */         this.persister.delete(bean, trans);
/* 1861 */         deleteCount++;
/*      */       } 
/*      */       
/* 1864 */       wrap.commitIfCreated();
/*      */       
/* 1866 */       return deleteCount;
/*      */     }
/* 1868 */     catch (RuntimeException e) {
/* 1869 */       wrap.rollbackIfCreated();
/* 1870 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1878 */   public int execute(CallableSql callSql, Transaction t) { return this.persister.executeCallable(callSql, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1885 */   public int execute(CallableSql callSql) { return execute(callSql, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1892 */   public int execute(SqlUpdate updSql, Transaction t) { return this.persister.executeSqlUpdate(updSql, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1899 */   public int execute(SqlUpdate updSql) { return execute(updSql, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1906 */   public int execute(Update<?> update, Transaction t) { return this.persister.executeOrmUpdate(update, t); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1913 */   public int execute(Update<?> update) { return execute(update, null); }
/*      */ 
/*      */ 
/*      */   
/* 1917 */   public <T> BeanManager<T> getBeanManager(Class<T> beanClass) { return this.beanDescriptorManager.getBeanManager(beanClass); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1924 */   public List<BeanDescriptor<?>> getBeanDescriptors() { return this.beanDescriptorManager.getBeanDescriptorList(); }
/*      */ 
/*      */   
/*      */   public void register(BeanPersistController c) {
/* 1928 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/* 1929 */     for (int i = 0; i < list.size(); i++) {
/* 1930 */       ((BeanDescriptor)list.get(i)).register(c);
/*      */     }
/*      */   }
/*      */   
/*      */   public void deregister(BeanPersistController c) {
/* 1935 */     List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
/* 1936 */     for (int i = 0; i < list.size(); i++) {
/* 1937 */       ((BeanDescriptor)list.get(i)).deregister(c);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSupportedType(Type genericType) {
/* 1943 */     ParamTypeHelper.TypeInfo typeInfo = ParamTypeHelper.getTypeInfo(genericType);
/* 1944 */     if (typeInfo == null) {
/* 1945 */       return false;
/*      */     }
/* 1947 */     return (getBeanDescriptor(typeInfo.getBeanType()) != null);
/*      */   }
/*      */   
/*      */   public Object getBeanId(Object bean) {
/* 1951 */     BeanDescriptor<?> desc = getBeanDescriptor(bean.getClass());
/* 1952 */     if (desc == null) {
/* 1953 */       String m = bean.getClass().getName() + " is NOT an Entity Bean registered with this server?";
/* 1954 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1957 */     return desc.getId(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1964 */   public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> beanClass) { return this.beanDescriptorManager.getBeanDescriptor(beanClass); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1971 */   public List<BeanDescriptor<?>> getBeanDescriptors(String tableName) { return this.beanDescriptorManager.getBeanDescriptors(tableName); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1978 */   public BeanDescriptor<?> getBeanDescriptorById(String descriptorId) { return this.beanDescriptorManager.getBeanDescriptorById(descriptorId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1987 */   public void remoteTransactionEvent(RemoteTransactionEvent event) { this.transactionManager.remoteTransactionEvent(event); }
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
/*      */   TransWrapper initTransIfRequired(Transaction t) {
/* 2002 */     if (t != null) {
/* 2003 */       return new TransWrapper((SpiTransaction)t, false);
/*      */     }
/*      */     
/* 2006 */     boolean wasCreated = false;
/* 2007 */     SpiTransaction trans = this.transactionScopeManager.get();
/* 2008 */     if (trans == null) {
/*      */       
/* 2010 */       trans = this.transactionManager.createTransaction(false, -1);
/* 2011 */       wasCreated = true;
/*      */     } 
/* 2013 */     return new TransWrapper(trans, wasCreated);
/*      */   }
/*      */ 
/*      */   
/* 2017 */   public SpiTransaction createServerTransaction(boolean isExplicit, int isolationLevel) { return this.transactionManager.createTransaction(isExplicit, isolationLevel); }
/*      */ 
/*      */ 
/*      */   
/* 2021 */   public SpiTransaction createQueryTransaction() { return this.transactionManager.createQueryTransaction(); }
/*      */ 
/*      */ 
/*      */   
/* 2025 */   private static final String AVAJE_EBEAN = com.avaje.ebean.Ebean.class.getName().substring(0, 15);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallStack createCallStack() {
/* 2036 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/*      */ 
/*      */     
/* 2039 */     int startIndex = 5;
/*      */ 
/*      */     
/* 2042 */     for (; startIndex < stackTrace.length && 
/* 2043 */       stackTrace[startIndex].getClassName().startsWith(AVAJE_EBEAN); startIndex++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2048 */     int stackLength = stackTrace.length - startIndex;
/* 2049 */     if (stackLength > this.maxCallStack)
/*      */     {
/* 2051 */       stackLength = this.maxCallStack;
/*      */     }
/*      */ 
/*      */     
/* 2055 */     StackTraceElement[] finalTrace = new StackTraceElement[stackLength];
/* 2056 */     for (int i = 0; i < stackLength; i++) {
/* 2057 */       finalTrace[i] = stackTrace[i + startIndex];
/*      */     }
/*      */     
/* 2060 */     if (stackLength < 1)
/*      */     {
/* 2062 */       throw new RuntimeException("StackTraceElement size 0?  stack: " + Arrays.toString(stackTrace));
/*      */     }
/*      */     
/* 2065 */     return new CallStack(finalTrace);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 2070 */   public JsonContext createJsonContext() { return this.jsonContext; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */