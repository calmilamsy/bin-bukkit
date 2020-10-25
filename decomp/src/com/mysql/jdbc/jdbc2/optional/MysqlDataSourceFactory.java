/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.spi.ObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MysqlDataSourceFactory
/*     */   implements ObjectFactory
/*     */ {
/*     */   protected static final String DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
/*     */   protected static final String POOL_DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource";
/*     */   protected static final String XA_DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";
/*     */   
/*     */   public Object getObjectInstance(Object refObj, Name nm, Context ctx, Hashtable env) throws Exception {
/*  77 */     Reference ref = (Reference)refObj;
/*  78 */     String className = ref.getClassName();
/*     */     
/*  80 */     if (className != null && (className.equals("com.mysql.jdbc.jdbc2.optional.MysqlDataSource") || className.equals("com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource") || className.equals("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"))) {
/*     */ 
/*     */ 
/*     */       
/*  84 */       MysqlDataSource dataSource = null;
/*     */       
/*     */       try {
/*  87 */         dataSource = (MysqlDataSource)Class.forName(className).newInstance();
/*     */       }
/*  89 */       catch (Exception ex) {
/*  90 */         throw new RuntimeException("Unable to create DataSource of class '" + className + "', reason: " + ex.toString());
/*     */       } 
/*     */ 
/*     */       
/*  94 */       int portNumber = 3306;
/*     */       
/*  96 */       String portNumberAsString = nullSafeRefAddrStringGet("port", ref);
/*     */       
/*  98 */       if (portNumberAsString != null) {
/*  99 */         portNumber = Integer.parseInt(portNumberAsString);
/*     */       }
/*     */       
/* 102 */       dataSource.setPort(portNumber);
/*     */       
/* 104 */       String user = nullSafeRefAddrStringGet("user", ref);
/*     */       
/* 106 */       if (user != null) {
/* 107 */         dataSource.setUser(user);
/*     */       }
/*     */       
/* 110 */       String password = nullSafeRefAddrStringGet("password", ref);
/*     */       
/* 112 */       if (password != null) {
/* 113 */         dataSource.setPassword(password);
/*     */       }
/*     */       
/* 116 */       String serverName = nullSafeRefAddrStringGet("serverName", ref);
/*     */       
/* 118 */       if (serverName != null) {
/* 119 */         dataSource.setServerName(serverName);
/*     */       }
/*     */       
/* 122 */       String databaseName = nullSafeRefAddrStringGet("databaseName", ref);
/*     */       
/* 124 */       if (databaseName != null) {
/* 125 */         dataSource.setDatabaseName(databaseName);
/*     */       }
/*     */       
/* 128 */       String explicitUrlAsString = nullSafeRefAddrStringGet("explicitUrl", ref);
/*     */       
/* 130 */       if (explicitUrlAsString != null && 
/* 131 */         Boolean.valueOf(explicitUrlAsString).booleanValue()) {
/* 132 */         dataSource.setUrl(nullSafeRefAddrStringGet("url", ref));
/*     */       }
/*     */ 
/*     */       
/* 136 */       dataSource.setPropertiesViaRef(ref);
/*     */       
/* 138 */       return dataSource;
/*     */     } 
/*     */ 
/*     */     
/* 142 */     return null;
/*     */   }
/*     */   
/*     */   private String nullSafeRefAddrStringGet(String referenceName, Reference ref) {
/* 146 */     RefAddr refAddr = ref.get(referenceName);
/*     */     
/* 148 */     return (refAddr != null) ? (String)refAddr.getContent() : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\MysqlDataSourceFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */