/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.H2Platform;
/*     */ import com.avaje.ebean.config.dbplatform.HsqldbPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.MsSqlServer2000Platform;
/*     */ import com.avaje.ebean.config.dbplatform.MsSqlServer2005Platform;
/*     */ import com.avaje.ebean.config.dbplatform.MySqlPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.Oracle10Platform;
/*     */ import com.avaje.ebean.config.dbplatform.Oracle9Platform;
/*     */ import com.avaje.ebean.config.dbplatform.PostgresPlatform;
/*     */ import com.avaje.ebean.config.dbplatform.SQLitePlatform;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabasePlatformFactory
/*     */ {
/*  52 */   private static final Logger logger = Logger.getLogger(DatabasePlatformFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabasePlatform create(ServerConfig serverConfig) {
/*     */     try {
/*  61 */       if (serverConfig.getDatabasePlatformName() != null)
/*     */       {
/*  63 */         return byDatabaseName(serverConfig.getDatabasePlatformName());
/*     */       }
/*     */       
/*  66 */       if (serverConfig.getDataSourceConfig().isOffline()) {
/*  67 */         String m = "You must specify a DatabasePlatformName when you are offline";
/*  68 */         throw new PersistenceException(m);
/*     */       } 
/*     */       
/*  71 */       return byDataSource(serverConfig.getDataSource());
/*     */     
/*     */     }
/*  74 */     catch (Exception ex) {
/*  75 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDatabaseName(String dbName) throws SQLException {
/*  84 */     dbName = dbName.toLowerCase();
/*  85 */     if (dbName.equals("postgres83")) {
/*  86 */       return new PostgresPlatform();
/*     */     }
/*  88 */     if (dbName.equals("oracle9")) {
/*  89 */       return new Oracle9Platform();
/*     */     }
/*  91 */     if (dbName.equals("oracle10")) {
/*  92 */       return new Oracle10Platform();
/*     */     }
/*  94 */     if (dbName.equals("oracle")) {
/*  95 */       return new Oracle10Platform();
/*     */     }
/*  97 */     if (dbName.equals("sqlserver2005")) {
/*  98 */       return new MsSqlServer2005Platform();
/*     */     }
/* 100 */     if (dbName.equals("sqlserver2000")) {
/* 101 */       return new MsSqlServer2000Platform();
/*     */     }
/* 103 */     if (dbName.equals("mysql")) {
/* 104 */       return new MySqlPlatform();
/*     */     }
/* 106 */     if (dbName.equals("sqlite")) {
/* 107 */       return new SQLitePlatform();
/*     */     }
/*     */     
/* 110 */     throw new RuntimeException("database platform " + dbName + " is not known?");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDataSource(DataSource dataSource) {
/* 119 */     conn = null;
/*     */     try {
/* 121 */       conn = dataSource.getConnection();
/* 122 */       DatabaseMetaData metaData = conn.getMetaData();
/*     */       
/* 124 */       return byDatabaseMeta(metaData);
/*     */     }
/* 126 */     catch (SQLException ex) {
/* 127 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/*     */       try {
/* 131 */         if (conn != null) {
/* 132 */           conn.close();
/*     */         }
/* 134 */       } catch (SQLException ex) {
/* 135 */         logger.log(Level.SEVERE, null, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DatabasePlatform byDatabaseMeta(DatabaseMetaData metaData) throws SQLException {
/* 145 */     String dbProductName = metaData.getDatabaseProductName();
/* 146 */     dbProductName = dbProductName.toLowerCase();
/*     */     
/* 148 */     int majorVersion = metaData.getDatabaseMajorVersion();
/*     */     
/* 150 */     if (dbProductName.indexOf("oracle") > -1) {
/* 151 */       if (majorVersion > 9) {
/* 152 */         return new Oracle10Platform();
/*     */       }
/* 154 */       return new Oracle9Platform();
/*     */     } 
/*     */     
/* 157 */     if (dbProductName.indexOf("microsoft") > -1) {
/* 158 */       if (majorVersion > 8) {
/* 159 */         return new MsSqlServer2005Platform();
/*     */       }
/* 161 */       return new MsSqlServer2000Platform();
/*     */     } 
/*     */ 
/*     */     
/* 165 */     if (dbProductName.indexOf("mysql") > -1) {
/* 166 */       return new MySqlPlatform();
/*     */     }
/* 168 */     if (dbProductName.indexOf("h2") > -1) {
/* 169 */       return new H2Platform();
/*     */     }
/* 171 */     if (dbProductName.indexOf("hsql database engine") > -1) {
/* 172 */       return new HsqldbPlatform();
/*     */     }
/* 174 */     if (dbProductName.indexOf("postgres") > -1) {
/* 175 */       return new PostgresPlatform();
/*     */     }
/* 177 */     if (dbProductName.indexOf("sqlite") > -1) {
/* 178 */       return new SQLitePlatform();
/*     */     }
/*     */     
/* 181 */     return new DatabasePlatform();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DatabasePlatformFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */