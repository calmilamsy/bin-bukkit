/*      */ package com.avaje.ebean;
/*      */ 
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.text.csv.CsvReader;
/*      */ import com.avaje.ebean.text.json.JsonContext;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.logging.Logger;
/*      */ import javax.persistence.OptimisticLockException;
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
/*      */ public final class Ebean
/*      */ {
/*  138 */   private static final Logger logger = Logger.getLogger(Ebean.class.getName());
/*      */   
/*      */   private static final String EBVERSION = "2.7.3";
/*      */   
/*      */   private static final ServerManager serverMgr;
/*      */ 
/*      */   
/*      */   static  {
/*  146 */     version = System.getProperty("java.version");
/*  147 */     logger.info("Ebean Version[2.7.3] Java Version[" + version + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  153 */     serverMgr = new ServerManager(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ServerManager
/*      */   {
/*  164 */     private final ConcurrentHashMap<String, EbeanServer> concMap = new ConcurrentHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  170 */     private final HashMap<String, EbeanServer> syncMap = new HashMap();
/*      */     
/*  172 */     private final Object monitor = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EbeanServer primaryServer;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ServerManager() {
/*  183 */       if (GlobalProperties.isSkipPrimaryServer()) {
/*      */ 
/*      */         
/*  186 */         logger.fine("GlobalProperties.isSkipPrimaryServer()");
/*      */       }
/*      */       else {
/*      */         
/*  190 */         String primaryName = getPrimaryServerName();
/*  191 */         logger.fine("primaryName:" + primaryName);
/*  192 */         if (primaryName != null && primaryName.trim().length() > 0) {
/*  193 */           this.primaryServer = getWithCreate(primaryName.trim());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private String getPrimaryServerName() {
/*  200 */       String serverName = GlobalProperties.get("ebean.default.datasource", null);
/*  201 */       return GlobalProperties.get("datasource.default", serverName);
/*      */     }
/*      */     
/*      */     private EbeanServer getPrimaryServer() {
/*  205 */       if (this.primaryServer == null) {
/*  206 */         String msg = "The default EbeanServer has not been defined?";
/*  207 */         msg = msg + " This is normally set via the ebean.datasource.default property.";
/*  208 */         msg = msg + " Otherwise it should be registered programatically via registerServer()";
/*  209 */         throw new PersistenceException(msg);
/*      */       } 
/*  211 */       return this.primaryServer;
/*      */     }
/*      */     
/*      */     private EbeanServer get(String name) {
/*  215 */       if (name == null || name.length() == 0) {
/*  216 */         return this.primaryServer;
/*      */       }
/*      */       
/*  219 */       EbeanServer server = (EbeanServer)this.concMap.get(name);
/*  220 */       if (server != null) {
/*  221 */         return server;
/*      */       }
/*      */       
/*  224 */       return getWithCreate(name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EbeanServer getWithCreate(String name) {
/*  232 */       synchronized (this.monitor) {
/*      */         
/*  234 */         EbeanServer server = (EbeanServer)this.syncMap.get(name);
/*  235 */         if (server == null) {
/*      */           
/*  237 */           server = EbeanServerFactory.create(name);
/*  238 */           register(server, false);
/*      */         } 
/*  240 */         return server;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void register(EbeanServer server, boolean isPrimaryServer) {
/*  248 */       synchronized (this.monitor) {
/*  249 */         this.concMap.put(server.getName(), server);
/*  250 */         EbeanServer existingServer = (EbeanServer)this.syncMap.put(server.getName(), server);
/*  251 */         if (existingServer != null) {
/*  252 */           String msg = "Existing EbeanServer [" + server.getName() + "] is being replaced?";
/*  253 */           logger.warning(msg);
/*      */         } 
/*      */         
/*  256 */         if (isPrimaryServer) {
/*  257 */           this.primaryServer = server;
/*      */         }
/*      */       } 
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
/*  288 */   public static EbeanServer getServer(String name) { return serverMgr.get(name); }
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
/*  309 */   public static ExpressionFactory getExpressionFactory() { return serverMgr.getPrimaryServer().getExpressionFactory(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   protected static void register(EbeanServer server, boolean isPrimaryServer) { serverMgr.register(server, isPrimaryServer); }
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
/*  333 */   public static Object nextId(Class<?> beanType) { return serverMgr.getPrimaryServer().nextId(beanType); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  343 */   public static void logComment(String msg) { serverMgr.getPrimaryServer().logComment(msg); }
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
/*  386 */   public static Transaction beginTransaction() { return serverMgr.getPrimaryServer().beginTransaction(); }
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
/*  397 */   public static Transaction beginTransaction(TxIsolation isolation) { return serverMgr.getPrimaryServer().beginTransaction(isolation); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  405 */   public static Transaction currentTransaction() { return serverMgr.getPrimaryServer().currentTransaction(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  412 */   public static void commitTransaction() { serverMgr.getPrimaryServer().commitTransaction(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  419 */   public static void rollbackTransaction() { serverMgr.getPrimaryServer().rollbackTransaction(); }
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
/*  446 */   public static void endTransaction() { serverMgr.getPrimaryServer().endTransaction(); }
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
/*  465 */   public static InvalidValue validate(Object bean) { return serverMgr.getPrimaryServer().validate(bean); }
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
/*  489 */   public static InvalidValue[] validate(Object bean, String propertyName, Object value) { return serverMgr.getPrimaryServer().validate(bean, propertyName, value); }
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
/*  500 */   public static Map<String, ValuePair> diff(Object a, Object b) { return serverMgr.getPrimaryServer().diff(a, b); }
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
/*  538 */   public static void save(Object bean) throws OptimisticLockException { serverMgr.getPrimaryServer().save(bean); }
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
/*  581 */   public static void update(Object bean) throws OptimisticLockException { serverMgr.getPrimaryServer().update(bean); }
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
/*  601 */   public static void update(Object bean, Set<String> updateProps) { serverMgr.getPrimaryServer().update(bean, updateProps); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  608 */   public static int save(Iterator<?> iterator) throws OptimisticLockException { return serverMgr.getPrimaryServer().save(iterator); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  615 */   public static int save(Collection<?> c) throws OptimisticLockException { return save(c.iterator()); }
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
/*  629 */   public static int deleteManyToManyAssociations(Object ownerBean, String propertyName) { return serverMgr.getPrimaryServer().deleteManyToManyAssociations(ownerBean, propertyName); }
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
/*  647 */   public static void saveManyToManyAssociations(Object ownerBean, String propertyName) { serverMgr.getPrimaryServer().saveManyToManyAssociations(ownerBean, propertyName); }
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
/*  667 */   public static void saveAssociation(Object ownerBean, String propertyName) { serverMgr.getPrimaryServer().saveAssociation(ownerBean, propertyName); }
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
/*  678 */   public static void delete(Object bean) throws OptimisticLockException { serverMgr.getPrimaryServer().delete(bean); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  685 */   public static int delete(Class<?> beanType, Object id) { return serverMgr.getPrimaryServer().delete(beanType, id); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  692 */   public static void delete(Class<?> beanType, Collection<?> ids) { serverMgr.getPrimaryServer().delete(beanType, ids); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  699 */   public static int delete(Iterator<?> it) throws OptimisticLockException { return serverMgr.getPrimaryServer().delete(it); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  706 */   public static int delete(Collection<?> c) throws OptimisticLockException { return delete(c.iterator()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  716 */   public static void refresh(Object bean) throws OptimisticLockException { serverMgr.getPrimaryServer().refresh(bean); }
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
/*  735 */   public static void refreshMany(Object bean, String manyPropertyName) { serverMgr.getPrimaryServer().refreshMany(bean, manyPropertyName); }
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
/*  761 */   public static <T> T getReference(Class<T> beanType, Object id) { return (T)serverMgr.getPrimaryServer().getReference(beanType, id); }
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
/*  807 */   public static <T> void sort(List<T> list, String sortByClause) { serverMgr.getPrimaryServer().sort(list, sortByClause); }
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
/*  855 */   public static <T> T find(Class<T> beanType, Object id) { return (T)serverMgr.getPrimaryServer().find(beanType, id); }
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
/*  867 */   public static SqlQuery createSqlQuery(String sql) { return serverMgr.getPrimaryServer().createSqlQuery(sql); }
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
/*  880 */   public static SqlQuery createNamedSqlQuery(String namedQuery) { return serverMgr.getPrimaryServer().createNamedSqlQuery(namedQuery); }
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
/*  900 */   public static SqlUpdate createSqlUpdate(String sql) { return serverMgr.getPrimaryServer().createSqlUpdate(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  909 */   public static CallableSql createCallableSql(String sql) { return serverMgr.getPrimaryServer().createCallableSql(sql); }
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
/*  930 */   public static SqlUpdate createNamedSqlUpdate(String namedQuery) { return serverMgr.getPrimaryServer().createNamedSqlUpdate(namedQuery); }
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
/*  957 */   public static <T> Query<T> createNamedQuery(Class<T> beanType, String namedQuery) { return serverMgr.getPrimaryServer().createNamedQuery(beanType, namedQuery); }
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
/*  985 */   public static <T> Query<T> createQuery(Class<T> beanType, String query) { return serverMgr.getPrimaryServer().createQuery(beanType, query); }
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
/* 1041 */   public static <T> Update<T> createNamedUpdate(Class<T> beanType, String namedUpdate) { return serverMgr.getPrimaryServer().createNamedUpdate(beanType, namedUpdate); }
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
/* 1074 */   public static <T> Update<T> createUpdate(Class<T> beanType, String ormUpdate) { return serverMgr.getPrimaryServer().createUpdate(beanType, ormUpdate); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1082 */   public static <T> CsvReader<T> createCsvReader(Class<T> beanType) { return serverMgr.getPrimaryServer().createCsvReader(beanType); }
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
/* 1138 */   public static <T> Query<T> createQuery(Class<T> beanType) { return serverMgr.getPrimaryServer().createQuery(beanType); }
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
/* 1155 */   public static <T> Query<T> find(Class<T> beanType) { return serverMgr.getPrimaryServer().find(beanType); }
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
/* 1169 */   public static <T> Filter<T> filter(Class<T> beanType) { return serverMgr.getPrimaryServer().filter(beanType); }
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
/* 1214 */   public static int execute(SqlUpdate sqlUpdate) { return serverMgr.getPrimaryServer().execute(sqlUpdate); }
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
/* 1241 */   public static int execute(CallableSql callableSql) { return serverMgr.getPrimaryServer().execute(callableSql); }
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
/* 1265 */   public static void execute(TxScope scope, TxRunnable r) { serverMgr.getPrimaryServer().execute(scope, r); }
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
/* 1291 */   public static void execute(TxRunnable r) { serverMgr.getPrimaryServer().execute(r); }
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
/* 1316 */   public static <T> T execute(TxScope scope, TxCallable<T> c) { return (T)serverMgr.getPrimaryServer().execute(scope, c); }
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
/* 1348 */   public static <T> T execute(TxCallable<T> c) { return (T)serverMgr.getPrimaryServer().execute(c); }
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
/* 1388 */   public static void externalModification(String tableName, boolean inserts, boolean updates, boolean deletes) { serverMgr.getPrimaryServer().externalModification(tableName, inserts, updates, deletes); }
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
/* 1399 */   public static BeanState getBeanState(Object bean) { return serverMgr.getPrimaryServer().getBeanState(bean); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1407 */   public static ServerCacheManager getServerCacheManager() { return serverMgr.getPrimaryServer().getServerCacheManager(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1415 */   public static BackgroundExecutor getBackgroundExecutor() { return serverMgr.getPrimaryServer().getBackgroundExecutor(); }
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
/* 1426 */   public static void runCacheWarming() { serverMgr.getPrimaryServer().runCacheWarming(); }
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
/* 1438 */   public static void runCacheWarming(Class<?> beanType) { serverMgr.getPrimaryServer().runCacheWarming(beanType); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1446 */   public static JsonContext createJsonContext() { return serverMgr.getPrimaryServer().createJsonContext(); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Ebean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */