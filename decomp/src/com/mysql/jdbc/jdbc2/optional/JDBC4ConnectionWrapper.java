/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.Connection;
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.NClob;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Struct;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4ConnectionWrapper
/*     */   extends ConnectionWrapper
/*     */ {
/*  70 */   public JDBC4ConnectionWrapper(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException { super(mysqlPooledConnection, mysqlConnection, forXa); }
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*     */     try {
/*  75 */       super.close();
/*     */     } finally {
/*  77 */       this.unwrappedInterfaces = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SQLXML createSQLXML() throws SQLException {
/*  82 */     checkClosed();
/*     */     
/*     */     try {
/*  85 */       return this.mc.createSQLXML();
/*  86 */     } catch (SQLException sqlException) {
/*  87 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
/*  95 */     checkClosed();
/*     */     
/*     */     try {
/*  98 */       return this.mc.createArrayOf(typeName, elements);
/*     */     }
/* 100 */     catch (SQLException sqlException) {
/* 101 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 104 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
/* 109 */     checkClosed();
/*     */     
/*     */     try {
/* 112 */       return this.mc.createStruct(typeName, attributes);
/*     */     }
/* 114 */     catch (SQLException sqlException) {
/* 115 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */   public Properties getClientInfo() throws SQLException {
/* 122 */     checkClosed();
/*     */     
/*     */     try {
/* 125 */       return this.mc.getClientInfo();
/* 126 */     } catch (SQLException sqlException) {
/* 127 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 130 */       return null;
/*     */     } 
/*     */   }
/*     */   public String getClientInfo(String name) throws SQLException {
/* 134 */     checkClosed();
/*     */     
/*     */     try {
/* 137 */       return this.mc.getClientInfo(name);
/* 138 */     } catch (SQLException sqlException) {
/* 139 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 142 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(int timeout) throws SQLException {
/*     */     try {
/* 169 */       return this.mc.isValid(timeout);
/* 170 */     } catch (SQLException sqlException) {
/* 171 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 174 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setClientInfo(Properties properties) throws SQLClientInfoException {
/*     */     try {
/* 180 */       checkClosed();
/*     */       
/* 182 */       this.mc.setClientInfo(properties);
/* 183 */     } catch (SQLException sqlException) {
/*     */       try {
/* 185 */         checkAndFireConnectionError(sqlException);
/* 186 */       } catch (SQLException sqlEx2) {
/* 187 */         SQLClientInfoException clientEx = new SQLClientInfoException();
/* 188 */         clientEx.initCause(sqlEx2);
/*     */         
/* 190 */         throw clientEx;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientInfo(String name, String value) throws SQLClientInfoException {
/*     */     try {
/* 198 */       checkClosed();
/*     */       
/* 200 */       this.mc.setClientInfo(name, value);
/* 201 */     } catch (SQLException sqlException) {
/*     */       try {
/* 203 */         checkAndFireConnectionError(sqlException);
/* 204 */       } catch (SQLException sqlEx2) {
/* 205 */         SQLClientInfoException clientEx = new SQLClientInfoException();
/* 206 */         clientEx.initCause(sqlEx2);
/*     */         
/* 208 */         throw clientEx;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 236 */     checkClosed();
/*     */     
/* 238 */     boolean isInstance = iface.isInstance(this);
/*     */     
/* 240 */     if (isInstance) {
/* 241 */       return true;
/*     */     }
/*     */     
/* 244 */     return (iface.getName().equals("com.mysql.jdbc.Connection") || iface.getName().equals("com.mysql.jdbc.ConnectionProperties"));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 270 */       if ("java.sql.Connection".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName()))
/*     */       {
/* 272 */         return (T)iface.cast(this);
/*     */       }
/*     */       
/* 275 */       if (this.unwrappedInterfaces == null) {
/* 276 */         this.unwrappedInterfaces = new HashMap();
/*     */       }
/*     */       
/* 279 */       Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
/*     */       
/* 281 */       if (cachedUnwrapped == null) {
/* 282 */         cachedUnwrapped = Proxy.newProxyInstance(this.mc.getClass().getClassLoader(), new Class[] { iface }, new WrapperBase.ConnectionErrorFiringInvocationHandler(this, this.mc));
/*     */ 
/*     */         
/* 285 */         this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*     */       } 
/*     */       
/* 288 */       return (T)iface.cast(cachedUnwrapped);
/* 289 */     } catch (ClassCastException cce) {
/* 290 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Blob createBlob() throws SQLException {
/* 299 */     checkClosed();
/*     */     
/*     */     try {
/* 302 */       return this.mc.createBlob();
/* 303 */     } catch (SQLException sqlException) {
/* 304 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 307 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Clob createClob() throws SQLException {
/* 314 */     checkClosed();
/*     */     
/*     */     try {
/* 317 */       return this.mc.createClob();
/* 318 */     } catch (SQLException sqlException) {
/* 319 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 322 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob createNClob() throws SQLException {
/* 329 */     checkClosed();
/*     */     
/*     */     try {
/* 332 */       return this.mc.createNClob();
/* 333 */     } catch (SQLException sqlException) {
/* 334 */       checkAndFireConnectionError(sqlException);
/*     */ 
/*     */       
/* 337 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4ConnectionWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */