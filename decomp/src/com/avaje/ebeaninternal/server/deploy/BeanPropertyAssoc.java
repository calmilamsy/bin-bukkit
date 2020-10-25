/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdEmbedded;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdMultiple;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedIdSimple;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class BeanPropertyAssoc<T>
/*     */   extends BeanProperty
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(BeanPropertyAssoc.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   BeanDescriptor<T> targetDescriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   IdBinder targetIdBinder;
/*     */ 
/*     */ 
/*     */   
/*     */   InheritInfo targetInheritInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   String targetIdProperty;
/*     */ 
/*     */ 
/*     */   
/*     */   final BeanCascadeInfo cascadeInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   final TableJoin tableJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   final Class<T> targetType;
/*     */ 
/*     */   
/*     */   final BeanTable beanTable;
/*     */ 
/*     */   
/*     */   final String mappedBy;
/*     */ 
/*     */   
/*     */   final boolean isOuterJoin;
/*     */ 
/*     */   
/*     */   String extraWhere;
/*     */ 
/*     */   
/*     */   boolean saveRecurseSkippable;
/*     */ 
/*     */   
/*     */   boolean deleteRecurseSkippable;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssoc(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssoc<T> deploy) {
/*  95 */     super(owner, descriptor, deploy);
/*  96 */     this.extraWhere = InternString.intern(deploy.getExtraWhere());
/*  97 */     this.isOuterJoin = deploy.isOuterJoin();
/*  98 */     this.beanTable = deploy.getBeanTable();
/*  99 */     this.mappedBy = InternString.intern(deploy.getMappedBy());
/*     */     
/* 101 */     this.tableJoin = new TableJoin(deploy.getTableJoin(), null);
/*     */     
/* 103 */     this.targetType = deploy.getTargetType();
/* 104 */     this.cascadeInfo = deploy.getCascadeInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 114 */     if (!this.isTransient) {
/* 115 */       this.targetDescriptor = this.descriptor.getBeanDescriptor(this.targetType);
/* 116 */       this.targetIdBinder = this.targetDescriptor.getIdBinder();
/* 117 */       this.targetInheritInfo = this.targetDescriptor.getInheritInfo();
/*     */       
/* 119 */       this.saveRecurseSkippable = this.targetDescriptor.isSaveRecurseSkippable();
/* 120 */       this.deleteRecurseSkippable = this.targetDescriptor.isDeleteRecurseSkippable();
/*     */       
/* 122 */       this.cascadeValidate = this.cascadeInfo.isValidate();
/*     */       
/* 124 */       if (!this.targetIdBinder.isComplexId()) {
/* 125 */         this.targetIdProperty = this.targetIdBinder.getIdProperty();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElPropertyValue createElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 136 */     BeanDescriptor<?> embDesc = getTargetDescriptor();
/*     */     
/* 138 */     if (chain == null) {
/* 139 */       chain = new ElPropertyChainBuilder(isEmbedded(), propName);
/*     */     }
/* 141 */     chain.add(this);
/* 142 */     if (containsMany()) {
/* 143 */       chain.setContainsMany(true);
/*     */     }
/* 145 */     return embDesc.buildElGetValue(remainder, chain, propertyDeploy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public boolean addJoin(boolean forceOuterJoin, String prefix, DbSqlContext ctx) { return this.tableJoin.addJoin(forceOuterJoin, prefix, ctx); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public boolean addJoin(boolean forceOuterJoin, String a1, String a2, DbSqlContext ctx) { return this.tableJoin.addJoin(forceOuterJoin, a1, a2, ctx); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void addInnerJoin(String a1, String a2, DbSqlContext ctx) { this.tableJoin.addInnerJoin(a1, a2, ctx); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public boolean isScalar() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getMappedBy() { return this.mappedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public String getTargetIdProperty() { return this.targetIdProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public BeanDescriptor<T> getTargetDescriptor() { return this.targetDescriptor; }
/*     */ 
/*     */   
/*     */   public boolean isSaveRecurseSkippable(Object bean) {
/* 202 */     if (!this.saveRecurseSkippable)
/*     */     {
/*     */       
/* 205 */       return false;
/*     */     }
/* 207 */     if (bean instanceof EntityBean) {
/* 208 */       return !((EntityBean)bean)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/*     */     
/* 211 */     return false;
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
/* 225 */   public boolean isSaveRecurseSkippable() { return this.saveRecurseSkippable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   public boolean isDeleteRecurseSkippable() { return this.deleteRecurseSkippable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasId(Object bean) {
/* 240 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 242 */     BeanProperty[] uids = targetDesc.propertiesId();
/* 243 */     for (int i = 0; i < uids.length; i++) {
/*     */       
/* 245 */       Object value = uids[i].getValue(bean);
/* 246 */       if (value == null) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return true;
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
/* 262 */   public Class<?> getTargetType() { return this.targetType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public String getExtraWhere() { return this.extraWhere; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public boolean isOuterJoin() { return this.isOuterJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUpdateable() {
/* 284 */     if (this.tableJoin.columns().length > 0) {
/* 285 */       return this.tableJoin.columns()[0].isUpdateable();
/*     */     }
/*     */     
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsertable() {
/* 295 */     if (this.tableJoin.columns().length > 0) {
/* 296 */       return this.tableJoin.columns()[0].isInsertable();
/*     */     }
/*     */     
/* 299 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public TableJoin getTableJoin() { return this.tableJoin; }
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
/* 317 */   public BeanTable getBeanTable() { return this.beanTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public BeanCascadeInfo getCascadeInfo() { return this.cascadeInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImportedId createImportedId(BeanPropertyAssoc<?> owner, BeanDescriptor<?> target, TableJoin join) {
/* 333 */     BeanProperty[] props = target.propertiesId();
/* 334 */     BeanProperty[] others = target.propertiesBaseScalar();
/*     */     
/* 336 */     if (this.descriptor.isSqlSelectBased()) {
/* 337 */       String dbColumn = owner.getDbColumn();
/* 338 */       return new ImportedIdSimple(owner, dbColumn, props[0], false);
/*     */     } 
/*     */     
/* 341 */     TableJoinColumn[] cols = join.columns();
/*     */     
/* 343 */     if (props.length == 1) {
/* 344 */       if (!props[0].isEmbedded()) {
/*     */         
/* 346 */         if (cols.length != 1) {
/* 347 */           String msg = "No Imported Id column for [" + props[0] + "] in table [" + join.getTable() + "]";
/* 348 */           logger.log(Level.SEVERE, msg);
/* 349 */           return null;
/*     */         } 
/* 351 */         return createImportedScalar(owner, cols[0], props, others);
/*     */       } 
/*     */ 
/*     */       
/* 355 */       BeanPropertyAssocOne<?> embProp = (BeanPropertyAssocOne)props[0];
/* 356 */       BeanProperty[] embBaseProps = embProp.getTargetDescriptor().propertiesBaseScalar();
/* 357 */       ImportedIdSimple[] scalars = createImportedList(owner, cols, embBaseProps, others);
/*     */       
/* 359 */       return new ImportedIdEmbedded(owner, embProp, scalars);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 364 */     ImportedIdSimple[] scalars = createImportedList(owner, cols, props, others);
/* 365 */     return new ImportedIdMultiple(owner, scalars);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ImportedIdSimple[] createImportedList(BeanPropertyAssoc<?> owner, TableJoinColumn[] cols, BeanProperty[] props, BeanProperty[] others) {
/* 371 */     ArrayList<ImportedIdSimple> list = new ArrayList<ImportedIdSimple>();
/*     */     
/* 373 */     for (int i = 0; i < cols.length; i++) {
/* 374 */       list.add(createImportedScalar(owner, cols[i], props, others));
/*     */     }
/*     */     
/* 377 */     return ImportedIdSimple.sort(list);
/*     */   }
/*     */ 
/*     */   
/*     */   private ImportedIdSimple createImportedScalar(BeanPropertyAssoc<?> owner, TableJoinColumn col, BeanProperty[] props, BeanProperty[] others) {
/* 382 */     String matchColumn = col.getForeignDbColumn();
/* 383 */     String localColumn = col.getLocalDbColumn();
/*     */     
/* 385 */     for (j = 0; j < props.length; j++) {
/* 386 */       if (props[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
/* 387 */         return new ImportedIdSimple(owner, localColumn, props[j], j);
/*     */       }
/*     */     } 
/*     */     
/* 391 */     for (int j = 0; j < others.length; j++) {
/* 392 */       if (others[j].getDbColumn().equalsIgnoreCase(matchColumn)) {
/* 393 */         return new ImportedIdSimple(owner, localColumn, others[j], j + props.length);
/*     */       }
/*     */     } 
/*     */     
/* 397 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the local match for [" + matchColumn + "] " + " Perhaps an error in a @JoinColumn";
/*     */ 
/*     */     
/* 400 */     throw new PersistenceException(msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssoc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */