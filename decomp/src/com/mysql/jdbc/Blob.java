/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Blob
/*     */   implements Blob, OutputStreamWatcher
/*     */ {
/*     */   private byte[] binaryData;
/*     */   private boolean isClosed;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   Blob(ExceptionInterceptor exceptionInterceptor) {
/*  60 */     this.binaryData = null;
/*  61 */     this.isClosed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     setBinaryData(Constants.EMPTY_BYTE_ARRAY);
/*  69 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Blob(byte[] data, ExceptionInterceptor exceptionInterceptor) {
/*     */     this.binaryData = null;
/*     */     this.isClosed = false;
/*  79 */     setBinaryData(data);
/*  80 */     this.exceptionInterceptor = exceptionInterceptor;
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
/*     */   Blob(byte[] data, ResultSetInternalMethods creatorResultSetToSet, int columnIndexToSet) {
/*     */     this.binaryData = null;
/*     */     this.isClosed = false;
/*  94 */     setBinaryData(data);
/*     */   }
/*     */ 
/*     */   
/*  98 */   private byte[] getBinaryData() { return this.binaryData; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getBinaryStream() throws SQLException {
/* 110 */     checkClosed();
/*     */     
/* 112 */     return new ByteArrayInputStream(getBinaryData());
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
/*     */   public byte[] getBytes(long pos, int length) throws SQLException {
/* 131 */     checkClosed();
/*     */     
/* 133 */     if (pos < 1L) {
/* 134 */       throw SQLError.createSQLException(Messages.getString("Blob.2"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 138 */     pos--;
/*     */     
/* 140 */     if (pos > this.binaryData.length) {
/* 141 */       throw SQLError.createSQLException("\"pos\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (pos + length > this.binaryData.length) {
/* 146 */       throw SQLError.createSQLException("\"pos\" + \"length\" arguments can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 150 */     byte[] newData = new byte[length];
/* 151 */     System.arraycopy(getBinaryData(), (int)pos, newData, 0, length);
/*     */     
/* 153 */     return newData;
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
/*     */   public long length() throws SQLException {
/* 166 */     checkClosed();
/*     */     
/* 168 */     return getBinaryData().length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public long position(byte[] pattern, long start) throws SQLException { throw SQLError.createSQLException("Not implemented", this.exceptionInterceptor); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long position(Blob pattern, long start) throws SQLException {
/* 193 */     checkClosed();
/*     */     
/* 195 */     return position(pattern.getBytes(0L, (int)pattern.length()), start);
/*     */   }
/*     */ 
/*     */   
/* 199 */   private void setBinaryData(byte[] newBinaryData) { this.binaryData = newBinaryData; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream setBinaryStream(long indexToWriteAt) throws SQLException {
/* 207 */     checkClosed();
/*     */     
/* 209 */     if (indexToWriteAt < 1L) {
/* 210 */       throw SQLError.createSQLException(Messages.getString("Blob.0"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 214 */     WatchableOutputStream bytesOut = new WatchableOutputStream();
/* 215 */     bytesOut.setWatcher(this);
/*     */     
/* 217 */     if (indexToWriteAt > 0L) {
/* 218 */       bytesOut.write(this.binaryData, 0, (int)(indexToWriteAt - 1L));
/*     */     }
/*     */     
/* 221 */     return bytesOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(long writeAt, byte[] bytes) throws SQLException {
/* 228 */     checkClosed();
/*     */     
/* 230 */     return setBytes(writeAt, bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(long writeAt, byte[] bytes, int offset, int length) throws SQLException {
/* 238 */     checkClosed();
/*     */     
/* 240 */     OutputStream bytesOut = setBinaryStream(writeAt);
/*     */     
/*     */     try {
/* 243 */       bytesOut.write(bytes, offset, length);
/* 244 */     } catch (IOException ioEx) {
/* 245 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("Blob.1"), "S1000", this.exceptionInterceptor);
/*     */       
/* 247 */       sqlEx.initCause(ioEx);
/*     */       
/* 249 */       throw sqlEx;
/*     */     } finally {
/*     */       try {
/* 252 */         bytesOut.close();
/* 253 */       } catch (IOException doNothing) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 258 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public void streamClosed(byte[] byteData) { this.binaryData = byteData; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void streamClosed(WatchableOutputStream out) {
/* 272 */     int streamSize = out.size();
/*     */     
/* 274 */     if (streamSize < this.binaryData.length) {
/* 275 */       out.write(this.binaryData, streamSize, this.binaryData.length - streamSize);
/*     */     }
/*     */ 
/*     */     
/* 279 */     this.binaryData = out.toByteArray();
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
/*     */   public void truncate(long len) throws SQLException {
/* 301 */     checkClosed();
/*     */     
/* 303 */     if (len < 0L) {
/* 304 */       throw SQLError.createSQLException("\"len\" argument can not be < 1.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 308 */     if (len > this.binaryData.length) {
/* 309 */       throw SQLError.createSQLException("\"len\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     byte[] newData = new byte[(int)len];
/* 317 */     System.arraycopy(getBinaryData(), 0, newData, 0, (int)len);
/* 318 */     this.binaryData = newData;
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
/*     */   public void free() throws SQLException {
/* 340 */     this.binaryData = null;
/* 341 */     this.isClosed = true;
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
/*     */   public InputStream getBinaryStream(long pos, long length) throws SQLException {
/* 361 */     checkClosed();
/*     */     
/* 363 */     if (pos < 1L) {
/* 364 */       throw SQLError.createSQLException("\"pos\" argument can not be < 1.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 368 */     pos--;
/*     */     
/* 370 */     if (pos > this.binaryData.length) {
/* 371 */       throw SQLError.createSQLException("\"pos\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 375 */     if (pos + length > this.binaryData.length) {
/* 376 */       throw SQLError.createSQLException("\"pos\" + \"length\" arguments can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 380 */     return new ByteArrayInputStream(getBinaryData(), (int)pos, (int)length);
/*     */   }
/*     */   
/*     */   private void checkClosed() throws SQLException {
/* 384 */     if (this.isClosed)
/* 385 */       throw SQLError.createSQLException("Invalid operation on closed BLOB", "S1009", this.exceptionInterceptor); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Blob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */