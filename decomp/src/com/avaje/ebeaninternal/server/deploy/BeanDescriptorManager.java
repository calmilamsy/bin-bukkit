/*      */ package com.avaje.ebeaninternal.server.deploy;
/*      */ 
/*      */ import com.avaje.ebean.BackgroundExecutor;
/*      */ import com.avaje.ebean.RawSql;
/*      */ import com.avaje.ebean.RawSqlBuilder;
/*      */ import com.avaje.ebean.bean.EntityBean;
/*      */ import com.avaje.ebean.cache.ServerCacheManager;
/*      */ import com.avaje.ebean.config.EncryptKey;
/*      */ import com.avaje.ebean.config.EncryptKeyManager;
/*      */ import com.avaje.ebean.config.GlobalProperties;
/*      */ import com.avaje.ebean.config.NamingConvention;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.dbplatform.DbIdentity;
/*      */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*      */ import com.avaje.ebean.config.dbplatform.IdType;
/*      */ import com.avaje.ebean.config.lucene.IndexDefn;
/*      */ import com.avaje.ebean.event.BeanFinder;
/*      */ import com.avaje.ebean.validation.factory.LengthValidatorFactory;
/*      */ import com.avaje.ebean.validation.factory.NotNullValidatorFactory;
/*      */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*      */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*      */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.InternString;
/*      */ import com.avaje.ebeaninternal.server.core.InternalConfiguration;
/*      */ import com.avaje.ebeaninternal.server.core.Message;
/*      */ import com.avaje.ebeaninternal.server.core.XmlConfig;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmbedded;
/*      */ import com.avaje.ebeaninternal.server.deploy.id.IdBinderFactory;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.ReadAnnotations;
/*      */ import com.avaje.ebeaninternal.server.deploy.parse.TransientProperties;
/*      */ import com.avaje.ebeaninternal.server.idgen.UuidIdGenerator;
/*      */ import com.avaje.ebeaninternal.server.lib.util.Dnode;
/*      */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*      */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflect;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectFactory;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.EnhanceBeanReflectFactory;
/*      */ import com.avaje.ebeaninternal.server.subclass.SubClassManager;
/*      */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*      */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.persistence.PersistenceException;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BeanDescriptorManager
/*      */   implements BeanDescriptorMap
/*      */ {
/*   99 */   private static final Logger logger = Logger.getLogger(BeanDescriptorManager.class.getName());
/*      */   
/*  101 */   private static final BeanDescComparator beanDescComparator = new BeanDescComparator(null); private final ReadAnnotations readAnnotations; private final TransientProperties transientProperties; private final DeployInherit deplyInherit; private final BeanReflectFactory reflectFactory; private final DeployUtil deployUtil; private final TypeManager typeManager; private final PersistControllerManager persistControllerManager; private final BeanFinderManager beanFinderManager; private final PersistListenerManager persistListenerManager; private final BeanQueryAdapterManager beanQueryAdapterManager; private final SubClassManager subClassManager; private final NamingConvention namingConvention; private final DeployCreateProperties createProperties; private final DeployOrmXml deployOrmXml; private final BeanManagerFactory beanManagerFactory; private int enhancedClassCount; private int subclassClassCount; private final HashSet<String> subclassedEntities; private final boolean updateChangesOnly; private final BootupClasses bootupClasses;
/*      */   public BeanDescriptorManager(InternalConfiguration config, LuceneIndexManager luceneManager) {
/*  103 */     this.readAnnotations = new ReadAnnotations();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  138 */     this.subclassedEntities = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  146 */     this.deplyInfoMap = new HashMap();
/*      */     
/*  148 */     this.beanTableMap = new HashMap();
/*      */     
/*  150 */     this.descMap = new HashMap();
/*  151 */     this.idDescMap = new HashMap();
/*      */     
/*  153 */     this.beanManagerMap = new HashMap();
/*      */     
/*  155 */     this.tableToDescMap = new HashMap();
/*      */ 
/*      */ 
/*      */     
/*  159 */     this.descriptorUniqueIds = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  167 */     this.uuidIdGenerator = new UuidIdGenerator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     this.luceneManager = luceneManager;
/*      */     
/*  190 */     this.serverName = InternString.intern(config.getServerConfig().getName());
/*  191 */     this.cacheManager = config.getCacheManager();
/*  192 */     this.xmlConfig = config.getXmlConfig();
/*  193 */     this.dbSequenceBatchSize = config.getServerConfig().getDatabaseSequenceBatchSize();
/*  194 */     this.backgroundExecutor = config.getBackgroundExecutor();
/*  195 */     this.dataSource = config.getServerConfig().getDataSource();
/*  196 */     this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
/*  197 */     this.databasePlatform = config.getServerConfig().getDatabasePlatform();
/*  198 */     this.idBinderFactory = new IdBinderFactory(this.databasePlatform.isIdInExpandedForm());
/*      */     
/*  200 */     this.bootupClasses = config.getBootupClasses();
/*  201 */     this.createProperties = config.getDeployCreateProperties();
/*  202 */     this.subClassManager = config.getSubClassManager();
/*  203 */     this.typeManager = config.getTypeManager();
/*  204 */     this.namingConvention = config.getServerConfig().getNamingConvention();
/*  205 */     this.dbIdentity = config.getDatabasePlatform().getDbIdentity();
/*  206 */     this.deplyInherit = config.getDeployInherit();
/*  207 */     this.deployOrmXml = config.getDeployOrmXml();
/*  208 */     this.deployUtil = config.getDeployUtil();
/*      */     
/*  210 */     this.beanManagerFactory = new BeanManagerFactory(config.getServerConfig(), config.getDatabasePlatform());
/*      */     
/*  212 */     this.updateChangesOnly = config.getServerConfig().isUpdateChangesOnly();
/*      */     
/*  214 */     this.persistControllerManager = new PersistControllerManager(this.bootupClasses);
/*  215 */     this.persistListenerManager = new PersistListenerManager(this.bootupClasses);
/*  216 */     this.beanQueryAdapterManager = new BeanQueryAdapterManager(this.bootupClasses);
/*      */     
/*  218 */     this.beanFinderManager = new DefaultBeanFinderManager();
/*      */     
/*  220 */     this.reflectFactory = createReflectionFactory();
/*  221 */     this.transientProperties = new TransientProperties();
/*      */   }
/*      */   private final String serverName; private Map<Class<?>, DeployBeanInfo<?>> deplyInfoMap; private final Map<Class<?>, BeanTable> beanTableMap; private final Map<String, BeanDescriptor<?>> descMap; private final Map<String, BeanDescriptor<?>> idDescMap; private final Map<String, BeanManager<?>> beanManagerMap; private final Map<String, List<BeanDescriptor<?>>> tableToDescMap; private List<BeanDescriptor<?>> immutableDescriptorList; private final Set<Integer> descriptorUniqueIds; private final DbIdentity dbIdentity; private final DataSource dataSource; private final DatabasePlatform databasePlatform; private final UuidIdGenerator uuidIdGenerator; private final ServerCacheManager cacheManager; private final BackgroundExecutor backgroundExecutor; private final int dbSequenceBatchSize; private final EncryptKeyManager encryptKeyManager; private final IdBinderFactory idBinderFactory; private final XmlConfig xmlConfig; private final LuceneIndexManager luceneManager;
/*      */   
/*  225 */   public BeanDescriptor<?> getBeanDescriptorById(String descriptorId) { return (BeanDescriptor)this.idDescMap.get(descriptorId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> entityType) {
/*  232 */     String className = SubClassUtil.getSuperClassName(entityType.getName());
/*  233 */     return (BeanDescriptor)this.descMap.get(className);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> BeanDescriptor<T> getBeanDescriptor(String entityClassName) {
/*  240 */     entityClassName = SubClassUtil.getSuperClassName(entityClassName);
/*  241 */     return (BeanDescriptor)this.descMap.get(entityClassName);
/*      */   }
/*      */ 
/*      */   
/*  245 */   public String getServerName() { return this.serverName; }
/*      */ 
/*      */ 
/*      */   
/*  249 */   public ServerCacheManager getCacheManager() { return this.cacheManager; }
/*      */ 
/*      */ 
/*      */   
/*  253 */   public NamingConvention getNamingConvention() { return this.namingConvention; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEbeanServer(SpiEbeanServer internalEbean) {
/*  260 */     for (BeanDescriptor<?> desc : this.immutableDescriptorList) {
/*  261 */       desc.setEbeanServer(internalEbean);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  266 */   public IdBinder createIdBinder(BeanProperty[] uids) { return this.idBinderFactory.createIdBinder(uids); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void deploy() {
/*      */     try {
/*  272 */       createListeners();
/*  273 */       readEmbeddedDeployment();
/*  274 */       readEntityDeploymentInitial();
/*  275 */       assignLuceneIndexDefns();
/*  276 */       readEntityBeanTable();
/*  277 */       readEntityDeploymentAssociations();
/*  278 */       readInheritedIdGenerators();
/*      */ 
/*      */       
/*  281 */       readEntityRelationships();
/*  282 */       readRawSqlQueries();
/*      */       
/*  284 */       List<BeanDescriptor<?>> list = new ArrayList<BeanDescriptor<?>>(this.descMap.values());
/*  285 */       Collections.sort(list, beanDescComparator);
/*  286 */       this.immutableDescriptorList = Collections.unmodifiableList(list);
/*      */ 
/*      */       
/*  289 */       for (BeanDescriptor<?> d : list) {
/*  290 */         this.idDescMap.put(d.getDescriptorId(), d);
/*      */       }
/*      */       
/*  293 */       initialiseAll();
/*  294 */       readForeignKeys();
/*      */       
/*  296 */       readTableToDescriptor();
/*      */       
/*  298 */       logStatus();
/*      */       
/*  300 */       this.deplyInfoMap.clear();
/*  301 */       this.deplyInfoMap = null;
/*  302 */     } catch (RuntimeException e) {
/*  303 */       String msg = "Error in deployment";
/*  304 */       logger.log(Level.SEVERE, msg, e);
/*  305 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   public EncryptKey getEncryptKey(String tableName, String columnName) { return this.encryptKeyManager.getEncryptKey(tableName, columnName); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cacheNotify(TransactionEventTable.TableIUD tableIUD) {
/*  322 */     List<BeanDescriptor<?>> list = getBeanDescriptors(tableIUD.getTable());
/*  323 */     if (list != null) {
/*  324 */       for (int i = 0; i < list.size(); i++) {
/*  325 */         ((BeanDescriptor)list.get(i)).cacheNotify(tableIUD);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  334 */   public List<BeanDescriptor<?>> getBeanDescriptors(String tableName) { return (List)this.tableToDescMap.get(tableName.toLowerCase()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readTableToDescriptor() {
/*  345 */     for (BeanDescriptor<?> desc : this.descMap.values()) {
/*  346 */       String baseTable = desc.getBaseTable();
/*  347 */       if (baseTable == null) {
/*      */         continue;
/*      */       }
/*  350 */       baseTable = baseTable.toLowerCase();
/*      */       
/*  352 */       List<BeanDescriptor<?>> list = (List)this.tableToDescMap.get(baseTable);
/*  353 */       if (list == null) {
/*  354 */         list = new ArrayList<BeanDescriptor<?>>(true);
/*  355 */         this.tableToDescMap.put(baseTable, list);
/*      */       } 
/*  357 */       list.add(desc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readForeignKeys() {
/*  364 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  365 */       d.initialiseFkeys();
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
/*      */   private void initialiseAll() {
/*  390 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  391 */       d.initialiseId();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  396 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  397 */       d.initInheritInfo();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  402 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  403 */       d.initialiseOther();
/*      */     }
/*      */     
/*  406 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  407 */       IndexDefn<?> luceneIndexDefn = d.getLuceneIndexDefn();
/*  408 */       if (luceneIndexDefn != null) {
/*      */         try {
/*  410 */           LIndex luceneIndex = this.luceneManager.create(luceneIndexDefn, d);
/*  411 */           d.setLuceneIndex(luceneIndex);
/*  412 */         } catch (IOException e) {
/*  413 */           String msg = "Error creating Lucene Index " + luceneIndexDefn.getClass().getName();
/*  414 */           logger.log(Level.SEVERE, msg, e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  420 */     for (BeanDescriptor<?> d : this.descMap.values()) {
/*  421 */       if (!d.isEmbedded()) {
/*  422 */         BeanManager<?> m = this.beanManagerFactory.create(d);
/*  423 */         this.beanManagerMap.put(d.getFullName(), m);
/*      */         
/*  425 */         checkForValidEmbeddedId(d);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkForValidEmbeddedId(BeanDescriptor<?> d) {
/*  431 */     IdBinder idBinder = d.getIdBinder();
/*  432 */     if (idBinder != null && idBinder instanceof IdBinderEmbedded) {
/*  433 */       IdBinderEmbedded embId = (IdBinderEmbedded)idBinder;
/*  434 */       BeanDescriptor<?> idBeanDescriptor = embId.getIdBeanDescriptor();
/*  435 */       Class<?> idType = idBeanDescriptor.getBeanType();
/*      */       try {
/*  437 */         idType.getDeclaredMethod("hashCode", new Class[0]);
/*  438 */         idType.getDeclaredMethod("equals", new Class[] { Object.class });
/*  439 */       } catch (NoSuchMethodException e) {
/*  440 */         checkMissingHashCodeOrEquals(e, idType, d.getBeanType());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkMissingHashCodeOrEquals(Exception source, Class<?> idType, Class<?> beanType) {
/*  447 */     String msg = "SERIOUS ERROR: The hashCode() and equals() methods *MUST* be implemented ";
/*  448 */     msg = msg + "on Embedded bean " + idType + " as it is used as an Id for " + beanType;
/*      */     
/*  450 */     if (GlobalProperties.getBoolean("ebean.strict", true)) {
/*  451 */       throw new PersistenceException(msg, source);
/*      */     }
/*  453 */     logger.log(Level.SEVERE, msg, source);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  461 */   public List<BeanDescriptor<?>> getBeanDescriptorList() { return this.immutableDescriptorList; }
/*      */ 
/*      */ 
/*      */   
/*  465 */   public Map<Class<?>, BeanTable> getBeanTables() { return this.beanTableMap; }
/*      */ 
/*      */ 
/*      */   
/*  469 */   public BeanTable getBeanTable(Class<?> type) { return (BeanTable)this.beanTableMap.get(type); }
/*      */ 
/*      */ 
/*      */   
/*  473 */   public Map<String, BeanDescriptor<?>> getBeanDescriptors() { return this.descMap; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  479 */   public <T> BeanManager<T> getBeanManager(Class<T> entityType) { return getBeanManager(entityType.getName()); }
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanManager<?> getBeanManager(String beanClassName) {
/*  484 */     beanClassName = SubClassUtil.getSuperClassName(beanClassName);
/*  485 */     return (BeanManager)this.beanManagerMap.get(beanClassName);
/*      */   }
/*      */ 
/*      */   
/*  489 */   public DNativeQuery getNativeQuery(String name) { return this.deployOrmXml.getNativeQuery(name); }
/*      */ 
/*      */ 
/*      */   
/*      */   private void assignLuceneIndexDefns() {
/*  494 */     List<IndexDefn<?>> list = this.bootupClasses.getLuceneIndexInstances();
/*  495 */     for (int i = 0; i < list.size(); i++) {
/*  496 */       IndexDefn<?> indexDefn = (IndexDefn)list.get(i);
/*  497 */       Class<?> entityClass = getIndexDefnEntityClass(indexDefn.getClass());
/*      */       
/*  499 */       DeployBeanInfo<?> deployBeanInfo = (DeployBeanInfo)this.deplyInfoMap.get(entityClass);
/*  500 */       if (deployBeanInfo == null) {
/*  501 */         String msg = "Could not find entity deployment for " + entityClass;
/*  502 */         throw new PersistenceException(msg);
/*      */       } 
/*  504 */       deployBeanInfo.getDescriptor().setIndexDefn(indexDefn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Class<?> getIndexDefnEntityClass(Class<?> controller) {
/*  510 */     Class<?> cls = ParamTypeUtil.findParamType(controller, IndexDefn.class);
/*      */     
/*  512 */     if (cls == null) {
/*  513 */       String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
/*  514 */       throw new PersistenceException(msg);
/*      */     } 
/*  516 */     return cls;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createListeners() {
/*  524 */     int qa = this.beanQueryAdapterManager.getRegisterCount();
/*  525 */     int cc = this.persistControllerManager.getRegisterCount();
/*  526 */     int lc = this.persistListenerManager.getRegisterCount();
/*  527 */     int fc = this.beanFinderManager.createBeanFinders(this.bootupClasses.getBeanFinders());
/*      */     
/*  529 */     logger.fine("BeanPersistControllers[" + cc + "] BeanFinders[" + fc + "] BeanPersistListeners[" + lc + "] BeanQueryAdapters[" + qa + "]");
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
/*      */   private void logStatus() {
/*  542 */     String msg = "Entities enhanced[" + this.enhancedClassCount + "] subclassed[" + this.subclassClassCount + "]";
/*  543 */     logger.info(msg);
/*      */     
/*  545 */     if (this.enhancedClassCount > 0 && 
/*  546 */       this.subclassClassCount > 0) {
/*  547 */       String subclassEntityNames = this.subclassedEntities.toString();
/*      */       
/*  549 */       String m = "Mixing enhanced and subclassed entities. Subclassed classes:" + subclassEntityNames;
/*  550 */       logger.warning(m);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> BeanDescriptor<T> createEmbedded(Class<T> beanClass) {
/*  557 */     DeployBeanInfo<T> info = createDeployBeanInfo(beanClass);
/*  558 */     readDeployAssociations(info);
/*      */     
/*  560 */     Integer key = getUniqueHash(info.getDescriptor());
/*      */     
/*  562 */     return new BeanDescriptor(this, this.typeManager, info.getDescriptor(), key.toString());
/*      */   }
/*      */ 
/*      */   
/*  566 */   private void registerBeanDescriptor(BeanDescriptor<?> desc) { this.descMap.put(desc.getBeanType().getName(), desc); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readEmbeddedDeployment() {
/*  574 */     ArrayList<Class<?>> embeddedClasses = this.bootupClasses.getEmbeddables();
/*  575 */     for (int i = 0; i < embeddedClasses.size(); i++) {
/*  576 */       Class<?> cls = (Class)embeddedClasses.get(i);
/*  577 */       if (logger.isLoggable(Level.FINER)) {
/*  578 */         String msg = "load deployinfo for embeddable:" + cls.getName();
/*  579 */         logger.finer(msg);
/*      */       } 
/*  581 */       BeanDescriptor<?> embDesc = createEmbedded(cls);
/*  582 */       registerBeanDescriptor(embDesc);
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
/*      */   private void readEntityDeploymentInitial() {
/*  595 */     ArrayList<Class<?>> entityClasses = this.bootupClasses.getEntities();
/*      */     
/*  597 */     for (Class<?> entityClass : entityClasses) {
/*  598 */       DeployBeanInfo<?> info = createDeployBeanInfo(entityClass);
/*  599 */       this.deplyInfoMap.put(entityClass, info);
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
/*      */   private void readEntityBeanTable() {
/*  611 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  612 */     while (it.hasNext()) {
/*  613 */       DeployBeanInfo<?> info = (DeployBeanInfo)it.next();
/*  614 */       BeanTable beanTable = createBeanTable(info);
/*  615 */       this.beanTableMap.put(beanTable.getBeanType(), beanTable);
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
/*      */   private void readEntityDeploymentAssociations() {
/*  627 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  628 */     while (it.hasNext()) {
/*  629 */       DeployBeanInfo<?> info = (DeployBeanInfo)it.next();
/*  630 */       readDeployAssociations(info);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readInheritedIdGenerators() {
/*  636 */     Iterator<DeployBeanInfo<?>> it = this.deplyInfoMap.values().iterator();
/*  637 */     while (it.hasNext()) {
/*  638 */       DeployBeanInfo<?> info = (DeployBeanInfo)it.next();
/*  639 */       DeployBeanDescriptor<?> descriptor = info.getDescriptor();
/*  640 */       InheritInfo inheritInfo = descriptor.getInheritInfo();
/*  641 */       if (inheritInfo != null && !inheritInfo.isRoot()) {
/*  642 */         DeployBeanInfo<?> rootBeanInfo = (DeployBeanInfo)this.deplyInfoMap.get(inheritInfo.getRoot().getType());
/*  643 */         IdGenerator rootIdGen = rootBeanInfo.getDescriptor().getIdGenerator();
/*  644 */         if (rootIdGen != null) {
/*  645 */           descriptor.setIdGenerator(rootIdGen);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanTable createBeanTable(DeployBeanInfo<?> info) {
/*  656 */     DeployBeanDescriptor<?> deployDescriptor = info.getDescriptor();
/*  657 */     DeployBeanTable beanTable = deployDescriptor.createDeployBeanTable();
/*  658 */     return new BeanTable(beanTable, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readRawSqlQueries() {
/*  666 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*      */       
/*  668 */       DeployBeanDescriptor<?> deployDesc = info.getDescriptor();
/*  669 */       BeanDescriptor<?> desc = getBeanDescriptor(deployDesc.getBeanType());
/*      */       
/*  671 */       for (DRawSqlMeta rawSqlMeta : deployDesc.getRawSqlMeta()) {
/*  672 */         if (rawSqlMeta.getQuery() == null) {
/*      */           continue;
/*      */         }
/*  675 */         DeployNamedQuery nq = (new DRawSqlSelectBuilder(this.namingConvention, desc, rawSqlMeta)).parse();
/*  676 */         desc.addNamedQuery(nq);
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
/*      */   private void readEntityRelationships() {
/*  688 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  689 */       checkMappedBy(info);
/*      */     }
/*      */     
/*  692 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  693 */       secondaryPropsJoins(info);
/*      */     }
/*      */     
/*  696 */     for (DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
/*  697 */       DeployBeanDescriptor<?> deployBeanDescriptor = info.getDescriptor();
/*  698 */       Integer key = getUniqueHash(deployBeanDescriptor);
/*  699 */       registerBeanDescriptor(new BeanDescriptor(this, this.typeManager, info.getDescriptor(), key.toString()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Integer getUniqueHash(DeployBeanDescriptor<?> deployBeanDescriptor) {
/*  705 */     int hashCode = deployBeanDescriptor.getFullName().hashCode();
/*      */     
/*  707 */     for (int i = 0; i < 100000; i++) {
/*  708 */       Integer key = Integer.valueOf(hashCode + i);
/*  709 */       if (!this.descriptorUniqueIds.contains(key)) {
/*  710 */         return key;
/*      */       }
/*      */     } 
/*  713 */     throw new RuntimeException("Failed to generate a unique hash for " + deployBeanDescriptor.getFullName());
/*      */   }
/*      */ 
/*      */   
/*      */   private void secondaryPropsJoins(DeployBeanInfo<?> info) {
/*  718 */     DeployBeanDescriptor<?> descriptor = info.getDescriptor();
/*  719 */     for (DeployBeanProperty prop : descriptor.propertiesBase()) {
/*  720 */       if (prop.isSecondaryTable()) {
/*  721 */         String tableName = prop.getSecondaryTable();
/*      */         
/*  723 */         DeployBeanPropertyAssocOne<?> assocOne = descriptor.findJoinToTable(tableName);
/*  724 */         if (assocOne == null) {
/*  725 */           String msg = "Error with property " + prop.getFullBeanName() + ". Could not find a Relationship to table " + tableName + ". Perhaps you could use a @JoinColumn instead.";
/*      */ 
/*      */           
/*  728 */           throw new RuntimeException(msg);
/*      */         } 
/*  730 */         DeployTableJoin tableJoin = assocOne.getTableJoin();
/*  731 */         prop.setSecondaryTableJoin(tableJoin, assocOne.getName());
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
/*      */   private void checkMappedBy(DeployBeanInfo<?> info) {
/*  746 */     for (DeployBeanPropertyAssocOne<?> oneProp : info.getDescriptor().propertiesAssocOne()) {
/*  747 */       if (!oneProp.isTransient() && 
/*  748 */         oneProp.getMappedBy() != null) {
/*  749 */         checkMappedByOneToOne(info, oneProp);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  754 */     for (DeployBeanPropertyAssocMany<?> manyProp : info.getDescriptor().propertiesAssocMany()) {
/*  755 */       if (!manyProp.isTransient()) {
/*  756 */         if (manyProp.isManyToMany()) {
/*  757 */           checkMappedByManyToMany(info, manyProp); continue;
/*      */         } 
/*  759 */         checkMappedByOneToMany(info, manyProp);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private DeployBeanDescriptor<?> getTargetDescriptor(DeployBeanPropertyAssoc<?> prop) {
/*  767 */     Class<?> targetType = prop.getTargetType();
/*  768 */     DeployBeanInfo<?> info = (DeployBeanInfo)this.deplyInfoMap.get(targetType);
/*  769 */     if (info == null) {
/*  770 */       String msg = "Can not find descriptor [" + targetType + "] for " + prop.getFullBeanName();
/*  771 */       throw new PersistenceException(msg);
/*      */     } 
/*      */     
/*  774 */     return info.getDescriptor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean findMappedBy(DeployBeanPropertyAssocMany<?> prop) {
/*  784 */     Class<?> owningType = prop.getOwningType();
/*      */     
/*  786 */     Set<String> matchSet = new HashSet<String>();
/*      */ 
/*      */     
/*  789 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor(prop);
/*  790 */     List<DeployBeanPropertyAssocOne<?>> ones = targetDesc.propertiesAssocOne();
/*  791 */     for (DeployBeanPropertyAssocOne<?> possibleMappedBy : ones) {
/*  792 */       Class<?> possibleMappedByType = possibleMappedBy.getTargetType();
/*  793 */       if (possibleMappedByType.equals(owningType)) {
/*  794 */         prop.setMappedBy(possibleMappedBy.getName());
/*  795 */         matchSet.add(possibleMappedBy.getName());
/*      */       } 
/*      */     } 
/*      */     
/*  799 */     if (matchSet.size() == 0)
/*      */     {
/*      */       
/*  802 */       return false;
/*      */     }
/*  804 */     if (matchSet.size() == 1)
/*      */     {
/*  806 */       return true;
/*      */     }
/*  808 */     if (matchSet.size() == 2) {
/*      */ 
/*      */       
/*  811 */       String name = prop.getName();
/*      */ 
/*      */       
/*  814 */       String targetType = prop.getTargetType().getName();
/*  815 */       String shortTypeName = targetType.substring(targetType.lastIndexOf(".") + 1);
/*      */ 
/*      */       
/*  818 */       int p = name.indexOf(shortTypeName);
/*  819 */       if (p > 1) {
/*      */ 
/*      */         
/*  822 */         String searchName = name.substring(0, p).toLowerCase();
/*      */ 
/*      */         
/*  825 */         Iterator<String> it = matchSet.iterator();
/*  826 */         while (it.hasNext()) {
/*  827 */           String possibleMappedBy = (String)it.next();
/*  828 */           String possibleLower = possibleMappedBy.toLowerCase();
/*  829 */           if (possibleLower.indexOf(searchName) > -1) {
/*      */             
/*  831 */             prop.setMappedBy(possibleMappedBy);
/*      */             
/*  833 */             String m = "Implicitly found mappedBy for " + targetDesc + "." + prop;
/*  834 */             m = m + " by searching for [" + searchName + "] against " + matchSet;
/*  835 */             logger.fine(m);
/*      */             
/*  837 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  844 */     String msg = "Error on " + prop.getFullBeanName() + " missing mappedBy.";
/*  845 */     msg = msg + " There are [" + matchSet.size() + "] possible properties in " + targetDesc;
/*  846 */     msg = msg + " that this association could be mapped to. Please specify one using ";
/*  847 */     msg = msg + "the mappedBy attribute on @OneToMany.";
/*  848 */     throw new PersistenceException(msg);
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
/*      */   private void makeUnidirectional(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> oneToMany) {
/*  866 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor(oneToMany);
/*      */     
/*  868 */     Class<?> owningType = oneToMany.getOwningType();
/*      */     
/*  870 */     if (!oneToMany.getCascadeInfo().isSave()) {
/*      */ 
/*      */       
/*  873 */       Class<?> targetType = oneToMany.getTargetType();
/*  874 */       String msg = "Error on " + oneToMany.getFullBeanName() + ". @OneToMany MUST have ";
/*  875 */       msg = msg + "Cascade.PERSIST or Cascade.ALL because this is a unidirectional ";
/*  876 */       msg = msg + "relationship. That is, there is no property of type " + owningType + " on " + targetType;
/*      */       
/*  878 */       throw new PersistenceException(msg);
/*      */     } 
/*      */ 
/*      */     
/*  882 */     oneToMany.setUnidirectional(true);
/*      */ 
/*      */ 
/*      */     
/*  886 */     DeployBeanPropertyAssocOne<?> unidirectional = new DeployBeanPropertyAssocOne<?>(targetDesc, owningType);
/*  887 */     unidirectional.setUndirectionalShadow(true);
/*  888 */     unidirectional.setNullable(false);
/*  889 */     unidirectional.setDbRead(true);
/*  890 */     unidirectional.setDbInsertable(true);
/*  891 */     unidirectional.setDbUpdateable(false);
/*      */     
/*  893 */     targetDesc.setUnidirectional(unidirectional);
/*      */ 
/*      */     
/*  896 */     BeanTable beanTable = getBeanTable(owningType);
/*  897 */     unidirectional.setBeanTable(beanTable);
/*  898 */     unidirectional.setName(beanTable.getBaseTable());
/*      */     
/*  900 */     info.setBeanJoinType(unidirectional, true);
/*      */ 
/*      */     
/*  903 */     DeployTableJoin oneToManyJoin = oneToMany.getTableJoin();
/*  904 */     if (!oneToManyJoin.hasJoinColumns()) {
/*  905 */       throw new RuntimeException("No join columns");
/*      */     }
/*      */ 
/*      */     
/*  909 */     DeployTableJoin unidirectionalJoin = unidirectional.getTableJoin();
/*  910 */     unidirectionalJoin.setColumns(oneToManyJoin.columns(), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMappedByOneToOne(DeployBeanInfo<?> info, DeployBeanPropertyAssocOne<?> prop) {
/*  918 */     String mappedBy = prop.getMappedBy();
/*      */ 
/*      */     
/*  921 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor(prop);
/*  922 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*  923 */     if (mappedProp == null) {
/*  924 */       String m = "Error on " + prop.getFullBeanName();
/*  925 */       m = m + "  Can not find mappedBy property [" + targetDesc + "." + mappedBy + "] ";
/*  926 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  929 */     if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
/*  930 */       String m = "Error on " + prop.getFullBeanName();
/*  931 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
/*  932 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  935 */     DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
/*      */     
/*  937 */     if (!mappedAssocOne.isOneToOne()) {
/*  938 */       String m = "Error on " + prop.getFullBeanName();
/*  939 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
/*  940 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  943 */     DeployTableJoin tableJoin = prop.getTableJoin();
/*  944 */     if (!tableJoin.hasJoinColumns()) {
/*      */       
/*  946 */       DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
/*  947 */       otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
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
/*      */   private void checkMappedByOneToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
/*  963 */     if (prop.getMappedBy() == null && 
/*  964 */       !findMappedBy(prop)) {
/*  965 */       makeUnidirectional(info, prop);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  972 */     String mappedBy = prop.getMappedBy();
/*      */ 
/*      */     
/*  975 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor(prop);
/*  976 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*  977 */     if (mappedProp == null) {
/*      */       
/*  979 */       String m = "Error on " + prop.getFullBeanName();
/*  980 */       m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
/*  981 */       m = m + "in [" + targetDesc + "]";
/*  982 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  985 */     if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
/*  986 */       String m = "Error on " + prop.getFullBeanName();
/*  987 */       m = m + ". mappedBy property [" + mappedBy + "]is not a ManyToOne?";
/*  988 */       m = m + "in [" + targetDesc + "]";
/*  989 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/*  992 */     DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne)mappedProp;
/*      */     
/*  994 */     DeployTableJoin tableJoin = prop.getTableJoin();
/*  995 */     if (!tableJoin.hasJoinColumns()) {
/*      */       
/*  997 */       DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
/*  998 */       otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMappedByManyToMany(DeployBeanInfo<?> info, DeployBeanPropertyAssocMany<?> prop) {
/* 1009 */     String mappedBy = prop.getMappedBy();
/* 1010 */     if (mappedBy == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1015 */     DeployBeanDescriptor<?> targetDesc = getTargetDescriptor(prop);
/* 1016 */     DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
/*      */     
/* 1018 */     if (mappedProp == null) {
/* 1019 */       String m = "Error on " + prop.getFullBeanName();
/* 1020 */       m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
/* 1021 */       m = m + "in [" + targetDesc + "]";
/* 1022 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1025 */     if (!(mappedProp instanceof DeployBeanPropertyAssocMany)) {
/* 1026 */       String m = "Error on " + prop.getFullBeanName();
/* 1027 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
/* 1028 */       throw new PersistenceException(m);
/*      */     } 
/*      */     
/* 1031 */     DeployBeanPropertyAssocMany<?> mappedAssocMany = (DeployBeanPropertyAssocMany)mappedProp;
/*      */     
/* 1033 */     if (!mappedAssocMany.isManyToMany()) {
/* 1034 */       String m = "Error on " + prop.getFullBeanName();
/* 1035 */       m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
/* 1036 */       throw new PersistenceException(m);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     DeployTableJoin mappedIntJoin = mappedAssocMany.getIntersectionJoin();
/* 1044 */     DeployTableJoin mappendInverseJoin = mappedAssocMany.getInverseJoin();
/*      */     
/* 1046 */     String intTableName = mappedIntJoin.getTable();
/*      */     
/* 1048 */     DeployTableJoin tableJoin = prop.getTableJoin();
/* 1049 */     mappedIntJoin.copyTo(tableJoin, true, targetDesc.getBaseTable());
/*      */     
/* 1051 */     DeployTableJoin intJoin = new DeployTableJoin();
/* 1052 */     mappendInverseJoin.copyTo(intJoin, false, intTableName);
/* 1053 */     prop.setIntersectionJoin(intJoin);
/*      */     
/* 1055 */     DeployTableJoin inverseJoin = new DeployTableJoin();
/* 1056 */     mappedIntJoin.copyTo(inverseJoin, false, intTableName);
/* 1057 */     prop.setInverseJoin(inverseJoin);
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void setBeanControllerFinderListener(DeployBeanDescriptor<T> descriptor) {
/* 1062 */     Class<T> beanType = descriptor.getBeanType();
/*      */     
/* 1064 */     this.persistControllerManager.addPersistControllers(descriptor);
/* 1065 */     this.persistListenerManager.addPersistListeners(descriptor);
/* 1066 */     this.beanQueryAdapterManager.addQueryAdapter(descriptor);
/*      */     
/* 1068 */     BeanFinder<T> beanFinder = this.beanFinderManager.getBeanFinder(beanType);
/* 1069 */     if (beanFinder != null) {
/* 1070 */       descriptor.setBeanFinder(beanFinder);
/* 1071 */       logger.fine("BeanFinder on[" + descriptor.getFullName() + "] " + beanFinder.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> DeployBeanInfo<T> createDeployBeanInfo(Class<T> beanClass) {
/* 1080 */     DeployBeanDescriptor<T> desc = new DeployBeanDescriptor<T>(beanClass);
/*      */     
/* 1082 */     desc.setUpdateChangesOnly(this.updateChangesOnly);
/*      */ 
/*      */     
/* 1085 */     setBeanControllerFinderListener(desc);
/* 1086 */     this.deplyInherit.process(desc);
/*      */     
/* 1088 */     this.createProperties.createProperties(desc);
/*      */     
/* 1090 */     DeployBeanInfo<T> info = new DeployBeanInfo<T>(this.deployUtil, desc);
/*      */     
/* 1092 */     this.readAnnotations.readInitial(info);
/* 1093 */     return info;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> void readDeployAssociations(DeployBeanInfo<T> info) {
/* 1098 */     DeployBeanDescriptor<T> desc = info.getDescriptor();
/*      */     
/* 1100 */     this.readAnnotations.readAssociations(info, this);
/*      */     
/* 1102 */     readXml(desc);
/*      */     
/* 1104 */     if (!BeanDescriptor.EntityType.ORM.equals(desc.getEntityType()))
/*      */     {
/* 1106 */       desc.setBaseTable(null);
/*      */     }
/*      */ 
/*      */     
/* 1110 */     this.transientProperties.process(desc);
/* 1111 */     setScalarType(desc);
/*      */     
/* 1113 */     if (!desc.isEmbedded()) {
/*      */       
/* 1115 */       setIdGeneration(desc);
/*      */ 
/*      */       
/* 1118 */       setConcurrencyMode(desc);
/*      */     } 
/*      */     
/* 1121 */     autoAddValidators(desc);
/*      */ 
/*      */     
/* 1124 */     createByteCode(desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> IdType setIdGeneration(DeployBeanDescriptor<T> desc) {
/* 1132 */     if (desc.propertiesId().size() == 0) {
/*      */       
/* 1134 */       if (desc.isBaseTableType() && desc.getBeanFinder() == null)
/*      */       {
/*      */ 
/*      */         
/* 1138 */         logger.warning(Message.msg("deploy.nouid", desc.getFullName()));
/*      */       }
/* 1140 */       return null;
/*      */     } 
/*      */     
/* 1143 */     if (IdType.SEQUENCE.equals(desc.getIdType()) && !this.dbIdentity.isSupportsSequence()) {
/*      */       
/* 1145 */       logger.info("Explicit sequence on " + desc.getFullName() + " but not supported by DB Platform - ignored");
/* 1146 */       desc.setIdType(null);
/*      */     } 
/* 1148 */     if (IdType.IDENTITY.equals(desc.getIdType()) && !this.dbIdentity.isSupportsIdentity()) {
/*      */       
/* 1150 */       logger.info("Explicit Identity on " + desc.getFullName() + " but not supported by DB Platform - ignored");
/* 1151 */       desc.setIdType(null);
/*      */     } 
/*      */     
/* 1154 */     if (desc.getIdType() == null)
/*      */     {
/* 1156 */       desc.setIdType(this.dbIdentity.getIdType());
/*      */     }
/*      */     
/* 1159 */     if (IdType.GENERATOR.equals(desc.getIdType())) {
/* 1160 */       String genName = desc.getIdGeneratorName();
/* 1161 */       if ("auto.uuid".equals(genName)) {
/* 1162 */         desc.setIdGenerator(this.uuidIdGenerator);
/* 1163 */         return IdType.GENERATOR;
/*      */       } 
/*      */     } 
/*      */     
/* 1167 */     if (desc.getBaseTable() == null)
/*      */     {
/*      */       
/* 1170 */       return null;
/*      */     }
/*      */     
/* 1173 */     if (IdType.IDENTITY.equals(desc.getIdType())) {
/*      */       
/* 1175 */       String selectLastInsertedId = this.dbIdentity.getSelectLastInsertedId(desc.getBaseTable());
/* 1176 */       desc.setSelectLastInsertedId(selectLastInsertedId);
/* 1177 */       return IdType.IDENTITY;
/*      */     } 
/*      */     
/* 1180 */     String seqName = desc.getIdGeneratorName();
/* 1181 */     if (seqName != null) {
/* 1182 */       logger.fine("explicit sequence " + seqName + " on " + desc.getFullName());
/*      */     } else {
/* 1184 */       String primaryKeyColumn = desc.getSinglePrimaryKeyColumn();
/*      */       
/* 1186 */       seqName = this.namingConvention.getSequenceName(desc.getBaseTable(), primaryKeyColumn);
/*      */     } 
/*      */ 
/*      */     
/* 1190 */     IdGenerator seqIdGen = createSequenceIdGenerator(seqName);
/* 1191 */     desc.setIdGenerator(seqIdGen);
/*      */     
/* 1193 */     return IdType.SEQUENCE;
/*      */   }
/*      */ 
/*      */   
/* 1197 */   private IdGenerator createSequenceIdGenerator(String seqName) { return this.databasePlatform.createSequenceIdGenerator(this.backgroundExecutor, this.dataSource, seqName, this.dbSequenceBatchSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createByteCode(DeployBeanDescriptor<?> deploy) {
/* 1204 */     setEntityBeanClass(deploy);
/*      */ 
/*      */ 
/*      */     
/* 1208 */     setBeanReflect(deploy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void autoAddValidators(DeployBeanDescriptor<?> deployDesc) {
/* 1216 */     for (DeployBeanProperty prop : deployDesc.propertiesBase()) {
/* 1217 */       autoAddValidators(prop);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void autoAddValidators(DeployBeanProperty prop) {
/* 1226 */     if (String.class.equals(prop.getPropertyType()) && prop.getDbLength() > 0)
/*      */     {
/* 1228 */       if (!prop.containsValidatorType(LengthValidatorFactory.LengthValidator.class)) {
/* 1229 */         prop.addValidator(LengthValidatorFactory.create(0, prop.getDbLength()));
/*      */       }
/*      */     }
/* 1232 */     if (!prop.isNullable() && !prop.isId() && !prop.isGenerated())
/*      */     {
/* 1234 */       if (!prop.containsValidatorType(NotNullValidatorFactory.NotNullValidator.class)) {
/* 1235 */         prop.addValidator(NotNullValidatorFactory.NOT_NULL);
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
/*      */   private void setScalarType(DeployBeanDescriptor<?> deployDesc) {
/* 1252 */     Iterator<DeployBeanProperty> it = deployDesc.propertiesAll();
/* 1253 */     while (it.hasNext()) {
/* 1254 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/* 1255 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/* 1258 */       this.deployUtil.setScalarType(prop);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXml(DeployBeanDescriptor<?> deployDesc) {
/* 1265 */     List<Dnode> eXml = this.xmlConfig.findEntityXml(deployDesc.getFullName());
/* 1266 */     readXmlRawSql(deployDesc, eXml);
/*      */ 
/*      */     
/* 1269 */     Dnode entityXml = this.deployOrmXml.findEntityDeploymentXml(deployDesc.getFullName());
/*      */     
/* 1271 */     if (entityXml != null) {
/* 1272 */       readXmlNamedQueries(deployDesc, entityXml);
/* 1273 */       readXmlSql(deployDesc, entityXml);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlSql(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
/* 1283 */     List<Dnode> sqlSelectList = entityXml.findAll("sql-select", entityXml.getLevel() + 1);
/* 1284 */     for (int i = 0; i < sqlSelectList.size(); i++) {
/* 1285 */       Dnode sqlSelect = (Dnode)sqlSelectList.get(i);
/* 1286 */       readSqlSelect(deployDesc, sqlSelect);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String findContent(Dnode node, String nodeName) {
/* 1291 */     Dnode found = node.find(nodeName);
/* 1292 */     if (found != null) {
/* 1293 */       return found.getNodeContent();
/*      */     }
/* 1295 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readSqlSelect(DeployBeanDescriptor<?> deployDesc, Dnode sqlSelect) {
/* 1301 */     String name = sqlSelect.getStringAttr("name", "default");
/* 1302 */     String extend = sqlSelect.getStringAttr("extend", null);
/* 1303 */     String queryDebug = sqlSelect.getStringAttr("debug", null);
/* 1304 */     boolean debug = (queryDebug != null && queryDebug.equalsIgnoreCase("true"));
/*      */ 
/*      */     
/* 1307 */     String query = findContent(sqlSelect, "query");
/* 1308 */     String where = findContent(sqlSelect, "where");
/* 1309 */     String having = findContent(sqlSelect, "having");
/* 1310 */     String columnMapping = findContent(sqlSelect, "columnMapping");
/*      */     
/* 1312 */     DRawSqlMeta m = new DRawSqlMeta(name, extend, query, debug, where, having, columnMapping);
/*      */     
/* 1314 */     deployDesc.add(m);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlRawSql(DeployBeanDescriptor<?> deployDesc, List<Dnode> entityXml) {
/* 1320 */     List<Dnode> rawSqlQueries = this.xmlConfig.find(entityXml, "raw-sql");
/* 1321 */     for (int i = 0; i < rawSqlQueries.size(); i++) {
/* 1322 */       Dnode rawSqlDnode = (Dnode)rawSqlQueries.get(i);
/* 1323 */       String name = rawSqlDnode.getAttribute("name");
/* 1324 */       if (isEmpty(name)) {
/* 1325 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing name attribute");
/*      */       }
/* 1327 */       Dnode queryNode = rawSqlDnode.find("query");
/* 1328 */       if (queryNode == null) {
/* 1329 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing query element");
/*      */       }
/* 1331 */       String sql = queryNode.getNodeContent();
/* 1332 */       if (isEmpty(sql)) {
/* 1333 */         throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " has empty sql in the query element?");
/*      */       }
/*      */       
/* 1336 */       List<Dnode> columnMappings = rawSqlDnode.findAll("columnMapping", 1);
/*      */       
/* 1338 */       RawSqlBuilder rawSqlBuilder = RawSqlBuilder.parse(sql);
/* 1339 */       for (int j = 0; j < columnMappings.size(); j++) {
/* 1340 */         Dnode cm = (Dnode)columnMappings.get(j);
/* 1341 */         String column = cm.getAttribute("column");
/* 1342 */         String property = cm.getAttribute("property");
/* 1343 */         rawSqlBuilder.columnMapping(column, property);
/*      */       } 
/* 1345 */       RawSql rawSql = rawSqlBuilder.create();
/*      */       
/* 1347 */       DeployNamedQuery namedQuery = new DeployNamedQuery(name, rawSql);
/* 1348 */       deployDesc.add(namedQuery);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 1353 */   private boolean isEmpty(String s) { return (s == null || s.trim().length() == 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readXmlNamedQueries(DeployBeanDescriptor<?> deployDesc, Dnode entityXml) {
/* 1362 */     List<Dnode> namedQueries = entityXml.findAll("named-query", 1);
/*      */     
/* 1364 */     for (Dnode namedQueryXml : namedQueries) {
/*      */       
/* 1366 */       String name = namedQueryXml.getAttribute("name");
/* 1367 */       Dnode query = namedQueryXml.find("query");
/* 1368 */       if (query == null) {
/* 1369 */         logger.warning("orm.xml " + deployDesc.getFullName() + " named-query missing query element?");
/*      */         continue;
/*      */       } 
/* 1372 */       String oql = query.getNodeContent();
/*      */       
/* 1374 */       if (name == null || oql == null) {
/* 1375 */         logger.warning("orm.xml " + deployDesc.getFullName() + " named-query has no query content?");
/*      */         continue;
/*      */       } 
/* 1378 */       DeployNamedQuery q = new DeployNamedQuery(name, oql, null);
/* 1379 */       deployDesc.add(q);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1387 */   private BeanReflectFactory createReflectionFactory() { return new EnhanceBeanReflectFactory(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBeanReflect(DeployBeanDescriptor<?> desc) {
/* 1404 */     Class<?> beanType = desc.getBeanType();
/* 1405 */     Class<?> factType = desc.getFactoryType();
/*      */     
/* 1407 */     BeanReflect beanReflect = this.reflectFactory.create(beanType, factType);
/* 1408 */     desc.setBeanReflect(beanReflect);
/*      */     
/*      */     try {
/* 1411 */       Iterator<DeployBeanProperty> it = desc.propertiesAll();
/* 1412 */       while (it.hasNext()) {
/* 1413 */         DeployBeanProperty prop = (DeployBeanProperty)it.next();
/* 1414 */         String propName = prop.getName();
/*      */         
/* 1416 */         if (desc.isAbstract() || beanReflect.isVanillaOnly()) {
/*      */ 
/*      */ 
/*      */           
/* 1420 */           prop.setGetter(ReflectGetter.create(prop));
/* 1421 */           prop.setSetter(ReflectSetter.create(prop));
/*      */           
/*      */           continue;
/*      */         } 
/* 1425 */         BeanReflectGetter getter = beanReflect.getGetter(propName);
/* 1426 */         BeanReflectSetter setter = beanReflect.getSetter(propName);
/* 1427 */         prop.setGetter(getter);
/* 1428 */         prop.setSetter(setter);
/* 1429 */         if (getter == null)
/*      */         {
/* 1431 */           String m = "BeanReflectGetter for " + prop.getFullBeanName() + " was not found?";
/* 1432 */           throw new RuntimeException(m);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1437 */     } catch (IllegalArgumentException e) {
/* 1438 */       Class<?> superClass = desc.getBeanType().getSuperclass();
/* 1439 */       String msg = "Error with [" + desc.getFullName() + "] I believe it is not enhanced but it's superClass [" + superClass + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)";
/*      */ 
/*      */       
/* 1442 */       throw new PersistenceException(msg, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setConcurrencyMode(DeployBeanDescriptor<?> desc) {
/* 1453 */     if (!desc.getConcurrencyMode().equals(ConcurrencyMode.ALL)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1458 */     if (checkForVersionProperties(desc)) {
/* 1459 */       desc.setConcurrencyMode(ConcurrencyMode.VERSION);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkForVersionProperties(DeployBeanDescriptor<?> desc) {
/* 1468 */     boolean hasVersionProperty = false;
/*      */     
/* 1470 */     List<DeployBeanProperty> props = desc.propertiesBase();
/* 1471 */     for (int i = 0; i < props.size(); i++) {
/* 1472 */       if (((DeployBeanProperty)props.get(i)).isVersionColumn()) {
/* 1473 */         hasVersionProperty = true;
/*      */       }
/*      */     } 
/*      */     
/* 1477 */     return hasVersionProperty;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean hasEntityBeanInterface(Class<?> beanClass) {
/* 1482 */     Class[] interfaces = beanClass.getInterfaces();
/* 1483 */     for (int i = 0; i < interfaces.length; i++) {
/* 1484 */       if (interfaces[i].equals(EntityBean.class)) {
/* 1485 */         return true;
/*      */       }
/*      */     } 
/* 1488 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setEntityBeanClass(DeployBeanDescriptor<?> desc) {
/* 1496 */     Class<?> beanClass = desc.getBeanType();
/*      */     
/* 1498 */     if (desc.isAbstract()) {
/* 1499 */       if (hasEntityBeanInterface(beanClass)) {
/* 1500 */         checkEnhanced(desc, beanClass);
/*      */       } else {
/* 1502 */         checkSubclass(desc, beanClass);
/*      */       } 
/*      */       return;
/*      */     } 
/*      */     try {
/* 1507 */       Object testBean = null;
/*      */       try {
/* 1509 */         testBean = beanClass.newInstance();
/* 1510 */       } catch (InstantiationException e) {
/*      */         
/* 1512 */         logger.fine("no default constructor on " + beanClass + " e:" + e);
/* 1513 */       } catch (IllegalAccessException e) {
/*      */         
/* 1515 */         logger.fine("no default constructor on " + beanClass + " e:" + e);
/*      */       } 
/* 1517 */       if (!(testBean instanceof EntityBean)) {
/* 1518 */         checkSubclass(desc, beanClass);
/*      */       } else {
/*      */         
/* 1521 */         String className = beanClass.getName();
/*      */ 
/*      */         
/*      */         try {
/* 1525 */           String marker = ((EntityBean)testBean)._ebean_getMarker();
/* 1526 */           if (!marker.equals(className)) {
/* 1527 */             String msg = "Error with [" + desc.getFullName() + "] It has not been enhanced but it's superClass [" + beanClass.getSuperclass() + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)" + " marker[" + marker + "] className[" + className + "]";
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1532 */             throw new PersistenceException(msg);
/*      */           } 
/* 1534 */         } catch (AbstractMethodError e) {
/* 1535 */           throw new PersistenceException("Old Ebean v1.0 enhancement detected in Ebean v1.1 - please do a clean enhancement.", e);
/*      */         } 
/*      */ 
/*      */         
/* 1539 */         checkEnhanced(desc, beanClass);
/*      */       }
/*      */     
/* 1542 */     } catch (PersistenceException ex) {
/* 1543 */       throw ex;
/*      */     }
/* 1545 */     catch (Exception ex) {
/* 1546 */       throw new PersistenceException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkEnhanced(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
/* 1552 */     checkInheritedClasses(true, beanClass);
/*      */     
/* 1554 */     desc.setFactoryType(beanClass);
/* 1555 */     if (!beanClass.getName().startsWith("com.avaje.ebean.meta")) {
/* 1556 */       this.enhancedClassCount++;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkSubclass(DeployBeanDescriptor<?> desc, Class<?> beanClass) {
/* 1562 */     checkInheritedClasses(false, beanClass);
/* 1563 */     desc.checkReadAndWriteMethods();
/*      */     
/* 1565 */     this.subclassClassCount++;
/*      */     
/* 1567 */     BeanDescriptor.EntityType entityType = desc.getEntityType();
/* 1568 */     if (BeanDescriptor.EntityType.XMLELEMENT.equals(entityType)) {
/* 1569 */       desc.setFactoryType(beanClass);
/*      */     } else {
/* 1571 */       Class<?> subClass = this.subClassManager.resolve(beanClass.getName());
/* 1572 */       desc.setFactoryType(subClass);
/* 1573 */       this.subclassedEntities.add(desc.getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkInheritedClasses(boolean ensureEnhanced, Class<?> beanClass) {
/* 1582 */     Class<?> superclass = beanClass.getSuperclass();
/* 1583 */     if (Object.class.equals(superclass)) {
/*      */       return;
/*      */     }
/*      */     
/* 1587 */     boolean isClassEnhanced = EntityBean.class.isAssignableFrom(superclass);
/*      */     
/* 1589 */     if (ensureEnhanced != isClassEnhanced) {
/*      */       String msg;
/* 1591 */       if (ensureEnhanced) {
/* 1592 */         msg = "Class [" + superclass + "] is not enhanced and [" + beanClass + "] is - (you can not mix!!)";
/*      */       } else {
/* 1594 */         msg = "Class [" + superclass + "] is enhanced and [" + beanClass + "] is not - (you can not mix!!)";
/*      */       } 
/* 1596 */       throw new IllegalStateException(msg);
/*      */     } 
/*      */ 
/*      */     
/* 1600 */     checkInheritedClasses(ensureEnhanced, superclass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class BeanDescComparator
/*      */     extends Object
/*      */     implements Comparator<BeanDescriptor<?>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     private BeanDescComparator() {}
/*      */     
/* 1612 */     public int compare(BeanDescriptor<?> o1, BeanDescriptor<?> o2) { return o1.getName().compareTo(o2.getName()); }
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptorManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */