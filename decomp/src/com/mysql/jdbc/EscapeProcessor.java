/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ class EscapeProcessor
/*     */ {
/*     */   private static Map JDBC_CONVERT_TO_MYSQL_TYPE_MAP;
/*     */   private static Map JDBC_NO_CONVERT_TO_MYSQL_EXPRESSION_MAP;
/*     */   
/*     */   static  {
/*  52 */     tempMap = new HashMap();
/*     */     
/*  54 */     tempMap.put("BIGINT", "0 + ?");
/*  55 */     tempMap.put("BINARY", "BINARY");
/*  56 */     tempMap.put("BIT", "0 + ?");
/*  57 */     tempMap.put("CHAR", "CHAR");
/*  58 */     tempMap.put("DATE", "DATE");
/*  59 */     tempMap.put("DECIMAL", "0.0 + ?");
/*  60 */     tempMap.put("DOUBLE", "0.0 + ?");
/*  61 */     tempMap.put("FLOAT", "0.0 + ?");
/*  62 */     tempMap.put("INTEGER", "0 + ?");
/*  63 */     tempMap.put("LONGVARBINARY", "BINARY");
/*  64 */     tempMap.put("LONGVARCHAR", "CONCAT(?)");
/*  65 */     tempMap.put("REAL", "0.0 + ?");
/*  66 */     tempMap.put("SMALLINT", "CONCAT(?)");
/*  67 */     tempMap.put("TIME", "TIME");
/*  68 */     tempMap.put("TIMESTAMP", "DATETIME");
/*  69 */     tempMap.put("TINYINT", "CONCAT(?)");
/*  70 */     tempMap.put("VARBINARY", "BINARY");
/*  71 */     tempMap.put("VARCHAR", "CONCAT(?)");
/*     */     
/*  73 */     JDBC_CONVERT_TO_MYSQL_TYPE_MAP = Collections.unmodifiableMap(tempMap);
/*     */     
/*  75 */     tempMap = new HashMap(JDBC_CONVERT_TO_MYSQL_TYPE_MAP);
/*     */     
/*  77 */     tempMap.put("BINARY", "CONCAT(?)");
/*  78 */     tempMap.put("CHAR", "CONCAT(?)");
/*  79 */     tempMap.remove("DATE");
/*  80 */     tempMap.put("LONGVARBINARY", "CONCAT(?)");
/*  81 */     tempMap.remove("TIME");
/*  82 */     tempMap.remove("TIMESTAMP");
/*  83 */     tempMap.put("VARBINARY", "CONCAT(?)");
/*     */     
/*  85 */     JDBC_NO_CONVERT_TO_MYSQL_EXPRESSION_MAP = Collections.unmodifiableMap(tempMap);
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
/*     */   public static final Object escapeSQL(String sql, boolean serverSupportsConvertFn, MySQLConnection conn) throws SQLException {
/* 106 */     boolean replaceEscapeSequence = false;
/* 107 */     String escapeSequence = null;
/*     */     
/* 109 */     if (sql == null) {
/* 110 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     int beginBrace = sql.indexOf('{');
/* 118 */     int nextEndBrace = (beginBrace == -1) ? -1 : sql.indexOf('}', beginBrace);
/*     */ 
/*     */     
/* 121 */     if (nextEndBrace == -1) {
/* 122 */       return sql;
/*     */     }
/*     */     
/* 125 */     StringBuffer newSql = new StringBuffer();
/*     */     
/* 127 */     EscapeTokenizer escapeTokenizer = new EscapeTokenizer(sql);
/*     */     
/* 129 */     byte usesVariables = 0;
/* 130 */     boolean callingStoredFunction = false;
/*     */     
/* 132 */     while (escapeTokenizer.hasMoreTokens()) {
/* 133 */       String token = escapeTokenizer.nextToken();
/*     */       
/* 135 */       if (token.length() != 0) {
/* 136 */         if (token.charAt(0) == '{') {
/*     */           
/* 138 */           if (!token.endsWith("}")) {
/* 139 */             throw SQLError.createSQLException("Not a valid escape sequence: " + token, conn.getExceptionInterceptor());
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 144 */           if (token.length() > 2) {
/* 145 */             int nestedBrace = token.indexOf('{', 2);
/*     */             
/* 147 */             if (nestedBrace != -1) {
/* 148 */               StringBuffer buf = new StringBuffer(token.substring(0, 1));
/*     */ 
/*     */               
/* 151 */               Object remainingResults = escapeSQL(token.substring(1, token.length() - 1), serverSupportsConvertFn, conn);
/*     */ 
/*     */ 
/*     */               
/* 155 */               String remaining = null;
/*     */               
/* 157 */               if (remainingResults instanceof String) {
/* 158 */                 remaining = (String)remainingResults;
/*     */               } else {
/* 160 */                 remaining = ((EscapeProcessorResult)remainingResults).escapedSql;
/*     */                 
/* 162 */                 if (usesVariables != 1) {
/* 163 */                   usesVariables = ((EscapeProcessorResult)remainingResults).usesVariables;
/*     */                 }
/*     */               } 
/*     */               
/* 167 */               buf.append(remaining);
/*     */               
/* 169 */               buf.append('}');
/*     */               
/* 171 */               token = buf.toString();
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 177 */           String collapsedToken = removeWhitespace(token);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 182 */           if (StringUtils.startsWithIgnoreCase(collapsedToken, "{escape")) {
/*     */             
/*     */             try {
/* 185 */               StringTokenizer st = new StringTokenizer(token, " '");
/*     */               
/* 187 */               st.nextToken();
/* 188 */               escapeSequence = st.nextToken();
/*     */               
/* 190 */               if (escapeSequence.length() < 3) {
/* 191 */                 newSql.append(token);
/*     */ 
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/* 197 */               escapeSequence = escapeSequence.substring(1, escapeSequence.length() - 1);
/*     */               
/* 199 */               replaceEscapeSequence = true;
/*     */               continue;
/* 201 */             } catch (NoSuchElementException e) {
/* 202 */               newSql.append(token);
/*     */               
/*     */               continue;
/*     */             } 
/*     */           }
/* 207 */           if (StringUtils.startsWithIgnoreCase(collapsedToken, "{fn")) {
/*     */             
/* 209 */             int startPos = token.toLowerCase().indexOf("fn ") + 3;
/* 210 */             int endPos = token.length() - 1;
/*     */             
/* 212 */             String fnToken = token.substring(startPos, endPos);
/*     */ 
/*     */ 
/*     */             
/* 216 */             if (StringUtils.startsWithIgnoreCaseAndWs(fnToken, "convert")) {
/*     */               
/* 218 */               newSql.append(processConvertToken(fnToken, serverSupportsConvertFn, conn));
/*     */               
/*     */               continue;
/*     */             } 
/* 222 */             newSql.append(fnToken); continue;
/*     */           } 
/* 224 */           if (StringUtils.startsWithIgnoreCase(collapsedToken, "{d")) {
/*     */             
/* 226 */             int startPos = token.indexOf('\'') + 1;
/* 227 */             int endPos = token.lastIndexOf('\'');
/*     */             
/* 229 */             if (startPos == -1 || endPos == -1) {
/* 230 */               newSql.append(token);
/*     */ 
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 236 */             String argument = token.substring(startPos, endPos);
/*     */             
/*     */             try {
/* 239 */               StringTokenizer st = new StringTokenizer(argument, " -");
/*     */               
/* 241 */               String year4 = st.nextToken();
/* 242 */               String month2 = st.nextToken();
/* 243 */               String day2 = st.nextToken();
/* 244 */               String dateString = "'" + year4 + "-" + month2 + "-" + day2 + "'";
/*     */               
/* 246 */               newSql.append(dateString); continue;
/* 247 */             } catch (NoSuchElementException e) {
/* 248 */               throw SQLError.createSQLException("Syntax error for DATE escape sequence '" + argument + "'", "42000", conn.getExceptionInterceptor());
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 253 */           if (StringUtils.startsWithIgnoreCase(collapsedToken, "{ts")) {
/*     */             
/* 255 */             processTimestampToken(conn, newSql, token); continue;
/* 256 */           }  if (StringUtils.startsWithIgnoreCase(collapsedToken, "{t")) {
/*     */             
/* 258 */             processTimeToken(conn, newSql, token); continue;
/* 259 */           }  if (StringUtils.startsWithIgnoreCase(collapsedToken, "{call") || StringUtils.startsWithIgnoreCase(collapsedToken, "{?=call")) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 264 */             int startPos = StringUtils.indexOfIgnoreCase(token, "CALL") + 5;
/*     */             
/* 266 */             int endPos = token.length() - 1;
/*     */             
/* 268 */             if (StringUtils.startsWithIgnoreCase(collapsedToken, "{?=call")) {
/*     */               
/* 270 */               callingStoredFunction = true;
/* 271 */               newSql.append("SELECT ");
/* 272 */               newSql.append(token.substring(startPos, endPos));
/*     */             } else {
/* 274 */               callingStoredFunction = false;
/* 275 */               newSql.append("CALL ");
/* 276 */               newSql.append(token.substring(startPos, endPos));
/*     */             } 
/*     */             
/* 279 */             for (int i = endPos - 1; i >= startPos; ) {
/* 280 */               char c = token.charAt(i);
/*     */               
/* 282 */               if (Character.isWhitespace(c)) {
/*     */                 i--;
/*     */                 continue;
/*     */               } 
/* 286 */               if (c != ')') {
/* 287 */                 newSql.append("()");
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 295 */           if (StringUtils.startsWithIgnoreCase(collapsedToken, "{oj"))
/*     */           {
/*     */ 
/*     */             
/* 299 */             newSql.append(token); } 
/*     */           continue;
/*     */         } 
/* 302 */         newSql.append(token);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 307 */     String escapedSql = newSql.toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     if (replaceEscapeSequence) {
/* 314 */       String currentSql = escapedSql;
/*     */       
/* 316 */       while (currentSql.indexOf(escapeSequence) != -1) {
/* 317 */         int escapePos = currentSql.indexOf(escapeSequence);
/* 318 */         String lhs = currentSql.substring(0, escapePos);
/* 319 */         String rhs = currentSql.substring(escapePos + 1, currentSql.length());
/*     */         
/* 321 */         currentSql = lhs + "\\" + rhs;
/*     */       } 
/*     */       
/* 324 */       escapedSql = currentSql;
/*     */     } 
/*     */     
/* 327 */     EscapeProcessorResult epr = new EscapeProcessorResult();
/* 328 */     epr.escapedSql = escapedSql;
/* 329 */     epr.callingStoredFunction = callingStoredFunction;
/*     */     
/* 331 */     if (usesVariables != 1) {
/* 332 */       if (escapeTokenizer.sawVariableUse()) {
/* 333 */         epr.usesVariables = 1;
/*     */       } else {
/* 335 */         epr.usesVariables = 0;
/*     */       } 
/*     */     }
/*     */     
/* 339 */     return epr;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processTimeToken(MySQLConnection conn, StringBuffer newSql, String token) throws SQLException {
/* 344 */     int startPos = token.indexOf('\'') + 1;
/* 345 */     int endPos = token.lastIndexOf('\'');
/*     */     
/* 347 */     if (startPos == -1 || endPos == -1) {
/* 348 */       newSql.append(token);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 354 */       String argument = token.substring(startPos, endPos);
/*     */       
/*     */       try {
/* 357 */         StringTokenizer st = new StringTokenizer(argument, " :");
/*     */         
/* 359 */         String hour = st.nextToken();
/* 360 */         String minute = st.nextToken();
/* 361 */         String second = st.nextToken();
/*     */         
/* 363 */         if (!conn.getUseTimezone() || !conn.getUseLegacyDatetimeCode()) {
/*     */           
/* 365 */           String timeString = "'" + hour + ":" + minute + ":" + second + "'";
/*     */           
/* 367 */           newSql.append(timeString);
/*     */         } else {
/* 369 */           Calendar sessionCalendar = null;
/*     */           
/* 371 */           if (conn != null) {
/* 372 */             sessionCalendar = conn.getCalendarInstanceForSessionOrNew();
/*     */           } else {
/*     */             
/* 375 */             sessionCalendar = new GregorianCalendar();
/*     */           } 
/*     */           
/*     */           try {
/* 379 */             int hourInt = Integer.parseInt(hour);
/* 380 */             int minuteInt = Integer.parseInt(minute);
/*     */             
/* 382 */             int secondInt = Integer.parseInt(second);
/*     */ 
/*     */             
/* 385 */             synchronized (sessionCalendar) {
/* 386 */               Time toBeAdjusted = TimeUtil.fastTimeCreate(sessionCalendar, hourInt, minuteInt, secondInt, conn.getExceptionInterceptor());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 392 */               Time inServerTimezone = TimeUtil.changeTimezone(conn, sessionCalendar, null, toBeAdjusted, sessionCalendar.getTimeZone(), conn.getServerTimezoneTZ(), false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 404 */               newSql.append("'");
/* 405 */               newSql.append(inServerTimezone.toString());
/*     */               
/* 407 */               newSql.append("'");
/*     */             }
/*     */           
/* 410 */           } catch (NumberFormatException nfe) {
/* 411 */             throw SQLError.createSQLException("Syntax error in TIMESTAMP escape sequence '" + token + "'.", "S1009", conn.getExceptionInterceptor());
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 418 */       catch (NoSuchElementException e) {
/* 419 */         throw SQLError.createSQLException("Syntax error for escape sequence '" + argument + "'", "42000", conn.getExceptionInterceptor());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processTimestampToken(MySQLConnection conn, StringBuffer newSql, String token) throws SQLException {
/* 428 */     int startPos = token.indexOf('\'') + 1;
/* 429 */     int endPos = token.lastIndexOf('\'');
/*     */     
/* 431 */     if (startPos == -1 || endPos == -1) {
/* 432 */       newSql.append(token);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 438 */       String argument = token.substring(startPos, endPos);
/*     */       
/*     */       try {
/* 441 */         if (!conn.getUseLegacyDatetimeCode()) {
/* 442 */           Timestamp ts = Timestamp.valueOf(argument);
/* 443 */           SimpleDateFormat tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''", Locale.US);
/*     */ 
/*     */           
/* 446 */           tsdf.setTimeZone(conn.getServerTimezoneTZ());
/*     */ 
/*     */ 
/*     */           
/* 450 */           newSql.append(tsdf.format(ts));
/*     */         } else {
/* 452 */           StringTokenizer st = new StringTokenizer(argument, " .-:");
/*     */           
/*     */           try {
/* 455 */             String year4 = st.nextToken();
/* 456 */             String month2 = st.nextToken();
/* 457 */             String day2 = st.nextToken();
/* 458 */             String hour = st.nextToken();
/* 459 */             String minute = st.nextToken();
/* 460 */             String second = st.nextToken();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 493 */             if (!conn.getUseTimezone() && !conn.getUseJDBCCompliantTimezoneShift()) {
/*     */ 
/*     */               
/* 496 */               newSql.append("'").append(year4).append("-").append(month2).append("-").append(day2).append(" ").append(hour).append(":").append(minute).append(":").append(second).append("'");
/*     */             } else {
/*     */               Calendar sessionCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 506 */               if (conn != null) {
/* 507 */                 sessionCalendar = conn.getCalendarInstanceForSessionOrNew();
/*     */               } else {
/*     */                 
/* 510 */                 sessionCalendar = new GregorianCalendar();
/* 511 */                 sessionCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 517 */                 int year4Int = Integer.parseInt(year4);
/*     */                 
/* 519 */                 int month2Int = Integer.parseInt(month2);
/*     */                 
/* 521 */                 int day2Int = Integer.parseInt(day2);
/*     */                 
/* 523 */                 int hourInt = Integer.parseInt(hour);
/*     */                 
/* 525 */                 int minuteInt = Integer.parseInt(minute);
/*     */                 
/* 527 */                 int secondInt = Integer.parseInt(second);
/*     */ 
/*     */                 
/* 530 */                 synchronized (sessionCalendar) {
/* 531 */                   boolean useGmtMillis = conn.getUseGmtMillisForDatetimes();
/*     */ 
/*     */                   
/* 534 */                   Timestamp toBeAdjusted = TimeUtil.fastTimestampCreate(useGmtMillis, useGmtMillis ? Calendar.getInstance(TimeZone.getTimeZone("GMT")) : null, sessionCalendar, year4Int, month2Int, day2Int, hourInt, minuteInt, secondInt, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 550 */                   Timestamp inServerTimezone = TimeUtil.changeTimezone(conn, sessionCalendar, null, toBeAdjusted, sessionCalendar.getTimeZone(), conn.getServerTimezoneTZ(), false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 562 */                   newSql.append("'");
/*     */                   
/* 564 */                   String timezoneLiteral = inServerTimezone.toString();
/*     */ 
/*     */                   
/* 567 */                   int indexOfDot = timezoneLiteral.indexOf(".");
/*     */ 
/*     */                   
/* 570 */                   if (indexOfDot != -1) {
/* 571 */                     timezoneLiteral = timezoneLiteral.substring(0, indexOfDot);
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/* 576 */                   newSql.append(timezoneLiteral);
/*     */                 } 
/*     */ 
/*     */                 
/* 580 */                 newSql.append("'");
/*     */               }
/* 582 */               catch (NumberFormatException nfe) {
/* 583 */                 throw SQLError.createSQLException("Syntax error in TIMESTAMP escape sequence '" + token + "'.", "S1009", conn.getExceptionInterceptor());
/*     */               
/*     */               }
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 591 */           catch (NoSuchElementException e) {
/* 592 */             throw SQLError.createSQLException("Syntax error for TIMESTAMP escape sequence '" + argument + "'", "42000", conn.getExceptionInterceptor());
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 598 */       catch (IllegalArgumentException illegalArgumentException) {
/* 599 */         SQLException sqlEx = SQLError.createSQLException("Syntax error for TIMESTAMP escape sequence '" + argument + "'", "42000", conn.getExceptionInterceptor());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 604 */         sqlEx.initCause(illegalArgumentException);
/*     */         
/* 606 */         throw sqlEx;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String processConvertToken(String functionToken, boolean serverSupportsConvertFn, MySQLConnection conn) throws SQLException {
/* 651 */     int firstIndexOfParen = functionToken.indexOf("(");
/*     */     
/* 653 */     if (firstIndexOfParen == -1) {
/* 654 */       throw SQLError.createSQLException("Syntax error while processing {fn convert (... , ...)} token, missing opening parenthesis in token '" + functionToken + "'.", "42000", conn.getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 661 */     int tokenLength = functionToken.length();
/*     */     
/* 663 */     int indexOfComma = functionToken.lastIndexOf(",");
/*     */     
/* 665 */     if (indexOfComma == -1) {
/* 666 */       throw SQLError.createSQLException("Syntax error while processing {fn convert (... , ...)} token, missing comma in token '" + functionToken + "'.", "42000", conn.getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 673 */     int indexOfCloseParen = functionToken.indexOf(')', indexOfComma);
/*     */     
/* 675 */     if (indexOfCloseParen == -1) {
/* 676 */       throw SQLError.createSQLException("Syntax error while processing {fn convert (... , ...)} token, missing closing parenthesis in token '" + functionToken + "'.", "42000", conn.getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 684 */     String expression = functionToken.substring(firstIndexOfParen + 1, indexOfComma);
/*     */     
/* 686 */     String type = functionToken.substring(indexOfComma + 1, indexOfCloseParen);
/*     */ 
/*     */     
/* 689 */     String newType = null;
/*     */     
/* 691 */     String trimmedType = type.trim();
/*     */     
/* 693 */     if (StringUtils.startsWithIgnoreCase(trimmedType, "SQL_")) {
/* 694 */       trimmedType = trimmedType.substring(4, trimmedType.length());
/*     */     }
/*     */     
/* 697 */     if (serverSupportsConvertFn) {
/* 698 */       newType = (String)JDBC_CONVERT_TO_MYSQL_TYPE_MAP.get(trimmedType.toUpperCase(Locale.ENGLISH));
/*     */     } else {
/*     */       
/* 701 */       newType = (String)JDBC_NO_CONVERT_TO_MYSQL_EXPRESSION_MAP.get(trimmedType.toUpperCase(Locale.ENGLISH));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 711 */       if (newType == null) {
/* 712 */         throw SQLError.createSQLException("Can't find conversion re-write for type '" + type + "' that is applicable for this server version while processing escape tokens.", "S1000", conn.getExceptionInterceptor());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 721 */     if (newType == null) {
/* 722 */       throw SQLError.createSQLException("Unsupported conversion type '" + type.trim() + "' found while processing escape token.", "S1000", conn.getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 727 */     int replaceIndex = newType.indexOf("?");
/*     */     
/* 729 */     if (replaceIndex != -1) {
/* 730 */       StringBuffer convertRewrite = new StringBuffer(newType.substring(0, replaceIndex));
/*     */       
/* 732 */       convertRewrite.append(expression);
/* 733 */       convertRewrite.append(newType.substring(replaceIndex + 1, newType.length()));
/*     */ 
/*     */       
/* 736 */       return convertRewrite.toString();
/*     */     } 
/*     */     
/* 739 */     StringBuffer castRewrite = new StringBuffer("CAST(");
/* 740 */     castRewrite.append(expression);
/* 741 */     castRewrite.append(" AS ");
/* 742 */     castRewrite.append(newType);
/* 743 */     castRewrite.append(")");
/*     */     
/* 745 */     return castRewrite.toString();
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
/*     */   private static String removeWhitespace(String toCollapse) {
/* 759 */     if (toCollapse == null) {
/* 760 */       return null;
/*     */     }
/*     */     
/* 763 */     int length = toCollapse.length();
/*     */     
/* 765 */     StringBuffer collapsed = new StringBuffer(length);
/*     */     
/* 767 */     for (int i = 0; i < length; i++) {
/* 768 */       char c = toCollapse.charAt(i);
/*     */       
/* 770 */       if (!Character.isWhitespace(c)) {
/* 771 */         collapsed.append(c);
/*     */       }
/*     */     } 
/*     */     
/* 775 */     return collapsed.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\EscapeProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */