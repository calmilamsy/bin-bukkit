/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class BeanPropertyAssocMany<T>
/*     */   extends BeanPropertyAssoc<T>
/*     */ {
/*     */   final TableJoin intersectionJoin;
/*     */   final TableJoin inverseJoin;
/*     */   final boolean unidirectional;
/*     */   final boolean manyToMany;
/*     */   final String fetchOrderBy;
/*     */   final String mapKey;
/*     */   final ManyType manyType;
/*     */   final String serverName;
/*     */   final BeanCollection.ModifyListenMode modifyListenMode;
/*     */   BeanProperty mapKeyProperty;
/*     */   ExportedProperty[] exportedProperties;
/*     */   BeanProperty childMasterProperty;
/*     */   boolean embeddedExportedProperties;
/*     */   BeanCollectionHelp<T> help;
/*     */   ImportedId importedId;
/*     */   String deleteByParentIdSql;
/*     */   String deleteByParentIdInSql;
/*     */   final CollectionTypeConverter typeConverter;
/*     */   
/*     */   public BeanPropertyAssocMany(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyAssocMany<T> deploy) {
/* 116 */     super(owner, descriptor, deploy);
/* 117 */     this.unidirectional = deploy.isUnidirectional();
/* 118 */     this.manyToMany = deploy.isManyToMany();
/* 119 */     this.serverName = descriptor.getServerName();
/* 120 */     this.manyType = deploy.getManyType();
/* 121 */     this.typeConverter = this.manyType.getTypeConverter();
/* 122 */     this.mapKey = deploy.getMapKey();
/* 123 */     this.fetchOrderBy = deploy.getFetchOrderBy();
/*     */     
/* 125 */     this.intersectionJoin = deploy.createIntersectionTableJoin();
/* 126 */     this.inverseJoin = deploy.createInverseTableJoin();
/* 127 */     this.modifyListenMode = deploy.getModifyListenMode();
/*     */   }
/*     */   
/*     */   public void initialise() {
/* 131 */     super.initialise();
/*     */     
/* 133 */     if (!this.isTransient) {
/* 134 */       String delStmt; this.help = BeanCollectionHelpFactory.create(this);
/*     */       
/* 136 */       if (this.manyToMany) {
/*     */         
/* 138 */         this.importedId = createImportedId(this, this.targetDescriptor, this.tableJoin);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 143 */         this.childMasterProperty = initChildMasterProperty();
/*     */       } 
/*     */       
/* 146 */       if (this.mapKey != null) {
/* 147 */         this.mapKeyProperty = initMapKeyProperty();
/*     */       }
/*     */       
/* 150 */       this.exportedProperties = createExported();
/* 151 */       if (this.exportedProperties.length > 0) {
/* 152 */         this.embeddedExportedProperties = this.exportedProperties[0].isEmbedded();
/*     */       }
/*     */ 
/*     */       
/* 156 */       if (this.manyToMany) {
/* 157 */         delStmt = "delete from " + this.inverseJoin.getTable() + " where ";
/*     */       } else {
/* 159 */         delStmt = "delete from " + this.targetDescriptor.getBaseTable() + " where ";
/*     */       } 
/* 161 */       this.deleteByParentIdSql = delStmt + deriveWhereParentIdSql(false);
/* 162 */       this.deleteByParentIdInSql = delStmt + deriveWhereParentIdSql(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValueUnderlying(Object bean) {
/* 172 */     Object value = getValue(bean);
/* 173 */     if (this.typeConverter != null) {
/* 174 */       value = this.typeConverter.toUnderlying(value);
/*     */     }
/* 176 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public Object getValue(Object bean) { return super.getValue(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public Object getValueIntercept(Object bean) { return super.getValueIntercept(bean); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object bean, Object value) {
/* 191 */     if (this.typeConverter != null) {
/* 192 */       value = this.typeConverter.toWrapped(value);
/*     */     }
/* 194 */     super.setValue(bean, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValueIntercept(Object bean, Object value) {
/* 199 */     if (this.typeConverter != null) {
/* 200 */       value = this.typeConverter.toWrapped(value);
/*     */     }
/* 202 */     super.setValueIntercept(bean, value);
/*     */   }
/*     */ 
/*     */   
/* 206 */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) { return createElPropertyValue(propName, remainder, chain, propertyDeploy); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
/* 212 */     Object sourceCollection = getValueUnderlying(sourceBean);
/* 213 */     if (sourceCollection != null) {
/* 214 */       Object copyCollection = this.help.copyCollection(sourceCollection, ctx, maxDepth, destBean);
/* 215 */       setValue(destBean, copyCollection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SqlUpdate deleteByParentId(Object parentId, List<Object> parentIdist) {
/* 220 */     if (parentId != null) {
/* 221 */       return deleteByParentId(parentId);
/*     */     }
/* 223 */     return deleteByParentIdList(parentIdist);
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentId(Object parentId) {
/* 228 */     DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByParentIdSql);
/* 229 */     bindWhereParendId(sqlDelete, parentId);
/* 230 */     return sqlDelete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> findIdsByParentId(Object parentId, List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 237 */     if (parentId != null) {
/* 238 */       return findIdsByParentId(parentId, t, excludeDetailIds);
/*     */     }
/* 240 */     return findIdsByParentIdList(parentIdist, t, excludeDetailIds);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentId(Object parentId, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 246 */     String rawWhere = deriveWhereParentIdSql(false);
/*     */     
/* 248 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 249 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(rawWhere).query();
/*     */ 
/*     */     
/* 252 */     bindWhereParendId(1, q, parentId);
/*     */     
/* 254 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 255 */       Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
/* 256 */       q.where().not(idIn);
/*     */     } 
/*     */     
/* 259 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> findIdsByParentIdList(List<Object> parentIdist, Transaction t, ArrayList<Object> excludeDetailIds) {
/* 264 */     String rawWhere = deriveWhereParentIdSql(true);
/* 265 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/*     */     
/* 267 */     String expr = rawWhere + inClause;
/*     */     
/* 269 */     SpiEbeanServer spiEbeanServer = getBeanDescriptor().getEbeanServer();
/* 270 */     Query<?> q = spiEbeanServer.find(getPropertyType()).where().raw(expr).query();
/*     */ 
/*     */     
/* 273 */     int pos = 1;
/* 274 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 275 */       pos = bindWhereParendId(pos, q, parentIdist.get(i));
/*     */     }
/*     */     
/* 278 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 279 */       Expression idIn = q.getExpressionFactory().idIn(excludeDetailIds);
/* 280 */       q.where().not(idIn);
/*     */     } 
/*     */     
/* 283 */     return spiEbeanServer.findIds(q, t);
/*     */   }
/*     */ 
/*     */   
/*     */   private SqlUpdate deleteByParentIdList(List<Object> parentIdist) {
/* 288 */     StringBuilder sb = new StringBuilder(100);
/* 289 */     sb.append(this.deleteByParentIdInSql);
/*     */     
/* 291 */     String inClause = this.targetIdBinder.getIdInValueExpr(parentIdist.size());
/* 292 */     sb.append(inClause);
/*     */     
/* 294 */     DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
/* 295 */     for (int i = 0; i < parentIdist.size(); i++) {
/* 296 */       bindWhereParendId(delete, parentIdist.get(i));
/*     */     }
/*     */     
/* 299 */     return delete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoader(BeanCollectionLoader loader) {
/* 307 */     if (this.help != null) {
/* 308 */       this.help.setLoader(loader);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public BeanCollection.ModifyListenMode getModifyListenMode() { return this.modifyListenMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public boolean hasChanged(Object bean, Object oldValues) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIgnore(DbReadContext ctx) {}
/*     */ 
/*     */ 
/*     */   
/* 338 */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException { sqlBeanLoad.loadAssocMany(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 348 */   public Object read(DbReadContext ctx) throws SQLException { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueLoaded(Object value) {
/* 354 */     if (value instanceof BeanCollection) {
/* 355 */       return ((BeanCollection)value).isPopulated();
/*     */     }
/* 357 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 361 */   public void add(BeanCollection<?> collection, Object bean) { this.help.add(collection, bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue validateCascade(Object manyValue) {
/* 367 */     ArrayList<InvalidValue> errs = this.help.validate(manyValue);
/*     */     
/* 369 */     if (errs == null) {
/* 370 */       return null;
/*     */     }
/* 372 */     return new InvalidValue("recurse.many", this.targetDescriptor.getFullName(), manyValue, InvalidValue.toArray(errs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 380 */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) { this.help.refresh(server, query, t, parentBean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 387 */   public void refresh(BeanCollection<?> bc, Object parentBean) { this.help.refresh(bc, parentBean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 395 */   public Object[] getAssocOneIdValues(Object bean) { return this.targetDescriptor.getIdBinder().getIdValues(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 402 */   public String getAssocOneIdExpr(String prefix, String operator) { return this.targetDescriptor.getIdBinder().getAssocOneIdExpr(prefix, operator); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 410 */   public String getAssocIdInValueExpr(int size) { return this.targetDescriptor.getIdBinder().getIdInValueExpr(size); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 418 */   public String getAssocIdInExpr(String prefix) { return this.targetDescriptor.getIdBinder().getAssocIdInExpr(prefix); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   public boolean isAssocId() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 429 */   public boolean isAssocProperty() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 437 */   public boolean containsMany() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 444 */   public ManyType getManyType() { return this.manyType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 451 */   public boolean isManyToMany() { return this.manyToMany; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   public TableJoin getIntersectionTableJoin() { return this.intersectionJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJoinValuesToChild(Object parent, Object child, Object mapKeyValue) {
/* 467 */     if (this.mapKeyProperty != null) {
/* 468 */       this.mapKeyProperty.setValue(child, mapKeyValue);
/*     */     }
/*     */     
/* 471 */     if (!this.manyToMany && 
/* 472 */       this.childMasterProperty != null)
/*     */     {
/*     */       
/* 475 */       this.childMasterProperty.setValue(child, parent);
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
/*     */   
/* 488 */   public String getFetchOrderBy() { return this.fetchOrderBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 495 */   public String getMapKey() { return this.mapKey; }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<?> createReferenceIfNull(Object parentBean) {
/* 500 */     Object v = getValue(parentBean);
/* 501 */     if (v instanceof BeanCollection) {
/* 502 */       BeanCollection<?> bc = (BeanCollection)v;
/* 503 */       return bc.isReference() ? bc : null;
/*     */     } 
/* 505 */     return createReference(parentBean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<?> createReference(Object parentBean) {
/* 511 */     BeanCollection<?> ref = this.help.createReference(parentBean, this.name);
/* 512 */     setValue(parentBean, ref);
/* 513 */     return ref;
/*     */   }
/*     */ 
/*     */   
/* 517 */   public Object createEmpty(boolean vanilla) { return this.help.createEmpty(vanilla); }
/*     */ 
/*     */ 
/*     */   
/* 521 */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) { return this.help.getBeanCollectionAdd(bc, mapKey); }
/*     */ 
/*     */ 
/*     */   
/* 525 */   public Object getParentId(Object parentBean) { return this.descriptor.getId(parentBean); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void bindWhereParendId(DefaultSqlUpdate sqlUpd, Object parentId) {
/* 530 */     if (this.exportedProperties.length == 1) {
/* 531 */       sqlUpd.addParameter(parentId);
/*     */       return;
/*     */     } 
/* 534 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 535 */       Object embVal = this.exportedProperties[i].getValue(parentId);
/* 536 */       sqlUpd.addParameter(embVal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int bindWhereParendId(int pos, Query<?> q, Object parentId) {
/* 542 */     if (this.exportedProperties.length == 1) {
/* 543 */       q.setParameter(pos++, parentId);
/*     */     }
/*     */     else {
/*     */       
/* 547 */       for (int i = 0; i < this.exportedProperties.length; i++) {
/* 548 */         Object embVal = this.exportedProperties[i].getValue(parentId);
/* 549 */         q.setParameter(pos++, embVal);
/*     */       } 
/*     */     } 
/* 552 */     return pos;
/*     */   }
/*     */ 
/*     */   
/*     */   private String deriveWhereParentIdSql(boolean inClause) {
/* 557 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 559 */     if (inClause) {
/* 560 */       sb.append("(");
/*     */     }
/* 562 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 563 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 564 */       if (i > 0) {
/* 565 */         String s = inClause ? "," : " and ";
/* 566 */         sb.append(s);
/*     */       } 
/* 568 */       sb.append(fkColumn);
/* 569 */       if (!inClause) {
/* 570 */         sb.append("=? ");
/*     */       }
/*     */     } 
/* 573 */     if (inClause) {
/* 574 */       sb.append(")");
/*     */     }
/* 576 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPredicates(SpiQuery<?> query, Object parentBean) {
/* 581 */     if (this.manyToMany)
/*     */     {
/*     */ 
/*     */       
/* 585 */       query.setIncludeTableJoin(this.inverseJoin);
/*     */     }
/*     */     
/* 588 */     if (this.embeddedExportedProperties) {
/*     */       
/* 590 */       BeanProperty[] uids = this.descriptor.propertiesId();
/* 591 */       parentBean = uids[0].getValue(parentBean);
/*     */     } 
/*     */     
/* 594 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 595 */       Object val = this.exportedProperties[i].getValue(parentBean);
/* 596 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/* 597 */       if (!this.manyToMany) {
/* 598 */         fkColumn = this.targetDescriptor.getBaseTableAlias() + "." + fkColumn;
/*     */       } else {
/*     */         
/* 601 */         fkColumn = "int_." + fkColumn;
/*     */       } 
/* 603 */       query.where().eq(fkColumn, val);
/*     */     } 
/*     */     
/* 606 */     if (this.extraWhere != null) {
/*     */       
/* 608 */       String ta = this.targetDescriptor.getBaseTableAlias();
/* 609 */       String where = StringHelper.replaceString(this.extraWhere, "${ta}", ta);
/* 610 */       query.where().raw(where);
/*     */     } 
/*     */     
/* 613 */     if (this.fetchOrderBy != null) {
/* 614 */       query.order(this.fetchOrderBy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty[] createExported() {
/* 623 */     BeanProperty[] uids = this.descriptor.propertiesId();
/*     */     
/* 625 */     ArrayList<ExportedProperty> list = new ArrayList<ExportedProperty>();
/*     */     
/* 627 */     if (uids.length == 1 && uids[0].isEmbedded()) {
/*     */       
/* 629 */       BeanPropertyAssocOne<?> one = (BeanPropertyAssocOne)uids[0];
/* 630 */       BeanDescriptor<?> targetDesc = one.getTargetDescriptor();
/* 631 */       BeanProperty[] emIds = targetDesc.propertiesBaseScalar();
/*     */       try {
/* 633 */         for (int i = 0; i < emIds.length; i++) {
/* 634 */           ExportedProperty expProp = findMatch(true, emIds[i]);
/* 635 */           list.add(expProp);
/*     */         } 
/* 637 */       } catch (PersistenceException e) {
/*     */         
/* 639 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*     */       
/* 643 */       for (int i = 0; i < uids.length; i++) {
/* 644 */         ExportedProperty expProp = findMatch(false, uids[i]);
/* 645 */         list.add(expProp);
/*     */       } 
/*     */     } 
/*     */     
/* 649 */     return (ExportedProperty[])list.toArray(new ExportedProperty[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExportedProperty findMatch(boolean embedded, BeanProperty prop) {
/*     */     TableJoinColumn[] columns;
/* 657 */     String searchTable, matchColumn = prop.getDbColumn();
/*     */ 
/*     */ 
/*     */     
/* 661 */     if (this.manyToMany) {
/*     */       
/* 663 */       columns = this.intersectionJoin.columns();
/* 664 */       searchTable = this.intersectionJoin.getTable();
/*     */     } else {
/*     */       
/* 667 */       columns = this.tableJoin.columns();
/* 668 */       searchTable = this.tableJoin.getTable();
/*     */     } 
/* 670 */     for (int i = 0; i < columns.length; i++) {
/* 671 */       String matchTo = columns[i].getLocalDbColumn();
/*     */       
/* 673 */       if (matchColumn.equalsIgnoreCase(matchTo)) {
/* 674 */         String foreignCol = columns[i].getForeignDbColumn();
/* 675 */         return new ExportedProperty(embedded, foreignCol, prop);
/*     */       } 
/*     */     } 
/*     */     
/* 679 */     String msg = "Error with the Join on [" + getFullBeanName() + "]. Could not find the matching foreign key for [" + matchColumn + "] in table[" + searchTable + "]?" + " Perhaps using a @JoinColumn with the name/referencedColumnName attributes swapped?";
/*     */ 
/*     */     
/* 682 */     throw new PersistenceException(msg);
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
/*     */   private BeanProperty initChildMasterProperty() {
/* 694 */     if (this.unidirectional) {
/* 695 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 699 */     Class<?> beanType = this.descriptor.getBeanType();
/* 700 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 702 */     BeanPropertyAssocOne[] ones = targetDesc.propertiesOne();
/* 703 */     for (int i = 0; i < ones.length; i++) {
/*     */       
/* 705 */       BeanPropertyAssocOne<?> prop = ones[i];
/* 706 */       if (this.mappedBy != null) {
/*     */         
/* 708 */         if (this.mappedBy.equalsIgnoreCase(prop.getName())) {
/* 709 */           return prop;
/*     */         
/*     */         }
/*     */       }
/* 713 */       else if (prop.getTargetType().equals(beanType)) {
/*     */         
/* 715 */         return prop;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 720 */     String msg = "Can not find Master [" + beanType + "] in Child[" + targetDesc + "]";
/* 721 */     throw new RuntimeException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty initMapKeyProperty() {
/* 731 */     BeanDescriptor<?> targetDesc = getTargetDescriptor();
/*     */     
/* 733 */     Iterator<BeanProperty> it = targetDesc.propertiesAll();
/* 734 */     while (it.hasNext()) {
/* 735 */       BeanProperty prop = (BeanProperty)it.next();
/* 736 */       if (this.mapKey.equalsIgnoreCase(prop.getName())) {
/* 737 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 741 */     String from = this.descriptor.getFullName();
/* 742 */     String to = targetDesc.getFullName();
/* 743 */     String msg = from + ": Could not find mapKey property [" + this.mapKey + "] on [" + to + "]";
/* 744 */     throw new PersistenceException(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyDeleteChildren(Object parentBean, ArrayList<Object> excludeDetailIds) {
/* 749 */     IntersectionRow row = new IntersectionRow(this.tableJoin.getTable());
/* 750 */     if (excludeDetailIds != null && !excludeDetailIds.isEmpty()) {
/* 751 */       row.setExcludeIds(excludeDetailIds, getTargetDescriptor());
/*     */     }
/* 753 */     buildExport(row, parentBean);
/* 754 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyToManyDeleteChildren(Object parentBean) {
/* 759 */     IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
/* 760 */     buildExport(row, parentBean);
/* 761 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntersectionRow buildManyToManyMapBean(Object parent, Object other) {
/* 766 */     IntersectionRow row = new IntersectionRow(this.intersectionJoin.getTable());
/*     */     
/* 768 */     buildExport(row, parent);
/* 769 */     buildImport(row, other);
/* 770 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildExport(IntersectionRow row, Object parentBean) {
/* 775 */     if (this.embeddedExportedProperties) {
/* 776 */       BeanProperty[] uids = this.descriptor.propertiesId();
/* 777 */       parentBean = uids[0].getValue(parentBean);
/*     */     } 
/* 779 */     for (int i = 0; i < this.exportedProperties.length; i++) {
/* 780 */       Object val = this.exportedProperties[i].getValue(parentBean);
/* 781 */       String fkColumn = this.exportedProperties[i].getForeignDbColumn();
/*     */       
/* 783 */       row.put(fkColumn, val);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 793 */   private void buildImport(IntersectionRow row, Object otherBean) { this.importedId.buildImport(row, otherBean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 801 */   public boolean hasImportedId(Object otherBean) { return (null != this.targetDescriptor.getId(otherBean)); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 806 */     Boolean include = ctx.includeMany(this.name);
/* 807 */     if (Boolean.FALSE.equals(include)) {
/*     */       return;
/*     */     }
/*     */     
/* 811 */     Object value = getValueIntercept(bean);
/* 812 */     if (value != null) {
/* 813 */       ctx.pushParentBeanMany(bean);
/* 814 */       this.help.jsonWrite(ctx, this.name, value, (include != null));
/* 815 */       ctx.popParentBeanMany();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/* 821 */     if (!ctx.readArrayBegin()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 826 */     Object collection = this.help.createEmpty(false);
/* 827 */     BeanCollectionAdd add = getBeanCollectionAdd(collection, null);
/*     */     do {
/* 829 */       ReadJsonContext.ReadBeanState detailBeanState = this.targetDescriptor.jsonRead(ctx, this.name);
/* 830 */       if (detailBeanState == null) {
/*     */         break;
/*     */       }
/*     */       
/* 834 */       Object detailBean = detailBeanState.getBean();
/* 835 */       add.addBean(detailBean);
/*     */       
/* 837 */       if (bean != null && this.childMasterProperty != null) {
/*     */         
/* 839 */         this.childMasterProperty.setValue(detailBean, bean);
/* 840 */         detailBeanState.setLoaded(this.childMasterProperty.getName());
/*     */       } 
/*     */       
/* 843 */       detailBeanState.setLoadedState();
/*     */     }
/* 845 */     while (ctx.readArrayNext());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 850 */     setValue(bean, collection);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyAssocMany.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */