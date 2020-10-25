/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataSourceConfig
/*     */ {
/*     */   private String url;
/*     */   private String username;
/*     */   private String password;
/*     */   private String driver;
/*  25 */   private int minConnections = 2;
/*     */   
/*  27 */   private int maxConnections = 20;
/*     */   
/*  29 */   private int isolationLevel = 2;
/*     */   
/*     */   private String heartbeatSql;
/*     */   
/*     */   private boolean captureStackTrace;
/*     */   
/*  35 */   private int maxStackTraceSize = 5;
/*     */   
/*  37 */   private int leakTimeMinutes = 30;
/*     */   
/*  39 */   private int maxInactiveTimeSecs = 900;
/*     */   
/*  41 */   private int pstmtCacheSize = 20;
/*  42 */   private int cstmtCacheSize = 20;
/*     */   
/*  44 */   private int waitTimeoutMillis = 1000;
/*     */ 
/*     */   
/*     */   private String poolListener;
/*     */ 
/*     */   
/*     */   private boolean offline;
/*     */ 
/*     */ 
/*     */   
/*  54 */   public String getUrl() { return this.url; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public void setUrl(String url) { this.url = url; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getUsername() { return this.username; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public void setUsername(String username) { this.username = username; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public String getPassword() { return this.password; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void setPassword(String password) { this.password = password; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String getDriver() { return this.driver; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public void setDriver(String driver) { this.driver = driver; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int getIsolationLevel() { return this.isolationLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public void setIsolationLevel(int isolationLevel) { this.isolationLevel = isolationLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public int getMinConnections() { return this.minConnections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public void setMinConnections(int minConnections) { this.minConnections = minConnections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public int getMaxConnections() { return this.maxConnections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public void setMaxConnections(int maxConnections) { this.maxConnections = maxConnections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public String getHeartbeatSql() { return this.heartbeatSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public void setHeartbeatSql(String heartbeatSql) { this.heartbeatSql = heartbeatSql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public boolean isCaptureStackTrace() { return this.captureStackTrace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public void setCaptureStackTrace(boolean captureStackTrace) { this.captureStackTrace = captureStackTrace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   public int getMaxStackTraceSize() { return this.maxStackTraceSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public void setMaxStackTraceSize(int maxStackTraceSize) { this.maxStackTraceSize = maxStackTraceSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public int getLeakTimeMinutes() { return this.leakTimeMinutes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public void setLeakTimeMinutes(int leakTimeMinutes) { this.leakTimeMinutes = leakTimeMinutes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public int getPstmtCacheSize() { return this.pstmtCacheSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public void setPstmtCacheSize(int pstmtCacheSize) { this.pstmtCacheSize = pstmtCacheSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public int getCstmtCacheSize() { return this.cstmtCacheSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public void setCstmtCacheSize(int cstmtCacheSize) { this.cstmtCacheSize = cstmtCacheSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public int getWaitTimeoutMillis() { return this.waitTimeoutMillis; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public void setWaitTimeoutMillis(int waitTimeoutMillis) { this.waitTimeoutMillis = waitTimeoutMillis; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public int getMaxInactiveTimeSecs() { return this.maxInactiveTimeSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public void setMaxInactiveTimeSecs(int maxInactiveTimeSecs) { this.maxInactiveTimeSecs = maxInactiveTimeSecs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   public String getPoolListener() { return this.poolListener; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public void setPoolListener(String poolListener) { this.poolListener = poolListener; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public boolean isOffline() { return this.offline; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public void setOffline(boolean offline) { this.offline = offline; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSettings(String serverName) {
/* 341 */     String prefix = "datasource." + serverName + ".";
/*     */     
/* 343 */     this.username = GlobalProperties.get(prefix + "username", null);
/* 344 */     this.password = GlobalProperties.get(prefix + "password", null);
/*     */ 
/*     */ 
/*     */     
/* 348 */     String v = GlobalProperties.get(prefix + "databaseDriver", null);
/* 349 */     this.driver = GlobalProperties.get(prefix + "driver", v);
/*     */ 
/*     */     
/* 352 */     v = GlobalProperties.get(prefix + "databaseUrl", null);
/* 353 */     this.url = GlobalProperties.get(prefix + "url", v);
/*     */     
/* 355 */     this.captureStackTrace = GlobalProperties.getBoolean(prefix + "captureStackTrace", false);
/* 356 */     this.maxStackTraceSize = GlobalProperties.getInt(prefix + "maxStackTraceSize", 5);
/* 357 */     this.leakTimeMinutes = GlobalProperties.getInt(prefix + "leakTimeMinutes", 30);
/* 358 */     this.maxInactiveTimeSecs = GlobalProperties.getInt(prefix + "maxInactiveTimeSecs", 900);
/*     */     
/* 360 */     this.minConnections = GlobalProperties.getInt(prefix + "minConnections", 0);
/* 361 */     this.maxConnections = GlobalProperties.getInt(prefix + "maxConnections", 20);
/* 362 */     this.pstmtCacheSize = GlobalProperties.getInt(prefix + "pstmtCacheSize", 20);
/* 363 */     this.cstmtCacheSize = GlobalProperties.getInt(prefix + "cstmtCacheSize", 20);
/*     */     
/* 365 */     this.waitTimeoutMillis = GlobalProperties.getInt(prefix + "waitTimeout", 1000);
/*     */     
/* 367 */     this.heartbeatSql = GlobalProperties.get(prefix + "heartbeatSql", null);
/* 368 */     this.poolListener = GlobalProperties.get(prefix + "poolListener", null);
/* 369 */     this.offline = GlobalProperties.getBoolean(prefix + "offline", false);
/*     */     
/* 371 */     String isoLevel = GlobalProperties.get(prefix + "isolationlevel", "READ_COMMITTED");
/* 372 */     this.isolationLevel = TransactionIsolation.getLevel(isoLevel);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\DataSourceConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */