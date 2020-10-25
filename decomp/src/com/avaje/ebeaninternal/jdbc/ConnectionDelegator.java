/*     */ package com.avaje.ebeaninternal.jdbc;
/*     */ 
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConnectionDelegator
/*     */   implements Connection
/*     */ {
/*     */   private final Connection delegate;
/*     */   
/*  36 */   public ConnectionDelegator(Connection delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public Statement createStatement() throws SQLException { return this.delegate.createStatement(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public PreparedStatement prepareStatement(String sql) throws SQLException { return this.delegate.prepareStatement(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public CallableStatement prepareCall(String sql) throws SQLException { return this.delegate.prepareCall(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public String nativeSQL(String sql) throws SQLException { return this.delegate.nativeSQL(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void setAutoCommit(boolean autoCommit) throws SQLException { this.delegate.setAutoCommit(autoCommit); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean getAutoCommit() throws SQLException { return this.delegate.getAutoCommit(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void commit() throws SQLException { this.delegate.commit(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void rollback() throws SQLException { this.delegate.rollback(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void close() throws SQLException { this.delegate.close(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public boolean isClosed() throws SQLException { return this.delegate.isClosed(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public DatabaseMetaData getMetaData() throws SQLException { return this.delegate.getMetaData(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void setReadOnly(boolean readOnly) throws SQLException { this.delegate.setReadOnly(readOnly); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public boolean isReadOnly() throws SQLException { return this.delegate.isReadOnly(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public void setCatalog(String catalog) throws SQLException { this.delegate.setCatalog(catalog); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String getCatalog() throws SQLException { return this.delegate.getCatalog(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void setTransactionIsolation(int level) throws SQLException { this.delegate.setTransactionIsolation(level); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public int getTransactionIsolation() throws SQLException { return this.delegate.getTransactionIsolation(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public SQLWarning getWarnings() throws SQLException { return this.delegate.getWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void clearWarnings() throws SQLException { this.delegate.clearWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException { return this.delegate.createStatement(resultSetType, resultSetConcurrency); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public Map<String, Class<?>> getTypeMap() throws SQLException { return this.delegate.getTypeMap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public void setTypeMap(Map<String, Class<?>> map) throws SQLException { this.delegate.setTypeMap(map); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public void setHoldability(int holdability) throws SQLException { this.delegate.setHoldability(holdability); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int getHoldability() throws SQLException { return this.delegate.getHoldability(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public Savepoint setSavepoint() throws SQLException { return this.delegate.setSavepoint(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public Savepoint setSavepoint(String name) throws SQLException { return this.delegate.setSavepoint(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void rollback(Savepoint savepoint) throws SQLException { this.delegate.rollback(savepoint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public void releaseSavepoint(Savepoint savepoint) throws SQLException { this.delegate.releaseSavepoint(savepoint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return this.delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException { return this.delegate.prepareStatement(sql, autoGeneratedKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException { return this.delegate.prepareStatement(sql, columnIndexes); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException { return this.delegate.prepareStatement(sql, columnNames); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\jdbc\ConnectionDelegator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */