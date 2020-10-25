/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ResultSetImpl
/*      */   implements ResultSetInternalMethods
/*      */ {
/*      */   private static final Constructor JDBC_4_RS_4_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_RS_6_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_UPD_RS_6_ARG_CTOR;
/*      */   protected static final double MIN_DIFF_PREC;
/*      */   protected static final double MAX_DIFF_PREC;
/*      */   protected static int resultCounter;
/*      */   protected String catalog;
/*      */   protected Map columnLabelToIndex;
/*      */   protected Map columnToIndexCache;
/*      */   protected boolean[] columnUsed;
/*      */   protected MySQLConnection connection;
/*      */   protected long connectionId;
/*      */   protected int currentRow;
/*      */   TimeZone defaultTimeZone;
/*      */   protected boolean doingUpdates;
/*      */   protected ProfilerEventHandler eventSink;
/*      */   Calendar fastDateCal;
/*      */   protected int fetchDirection;
/*      */   protected int fetchSize;
/*      */   protected Field[] fields;
/*      */   protected char firstCharOfQuery;
/*      */   protected Map fullColumnNameToIndex;
/*      */   protected Map columnNameToIndex;
/*      */   protected boolean hasBuiltIndexMapping;
/*      */   protected boolean isBinaryEncoded;
/*      */   protected boolean isClosed;
/*      */   protected ResultSetInternalMethods nextResultSet;
/*      */   protected boolean onInsertRow;
/*      */   protected StatementImpl owningStatement;
/*      */   protected Throwable pointOfOrigin;
/*      */   protected boolean profileSql;
/*      */   protected boolean reallyResult;
/*      */   protected int resultId;
/*      */   protected int resultSetConcurrency;
/*      */   protected int resultSetType;
/*      */   protected RowData rowData;
/*      */   protected String serverInfo;
/*      */   PreparedStatement statementUsedForFetchingRows;
/*      */   protected ResultSetRow thisRow;
/*      */   protected long updateCount;
/*      */   protected long updateId;
/*      */   private boolean useStrictFloatingPoint;
/*      */   protected boolean useUsageAdvisor;
/*      */   protected SQLWarning warningChain;
/*      */   protected boolean wasNullFlag;
/*      */   protected Statement wrapperStatement;
/*      */   protected boolean retainOwningStatement;
/*      */   protected Calendar gmtCalendar;
/*      */   protected boolean useFastDateParsing;
/*      */   private boolean padCharsWithSpace;
/*      */   private boolean jdbcCompliantTruncationForReads;
/*      */   private boolean useFastIntParsing;
/*      */   private boolean useColumnNamesInFindColumn;
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */   protected static final char[] EMPTY_SPACE;
/*      */   private boolean onValidRow;
/*      */   private String invalidRowReason;
/*      */   protected boolean useLegacyDatetimeCode;
/*      */   private TimeZone serverTimeZoneTz;
/*      */   
/*      */   static  {
/*  123 */     if (Util.isJdbc4()) {
/*      */       try {
/*  125 */         JDBC_4_RS_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(new Class[] { long.class, long.class, MySQLConnection.class, StatementImpl.class });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  130 */         JDBC_4_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(new Class[] { String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  136 */         JDBC_4_UPD_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4UpdatableResultSet").getConstructor(new Class[] { String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  143 */       catch (SecurityException e) {
/*  144 */         throw new RuntimeException(e);
/*  145 */       } catch (NoSuchMethodException e) {
/*  146 */         NoSuchMethodException noSuchMethodException; throw new RuntimeException(noSuchMethodException);
/*  147 */       } catch (ClassNotFoundException e) {
/*  148 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*  151 */       JDBC_4_RS_4_ARG_CTOR = null;
/*  152 */       JDBC_4_RS_6_ARG_CTOR = null;
/*  153 */       JDBC_4_UPD_RS_6_ARG_CTOR = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     MIN_DIFF_PREC = Float.parseFloat(Float.toString(Float.MIN_VALUE)) - Double.parseDouble(Float.toString(Float.MIN_VALUE));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     MAX_DIFF_PREC = Float.parseFloat(Float.toString(Float.MAX_VALUE)) - Double.parseDouble(Float.toString(Float.MAX_VALUE));
/*      */ 
/*      */ 
/*      */     
/*  170 */     resultCounter = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  341 */     EMPTY_SPACE = new char[255];
/*      */ 
/*      */     
/*  344 */     for (byte b = 0; b < EMPTY_SPACE.length; b++)
/*  345 */       EMPTY_SPACE[b] = ' '; 
/*      */   } protected static BigInteger convertLongToUlong(long longVal) { byte[] asBytes = new byte[8]; asBytes[7] = (byte)(int)(longVal & 0xFFL); asBytes[6] = (byte)(int)(longVal >>> 8); asBytes[5] = (byte)(int)(longVal >>> 16); asBytes[4] = (byte)(int)(longVal >>> 24);
/*      */     asBytes[3] = (byte)(int)(longVal >>> 32);
/*      */     asBytes[2] = (byte)(int)(longVal >>> 40);
/*      */     asBytes[1] = (byte)(int)(longVal >>> 48);
/*      */     asBytes[0] = (byte)(int)(longVal >>> 56);
/*  351 */     return new BigInteger(true, asBytes); } protected static ResultSetImpl getInstance(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException { if (!Util.isJdbc4()) {
/*  352 */       return new ResultSetImpl(updateCount, updateID, conn, creatorStmt);
/*      */     }
/*      */     
/*  355 */     return (ResultSetImpl)Util.handleNewInstance(JDBC_4_RS_4_ARG_CTOR, new Object[] { Constants.longValueOf(updateCount), Constants.longValueOf(updateID), conn, creatorStmt }, conn.getExceptionInterceptor()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ResultSetImpl getInstance(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt, boolean isUpdatable) throws SQLException {
/*  371 */     if (!Util.isJdbc4()) {
/*  372 */       if (!isUpdatable) {
/*  373 */         return new ResultSetImpl(catalog, fields, tuples, conn, creatorStmt);
/*      */       }
/*      */       
/*  376 */       return new UpdatableResultSet(catalog, fields, tuples, conn, creatorStmt);
/*      */     } 
/*      */ 
/*      */     
/*  380 */     if (!isUpdatable) {
/*  381 */       return (ResultSetImpl)Util.handleNewInstance(JDBC_4_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  386 */     return (ResultSetImpl)Util.handleNewInstance(JDBC_4_UPD_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetImpl(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt)
/*      */   {
/*      */     this.catalog = null;
/*      */     this.columnLabelToIndex = null;
/*      */     this.columnToIndexCache = null;
/*      */     this.columnUsed = null;
/*      */     this.connectionId = 0L;
/*      */     this.currentRow = -1;
/*      */     this.doingUpdates = false;
/*      */     this.eventSink = null;
/*      */     this.fastDateCal = null;
/*      */     this.fetchDirection = 1000;
/*      */     this.fetchSize = 0;
/*      */     this.fullColumnNameToIndex = null;
/*      */     this.columnNameToIndex = null;
/*      */     this.hasBuiltIndexMapping = false;
/*      */     this.isBinaryEncoded = false;
/*      */     this.isClosed = false;
/*      */     this.nextResultSet = null;
/*      */     this.onInsertRow = false;
/*      */     this.profileSql = false;
/*      */     this.reallyResult = false;
/*      */     this.resultSetConcurrency = 0;
/*      */     this.resultSetType = 0;
/*      */     this.serverInfo = null;
/*      */     this.thisRow = null;
/*      */     this.updateId = -1L;
/*      */     this.useStrictFloatingPoint = false;
/*      */     this.useUsageAdvisor = false;
/*      */     this.warningChain = null;
/*      */     this.wasNullFlag = false;
/*      */     this.gmtCalendar = null;
/*      */     this.useFastDateParsing = false;
/*      */     this.padCharsWithSpace = false;
/*      */     this.useFastIntParsing = true;
/*  846 */     this.onValidRow = false;
/*  847 */     this.invalidRowReason = null; this.updateCount = updateCount; this.updateId = updateID; this.reallyResult = false; this.fields = new Field[0]; this.connection = conn; this.owningStatement = creatorStmt; this.exceptionInterceptor = this.connection.getExceptionInterceptor(); this.retainOwningStatement = false; if (this.connection != null) { this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose(); this.connectionId = this.connection.getId(); this.serverTimeZoneTz = this.connection.getServerTimezoneTZ(); this.padCharsWithSpace = this.connection.getPadCharsWithSpace(); }  this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode(); } public ResultSetImpl(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException { this.catalog = null; this.columnLabelToIndex = null; this.columnToIndexCache = null; this.columnUsed = null; this.connectionId = 0L; this.currentRow = -1; this.doingUpdates = false; this.eventSink = null; this.fastDateCal = null; this.fetchDirection = 1000; this.fetchSize = 0; this.fullColumnNameToIndex = null; this.columnNameToIndex = null; this.hasBuiltIndexMapping = false; this.isBinaryEncoded = false; this.isClosed = false; this.nextResultSet = null; this.onInsertRow = false; this.profileSql = false; this.reallyResult = false; this.resultSetConcurrency = 0; this.resultSetType = 0; this.serverInfo = null; this.thisRow = null; this.updateId = -1L; this.useStrictFloatingPoint = false; this.useUsageAdvisor = false; this.warningChain = null; this.wasNullFlag = false; this.gmtCalendar = null; this.useFastDateParsing = false; this.padCharsWithSpace = false; this.useFastIntParsing = true; this.onValidRow = false; this.invalidRowReason = null; this.connection = conn; this.retainOwningStatement = false; if (this.connection != null) { this.useStrictFloatingPoint = this.connection.getStrictFloatingPoint(); setDefaultTimeZone(this.connection.getDefaultTimeZone()); this.connectionId = this.connection.getId(); this.useFastDateParsing = this.connection.getUseFastDateParsing(); this.profileSql = this.connection.getProfileSql(); this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose(); this.jdbcCompliantTruncationForReads = this.connection.getJdbcCompliantTruncationForReads(); this.useFastIntParsing = this.connection.getUseFastIntParsing(); this.serverTimeZoneTz = this.connection.getServerTimezoneTZ(); this.padCharsWithSpace = this.connection.getPadCharsWithSpace(); }  this.owningStatement = creatorStmt; this.catalog = catalog; this.fields = fields; this.rowData = tuples; this.updateCount = this.rowData.size(); this.reallyResult = true; if (this.rowData.size() > 0) { if (this.updateCount == 1L && this.thisRow == null) { this.rowData.close(); this.updateCount = -1L; }  } else { this.thisRow = null; }  this.rowData.setOwner(this); if (this.fields != null)
/*      */       initializeWithMetadata();  this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode(); this.useColumnNamesInFindColumn = this.connection.getUseColumnNamesInFindColumn(); setRowPositionValidity(); }
/*      */   public void initializeWithMetadata() throws SQLException { this.rowData.setMetadata(this.fields); this.columnToIndexCache = new HashMap(); if (this.profileSql || this.connection.getUseUsageAdvisor()) { this.columnUsed = new boolean[this.fields.length]; this.pointOfOrigin = new Throwable(); this.resultId = resultCounter++; this.useUsageAdvisor = this.connection.getUseUsageAdvisor(); this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection); }  if (this.connection.getGatherPerformanceMetrics()) { this.connection.incrementNumberOfResultSetsCreated(); Map tableNamesMap = new HashMap(); for (int i = 0; i < this.fields.length; i++) { Field f = this.fields[i]; String tableName = f.getOriginalTableName(); if (tableName == null)
/*      */           tableName = f.getTableName();  if (tableName != null) { if (this.connection.lowerCaseTableNames())
/*      */             tableName = tableName.toLowerCase();  tableNamesMap.put(tableName, null); }  }  this.connection.reportNumberOfTablesAccessed(tableNamesMap.size()); }  }
/*  852 */   private void createCalendarIfNeeded() throws SQLException { if (this.fastDateCal == null) { this.fastDateCal = new GregorianCalendar(Locale.US); this.fastDateCal.setTimeZone(getDefaultTimeZone()); }  } private void setRowPositionValidity() throws SQLException { if (!this.rowData.isDynamic() && this.rowData.size() == 0)
/*  853 */     { this.invalidRowReason = Messages.getString("ResultSet.Illegal_operation_on_empty_result_set");
/*      */       
/*  855 */       this.onValidRow = false; }
/*  856 */     else if (this.rowData.isBeforeFirst())
/*  857 */     { this.invalidRowReason = Messages.getString("ResultSet.Before_start_of_result_set_146");
/*      */       
/*  859 */       this.onValidRow = false; }
/*  860 */     else if (this.rowData.isAfterLast())
/*  861 */     { this.invalidRowReason = Messages.getString("ResultSet.After_end_of_result_set_148");
/*      */       
/*  863 */       this.onValidRow = false; }
/*      */     else
/*  865 */     { this.onValidRow = true;
/*  866 */       this.invalidRowReason = null; }  }
/*      */   public boolean absolute(int row) throws SQLException { boolean b; checkClosed(); if (this.rowData.size() == 0) { b = false; } else { if (row == 0) throw SQLError.createSQLException(Messages.getString("ResultSet.Cannot_absolute_position_to_row_0_110"), "S1009", getExceptionInterceptor());  if (this.onInsertRow) this.onInsertRow = false;  if (this.doingUpdates) this.doingUpdates = false;  if (this.thisRow != null) this.thisRow.closeOpenStreams();  if (row == 1) { b = first(); } else if (row == -1) { b = last(); } else if (row > this.rowData.size()) { afterLast(); b = false; } else if (row < 0) { int newRowPosition = this.rowData.size() + row + 1; if (newRowPosition <= 0) { beforeFirst(); b = false; } else { b = absolute(newRowPosition); }  } else { row--; this.rowData.setCurrentRow(row); this.thisRow = this.rowData.getAt(row); b = true; }  }  setRowPositionValidity(); return b; }
/*      */   public void afterLast() throws SQLException { checkClosed(); if (this.onInsertRow) this.onInsertRow = false;  if (this.doingUpdates) this.doingUpdates = false;  if (this.thisRow != null) this.thisRow.closeOpenStreams();  if (this.rowData.size() != 0) { this.rowData.afterLast(); this.thisRow = null; }  setRowPositionValidity(); }
/*      */   public void beforeFirst() throws SQLException { checkClosed(); if (this.onInsertRow) this.onInsertRow = false;  if (this.doingUpdates) this.doingUpdates = false;  if (this.rowData.size() == 0) return;  if (this.thisRow != null) this.thisRow.closeOpenStreams();  this.rowData.beforeFirst(); this.thisRow = null; setRowPositionValidity(); }
/*      */   public void buildIndexMapping() throws SQLException { int numFields = this.fields.length; this.columnLabelToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER); this.fullColumnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER); this.columnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER); for (int i = numFields - 1; i >= 0; i--) { Integer index = Constants.integerValueOf(i); String columnName = this.fields[i].getOriginalName(); String columnLabel = this.fields[i].getName(); String fullColumnName = this.fields[i].getFullName(); if (columnLabel != null) this.columnLabelToIndex.put(columnLabel, index);  if (fullColumnName != null) this.fullColumnNameToIndex.put(fullColumnName, index);  if (columnName != null) this.columnNameToIndex.put(columnName, index);  }  this.hasBuiltIndexMapping = true; }
/*      */   public void cancelRowUpdates() throws SQLException { throw new NotUpdatable(); }
/*      */   protected final void checkClosed() throws SQLException { if (this.isClosed) throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000", getExceptionInterceptor());  }
/*      */   protected final void checkColumnBounds(int columnIndex) throws SQLException { if (columnIndex < 1) throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_low", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", getExceptionInterceptor());  if (columnIndex > this.fields.length) throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_high", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", getExceptionInterceptor());  if (this.profileSql || this.useUsageAdvisor)
/*      */       this.columnUsed[columnIndex - 1] = true;  } protected void checkRowPos() throws SQLException { checkClosed(); if (!this.onValidRow)
/*  875 */       throw SQLError.createSQLException(this.invalidRowReason, "S1000", getExceptionInterceptor());  } public void clearNextResult() throws SQLException { this.nextResultSet = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  886 */   public void clearWarnings() throws SQLException { this.warningChain = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  907 */   public void close() throws SQLException { realClose(true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertToZeroWithEmptyCheck() throws SQLException {
/*  914 */     if (this.connection.getEmptyStringsConvertToZero()) {
/*  915 */       return 0;
/*      */     }
/*      */     
/*  918 */     throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String convertToZeroLiteralStringWithEmptyCheck() throws SQLException {
/*  925 */     if (this.connection.getEmptyStringsConvertToZero()) {
/*  926 */       return "0";
/*      */     }
/*      */     
/*  929 */     throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  937 */   public ResultSetInternalMethods copy() throws SQLException { return getInstance(this.catalog, this.fields, this.rowData, this.connection, this.owningStatement, false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void redefineFieldsForDBMD(Field[] f) {
/*  944 */     this.fields = f;
/*      */     
/*  946 */     for (int i = 0; i < this.fields.length; i++) {
/*  947 */       this.fields[i].setUseOldNameMetadata(true);
/*  948 */       this.fields[i].setConnection(this.connection);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void populateCachedMetaData(CachedResultSetMetaData cachedMetaData) throws SQLException {
/*  954 */     cachedMetaData.fields = this.fields;
/*  955 */     cachedMetaData.columnNameToIndex = this.columnLabelToIndex;
/*  956 */     cachedMetaData.fullColumnNameToIndex = this.fullColumnNameToIndex;
/*  957 */     cachedMetaData.metadata = getMetaData();
/*      */   }
/*      */   
/*      */   public void initializeFromCachedMetaData(CachedResultSetMetaData cachedMetaData) throws SQLException {
/*  961 */     this.fields = cachedMetaData.fields;
/*  962 */     this.columnLabelToIndex = cachedMetaData.columnNameToIndex;
/*  963 */     this.fullColumnNameToIndex = cachedMetaData.fullColumnNameToIndex;
/*  964 */     this.hasBuiltIndexMapping = true;
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
/*  979 */   public void deleteRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String extractStringFromNativeColumn(int columnIndex, int mysqlType) throws SQLException {
/*  991 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/*  993 */     this.wasNullFlag = false;
/*      */     
/*  995 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/*  996 */       this.wasNullFlag = true;
/*      */       
/*  998 */       return null;
/*      */     } 
/*      */     
/* 1001 */     this.wasNullFlag = false;
/*      */     
/* 1003 */     String encoding = this.fields[columnIndexMinusOne].getCharacterSet();
/*      */ 
/*      */     
/* 1006 */     return this.thisRow.getString(columnIndex - 1, encoding, this.connection);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Date fastDateCreate(Calendar cal, int year, int month, int day) {
/* 1011 */     if (this.useLegacyDatetimeCode) {
/* 1012 */       return TimeUtil.fastDateCreate(year, month, day, cal);
/*      */     }
/*      */     
/* 1015 */     if (cal == null) {
/* 1016 */       createCalendarIfNeeded();
/* 1017 */       cal = this.fastDateCal;
/*      */     } 
/*      */     
/* 1020 */     boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
/*      */     
/* 1022 */     return TimeUtil.fastDateCreate(useGmtMillis, useGmtMillis ? getGmtCalendar() : cal, cal, year, month, day);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Time fastTimeCreate(Calendar cal, int hour, int minute, int second) throws SQLException {
/* 1029 */     if (!this.useLegacyDatetimeCode) {
/* 1030 */       return TimeUtil.fastTimeCreate(hour, minute, second, cal, getExceptionInterceptor());
/*      */     }
/*      */     
/* 1033 */     if (cal == null) {
/* 1034 */       createCalendarIfNeeded();
/* 1035 */       cal = this.fastDateCal;
/*      */     } 
/*      */     
/* 1038 */     return TimeUtil.fastTimeCreate(cal, hour, minute, second, getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Timestamp fastTimestampCreate(Calendar cal, int year, int month, int day, int hour, int minute, int seconds, int secondsPart) {
/* 1044 */     if (!this.useLegacyDatetimeCode) {
/* 1045 */       return TimeUtil.fastTimestampCreate(cal.getTimeZone(), year, month, day, hour, minute, seconds, secondsPart);
/*      */     }
/*      */ 
/*      */     
/* 1049 */     if (cal == null) {
/* 1050 */       createCalendarIfNeeded();
/* 1051 */       cal = this.fastDateCal;
/*      */     } 
/*      */     
/* 1054 */     boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
/*      */     
/* 1056 */     return TimeUtil.fastTimestampCreate(useGmtMillis, useGmtMillis ? getGmtCalendar() : null, cal, year, month, day, hour, minute, seconds, secondsPart);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int findColumn(String columnName) throws SQLException {
/* 1105 */     checkClosed();
/*      */     
/* 1107 */     if (!this.hasBuiltIndexMapping) {
/* 1108 */       buildIndexMapping();
/*      */     }
/*      */     
/* 1111 */     Integer index = (Integer)this.columnToIndexCache.get(columnName);
/*      */     
/* 1113 */     if (index != null) {
/* 1114 */       return index.intValue() + 1;
/*      */     }
/*      */     
/* 1117 */     index = (Integer)this.columnLabelToIndex.get(columnName);
/*      */     
/* 1119 */     if (index == null && this.useColumnNamesInFindColumn) {
/* 1120 */       index = (Integer)this.columnNameToIndex.get(columnName);
/*      */     }
/*      */     
/* 1123 */     if (index == null) {
/* 1124 */       index = (Integer)this.fullColumnNameToIndex.get(columnName);
/*      */     }
/*      */     
/* 1127 */     if (index != null) {
/* 1128 */       this.columnToIndexCache.put(columnName, index);
/*      */       
/* 1130 */       return index.intValue() + 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1135 */     for (int i = 0; i < this.fields.length; i++) {
/* 1136 */       if (this.fields[i].getName().equalsIgnoreCase(columnName))
/* 1137 */         return i + 1; 
/* 1138 */       if (this.fields[i].getFullName().equalsIgnoreCase(columnName))
/*      */       {
/* 1140 */         return i + 1;
/*      */       }
/*      */     } 
/*      */     
/* 1144 */     throw SQLError.createSQLException(Messages.getString("ResultSet.Column____112") + columnName + Messages.getString("ResultSet.___not_found._113"), "S0022", getExceptionInterceptor());
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
/*      */   public boolean first() throws SQLException {
/* 1164 */     checkClosed();
/*      */     
/* 1166 */     boolean b = true;
/*      */     
/* 1168 */     if (this.rowData.isEmpty()) {
/* 1169 */       b = false;
/*      */     } else {
/*      */       
/* 1172 */       if (this.onInsertRow) {
/* 1173 */         this.onInsertRow = false;
/*      */       }
/*      */       
/* 1176 */       if (this.doingUpdates) {
/* 1177 */         this.doingUpdates = false;
/*      */       }
/*      */       
/* 1180 */       this.rowData.beforeFirst();
/* 1181 */       this.thisRow = this.rowData.next();
/*      */     } 
/*      */     
/* 1184 */     setRowPositionValidity();
/*      */     
/* 1186 */     return b;
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
/*      */   public Array getArray(int i) throws SQLException {
/* 1203 */     checkColumnBounds(i);
/*      */     
/* 1205 */     throw SQLError.notImplemented();
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
/* 1222 */   public Array getArray(String colName) throws SQLException { return getArray(findColumn(colName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getAsciiStream(int columnIndex) throws SQLException {
/* 1251 */     checkRowPos();
/*      */     
/* 1253 */     if (!this.isBinaryEncoded) {
/* 1254 */       return getBinaryStream(columnIndex);
/*      */     }
/*      */     
/* 1257 */     return getNativeBinaryStream(columnIndex);
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
/* 1272 */   public InputStream getAsciiStream(String columnName) throws SQLException { return getAsciiStream(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
/* 1289 */     if (!this.isBinaryEncoded) {
/* 1290 */       String stringVal = getString(columnIndex);
/*      */ 
/*      */       
/* 1293 */       if (stringVal != null) {
/* 1294 */         if (stringVal.length() == 0)
/*      */         {
/* 1296 */           return new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1303 */           return new BigDecimal(stringVal);
/*      */         
/*      */         }
/* 1306 */         catch (NumberFormatException ex) {
/* 1307 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1315 */       return null;
/*      */     } 
/*      */     
/* 1318 */     return getNativeBigDecimal(columnIndex);
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
/*      */   public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
/* 1339 */     if (!this.isBinaryEncoded) {
/* 1340 */       String stringVal = getString(columnIndex);
/*      */ 
/*      */       
/* 1343 */       if (stringVal != null) {
/* 1344 */         BigDecimal bigDecimal; if (stringVal.length() == 0) {
/* 1345 */           bigDecimal = new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */ 
/*      */           
/*      */           try {
/* 1349 */             return bigDecimal.setScale(scale);
/* 1350 */           } catch (ArithmeticException ex) {
/*      */             try {
/* 1352 */               return bigDecimal.setScale(scale, 4);
/*      */             }
/* 1354 */             catch (ArithmeticException arEx) {
/* 1355 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1365 */           bigDecimal = new BigDecimal(stringVal);
/* 1366 */         } catch (NumberFormatException ex) {
/* 1367 */           if (this.fields[columnIndex - 1].getMysqlType() == 16) {
/* 1368 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 1370 */             bigDecimal = new BigDecimal(valueAsLong);
/*      */           } else {
/* 1372 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1381 */           return bigDecimal.setScale(scale);
/* 1382 */         } catch (ArithmeticException ex) {
/*      */           try {
/* 1384 */             return bigDecimal.setScale(scale, 4);
/* 1385 */           } catch (ArithmeticException arithEx) {
/* 1386 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1395 */       return null;
/*      */     } 
/*      */     
/* 1398 */     return getNativeBigDecimal(columnIndex, scale);
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
/* 1414 */   public BigDecimal getBigDecimal(String columnName) throws SQLException { return getBigDecimal(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1434 */   public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException { return getBigDecimal(findColumn(columnName), scale); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final BigDecimal getBigDecimalFromString(String stringVal, int columnIndex, int scale) throws SQLException {
/* 1441 */     if (stringVal != null) {
/* 1442 */       if (stringVal.length() == 0) {
/* 1443 */         BigDecimal bdVal = new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */         
/*      */         try {
/* 1446 */           return bdVal.setScale(scale);
/* 1447 */         } catch (ArithmeticException ex) {
/*      */           try {
/* 1449 */             return bdVal.setScale(scale, 4);
/* 1450 */           } catch (ArithmeticException arEx) {
/* 1451 */             throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1462 */         return (new BigDecimal(stringVal)).setScale(scale);
/* 1463 */       } catch (ArithmeticException ex) {
/*      */         try {
/* 1465 */           return (new BigDecimal(stringVal)).setScale(scale, 4);
/*      */         }
/* 1467 */         catch (ArithmeticException arEx) {
/* 1468 */           throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1475 */       catch (NumberFormatException ex) {
/* 1476 */         if (this.fields[columnIndex - 1].getMysqlType() == 16) {
/* 1477 */           long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */           
/*      */           try {
/* 1480 */             return (new BigDecimal(valueAsLong)).setScale(scale);
/* 1481 */           } catch (ArithmeticException arEx1) {
/*      */             try {
/* 1483 */               return (new BigDecimal(valueAsLong)).setScale(scale, 4);
/*      */             }
/* 1485 */             catch (ArithmeticException arEx2) {
/* 1486 */               throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1495 */         if (this.fields[columnIndex - 1].getMysqlType() == 1 && this.connection.getTinyInt1isBit() && this.fields[columnIndex - 1].getLength() == 1L)
/*      */         {
/* 1497 */           return (new BigDecimal(stringVal.equalsIgnoreCase("true") ? 1 : 0)).setScale(scale);
/*      */         }
/*      */         
/* 1500 */         throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1508 */     return null;
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
/*      */   public InputStream getBinaryStream(int columnIndex) throws SQLException {
/* 1529 */     checkRowPos();
/*      */     
/* 1531 */     if (!this.isBinaryEncoded) {
/* 1532 */       checkColumnBounds(columnIndex);
/*      */       
/* 1534 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1536 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1537 */         this.wasNullFlag = true;
/*      */         
/* 1539 */         return null;
/*      */       } 
/*      */       
/* 1542 */       this.wasNullFlag = false;
/*      */       
/* 1544 */       return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
/*      */     } 
/*      */     
/* 1547 */     return getNativeBinaryStream(columnIndex);
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
/* 1562 */   public InputStream getBinaryStream(String columnName) throws SQLException { return getBinaryStream(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Blob getBlob(int columnIndex) throws SQLException {
/* 1577 */     if (!this.isBinaryEncoded) {
/* 1578 */       checkRowPos();
/*      */       
/* 1580 */       checkColumnBounds(columnIndex);
/*      */       
/* 1582 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1584 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1585 */         this.wasNullFlag = true;
/*      */       } else {
/* 1587 */         this.wasNullFlag = false;
/*      */       } 
/*      */       
/* 1590 */       if (this.wasNullFlag) {
/* 1591 */         return null;
/*      */       }
/*      */       
/* 1594 */       if (!this.connection.getEmulateLocators()) {
/* 1595 */         return new Blob(this.thisRow.getColumnValue(columnIndexMinusOne), getExceptionInterceptor());
/*      */       }
/*      */       
/* 1598 */       return new BlobFromLocator(this, columnIndex, getExceptionInterceptor());
/*      */     } 
/*      */     
/* 1601 */     return getNativeBlob(columnIndex);
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
/* 1616 */   public Blob getBlob(String colName) throws SQLException { return getBlob(findColumn(colName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int columnIndex) throws SQLException {
/*      */     long boolVal;
/* 1632 */     checkColumnBounds(columnIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1639 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 1641 */     Field field = this.fields[columnIndexMinusOne];
/*      */     
/* 1643 */     if (field.getMysqlType() == 16) {
/* 1644 */       return byteArrayToBoolean(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1647 */     this.wasNullFlag = false;
/*      */     
/* 1649 */     int sqlType = field.getSQLType();
/*      */     
/* 1651 */     switch (sqlType) {
/*      */       case 16:
/* 1653 */         if (field.getMysqlType() == -1) {
/* 1654 */           String stringVal = getString(columnIndex);
/*      */           
/* 1656 */           return getBooleanFromString(stringVal, columnIndex);
/*      */         } 
/*      */         
/* 1659 */         boolVal = getLong(columnIndex, false);
/*      */         
/* 1661 */         return (boolVal == -1L || boolVal > 0L);
/*      */       case -7:
/*      */       case -6:
/*      */       case -5:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/* 1672 */         boolVal = getLong(columnIndex, false);
/*      */         
/* 1674 */         return (boolVal == -1L || boolVal > 0L);
/*      */     } 
/* 1676 */     if (this.connection.getPedantic())
/*      */     {
/* 1678 */       switch (sqlType) {
/*      */         case -4:
/*      */         case -3:
/*      */         case -2:
/*      */         case 70:
/*      */         case 91:
/*      */         case 92:
/*      */         case 93:
/*      */         case 2000:
/*      */         case 2002:
/*      */         case 2003:
/*      */         case 2004:
/*      */         case 2005:
/*      */         case 2006:
/* 1692 */           throw SQLError.createSQLException("Required type conversion not allowed", "22018", getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */     
/*      */     }
/* 1697 */     if (sqlType == -2 || sqlType == -3 || sqlType == -4 || sqlType == 2004)
/*      */     {
/*      */ 
/*      */       
/* 1701 */       return byteArrayToBoolean(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1704 */     if (this.useUsageAdvisor) {
/* 1705 */       issueConversionViaParsingWarning("getBoolean()", columnIndex, this.thisRow.getColumnValue(columnIndexMinusOne), this.fields[columnIndex], new int[] { 16, 5, 1, 2, 3, 8, 4 });
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
/* 1717 */     String stringVal = getString(columnIndex);
/*      */     
/* 1719 */     return getBooleanFromString(stringVal, columnIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean byteArrayToBoolean(int columnIndexMinusOne) throws SQLException {
/* 1724 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndexMinusOne);
/*      */     
/* 1726 */     if (arrayOfByte == null) {
/* 1727 */       this.wasNullFlag = true;
/*      */       
/* 1729 */       return false;
/*      */     } 
/*      */     
/* 1732 */     this.wasNullFlag = false;
/*      */     
/* 1734 */     if ((byte[])arrayOfByte.length == 0) {
/* 1735 */       return false;
/*      */     }
/*      */     
/* 1738 */     byte boolVal = (byte[])arrayOfByte[0];
/*      */     
/* 1740 */     if (boolVal == 49)
/* 1741 */       return true; 
/* 1742 */     if (boolVal == 48) {
/* 1743 */       return false;
/*      */     }
/*      */     
/* 1746 */     return (boolVal == -1 || boolVal > 0);
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
/* 1761 */   public boolean getBoolean(String columnName) throws SQLException { return getBoolean(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean getBooleanFromString(String stringVal, int columnIndex) throws SQLException {
/* 1766 */     if (stringVal != null && stringVal.length() > 0) {
/* 1767 */       int c = Character.toLowerCase(stringVal.charAt(0));
/*      */       
/* 1769 */       return (c == 116 || c == 121 || c == 49 || stringVal.equals("-1"));
/*      */     } 
/*      */ 
/*      */     
/* 1773 */     return false;
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
/*      */   public byte getByte(int columnIndex) throws SQLException {
/* 1788 */     if (!this.isBinaryEncoded) {
/* 1789 */       String stringVal = getString(columnIndex);
/*      */       
/* 1791 */       if (this.wasNullFlag || stringVal == null) {
/* 1792 */         return 0;
/*      */       }
/*      */       
/* 1795 */       return getByteFromString(stringVal, columnIndex);
/*      */     } 
/*      */     
/* 1798 */     return getNativeByte(columnIndex);
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
/* 1813 */   public byte getByte(String columnName) throws SQLException { return getByte(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte getByteFromString(String stringVal, int columnIndex) throws SQLException {
/* 1819 */     if (stringVal != null && stringVal.length() == 0) {
/* 1820 */       return (byte)convertToZeroWithEmptyCheck();
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
/* 1831 */     if (stringVal == null) {
/* 1832 */       return 0;
/*      */     }
/*      */     
/* 1835 */     stringVal = stringVal.trim();
/*      */     
/*      */     try {
/* 1838 */       int decimalIndex = stringVal.indexOf(".");
/*      */ 
/*      */       
/* 1841 */       if (decimalIndex != -1) {
/* 1842 */         double valueAsDouble = Double.parseDouble(stringVal);
/*      */         
/* 1844 */         if (this.jdbcCompliantTruncationForReads && (
/* 1845 */           valueAsDouble < -128.0D || valueAsDouble > 127.0D))
/*      */         {
/* 1847 */           throwRangeException(stringVal, columnIndex, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1852 */         return (byte)(int)valueAsDouble;
/*      */       } 
/*      */       
/* 1855 */       long valueAsLong = Long.parseLong(stringVal);
/*      */       
/* 1857 */       if (this.jdbcCompliantTruncationForReads && (
/* 1858 */         valueAsLong < -128L || valueAsLong > 127L))
/*      */       {
/* 1860 */         throwRangeException(String.valueOf(valueAsLong), columnIndex, -6);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1865 */       return (byte)(int)valueAsLong;
/* 1866 */     } catch (NumberFormatException NFE) {
/* 1867 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Value____173") + stringVal + Messages.getString("ResultSet.___is_out_of_range_[-127,127]_174"), "S1009", getExceptionInterceptor());
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
/* 1892 */   public byte[] getBytes(int columnIndex) throws SQLException { return getBytes(columnIndex, false); }
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] getBytes(int columnIndex, boolean noConversion) throws SQLException {
/* 1897 */     if (!this.isBinaryEncoded) {
/* 1898 */       checkRowPos();
/*      */       
/* 1900 */       checkColumnBounds(columnIndex);
/*      */       
/* 1902 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1904 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1905 */         this.wasNullFlag = true;
/*      */       } else {
/* 1907 */         this.wasNullFlag = false;
/*      */       } 
/*      */       
/* 1910 */       if (this.wasNullFlag) {
/* 1911 */         return null;
/*      */       }
/*      */       
/* 1914 */       return this.thisRow.getColumnValue(columnIndexMinusOne);
/*      */     } 
/*      */     
/* 1917 */     return getNativeBytes(columnIndex, noConversion);
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
/* 1932 */   public byte[] getBytes(String columnName) throws SQLException { return getBytes(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte[] getBytesFromString(String stringVal, int columnIndex) throws SQLException {
/* 1937 */     if (stringVal != null) {
/* 1938 */       return StringUtils.getBytes(stringVal, this.connection.getEncoding(), this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection, getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1945 */     return null;
/*      */   }
/*      */   
/*      */   public int getBytesSize() throws SQLException {
/* 1949 */     RowData localRowData = this.rowData;
/*      */     
/* 1951 */     checkClosed();
/*      */     
/* 1953 */     if (localRowData instanceof RowDataStatic) {
/* 1954 */       int bytesSize = 0;
/*      */       
/* 1956 */       int numRows = localRowData.size();
/*      */       
/* 1958 */       for (int i = 0; i < numRows; i++) {
/* 1959 */         bytesSize += localRowData.getAt(i).getBytesSize();
/*      */       }
/*      */       
/* 1962 */       return bytesSize;
/*      */     } 
/*      */     
/* 1965 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Calendar getCalendarInstanceForSessionOrNew() {
/* 1973 */     if (this.connection != null) {
/* 1974 */       return this.connection.getCalendarInstanceForSessionOrNew();
/*      */     }
/*      */     
/* 1977 */     return new GregorianCalendar();
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
/*      */   public Reader getCharacterStream(int columnIndex) throws SQLException {
/* 1998 */     if (!this.isBinaryEncoded) {
/* 1999 */       checkColumnBounds(columnIndex);
/*      */       
/* 2001 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 2003 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2004 */         this.wasNullFlag = true;
/*      */         
/* 2006 */         return null;
/*      */       } 
/*      */       
/* 2009 */       this.wasNullFlag = false;
/*      */       
/* 2011 */       return this.thisRow.getReader(columnIndexMinusOne);
/*      */     } 
/*      */     
/* 2014 */     return getNativeCharacterStream(columnIndex);
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
/* 2034 */   public Reader getCharacterStream(String columnName) throws SQLException { return getCharacterStream(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final Reader getCharacterStreamFromString(String stringVal, int columnIndex) throws SQLException {
/* 2039 */     if (stringVal != null) {
/* 2040 */       return new StringReader(stringVal);
/*      */     }
/*      */     
/* 2043 */     return null;
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
/*      */   public Clob getClob(int i) throws SQLException {
/* 2058 */     if (!this.isBinaryEncoded) {
/* 2059 */       String asString = getStringForClob(i);
/*      */       
/* 2061 */       if (asString == null) {
/* 2062 */         return null;
/*      */       }
/*      */       
/* 2065 */       return new Clob(asString, getExceptionInterceptor());
/*      */     } 
/*      */     
/* 2068 */     return getNativeClob(i);
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
/* 2083 */   public Clob getClob(String colName) throws SQLException { return getClob(findColumn(colName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2088 */   private final Clob getClobFromString(String stringVal, int columnIndex) throws SQLException { return new Clob(stringVal, getExceptionInterceptor()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2101 */   public int getConcurrency() throws SQLException { return 1007; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2130 */   public String getCursorName() throws SQLException { throw SQLError.createSQLException(Messages.getString("ResultSet.Positioned_Update_not_supported"), "S1C00", getExceptionInterceptor()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2147 */   public Date getDate(int columnIndex) throws SQLException { return getDate(columnIndex, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int columnIndex, Calendar cal) throws SQLException {
/* 2168 */     if (this.isBinaryEncoded) {
/* 2169 */       return getNativeDate(columnIndex, cal);
/*      */     }
/*      */     
/* 2172 */     if (!this.useFastDateParsing) {
/* 2173 */       String stringVal = getStringInternal(columnIndex, false);
/*      */       
/* 2175 */       if (stringVal == null) {
/* 2176 */         return null;
/*      */       }
/*      */       
/* 2179 */       return getDateFromString(stringVal, columnIndex, cal);
/*      */     } 
/*      */     
/* 2182 */     checkColumnBounds(columnIndex);
/*      */     
/* 2184 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 2186 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2187 */       this.wasNullFlag = true;
/*      */       
/* 2189 */       return null;
/*      */     } 
/*      */     
/* 2192 */     this.wasNullFlag = false;
/*      */     
/* 2194 */     return this.thisRow.getDateFast(columnIndexMinusOne, this.connection, this, cal);
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
/* 2210 */   public Date getDate(String columnName) throws SQLException { return getDate(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2230 */   public Date getDate(String columnName, Calendar cal) throws SQLException { return getDate(findColumn(columnName), cal); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final Date getDateFromString(String stringVal, int columnIndex, Calendar targetCalendar) throws SQLException {
/* 2235 */     int year = 0;
/* 2236 */     int month = 0;
/* 2237 */     int day = 0;
/*      */     
/*      */     try {
/* 2240 */       this.wasNullFlag = false;
/*      */       
/* 2242 */       if (stringVal == null) {
/* 2243 */         this.wasNullFlag = true;
/*      */         
/* 2245 */         return null;
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
/* 2256 */       stringVal = stringVal.trim();
/*      */       
/* 2258 */       if (stringVal.equals("0") || stringVal.equals("0000-00-00") || stringVal.equals("0000-00-00 00:00:00") || stringVal.equals("00000000000000") || stringVal.equals("0")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2263 */         if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */           
/* 2265 */           this.wasNullFlag = true;
/*      */           
/* 2267 */           return null;
/* 2268 */         }  if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */         {
/* 2270 */           throw SQLError.createSQLException("Value '" + stringVal + "' can not be represented as java.sql.Date", "S1009", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2277 */         return fastDateCreate(targetCalendar, 1, 1, 1);
/*      */       } 
/* 2279 */       if (this.fields[columnIndex - 1].getMysqlType() == 7) {
/*      */         
/* 2281 */         switch (stringVal.length()) {
/*      */           case 19:
/*      */           case 21:
/* 2284 */             year = Integer.parseInt(stringVal.substring(0, 4));
/* 2285 */             month = Integer.parseInt(stringVal.substring(5, 7));
/* 2286 */             day = Integer.parseInt(stringVal.substring(8, 10));
/*      */             
/* 2288 */             return fastDateCreate(targetCalendar, year, month, day);
/*      */ 
/*      */           
/*      */           case 8:
/*      */           case 14:
/* 2293 */             year = Integer.parseInt(stringVal.substring(0, 4));
/* 2294 */             month = Integer.parseInt(stringVal.substring(4, 6));
/* 2295 */             day = Integer.parseInt(stringVal.substring(6, 8));
/*      */             
/* 2297 */             return fastDateCreate(targetCalendar, year, month, day);
/*      */ 
/*      */           
/*      */           case 6:
/*      */           case 10:
/*      */           case 12:
/* 2303 */             year = Integer.parseInt(stringVal.substring(0, 2));
/*      */             
/* 2305 */             if (year <= 69) {
/* 2306 */               year += 100;
/*      */             }
/*      */             
/* 2309 */             month = Integer.parseInt(stringVal.substring(2, 4));
/* 2310 */             day = Integer.parseInt(stringVal.substring(4, 6));
/*      */             
/* 2312 */             return fastDateCreate(targetCalendar, year + 1900, month, day);
/*      */ 
/*      */           
/*      */           case 4:
/* 2316 */             year = Integer.parseInt(stringVal.substring(0, 4));
/*      */             
/* 2318 */             if (year <= 69) {
/* 2319 */               year += 100;
/*      */             }
/*      */             
/* 2322 */             month = Integer.parseInt(stringVal.substring(2, 4));
/*      */             
/* 2324 */             return fastDateCreate(targetCalendar, year + 1900, month, 1);
/*      */ 
/*      */           
/*      */           case 2:
/* 2328 */             year = Integer.parseInt(stringVal.substring(0, 2));
/*      */             
/* 2330 */             if (year <= 69) {
/* 2331 */               year += 100;
/*      */             }
/*      */             
/* 2334 */             return fastDateCreate(targetCalendar, year + 1900, 1, 1);
/*      */         } 
/*      */ 
/*      */         
/* 2338 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2343 */       if (this.fields[columnIndex - 1].getMysqlType() == 13) {
/*      */         
/* 2345 */         if (stringVal.length() == 2 || stringVal.length() == 1) {
/* 2346 */           year = Integer.parseInt(stringVal);
/*      */           
/* 2348 */           if (year <= 69) {
/* 2349 */             year += 100;
/*      */           }
/*      */           
/* 2352 */           year += 1900;
/*      */         } else {
/* 2354 */           year = Integer.parseInt(stringVal.substring(0, 4));
/*      */         } 
/*      */         
/* 2357 */         return fastDateCreate(targetCalendar, year, 1, 1);
/* 2358 */       }  if (this.fields[columnIndex - 1].getMysqlType() == 11) {
/* 2359 */         return fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */       }
/* 2361 */       if (stringVal.length() < 10) {
/* 2362 */         if (stringVal.length() == 8) {
/* 2363 */           return fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */         }
/*      */         
/* 2366 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2372 */       if (stringVal.length() != 18) {
/* 2373 */         year = Integer.parseInt(stringVal.substring(0, 4));
/* 2374 */         month = Integer.parseInt(stringVal.substring(5, 7));
/* 2375 */         day = Integer.parseInt(stringVal.substring(8, 10));
/*      */       } else {
/*      */         
/* 2378 */         StringTokenizer st = new StringTokenizer(stringVal, "- ");
/*      */         
/* 2380 */         year = Integer.parseInt(st.nextToken());
/* 2381 */         month = Integer.parseInt(st.nextToken());
/* 2382 */         day = Integer.parseInt(st.nextToken());
/*      */       } 
/*      */ 
/*      */       
/* 2386 */       return fastDateCreate(targetCalendar, year, month, day);
/* 2387 */     } catch (SQLException sqlEx) {
/* 2388 */       throw sqlEx;
/* 2389 */     } catch (Exception e) {
/* 2390 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2395 */       sqlEx.initCause(e);
/*      */       
/* 2397 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */   
/*      */   private TimeZone getDefaultTimeZone() {
/* 2402 */     if (!this.useLegacyDatetimeCode && this.connection != null) {
/* 2403 */       return this.serverTimeZoneTz;
/*      */     }
/*      */     
/* 2406 */     return this.connection.getDefaultTimeZone();
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
/*      */   public double getDouble(int columnIndex) throws SQLException {
/* 2421 */     if (!this.isBinaryEncoded) {
/* 2422 */       return getDoubleInternal(columnIndex);
/*      */     }
/*      */     
/* 2425 */     return getNativeDouble(columnIndex);
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
/* 2440 */   public double getDouble(String columnName) throws SQLException { return getDouble(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2445 */   private final double getDoubleFromString(String stringVal, int columnIndex) throws SQLException { return getDoubleInternal(stringVal, columnIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2461 */   protected double getDoubleInternal(int colIndex) throws SQLException { return getDoubleInternal(getString(colIndex), colIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getDoubleInternal(String stringVal, int colIndex) throws SQLException {
/*      */     try {
/* 2481 */       if (stringVal == null) {
/* 2482 */         return 0.0D;
/*      */       }
/*      */       
/* 2485 */       if (stringVal.length() == 0) {
/* 2486 */         return convertToZeroWithEmptyCheck();
/*      */       }
/*      */       
/* 2489 */       double d = Double.parseDouble(stringVal);
/*      */       
/* 2491 */       if (this.useStrictFloatingPoint)
/*      */       {
/* 2493 */         if (d == 2.147483648E9D) {
/*      */           
/* 2495 */           d = 2.147483647E9D;
/* 2496 */         } else if (d == 1.0000000036275E-15D) {
/*      */           
/* 2498 */           d = 1.0E-15D;
/* 2499 */         } else if (d == 9.999999869911E14D) {
/* 2500 */           d = 9.99999999999999E14D;
/* 2501 */         } else if (d == 1.4012984643248E-45D) {
/* 2502 */           d = 1.4E-45D;
/* 2503 */         } else if (d == 1.4013E-45D) {
/* 2504 */           d = 1.4E-45D;
/* 2505 */         } else if (d == 3.4028234663853E37D) {
/* 2506 */           d = 3.4028235E37D;
/* 2507 */         } else if (d == -2.14748E9D) {
/* 2508 */           d = -2.147483648E9D;
/* 2509 */         } else if (d == 3.40282E37D) {
/* 2510 */           d = 3.4028235E37D;
/*      */         } 
/*      */       }
/*      */       
/* 2514 */       return d;
/* 2515 */     } catch (NumberFormatException e) {
/* 2516 */       if (this.fields[colIndex - 1].getMysqlType() == 16) {
/* 2517 */         long valueAsLong = getNumericRepresentationOfSQLBitType(colIndex);
/*      */         
/* 2519 */         return valueAsLong;
/*      */       } 
/*      */       
/* 2522 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_number", new Object[] { stringVal, Constants.integerValueOf(colIndex) }), "S1009", getExceptionInterceptor());
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
/* 2538 */   public int getFetchDirection() throws SQLException { return this.fetchDirection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2550 */   public int getFetchSize() throws SQLException { return this.fetchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2560 */   public char getFirstCharOfQuery() { return this.firstCharOfQuery; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(int columnIndex) throws SQLException {
/* 2575 */     if (!this.isBinaryEncoded) {
/* 2576 */       String val = null;
/*      */       
/* 2578 */       val = getString(columnIndex);
/*      */       
/* 2580 */       return getFloatFromString(val, columnIndex);
/*      */     } 
/*      */     
/* 2583 */     return getNativeFloat(columnIndex);
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
/* 2598 */   public float getFloat(String columnName) throws SQLException { return getFloat(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final float getFloatFromString(String val, int columnIndex) throws SQLException {
/*      */     try {
/* 2604 */       if (val != null) {
/* 2605 */         if (val.length() == 0) {
/* 2606 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2609 */         float f = Float.parseFloat(val);
/*      */         
/* 2611 */         if (this.jdbcCompliantTruncationForReads && (
/* 2612 */           f == Float.MIN_VALUE || f == Float.MAX_VALUE)) {
/* 2613 */           double valAsDouble = Double.parseDouble(val);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2619 */           if (valAsDouble < 1.401298464324817E-45D - MIN_DIFF_PREC || valAsDouble > 3.4028234663852886E38D - MAX_DIFF_PREC)
/*      */           {
/* 2621 */             throwRangeException(String.valueOf(valAsDouble), columnIndex, 6);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2627 */         return f;
/*      */       } 
/*      */       
/* 2630 */       return 0.0F;
/* 2631 */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 2633 */         Double valueAsDouble = new Double(val);
/* 2634 */         float valueAsFloat = valueAsDouble.floatValue();
/*      */         
/* 2636 */         if (this.jdbcCompliantTruncationForReads)
/*      */         {
/* 2638 */           if ((this.jdbcCompliantTruncationForReads && valueAsFloat == Float.NEGATIVE_INFINITY) || valueAsFloat == Float.POSITIVE_INFINITY)
/*      */           {
/*      */             
/* 2641 */             throwRangeException(valueAsDouble.toString(), columnIndex, 6);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/* 2646 */         return valueAsFloat;
/* 2647 */       } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */         
/* 2651 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getFloat()_-____200") + val + Messages.getString("ResultSet.___in_column__201") + columnIndex, "S1009", getExceptionInterceptor());
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
/*      */   public int getInt(int columnIndex) throws SQLException {
/* 2672 */     checkRowPos();
/*      */     
/* 2674 */     if (!this.isBinaryEncoded) {
/* 2675 */       int columnIndexMinusOne = columnIndex - 1;
/* 2676 */       if (this.useFastIntParsing) {
/* 2677 */         checkColumnBounds(columnIndex);
/*      */         
/* 2679 */         if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2680 */           this.wasNullFlag = true;
/*      */         } else {
/* 2682 */           this.wasNullFlag = false;
/*      */         } 
/*      */         
/* 2685 */         if (this.wasNullFlag) {
/* 2686 */           return 0;
/*      */         }
/*      */         
/* 2689 */         if (this.thisRow.length(columnIndexMinusOne) == 0L) {
/* 2690 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2693 */         boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
/*      */ 
/*      */         
/* 2696 */         if (!needsFullParse) {
/*      */           try {
/* 2698 */             return getIntWithOverflowCheck(columnIndexMinusOne);
/* 2699 */           } catch (NumberFormatException nfe) {
/*      */             
/*      */             try {
/* 2702 */               return parseIntAsDouble(columnIndex, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 2707 */             catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */               
/* 2711 */               if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2712 */                 long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */                 
/* 2714 */                 if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -2147483648L || valueAsLong > 2147483647L))
/*      */                 {
/*      */                   
/* 2717 */                   throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/* 2722 */                 return (int)valueAsLong;
/*      */               } 
/*      */               
/* 2725 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         }
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
/* 2741 */       String val = null;
/*      */       
/*      */       try {
/* 2744 */         val = getString(columnIndex);
/*      */         
/* 2746 */         if (val != null) {
/* 2747 */           if (val.length() == 0) {
/* 2748 */             return convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 2751 */           if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
/*      */             
/* 2753 */             int intVal = Integer.parseInt(val);
/*      */             
/* 2755 */             checkForIntegerTruncation(columnIndexMinusOne, null, intVal);
/*      */             
/* 2757 */             return intVal;
/*      */           } 
/*      */ 
/*      */           
/* 2761 */           int intVal = parseIntAsDouble(columnIndex, val);
/*      */           
/* 2763 */           checkForIntegerTruncation(columnIndex, null, intVal);
/*      */           
/* 2765 */           return intVal;
/*      */         } 
/*      */         
/* 2768 */         return 0;
/* 2769 */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 2771 */           return parseIntAsDouble(columnIndex, val);
/* 2772 */         } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */           
/* 2776 */           if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2777 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 2779 */             if (this.jdbcCompliantTruncationForReads && (valueAsLong < -2147483648L || valueAsLong > 2147483647L))
/*      */             {
/* 2781 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */             }
/*      */ 
/*      */             
/* 2785 */             return (int)valueAsLong;
/*      */           } 
/*      */           
/* 2788 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + val + "'", "S1009", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2798 */     return getNativeInt(columnIndex);
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
/* 2813 */   public int getInt(String columnName) throws SQLException { return getInt(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final int getIntFromString(String val, int columnIndex) throws SQLException {
/*      */     try {
/* 2819 */       if (val != null) {
/*      */         
/* 2821 */         if (val.length() == 0) {
/* 2822 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2825 */         if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2835 */           val = val.trim();
/*      */           
/* 2837 */           int valueAsInt = Integer.parseInt(val);
/*      */           
/* 2839 */           if (this.jdbcCompliantTruncationForReads && (
/* 2840 */             valueAsInt == Integer.MIN_VALUE || valueAsInt == Integer.MAX_VALUE)) {
/*      */             
/* 2842 */             long valueAsLong = Long.parseLong(val);
/*      */             
/* 2844 */             if (valueAsLong < -2147483648L || valueAsLong > 2147483647L)
/*      */             {
/* 2846 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2853 */           return valueAsInt;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2858 */         double valueAsDouble = Double.parseDouble(val);
/*      */         
/* 2860 */         if (this.jdbcCompliantTruncationForReads && (
/* 2861 */           valueAsDouble < -2.147483648E9D || valueAsDouble > 2.147483647E9D))
/*      */         {
/* 2863 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2868 */         return (int)valueAsDouble;
/*      */       } 
/*      */       
/* 2871 */       return 0;
/* 2872 */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 2874 */         double valueAsDouble = Double.parseDouble(val);
/*      */         
/* 2876 */         if (this.jdbcCompliantTruncationForReads && (
/* 2877 */           valueAsDouble < -2.147483648E9D || valueAsDouble > 2.147483647E9D))
/*      */         {
/* 2879 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2884 */         return (int)valueAsDouble;
/* 2885 */       } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */         
/* 2889 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____206") + val + Messages.getString("ResultSet.___in_column__207") + columnIndex, "S1009", getExceptionInterceptor());
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
/* 2909 */   public long getLong(int columnIndex) throws SQLException { return getLong(columnIndex, true); }
/*      */ 
/*      */   
/*      */   private long getLong(int columnIndex, boolean overflowCheck) throws SQLException {
/* 2913 */     if (!this.isBinaryEncoded) {
/* 2914 */       checkRowPos();
/*      */       
/* 2916 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 2918 */       if (this.useFastIntParsing) {
/*      */         
/* 2920 */         checkColumnBounds(columnIndex);
/*      */         
/* 2922 */         if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2923 */           this.wasNullFlag = true;
/*      */         } else {
/* 2925 */           this.wasNullFlag = false;
/*      */         } 
/*      */         
/* 2928 */         if (this.wasNullFlag) {
/* 2929 */           return 0L;
/*      */         }
/*      */         
/* 2932 */         if (this.thisRow.length(columnIndexMinusOne) == 0L) {
/* 2933 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2936 */         boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
/*      */         
/* 2938 */         if (!needsFullParse) {
/*      */           try {
/* 2940 */             return getLongWithOverflowCheck(columnIndexMinusOne, overflowCheck);
/* 2941 */           } catch (NumberFormatException nfe) {
/*      */             
/*      */             try {
/* 2944 */               return parseLongAsDouble(columnIndexMinusOne, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 2949 */             catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */               
/* 2953 */               if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2954 */                 return getNumericRepresentationOfSQLBitType(columnIndex);
/*      */               }
/*      */               
/* 2957 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         }
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
/* 2971 */       String val = null;
/*      */       
/*      */       try {
/* 2974 */         val = getString(columnIndex);
/*      */         
/* 2976 */         if (val != null) {
/* 2977 */           if (val.length() == 0) {
/* 2978 */             return convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 2981 */           if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
/* 2982 */             return parseLongWithOverflowCheck(columnIndexMinusOne, null, val, overflowCheck);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 2987 */           return parseLongAsDouble(columnIndexMinusOne, val);
/*      */         } 
/*      */         
/* 2990 */         return 0L;
/* 2991 */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 2993 */           return parseLongAsDouble(columnIndexMinusOne, val);
/* 2994 */         } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */           
/* 2998 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + val + "'", "S1009", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3006 */     return getNativeLong(columnIndex, overflowCheck, true);
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
/* 3021 */   public long getLong(String columnName) throws SQLException { return getLong(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final long getLongFromString(String val, int columnIndexZeroBased) throws SQLException {
/*      */     try {
/* 3027 */       if (val != null) {
/*      */         
/* 3029 */         if (val.length() == 0) {
/* 3030 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 3033 */         if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
/* 3034 */           return parseLongWithOverflowCheck(columnIndexZeroBased, null, val, true);
/*      */         }
/*      */ 
/*      */         
/* 3038 */         return parseLongAsDouble(columnIndexZeroBased, val);
/*      */       } 
/*      */       
/* 3041 */       return 0L;
/* 3042 */     } catch (NumberFormatException nfe) {
/*      */       
/*      */       try {
/* 3045 */         return parseLongAsDouble(columnIndexZeroBased, val);
/* 3046 */       } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */         
/* 3050 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____211") + val + Messages.getString("ResultSet.___in_column__212") + (columnIndexZeroBased + 1), "S1009", getExceptionInterceptor());
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
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/* 3069 */     checkClosed();
/*      */     
/* 3071 */     return new ResultSetMetaData(this.fields, this.connection.getUseOldAliasMetadataBehavior(), getExceptionInterceptor());
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
/* 3089 */   protected Array getNativeArray(int i) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InputStream getNativeAsciiStream(int columnIndex) throws SQLException {
/* 3119 */     checkRowPos();
/*      */     
/* 3121 */     return getNativeBinaryStream(columnIndex);
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
/*      */   protected BigDecimal getNativeBigDecimal(int columnIndex) throws SQLException {
/* 3140 */     checkColumnBounds(columnIndex);
/*      */     
/* 3142 */     int scale = this.fields[columnIndex - 1].getDecimals();
/*      */     
/* 3144 */     return getNativeBigDecimal(columnIndex, scale);
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
/*      */   protected BigDecimal getNativeBigDecimal(int columnIndex, int scale) throws SQLException {
/* 3163 */     checkColumnBounds(columnIndex);
/*      */     
/* 3165 */     String stringVal = null;
/*      */     
/* 3167 */     Field f = this.fields[columnIndex - 1];
/*      */     
/* 3169 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3171 */     if (arrayOfByte == null) {
/* 3172 */       this.wasNullFlag = true;
/*      */       
/* 3174 */       return null;
/*      */     } 
/*      */     
/* 3177 */     this.wasNullFlag = false;
/*      */     
/* 3179 */     switch (f.getSQLType())
/*      */     { case 2:
/*      */       case 3:
/* 3182 */         stringVal = StringUtils.toAsciiString((byte[])arrayOfByte);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3189 */         return getBigDecimalFromString(stringVal, columnIndex, scale); }  stringVal = getNativeString(columnIndex); return getBigDecimalFromString(stringVal, columnIndex, scale);
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
/*      */   protected InputStream getNativeBinaryStream(int columnIndex) throws SQLException {
/* 3211 */     checkRowPos();
/*      */     
/* 3213 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3215 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 3216 */       this.wasNullFlag = true;
/*      */       
/* 3218 */       return null;
/*      */     } 
/*      */     
/* 3221 */     this.wasNullFlag = false;
/*      */     
/* 3223 */     switch (this.fields[columnIndexMinusOne].getSQLType()) {
/*      */       case -7:
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/*      */       case 2004:
/* 3229 */         return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
/*      */     } 
/*      */     
/* 3232 */     byte[] b = getNativeBytes(columnIndex, false);
/*      */     
/* 3234 */     if (b != null) {
/* 3235 */       return new ByteArrayInputStream(b);
/*      */     }
/*      */     
/* 3238 */     return null;
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
/*      */   protected Blob getNativeBlob(int columnIndex) throws SQLException {
/* 3253 */     checkRowPos();
/*      */     
/* 3255 */     checkColumnBounds(columnIndex);
/*      */     
/* 3257 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3259 */     if (arrayOfByte == null) {
/* 3260 */       this.wasNullFlag = true;
/*      */     } else {
/* 3262 */       this.wasNullFlag = false;
/*      */     } 
/*      */     
/* 3265 */     if (this.wasNullFlag) {
/* 3266 */       return null;
/*      */     }
/*      */     
/* 3269 */     int mysqlType = this.fields[columnIndex - 1].getMysqlType();
/*      */     
/* 3271 */     byte[] dataAsBytes = null;
/*      */     
/* 3273 */     switch (mysqlType) {
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/* 3278 */         dataAsBytes = (byte[])arrayOfByte;
/*      */         break;
/*      */       
/*      */       default:
/* 3282 */         dataAsBytes = getNativeBytes(columnIndex, false);
/*      */         break;
/*      */     } 
/* 3285 */     if (!this.connection.getEmulateLocators()) {
/* 3286 */       return new Blob(dataAsBytes, getExceptionInterceptor());
/*      */     }
/*      */     
/* 3289 */     return new BlobFromLocator(this, columnIndex, getExceptionInterceptor());
/*      */   }
/*      */   
/*      */   public static boolean arraysEqual(byte[] left, byte[] right) {
/* 3293 */     if (left == null) {
/* 3294 */       return (right == null);
/*      */     }
/* 3296 */     if (right == null) {
/* 3297 */       return false;
/*      */     }
/* 3299 */     if (left.length != right.length) {
/* 3300 */       return false;
/*      */     }
/* 3302 */     for (int i = 0; i < left.length; i++) {
/* 3303 */       if (left[i] != right[i]) {
/* 3304 */         return false;
/*      */       }
/*      */     } 
/* 3307 */     return true;
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
/* 3322 */   protected byte getNativeByte(int columnIndex) throws SQLException { return getNativeByte(columnIndex, true); } protected byte getNativeByte(int columnIndex, boolean overflowCheck) throws SQLException { double valueAsDouble; float valueAsFloat; int valueAsInt;
/*      */     short valueAsShort;
/*      */     byte valueAsByte;
/*      */     long valueAsLong;
/* 3326 */     checkRowPos();
/*      */     
/* 3328 */     checkColumnBounds(columnIndex);
/*      */     
/* 3330 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3332 */     if (arrayOfByte == null) {
/* 3333 */       this.wasNullFlag = true;
/*      */       
/* 3335 */       return 0;
/*      */     } 
/*      */     
/* 3338 */     if (arrayOfByte == null) {
/* 3339 */       this.wasNullFlag = true;
/*      */     } else {
/* 3341 */       this.wasNullFlag = false;
/*      */     } 
/*      */     
/* 3344 */     if (this.wasNullFlag) {
/* 3345 */       return 0;
/*      */     }
/*      */     
/* 3348 */     columnIndex--;
/*      */     
/* 3350 */     Field field = this.fields[columnIndex];
/*      */     
/* 3352 */     switch (field.getMysqlType()) {
/*      */       case 16:
/* 3354 */         valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */         
/* 3356 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -128L || valueAsLong > 127L))
/*      */         {
/*      */           
/* 3359 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */         
/* 3363 */         return (byte)(int)valueAsLong;
/*      */       case 1:
/* 3365 */         valueAsByte = (byte[])arrayOfByte[0];
/*      */         
/* 3367 */         if (!field.isUnsigned()) {
/* 3368 */           return valueAsByte;
/*      */         }
/*      */         
/* 3371 */         valueAsShort = (valueAsByte >= 0) ? (short)valueAsByte : (short)(valueAsByte + 256);
/*      */ 
/*      */         
/* 3374 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && 
/* 3375 */           valueAsShort > 127) {
/* 3376 */           throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3381 */         return (byte)valueAsShort;
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 3385 */         valueAsShort = getNativeShort(columnIndex + 1);
/*      */         
/* 3387 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 3388 */           valueAsShort < -128 || valueAsShort > 127))
/*      */         {
/* 3390 */           throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3395 */         return (byte)valueAsShort;
/*      */       case 3:
/*      */       case 9:
/* 3398 */         valueAsInt = getNativeInt(columnIndex + 1, false);
/*      */         
/* 3400 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 3401 */           valueAsInt < -128 || valueAsInt > 127)) {
/* 3402 */           throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3407 */         return (byte)valueAsInt;
/*      */       
/*      */       case 4:
/* 3410 */         valueAsFloat = getNativeFloat(columnIndex + 1);
/*      */         
/* 3412 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 3413 */           valueAsFloat < -128.0F || valueAsFloat > 127.0F))
/*      */         {
/*      */           
/* 3416 */           throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3421 */         return (byte)(int)valueAsFloat;
/*      */       
/*      */       case 5:
/* 3424 */         valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */         
/* 3426 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 3427 */           valueAsDouble < -128.0D || valueAsDouble > 127.0D))
/*      */         {
/* 3429 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3434 */         return (byte)(int)valueAsDouble;
/*      */       
/*      */       case 8:
/* 3437 */         valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */         
/* 3439 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 3440 */           valueAsLong < -128L || valueAsLong > 127L))
/*      */         {
/* 3442 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3447 */         return (byte)(int)valueAsLong;
/*      */     } 
/*      */     
/* 3450 */     if (this.useUsageAdvisor) {
/* 3451 */       issueConversionViaParsingWarning("getByte()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3461 */     return getByteFromString(getNativeString(columnIndex + 1), columnIndex + 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] getNativeBytes(int columnIndex, boolean noConversion) throws SQLException {
/* 3483 */     checkRowPos();
/*      */     
/* 3485 */     checkColumnBounds(columnIndex);
/*      */     
/* 3487 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3489 */     if (arrayOfByte == null) {
/* 3490 */       this.wasNullFlag = true;
/*      */     } else {
/* 3492 */       this.wasNullFlag = false;
/*      */     } 
/*      */     
/* 3495 */     if (this.wasNullFlag) {
/* 3496 */       return null;
/*      */     }
/*      */     
/* 3499 */     Field field = this.fields[columnIndex - 1];
/*      */     
/* 3501 */     int mysqlType = field.getMysqlType();
/*      */ 
/*      */ 
/*      */     
/* 3505 */     if (noConversion) {
/* 3506 */       mysqlType = 252;
/*      */     }
/*      */     
/* 3509 */     switch (mysqlType) {
/*      */       case 16:
/*      */       case 249:
/*      */       case 250:
/*      */       case 251:
/*      */       case 252:
/* 3515 */         return (byte[])arrayOfByte;
/*      */       
/*      */       case 15:
/*      */       case 253:
/*      */       case 254:
/* 3520 */         if (arrayOfByte instanceof byte[]) {
/* 3521 */           return (byte[])arrayOfByte;
/*      */         }
/*      */         break;
/*      */     } 
/* 3525 */     int sqlType = field.getSQLType();
/*      */     
/* 3527 */     if (sqlType == -3 || sqlType == -2) {
/* 3528 */       return (byte[])arrayOfByte;
/*      */     }
/*      */     
/* 3531 */     return getBytesFromString(getNativeString(columnIndex), columnIndex);
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
/*      */   protected Reader getNativeCharacterStream(int columnIndex) throws SQLException {
/* 3552 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3554 */     switch (this.fields[columnIndexMinusOne].getSQLType()) {
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/*      */       case 2005:
/* 3559 */         if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 3560 */           this.wasNullFlag = true;
/*      */           
/* 3562 */           return null;
/*      */         } 
/*      */         
/* 3565 */         this.wasNullFlag = false;
/*      */         
/* 3567 */         return this.thisRow.getReader(columnIndexMinusOne);
/*      */     } 
/*      */     
/* 3570 */     String asString = null;
/*      */     
/* 3572 */     asString = getStringForClob(columnIndex);
/*      */     
/* 3574 */     if (asString == null) {
/* 3575 */       return null;
/*      */     }
/*      */     
/* 3578 */     return getCharacterStreamFromString(asString, columnIndex);
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
/*      */   protected Clob getNativeClob(int columnIndex) throws SQLException
/*      */   {
/* 3593 */     String stringVal = getStringForClob(columnIndex);
/*      */     
/* 3595 */     if (stringVal == null) {
/* 3596 */       return null;
/*      */     }
/*      */     
/* 3599 */     return getClobFromString(stringVal, columnIndex); } private String getNativeConvertToString(int columnIndex, Field field) throws SQLException { String result; Timestamp tstamp; byte[] arrayOfByte; Time tm; Date dt; byte[] data; String stringVal;
/*      */     double doubleVal;
/*      */     float floatVal;
/*      */     long longVal, longVal;
/*      */     int intVal;
/*      */     short unsignedTinyVal;
/*      */     byte tinyintVal;
/*      */     boolean booleanVal;
/* 3607 */     int sqlType = field.getSQLType();
/* 3608 */     int mysqlType = field.getMysqlType();
/*      */     
/* 3610 */     switch (sqlType) {
/*      */       case -7:
/* 3612 */         return String.valueOf(getNumericRepresentationOfSQLBitType(columnIndex));
/*      */       case 16:
/* 3614 */         booleanVal = getBoolean(columnIndex);
/*      */         
/* 3616 */         if (this.wasNullFlag) {
/* 3617 */           return null;
/*      */         }
/*      */         
/* 3620 */         return String.valueOf(booleanVal);
/*      */       
/*      */       case -6:
/* 3623 */         tinyintVal = getNativeByte(columnIndex, false);
/*      */         
/* 3625 */         if (this.wasNullFlag) {
/* 3626 */           return null;
/*      */         }
/*      */         
/* 3629 */         if (!field.isUnsigned() || tinyintVal >= 0) {
/* 3630 */           return String.valueOf(tinyintVal);
/*      */         }
/*      */         
/* 3633 */         unsignedTinyVal = (short)(tinyintVal & 0xFF);
/*      */         
/* 3635 */         return String.valueOf(unsignedTinyVal);
/*      */ 
/*      */       
/*      */       case 5:
/* 3639 */         intVal = getNativeInt(columnIndex, false);
/*      */         
/* 3641 */         if (this.wasNullFlag) {
/* 3642 */           return null;
/*      */         }
/*      */         
/* 3645 */         if (!field.isUnsigned() || intVal >= 0) {
/* 3646 */           return String.valueOf(intVal);
/*      */         }
/*      */         
/* 3649 */         intVal &= 0xFFFF;
/*      */         
/* 3651 */         return String.valueOf(intVal);
/*      */       
/*      */       case 4:
/* 3654 */         intVal = getNativeInt(columnIndex, false);
/*      */         
/* 3656 */         if (this.wasNullFlag) {
/* 3657 */           return null;
/*      */         }
/*      */         
/* 3660 */         if (!field.isUnsigned() || intVal >= 0 || field.getMysqlType() == 9)
/*      */         {
/*      */           
/* 3663 */           return String.valueOf(intVal);
/*      */         }
/*      */         
/* 3666 */         longVal = intVal & 0xFFFFFFFFL;
/*      */         
/* 3668 */         return String.valueOf(longVal);
/*      */ 
/*      */       
/*      */       case -5:
/* 3672 */         if (!field.isUnsigned()) {
/* 3673 */           longVal = getNativeLong(columnIndex, false, true);
/*      */           
/* 3675 */           if (this.wasNullFlag) {
/* 3676 */             return null;
/*      */           }
/*      */           
/* 3679 */           return String.valueOf(longVal);
/*      */         } 
/*      */         
/* 3682 */         longVal = getNativeLong(columnIndex, false, false);
/*      */         
/* 3684 */         if (this.wasNullFlag) {
/* 3685 */           return null;
/*      */         }
/*      */         
/* 3688 */         return String.valueOf(convertLongToUlong(longVal));
/*      */       case 7:
/* 3690 */         floatVal = getNativeFloat(columnIndex);
/*      */         
/* 3692 */         if (this.wasNullFlag) {
/* 3693 */           return null;
/*      */         }
/*      */         
/* 3696 */         return String.valueOf(floatVal);
/*      */       
/*      */       case 6:
/*      */       case 8:
/* 3700 */         doubleVal = getNativeDouble(columnIndex);
/*      */         
/* 3702 */         if (this.wasNullFlag) {
/* 3703 */           return null;
/*      */         }
/*      */         
/* 3706 */         return String.valueOf(doubleVal);
/*      */       
/*      */       case 2:
/*      */       case 3:
/* 3710 */         stringVal = StringUtils.toAsciiString((byte[])this.thisRow.getColumnValue(columnIndex - 1));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3715 */         if (stringVal != null) {
/* 3716 */           BigDecimal val; this.wasNullFlag = false;
/*      */           
/* 3718 */           if (stringVal.length() == 0) {
/* 3719 */             val = new BigDecimal(false);
/*      */             
/* 3721 */             return val.toString();
/*      */           } 
/*      */           
/*      */           try {
/* 3725 */             val = new BigDecimal(stringVal);
/* 3726 */           } catch (NumberFormatException ex) {
/* 3727 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3734 */           return val.toString();
/*      */         } 
/*      */         
/* 3737 */         this.wasNullFlag = true;
/*      */         
/* 3739 */         return null;
/*      */ 
/*      */       
/*      */       case -1:
/*      */       case 1:
/*      */       case 12:
/* 3745 */         return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */       
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 3750 */         if (!field.isBlob())
/* 3751 */           return extractStringFromNativeColumn(columnIndex, mysqlType); 
/* 3752 */         if (!field.isBinary()) {
/* 3753 */           return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */         }
/* 3755 */         data = getBytes(columnIndex);
/* 3756 */         arrayOfByte = data;
/*      */         
/* 3758 */         if (data != null && data.length >= 2) {
/* 3759 */           if (data[0] == -84 && data[1] == -19) {
/*      */             
/*      */             try {
/* 3762 */               ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
/*      */               
/* 3764 */               ObjectInputStream objIn = new ObjectInputStream(bytesIn);
/*      */               
/* 3766 */               Object object = objIn.readObject();
/* 3767 */               objIn.close();
/* 3768 */               bytesIn.close();
/* 3769 */             } catch (ClassNotFoundException cnfe) {
/* 3770 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 3776 */             catch (IOException ex) {
/* 3777 */               arrayOfByte = data;
/*      */             } 
/*      */           }
/*      */           
/* 3781 */           return arrayOfByte.toString();
/*      */         } 
/*      */         
/* 3784 */         return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 91:
/* 3790 */         if (mysqlType == 13) {
/* 3791 */           short shortVal = getNativeShort(columnIndex);
/*      */           
/* 3793 */           if (!this.connection.getYearIsDateType()) {
/*      */             
/* 3795 */             if (this.wasNullFlag) {
/* 3796 */               return null;
/*      */             }
/*      */             
/* 3799 */             return String.valueOf(shortVal);
/*      */           } 
/*      */           
/* 3802 */           if (field.getLength() == 2L) {
/*      */             
/* 3804 */             if (shortVal <= 69) {
/* 3805 */               shortVal = (short)(shortVal + 100);
/*      */             }
/*      */             
/* 3808 */             shortVal = (short)(shortVal + 1900);
/*      */           } 
/*      */           
/* 3811 */           return fastDateCreate(null, shortVal, 1, 1).toString();
/*      */         } 
/*      */ 
/*      */         
/* 3815 */         if (this.connection.getNoDatetimeStringSync()) {
/* 3816 */           byte[] asBytes = getNativeBytes(columnIndex, true);
/*      */           
/* 3818 */           if (asBytes == null) {
/* 3819 */             return null;
/*      */           }
/*      */           
/* 3822 */           if (asBytes.length == 0)
/*      */           {
/* 3824 */             return "0000-00-00";
/*      */           }
/*      */           
/* 3827 */           int year = asBytes[0] & 0xFF | (asBytes[1] & 0xFF) << 8;
/*      */           
/* 3829 */           int month = asBytes[2];
/* 3830 */           int day = asBytes[3];
/*      */           
/* 3832 */           if (year == 0 && month == 0 && day == 0) {
/* 3833 */             return "0000-00-00";
/*      */           }
/*      */         } 
/*      */         
/* 3837 */         dt = getNativeDate(columnIndex);
/*      */         
/* 3839 */         if (dt == null) {
/* 3840 */           return null;
/*      */         }
/*      */         
/* 3843 */         return String.valueOf(dt);
/*      */       
/*      */       case 92:
/* 3846 */         tm = getNativeTime(columnIndex, null, this.defaultTimeZone, false);
/*      */         
/* 3848 */         if (tm == null) {
/* 3849 */           return null;
/*      */         }
/*      */         
/* 3852 */         return String.valueOf(tm);
/*      */       
/*      */       case 93:
/* 3855 */         if (this.connection.getNoDatetimeStringSync()) {
/* 3856 */           byte[] asBytes = getNativeBytes(columnIndex, true);
/*      */           
/* 3858 */           if (asBytes == null) {
/* 3859 */             return null;
/*      */           }
/*      */           
/* 3862 */           if (asBytes.length == 0)
/*      */           {
/* 3864 */             return "0000-00-00 00:00:00";
/*      */           }
/*      */           
/* 3867 */           int year = asBytes[0] & 0xFF | (asBytes[1] & 0xFF) << 8;
/*      */           
/* 3869 */           int month = asBytes[2];
/* 3870 */           int day = asBytes[3];
/*      */           
/* 3872 */           if (year == 0 && month == 0 && day == 0) {
/* 3873 */             return "0000-00-00 00:00:00";
/*      */           }
/*      */         } 
/*      */         
/* 3877 */         tstamp = getNativeTimestamp(columnIndex, null, this.defaultTimeZone, false);
/*      */ 
/*      */         
/* 3880 */         if (tstamp == null) {
/* 3881 */           return null;
/*      */         }
/*      */         
/* 3884 */         result = String.valueOf(tstamp);
/*      */         
/* 3886 */         if (!this.connection.getNoDatetimeStringSync()) {
/* 3887 */           return result;
/*      */         }
/*      */         
/* 3890 */         if (result.endsWith(".0")) {
/* 3891 */           return result.substring(0, result.length() - 2);
/*      */         }
/*      */         break;
/*      */     } 
/* 3895 */     return extractStringFromNativeColumn(columnIndex, mysqlType); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3911 */   protected Date getNativeDate(int columnIndex) throws SQLException { return getNativeDate(columnIndex, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Date getNativeDate(int columnIndex, Calendar cal) throws SQLException {
/* 3932 */     checkRowPos();
/* 3933 */     checkColumnBounds(columnIndex);
/*      */     
/* 3935 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3937 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 3939 */     Date dateToReturn = null;
/*      */     
/* 3941 */     if (mysqlType == 10) {
/*      */       
/* 3943 */       dateToReturn = this.thisRow.getNativeDate(columnIndexMinusOne, this.connection, this, cal);
/*      */     } else {
/*      */       
/* 3946 */       TimeZone tz = (cal != null) ? cal.getTimeZone() : getDefaultTimeZone();
/*      */ 
/*      */       
/* 3949 */       boolean rollForward = (tz != null && !tz.equals(getDefaultTimeZone()));
/*      */       
/* 3951 */       dateToReturn = (Date)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 91, mysqlType, tz, rollForward, this.connection, this);
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
/* 3963 */     if (dateToReturn == null) {
/*      */       
/* 3965 */       this.wasNullFlag = true;
/*      */       
/* 3967 */       return null;
/*      */     } 
/*      */     
/* 3970 */     this.wasNullFlag = false;
/*      */     
/* 3972 */     return dateToReturn;
/*      */   }
/*      */   
/*      */   Date getNativeDateViaParseConversion(int columnIndex) throws SQLException {
/* 3976 */     if (this.useUsageAdvisor) {
/* 3977 */       issueConversionViaParsingWarning("getDate()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 10 });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3982 */     String stringVal = getNativeString(columnIndex);
/*      */     
/* 3984 */     return getDateFromString(stringVal, columnIndex, null);
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
/*      */   protected double getNativeDouble(int columnIndex) throws SQLException {
/*      */     BigInteger asBigInt;
/*      */     long valueAsLong;
/* 3999 */     checkRowPos();
/* 4000 */     checkColumnBounds(columnIndex);
/*      */     
/* 4002 */     columnIndex--;
/*      */     
/* 4004 */     if (this.thisRow.isNull(columnIndex)) {
/* 4005 */       this.wasNullFlag = true;
/*      */       
/* 4007 */       return 0.0D;
/*      */     } 
/*      */     
/* 4010 */     this.wasNullFlag = false;
/*      */     
/* 4012 */     Field f = this.fields[columnIndex];
/*      */     
/* 4014 */     switch (f.getMysqlType()) {
/*      */       case 5:
/* 4016 */         return this.thisRow.getNativeDouble(columnIndex);
/*      */       case 1:
/* 4018 */         if (!f.isUnsigned()) {
/* 4019 */           return getNativeByte(columnIndex + 1);
/*      */         }
/*      */         
/* 4022 */         return getNativeShort(columnIndex + 1);
/*      */       case 2:
/*      */       case 13:
/* 4025 */         if (!f.isUnsigned()) {
/* 4026 */           return getNativeShort(columnIndex + 1);
/*      */         }
/*      */         
/* 4029 */         return getNativeInt(columnIndex + 1);
/*      */       case 3:
/*      */       case 9:
/* 4032 */         if (!f.isUnsigned()) {
/* 4033 */           return getNativeInt(columnIndex + 1);
/*      */         }
/*      */         
/* 4036 */         return getNativeLong(columnIndex + 1);
/*      */       case 8:
/* 4038 */         valueAsLong = getNativeLong(columnIndex + 1);
/*      */         
/* 4040 */         if (!f.isUnsigned()) {
/* 4041 */           return valueAsLong;
/*      */         }
/*      */         
/* 4044 */         asBigInt = convertLongToUlong(valueAsLong);
/*      */ 
/*      */ 
/*      */         
/* 4048 */         return asBigInt.doubleValue();
/*      */       case 4:
/* 4050 */         return getNativeFloat(columnIndex + 1);
/*      */       case 16:
/* 4052 */         return getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */     } 
/* 4054 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4056 */     if (this.useUsageAdvisor) {
/* 4057 */       issueConversionViaParsingWarning("getDouble()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4067 */     return getDoubleFromString(stringVal, columnIndex + 1);
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
/*      */   protected float getNativeFloat(int columnIndex) throws SQLException {
/*      */     BigInteger asBigInt;
/*      */     float valueAsFloat;
/*      */     Double valueAsDouble;
/*      */     long valueAsLong;
/* 4083 */     checkRowPos();
/* 4084 */     checkColumnBounds(columnIndex);
/*      */     
/* 4086 */     columnIndex--;
/*      */     
/* 4088 */     if (this.thisRow.isNull(columnIndex)) {
/* 4089 */       this.wasNullFlag = true;
/*      */       
/* 4091 */       return 0.0F;
/*      */     } 
/*      */     
/* 4094 */     this.wasNullFlag = false;
/*      */     
/* 4096 */     Field f = this.fields[columnIndex];
/*      */     
/* 4098 */     switch (f.getMysqlType()) {
/*      */       case 16:
/* 4100 */         valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */         
/* 4102 */         return (float)valueAsLong;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 4109 */         valueAsDouble = new Double(getNativeDouble(columnIndex + 1));
/*      */         
/* 4111 */         valueAsFloat = valueAsDouble.floatValue();
/*      */         
/* 4113 */         if ((this.jdbcCompliantTruncationForReads && valueAsFloat == Float.NEGATIVE_INFINITY) || valueAsFloat == Float.POSITIVE_INFINITY)
/*      */         {
/*      */           
/* 4116 */           throwRangeException(valueAsDouble.toString(), columnIndex + 1, 6);
/*      */         }
/*      */ 
/*      */         
/* 4120 */         return (float)getNativeDouble(columnIndex + 1);
/*      */       case 1:
/* 4122 */         if (!f.isUnsigned()) {
/* 4123 */           return getNativeByte(columnIndex + 1);
/*      */         }
/*      */         
/* 4126 */         return getNativeShort(columnIndex + 1);
/*      */       case 2:
/*      */       case 13:
/* 4129 */         if (!f.isUnsigned()) {
/* 4130 */           return getNativeShort(columnIndex + 1);
/*      */         }
/*      */         
/* 4133 */         return getNativeInt(columnIndex + 1);
/*      */       case 3:
/*      */       case 9:
/* 4136 */         if (!f.isUnsigned()) {
/* 4137 */           return getNativeInt(columnIndex + 1);
/*      */         }
/*      */         
/* 4140 */         return (float)getNativeLong(columnIndex + 1);
/*      */       case 8:
/* 4142 */         valueAsLong = getNativeLong(columnIndex + 1);
/*      */         
/* 4144 */         if (!f.isUnsigned()) {
/* 4145 */           return (float)valueAsLong;
/*      */         }
/*      */         
/* 4148 */         asBigInt = convertLongToUlong(valueAsLong);
/*      */ 
/*      */ 
/*      */         
/* 4152 */         return asBigInt.floatValue();
/*      */       
/*      */       case 4:
/* 4155 */         return this.thisRow.getNativeFloat(columnIndex);
/*      */     } 
/*      */     
/* 4158 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4160 */     if (this.useUsageAdvisor) {
/* 4161 */       issueConversionViaParsingWarning("getFloat()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4171 */     return getFloatFromString(stringVal, columnIndex + 1);
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
/* 4187 */   protected int getNativeInt(int columnIndex) throws SQLException { return getNativeInt(columnIndex, true); } protected int getNativeInt(int columnIndex, boolean overflowCheck) throws SQLException { double valueAsDouble; int valueAsInt;
/*      */     short asShort;
/*      */     byte tinyintVal;
/*      */     long valueAsLong;
/* 4191 */     checkRowPos();
/* 4192 */     checkColumnBounds(columnIndex);
/*      */     
/* 4194 */     columnIndex--;
/*      */     
/* 4196 */     if (this.thisRow.isNull(columnIndex)) {
/* 4197 */       this.wasNullFlag = true;
/*      */       
/* 4199 */       return 0;
/*      */     } 
/*      */     
/* 4202 */     this.wasNullFlag = false;
/*      */     
/* 4204 */     Field f = this.fields[columnIndex];
/*      */     
/* 4206 */     switch (f.getMysqlType()) {
/*      */       case 16:
/* 4208 */         valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */         
/* 4210 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -2147483648L || valueAsLong > 2147483647L))
/*      */         {
/*      */           
/* 4213 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */         }
/*      */ 
/*      */         
/* 4217 */         return (short)(int)valueAsLong;
/*      */       case 1:
/* 4219 */         tinyintVal = getNativeByte(columnIndex + 1, false);
/*      */         
/* 4221 */         if (!f.isUnsigned() || tinyintVal >= 0) {
/* 4222 */           return tinyintVal;
/*      */         }
/*      */         
/* 4225 */         return tinyintVal + 256;
/*      */       case 2:
/*      */       case 13:
/* 4228 */         asShort = getNativeShort(columnIndex + 1, false);
/*      */         
/* 4230 */         if (!f.isUnsigned() || asShort >= 0) {
/* 4231 */           return asShort;
/*      */         }
/*      */         
/* 4234 */         return asShort + 65536;
/*      */       
/*      */       case 3:
/*      */       case 9:
/* 4238 */         valueAsInt = this.thisRow.getNativeInt(columnIndex);
/*      */         
/* 4240 */         if (!f.isUnsigned()) {
/* 4241 */           return valueAsInt;
/*      */         }
/*      */         
/* 4244 */         valueAsLong = (valueAsInt >= 0) ? valueAsInt : (valueAsInt + 4294967296L);
/*      */ 
/*      */         
/* 4247 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsLong > 2147483647L)
/*      */         {
/* 4249 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */         }
/*      */ 
/*      */         
/* 4253 */         return (int)valueAsLong;
/*      */       case 8:
/* 4255 */         valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */         
/* 4257 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4258 */           valueAsLong < -2147483648L || valueAsLong > 2147483647L))
/*      */         {
/* 4260 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4265 */         return (int)valueAsLong;
/*      */       case 5:
/* 4267 */         valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */         
/* 4269 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4270 */           valueAsDouble < -2.147483648E9D || valueAsDouble > 2.147483647E9D))
/*      */         {
/* 4272 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4277 */         return (int)valueAsDouble;
/*      */       case 4:
/* 4279 */         valueAsDouble = getNativeFloat(columnIndex + 1);
/*      */         
/* 4281 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4282 */           valueAsDouble < -2.147483648E9D || valueAsDouble > 2.147483647E9D))
/*      */         {
/* 4284 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4289 */         return (int)valueAsDouble;
/*      */     } 
/*      */     
/* 4292 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4294 */     if (this.useUsageAdvisor) {
/* 4295 */       issueConversionViaParsingWarning("getInt()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4305 */     return getIntFromString(stringVal, columnIndex + 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4321 */   protected long getNativeLong(int columnIndex) throws SQLException { return getNativeLong(columnIndex, true, true); } protected long getNativeLong(int columnIndex, boolean overflowCheck, boolean expandUnsignedLong) throws SQLException {
/*      */     double valueAsDouble;
/*      */     BigInteger asBigInt;
/*      */     long valueAsLong;
/*      */     int asInt;
/* 4326 */     checkRowPos();
/* 4327 */     checkColumnBounds(columnIndex);
/*      */     
/* 4329 */     columnIndex--;
/*      */     
/* 4331 */     if (this.thisRow.isNull(columnIndex)) {
/* 4332 */       this.wasNullFlag = true;
/*      */       
/* 4334 */       return 0L;
/*      */     } 
/*      */     
/* 4337 */     this.wasNullFlag = false;
/*      */     
/* 4339 */     Field f = this.fields[columnIndex];
/*      */     
/* 4341 */     switch (f.getMysqlType()) {
/*      */       case 16:
/* 4343 */         return getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */       case 1:
/* 4345 */         if (!f.isUnsigned()) {
/* 4346 */           return getNativeByte(columnIndex + 1);
/*      */         }
/*      */         
/* 4349 */         return getNativeInt(columnIndex + 1);
/*      */       case 2:
/* 4351 */         if (!f.isUnsigned()) {
/* 4352 */           return getNativeShort(columnIndex + 1);
/*      */         }
/*      */         
/* 4355 */         return getNativeInt(columnIndex + 1, false);
/*      */       
/*      */       case 13:
/* 4358 */         return getNativeShort(columnIndex + 1);
/*      */       case 3:
/*      */       case 9:
/* 4361 */         asInt = getNativeInt(columnIndex + 1, false);
/*      */         
/* 4363 */         if (!f.isUnsigned() || asInt >= 0) {
/* 4364 */           return asInt;
/*      */         }
/*      */         
/* 4367 */         return asInt + 4294967296L;
/*      */       case 8:
/* 4369 */         valueAsLong = this.thisRow.getNativeLong(columnIndex);
/*      */         
/* 4371 */         if (!f.isUnsigned() || !expandUnsignedLong) {
/* 4372 */           return valueAsLong;
/*      */         }
/*      */         
/* 4375 */         asBigInt = convertLongToUlong(valueAsLong);
/*      */         
/* 4377 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (asBigInt.compareTo(new BigInteger(String.valueOf(Float.MAX_VALUE))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(Float.MIN_VALUE))) < 0))
/*      */         {
/*      */           
/* 4380 */           throwRangeException(asBigInt.toString(), columnIndex + 1, -5);
/*      */         }
/*      */ 
/*      */         
/* 4384 */         return getLongFromString(asBigInt.toString(), columnIndex);
/*      */       
/*      */       case 5:
/* 4387 */         valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */         
/* 4389 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4390 */           valueAsDouble < -9.223372036854776E18D || valueAsDouble > 9.223372036854776E18D))
/*      */         {
/* 4392 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4397 */         return (long)valueAsDouble;
/*      */       case 4:
/* 4399 */         valueAsDouble = getNativeFloat(columnIndex + 1);
/*      */         
/* 4401 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4402 */           valueAsDouble < -9.223372036854776E18D || valueAsDouble > 9.223372036854776E18D))
/*      */         {
/* 4404 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4409 */         return (long)valueAsDouble;
/*      */     } 
/* 4411 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4413 */     if (this.useUsageAdvisor) {
/* 4414 */       issueConversionViaParsingWarning("getLong()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4424 */     return getLongFromString(stringVal, columnIndex + 1);
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
/* 4442 */   protected Ref getNativeRef(int i) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4457 */   protected short getNativeShort(int columnIndex) throws SQLException { return getNativeShort(columnIndex, true); } protected short getNativeShort(int columnIndex, boolean overflowCheck) throws SQLException { float valueAsFloat; double valueAsDouble; BigInteger asBigInt; long valueAsLong;
/*      */     int valueAsInt;
/*      */     short asShort;
/*      */     byte tinyintVal;
/* 4461 */     checkRowPos();
/* 4462 */     checkColumnBounds(columnIndex);
/*      */     
/* 4464 */     columnIndex--;
/*      */ 
/*      */     
/* 4467 */     if (this.thisRow.isNull(columnIndex)) {
/* 4468 */       this.wasNullFlag = true;
/*      */       
/* 4470 */       return 0;
/*      */     } 
/*      */     
/* 4473 */     this.wasNullFlag = false;
/*      */     
/* 4475 */     Field f = this.fields[columnIndex];
/*      */     
/* 4477 */     switch (f.getMysqlType()) {
/*      */       
/*      */       case 1:
/* 4480 */         tinyintVal = getNativeByte(columnIndex + 1, false);
/*      */         
/* 4482 */         if (!f.isUnsigned() || tinyintVal >= 0) {
/* 4483 */           return (short)tinyintVal;
/*      */         }
/*      */         
/* 4486 */         return (short)(tinyintVal + 256);
/*      */       
/*      */       case 2:
/*      */       case 13:
/* 4490 */         asShort = this.thisRow.getNativeShort(columnIndex);
/*      */         
/* 4492 */         if (!f.isUnsigned()) {
/* 4493 */           return asShort;
/*      */         }
/*      */         
/* 4496 */         valueAsInt = asShort & 0xFFFF;
/*      */         
/* 4498 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsInt > 32767)
/*      */         {
/* 4500 */           throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
/*      */         }
/*      */ 
/*      */         
/* 4504 */         return (short)valueAsInt;
/*      */       case 3:
/*      */       case 9:
/* 4507 */         if (!f.isUnsigned()) {
/* 4508 */           valueAsInt = getNativeInt(columnIndex + 1, false);
/*      */           
/* 4510 */           if ((overflowCheck && this.jdbcCompliantTruncationForReads && valueAsInt > 32767) || valueAsInt < -32768)
/*      */           {
/*      */             
/* 4513 */             throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
/*      */           }
/*      */ 
/*      */           
/* 4517 */           return (short)valueAsInt;
/*      */         } 
/*      */         
/* 4520 */         valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */         
/* 4522 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsLong > 32767L)
/*      */         {
/* 4524 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
/*      */         }
/*      */ 
/*      */         
/* 4528 */         return (short)(int)valueAsLong;
/*      */       
/*      */       case 8:
/* 4531 */         valueAsLong = getNativeLong(columnIndex + 1, false, false);
/*      */         
/* 4533 */         if (!f.isUnsigned()) {
/* 4534 */           if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4535 */             valueAsLong < -32768L || valueAsLong > 32767L))
/*      */           {
/* 4537 */             throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 4542 */           return (short)(int)valueAsLong;
/*      */         } 
/*      */         
/* 4545 */         asBigInt = convertLongToUlong(valueAsLong);
/*      */         
/* 4547 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (asBigInt.compareTo(new BigInteger(String.valueOf(32767))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(-32768))) < 0))
/*      */         {
/*      */           
/* 4550 */           throwRangeException(asBigInt.toString(), columnIndex + 1, 5);
/*      */         }
/*      */ 
/*      */         
/* 4554 */         return (short)getIntFromString(asBigInt.toString(), columnIndex + 1);
/*      */       
/*      */       case 5:
/* 4557 */         valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */         
/* 4559 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4560 */           valueAsDouble < -32768.0D || valueAsDouble > 32767.0D))
/*      */         {
/* 4562 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 5);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4567 */         return (short)(int)valueAsDouble;
/*      */       case 4:
/* 4569 */         valueAsFloat = getNativeFloat(columnIndex + 1);
/*      */         
/* 4571 */         if (overflowCheck && this.jdbcCompliantTruncationForReads && (
/* 4572 */           valueAsFloat < -32768.0F || valueAsFloat > 32767.0F))
/*      */         {
/* 4574 */           throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, 5);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4579 */         return (short)(int)valueAsFloat;
/*      */     } 
/* 4581 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4583 */     if (this.useUsageAdvisor) {
/* 4584 */       issueConversionViaParsingWarning("getShort()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4594 */     return getShortFromString(stringVal, columnIndex + 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNativeString(int columnIndex) throws SQLException {
/* 4610 */     checkRowPos();
/* 4611 */     checkColumnBounds(columnIndex);
/*      */     
/* 4613 */     if (this.fields == null) {
/* 4614 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_133"), "S1002", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4620 */     if (this.thisRow.isNull(columnIndex - 1)) {
/* 4621 */       this.wasNullFlag = true;
/*      */       
/* 4623 */       return null;
/*      */     } 
/*      */     
/* 4626 */     this.wasNullFlag = false;
/*      */     
/* 4628 */     String stringVal = null;
/*      */     
/* 4630 */     Field field = this.fields[columnIndex - 1];
/*      */ 
/*      */     
/* 4633 */     stringVal = getNativeConvertToString(columnIndex, field);
/* 4634 */     int mysqlType = field.getMysqlType();
/*      */     
/* 4636 */     if (mysqlType != 7 && mysqlType != 10 && field.isZeroFill() && stringVal != null) {
/*      */ 
/*      */       
/* 4639 */       int origLength = stringVal.length();
/*      */       
/* 4641 */       StringBuffer zeroFillBuf = new StringBuffer(origLength);
/*      */       
/* 4643 */       long numZeros = field.getLength() - origLength;
/*      */       long i;
/* 4645 */       for (i = 0L; i < numZeros; i++) {
/* 4646 */         zeroFillBuf.append('0');
/*      */       }
/*      */       
/* 4649 */       zeroFillBuf.append(stringVal);
/*      */       
/* 4651 */       stringVal = zeroFillBuf.toString();
/*      */     } 
/*      */     
/* 4654 */     return stringVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Time getNativeTime(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4660 */     checkRowPos();
/* 4661 */     checkColumnBounds(columnIndex);
/*      */     
/* 4663 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4665 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 4667 */     Time timeVal = null;
/*      */     
/* 4669 */     if (mysqlType == 11) {
/* 4670 */       timeVal = this.thisRow.getNativeTime(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
/*      */     }
/*      */     else {
/*      */       
/* 4674 */       timeVal = (Time)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 92, mysqlType, tz, rollForward, this.connection, this);
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
/* 4686 */     if (timeVal == null) {
/*      */       
/* 4688 */       this.wasNullFlag = true;
/*      */       
/* 4690 */       return null;
/*      */     } 
/*      */     
/* 4693 */     this.wasNullFlag = false;
/*      */     
/* 4695 */     return timeVal;
/*      */   }
/*      */ 
/*      */   
/*      */   Time getNativeTimeViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4700 */     if (this.useUsageAdvisor) {
/* 4701 */       issueConversionViaParsingWarning("getTime()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 11 });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4706 */     String strTime = getNativeString(columnIndex);
/*      */     
/* 4708 */     return getTimeFromString(strTime, targetCalendar, columnIndex, tz, rollForward);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Timestamp getNativeTimestamp(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4715 */     checkRowPos();
/* 4716 */     checkColumnBounds(columnIndex);
/*      */     
/* 4718 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4720 */     Timestamp tsVal = null;
/*      */     
/* 4722 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 4724 */     switch (mysqlType) {
/*      */       case 7:
/*      */       case 12:
/* 4727 */         tsVal = this.thisRow.getNativeTimestamp(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 4734 */         tsVal = (Timestamp)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 93, mysqlType, tz, rollForward, this.connection, this);
/*      */         break;
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
/* 4746 */     if (tsVal == null) {
/*      */       
/* 4748 */       this.wasNullFlag = true;
/*      */       
/* 4750 */       return null;
/*      */     } 
/*      */     
/* 4753 */     this.wasNullFlag = false;
/*      */     
/* 4755 */     return tsVal;
/*      */   }
/*      */ 
/*      */   
/*      */   Timestamp getNativeTimestampViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 4760 */     if (this.useUsageAdvisor) {
/* 4761 */       issueConversionViaParsingWarning("getTimestamp()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 7, 12 });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4767 */     String strTimestamp = getNativeString(columnIndex);
/*      */     
/* 4769 */     return getTimestampFromString(columnIndex, targetCalendar, strTimestamp, tz, rollForward);
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
/*      */   protected InputStream getNativeUnicodeStream(int columnIndex) throws SQLException {
/* 4796 */     checkRowPos();
/*      */     
/* 4798 */     return getBinaryStream(columnIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected URL getNativeURL(int colIndex) throws SQLException {
/* 4805 */     String val = getString(colIndex);
/*      */     
/* 4807 */     if (val == null) {
/* 4808 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 4812 */       return new URL(val);
/* 4813 */     } catch (MalformedURLException mfe) {
/* 4814 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____141") + val + "'", "S1009", getExceptionInterceptor());
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
/* 4826 */   public ResultSetInternalMethods getNextResultSet() throws SQLException { return this.nextResultSet; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(int columnIndex) throws SQLException {
/*      */     String stringVal;
/* 4853 */     checkRowPos();
/* 4854 */     checkColumnBounds(columnIndex);
/*      */     
/* 4856 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4858 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 4859 */       this.wasNullFlag = true;
/*      */       
/* 4861 */       return null;
/*      */     } 
/*      */     
/* 4864 */     this.wasNullFlag = false;
/*      */ 
/*      */     
/* 4867 */     Field field = this.fields[columnIndexMinusOne];
/*      */     
/* 4869 */     switch (field.getSQLType()) {
/*      */       case -7:
/*      */       case 16:
/* 4872 */         if (field.getMysqlType() == 16 && !field.isSingleBit())
/*      */         {
/* 4874 */           return getBytes(columnIndex);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4880 */         return Boolean.valueOf(getBoolean(columnIndex));
/*      */       
/*      */       case -6:
/* 4883 */         if (!field.isUnsigned()) {
/* 4884 */           return Constants.integerValueOf(getByte(columnIndex));
/*      */         }
/*      */         
/* 4887 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */ 
/*      */       
/*      */       case 5:
/* 4891 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */ 
/*      */       
/*      */       case 4:
/* 4895 */         if (!field.isUnsigned() || field.getMysqlType() == 9)
/*      */         {
/* 4897 */           return Constants.integerValueOf(getInt(columnIndex));
/*      */         }
/*      */         
/* 4900 */         return Constants.longValueOf(getLong(columnIndex));
/*      */ 
/*      */       
/*      */       case -5:
/* 4904 */         if (!field.isUnsigned()) {
/* 4905 */           return Constants.longValueOf(getLong(columnIndex));
/*      */         }
/*      */         
/* 4908 */         stringVal = getString(columnIndex);
/*      */         
/* 4910 */         if (stringVal == null) {
/* 4911 */           return null;
/*      */         }
/*      */         
/*      */         try {
/* 4915 */           return new BigInteger(stringVal);
/* 4916 */         } catch (NumberFormatException nfe) {
/* 4917 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigInteger", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 3:
/* 4925 */         stringVal = getString(columnIndex);
/*      */ 
/*      */ 
/*      */         
/* 4929 */         if (stringVal != null) {
/* 4930 */           if (stringVal.length() == 0) {
/* 4931 */             return new BigDecimal(false);
/*      */           }
/*      */           
/*      */           try {
/*      */             BigDecimal val;
/*      */             
/* 4937 */             val = new BigDecimal(stringVal);
/* 4938 */           } catch (NumberFormatException ex) {
/* 4939 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4946 */           return val;
/*      */         } 
/*      */         
/* 4949 */         return null;
/*      */       
/*      */       case 7:
/* 4952 */         return new Float(getFloat(columnIndex));
/*      */       
/*      */       case 6:
/*      */       case 8:
/* 4956 */         return new Double(getDouble(columnIndex));
/*      */       
/*      */       case 1:
/*      */       case 12:
/* 4960 */         if (!field.isOpaqueBinary()) {
/* 4961 */           return getString(columnIndex);
/*      */         }
/*      */         
/* 4964 */         return getBytes(columnIndex);
/*      */       case -1:
/* 4966 */         if (!field.isOpaqueBinary()) {
/* 4967 */           return getStringForClob(columnIndex);
/*      */         }
/*      */         
/* 4970 */         return getBytes(columnIndex);
/*      */       
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 4975 */         if (field.getMysqlType() == 255)
/* 4976 */           return getBytes(columnIndex); 
/* 4977 */         if (field.isBinary() || field.isBlob()) {
/* 4978 */           byte[] data = getBytes(columnIndex);
/*      */           
/* 4980 */           if (this.connection.getAutoDeserialize()) {
/* 4981 */             byte[] arrayOfByte = data;
/*      */             
/* 4983 */             if (data != null && data.length >= 2) {
/* 4984 */               if (data[0] == -84 && data[1] == -19) {
/*      */                 
/*      */                 try {
/* 4987 */                   ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
/*      */                   
/* 4989 */                   ObjectInputStream objIn = new ObjectInputStream(bytesIn);
/*      */                   
/* 4991 */                   Object object = objIn.readObject();
/* 4992 */                   objIn.close();
/* 4993 */                   bytesIn.close();
/* 4994 */                 } catch (ClassNotFoundException cnfe) {
/* 4995 */                   throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/* 5001 */                 catch (IOException ex) {
/* 5002 */                   arrayOfByte = data;
/*      */                 } 
/*      */               } else {
/* 5005 */                 return getString(columnIndex);
/*      */               } 
/*      */             }
/*      */             
/* 5009 */             return arrayOfByte;
/*      */           } 
/*      */           
/* 5012 */           return data;
/*      */         } 
/*      */         
/* 5015 */         return getBytes(columnIndex);
/*      */       
/*      */       case 91:
/* 5018 */         if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType())
/*      */         {
/* 5020 */           return Constants.shortValueOf(getShort(columnIndex));
/*      */         }
/*      */         
/* 5023 */         return getDate(columnIndex);
/*      */       
/*      */       case 92:
/* 5026 */         return getTime(columnIndex);
/*      */       
/*      */       case 93:
/* 5029 */         return getTimestamp(columnIndex);
/*      */     } 
/*      */     
/* 5032 */     return getString(columnIndex);
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
/* 5052 */   public Object getObject(int i, Map map) throws SQLException { return getObject(i); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5079 */   public Object getObject(String columnName) throws SQLException { return getObject(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5099 */   public Object getObject(String colName, Map map) throws SQLException { return getObject(findColumn(colName), map); }
/*      */ 
/*      */   
/*      */   public Object getObjectStoredProc(int columnIndex, int desiredSqlType) throws SQLException {
/*      */     String stringVal;
/* 5104 */     checkRowPos();
/* 5105 */     checkColumnBounds(columnIndex);
/*      */     
/* 5107 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 5109 */     if (arrayOfByte == null) {
/* 5110 */       this.wasNullFlag = true;
/*      */       
/* 5112 */       return null;
/*      */     } 
/*      */     
/* 5115 */     this.wasNullFlag = false;
/*      */ 
/*      */     
/* 5118 */     Field field = this.fields[columnIndex - 1];
/*      */     
/* 5120 */     switch (desiredSqlType) {
/*      */ 
/*      */ 
/*      */       
/*      */       case -7:
/*      */       case 16:
/* 5126 */         return Boolean.valueOf(getBoolean(columnIndex));
/*      */       
/*      */       case -6:
/* 5129 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */       
/*      */       case 5:
/* 5132 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */ 
/*      */       
/*      */       case 4:
/* 5136 */         if (!field.isUnsigned() || field.getMysqlType() == 9)
/*      */         {
/* 5138 */           return Constants.integerValueOf(getInt(columnIndex));
/*      */         }
/*      */         
/* 5141 */         return Constants.longValueOf(getLong(columnIndex));
/*      */ 
/*      */       
/*      */       case -5:
/* 5145 */         if (field.isUnsigned()) {
/* 5146 */           return getBigDecimal(columnIndex);
/*      */         }
/*      */         
/* 5149 */         return Constants.longValueOf(getLong(columnIndex));
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 3:
/* 5154 */         stringVal = getString(columnIndex);
/*      */ 
/*      */         
/* 5157 */         if (stringVal != null) {
/* 5158 */           if (stringVal.length() == 0) {
/* 5159 */             return new BigDecimal(false);
/*      */           }
/*      */           
/*      */           try {
/*      */             BigDecimal val;
/*      */             
/* 5165 */             val = new BigDecimal(stringVal);
/* 5166 */           } catch (NumberFormatException ex) {
/* 5167 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 5174 */           return val;
/*      */         } 
/*      */         
/* 5177 */         return null;
/*      */       
/*      */       case 7:
/* 5180 */         return new Float(getFloat(columnIndex));
/*      */ 
/*      */       
/*      */       case 6:
/* 5184 */         if (!this.connection.getRunningCTS13()) {
/* 5185 */           return new Double(getFloat(columnIndex));
/*      */         }
/* 5187 */         return new Float(getFloat(columnIndex));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 5194 */         return new Double(getDouble(columnIndex));
/*      */       
/*      */       case 1:
/*      */       case 12:
/* 5198 */         return getString(columnIndex);
/*      */       case -1:
/* 5200 */         return getStringForClob(columnIndex);
/*      */       case -4:
/*      */       case -3:
/*      */       case -2:
/* 5204 */         return getBytes(columnIndex);
/*      */       
/*      */       case 91:
/* 5207 */         if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType())
/*      */         {
/* 5209 */           return Constants.shortValueOf(getShort(columnIndex));
/*      */         }
/*      */         
/* 5212 */         return getDate(columnIndex);
/*      */       
/*      */       case 92:
/* 5215 */         return getTime(columnIndex);
/*      */       
/*      */       case 93:
/* 5218 */         return getTimestamp(columnIndex);
/*      */     } 
/*      */     
/* 5221 */     return getString(columnIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5227 */   public Object getObjectStoredProc(int i, Map map, int desiredSqlType) throws SQLException { return getObjectStoredProc(i, desiredSqlType); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5232 */   public Object getObjectStoredProc(String columnName, int desiredSqlType) throws SQLException { return getObjectStoredProc(findColumn(columnName), desiredSqlType); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5237 */   public Object getObjectStoredProc(String colName, Map map, int desiredSqlType) throws SQLException { return getObjectStoredProc(findColumn(colName), map, desiredSqlType); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Ref getRef(int i) throws SQLException {
/* 5254 */     checkColumnBounds(i);
/* 5255 */     throw SQLError.notImplemented();
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
/* 5272 */   public Ref getRef(String colName) throws SQLException { return getRef(findColumn(colName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRow() throws SQLException {
/* 5289 */     checkClosed();
/*      */     
/* 5291 */     int currentRowNumber = this.rowData.getCurrentRowNumber();
/* 5292 */     int row = 0;
/*      */ 
/*      */ 
/*      */     
/* 5296 */     if (!this.rowData.isDynamic()) {
/* 5297 */       if (currentRowNumber < 0 || this.rowData.isAfterLast() || this.rowData.isEmpty()) {
/*      */         
/* 5299 */         row = 0;
/*      */       } else {
/* 5301 */         row = currentRowNumber + 1;
/*      */       } 
/*      */     } else {
/*      */       
/* 5305 */       row = currentRowNumber + 1;
/*      */     } 
/*      */     
/* 5308 */     return row;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5317 */   public String getServerInfo() throws SQLException { return this.serverInfo; }
/*      */ 
/*      */ 
/*      */   
/*      */   private long getNumericRepresentationOfSQLBitType(int columnIndex) throws SQLException {
/* 5322 */     byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 5324 */     if (this.fields[columnIndex - 1].isSingleBit() || (byte[])arrayOfByte.length == 1)
/*      */     {
/* 5326 */       return (byte[])arrayOfByte[0];
/*      */     }
/*      */ 
/*      */     
/* 5330 */     byte[] asBytes = (byte[])arrayOfByte;
/*      */ 
/*      */     
/* 5333 */     int shift = 0;
/*      */     
/* 5335 */     long[] steps = new long[asBytes.length];
/*      */     
/* 5337 */     for (i = asBytes.length - 1; i >= 0; i--) {
/* 5338 */       steps[i] = (asBytes[i] & 0xFF) << shift;
/* 5339 */       shift += 8;
/*      */     } 
/*      */     
/* 5342 */     long valueAsLong = 0L;
/*      */     
/* 5344 */     for (int i = 0; i < asBytes.length; i++) {
/* 5345 */       valueAsLong |= steps[i];
/*      */     }
/*      */     
/* 5348 */     return valueAsLong;
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
/*      */   public short getShort(int columnIndex) throws SQLException {
/* 5363 */     if (!this.isBinaryEncoded) {
/* 5364 */       checkRowPos();
/*      */       
/* 5366 */       if (this.useFastIntParsing) {
/*      */         
/* 5368 */         checkColumnBounds(columnIndex);
/*      */         
/* 5370 */         byte[] arrayOfByte = this.thisRow.getColumnValue(columnIndex - 1);
/*      */         
/* 5372 */         if (arrayOfByte == null) {
/* 5373 */           this.wasNullFlag = true;
/*      */         } else {
/* 5375 */           this.wasNullFlag = false;
/*      */         } 
/*      */         
/* 5378 */         if (this.wasNullFlag) {
/* 5379 */           return 0;
/*      */         }
/*      */         
/* 5382 */         byte[] shortAsBytes = (byte[])arrayOfByte;
/*      */         
/* 5384 */         if (shortAsBytes.length == 0) {
/* 5385 */           return (short)convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 5388 */         boolean needsFullParse = false;
/*      */         
/* 5390 */         for (i = 0; i < shortAsBytes.length; i++) {
/* 5391 */           if ((char)shortAsBytes[i] == 'e' || (char)shortAsBytes[i] == 'E') {
/*      */             
/* 5393 */             needsFullParse = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/* 5399 */         if (!needsFullParse) {
/*      */           try {
/* 5401 */             return parseShortWithOverflowCheck(columnIndex, shortAsBytes, null);
/*      */           }
/* 5403 */           catch (NumberFormatException i) {
/*      */             NumberFormatException nfe;
/*      */             try {
/* 5406 */               return parseShortAsDouble(columnIndex, new String(shortAsBytes));
/*      */             }
/* 5408 */             catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */               
/* 5412 */               if (this.fields[columnIndex - 1].getMysqlType() == 16) {
/* 5413 */                 long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */                 
/* 5415 */                 if (this.jdbcCompliantTruncationForReads && (valueAsLong < -32768L || valueAsLong > 32767L))
/*      */                 {
/*      */                   
/* 5418 */                   throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
/*      */                 }
/*      */ 
/*      */                 
/* 5422 */                 return (short)(int)valueAsLong;
/*      */               } 
/*      */               
/* 5425 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + new String(shortAsBytes) + "'", "S1009", getExceptionInterceptor());
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5435 */       String val = null;
/*      */       
/*      */       try {
/* 5438 */         val = getString(columnIndex);
/*      */         
/* 5440 */         if (val != null) {
/*      */           
/* 5442 */           if (val.length() == 0) {
/* 5443 */             return (short)convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 5446 */           if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1)
/*      */           {
/* 5448 */             return parseShortWithOverflowCheck(columnIndex, null, val);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 5453 */           return parseShortAsDouble(columnIndex, val);
/*      */         } 
/*      */         
/* 5456 */         return 0;
/* 5457 */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 5459 */           return parseShortAsDouble(columnIndex, val);
/* 5460 */         } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */           
/* 5464 */           if (this.fields[columnIndex - 1].getMysqlType() == 16) {
/* 5465 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 5467 */             if (this.jdbcCompliantTruncationForReads && (valueAsLong < -32768L || valueAsLong > 32767L))
/*      */             {
/*      */               
/* 5470 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
/*      */             }
/*      */ 
/*      */             
/* 5474 */             return (short)(int)valueAsLong;
/*      */           } 
/*      */           
/* 5477 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + val + "'", "S1009", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5485 */     return getNativeShort(columnIndex);
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
/* 5500 */   public short getShort(String columnName) throws SQLException { return getShort(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/*      */   private final short getShortFromString(String val, int columnIndex) throws SQLException {
/*      */     try {
/* 5506 */       if (val != null) {
/*      */         
/* 5508 */         if (val.length() == 0) {
/* 5509 */           return (short)convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 5512 */         if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1)
/*      */         {
/* 5514 */           return parseShortWithOverflowCheck(columnIndex, null, val);
/*      */         }
/*      */ 
/*      */         
/* 5518 */         return parseShortAsDouble(columnIndex, val);
/*      */       } 
/*      */       
/* 5521 */       return 0;
/* 5522 */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 5524 */         return parseShortAsDouble(columnIndex, val);
/* 5525 */       } catch (NumberFormatException newNfe) {
/*      */ 
/*      */ 
/*      */         
/* 5529 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____217") + val + Messages.getString("ResultSet.___in_column__218") + columnIndex, "S1009", getExceptionInterceptor());
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
/*      */   public Statement getStatement() throws SQLException {
/* 5548 */     if (this.isClosed && !this.retainOwningStatement) {
/* 5549 */       throw SQLError.createSQLException("Operation not allowed on closed ResultSet. Statements can be retained over result set closure by setting the connection property \"retainStatementAfterResultSetClose\" to \"true\".", "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5557 */     if (this.wrapperStatement != null) {
/* 5558 */       return this.wrapperStatement;
/*      */     }
/*      */     
/* 5561 */     return this.owningStatement;
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
/*      */   public String getString(int columnIndex) throws SQLException {
/* 5576 */     String stringVal = getStringInternal(columnIndex, true);
/*      */     
/* 5578 */     if (this.padCharsWithSpace && stringVal != null) {
/* 5579 */       Field f = this.fields[columnIndex - 1];
/*      */       
/* 5581 */       if (f.getMysqlType() == 254) {
/* 5582 */         int fieldLength = (int)f.getLength() / f.getMaxBytesPerCharacter();
/*      */ 
/*      */         
/* 5585 */         int currentLength = stringVal.length();
/*      */         
/* 5587 */         if (currentLength < fieldLength) {
/* 5588 */           StringBuffer paddedBuf = new StringBuffer(fieldLength);
/* 5589 */           paddedBuf.append(stringVal);
/*      */           
/* 5591 */           int difference = fieldLength - currentLength;
/*      */           
/* 5593 */           paddedBuf.append(EMPTY_SPACE, 0, difference);
/*      */           
/* 5595 */           stringVal = paddedBuf.toString();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5600 */     return stringVal;
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
/* 5616 */   public String getString(String columnName) throws SQLException { return getString(findColumn(columnName)); }
/*      */ 
/*      */   
/*      */   private String getStringForClob(int columnIndex) throws SQLException {
/* 5620 */     String asString = null;
/*      */     
/* 5622 */     String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */ 
/*      */     
/* 5625 */     if (forcedEncoding == null) {
/* 5626 */       if (!this.isBinaryEncoded) {
/* 5627 */         asString = getString(columnIndex);
/*      */       } else {
/* 5629 */         asString = getNativeString(columnIndex);
/*      */       } 
/*      */     } else {
/*      */       try {
/* 5633 */         byte[] asBytes = null;
/*      */         
/* 5635 */         if (!this.isBinaryEncoded) {
/* 5636 */           asBytes = getBytes(columnIndex);
/*      */         } else {
/* 5638 */           asBytes = getNativeBytes(columnIndex, true);
/*      */         } 
/*      */         
/* 5641 */         if (asBytes != null) {
/* 5642 */           asString = new String(asBytes, forcedEncoding);
/*      */         }
/* 5644 */       } catch (UnsupportedEncodingException uee) {
/* 5645 */         throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5650 */     return asString;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getStringInternal(int columnIndex, boolean checkDateTypes) throws SQLException {
/* 5655 */     if (!this.isBinaryEncoded) {
/* 5656 */       checkRowPos();
/* 5657 */       checkColumnBounds(columnIndex);
/*      */       
/* 5659 */       if (this.fields == null) {
/* 5660 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_99"), "S1002", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5668 */       int internalColumnIndex = columnIndex - 1;
/*      */       
/* 5670 */       if (this.thisRow.isNull(internalColumnIndex)) {
/* 5671 */         this.wasNullFlag = true;
/*      */         
/* 5673 */         return null;
/*      */       } 
/*      */       
/* 5676 */       this.wasNullFlag = false;
/*      */ 
/*      */       
/* 5679 */       Field metadata = this.fields[internalColumnIndex];
/*      */       
/* 5681 */       String stringVal = null;
/*      */       
/* 5683 */       if (metadata.getMysqlType() == 16) {
/* 5684 */         if (metadata.isSingleBit()) {
/* 5685 */           byte[] value = this.thisRow.getColumnValue(internalColumnIndex);
/*      */           
/* 5687 */           if (value.length == 0) {
/* 5688 */             return String.valueOf(convertToZeroWithEmptyCheck());
/*      */           }
/*      */           
/* 5691 */           return String.valueOf(value[0]);
/*      */         } 
/*      */         
/* 5694 */         return String.valueOf(getNumericRepresentationOfSQLBitType(columnIndex));
/*      */       } 
/*      */       
/* 5697 */       String encoding = metadata.getCharacterSet();
/*      */       
/* 5699 */       stringVal = this.thisRow.getString(internalColumnIndex, encoding, this.connection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5706 */       if (metadata.getMysqlType() == 13) {
/* 5707 */         if (!this.connection.getYearIsDateType()) {
/* 5708 */           return stringVal;
/*      */         }
/*      */         
/* 5711 */         Date dt = getDateFromString(stringVal, columnIndex, null);
/*      */         
/* 5713 */         if (dt == null) {
/* 5714 */           this.wasNullFlag = true;
/*      */           
/* 5716 */           return null;
/*      */         } 
/*      */         
/* 5719 */         this.wasNullFlag = false;
/*      */         
/* 5721 */         return dt.toString();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 5726 */       if (checkDateTypes && !this.connection.getNoDatetimeStringSync()) {
/* 5727 */         Timestamp ts; Date dt; Time tm; switch (metadata.getSQLType()) {
/*      */           case 92:
/* 5729 */             tm = getTimeFromString(stringVal, null, columnIndex, getDefaultTimeZone(), false);
/*      */ 
/*      */             
/* 5732 */             if (tm == null) {
/* 5733 */               this.wasNullFlag = true;
/*      */               
/* 5735 */               return null;
/*      */             } 
/*      */             
/* 5738 */             this.wasNullFlag = false;
/*      */             
/* 5740 */             return tm.toString();
/*      */           
/*      */           case 91:
/* 5743 */             dt = getDateFromString(stringVal, columnIndex, null);
/*      */             
/* 5745 */             if (dt == null) {
/* 5746 */               this.wasNullFlag = true;
/*      */               
/* 5748 */               return null;
/*      */             } 
/*      */             
/* 5751 */             this.wasNullFlag = false;
/*      */             
/* 5753 */             return dt.toString();
/*      */           case 93:
/* 5755 */             ts = getTimestampFromString(columnIndex, null, stringVal, getDefaultTimeZone(), false);
/*      */ 
/*      */             
/* 5758 */             if (ts == null) {
/* 5759 */               this.wasNullFlag = true;
/*      */               
/* 5761 */               return null;
/*      */             } 
/*      */             
/* 5764 */             this.wasNullFlag = false;
/*      */             
/* 5766 */             return ts.toString();
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 5772 */       return stringVal;
/*      */     } 
/*      */     
/* 5775 */     return getNativeString(columnIndex);
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
/* 5790 */   public Time getTime(int columnIndex) throws SQLException { return getTimeInternal(columnIndex, null, getDefaultTimeZone(), false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5810 */   public Time getTime(int columnIndex, Calendar cal) throws SQLException { return getTimeInternal(columnIndex, cal, cal.getTimeZone(), true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5825 */   public Time getTime(String columnName) throws SQLException { return getTime(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5845 */   public Time getTime(String columnName, Calendar cal) throws SQLException { return getTime(findColumn(columnName), cal); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Time getTimeFromString(String timeAsString, Calendar targetCalendar, int columnIndex, TimeZone tz, boolean rollForward) throws SQLException {
/* 5852 */     int hr = 0;
/* 5853 */     int min = 0;
/* 5854 */     int sec = 0;
/*      */ 
/*      */     
/*      */     try {
/* 5858 */       if (timeAsString == null) {
/* 5859 */         this.wasNullFlag = true;
/*      */         
/* 5861 */         return null;
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
/* 5872 */       timeAsString = timeAsString.trim();
/*      */       
/* 5874 */       if (timeAsString.equals("0") || timeAsString.equals("0000-00-00") || timeAsString.equals("0000-00-00 00:00:00") || timeAsString.equals("00000000000000")) {
/*      */ 
/*      */ 
/*      */         
/* 5878 */         if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */           
/* 5880 */           this.wasNullFlag = true;
/*      */           
/* 5882 */           return null;
/* 5883 */         }  if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */         {
/* 5885 */           throw SQLError.createSQLException("Value '" + timeAsString + "' can not be represented as java.sql.Time", "S1009", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5892 */         return fastTimeCreate(targetCalendar, 0, 0, 0);
/*      */       } 
/*      */       
/* 5895 */       this.wasNullFlag = false;
/*      */       
/* 5897 */       Field timeColField = this.fields[columnIndex - 1];
/*      */       
/* 5899 */       if (timeColField.getMysqlType() == 7)
/*      */       
/* 5901 */       { int length = timeAsString.length();
/*      */         
/* 5903 */         switch (length) {
/*      */           
/*      */           case 19:
/* 5906 */             hr = Integer.parseInt(timeAsString.substring(length - 8, length - 6));
/*      */             
/* 5908 */             min = Integer.parseInt(timeAsString.substring(length - 5, length - 3));
/*      */             
/* 5910 */             sec = Integer.parseInt(timeAsString.substring(length - 2, length));
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/*      */           case 14:
/* 5917 */             hr = Integer.parseInt(timeAsString.substring(length - 6, length - 4));
/*      */             
/* 5919 */             min = Integer.parseInt(timeAsString.substring(length - 4, length - 2));
/*      */             
/* 5921 */             sec = Integer.parseInt(timeAsString.substring(length - 2, length));
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/* 5928 */             hr = Integer.parseInt(timeAsString.substring(6, 8));
/* 5929 */             min = Integer.parseInt(timeAsString.substring(8, 10));
/* 5930 */             sec = 0;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 5936 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Timestamp_too_small_to_convert_to_Time_value_in_column__257") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009", getExceptionInterceptor());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5945 */         SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_TIMESTAMP_to_Time_with_getTime()_on_column__261") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5952 */         if (this.warningChain == null) {
/* 5953 */           this.warningChain = precisionLost;
/*      */         } else {
/* 5955 */           this.warningChain.setNextWarning(precisionLost);
/*      */         }  }
/* 5957 */       else if (timeColField.getMysqlType() == 12)
/* 5958 */       { hr = Integer.parseInt(timeAsString.substring(11, 13));
/* 5959 */         min = Integer.parseInt(timeAsString.substring(14, 16));
/* 5960 */         sec = Integer.parseInt(timeAsString.substring(17, 19));
/*      */         
/* 5962 */         SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_DATETIME_to_Time_with_getTime()_on_column__264") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5969 */         if (this.warningChain == null) {
/* 5970 */           this.warningChain = precisionLost;
/*      */         } else {
/* 5972 */           this.warningChain.setNextWarning(precisionLost);
/*      */         }  }
/* 5974 */       else { if (timeColField.getMysqlType() == 10) {
/* 5975 */           return fastTimeCreate(targetCalendar, 0, 0, 0);
/*      */         }
/*      */ 
/*      */         
/* 5979 */         if (timeAsString.length() != 5 && timeAsString.length() != 8)
/*      */         {
/* 5981 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Time____267") + timeAsString + Messages.getString("ResultSet.___in_column__268") + columnIndex, "S1009", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5988 */         hr = Integer.parseInt(timeAsString.substring(0, 2));
/* 5989 */         min = Integer.parseInt(timeAsString.substring(3, 5));
/* 5990 */         sec = (timeAsString.length() == 5) ? 0 : Integer.parseInt(timeAsString.substring(6)); }
/*      */ 
/*      */ 
/*      */       
/* 5994 */       Calendar sessionCalendar = getCalendarInstanceForSessionOrNew();
/*      */       
/* 5996 */       synchronized (sessionCalendar) {
/* 5997 */         return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, fastTimeCreate(sessionCalendar, hr, min, sec), this.connection.getServerTimezoneTZ(), tz, rollForward);
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 6005 */     catch (Exception ex) {
/* 6006 */       SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1009", getExceptionInterceptor());
/*      */       
/* 6008 */       sqlEx.initCause(ex);
/*      */       
/* 6010 */       throw sqlEx;
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
/*      */   private Time getTimeInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 6031 */     checkRowPos();
/*      */     
/* 6033 */     if (this.isBinaryEncoded) {
/* 6034 */       return getNativeTime(columnIndex, targetCalendar, tz, rollForward);
/*      */     }
/*      */     
/* 6037 */     if (!this.useFastDateParsing) {
/* 6038 */       String timeAsString = getStringInternal(columnIndex, false);
/*      */       
/* 6040 */       return getTimeFromString(timeAsString, targetCalendar, columnIndex, tz, rollForward);
/*      */     } 
/*      */ 
/*      */     
/* 6044 */     checkColumnBounds(columnIndex);
/*      */     
/* 6046 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 6048 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 6049 */       this.wasNullFlag = true;
/*      */       
/* 6051 */       return null;
/*      */     } 
/*      */     
/* 6054 */     this.wasNullFlag = false;
/*      */     
/* 6056 */     return this.thisRow.getTimeFast(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
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
/* 6073 */   public Timestamp getTimestamp(int columnIndex) throws SQLException { return getTimestampInternal(columnIndex, null, getDefaultTimeZone(), false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 6095 */   public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException { return getTimestampInternal(columnIndex, cal, cal.getTimeZone(), true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 6111 */   public Timestamp getTimestamp(String columnName) throws SQLException { return getTimestamp(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 6132 */   public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException { return getTimestamp(findColumn(columnName), cal); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Timestamp getTimestampFromString(int columnIndex, Calendar targetCalendar, String timestampValue, TimeZone tz, boolean rollForward) throws SQLException {
/*      */     try {
/* 6140 */       this.wasNullFlag = false;
/*      */       
/* 6142 */       if (timestampValue == null) {
/* 6143 */         this.wasNullFlag = true;
/*      */         
/* 6145 */         return null;
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
/* 6156 */       timestampValue = timestampValue.trim();
/*      */       
/* 6158 */       int length = timestampValue.length();
/*      */       
/* 6160 */       Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */       
/* 6164 */       synchronized (sessionCalendar) {
/* 6165 */         if (length > 0 && timestampValue.charAt(0) == '0' && (timestampValue.equals("0000-00-00") || timestampValue.equals("0000-00-00 00:00:00") || timestampValue.equals("00000000000000") || timestampValue.equals("0"))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6172 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */             
/* 6174 */             this.wasNullFlag = true;
/*      */             
/* 6176 */             return null;
/* 6177 */           }  if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           {
/* 6179 */             throw SQLError.createSQLException("Value '" + timestampValue + "' can not be represented as java.sql.Timestamp", "S1009", getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6186 */           return fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
/*      */         } 
/* 6188 */         if (this.fields[columnIndex - 1].getMysqlType() == 13) {
/*      */           
/* 6190 */           if (!this.useLegacyDatetimeCode) {
/* 6191 */             return TimeUtil.fastTimestampCreate(tz, Integer.parseInt(timestampValue.substring(0, 4)), 1, 1, 0, 0, 0, 0);
/*      */           }
/*      */ 
/*      */           
/* 6195 */           return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, fastTimestampCreate(sessionCalendar, Integer.parseInt(timestampValue.substring(0, 4)), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6205 */         if (timestampValue.endsWith(".")) {
/* 6206 */           timestampValue = timestampValue.substring(0, timestampValue.length() - 1);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6212 */         int year = 0;
/* 6213 */         int month = 0;
/* 6214 */         int day = 0;
/* 6215 */         int hour = 0;
/* 6216 */         int minutes = 0;
/* 6217 */         int seconds = 0;
/* 6218 */         int nanos = 0;
/*      */         
/* 6220 */         switch (length) {
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/* 6229 */             year = Integer.parseInt(timestampValue.substring(0, 4));
/* 6230 */             month = Integer.parseInt(timestampValue.substring(5, 7));
/*      */             
/* 6232 */             day = Integer.parseInt(timestampValue.substring(8, 10));
/* 6233 */             hour = Integer.parseInt(timestampValue.substring(11, 13));
/*      */             
/* 6235 */             minutes = Integer.parseInt(timestampValue.substring(14, 16));
/*      */             
/* 6237 */             seconds = Integer.parseInt(timestampValue.substring(17, 19));
/*      */ 
/*      */             
/* 6240 */             nanos = 0;
/*      */             
/* 6242 */             if (length > 19) {
/* 6243 */               int decimalIndex = timestampValue.lastIndexOf('.');
/*      */               
/* 6245 */               if (decimalIndex != -1) {
/* 6246 */                 if (decimalIndex + 2 <= length) {
/* 6247 */                   nanos = Integer.parseInt(timestampValue.substring(decimalIndex + 1));
/*      */ 
/*      */                   
/* 6250 */                   int numDigits = length - decimalIndex + 1;
/*      */                   
/* 6252 */                   if (numDigits < 9) {
/* 6253 */                     int factor = (int)Math.pow(10.0D, (9 - numDigits));
/* 6254 */                     nanos *= factor;
/*      */                   }  break;
/*      */                 } 
/* 6257 */                 throw new IllegalArgumentException();
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
/* 6271 */             year = Integer.parseInt(timestampValue.substring(0, 4));
/* 6272 */             month = Integer.parseInt(timestampValue.substring(4, 6));
/*      */             
/* 6274 */             day = Integer.parseInt(timestampValue.substring(6, 8));
/* 6275 */             hour = Integer.parseInt(timestampValue.substring(8, 10));
/*      */             
/* 6277 */             minutes = Integer.parseInt(timestampValue.substring(10, 12));
/*      */             
/* 6279 */             seconds = Integer.parseInt(timestampValue.substring(12, 14));
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/* 6286 */             year = Integer.parseInt(timestampValue.substring(0, 2));
/*      */             
/* 6288 */             if (year <= 69) {
/* 6289 */               year += 100;
/*      */             }
/*      */             
/* 6292 */             year += 1900;
/*      */             
/* 6294 */             month = Integer.parseInt(timestampValue.substring(2, 4));
/*      */             
/* 6296 */             day = Integer.parseInt(timestampValue.substring(4, 6));
/* 6297 */             hour = Integer.parseInt(timestampValue.substring(6, 8));
/* 6298 */             minutes = Integer.parseInt(timestampValue.substring(8, 10));
/*      */             
/* 6300 */             seconds = Integer.parseInt(timestampValue.substring(10, 12));
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/* 6307 */             if (this.fields[columnIndex - 1].getMysqlType() == 10 || timestampValue.indexOf("-") != -1) {
/*      */               
/* 6309 */               year = Integer.parseInt(timestampValue.substring(0, 4));
/* 6310 */               month = Integer.parseInt(timestampValue.substring(5, 7));
/*      */               
/* 6312 */               day = Integer.parseInt(timestampValue.substring(8, 10));
/* 6313 */               hour = 0;
/* 6314 */               minutes = 0; break;
/*      */             } 
/* 6316 */             year = Integer.parseInt(timestampValue.substring(0, 2));
/*      */             
/* 6318 */             if (year <= 69) {
/* 6319 */               year += 100;
/*      */             }
/*      */             
/* 6322 */             month = Integer.parseInt(timestampValue.substring(2, 4));
/*      */             
/* 6324 */             day = Integer.parseInt(timestampValue.substring(4, 6));
/* 6325 */             hour = Integer.parseInt(timestampValue.substring(6, 8));
/* 6326 */             minutes = Integer.parseInt(timestampValue.substring(8, 10));
/*      */ 
/*      */             
/* 6329 */             year += 1900;
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 8:
/* 6336 */             if (timestampValue.indexOf(":") != -1) {
/* 6337 */               hour = Integer.parseInt(timestampValue.substring(0, 2));
/*      */               
/* 6339 */               minutes = Integer.parseInt(timestampValue.substring(3, 5));
/*      */               
/* 6341 */               seconds = Integer.parseInt(timestampValue.substring(6, 8));
/*      */               
/* 6343 */               year = 1970;
/* 6344 */               month = 1;
/* 6345 */               day = 1;
/*      */               
/*      */               break;
/*      */             } 
/* 6349 */             year = Integer.parseInt(timestampValue.substring(0, 4));
/* 6350 */             month = Integer.parseInt(timestampValue.substring(4, 6));
/*      */             
/* 6352 */             day = Integer.parseInt(timestampValue.substring(6, 8));
/*      */             
/* 6354 */             year -= 1900;
/* 6355 */             month--;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 6:
/* 6361 */             year = Integer.parseInt(timestampValue.substring(0, 2));
/*      */             
/* 6363 */             if (year <= 69) {
/* 6364 */               year += 100;
/*      */             }
/*      */             
/* 6367 */             year += 1900;
/*      */             
/* 6369 */             month = Integer.parseInt(timestampValue.substring(2, 4));
/*      */             
/* 6371 */             day = Integer.parseInt(timestampValue.substring(4, 6));
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/* 6377 */             year = Integer.parseInt(timestampValue.substring(0, 2));
/*      */             
/* 6379 */             if (year <= 69) {
/* 6380 */               year += 100;
/*      */             }
/*      */             
/* 6383 */             year += 1900;
/*      */             
/* 6385 */             month = Integer.parseInt(timestampValue.substring(2, 4));
/*      */ 
/*      */             
/* 6388 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 6394 */             year = Integer.parseInt(timestampValue.substring(0, 2));
/*      */             
/* 6396 */             if (year <= 69) {
/* 6397 */               year += 100;
/*      */             }
/*      */             
/* 6400 */             year += 1900;
/* 6401 */             month = 1;
/* 6402 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 6408 */             throw new SQLException("Bad format for Timestamp '" + timestampValue + "' in column " + columnIndex + ".", "S1009");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6414 */         if (!this.useLegacyDatetimeCode) {
/* 6415 */           return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minutes, seconds, nanos);
/*      */         }
/*      */ 
/*      */         
/* 6419 */         return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 6427 */     catch (Exception e) {
/* 6428 */       SQLException sqlEx = SQLError.createSQLException("Cannot convert value '" + timestampValue + "' from column " + columnIndex + " to TIMESTAMP.", "S1009", getExceptionInterceptor());
/*      */ 
/*      */       
/* 6431 */       sqlEx.initCause(e);
/*      */       
/* 6433 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Timestamp getTimestampFromBytes(int columnIndex, Calendar targetCalendar, byte[] timestampAsBytes, TimeZone tz, boolean rollForward) throws SQLException {
/* 6442 */     checkColumnBounds(columnIndex);
/*      */     
/*      */     try {
/* 6445 */       this.wasNullFlag = false;
/*      */       
/* 6447 */       if (timestampAsBytes == null) {
/* 6448 */         this.wasNullFlag = true;
/*      */         
/* 6450 */         return null;
/*      */       } 
/*      */       
/* 6453 */       int length = timestampAsBytes.length;
/*      */       
/* 6455 */       Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */ 
/*      */ 
/*      */       
/* 6459 */       synchronized (sessionCalendar) {
/* 6460 */         boolean allZeroTimestamp = true;
/*      */         
/* 6462 */         boolean onlyTimePresent = (StringUtils.indexOf(timestampAsBytes, ':') != -1);
/*      */         
/* 6464 */         for (i = 0; i < length; i++) {
/* 6465 */           byte b = timestampAsBytes[i];
/*      */           
/* 6467 */           if (b == 32 || b == 45 || b == 47) {
/* 6468 */             onlyTimePresent = false;
/*      */           }
/*      */           
/* 6471 */           if (b != 48 && b != 32 && b != 58 && b != 45 && b != 47 && b != 46) {
/*      */             
/* 6473 */             allZeroTimestamp = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/* 6479 */         if (!onlyTimePresent && allZeroTimestamp) {
/*      */           
/* 6481 */           if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
/*      */             
/* 6483 */             this.wasNullFlag = true;
/*      */             
/* 6485 */             return null;
/* 6486 */           }  if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */           {
/* 6488 */             throw SQLError.createSQLException("Value '" + timestampAsBytes + "' can not be represented as java.sql.Timestamp", "S1009", getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6495 */           if (!this.useLegacyDatetimeCode) {
/* 6496 */             return TimeUtil.fastTimestampCreate(tz, 1, 1, 1, 0, 0, 0, 0);
/*      */           }
/*      */ 
/*      */           
/* 6500 */           return fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
/* 6501 */         }  if (this.fields[columnIndex - 1].getMysqlType() == 13) {
/*      */           
/* 6503 */           if (!this.useLegacyDatetimeCode) {
/* 6504 */             return TimeUtil.fastTimestampCreate(tz, StringUtils.getInt(timestampAsBytes, 0, 4), 1, 1, 0, 0, 0, 0);
/*      */           }
/*      */ 
/*      */           
/* 6508 */           return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, fastTimestampCreate(sessionCalendar, StringUtils.getInt(timestampAsBytes, 0, 4), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6516 */         if (timestampAsBytes[length - 1] == 46) {
/* 6517 */           length--;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 6522 */         int year = 0;
/* 6523 */         int month = 0;
/* 6524 */         int day = 0;
/* 6525 */         int hour = 0;
/* 6526 */         int minutes = 0;
/* 6527 */         int seconds = 0;
/* 6528 */         int nanos = 0;
/*      */         
/* 6530 */         switch (length) {
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/* 6539 */             year = StringUtils.getInt(timestampAsBytes, 0, 4);
/* 6540 */             month = StringUtils.getInt(timestampAsBytes, 5, 7);
/* 6541 */             day = StringUtils.getInt(timestampAsBytes, 8, 10);
/* 6542 */             hour = StringUtils.getInt(timestampAsBytes, 11, 13);
/* 6543 */             minutes = StringUtils.getInt(timestampAsBytes, 14, 16);
/* 6544 */             seconds = StringUtils.getInt(timestampAsBytes, 17, 19);
/*      */             
/* 6546 */             nanos = 0;
/*      */             
/* 6548 */             if (length > 19) {
/* 6549 */               int decimalIndex = StringUtils.lastIndexOf(timestampAsBytes, '.');
/*      */               
/* 6551 */               if (decimalIndex != -1) {
/* 6552 */                 if (decimalIndex + 2 <= length) {
/* 6553 */                   nanos = StringUtils.getInt(timestampAsBytes, decimalIndex + 1, length); break;
/*      */                 } 
/* 6555 */                 throw new IllegalArgumentException();
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
/* 6569 */             year = StringUtils.getInt(timestampAsBytes, 0, 4);
/* 6570 */             month = StringUtils.getInt(timestampAsBytes, 4, 6);
/* 6571 */             day = StringUtils.getInt(timestampAsBytes, 6, 8);
/* 6572 */             hour = StringUtils.getInt(timestampAsBytes, 8, 10);
/* 6573 */             minutes = StringUtils.getInt(timestampAsBytes, 10, 12);
/* 6574 */             seconds = StringUtils.getInt(timestampAsBytes, 12, 14);
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 12:
/* 6580 */             year = StringUtils.getInt(timestampAsBytes, 0, 2);
/*      */             
/* 6582 */             if (year <= 69) {
/* 6583 */               year += 100;
/*      */             }
/*      */             
/* 6586 */             year += 1900;
/*      */             
/* 6588 */             month = StringUtils.getInt(timestampAsBytes, 2, 4);
/* 6589 */             day = StringUtils.getInt(timestampAsBytes, 4, 6);
/* 6590 */             hour = StringUtils.getInt(timestampAsBytes, 6, 8);
/* 6591 */             minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
/* 6592 */             seconds = StringUtils.getInt(timestampAsBytes, 10, 12);
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 10:
/* 6598 */             if (this.fields[columnIndex - 1].getMysqlType() == 10 || StringUtils.indexOf(timestampAsBytes, '-') != -1) {
/*      */               
/* 6600 */               year = StringUtils.getInt(timestampAsBytes, 0, 4);
/* 6601 */               month = StringUtils.getInt(timestampAsBytes, 5, 7);
/* 6602 */               day = StringUtils.getInt(timestampAsBytes, 8, 10);
/* 6603 */               hour = 0;
/* 6604 */               minutes = 0; break;
/*      */             } 
/* 6606 */             year = StringUtils.getInt(timestampAsBytes, 0, 2);
/*      */             
/* 6608 */             if (year <= 69) {
/* 6609 */               year += 100;
/*      */             }
/*      */             
/* 6612 */             month = StringUtils.getInt(timestampAsBytes, 2, 4);
/* 6613 */             day = StringUtils.getInt(timestampAsBytes, 4, 6);
/* 6614 */             hour = StringUtils.getInt(timestampAsBytes, 6, 8);
/* 6615 */             minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
/*      */             
/* 6617 */             year += 1900;
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 8:
/* 6624 */             if (StringUtils.indexOf(timestampAsBytes, ':') != -1) {
/* 6625 */               hour = StringUtils.getInt(timestampAsBytes, 0, 2);
/* 6626 */               minutes = StringUtils.getInt(timestampAsBytes, 3, 5);
/* 6627 */               seconds = StringUtils.getInt(timestampAsBytes, 6, 8);
/*      */               
/* 6629 */               year = 1970;
/* 6630 */               month = 1;
/* 6631 */               day = 1;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 6636 */             year = StringUtils.getInt(timestampAsBytes, 0, 4);
/* 6637 */             month = StringUtils.getInt(timestampAsBytes, 4, 6);
/* 6638 */             day = StringUtils.getInt(timestampAsBytes, 6, 8);
/*      */             
/* 6640 */             year -= 1900;
/* 6641 */             month--;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 6:
/* 6647 */             year = StringUtils.getInt(timestampAsBytes, 0, 2);
/*      */             
/* 6649 */             if (year <= 69) {
/* 6650 */               year += 100;
/*      */             }
/*      */             
/* 6653 */             year += 1900;
/*      */             
/* 6655 */             month = StringUtils.getInt(timestampAsBytes, 2, 4);
/* 6656 */             day = StringUtils.getInt(timestampAsBytes, 4, 6);
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 4:
/* 6662 */             year = StringUtils.getInt(timestampAsBytes, 0, 2);
/*      */             
/* 6664 */             if (year <= 69) {
/* 6665 */               year += 100;
/*      */             }
/*      */             
/* 6668 */             year += 1900;
/*      */             
/* 6670 */             month = StringUtils.getInt(timestampAsBytes, 2, 4);
/* 6671 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 6677 */             year = StringUtils.getInt(timestampAsBytes, 0, 2);
/*      */             
/* 6679 */             if (year <= 69) {
/* 6680 */               year += 100;
/*      */             }
/*      */             
/* 6683 */             year += 1900;
/* 6684 */             month = 1;
/* 6685 */             day = 1;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 6691 */             throw new SQLException("Bad format for Timestamp '" + new String(timestampAsBytes) + "' in column " + columnIndex + ".", "S1009");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6697 */         if (!this.useLegacyDatetimeCode) {
/* 6698 */           return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minutes, seconds, nanos);
/*      */         }
/*      */ 
/*      */         
/* 6702 */         return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 6710 */     catch (Exception e) {
/* 6711 */       SQLException sqlEx = SQLError.createSQLException("Cannot convert value '" + new String(timestampAsBytes) + "' from column " + columnIndex + " to TIMESTAMP.", "S1009", getExceptionInterceptor());
/*      */ 
/*      */       
/* 6714 */       sqlEx.initCause(e);
/*      */       
/* 6716 */       throw sqlEx;
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
/*      */   private Timestamp getTimestampInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException {
/* 6737 */     if (this.isBinaryEncoded) {
/* 6738 */       return getNativeTimestamp(columnIndex, targetCalendar, tz, rollForward);
/*      */     }
/*      */     
/* 6741 */     Timestamp tsVal = null;
/*      */     
/* 6743 */     if (!this.useFastDateParsing) {
/* 6744 */       String timestampValue = getStringInternal(columnIndex, false);
/*      */       
/* 6746 */       tsVal = getTimestampFromString(columnIndex, targetCalendar, timestampValue, tz, rollForward);
/*      */     }
/*      */     else {
/*      */       
/* 6750 */       checkClosed();
/* 6751 */       checkRowPos();
/* 6752 */       checkColumnBounds(columnIndex);
/*      */       
/* 6754 */       tsVal = this.thisRow.getTimestampFast(columnIndex - 1, targetCalendar, tz, rollForward, this.connection, this);
/*      */     } 
/*      */ 
/*      */     
/* 6758 */     if (tsVal == null) {
/* 6759 */       this.wasNullFlag = true;
/*      */     } else {
/* 6761 */       this.wasNullFlag = false;
/*      */     } 
/*      */     
/* 6764 */     return tsVal;
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
/* 6778 */   public int getType() throws SQLException { return this.resultSetType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getUnicodeStream(int columnIndex) throws SQLException {
/* 6800 */     if (!this.isBinaryEncoded) {
/* 6801 */       checkRowPos();
/*      */       
/* 6803 */       return getBinaryStream(columnIndex);
/*      */     } 
/*      */     
/* 6806 */     return getNativeBinaryStream(columnIndex);
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
/* 6823 */   public InputStream getUnicodeStream(String columnName) throws SQLException { return getUnicodeStream(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */   
/* 6827 */   public long getUpdateCount() { return this.updateCount; }
/*      */ 
/*      */ 
/*      */   
/* 6831 */   public long getUpdateID() { return this.updateId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(int colIndex) throws SQLException {
/* 6838 */     String val = getString(colIndex);
/*      */     
/* 6840 */     if (val == null) {
/* 6841 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 6845 */       return new URL(val);
/* 6846 */     } catch (MalformedURLException mfe) {
/* 6847 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____104") + val + "'", "S1009", getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getURL(String colName) throws SQLException {
/* 6857 */     String val = getString(colName);
/*      */     
/* 6859 */     if (val == null) {
/* 6860 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 6864 */       return new URL(val);
/* 6865 */     } catch (MalformedURLException mfe) {
/* 6866 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____107") + val + "'", "S1009", getExceptionInterceptor());
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
/* 6893 */   public SQLWarning getWarnings() throws SQLException { return this.warningChain; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 6908 */   public void insertRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfterLast() throws SQLException {
/* 6925 */     checkClosed();
/*      */     
/* 6927 */     return this.rowData.isAfterLast();
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
/*      */   public boolean isBeforeFirst() throws SQLException {
/* 6946 */     checkClosed();
/*      */     
/* 6948 */     return this.rowData.isBeforeFirst();
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
/*      */   public boolean isFirst() throws SQLException {
/* 6964 */     checkClosed();
/*      */     
/* 6966 */     return this.rowData.isFirst();
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
/*      */   public boolean isLast() throws SQLException {
/* 6985 */     checkClosed();
/*      */     
/* 6987 */     return this.rowData.isLast();
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
/*      */   private void issueConversionViaParsingWarning(String methodName, int columnIndex, Object value, Field fieldInfo, int[] typesWithNoParseConversion) throws SQLException {
/* 6999 */     StringBuffer originalQueryBuf = new StringBuffer();
/*      */     
/* 7001 */     if (this.owningStatement != null && this.owningStatement instanceof PreparedStatement) {
/*      */       
/* 7003 */       originalQueryBuf.append(Messages.getString("ResultSet.CostlyConversionCreatedFromQuery"));
/* 7004 */       originalQueryBuf.append(((PreparedStatement)this.owningStatement).originalSql);
/*      */       
/* 7006 */       originalQueryBuf.append("\n\n");
/*      */     } else {
/* 7008 */       originalQueryBuf.append(".");
/*      */     } 
/*      */     
/* 7011 */     StringBuffer convertibleTypesBuf = new StringBuffer();
/*      */     
/* 7013 */     for (int i = 0; i < typesWithNoParseConversion.length; i++) {
/* 7014 */       convertibleTypesBuf.append(MysqlDefs.typeToName(typesWithNoParseConversion[i]));
/* 7015 */       convertibleTypesBuf.append("\n");
/*      */     } 
/*      */     
/* 7018 */     String message = Messages.getString("ResultSet.CostlyConversion", new Object[] { methodName, new Integer(columnIndex + 1), fieldInfo.getOriginalName(), fieldInfo.getOriginalTableName(), originalQueryBuf.toString(), (value != null) ? value.getClass().getName() : ResultSetMetaData.getClassNameForJavaType(fieldInfo.getSQLType(), fieldInfo.isUnsigned(), fieldInfo.getMysqlType(), (fieldInfo.isBinary() || fieldInfo.isBlob()), fieldInfo.isOpaqueBinary()), MysqlDefs.typeToName(fieldInfo.getMysqlType()), convertibleTypesBuf.toString() });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7033 */     this.eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
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
/*      */   public boolean last() throws SQLException {
/* 7057 */     checkClosed();
/*      */     
/* 7059 */     boolean b = true;
/*      */     
/* 7061 */     if (this.rowData.size() == 0) {
/* 7062 */       b = false;
/*      */     } else {
/*      */       
/* 7065 */       if (this.onInsertRow) {
/* 7066 */         this.onInsertRow = false;
/*      */       }
/*      */       
/* 7069 */       if (this.doingUpdates) {
/* 7070 */         this.doingUpdates = false;
/*      */       }
/*      */       
/* 7073 */       if (this.thisRow != null) {
/* 7074 */         this.thisRow.closeOpenStreams();
/*      */       }
/*      */       
/* 7077 */       this.rowData.beforeLast();
/* 7078 */       this.thisRow = this.rowData.next();
/*      */     } 
/*      */     
/* 7081 */     setRowPositionValidity();
/*      */     
/* 7083 */     return b;
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
/* 7105 */   public void moveToCurrentRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7126 */   public void moveToInsertRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean next() throws SQLException {
/*      */     boolean b;
/* 7145 */     checkClosed();
/*      */     
/* 7147 */     if (this.onInsertRow) {
/* 7148 */       this.onInsertRow = false;
/*      */     }
/*      */     
/* 7151 */     if (this.doingUpdates) {
/* 7152 */       this.doingUpdates = false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7157 */     if (!reallyResult()) {
/* 7158 */       throw SQLError.createSQLException(Messages.getString("ResultSet.ResultSet_is_from_UPDATE._No_Data_115"), "S1000", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7164 */     if (this.thisRow != null) {
/* 7165 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/* 7168 */     if (this.rowData.size() == 0) {
/* 7169 */       b = false;
/*      */     } else {
/* 7171 */       this.thisRow = this.rowData.next();
/*      */       
/* 7173 */       if (this.thisRow == null) {
/* 7174 */         b = false;
/*      */       } else {
/* 7176 */         clearWarnings();
/*      */         
/* 7178 */         b = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 7183 */     setRowPositionValidity();
/*      */     
/* 7185 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   private int parseIntAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException {
/* 7190 */     if (val == null) {
/* 7191 */       return 0;
/*      */     }
/*      */     
/* 7194 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7196 */     if (this.jdbcCompliantTruncationForReads && (
/* 7197 */       valueAsDouble < -2.147483648E9D || valueAsDouble > 2.147483647E9D))
/*      */     {
/* 7199 */       throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7204 */     return (int)valueAsDouble;
/*      */   }
/*      */   
/*      */   private int getIntWithOverflowCheck(int columnIndex) throws SQLException {
/* 7208 */     int intValue = this.thisRow.getInt(columnIndex);
/*      */     
/* 7210 */     checkForIntegerTruncation(columnIndex, null, intValue);
/*      */ 
/*      */     
/* 7213 */     return intValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkForIntegerTruncation(int columnIndex, byte[] valueAsBytes, int intValue) throws SQLException {
/* 7219 */     if (this.jdbcCompliantTruncationForReads && (
/* 7220 */       intValue == Integer.MIN_VALUE || intValue == Integer.MAX_VALUE)) {
/* 7221 */       String valueAsString = null;
/*      */       
/* 7223 */       if (valueAsBytes == null) {
/* 7224 */         valueAsString = this.thisRow.getString(columnIndex, this.fields[columnIndex].getCharacterSet(), this.connection);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 7229 */       long valueAsLong = Long.parseLong((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
/*      */ 
/*      */ 
/*      */       
/* 7233 */       if (valueAsLong < -2147483648L || valueAsLong > 2147483647L)
/*      */       {
/* 7235 */         throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndex + 1, 4);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long parseLongAsDouble(int columnIndexZeroBased, String val) throws NumberFormatException, SQLException {
/* 7245 */     if (val == null) {
/* 7246 */       return 0L;
/*      */     }
/*      */     
/* 7249 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7251 */     if (this.jdbcCompliantTruncationForReads && (
/* 7252 */       valueAsDouble < -9.223372036854776E18D || valueAsDouble > 9.223372036854776E18D))
/*      */     {
/* 7254 */       throwRangeException(val, columnIndexZeroBased + 1, -5);
/*      */     }
/*      */ 
/*      */     
/* 7258 */     return (long)valueAsDouble;
/*      */   }
/*      */   
/*      */   private long getLongWithOverflowCheck(int columnIndexZeroBased, boolean doOverflowCheck) throws SQLException {
/* 7262 */     long longValue = this.thisRow.getLong(columnIndexZeroBased);
/*      */     
/* 7264 */     if (doOverflowCheck) {
/* 7265 */       checkForLongTruncation(columnIndexZeroBased, null, longValue);
/*      */     }
/*      */     
/* 7268 */     return longValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long parseLongWithOverflowCheck(int columnIndexZeroBased, byte[] valueAsBytes, String valueAsString, boolean doCheck) throws NumberFormatException, SQLException {
/* 7275 */     long longValue = 0L;
/*      */     
/* 7277 */     if (valueAsBytes == null && valueAsString == null) {
/* 7278 */       return 0L;
/*      */     }
/*      */     
/* 7281 */     if (valueAsBytes != null) {
/* 7282 */       longValue = StringUtils.getLong(valueAsBytes);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7292 */       valueAsString = valueAsString.trim();
/*      */       
/* 7294 */       longValue = Long.parseLong(valueAsString);
/*      */     } 
/*      */     
/* 7297 */     if (doCheck && this.jdbcCompliantTruncationForReads) {
/* 7298 */       checkForLongTruncation(columnIndexZeroBased, valueAsBytes, longValue);
/*      */     }
/*      */     
/* 7301 */     return longValue;
/*      */   }
/*      */   
/*      */   private void checkForLongTruncation(int columnIndexZeroBased, byte[] valueAsBytes, long longValue) throws SQLException {
/* 7305 */     if (longValue == Float.MIN_VALUE || longValue == Float.MAX_VALUE) {
/*      */       
/* 7307 */       String valueAsString = null;
/*      */       
/* 7309 */       if (valueAsBytes == null) {
/* 7310 */         valueAsString = this.thisRow.getString(columnIndexZeroBased, this.fields[columnIndexZeroBased].getCharacterSet(), this.connection);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 7315 */       double valueAsDouble = Double.parseDouble((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
/*      */ 
/*      */ 
/*      */       
/* 7319 */       if (valueAsDouble < -9.223372036854776E18D || valueAsDouble > 9.223372036854776E18D)
/*      */       {
/* 7321 */         throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndexZeroBased + 1, -5);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short parseShortAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException {
/* 7330 */     if (val == null) {
/* 7331 */       return 0;
/*      */     }
/*      */     
/* 7334 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7336 */     if (this.jdbcCompliantTruncationForReads && (
/* 7337 */       valueAsDouble < -32768.0D || valueAsDouble > 32767.0D))
/*      */     {
/* 7339 */       throwRangeException(String.valueOf(valueAsDouble), columnIndex, 5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7344 */     return (short)(int)valueAsDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short parseShortWithOverflowCheck(int columnIndex, byte[] valueAsBytes, String valueAsString) throws NumberFormatException, SQLException {
/* 7351 */     short shortValue = 0;
/*      */     
/* 7353 */     if (valueAsBytes == null && valueAsString == null) {
/* 7354 */       return 0;
/*      */     }
/*      */     
/* 7357 */     if (valueAsBytes != null) {
/* 7358 */       shortValue = StringUtils.getShort(valueAsBytes);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 7368 */       valueAsString = valueAsString.trim();
/*      */       
/* 7370 */       shortValue = Short.parseShort(valueAsString);
/*      */     } 
/*      */     
/* 7373 */     if (this.jdbcCompliantTruncationForReads && (
/* 7374 */       shortValue == Short.MIN_VALUE || shortValue == Short.MAX_VALUE)) {
/* 7375 */       long valueAsLong = Long.parseLong((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
/*      */ 
/*      */ 
/*      */       
/* 7379 */       if (valueAsLong < -32768L || valueAsLong > 32767L)
/*      */       {
/* 7381 */         throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndex, 5);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7388 */     return shortValue;
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
/*      */   public boolean prev() throws SQLException {
/* 7412 */     checkClosed();
/*      */     
/* 7414 */     int rowIndex = this.rowData.getCurrentRowNumber();
/*      */     
/* 7416 */     if (this.thisRow != null) {
/* 7417 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/* 7420 */     boolean b = true;
/*      */     
/* 7422 */     if (rowIndex - 1 >= 0) {
/* 7423 */       rowIndex--;
/* 7424 */       this.rowData.setCurrentRow(rowIndex);
/* 7425 */       this.thisRow = this.rowData.getAt(rowIndex);
/*      */       
/* 7427 */       b = true;
/* 7428 */     } else if (rowIndex - 1 == -1) {
/* 7429 */       rowIndex--;
/* 7430 */       this.rowData.setCurrentRow(rowIndex);
/* 7431 */       this.thisRow = null;
/*      */       
/* 7433 */       b = false;
/*      */     } else {
/* 7435 */       b = false;
/*      */     } 
/*      */     
/* 7438 */     setRowPositionValidity();
/*      */     
/* 7440 */     return b;
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
/*      */   public boolean previous() throws SQLException {
/* 7462 */     if (this.onInsertRow) {
/* 7463 */       this.onInsertRow = false;
/*      */     }
/*      */     
/* 7466 */     if (this.doingUpdates) {
/* 7467 */       this.doingUpdates = false;
/*      */     }
/*      */     
/* 7470 */     return prev();
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
/*      */   public void realClose(boolean calledExplicitly) throws SQLException {
/* 7483 */     if (this.isClosed) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 7488 */       if (this.useUsageAdvisor)
/*      */       {
/*      */ 
/*      */         
/* 7492 */         if (!calledExplicitly) {
/* 7493 */           this.eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.ResultSet_implicitly_closed_by_driver")));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7512 */         if (this.rowData instanceof RowDataStatic) {
/*      */ 
/*      */ 
/*      */           
/* 7516 */           if (this.rowData.size() > this.connection.getResultSetSizeThreshold())
/*      */           {
/* 7518 */             this.eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owningStatement == null) ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Too_Large_Result_Set", new Object[] { new Integer(this.rowData.size()), new Integer(this.connection.getResultSetSizeThreshold()) })));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 7546 */           if (!isLast() && !isAfterLast() && this.rowData.size() != 0)
/*      */           {
/* 7548 */             this.eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owningStatement == null) ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Possible_incomplete_traversal_of_result_set", new Object[] { new Integer(getRow()), new Integer(this.rowData.size()) })));
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7581 */         if (this.columnUsed.length > 0 && !this.rowData.wasEmpty()) {
/* 7582 */           StringBuffer buf = new StringBuffer(Messages.getString("ResultSet.The_following_columns_were_never_referenced"));
/*      */ 
/*      */ 
/*      */           
/* 7586 */           boolean issueWarn = false;
/*      */           
/* 7588 */           for (int i = 0; i < this.columnUsed.length; i++) {
/* 7589 */             if (!this.columnUsed[i]) {
/* 7590 */               if (!issueWarn) {
/* 7591 */                 issueWarn = true;
/*      */               } else {
/* 7593 */                 buf.append(", ");
/*      */               } 
/*      */               
/* 7596 */               buf.append(this.fields[i].getFullName());
/*      */             } 
/*      */           } 
/*      */           
/* 7600 */           if (issueWarn) {
/* 7601 */             this.eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), false, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, buf.toString()));
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 7615 */       if (this.owningStatement != null && calledExplicitly) {
/* 7616 */         this.owningStatement.removeOpenResultSet(this);
/*      */       }
/*      */       
/* 7619 */       SQLException exceptionDuringClose = null;
/*      */       
/* 7621 */       if (this.rowData != null) {
/*      */         try {
/* 7623 */           this.rowData.close();
/* 7624 */         } catch (SQLException sqlEx) {
/* 7625 */           exceptionDuringClose = sqlEx;
/*      */         } 
/*      */       }
/*      */       
/* 7629 */       if (this.statementUsedForFetchingRows != null) {
/*      */         try {
/* 7631 */           this.statementUsedForFetchingRows.realClose(true, false);
/* 7632 */         } catch (SQLException sqlEx) {
/* 7633 */           if (exceptionDuringClose != null) {
/* 7634 */             exceptionDuringClose.setNextException(sqlEx);
/*      */           } else {
/* 7636 */             exceptionDuringClose = sqlEx;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 7641 */       this.rowData = null;
/* 7642 */       this.defaultTimeZone = null;
/* 7643 */       this.fields = null;
/* 7644 */       this.columnLabelToIndex = null;
/* 7645 */       this.fullColumnNameToIndex = null;
/* 7646 */       this.columnToIndexCache = null;
/* 7647 */       this.eventSink = null;
/* 7648 */       this.warningChain = null;
/*      */       
/* 7650 */       if (!this.retainOwningStatement) {
/* 7651 */         this.owningStatement = null;
/*      */       }
/*      */       
/* 7654 */       this.catalog = null;
/* 7655 */       this.serverInfo = null;
/* 7656 */       this.thisRow = null;
/* 7657 */       this.fastDateCal = null;
/* 7658 */       this.connection = null;
/*      */       
/* 7660 */       this.isClosed = true;
/*      */       
/* 7662 */       if (exceptionDuringClose != null) {
/* 7663 */         throw exceptionDuringClose;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean reallyResult() throws SQLException {
/* 7669 */     if (this.rowData != null) {
/* 7670 */       return true;
/*      */     }
/*      */     
/* 7673 */     return this.reallyResult;
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
/* 7697 */   public void refreshRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean relative(int rows) throws SQLException {
/* 7727 */     checkClosed();
/*      */     
/* 7729 */     if (this.rowData.size() == 0) {
/* 7730 */       setRowPositionValidity();
/*      */       
/* 7732 */       return false;
/*      */     } 
/*      */     
/* 7735 */     if (this.thisRow != null) {
/* 7736 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/* 7739 */     this.rowData.moveRowRelative(rows);
/* 7740 */     this.thisRow = this.rowData.getAt(this.rowData.getCurrentRowNumber());
/*      */     
/* 7742 */     setRowPositionValidity();
/*      */     
/* 7744 */     return (!this.rowData.isAfterLast() && !this.rowData.isBeforeFirst());
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
/* 7763 */   public boolean rowDeleted() throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7781 */   public boolean rowInserted() throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7799 */   public boolean rowUpdated() throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7807 */   protected void setBinaryEncoded() throws SQLException { this.isBinaryEncoded = true; }
/*      */ 
/*      */ 
/*      */   
/* 7811 */   private void setDefaultTimeZone(TimeZone defaultTimeZone) { this.defaultTimeZone = defaultTimeZone; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFetchDirection(int direction) throws SQLException {
/* 7830 */     if (direction != 1000 && direction != 1001 && direction != 1002)
/*      */     {
/* 7832 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_value_for_fetch_direction_64"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7838 */     this.fetchDirection = direction;
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
/*      */   public void setFetchSize(int rows) throws SQLException {
/* 7858 */     if (rows < 0) {
/* 7859 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Value_must_be_between_0_and_getMaxRows()_66"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7865 */     this.fetchSize = rows;
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
/* 7876 */   public void setFirstCharOfQuery(char c) { this.firstCharOfQuery = c; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7887 */   protected void setNextResultSet(ResultSetInternalMethods nextResultSet) { this.nextResultSet = nextResultSet; }
/*      */ 
/*      */ 
/*      */   
/* 7891 */   public void setOwningStatement(StatementImpl owningStatement) { this.owningStatement = owningStatement; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7901 */   protected void setResultSetConcurrency(int concurrencyFlag) throws SQLException { this.resultSetConcurrency = concurrencyFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7912 */   protected void setResultSetType(int typeFlag) throws SQLException { this.resultSetType = typeFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7922 */   protected void setServerInfo(String info) { this.serverInfo = info; }
/*      */ 
/*      */ 
/*      */   
/* 7926 */   public void setStatementUsedForFetchingRows(PreparedStatement stmt) { this.statementUsedForFetchingRows = stmt; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7934 */   public void setWrapperStatement(Statement wrapperStatement) { this.wrapperStatement = wrapperStatement; }
/*      */ 
/*      */ 
/*      */   
/*      */   private void throwRangeException(String valueAsString, int columnIndex, int jdbcType) throws SQLException {
/* 7939 */     String datatype = null;
/*      */     
/* 7941 */     switch (jdbcType)
/*      */     { case -6:
/* 7943 */         datatype = "TINYINT";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7970 */         throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 5: datatype = "SMALLINT"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 4: datatype = "INTEGER"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case -5: datatype = "BIGINT"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 7: datatype = "REAL"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 6: datatype = "FLOAT"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 8: datatype = "DOUBLE"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());case 3: datatype = "DECIMAL"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor()); }  datatype = " (JDBC type '" + jdbcType + "')"; throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() throws SQLException {
/* 7981 */     if (this.reallyResult) {
/* 7982 */       return super.toString();
/*      */     }
/*      */     
/* 7985 */     return "Result set representing update count of " + this.updateCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7992 */   public void updateArray(int arg0, Array arg1) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 7999 */   public void updateArray(String arg0, Array arg1) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8023 */   public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8045 */   public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException { updateAsciiStream(findColumn(columnName), x, length); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8066 */   public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8085 */   public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException { updateBigDecimal(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8109 */   public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8131 */   public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException { updateBinaryStream(findColumn(columnName), x, length); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8138 */   public void updateBlob(int arg0, Blob arg1) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8145 */   public void updateBlob(String arg0, Blob arg1) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8165 */   public void updateBoolean(int columnIndex, boolean x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8183 */   public void updateBoolean(String columnName, boolean x) throws SQLException { updateBoolean(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8203 */   public void updateByte(int columnIndex, byte x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8221 */   public void updateByte(String columnName, byte x) throws SQLException { updateByte(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8241 */   public void updateBytes(int columnIndex, byte[] x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8259 */   public void updateBytes(String columnName, byte[] x) throws SQLException { updateBytes(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8283 */   public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8305 */   public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException { updateCharacterStream(findColumn(columnName), reader, length); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8312 */   public void updateClob(int arg0, Clob arg1) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8320 */   public void updateClob(String columnName, Clob clob) throws SQLException { updateClob(findColumn(columnName), clob); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8341 */   public void updateDate(int columnIndex, Date x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8360 */   public void updateDate(String columnName, Date x) throws SQLException { updateDate(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8380 */   public void updateDouble(int columnIndex, double x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8398 */   public void updateDouble(String columnName, double x) throws SQLException { updateDouble(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8418 */   public void updateFloat(int columnIndex, float x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8436 */   public void updateFloat(String columnName, float x) throws SQLException { updateFloat(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8456 */   public void updateInt(int columnIndex, int x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8474 */   public void updateInt(String columnName, int x) throws SQLException { updateInt(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8494 */   public void updateLong(int columnIndex, long x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8512 */   public void updateLong(String columnName, long x) throws SQLException { updateLong(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8530 */   public void updateNull(int columnIndex) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8546 */   public void updateNull(String columnName) { updateNull(findColumn(columnName)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8566 */   public void updateObject(int columnIndex, Object x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8591 */   public void updateObject(int columnIndex, Object x, int scale) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8609 */   public void updateObject(String columnName, Object x) throws SQLException { updateObject(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8632 */   public void updateObject(String columnName, Object x, int scale) throws SQLException { updateObject(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8639 */   public void updateRef(int arg0, Ref arg1) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8646 */   public void updateRef(String arg0, Ref arg1) throws SQLException { throw SQLError.notImplemented(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8660 */   public void updateRow() throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8680 */   public void updateShort(int columnIndex, short x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8698 */   public void updateShort(String columnName, short x) throws SQLException { updateShort(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8718 */   public void updateString(int columnIndex, String x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8736 */   public void updateString(String columnName, String x) throws SQLException { updateString(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8757 */   public void updateTime(int columnIndex, Time x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8776 */   public void updateTime(String columnName, Time x) throws SQLException { updateTime(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8798 */   public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException { throw new NotUpdatable(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8817 */   public void updateTimestamp(String columnName, Timestamp x) throws SQLException { updateTimestamp(findColumn(columnName), x); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 8832 */   public boolean wasNull() throws SQLException { return this.wasNullFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Calendar getGmtCalendar() {
/* 8839 */     if (this.gmtCalendar == null) {
/* 8840 */       this.gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
/*      */     }
/*      */     
/* 8843 */     return this.gmtCalendar;
/*      */   }
/*      */ 
/*      */   
/* 8847 */   protected ExceptionInterceptor getExceptionInterceptor() { return this.exceptionInterceptor; }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ResultSetImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */