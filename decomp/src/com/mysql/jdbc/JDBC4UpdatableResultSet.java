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
/*     */ public class JDBC4UpdatableResultSet
/*     */   extends UpdatableResultSet
/*     */ {
/*  48 */   public JDBC4UpdatableResultSet(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException { super(catalog, fields, tuples, conn, creatorStmt); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void updateClob(int columnIndex, Reader reader) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException { updateNCharacterStream(columnIndex, x, (int)length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void updateNClob(int columnIndex, Reader reader) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void updateRowId(int columnIndex, RowId x) throws SQLException { throw new NotUpdatable(); }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException { updateAsciiStream(findColumn(columnLabel), x); }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException { updateAsciiStream(findColumn(columnLabel), x, length); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException { updateBinaryStream(findColumn(columnLabel), x); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException { updateBinaryStream(findColumn(columnLabel), x, length); }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException { updateBlob(findColumn(columnLabel), inputStream); }
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException { updateBlob(findColumn(columnLabel), inputStream, length); }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException { updateCharacterStream(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { updateCharacterStream(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public void updateClob(String columnLabel, Reader reader) throws SQLException { updateClob(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException { updateClob(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException { updateNCharacterStream(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { updateNCharacterStream(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void updateNClob(String columnLabel, Reader reader) throws SQLException { updateNClob(findColumn(columnLabel), reader); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException { updateNClob(findColumn(columnLabel), reader, length); }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException { updateSQLXML(findColumn(columnLabel), xmlObject); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
/* 213 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 214 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 215 */       throw new SQLException("Can not call updateNCharacterStream() when field's character set isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 219 */     if (!this.onInsertRow) {
/* 220 */       if (!this.doingUpdates) {
/* 221 */         this.doingUpdates = true;
/* 222 */         syncUpdate();
/*     */       } 
/*     */       
/* 225 */       ((JDBC4PreparedStatement)this.updater).setNCharacterStream(columnIndex, x, length);
/*     */     } else {
/* 227 */       ((JDBC4PreparedStatement)this.inserter).setNCharacterStream(columnIndex, x, length);
/*     */       
/* 229 */       if (x == null) {
/* 230 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*     */       } else {
/* 232 */         this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/* 256 */   public void updateNCharacterStream(String columnName, Reader reader, int length) throws SQLException { updateNCharacterStream(findColumn(columnName), reader, length); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
/* 264 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 265 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 266 */       throw new SQLException("Can not call updateNClob() when field's character set isn't UTF-8");
/*     */     }
/*     */     
/* 269 */     if (nClob == null) {
/* 270 */       updateNull(columnIndex);
/*     */     } else {
/* 272 */       updateNCharacterStream(columnIndex, nClob.getCharacterStream(), (int)nClob.length());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public void updateNClob(String columnName, NClob nClob) throws SQLException { updateNClob(findColumn(columnName), nClob); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNString(int columnIndex, String x) throws SQLException {
/* 301 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 302 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 303 */       throw new SQLException("Can not call updateNString() when field's character set isn't UTF-8");
/*     */     }
/*     */     
/* 306 */     if (!this.onInsertRow) {
/* 307 */       if (!this.doingUpdates) {
/* 308 */         this.doingUpdates = true;
/* 309 */         syncUpdate();
/*     */       } 
/*     */       
/* 312 */       ((JDBC4PreparedStatement)this.updater).setNString(columnIndex, x);
/*     */     } else {
/* 314 */       ((JDBC4PreparedStatement)this.inserter).setNString(columnIndex, x);
/*     */       
/* 316 */       if (x == null) {
/* 317 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*     */       } else {
/* 319 */         this.thisRow.setColumnValue(columnIndex - 1, StringUtils.getBytes(x, this.charConverter, fieldEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/* 343 */   public void updateNString(String columnName, String x) throws SQLException { updateNString(findColumn(columnName), x); }
/*     */ 
/*     */ 
/*     */   
/* 347 */   public int getHoldability() throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 363 */     String stringVal = getStringForNClob(columnIndex);
/*     */     
/* 365 */     if (stringVal == null) {
/* 366 */       return null;
/*     */     }
/*     */     
/* 369 */     return getNClobFromString(stringVal, columnIndex);
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
/*     */   public Reader getNCharacterStream(int columnIndex) throws SQLException {
/* 388 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 389 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 390 */       throw new SQLException("Can not call getNCharacterStream() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 394 */     return getCharacterStream(columnIndex);
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
/* 413 */   public Reader getNCharacterStream(String columnName) throws SQLException { return getNCharacterStream(findColumn(columnName)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 428 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*     */     
/* 430 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 431 */       throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 435 */     if (!this.isBinaryEncoded) {
/* 436 */       String asString = getStringForNClob(columnIndex);
/*     */       
/* 438 */       if (asString == null) {
/* 439 */         return null;
/*     */       }
/*     */       
/* 442 */       return new JDBC4NClob(asString, getExceptionInterceptor());
/*     */     } 
/*     */     
/* 445 */     return getNativeNClob(columnIndex);
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
/* 460 */   public NClob getNClob(String columnName) throws SQLException { return getNClob(findColumn(columnName)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 465 */   private final NClob getNClobFromString(String stringVal, int columnIndex) throws SQLException { return new JDBC4NClob(stringVal, getExceptionInterceptor()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 482 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*     */     
/* 484 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 485 */       throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 489 */     return getString(columnIndex);
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
/* 507 */   public String getNString(String columnName) throws SQLException { return getNString(findColumn(columnName)); }
/*     */ 
/*     */ 
/*     */   
/* 511 */   public RowId getRowId(int columnIndex) throws SQLException { throw SQLError.notImplemented(); }
/*     */ 
/*     */ 
/*     */   
/* 515 */   public RowId getRowId(String columnLabel) throws SQLException { return getRowId(findColumn(columnLabel)); }
/*     */ 
/*     */ 
/*     */   
/* 519 */   public SQLXML getSQLXML(int columnIndex) throws SQLException { return new JDBC4MysqlSQLXML(this, columnIndex, getExceptionInterceptor()); }
/*     */ 
/*     */ 
/*     */   
/* 523 */   public SQLXML getSQLXML(String columnLabel) throws SQLException { return getSQLXML(findColumn(columnLabel)); }
/*     */ 
/*     */   
/*     */   private String getStringForNClob(int columnIndex) throws SQLException {
/* 527 */     String asString = null;
/*     */     
/* 529 */     String forcedEncoding = "UTF-8";
/*     */     
/*     */     try {
/* 532 */       byte[] asBytes = null;
/*     */       
/* 534 */       if (!this.isBinaryEncoded) {
/* 535 */         asBytes = getBytes(columnIndex);
/*     */       } else {
/* 537 */         asBytes = getNativeBytes(columnIndex, true);
/*     */       } 
/*     */       
/* 540 */       if (asBytes != null) {
/* 541 */         asString = new String(asBytes, forcedEncoding);
/*     */       }
/* 543 */     } catch (UnsupportedEncodingException uee) {
/* 544 */       throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */     
/* 548 */     return asString;
/*     */   }
/*     */ 
/*     */   
/* 552 */   public boolean isClosed() throws SQLException { return this.isClosed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 578 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 582 */     return iface.isInstance(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 608 */       return (T)iface.cast(this);
/* 609 */     } catch (ClassCastException cce) {
/* 610 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4UpdatableResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */