/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.sql.NClob;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4ServerPreparedStatement
/*     */   extends ServerPreparedStatement
/*     */ {
/*  44 */   public JDBC4ServerPreparedStatement(MySQLConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency) throws SQLException { super(conn, sql, catalog, resultSetType, resultSetConcurrency); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/*  55 */     if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8"))
/*     */     {
/*  57 */       throw SQLError.createSQLException("Can not call setNCharacterStream() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/*  61 */     checkClosed();
/*     */     
/*  63 */     if (reader == null) {
/*  64 */       setNull(parameterIndex, -2);
/*     */     } else {
/*  66 */       ServerPreparedStatement.BindValue binding = getBinding(parameterIndex, true);
/*  67 */       setType(binding, 252);
/*     */       
/*  69 */       binding.value = reader;
/*  70 */       binding.isNull = false;
/*  71 */       binding.isLongData = true;
/*     */       
/*  73 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/*  74 */         binding.bindLength = length;
/*     */       } else {
/*  76 */         binding.bindLength = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public void setNClob(int parameterIndex, NClob x) throws SQLException { setNClob(parameterIndex, x.getCharacterStream(), this.connection.getUseStreamLengthsInPrepStmts() ? x.length() : -1L); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/* 105 */     if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8"))
/*     */     {
/* 107 */       throw SQLError.createSQLException("Can not call setNClob() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/* 111 */     checkClosed();
/*     */     
/* 113 */     if (reader == null) {
/* 114 */       setNull(parameterIndex, 2011);
/*     */     } else {
/* 116 */       ServerPreparedStatement.BindValue binding = getBinding(parameterIndex, true);
/* 117 */       setType(binding, 252);
/*     */       
/* 119 */       binding.value = reader;
/* 120 */       binding.isNull = false;
/* 121 */       binding.isLongData = true;
/*     */       
/* 123 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 124 */         binding.bindLength = length;
/*     */       } else {
/* 126 */         binding.bindLength = -1L;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNString(int parameterIndex, String x) throws SQLException {
/* 135 */     if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
/*     */       
/* 137 */       setString(parameterIndex, x);
/*     */     } else {
/* 139 */       throw SQLError.createSQLException("Can not call setNString() when connection character set isn't UTF-8", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 145 */   public void setRowId(int parameterIndex, RowId x) throws SQLException { JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException { JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4ServerPreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */