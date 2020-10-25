/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.sql.Clob;
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
/*     */ public class Clob
/*     */   implements Clob, OutputStreamWatcher, WriterWatcher
/*     */ {
/*     */   private String charData;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   Clob(ExceptionInterceptor exceptionInterceptor) {
/*  47 */     this.charData = "";
/*  48 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */   
/*     */   Clob(String charDataInit, ExceptionInterceptor exceptionInterceptor) {
/*  52 */     this.charData = charDataInit;
/*  53 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getAsciiStream() throws SQLException {
/*  60 */     if (this.charData != null) {
/*  61 */       return new ByteArrayInputStream(this.charData.getBytes());
/*     */     }
/*     */     
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getCharacterStream() throws SQLException {
/*  71 */     if (this.charData != null) {
/*  72 */       return new StringReader(this.charData);
/*     */     }
/*     */     
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubString(long startPos, int length) throws SQLException {
/*  82 */     if (startPos < 1L) {
/*  83 */       throw SQLError.createSQLException(Messages.getString("Clob.6"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/*  87 */     int adjustedStartPos = (int)startPos - 1;
/*  88 */     int adjustedEndIndex = adjustedStartPos + length;
/*     */     
/*  90 */     if (this.charData != null) {
/*  91 */       if (adjustedEndIndex > this.charData.length()) {
/*  92 */         throw SQLError.createSQLException(Messages.getString("Clob.7"), "S1009", this.exceptionInterceptor);
/*     */       }
/*     */ 
/*     */       
/*  96 */       return this.charData.substring(adjustedStartPos, adjustedEndIndex);
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() throws SQLException {
/* 107 */     if (this.charData != null) {
/* 108 */       return this.charData.length();
/*     */     }
/*     */     
/* 111 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public long position(Clob arg0, long arg1) throws SQLException { return position(arg0.getSubString(0L, (int)arg0.length()), arg1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long position(String stringToFind, long startPos) throws SQLException {
/* 126 */     if (startPos < 1L) {
/* 127 */       throw SQLError.createSQLException(Messages.getString("Clob.8") + startPos + Messages.getString("Clob.9"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (this.charData != null) {
/* 133 */       if (startPos - 1L > this.charData.length()) {
/* 134 */         throw SQLError.createSQLException(Messages.getString("Clob.10"), "S1009", this.exceptionInterceptor);
/*     */       }
/*     */ 
/*     */       
/* 138 */       int pos = this.charData.indexOf(stringToFind, (int)(startPos - 1L));
/*     */       
/* 140 */       return (pos == -1) ? -1L : (pos + 1);
/*     */     } 
/*     */     
/* 143 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream setAsciiStream(long indexToWriteAt) throws SQLException {
/* 150 */     if (indexToWriteAt < 1L) {
/* 151 */       throw SQLError.createSQLException(Messages.getString("Clob.0"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 155 */     WatchableOutputStream bytesOut = new WatchableOutputStream();
/* 156 */     bytesOut.setWatcher(this);
/*     */     
/* 158 */     if (indexToWriteAt > 0L) {
/* 159 */       bytesOut.write(this.charData.getBytes(), 0, (int)(indexToWriteAt - 1L));
/*     */     }
/*     */ 
/*     */     
/* 163 */     return bytesOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer setCharacterStream(long indexToWriteAt) throws SQLException {
/* 170 */     if (indexToWriteAt < 1L) {
/* 171 */       throw SQLError.createSQLException(Messages.getString("Clob.1"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 175 */     WatchableWriter writer = new WatchableWriter();
/* 176 */     writer.setWatcher(this);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (indexToWriteAt > 1L) {
/* 182 */       writer.write(this.charData, 0, (int)(indexToWriteAt - 1L));
/*     */     }
/*     */     
/* 185 */     return writer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setString(long pos, String str) throws SQLException {
/* 192 */     if (pos < 1L) {
/* 193 */       throw SQLError.createSQLException(Messages.getString("Clob.2"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 197 */     if (str == null) {
/* 198 */       throw SQLError.createSQLException(Messages.getString("Clob.3"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 202 */     StringBuffer charBuf = new StringBuffer(this.charData);
/*     */     
/* 204 */     pos--;
/*     */     
/* 206 */     int strLength = str.length();
/*     */     
/* 208 */     charBuf.replace((int)pos, (int)(pos + strLength), str);
/*     */     
/* 210 */     this.charData = charBuf.toString();
/*     */     
/* 212 */     return strLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setString(long pos, String str, int offset, int len) throws SQLException {
/* 220 */     if (pos < 1L) {
/* 221 */       throw SQLError.createSQLException(Messages.getString("Clob.4"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 225 */     if (str == null) {
/* 226 */       throw SQLError.createSQLException(Messages.getString("Clob.5"), "S1009", this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/* 230 */     StringBuffer charBuf = new StringBuffer(this.charData);
/*     */     
/* 232 */     pos--;
/*     */     
/* 234 */     String replaceString = str.substring(offset, len);
/*     */     
/* 236 */     charBuf.replace((int)pos, (int)(pos + replaceString.length()), replaceString);
/*     */ 
/*     */     
/* 239 */     this.charData = charBuf.toString();
/*     */     
/* 241 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void streamClosed(WatchableOutputStream out) {
/* 248 */     int streamSize = out.size();
/*     */     
/* 250 */     if (streamSize < this.charData.length()) {
/*     */       try {
/* 252 */         out.write(StringUtils.getBytes(this.charData, null, null, false, null, this.exceptionInterceptor), streamSize, this.charData.length() - streamSize);
/*     */       
/*     */       }
/* 255 */       catch (SQLException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 260 */     this.charData = StringUtils.toAsciiString(out.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void truncate(long length) throws SQLException {
/* 267 */     if (length > this.charData.length()) {
/* 268 */       throw SQLError.createSQLException(Messages.getString("Clob.11") + this.charData.length() + Messages.getString("Clob.12") + length + Messages.getString("Clob.13"), this.exceptionInterceptor);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     this.charData = this.charData.substring(0, (int)length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   public void writerClosed(char[] charDataBeingWritten) { this.charData = new String(charDataBeingWritten); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writerClosed(WatchableWriter out) {
/* 288 */     int dataLength = out.size();
/*     */     
/* 290 */     if (dataLength < this.charData.length()) {
/* 291 */       out.write(this.charData, dataLength, this.charData.length() - dataLength);
/*     */     }
/*     */ 
/*     */     
/* 295 */     this.charData = out.toString();
/*     */   }
/*     */ 
/*     */   
/* 299 */   public void free() throws SQLException { this.charData = null; }
/*     */ 
/*     */ 
/*     */   
/* 303 */   public Reader getCharacterStream(long pos, long length) throws SQLException { return new StringReader(getSubString(pos, (int)length)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Clob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */