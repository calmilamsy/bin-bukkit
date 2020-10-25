/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteArrayRow
/*     */   extends ResultSetRow
/*     */ {
/*     */   byte[][] internalRowData;
/*     */   
/*     */   public ByteArrayRow(byte[][] internalRowData, ExceptionInterceptor exceptionInterceptor) {
/*  49 */     super(exceptionInterceptor);
/*     */     
/*  51 */     this.internalRowData = internalRowData;
/*     */   }
/*     */ 
/*     */   
/*  55 */   public byte[] getColumnValue(int index) throws SQLException { return this.internalRowData[index]; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public void setColumnValue(int index, byte[] value) throws SQLException { this.internalRowData[index] = value; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(int index, String encoding, MySQLConnection conn) throws SQLException {
/*  64 */     byte[] columnData = this.internalRowData[index];
/*     */     
/*  66 */     if (columnData == null) {
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     return getString(encoding, conn, columnData, 0, columnData.length);
/*     */   }
/*     */ 
/*     */   
/*  74 */   public boolean isNull(int index) throws SQLException { return (this.internalRowData[index] == null); }
/*     */ 
/*     */   
/*     */   public boolean isFloatingPointNumber(int index) throws SQLException {
/*  78 */     byte[] numAsBytes = this.internalRowData[index];
/*     */     
/*  80 */     if (this.internalRowData[index] == null || this.internalRowData[index].length == 0)
/*     */     {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     for (int i = 0; i < numAsBytes.length; i++) {
/*  86 */       if ((char)numAsBytes[i] == 'e' || (char)numAsBytes[i] == 'E') {
/*  87 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public long length(int index) throws SQLException {
/*  95 */     if (this.internalRowData[index] == null) {
/*  96 */       return 0L;
/*     */     }
/*     */     
/*  99 */     return this.internalRowData[index].length;
/*     */   }
/*     */   
/*     */   public int getInt(int columnIndex) {
/* 103 */     if (this.internalRowData[columnIndex] == null) {
/* 104 */       return 0;
/*     */     }
/*     */     
/* 107 */     return StringUtils.getInt(this.internalRowData[columnIndex]);
/*     */   }
/*     */   
/*     */   public long getLong(int columnIndex) throws SQLException {
/* 111 */     if (this.internalRowData[columnIndex] == null) {
/* 112 */       return 0L;
/*     */     }
/*     */     
/* 115 */     return StringUtils.getLong(this.internalRowData[columnIndex]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getTimestampFast(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 121 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 123 */     if (columnValue == null) {
/* 124 */       return null;
/*     */     }
/*     */     
/* 127 */     return getTimestampFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNativeDouble(int columnIndex) throws SQLException {
/* 133 */     if (this.internalRowData[columnIndex] == null) {
/* 134 */       return 0.0D;
/*     */     }
/*     */     
/* 137 */     return getNativeDouble(this.internalRowData[columnIndex], 0);
/*     */   }
/*     */   
/*     */   public float getNativeFloat(int columnIndex) throws SQLException {
/* 141 */     if (this.internalRowData[columnIndex] == null) {
/* 142 */       return 0.0F;
/*     */     }
/*     */     
/* 145 */     return getNativeFloat(this.internalRowData[columnIndex], 0);
/*     */   }
/*     */   
/*     */   public int getNativeInt(int columnIndex) {
/* 149 */     if (this.internalRowData[columnIndex] == null) {
/* 150 */       return 0;
/*     */     }
/*     */     
/* 153 */     return getNativeInt(this.internalRowData[columnIndex], 0);
/*     */   }
/*     */   
/*     */   public long getNativeLong(int columnIndex) throws SQLException {
/* 157 */     if (this.internalRowData[columnIndex] == null) {
/* 158 */       return 0L;
/*     */     }
/*     */     
/* 161 */     return getNativeLong(this.internalRowData[columnIndex], 0);
/*     */   }
/*     */   
/*     */   public short getNativeShort(int columnIndex) throws SQLException {
/* 165 */     if (this.internalRowData[columnIndex] == null) {
/* 166 */       return 0;
/*     */     }
/*     */     
/* 169 */     return getNativeShort(this.internalRowData[columnIndex], 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getNativeTimestamp(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 175 */     byte[] bits = this.internalRowData[columnIndex];
/*     */     
/* 177 */     if (bits == null) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     return getNativeTimestamp(bits, 0, bits.length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeOpenStreams() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getBinaryInputStream(int columnIndex) throws SQLException {
/* 191 */     if (this.internalRowData[columnIndex] == null) {
/* 192 */       return null;
/*     */     }
/*     */     
/* 195 */     return new ByteArrayInputStream(this.internalRowData[columnIndex]);
/*     */   }
/*     */   
/*     */   public Reader getReader(int columnIndex) throws SQLException {
/* 199 */     InputStream stream = getBinaryInputStream(columnIndex);
/*     */     
/* 201 */     if (stream == null) {
/* 202 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 206 */       return new InputStreamReader(stream, this.metadata[columnIndex].getCharacterSet());
/*     */     }
/* 208 */     catch (UnsupportedEncodingException e) {
/* 209 */       SQLException sqlEx = SQLError.createSQLException("", this.exceptionInterceptor);
/*     */       
/* 211 */       sqlEx.initCause(e);
/*     */       
/* 213 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Time getTimeFast(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 220 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 222 */     if (columnValue == null) {
/* 223 */       return null;
/*     */     }
/*     */     
/* 226 */     return getTimeFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDateFast(int columnIndex, MySQLConnection conn, ResultSetImpl rs, Calendar targetCalendar) throws SQLException {
/* 232 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 234 */     if (columnValue == null) {
/* 235 */       return null;
/*     */     }
/*     */     
/* 238 */     return getDateFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, conn, rs, targetCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNativeDateTimeValue(int columnIndex, Calendar targetCalendar, int jdbcType, int mysqlType, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 246 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 248 */     if (columnValue == null) {
/* 249 */       return null;
/*     */     }
/*     */     
/* 252 */     return getNativeDateTimeValue(columnIndex, columnValue, 0, columnValue.length, targetCalendar, jdbcType, mysqlType, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getNativeDate(int columnIndex, MySQLConnection conn, ResultSetImpl rs, Calendar cal) throws SQLException {
/* 259 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 261 */     if (columnValue == null) {
/* 262 */       return null;
/*     */     }
/*     */     
/* 265 */     return getNativeDate(columnIndex, columnValue, 0, columnValue.length, conn, rs, cal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Time getNativeTime(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/* 272 */     byte[] columnValue = this.internalRowData[columnIndex];
/*     */     
/* 274 */     if (columnValue == null) {
/* 275 */       return null;
/*     */     }
/*     */     
/* 278 */     return getNativeTime(columnIndex, columnValue, 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytesSize() {
/* 283 */     if (this.internalRowData == null) {
/* 284 */       return 0;
/*     */     }
/*     */     
/* 287 */     int bytesSize = 0;
/*     */     
/* 289 */     for (int i = 0; i < this.internalRowData.length; i++) {
/* 290 */       if (this.internalRowData[i] != null) {
/* 291 */         bytesSize += this.internalRowData[i].length;
/*     */       }
/*     */     } 
/*     */     
/* 295 */     return bytesSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ByteArrayRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */