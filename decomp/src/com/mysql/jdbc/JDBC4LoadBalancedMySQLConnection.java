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
/*     */ 
/*     */ 
/*     */ public class JDBC4LoadBalancedMySQLConnection
/*     */   extends LoadBalancedMySQLConnection
/*     */   implements JDBC4MySQLConnection
/*     */ {
/*  48 */   public JDBC4LoadBalancedMySQLConnection(LoadBalancingConnectionProxy proxy) throws SQLException { super(proxy); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   private JDBC4Connection getJDBC4Connection() { return (JDBC4Connection)this.proxy.currentConn; }
/*     */ 
/*     */   
/*  55 */   public SQLXML createSQLXML() throws SQLException { return getJDBC4Connection().createSQLXML(); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Array createArrayOf(String typeName, Object[] elements) throws SQLException { return getJDBC4Connection().createArrayOf(typeName, elements); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public Struct createStruct(String typeName, Object[] attributes) throws SQLException { return getJDBC4Connection().createStruct(typeName, attributes); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public Properties getClientInfo() throws SQLException { return getJDBC4Connection().getClientInfo(); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getClientInfo(String name) throws SQLException { return getJDBC4Connection().getClientInfo(name); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isValid(int timeout) throws SQLException { return getJDBC4Connection().isValid(timeout); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void setClientInfo(Properties properties) throws SQLClientInfoException { getJDBC4Connection().setClientInfo(properties); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void setClientInfo(String name, String value) throws SQLClientInfoException { getJDBC4Connection().setClientInfo(name, value); }
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/*  88 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/*  92 */     return iface.isInstance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 100 */       return (T)iface.cast(this);
/* 101 */     } catch (ClassCastException cce) {
/* 102 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public Blob createBlob() { return getJDBC4Connection().createBlob(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public Clob createClob() { return getJDBC4Connection().createClob(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public NClob createNClob() { return getJDBC4Connection().createNClob(); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   protected JDBC4ClientInfoProvider getClientInfoProviderImpl() throws SQLException { return getJDBC4Connection().getClientInfoProviderImpl(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4LoadBalancedMySQLConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */