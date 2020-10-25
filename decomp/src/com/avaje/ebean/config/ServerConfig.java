/*      */ package com.avaje.ebean.config;
/*      */ 
/*      */ import com.avaje.ebean.LogLevel;
/*      */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*      */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*      */ import com.avaje.ebean.config.ldap.LdapConfig;
/*      */ import com.avaje.ebean.config.ldap.LdapContextFactory;
/*      */ import com.avaje.ebean.config.lucene.LuceneConfig;
/*      */ import com.avaje.ebean.event.BeanPersistController;
/*      */ import com.avaje.ebean.event.BeanPersistListener;
/*      */ import com.avaje.ebean.event.BeanQueryAdapter;
/*      */ import com.avaje.ebeaninternal.api.ClassUtil;
/*      */ import com.avaje.ebeaninternal.server.core.DetectLucene;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ServerConfig
/*      */ {
/*      */   private static final int DEFAULT_QUERY_BATCH_SIZE = 100;
/*      */   private String name;
/*      */   private String resourceDirectory;
/*      */   private int enhanceLogLevel;
/*      */   private boolean register = true;
/*      */   private boolean defaultServer;
/*      */   private boolean validateOnSave = true;
/*  126 */   private List<Class<?>> classes = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   private List<String> packages = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   private List<String> searchJars = new ArrayList();
/*      */ 
/*      */   
/*  142 */   private AutofetchConfig autofetchConfig = new AutofetchConfig();
/*      */ 
/*      */ 
/*      */   
/*      */   private String databasePlatformName;
/*      */ 
/*      */ 
/*      */   
/*      */   private DatabasePlatform databasePlatform;
/*      */ 
/*      */ 
/*      */   
/*  154 */   private int databaseSequenceBatchSize = 20;
/*      */   
/*      */   private boolean persistBatching;
/*      */   
/*  158 */   private int persistBatchSize = 20;
/*      */ 
/*      */   
/*  161 */   private int lazyLoadBatchSize = 1;
/*      */ 
/*      */   
/*  164 */   private int queryBatchSize = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ddlGenerate;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ddlRun;
/*      */ 
/*      */   
/*      */   private boolean debugSql;
/*      */ 
/*      */   
/*      */   private boolean debugLazyLoad;
/*      */ 
/*      */   
/*      */   private boolean useJtaTransactionManager;
/*      */ 
/*      */   
/*      */   private ExternalTransactionManager externalTransactionManager;
/*      */ 
/*      */   
/*      */   private boolean loggingToJavaLogger;
/*      */ 
/*      */   
/*  190 */   private String loggingDirectory = "logs";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  195 */   private LogLevel loggingLevel = LogLevel.NONE;
/*      */ 
/*      */ 
/*      */   
/*      */   private PstmtDelegate pstmtDelegate;
/*      */ 
/*      */ 
/*      */   
/*      */   private DataSource dataSource;
/*      */ 
/*      */ 
/*      */   
/*  207 */   private DataSourceConfig dataSourceConfig = new DataSourceConfig();
/*      */ 
/*      */   
/*      */   private String dataSourceJndiName;
/*      */ 
/*      */   
/*      */   private String databaseBooleanTrue;
/*      */ 
/*      */   
/*      */   private String databaseBooleanFalse;
/*      */ 
/*      */   
/*      */   private NamingConvention namingConvention;
/*      */ 
/*      */   
/*      */   private boolean updateChangesOnly = true;
/*      */   
/*  224 */   private List<BeanPersistController> persistControllers = new ArrayList();
/*  225 */   private List<BeanPersistListener<?>> persistListeners = new ArrayList();
/*  226 */   private List<BeanQueryAdapter> queryAdapters = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EncryptKeyManager encryptKeyManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EncryptDeployManager encryptDeployManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private Encryptor encryptor;
/*      */ 
/*      */ 
/*      */   
/*      */   private DbEncrypt dbEncrypt;
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapConfig ldapConfig;
/*      */ 
/*      */ 
/*      */   
/*      */   private LuceneConfig luceneConfig;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean vanillaMode;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean vanillaRefMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  265 */   public String getName() { return this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  272 */   public void setName(String name) { this.name = name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  283 */   public boolean isRegister() { return this.register; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  294 */   public void setRegister(boolean register) { this.register = register; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  305 */   public boolean isDefaultServer() { return this.defaultServer; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  316 */   public void setDefaultServer(boolean defaultServer) { this.defaultServer = defaultServer; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  329 */   public boolean isPersistBatching() { return this.persistBatching; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  338 */   public boolean isUsePersistBatching() { return this.persistBatching; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  351 */   public void setPersistBatching(boolean persistBatching) { this.persistBatching = persistBatching; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  360 */   public void setUsePersistBatching(boolean persistBatching) { this.persistBatching = persistBatching; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  367 */   public int getPersistBatchSize() { return this.persistBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  374 */   public void setPersistBatchSize(int persistBatchSize) { this.persistBatchSize = persistBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  381 */   public int getLazyLoadBatchSize() { return this.lazyLoadBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  390 */   public int getQueryBatchSize() { return this.queryBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  399 */   public void setQueryBatchSize(int queryBatchSize) { this.queryBatchSize = queryBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  417 */   public void setLazyLoadBatchSize(int lazyLoadBatchSize) { this.lazyLoadBatchSize = lazyLoadBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  429 */   public void setDatabaseSequenceBatchSize(int databaseSequenceBatchSize) { this.databaseSequenceBatchSize = databaseSequenceBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  436 */   public boolean isUseJtaTransactionManager() { return this.useJtaTransactionManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  443 */   public void setUseJtaTransactionManager(boolean useJtaTransactionManager) { this.useJtaTransactionManager = useJtaTransactionManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  450 */   public ExternalTransactionManager getExternalTransactionManager() { return this.externalTransactionManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  457 */   public void setExternalTransactionManager(ExternalTransactionManager externalTransactionManager) { this.externalTransactionManager = externalTransactionManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  469 */   public boolean isVanillaMode() { return this.vanillaMode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  488 */   public void setVanillaMode(boolean vanillaMode) { this.vanillaMode = vanillaMode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  499 */   public boolean isVanillaRefMode() { return this.vanillaRefMode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  508 */   public void setVanillaRefMode(boolean vanillaRefMode) { this.vanillaRefMode = vanillaRefMode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  515 */   public boolean isValidateOnSave() { return this.validateOnSave; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  522 */   public void setValidateOnSave(boolean validateOnSave) { this.validateOnSave = validateOnSave; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  529 */   public int getEnhanceLogLevel() { return this.enhanceLogLevel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  536 */   public void setEnhanceLogLevel(int enhanceLogLevel) { this.enhanceLogLevel = enhanceLogLevel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  546 */   public NamingConvention getNamingConvention() { return this.namingConvention; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  556 */   public void setNamingConvention(NamingConvention namingConvention) { this.namingConvention = namingConvention; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  563 */   public AutofetchConfig getAutofetchConfig() { return this.autofetchConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  570 */   public void setAutofetchConfig(AutofetchConfig autofetchConfig) { this.autofetchConfig = autofetchConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  577 */   public PstmtDelegate getPstmtDelegate() { return this.pstmtDelegate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  588 */   public void setPstmtDelegate(PstmtDelegate pstmtDelegate) { this.pstmtDelegate = pstmtDelegate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  595 */   public DataSource getDataSource() { return this.dataSource; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  602 */   public void setDataSource(DataSource dataSource) { this.dataSource = dataSource; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  610 */   public DataSourceConfig getDataSourceConfig() { return this.dataSourceConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  618 */   public void setDataSourceConfig(DataSourceConfig dataSourceConfig) { this.dataSourceConfig = dataSourceConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  625 */   public String getDataSourceJndiName() { return this.dataSourceJndiName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  637 */   public void setDataSourceJndiName(String dataSourceJndiName) { this.dataSourceJndiName = dataSourceJndiName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  650 */   public String getDatabaseBooleanTrue() { return this.databaseBooleanTrue; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  663 */   public void setDatabaseBooleanTrue(String databaseTrue) { this.databaseBooleanTrue = databaseTrue; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  676 */   public String getDatabaseBooleanFalse() { return this.databaseBooleanFalse; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  689 */   public void setDatabaseBooleanFalse(String databaseFalse) { this.databaseBooleanFalse = databaseFalse; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  696 */   public int getDatabaseSequenceBatchSize() { return this.databaseSequenceBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  715 */   public void setDatabaseSequenceBatch(int databaseSequenceBatchSize) { this.databaseSequenceBatchSize = databaseSequenceBatchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  726 */   public String getDatabasePlatformName() { return this.databasePlatformName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  748 */   public void setDatabasePlatformName(String databasePlatformName) { this.databasePlatformName = databasePlatformName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  755 */   public DatabasePlatform getDatabasePlatform() { return this.databasePlatform; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  766 */   public void setDatabasePlatform(DatabasePlatform databasePlatform) { this.databasePlatform = databasePlatform; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  773 */   public EncryptKeyManager getEncryptKeyManager() { return this.encryptKeyManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  792 */   public void setEncryptKeyManager(EncryptKeyManager encryptKeyManager) { this.encryptKeyManager = encryptKeyManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  803 */   public EncryptDeployManager getEncryptDeployManager() { return this.encryptDeployManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  814 */   public void setEncryptDeployManager(EncryptDeployManager encryptDeployManager) { this.encryptDeployManager = encryptDeployManager; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  822 */   public Encryptor getEncryptor() { return this.encryptor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  834 */   public void setEncryptor(Encryptor encryptor) { this.encryptor = encryptor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  845 */   public DbEncrypt getDbEncrypt() { return this.dbEncrypt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  856 */   public void setDbEncrypt(DbEncrypt dbEncrypt) { this.dbEncrypt = dbEncrypt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  867 */   public boolean isDebugSql() { return this.debugSql; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  878 */   public void setDebugSql(boolean debugSql) { this.debugSql = debugSql; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  885 */   public boolean isDebugLazyLoad() { return this.debugLazyLoad; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  892 */   public void setDebugLazyLoad(boolean debugLazyLoad) { this.debugLazyLoad = debugLazyLoad; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  902 */   public LogLevel getLoggingLevel() { return this.loggingLevel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  912 */   public void setLoggingLevel(LogLevel logLevel) { this.loggingLevel = logLevel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  919 */   public String getLoggingDirectory() { return this.loggingDirectory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  927 */   public String getLoggingDirectoryWithEval() { return GlobalProperties.evaluateExpressions(this.loggingDirectory); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  948 */   public void setLoggingDirectory(String loggingDirectory) { this.loggingDirectory = loggingDirectory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  960 */   public boolean isLoggingToJavaLogger() { return this.loggingToJavaLogger; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  969 */   public void setLoggingToJavaLogger(boolean transactionLogToJavaLogger) { this.loggingToJavaLogger = transactionLogToJavaLogger; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  978 */   public boolean isUseJuliTransactionLogger() { return isLoggingToJavaLogger(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  987 */   public void setUseJuliTransactionLogger(boolean transactionLogToJavaLogger) { setLoggingToJavaLogger(transactionLogToJavaLogger); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  994 */   public void setDdlGenerate(boolean ddlGenerate) { this.ddlGenerate = ddlGenerate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1001 */   public void setDdlRun(boolean ddlRun) { this.ddlRun = ddlRun; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1008 */   public boolean isDdlGenerate() { return this.ddlGenerate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1015 */   public boolean isDdlRun() { return this.ddlRun; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1022 */   public LdapConfig getLdapConfig() { return this.ldapConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1029 */   public void setLdapConfig(LdapConfig ldapConfig) { this.ldapConfig = ldapConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1036 */   public LuceneConfig getLuceneConfig() { return this.luceneConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1043 */   public void setLuceneConfig(LuceneConfig luceneConfig) { this.luceneConfig = luceneConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addClass(Class<?> cls) {
/* 1066 */     if (this.classes == null) {
/* 1067 */       this.classes = new ArrayList();
/*      */     }
/* 1069 */     this.classes.add(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPackage(String packageName) {
/* 1079 */     if (this.packages == null) {
/* 1080 */       this.packages = new ArrayList();
/*      */     }
/* 1082 */     this.packages.add(packageName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1092 */   public List<String> getPackages() { return this.packages; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1102 */   public void setPackages(List<String> packages) { this.packages = packages; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addJar(String jarName) {
/* 1123 */     if (this.searchJars == null) {
/* 1124 */       this.searchJars = new ArrayList();
/*      */     }
/* 1126 */     this.searchJars.add(jarName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1136 */   public List<String> getJars() { return this.searchJars; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1146 */   public void setJars(List<String> searchJars) { this.searchJars = searchJars; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1161 */   public void setClasses(List<Class<?>> classes) { this.classes = classes; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1169 */   public List<Class<?>> getClasses() { return this.classes; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1176 */   public boolean isUpdateChangesOnly() { return this.updateChangesOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1183 */   public void setUpdateChangesOnly(boolean updateChangesOnly) { this.updateChangesOnly = updateChangesOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1190 */   public String getResourceDirectory() { return this.resourceDirectory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1197 */   public void setResourceDirectory(String resourceDirectory) { this.resourceDirectory = resourceDirectory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1208 */   public void add(BeanQueryAdapter beanQueryAdapter) { this.queryAdapters.add(beanQueryAdapter); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1215 */   public List<BeanQueryAdapter> getQueryAdapters() { return this.queryAdapters; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1226 */   public void setQueryAdapters(List<BeanQueryAdapter> queryAdapters) { this.queryAdapters = queryAdapters; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1237 */   public void add(BeanPersistController beanPersistController) { this.persistControllers.add(beanPersistController); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1244 */   public List<BeanPersistController> getPersistControllers() { return this.persistControllers; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1255 */   public void setPersistControllers(List<BeanPersistController> persistControllers) { this.persistControllers = persistControllers; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1266 */   public void add(BeanPersistListener<?> beanPersistListener) { this.persistListeners.add(beanPersistListener); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1273 */   public List<BeanPersistListener<?>> getPersistListeners() { return this.persistListeners; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1284 */   public void setPersistListeners(List<BeanPersistListener<?>> persistListeners) { this.persistListeners = persistListeners; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadFromProperties() {
/* 1291 */     ConfigPropertyMap p = new ConfigPropertyMap(this.name);
/* 1292 */     loadSettings(p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1299 */   public GlobalProperties.PropertySource getPropertySource() { return GlobalProperties.getPropertySource(this.name); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProperty(String propertyName, String defaultValue) {
/* 1306 */     GlobalProperties.PropertySource p = new ConfigPropertyMap(this.name);
/* 1307 */     return p.get(propertyName, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1314 */   public String getProperty(String propertyName) { return getProperty(propertyName, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T createInstance(GlobalProperties.PropertySource p, Class<T> type, String key) {
/* 1320 */     String classname = p.get(key, null);
/* 1321 */     if (classname == null) {
/* 1322 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1326 */       Class<?> cls = ClassUtil.forName(classname, getClass());
/* 1327 */       return (T)cls.newInstance();
/* 1328 */     } catch (Exception e) {
/* 1329 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadSettings(ConfigPropertyMap p) {
/* 1338 */     if (this.autofetchConfig == null) {
/* 1339 */       this.autofetchConfig = new AutofetchConfig();
/*      */     }
/* 1341 */     this.autofetchConfig.loadSettings(p);
/* 1342 */     if (this.dataSourceConfig == null) {
/* 1343 */       this.dataSourceConfig = new DataSourceConfig();
/*      */     }
/* 1345 */     this.dataSourceConfig.loadSettings(p.getServerName());
/*      */     
/* 1347 */     if (this.ldapConfig == null) {
/* 1348 */       LdapContextFactory ctxFact = (LdapContextFactory)createInstance(p, LdapContextFactory.class, "ldapContextFactory");
/* 1349 */       if (ctxFact != null) {
/* 1350 */         this.ldapConfig = new LdapConfig();
/* 1351 */         this.ldapConfig.setContextFactory(ctxFact);
/* 1352 */         this.ldapConfig.setVanillaMode(p.getBoolean("ldapVanillaMode", false));
/*      */       } 
/*      */     } 
/* 1355 */     if (this.luceneConfig == null && 
/* 1356 */       DetectLucene.isPresent()) {
/* 1357 */       this.luceneConfig = new LuceneConfig();
/* 1358 */       this.luceneConfig.loadSettings(this.name);
/*      */     } 
/*      */ 
/*      */     
/* 1362 */     this.useJtaTransactionManager = p.getBoolean("useJtaTransactionManager", false);
/* 1363 */     this.namingConvention = createNamingConvention(p);
/* 1364 */     this.databasePlatform = (DatabasePlatform)createInstance(p, DatabasePlatform.class, "databasePlatform");
/* 1365 */     this.encryptKeyManager = (EncryptKeyManager)createInstance(p, EncryptKeyManager.class, "encryptKeyManager");
/* 1366 */     this.encryptDeployManager = (EncryptDeployManager)createInstance(p, EncryptDeployManager.class, "encryptDeployManager");
/* 1367 */     this.encryptor = (Encryptor)createInstance(p, Encryptor.class, "encryptor");
/* 1368 */     this.dbEncrypt = (DbEncrypt)createInstance(p, DbEncrypt.class, "dbEncrypt");
/*      */     
/* 1370 */     String jarsProp = p.get("search.jars", p.get("jars", null));
/* 1371 */     if (jarsProp != null) {
/* 1372 */       this.searchJars = getSearchJarsPackages(jarsProp);
/*      */     }
/*      */     
/* 1375 */     String packagesProp = p.get("search.packages", p.get("packages", null));
/* 1376 */     if (this.packages != null) {
/* 1377 */       this.packages = getSearchJarsPackages(packagesProp);
/*      */     }
/*      */     
/* 1380 */     this.vanillaMode = p.getBoolean("vanillaMode", false);
/* 1381 */     this.vanillaRefMode = p.getBoolean("vanillaRefMode", false);
/* 1382 */     this.updateChangesOnly = p.getBoolean("updateChangesOnly", true);
/*      */     
/* 1384 */     boolean batchMode = p.getBoolean("batch.mode", false);
/* 1385 */     this.persistBatching = p.getBoolean("persistBatching", batchMode);
/*      */     
/* 1387 */     int batchSize = p.getInt("batch.size", 20);
/* 1388 */     this.persistBatchSize = p.getInt("persistBatchSize", batchSize);
/*      */     
/* 1390 */     this.dataSourceJndiName = p.get("dataSourceJndiName", null);
/* 1391 */     this.databaseSequenceBatchSize = p.getInt("databaseSequenceBatchSize", 20);
/* 1392 */     this.databaseBooleanTrue = p.get("databaseBooleanTrue", null);
/* 1393 */     this.databaseBooleanFalse = p.get("databaseBooleanFalse", null);
/* 1394 */     this.databasePlatformName = p.get("databasePlatformName", null);
/*      */     
/* 1396 */     this.lazyLoadBatchSize = p.getInt("lazyLoadBatchSize", 1);
/* 1397 */     this.queryBatchSize = p.getInt("queryBatchSize", 100);
/*      */     
/* 1399 */     this.ddlGenerate = p.getBoolean("ddl.generate", false);
/* 1400 */     this.ddlRun = p.getBoolean("ddl.run", false);
/* 1401 */     this.debugSql = p.getBoolean("debug.sql", false);
/* 1402 */     this.debugLazyLoad = p.getBoolean("debug.lazyload", false);
/*      */     
/* 1404 */     this.loggingLevel = getLogLevelValue(p);
/*      */     
/* 1406 */     String s = p.get("useJuliTransactionLogger", null);
/* 1407 */     s = p.get("loggingToJavaLogger", s);
/* 1408 */     this.loggingToJavaLogger = "true".equalsIgnoreCase(s);
/*      */     
/* 1410 */     s = p.get("log.directory", "logs");
/* 1411 */     this.loggingDirectory = p.get("logging.directory", s);
/*      */     
/* 1413 */     this.classes = getClasses(p);
/*      */   }
/*      */ 
/*      */   
/*      */   private LogLevel getLogLevelValue(ConfigPropertyMap p) {
/* 1418 */     String logValue = p.get("logging", "NONE");
/* 1419 */     logValue = p.get("log.level", logValue);
/* 1420 */     logValue = p.get("logging.level", logValue);
/* 1421 */     if (logValue.trim().equalsIgnoreCase("ALL")) {
/* 1422 */       logValue = "SQL";
/*      */     }
/* 1424 */     return (LogLevel)Enum.valueOf(LogLevel.class, logValue.toUpperCase());
/*      */   }
/*      */ 
/*      */   
/*      */   private NamingConvention createNamingConvention(GlobalProperties.PropertySource p) {
/* 1429 */     NamingConvention nc = (NamingConvention)createInstance(p, NamingConvention.class, "namingconvention");
/* 1430 */     if (nc == null) {
/* 1431 */       return null;
/*      */     }
/* 1433 */     if (nc instanceof AbstractNamingConvention) {
/* 1434 */       AbstractNamingConvention anc = (AbstractNamingConvention)nc;
/* 1435 */       String v = p.get("namingConvention.useForeignKeyPrefix", null);
/* 1436 */       if (v != null) {
/* 1437 */         boolean useForeignKeyPrefix = Boolean.valueOf(v).booleanValue();
/* 1438 */         anc.setUseForeignKeyPrefix(useForeignKeyPrefix);
/*      */       } 
/*      */       
/* 1441 */       String sequenceFormat = p.get("namingConvention.sequenceFormat", null);
/* 1442 */       if (sequenceFormat != null) {
/* 1443 */         anc.setSequenceFormat(sequenceFormat);
/*      */       }
/*      */     } 
/* 1446 */     return nc;
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
/*      */   private ArrayList<Class<?>> getClasses(GlobalProperties.PropertySource p) {
/* 1459 */     String classNames = p.get("classes", null);
/* 1460 */     if (classNames == null)
/*      */     {
/* 1462 */       return null;
/*      */     }
/*      */     
/* 1465 */     ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
/*      */     
/* 1467 */     String[] split = classNames.split("[ ,;]");
/* 1468 */     for (int i = 0; i < split.length; i++) {
/* 1469 */       String cn = split[i].trim();
/* 1470 */       if (cn.length() > 0 && !"class".equalsIgnoreCase(cn)) {
/*      */         try {
/* 1472 */           classes.add(ClassUtil.forName(cn, getClass()));
/* 1473 */         } catch (ClassNotFoundException e) {
/* 1474 */           String msg = "Error registering class [" + cn + "] from [" + classNames + "]";
/* 1475 */           throw new RuntimeException(msg, e);
/*      */         } 
/*      */       }
/*      */     } 
/* 1479 */     return classes;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<String> getSearchJarsPackages(String searchPackages) {
/* 1484 */     List<String> hitList = new ArrayList<String>();
/*      */     
/* 1486 */     if (searchPackages != null) {
/*      */       
/* 1488 */       String[] entries = searchPackages.split("[ ,;]");
/* 1489 */       for (int i = 0; i < entries.length; i++) {
/* 1490 */         hitList.add(entries[i].trim());
/*      */       }
/*      */     } 
/* 1493 */     return hitList;
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\ServerConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */