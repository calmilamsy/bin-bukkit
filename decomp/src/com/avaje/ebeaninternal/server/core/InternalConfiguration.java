/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheManager;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.ldap.LdapConfig;
/*     */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*     */ import com.avaje.ebean.text.json.JsonContext;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManagerFactory;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployOrmXml;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
/*     */ import com.avaje.ebeaninternal.server.expression.DefaultExpressionFactory;
/*     */ import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import com.avaje.ebeaninternal.server.lucene.NoLuceneIndexManager;
/*     */ import com.avaje.ebeaninternal.server.persist.Binder;
/*     */ import com.avaje.ebeaninternal.server.persist.DefaultPersister;
/*     */ import com.avaje.ebeaninternal.server.query.CQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.query.DefaultOrmQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.query.DefaultRelationalQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.resource.ResourceManager;
/*     */ import com.avaje.ebeaninternal.server.resource.ResourceManagerFactory;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassManager;
/*     */ import com.avaje.ebeaninternal.server.text.json.DJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.DefaultJsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultTransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.ExternalTransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.JtaTransactionManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionManager;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionScopeManager;
/*     */ import com.avaje.ebeaninternal.server.type.DefaultTypeManager;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalConfiguration
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger(InternalConfiguration.class.getName());
/*     */ 
/*     */   
/*     */   private final ServerConfig serverConfig;
/*     */ 
/*     */   
/*     */   private final BootupClasses bootupClasses;
/*     */   
/*     */   private final SubClassManager subClassManager;
/*     */   
/*     */   private final DeployInherit deployInherit;
/*     */   
/*     */   private final ResourceManager resourceManager;
/*     */   
/*     */   private final DeployOrmXml deployOrmXml;
/*     */   
/*     */   private final TypeManager typeManager;
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final DeployCreateProperties deployCreateProperties;
/*     */   
/*     */   private final DeployUtil deployUtil;
/*     */   
/*     */   private final BeanDescriptorManager beanDescriptorManager;
/*     */   
/*     */   private final MAdminLogging logControl;
/*     */   
/*     */   private final DebugLazyLoad debugLazyLoad;
/*     */   
/*     */   private final TransactionManager transactionManager;
/*     */   
/*     */   private final TransactionScopeManager transactionScopeManager;
/*     */   
/*     */   private final CQueryEngine cQueryEngine;
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */   
/*     */   private final ServerCacheManager cacheManager;
/*     */   
/*     */   private final ExpressionFactory expressionFactory;
/*     */   
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   
/*     */   private final PstmtBatch pstmtBatch;
/*     */   
/*     */   private final XmlConfig xmlConfig;
/*     */   
/*     */   private final LuceneIndexManager luceneIndexManager;
/*     */ 
/*     */   
/*     */   public InternalConfiguration(XmlConfig xmlConfig, ClusterManager clusterManager, ServerCacheManager cacheManager, BackgroundExecutor backgroundExecutor, ServerConfig serverConfig, BootupClasses bootupClasses, PstmtBatch pstmtBatch) {
/* 126 */     this.xmlConfig = xmlConfig;
/* 127 */     this.pstmtBatch = pstmtBatch;
/* 128 */     this.clusterManager = clusterManager;
/* 129 */     this.backgroundExecutor = backgroundExecutor;
/* 130 */     this.cacheManager = cacheManager;
/* 131 */     this.serverConfig = serverConfig;
/* 132 */     this.bootupClasses = bootupClasses;
/* 133 */     this.expressionFactory = new DefaultExpressionFactory();
/*     */     
/* 135 */     this.subClassManager = new SubClassManager(serverConfig);
/*     */     
/* 137 */     this.typeManager = new DefaultTypeManager(serverConfig, bootupClasses);
/* 138 */     this.binder = new Binder(this.typeManager);
/*     */     
/* 140 */     this.resourceManager = ResourceManagerFactory.createResourceManager(serverConfig);
/* 141 */     this.deployOrmXml = new DeployOrmXml(this.resourceManager.getResourceSource());
/* 142 */     this.deployInherit = new DeployInherit(bootupClasses);
/*     */     
/* 144 */     this.deployCreateProperties = new DeployCreateProperties(this.typeManager);
/* 145 */     this.deployUtil = new DeployUtil(this.typeManager, serverConfig);
/*     */     
/* 147 */     this.luceneIndexManager = createLuceneManager(serverConfig);
/*     */     
/* 149 */     this.beanDescriptorManager = new BeanDescriptorManager(this, this.luceneIndexManager);
/* 150 */     this.beanDescriptorManager.deploy();
/*     */     
/* 152 */     this.debugLazyLoad = new DebugLazyLoad(serverConfig.isDebugLazyLoad());
/*     */     
/* 154 */     this.transactionManager = new TransactionManager(clusterManager, this.luceneIndexManager, backgroundExecutor, serverConfig, this.beanDescriptorManager);
/*     */     
/* 156 */     this.logControl = new MAdminLogging(serverConfig, this.transactionManager);
/*     */     
/* 158 */     this.cQueryEngine = new CQueryEngine(serverConfig.getDatabasePlatform(), this.logControl, this.binder, backgroundExecutor, this.luceneIndexManager);
/*     */ 
/*     */     
/* 161 */     JtaTransactionManager jtaTransactionManager = serverConfig.getExternalTransactionManager();
/* 162 */     if (jtaTransactionManager == null && serverConfig.isUseJtaTransactionManager()) {
/* 163 */       jtaTransactionManager = new JtaTransactionManager();
/*     */     }
/* 165 */     if (jtaTransactionManager != null) {
/* 166 */       jtaTransactionManager.setTransactionManager(this.transactionManager);
/* 167 */       this.transactionScopeManager = new ExternalTransactionScopeManager(this.transactionManager, jtaTransactionManager);
/* 168 */       logger.info("Using Transaction Manager [" + jtaTransactionManager.getClass() + "]");
/*     */     } else {
/* 170 */       this.transactionScopeManager = new DefaultTransactionScopeManager(this.transactionManager);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LuceneIndexManager createLuceneManager(ServerConfig serverConfig) {
/* 177 */     if (!DetectLucene.isPresent())
/*     */     {
/*     */       
/* 180 */       return new NoLuceneIndexManager();
/*     */     }
/*     */     
/* 183 */     return LuceneManagerFactory.createLuceneManager(this.clusterManager, this.backgroundExecutor, serverConfig);
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonContext createJsonContext(SpiEbeanServer server) {
/* 188 */     String s = this.serverConfig.getProperty("json.pretty", "false");
/* 189 */     boolean dfltPretty = "true".equalsIgnoreCase(s);
/*     */     
/* 191 */     s = this.serverConfig.getProperty("json.jsonValueAdapter", null);
/*     */     
/* 193 */     JsonValueAdapter jsonValueAdapter = new DefaultJsonValueAdapter();
/* 194 */     if (s != null) {
/* 195 */       jsonValueAdapter = (JsonValueAdapter)ClassUtil.newInstance(s, getClass());
/*     */     }
/* 197 */     return new DJsonContext(server, jsonValueAdapter, dfltPretty);
/*     */   }
/*     */ 
/*     */   
/* 201 */   public LuceneIndexManager getLuceneIndexManager() { return this.luceneIndexManager; }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public XmlConfig getXmlConfig() { return this.xmlConfig; }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public AutoFetchManager createAutoFetchManager(SpiEbeanServer server) { return AutoFetchManagerFactory.create(server, this.serverConfig, this.resourceManager); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public RelationalQueryEngine createRelationalQueryEngine() { return new DefaultRelationalQueryEngine(this.logControl, this.binder, this.serverConfig.getDatabaseBooleanTrue()); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public OrmQueryEngine createOrmQueryEngine() { return new DefaultOrmQueryEngine(this.beanDescriptorManager, this.cQueryEngine); }
/*     */ 
/*     */   
/*     */   public Persister createPersister(SpiEbeanServer server) {
/* 222 */     LdapContextFactory ldapCtxFactory = null;
/* 223 */     LdapConfig ldapConfig = this.serverConfig.getLdapConfig();
/* 224 */     if (ldapConfig != null) {
/* 225 */       ldapCtxFactory = ldapConfig.getContextFactory();
/*     */     }
/* 227 */     return new DefaultPersister(server, this.serverConfig.isValidateOnSave(), this.binder, this.beanDescriptorManager, this.pstmtBatch, ldapCtxFactory);
/*     */   }
/*     */ 
/*     */   
/* 231 */   public PstmtBatch getPstmtBatch() { return this.pstmtBatch; }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public ServerCacheManager getCacheManager() { return this.cacheManager; }
/*     */ 
/*     */ 
/*     */   
/* 239 */   public BootupClasses getBootupClasses() { return this.bootupClasses; }
/*     */ 
/*     */ 
/*     */   
/* 243 */   public DatabasePlatform getDatabasePlatform() { return this.serverConfig.getDatabasePlatform(); }
/*     */ 
/*     */ 
/*     */   
/* 247 */   public ServerConfig getServerConfig() { return this.serverConfig; }
/*     */ 
/*     */ 
/*     */   
/* 251 */   public ExpressionFactory getExpressionFactory() { return this.expressionFactory; }
/*     */ 
/*     */ 
/*     */   
/* 255 */   public TypeManager getTypeManager() { return this.typeManager; }
/*     */ 
/*     */ 
/*     */   
/* 259 */   public Binder getBinder() { return this.binder; }
/*     */ 
/*     */ 
/*     */   
/* 263 */   public BeanDescriptorManager getBeanDescriptorManager() { return this.beanDescriptorManager; }
/*     */ 
/*     */ 
/*     */   
/* 267 */   public SubClassManager getSubClassManager() { return this.subClassManager; }
/*     */ 
/*     */ 
/*     */   
/* 271 */   public DeployInherit getDeployInherit() { return this.deployInherit; }
/*     */ 
/*     */ 
/*     */   
/* 275 */   public ResourceManager getResourceManager() { return this.resourceManager; }
/*     */ 
/*     */ 
/*     */   
/* 279 */   public DeployOrmXml getDeployOrmXml() { return this.deployOrmXml; }
/*     */ 
/*     */ 
/*     */   
/* 283 */   public DeployCreateProperties getDeployCreateProperties() { return this.deployCreateProperties; }
/*     */ 
/*     */ 
/*     */   
/* 287 */   public DeployUtil getDeployUtil() { return this.deployUtil; }
/*     */ 
/*     */ 
/*     */   
/* 291 */   public MAdminLogging getLogControl() { return this.logControl; }
/*     */ 
/*     */ 
/*     */   
/* 295 */   public TransactionManager getTransactionManager() { return this.transactionManager; }
/*     */ 
/*     */ 
/*     */   
/* 299 */   public TransactionScopeManager getTransactionScopeManager() { return this.transactionScopeManager; }
/*     */ 
/*     */ 
/*     */   
/* 303 */   public CQueryEngine getCQueryEngine() { return this.cQueryEngine; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 308 */   public ClusterManager getClusterManager() { return this.clusterManager; }
/*     */ 
/*     */ 
/*     */   
/* 312 */   public DebugLazyLoad getDebugLazyLoad() { return this.debugLazyLoad; }
/*     */ 
/*     */ 
/*     */   
/* 316 */   public BackgroundExecutor getBackgroundExecutor() { return this.backgroundExecutor; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\InternalConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */