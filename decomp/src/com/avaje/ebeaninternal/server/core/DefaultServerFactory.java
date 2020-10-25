/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.cache.ServerCacheManager;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.PstmtDelegate;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.UnderscoreNamingConvention;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cache.DefaultServerCacheFactory;
/*     */ import com.avaje.ebeaninternal.server.cache.DefaultServerCacheManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.jdbc.OraclePstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.jdbc.StandardPstmtDelegate;
/*     */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*     */ import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerFactory;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
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
/*     */ public class DefaultServerFactory
/*     */   implements BootupEbeanManager
/*     */ {
/*  66 */   private static final Logger logger = Logger.getLogger(DefaultServerFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final AtomicInteger serverId = new AtomicInteger(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private final ClusterManager clusterManager = new ClusterManager();
/*  83 */   private final JndiDataSourceLookup jndiDataSourceFactory = new JndiDataSourceLookup(); private final BootupClassPathSearch bootupClassSearch;
/*     */   public DefaultServerFactory() {
/*  85 */     List<String> packages = getSearchJarsPackages(GlobalProperties.get("ebean.search.packages", null));
/*  86 */     List<String> jars = getSearchJarsPackages(GlobalProperties.get("ebean.search.jars", null));
/*     */     
/*  88 */     this.bootupClassSearch = new BootupClassPathSearch(null, packages, jars);
/*  89 */     this.xmlConfigLoader = new XmlConfigLoader(null);
/*     */     
/*  91 */     this.xmlConfig = this.xmlConfigLoader.load();
/*     */ 
/*     */ 
/*     */     
/*  95 */     ShutdownManager.registerServerFactory(this);
/*     */   }
/*     */   private final XmlConfigLoader xmlConfigLoader; private final XmlConfig xmlConfig;
/*     */   
/*     */   private List<String> getSearchJarsPackages(String searchPackages) {
/* 100 */     List<String> hitList = new ArrayList<String>();
/*     */     
/* 102 */     if (searchPackages != null) {
/*     */       
/* 104 */       String[] entries = searchPackages.split("[ ,;]");
/* 105 */       for (int i = 0; i < entries.length; i++) {
/* 106 */         hitList.add(entries[i].trim());
/*     */       }
/*     */     } 
/* 109 */     return hitList;
/*     */   }
/*     */ 
/*     */   
/* 113 */   public void shutdown() { this.clusterManager.shutdown(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiEbeanServer createServer(String name) {
/* 122 */     ConfigBuilder b = new ConfigBuilder();
/* 123 */     ServerConfig config = b.build(name);
/*     */     
/* 125 */     return createServer(config);
/*     */   }
/*     */ 
/*     */   
/*     */   private BackgroundExecutor createBackgroundExecutor(ServerConfig serverConfig, int uniqueServerId) {
/* 130 */     String namePrefix = "Ebean-" + serverConfig.getName();
/*     */ 
/*     */     
/* 133 */     int schedulePoolSize = GlobalProperties.getInt("backgroundExecutor.schedulePoolsize", 1);
/*     */ 
/*     */     
/* 136 */     int minPoolSize = GlobalProperties.getInt("backgroundExecutor.minPoolSize", 1);
/* 137 */     int poolSize = GlobalProperties.getInt("backgroundExecutor.poolsize", 20);
/* 138 */     int maxPoolSize = GlobalProperties.getInt("backgroundExecutor.maxPoolSize", poolSize);
/*     */     
/* 140 */     int idleSecs = GlobalProperties.getInt("backgroundExecutor.idlesecs", 60);
/* 141 */     int shutdownSecs = GlobalProperties.getInt("backgroundExecutor.shutdownSecs", 30);
/*     */     
/* 143 */     boolean useTrad = GlobalProperties.getBoolean("backgroundExecutor.traditional", true);
/*     */     
/* 145 */     if (useTrad) {
/*     */ 
/*     */       
/* 148 */       ThreadPool pool = ThreadPoolManager.getThreadPool(namePrefix);
/* 149 */       pool.setMinSize(minPoolSize);
/* 150 */       pool.setMaxSize(maxPoolSize);
/* 151 */       pool.setMaxIdleTime((idleSecs * 1000));
/* 152 */       return new TraditionalBackgroundExecutor(pool, schedulePoolSize, shutdownSecs, namePrefix);
/*     */     } 
/* 154 */     return new DefaultBackgroundExecutor(poolSize, schedulePoolSize, idleSecs, shutdownSecs, namePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiEbeanServer createServer(ServerConfig serverConfig) {
/* 163 */     synchronized (this) {
/* 164 */       MBeanServer mbeanServer; setNamingConvention(serverConfig);
/*     */       
/* 166 */       BootupClasses bootupClasses = getBootupClasses(serverConfig);
/*     */       
/* 168 */       setDataSource(serverConfig);
/*     */       
/* 170 */       boolean online = checkDataSource(serverConfig);
/*     */ 
/*     */       
/* 173 */       setDatabasePlatform(serverConfig);
/* 174 */       if (serverConfig.getDbEncrypt() != null)
/*     */       {
/* 176 */         serverConfig.getDatabasePlatform().setDbEncrypt(serverConfig.getDbEncrypt());
/*     */       }
/*     */       
/* 179 */       DatabasePlatform dbPlatform = serverConfig.getDatabasePlatform();
/*     */       
/* 181 */       OraclePstmtBatch oraclePstmtBatch = null;
/*     */       
/* 183 */       if (dbPlatform.getName().startsWith("oracle")) {
/* 184 */         PstmtDelegate pstmtDelegate = serverConfig.getPstmtDelegate();
/* 185 */         if (pstmtDelegate == null)
/*     */         {
/* 187 */           pstmtDelegate = getOraclePstmtDelegate(serverConfig.getDataSource());
/*     */         }
/* 189 */         if (pstmtDelegate != null)
/*     */         {
/*     */           
/* 192 */           oraclePstmtBatch = new OraclePstmtBatch(pstmtDelegate);
/*     */         }
/* 194 */         if (oraclePstmtBatch == null) {
/*     */           
/* 196 */           logger.warning("Can not support JDBC batching with Oracle without a PstmtDelegate");
/* 197 */           serverConfig.setPersistBatching(false);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 202 */       serverConfig.getNamingConvention().setDatabasePlatform(serverConfig.getDatabasePlatform());
/*     */       
/* 204 */       ServerCacheManager cacheManager = getCacheManager(serverConfig);
/*     */       
/* 206 */       int uniqueServerId = this.serverId.incrementAndGet();
/* 207 */       BackgroundExecutor bgExecutor = createBackgroundExecutor(serverConfig, uniqueServerId);
/*     */       
/* 209 */       InternalConfiguration c = new InternalConfiguration(this.xmlConfig, this.clusterManager, cacheManager, bgExecutor, serverConfig, bootupClasses, oraclePstmtBatch);
/*     */ 
/*     */       
/* 212 */       DefaultServer server = new DefaultServer(c, cacheManager);
/*     */       
/* 214 */       cacheManager.init(server);
/*     */ 
/*     */       
/* 217 */       ArrayList<?> list = MBeanServerFactory.findMBeanServer(null);
/* 218 */       if (list.size() == 0) {
/*     */         
/* 220 */         mbeanServer = MBeanServerFactory.createMBeanServer();
/*     */       } else {
/*     */         
/* 223 */         mbeanServer = (MBeanServer)list.get(0);
/*     */       } 
/*     */       
/* 226 */       server.registerMBeans(mbeanServer, uniqueServerId);
/*     */ 
/*     */       
/* 229 */       executeDDL(server, online);
/*     */ 
/*     */       
/* 232 */       server.initialise();
/*     */       
/* 234 */       if (online) {
/* 235 */         if (this.clusterManager.isClustering())
/*     */         {
/* 237 */           this.clusterManager.registerServer(server);
/*     */         }
/*     */ 
/*     */         
/* 241 */         int delaySecs = GlobalProperties.getInt("ebean.cacheWarmingDelay", 30);
/* 242 */         long sleepMillis = (1000 * delaySecs);
/*     */         
/* 244 */         if (sleepMillis > 0L) {
/* 245 */           Timer t = new Timer("EbeanCacheWarmer", true);
/* 246 */           t.schedule(new CacheWarmer(sleepMillis, server), sleepMillis);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 251 */       server.start();
/* 252 */       return server;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PstmtDelegate getOraclePstmtDelegate(DataSource ds) {
/* 258 */     if (ds instanceof com.avaje.ebeaninternal.server.lib.sql.DataSourcePool)
/*     */     {
/* 260 */       return new StandardPstmtDelegate();
/*     */     }
/*     */     
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerCacheManager getCacheManager(ServerConfig serverConfig) {
/* 273 */     ServerCacheOptions beanOptions = null;
/* 274 */     if (beanOptions == null) {
/*     */       
/* 276 */       beanOptions = new ServerCacheOptions();
/* 277 */       beanOptions.setMaxSize(GlobalProperties.getInt("cache.maxSize", 1000));
/* 278 */       beanOptions.setMaxIdleSecs(GlobalProperties.getInt("cache.maxIdleTime", 600));
/* 279 */       beanOptions.setMaxSecsToLive(GlobalProperties.getInt("cache.maxTimeToLive", 21600));
/*     */     } 
/*     */ 
/*     */     
/* 283 */     ServerCacheOptions queryOptions = null;
/* 284 */     if (queryOptions == null) {
/*     */       
/* 286 */       queryOptions = new ServerCacheOptions();
/* 287 */       queryOptions.setMaxSize(GlobalProperties.getInt("querycache.maxSize", 100));
/* 288 */       queryOptions.setMaxIdleSecs(GlobalProperties.getInt("querycache.maxIdleTime", 600));
/* 289 */       queryOptions.setMaxSecsToLive(GlobalProperties.getInt("querycache.maxTimeToLive", 21600));
/*     */     } 
/*     */ 
/*     */     
/* 293 */     DefaultServerCacheFactory defaultServerCacheFactory = null;
/* 294 */     if (defaultServerCacheFactory == null) {
/* 295 */       defaultServerCacheFactory = new DefaultServerCacheFactory();
/*     */     }
/*     */     
/* 298 */     return new DefaultServerCacheManager(defaultServerCacheFactory, beanOptions, queryOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BootupClasses getBootupClasses(ServerConfig serverConfig) {
/* 307 */     BootupClasses bootupClasses = getBootupClasses1(serverConfig);
/* 308 */     bootupClasses.addPersistControllers(serverConfig.getPersistControllers());
/*     */     
/* 310 */     return bootupClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BootupClasses getBootupClasses1(ServerConfig serverConfig) {
/* 318 */     List<Class<?>> entityClasses = serverConfig.getClasses();
/* 319 */     if (entityClasses != null && entityClasses.size() > 0)
/*     */     {
/* 321 */       return new BootupClasses(serverConfig.getClasses());
/*     */     }
/*     */     
/* 324 */     List<String> jars = serverConfig.getJars();
/* 325 */     List<String> packages = serverConfig.getPackages();
/*     */     
/* 327 */     if ((packages != null && !packages.isEmpty()) || (jars != null && !jars.isEmpty())) {
/*     */       
/* 329 */       BootupClassPathSearch search = new BootupClassPathSearch(null, packages, jars);
/* 330 */       return search.getBootupClasses();
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return this.bootupClassSearch.getBootupClasses().createCopy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   private void executeDDL(SpiEbeanServer server, boolean online) { server.getDdlGenerator().execute(online); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNamingConvention(ServerConfig config) {
/* 349 */     if (config.getNamingConvention() == null) {
/* 350 */       UnderscoreNamingConvention nc = new UnderscoreNamingConvention();
/* 351 */       config.setNamingConvention(nc);
/*     */       
/* 353 */       String v = config.getProperty("namingConvention.useForeignKeyPrefix");
/* 354 */       if (v != null) {
/* 355 */         boolean useForeignKeyPrefix = Boolean.valueOf(v).booleanValue();
/* 356 */         nc.setUseForeignKeyPrefix(useForeignKeyPrefix);
/*     */       } 
/*     */       
/* 359 */       String sequenceFormat = config.getProperty("namingConvention.sequenceFormat");
/* 360 */       if (sequenceFormat != null) {
/* 361 */         nc.setSequenceFormat(sequenceFormat);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDatabasePlatform(ServerConfig config) {
/* 372 */     DatabasePlatform dbPlatform = config.getDatabasePlatform();
/* 373 */     if (dbPlatform == null) {
/*     */       
/* 375 */       DatabasePlatformFactory factory = new DatabasePlatformFactory();
/*     */       
/* 377 */       DatabasePlatform db = factory.create(config);
/* 378 */       config.setDatabasePlatform(db);
/* 379 */       logger.info("DatabasePlatform name:" + config.getName() + " platform:" + db.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDataSource(ServerConfig config) {
/* 387 */     if (config.getDataSource() == null) {
/* 388 */       DataSource ds = getDataSourceFromConfig(config);
/* 389 */       config.setDataSource(ds);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private DataSource getDataSourceFromConfig(ServerConfig config) {
/* 395 */     DataSource ds = null;
/*     */     
/* 397 */     if (config.getDataSourceJndiName() != null) {
/* 398 */       ds = this.jndiDataSourceFactory.lookup(config.getDataSourceJndiName());
/* 399 */       if (ds == null) {
/* 400 */         String m = "JNDI lookup for DataSource " + config.getDataSourceJndiName() + " returned null.";
/* 401 */         throw new PersistenceException(m);
/*     */       } 
/* 403 */       return ds;
/*     */     } 
/*     */ 
/*     */     
/* 407 */     DataSourceConfig dsConfig = config.getDataSourceConfig();
/* 408 */     if (dsConfig == null) {
/* 409 */       String m = "No DataSourceConfig definded for " + config.getName();
/* 410 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 413 */     if (dsConfig.isOffline()) {
/* 414 */       if (config.getDatabasePlatformName() == null) {
/* 415 */         String m = "You MUST specify a DatabasePlatformName on ServerConfig when offline";
/* 416 */         throw new PersistenceException(m);
/*     */       } 
/* 418 */       return null;
/*     */     } 
/*     */     
/* 421 */     if (dsConfig.getHeartbeatSql() == null) {
/*     */       
/* 423 */       String heartbeatSql = getHeartbeatSql(dsConfig.getDriver());
/* 424 */       dsConfig.setHeartbeatSql(heartbeatSql);
/*     */     } 
/*     */     
/* 427 */     return DataSourceGlobalManager.getDataSource(config.getName(), dsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getHeartbeatSql(String driver) {
/* 434 */     if (driver != null) {
/* 435 */       String d = driver.toLowerCase();
/* 436 */       if (d.contains("oracle")) {
/* 437 */         return "select 'x' from dual";
/*     */       }
/* 439 */       if (d.contains(".h2.") || d.contains(".mysql.") || d.contains("postgre")) {
/* 440 */         return "select 1";
/*     */       }
/*     */     } 
/* 443 */     return null;
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
/*     */   private boolean checkDataSource(ServerConfig serverConfig) {
/* 458 */     if (serverConfig.getDataSource() == null) {
/* 459 */       if (serverConfig.getDataSourceConfig().isOffline())
/*     */       {
/* 461 */         return false;
/*     */       }
/* 463 */       throw new RuntimeException("DataSource not set?");
/*     */     } 
/*     */     
/* 466 */     c = null;
/*     */     try {
/* 468 */       c = serverConfig.getDataSource().getConnection();
/*     */       
/* 470 */       if (c.getAutoCommit()) {
/* 471 */         String m = "DataSource [" + serverConfig.getName() + "] has autoCommit defaulting to true!";
/* 472 */         logger.warning(m);
/*     */       } 
/*     */       
/* 475 */       return true;
/*     */     }
/* 477 */     catch (SQLException ex) {
/* 478 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/* 481 */       if (c != null) {
/*     */         try {
/* 483 */           c.close();
/* 484 */         } catch (SQLException ex) {
/* 485 */           logger.log(Level.SEVERE, null, ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class CacheWarmer
/*     */     extends TimerTask
/*     */   {
/* 494 */     private static final Logger log = Logger.getLogger(CacheWarmer.class.getName());
/*     */     
/*     */     private final long sleepMillis;
/*     */     private final EbeanServer server;
/*     */     
/*     */     CacheWarmer(long sleepMillis, EbeanServer server) {
/* 500 */       this.sleepMillis = sleepMillis;
/* 501 */       this.server = server;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 506 */         Thread.sleep(this.sleepMillis);
/* 507 */       } catch (InterruptedException e) {
/* 508 */         String msg = "Error while sleeping prior to cache warming";
/* 509 */         log.log(Level.SEVERE, msg, e);
/*     */       } 
/* 511 */       this.server.runCacheWarming();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultServerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */