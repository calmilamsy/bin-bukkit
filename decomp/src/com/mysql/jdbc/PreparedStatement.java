/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.sql.Array;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.Date;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PreparedStatement
/*      */   extends StatementImpl
/*      */   implements PreparedStatement
/*      */ {
/*      */   private static final Constructor JDBC_4_PSTMT_2_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_PSTMT_3_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_PSTMT_4_ARG_CTOR;
/*      */   private static final byte[] HEX_DIGITS;
/*      */   
/*      */   static  {
/*   99 */     if (Util.isJdbc4()) {
/*      */       try {
/*  101 */         JDBC_4_PSTMT_2_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class });
/*      */ 
/*      */ 
/*      */         
/*  105 */         JDBC_4_PSTMT_3_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  110 */         JDBC_4_PSTMT_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class, ParseInfo.class });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  115 */       catch (SecurityException e) {
/*  116 */         throw new RuntimeException(e);
/*  117 */       } catch (NoSuchMethodException e) {
/*  118 */         throw new RuntimeException(e);
/*  119 */       } catch (ClassNotFoundException e) {
/*  120 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*  123 */       JDBC_4_PSTMT_2_ARG_CTOR = null;
/*  124 */       JDBC_4_PSTMT_3_ARG_CTOR = null;
/*  125 */       JDBC_4_PSTMT_4_ARG_CTOR = null;
/*      */     } 
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
/*      */ 
/*      */ 
/*      */     
/*  725 */     HEX_DIGITS = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
/*      */   } class BatchParams {
/*      */     boolean[] isNull; boolean[] isStream; InputStream[] parameterStreams; byte[][] parameterStrings; int[] streamLengths; BatchParams(byte[][] strings, InputStream[] streams, boolean[] isStreamFlags, int[] lengths, boolean[] isNullFlags) { this.isNull = null; this.isStream = null; this.parameterStreams = null; this.parameterStrings = (byte[][])null; this.streamLengths = null; this.parameterStrings = new byte[strings.length][]; this.parameterStreams = new InputStream[streams.length]; this.isStream = new boolean[isStreamFlags.length]; this.streamLengths = new int[lengths.length]; this.isNull = new boolean[isNullFlags.length]; System.arraycopy(strings, 0, this.parameterStrings, 0, strings.length); System.arraycopy(streams, 0, this.parameterStreams, 0, streams.length); System.arraycopy(isStreamFlags, 0, this.isStream, 0, isStreamFlags.length); System.arraycopy(lengths, 0, this.streamLengths, 0, lengths.length); System.arraycopy(isNullFlags, 0, this.isNull, 0, isNullFlags.length); } } class EndPoint {
/*      */     int begin; int end; EndPoint(int b, int e) throws SQLException { this.begin = b; this.end = e; } } class ParseInfo {
/*      */     char firstStmtChar; boolean foundLimitClause; boolean foundLoadData; long lastUsed; int statementLength; int statementStartPos; boolean canRewriteAsMultiValueInsert; byte[][] staticSql; boolean isOnDuplicateKeyUpdate; int locationOfOnDuplicateKeyUpdate; String valuesClause; boolean parametersInDuplicateKeyClause; private ParseInfo batchHead; private ParseInfo batchValues; private ParseInfo batchODKUClause; ParseInfo(PreparedStatement this$0, String sql, MySQLConnection conn, DatabaseMetaData dbmd, String encoding, SingleByteCharsetConverter converter) throws SQLException { this(sql, conn, dbmd, encoding, converter, true); } public ParseInfo(PreparedStatement this$0, String sql, MySQLConnection conn, DatabaseMetaData dbmd, String encoding, SingleByteCharsetConverter converter, boolean buildRewriteInfo) throws SQLException { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: putfield this$0 : Lcom/mysql/jdbc/PreparedStatement;
/*      */       //   5: aload_0
/*      */       //   6: invokespecial <init> : ()V
/*      */       //   9: aload_0
/*      */       //   10: iconst_0
/*      */       //   11: putfield firstStmtChar : C
/*      */       //   14: aload_0
/*      */       //   15: iconst_0
/*      */       //   16: putfield foundLimitClause : Z
/*      */       //   19: aload_0
/*      */       //   20: iconst_0
/*      */       //   21: putfield foundLoadData : Z
/*      */       //   24: aload_0
/*      */       //   25: lconst_0
/*      */       //   26: putfield lastUsed : J
/*      */       //   29: aload_0
/*      */       //   30: iconst_0
/*      */       //   31: putfield statementLength : I
/*      */       //   34: aload_0
/*      */       //   35: iconst_0
/*      */       //   36: putfield statementStartPos : I
/*      */       //   39: aload_0
/*      */       //   40: iconst_0
/*      */       //   41: putfield canRewriteAsMultiValueInsert : Z
/*      */       //   44: aload_0
/*      */       //   45: aconst_null
/*      */       //   46: checkcast [[B
/*      */       //   49: putfield staticSql : [[B
/*      */       //   52: aload_0
/*      */       //   53: iconst_0
/*      */       //   54: putfield isOnDuplicateKeyUpdate : Z
/*      */       //   57: aload_0
/*      */       //   58: iconst_m1
/*      */       //   59: putfield locationOfOnDuplicateKeyUpdate : I
/*      */       //   62: aload_0
/*      */       //   63: iconst_0
/*      */       //   64: putfield parametersInDuplicateKeyClause : Z
/*      */       //   67: aload_2
/*      */       //   68: ifnonnull -> 86
/*      */       //   71: ldc 'PreparedStatement.61'
/*      */       //   73: invokestatic getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */       //   76: ldc 'S1009'
/*      */       //   78: aload_1
/*      */       //   79: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */       //   82: invokestatic createSQLException : (Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */       //   85: athrow
/*      */       //   86: aload_0
/*      */       //   87: aload_1
/*      */       //   88: aload_2
/*      */       //   89: invokevirtual getOnDuplicateKeyLocation : (Ljava/lang/String;)I
/*      */       //   92: putfield locationOfOnDuplicateKeyUpdate : I
/*      */       //   95: aload_0
/*      */       //   96: aload_0
/*      */       //   97: getfield locationOfOnDuplicateKeyUpdate : I
/*      */       //   100: iconst_m1
/*      */       //   101: if_icmpeq -> 108
/*      */       //   104: iconst_1
/*      */       //   105: goto -> 109
/*      */       //   108: iconst_0
/*      */       //   109: putfield isOnDuplicateKeyUpdate : Z
/*      */       //   112: aload_0
/*      */       //   113: invokestatic currentTimeMillis : ()J
/*      */       //   116: putfield lastUsed : J
/*      */       //   119: aload #4
/*      */       //   121: invokeinterface getIdentifierQuoteString : ()Ljava/lang/String;
/*      */       //   126: astore #8
/*      */       //   128: iconst_0
/*      */       //   129: istore #9
/*      */       //   131: aload #8
/*      */       //   133: ifnull -> 162
/*      */       //   136: aload #8
/*      */       //   138: ldc ' '
/*      */       //   140: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */       //   143: ifne -> 162
/*      */       //   146: aload #8
/*      */       //   148: invokevirtual length : ()I
/*      */       //   151: ifle -> 162
/*      */       //   154: aload #8
/*      */       //   156: iconst_0
/*      */       //   157: invokevirtual charAt : (I)C
/*      */       //   160: istore #9
/*      */       //   162: aload_0
/*      */       //   163: aload_2
/*      */       //   164: invokevirtual length : ()I
/*      */       //   167: putfield statementLength : I
/*      */       //   170: new java/util/ArrayList
/*      */       //   173: dup
/*      */       //   174: invokespecial <init> : ()V
/*      */       //   177: astore #10
/*      */       //   179: iconst_0
/*      */       //   180: istore #11
/*      */       //   182: iconst_0
/*      */       //   183: istore #12
/*      */       //   185: iconst_0
/*      */       //   186: istore #13
/*      */       //   188: iconst_0
/*      */       //   189: istore #14
/*      */       //   191: aload_0
/*      */       //   192: getfield statementLength : I
/*      */       //   195: iconst_5
/*      */       //   196: isub
/*      */       //   197: istore #16
/*      */       //   199: aload_0
/*      */       //   200: iconst_0
/*      */       //   201: putfield foundLimitClause : Z
/*      */       //   204: aload_1
/*      */       //   205: getfield connection : Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   208: invokeinterface isNoBackslashEscapesSet : ()Z
/*      */       //   213: istore #17
/*      */       //   215: aload_0
/*      */       //   216: aload_1
/*      */       //   217: aload_2
/*      */       //   218: invokevirtual findStartOfStatement : (Ljava/lang/String;)I
/*      */       //   221: putfield statementStartPos : I
/*      */       //   224: aload_0
/*      */       //   225: getfield statementStartPos : I
/*      */       //   228: istore #15
/*      */       //   230: iload #15
/*      */       //   232: aload_0
/*      */       //   233: getfield statementLength : I
/*      */       //   236: if_icmpge -> 876
/*      */       //   239: aload_2
/*      */       //   240: iload #15
/*      */       //   242: invokevirtual charAt : (I)C
/*      */       //   245: istore #18
/*      */       //   247: aload_0
/*      */       //   248: getfield firstStmtChar : C
/*      */       //   251: ifne -> 271
/*      */       //   254: iload #18
/*      */       //   256: invokestatic isLetter : (C)Z
/*      */       //   259: ifeq -> 271
/*      */       //   262: aload_0
/*      */       //   263: iload #18
/*      */       //   265: invokestatic toUpperCase : (C)C
/*      */       //   268: putfield firstStmtChar : C
/*      */       //   271: iload #17
/*      */       //   273: ifne -> 300
/*      */       //   276: iload #18
/*      */       //   278: bipush #92
/*      */       //   280: if_icmpne -> 300
/*      */       //   283: iload #15
/*      */       //   285: aload_0
/*      */       //   286: getfield statementLength : I
/*      */       //   289: iconst_1
/*      */       //   290: isub
/*      */       //   291: if_icmpge -> 300
/*      */       //   294: iinc #15, 1
/*      */       //   297: goto -> 870
/*      */       //   300: iload #11
/*      */       //   302: ifne -> 332
/*      */       //   305: iload #9
/*      */       //   307: ifeq -> 332
/*      */       //   310: iload #18
/*      */       //   312: iload #9
/*      */       //   314: if_icmpne -> 332
/*      */       //   317: iload #13
/*      */       //   319: ifne -> 326
/*      */       //   322: iconst_1
/*      */       //   323: goto -> 327
/*      */       //   326: iconst_0
/*      */       //   327: istore #13
/*      */       //   329: goto -> 680
/*      */       //   332: iload #13
/*      */       //   334: ifne -> 680
/*      */       //   337: iload #11
/*      */       //   339: ifeq -> 450
/*      */       //   342: iload #18
/*      */       //   344: bipush #39
/*      */       //   346: if_icmpeq -> 356
/*      */       //   349: iload #18
/*      */       //   351: bipush #34
/*      */       //   353: if_icmpne -> 411
/*      */       //   356: iload #18
/*      */       //   358: iload #12
/*      */       //   360: if_icmpne -> 411
/*      */       //   363: iload #15
/*      */       //   365: aload_0
/*      */       //   366: getfield statementLength : I
/*      */       //   369: iconst_1
/*      */       //   370: isub
/*      */       //   371: if_icmpge -> 393
/*      */       //   374: aload_2
/*      */       //   375: iload #15
/*      */       //   377: iconst_1
/*      */       //   378: iadd
/*      */       //   379: invokevirtual charAt : (I)C
/*      */       //   382: iload #12
/*      */       //   384: if_icmpne -> 393
/*      */       //   387: iinc #15, 1
/*      */       //   390: goto -> 870
/*      */       //   393: iload #11
/*      */       //   395: ifne -> 402
/*      */       //   398: iconst_1
/*      */       //   399: goto -> 403
/*      */       //   402: iconst_0
/*      */       //   403: istore #11
/*      */       //   405: iconst_0
/*      */       //   406: istore #12
/*      */       //   408: goto -> 680
/*      */       //   411: iload #18
/*      */       //   413: bipush #39
/*      */       //   415: if_icmpeq -> 425
/*      */       //   418: iload #18
/*      */       //   420: bipush #34
/*      */       //   422: if_icmpne -> 680
/*      */       //   425: iload #18
/*      */       //   427: iload #12
/*      */       //   429: if_icmpne -> 680
/*      */       //   432: iload #11
/*      */       //   434: ifne -> 441
/*      */       //   437: iconst_1
/*      */       //   438: goto -> 442
/*      */       //   441: iconst_0
/*      */       //   442: istore #11
/*      */       //   444: iconst_0
/*      */       //   445: istore #12
/*      */       //   447: goto -> 680
/*      */       //   450: iload #18
/*      */       //   452: bipush #35
/*      */       //   454: if_icmpeq -> 488
/*      */       //   457: iload #18
/*      */       //   459: bipush #45
/*      */       //   461: if_icmpne -> 534
/*      */       //   464: iload #15
/*      */       //   466: iconst_1
/*      */       //   467: iadd
/*      */       //   468: aload_0
/*      */       //   469: getfield statementLength : I
/*      */       //   472: if_icmpge -> 534
/*      */       //   475: aload_2
/*      */       //   476: iload #15
/*      */       //   478: iconst_1
/*      */       //   479: iadd
/*      */       //   480: invokevirtual charAt : (I)C
/*      */       //   483: bipush #45
/*      */       //   485: if_icmpne -> 534
/*      */       //   488: aload_0
/*      */       //   489: getfield statementLength : I
/*      */       //   492: iconst_1
/*      */       //   493: isub
/*      */       //   494: istore #19
/*      */       //   496: iload #15
/*      */       //   498: iload #19
/*      */       //   500: if_icmpge -> 870
/*      */       //   503: aload_2
/*      */       //   504: iload #15
/*      */       //   506: invokevirtual charAt : (I)C
/*      */       //   509: istore #18
/*      */       //   511: iload #18
/*      */       //   513: bipush #13
/*      */       //   515: if_icmpeq -> 870
/*      */       //   518: iload #18
/*      */       //   520: bipush #10
/*      */       //   522: if_icmpne -> 528
/*      */       //   525: goto -> 870
/*      */       //   528: iinc #15, 1
/*      */       //   531: goto -> 496
/*      */       //   534: iload #18
/*      */       //   536: bipush #47
/*      */       //   538: if_icmpne -> 659
/*      */       //   541: iload #15
/*      */       //   543: iconst_1
/*      */       //   544: iadd
/*      */       //   545: aload_0
/*      */       //   546: getfield statementLength : I
/*      */       //   549: if_icmpge -> 659
/*      */       //   552: aload_2
/*      */       //   553: iload #15
/*      */       //   555: iconst_1
/*      */       //   556: iadd
/*      */       //   557: invokevirtual charAt : (I)C
/*      */       //   560: istore #19
/*      */       //   562: iload #19
/*      */       //   564: bipush #42
/*      */       //   566: if_icmpne -> 656
/*      */       //   569: iinc #15, 2
/*      */       //   572: iload #15
/*      */       //   574: istore #20
/*      */       //   576: iload #20
/*      */       //   578: aload_0
/*      */       //   579: getfield statementLength : I
/*      */       //   582: if_icmpge -> 656
/*      */       //   585: iinc #15, 1
/*      */       //   588: aload_2
/*      */       //   589: iload #20
/*      */       //   591: invokevirtual charAt : (I)C
/*      */       //   594: istore #19
/*      */       //   596: iload #19
/*      */       //   598: bipush #42
/*      */       //   600: if_icmpne -> 650
/*      */       //   603: iload #20
/*      */       //   605: iconst_1
/*      */       //   606: iadd
/*      */       //   607: aload_0
/*      */       //   608: getfield statementLength : I
/*      */       //   611: if_icmpge -> 650
/*      */       //   614: aload_2
/*      */       //   615: iload #20
/*      */       //   617: iconst_1
/*      */       //   618: iadd
/*      */       //   619: invokevirtual charAt : (I)C
/*      */       //   622: bipush #47
/*      */       //   624: if_icmpne -> 650
/*      */       //   627: iinc #15, 1
/*      */       //   630: iload #15
/*      */       //   632: aload_0
/*      */       //   633: getfield statementLength : I
/*      */       //   636: if_icmpge -> 656
/*      */       //   639: aload_2
/*      */       //   640: iload #15
/*      */       //   642: invokevirtual charAt : (I)C
/*      */       //   645: istore #18
/*      */       //   647: goto -> 656
/*      */       //   650: iinc #20, 1
/*      */       //   653: goto -> 576
/*      */       //   656: goto -> 680
/*      */       //   659: iload #18
/*      */       //   661: bipush #39
/*      */       //   663: if_icmpeq -> 673
/*      */       //   666: iload #18
/*      */       //   668: bipush #34
/*      */       //   670: if_icmpne -> 680
/*      */       //   673: iconst_1
/*      */       //   674: istore #11
/*      */       //   676: iload #18
/*      */       //   678: istore #12
/*      */       //   680: iload #18
/*      */       //   682: bipush #63
/*      */       //   684: if_icmpne -> 743
/*      */       //   687: iload #11
/*      */       //   689: ifne -> 743
/*      */       //   692: iload #13
/*      */       //   694: ifne -> 743
/*      */       //   697: aload #10
/*      */       //   699: iconst_2
/*      */       //   700: newarray int
/*      */       //   702: dup
/*      */       //   703: iconst_0
/*      */       //   704: iload #14
/*      */       //   706: iastore
/*      */       //   707: dup
/*      */       //   708: iconst_1
/*      */       //   709: iload #15
/*      */       //   711: iastore
/*      */       //   712: invokevirtual add : (Ljava/lang/Object;)Z
/*      */       //   715: pop
/*      */       //   716: iload #15
/*      */       //   718: iconst_1
/*      */       //   719: iadd
/*      */       //   720: istore #14
/*      */       //   722: aload_0
/*      */       //   723: getfield isOnDuplicateKeyUpdate : Z
/*      */       //   726: ifeq -> 743
/*      */       //   729: iload #15
/*      */       //   731: aload_0
/*      */       //   732: getfield locationOfOnDuplicateKeyUpdate : I
/*      */       //   735: if_icmple -> 743
/*      */       //   738: aload_0
/*      */       //   739: iconst_1
/*      */       //   740: putfield parametersInDuplicateKeyClause : Z
/*      */       //   743: iload #11
/*      */       //   745: ifne -> 870
/*      */       //   748: iload #15
/*      */       //   750: iload #16
/*      */       //   752: if_icmpge -> 870
/*      */       //   755: iload #18
/*      */       //   757: bipush #76
/*      */       //   759: if_icmpeq -> 769
/*      */       //   762: iload #18
/*      */       //   764: bipush #108
/*      */       //   766: if_icmpne -> 870
/*      */       //   769: aload_2
/*      */       //   770: iload #15
/*      */       //   772: iconst_1
/*      */       //   773: iadd
/*      */       //   774: invokevirtual charAt : (I)C
/*      */       //   777: istore #19
/*      */       //   779: iload #19
/*      */       //   781: bipush #73
/*      */       //   783: if_icmpeq -> 793
/*      */       //   786: iload #19
/*      */       //   788: bipush #105
/*      */       //   790: if_icmpne -> 870
/*      */       //   793: aload_2
/*      */       //   794: iload #15
/*      */       //   796: iconst_2
/*      */       //   797: iadd
/*      */       //   798: invokevirtual charAt : (I)C
/*      */       //   801: istore #20
/*      */       //   803: iload #20
/*      */       //   805: bipush #77
/*      */       //   807: if_icmpeq -> 817
/*      */       //   810: iload #20
/*      */       //   812: bipush #109
/*      */       //   814: if_icmpne -> 870
/*      */       //   817: aload_2
/*      */       //   818: iload #15
/*      */       //   820: iconst_3
/*      */       //   821: iadd
/*      */       //   822: invokevirtual charAt : (I)C
/*      */       //   825: istore #21
/*      */       //   827: iload #21
/*      */       //   829: bipush #73
/*      */       //   831: if_icmpeq -> 841
/*      */       //   834: iload #21
/*      */       //   836: bipush #105
/*      */       //   838: if_icmpne -> 870
/*      */       //   841: aload_2
/*      */       //   842: iload #15
/*      */       //   844: iconst_4
/*      */       //   845: iadd
/*      */       //   846: invokevirtual charAt : (I)C
/*      */       //   849: istore #22
/*      */       //   851: iload #22
/*      */       //   853: bipush #84
/*      */       //   855: if_icmpeq -> 865
/*      */       //   858: iload #22
/*      */       //   860: bipush #116
/*      */       //   862: if_icmpne -> 870
/*      */       //   865: aload_0
/*      */       //   866: iconst_1
/*      */       //   867: putfield foundLimitClause : Z
/*      */       //   870: iinc #15, 1
/*      */       //   873: goto -> 230
/*      */       //   876: aload_0
/*      */       //   877: getfield firstStmtChar : C
/*      */       //   880: bipush #76
/*      */       //   882: if_icmpne -> 910
/*      */       //   885: aload_2
/*      */       //   886: ldc 'LOAD DATA'
/*      */       //   888: invokestatic startsWithIgnoreCaseAndWs : (Ljava/lang/String;Ljava/lang/String;)Z
/*      */       //   891: ifeq -> 902
/*      */       //   894: aload_0
/*      */       //   895: iconst_1
/*      */       //   896: putfield foundLoadData : Z
/*      */       //   899: goto -> 915
/*      */       //   902: aload_0
/*      */       //   903: iconst_0
/*      */       //   904: putfield foundLoadData : Z
/*      */       //   907: goto -> 915
/*      */       //   910: aload_0
/*      */       //   911: iconst_0
/*      */       //   912: putfield foundLoadData : Z
/*      */       //   915: aload #10
/*      */       //   917: iconst_2
/*      */       //   918: newarray int
/*      */       //   920: dup
/*      */       //   921: iconst_0
/*      */       //   922: iload #14
/*      */       //   924: iastore
/*      */       //   925: dup
/*      */       //   926: iconst_1
/*      */       //   927: aload_0
/*      */       //   928: getfield statementLength : I
/*      */       //   931: iastore
/*      */       //   932: invokevirtual add : (Ljava/lang/Object;)Z
/*      */       //   935: pop
/*      */       //   936: aload_0
/*      */       //   937: aload #10
/*      */       //   939: invokevirtual size : ()I
/*      */       //   942: anewarray [B
/*      */       //   945: putfield staticSql : [[B
/*      */       //   948: aload_2
/*      */       //   949: invokevirtual toCharArray : ()[C
/*      */       //   952: astore #18
/*      */       //   954: iconst_0
/*      */       //   955: istore #15
/*      */       //   957: iload #15
/*      */       //   959: aload_0
/*      */       //   960: getfield staticSql : [[B
/*      */       //   963: arraylength
/*      */       //   964: if_icmpge -> 1199
/*      */       //   967: aload #10
/*      */       //   969: iload #15
/*      */       //   971: invokevirtual get : (I)Ljava/lang/Object;
/*      */       //   974: checkcast [I
/*      */       //   977: checkcast [I
/*      */       //   980: astore #19
/*      */       //   982: aload #19
/*      */       //   984: iconst_1
/*      */       //   985: iaload
/*      */       //   986: istore #20
/*      */       //   988: aload #19
/*      */       //   990: iconst_0
/*      */       //   991: iaload
/*      */       //   992: istore #21
/*      */       //   994: iload #20
/*      */       //   996: iload #21
/*      */       //   998: isub
/*      */       //   999: istore #22
/*      */       //   1001: aload_0
/*      */       //   1002: getfield foundLoadData : Z
/*      */       //   1005: ifeq -> 1038
/*      */       //   1008: new java/lang/String
/*      */       //   1011: dup
/*      */       //   1012: aload #18
/*      */       //   1014: iload #21
/*      */       //   1016: iload #22
/*      */       //   1018: invokespecial <init> : ([CII)V
/*      */       //   1021: astore #23
/*      */       //   1023: aload_0
/*      */       //   1024: getfield staticSql : [[B
/*      */       //   1027: iload #15
/*      */       //   1029: aload #23
/*      */       //   1031: invokevirtual getBytes : ()[B
/*      */       //   1034: aastore
/*      */       //   1035: goto -> 1193
/*      */       //   1038: aload #5
/*      */       //   1040: ifnonnull -> 1092
/*      */       //   1043: iload #22
/*      */       //   1045: newarray byte
/*      */       //   1047: astore #23
/*      */       //   1049: iconst_0
/*      */       //   1050: istore #24
/*      */       //   1052: iload #24
/*      */       //   1054: iload #22
/*      */       //   1056: if_icmpge -> 1080
/*      */       //   1059: aload #23
/*      */       //   1061: iload #24
/*      */       //   1063: aload_2
/*      */       //   1064: iload #21
/*      */       //   1066: iload #24
/*      */       //   1068: iadd
/*      */       //   1069: invokevirtual charAt : (I)C
/*      */       //   1072: i2b
/*      */       //   1073: bastore
/*      */       //   1074: iinc #24, 1
/*      */       //   1077: goto -> 1052
/*      */       //   1080: aload_0
/*      */       //   1081: getfield staticSql : [[B
/*      */       //   1084: iload #15
/*      */       //   1086: aload #23
/*      */       //   1088: aastore
/*      */       //   1089: goto -> 1193
/*      */       //   1092: aload #6
/*      */       //   1094: ifnull -> 1141
/*      */       //   1097: aload_0
/*      */       //   1098: getfield staticSql : [[B
/*      */       //   1101: iload #15
/*      */       //   1103: aload_2
/*      */       //   1104: aload #6
/*      */       //   1106: aload #5
/*      */       //   1108: aload_1
/*      */       //   1109: getfield connection : Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   1112: invokeinterface getServerCharacterEncoding : ()Ljava/lang/String;
/*      */       //   1117: iload #21
/*      */       //   1119: iload #22
/*      */       //   1121: aload_1
/*      */       //   1122: getfield connection : Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   1125: invokeinterface parserKnowsUnicode : ()Z
/*      */       //   1130: aload_1
/*      */       //   1131: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */       //   1134: invokestatic getBytes : (Ljava/lang/String;Lcom/mysql/jdbc/SingleByteCharsetConverter;Ljava/lang/String;Ljava/lang/String;IIZLcom/mysql/jdbc/ExceptionInterceptor;)[B
/*      */       //   1137: aastore
/*      */       //   1138: goto -> 1193
/*      */       //   1141: new java/lang/String
/*      */       //   1144: dup
/*      */       //   1145: aload #18
/*      */       //   1147: iload #21
/*      */       //   1149: iload #22
/*      */       //   1151: invokespecial <init> : ([CII)V
/*      */       //   1154: astore #23
/*      */       //   1156: aload_0
/*      */       //   1157: getfield staticSql : [[B
/*      */       //   1160: iload #15
/*      */       //   1162: aload #23
/*      */       //   1164: aload #5
/*      */       //   1166: aload_1
/*      */       //   1167: getfield connection : Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   1170: invokeinterface getServerCharacterEncoding : ()Ljava/lang/String;
/*      */       //   1175: aload_1
/*      */       //   1176: getfield connection : Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   1179: invokeinterface parserKnowsUnicode : ()Z
/*      */       //   1184: aload_3
/*      */       //   1185: aload_1
/*      */       //   1186: invokevirtual getExceptionInterceptor : ()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */       //   1189: invokestatic getBytes : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLcom/mysql/jdbc/MySQLConnection;Lcom/mysql/jdbc/ExceptionInterceptor;)[B
/*      */       //   1192: aastore
/*      */       //   1193: iinc #15, 1
/*      */       //   1196: goto -> 957
/*      */       //   1199: goto -> 1243
/*      */       //   1202: astore #8
/*      */       //   1204: new java/sql/SQLException
/*      */       //   1207: dup
/*      */       //   1208: new java/lang/StringBuilder
/*      */       //   1211: dup
/*      */       //   1212: invokespecial <init> : ()V
/*      */       //   1215: ldc 'Parse error for '
/*      */       //   1217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   1220: aload_2
/*      */       //   1221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   1224: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   1227: invokespecial <init> : (Ljava/lang/String;)V
/*      */       //   1230: astore #9
/*      */       //   1232: aload #9
/*      */       //   1234: aload #8
/*      */       //   1236: invokevirtual initCause : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*      */       //   1239: pop
/*      */       //   1240: aload #9
/*      */       //   1242: athrow
/*      */       //   1243: iload #7
/*      */       //   1245: ifeq -> 1311
/*      */       //   1248: aload_0
/*      */       //   1249: aload_2
/*      */       //   1250: aload_0
/*      */       //   1251: getfield isOnDuplicateKeyUpdate : Z
/*      */       //   1254: aload_0
/*      */       //   1255: getfield locationOfOnDuplicateKeyUpdate : I
/*      */       //   1258: aload_0
/*      */       //   1259: getfield statementStartPos : I
/*      */       //   1262: invokestatic canRewrite : (Ljava/lang/String;ZII)Z
/*      */       //   1265: ifeq -> 1279
/*      */       //   1268: aload_0
/*      */       //   1269: getfield parametersInDuplicateKeyClause : Z
/*      */       //   1272: ifne -> 1279
/*      */       //   1275: iconst_1
/*      */       //   1276: goto -> 1280
/*      */       //   1279: iconst_0
/*      */       //   1280: putfield canRewriteAsMultiValueInsert : Z
/*      */       //   1283: aload_0
/*      */       //   1284: getfield canRewriteAsMultiValueInsert : Z
/*      */       //   1287: ifeq -> 1311
/*      */       //   1290: aload_3
/*      */       //   1291: invokeinterface getRewriteBatchedStatements : ()Z
/*      */       //   1296: ifeq -> 1311
/*      */       //   1299: aload_0
/*      */       //   1300: aload_2
/*      */       //   1301: aload_3
/*      */       //   1302: aload #4
/*      */       //   1304: aload #5
/*      */       //   1306: aload #6
/*      */       //   1308: invokespecial buildRewriteBatchedParams : (Ljava/lang/String;Lcom/mysql/jdbc/MySQLConnection;Ljava/sql/DatabaseMetaData;Ljava/lang/String;Lcom/mysql/jdbc/SingleByteCharsetConverter;)V
/*      */       //   1311: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #213	-> 0
/*      */       //   #175	-> 9
/*      */       //   #177	-> 14
/*      */       //   #179	-> 19
/*      */       //   #181	-> 24
/*      */       //   #183	-> 29
/*      */       //   #185	-> 34
/*      */       //   #187	-> 39
/*      */       //   #189	-> 44
/*      */       //   #191	-> 52
/*      */       //   #193	-> 57
/*      */       //   #197	-> 62
/*      */       //   #215	-> 67
/*      */       //   #216	-> 71
/*      */       //   #221	-> 86
/*      */       //   #222	-> 95
/*      */       //   #224	-> 112
/*      */       //   #226	-> 119
/*      */       //   #228	-> 128
/*      */       //   #230	-> 131
/*      */       //   #233	-> 154
/*      */       //   #236	-> 162
/*      */       //   #238	-> 170
/*      */       //   #239	-> 179
/*      */       //   #240	-> 182
/*      */       //   #241	-> 185
/*      */       //   #242	-> 188
/*      */       //   #245	-> 191
/*      */       //   #247	-> 199
/*      */       //   #249	-> 204
/*      */       //   #255	-> 215
/*      */       //   #257	-> 224
/*      */       //   #258	-> 239
/*      */       //   #260	-> 247
/*      */       //   #263	-> 262
/*      */       //   #266	-> 271
/*      */       //   #268	-> 294
/*      */       //   #269	-> 297
/*      */       //   #274	-> 300
/*      */       //   #276	-> 317
/*      */       //   #277	-> 332
/*      */       //   #280	-> 337
/*      */       //   #281	-> 342
/*      */       //   #282	-> 363
/*      */       //   #283	-> 387
/*      */       //   #284	-> 390
/*      */       //   #287	-> 393
/*      */       //   #288	-> 405
/*      */       //   #289	-> 411
/*      */       //   #290	-> 432
/*      */       //   #291	-> 444
/*      */       //   #294	-> 450
/*      */       //   #299	-> 488
/*      */       //   #301	-> 496
/*      */       //   #302	-> 503
/*      */       //   #304	-> 511
/*      */       //   #305	-> 525
/*      */       //   #301	-> 528
/*      */       //   #310	-> 534
/*      */       //   #312	-> 552
/*      */       //   #314	-> 562
/*      */       //   #315	-> 569
/*      */       //   #317	-> 572
/*      */       //   #318	-> 585
/*      */       //   #319	-> 588
/*      */       //   #321	-> 596
/*      */       //   #322	-> 614
/*      */       //   #323	-> 627
/*      */       //   #325	-> 630
/*      */       //   #326	-> 639
/*      */       //   #317	-> 650
/*      */       //   #334	-> 656
/*      */       //   #335	-> 673
/*      */       //   #336	-> 676
/*      */       //   #341	-> 680
/*      */       //   #342	-> 697
/*      */       //   #343	-> 716
/*      */       //   #345	-> 722
/*      */       //   #346	-> 738
/*      */       //   #350	-> 743
/*      */       //   #351	-> 755
/*      */       //   #352	-> 769
/*      */       //   #354	-> 779
/*      */       //   #355	-> 793
/*      */       //   #357	-> 803
/*      */       //   #358	-> 817
/*      */       //   #360	-> 827
/*      */       //   #361	-> 841
/*      */       //   #363	-> 851
/*      */       //   #364	-> 865
/*      */       //   #257	-> 870
/*      */       //   #373	-> 876
/*      */       //   #374	-> 885
/*      */       //   #375	-> 894
/*      */       //   #377	-> 902
/*      */       //   #380	-> 910
/*      */       //   #383	-> 915
/*      */       //   #384	-> 936
/*      */       //   #385	-> 948
/*      */       //   #387	-> 954
/*      */       //   #388	-> 967
/*      */       //   #389	-> 982
/*      */       //   #390	-> 988
/*      */       //   #391	-> 994
/*      */       //   #393	-> 1001
/*      */       //   #394	-> 1008
/*      */       //   #395	-> 1023
/*      */       //   #396	-> 1035
/*      */       //   #397	-> 1043
/*      */       //   #399	-> 1049
/*      */       //   #400	-> 1059
/*      */       //   #399	-> 1074
/*      */       //   #403	-> 1080
/*      */       //   #404	-> 1089
/*      */       //   #405	-> 1092
/*      */       //   #406	-> 1097
/*      */       //   #411	-> 1141
/*      */       //   #413	-> 1156
/*      */       //   #387	-> 1193
/*      */       //   #425	-> 1199
/*      */       //   #420	-> 1202
/*      */       //   #421	-> 1204
/*      */       //   #422	-> 1232
/*      */       //   #424	-> 1240
/*      */       //   #428	-> 1243
/*      */       //   #429	-> 1248
/*      */       //   #434	-> 1283
/*      */       //   #436	-> 1299
/*      */       //   #440	-> 1311
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   496	38	19	endOfStmt	I
/*      */       //   576	80	20	j	I
/*      */       //   562	94	19	cNext	C
/*      */       //   851	19	22	posT	C
/*      */       //   827	43	21	posI2	C
/*      */       //   803	67	20	posM	C
/*      */       //   779	91	19	posI1	C
/*      */       //   247	623	18	c	C
/*      */       //   1023	12	23	temp	Ljava/lang/String;
/*      */       //   1052	28	24	j	I
/*      */       //   1049	40	23	buf	[B
/*      */       //   1156	37	23	temp	Ljava/lang/String;
/*      */       //   982	211	19	ep	[I
/*      */       //   988	205	20	end	I
/*      */       //   994	199	21	begin	I
/*      */       //   1001	192	22	len	I
/*      */       //   128	1071	8	quotedIdentifierString	Ljava/lang/String;
/*      */       //   131	1068	9	quotedIdentifierChar	C
/*      */       //   179	1020	10	endpointList	Ljava/util/ArrayList;
/*      */       //   182	1017	11	inQuotes	Z
/*      */       //   185	1014	12	quoteChar	C
/*      */       //   188	1011	13	inQuotedId	Z
/*      */       //   191	1008	14	lastParmEnd	I
/*      */       //   230	969	15	i	I
/*      */       //   199	1000	16	stopLookingForLimitClause	I
/*      */       //   215	984	17	noBackslashEscapes	Z
/*      */       //   954	245	18	asCharArray	[C
/*      */       //   1232	11	9	sqlEx	Ljava/sql/SQLException;
/*      */       //   1204	39	8	oobEx	Ljava/lang/StringIndexOutOfBoundsException;
/*      */       //   0	1312	0	this	Lcom/mysql/jdbc/PreparedStatement$ParseInfo;
/*      */       //   0	1312	2	sql	Ljava/lang/String;
/*      */       //   0	1312	3	conn	Lcom/mysql/jdbc/MySQLConnection;
/*      */       //   0	1312	4	dbmd	Ljava/sql/DatabaseMetaData;
/*      */       //   0	1312	5	encoding	Ljava/lang/String;
/*      */       //   0	1312	6	converter	Lcom/mysql/jdbc/SingleByteCharsetConverter;
/*      */       //   0	1312	7	buildRewriteInfo	Z
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   67	1199	1202	java/lang/StringIndexOutOfBoundsException } private void buildRewriteBatchedParams(String sql, MySQLConnection conn, DatabaseMetaData metadata, String encoding, SingleByteCharsetConverter converter) throws SQLException { this.valuesClause = extractValuesClause(sql); String odkuClause = this.isOnDuplicateKeyUpdate ? sql.substring(this.locationOfOnDuplicateKeyUpdate) : null; String headSql = null; if (this.isOnDuplicateKeyUpdate) { headSql = sql.substring(0, this.locationOfOnDuplicateKeyUpdate); } else { headSql = sql; }  this.batchHead = new ParseInfo(PreparedStatement.this, headSql, conn, metadata, encoding, converter, false); this.batchValues = new ParseInfo(PreparedStatement.this, "," + this.valuesClause, conn, metadata, encoding, converter, false); this.batchODKUClause = null; if (odkuClause != null && odkuClause.length() > 0) this.batchODKUClause = new ParseInfo(PreparedStatement.this, "," + this.valuesClause + " " + odkuClause, conn, metadata, encoding, converter, false);  } private String extractValuesClause(String sql) { String quoteCharStr = PreparedStatement.this.connection.getMetaData().getIdentifierQuoteString(); int indexOfValues = -1; int valuesSearchStart = this.statementStartPos; while (indexOfValues == -1) { if (quoteCharStr.length() > 0) { indexOfValues = StringUtils.indexOfIgnoreCaseRespectQuotes(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES", quoteCharStr.charAt(0), false); } else { indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES"); }  if (indexOfValues > 0) { char c = PreparedStatement.this.originalSql.charAt(indexOfValues - 1); if (!Character.isWhitespace(c) && c != ')' && c != '`') { valuesSearchStart = indexOfValues + 6; indexOfValues = -1; continue; }  c = PreparedStatement.this.originalSql.charAt(indexOfValues + 6); if (!Character.isWhitespace(c) && c != '(') { valuesSearchStart = indexOfValues + 6; indexOfValues = -1; }  }  }  if (indexOfValues == -1) return null;  int indexOfFirstParen = sql.indexOf('(', indexOfValues + 6); if (indexOfFirstParen == -1) return null;  int endOfValuesClause = sql.lastIndexOf(')'); if (endOfValuesClause == -1) return null;  if (this.isOnDuplicateKeyUpdate) endOfValuesClause = this.locationOfOnDuplicateKeyUpdate - 1;  return sql.substring(indexOfFirstParen, endOfValuesClause + 1); } ParseInfo getParseInfoForBatch(int numBatch) { PreparedStatement.AppendingBatchVisitor apv = new PreparedStatement.AppendingBatchVisitor(PreparedStatement.this); buildInfoForBatch(numBatch, apv); return new ParseInfo(PreparedStatement.this, apv.getStaticSqlStrings(), this.firstStmtChar, this.foundLimitClause, this.foundLoadData, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementLength, this.statementStartPos); } String getSqlForBatch(int numBatch) { ParseInfo batchInfo = getParseInfoForBatch(numBatch); return getSqlForBatch(batchInfo); } String getSqlForBatch(ParseInfo batchInfo) throws UnsupportedEncodingException { int size = 0; byte[][] sqlStrings = batchInfo.staticSql; int sqlStringsLength = sqlStrings.length; for (i = 0; i < sqlStringsLength; i++) { size += sqlStrings[i].length; size++; }  StringBuffer buf = new StringBuffer(size); for (int i = 0; i < sqlStringsLength - 1; i++) { buf.append(new String(sqlStrings[i], PreparedStatement.this.charEncoding)); buf.append("?"); }  buf.append(new String(sqlStrings[sqlStringsLength - 1])); return buf.toString(); } private void buildInfoForBatch(int numBatch, PreparedStatement.BatchVisitor visitor) { byte[][] headStaticSql = this.batchHead.staticSql; int headStaticSqlLength = headStaticSql.length; if (headStaticSqlLength > 1) for (int i = 0; i < headStaticSqlLength - 1; i++) visitor.append(headStaticSql[i]).increment();   byte[] endOfHead = headStaticSql[headStaticSqlLength - 1]; byte[][] valuesStaticSql = this.batchValues.staticSql; byte[] beginOfValues = valuesStaticSql[0]; visitor.merge(endOfHead, beginOfValues).increment(); int numValueRepeats = numBatch - 1; if (this.batchODKUClause != null)
/*      */         numValueRepeats--;  int valuesStaticSqlLength = valuesStaticSql.length; byte[] endOfValues = valuesStaticSql[valuesStaticSqlLength - 1]; for (int i = 0; i < numValueRepeats; i++) { for (int j = 1; j < valuesStaticSqlLength - 1; j++)
/*      */           visitor.append(valuesStaticSql[j]).increment();  visitor.merge(endOfValues, beginOfValues).increment(); }  if (this.batchODKUClause != null) { byte[][] batchOdkuStaticSql = this.batchODKUClause.staticSql; byte[] beginOfOdku = batchOdkuStaticSql[0]; visitor.decrement().merge(endOfValues, beginOfOdku).increment(); int batchOdkuStaticSqlLength = batchOdkuStaticSql.length; if (numBatch > 1) { for (int i = 1; i < batchOdkuStaticSqlLength; i++)
/*      */             visitor.append(batchOdkuStaticSql[i]).increment();  } else { visitor.decrement().append(batchOdkuStaticSql[batchOdkuStaticSqlLength - 1]); }  } else { visitor.decrement().append(this.staticSql[this.staticSql.length - 1]); }  }
/*      */     private ParseInfo(byte[][] staticSql, char firstStmtChar, boolean foundLimitClause, boolean foundLoadData, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementLength, int statementStartPos) { this.firstStmtChar = Character.MIN_VALUE; this.foundLimitClause = false; this.foundLoadData = false; this.lastUsed = 0L; this.statementLength = 0; this.statementStartPos = 0; this.canRewriteAsMultiValueInsert = false; this.staticSql = (byte[][])null; this.isOnDuplicateKeyUpdate = false; this.locationOfOnDuplicateKeyUpdate = -1; this.parametersInDuplicateKeyClause = false; this.firstStmtChar = firstStmtChar; this.foundLimitClause = foundLimitClause; this.foundLoadData = foundLoadData; this.isOnDuplicateKeyUpdate = isOnDuplicateKeyUpdate; this.locationOfOnDuplicateKeyUpdate = locationOfOnDuplicateKeyUpdate; this.statementLength = statementLength; this.statementStartPos = statementStartPos; this.staticSql = staticSql; } }
/*      */   static interface BatchVisitor {
/*      */     BatchVisitor increment();
/*      */     BatchVisitor decrement();
/*      */     BatchVisitor append(byte[] param1ArrayOfByte);
/*      */     BatchVisitor merge(byte[] param1ArrayOfByte1, byte[] param1ArrayOfByte2); }
/*      */   class AppendingBatchVisitor implements BatchVisitor {
/*      */     LinkedList statementComponents = new LinkedList();
/*      */     public PreparedStatement.BatchVisitor append(byte[] values) { this.statementComponents.addLast(values); return this; }
/*      */     public PreparedStatement.BatchVisitor increment() { return this; }
/*      */     public PreparedStatement.BatchVisitor decrement() { this.statementComponents.removeLast(); return this; }
/*      */     public PreparedStatement.BatchVisitor merge(byte[] front, byte[] back) { int mergedLength = front.length + back.length; byte[] merged = new byte[mergedLength]; System.arraycopy(front, 0, merged, 0, front.length); System.arraycopy(back, 0, merged, front.length, back.length); this.statementComponents.addLast(merged); return this; }
/*      */     public byte[][] getStaticSqlStrings() { byte[][] asBytes = new byte[this.statementComponents.size()][]; this.statementComponents.toArray(asBytes); return asBytes; }
/*      */     public String toString() throws SQLException { StringBuffer buf = new StringBuffer(); Iterator iter = this.statementComponents.iterator(); while (iter.hasNext())
/*      */         buf.append(new String((byte[])iter.next()));  return buf.toString(); } }
/*  748 */   protected static int readFully(Reader reader, char[] buf, int length) throws IOException { int numCharsRead = 0;
/*      */     
/*  750 */     while (numCharsRead < length) {
/*  751 */       int count = reader.read(buf, numCharsRead, length - numCharsRead);
/*      */       
/*  753 */       if (count < 0) {
/*      */         break;
/*      */       }
/*      */       
/*  757 */       numCharsRead += count;
/*      */     } 
/*      */     
/*  760 */     return numCharsRead; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean batchHasPlainStatements = false;
/*      */ 
/*      */ 
/*      */   
/*  771 */   private DatabaseMetaData dbmd = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  777 */   protected char firstCharOfStmt = Character.MIN_VALUE;
/*      */ 
/*      */   
/*      */   protected boolean hasLimitClause = false;
/*      */ 
/*      */   
/*      */   protected boolean isLoadDataQuery = false;
/*      */   
/*  785 */   private boolean[] isNull = null;
/*      */   
/*  787 */   private boolean[] isStream = null;
/*      */   
/*  789 */   protected int numberOfExecutions = 0;
/*      */ 
/*      */   
/*  792 */   protected String originalSql = null;
/*      */ 
/*      */   
/*      */   protected int parameterCount;
/*      */   
/*      */   protected MysqlParameterMetadata parameterMetaData;
/*      */   
/*  799 */   private InputStream[] parameterStreams = null;
/*      */   
/*  801 */   private byte[][] parameterValues = (byte[][])null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  807 */   protected int[] parameterTypes = null;
/*      */   
/*      */   protected ParseInfo parseInfo;
/*      */   
/*      */   private ResultSetMetaData pstmtResultMetaData;
/*      */   
/*  813 */   private byte[][] staticSqlStrings = (byte[][])null;
/*      */   
/*  815 */   private byte[] streamConvertBuf = new byte[4096];
/*      */   
/*  817 */   private int[] streamLengths = null;
/*      */   
/*  819 */   private SimpleDateFormat tsdf = null;
/*      */ 
/*      */   
/*      */   protected boolean useTrueBoolean = false;
/*      */ 
/*      */   
/*      */   protected boolean usingAnsiMode;
/*      */ 
/*      */   
/*      */   protected String batchedValuesClause;
/*      */   
/*      */   private boolean doPingInstead;
/*      */   
/*      */   private SimpleDateFormat ddf;
/*      */   
/*      */   private SimpleDateFormat tdf;
/*      */   
/*      */   private boolean compensateForOnDuplicateKeyUpdate = false;
/*      */   
/*      */   private CharsetEncoder charsetEncoder;
/*      */   
/*  840 */   private int batchCommandIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int rewrittenBatchSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String catalog) throws SQLException {
/*  851 */     if (!Util.isJdbc4()) {
/*  852 */       return new PreparedStatement(conn, catalog);
/*      */     }
/*      */     
/*  855 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_2_ARG_CTOR, new Object[] { conn, catalog }, conn.getExceptionInterceptor());
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
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String sql, String catalog) throws SQLException {
/*  868 */     if (!Util.isJdbc4()) {
/*  869 */       return new PreparedStatement(conn, sql, catalog);
/*      */     }
/*      */     
/*  872 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_3_ARG_CTOR, new Object[] { conn, sql, catalog }, conn.getExceptionInterceptor());
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
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String sql, String catalog, ParseInfo cachedParseInfo) throws SQLException {
/*  885 */     if (!Util.isJdbc4()) {
/*  886 */       return new PreparedStatement(conn, sql, catalog, cachedParseInfo);
/*      */     }
/*      */     
/*  889 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_4_ARG_CTOR, new Object[] { conn, sql, catalog, cachedParseInfo }, conn.getExceptionInterceptor());
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
/*      */   
/*      */   public PreparedStatement(MySQLConnection conn, String catalog) throws SQLException
/*      */   {
/*  907 */     super(conn, catalog);
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
/* 2575 */     this.rewrittenBatchSize = 0; this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts(); } public PreparedStatement(MySQLConnection conn, String sql, String catalog) throws SQLException { super(conn, catalog); this.rewrittenBatchSize = 0; if (sql == null) throw SQLError.createSQLException(Messages.getString("PreparedStatement.0"), "S1009", getExceptionInterceptor());  this.originalSql = sql; if (this.originalSql.startsWith("/* ping */")) { this.doPingInstead = true; } else { this.doPingInstead = false; }  this.dbmd = this.connection.getMetaData(); this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23); this.parseInfo = new ParseInfo(sql, this.connection, this.dbmd, this.charEncoding, this.charConverter); initializeFromParseInfo(); this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts(); if (conn.getRequiresEscapingEncoder()) this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();  } public void addBatch() throws SQLException { if (this.batchedArgs == null) this.batchedArgs = new ArrayList();  for (int i = 0; i < this.parameterValues.length; i++) checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);  this.batchedArgs.add(new BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull)); } public void addBatch(String sql) throws SQLException { this.batchHasPlainStatements = true; super.addBatch(sql); } protected String asSql() throws SQLException { return asSql(false); } protected String asSql(boolean quoteStreamsAndUnknowns) throws SQLException { if (this.isClosed) return "statement has been closed, no further internal information available";  StringBuffer buf = new StringBuffer(); try { int realParameterCount = this.parameterCount + getParameterIndexOffset(); Object batchArg = null; if (this.batchCommandIndex != -1) batchArg = this.batchedArgs.get(this.batchCommandIndex);  for (int i = 0; i < realParameterCount; i++) { if (this.charEncoding != null) { buf.append(new String(this.staticSqlStrings[i], this.charEncoding)); } else { buf.append(new String(this.staticSqlStrings[i])); }  byte[] val = null; if (batchArg != null && batchArg instanceof String) { buf.append((String)batchArg); } else { if (this.batchCommandIndex == -1) { val = this.parameterValues[i]; } else { val = ((BatchParams)batchArg).parameterStrings[i]; }  boolean isStreamParam = false; if (this.batchCommandIndex == -1) { isStreamParam = this.isStream[i]; } else { isStreamParam = ((BatchParams)batchArg).isStream[i]; }  if (val == null && !isStreamParam) { if (quoteStreamsAndUnknowns) buf.append("'");  buf.append("** NOT SPECIFIED **"); if (quoteStreamsAndUnknowns) buf.append("'");  } else if (isStreamParam) { if (quoteStreamsAndUnknowns) buf.append("'");  buf.append("** STREAM DATA **"); if (quoteStreamsAndUnknowns) buf.append("'");  } else if (this.charConverter != null) { buf.append(this.charConverter.toString(val)); } else if (this.charEncoding != null) { buf.append(new String(val, this.charEncoding)); } else { buf.append(StringUtils.toAsciiString(val)); }  }  }  if (this.charEncoding != null) { buf.append(new String(this.staticSqlStrings[this.parameterCount + getParameterIndexOffset()], this.charEncoding)); } else { buf.append(StringUtils.toAsciiString(this.staticSqlStrings[this.parameterCount + getParameterIndexOffset()])); }  } catch (UnsupportedEncodingException uue) { throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33")); }  return buf.toString(); } public void clearBatch() throws SQLException { this.batchHasPlainStatements = false; super.clearBatch(); } public void clearParameters() throws SQLException { checkClosed(); for (int i = 0; i < this.parameterValues.length; i++) { this.parameterValues[i] = null; this.parameterStreams[i] = null; this.isStream[i] = false; this.isNull[i] = false; this.parameterTypes[i] = 0; }  } public void close() throws SQLException { realClose(true, true); } private final void escapeblockFast(byte[] buf, Buffer packet, int size) throws SQLException { int lastwritten = 0; for (int i = 0; i < size; i++) { byte b = buf[i]; if (b == 0) { if (i > lastwritten) packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);  packet.writeByte((byte)92); packet.writeByte((byte)48); lastwritten = i + 1; } else if (b == 92 || b == 39 || (!this.usingAnsiMode && b == 34)) { if (i > lastwritten) packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);  packet.writeByte((byte)92); lastwritten = i; }  }  if (lastwritten < size) packet.writeBytesNoNull(buf, lastwritten, size - lastwritten);  } public PreparedStatement(MySQLConnection conn, String sql, String catalog, ParseInfo cachedParseInfo) throws SQLException { super(conn, catalog); this.rewrittenBatchSize = 0; if (sql == null) throw SQLError.createSQLException(Messages.getString("PreparedStatement.1"), "S1009", getExceptionInterceptor());  this.originalSql = sql; this.dbmd = this.connection.getMetaData(); this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23); this.parseInfo = cachedParseInfo; this.usingAnsiMode = !this.connection.useAnsiQuotedIdentifiers(); initializeFromParseInfo(); this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts(); if (conn.getRequiresEscapingEncoder()) this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();  }
/*      */   private final void escapeblockFast(byte[] buf, ByteArrayOutputStream bytesOut, int size) { int lastwritten = 0; for (int i = 0; i < size; i++) { byte b = buf[i]; if (b == 0) { if (i > lastwritten) bytesOut.write(buf, lastwritten, i - lastwritten);  bytesOut.write(92); bytesOut.write(48); lastwritten = i + 1; } else if (b == 92 || b == 39 || (!this.usingAnsiMode && b == 34)) { if (i > lastwritten) bytesOut.write(buf, lastwritten, i - lastwritten);  bytesOut.write(92); lastwritten = i; }  }  if (lastwritten < size) bytesOut.write(buf, lastwritten, size - lastwritten);  }
/*      */   protected boolean checkReadOnlySafeStatement() throws SQLException { return (!this.connection.isReadOnly() || this.firstCharOfStmt == 'S'); }
/* 2578 */   public boolean execute() throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; if (!checkReadOnlySafeStatement()) throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009", getExceptionInterceptor());  ResultSetInternalMethods rs = null; CachedResultSetMetaData cachedMetadata = null; synchronized (locallyScopedConn.getMutex()) { this.lastQueryIsOnDupKeyUpdate = false; if (this.retrieveGeneratedKeys) this.lastQueryIsOnDupKeyUpdate = containsOnDuplicateKeyUpdateInSQL();  boolean doStreaming = createStreamingResultSet(); clearWarnings(); if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0) executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());  this.batchedGeneratedKeys = null; Buffer sendPacket = fillSendPacket(); String oldCatalog = null; if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  if (locallyScopedConn.getCacheResultSetMetadata()) cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);  Field[] metadataFromCache = null; if (cachedMetadata != null) metadataFromCache = cachedMetadata.fields;  boolean oldInfoMsgState = false; if (this.retrieveGeneratedKeys) { oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); }  if (locallyScopedConn.useMaxRows()) { int rowLimit = -1; if (this.firstCharOfStmt == 'S') { if (this.hasLimitClause) { rowLimit = this.maxRows; } else if (this.maxRows <= 0) { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows); }  } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); }  rs = executeInternal(rowLimit, sendPacket, doStreaming, (this.firstCharOfStmt == 'S'), metadataFromCache, false); } else { rs = executeInternal(-1, sendPacket, doStreaming, (this.firstCharOfStmt == 'S'), metadataFromCache, false); }  if (cachedMetadata != null) { locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results); } else if (rs.reallyResult() && locallyScopedConn.getCacheResultSetMetadata()) { locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, rs); }  if (this.retrieveGeneratedKeys) { locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState); rs.setFirstCharOfQuery(this.firstCharOfStmt); }  if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  if (rs != null) { this.lastInsertId = rs.getUpdateID(); this.results = rs; }  }  return (rs != null && rs.reallyResult()); } public int[] executeBatch() throws SQLException { checkClosed(); if (this.connection.isReadOnly()) throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");  synchronized (this.connection.getMutex()) { if (this.batchedArgs == null || this.batchedArgs.size() == 0) return new int[0];  int batchTimeout = this.timeoutInMillis; this.timeoutInMillis = 0; resetCancelledState(); try { clearWarnings(); if (!this.batchHasPlainStatements && this.connection.getRewriteBatchedStatements()) { if (canRewriteAsMultiValueInsertAtSqlLevel()) return executeBatchedInserts(batchTimeout);  if (this.connection.versionMeetsMinimum(4, 1, 0) && !this.batchHasPlainStatements && this.batchedArgs != null && this.batchedArgs.size() > 3) return executePreparedBatchAsMultiStatement(batchTimeout);  }  return executeBatchSerially(batchTimeout); } finally { clearBatch(); }  }  } public boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException { return this.parseInfo.canRewriteAsMultiValueInsert; } protected int getLocationOfOnDuplicateKeyUpdate() { return this.parseInfo.locationOfOnDuplicateKeyUpdate; } protected int[] executePreparedBatchAsMultiStatement(int batchTimeout) throws SQLException { synchronized (this.connection.getMutex()) { if (this.batchedValuesClause == null) this.batchedValuesClause = this.originalSql + ";";  MySQLConnection locallyScopedConn = this.connection; boolean multiQueriesEnabled = locallyScopedConn.getAllowMultiQueries(); StatementImpl.CancelTask timeoutTask = null; try { clearWarnings(); int numBatchedArgs = this.batchedArgs.size(); if (this.retrieveGeneratedKeys) this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);  int numValuesPerBatch = computeBatchSize(numBatchedArgs); if (numBatchedArgs < numValuesPerBatch) numValuesPerBatch = numBatchedArgs;  PreparedStatement batchedStatement = null; int batchedParamIndex = 1; int numberToExecuteAsMultiValue = 0; int batchCounter = 0; int updateCountCounter = 0; int[] updateCounts = new int[numBatchedArgs]; SQLException sqlEx = null; try { if (!multiQueriesEnabled) locallyScopedConn.getIO().enableMultiQueries();  if (this.retrieveGeneratedKeys) { batchedStatement = locallyScopedConn.prepareStatement(generateMultiStatementForBatch(numValuesPerBatch), 1); } else { batchedStatement = locallyScopedConn.prepareStatement(generateMultiStatementForBatch(numValuesPerBatch)); }  if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new StatementImpl.CancelTask(this, (StatementImpl)batchedStatement); locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout); }  if (numBatchedArgs < numValuesPerBatch) { numberToExecuteAsMultiValue = numBatchedArgs; } else { numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch; }  int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch; for (i = 0; i < numberArgsToExecute; i++) { if (i != 0 && i % numValuesPerBatch == 0) { try { batchedStatement.execute(); } catch (SQLException ex) { sqlEx = handleExceptionForBatch(batchCounter, numValuesPerBatch, updateCounts, ex); }  updateCountCounter = processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts); batchedStatement.clearParameters(); batchedParamIndex = 1; }  batchedParamIndex = setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++)); }  try { batchedStatement.execute(); } catch (SQLException i) { SQLException ex; sqlEx = handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex); }  updateCountCounter = processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts); batchedStatement.clearParameters(); numValuesPerBatch = numBatchedArgs - batchCounter; } finally { if (batchedStatement != null) batchedStatement.close();  }  try { if (numValuesPerBatch > 0) { if (this.retrieveGeneratedKeys) { batchedStatement = locallyScopedConn.prepareStatement(generateMultiStatementForBatch(numValuesPerBatch), 1); } else { batchedStatement = locallyScopedConn.prepareStatement(generateMultiStatementForBatch(numValuesPerBatch)); }  if (timeoutTask != null) timeoutTask.toCancel = (StatementImpl)batchedStatement;  batchedParamIndex = 1; while (batchCounter < numBatchedArgs) batchedParamIndex = setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));  try { batchedStatement.execute(); } catch (SQLException ex) { sqlEx = handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex); }  updateCountCounter = processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts); batchedStatement.clearParameters(); }  if (timeoutTask != null) { if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); timeoutTask = null; }  if (sqlEx != null) throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);  return updateCounts; } finally { if (batchedStatement != null) batchedStatement.close();  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  resetCancelledState(); if (!multiQueriesEnabled) locallyScopedConn.getIO().disableMultiQueries();  clearBatch(); }  }  } private String generateMultiStatementForBatch(int numBatches) { StringBuffer newStatementSql = new StringBuffer((this.originalSql.length() + 1) * numBatches); newStatementSql.append(this.originalSql); for (int i = 0; i < numBatches - 1; i++) { newStatementSql.append(';'); newStatementSql.append(this.originalSql); }  return newStatementSql.toString(); } public int getRewrittenBatchSize() { return this.rewrittenBatchSize; } protected int[] executeBatchedInserts(int batchTimeout) throws SQLException { String valuesClause = getValuesClause(); MySQLConnection locallyScopedConn = this.connection; if (valuesClause == null) return executeBatchSerially(batchTimeout);  int numBatchedArgs = this.batchedArgs.size(); if (this.retrieveGeneratedKeys) this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);  int numValuesPerBatch = computeBatchSize(numBatchedArgs); if (numBatchedArgs < numValuesPerBatch) numValuesPerBatch = numBatchedArgs;  PreparedStatement batchedStatement = null; int batchedParamIndex = 1; int updateCountRunningTotal = 0; int numberToExecuteAsMultiValue = 0; int batchCounter = 0; StatementImpl.CancelTask timeoutTask = null; SQLException sqlEx = null; int[] updateCounts = new int[numBatchedArgs]; for (i = 0; i < this.batchedArgs.size(); i++) updateCounts[i] = 1;  try { try { batchedStatement = prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch); if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new StatementImpl.CancelTask(this, (StatementImpl)batchedStatement); locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout); }  if (numBatchedArgs < numValuesPerBatch) { numberToExecuteAsMultiValue = numBatchedArgs; } else { numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch; }  int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch; for (i = 0; i < numberArgsToExecute; i++) { if (i != 0 && i % numValuesPerBatch == 0) { try { updateCountRunningTotal += batchedStatement.executeUpdate(); } catch (SQLException ex) { sqlEx = handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex); }  getBatchedGeneratedKeys(batchedStatement); batchedStatement.clearParameters(); batchedParamIndex = 1; }  batchedParamIndex = setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++)); }  try { updateCountRunningTotal += batchedStatement.executeUpdate(); } catch (SQLException i) { SQLException ex; sqlEx = handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex); }  getBatchedGeneratedKeys(batchedStatement); numValuesPerBatch = numBatchedArgs - batchCounter; } finally { if (batchedStatement != null) batchedStatement.close();  }  try { if (numValuesPerBatch > 0) { batchedStatement = prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch); if (timeoutTask != null) timeoutTask.toCancel = (StatementImpl)batchedStatement;  batchedParamIndex = 1; while (batchCounter < numBatchedArgs) batchedParamIndex = setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));  try { updateCountRunningTotal += batchedStatement.executeUpdate(); } catch (SQLException i) { SQLException ex; sqlEx = handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex); }  getBatchedGeneratedKeys(batchedStatement); }  if (sqlEx != null) throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);  return updateCounts; } finally { if (batchedStatement != null) batchedStatement.close();  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  resetCancelledState(); }  } protected String getValuesClause() throws SQLException { return this.parseInfo.valuesClause; } protected int computeBatchSize(int numBatchedArgs) throws SQLException { long[] combinedValues = computeMaxParameterSetSizeAndBatchSize(numBatchedArgs); long maxSizeOfParameterSet = combinedValues[0]; long sizeOfEntireBatch = combinedValues[1]; int maxAllowedPacket = this.connection.getMaxAllowedPacket(); if (sizeOfEntireBatch < (maxAllowedPacket - this.originalSql.length())) return numBatchedArgs;  return (int)Math.max(1L, (maxAllowedPacket - this.originalSql.length()) / maxSizeOfParameterSet); } protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs) throws SQLException { long sizeOfEntireBatch = 0L; long maxSizeOfParameterSet = 0L; for (int i = 0; i < numBatchedArgs; i++) { BatchParams paramArg = (BatchParams)this.batchedArgs.get(i); boolean[] isNullBatch = paramArg.isNull; boolean[] isStreamBatch = paramArg.isStream; long sizeOfParameterSet = 0L; for (int j = 0; j < isNullBatch.length; j++) { if (!isNullBatch[j]) { if (isStreamBatch[j]) { int streamLength = paramArg.streamLengths[j]; if (streamLength != -1) { sizeOfParameterSet += (streamLength * 2); } else { int paramLength = paramArg.parameterStrings[j].length; sizeOfParameterSet += paramLength; }  } else { sizeOfParameterSet += paramArg.parameterStrings[j].length; }  } else { sizeOfParameterSet += 4L; }  }  if (getValuesClause() != null) { sizeOfParameterSet += (getValuesClause().length() + 1); } else { sizeOfParameterSet += (this.originalSql.length() + 1); }  sizeOfEntireBatch += sizeOfParameterSet; if (sizeOfParameterSet > maxSizeOfParameterSet) maxSizeOfParameterSet = sizeOfParameterSet;  }  return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch }; } protected int[] executeBatchSerially(int batchTimeout) throws SQLException { MySQLConnection locallyScopedConn = this.connection; if (locallyScopedConn == null) checkClosed();  int[] updateCounts = null; if (this.batchedArgs != null) { int nbrCommands = this.batchedArgs.size(); updateCounts = new int[nbrCommands]; for (i = 0; i < nbrCommands; i++) updateCounts[i] = -3;  SQLException sqlEx = null; StatementImpl.CancelTask timeoutTask = null; try { if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new StatementImpl.CancelTask(this, this); locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout); }  if (this.retrieveGeneratedKeys) this.batchedGeneratedKeys = new ArrayList(nbrCommands);  for (this.batchCommandIndex = 0; this.batchCommandIndex < nbrCommands; this.batchCommandIndex++) { Object arg = this.batchedArgs.get(this.batchCommandIndex); if (arg instanceof String) { updateCounts[this.batchCommandIndex] = executeUpdate((String)arg); } else { BatchParams paramArg = (BatchParams)arg; try { updateCounts[this.batchCommandIndex] = executeUpdate(paramArg.parameterStrings, paramArg.parameterStreams, paramArg.isStream, paramArg.streamLengths, paramArg.isNull, true); if (this.retrieveGeneratedKeys) { ResultSet rs = null; try { if (containsOnDuplicateKeyUpdateInSQL()) { rs = getGeneratedKeysInternal(1); } else { rs = getGeneratedKeysInternal(); }  while (rs.next()) { this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor())); }  } finally { if (rs != null) rs.close();  }  }  } catch (SQLException ex) { updateCounts[this.batchCommandIndex] = -3; if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !hasDeadlockOrTimeoutRolledBackTx(ex)) { sqlEx = ex; } else { int[] newUpdateCounts = new int[this.batchCommandIndex]; System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex); throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts); }  }  }  }  if (sqlEx != null) throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);  } catch (NullPointerException npe) { try { checkClosed(); } catch (SQLException connectionClosedEx) { updateCounts[this.batchCommandIndex] = -3; int[] newUpdateCounts = new int[this.batchCommandIndex]; System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex); throw new BatchUpdateException(connectionClosedEx.getMessage(), connectionClosedEx.getSQLState(), connectionClosedEx.getErrorCode(), newUpdateCounts); }  throw npe; } finally { this.batchCommandIndex = -1; if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConn.getCancelTimer().purge(); }  resetCancelledState(); }  }  return (updateCounts != null) ? updateCounts : new int[0]; }
/*      */   public String getDateTime(String pattern) { SimpleDateFormat sdf = new SimpleDateFormat(pattern); return sdf.format(new Date()); }
/*      */   protected ResultSetInternalMethods executeInternal(int maxRowsToRetrieve, Buffer sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, Field[] metadataFromCache, boolean isBatch) throws SQLException { try { ResultSetInternalMethods rs; resetCancelledState(); MySQLConnection locallyScopedConnection = this.connection; this.numberOfExecutions++; if (this.doPingInstead) { doPingInstead(); return this.results; }  StatementImpl.CancelTask timeoutTask = null; try { if (locallyScopedConnection.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConnection.versionMeetsMinimum(5, 0, 0)) { timeoutTask = new StatementImpl.CancelTask(this, this); locallyScopedConnection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis); }  rs = locallyScopedConnection.execSQL(this, null, maxRowsToRetrieve, sendPacket, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, metadataFromCache, isBatch); if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConnection.getCancelTimer().purge(); if (timeoutTask.caughtWhileCancelling != null) throw timeoutTask.caughtWhileCancelling;  timeoutTask = null; }  synchronized (this.cancelTimeoutMutex) { if (this.wasCancelled) { MySQLStatementCancelledException mySQLStatementCancelledException = null; if (this.wasCancelledByTimeout) { MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException(); } else { mySQLStatementCancelledException = new MySQLStatementCancelledException(); }  resetCancelledState(); throw mySQLStatementCancelledException; }  }  } finally { if (timeoutTask != null) { timeoutTask.cancel(); locallyScopedConnection.getCancelTimer().purge(); }  }  return rs; } catch (NullPointerException npe) { checkClosed(); throw npe; }  }
/*      */   public ResultSet executeQuery() throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; checkForDml(this.originalSql, this.firstCharOfStmt); CachedResultSetMetaData cachedMetadata = null; synchronized (locallyScopedConn.getMutex()) { clearWarnings(); boolean doStreaming = createStreamingResultSet(); this.batchedGeneratedKeys = null; if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0) { Statement stmt = null; try { stmt = this.connection.createStatement(); ((StatementImpl)stmt).executeSimpleNonQuery(this.connection, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults()); } finally { if (stmt != null) stmt.close();  }  }  Buffer sendPacket = fillSendPacket(); if (this.results != null && !this.connection.getHoldResultsOpenOverStatementClose() && !this.holdResultsOpenOverClose) this.results.realClose(false);  String oldCatalog = null; if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  if (locallyScopedConn.getCacheResultSetMetadata()) cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);  Field[] metadataFromCache = null; if (cachedMetadata != null) metadataFromCache = cachedMetadata.fields;  if (locallyScopedConn.useMaxRows()) { if (this.hasLimitClause) { this.results = executeInternal(this.maxRows, sendPacket, createStreamingResultSet(), true, metadataFromCache, false); } else { if (this.maxRows <= 0) { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT"); } else { executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows); }  this.results = executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false); if (oldCatalog != null) this.connection.setCatalog(oldCatalog);  }  } else { this.results = executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false); }  if (oldCatalog != null) locallyScopedConn.setCatalog(oldCatalog);  if (cachedMetadata != null) { locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results); } else if (locallyScopedConn.getCacheResultSetMetadata()) { locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, this.results); }  }  this.lastInsertId = this.results.getUpdateID(); return this.results; }
/* 2582 */   public String getNonRewrittenSql() throws SQLException { int indexOfBatch = this.originalSql.indexOf(" of: ");
/*      */     
/* 2584 */     if (indexOfBatch != -1) {
/* 2585 */       return this.originalSql.substring(indexOfBatch + 5);
/*      */     }
/*      */     
/* 2588 */     return this.originalSql; } public int executeUpdate() { return executeUpdate(true, false); } protected int executeUpdate(boolean clearBatchedGeneratedKeysAndWarnings, boolean isBatch) throws SQLException { if (clearBatchedGeneratedKeysAndWarnings) { clearWarnings(); this.batchedGeneratedKeys = null; }  return executeUpdate(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull, isBatch); } protected int executeUpdate(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths, boolean[] batchedIsNull, boolean isReallyBatch) throws SQLException { checkClosed(); MySQLConnection locallyScopedConn = this.connection; if (locallyScopedConn.isReadOnly())
/*      */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009", getExceptionInterceptor());  if (this.firstCharOfStmt == 'S' && isSelectQuery())
/*      */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03", getExceptionInterceptor());  if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose())
/*      */       this.results.realClose(false);  ResultSetInternalMethods rs = null; synchronized (locallyScopedConn.getMutex()) { Buffer sendPacket = fillSendPacket(batchedParameterStrings, batchedParameterStreams, batchedIsStream, batchedStreamLengths); String oldCatalog = null; if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) { oldCatalog = locallyScopedConn.getCatalog(); locallyScopedConn.setCatalog(this.currentCatalog); }  if (locallyScopedConn.useMaxRows())
/*      */         executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");  boolean oldInfoMsgState = false; if (this.retrieveGeneratedKeys) { oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled(); locallyScopedConn.setReadInfoMsgEnabled(true); }  rs = executeInternal(-1, sendPacket, false, false, null, isReallyBatch); if (this.retrieveGeneratedKeys) { locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState); rs.setFirstCharOfQuery(this.firstCharOfStmt); }  if (oldCatalog != null)
/*      */         locallyScopedConn.setCatalog(oldCatalog);  }  this.results = rs; this.updateCount = rs.getUpdateCount(); if (containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate)
/*      */       if (this.updateCount == 2L || this.updateCount == 0L)
/*      */         this.updateCount = 1L;   int truncatedUpdateCount = 0; if (this.updateCount > 2147483647L) { truncatedUpdateCount = Integer.MAX_VALUE; } else { truncatedUpdateCount = (int)this.updateCount; }  this.lastInsertId = rs.getUpdateID(); return truncatedUpdateCount; } protected boolean containsOnDuplicateKeyUpdateInSQL() throws SQLException { return this.parseInfo.isOnDuplicateKeyUpdate; }
/*      */   protected Buffer fillSendPacket() throws SQLException { return fillSendPacket(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths); }
/*      */   protected Buffer fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths) throws SQLException { Buffer sendPacket = this.connection.getIO().getSharedSendPacket(); sendPacket.clear(); sendPacket.writeByte((byte)3); boolean useStreamLengths = this.connection.getUseStreamLengthsInPrepStmts(); int ensurePacketSize = 0; String statementComment = this.connection.getStatementComment(); byte[] commentAsBytes = null; if (statementComment != null) { if (this.charConverter != null) { commentAsBytes = this.charConverter.toBytes(statementComment); } else { commentAsBytes = StringUtils.getBytes(statementComment, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()); }  ensurePacketSize += commentAsBytes.length; ensurePacketSize += 6; }  for (i = 0; i < batchedParameterStrings.length; i++) { if (batchedIsStream[i] && useStreamLengths)
/*      */         ensurePacketSize += batchedStreamLengths[i];  }  if (ensurePacketSize != 0)
/*      */       sendPacket.ensureCapacity(ensurePacketSize);  if (commentAsBytes != null) { sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES); sendPacket.writeBytesNoNull(commentAsBytes); sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES); }  for (int i = 0; i < batchedParameterStrings.length; i++) { checkAllParametersSet(batchedParameterStrings[i], batchedParameterStreams[i], i); sendPacket.writeBytesNoNull(this.staticSqlStrings[i]); if (batchedIsStream[i]) { streamToBytes(sendPacket, batchedParameterStreams[i], true, batchedStreamLengths[i], useStreamLengths); } else { sendPacket.writeBytesNoNull(batchedParameterStrings[i]); }
/*      */        }
/*      */      sendPacket.writeBytesNoNull(this.staticSqlStrings[batchedParameterStrings.length]); return sendPacket; }
/*      */   private void checkAllParametersSet(byte[] parameterString, InputStream parameterStream, int columnIndex) throws SQLException { if (parameterString == null && parameterStream == null)
/*      */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.40") + (columnIndex + 1), "07001", getExceptionInterceptor());  }
/*      */   protected PreparedStatement prepareBatchedInsertSQL(MySQLConnection localConn, int numBatches) throws SQLException { PreparedStatement pstmt = new PreparedStatement(localConn, "Rewritten batch of: " + this.originalSql, this.currentCatalog, this.parseInfo.getParseInfoForBatch(numBatches)); pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys); pstmt.rewrittenBatchSize = numBatches; return pstmt; }
/* 2605 */   public byte[] getBytesRepresentation(int parameterIndex) throws SQLException { if (this.isStream[parameterIndex]) {
/* 2606 */       return streamToBytes(this.parameterStreams[parameterIndex], false, this.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2611 */     byte[] parameterVal = this.parameterValues[parameterIndex];
/*      */     
/* 2613 */     if (parameterVal == null) {
/* 2614 */       return null;
/*      */     }
/*      */     
/* 2617 */     if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
/*      */       
/* 2619 */       byte[] valNoQuotes = new byte[parameterVal.length - 2];
/* 2620 */       System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
/*      */ 
/*      */       
/* 2623 */       return valNoQuotes;
/*      */     } 
/*      */     
/* 2626 */     return parameterVal; }
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
/*      */   protected byte[] getBytesRepresentationForBatch(int parameterIndex, int commandIndex) throws SQLException {
/* 2638 */     Object batchedArg = this.batchedArgs.get(commandIndex);
/* 2639 */     if (batchedArg instanceof String) {
/*      */       try {
/* 2641 */         return ((String)batchedArg).getBytes(this.charEncoding);
/*      */       }
/* 2643 */       catch (UnsupportedEncodingException uue) {
/* 2644 */         throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2651 */     BatchParams params = (BatchParams)batchedArg;
/* 2652 */     if (params.isStream[parameterIndex]) {
/* 2653 */       return streamToBytes(params.parameterStreams[parameterIndex], false, params.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
/*      */     }
/*      */     
/* 2656 */     byte[] parameterVal = params.parameterStrings[parameterIndex];
/* 2657 */     if (parameterVal == null) {
/* 2658 */       return null;
/*      */     }
/* 2660 */     if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
/*      */       
/* 2662 */       byte[] valNoQuotes = new byte[parameterVal.length - 2];
/* 2663 */       System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
/*      */ 
/*      */       
/* 2666 */       return valNoQuotes;
/*      */     } 
/*      */     
/* 2669 */     return parameterVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String getDateTimePattern(String dt, boolean toTime) throws Exception {
/* 2679 */     int dtLength = (dt != null) ? dt.length() : 0;
/*      */     
/* 2681 */     if (dtLength >= 8 && dtLength <= 10) {
/* 2682 */       int dashCount = 0;
/* 2683 */       boolean isDateOnly = true;
/*      */       
/* 2685 */       for (int i = 0; i < dtLength; i++) {
/* 2686 */         char c = dt.charAt(i);
/*      */         
/* 2688 */         if (!Character.isDigit(c) && c != '-') {
/* 2689 */           isDateOnly = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 2694 */         if (c == '-') {
/* 2695 */           dashCount++;
/*      */         }
/*      */       } 
/*      */       
/* 2699 */       if (isDateOnly && dashCount == 2) {
/* 2700 */         return "yyyy-MM-dd";
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2707 */     boolean colonsOnly = true;
/*      */     
/* 2709 */     for (int i = 0; i < dtLength; i++) {
/* 2710 */       char c = dt.charAt(i);
/*      */       
/* 2712 */       if (!Character.isDigit(c) && c != ':') {
/* 2713 */         colonsOnly = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 2719 */     if (colonsOnly) {
/* 2720 */       return "HH:mm:ss";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2729 */     StringReader reader = new StringReader(dt + " ");
/* 2730 */     ArrayList vec = new ArrayList();
/* 2731 */     ArrayList vecRemovelist = new ArrayList();
/* 2732 */     Object[] nv = new Object[3];
/*      */     
/* 2734 */     nv[0] = Constants.characterValueOf('y');
/* 2735 */     nv[1] = new StringBuffer();
/* 2736 */     nv[2] = Constants.integerValueOf(0);
/* 2737 */     vec.add(nv);
/*      */     
/* 2739 */     if (toTime) {
/* 2740 */       nv = new Object[3];
/* 2741 */       nv[0] = Constants.characterValueOf('h');
/* 2742 */       nv[1] = new StringBuffer();
/* 2743 */       nv[2] = Constants.integerValueOf(0);
/* 2744 */       vec.add(nv);
/*      */     } 
/*      */     int z;
/* 2747 */     while ((z = reader.read()) != -1) {
/* 2748 */       char separator = (char)z;
/* 2749 */       int maxvecs = vec.size();
/*      */       
/* 2751 */       for (int count = 0; count < maxvecs; count++) {
/* 2752 */         Object[] v = (Object[])vec.get(count);
/* 2753 */         int n = ((Integer)v[2]).intValue();
/* 2754 */         char c = getSuccessor(((Character)v[0]).charValue(), n);
/*      */         
/* 2756 */         if (!Character.isLetterOrDigit(separator)) {
/* 2757 */           if (c == ((Character)v[0]).charValue() && c != 'S') {
/* 2758 */             vecRemovelist.add(v);
/*      */           } else {
/* 2760 */             ((StringBuffer)v[1]).append(separator);
/*      */             
/* 2762 */             if (c == 'X' || c == 'Y') {
/* 2763 */               v[2] = Constants.integerValueOf(4);
/*      */             }
/*      */           } 
/*      */         } else {
/* 2767 */           if (c == 'X') {
/* 2768 */             c = 'y';
/* 2769 */             nv = new Object[3];
/* 2770 */             nv[1] = (new StringBuffer(((StringBuffer)v[1]).toString())).append('M');
/*      */             
/* 2772 */             nv[0] = Constants.characterValueOf('M');
/* 2773 */             nv[2] = Constants.integerValueOf(1);
/* 2774 */             vec.add(nv);
/* 2775 */           } else if (c == 'Y') {
/* 2776 */             c = 'M';
/* 2777 */             nv = new Object[3];
/* 2778 */             nv[1] = (new StringBuffer(((StringBuffer)v[1]).toString())).append('d');
/*      */             
/* 2780 */             nv[0] = Constants.characterValueOf('d');
/* 2781 */             nv[2] = Constants.integerValueOf(1);
/* 2782 */             vec.add(nv);
/*      */           } 
/*      */           
/* 2785 */           ((StringBuffer)v[1]).append(c);
/*      */           
/* 2787 */           if (c == ((Character)v[0]).charValue()) {
/* 2788 */             v[2] = Constants.integerValueOf(n + 1);
/*      */           } else {
/* 2790 */             v[0] = Constants.characterValueOf(c);
/* 2791 */             v[2] = Constants.integerValueOf(1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2796 */       int size = vecRemovelist.size();
/*      */       
/* 2798 */       for (int i = 0; i < size; i++) {
/* 2799 */         Object[] v = (Object[])vecRemovelist.get(i);
/* 2800 */         vec.remove(v);
/*      */       } 
/*      */       
/* 2803 */       vecRemovelist.clear();
/*      */     } 
/*      */     
/* 2806 */     int size = vec.size();
/*      */     
/* 2808 */     for (i = 0; i < size; i++) {
/* 2809 */       Object[] v = (Object[])vec.get(i);
/* 2810 */       char c = ((Character)v[0]).charValue();
/* 2811 */       int n = ((Integer)v[2]).intValue();
/*      */       
/* 2813 */       boolean bk = (getSuccessor(c, n) != c);
/* 2814 */       boolean atEnd = ((c == 's' || c == 'm' || (c == 'h' && toTime)) && bk);
/* 2815 */       boolean finishesAtDate = (bk && c == 'd' && !toTime);
/* 2816 */       boolean containsEnd = (((StringBuffer)v[1]).toString().indexOf('W') != -1);
/*      */ 
/*      */       
/* 2819 */       if ((!atEnd && !finishesAtDate) || containsEnd) {
/* 2820 */         vecRemovelist.add(v);
/*      */       }
/*      */     } 
/*      */     
/* 2824 */     size = vecRemovelist.size();
/*      */     
/* 2826 */     for (int i = 0; i < size; i++) {
/* 2827 */       vec.remove(vecRemovelist.get(i));
/*      */     }
/*      */     
/* 2830 */     vecRemovelist.clear();
/* 2831 */     Object[] v = (Object[])vec.get(0);
/*      */     
/* 2833 */     StringBuffer format = (StringBuffer)v[1];
/* 2834 */     format.setLength(format.length() - 1);
/*      */     
/* 2836 */     return format.toString();
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
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/* 2862 */     if (!isSelectQuery()) {
/* 2863 */       return null;
/*      */     }
/*      */     
/* 2866 */     PreparedStatement mdStmt = null;
/* 2867 */     ResultSet mdRs = null;
/*      */     
/* 2869 */     if (this.pstmtResultMetaData == null) {
/*      */       try {
/* 2871 */         mdStmt = new PreparedStatement(this.connection, this.originalSql, this.currentCatalog, this.parseInfo);
/*      */ 
/*      */         
/* 2874 */         mdStmt.setMaxRows(0);
/*      */         
/* 2876 */         int paramCount = this.parameterValues.length;
/*      */         
/* 2878 */         for (int i = 1; i <= paramCount; i++) {
/* 2879 */           mdStmt.setString(i, "");
/*      */         }
/*      */         
/* 2882 */         boolean hadResults = mdStmt.execute();
/*      */         
/* 2884 */         if (hadResults) {
/* 2885 */           mdRs = mdStmt.getResultSet();
/*      */           
/* 2887 */           this.pstmtResultMetaData = mdRs.getMetaData();
/*      */         } else {
/* 2889 */           this.pstmtResultMetaData = new ResultSetMetaData(new Field[0], this.connection.getUseOldAliasMetadataBehavior(), getExceptionInterceptor());
/*      */         }
/*      */       
/*      */       } finally {
/*      */         
/* 2894 */         SQLException sqlExRethrow = null;
/*      */         
/* 2896 */         if (mdRs != null) {
/*      */           try {
/* 2898 */             mdRs.close();
/* 2899 */           } catch (SQLException sqlEx) {
/* 2900 */             sqlExRethrow = sqlEx;
/*      */           } 
/*      */           
/* 2903 */           mdRs = null;
/*      */         } 
/*      */         
/* 2906 */         if (mdStmt != null) {
/*      */           try {
/* 2908 */             mdStmt.close();
/* 2909 */           } catch (SQLException sqlEx) {
/* 2910 */             sqlExRethrow = sqlEx;
/*      */           } 
/*      */           
/* 2913 */           mdStmt = null;
/*      */         } 
/*      */         
/* 2916 */         if (sqlExRethrow != null) {
/* 2917 */           throw sqlExRethrow;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2922 */     return this.pstmtResultMetaData;
/*      */   }
/*      */ 
/*      */   
/* 2926 */   protected boolean isSelectQuery() throws SQLException { return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(this.originalSql, "'\"", "'\"", true, false, true, true), "SELECT"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ParameterMetaData getParameterMetaData() throws SQLException {
/* 2937 */     if (this.parameterMetaData == null) {
/* 2938 */       if (this.connection.getGenerateSimpleParameterMetadata()) {
/* 2939 */         this.parameterMetaData = new MysqlParameterMetadata(this.parameterCount);
/*      */       } else {
/* 2941 */         this.parameterMetaData = new MysqlParameterMetadata(null, this.parameterCount, getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2946 */     return this.parameterMetaData;
/*      */   }
/*      */ 
/*      */   
/* 2950 */   ParseInfo getParseInfo() { return this.parseInfo; }
/*      */ 
/*      */ 
/*      */   
/* 2954 */   private final char getSuccessor(char c, int n) { return (c == 'y' && n == 2) ? 'X' : ((c == 'y' && n < 4) ? 'y' : ((c == 'y') ? 'M' : ((c == 'M' && n == 2) ? 'Y' : ((c == 'M' && n < 3) ? 'M' : ((c == 'M') ? 'd' : ((c == 'd' && n < 2) ? 'd' : ((c == 'd') ? 'H' : ((c == 'H' && n < 2) ? 'H' : ((c == 'H') ? 'm' : ((c == 'm' && n < 2) ? 'm' : ((c == 'm') ? 's' : ((c == 's' && n < 2) ? 's' : 'W')))))))))))); }
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
/*      */   private final void hexEscapeBlock(byte[] buf, Buffer packet, int size) throws SQLException {
/* 2980 */     for (int i = 0; i < size; i++) {
/* 2981 */       byte b = buf[i];
/* 2982 */       int lowBits = (b & 0xFF) / 16;
/* 2983 */       int highBits = (b & 0xFF) % 16;
/*      */       
/* 2985 */       packet.writeByte(HEX_DIGITS[lowBits]);
/* 2986 */       packet.writeByte(HEX_DIGITS[highBits]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeFromParseInfo() throws SQLException {
/* 2991 */     this.staticSqlStrings = this.parseInfo.staticSql;
/* 2992 */     this.hasLimitClause = this.parseInfo.foundLimitClause;
/* 2993 */     this.isLoadDataQuery = this.parseInfo.foundLoadData;
/* 2994 */     this.firstCharOfStmt = this.parseInfo.firstStmtChar;
/*      */     
/* 2996 */     this.parameterCount = this.staticSqlStrings.length - 1;
/*      */     
/* 2998 */     this.parameterValues = new byte[this.parameterCount][];
/* 2999 */     this.parameterStreams = new InputStream[this.parameterCount];
/* 3000 */     this.isStream = new boolean[this.parameterCount];
/* 3001 */     this.streamLengths = new int[this.parameterCount];
/* 3002 */     this.isNull = new boolean[this.parameterCount];
/* 3003 */     this.parameterTypes = new int[this.parameterCount];
/*      */     
/* 3005 */     clearParameters();
/*      */     
/* 3007 */     for (int j = 0; j < this.parameterCount; j++) {
/* 3008 */       this.isStream[j] = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 3013 */   boolean isNull(int paramIndex) { return this.isNull[paramIndex]; }
/*      */ 
/*      */   
/*      */   private final int readblock(InputStream i, byte[] b) throws SQLException {
/*      */     try {
/* 3018 */       return i.read(b);
/* 3019 */     } catch (Throwable ex) {
/* 3020 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", getExceptionInterceptor());
/*      */       
/* 3022 */       sqlEx.initCause(ex);
/*      */       
/* 3024 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final int readblock(InputStream i, byte[] b, int length) throws SQLException {
/*      */     try {
/* 3031 */       int lengthToRead = length;
/*      */       
/* 3033 */       if (lengthToRead > b.length) {
/* 3034 */         lengthToRead = b.length;
/*      */       }
/*      */       
/* 3037 */       return i.read(b, 0, lengthToRead);
/* 3038 */     } catch (Throwable ex) {
/* 3039 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", getExceptionInterceptor());
/*      */       
/* 3041 */       sqlEx.initCause(ex);
/*      */       
/* 3043 */       throw sqlEx;
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
/*      */   protected void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
/* 3058 */     if (this.useUsageAdvisor && 
/* 3059 */       this.numberOfExecutions <= 1) {
/* 3060 */       String message = Messages.getString("PreparedStatement.43");
/*      */       
/* 3062 */       this.eventSink.consumeEvent(new ProfilerEvent(false, "", this.currentCatalog, this.connectionId, getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3071 */     super.realClose(calledExplicitly, closeOpenResults);
/*      */     
/* 3073 */     this.dbmd = null;
/* 3074 */     this.originalSql = null;
/* 3075 */     this.staticSqlStrings = (byte[][])null;
/* 3076 */     this.parameterValues = (byte[][])null;
/* 3077 */     this.parameterStreams = null;
/* 3078 */     this.isStream = null;
/* 3079 */     this.streamLengths = null;
/* 3080 */     this.isNull = null;
/* 3081 */     this.streamConvertBuf = null;
/* 3082 */     this.parameterTypes = null;
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
/*      */ 
/*      */   
/* 3099 */   public void setArray(int i, Array x) throws SQLException { throw SQLError.notImplemented(); }
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
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 3126 */     if (x == null) {
/* 3127 */       setNull(parameterIndex, 12);
/*      */     } else {
/* 3129 */       setBinaryStream(parameterIndex, x, length);
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
/*      */   
/*      */   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
/* 3147 */     if (x == null) {
/* 3148 */       setNull(parameterIndex, 3);
/*      */     } else {
/* 3150 */       setInternal(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString(x)));
/*      */ 
/*      */       
/* 3153 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 3;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 3179 */     if (x == null) {
/* 3180 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 3182 */       int parameterIndexOffset = getParameterIndexOffset();
/*      */       
/* 3184 */       if (parameterIndex < 1 || parameterIndex > this.staticSqlStrings.length)
/*      */       {
/* 3186 */         throw SQLError.createSQLException(Messages.getString("PreparedStatement.2") + parameterIndex + Messages.getString("PreparedStatement.3") + this.staticSqlStrings.length + Messages.getString("PreparedStatement.4"), "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3191 */       if (parameterIndexOffset == -1 && parameterIndex == 1) {
/* 3192 */         throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3197 */       this.parameterStreams[parameterIndex - 1 + parameterIndexOffset] = x;
/* 3198 */       this.isStream[parameterIndex - 1 + parameterIndexOffset] = true;
/* 3199 */       this.streamLengths[parameterIndex - 1 + parameterIndexOffset] = length;
/* 3200 */       this.isNull[parameterIndex - 1 + parameterIndexOffset] = false;
/* 3201 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 2004;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 3207 */   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException { setBinaryStream(parameterIndex, inputStream, (int)length); }
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
/*      */   public void setBlob(int i, Blob x) throws SQLException {
/* 3222 */     if (x == null) {
/* 3223 */       setNull(i, 2004);
/*      */     } else {
/* 3225 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/*      */       
/* 3227 */       bytesOut.write(39);
/* 3228 */       escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length());
/*      */       
/* 3230 */       bytesOut.write(39);
/*      */       
/* 3232 */       setInternal(i, bytesOut.toByteArray());
/*      */       
/* 3234 */       this.parameterTypes[i - 1 + getParameterIndexOffset()] = 2004;
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
/*      */   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
/* 3251 */     if (this.useTrueBoolean) {
/* 3252 */       setInternal(parameterIndex, x ? "1" : "0");
/*      */     } else {
/* 3254 */       setInternal(parameterIndex, x ? "'t'" : "'f'");
/*      */       
/* 3256 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 16;
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
/*      */   public void setByte(int parameterIndex, byte x) throws SQLException {
/* 3273 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3275 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = -6;
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
/*      */   
/*      */   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
/* 3292 */     setBytes(parameterIndex, x, true, true);
/*      */     
/* 3294 */     if (x != null) {
/* 3295 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = -2;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBytes(int parameterIndex, byte[] x, boolean checkForIntroducer, boolean escapeForMBChars) throws SQLException {
/* 3302 */     if (x == null) {
/* 3303 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 3305 */       String connectionEncoding = this.connection.getEncoding();
/*      */       
/* 3307 */       if (this.connection.isNoBackslashEscapesSet() || (escapeForMBChars && this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3315 */         ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
/*      */         
/* 3317 */         bOut.write(120);
/* 3318 */         bOut.write(39);
/*      */         
/* 3320 */         for (int i = 0; i < x.length; i++) {
/* 3321 */           int lowBits = (x[i] & 0xFF) / 16;
/* 3322 */           int highBits = (x[i] & 0xFF) % 16;
/*      */           
/* 3324 */           bOut.write(HEX_DIGITS[lowBits]);
/* 3325 */           bOut.write(HEX_DIGITS[highBits]);
/*      */         } 
/*      */         
/* 3328 */         bOut.write(39);
/*      */         
/* 3330 */         setInternal(parameterIndex, bOut.toByteArray());
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 3336 */       int numBytes = x.length;
/*      */       
/* 3338 */       int pad = 2;
/*      */       
/* 3340 */       boolean needsIntroducer = (checkForIntroducer && this.connection.versionMeetsMinimum(4, 1, 0));
/*      */ 
/*      */       
/* 3343 */       if (needsIntroducer) {
/* 3344 */         pad += 7;
/*      */       }
/*      */       
/* 3347 */       ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes + pad);
/*      */ 
/*      */       
/* 3350 */       if (needsIntroducer) {
/* 3351 */         bOut.write(95);
/* 3352 */         bOut.write(98);
/* 3353 */         bOut.write(105);
/* 3354 */         bOut.write(110);
/* 3355 */         bOut.write(97);
/* 3356 */         bOut.write(114);
/* 3357 */         bOut.write(121);
/*      */       } 
/* 3359 */       bOut.write(39);
/*      */       
/* 3361 */       for (int i = 0; i < numBytes; i++) {
/* 3362 */         byte b = x[i];
/*      */         
/* 3364 */         switch (b) {
/*      */           case 0:
/* 3366 */             bOut.write(92);
/* 3367 */             bOut.write(48);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 10:
/* 3372 */             bOut.write(92);
/* 3373 */             bOut.write(110);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 13:
/* 3378 */             bOut.write(92);
/* 3379 */             bOut.write(114);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 92:
/* 3384 */             bOut.write(92);
/* 3385 */             bOut.write(92);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 39:
/* 3390 */             bOut.write(92);
/* 3391 */             bOut.write(39);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 34:
/* 3396 */             bOut.write(92);
/* 3397 */             bOut.write(34);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 26:
/* 3402 */             bOut.write(92);
/* 3403 */             bOut.write(90);
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 3408 */             bOut.write(b);
/*      */             break;
/*      */         } 
/*      */       } 
/* 3412 */       bOut.write(39);
/*      */       
/* 3414 */       setInternal(parameterIndex, bOut.toByteArray());
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
/*      */   
/*      */   protected void setBytesNoEscape(int parameterIndex, byte[] parameterAsBytes) throws SQLException {
/* 3432 */     byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
/* 3433 */     parameterWithQuotes[0] = 39;
/* 3434 */     System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
/*      */     
/* 3436 */     parameterWithQuotes[parameterAsBytes.length + 1] = 39;
/*      */     
/* 3438 */     setInternal(parameterIndex, parameterWithQuotes);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 3443 */   protected void setBytesNoEscapeNoQuotes(int parameterIndex, byte[] parameterAsBytes) throws SQLException { setInternal(parameterIndex, parameterAsBytes); }
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
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
/*      */     try {
/* 3471 */       if (reader == null) {
/* 3472 */         setNull(parameterIndex, -1);
/*      */       } else {
/* 3474 */         char[] c = null;
/* 3475 */         int len = 0;
/*      */         
/* 3477 */         boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
/*      */ 
/*      */         
/* 3480 */         String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */         
/* 3482 */         if (useLength && length != -1) {
/* 3483 */           c = new char[length];
/*      */           
/* 3485 */           int numCharsRead = readFully(reader, c, length);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3490 */           if (forcedEncoding == null) {
/* 3491 */             setString(parameterIndex, new String(c, false, numCharsRead));
/*      */           } else {
/*      */             try {
/* 3494 */               setBytes(parameterIndex, (new String(c, false, numCharsRead)).getBytes(forcedEncoding));
/*      */             
/*      */             }
/* 3497 */             catch (UnsupportedEncodingException uee) {
/* 3498 */               throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 3503 */           c = new char[4096];
/*      */           
/* 3505 */           StringBuffer buf = new StringBuffer();
/*      */           
/* 3507 */           while ((len = reader.read(c)) != -1) {
/* 3508 */             buf.append(c, 0, len);
/*      */           }
/*      */           
/* 3511 */           if (forcedEncoding == null) {
/* 3512 */             setString(parameterIndex, buf.toString());
/*      */           } else {
/*      */             try {
/* 3515 */               setBytes(parameterIndex, buf.toString().getBytes(forcedEncoding));
/*      */             }
/* 3517 */             catch (UnsupportedEncodingException uee) {
/* 3518 */               throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3524 */         this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 2005;
/*      */       } 
/* 3526 */     } catch (IOException ioEx) {
/* 3527 */       throw SQLError.createSQLException(ioEx.toString(), "S1000", getExceptionInterceptor());
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
/*      */   public void setClob(int i, Clob x) throws SQLException {
/* 3544 */     if (x == null) {
/* 3545 */       setNull(i, 2005);
/*      */     } else {
/*      */       
/* 3548 */       String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */       
/* 3550 */       if (forcedEncoding == null) {
/* 3551 */         setString(i, x.getSubString(1L, (int)x.length()));
/*      */       } else {
/*      */         try {
/* 3554 */           setBytes(i, x.getSubString(1L, (int)x.length()).getBytes(forcedEncoding));
/*      */         }
/* 3556 */         catch (UnsupportedEncodingException uee) {
/* 3557 */           throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3562 */       this.parameterTypes[i - 1 + getParameterIndexOffset()] = 2005;
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
/*      */ 
/*      */   
/* 3580 */   public void setDate(int parameterIndex, Date x) throws SQLException { setDate(parameterIndex, x, null); }
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
/*      */   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
/* 3599 */     if (x == null) {
/* 3600 */       setNull(parameterIndex, 91);
/*      */     } else {
/* 3602 */       checkClosed();
/*      */       
/* 3604 */       if (!this.useLegacyDatetimeCode) {
/* 3605 */         newSetDateInternal(parameterIndex, x, cal);
/*      */       }
/*      */       else {
/*      */         
/* 3609 */         SimpleDateFormat dateFormatter = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
/*      */         
/* 3611 */         setInternal(parameterIndex, dateFormatter.format(x));
/*      */         
/* 3613 */         this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 91;
/*      */       } 
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
/*      */   
/*      */   public void setDouble(int parameterIndex, double x) throws SQLException {
/* 3632 */     if (!this.connection.getAllowNanAndInf() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x)))
/*      */     {
/*      */       
/* 3635 */       throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3641 */     setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
/*      */ 
/*      */     
/* 3644 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 8;
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
/*      */   public void setFloat(int parameterIndex, float x) throws SQLException {
/* 3660 */     setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
/*      */ 
/*      */     
/* 3663 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 6;
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
/*      */   public void setInt(int parameterIndex, int x) throws SQLException {
/* 3679 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3681 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 4;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setInternal(int paramIndex, byte[] val) throws SQLException {
/* 3686 */     if (this.isClosed) {
/* 3687 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.48"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 3691 */     int parameterIndexOffset = getParameterIndexOffset();
/*      */     
/* 3693 */     checkBounds(paramIndex, parameterIndexOffset);
/*      */     
/* 3695 */     this.isStream[paramIndex - 1 + parameterIndexOffset] = false;
/* 3696 */     this.isNull[paramIndex - 1 + parameterIndexOffset] = false;
/* 3697 */     this.parameterStreams[paramIndex - 1 + parameterIndexOffset] = null;
/* 3698 */     this.parameterValues[paramIndex - 1 + parameterIndexOffset] = val;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkBounds(int paramIndex, int parameterIndexOffset) throws SQLException {
/* 3703 */     if (paramIndex < 1) {
/* 3704 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */     
/* 3708 */     if (paramIndex > this.parameterCount) {
/* 3709 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + this.parameterValues.length + Messages.getString("PreparedStatement.53"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3714 */     if (parameterIndexOffset == -1 && paramIndex == 1) {
/* 3715 */       throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setInternal(int paramIndex, String val) throws SQLException {
/* 3722 */     checkClosed();
/*      */     
/* 3724 */     byte[] parameterAsBytes = null;
/*      */     
/* 3726 */     if (this.charConverter != null) {
/* 3727 */       parameterAsBytes = this.charConverter.toBytes(val);
/*      */     } else {
/* 3729 */       parameterAsBytes = StringUtils.getBytes(val, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3735 */     setInternal(paramIndex, parameterAsBytes);
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
/*      */   public void setLong(int parameterIndex, long x) throws SQLException {
/* 3751 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3753 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = -5;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNull(int parameterIndex, int sqlType) throws SQLException {
/* 3773 */     setInternal(parameterIndex, "null");
/* 3774 */     this.isNull[parameterIndex - 1 + getParameterIndexOffset()] = true;
/*      */     
/* 3776 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNull(int parameterIndex, int sqlType, String arg) throws SQLException {
/* 3798 */     setNull(parameterIndex, sqlType);
/*      */     
/* 3800 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setNumericObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
/*      */     Number parameterAsNum;
/* 3806 */     if (parameterObj instanceof Boolean) {
/* 3807 */       parameterAsNum = ((Boolean)parameterObj).booleanValue() ? Constants.integerValueOf(1) : Constants.integerValueOf(0);
/*      */     
/*      */     }
/* 3810 */     else if (parameterObj instanceof String) {
/* 3811 */       boolean parameterAsBoolean; switch (targetSqlType) {
/*      */         case -7:
/* 3813 */           if ("1".equals((String)parameterObj) || "0".equals((String)parameterObj)) {
/*      */             
/* 3815 */             Number parameterAsNum = Integer.valueOf((String)parameterObj); break;
/*      */           } 
/* 3817 */           parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
/*      */ 
/*      */           
/* 3820 */           parameterAsNum = parameterAsBoolean ? Constants.integerValueOf(1) : Constants.integerValueOf(0);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case -6:
/*      */         case 4:
/*      */         case 5:
/* 3829 */           parameterAsNum = Integer.valueOf((String)parameterObj);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case -5:
/* 3835 */           parameterAsNum = Long.valueOf((String)parameterObj);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 7:
/* 3841 */           parameterAsNum = Float.valueOf((String)parameterObj);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 6:
/*      */         case 8:
/* 3848 */           parameterAsNum = Double.valueOf((String)parameterObj);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 3856 */           parameterAsNum = new BigDecimal((String)parameterObj);
/*      */           break;
/*      */       } 
/*      */     } else {
/* 3860 */       parameterAsNum = (Number)parameterObj;
/*      */     } 
/*      */     
/* 3863 */     switch (targetSqlType) {
/*      */       case -7:
/*      */       case -6:
/*      */       case 4:
/*      */       case 5:
/* 3868 */         setInt(parameterIndex, parameterAsNum.intValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case -5:
/* 3873 */         setLong(parameterIndex, parameterAsNum.longValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 7:
/* 3878 */         setFloat(parameterIndex, parameterAsNum.floatValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 8:
/* 3884 */         setDouble(parameterIndex, parameterAsNum.doubleValue());
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 3:
/* 3891 */         if (parameterAsNum instanceof BigDecimal) {
/* 3892 */           BigDecimal scaledBigDecimal = null;
/*      */           
/*      */           try {
/* 3895 */             scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale);
/*      */           }
/* 3897 */           catch (ArithmeticException ex) {
/*      */             try {
/* 3899 */               scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale, 4);
/*      */             
/*      */             }
/* 3902 */             catch (ArithmeticException arEx) {
/* 3903 */               throw SQLError.createSQLException("Can't set scale of '" + scale + "' for DECIMAL argument '" + parameterAsNum + "'", "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3912 */           setBigDecimal(parameterIndex, scaledBigDecimal); break;
/* 3913 */         }  if (parameterAsNum instanceof BigInteger) {
/* 3914 */           setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum, scale));
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 3920 */         setBigDecimal(parameterIndex, new BigDecimal(parameterAsNum.doubleValue()));
/*      */         break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(int parameterIndex, Object parameterObj) throws SQLException {
/* 3942 */     if (parameterObj == null) {
/* 3943 */       setNull(parameterIndex, 1111);
/*      */     }
/* 3945 */     else if (parameterObj instanceof Byte) {
/* 3946 */       setInt(parameterIndex, ((Byte)parameterObj).intValue());
/* 3947 */     } else if (parameterObj instanceof String) {
/* 3948 */       setString(parameterIndex, (String)parameterObj);
/* 3949 */     } else if (parameterObj instanceof BigDecimal) {
/* 3950 */       setBigDecimal(parameterIndex, (BigDecimal)parameterObj);
/* 3951 */     } else if (parameterObj instanceof Short) {
/* 3952 */       setShort(parameterIndex, ((Short)parameterObj).shortValue());
/* 3953 */     } else if (parameterObj instanceof Integer) {
/* 3954 */       setInt(parameterIndex, ((Integer)parameterObj).intValue());
/* 3955 */     } else if (parameterObj instanceof Long) {
/* 3956 */       setLong(parameterIndex, ((Long)parameterObj).longValue());
/* 3957 */     } else if (parameterObj instanceof Float) {
/* 3958 */       setFloat(parameterIndex, ((Float)parameterObj).floatValue());
/* 3959 */     } else if (parameterObj instanceof Double) {
/* 3960 */       setDouble(parameterIndex, ((Double)parameterObj).doubleValue());
/* 3961 */     } else if (parameterObj instanceof byte[]) {
/* 3962 */       setBytes(parameterIndex, (byte[])parameterObj);
/* 3963 */     } else if (parameterObj instanceof Date) {
/* 3964 */       setDate(parameterIndex, (Date)parameterObj);
/* 3965 */     } else if (parameterObj instanceof Time) {
/* 3966 */       setTime(parameterIndex, (Time)parameterObj);
/* 3967 */     } else if (parameterObj instanceof Timestamp) {
/* 3968 */       setTimestamp(parameterIndex, (Timestamp)parameterObj);
/* 3969 */     } else if (parameterObj instanceof Boolean) {
/* 3970 */       setBoolean(parameterIndex, ((Boolean)parameterObj).booleanValue());
/*      */     }
/* 3972 */     else if (parameterObj instanceof InputStream) {
/* 3973 */       setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
/* 3974 */     } else if (parameterObj instanceof Blob) {
/* 3975 */       setBlob(parameterIndex, (Blob)parameterObj);
/* 3976 */     } else if (parameterObj instanceof Clob) {
/* 3977 */       setClob(parameterIndex, (Clob)parameterObj);
/* 3978 */     } else if (this.connection.getTreatUtilDateAsTimestamp() && parameterObj instanceof Date) {
/*      */       
/* 3980 */       setTimestamp(parameterIndex, new Timestamp(((Date)parameterObj).getTime()));
/*      */     }
/* 3982 */     else if (parameterObj instanceof BigInteger) {
/* 3983 */       setString(parameterIndex, parameterObj.toString());
/*      */     } else {
/* 3985 */       setSerializableObject(parameterIndex, parameterObj);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType) throws SQLException {
/* 4006 */     if (!(parameterObj instanceof BigDecimal)) {
/* 4007 */       setObject(parameterIndex, parameterObj, targetSqlType, 0);
/*      */     } else {
/* 4009 */       setObject(parameterIndex, parameterObj, targetSqlType, ((BigDecimal)parameterObj).scale());
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
/*      */   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
/* 4045 */     if (parameterObj == null) {
/* 4046 */       setNull(parameterIndex, 1111);
/*      */     } else {
/*      */       try {
/* 4049 */         Date parameterAsDate; switch (targetSqlType) {
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
/*      */           case 16:
/* 4069 */             if (parameterObj instanceof Boolean) {
/* 4070 */               setBoolean(parameterIndex, ((Boolean)parameterObj).booleanValue());
/*      */             
/*      */             }
/* 4073 */             else if (parameterObj instanceof String) {
/* 4074 */               setBoolean(parameterIndex, ("true".equalsIgnoreCase((String)parameterObj) || !"0".equalsIgnoreCase((String)parameterObj)));
/*      */ 
/*      */             
/*      */             }
/* 4078 */             else if (parameterObj instanceof Number) {
/* 4079 */               int intValue = ((Number)parameterObj).intValue();
/*      */               
/* 4081 */               setBoolean(parameterIndex, (intValue != 0));
/*      */             }
/*      */             else {
/*      */               
/* 4085 */               throw SQLError.createSQLException("No conversion from " + parameterObj.getClass().getName() + " to Types.BOOLEAN possible.", "S1009", getExceptionInterceptor());
/*      */             } 
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case -7:
/*      */           case -6:
/*      */           case -5:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/* 4101 */             setNumericObject(parameterIndex, parameterObj, targetSqlType, scale);
/*      */             return;
/*      */ 
/*      */           
/*      */           case -1:
/*      */           case 1:
/*      */           case 12:
/* 4108 */             if (parameterObj instanceof BigDecimal) {
/* 4109 */               setString(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString((BigDecimal)parameterObj)));
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 4115 */               setString(parameterIndex, parameterObj.toString());
/*      */             } 
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case 2005:
/* 4122 */             if (parameterObj instanceof Clob) {
/* 4123 */               setClob(parameterIndex, (Clob)parameterObj);
/*      */             } else {
/* 4125 */               setString(parameterIndex, parameterObj.toString());
/*      */             } 
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case -4:
/*      */           case -3:
/*      */           case -2:
/*      */           case 2004:
/* 4135 */             if (parameterObj instanceof byte[]) {
/* 4136 */               setBytes(parameterIndex, (byte[])parameterObj);
/* 4137 */             } else if (parameterObj instanceof Blob) {
/* 4138 */               setBlob(parameterIndex, (Blob)parameterObj);
/*      */             } else {
/* 4140 */               setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*      */             } 
/*      */             return;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 91:
/*      */           case 93:
/* 4154 */             if (parameterObj instanceof String) {
/* 4155 */               ParsePosition pp = new ParsePosition(false);
/* 4156 */               DateFormat sdf = new SimpleDateFormat(getDateTimePattern((String)parameterObj, false), Locale.US);
/*      */               
/* 4158 */               parameterAsDate = sdf.parse((String)parameterObj, pp);
/*      */             } else {
/* 4160 */               parameterAsDate = (Date)parameterObj;
/*      */             } 
/*      */             
/* 4163 */             switch (targetSqlType) {
/*      */               
/*      */               case 91:
/* 4166 */                 if (parameterAsDate instanceof Date) {
/* 4167 */                   setDate(parameterIndex, (Date)parameterAsDate);
/*      */                   break;
/*      */                 } 
/* 4170 */                 setDate(parameterIndex, new Date(parameterAsDate.getTime()));
/*      */                 break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case 93:
/* 4178 */                 if (parameterAsDate instanceof Timestamp) {
/* 4179 */                   setTimestamp(parameterIndex, (Timestamp)parameterAsDate);
/*      */                   break;
/*      */                 } 
/* 4182 */                 setTimestamp(parameterIndex, new Timestamp(parameterAsDate.getTime()));
/*      */                 break;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */ 
/*      */ 
/*      */           
/*      */           case 92:
/* 4194 */             if (parameterObj instanceof String) {
/* 4195 */               DateFormat sdf = new SimpleDateFormat(getDateTimePattern((String)parameterObj, true), Locale.US);
/*      */               
/* 4197 */               setTime(parameterIndex, new Time(sdf.parse((String)parameterObj).getTime()));
/*      */             }
/* 4199 */             else if (parameterObj instanceof Timestamp) {
/* 4200 */               Timestamp xT = (Timestamp)parameterObj;
/* 4201 */               setTime(parameterIndex, new Time(xT.getTime()));
/*      */             } else {
/* 4203 */               setTime(parameterIndex, (Time)parameterObj);
/*      */             } 
/*      */             return;
/*      */ 
/*      */           
/*      */           case 1111:
/* 4209 */             setSerializableObject(parameterIndex, parameterObj);
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/* 4214 */         throw SQLError.createSQLException(Messages.getString("PreparedStatement.16"), "S1000", getExceptionInterceptor());
/*      */ 
/*      */       
/*      */       }
/* 4218 */       catch (Exception ex) {
/* 4219 */         if (ex instanceof SQLException) {
/* 4220 */           throw (SQLException)ex;
/*      */         }
/*      */         
/* 4223 */         SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + ex.getClass().getName() + Messages.getString("PreparedStatement.19") + ex.getMessage(), "S1000", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4231 */         sqlEx.initCause(ex);
/*      */         
/* 4233 */         throw sqlEx;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int setOneBatchedParameterSet(PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet) throws SQLException {
/* 4241 */     BatchParams paramArg = (BatchParams)paramSet;
/*      */     
/* 4243 */     boolean[] isNullBatch = paramArg.isNull;
/* 4244 */     boolean[] isStreamBatch = paramArg.isStream;
/*      */     
/* 4246 */     for (int j = 0; j < isNullBatch.length; j++) {
/* 4247 */       if (isNullBatch[j]) {
/* 4248 */         batchedStatement.setNull(batchedParamIndex++, 0);
/*      */       }
/* 4250 */       else if (isStreamBatch[j]) {
/* 4251 */         batchedStatement.setBinaryStream(batchedParamIndex++, paramArg.parameterStreams[j], paramArg.streamLengths[j]);
/*      */       }
/*      */       else {
/*      */         
/* 4255 */         ((PreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, paramArg.parameterStrings[j]);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4262 */     return batchedParamIndex;
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
/*      */ 
/*      */   
/* 4279 */   public void setRef(int i, Ref x) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4289 */   void setResultSetConcurrency(int concurrencyFlag) { this.resultSetConcurrency = concurrencyFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4299 */   void setResultSetType(int typeFlag) { this.resultSetType = typeFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4308 */   protected void setRetrieveGeneratedKeys(boolean retrieveGeneratedKeys) { this.retrieveGeneratedKeys = retrieveGeneratedKeys; }
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
/*      */   private final void setSerializableObject(int parameterIndex, Object parameterObj) throws SQLException {
/*      */     try {
/* 4328 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/* 4329 */       ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
/* 4330 */       objectOut.writeObject(parameterObj);
/* 4331 */       objectOut.flush();
/* 4332 */       objectOut.close();
/* 4333 */       bytesOut.flush();
/* 4334 */       bytesOut.close();
/*      */       
/* 4336 */       byte[] buf = bytesOut.toByteArray();
/* 4337 */       ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
/* 4338 */       setBinaryStream(parameterIndex, bytesIn, buf.length);
/* 4339 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = -2;
/* 4340 */     } catch (Exception ex) {
/* 4341 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.54") + ex.getClass().getName(), "S1009", getExceptionInterceptor());
/*      */ 
/*      */       
/* 4344 */       sqlEx.initCause(ex);
/*      */       
/* 4346 */       throw sqlEx;
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
/*      */   public void setShort(int parameterIndex, short x) throws SQLException {
/* 4363 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 4365 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 5;
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
/*      */ 
/*      */   
/*      */   public void setString(int parameterIndex, String x) throws SQLException {
/* 4383 */     if (x == null) {
/* 4384 */       setNull(parameterIndex, 1);
/*      */     } else {
/* 4386 */       checkClosed();
/*      */       
/* 4388 */       int stringLength = x.length();
/*      */       
/* 4390 */       if (this.connection.isNoBackslashEscapesSet()) {
/*      */ 
/*      */         
/* 4393 */         boolean needsHexEscape = isEscapeNeededForString(x, stringLength);
/*      */ 
/*      */         
/* 4396 */         if (!needsHexEscape) {
/* 4397 */           byte[] parameterAsBytes = null;
/*      */           
/* 4399 */           StringBuffer quotedString = new StringBuffer(x.length() + 2);
/* 4400 */           quotedString.append('\'');
/* 4401 */           quotedString.append(x);
/* 4402 */           quotedString.append('\'');
/*      */           
/* 4404 */           if (!this.isLoadDataQuery) {
/* 4405 */             parameterAsBytes = StringUtils.getBytes(quotedString.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 4411 */             parameterAsBytes = quotedString.toString().getBytes();
/*      */           } 
/*      */           
/* 4414 */           setInternal(parameterIndex, parameterAsBytes);
/*      */         } else {
/* 4416 */           byte[] parameterAsBytes = null;
/*      */           
/* 4418 */           if (!this.isLoadDataQuery) {
/* 4419 */             parameterAsBytes = StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 4425 */             parameterAsBytes = x.getBytes();
/*      */           } 
/*      */           
/* 4428 */           setBytes(parameterIndex, parameterAsBytes);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 4434 */       String parameterAsString = x;
/* 4435 */       boolean needsQuoted = true;
/*      */       
/* 4437 */       if (this.isLoadDataQuery || isEscapeNeededForString(x, stringLength)) {
/* 4438 */         needsQuoted = false;
/*      */         
/* 4440 */         StringBuffer buf = new StringBuffer((int)(x.length() * 1.1D));
/*      */         
/* 4442 */         buf.append('\'');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4451 */         for (int i = 0; i < stringLength; i++) {
/* 4452 */           char c = x.charAt(i);
/*      */           
/* 4454 */           switch (c) {
/*      */             case '\000':
/* 4456 */               buf.append('\\');
/* 4457 */               buf.append('0');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '\n':
/* 4462 */               buf.append('\\');
/* 4463 */               buf.append('n');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '\r':
/* 4468 */               buf.append('\\');
/* 4469 */               buf.append('r');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '\\':
/* 4474 */               buf.append('\\');
/* 4475 */               buf.append('\\');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '\'':
/* 4480 */               buf.append('\\');
/* 4481 */               buf.append('\'');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '"':
/* 4486 */               if (this.usingAnsiMode) {
/* 4487 */                 buf.append('\\');
/*      */               }
/*      */               
/* 4490 */               buf.append('"');
/*      */               break;
/*      */ 
/*      */             
/*      */             case '\032':
/* 4495 */               buf.append('\\');
/* 4496 */               buf.append('Z');
/*      */               break;
/*      */ 
/*      */ 
/*      */             
/*      */             case '¥':
/*      */             case '₩':
/* 4503 */               if (this.charsetEncoder != null) {
/* 4504 */                 CharBuffer cbuf = CharBuffer.allocate(1);
/* 4505 */                 ByteBuffer bbuf = ByteBuffer.allocate(1);
/* 4506 */                 cbuf.put(c);
/* 4507 */                 cbuf.position(0);
/* 4508 */                 this.charsetEncoder.encode(cbuf, bbuf, true);
/* 4509 */                 if (bbuf.get(0) == 92) {
/* 4510 */                   buf.append('\\');
/*      */                 }
/*      */               } 
/*      */ 
/*      */             
/*      */             default:
/* 4516 */               buf.append(c);
/*      */               break;
/*      */           } 
/*      */         } 
/* 4520 */         buf.append('\'');
/*      */         
/* 4522 */         parameterAsString = buf.toString();
/*      */       } 
/*      */       
/* 4525 */       byte[] parameterAsBytes = null;
/*      */       
/* 4527 */       if (!this.isLoadDataQuery) {
/* 4528 */         if (needsQuoted) {
/* 4529 */           parameterAsBytes = StringUtils.getBytesWrapped(parameterAsString, '\'', '\'', this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4534 */           parameterAsBytes = StringUtils.getBytes(parameterAsString, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 4541 */         parameterAsBytes = parameterAsString.getBytes();
/*      */       } 
/*      */       
/* 4544 */       setInternal(parameterIndex, parameterAsBytes);
/*      */       
/* 4546 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 12;
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isEscapeNeededForString(String x, int stringLength) {
/* 4551 */     boolean needsHexEscape = false;
/*      */     
/* 4553 */     for (int i = 0; i < stringLength; i++) {
/* 4554 */       char c = x.charAt(i);
/*      */       
/* 4556 */       switch (c) {
/*      */         
/*      */         case '\000':
/* 4559 */           needsHexEscape = true;
/*      */           break;
/*      */         
/*      */         case '\n':
/* 4563 */           needsHexEscape = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case '\r':
/* 4568 */           needsHexEscape = true;
/*      */           break;
/*      */         
/*      */         case '\\':
/* 4572 */           needsHexEscape = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case '\'':
/* 4577 */           needsHexEscape = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case '"':
/* 4582 */           needsHexEscape = true;
/*      */           break;
/*      */ 
/*      */         
/*      */         case '\032':
/* 4587 */           needsHexEscape = true;
/*      */           break;
/*      */       } 
/*      */       
/* 4591 */       if (needsHexEscape) {
/*      */         break;
/*      */       }
/*      */     } 
/* 4595 */     return needsHexEscape;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4614 */   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException { setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true); }
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
/* 4631 */   public void setTime(int parameterIndex, Time x) throws SQLException { setTimeInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false); }
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
/*      */   private void setTimeInternal(int parameterIndex, Time x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4652 */     if (x == null) {
/* 4653 */       setNull(parameterIndex, 92);
/*      */     } else {
/* 4655 */       checkClosed();
/*      */       
/* 4657 */       if (!this.useLegacyDatetimeCode) {
/* 4658 */         newSetTimeInternal(parameterIndex, x, targetCalendar);
/*      */       } else {
/* 4660 */         Calendar sessionCalendar = getCalendarInstanceForSessionOrNew();
/*      */         
/* 4662 */         synchronized (sessionCalendar) {
/* 4663 */           x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4670 */         setInternal(parameterIndex, "'" + x.toString() + "'");
/*      */       } 
/*      */       
/* 4673 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 92;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4693 */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException { setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true); }
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
/* 4710 */   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException { setTimestampInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false); }
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
/*      */   private void setTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4730 */     if (x == null) {
/* 4731 */       setNull(parameterIndex, 93);
/*      */     } else {
/* 4733 */       checkClosed();
/*      */       
/* 4735 */       if (!this.useLegacyDatetimeCode) {
/* 4736 */         newSetTimestampInternal(parameterIndex, x, targetCalendar);
/*      */       } else {
/* 4738 */         String timestampString = null;
/*      */         
/* 4740 */         Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */         
/* 4744 */         synchronized (sessionCalendar) {
/* 4745 */           x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4752 */         if (this.connection.getUseSSPSCompatibleTimezoneShift()) {
/* 4753 */           doSSPSCompatibleTimezoneShift(parameterIndex, x, sessionCalendar);
/*      */         } else {
/* 4755 */           synchronized (this) {
/* 4756 */             if (this.tsdf == null) {
/* 4757 */               this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''", Locale.US);
/*      */             }
/*      */             
/* 4760 */             timestampString = this.tsdf.format(x);
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
/* 4775 */             setInternal(parameterIndex, timestampString);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 4781 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 93;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void newSetTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar) throws SQLException {
/* 4787 */     if (this.tsdf == null) {
/* 4788 */       this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss", Locale.US);
/*      */     }
/*      */     
/* 4791 */     String timestampString = null;
/*      */     
/* 4793 */     if (targetCalendar != null) {
/* 4794 */       targetCalendar.setTime(x);
/* 4795 */       this.tsdf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4797 */       timestampString = this.tsdf.format(x);
/*      */     } else {
/* 4799 */       this.tsdf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4800 */       timestampString = this.tsdf.format(x);
/*      */     } 
/*      */     
/* 4803 */     StringBuffer buf = new StringBuffer();
/* 4804 */     buf.append(timestampString);
/* 4805 */     buf.append('.');
/* 4806 */     buf.append(formatNanos(x.getNanos()));
/* 4807 */     buf.append('\'');
/*      */     
/* 4809 */     setInternal(parameterIndex, buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 4814 */   private String formatNanos(int nanos) { return "0"; }
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
/*      */   private void newSetTimeInternal(int parameterIndex, Time x, Calendar targetCalendar) throws SQLException {
/* 4844 */     if (this.tdf == null) {
/* 4845 */       this.tdf = new SimpleDateFormat("''HH:mm:ss''", Locale.US);
/*      */     }
/*      */ 
/*      */     
/* 4849 */     String timeString = null;
/*      */     
/* 4851 */     if (targetCalendar != null) {
/* 4852 */       targetCalendar.setTime(x);
/* 4853 */       this.tdf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4855 */       timeString = this.tdf.format(x);
/*      */     } else {
/* 4857 */       this.tdf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4858 */       timeString = this.tdf.format(x);
/*      */     } 
/*      */     
/* 4861 */     setInternal(parameterIndex, timeString);
/*      */   }
/*      */ 
/*      */   
/*      */   private void newSetDateInternal(int parameterIndex, Date x, Calendar targetCalendar) throws SQLException {
/* 4866 */     if (this.ddf == null) {
/* 4867 */       this.ddf = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
/*      */     }
/*      */ 
/*      */     
/* 4871 */     String timeString = null;
/*      */     
/* 4873 */     if (targetCalendar != null) {
/* 4874 */       targetCalendar.setTime(x);
/* 4875 */       this.ddf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4877 */       timeString = this.ddf.format(x);
/*      */     } else {
/* 4879 */       this.ddf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4880 */       timeString = this.ddf.format(x);
/*      */     } 
/*      */     
/* 4883 */     setInternal(parameterIndex, timeString);
/*      */   }
/*      */   
/*      */   private void doSSPSCompatibleTimezoneShift(int parameterIndex, Timestamp x, Calendar sessionCalendar) throws SQLException {
/* 4887 */     Calendar sessionCalendar2 = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4892 */     synchronized (sessionCalendar2) {
/* 4893 */       Date oldTime = sessionCalendar2.getTime();
/*      */       
/*      */       try {
/* 4896 */         sessionCalendar2.setTime(x);
/*      */         
/* 4898 */         int year = sessionCalendar2.get(1);
/* 4899 */         int month = sessionCalendar2.get(2) + 1;
/* 4900 */         int date = sessionCalendar2.get(5);
/*      */         
/* 4902 */         int hour = sessionCalendar2.get(11);
/* 4903 */         int minute = sessionCalendar2.get(12);
/* 4904 */         int seconds = sessionCalendar2.get(13);
/*      */         
/* 4906 */         StringBuffer tsBuf = new StringBuffer();
/*      */         
/* 4908 */         tsBuf.append('\'');
/* 4909 */         tsBuf.append(year);
/*      */         
/* 4911 */         tsBuf.append("-");
/*      */         
/* 4913 */         if (month < 10) {
/* 4914 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4917 */         tsBuf.append(month);
/*      */         
/* 4919 */         tsBuf.append('-');
/*      */         
/* 4921 */         if (date < 10) {
/* 4922 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4925 */         tsBuf.append(date);
/*      */         
/* 4927 */         tsBuf.append(' ');
/*      */         
/* 4929 */         if (hour < 10) {
/* 4930 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4933 */         tsBuf.append(hour);
/*      */         
/* 4935 */         tsBuf.append(':');
/*      */         
/* 4937 */         if (minute < 10) {
/* 4938 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4941 */         tsBuf.append(minute);
/*      */         
/* 4943 */         tsBuf.append(':');
/*      */         
/* 4945 */         if (seconds < 10) {
/* 4946 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4949 */         tsBuf.append(seconds);
/*      */         
/* 4951 */         tsBuf.append('.');
/* 4952 */         tsBuf.append(formatNanos(x.getNanos()));
/* 4953 */         tsBuf.append('\'');
/*      */         
/* 4955 */         setInternal(parameterIndex, tsBuf.toString());
/*      */       } finally {
/*      */         
/* 4958 */         sessionCalendar.setTime(oldTime);
/*      */       } 
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
/*      */   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
/* 4989 */     if (x == null) {
/* 4990 */       setNull(parameterIndex, 12);
/*      */     } else {
/* 4992 */       setBinaryStream(parameterIndex, x, length);
/*      */       
/* 4994 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 2005;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURL(int parameterIndex, URL arg) throws SQLException {
/* 5002 */     if (arg != null) {
/* 5003 */       setString(parameterIndex, arg.toString());
/*      */       
/* 5005 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 70;
/*      */     } else {
/* 5007 */       setNull(parameterIndex, 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void streamToBytes(Buffer packet, InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
/*      */     try {
/* 5015 */       String connectionEncoding = this.connection.getEncoding();
/*      */       
/* 5017 */       boolean hexEscape = false;
/*      */       
/* 5019 */       if (this.connection.isNoBackslashEscapesSet() || (this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding) && !this.connection.parserKnowsUnicode()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 5024 */         hexEscape = true;
/*      */       }
/*      */       
/* 5027 */       if (streamLength == -1) {
/* 5028 */         useLength = false;
/*      */       }
/*      */       
/* 5031 */       int bc = -1;
/*      */       
/* 5033 */       if (useLength) {
/* 5034 */         bc = readblock(in, this.streamConvertBuf, streamLength);
/*      */       } else {
/* 5036 */         bc = readblock(in, this.streamConvertBuf);
/*      */       } 
/*      */       
/* 5039 */       int lengthLeftToRead = streamLength - bc;
/*      */       
/* 5041 */       if (hexEscape) {
/* 5042 */         packet.writeStringNoNull("x");
/* 5043 */       } else if (this.connection.getIO().versionMeetsMinimum(4, 1, 0)) {
/* 5044 */         packet.writeStringNoNull("_binary");
/*      */       } 
/*      */       
/* 5047 */       if (escape) {
/* 5048 */         packet.writeByte((byte)39);
/*      */       }
/*      */       
/* 5051 */       while (bc > 0) {
/* 5052 */         if (hexEscape) {
/* 5053 */           hexEscapeBlock(this.streamConvertBuf, packet, bc);
/* 5054 */         } else if (escape) {
/* 5055 */           escapeblockFast(this.streamConvertBuf, packet, bc);
/*      */         } else {
/* 5057 */           packet.writeBytesNoNull(this.streamConvertBuf, 0, bc);
/*      */         } 
/*      */         
/* 5060 */         if (useLength) {
/* 5061 */           bc = readblock(in, this.streamConvertBuf, lengthLeftToRead);
/*      */           
/* 5063 */           if (bc > 0)
/* 5064 */             lengthLeftToRead -= bc; 
/*      */           continue;
/*      */         } 
/* 5067 */         bc = readblock(in, this.streamConvertBuf);
/*      */       } 
/*      */ 
/*      */       
/* 5071 */       if (escape) {
/* 5072 */         packet.writeByte((byte)39);
/*      */       }
/*      */     } finally {
/* 5075 */       if (this.connection.getAutoClosePStmtStreams()) {
/*      */         try {
/* 5077 */           in.close();
/* 5078 */         } catch (IOException ioEx) {}
/*      */ 
/*      */ 
/*      */         
/* 5082 */         in = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final byte[] streamToBytes(InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
/*      */     try {
/* 5090 */       if (streamLength == -1) {
/* 5091 */         useLength = false;
/*      */       }
/*      */       
/* 5094 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/*      */       
/* 5096 */       int bc = -1;
/*      */       
/* 5098 */       if (useLength) {
/* 5099 */         bc = readblock(in, this.streamConvertBuf, streamLength);
/*      */       } else {
/* 5101 */         bc = readblock(in, this.streamConvertBuf);
/*      */       } 
/*      */       
/* 5104 */       int lengthLeftToRead = streamLength - bc;
/*      */       
/* 5106 */       if (escape) {
/* 5107 */         if (this.connection.versionMeetsMinimum(4, 1, 0)) {
/* 5108 */           bytesOut.write(95);
/* 5109 */           bytesOut.write(98);
/* 5110 */           bytesOut.write(105);
/* 5111 */           bytesOut.write(110);
/* 5112 */           bytesOut.write(97);
/* 5113 */           bytesOut.write(114);
/* 5114 */           bytesOut.write(121);
/*      */         } 
/*      */         
/* 5117 */         bytesOut.write(39);
/*      */       } 
/*      */       
/* 5120 */       while (bc > 0) {
/* 5121 */         if (escape) {
/* 5122 */           escapeblockFast(this.streamConvertBuf, bytesOut, bc);
/*      */         } else {
/* 5124 */           bytesOut.write(this.streamConvertBuf, 0, bc);
/*      */         } 
/*      */         
/* 5127 */         if (useLength) {
/* 5128 */           bc = readblock(in, this.streamConvertBuf, lengthLeftToRead);
/*      */           
/* 5130 */           if (bc > 0)
/* 5131 */             lengthLeftToRead -= bc; 
/*      */           continue;
/*      */         } 
/* 5134 */         bc = readblock(in, this.streamConvertBuf);
/*      */       } 
/*      */ 
/*      */       
/* 5138 */       if (escape) {
/* 5139 */         bytesOut.write(39);
/*      */       }
/*      */       
/* 5142 */       return bytesOut.toByteArray();
/*      */     } finally {
/* 5144 */       if (this.connection.getAutoClosePStmtStreams()) {
/*      */         try {
/* 5146 */           in.close();
/* 5147 */         } catch (IOException ioEx) {}
/*      */ 
/*      */ 
/*      */         
/* 5151 */         in = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() throws SQLException {
/* 5162 */     StringBuffer buf = new StringBuffer();
/* 5163 */     buf.append(super.toString());
/* 5164 */     buf.append(": ");
/*      */     
/*      */     try {
/* 5167 */       buf.append(asSql());
/* 5168 */     } catch (SQLException sqlEx) {
/* 5169 */       buf.append("EXCEPTION: " + sqlEx.toString());
/*      */     } 
/*      */     
/* 5172 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5178 */   public boolean isClosed() throws SQLException { return this.isClosed; }
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
/* 5189 */   protected int getParameterIndexOffset() { return 0; }
/*      */ 
/*      */ 
/*      */   
/* 5193 */   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException { setAsciiStream(parameterIndex, x, -1); }
/*      */ 
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
/* 5197 */     setAsciiStream(parameterIndex, x, (int)length);
/* 5198 */     this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 2005;
/*      */   }
/*      */ 
/*      */   
/* 5202 */   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException { setBinaryStream(parameterIndex, x, -1); }
/*      */ 
/*      */ 
/*      */   
/* 5206 */   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException { setBinaryStream(parameterIndex, x, (int)length); }
/*      */ 
/*      */ 
/*      */   
/* 5210 */   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException { setBinaryStream(parameterIndex, inputStream); }
/*      */ 
/*      */ 
/*      */   
/* 5214 */   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException { setCharacterStream(parameterIndex, reader, -1); }
/*      */ 
/*      */ 
/*      */   
/* 5218 */   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException { setCharacterStream(parameterIndex, reader, (int)length); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5223 */   public void setClob(int parameterIndex, Reader reader) throws SQLException { setCharacterStream(parameterIndex, reader); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5229 */   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException { setCharacterStream(parameterIndex, reader, length); }
/*      */ 
/*      */ 
/*      */   
/* 5233 */   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException { setNCharacterStream(parameterIndex, value, -1L); }
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
/*      */   public void setNString(int parameterIndex, String x) throws SQLException {
/* 5251 */     if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
/*      */       
/* 5253 */       setString(parameterIndex, x);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 5258 */     if (x == null) {
/* 5259 */       setNull(parameterIndex, 1);
/*      */     } else {
/* 5261 */       int stringLength = x.length();
/*      */ 
/*      */ 
/*      */       
/* 5265 */       StringBuffer buf = new StringBuffer((int)(x.length() * 1.1D + 4.0D));
/* 5266 */       buf.append("_utf8");
/* 5267 */       buf.append('\'');
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5276 */       for (i = 0; i < stringLength; i++) {
/* 5277 */         char c = x.charAt(i);
/*      */         
/* 5279 */         switch (c) {
/*      */           case '\000':
/* 5281 */             buf.append('\\');
/* 5282 */             buf.append('0');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\n':
/* 5287 */             buf.append('\\');
/* 5288 */             buf.append('n');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\r':
/* 5293 */             buf.append('\\');
/* 5294 */             buf.append('r');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\\':
/* 5299 */             buf.append('\\');
/* 5300 */             buf.append('\\');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\'':
/* 5305 */             buf.append('\\');
/* 5306 */             buf.append('\'');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/* 5311 */             if (this.usingAnsiMode) {
/* 5312 */               buf.append('\\');
/*      */             }
/*      */             
/* 5315 */             buf.append('"');
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\032':
/* 5320 */             buf.append('\\');
/* 5321 */             buf.append('Z');
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 5326 */             buf.append(c);
/*      */             break;
/*      */         } 
/*      */       } 
/* 5330 */       buf.append('\'');
/*      */       
/* 5332 */       String parameterAsString = buf.toString();
/*      */       
/* 5334 */       byte[] parameterAsBytes = null;
/*      */       
/* 5336 */       if (!this.isLoadDataQuery) {
/* 5337 */         parameterAsBytes = StringUtils.getBytes(parameterAsString, this.connection.getCharsetConverter("UTF-8"), "UTF-8", this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 5343 */         parameterAsBytes = parameterAsString.getBytes();
/*      */       } 
/*      */       
/* 5346 */       setInternal(parameterIndex, parameterAsBytes);
/*      */       
/* 5348 */       this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = -9;
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
/*      */   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/*      */     try {
/* 5377 */       if (reader == null) {
/* 5378 */         setNull(parameterIndex, -1);
/*      */       } else {
/*      */         
/* 5381 */         char[] c = null;
/* 5382 */         int len = 0;
/*      */         
/* 5384 */         boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5389 */         if (useLength && length != -1L) {
/* 5390 */           c = new char[(int)length];
/*      */           
/* 5392 */           int numCharsRead = readFully(reader, c, (int)length);
/*      */ 
/*      */ 
/*      */           
/* 5396 */           setNString(parameterIndex, new String(c, false, numCharsRead));
/*      */         } else {
/*      */           
/* 5399 */           c = new char[4096];
/*      */           
/* 5401 */           StringBuffer buf = new StringBuffer();
/*      */           
/* 5403 */           while ((len = reader.read(c)) != -1) {
/* 5404 */             buf.append(c, 0, len);
/*      */           }
/*      */           
/* 5407 */           setNString(parameterIndex, buf.toString());
/*      */         } 
/*      */         
/* 5410 */         this.parameterTypes[parameterIndex - 1 + getParameterIndexOffset()] = 2011;
/*      */       } 
/* 5412 */     } catch (IOException ioEx) {
/* 5413 */       throw SQLError.createSQLException(ioEx.toString(), "S1000", getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 5419 */   public void setNClob(int parameterIndex, Reader reader) throws SQLException { setNCharacterStream(parameterIndex, reader); }
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
/*      */   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
/* 5437 */     if (reader == null) {
/* 5438 */       setNull(parameterIndex, -1);
/*      */     } else {
/* 5440 */       setNCharacterStream(parameterIndex, reader, length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 5445 */   public ParameterBindings getParameterBindings() throws SQLException { return new EmulatedPreparedStatementBindings(); }
/*      */   
/*      */   class EmulatedPreparedStatementBindings
/*      */     implements ParameterBindings
/*      */   {
/*      */     private ResultSetImpl bindingsAsRs;
/*      */     private boolean[] parameterIsNull;
/*      */     
/*      */     public EmulatedPreparedStatementBindings() throws SQLException {
/* 5454 */       List rows = new ArrayList();
/* 5455 */       this.parameterIsNull = new boolean[PreparedStatement.this.parameterCount];
/* 5456 */       System.arraycopy(this$0.isNull, 0, this.parameterIsNull, 0, PreparedStatement.this.parameterCount);
/*      */ 
/*      */       
/* 5459 */       byte[][] rowData = new byte[PreparedStatement.this.parameterCount][];
/* 5460 */       Field[] typeMetadata = new Field[PreparedStatement.this.parameterCount];
/*      */       
/* 5462 */       for (int i = 0; i < PreparedStatement.this.parameterCount; i++) {
/* 5463 */         if (this$0.batchCommandIndex == -1) {
/* 5464 */           rowData[i] = this$0.getBytesRepresentation(i);
/*      */         } else {
/* 5466 */           rowData[i] = this$0.getBytesRepresentationForBatch(i, this$0.batchCommandIndex);
/*      */         } 
/* 5468 */         int charsetIndex = 0;
/*      */         
/* 5470 */         if (PreparedStatement.this.parameterTypes[i] == -2 || PreparedStatement.this.parameterTypes[i] == 2004) {
/*      */           
/* 5472 */           charsetIndex = 63;
/*      */         } else {
/* 5474 */           String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(PreparedStatement.this.connection.getEncoding(), PreparedStatement.this.connection);
/*      */ 
/*      */           
/* 5477 */           charsetIndex = CharsetMapping.getCharsetIndexForMysqlEncodingName(mysqlEncodingName);
/*      */         } 
/*      */ 
/*      */         
/* 5481 */         Field parameterMetadata = new Field(null, "parameter_" + (i + 1), charsetIndex, PreparedStatement.this.parameterTypes[i], rowData[i].length);
/*      */ 
/*      */         
/* 5484 */         parameterMetadata.setConnection(PreparedStatement.this.connection);
/* 5485 */         typeMetadata[i] = parameterMetadata;
/*      */       } 
/*      */       
/* 5488 */       rows.add(new ByteArrayRow(rowData, this$0.getExceptionInterceptor()));
/*      */       
/* 5490 */       this.bindingsAsRs = new ResultSetImpl(PreparedStatement.this.connection.getCatalog(), typeMetadata, new RowDataStatic(rows), PreparedStatement.this.connection, null);
/*      */       
/* 5492 */       this.bindingsAsRs.next();
/*      */     }
/*      */ 
/*      */     
/* 5496 */     public Array getArray(int parameterIndex) throws SQLException { return this.bindingsAsRs.getArray(parameterIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5501 */     public InputStream getAsciiStream(int parameterIndex) throws SQLException { return this.bindingsAsRs.getAsciiStream(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5505 */     public BigDecimal getBigDecimal(int parameterIndex) throws SQLException { return this.bindingsAsRs.getBigDecimal(parameterIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5510 */     public InputStream getBinaryStream(int parameterIndex) throws SQLException { return this.bindingsAsRs.getBinaryStream(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5514 */     public Blob getBlob(int parameterIndex) throws SQLException { return this.bindingsAsRs.getBlob(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5518 */     public boolean getBoolean(int parameterIndex) { return this.bindingsAsRs.getBoolean(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5522 */     public byte getByte(int parameterIndex) throws SQLException { return this.bindingsAsRs.getByte(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5526 */     public byte[] getBytes(int parameterIndex) throws SQLException { return this.bindingsAsRs.getBytes(parameterIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5531 */     public Reader getCharacterStream(int parameterIndex) throws SQLException { return this.bindingsAsRs.getCharacterStream(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5535 */     public Clob getClob(int parameterIndex) throws SQLException { return this.bindingsAsRs.getClob(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5539 */     public Date getDate(int parameterIndex) throws SQLException { return this.bindingsAsRs.getDate(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5543 */     public double getDouble(int parameterIndex) throws SQLException { return this.bindingsAsRs.getDouble(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5547 */     public float getFloat(int parameterIndex) throws SQLException { return this.bindingsAsRs.getFloat(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5551 */     public int getInt(int parameterIndex) throws SQLException { return this.bindingsAsRs.getInt(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5555 */     public long getLong(int parameterIndex) throws SQLException { return this.bindingsAsRs.getLong(parameterIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5560 */     public Reader getNCharacterStream(int parameterIndex) throws SQLException { return this.bindingsAsRs.getCharacterStream(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5564 */     public Reader getNClob(int parameterIndex) throws SQLException { return this.bindingsAsRs.getCharacterStream(parameterIndex); }
/*      */ 
/*      */     
/*      */     public Object getObject(int parameterIndex) throws SQLException {
/* 5568 */       PreparedStatement.this.checkBounds(parameterIndex, 0);
/*      */       
/* 5570 */       if (this.parameterIsNull[parameterIndex - 1]) {
/* 5571 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5578 */       switch (PreparedStatement.this.parameterTypes[parameterIndex - 1]) {
/*      */         case -6:
/* 5580 */           return new Byte(getByte(parameterIndex));
/*      */         case 5:
/* 5582 */           return new Short(getShort(parameterIndex));
/*      */         case 4:
/* 5584 */           return new Integer(getInt(parameterIndex));
/*      */         case -5:
/* 5586 */           return new Long(getLong(parameterIndex));
/*      */         case 6:
/* 5588 */           return new Float(getFloat(parameterIndex));
/*      */         case 8:
/* 5590 */           return new Double(getDouble(parameterIndex));
/*      */       } 
/* 5592 */       return this.bindingsAsRs.getObject(parameterIndex);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 5597 */     public Ref getRef(int parameterIndex) throws SQLException { return this.bindingsAsRs.getRef(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5601 */     public short getShort(int parameterIndex) throws SQLException { return this.bindingsAsRs.getShort(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5605 */     public String getString(int parameterIndex) { return this.bindingsAsRs.getString(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5609 */     public Time getTime(int parameterIndex) throws SQLException { return this.bindingsAsRs.getTime(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5613 */     public Timestamp getTimestamp(int parameterIndex) throws SQLException { return this.bindingsAsRs.getTimestamp(parameterIndex); }
/*      */ 
/*      */ 
/*      */     
/* 5617 */     public URL getURL(int parameterIndex) throws SQLException { return this.bindingsAsRs.getURL(parameterIndex); }
/*      */ 
/*      */     
/*      */     public boolean isNull(int parameterIndex) {
/* 5621 */       PreparedStatement.this.checkBounds(parameterIndex, 0);
/*      */       
/* 5623 */       return this.parameterIsNull[parameterIndex - 1];
/*      */     }
/*      */   }
/*      */   
/*      */   public String getPreparedSql() throws SQLException {
/* 5628 */     if (this.rewrittenBatchSize == 0) {
/* 5629 */       return this.originalSql;
/*      */     }
/*      */     
/*      */     try {
/* 5633 */       return this.parseInfo.getSqlForBatch(this.parseInfo);
/* 5634 */     } catch (UnsupportedEncodingException e) {
/* 5635 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getUpdateCount() {
/* 5640 */     int count = super.getUpdateCount();
/*      */     
/* 5642 */     if (containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate)
/*      */     {
/* 5644 */       if (count == 2 || count == 0) {
/* 5645 */         count = 1;
/*      */       }
/*      */     }
/*      */     
/* 5649 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean canRewrite(String sql, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementStartPos) {
/* 5656 */     boolean rewritableOdku = true;
/*      */     
/* 5658 */     if (isOnDuplicateKeyUpdate) {
/* 5659 */       int updateClausePos = StringUtils.indexOfIgnoreCase(locationOfOnDuplicateKeyUpdate, sql, " UPDATE ");
/*      */ 
/*      */       
/* 5662 */       if (updateClausePos != -1) {
/* 5663 */         rewritableOdku = (StringUtils.indexOfIgnoreCaseRespectMarker(updateClausePos, sql, "LAST_INSERT_ID", "\"'`", "\"'`", false) == -1);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5670 */     return (StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementStartPos) && StringUtils.indexOfIgnoreCaseRespectMarker(statementStartPos, sql, "SELECT", "\"'`", "\"'`", false) == -1 && rewritableOdku);
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\PreparedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */