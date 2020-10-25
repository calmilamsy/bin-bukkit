/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.NClob;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Struct;
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
/*     */ public class JDBC4Connection
/*     */   extends ConnectionImpl
/*     */ {
/*     */   private JDBC4ClientInfoProvider infoProvider;
/*     */   
/*  47 */   public JDBC4Connection(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo, String url) throws SQLException { super(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public SQLXML createSQLXML() throws SQLException { return new JDBC4MysqlSQLXML(getExceptionInterceptor()); }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public Properties getClientInfo() throws SQLException { return getClientInfoProviderImpl().getClientInfo(this); }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getClientInfo(String name) throws SQLException { return getClientInfoProviderImpl().getClientInfo(this, name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  93 */     if (isClosed()) {
/*  94 */       return false;
/*     */     }
/*     */     
/*     */     try {
/*  98 */       synchronized (getMutex()) {
/*     */         try {
/* 100 */           pingInternal(false, timeout * 1000);
/* 101 */         } catch (Throwable t) {
/*     */           try {
/* 103 */             abortInternal();
/* 104 */           } catch (Throwable ignoreThrown) {}
/*     */ 
/*     */ 
/*     */           
/* 108 */           return false;
/*     */         } 
/*     */       } 
/* 111 */     } catch (Throwable t) {
/* 112 */       return false;
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public void setClientInfo(Properties properties) throws SQLClientInfoException {
/*     */     try {
/* 120 */       getClientInfoProviderImpl().setClientInfo(this, properties);
/* 121 */     } catch (SQLClientInfoException ciEx) {
/* 122 */       throw ciEx;
/* 123 */     } catch (SQLException sqlEx) {
/* 124 */       SQLClientInfoException clientInfoEx = new SQLClientInfoException();
/* 125 */       clientInfoEx.initCause(sqlEx);
/*     */       
/* 127 */       throw clientInfoEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setClientInfo(String name, String value) throws SQLClientInfoException {
/*     */     try {
/* 133 */       getClientInfoProviderImpl().setClientInfo(this, name, value);
/* 134 */     } catch (SQLClientInfoException ciEx) {
/* 135 */       throw ciEx;
/* 136 */     } catch (SQLException sqlEx) {
/* 137 */       SQLClientInfoException clientInfoEx = new SQLClientInfoException();
/* 138 */       clientInfoEx.initCause(sqlEx);
/*     */       
/* 140 */       throw clientInfoEx;
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 160 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 164 */     return iface.isInstance(this);
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
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 185 */       return (T)iface.cast(this);
/* 186 */     } catch (ClassCastException cce) {
/* 187 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public Blob createBlob() { return new Blob(getExceptionInterceptor()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public Clob createClob() { return new Clob(getExceptionInterceptor()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public NClob createNClob() { return new JDBC4NClob(getExceptionInterceptor()); }
/*     */ 
/*     */   
/*     */   protected JDBC4ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
/* 214 */     if (this.infoProvider == null) {
/*     */       try {
/*     */         try {
/* 217 */           this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance(getClientInfoProvider(), new Class[0], new Object[0], getExceptionInterceptor());
/*     */         }
/* 219 */         catch (SQLException sqlEx) {
/* 220 */           if (sqlEx.getCause() instanceof ClassCastException)
/*     */           {
/* 222 */             this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance("com.mysql.jdbc." + getClientInfoProvider(), new Class[0], new Object[0], getExceptionInterceptor());
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 227 */       catch (ClassCastException cce) {
/* 228 */         throw SQLError.createSQLException(Messages.getString("JDBC4Connection.ClientInfoNotImplemented", new Object[] { getClientInfoProvider() }), "S1009", getExceptionInterceptor());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 233 */       this.infoProvider.initialize(this, this.props);
/*     */     } 
/*     */     
/* 236 */     return this.infoProvider;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */