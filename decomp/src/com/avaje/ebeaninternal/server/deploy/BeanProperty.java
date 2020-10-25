/*      */ package com.avaje.ebeaninternal.server.deploy;
/*      */ 
/*      */ import com.avaje.ebean.InvalidValue;
/*      */ import com.avaje.ebean.config.EncryptKey;
/*      */ import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
/*      */ import com.avaje.ebean.config.dbplatform.DbType;
/*      */ import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
/*      */ import com.avaje.ebean.config.lucene.LuceneIndex;
/*      */ import com.avaje.ebean.text.StringFormatter;
/*      */ import com.avaje.ebean.text.StringParser;
/*      */ import com.avaje.ebean.validation.factory.Validator;
/*      */ import com.avaje.ebeaninternal.server.core.InternString;
/*      */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*      */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
/*      */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*      */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*      */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*      */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*      */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*      */ import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
/*      */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*      */ import com.avaje.ebeaninternal.server.type.DataBind;
/*      */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*      */ import com.avaje.ebeaninternal.util.ValueUtil;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.BasicAttribute;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BeanProperty
/*      */   implements ElPropertyValue
/*      */ {
/*      */   public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
/*      */   public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
/*      */   public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
/*      */   public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
/*      */   final boolean id;
/*      */   final boolean unidirectionalShadow;
/*      */   final boolean embedded;
/*      */   final boolean version;
/*      */   final boolean nullable;
/*      */   final boolean unique;
/*      */   final boolean dbRead;
/*      */   final boolean dbInsertable;
/*      */   final boolean dbUpdatable;
/*      */   final boolean secondaryTable;
/*      */   final TableJoin secondaryTableJoin;
/*      */   final String secondaryTableJoinPrefix;
/*      */   final boolean inherited;
/*      */   final Class<?> owningType;
/*      */   final boolean local;
/*      */   final boolean lob;
/*      */   final boolean fetchEager;
/*      */   final boolean isTransient;
/*      */   final String name;
/*      */   final Field field;
/*      */   final Class<?> propertyType;
/*      */   final String dbBind;
/*      */   final String dbColumn;
/*      */   final String elPlaceHolder;
/*      */   final String elPlaceHolderEncrypted;
/*      */   final String sqlFormulaSelect;
/*      */   final String sqlFormulaJoin;
/*      */   final boolean formula;
/*      */   final boolean dbEncrypted;
/*      */   final boolean localEncrypted;
/*      */   final int dbEncryptedType;
/*      */   final int dbType;
/*      */   final Object defaultValue;
/*      */   final Map<String, String> extraAttributeMap;
/*      */   final Method readMethod;
/*      */   final Method writeMethod;
/*      */   final GeneratedProperty generatedProperty;
/*      */   final BeanReflectGetter getter;
/*      */   final BeanReflectSetter setter;
/*      */   final BeanDescriptor<?> descriptor;
/*      */   final ScalarType scalarType;
/*      */   final LdapAttributeAdapter ldapAttributeAdapter;
/*      */   final Validator[] validators;
/*      */   final boolean hasLocalValidators;
/*      */   boolean cascadeValidate;
/*      */   final int dbLength;
/*      */   final int dbScale;
/*      */   final String dbColumnDefn;
/*      */   final String dbConstraintExpression;
/*      */   final DbEncryptFunction dbEncryptFunction;
/*      */   final boolean dynamicSubclassWithInheritance;
/*      */   int deployOrder;
/*      */   
/*  290 */   public BeanProperty(DeployBeanProperty deploy) { this(null, null, deploy); }
/*      */ 
/*      */ 
/*      */   
/*      */   public BeanProperty(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanProperty deploy) {
/*  295 */     this.descriptor = descriptor;
/*  296 */     this.name = InternString.intern(deploy.getName());
/*  297 */     if (descriptor != null) {
/*  298 */       this.dynamicSubclassWithInheritance = (descriptor.isDynamicSubclass() && descriptor.hasInheritance());
/*      */     } else {
/*  300 */       this.dynamicSubclassWithInheritance = false;
/*      */     } 
/*  302 */     this.unidirectionalShadow = deploy.isUndirectionalShadow();
/*  303 */     this.localEncrypted = deploy.isLocalEncrypted();
/*  304 */     this.dbEncrypted = deploy.isDbEncrypted();
/*  305 */     this.dbEncryptedType = deploy.getDbEncryptedType();
/*  306 */     this.dbEncryptFunction = deploy.getDbEncryptFunction();
/*  307 */     this.dbBind = deploy.getDbBind();
/*  308 */     this.dbRead = deploy.isDbRead();
/*  309 */     this.dbInsertable = deploy.isDbInsertable();
/*  310 */     this.dbUpdatable = deploy.isDbUpdateable();
/*      */     
/*  312 */     this.secondaryTable = deploy.isSecondaryTable();
/*  313 */     if (this.secondaryTable) {
/*  314 */       this.secondaryTableJoin = new TableJoin(deploy.getSecondaryTableJoin(), null);
/*  315 */       this.secondaryTableJoinPrefix = deploy.getSecondaryTableJoinPrefix();
/*      */     } else {
/*  317 */       this.secondaryTableJoin = null;
/*  318 */       this.secondaryTableJoinPrefix = null;
/*      */     } 
/*  320 */     this.fetchEager = deploy.isFetchEager();
/*  321 */     this.isTransient = deploy.isTransient();
/*  322 */     this.nullable = deploy.isNullable();
/*  323 */     this.unique = deploy.isUnique();
/*  324 */     this.dbLength = deploy.getDbLength();
/*  325 */     this.dbScale = deploy.getDbScale();
/*  326 */     this.dbColumnDefn = InternString.intern(deploy.getDbColumnDefn());
/*  327 */     this.dbConstraintExpression = InternString.intern(deploy.getDbConstraintExpression());
/*      */     
/*  329 */     this.inherited = false;
/*  330 */     this.owningType = deploy.getOwningType();
/*  331 */     this.local = deploy.isLocal();
/*      */     
/*  333 */     this.version = deploy.isVersionColumn();
/*  334 */     this.embedded = deploy.isEmbedded();
/*  335 */     this.id = deploy.isId();
/*  336 */     this.generatedProperty = deploy.getGeneratedProperty();
/*  337 */     this.readMethod = deploy.getReadMethod();
/*  338 */     this.writeMethod = deploy.getWriteMethod();
/*  339 */     this.getter = deploy.getGetter();
/*  340 */     if (descriptor != null && this.getter == null && 
/*  341 */       !this.unidirectionalShadow) {
/*  342 */       String m = "Null Getter for: " + getFullBeanName();
/*  343 */       throw new RuntimeException(m);
/*      */     } 
/*      */     
/*  346 */     this.setter = deploy.getSetter();
/*      */     
/*  348 */     this.dbColumn = tableAliasIntern(descriptor, deploy.getDbColumn(), false, null);
/*  349 */     this.sqlFormulaJoin = InternString.intern(deploy.getSqlFormulaJoin());
/*  350 */     this.sqlFormulaSelect = InternString.intern(deploy.getSqlFormulaSelect());
/*  351 */     this.formula = (this.sqlFormulaSelect != null);
/*      */     
/*  353 */     this.extraAttributeMap = deploy.getExtraAttributeMap();
/*  354 */     this.defaultValue = deploy.getDefaultValue();
/*  355 */     this.dbType = deploy.getDbType();
/*  356 */     this.scalarType = deploy.getScalarType();
/*  357 */     this.ldapAttributeAdapter = deploy.getLdapAttributeAdapter();
/*  358 */     this.lob = isLobType(this.dbType);
/*  359 */     this.propertyType = deploy.getPropertyType();
/*  360 */     this.field = deploy.getField();
/*  361 */     this.validators = deploy.getValidators();
/*  362 */     this.hasLocalValidators = (this.validators.length > 0);
/*      */     
/*  364 */     BeanDescriptor.EntityType et = (descriptor == null) ? null : descriptor.getEntityType();
/*  365 */     this.elPlaceHolder = tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), false, null);
/*  366 */     this.elPlaceHolderEncrypted = tableAliasIntern(descriptor, deploy.getElPlaceHolder(et), this.dbEncrypted, this.dbColumn);
/*      */   }
/*      */   
/*      */   private String tableAliasIntern(BeanDescriptor<?> descriptor, String s, boolean dbEncrypted, String dbColumn) {
/*  370 */     if (descriptor != null) {
/*  371 */       s = StringHelper.replaceString(s, "${ta}.", "${}");
/*  372 */       s = StringHelper.replaceString(s, "${ta}", "${}");
/*      */       
/*  374 */       if (dbEncrypted) {
/*  375 */         s = this.dbEncryptFunction.getDecryptSql(s);
/*  376 */         String namedParam = ":encryptkey_" + descriptor.getBaseTable() + "___" + dbColumn;
/*  377 */         s = StringHelper.replaceString(s, "?", namedParam);
/*      */       } 
/*      */     } 
/*  380 */     return InternString.intern(s);
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
/*      */   public BeanProperty(BeanProperty source, BeanPropertyOverride override) {
/*  392 */     this.descriptor = source.descriptor;
/*  393 */     this.name = InternString.intern(source.getName());
/*  394 */     this.dynamicSubclassWithInheritance = source.dynamicSubclassWithInheritance;
/*      */     
/*  396 */     this.dbColumn = InternString.intern(override.getDbColumn());
/*  397 */     this.sqlFormulaJoin = InternString.intern(override.getSqlFormulaJoin());
/*  398 */     this.sqlFormulaSelect = InternString.intern(override.getSqlFormulaSelect());
/*  399 */     this.formula = (this.sqlFormulaSelect != null);
/*      */     
/*  401 */     this.fetchEager = source.fetchEager;
/*  402 */     this.unidirectionalShadow = source.unidirectionalShadow;
/*  403 */     this.localEncrypted = source.isLocalEncrypted();
/*  404 */     this.isTransient = source.isTransient();
/*  405 */     this.secondaryTable = source.isSecondaryTable();
/*  406 */     this.secondaryTableJoin = source.secondaryTableJoin;
/*  407 */     this.secondaryTableJoinPrefix = source.secondaryTableJoinPrefix;
/*      */     
/*  409 */     this.dbBind = source.getDbBind();
/*  410 */     this.dbEncrypted = source.isDbEncrypted();
/*  411 */     this.dbEncryptedType = source.getDbEncryptedType();
/*  412 */     this.dbEncryptFunction = source.dbEncryptFunction;
/*  413 */     this.dbRead = source.isDbRead();
/*  414 */     this.dbInsertable = source.isDbInsertable();
/*  415 */     this.dbUpdatable = source.isDbUpdatable();
/*  416 */     this.nullable = source.isNullable();
/*  417 */     this.unique = source.isUnique();
/*  418 */     this.dbLength = source.getDbLength();
/*  419 */     this.dbScale = source.getDbScale();
/*  420 */     this.dbColumnDefn = InternString.intern(source.getDbColumnDefn());
/*  421 */     this.dbConstraintExpression = InternString.intern(source.getDbConstraintExpression());
/*      */     
/*  423 */     this.inherited = source.isInherited();
/*  424 */     this.owningType = source.owningType;
/*  425 */     this.local = this.owningType.equals(this.descriptor.getBeanType());
/*      */     
/*  427 */     this.version = source.isVersion();
/*  428 */     this.embedded = source.isEmbedded();
/*  429 */     this.id = source.isId();
/*  430 */     this.generatedProperty = source.getGeneratedProperty();
/*  431 */     this.readMethod = source.getReadMethod();
/*  432 */     this.writeMethod = source.getWriteMethod();
/*  433 */     this.getter = source.getter;
/*  434 */     this.setter = source.setter;
/*  435 */     this.extraAttributeMap = source.extraAttributeMap;
/*  436 */     this.defaultValue = source.getDefaultValue();
/*  437 */     this.dbType = source.getDbType();
/*  438 */     this.scalarType = source.scalarType;
/*  439 */     this.ldapAttributeAdapter = source.ldapAttributeAdapter;
/*  440 */     this.lob = isLobType(this.dbType);
/*  441 */     this.propertyType = source.getPropertyType();
/*  442 */     this.field = source.getField();
/*  443 */     this.validators = source.getValidators();
/*  444 */     this.hasLocalValidators = (this.validators.length > 0);
/*      */     
/*  446 */     this.elPlaceHolder = override.replace(source.elPlaceHolder, source.dbColumn);
/*  447 */     this.elPlaceHolderEncrypted = override.replace(source.elPlaceHolderEncrypted, source.dbColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialise() {
/*  457 */     if (!this.isTransient && this.scalarType == null) {
/*  458 */       String msg = "No ScalarType assigned to " + this.descriptor.getFullName() + "." + getName();
/*  459 */       throw new RuntimeException(msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  467 */   public int getDeployOrder() { return this.deployOrder; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  474 */   public void setDeployOrder(int deployOrder) { this.deployOrder = deployOrder; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  479 */   public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) { throw new PersistenceException("Not valid on scalar bean property " + getFullBeanName()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  486 */   public BeanDescriptor<?> getBeanDescriptor() { return this.descriptor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  493 */   public boolean isScalar() { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  500 */   public boolean isFormula() { return this.formula; }
/*      */ 
/*      */   
/*      */   public boolean hasChanged(Object bean, Object oldValues) {
/*  504 */     Object value = getValue(bean);
/*  505 */     Object oldVal = getValue(oldValues);
/*      */     
/*  507 */     return !ValueUtil.areEqual(value, oldVal);
/*      */   }
/*      */   
/*      */   public void copyProperty(Object sourceBean, Object destBean) {
/*  511 */     Object value = getValue(sourceBean);
/*  512 */     setValue(destBean, value);
/*      */   }
/*      */   
/*      */   public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
/*  516 */     Object value = getValue(sourceBean);
/*  517 */     setValue(destBean, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  524 */   public EncryptKey getEncryptKey() { return this.descriptor.getEncryptKey(this); }
/*      */ 
/*      */ 
/*      */   
/*  528 */   public String getDecryptProperty() { return this.dbEncryptFunction.getDecryptSql(getName()); }
/*      */ 
/*      */ 
/*      */   
/*  532 */   public String getDecryptProperty(String propertyName) { return this.dbEncryptFunction.getDecryptSql(propertyName); }
/*      */ 
/*      */ 
/*      */   
/*  536 */   public String getDecryptSql() { return this.dbEncryptFunction.getDecryptSql(getDbColumn()); }
/*      */ 
/*      */ 
/*      */   
/*  540 */   public String getDecryptSql(String tableAlias) { return this.dbEncryptFunction.getDecryptSql(tableAlias + "." + getDbColumn()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendFrom(DbSqlContext ctx, boolean forceOuterJoin) {
/*  548 */     if (this.formula && this.sqlFormulaJoin != null) {
/*  549 */       ctx.appendFormulaJoin(this.sqlFormulaJoin, forceOuterJoin);
/*      */     }
/*  551 */     else if (this.secondaryTableJoin != null) {
/*      */       
/*  553 */       String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
/*  554 */       this.secondaryTableJoin.addJoin(forceOuterJoin, relativePrefix, ctx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  563 */   public String getSecondaryTableJoinPrefix() { return this.secondaryTableJoinPrefix; }
/*      */ 
/*      */   
/*      */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/*  567 */     if (this.formula) {
/*  568 */       ctx.appendFormulaSelect(this.sqlFormulaSelect);
/*      */     }
/*  570 */     else if (!this.isTransient) {
/*      */       
/*  572 */       if (this.secondaryTableJoin != null) {
/*  573 */         String relativePrefix = ctx.getRelativePrefix(this.secondaryTableJoinPrefix);
/*  574 */         ctx.pushTableAlias(relativePrefix);
/*      */       } 
/*      */       
/*  577 */       if (this.dbEncrypted) {
/*  578 */         String decryptSql = getDecryptSql(ctx.peekTableAlias());
/*  579 */         ctx.appendRawColumn(decryptSql);
/*  580 */         ctx.addEncryptedProp(this);
/*      */       } else {
/*      */         
/*  583 */         ctx.appendColumn(this.dbColumn);
/*      */       } 
/*      */       
/*  586 */       if (this.secondaryTableJoin != null) {
/*  587 */         ctx.popTableAlias();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  593 */   public boolean isAssignableFrom(Class<?> type) { return this.owningType.isAssignableFrom(type); }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object readSetOwning(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/*      */     try {
/*  599 */       Object value = this.scalarType.read(ctx.getDataReader());
/*  600 */       if (value != null && bean != null)
/*      */       {
/*      */         
/*  603 */         if (this.owningType.equals(type)) {
/*  604 */           setValue(bean, value);
/*      */         }
/*      */       }
/*  607 */       return value;
/*  608 */     } catch (Exception e) {
/*  609 */       String msg = "Error readSet on " + this.descriptor + "." + this.name;
/*  610 */       throw new PersistenceException(msg, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  615 */   public void loadIgnore(DbReadContext ctx) { this.scalarType.loadIgnore(ctx.getDataReader()); }
/*      */ 
/*      */ 
/*      */   
/*  619 */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException { sqlBeanLoad.load(this); }
/*      */ 
/*      */   
/*      */   public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
/*  623 */     if (prefix == null) {
/*  624 */       selectChain.add(this.name);
/*      */     } else {
/*  626 */       selectChain.add(prefix + "." + this.name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  631 */   public Object read(DbReadContext ctx) throws SQLException { return this.scalarType.read(ctx.getDataReader()); }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/*      */     try {
/*  637 */       Object value = this.scalarType.read(ctx.getDataReader());
/*  638 */       if (bean != null && (type == null || this.owningType.isAssignableFrom(type)))
/*      */       {
/*      */         
/*  641 */         setValue(bean, value);
/*      */       }
/*  643 */       return value;
/*  644 */     } catch (Exception e) {
/*  645 */       String msg = "Error readSet on " + this.descriptor + "." + this.name;
/*  646 */       throw new PersistenceException(msg, e);
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
/*  658 */   public Object toBeanType(Object value) { return this.scalarType.toBeanType(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  663 */   public void bind(DataBind b, Object value) throws SQLException { this.scalarType.bind(b, value); }
/*      */ 
/*      */ 
/*      */   
/*  667 */   public void writeData(DataOutput dataOutput, Object value) throws IOException { this.scalarType.writeData(dataOutput, value); }
/*      */ 
/*      */ 
/*      */   
/*  671 */   public Object readData(DataInput dataInput) throws IOException { return this.scalarType.readData(dataInput); }
/*      */ 
/*      */ 
/*      */   
/*  675 */   Validator[] getValidators() { return this.validators; }
/*      */ 
/*      */ 
/*      */   
/*  679 */   public boolean isCascadeValidate() { return this.cascadeValidate; }
/*      */ 
/*      */ 
/*      */   
/*  683 */   public boolean hasLocalValidators() { return this.hasLocalValidators; }
/*      */ 
/*      */ 
/*      */   
/*  687 */   public boolean hasValidationRules(boolean cascade) { return (this.hasLocalValidators || (cascade && this.cascadeValidate)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  698 */   public boolean isValueLoaded(Object value) { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  705 */   public InvalidValue validateCascade(Object value) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final List<InvalidValue> validate(boolean cascade, Object value) {
/*  719 */     if (!isValueLoaded(value)) {
/*  720 */       return null;
/*      */     }
/*      */     
/*  723 */     ArrayList<InvalidValue> list = null;
/*  724 */     for (int i = 0; i < this.validators.length; i++) {
/*  725 */       if (!this.validators[i].isValid(value)) {
/*  726 */         if (list == null) {
/*  727 */           list = new ArrayList<InvalidValue>();
/*      */         }
/*  729 */         Validator v = this.validators[i];
/*  730 */         list.add(new InvalidValue(v.getKey(), v.getAttributes(), this.descriptor.getFullName(), this.name, value));
/*      */       } 
/*      */     } 
/*      */     
/*  734 */     if (list == null && cascade && this.cascadeValidate) {
/*      */       
/*  736 */       InvalidValue recursive = validateCascade(value);
/*  737 */       if (recursive != null) {
/*  738 */         return InvalidValue.toList(recursive);
/*      */       }
/*      */     } 
/*      */     
/*  742 */     return list;
/*      */   }
/*      */ 
/*      */   
/*  746 */   public BeanProperty getBeanProperty() { return this; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  753 */   public Method getReadMethod() { return this.readMethod; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  760 */   public Method getWriteMethod() { return this.writeMethod; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  767 */   public boolean isInherited() { return this.inherited; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  774 */   public boolean isLocal() { return this.local; }
/*      */ 
/*      */   
/*      */   public Attribute createAttribute(Object bean) {
/*  778 */     Object v = getValue(bean);
/*  779 */     if (v == null) {
/*  780 */       return null;
/*      */     }
/*  782 */     if (this.ldapAttributeAdapter != null) {
/*  783 */       return this.ldapAttributeAdapter.createAttribute(v);
/*      */     }
/*  785 */     Object ldapValue = this.scalarType.toJdbcType(v);
/*  786 */     return new BasicAttribute(this.dbColumn, ldapValue);
/*      */   }
/*      */   
/*      */   public void setAttributeValue(Object bean, Attribute attr) {
/*      */     try {
/*  791 */       if (attr != null) {
/*      */         Object beanValue;
/*  793 */         if (this.ldapAttributeAdapter != null) {
/*  794 */           beanValue = this.ldapAttributeAdapter.readAttribute(attr);
/*      */         } else {
/*  796 */           beanValue = this.scalarType.toBeanType(attr.get());
/*      */         } 
/*      */         
/*  799 */         setValue(bean, beanValue);
/*      */       } 
/*  801 */     } catch (NamingException e) {
/*  802 */       throw new LdapPersistenceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValue(Object bean, Object value) {
/*      */     try {
/*  812 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  813 */         this.setter.set(bean, value);
/*      */       } else {
/*  815 */         Object[] args = new Object[1];
/*  816 */         args[0] = value;
/*  817 */         this.writeMethod.invoke(bean, args);
/*      */       } 
/*  819 */     } catch (Exception ex) {
/*  820 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  821 */       String msg = "set " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
/*      */       
/*  823 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValueIntercept(Object bean, Object value) {
/*      */     try {
/*  832 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  833 */         this.setter.setIntercept(bean, value);
/*      */       } else {
/*  835 */         Object[] args = new Object[1];
/*  836 */         args[0] = value;
/*  837 */         this.writeMethod.invoke(bean, args);
/*      */       } 
/*  839 */     } catch (Exception ex) {
/*  840 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  841 */       String msg = "setIntercept " + this.name + " on [" + this.descriptor + "] arg[" + value + "] type[" + beanType + "] threw error";
/*      */       
/*  843 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*  847 */   private static Object[] NO_ARGS = new Object[0];
/*      */   
/*      */   private ArrayList<LuceneIndex> luceneIndexes;
/*      */ 
/*      */   
/*      */   public Object getValueWithInheritance(Object bean) {
/*  853 */     if (this.dynamicSubclassWithInheritance) {
/*  854 */       return this.descriptor.getBeanPropertyWithInheritance(bean, this.name);
/*      */     }
/*  856 */     return getValue(bean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValue(Object bean) {
/*      */     try {
/*  864 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  865 */         return this.getter.get(bean);
/*      */       }
/*  867 */       return this.readMethod.invoke(bean, NO_ARGS);
/*      */     }
/*  869 */     catch (Exception ex) {
/*  870 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  871 */       String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  872 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getValueViaReflection(Object bean) {
/*      */     try {
/*  881 */       return this.readMethod.invoke(bean, NO_ARGS);
/*  882 */     } catch (Exception ex) {
/*  883 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  884 */       String msg = "get " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  885 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object getValueIntercept(Object bean) {
/*      */     try {
/*  891 */       if (bean instanceof com.avaje.ebean.bean.EntityBean) {
/*  892 */         return this.getter.getIntercept(bean);
/*      */       }
/*  894 */       return this.readMethod.invoke(bean, NO_ARGS);
/*      */     }
/*  896 */     catch (Exception ex) {
/*  897 */       String beanType = (bean == null) ? "null" : bean.getClass().getName();
/*  898 */       String msg = "getIntercept " + this.name + " on [" + this.descriptor + "] type[" + beanType + "] threw error.";
/*  899 */       throw new RuntimeException(msg, ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object elConvertType(Object value) {
/*  904 */     if (value == null) {
/*  905 */       return null;
/*      */     }
/*  907 */     return convertToLogicalType(value);
/*      */   }
/*      */ 
/*      */   
/*  911 */   public void elSetReference(Object bean) { throw new RuntimeException("Should not be called"); }
/*      */ 
/*      */   
/*      */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/*  915 */     if (bean != null) {
/*  916 */       setValueIntercept(bean, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public Object elGetValue(Object bean) {
/*  921 */     if (bean == null) {
/*  922 */       return null;
/*      */     }
/*  924 */     return getValueIntercept(bean);
/*      */   }
/*      */ 
/*      */   
/*  928 */   public Object elGetReference(Object bean) { throw new RuntimeException("Not expected to call this"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  935 */   public String getName() { return this.name; }
/*      */ 
/*      */ 
/*      */   
/*  939 */   public String getElName() { return this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  946 */   public boolean isDeployOnly() { return false; }
/*      */ 
/*      */ 
/*      */   
/*  950 */   public boolean containsManySince(String sinceProperty) { return containsMany(); }
/*      */ 
/*      */ 
/*      */   
/*  954 */   public boolean containsMany() { return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  959 */   public Object[] getAssocOneIdValues(Object bean) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  964 */   public String getAssocOneIdExpr(String prefix, String operator) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  969 */   public String getAssocIdInExpr(String prefix) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  974 */   public String getAssocIdInValueExpr(int size) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  979 */   public boolean isAssocId() { return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  984 */   public boolean isAssocProperty() { return false; }
/*      */ 
/*      */ 
/*      */   
/*  988 */   public String getElPlaceholder(boolean encrypted) { return encrypted ? this.elPlaceHolderEncrypted : this.elPlaceHolder; }
/*      */ 
/*      */ 
/*      */   
/*  992 */   public String getElPrefix() { return this.secondaryTableJoinPrefix; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  999 */   public String getFullBeanName() { return this.descriptor.getFullName() + "." + this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1006 */   public ScalarType<?> getScalarType() { return this.scalarType; }
/*      */ 
/*      */ 
/*      */   
/* 1010 */   public StringFormatter getStringFormatter() { return this.scalarType; }
/*      */ 
/*      */ 
/*      */   
/* 1014 */   public StringParser getStringParser() { return this.scalarType; }
/*      */ 
/*      */ 
/*      */   
/* 1018 */   public boolean isDateTimeCapable() { return (this.scalarType != null && this.scalarType.isDateTimeCapable()); }
/*      */ 
/*      */ 
/*      */   
/* 1022 */   public int getJdbcType() { return (this.scalarType == null) ? 0 : this.scalarType.getJdbcType(); }
/*      */ 
/*      */ 
/*      */   
/* 1026 */   public Object parseDateTime(long systemTimeMillis) { return this.scalarType.parseDateTime(systemTimeMillis); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1033 */   public int getDbLength() { return this.dbLength; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   public int getDbScale() { return this.dbScale; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1047 */   public String getDbColumnDefn() { return this.dbColumnDefn; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1057 */   public String getDbConstraintExpression() { return this.dbConstraintExpression; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String renderDbType(DbType dbType) {
/* 1064 */     if (this.dbColumnDefn != null) {
/* 1065 */       return this.dbColumnDefn;
/*      */     }
/* 1067 */     return dbType.renderType(this.dbLength, this.dbScale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1074 */   public Field getField() { return this.field; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1081 */   public GeneratedProperty getGeneratedProperty() { return this.generatedProperty; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1088 */   public boolean isNullable() { return this.nullable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1096 */   public boolean isDDLNotNull() { return (isVersion() || (this.generatedProperty != null && this.generatedProperty.isDDLNotNullable())); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1103 */   public boolean isUnique() { return this.unique; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1110 */   public boolean isTransient() { return this.isTransient; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1117 */   public boolean isVersion() { return this.version; }
/*      */ 
/*      */ 
/*      */   
/* 1121 */   public String getDeployProperty() { return this.dbColumn; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1128 */   public String getDbColumn() { return this.dbColumn; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1135 */   public int getDbType() { return this.dbType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object convertToLogicalType(Object value) {
/* 1142 */     if (this.scalarType != null) {
/* 1143 */       return this.scalarType.toBeanType(value);
/*      */     }
/* 1145 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerLuceneIndex(LuceneIndex luceneIndex) {
/* 1151 */     if (this.luceneIndexes == null) {
/* 1152 */       this.luceneIndexes = new ArrayList();
/*      */     }
/* 1154 */     this.luceneIndexes.add(luceneIndex);
/*      */   }
/*      */ 
/*      */   
/* 1158 */   public boolean isDeltaRequired() { return (this.luceneIndexes != null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1166 */   public boolean isFetchEager() { return this.fetchEager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1174 */   public boolean isLob() { return this.lob; }
/*      */ 
/*      */   
/*      */   private boolean isLobType(int type) {
/* 1178 */     switch (type) {
/*      */       case 2005:
/* 1180 */         return true;
/*      */       case 2004:
/* 1182 */         return true;
/*      */       case -4:
/* 1184 */         return true;
/*      */       case -1:
/* 1186 */         return true;
/*      */     } 
/*      */     
/* 1189 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1198 */   public String getDbBind() { return this.dbBind; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1205 */   public boolean isLocalEncrypted() { return this.localEncrypted; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1212 */   public boolean isDbEncrypted() { return this.dbEncrypted; }
/*      */ 
/*      */ 
/*      */   
/* 1216 */   public int getDbEncryptedType() { return this.dbEncryptedType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1223 */   public boolean isDbInsertable() { return this.dbInsertable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1230 */   public boolean isDbUpdatable() { return this.dbUpdatable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1237 */   public boolean isDbRead() { return this.dbRead; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1245 */   public boolean isSecondaryTable() { return this.secondaryTable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1252 */   public Class<?> getPropertyType() { return this.propertyType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1259 */   public boolean isId() { return this.id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1267 */   public boolean isEmbedded() { return this.embedded; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1274 */   public String getExtraAttribute(String key) { return (String)this.extraAttributeMap.get(key); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1281 */   public Object getDefaultValue() { return this.defaultValue; }
/*      */ 
/*      */ 
/*      */   
/* 1285 */   public String toString() { return this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void jsonWrite(WriteJsonContext ctx, Object bean) {
/* 1291 */     Object value = getValueIntercept(bean);
/* 1292 */     if (value == null) {
/* 1293 */       ctx.appendNull(this.name);
/*      */     } else {
/* 1295 */       String jv = this.scalarType.jsonToString(value, ctx.getValueAdapter());
/* 1296 */       ctx.appendKeyValue(this.name, jv);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void jsonRead(ReadJsonContext ctx, Object bean) {
/*      */     Object objValue;
/* 1302 */     String jsonValue = ctx.readScalarValue();
/*      */ 
/*      */     
/* 1305 */     if (jsonValue == null) {
/* 1306 */       objValue = null;
/*      */     } else {
/* 1308 */       objValue = this.scalarType.jsonFromString(jsonValue, ctx.getValueAdapter());
/*      */     } 
/* 1310 */     setValue(bean, objValue);
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */