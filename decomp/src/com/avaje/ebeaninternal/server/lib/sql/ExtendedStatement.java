/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebeaninternal.jdbc.PreparedStatementDelegator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ExtendedStatement
/*     */   extends PreparedStatementDelegator
/*     */ {
/*     */   protected final PooledConnection pooledConnection;
/*     */   protected final PreparedStatement pstmt;
/*     */   
/*     */   public ExtendedStatement(PooledConnection pooledConnection, PreparedStatement pstmt) {
/*  53 */     super(pstmt);
/*     */     
/*  55 */     this.pooledConnection = pooledConnection;
/*  56 */     this.pstmt = pstmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void close() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*     */     try {
/*  69 */       return this.pstmt.getConnection();
/*  70 */     } catch (SQLException e) {
/*  71 */       this.pooledConnection.addError(e);
/*  72 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/*     */     try {
/*  81 */       this.pooledConnection.setLastStatement(sql);
/*  82 */       this.pstmt.addBatch(sql);
/*  83 */     } catch (SQLException e) {
/*  84 */       this.pooledConnection.addError(e);
/*  85 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/*     */     try {
/*  94 */       this.pooledConnection.setLastStatement(sql);
/*  95 */       return this.pstmt.execute(sql);
/*  96 */     } catch (SQLException e) {
/*  97 */       this.pooledConnection.addError(e);
/*  98 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/*     */     try {
/* 107 */       this.pooledConnection.setLastStatement(sql);
/* 108 */       return this.pstmt.executeQuery(sql);
/* 109 */     } catch (SQLException e) {
/* 110 */       this.pooledConnection.addError(e);
/* 111 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/*     */     try {
/* 120 */       this.pooledConnection.setLastStatement(sql);
/* 121 */       return this.pstmt.executeUpdate(sql);
/* 122 */     } catch (SQLException e) {
/* 123 */       this.pooledConnection.addError(e);
/* 124 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public int[] executeBatch() throws SQLException { return this.pstmt.executeBatch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void cancel() throws SQLException { this.pstmt.cancel(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public void clearBatch() throws SQLException { this.pstmt.clearBatch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public void clearWarnings() throws SQLException { this.pstmt.clearWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public int getFetchDirection() throws SQLException { return this.pstmt.getFetchDirection(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public int getFetchSize() throws SQLException { return this.pstmt.getFetchSize(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public int getMaxFieldSize() throws SQLException { return this.pstmt.getMaxFieldSize(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public int getMaxRows() throws SQLException { return this.pstmt.getMaxRows(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public boolean getMoreResults() throws SQLException { return this.pstmt.getMoreResults(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public int getQueryTimeout() throws SQLException { return this.pstmt.getQueryTimeout(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public ResultSet getResultSet() throws SQLException { return this.pstmt.getResultSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public int getResultSetConcurrency() throws SQLException { return this.pstmt.getResultSetConcurrency(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public int getResultSetType() throws SQLException { return this.pstmt.getResultSetType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public int getUpdateCount() throws SQLException { return this.pstmt.getUpdateCount(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public SQLWarning getWarnings() throws SQLException { return this.pstmt.getWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public void setCursorName(String name) throws SQLException { this.pstmt.setCursorName(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public void setEscapeProcessing(boolean enable) throws SQLException { this.pstmt.setEscapeProcessing(enable); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public void setFetchDirection(int direction) throws SQLException { this.pstmt.setFetchDirection(direction); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   public void setFetchSize(int rows) throws SQLException { this.pstmt.setFetchSize(rows); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public void setMaxFieldSize(int max) throws SQLException { this.pstmt.setMaxFieldSize(max); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public void setMaxRows(int max) throws SQLException { this.pstmt.setMaxRows(max); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 279 */   public void setQueryTimeout(int seconds) throws SQLException { this.pstmt.setQueryTimeout(seconds); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public boolean getMoreResults(int i) throws SQLException { return this.pstmt.getMoreResults(i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public ResultSet getGeneratedKeys() throws SQLException { return this.pstmt.getGeneratedKeys(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public int executeUpdate(String s, int i) throws SQLException { return this.pstmt.executeUpdate(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public int executeUpdate(String s, int[] i) throws SQLException { return this.pstmt.executeUpdate(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 314 */   public int executeUpdate(String s, String[] i) throws SQLException { return this.pstmt.executeUpdate(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 321 */   public boolean execute(String s, int i) throws SQLException { return this.pstmt.execute(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public boolean execute(String s, int[] i) throws SQLException { return this.pstmt.execute(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   public boolean execute(String s, String[] i) throws SQLException { return this.pstmt.execute(s, i); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public int getResultSetHoldability() throws SQLException { return this.pstmt.getResultSetHoldability(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\ExtendedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */