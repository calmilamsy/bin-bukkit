/*      */ package com.avaje.ebeaninternal.server.deploy.meta;
/*      */ 
/*      */ import com.avaje.ebean.Query;
/*      */ import com.avaje.ebean.config.TableName;
/*      */ import com.avaje.ebean.config.dbplatform.IdGenerator;
/*      */ import com.avaje.ebean.config.dbplatform.IdType;
/*      */ import com.avaje.ebean.config.lucene.IndexDefn;
/*      */ import com.avaje.ebean.event.BeanFinder;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanPersistListener;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*      */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*      */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistController;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistListener;
/*      */ import com.avaje.ebeaninternal.server.deploy.ChainedBeanQueryAdapter;
/*      */ import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
/*      */ import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*      */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*      */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflect;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DeployBeanDescriptor<T>
/*      */   extends Object
/*      */ {
/*      */   static class PropOrder
/*      */     extends Object
/*      */     implements Comparator<DeployBeanProperty>
/*      */   {
/*      */     public int compare(DeployBeanProperty o1, DeployBeanProperty o2) {
/*   69 */       int v2 = o1.getSortOrder();
/*   70 */       int v1 = o2.getSortOrder();
/*   71 */       return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1);
/*      */     } }
/*      */   
/*   74 */   private static final PropOrder PROP_ORDER = new PropOrder(); private static final String I_SCALAOBJECT = "scala.ScalaObject"; private LinkedHashMap<String, DeployBeanProperty> propMap; private final Class<T> beanType;
/*      */   private BeanDescriptor.EntityType entityType;
/*      */   private final Map<String, DeployNamedQuery> namedQueries;
/*      */   private final Map<String, DeployNamedUpdate> namedUpdates;
/*   78 */   private static final Logger logger = Logger.getLogger(DeployBeanDescriptor.class.getName()); private final Map<String, DRawSqlMeta> rawSqlMetas; private DeployBeanPropertyAssocOne<?> unidirectional; private IdType idType; private String idGeneratorName; private IdGenerator idGenerator; private String sequenceName; private String ldapBaseDn;
/*      */   private String[] ldapObjectclasses;
/*   80 */   private static final String META_BEAN_PREFIX = com.avaje.ebean.meta.MetaAutoFetchStatistic.class.getName().substring(0, 20); private String selectLastInsertedId; private String lazyFetchIncludes;
/*      */   private ConcurrencyMode concurrencyMode;
/*      */   private boolean updateChangesOnly;
/*      */   
/*      */   public DeployBeanDescriptor(Class<T> beanType) {
/*   85 */     this.propMap = new LinkedHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     this.namedQueries = new LinkedHashMap();
/*      */     
/*   96 */     this.namedUpdates = new LinkedHashMap();
/*      */     
/*   98 */     this.rawSqlMetas = new LinkedHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     this.concurrencyMode = ConcurrencyMode.ALL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  147 */     this.extraAttrMap = new HashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  167 */     this.persistControllers = new ArrayList();
/*  168 */     this.persistListeners = new ArrayList();
/*  169 */     this.queryAdapters = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  185 */     this.tableJoinList = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  200 */     this.beanType = beanType;
/*      */   }
/*      */   private String[] dependantTables; private List<CompoundUniqueContraint> compoundUniqueConstraints; private HashMap<String, String> extraAttrMap; private String baseTable; private TableName baseTableFull; private BeanReflect beanReflect;
/*      */   private Class<?> factoryType;
/*      */   private List<BeanPersistController> persistControllers;
/*      */   private List<BeanPersistListener<T>> persistListeners;
/*      */   
/*  207 */   public boolean isAbstract() { return Modifier.isAbstract(this.beanType.getModifiers()); }
/*      */   private List<BeanQueryAdapter> queryAdapters; private ReferenceOptions referenceOptions; private BeanFinder<T> beanFinder; private Query.UseIndex useIndex; private IndexDefn<?> indexDefn;
/*      */   private ArrayList<DeployTableJoin> tableJoinList;
/*      */   private InheritInfo inheritInfo;
/*      */   private String name;
/*      */   private boolean processedRawSqlExtend;
/*      */   
/*  214 */   public Query.UseIndex getUseIndex() { return this.useIndex; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   public void setUseIndex(Query.UseIndex useIndex) { this.useIndex = useIndex; }
/*      */ 
/*      */ 
/*      */   
/*  225 */   public IndexDefn<?> getIndexDefn() { return this.indexDefn; }
/*      */ 
/*      */ 
/*      */   
/*  229 */   public void setIndexDefn(IndexDefn<?> indexDefn) { this.indexDefn = indexDefn; }
/*      */ 
/*      */   
/*      */   public boolean isScalaObject() {
/*  233 */     Class[] interfaces = this.beanType.getInterfaces();
/*  234 */     for (int i = 0; i < interfaces.length; i++) {
/*  235 */       String iname = interfaces[i].getName();
/*  236 */       if ("scala.ScalaObject".equals(iname)) {
/*  237 */         return true;
/*      */       }
/*      */     } 
/*  240 */     return false;
/*      */   }
/*      */   
/*      */   public Collection<DRawSqlMeta> getRawSqlMeta() {
/*  244 */     if (!this.processedRawSqlExtend) {
/*  245 */       rawSqlProcessExtend();
/*  246 */       this.processedRawSqlExtend = true;
/*      */     } 
/*  248 */     return this.rawSqlMetas.values();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rawSqlProcessExtend() {
/*  257 */     for (DRawSqlMeta rawSqlMeta : this.rawSqlMetas.values()) {
/*  258 */       String extend = rawSqlMeta.getExtend();
/*  259 */       if (extend != null) {
/*  260 */         DRawSqlMeta parentQuery = (DRawSqlMeta)this.rawSqlMetas.get(extend);
/*  261 */         if (parentQuery == null) {
/*  262 */           throw new RuntimeException("parent query [" + extend + "] not found for sql-select " + rawSqlMeta.getName());
/*      */         }
/*  264 */         rawSqlMeta.extend(parentQuery);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DeployBeanTable createDeployBeanTable() {
/*  272 */     DeployBeanTable beanTable = new DeployBeanTable(getBeanType());
/*  273 */     beanTable.setBaseTable(this.baseTable);
/*  274 */     beanTable.setIdProperties(propertiesId());
/*      */     
/*  276 */     return beanTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkReadAndWriteMethods() {
/*  285 */     if (isMeta()) {
/*  286 */       return true;
/*      */     }
/*  288 */     boolean missingMethods = false;
/*      */     
/*  290 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  291 */     while (it.hasNext()) {
/*  292 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  293 */       if (!prop.isTransient()) {
/*  294 */         String m = "";
/*  295 */         if (prop.getReadMethod() == null) {
/*  296 */           m = m + " missing readMethod ";
/*      */         }
/*  298 */         if (prop.getWriteMethod() == null) {
/*  299 */           m = m + " missing writeMethod ";
/*      */         }
/*  301 */         if (!"".equals(m)) {
/*  302 */           m = m + ". Should it be transient?";
/*  303 */           String msg = "Bean property " + getFullName() + "." + prop.getName() + " has " + m;
/*  304 */           logger.log(Level.SEVERE, msg);
/*  305 */           missingMethods = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  309 */     return !missingMethods;
/*      */   }
/*      */ 
/*      */   
/*  313 */   public void setEntityType(BeanDescriptor.EntityType entityType) { this.entityType = entityType; }
/*      */ 
/*      */ 
/*      */   
/*  317 */   public boolean isEmbedded() { return BeanDescriptor.EntityType.EMBEDDED.equals(this.entityType); }
/*      */ 
/*      */   
/*      */   public boolean isBaseTableType() {
/*  321 */     BeanDescriptor.EntityType et = getEntityType();
/*  322 */     return BeanDescriptor.EntityType.ORM.equals(et);
/*      */   }
/*      */   
/*      */   public BeanDescriptor.EntityType getEntityType() {
/*  326 */     if (this.entityType == null) {
/*  327 */       this.entityType = isMeta() ? BeanDescriptor.EntityType.META : BeanDescriptor.EntityType.ORM;
/*      */     }
/*  329 */     return this.entityType;
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
/*  340 */   private boolean isMeta() { return this.beanType.getName().startsWith(META_BEAN_PREFIX); }
/*      */ 
/*      */   
/*      */   public void add(DRawSqlMeta rawSqlMeta) {
/*  344 */     this.rawSqlMetas.put(rawSqlMeta.getName(), rawSqlMeta);
/*  345 */     if ("default".equals(rawSqlMeta.getName())) {
/*  346 */       setEntityType(BeanDescriptor.EntityType.SQL);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  351 */   public void add(DeployNamedUpdate namedUpdate) { this.namedUpdates.put(namedUpdate.getName(), namedUpdate); }
/*      */ 
/*      */   
/*      */   public void add(DeployNamedQuery namedQuery) {
/*  355 */     this.namedQueries.put(namedQuery.getName(), namedQuery);
/*  356 */     if ("default".equals(namedQuery.getName())) {
/*  357 */       setEntityType(BeanDescriptor.EntityType.SQL);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  362 */   public Map<String, DeployNamedQuery> getNamedQueries() { return this.namedQueries; }
/*      */ 
/*      */ 
/*      */   
/*  366 */   public Map<String, DeployNamedUpdate> getNamedUpdates() { return this.namedUpdates; }
/*      */ 
/*      */ 
/*      */   
/*  370 */   public BeanReflect getBeanReflect() { return this.beanReflect; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  377 */   public Class<T> getBeanType() { return this.beanType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  384 */   public Class<?> getFactoryType() { return this.factoryType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  394 */   public void setFactoryType(Class<?> factoryType) { this.factoryType = factoryType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  402 */   public void setBeanReflect(BeanReflect beanReflect) { this.beanReflect = beanReflect; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  410 */   public InheritInfo getInheritInfo() { return this.inheritInfo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  417 */   public void setInheritInfo(InheritInfo inheritInfo) { this.inheritInfo = inheritInfo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  424 */   public ReferenceOptions getReferenceOptions() { return this.referenceOptions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  431 */   public void setReferenceOptions(ReferenceOptions referenceOptions) { this.referenceOptions = referenceOptions; }
/*      */ 
/*      */ 
/*      */   
/*  435 */   public DeployBeanPropertyAssocOne<?> getUnidirectional() { return this.unidirectional; }
/*      */ 
/*      */ 
/*      */   
/*  439 */   public void setUnidirectional(DeployBeanPropertyAssocOne<?> unidirectional) { this.unidirectional = unidirectional; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  446 */   public ConcurrencyMode getConcurrencyMode() { return this.concurrencyMode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  453 */   public void setConcurrencyMode(ConcurrencyMode concurrencyMode) { this.concurrencyMode = concurrencyMode; }
/*      */ 
/*      */ 
/*      */   
/*  457 */   public String getLdapBaseDn() { return this.ldapBaseDn; }
/*      */ 
/*      */ 
/*      */   
/*  461 */   public void setLdapBaseDn(String ldapBaseDn) { this.ldapBaseDn = ldapBaseDn; }
/*      */ 
/*      */ 
/*      */   
/*  465 */   public String[] getLdapObjectclasses() { return this.ldapObjectclasses; }
/*      */ 
/*      */ 
/*      */   
/*  469 */   public void setLdapObjectclasses(String[] ldapObjectclasses) { this.ldapObjectclasses = ldapObjectclasses; }
/*      */ 
/*      */ 
/*      */   
/*  473 */   public boolean isUpdateChangesOnly() { return this.updateChangesOnly; }
/*      */ 
/*      */ 
/*      */   
/*  477 */   public void setUpdateChangesOnly(boolean updateChangesOnly) { this.updateChangesOnly = updateChangesOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  485 */   public String[] getDependantTables() { return this.dependantTables; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCompoundUniqueConstraint(CompoundUniqueContraint c) {
/*  492 */     if (this.compoundUniqueConstraints == null) {
/*  493 */       this.compoundUniqueConstraints = new ArrayList();
/*      */     }
/*  495 */     this.compoundUniqueConstraints.add(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
/*  502 */     if (this.compoundUniqueConstraints == null) {
/*  503 */       return null;
/*      */     }
/*  505 */     return (CompoundUniqueContraint[])this.compoundUniqueConstraints.toArray(new CompoundUniqueContraint[this.compoundUniqueConstraints.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  514 */   public void setDependantTables(String[] dependantTables) { this.dependantTables = dependantTables; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  521 */   public BeanFinder<T> getBeanFinder() { return this.beanFinder; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  529 */   public void setBeanFinder(BeanFinder<T> beanFinder) { this.beanFinder = beanFinder; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistController getPersistController() {
/*  536 */     if (this.persistControllers.size() == 0)
/*  537 */       return null; 
/*  538 */     if (this.persistControllers.size() == 1) {
/*  539 */       return (BeanPersistController)this.persistControllers.get(0);
/*      */     }
/*  541 */     return new ChainedBeanPersistController(this.persistControllers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanPersistListener<T> getPersistListener() {
/*  549 */     if (this.persistListeners.size() == 0)
/*  550 */       return null; 
/*  551 */     if (this.persistListeners.size() == 1) {
/*  552 */       return (BeanPersistListener)this.persistListeners.get(0);
/*      */     }
/*  554 */     return new ChainedBeanPersistListener(this.persistListeners);
/*      */   }
/*      */ 
/*      */   
/*      */   public BeanQueryAdapter getQueryAdapter() {
/*  559 */     if (this.queryAdapters.size() == 0)
/*  560 */       return null; 
/*  561 */     if (this.queryAdapters.size() == 1) {
/*  562 */       return (BeanQueryAdapter)this.queryAdapters.get(0);
/*      */     }
/*  564 */     return new ChainedBeanQueryAdapter(this.queryAdapters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  572 */   public void addPersistController(BeanPersistController controller) { this.persistControllers.add(controller); }
/*      */ 
/*      */ 
/*      */   
/*  576 */   public void addPersistListener(BeanPersistListener<T> listener) { this.persistListeners.add(listener); }
/*      */ 
/*      */ 
/*      */   
/*  580 */   public void addQueryAdapter(BeanQueryAdapter queryAdapter) { this.queryAdapters.add(queryAdapter); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  591 */   public boolean isUseIdGenerator() { return (this.idType == IdType.GENERATOR); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  599 */   public String getBaseTable() { return this.baseTable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  606 */   public TableName getBaseTableFull() { return this.baseTableFull; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseTable(TableName baseTableFull) {
/*  614 */     this.baseTableFull = baseTableFull;
/*  615 */     this.baseTable = (baseTableFull == null) ? null : baseTableFull.getQualifiedName();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sortProperties() {
/*  620 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*  621 */     list.addAll(this.propMap.values());
/*      */     
/*  623 */     Collections.sort(list, PROP_ORDER);
/*      */     
/*  625 */     this.propMap = new LinkedHashMap(list.size());
/*  626 */     for (int i = 0; i < list.size(); i++) {
/*  627 */       addBeanProperty((DeployBeanProperty)list.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  635 */   public DeployBeanProperty addBeanProperty(DeployBeanProperty prop) { return (DeployBeanProperty)this.propMap.put(prop.getName(), prop); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  642 */   public DeployBeanProperty getBeanProperty(String propName) { return (DeployBeanProperty)this.propMap.get(propName); }
/*      */ 
/*      */ 
/*      */   
/*  646 */   public Map<String, String> getExtraAttributeMap() { return this.extraAttrMap; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  653 */   public String getExtraAttribute(String key) { return (String)this.extraAttrMap.get(key); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  665 */   public void setExtraAttribute(String key, String value) { this.extraAttrMap.put(key, value); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  676 */   public String getFullName() { return this.beanType.getName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  683 */   public String getName() { return this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  690 */   public void setName(String name) { this.name = name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  698 */   public IdType getIdType() { return this.idType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  705 */   public void setIdType(IdType idType) { this.idType = idType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  712 */   public String getSequenceName() { return this.sequenceName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  719 */   public void setSequenceName(String sequenceName) { this.sequenceName = sequenceName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  729 */   public String getSelectLastInsertedId() { return this.selectLastInsertedId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  736 */   public void setSelectLastInsertedId(String selectLastInsertedId) { this.selectLastInsertedId = selectLastInsertedId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  744 */   public String getIdGeneratorName() { return this.idGeneratorName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  752 */   public void setIdGeneratorName(String idGeneratorName) { this.idGeneratorName = idGeneratorName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  759 */   public IdGenerator getIdGenerator() { return this.idGenerator; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdGenerator(IdGenerator idGenerator) {
/*  766 */     this.idGenerator = idGenerator;
/*  767 */     if (idGenerator != null && idGenerator.isDbSequence()) {
/*  768 */       setSequenceName(idGenerator.getName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  776 */   public String getLazyFetchIncludes() { return this.lazyFetchIncludes; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLazyFetchIncludes(String lazyFetchIncludes) {
/*  785 */     if (lazyFetchIncludes != null && lazyFetchIncludes.length() > 0) {
/*  786 */       this.lazyFetchIncludes = lazyFetchIncludes;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  794 */   public String toString() { return getFullName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  801 */   public void addTableJoin(DeployTableJoin join) { this.tableJoinList.add(join); }
/*      */ 
/*      */ 
/*      */   
/*  805 */   public List<DeployTableJoin> getTableJoins() { return this.tableJoinList; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  812 */   public Iterator<DeployBeanProperty> propertiesAll() { return this.propMap.values().iterator(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultSelectClause() {
/*  820 */     StringBuilder sb = new StringBuilder();
/*      */     
/*  822 */     boolean hasLazyFetch = false;
/*      */     
/*  824 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  825 */     while (it.hasNext()) {
/*  826 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  827 */       if (prop.isTransient())
/*      */         continue; 
/*  829 */       if (prop instanceof DeployBeanPropertyAssocMany) {
/*      */         continue;
/*      */       }
/*  832 */       if (prop.isFetchEager()) {
/*  833 */         sb.append(prop.getName()).append(","); continue;
/*      */       } 
/*  835 */       hasLazyFetch = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  840 */     if (!hasLazyFetch) {
/*  841 */       return null;
/*      */     }
/*  843 */     String selectClause = sb.toString();
/*  844 */     return selectClause.substring(0, selectClause.length() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getDefaultSelectDbArray(Set<String> defaultSelect) {
/*  852 */     ArrayList<String> list = new ArrayList<String>();
/*  853 */     for (DeployBeanProperty p : this.propMap.values()) {
/*  854 */       if (defaultSelect != null) {
/*  855 */         if (defaultSelect.contains(p.getName()))
/*      */         {
/*  857 */           list.add(p.getDbColumn()); }  continue;
/*      */       } 
/*  859 */       if (!p.isTransient() && p.isDbRead())
/*      */       {
/*  861 */         list.add(p.getDbColumn());
/*      */       }
/*      */     } 
/*  864 */     return (String[])list.toArray(new String[list.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> parseDefaultSelectClause(String rawList) {
/*  872 */     if (rawList == null) {
/*  873 */       return null;
/*      */     }
/*      */     
/*  876 */     String[] res = rawList.split(",");
/*      */     
/*  878 */     LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
/*      */     
/*  880 */     String temp = null;
/*  881 */     for (int i = 0; i < res.length; i++) {
/*  882 */       temp = res[i].trim();
/*  883 */       if (temp.length() > 0) {
/*  884 */         set.add(temp);
/*      */       }
/*      */     } 
/*  887 */     return Collections.unmodifiableSet(set);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSinglePrimaryKeyColumn() {
/*  895 */     List<DeployBeanProperty> ids = propertiesId();
/*  896 */     if (ids.size() == 1) {
/*  897 */       DeployBeanProperty p = (DeployBeanProperty)ids.get(0);
/*  898 */       if (p instanceof DeployBeanPropertyAssoc)
/*      */       {
/*  900 */         return null;
/*      */       }
/*  902 */       return p.getDbColumn();
/*      */     } 
/*      */     
/*  905 */     return null;
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
/*      */   public List<DeployBeanProperty> propertiesId() {
/*  917 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>(2);
/*      */     
/*  919 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  920 */     while (it.hasNext()) {
/*  921 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  922 */       if (prop.isId()) {
/*  923 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */     
/*  927 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public DeployBeanPropertyAssocOne<?> findJoinToTable(String tableName) {
/*  932 */     List<DeployBeanPropertyAssocOne<?>> assocOne = propertiesAssocOne();
/*  933 */     for (DeployBeanPropertyAssocOne<?> prop : assocOne) {
/*  934 */       DeployTableJoin tableJoin = prop.getTableJoin();
/*  935 */       if (tableJoin != null && tableJoin.getTable().equalsIgnoreCase(tableName)) {
/*  936 */         return prop;
/*      */       }
/*      */     } 
/*  939 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanPropertyAssocOne<?>> propertiesAssocOne() {
/*  948 */     ArrayList<DeployBeanPropertyAssocOne<?>> list = new ArrayList<DeployBeanPropertyAssocOne<?>>();
/*      */     
/*  950 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  951 */     while (it.hasNext()) {
/*  952 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  953 */       if (prop instanceof DeployBeanPropertyAssocOne && 
/*  954 */         !prop.isEmbedded()) {
/*  955 */         list.add((DeployBeanPropertyAssocOne)prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  960 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanPropertyAssocMany<?>> propertiesAssocMany() {
/*  969 */     ArrayList<DeployBeanPropertyAssocMany<?>> list = new ArrayList<DeployBeanPropertyAssocMany<?>>();
/*      */     
/*  971 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  972 */     while (it.hasNext()) {
/*  973 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*  974 */       if (prop instanceof DeployBeanPropertyAssocMany) {
/*  975 */         list.add((DeployBeanPropertyAssocMany)prop);
/*      */       }
/*      */     } 
/*      */     
/*  979 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanProperty> propertiesVersion() {
/*  989 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*      */     
/*  991 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/*  992 */     while (it.hasNext()) {
/*  993 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*      */       
/*  995 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/*  998 */       if (!prop.isId() && prop.isVersionColumn()) {
/*  999 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1004 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<DeployBeanProperty> propertiesBase() {
/* 1012 */     ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
/*      */     
/* 1014 */     Iterator<DeployBeanProperty> it = this.propMap.values().iterator();
/* 1015 */     while (it.hasNext()) {
/* 1016 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/*      */       
/* 1018 */       if (prop instanceof DeployBeanPropertyAssoc) {
/*      */         continue;
/*      */       }
/* 1021 */       if (!prop.isId()) {
/* 1022 */         list.add(prop);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1027 */     return list;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */