/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.ReferenceOptions;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class BeanPropertyAssocOne<T>
/*     */   extends BeanPropertyAssoc<T>
/*     */ {
/*     */   private final boolean oneToOne;
/*     */   private final boolean oneToOneExported;
/*     */   private final boolean embeddedVersion;
/*     */   private final boolean importedPrimaryKey;
/*     */   private final LocalHelp localHelp;
/*     */   private final BeanProperty[] embeddedProps;
/*     */   private final HashMap<String, BeanProperty> embeddedPropsMap;
/*     */   private ImportedId importedId;
/*     */   private ExportedProperty[] exportedProperties;
/*     */   private String deleteByParentIdSql;
/*     */   private String deleteByParentIdInSql;
/*     */   
/*  81 */   public BeanPropertyAssocOne(BeanDescriptorMap owner, DeployBeanPropertyAssocOne<T> deploy) { this(owner, null, deploy); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanPropertyAssocOne(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocOne<T> deploy) {
/*  90 */     super(owner, descriptor, deploy);
/*     */     
/*  92 */     this.importedPrimaryKey = deploy.isImportedPrimaryKey();
/*  93 */     this.oneToOne = deploy.isOneToOne();
/*  94 */     this.oneToOneExported = deploy.isOneToOneExported();
/*     */     
/*  96 */     if (this.embedded) {
/*     */       
/*  98 */       BeanEmbeddedMeta overrideMeta = BeanEmbeddedMetaFactory.create(owner, deploy, descriptor);
/*  99 */       this.embeddedProps = overrideMeta.getProperties();
/* 100 */       if (this.id) {
/* 101 */         this.embeddedVersion = false;
/*     */       } else {
/* 103 */         this.embeddedVersion = overrideMeta.isEmbeddedVersion();
/*     */       } 
/* 105 */       this.embeddedPropsMap = new HashMap();
/* 106 */       for (int i = 0; i < this.embeddedProps.length; i++) {
/* 107 */         this.embeddedPropsMap.put(this.embeddedProps[i].getName(), this.embeddedProps[i]);
/*     */       }
/*     */     } else {
/*     */       
/* 111 */       this.embeddedProps = null;
/* 112 */       this.embeddedPropsMap = null;
/* 113 */       this.embeddedVersion = false;
/*     */     } 
/* 115 */     this.localHelp = createHelp(this.embedded, this.oneToOneExported);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialise() {
/* 120 */     super.initialise();
/* 121 */     if (!this.isTransient && 
/* 122 */       !this.embedded)
/*     */     {
/* 124 */       if (!this.oneToOneExported) {
/* 125 */         this.importedId = createImportedId(this, this.targetDescriptor, this.tableJoin);
/*     */       } else {
/* 127 */         this.exportedProperties = createExported();
/*     */         
/* 129 */         String delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
/*     */         
/* 131 */         this.deleteByParentIdSql = delStmt + deriveWhereParentIdSql(false);
/* 132 */         this.deleteByParentIdInSql = delStmt + deriveWhereParentIdSql(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
/* 142 */     if (this.embedded) {
/* 143 */       BeanProperty embProp = (BeanProperty)this.embeddedPropsMap.get(remainder);
/* 144 */       if (embProp == null) {
/* 145 */         String msg = "Embedded Property " + remainder + " not found in " + getFullBeanName();
/* 146 */         throw new PersistenceException(msg);
/*     */       } 
/* 148 */       if (chain == null) {
/* 149 */         chain = new ElPropertyChainBuilder(true, propName);
/*     */       }
/* 151 */       chain.add(this);
/* 152 */       return chain.add(embProp).build();
/*     */     } 
/*     */     
/* 155 */     return createElPropertyValue(propName, remainder, chain, propertyDeploy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getElPlaceholder(boolean encrypted) { return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) { this.localHelp.copyProperty(sourceBean, destBean, ctx, maxDepth); }
/*     */ 
/*     */   
/*     */   public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
/* 170 */     if (parentId != null) {
/* 171 */       return deleteByParentId(parentId);
/*     */     }
/* 173 */     return deleteByParentIdList(parentIdist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
/* 179 */     StringBuilder sb = new StringBuilder(100);
/* 180 */     sb.append(this.deleteByParentIdInSql);
/*     */     
/* 182 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/* 183 */     sb.append(inClause);
/*     */     
/* 185 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
/* 186 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 187 */       this.targetIdBinder.bindId(delete, parentIdist.get(i));
/*     */     }
/*     */     
/* 190 */     return delete;
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentId(Object parentId) {
/* 195 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(this.deleteByParentIdSql);
/* 196 */     if (this.exportedProperties.length == 1) {
/* 197 */       delete.addParameter(parentId);
/*     */     } else {
/* 199 */       this.targetDescriptor.getIdBinder().bindId(delete, parentId);
/*     */     } 
/* 201 */     return delete;
/*     */   }
/*     */   
/*     */   public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t) {
/* 205 */     if (parentId != null) {
/* 206 */       return findIdsByParentId(parentId, t);
/*     */     }
/* 208 */     return findIdsByParentIdList(parentIdist, t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentId(Object parentId, Transaction t) {
/* 214 */     String rawWhere = deriveWhereParentIdSql(false);
/*     */     
/* 216 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 217 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(rawWhere).query();
/*     */ 
/*     */     
/* 220 */     bindWhereParendId(q, parentId);
/* 221 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t) {
/* 226 */     String rawWhere = deriveWhereParentIdSql(true);
/* 227 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/*     */     
/* 229 */     String expr = rawWhere + inClause;
/*     */     
/* 231 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 232 */     Query<?> q = (Query)spiEbeanServer.find(getPropertyType()).where().raw(expr);
/*     */ 
/*     */     
/* 235 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 236 */       bindWhereParendId(q, parentIdist.get(i));
/*     */     }
/*     */     
/* 239 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindWhereParendId(Query<?> q, Object parentId) {
/* 244 */     if (this.exportedProperties.length == 1) {
/* 245 */       q.setParameter(1, parentId);
/*     */     } else {
/*     */       
/* 248 */       int pos = 1;
/* 249 */       for (int i = 0; i < this.exportedProperties.length; i++) {
/* 250 */         Object embVal = this.exportedProperties[i].getValue(parentId);
/* 251 */         q.setParameter(pos++, embVal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addFkey() {
/* 257 */     if (this.importedId != null) {
/* 258 */       this.importedId.addFkeys(this.name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValueLoaded(Object value) {
/* 264 */     if (value instanceof EntityBean) {
/* 265 */       return ((EntityBean)value)._ebean_getIntercept().isLoaded();
/*     */     }
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue validateCascade(Object value) {
/* 273 */     BeanDescriptor<?> target = getTargetDescriptor();
/* 274 */     return target.validate(true, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasChangedEmbedded(Object bean, Object oldValues) {
/* 279 */     Object embValue = getValue(oldValues);
/* 280 */     if (embValue instanceof EntityBean)
/*     */     {
/* 282 */       return ((EntityBean)embValue)._ebean_getIntercept().isNewOrDirty();
/*     */     }
/* 284 */     if (embValue == null) {
/* 285 */       return (getValue(bean) != null);
/*     */     }
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 293 */     if (this.embedded) {
/* 294 */       return hasChangedEmbedded(bean, oldValues);
/*     */     }
/* 296 */     Object value = getValue(bean);
/* 297 */     Object oldVal = getValue(oldValues);
/* 298 */     if (this.oneToOneExported)
/*     */     {
/* 300 */       return false;
/*     */     }
/* 302 */     if (value == null)
/* 303 */       return (oldVal != null); 
/* 304 */     if (oldValues == null) {
/* 305 */       return true;
/*     */     }
/*     */     
/* 308 */     return this.importedId.hasChanged(value, oldVal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public BeanProperty[] getProperties() { return this.embeddedProps; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/* 322 */     prefix = SplitName.add(prefix, this.name);
/*     */     
/* 324 */     if (!this.embedded) {
/* 325 */       this.targetIdBinder.buildSelectExpressionChain(prefix, selectChain);
/*     */     } else {
/*     */       
/* 328 */       for (int i = 0; i < this.embeddedProps.length; i++) {
/* 329 */         this.embeddedProps[i].buildSelectExpressionChain(prefix, selectChain);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 339 */   public boolean isOneToOne() { return this.oneToOne; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public boolean isOneToOneExported() { return this.oneToOneExported; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public boolean isEmbeddedVersion() { return this.embeddedVersion; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   public boolean isImportedPrimaryKey() { return this.importedPrimaryKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public Class<?> getTargetType() { return getPropertyType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public Object[] getAssocOneIdValues(Object bean) { return this.targetDescriptor.getIdBinder().getIdValues(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public String getAssocOneIdExpr(String prefix, String operator) { return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 391 */   public String getAssocIdInValueExpr(int size) { return this.targetDescriptor.getIdBinder().getIdInValueExpr(size); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 399 */   public String getAssocIdInExpr(String prefix) { return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   public boolean isAssocId() { return !this.embedded; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 409 */   public boolean isAssocProperty() { return !this.embedded; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 418 */   public Object createEmbeddedId() { return getTargetDescriptor().createVanillaBean(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public Object createEmptyReference() { return this.targetDescriptor.createEntityBean(); }
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 429 */     Object value = getValueIntercept(bean);
/* 430 */     if (value != null) {
/* 431 */       ((EntityBean)value)._ebean_getIntercept().setReference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 437 */     Object value = getValueIntercept(bean);
/* 438 */     if (value == null) {
/* 439 */       value = this.targetDescriptor.createEntityBean();
/* 440 */       setValueIntercept(bean, value);
/*     */     } 
/* 442 */     return value;
/*     */   }
/*     */ 
/*     */   
/* 446 */   public ImportedId getImportedId() { return this.importedId; }
/*     */ 
/*     */ 
/*     */   
/*     */   private String deriveWhereParentIdSql(boolean inClause) {
/* 451 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 453 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 454 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 455 */       if (i > 0) {
/* 456 */         String s = inClause ? "," : " and ";
/* 457 */         sb.append(s);
/*     */       } 
/* 459 */       sb.append(fkColumn);
/* 460 */       if (!inClause) {
/* 461 */         sb.append("=? ");
/*     */       }
/*     */     } 
/* 464 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty[] createExported() {
/* 472 */     BeanProperty[] uids = this.descriptor.propertiesId();
/*     */     
/* 474 */     ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
/*     */     
/* 476 */     if (uids.length == 1 && uids[0].isEmbedded()) {
/*     */       
/* 478 */       BeanPropertyAssocOne<?> one = (BeanPropertyAssocOne)uids[0];
/* 479 */       BeanDescriptor<?> targetDesc = one.getTargetDescriptor();
/* 480 */       BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
/*     */       try {
/* 482 */         for (int i = 0; i < emIds.length; i++) {
/* 483 */           ExportedProperty expProp = findMatch(true, emIds[i]);
/* 484 */           list.add(expProp);
/*     */         } 
/* 486 */       } catch (PersistenceException e) {
/*     */         
/* 488 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 492 */       for (int i = 0; i < uids.length; i++) {
/* 493 */         ExportedProperty expProp = findMatch(false, uids[i]);
/* 494 */         list.add(expProp);
/*     */       } 
/*     */     } 
/*     */     
/* 498 */     return (ExportedProperty[])list.toArray(new ExportedProperty[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty findMatch(boolean embeddedProp, BeanProperty prop) {
/* 506 */     String matchColumn = prop.getDbColumn();
/*     */     
/* 508 */     String searchTable = this.tableJoin.getTable();
/* 509 */     TableJoinColumn[] columns = this.tableJoin.columns();
/*     */     
/* 511 */     for (int i = 0; i < columns.length; i++) {
/* 512 */       String matchTo = columns[i].getLocalDbColumn();
/*     */       
/* 514 */       if (matchColumn.equalsIgnoreCase(matchTo)) {
/* 515 */         String foreignCol = columns[i].getForeignDbColumn();
/* 516 */         return new ExportedProperty(embeddedProp, foreignCol, prop);
/*     */       } 
/*     */     } 
/*     */     
/* 520 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
/*     */ 
/*     */     
/* 523 */     throw new PersistenceException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 529 */     if (!this.isTransient) {
/* 530 */       this.localHelp.appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 536 */     if (!this.isTransient) {
/* 537 */       this.localHelp.appendFrom(ctx, forceOuterJoin);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/* 543 */     boolean assignable = (type == null || this.owningType.isAssignableFrom(type));
/* 544 */     return this.localHelp.readSet(ctx, bean, assignable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 554 */   public Object read(DbReadContext ctx) throws SQLException { return this.localHelp.read(ctx); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 559 */   public void loadIgnore(DbReadContext ctx) { this.localHelp.loadIgnore(ctx); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/* 564 */     Object dbVal = sqlBeanLoad.load(this);
/* 565 */     if (this.embedded && sqlBeanLoad.isLazyLoad() && 
/* 566 */       dbVal instanceof EntityBean) {
/* 567 */       ((EntityBean)dbVal)._ebean_getIntercept().setLoaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private LocalHelp createHelp(boolean embedded, boolean oneToOneExported) {
/* 573 */     if (embedded)
/* 574 */       return new Embedded(null); 
/* 575 */     if (oneToOneExported) {
/* 576 */       return new ReferenceExported(null);
/*     */     }
/* 578 */     return new Reference(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private abstract class LocalHelp
/*     */   {
/*     */     private LocalHelp() {}
/*     */ 
/*     */     
/*     */     abstract void copyProperty(Object param1Object1, Object param1Object2, CopyContext param1CopyContext, int param1Int);
/*     */ 
/*     */     
/*     */     abstract void loadIgnore(DbReadContext param1DbReadContext);
/*     */     
/*     */     abstract Object read(DbReadContext param1DbReadContext) throws SQLException;
/*     */     
/*     */     abstract Object readSet(DbReadContext param1DbReadContext, Object param1Object, boolean param1Boolean) throws SQLException;
/*     */     
/*     */     abstract void appendSelect(DbSqlContext param1DbSqlContext, boolean param1Boolean);
/*     */     
/*     */     abstract void appendFrom(DbSqlContext param1DbSqlContext, boolean param1Boolean);
/*     */     
/*     */     void copy(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
/* 601 */       Object value = BeanPropertyAssocOne.this.getValue(sourceBean);
/* 602 */       if (value != null) {
/* 603 */         Class<?> valueClass = value.getClass();
/* 604 */         BeanDescriptor<?> refDesc = BeanPropertyAssocOne.this.descriptor.getBeanDescriptor(valueClass);
/* 605 */         Object refId = refDesc.getId(value);
/* 606 */         Object destRef = ctx.get(valueClass, refId);
/* 607 */         if (destRef == null)
/*     */         {
/* 609 */           if (maxDepth > 1) {
/*     */             
/* 611 */             destRef = refDesc.createCopy(value, ctx, maxDepth - 1);
/*     */           } else {
/*     */             
/* 614 */             destRef = refDesc.createReference(ctx.isVanillaMode(), refId, destBean, null);
/*     */           }  } 
/* 616 */         BeanPropertyAssocOne.this.setValue(destBean, destRef);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private final class Embedded extends LocalHelp {
/* 621 */     private Embedded() { super(BeanPropertyAssocOne.this, null); }
/*     */     
/*     */     void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
/* 624 */       Object srcEmb = BeanPropertyAssocOne.this.getValue(sourceBean);
/* 625 */       if (srcEmb != null) {
/* 626 */         Object dstEmb = BeanPropertyAssocOne.this.targetDescriptor.createBean(ctx.isVanillaMode());
/* 627 */         for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 628 */           BeanPropertyAssocOne.this.embeddedProps[i].copyProperty(srcEmb, dstEmb, ctx, maxDepth);
/*     */         }
/* 630 */         BeanPropertyAssocOne.this.setValue(destBean, dstEmb);
/*     */       } 
/*     */     }
/*     */     
/*     */     void loadIgnore(DbReadContext ctx) {
/* 635 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 636 */         BeanPropertyAssocOne.this.embeddedProps[i].loadIgnore(ctx);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 642 */       Object dbVal = read(ctx);
/* 643 */       if (bean != null && assignable) {
/*     */         
/* 645 */         BeanPropertyAssocOne.this.setValue(bean, dbVal);
/* 646 */         ctx.propagateState(dbVal);
/* 647 */         return dbVal;
/*     */       } 
/*     */       
/* 650 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/* 656 */       EntityBean embeddedBean = BeanPropertyAssocOne.this.targetDescriptor.createEntityBean();
/*     */       
/* 658 */       boolean notNull = false;
/* 659 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 660 */         Object value = BeanPropertyAssocOne.this.embeddedProps[i].readSet(ctx, embeddedBean, null);
/* 661 */         if (value != null) {
/* 662 */           notNull = true;
/*     */         }
/*     */       } 
/* 665 */       if (notNull) {
/* 666 */         ctx.propagateState(embeddedBean);
/* 667 */         return embeddedBean;
/*     */       } 
/* 669 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {}
/*     */ 
/*     */ 
/*     */     
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 679 */       for (int i = 0; i < BeanPropertyAssocOne.this.embeddedProps.length; i++) {
/* 680 */         BeanPropertyAssocOne.this.embeddedProps[i].appendSelect(ctx, subQuery);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Reference
/*     */     extends LocalHelp
/*     */   {
/*     */     private final BeanPropertyAssocOne<?> beanProp;
/*     */     
/*     */     Reference(BeanPropertyAssocOne<?> beanProp) {
/* 692 */       super(BeanPropertyAssocOne.this, null);
/* 693 */       this.beanProp = beanProp;
/*     */     }
/*     */ 
/*     */     
/* 697 */     void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) { copy(sourceBean, destBean, ctx, maxDepth); }
/*     */ 
/*     */     
/*     */     void loadIgnore(DbReadContext ctx) {
/* 701 */       BeanPropertyAssocOne.this.targetIdBinder.loadIgnore(ctx);
/* 702 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/* 703 */         ctx.getDataReader().incrementPos(1);
/*     */       }
/*     */     }
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 708 */       Object val = read(ctx);
/* 709 */       if (bean != null && assignable) {
/* 710 */         BeanPropertyAssocOne.this.setValue(bean, val);
/* 711 */         ctx.propagateState(val);
/*     */       } 
/* 713 */       return val;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/* 722 */       BeanDescriptor<?> rowDescriptor = null;
/* 723 */       Class<?> rowType = BeanPropertyAssocOne.this.targetType;
/* 724 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 726 */         InheritInfo rowInheritInfo = BeanPropertyAssocOne.this.targetInheritInfo.readType(ctx);
/* 727 */         if (rowInheritInfo != null) {
/* 728 */           rowType = rowInheritInfo.getType();
/* 729 */           rowDescriptor = rowInheritInfo.getBeanDescriptor();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 734 */       Object id = BeanPropertyAssocOne.this.targetIdBinder.read(ctx);
/* 735 */       if (id == null) {
/* 736 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 740 */       Object existing = ctx.getPersistenceContext().get(rowType, id);
/*     */       
/* 742 */       if (existing != null) {
/* 743 */         return existing;
/*     */       }
/*     */ 
/*     */       
/* 747 */       Object parent = null;
/* 748 */       Object ref = null;
/*     */       
/* 750 */       boolean vanillaMode = ctx.isVanillaMode();
/* 751 */       int parentState = ctx.getParentState();
/*     */       
/* 753 */       ReferenceOptions options = ctx.getReferenceOptionsFor(this.beanProp);
/* 754 */       if (options != null && options.isUseCache()) {
/* 755 */         ref = BeanPropertyAssocOne.this.targetDescriptor.cacheGet(id);
/* 756 */         if (ref != null) {
/* 757 */           if (vanillaMode) {
/* 758 */             ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(true), 5);
/*     */           }
/* 760 */           else if (parentState == 1) {
/* 761 */             ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
/*     */           }
/* 763 */           else if (parentState == 0 && !options.isReadOnly()) {
/* 764 */             ref = BeanPropertyAssocOne.this.targetDescriptor.createCopy(ref, new CopyContext(false), 5);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 769 */       boolean createReference = false;
/* 770 */       if (ref == null) {
/*     */         
/* 772 */         createReference = true;
/* 773 */         if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */           
/* 775 */           ref = rowDescriptor.createReference(vanillaMode, id, parent, options);
/*     */         } else {
/* 777 */           ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, options);
/*     */         } 
/*     */       } 
/*     */       
/* 781 */       Object existingBean = ctx.getPersistenceContext().putIfAbsent(id, ref);
/* 782 */       if (existingBean != null) {
/*     */ 
/*     */ 
/*     */         
/* 786 */         ref = existingBean;
/* 787 */         createReference = false;
/*     */       } 
/*     */       
/* 790 */       if (!vanillaMode) {
/* 791 */         EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
/*     */         
/* 793 */         if (createReference) {
/* 794 */           if (parentState != 0) {
/* 795 */             ebi.setState(parentState);
/*     */           }
/* 797 */           ctx.register(BeanPropertyAssocOne.this.name, ebi);
/*     */         } 
/*     */       } 
/*     */       
/* 801 */       return ref;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 807 */       if (BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 809 */         String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.name);
/* 810 */         BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 820 */       if (!subQuery && BeanPropertyAssocOne.this.targetInheritInfo != null) {
/*     */         
/* 822 */         String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 823 */         String tableAlias = ctx.getTableAlias(relativePrefix);
/* 824 */         ctx.appendColumn(tableAlias, BeanPropertyAssocOne.this.targetInheritInfo.getDiscriminatorColumn());
/*     */       } 
/* 826 */       BeanPropertyAssocOne.this.importedId.sqlAppend(ctx);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ReferenceExported
/*     */     extends LocalHelp
/*     */   {
/* 833 */     private ReferenceExported() { super(BeanPropertyAssocOne.this, null); }
/*     */ 
/*     */     
/* 836 */     void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) { copy(sourceBean, destBean, ctx, maxDepth); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 841 */     void loadIgnore(DbReadContext ctx) { BeanPropertyAssocOne.this.targetDescriptor.getIdBinder().loadIgnore(ctx); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object readSet(DbReadContext ctx, Object bean, boolean assignable) throws SQLException {
/* 850 */       Object dbVal = read(ctx);
/* 851 */       if (bean != null && assignable) {
/* 852 */         BeanPropertyAssocOne.this.setValue(bean, dbVal);
/* 853 */         ctx.propagateState(dbVal);
/*     */       } 
/* 855 */       return dbVal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object read(DbReadContext ctx) throws SQLException {
/* 862 */       IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
/* 863 */       Object id = idBinder.read(ctx);
/* 864 */       if (id == null) {
/* 865 */         return null;
/*     */       }
/*     */       
/* 868 */       PersistenceContext persistCtx = ctx.getPersistenceContext();
/* 869 */       Object existing = persistCtx.get(BeanPropertyAssocOne.this.targetType, id);
/*     */       
/* 871 */       if (existing != null) {
/* 872 */         return existing;
/*     */       }
/* 874 */       boolean vanillaMode = ctx.isVanillaMode();
/* 875 */       Object parent = null;
/* 876 */       Object ref = BeanPropertyAssocOne.this.targetDescriptor.createReference(vanillaMode, id, parent, null);
/*     */       
/* 878 */       if (!vanillaMode) {
/* 879 */         EntityBeanIntercept ebi = ((EntityBean)ref)._ebean_getIntercept();
/* 880 */         if (ctx.getParentState() != 0) {
/* 881 */           ebi.setState(ctx.getParentState());
/*     */         }
/* 883 */         persistCtx.put(id, ref);
/* 884 */         ctx.register(BeanPropertyAssocOne.this.name, ebi);
/*     */       } 
/* 886 */       return ref;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 898 */       String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 899 */       ctx.pushTableAlias(relativePrefix);
/*     */       
/* 901 */       IdBinder idBinder = BeanPropertyAssocOne.this.targetDescriptor.getIdBinder();
/* 902 */       idBinder.appendSelect(ctx, subQuery);
/*     */       
/* 904 */       ctx.popTableAlias();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/* 910 */       String relativePrefix = ctx.getRelativePrefix(BeanPropertyAssocOne.this.getName());
/* 911 */       BeanPropertyAssocOne.this.tableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 918 */     Object value = getValueIntercept(bean);
/* 919 */     if (value == null) {
/* 920 */       ctx.beginAssocOneIsNull(this.name);
/*     */     
/*     */     }
/* 923 */     else if (!ctx.isParentBean(value)) {
/*     */ 
/*     */ 
/*     */       
/* 927 */       ctx.pushParentBean(bean);
/* 928 */       ctx.beginAssocOne(this.name);
/* 929 */       BeanDescriptor<?> refDesc = this.descriptor.getBeanDescriptor(value.getClass());
/* 930 */       refDesc.jsonWrite(ctx, value);
/* 931 */       ctx.endAssocOne();
/* 932 */       ctx.popParentBean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/* 940 */     T assocBean = (T)this.targetDescriptor.jsonReadBean(ctx, this.name);
/* 941 */     setValue(bean, assocBean);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssocOne.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */