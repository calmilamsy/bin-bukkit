/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
/*     */ import com.avaje.ebean.config.ldap.LdapAttributeAdapter;
/*     */ import com.avaje.ebean.validation.factory.Validator;
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
/*     */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeEnum;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.persistence.FetchType;
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
/*     */ public class DeployBeanProperty
/*     */ {
/*     */   private static final int ID_ORDER = 1000000;
/*     */   private static final int UNIDIRECTIONAL_ORDER = 100000;
/*     */   private static final int AUDITCOLUMN_ORDER = -1000000;
/*     */   private static final int VERSIONCOLUMN_ORDER = -1000000;
/*     */   public static final String EXCLUDE_FROM_UPDATE_WHERE = "EXCLUDE_FROM_UPDATE_WHERE";
/*     */   public static final String EXCLUDE_FROM_DELETE_WHERE = "EXCLUDE_FROM_DELETE_WHERE";
/*     */   public static final String EXCLUDE_FROM_INSERT = "EXCLUDE_FROM_INSERT";
/*     */   public static final String EXCLUDE_FROM_UPDATE = "EXCLUDE_FROM_UPDATE";
/*     */   private boolean id;
/*     */   private boolean embedded;
/*     */   private boolean versionColumn;
/*     */   private boolean fetchEager;
/*     */   private boolean nullable;
/*     */   private boolean unique;
/*     */   private LdapAttributeAdapter ldapAttributeAdapter;
/*     */   private int dbLength;
/*     */   private int dbScale;
/*     */   private String dbColumnDefn;
/*     */   private boolean isTransient;
/*     */   private boolean localEncrypted;
/*     */   private boolean dbEncrypted;
/*     */   private DbEncryptFunction dbEncryptFunction;
/*     */   private int dbEncryptedType;
/*     */   private String dbBind;
/*     */   private boolean dbRead;
/*     */   private boolean dbInsertable;
/*     */   private boolean dbUpdateable;
/*     */   private DeployTableJoin secondaryTableJoin;
/*     */   private String secondaryTableJoinPrefix;
/*     */   private String secondaryTable;
/*     */   private Class<?> owningType;
/*     */   private boolean lob;
/*     */   private String name;
/*     */   private Field field;
/*     */   private Class<?> propertyType;
/*     */   private ScalarType<?> scalarType;
/*     */   private String dbColumn;
/*     */   private String sqlFormulaSelect;
/*     */   private String sqlFormulaJoin;
/*     */   private int dbType;
/*     */   private Object defaultValue;
/*     */   private HashMap<String, String> extraAttributeMap;
/*     */   private Method readMethod;
/*     */   private Method writeMethod;
/*     */   private BeanReflectGetter getter;
/*     */   private BeanReflectSetter setter;
/*     */   private GeneratedProperty generatedProperty;
/*     */   private List<Validator> validators;
/*     */   private final DeployBeanDescriptor<?> desc;
/*     */   private boolean undirectionalShadow;
/*     */   private int sortOrder;
/*     */   
/*     */   public DeployBeanProperty(DeployBeanDescriptor<?> desc, Class<?> propertyType, ScalarType<?> scalarType, ScalarTypeConverter<?, ?> typeConverter) {
/* 104 */     this.fetchEager = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     this.nullable = true;
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
/* 133 */     this.dbBind = "?";
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
/* 210 */     this.extraAttributeMap = new HashMap();
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
/* 231 */     this.validators = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.desc = desc;
/* 241 */     this.propertyType = propertyType;
/* 242 */     this.scalarType = wrapScalarType(propertyType, scalarType, typeConverter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> wrapScalarType(Class<?> propertyType, ScalarType<?> scalarType, ScalarTypeConverter<?, ?> typeConverter) {
/* 250 */     if (typeConverter == null) {
/* 251 */       return scalarType;
/*     */     }
/* 253 */     return new ScalarTypeWrapper(propertyType, scalarType, typeConverter);
/*     */   }
/*     */   
/*     */   public int getSortOverride() {
/* 257 */     if (this.field == null) {
/* 258 */       return 0;
/*     */     }
/* 260 */     if (this.field.getAnnotation(javax.persistence.Id.class) != null)
/* 261 */       return 1000000; 
/* 262 */     if (this.field.getAnnotation(javax.persistence.EmbeddedId.class) != null)
/* 263 */       return 1000000; 
/* 264 */     if (this.undirectionalShadow)
/* 265 */       return 100000; 
/* 266 */     if (this.field.getAnnotation(com.avaje.ebean.annotation.CreatedTimestamp.class) != null)
/* 267 */       return -1000000; 
/* 268 */     if (this.field.getAnnotation(com.avaje.ebean.annotation.UpdatedTimestamp.class) != null)
/* 269 */       return -1000000; 
/* 270 */     if (this.field.getAnnotation(javax.persistence.Version.class) != null) {
/* 271 */       return -1000000;
/*     */     }
/* 273 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public boolean isScalar() { return true; }
/*     */ 
/*     */ 
/*     */   
/* 284 */   public String getFullBeanName() { return this.desc.getFullName() + "." + this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNullablePrimitive() {
/* 294 */     if (this.nullable && this.propertyType.isPrimitive()) {
/* 295 */       return true;
/*     */     }
/* 297 */     return false;
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
/*     */   public int getDbLength() {
/* 309 */     if (this.dbLength == 0 && this.scalarType != null) {
/* 310 */       return this.scalarType.getLength();
/*     */     }
/*     */     
/* 313 */     return this.dbLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public int getSortOrder() { return this.sortOrder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 334 */   public boolean isUndirectionalShadow() { return this.undirectionalShadow; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 341 */   public void setUndirectionalShadow(boolean undirectionalShadow) { this.undirectionalShadow = undirectionalShadow; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 348 */   public boolean isLocalEncrypted() { return this.localEncrypted; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 355 */   public void setLocalEncrypted(boolean localEncrypted) { this.localEncrypted = localEncrypted; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 362 */   public void setDbLength(int dbLength) { this.dbLength = dbLength; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 369 */   public int getDbScale() { return this.dbScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public void setDbScale(int dbScale) { this.dbScale = dbScale; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public String getDbColumnDefn() { return this.dbColumnDefn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbColumnDefn(String dbColumnDefn) {
/* 390 */     if (dbColumnDefn == null || dbColumnDefn.trim().length() == 0) {
/* 391 */       this.dbColumnDefn = null;
/*     */     } else {
/* 393 */       this.dbColumnDefn = InternString.intern(dbColumnDefn);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getDbConstraintExpression() {
/* 398 */     if (this.scalarType instanceof ScalarTypeEnum) {
/*     */       
/* 400 */       ScalarTypeEnum etype = (ScalarTypeEnum)this.scalarType;
/*     */ 
/*     */       
/* 403 */       return "check (" + this.dbColumn + " in " + etype.getContraintInValues() + ")";
/*     */     } 
/* 405 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   public void addValidator(Validator validator) { this.validators.add(validator); }
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
/*     */   public boolean containsValidatorType(Class<?> type) {
/* 424 */     Iterator<Validator> it = this.validators.iterator();
/* 425 */     while (it.hasNext()) {
/* 426 */       Validator validator = (Validator)it.next();
/* 427 */       if (validator.getClass().equals(type)) {
/* 428 */         return true;
/*     */       }
/*     */     } 
/* 431 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 438 */   public Validator[] getValidators() { return (Validator[])this.validators.toArray(new Validator[this.validators.size()]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 446 */   public ScalarType<?> getScalarType() { return this.scalarType; }
/*     */ 
/*     */ 
/*     */   
/* 450 */   public void setScalarType(ScalarType<?> scalarType) { this.scalarType = scalarType; }
/*     */ 
/*     */ 
/*     */   
/* 454 */   public BeanReflectGetter getGetter() { return this.getter; }
/*     */ 
/*     */ 
/*     */   
/* 458 */   public BeanReflectSetter getSetter() { return this.setter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 465 */   public Method getReadMethod() { return this.readMethod; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 472 */   public Method getWriteMethod() { return this.writeMethod; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 479 */   public void setOwningType(Class<?> owningType) { this.owningType = owningType; }
/*     */ 
/*     */ 
/*     */   
/* 483 */   public Class<?> getOwningType() { return this.owningType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 490 */   public boolean isLocal() { return (this.owningType == null || this.owningType.equals(this.desc.getBeanType())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 497 */   public void setGetter(BeanReflectGetter getter) { this.getter = getter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 504 */   public void setSetter(BeanReflectSetter setter) { this.setter = setter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 518 */   public void setName(String name) { this.name = InternString.intern(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 525 */   public Field getField() { return this.field; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 532 */   public void setField(Field field) { this.field = field; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 540 */   public boolean isGenerated() { return (this.generatedProperty != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 547 */   public GeneratedProperty getGeneratedProperty() { return this.generatedProperty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 554 */   public void setGeneratedProperty(GeneratedProperty generatedValue) { this.generatedProperty = generatedValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 561 */   public boolean isNullable() { return this.nullable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 568 */   public void setNullable(boolean isNullable) { this.nullable = isNullable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 575 */   public boolean isUnique() { return this.unique; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 582 */   public void setUnique(boolean unique) { this.unique = unique; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 589 */   public LdapAttributeAdapter getLdapAttributeAdapter() { return this.ldapAttributeAdapter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 596 */   public void setLdapAttributeAdapter(LdapAttributeAdapter ldapAttributeAdapter) { this.ldapAttributeAdapter = ldapAttributeAdapter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 603 */   public boolean isVersionColumn() { return this.versionColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 610 */   public void setVersionColumn(boolean isVersionColumn) { this.versionColumn = isVersionColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 617 */   public boolean isFetchEager() { return this.fetchEager; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 624 */   public void setFetchType(FetchType fetchType) { this.fetchEager = FetchType.EAGER.equals(fetchType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 631 */   public String getSqlFormulaSelect() { return this.sqlFormulaSelect; }
/*     */ 
/*     */ 
/*     */   
/* 635 */   public String getSqlFormulaJoin() { return this.sqlFormulaJoin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSqlFormula(String formulaSelect, String formulaJoin) {
/* 642 */     this.sqlFormulaSelect = formulaSelect;
/* 643 */     this.sqlFormulaJoin = formulaJoin.equals("") ? null : formulaJoin;
/* 644 */     this.dbRead = true;
/* 645 */     this.dbInsertable = false;
/* 646 */     this.dbUpdateable = false;
/*     */   }
/*     */   
/*     */   public String getElPlaceHolder(BeanDescriptor.EntityType et) {
/* 650 */     if (this.sqlFormulaSelect != null)
/* 651 */       return this.sqlFormulaSelect; 
/* 652 */     if (BeanDescriptor.EntityType.LDAP.equals(et)) {
/* 653 */       return getDbColumn();
/*     */     }
/* 655 */     if (this.secondaryTableJoinPrefix != null) {
/* 656 */       return "${" + this.secondaryTableJoinPrefix + "}" + getDbColumn();
/*     */     }
/*     */     
/* 659 */     return "${}" + getDbColumn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDbColumn() {
/* 667 */     if (this.sqlFormulaSelect != null) {
/* 668 */       return this.sqlFormulaSelect;
/*     */     }
/* 670 */     return this.dbColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 677 */   public void setDbColumn(String dbColumn) { this.dbColumn = InternString.intern(dbColumn); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 684 */   public int getDbType() { return this.dbType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDbType(int dbType) {
/* 691 */     this.dbType = dbType;
/* 692 */     this.lob = isLobType(dbType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 700 */   public boolean isLob() { return this.lob; }
/*     */ 
/*     */   
/*     */   private boolean isLobType(int type) {
/* 704 */     switch (type) {
/*     */       case 2005:
/* 706 */         return true;
/*     */       case 2004:
/* 708 */         return true;
/*     */       case -4:
/* 710 */         return true;
/*     */       case -1:
/* 712 */         return true;
/*     */     } 
/*     */     
/* 715 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 723 */   public boolean isSecondaryTable() { return (this.secondaryTable != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 730 */   public String getSecondaryTable() { return this.secondaryTable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecondaryTable(String secondaryTable) {
/* 737 */     this.secondaryTable = secondaryTable;
/* 738 */     this.dbInsertable = false;
/* 739 */     this.dbUpdateable = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 746 */   public String getSecondaryTableJoinPrefix() { return this.secondaryTableJoinPrefix; }
/*     */ 
/*     */ 
/*     */   
/* 750 */   public DeployTableJoin getSecondaryTableJoin() { return this.secondaryTableJoin; }
/*     */ 
/*     */   
/*     */   public void setSecondaryTableJoin(DeployTableJoin secondaryTableJoin, String prefix) {
/* 754 */     this.secondaryTableJoin = secondaryTableJoin;
/* 755 */     this.secondaryTableJoinPrefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 763 */   public String getDbBind() { return this.dbBind; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 770 */   public void setDbBind(String dbBind) { this.dbBind = dbBind; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 777 */   public boolean isDbEncrypted() { return this.dbEncrypted; }
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
/* 788 */   public DbEncryptFunction getDbEncryptFunction() { return this.dbEncryptFunction; }
/*     */ 
/*     */   
/*     */   public void setDbEncryptFunction(DbEncryptFunction dbEncryptFunction, DbEncrypt dbEncrypt, int dbLen) {
/* 792 */     this.dbEncryptFunction = dbEncryptFunction;
/* 793 */     this.dbEncrypted = true;
/* 794 */     this.dbBind = dbEncryptFunction.getEncryptBindSql();
/*     */     
/* 796 */     this.dbEncryptedType = isLob() ? 2004 : dbEncrypt.getEncryptDbType();
/* 797 */     if (dbLen > 0) {
/* 798 */       setDbLength(dbLen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 807 */   public int getDbEncryptedType() { return this.dbEncryptedType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 814 */   public void setDbEncryptedType(int dbEncryptedType) { this.dbEncryptedType = dbEncryptedType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 821 */   public boolean isDbRead() { return this.dbRead; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 828 */   public void setDbRead(boolean isDBRead) { this.dbRead = isDBRead; }
/*     */ 
/*     */ 
/*     */   
/* 832 */   public boolean isDbInsertable() { return this.dbInsertable; }
/*     */ 
/*     */ 
/*     */   
/* 836 */   public void setDbInsertable(boolean insertable) { this.dbInsertable = insertable; }
/*     */ 
/*     */ 
/*     */   
/* 840 */   public boolean isDbUpdateable() { return this.dbUpdateable; }
/*     */ 
/*     */ 
/*     */   
/* 844 */   public void setDbUpdateable(boolean updateable) { this.dbUpdateable = updateable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 851 */   public boolean isTransient() { return this.isTransient; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 858 */   public void setTransient(boolean isTransient) { this.isTransient = isTransient; }
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
/* 869 */   public void setReadMethod(Method readMethod) { this.readMethod = readMethod; }
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
/* 880 */   public void setWriteMethod(Method writeMethod) { this.writeMethod = writeMethod; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 887 */   public Class<?> getPropertyType() { return this.propertyType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 894 */   public boolean isId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 901 */   public void setId(boolean id) { this.id = id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 909 */   public boolean isEmbedded() { return this.embedded; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 916 */   public void setEmbedded(boolean embedded) { this.embedded = embedded; }
/*     */ 
/*     */ 
/*     */   
/* 920 */   public Map<String, String> getExtraAttributeMap() { return this.extraAttributeMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 927 */   public String getExtraAttribute(String key) { return (String)this.extraAttributeMap.get(key); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 934 */   public void setExtraAttribute(String key, String value) { this.extraAttributeMap.put(key, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 941 */   public Object getDefaultValue() { return this.defaultValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 948 */   public void setDefaultValue(Object defaultValue) { this.defaultValue = defaultValue; }
/*     */ 
/*     */ 
/*     */   
/* 952 */   public String toString() { return this.desc.getFullName() + "." + this.name; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */