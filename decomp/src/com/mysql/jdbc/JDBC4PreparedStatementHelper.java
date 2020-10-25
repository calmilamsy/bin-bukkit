/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.io.Reader;
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
/*    */ public class JDBC4PreparedStatementHelper
/*    */ {
/* 19 */   static void setRowId(PreparedStatement pstmt, int parameterIndex, RowId x) throws SQLException { throw SQLError.notImplemented(); }
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
/*    */   static void setNClob(PreparedStatement pstmt, int parameterIndex, NClob value) throws SQLException {
/* 35 */     if (value == null) {
/* 36 */       pstmt.setNull(parameterIndex, 2011);
/*    */     } else {
/* 38 */       pstmt.setNCharacterStream(parameterIndex, value.getCharacterStream(), value.length());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 43 */   static void setNClob(PreparedStatement pstmt, int parameterIndex, Reader reader) throws SQLException { pstmt.setNCharacterStream(parameterIndex, reader); }
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
/*    */   static void setNClob(PreparedStatement pstmt, int parameterIndex, Reader reader, long length) throws SQLException {
/* 61 */     if (reader == null) {
/* 62 */       pstmt.setNull(parameterIndex, 2011);
/*    */     } else {
/* 64 */       pstmt.setNCharacterStream(parameterIndex, reader, length);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static void setSQLXML(PreparedStatement pstmt, int parameterIndex, SQLXML xmlObject) throws SQLException {
/* 70 */     if (xmlObject == null) {
/* 71 */       pstmt.setNull(parameterIndex, 2009);
/*    */     } else {
/*    */       
/* 74 */       pstmt.setCharacterStream(parameterIndex, ((JDBC4MysqlSQLXML)xmlObject).serializeAsCharacterStream());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4PreparedStatementHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */