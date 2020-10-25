/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.NClob;
/*    */ import java.sql.RowId;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.SQLXML;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JDBC4PreparedStatement
/*    */   extends PreparedStatement
/*    */ {
/* 43 */   public JDBC4PreparedStatement(MySQLConnection conn, String catalog) throws SQLException { super(conn, catalog); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public JDBC4PreparedStatement(MySQLConnection conn, String sql, String catalog) throws SQLException { super(conn, sql, catalog); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public JDBC4PreparedStatement(MySQLConnection conn, String sql, String catalog, PreparedStatement.ParseInfo cachedParseInfo) throws SQLException { super(conn, sql, catalog, cachedParseInfo); }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void setRowId(int parameterIndex, RowId x) throws SQLException { JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public void setNClob(int parameterIndex, NClob value) throws SQLException { JDBC4PreparedStatementHelper.setNClob(this, parameterIndex, value); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException { JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4PreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */