/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CreatedTimestamp;
/*     */ import com.avaje.ebean.annotation.EmbeddedColumns;
/*     */ import com.avaje.ebean.annotation.Encrypted;
/*     */ import com.avaje.ebean.annotation.Formula;
/*     */ import com.avaje.ebean.annotation.LdapAttribute;
/*     */ import com.avaje.ebean.annotation.LdapId;
/*     */ import com.avaje.ebean.annotation.UpdatedTimestamp;
/*     */ import com.avaje.ebean.config.EncryptDeploy;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncryptFunction;
/*     */ import com.avaje.ebean.config.dbplatform.IdType;
/*     */ import com.avaje.ebean.validation.Length;
/*     */ import com.avaje.ebean.validation.NotNull;
/*     */ import com.avaje.ebean.validation.Pattern;
/*     */ import com.avaje.ebean.validation.Patterns;
/*     */ import com.avaje.ebean.validation.ValidatorMeta;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedPropertyFactory;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundType;
/*     */ import com.avaje.ebeaninternal.server.type.DataEncryptSupport;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBase;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeBytesEncrypted;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeEncryptedWrapper;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeLdapBoolean;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeLdapDate;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeLdapTimestamp;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.EmbeddedId;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.persistence.SequenceGenerator;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ import javax.persistence.Transient;
/*     */ import javax.persistence.Version;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationFields
/*     */   extends AnnotationParser
/*     */ {
/*  89 */   private FetchType defaultLobFetchType = FetchType.LAZY;
/*     */   
/*  91 */   private GeneratedPropertyFactory generatedPropFactory = new GeneratedPropertyFactory();
/*     */   
/*     */   public AnnotationFields(DeployBeanInfo<?> info) {
/*  94 */     super(info);
/*     */     
/*  96 */     if (GlobalProperties.getBoolean("ebean.lobEagerFetch", false)) {
/*  97 */       this.defaultLobFetchType = FetchType.EAGER;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() {
/* 106 */     Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
/* 107 */     while (it.hasNext()) {
/* 108 */       DeployBeanProperty prop = (DeployBeanProperty)it.next();
/* 109 */       if (prop instanceof com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc) {
/* 110 */         readAssocOne(prop);
/*     */       } else {
/* 112 */         readField(prop);
/*     */       } 
/*     */       
/* 115 */       readValidations(prop);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readAssocOne(DeployBeanProperty prop) {
/* 124 */     Id id = (Id)get(prop, Id.class);
/* 125 */     if (id != null) {
/* 126 */       prop.setId(true);
/* 127 */       prop.setNullable(false);
/*     */     } 
/*     */     
/* 130 */     EmbeddedId embeddedId = (EmbeddedId)get(prop, EmbeddedId.class);
/* 131 */     if (embeddedId != null) {
/* 132 */       prop.setId(true);
/* 133 */       prop.setNullable(false);
/* 134 */       prop.setEmbedded(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readField(DeployBeanProperty prop) {
/* 142 */     boolean isEnum = prop.getPropertyType().isEnum();
/* 143 */     Enumerated enumerated = (Enumerated)get(prop, Enumerated.class);
/* 144 */     if (isEnum || enumerated != null) {
/* 145 */       this.util.setEnumScalarType(enumerated, prop);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 150 */     prop.setDbRead(true);
/* 151 */     prop.setDbInsertable(true);
/* 152 */     prop.setDbUpdateable(true);
/*     */     
/* 154 */     Column column = (Column)get(prop, Column.class);
/* 155 */     if (column != null) {
/* 156 */       readColumn(column, prop);
/*     */     }
/* 158 */     LdapAttribute ldapAttribute = (LdapAttribute)get(prop, LdapAttribute.class);
/* 159 */     if (ldapAttribute != null)
/*     */     {
/* 161 */       readLdapAttribute(ldapAttribute, prop);
/*     */     }
/*     */     
/* 164 */     if (prop.getDbColumn() == null) {
/* 165 */       if (BeanDescriptor.EntityType.LDAP.equals(this.descriptor.getEntityType())) {
/*     */         
/* 167 */         prop.setDbColumn(prop.getName());
/*     */       }
/*     */       else {
/*     */         
/* 171 */         String dbColumn = this.namingConvention.getColumnFromProperty(this.beanType, prop.getName());
/* 172 */         prop.setDbColumn(dbColumn);
/*     */       } 
/*     */     }
/*     */     
/* 176 */     GeneratedValue gen = (GeneratedValue)get(prop, GeneratedValue.class);
/* 177 */     if (gen != null) {
/* 178 */       readGenValue(gen, prop);
/*     */     }
/*     */     
/* 181 */     Id id = (Id)get(prop, Id.class);
/* 182 */     if (id != null) {
/* 183 */       readId(id, prop);
/*     */     }
/* 185 */     LdapId ldapId = (LdapId)get(prop, LdapId.class);
/* 186 */     if (ldapId != null) {
/* 187 */       prop.setId(true);
/* 188 */       prop.setNullable(false);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     Lob lob = (Lob)get(prop, Lob.class);
/* 195 */     Temporal temporal = (Temporal)get(prop, Temporal.class);
/* 196 */     if (temporal != null) {
/* 197 */       readTemporal(temporal, prop);
/*     */     }
/* 199 */     else if (lob != null) {
/* 200 */       this.util.setLobType(prop);
/*     */     } 
/*     */     
/* 203 */     Formula formula = (Formula)get(prop, Formula.class);
/* 204 */     if (formula != null) {
/* 205 */       prop.setSqlFormula(formula.select(), formula.join());
/*     */     }
/*     */     
/* 208 */     Version version = (Version)get(prop, Version.class);
/* 209 */     if (version != null) {
/*     */       
/* 211 */       prop.setVersionColumn(true);
/* 212 */       this.generatedPropFactory.setVersion(prop);
/*     */     } 
/*     */     
/* 215 */     Basic basic = (Basic)get(prop, Basic.class);
/* 216 */     if (basic != null) {
/* 217 */       prop.setFetchType(basic.fetch());
/* 218 */       if (!basic.optional()) {
/* 219 */         prop.setNullable(false);
/*     */       }
/* 221 */     } else if (prop.isLob()) {
/*     */       
/* 223 */       prop.setFetchType(this.defaultLobFetchType);
/*     */     } 
/*     */     
/* 226 */     CreatedTimestamp ct = (CreatedTimestamp)get(prop, CreatedTimestamp.class);
/* 227 */     if (ct != null) {
/* 228 */       this.generatedPropFactory.setInsertTimestamp(prop);
/*     */     }
/*     */     
/* 231 */     UpdatedTimestamp ut = (UpdatedTimestamp)get(prop, UpdatedTimestamp.class);
/* 232 */     if (ut != null) {
/* 233 */       this.generatedPropFactory.setUpdateTimestamp(prop);
/*     */     }
/*     */     
/* 236 */     NotNull notNull = (NotNull)get(prop, NotNull.class);
/* 237 */     if (notNull != null)
/*     */     {
/* 239 */       prop.setNullable(false);
/*     */     }
/*     */     
/* 242 */     Length length = (Length)get(prop, Length.class);
/* 243 */     if (length != null && 
/* 244 */       length.max() < Integer.MAX_VALUE)
/*     */     {
/* 246 */       prop.setDbLength(length.max());
/*     */     }
/*     */ 
/*     */     
/* 250 */     EmbeddedColumns columns = (EmbeddedColumns)get(prop, EmbeddedColumns.class);
/* 251 */     if (columns != null) {
/* 252 */       if (prop instanceof DeployBeanPropertyCompound) {
/* 253 */         DeployBeanPropertyCompound p = (DeployBeanPropertyCompound)prop;
/*     */ 
/*     */         
/* 256 */         String propColumns = columns.columns();
/* 257 */         Map<String, String> propMap = StringHelper.delimitedToMap(propColumns, ",", "=");
/*     */         
/* 259 */         p.getDeployEmbedded().putAll(propMap);
/*     */         
/* 261 */         CtCompoundType<?> compoundType = p.getCompoundType();
/* 262 */         if (compoundType == null) {
/* 263 */           throw new RuntimeException("No registered CtCompoundType for " + p.getPropertyType());
/*     */         }
/*     */       } else {
/*     */         
/* 267 */         throw new RuntimeException("Can't use EmbeddedColumns on ScalarType " + prop.getFullBeanName());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 272 */     Transient t = (Transient)get(prop, Transient.class);
/* 273 */     if (t != null) {
/*     */       
/* 275 */       prop.setDbRead(false);
/* 276 */       prop.setDbInsertable(false);
/* 277 */       prop.setDbUpdateable(false);
/* 278 */       prop.setTransient(true);
/*     */     } 
/*     */     
/* 281 */     if (!prop.isTransient()) {
/*     */       
/* 283 */       EncryptDeploy encryptDeploy = this.util.getEncryptDeploy(this.info.getDescriptor().getBaseTableFull(), prop.getDbColumn());
/* 284 */       if (encryptDeploy == null || encryptDeploy.getMode().equals(EncryptDeploy.Mode.MODE_ANNOTATION)) {
/* 285 */         Encrypted encrypted = (Encrypted)get(prop, Encrypted.class);
/* 286 */         if (encrypted != null) {
/* 287 */           setEncryption(prop, encrypted.dbEncryption(), encrypted.dbLength());
/*     */         }
/* 289 */       } else if (EncryptDeploy.Mode.MODE_ENCRYPT.equals(encryptDeploy.getMode())) {
/* 290 */         setEncryption(prop, encryptDeploy.isDbEncrypt(), encryptDeploy.getDbLength());
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     if (BeanDescriptor.EntityType.LDAP.equals(this.descriptor.getEntityType())) {
/* 295 */       adjustTypesForLdap(prop);
/*     */     }
/*     */   }
/*     */   
/* 299 */   private static final ScalarTypeLdapBoolean LDAP_BOOLEAN_SCALARTYPE = new ScalarTypeLdapBoolean();
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustTypesForLdap(DeployBeanProperty prop) {
/* 304 */     Class<?> pt = prop.getPropertyType();
/* 305 */     if (boolean.class.equals(pt) || Boolean.class.equals(pt)) {
/* 306 */       prop.setScalarType(LDAP_BOOLEAN_SCALARTYPE);
/*     */     } else {
/*     */       
/* 309 */       ScalarType<?> sqlScalarType = prop.getScalarType();
/* 310 */       int sqlType = sqlScalarType.getJdbcType();
/* 311 */       if (sqlType == 93) {
/*     */         
/* 313 */         prop.setScalarType(new ScalarTypeLdapTimestamp(sqlScalarType));
/*     */       }
/* 315 */       else if (sqlType == 91) {
/*     */         
/* 317 */         prop.setScalarType(new ScalarTypeLdapDate(sqlScalarType));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setEncryption(DeployBeanProperty prop, boolean dbEncString, int dbLen) {
/* 327 */     this.util.checkEncryptKeyManagerDefined(prop.getFullBeanName());
/*     */     
/* 329 */     ScalarType<?> st = prop.getScalarType();
/* 330 */     if (byte[].class.equals(st.getType())) {
/*     */ 
/*     */ 
/*     */       
/* 334 */       ScalarTypeBytesBase baseType = (ScalarTypeBytesBase)st;
/* 335 */       DataEncryptSupport support = createDataEncryptSupport(prop);
/* 336 */       ScalarTypeBytesEncrypted encryptedScalarType = new ScalarTypeBytesEncrypted(baseType, support);
/* 337 */       prop.setScalarType(encryptedScalarType);
/* 338 */       prop.setLocalEncrypted(true);
/*     */       
/*     */       return;
/*     */     } 
/* 342 */     if (dbEncString) {
/*     */       
/* 344 */       DbEncrypt dbEncrypt = this.util.getDbPlatform().getDbEncrypt();
/*     */       
/* 346 */       if (dbEncrypt != null) {
/*     */         
/* 348 */         int jdbcType = prop.getScalarType().getJdbcType();
/* 349 */         DbEncryptFunction dbEncryptFunction = dbEncrypt.getDbEncryptFunction(jdbcType);
/* 350 */         if (dbEncryptFunction != null) {
/*     */           
/* 352 */           prop.setDbEncryptFunction(dbEncryptFunction, dbEncrypt, dbLen);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 358 */     prop.setScalarType(createScalarType(prop, st));
/* 359 */     prop.setLocalEncrypted(true);
/* 360 */     if (dbLen > 0) {
/* 361 */       prop.setDbLength(dbLen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarTypeEncryptedWrapper<?> createScalarType(DeployBeanProperty prop, ScalarType<?> st) {
/* 369 */     DataEncryptSupport support = createDataEncryptSupport(prop);
/* 370 */     ScalarTypeBytesBase byteType = getDbEncryptType(prop);
/*     */     
/* 372 */     return new ScalarTypeEncryptedWrapper(st, byteType, support);
/*     */   }
/*     */   
/*     */   private ScalarTypeBytesBase getDbEncryptType(DeployBeanProperty prop) {
/* 376 */     int dbType = prop.isLob() ? 2004 : -3;
/* 377 */     return (ScalarTypeBytesBase)this.util.getTypeManager().getScalarType(dbType);
/*     */   }
/*     */ 
/*     */   
/*     */   private DataEncryptSupport createDataEncryptSupport(DeployBeanProperty prop) {
/* 382 */     String table = this.info.getDescriptor().getBaseTable();
/* 383 */     String column = prop.getDbColumn();
/*     */     
/* 385 */     return this.util.createDataEncryptSupport(table, column);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readId(Id id, DeployBeanProperty prop) {
/* 391 */     prop.setId(true);
/* 392 */     prop.setNullable(false);
/*     */     
/* 394 */     if (prop.getPropertyType().equals(java.util.UUID.class))
/*     */     {
/* 396 */       if (this.descriptor.getIdGeneratorName() == null) {
/*     */ 
/*     */         
/* 399 */         this.descriptor.setIdGeneratorName("auto.uuid");
/* 400 */         this.descriptor.setIdType(IdType.GENERATOR);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void readGenValue(GeneratedValue gen, DeployBeanProperty prop) {
/* 407 */     String genName = gen.generator();
/*     */     
/* 409 */     SequenceGenerator sequenceGenerator = (SequenceGenerator)find(prop, SequenceGenerator.class);
/* 410 */     if (sequenceGenerator != null && 
/* 411 */       sequenceGenerator.name().equals(genName)) {
/* 412 */       genName = sequenceGenerator.sequenceName();
/*     */     }
/*     */ 
/*     */     
/* 416 */     GenerationType strategy = gen.strategy();
/*     */     
/* 418 */     if (strategy == GenerationType.IDENTITY) {
/* 419 */       this.descriptor.setIdType(IdType.IDENTITY);
/*     */     }
/* 421 */     else if (strategy == GenerationType.SEQUENCE) {
/* 422 */       this.descriptor.setIdType(IdType.SEQUENCE);
/* 423 */       if (genName != null && genName.length() > 0) {
/* 424 */         this.descriptor.setIdGeneratorName(genName);
/*     */       }
/*     */     }
/* 427 */     else if (strategy == GenerationType.AUTO && 
/* 428 */       prop.getPropertyType().equals(java.util.UUID.class)) {
/* 429 */       this.descriptor.setIdGeneratorName("auto.uuid");
/* 430 */       this.descriptor.setIdType(IdType.GENERATOR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readTemporal(Temporal temporal, DeployBeanProperty prop) {
/* 440 */     TemporalType type = temporal.value();
/* 441 */     if (type.equals(TemporalType.DATE)) {
/* 442 */       prop.setDbType(91);
/*     */     }
/* 444 */     else if (type.equals(TemporalType.TIMESTAMP)) {
/* 445 */       prop.setDbType(93);
/*     */     }
/* 447 */     else if (type.equals(TemporalType.TIME)) {
/* 448 */       prop.setDbType(92);
/*     */     } else {
/*     */       
/* 451 */       throw new PersistenceException("Unhandled type " + type);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readColumn(Column columnAnn, DeployBeanProperty prop) {
/* 458 */     if (!isEmpty(columnAnn.name())) {
/* 459 */       String dbColumn = this.databasePlatform.convertQuotedIdentifiers(columnAnn.name());
/* 460 */       prop.setDbColumn(dbColumn);
/*     */     } 
/*     */     
/* 463 */     prop.setDbInsertable(columnAnn.insertable());
/* 464 */     prop.setDbUpdateable(columnAnn.updatable());
/* 465 */     prop.setNullable(columnAnn.nullable());
/* 466 */     prop.setUnique(columnAnn.unique());
/* 467 */     if (columnAnn.precision() > 0) {
/* 468 */       prop.setDbLength(columnAnn.precision());
/* 469 */     } else if (columnAnn.length() != 255) {
/*     */       
/* 471 */       prop.setDbLength(columnAnn.length());
/*     */     } 
/* 473 */     prop.setDbScale(columnAnn.scale());
/* 474 */     prop.setDbColumnDefn(columnAnn.columnDefinition());
/*     */     
/* 476 */     String baseTable = this.descriptor.getBaseTable();
/* 477 */     String tableName = columnAnn.table();
/* 478 */     if (!tableName.equals("") && !tableName.equalsIgnoreCase(baseTable))
/*     */     {
/*     */ 
/*     */       
/* 482 */       prop.setSecondaryTable(tableName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readValidations(DeployBeanProperty prop) {
/* 492 */     Field field = prop.getField();
/* 493 */     if (field != null) {
/* 494 */       Annotation[] fieldAnnotations = field.getAnnotations();
/* 495 */       for (int i = 0; i < fieldAnnotations.length; i++) {
/* 496 */         readValidations(prop, fieldAnnotations[i]);
/*     */       }
/*     */     } 
/*     */     
/* 500 */     Method readMethod = prop.getReadMethod();
/* 501 */     if (readMethod != null) {
/* 502 */       Annotation[] methAnnotations = readMethod.getAnnotations();
/* 503 */       for (int i = 0; i < methAnnotations.length; i++) {
/* 504 */         readValidations(prop, methAnnotations[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readValidations(DeployBeanProperty prop, Annotation ann) {
/* 510 */     Class<?> type = ann.annotationType();
/* 511 */     if (type.equals(Patterns.class)) {
/*     */       
/* 513 */       Patterns patterns = (Patterns)ann;
/* 514 */       Pattern[] patternsArray = patterns.patterns();
/* 515 */       for (int i = 0; i < patternsArray.length; i++) {
/* 516 */         this.util.createValidator(prop, patternsArray[i]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 521 */       ValidatorMeta meta = (ValidatorMeta)type.getAnnotation(ValidatorMeta.class);
/* 522 */       if (meta != null)
/* 523 */         this.util.createValidator(prop, ann); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationFields.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */