/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.config.EncryptDeploy;
/*     */ import com.avaje.ebean.config.EncryptDeployManager;
/*     */ import com.avaje.ebean.config.EncryptKeyManager;
/*     */ import com.avaje.ebean.config.Encryptor;
/*     */ import com.avaje.ebean.config.NamingConvention;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.TableName;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.validation.factory.Validator;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.DataEncryptSupport;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarTypeEnumStandard;
/*     */ import com.avaje.ebeaninternal.server.type.SimpleAesEncryptor;
/*     */ import com.avaje.ebeaninternal.server.type.TypeManager;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
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
/*     */ public class DeployUtil
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(DeployUtil.class.getName());
/*     */ 
/*     */   
/*     */   private static final int dbCLOBType = 2005;
/*     */ 
/*     */   
/*     */   private static final int dbBLOBType = 2004;
/*     */ 
/*     */   
/*     */   private final NamingConvention namingConvention;
/*     */ 
/*     */   
/*     */   private final TypeManager typeManager;
/*     */ 
/*     */   
/*     */   private final ValidatorFactoryManager validatorFactoryManager;
/*     */ 
/*     */   
/*     */   private final String manyToManyAlias;
/*     */ 
/*     */   
/*     */   private final DatabasePlatform dbPlatform;
/*     */ 
/*     */   
/*     */   private final EncryptDeployManager encryptDeployManager;
/*     */ 
/*     */   
/*     */   private final EncryptKeyManager encryptKeyManager;
/*     */   
/*     */   private final Encryptor bytesEncryptor;
/*     */ 
/*     */   
/*     */   public DeployUtil(TypeManager typeMgr, ServerConfig serverConfig) {
/*  86 */     this.typeManager = typeMgr;
/*  87 */     this.namingConvention = serverConfig.getNamingConvention();
/*  88 */     this.dbPlatform = serverConfig.getDatabasePlatform();
/*  89 */     this.encryptDeployManager = serverConfig.getEncryptDeployManager();
/*  90 */     this.encryptKeyManager = serverConfig.getEncryptKeyManager();
/*     */     
/*  92 */     Encryptor be = serverConfig.getEncryptor();
/*  93 */     this.bytesEncryptor = (be != null) ? be : new SimpleAesEncryptor();
/*     */ 
/*     */     
/*  96 */     this.manyToManyAlias = "zzzzzz";
/*     */     
/*  98 */     this.validatorFactoryManager = new ValidatorFactoryManager();
/*     */   }
/*     */ 
/*     */   
/* 102 */   public TypeManager getTypeManager() { return this.typeManager; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public DatabasePlatform getDbPlatform() { return this.dbPlatform; }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public NamingConvention getNamingConvention() { return this.namingConvention; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkEncryptKeyManagerDefined(String fullPropName) {
/* 117 */     if (this.encryptKeyManager == null) {
/* 118 */       String msg = "Using encryption on " + fullPropName + " but no EncryptKeyManager defined!";
/* 119 */       throw new PersistenceException(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EncryptDeploy getEncryptDeploy(TableName table, String column) {
/* 124 */     if (this.encryptDeployManager == null) {
/* 125 */       return EncryptDeploy.ANNOTATION;
/*     */     }
/* 127 */     return this.encryptDeployManager.getEncryptDeploy(table, column);
/*     */   }
/*     */ 
/*     */   
/* 131 */   public DataEncryptSupport createDataEncryptSupport(String table, String column) { return new DataEncryptSupport(this.encryptKeyManager, this.bytesEncryptor, table, column); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public String getManyToManyAlias() { return this.manyToManyAlias; }
/*     */ 
/*     */   
/*     */   public void createValidator(DeployBeanProperty prop, Annotation ann) {
/*     */     try {
/* 143 */       Validator validator = this.validatorFactoryManager.create(ann, prop.getPropertyType());
/* 144 */       if (validator != null) {
/* 145 */         prop.addValidator(validator);
/*     */       }
/* 147 */     } catch (Exception e) {
/* 148 */       String msg = "Error creating a validator on " + prop.getFullBeanName();
/* 149 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ScalarType<?> setEnumScalarType(Enumerated enumerated, DeployBeanProperty prop) {
/* 155 */     Class<?> enumType = prop.getPropertyType();
/* 156 */     if (!enumType.isEnum()) {
/* 157 */       throw new IllegalArgumentException("Class [" + enumType + "] is Not a Enum?");
/*     */     }
/* 159 */     ScalarType<?> scalarType = this.typeManager.getScalarType(enumType);
/* 160 */     if (scalarType == null) {
/*     */       
/* 162 */       scalarType = this.typeManager.createEnumScalarType(enumType);
/* 163 */       if (scalarType == null) {
/*     */         
/* 165 */         EnumType type = (enumerated != null) ? enumerated.value() : null;
/* 166 */         scalarType = createEnumScalarTypePerSpec(enumType, type, prop.getDbType());
/*     */       } 
/*     */       
/* 169 */       this.typeManager.add(scalarType);
/*     */     } 
/* 171 */     prop.setScalarType(scalarType);
/* 172 */     prop.setDbType(scalarType.getJdbcType());
/* 173 */     return scalarType;
/*     */   }
/*     */ 
/*     */   
/*     */   private ScalarType<?> createEnumScalarTypePerSpec(Class<?> enumType, EnumType type, int dbType) {
/* 178 */     if (type == null)
/*     */     {
/* 180 */       return new ScalarTypeEnumStandard.OrdinalEnum(enumType);
/*     */     }
/* 182 */     if (type == EnumType.ORDINAL) {
/* 183 */       return new ScalarTypeEnumStandard.OrdinalEnum(enumType);
/*     */     }
/*     */     
/* 186 */     return new ScalarTypeEnumStandard.StringEnum(enumType);
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
/*     */   public void setScalarType(DeployBeanProperty property) {
/* 199 */     if (property.getScalarType() != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 204 */     if (property instanceof com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 209 */     ScalarType<?> scalarType = getScalarType(property);
/* 210 */     if (scalarType != null) {
/*     */ 
/*     */       
/* 213 */       property.setDbType(scalarType.getJdbcType());
/* 214 */       property.setScalarType(scalarType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> getScalarType(DeployBeanProperty property) {
/* 222 */     Class<?> propType = property.getPropertyType();
/* 223 */     ScalarType<?> scalarType = this.typeManager.getScalarType(propType, property.getDbType());
/* 224 */     if (scalarType != null) {
/* 225 */       return scalarType;
/*     */     }
/*     */     
/* 228 */     String msg = property.getFullBeanName() + " has no ScalarType - type[" + propType.getName() + "]";
/* 229 */     if (!property.isTransient()) {
/* 230 */       throw new PersistenceException(msg);
/*     */     }
/*     */ 
/*     */     
/* 234 */     logger.finest("... transient property " + msg);
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLobType(DeployBeanProperty prop) {
/* 245 */     Class<?> type = prop.getPropertyType();
/*     */ 
/*     */     
/* 248 */     int lobType = isClobType(type) ? 2005 : 2004;
/*     */     
/* 250 */     ScalarType<?> scalarType = this.typeManager.getScalarType(type, lobType);
/* 251 */     if (scalarType == null)
/*     */     {
/* 253 */       throw new RuntimeException("No ScalarType for LOB type [" + type + "] [" + lobType + "]");
/*     */     }
/* 255 */     prop.setDbType(lobType);
/* 256 */     prop.setScalarType(scalarType);
/*     */   }
/*     */   
/*     */   public boolean isClobType(Class<?> type) {
/* 260 */     if (type.equals(String.class)) {
/* 261 */       return true;
/*     */     }
/* 263 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */