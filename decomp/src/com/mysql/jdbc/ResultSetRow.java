/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ResultSetRow
/*      */ {
/*      */   protected ExceptionInterceptor exceptionInterceptor;
/*      */   protected Field[] metadata;
/*      */   
/*   54 */   protected ResultSetRow(ExceptionInterceptor exceptionInterceptor) { this.exceptionInterceptor = exceptionInterceptor; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void closeOpenStreams();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract InputStream getBinaryInputStream(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract byte[] getColumnValue(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Date getDateFast(int columnIndex, byte[] dateAsBytes, int offset, int length, MySQLConnection conn, ResultSetImpl rs, Calendar targetCalendar) throws SQLException {
/*   98 */     int year = 0;
/*   99 */     int month = 0;
/*  100 */     int day = 0;
/*      */     
/*      */     try {
/*  103 */       if (dateAsBytes == null) {
/*  104 */         return null;
/*      */       }
/*      */       
/*  107 */       boolean allZeroDate = true;
/*      */       
/*  109 */       boolean onlyTimePresent = false;
/*      */       
/*  111 */       for (i = 0; i < length; i++) {
/*  112 */         if (dateAsBytes[offset + i] == 58) {
/*  113 */           onlyTimePresent = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  118 */       for (int i = 0; i < length; i++) {
/*  119 */         byte b = dateAsBytes[offset + i];
/*      */         
/*  121 */         if (b == 32 || b == 45 || b == 47) {
/*  122 */           onlyTimePresent = false;
/*      */         }
/*      */         
/*  125 */         if (b != 48 && b != 32 && b != 58 && b != 45 && b != 47 && b != 46) {
/*      */           
/*  127 */           allZeroDate = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  133 */       if (!onlyTimePresent && allZeroDate) {
/*      */         
/*  135 */         if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */         {
/*      */           
/*  138 */           return null; } 
/*  139 */         if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */         {
/*  141 */           throw SQLError.createSQLException("Value '" + new String(dateAsBytes) + "' can not be represented as java.sql.Date", "S1009", this.exceptionInterceptor);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  149 */         return rs.fastDateCreate(targetCalendar, 1, 1, 1);
/*      */       } 
/*  151 */       if (this.metadata[columnIndex].getMysqlType() == 7) {
/*      */         
/*  153 */         switch (length) {
/*      */           case 19:
/*      */           case 21:
/*      */           case 29:
/*  157 */             year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 4);
/*      */             
/*  159 */             month = StringUtils.getInt(dateAsBytes, offset + 5, offset + 7);
/*      */             
/*  161 */             day = StringUtils.getInt(dateAsBytes, offset + 8, offset + 10);
/*      */ 
/*      */             
/*  164 */             return rs.fastDateCreate(targetCalendar, year, month, day);
/*      */ 
/*      */           
/*      */           case 8:
/*      */           case 14:
/*  169 */             year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 4);
/*      */             
/*  171 */             month = StringUtils.getInt(dateAsBytes, offset + 4, offset + 6);
/*      */             
/*  173 */             day = StringUtils.getInt(dateAsBytes, offset + 6, offset + 8);
/*      */ 
/*      */             
/*  176 */             return rs.fastDateCreate(targetCalendar, year, month, day);
/*      */ 
/*      */           
/*      */           case 6:
/*      */           case 10:
/*      */           case 12:
/*  182 */             year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/*  185 */             if (year <= 69) {
/*  186 */               year += 100;
/*      */             }
/*      */             
/*  189 */             month = StringUtils.getInt(dateAsBytes, offset + 2, offset + 4);
/*      */             
/*  191 */             day = StringUtils.getInt(dateAsBytes, offset + 4, offset + 6);
/*      */ 
/*      */             
/*  194 */             return rs.fastDateCreate(targetCalendar, year + 1900, month, day);
/*      */ 
/*      */           
/*      */           case 4:
/*  198 */             year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 4);
/*      */ 
/*      */             
/*  201 */             if (year <= 69) {
/*  202 */               year += 100;
/*      */             }
/*      */             
/*  205 */             month = StringUtils.getInt(dateAsBytes, offset + 2, offset + 4);
/*      */ 
/*      */             
/*  208 */             return rs.fastDateCreate(targetCalendar, year + 1900, month, 1);
/*      */ 
/*      */           
/*      */           case 2:
/*  212 */             year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/*  215 */             if (year <= 69) {
/*  216 */               year += 100;
/*      */             }
/*      */             
/*  219 */             return rs.fastDateCreate(targetCalendar, year + 1900, 1, 1);
/*      */         } 
/*      */ 
/*      */         
/*  223 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { new String(dateAsBytes), Constants.integerValueOf(columnIndex + 1) }), "S1009", this.exceptionInterceptor);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  235 */       if (this.metadata[columnIndex].getMysqlType() == 13) {
/*      */         
/*  237 */         if (length == 2 || length == 1) {
/*  238 */           year = StringUtils.getInt(dateAsBytes, offset, offset + length);
/*      */ 
/*      */           
/*  241 */           if (year <= 69) {
/*  242 */             year += 100;
/*      */           }
/*      */           
/*  245 */           year += 1900;
/*      */         } else {
/*  247 */           year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 4);
/*      */         } 
/*      */ 
/*      */         
/*  251 */         return rs.fastDateCreate(targetCalendar, year, 1, 1);
/*  252 */       }  if (this.metadata[columnIndex].getMysqlType() == 11) {
/*  253 */         return rs.fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */       }
/*  255 */       if (length < 10) {
/*  256 */         if (length == 8) {
/*  257 */           return rs.fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  262 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { new String(dateAsBytes), Constants.integerValueOf(columnIndex + 1) }), "S1009", this.exceptionInterceptor);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  275 */       if (length != 18) {
/*  276 */         year = StringUtils.getInt(dateAsBytes, offset + 0, offset + 4);
/*      */         
/*  278 */         month = StringUtils.getInt(dateAsBytes, offset + 5, offset + 7);
/*      */         
/*  280 */         day = StringUtils.getInt(dateAsBytes, offset + 8, offset + 10);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  285 */         StringTokenizer st = new StringTokenizer(new String(dateAsBytes, offset, length, "ISO8859_1"), "- ");
/*      */ 
/*      */         
/*  288 */         year = Integer.parseInt(st.nextToken());
/*  289 */         month = Integer.parseInt(st.nextToken());
/*  290 */         day = Integer.parseInt(st.nextToken());
/*      */       } 
/*      */ 
/*      */       
/*  294 */       return rs.fastDateCreate(targetCalendar, year, month, day);
/*  295 */     } catch (SQLException sqlEx) {
/*  296 */       throw sqlEx;
/*  297 */     } catch (Exception e) {
/*  298 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { new String(dateAsBytes), Constants.integerValueOf(columnIndex + 1) }), "S1009", this.exceptionInterceptor);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  303 */       sqlEx.initCause(e);
/*      */       
/*  305 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Date getDateFast(int paramInt, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl, Calendar paramCalendar) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getInt(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long getLong(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Date getNativeDate(int columnIndex, byte[] bits, int offset, int length, MySQLConnection conn, ResultSetImpl rs, Calendar cal) throws SQLException {
/*  340 */     int year = 0;
/*  341 */     int month = 0;
/*  342 */     int day = 0;
/*      */     
/*  344 */     if (length != 0) {
/*  345 */       year = bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8;
/*      */       
/*  347 */       month = bits[offset + 2];
/*  348 */       day = bits[offset + 3];
/*      */     } 
/*      */     
/*  351 */     if (length == 0 || (year == 0 && month == 0 && day == 0)) {
/*  352 */       if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */       {
/*  354 */         return null; } 
/*  355 */       if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */       {
/*  357 */         throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009", this.exceptionInterceptor);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  363 */       year = 1;
/*  364 */       month = 1;
/*  365 */       day = 1;
/*      */     } 
/*      */     
/*  368 */     if (!rs.useLegacyDatetimeCode) {
/*  369 */       return TimeUtil.fastDateCreate(year, month, day, cal);
/*      */     }
/*      */     
/*  372 */     return rs.fastDateCreate((cal == null) ? rs.getCalendarInstanceForSessionOrNew() : cal, year, month, day);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Date getNativeDate(int paramInt, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl, Calendar paramCalendar) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object getNativeDateTimeValue(int columnIndex, byte[] bits, int offset, int length, Calendar targetCalendar, int jdbcType, int mysqlType, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/*  384 */     int year = 0;
/*  385 */     int month = 0;
/*  386 */     int day = 0;
/*      */     
/*  388 */     int hour = 0;
/*  389 */     int minute = 0;
/*  390 */     int seconds = 0;
/*      */     
/*  392 */     int nanos = 0;
/*      */     
/*  394 */     if (bits == null)
/*      */     {
/*  396 */       return null;
/*      */     }
/*      */     
/*  399 */     Calendar sessionCalendar = conn.getUseJDBCCompliantTimezoneShift() ? conn.getUtcCalendar() : rs.getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */     
/*  403 */     boolean populatedFromDateTimeValue = false;
/*      */     
/*  405 */     switch (mysqlType) {
/*      */       case 7:
/*      */       case 12:
/*  408 */         populatedFromDateTimeValue = true;
/*      */         
/*  410 */         if (length != 0) {
/*  411 */           year = bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8;
/*      */           
/*  413 */           month = bits[offset + 2];
/*  414 */           day = bits[offset + 3];
/*      */           
/*  416 */           if (length > 4) {
/*  417 */             hour = bits[offset + 4];
/*  418 */             minute = bits[offset + 5];
/*  419 */             seconds = bits[offset + 6];
/*      */           } 
/*      */           
/*  422 */           if (length > 7)
/*      */           {
/*  424 */             nanos = (bits[offset + 7] & 0xFF | (bits[offset + 8] & 0xFF) << 8 | (bits[offset + 9] & 0xFF) << 16 | (bits[offset + 10] & 0xFF) << 24) * 1000;
/*      */           }
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/*  432 */         populatedFromDateTimeValue = true;
/*      */         
/*  434 */         if (bits.length != 0) {
/*  435 */           year = bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8;
/*      */           
/*  437 */           month = bits[offset + 2];
/*  438 */           day = bits[offset + 3];
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 11:
/*  443 */         populatedFromDateTimeValue = true;
/*      */         
/*  445 */         if (bits.length != 0) {
/*      */ 
/*      */           
/*  448 */           hour = bits[offset + 5];
/*  449 */           minute = bits[offset + 6];
/*  450 */           seconds = bits[offset + 7];
/*      */         } 
/*      */         
/*  453 */         year = 1970;
/*  454 */         month = 1;
/*  455 */         day = 1;
/*      */         break;
/*      */       
/*      */       default:
/*  459 */         populatedFromDateTimeValue = false;
/*      */         break;
/*      */     } 
/*  462 */     switch (jdbcType) {
/*      */       case 92:
/*  464 */         if (populatedFromDateTimeValue) {
/*  465 */           if (!rs.useLegacyDatetimeCode) {
/*  466 */             return TimeUtil.fastTimeCreate(hour, minute, seconds, targetCalendar, this.exceptionInterceptor);
/*      */           }
/*      */           
/*  469 */           Time time = TimeUtil.fastTimeCreate(rs.getCalendarInstanceForSessionOrNew(), hour, minute, seconds, this.exceptionInterceptor);
/*      */ 
/*      */ 
/*      */           
/*  473 */           return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, time, conn.getServerTimezoneTZ(), tz, rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  480 */         return rs.getNativeTimeViaParseConversion(columnIndex + 1, targetCalendar, tz, rollForward);
/*      */ 
/*      */       
/*      */       case 91:
/*  484 */         if (populatedFromDateTimeValue) {
/*  485 */           if (year == 0 && month == 0 && day == 0) {
/*  486 */             if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */             {
/*      */               
/*  489 */               return null; } 
/*  490 */             if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */             {
/*  492 */               throw new SQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  497 */             year = 1;
/*  498 */             month = 1;
/*  499 */             day = 1;
/*      */           } 
/*      */           
/*  502 */           if (!rs.useLegacyDatetimeCode) {
/*  503 */             return TimeUtil.fastDateCreate(year, month, day, targetCalendar);
/*      */           }
/*      */           
/*  506 */           return rs.fastDateCreate(rs.getCalendarInstanceForSessionOrNew(), year, month, day);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  512 */         return rs.getNativeDateViaParseConversion(columnIndex + 1);
/*      */       case 93:
/*  514 */         if (populatedFromDateTimeValue) {
/*  515 */           if (year == 0 && month == 0 && day == 0) {
/*  516 */             if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */             {
/*      */               
/*  519 */               return null; } 
/*  520 */             if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */             {
/*  522 */               throw new SQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  527 */             year = 1;
/*  528 */             month = 1;
/*  529 */             day = 1;
/*      */           } 
/*      */           
/*  532 */           if (!rs.useLegacyDatetimeCode) {
/*  533 */             return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minute, seconds, nanos);
/*      */           }
/*      */ 
/*      */           
/*  537 */           Timestamp ts = rs.fastTimestampCreate(rs.getCalendarInstanceForSessionOrNew(), year, month, day, hour, minute, seconds, nanos);
/*      */ 
/*      */ 
/*      */           
/*  541 */           return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, ts, conn.getServerTimezoneTZ(), tz, rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  548 */         return rs.getNativeTimestampViaParseConversion(columnIndex + 1, targetCalendar, tz, rollForward);
/*      */     } 
/*      */ 
/*      */     
/*  552 */     throw new SQLException("Internal error - conversion method doesn't support this type", "S1000");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Object getNativeDateTimeValue(int paramInt1, Calendar paramCalendar, int paramInt2, int paramInt3, TimeZone paramTimeZone, boolean paramBoolean, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getNativeDouble(byte[] bits, int offset) {
/*  564 */     long valueAsLong = (bits[offset + 0] & 0xFF) | (bits[offset + 1] & 0xFF) << 8 | (bits[offset + 2] & 0xFF) << 16 | (bits[offset + 3] & 0xFF) << 24 | (bits[offset + 4] & 0xFF) << 32 | (bits[offset + 5] & 0xFF) << 40 | (bits[offset + 6] & 0xFF) << 48 | (bits[offset + 7] & 0xFF) << 56;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  573 */     return Double.longBitsToDouble(valueAsLong);
/*      */   }
/*      */   
/*      */   public abstract double getNativeDouble(int paramInt) throws SQLException;
/*      */   
/*      */   protected float getNativeFloat(byte[] bits, int offset) {
/*  579 */     int asInt = bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8 | (bits[offset + 2] & 0xFF) << 16 | (bits[offset + 3] & 0xFF) << 24;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     return Float.intBitsToFloat(asInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract float getNativeFloat(int paramInt) throws SQLException;
/*      */ 
/*      */   
/*  591 */   protected int getNativeInt(byte[] bits, int offset) { return bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8 | (bits[offset + 2] & 0xFF) << 16 | (bits[offset + 3] & 0xFF) << 24; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getNativeInt(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  602 */   protected long getNativeLong(byte[] bits, int offset) { return (bits[offset + 0] & 0xFF) | (bits[offset + 1] & 0xFF) << 8 | (bits[offset + 2] & 0xFF) << 16 | (bits[offset + 3] & 0xFF) << 24 | (bits[offset + 4] & 0xFF) << 32 | (bits[offset + 5] & 0xFF) << 40 | (bits[offset + 6] & 0xFF) << 48 | (bits[offset + 7] & 0xFF) << 56; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long getNativeLong(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  617 */   protected short getNativeShort(byte[] bits, int offset) { return (short)(bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract short getNativeShort(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Time getNativeTime(int columnIndex, byte[] bits, int offset, int length, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/*  629 */     int hour = 0;
/*  630 */     int minute = 0;
/*  631 */     int seconds = 0;
/*      */     
/*  633 */     if (length != 0) {
/*      */ 
/*      */       
/*  636 */       hour = bits[offset + 5];
/*  637 */       minute = bits[offset + 6];
/*  638 */       seconds = bits[offset + 7];
/*      */     } 
/*      */     
/*  641 */     if (!rs.useLegacyDatetimeCode) {
/*  642 */       return TimeUtil.fastTimeCreate(hour, minute, seconds, targetCalendar, this.exceptionInterceptor);
/*      */     }
/*      */     
/*  645 */     Calendar sessionCalendar = rs.getCalendarInstanceForSessionOrNew();
/*      */     
/*  647 */     synchronized (sessionCalendar) {
/*  648 */       Time time = TimeUtil.fastTimeCreate(sessionCalendar, hour, minute, seconds, this.exceptionInterceptor);
/*      */ 
/*      */       
/*  651 */       return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, time, conn.getServerTimezoneTZ(), tz, rollForward);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Time getNativeTime(int paramInt, Calendar paramCalendar, TimeZone paramTimeZone, boolean paramBoolean, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Timestamp getNativeTimestamp(byte[] bits, int offset, int length, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/*  666 */     int year = 0;
/*  667 */     int month = 0;
/*  668 */     int day = 0;
/*      */     
/*  670 */     int hour = 0;
/*  671 */     int minute = 0;
/*  672 */     int seconds = 0;
/*      */     
/*  674 */     int nanos = 0;
/*      */     
/*  676 */     if (length != 0) {
/*  677 */       year = bits[offset + 0] & 0xFF | (bits[offset + 1] & 0xFF) << 8;
/*  678 */       month = bits[offset + 2];
/*  679 */       day = bits[offset + 3];
/*      */       
/*  681 */       if (length > 4) {
/*  682 */         hour = bits[offset + 4];
/*  683 */         minute = bits[offset + 5];
/*  684 */         seconds = bits[offset + 6];
/*      */       } 
/*      */       
/*  687 */       if (length > 7)
/*      */       {
/*  689 */         nanos = (bits[offset + 7] & 0xFF | (bits[offset + 8] & 0xFF) << 8 | (bits[offset + 9] & 0xFF) << 16 | (bits[offset + 10] & 0xFF) << 24) * 1000;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  695 */     if (length == 0 || (year == 0 && month == 0 && day == 0)) {
/*  696 */       if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */       {
/*      */         
/*  699 */         return null; } 
/*  700 */       if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */       {
/*  702 */         throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009", this.exceptionInterceptor);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  708 */       year = 1;
/*  709 */       month = 1;
/*  710 */       day = 1;
/*      */     } 
/*      */     
/*  713 */     if (!rs.useLegacyDatetimeCode) {
/*  714 */       return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minute, seconds, nanos);
/*      */     }
/*      */ 
/*      */     
/*  718 */     Calendar sessionCalendar = conn.getUseJDBCCompliantTimezoneShift() ? conn.getUtcCalendar() : rs.getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */     
/*  722 */     synchronized (sessionCalendar) {
/*  723 */       Timestamp ts = rs.fastTimestampCreate(sessionCalendar, year, month, day, hour, minute, seconds, nanos);
/*      */ 
/*      */       
/*  726 */       return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, ts, conn.getServerTimezoneTZ(), tz, rollForward);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Timestamp getNativeTimestamp(int paramInt, Calendar paramCalendar, TimeZone paramTimeZone, boolean paramBoolean, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Reader getReader(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getString(int paramInt, String paramString, MySQLConnection paramMySQLConnection) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getString(String encoding, MySQLConnection conn, byte[] value, int offset, int length) throws SQLException {
/*  784 */     String stringVal = null;
/*      */     
/*  786 */     if (conn != null && conn.getUseUnicode()) {
/*      */       try {
/*  788 */         if (encoding == null) {
/*  789 */           stringVal = new String(value);
/*      */         } else {
/*  791 */           SingleByteCharsetConverter converter = conn.getCharsetConverter(encoding);
/*      */ 
/*      */           
/*  794 */           if (converter != null) {
/*  795 */             stringVal = converter.toString(value, offset, length);
/*      */           } else {
/*  797 */             stringVal = new String(value, offset, length, encoding);
/*      */           } 
/*      */         } 
/*  800 */       } catch (UnsupportedEncodingException E) {
/*  801 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Unsupported_character_encoding____101") + encoding + "'.", "0S100", this.exceptionInterceptor);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  808 */       stringVal = StringUtils.toAsciiString(value, offset, length);
/*      */     } 
/*      */     
/*  811 */     return stringVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Time getTimeFast(int columnIndex, byte[] timeAsBytes, int offset, int length, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/*  819 */     int hr = 0;
/*  820 */     int min = 0;
/*  821 */     int sec = 0;
/*      */ 
/*      */     
/*      */     try {
/*  825 */       if (timeAsBytes == null) {
/*  826 */         return null;
/*      */       }
/*      */       
/*  829 */       boolean allZeroTime = true;
/*  830 */       boolean onlyTimePresent = false;
/*      */       
/*  832 */       for (i = 0; i < length; i++) {
/*  833 */         if (timeAsBytes[offset + i] == 58) {
/*  834 */           onlyTimePresent = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  839 */       for (i = 0; i < length; i++) {
/*  840 */         byte b = timeAsBytes[offset + i];
/*      */         
/*  842 */         if (b == 32 || b == 45 || b == 47) {
/*  843 */           onlyTimePresent = false;
/*      */         }
/*      */         
/*  846 */         if (b != 48 && b != 32 && b != 58 && b != 45 && b != 47 && b != 46) {
/*      */           
/*  848 */           allZeroTime = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  854 */       if (!onlyTimePresent && allZeroTime) {
/*  855 */         if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */         {
/*  857 */           return null; } 
/*  858 */         if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */         {
/*  860 */           throw SQLError.createSQLException("Value '" + new String(timeAsBytes) + "' can not be represented as java.sql.Time", "S1009", this.exceptionInterceptor);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  868 */         return rs.fastTimeCreate(targetCalendar, 0, 0, 0);
/*      */       } 
/*      */       
/*  871 */       Field timeColField = this.metadata[columnIndex];
/*      */       
/*  873 */       if (timeColField.getMysqlType() == 7) {
/*      */         
/*  875 */         switch (length) {
/*      */           
/*      */           case 19:
/*  878 */             hr = StringUtils.getInt(timeAsBytes, offset + length - 8, offset + length - 6);
/*      */             
/*  880 */             min = StringUtils.getInt(timeAsBytes, offset + length - 5, offset + length - 3);
/*      */             
/*  882 */             sec = StringUtils.getInt(timeAsBytes, offset + length - 2, offset + length);
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/*      */           case 14:
/*  889 */             hr = StringUtils.getInt(timeAsBytes, offset + length - 6, offset + length - 4);
/*      */             
/*  891 */             min = StringUtils.getInt(timeAsBytes, offset + length - 4, offset + length - 2);
/*      */             
/*  893 */             sec = StringUtils.getInt(timeAsBytes, offset + length - 2, offset + length);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/*  900 */             hr = StringUtils.getInt(timeAsBytes, offset + 6, offset + 8);
/*      */             
/*  902 */             min = StringUtils.getInt(timeAsBytes, offset + 8, offset + 10);
/*      */             
/*  904 */             sec = 0;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  910 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Timestamp_too_small_to_convert_to_Time_value_in_column__257") + (columnIndex + 1) + "(" + timeColField + ").", "S1009", this.exceptionInterceptor);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  920 */         SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_TIMESTAMP_to_Time_with_getTime()_on_column__261") + columnIndex + "(" + timeColField + ").");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  929 */       else if (timeColField.getMysqlType() == 12) {
/*  930 */         hr = StringUtils.getInt(timeAsBytes, offset + 11, offset + 13);
/*  931 */         min = StringUtils.getInt(timeAsBytes, offset + 14, offset + 16);
/*  932 */         sec = StringUtils.getInt(timeAsBytes, offset + 17, offset + 19);
/*      */         
/*  934 */         SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_DATETIME_to_Time_with_getTime()_on_column__264") + (columnIndex + 1) + "(" + timeColField + ").");
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  944 */         if (timeColField.getMysqlType() == 10) {
/*  945 */           return rs.fastTimeCreate(null, 0, 0, 0);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  950 */         if (length != 5 && length != 8) {
/*  951 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Time____267") + new String(timeAsBytes) + Messages.getString("ResultSet.___in_column__268") + (columnIndex + 1), "S1009", this.exceptionInterceptor);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  959 */         hr = StringUtils.getInt(timeAsBytes, offset + 0, offset + 2);
/*  960 */         min = StringUtils.getInt(timeAsBytes, offset + 3, offset + 5);
/*  961 */         sec = (length == 5) ? 0 : StringUtils.getInt(timeAsBytes, offset + 6, offset + 8);
/*      */       } 
/*      */ 
/*      */       
/*  965 */       Calendar sessionCalendar = rs.getCalendarInstanceForSessionOrNew();
/*      */       
/*  967 */       if (!rs.useLegacyDatetimeCode) {
/*  968 */         return rs.fastTimeCreate(targetCalendar, hr, min, sec);
/*      */       }
/*      */       
/*  971 */       synchronized (sessionCalendar) {
/*  972 */         return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, rs.fastTimeCreate(sessionCalendar, hr, min, sec), conn.getServerTimezoneTZ(), tz, rollForward);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  977 */     catch (Exception ex) {
/*  978 */       SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1009", this.exceptionInterceptor);
/*      */       
/*  980 */       sqlEx.initCause(ex);
/*      */       
/*  982 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Time getTimeFast(int paramInt, Calendar paramCalendar, TimeZone paramTimeZone, boolean paramBoolean, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Timestamp getTimestampFast(int columnIndex, byte[] timestampAsBytes, int offset, int length, Calendar targetCalendar, TimeZone tz, boolean rollForward, MySQLConnection conn, ResultSetImpl rs) throws SQLException {
/*      */     try {
/*  996 */       Calendar sessionCalendar = conn.getUseJDBCCompliantTimezoneShift() ? conn.getUtcCalendar() : rs.getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */       
/* 1000 */       synchronized (sessionCalendar) {
/* 1001 */         int i; boolean hasColon, hasDash, allZeroTimestamp = true;
/*      */         
/* 1003 */         boolean onlyTimePresent = false;
/*      */         
/* 1005 */         for (i = 0; i < length; i++) {
/* 1006 */           if (timestampAsBytes[offset + i] == 58) {
/* 1007 */             onlyTimePresent = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1012 */         for (i = 0; i < length; i++) {
/* 1013 */           byte b = timestampAsBytes[offset + i];
/*      */           
/* 1015 */           if (b == 32 || b == 45 || b == 47) {
/* 1016 */             onlyTimePresent = false;
/*      */           }
/*      */           
/* 1019 */           if (b != 48 && b != 32 && b != 58 && b != 45 && b != 47 && b != 46) {
/*      */             
/* 1021 */             allZeroTimestamp = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/* 1027 */         if (!onlyTimePresent && allZeroTimestamp) {
/*      */           
/* 1029 */           if ("convertToNull".equals(conn.getZeroDateTimeBehavior()))
/*      */           {
/*      */             
/* 1032 */             return null; } 
/* 1033 */           if ("exception".equals(conn.getZeroDateTimeBehavior()))
/*      */           {
/* 1035 */             throw SQLError.createSQLException("Value '" + timestampAsBytes + "' can not be represented as java.sql.Timestamp", "S1009", this.exceptionInterceptor);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1043 */           if (!rs.useLegacyDatetimeCode) {
/* 1044 */             return TimeUtil.fastTimestampCreate(tz, 1, 1, 1, 0, 0, 0, 0);
/*      */           }
/*      */ 
/*      */           
/* 1048 */           return rs.fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
/*      */         } 
/* 1050 */         if (this.metadata[columnIndex].getMysqlType() == 13) {
/*      */           
/* 1052 */           if (!rs.useLegacyDatetimeCode) {
/* 1053 */             return TimeUtil.fastTimestampCreate(tz, StringUtils.getInt(timestampAsBytes, offset, 4), 1, 1, 0, 0, 0, 0);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1058 */           return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, rs.fastTimestampCreate(sessionCalendar, StringUtils.getInt(timestampAsBytes, offset, 4), 1, 1, 0, 0, 0, 0), conn.getServerTimezoneTZ(), tz, rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1065 */         if (timestampAsBytes[offset + length - 1] == 46) {
/* 1066 */           length--;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1071 */         int year = 0;
/* 1072 */         int month = 0;
/* 1073 */         int day = 0;
/* 1074 */         int hour = 0;
/* 1075 */         int minutes = 0;
/* 1076 */         int seconds = 0;
/* 1077 */         int nanos = 0;
/*      */         
/* 1079 */         switch (length) {
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 29:
/* 1089 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 4);
/*      */             
/* 1091 */             month = StringUtils.getInt(timestampAsBytes, offset + 5, offset + 7);
/*      */             
/* 1093 */             day = StringUtils.getInt(timestampAsBytes, offset + 8, offset + 10);
/*      */             
/* 1095 */             hour = StringUtils.getInt(timestampAsBytes, offset + 11, offset + 13);
/*      */             
/* 1097 */             minutes = StringUtils.getInt(timestampAsBytes, offset + 14, offset + 16);
/*      */             
/* 1099 */             seconds = StringUtils.getInt(timestampAsBytes, offset + 17, offset + 19);
/*      */ 
/*      */             
/* 1102 */             nanos = 0;
/*      */             
/* 1104 */             if (length > 19) {
/* 1105 */               int decimalIndex = -1;
/*      */               
/* 1107 */               for (int i = 0; i < length; i++) {
/* 1108 */                 if (timestampAsBytes[offset + i] == 46) {
/* 1109 */                   decimalIndex = i;
/*      */                 }
/*      */               } 
/*      */               
/* 1113 */               if (decimalIndex != -1) {
/* 1114 */                 if (decimalIndex + 2 <= length) {
/* 1115 */                   nanos = StringUtils.getInt(timestampAsBytes, decimalIndex + 1, offset + length);
/*      */ 
/*      */ 
/*      */                   
/* 1119 */                   int numDigits = offset + length - decimalIndex + 1;
/*      */                   
/* 1121 */                   if (numDigits < 9) {
/* 1122 */                     int factor = (int)Math.pow(10.0D, (9 - numDigits));
/* 1123 */                     nanos *= factor;
/*      */                   }  break;
/*      */                 } 
/* 1126 */                 throw new IllegalArgumentException();
/*      */               } 
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 14:
/* 1140 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 4);
/*      */             
/* 1142 */             month = StringUtils.getInt(timestampAsBytes, offset + 4, offset + 6);
/*      */             
/* 1144 */             day = StringUtils.getInt(timestampAsBytes, offset + 6, offset + 8);
/*      */             
/* 1146 */             hour = StringUtils.getInt(timestampAsBytes, offset + 8, offset + 10);
/*      */             
/* 1148 */             minutes = StringUtils.getInt(timestampAsBytes, offset + 10, offset + 12);
/*      */             
/* 1150 */             seconds = StringUtils.getInt(timestampAsBytes, offset + 12, offset + 14);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/* 1157 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/* 1160 */             if (year <= 69) {
/* 1161 */               year += 100;
/*      */             }
/*      */             
/* 1164 */             year += 1900;
/*      */             
/* 1166 */             month = StringUtils.getInt(timestampAsBytes, offset + 2, offset + 4);
/*      */             
/* 1168 */             day = StringUtils.getInt(timestampAsBytes, offset + 4, offset + 6);
/*      */             
/* 1170 */             hour = StringUtils.getInt(timestampAsBytes, offset + 6, offset + 8);
/*      */             
/* 1172 */             minutes = StringUtils.getInt(timestampAsBytes, offset + 8, offset + 10);
/*      */             
/* 1174 */             seconds = StringUtils.getInt(timestampAsBytes, offset + 10, offset + 12);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/* 1181 */             hasDash = false;
/*      */             
/* 1183 */             for (i = 0; i < length; i++) {
/* 1184 */               if (timestampAsBytes[offset + i] == 45) {
/* 1185 */                 hasDash = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1190 */             if (this.metadata[columnIndex].getMysqlType() == 10 || hasDash) {
/*      */               
/* 1192 */               year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 4);
/*      */               
/* 1194 */               month = StringUtils.getInt(timestampAsBytes, offset + 5, offset + 7);
/*      */               
/* 1196 */               day = StringUtils.getInt(timestampAsBytes, offset + 8, offset + 10);
/*      */               
/* 1198 */               hour = 0;
/* 1199 */               minutes = 0; break;
/*      */             } 
/* 1201 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/* 1204 */             if (year <= 69) {
/* 1205 */               year += 100;
/*      */             }
/*      */             
/* 1208 */             month = StringUtils.getInt(timestampAsBytes, offset + 2, offset + 4);
/*      */             
/* 1210 */             day = StringUtils.getInt(timestampAsBytes, offset + 4, offset + 6);
/*      */             
/* 1212 */             hour = StringUtils.getInt(timestampAsBytes, offset + 6, offset + 8);
/*      */             
/* 1214 */             minutes = StringUtils.getInt(timestampAsBytes, offset + 8, offset + 10);
/*      */ 
/*      */             
/* 1217 */             year += 1900;
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 8:
/* 1224 */             hasColon = false;
/*      */             
/* 1226 */             for (i = 0; i < length; i++) {
/* 1227 */               if (timestampAsBytes[offset + i] == 58) {
/* 1228 */                 hasColon = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1233 */             if (hasColon) {
/* 1234 */               hour = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */               
/* 1236 */               minutes = StringUtils.getInt(timestampAsBytes, offset + 3, offset + 5);
/*      */               
/* 1238 */               seconds = StringUtils.getInt(timestampAsBytes, offset + 6, offset + 8);
/*      */ 
/*      */               
/* 1241 */               year = 1970;
/* 1242 */               month = 1;
/* 1243 */               day = 1;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 1248 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 4);
/*      */             
/* 1250 */             month = StringUtils.getInt(timestampAsBytes, offset + 4, offset + 6);
/*      */             
/* 1252 */             day = StringUtils.getInt(timestampAsBytes, offset + 6, offset + 8);
/*      */ 
/*      */             
/* 1255 */             year -= 1900;
/* 1256 */             month--;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 6:
/* 1262 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/* 1265 */             if (year <= 69) {
/* 1266 */               year += 100;
/*      */             }
/*      */             
/* 1269 */             year += 1900;
/*      */             
/* 1271 */             month = StringUtils.getInt(timestampAsBytes, offset + 2, offset + 4);
/*      */             
/* 1273 */             day = StringUtils.getInt(timestampAsBytes, offset + 4, offset + 6);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/* 1280 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/* 1283 */             if (year <= 69) {
/* 1284 */               year += 100;
/*      */             }
/*      */             
/* 1287 */             month = StringUtils.getInt(timestampAsBytes, offset + 2, offset + 4);
/*      */ 
/*      */             
/* 1290 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 1296 */             year = StringUtils.getInt(timestampAsBytes, offset + 0, offset + 2);
/*      */ 
/*      */             
/* 1299 */             if (year <= 69) {
/* 1300 */               year += 100;
/*      */             }
/*      */             
/* 1303 */             year += 1900;
/* 1304 */             month = 1;
/* 1305 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 1311 */             throw new SQLException("Bad format for Timestamp '" + new String(timestampAsBytes) + "' in column " + (columnIndex + 1) + ".", "S1009");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1319 */         if (!rs.useLegacyDatetimeCode) {
/* 1320 */           return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minutes, seconds, nanos);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1326 */         return TimeUtil.changeTimezone(conn, sessionCalendar, targetCalendar, rs.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), conn.getServerTimezoneTZ(), tz, rollForward);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1336 */     catch (Exception e) {
/* 1337 */       SQLException sqlEx = SQLError.createSQLException("Cannot convert value '" + getString(columnIndex, "ISO8859_1", conn) + "' from column " + (columnIndex + 1) + " to TIMESTAMP.", "S1009", this.exceptionInterceptor);
/*      */ 
/*      */ 
/*      */       
/* 1341 */       sqlEx.initCause(e);
/*      */       
/* 1343 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Timestamp getTimestampFast(int paramInt, Calendar paramCalendar, TimeZone paramTimeZone, boolean paramBoolean, MySQLConnection paramMySQLConnection, ResultSetImpl paramResultSetImpl) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isFloatingPointNumber(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isNull(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long length(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setColumnValue(int paramInt, byte[] paramArrayOfByte) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetRow setMetadata(Field[] f) throws SQLException {
/* 1411 */     this.metadata = f;
/*      */     
/* 1413 */     return this;
/*      */   }
/*      */   
/*      */   public abstract int getBytesSize();
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ResultSetRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */