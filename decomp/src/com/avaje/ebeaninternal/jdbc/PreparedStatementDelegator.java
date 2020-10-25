/*     */ package com.avaje.ebeaninternal.jdbc;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreparedStatementDelegator
/*     */   implements PreparedStatement
/*     */ {
/*     */   private final PreparedStatement delegate;
/*     */   
/*  46 */   public PreparedStatementDelegator(PreparedStatement delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public ResultSet executeQuery() throws SQLException { return this.delegate.executeQuery(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public int executeUpdate() throws SQLException { return this.delegate.executeUpdate(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public void setNull(int parameterIndex, int sqlType) throws SQLException { this.delegate.setNull(parameterIndex, sqlType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void setBoolean(int parameterIndex, boolean x) throws SQLException { this.delegate.setBoolean(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void setByte(int parameterIndex, byte x) throws SQLException { this.delegate.setByte(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void setShort(int parameterIndex, short x) throws SQLException { this.delegate.setShort(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void setInt(int parameterIndex, int x) throws SQLException { this.delegate.setInt(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public void setLong(int parameterIndex, long x) throws SQLException { this.delegate.setLong(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public void setFloat(int parameterIndex, float x) throws SQLException { this.delegate.setFloat(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setDouble(int parameterIndex, double x) throws SQLException { this.delegate.setDouble(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException { this.delegate.setBigDecimal(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setString(int parameterIndex, String x) throws SQLException { this.delegate.setString(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public void setBytes(int parameterIndex, byte[] x) throws SQLException { this.delegate.setBytes(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setDate(int parameterIndex, Date x) throws SQLException { this.delegate.setDate(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void setTime(int parameterIndex, Time x) throws SQLException { this.delegate.setTime(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException { this.delegate.setTimestamp(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException { this.delegate.setAsciiStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException { this.delegate.setUnicodeStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException { this.delegate.setBinaryStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public void clearParameters() throws SQLException { this.delegate.clearParameters(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public void setObject(int i, Object o, int i1, int i2) throws SQLException { this.delegate.setObject(i, o, i1, i2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException { this.delegate.setObject(parameterIndex, x, targetSqlType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public void setObject(int parameterIndex, Object x) throws SQLException { this.delegate.setObject(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public boolean execute() throws SQLException { return this.delegate.execute(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public void addBatch() throws SQLException { this.delegate.addBatch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException { this.delegate.setCharacterStream(parameterIndex, reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public void setRef(int parameterIndex, Ref x) throws SQLException { this.delegate.setRef(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public void setBlob(int parameterIndex, Blob x) throws SQLException { this.delegate.setBlob(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public void setClob(int parameterIndex, Clob x) throws SQLException { this.delegate.setClob(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public void setArray(int parameterIndex, Array x) throws SQLException { this.delegate.setArray(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   public ResultSetMetaData getMetaData() throws SQLException { return this.delegate.getMetaData(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException { this.delegate.setDate(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException { this.delegate.setTime(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException { this.delegate.setTimestamp(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException { this.delegate.setNull(parameterIndex, sqlType, typeName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   public void setURL(int parameterIndex, URL x) throws SQLException { this.delegate.setURL(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public ParameterMetaData getParameterMetaData() throws SQLException { return this.delegate.getParameterMetaData(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 274 */   public ResultSet executeQuery(String sql) throws SQLException { return this.delegate.executeQuery(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public int executeUpdate(String sql) throws SQLException { return this.delegate.executeUpdate(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public void close() throws SQLException { this.delegate.close(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public int getMaxFieldSize() throws SQLException { return this.delegate.getMaxFieldSize(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   public void setMaxFieldSize(int max) throws SQLException { this.delegate.setMaxFieldSize(max); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   public int getMaxRows() throws SQLException { return this.delegate.getMaxRows(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public void setMaxRows(int max) throws SQLException { this.delegate.setMaxRows(max); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 316 */   public void setEscapeProcessing(boolean enable) throws SQLException { this.delegate.setEscapeProcessing(enable); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 322 */   public int getQueryTimeout() throws SQLException { return this.delegate.getQueryTimeout(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public void setQueryTimeout(int seconds) throws SQLException { this.delegate.setQueryTimeout(seconds); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 334 */   public void cancel() throws SQLException { this.delegate.cancel(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public SQLWarning getWarnings() throws SQLException { return this.delegate.getWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public void clearWarnings() throws SQLException { this.delegate.clearWarnings(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 352 */   public void setCursorName(String name) throws SQLException { this.delegate.setCursorName(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 358 */   public boolean execute(String sql) throws SQLException { return this.delegate.execute(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 364 */   public ResultSet getResultSet() throws SQLException { return this.delegate.getResultSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   public int getUpdateCount() throws SQLException { return this.delegate.getUpdateCount(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public boolean getMoreResults() throws SQLException { return this.delegate.getMoreResults(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 382 */   public void setFetchDirection(int direction) throws SQLException { this.delegate.setFetchDirection(direction); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public int getFetchDirection() throws SQLException { return this.delegate.getFetchDirection(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 394 */   public void setFetchSize(int rows) throws SQLException { this.delegate.setFetchSize(rows); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 400 */   public int getFetchSize() throws SQLException { return this.delegate.getFetchSize(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 406 */   public int getResultSetConcurrency() throws SQLException { return this.delegate.getResultSetConcurrency(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   public int getResultSetType() throws SQLException { return this.delegate.getResultSetType(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 418 */   public void addBatch(String sql) throws SQLException { this.delegate.addBatch(sql); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   public void clearBatch() throws SQLException { this.delegate.clearBatch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 430 */   public int[] executeBatch() throws SQLException { return this.delegate.executeBatch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 436 */   public Connection getConnection() throws SQLException { return this.delegate.getConnection(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 442 */   public boolean getMoreResults(int current) throws SQLException { return this.delegate.getMoreResults(current); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 448 */   public ResultSet getGeneratedKeys() throws SQLException { return this.delegate.getGeneratedKeys(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException { return this.delegate.executeUpdate(sql, autoGeneratedKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 460 */   public int executeUpdate(String sql, int[] columnIndexes) throws SQLException { return this.delegate.executeUpdate(sql, columnIndexes); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 466 */   public int executeUpdate(String sql, String[] columnNames) throws SQLException { return this.delegate.executeUpdate(sql, columnNames); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 472 */   public boolean execute(String sql, int autoGeneratedKeys) throws SQLException { return this.delegate.execute(sql, autoGeneratedKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 478 */   public boolean execute(String sql, int[] columnIndexes) throws SQLException { return this.delegate.execute(sql, columnIndexes); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 484 */   public boolean execute(String sql, String[] columnNames) throws SQLException { return this.delegate.execute(sql, columnNames); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 490 */   public int getResultSetHoldability() throws SQLException { return this.delegate.getResultSetHoldability(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\jdbc\PreparedStatementDelegator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */