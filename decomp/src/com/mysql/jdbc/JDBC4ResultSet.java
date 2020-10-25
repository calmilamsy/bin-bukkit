/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ 
/*     */ 
/*     */ public class JDBC4ResultSet
/*     */   extends ResultSetImpl
/*     */ {
/*  48 */   public JDBC4ResultSet(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt) { super(updateCount, updateID, conn, creatorStmt); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public JDBC4ResultSet(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException { super(catalog, fields, tuples, conn, creatorStmt); }
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
/*     */   public Reader getNCharacterStream(int columnIndex) throws SQLException {
/*  72 */     checkColumnBounds(columnIndex);
/*     */     
/*  74 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*  75 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/*  76 */       throw new SQLException("Can not call getNCharacterStream() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/*  79 */     return getCharacterStream(columnIndex);
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
/*  98 */   public Reader getNCharacterStream(String columnName) throws SQLException { return getNCharacterStream(findColumn(columnName)); }
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
/*     */   public NClob getNClob(int columnIndex) throws SQLException {
/* 113 */     checkColumnBounds(columnIndex);
/*     */     
/* 115 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 116 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 117 */       throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/* 120 */     if (!this.isBinaryEncoded) {
/* 121 */       String asString = getStringForNClob(columnIndex);
/*     */       
/* 123 */       if (asString == null) {
/* 124 */         return null;
/*     */       }
/*     */       
/* 127 */       return new JDBC4NClob(asString, getExceptionInterceptor());
/*     */     } 
/*     */     
/* 130 */     return getNativeNClob(columnIndex);
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
/* 145 */   public NClob getNClob(String columnName) throws SQLException { return getNClob(findColumn(columnName)); }
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
/*     */   protected NClob getNativeNClob(int columnIndex) throws SQLException {
/* 161 */     String stringVal = getStringForNClob(columnIndex);
/*     */     
/* 163 */     if (stringVal == null) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     return getNClobFromString(stringVal, columnIndex);
/*     */   }
/*     */   
/*     */   private String getStringForNClob(int columnIndex) throws SQLException {
/* 171 */     String asString = null;
/*     */     
/* 173 */     String forcedEncoding = "UTF-8";
/*     */     
/*     */     try {
/* 176 */       byte[] asBytes = null;
/*     */       
/* 178 */       if (!this.isBinaryEncoded) {
/* 179 */         asBytes = getBytes(columnIndex);
/*     */       } else {
/* 181 */         asBytes = getNativeBytes(columnIndex, true);
/*     */       } 
/*     */       
/* 184 */       if (asBytes != null) {
/* 185 */         asString = new String(asBytes, forcedEncoding);
/*     */       }
/* 187 */     } catch (UnsupportedEncodingException uee) {
/* 188 */       throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return asString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 197 */   private final NClob getNClobFromString(String stringVal, int columnIndex) throws SQLException { return new JDBC4NClob(stringVal, getExceptionInterceptor()); }
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
/*     */   public String getNString(int columnIndex) throws SQLException {
/* 214 */     checkColumnBounds(columnIndex);
/*     */     
/* 216 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 217 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 218 */       throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/* 221 */     return getString(columnIndex);
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
/* 239 */   public String getNString(String columnName) throws SQLException { return getNString(findColumn(columnName)); }
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
/* 263 */   public void updateNCharacterStream(int columnIndex, Reader x, int length) throws SQLException { throw new NotUpdatable(); }
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
/* 285 */   public void updateNCharacterStream(String columnName, Reader reader, int length) throws SQLException { updateNCharacterStream(findColumn(columnName), reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public void updateNClob(String columnName, NClob nClob) throws SQLException { updateNClob(findColumn(columnName), nClob); }
/*     */ 
/*     */ 
/*     */   
/* 296 */   public void updateRowId(int columnIndex, RowId x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/* 300 */   public void updateRowId(String columnName, RowId x) throws SQLException { updateRowId(findColumn(columnName), x); }
/*     */ 
/*     */ 
/*     */   
/* 304 */   public int getHoldability() throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */   
/* 308 */   public RowId getRowId(int columnIndex) throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */   
/* 312 */   public RowId getRowId(String columnLabel) throws SQLException { return getRowId(findColumn(columnLabel)); }
/*     */ 
/*     */   
/*     */   public SQLXML getSQLXML(int columnIndex) throws SQLException {
/* 316 */     checkColumnBounds(columnIndex);
/*     */     
/* 318 */     return new JDBC4MysqlSQLXML(this, columnIndex, getExceptionInterceptor());
/*     */   }
/*     */ 
/*     */   
/* 322 */   public SQLXML getSQLXML(String columnLabel) throws SQLException { return getSQLXML(findColumn(columnLabel)); }
/*     */ 
/*     */ 
/*     */   
/* 326 */   public boolean isClosed() throws SQLException { return this.isClosed; }
/*     */ 
/*     */ 
/*     */   
/* 330 */   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException { updateAsciiStream(findColumn(columnLabel), x); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException { updateAsciiStream(findColumn(columnLabel), x, length); }
/*     */ 
/*     */ 
/*     */   
/* 349 */   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 354 */   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException { updateBinaryStream(findColumn(columnLabel), x); }
/*     */ 
/*     */ 
/*     */   
/* 358 */   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 363 */   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException { updateBinaryStream(findColumn(columnLabel), x, length); }
/*     */ 
/*     */ 
/*     */   
/* 367 */   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/* 371 */   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException { updateBlob(findColumn(columnLabel), inputStream); }
/*     */ 
/*     */ 
/*     */   
/* 375 */   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 380 */   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException { updateBlob(findColumn(columnLabel), inputStream, length); }
/*     */ 
/*     */ 
/*     */   
/* 384 */   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException { updateCharacterStream(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */   
/* 393 */   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 398 */   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { updateCharacterStream(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 402 */   public void updateClob(int columnIndex, Reader reader) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 407 */   public void updateClob(String columnLabel, Reader reader) throws SQLException { updateClob(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */   
/* 411 */   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 416 */   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException { updateClob(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 420 */   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException { updateNCharacterStream(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 430 */   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 435 */   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { updateNCharacterStream(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 439 */   public void updateNClob(int columnIndex, NClob nClob) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 444 */   public void updateNClob(int columnIndex, Reader reader) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 449 */   public void updateNClob(String columnLabel, Reader reader) throws SQLException { updateNClob(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/* 458 */   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException { updateNClob(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 462 */   public void updateNString(int columnIndex, String nString) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 467 */   public void updateNString(String columnLabel, String nString) throws SQLException { updateNString(findColumn(columnLabel), nString); }
/*     */ 
/*     */ 
/*     */   
/* 471 */   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 476 */   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException { updateSQLXML(findColumn(columnLabel), xmlObject); }
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 496 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 500 */     return iface.isInstance(this);
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
/* 521 */       return (T)iface.cast(this);
/* 522 */     } catch (ClassCastException cce) {
/* 523 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4ResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */