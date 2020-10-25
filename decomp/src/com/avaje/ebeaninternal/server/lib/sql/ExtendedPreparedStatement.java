/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedPreparedStatement
/*     */   extends ExtendedStatement
/*     */   implements PreparedStatement
/*     */ {
/*     */   final String sql;
/*     */   final String cacheKey;
/*     */   
/*     */   public ExtendedPreparedStatement(PooledConnection pooledConnection, PreparedStatement pstmt, String sql, String cacheKey) {
/*  63 */     super(pooledConnection, pstmt);
/*  64 */     this.sql = sql;
/*  65 */     this.cacheKey = cacheKey;
/*     */   }
/*     */ 
/*     */   
/*  69 */   public PreparedStatement getDelegate() { return this.pstmt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getCacheKey() { return this.cacheKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void closeDestroy() throws SQLException { this.pstmt.close(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public void close() throws SQLException { this.pooledConnection.returnPreparedStatement(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/*     */     try {
/* 108 */       this.pstmt.addBatch();
/* 109 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 112 */       this.pooledConnection.addError(e);
/* 113 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParameters() throws SQLException {
/*     */     try {
/* 122 */       this.pstmt.clearParameters();
/* 123 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 126 */       this.pooledConnection.addError(e);
/* 127 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute() throws SQLException {
/*     */     try {
/* 136 */       return this.pstmt.execute();
/* 137 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 140 */       this.pooledConnection.addError(e);
/* 141 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery() throws SQLException {
/*     */     try {
/* 150 */       return this.pstmt.executeQuery();
/* 151 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 154 */       this.pooledConnection.addError(e);
/* 155 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*     */     try {
/* 164 */       return this.pstmt.executeUpdate();
/* 165 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 168 */       this.pooledConnection.addError(e);
/* 169 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/*     */     try {
/* 178 */       return this.pstmt.getMetaData();
/* 179 */     } catch (SQLException e) {
/*     */ 
/*     */       
/* 182 */       this.pooledConnection.addError(e);
/* 183 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public ParameterMetaData getParameterMetaData() throws SQLException { return this.pstmt.getParameterMetaData(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void setArray(int i, Array x) throws SQLException { this.pstmt.setArray(i, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException { this.pstmt.setAsciiStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException { this.pstmt.setBigDecimal(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException { this.pstmt.setBinaryStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public void setBlob(int i, Blob x) throws SQLException { this.pstmt.setBlob(i, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public void setBoolean(int parameterIndex, boolean x) throws SQLException { this.pstmt.setBoolean(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public void setByte(int parameterIndex, byte x) throws SQLException { this.pstmt.setByte(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public void setBytes(int parameterIndex, byte[] x) throws SQLException { this.pstmt.setBytes(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException { this.pstmt.setCharacterStream(parameterIndex, reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   public void setClob(int i, Clob x) throws SQLException { this.pstmt.setClob(i, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public void setDate(int parameterIndex, Date x) throws SQLException { this.pstmt.setDate(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException { this.pstmt.setDate(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public void setDouble(int parameterIndex, double x) throws SQLException { this.pstmt.setDouble(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   public void setFloat(int parameterIndex, float x) throws SQLException { this.pstmt.setFloat(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public void setInt(int parameterIndex, int x) throws SQLException { this.pstmt.setInt(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   public void setLong(int parameterIndex, long x) throws SQLException { this.pstmt.setLong(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   public void setNull(int parameterIndex, int sqlType) throws SQLException { this.pstmt.setNull(parameterIndex, sqlType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException { this.pstmt.setNull(paramIndex, sqlType, typeName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public void setObject(int parameterIndex, Object x) throws SQLException { this.pstmt.setObject(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 332 */   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException { this.pstmt.setObject(parameterIndex, x, targetSqlType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException { this.pstmt.setObject(parameterIndex, x, targetSqlType, scale); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 347 */   public void setRef(int i, Ref x) throws SQLException { this.pstmt.setRef(i, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 354 */   public void setShort(int parameterIndex, short x) throws SQLException { this.pstmt.setShort(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 361 */   public void setString(int parameterIndex, String x) throws SQLException { this.pstmt.setString(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public void setTime(int parameterIndex, Time x) throws SQLException { this.pstmt.setTime(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 375 */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException { this.pstmt.setTime(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 382 */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException { this.pstmt.setTimestamp(parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException { this.pstmt.setTimestamp(parameterIndex, x, cal); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 397 */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException { this.pstmt.setUnicodeStream(parameterIndex, x, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   public void setURL(int parameterIndex, URL x) throws SQLException { this.pstmt.setURL(parameterIndex, x); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\ExtendedPreparedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */