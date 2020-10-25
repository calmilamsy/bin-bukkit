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
/*     */ public class JDBC4CallableStatement
/*     */   extends CallableStatement
/*     */ {
/*  42 */   public JDBC4CallableStatement(MySQLConnection conn, CallableStatement.CallableStatementParamInfo paramInfo) throws SQLException { super(conn, paramInfo); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public JDBC4CallableStatement(MySQLConnection conn, String sql, String catalog, boolean isFunctionCall) throws SQLException { super(conn, sql, catalog, isFunctionCall); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void setRowId(int parameterIndex, RowId x) throws SQLException { JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x); }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public void setRowId(String parameterName, RowId x) throws SQLException { JDBC4PreparedStatementHelper.setRowId(this, getNamedParamIndex(parameterName, false), x); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException { JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException { JDBC4PreparedStatementHelper.setSQLXML(this, getNamedParamIndex(parameterName, false), xmlObject); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
/*  73 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/*  75 */     SQLXML retValue = ((JDBC4ResultSet)rs).getSQLXML(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/*  78 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/*  80 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public SQLXML getSQLXML(String parameterName) throws SQLException {
/*  85 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     SQLXML retValue = ((JDBC4ResultSet)rs).getSQLXML(fixParameterName(parameterName));
/*     */ 
/*     */     
/*  93 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/*  95 */     return retValue;
/*     */   }
/*     */   
/*     */   public RowId getRowId(int parameterIndex) throws SQLException {
/*  99 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/* 101 */     RowId retValue = ((JDBC4ResultSet)rs).getRowId(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/* 104 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 106 */     return retValue;
/*     */   }
/*     */   
/*     */   public RowId getRowId(String parameterName) throws SQLException {
/* 110 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     RowId retValue = ((JDBC4ResultSet)rs).getRowId(fixParameterName(parameterName));
/*     */ 
/*     */     
/* 118 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 120 */     return retValue;
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
/* 135 */   public void setNClob(int parameterIndex, NClob value) throws SQLException { JDBC4PreparedStatementHelper.setNClob(this, parameterIndex, value); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void setNClob(String parameterName, NClob value) throws SQLException { JDBC4PreparedStatementHelper.setNClob(this, getNamedParamIndex(parameterName, false), value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public void setNClob(String parameterName, Reader reader) throws SQLException { setNClob(getNamedParamIndex(parameterName, false), reader); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public void setNClob(String parameterName, Reader reader, long length) throws SQLException { setNClob(getNamedParamIndex(parameterName, false), reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void setNString(String parameterName, String value) throws SQLException { setNString(getNamedParamIndex(parameterName, false), value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getCharacterStream(int parameterIndex) throws SQLException {
/* 165 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/* 167 */     Reader retValue = rs.getCharacterStream(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/* 170 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 172 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getCharacterStream(String parameterName) throws SQLException {
/* 179 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     Reader retValue = rs.getCharacterStream(fixParameterName(parameterName));
/*     */ 
/*     */     
/* 187 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 189 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
/* 196 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/* 198 */     Reader retValue = ((JDBC4ResultSet)rs).getNCharacterStream(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/* 201 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 203 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getNCharacterStream(String parameterName) throws SQLException {
/* 210 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     Reader retValue = ((JDBC4ResultSet)rs).getNCharacterStream(fixParameterName(parameterName));
/*     */ 
/*     */     
/* 218 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 220 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob getNClob(int parameterIndex) throws SQLException {
/* 227 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/* 229 */     NClob retValue = ((JDBC4ResultSet)rs).getNClob(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/* 232 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 234 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob getNClob(String parameterName) throws SQLException {
/* 241 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     NClob retValue = ((JDBC4ResultSet)rs).getNClob(fixParameterName(parameterName));
/*     */ 
/*     */     
/* 249 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 251 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNString(int parameterIndex) throws SQLException {
/* 258 */     ResultSetInternalMethods rs = getOutputParameters(parameterIndex);
/*     */     
/* 260 */     String retValue = ((JDBC4ResultSet)rs).getNString(mapOutputParameterIndexToRsIndex(parameterIndex));
/*     */ 
/*     */     
/* 263 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 265 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNString(String parameterName) throws SQLException {
/* 272 */     ResultSetInternalMethods rs = getOutputParameters(0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     String retValue = ((JDBC4ResultSet)rs).getNString(fixParameterName(parameterName));
/*     */ 
/*     */     
/* 280 */     this.outputParamWasNull = rs.wasNull();
/*     */     
/* 282 */     return retValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4CallableStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */