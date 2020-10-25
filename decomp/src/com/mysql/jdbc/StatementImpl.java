/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.InputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StatementImpl
/*      */   implements Statement
/*      */ {
/*      */   protected static final String PING_MARKER = "/* ping */";
/*      */   
/*      */   class CancelTask
/*      */     extends TimerTask
/*      */   {
/*      */     long connectionId;
/*      */     SQLException caughtWhileCancelling;
/*      */     StatementImpl toCancel;
/*      */     
/*      */     CancelTask(StatementImpl cancellee) throws SQLException {
/*   73 */       this.connectionId = 0L;
/*   74 */       this.caughtWhileCancelling = null;
/*      */ 
/*      */ 
/*      */       
/*   78 */       this.connectionId = StatementImpl.this.connection.getIO().getThreadId();
/*   79 */       this.toCancel = cancellee;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() throws SQLException {
/*   84 */       Thread cancelThread = new Thread()
/*      */         {
/*      */           public void run() throws SQLException {
/*   87 */             if (StatementImpl.this.connection.getQueryTimeoutKillsConnection()) {
/*      */               try {
/*   89 */                 this.this$1.toCancel.wasCancelled = true;
/*   90 */                 this.this$1.toCancel.wasCancelledByTimeout = true;
/*   91 */                 StatementImpl.this.connection.realClose(false, false, true, new MySQLStatementCancelledException(Messages.getString("Statement.ConnectionKilledDueToTimeout")));
/*      */               }
/*   93 */               catch (NullPointerException npe) {
/*      */               
/*   95 */               } catch (SQLException sqlEx) {
/*   96 */                 StatementImpl.CancelTask.this.caughtWhileCancelling = sqlEx;
/*      */               } 
/*      */             } else {
/*   99 */               Connection cancelConn = null;
/*  100 */               Statement cancelStmt = null;
/*      */               
/*      */               try {
/*  103 */                 synchronized (StatementImpl.this.cancelTimeoutMutex) {
/*  104 */                   cancelConn = StatementImpl.this.connection.duplicate();
/*  105 */                   cancelStmt = cancelConn.createStatement();
/*  106 */                   cancelStmt.execute("KILL QUERY " + StatementImpl.CancelTask.this.connectionId);
/*  107 */                   this.this$1.toCancel.wasCancelled = true;
/*  108 */                   this.this$1.toCancel.wasCancelledByTimeout = true;
/*      */                 } 
/*  110 */               } catch (SQLException sqlEx) {
/*  111 */                 StatementImpl.CancelTask.this.caughtWhileCancelling = sqlEx;
/*  112 */               } catch (NullPointerException npe) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*      */               finally {
/*      */                 
/*  120 */                 if (cancelStmt != null) {
/*      */                   try {
/*  122 */                     cancelStmt.close();
/*  123 */                   } catch (SQLException sqlEx) {
/*  124 */                     throw new RuntimeException(sqlEx.toString());
/*      */                   } 
/*      */                 }
/*      */                 
/*  128 */                 if (cancelConn != null) {
/*      */                   try {
/*  130 */                     cancelConn.close();
/*  131 */                   } catch (SQLException sqlEx) {
/*  132 */                     throw new RuntimeException(sqlEx.toString());
/*      */                   } 
/*      */                 }
/*      */                 
/*  136 */                 StatementImpl.CancelTask.this.toCancel = null;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         };
/*      */       
/*  142 */       cancelThread.start();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   protected Object cancelTimeoutMutex = new Object();
/*      */ 
/*      */   
/*  152 */   protected static int statementCounter = 1;
/*      */ 
/*      */   
/*      */   public static final byte USES_VARIABLES_FALSE = 0;
/*      */   
/*      */   public static final byte USES_VARIABLES_TRUE = 1;
/*      */   
/*      */   public static final byte USES_VARIABLES_UNKNOWN = -1;
/*      */   
/*      */   protected boolean wasCancelled = false;
/*      */   
/*      */   protected boolean wasCancelledByTimeout = false;
/*      */   
/*      */   protected List batchedArgs;
/*      */   
/*  167 */   protected SingleByteCharsetConverter charConverter = null;
/*      */ 
/*      */   
/*  170 */   protected String charEncoding = null;
/*      */ 
/*      */   
/*  173 */   protected MySQLConnection connection = null;
/*      */   
/*  175 */   protected long connectionId = 0L;
/*      */ 
/*      */   
/*  178 */   protected String currentCatalog = null;
/*      */ 
/*      */   
/*      */   protected boolean doEscapeProcessing = true;
/*      */ 
/*      */   
/*  184 */   protected ProfilerEventHandler eventSink = null;
/*      */ 
/*      */   
/*  187 */   private int fetchSize = 0;
/*      */ 
/*      */   
/*      */   protected boolean isClosed = false;
/*      */ 
/*      */   
/*  193 */   protected long lastInsertId = -1L;
/*      */ 
/*      */   
/*  196 */   protected int maxFieldSize = MysqlIO.getMaxBuf();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   protected int maxRows = -1;
/*      */ 
/*      */   
/*      */   protected boolean maxRowsChanged = false;
/*      */ 
/*      */   
/*  208 */   protected Set openResults = new HashSet();
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pedantic = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Throwable pointOfOrigin;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean profileSQL = false;
/*      */ 
/*      */   
/*  223 */   protected ResultSetInternalMethods results = null;
/*      */ 
/*      */   
/*  226 */   protected int resultSetConcurrency = 0;
/*      */ 
/*      */   
/*  229 */   protected int resultSetType = 0;
/*      */ 
/*      */   
/*      */   protected int statementId;
/*      */ 
/*      */   
/*  235 */   protected int timeoutInMillis = 0;
/*      */ 
/*      */   
/*  238 */   protected long updateCount = -1L;
/*      */ 
/*      */   
/*      */   protected boolean useUsageAdvisor = false;
/*      */ 
/*      */   
/*  244 */   protected SQLWarning warningChain = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean holdResultsOpenOverClose = false;
/*      */ 
/*      */ 
/*      */   
/*  252 */   protected ArrayList batchedGeneratedKeys = null;
/*      */   
/*      */   protected boolean retrieveGeneratedKeys = false;
/*      */   
/*      */   protected boolean continueBatchOnError = false;
/*      */   
/*  258 */   protected PingTarget pingTarget = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean useLegacyDatetimeCode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean lastQueryIsOnDupKeyUpdate = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatementImpl(MySQLConnection c, String catalog) throws SQLException {
/*  279 */     if (c == null || c.isClosed()) {
/*  280 */       throw SQLError.createSQLException(Messages.getString("Statement.0"), "08003", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  285 */     this.connection = c;
/*  286 */     this.connectionId = this.connection.getId();
/*  287 */     this.exceptionInterceptor = this.connection.getExceptionInterceptor();
/*      */ 
/*      */     
/*  290 */     this.currentCatalog = catalog;
/*  291 */     this.pedantic = this.connection.getPedantic();
/*  292 */     this.continueBatchOnError = this.connection.getContinueBatchOnError();
/*  293 */     this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode();
/*      */     
/*  295 */     if (!this.connection.getDontTrackOpenResources()) {
/*  296 */       this.connection.registerStatement(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     if (this.connection != null) {
/*  304 */       this.maxFieldSize = this.connection.getMaxAllowedPacket();
/*      */       
/*  306 */       int defaultFetchSize = this.connection.getDefaultFetchSize();
/*      */       
/*  308 */       if (defaultFetchSize != 0) {
/*  309 */         setFetchSize(defaultFetchSize);
/*      */       }
/*      */     } 
/*      */     
/*  313 */     if (this.connection.getUseUnicode()) {
/*  314 */       this.charEncoding = this.connection.getEncoding();
/*      */       
/*  316 */       this.charConverter = this.connection.getCharsetConverter(this.charEncoding);
/*      */     } 
/*      */     
/*  319 */     boolean profiling = (this.connection.getProfileSql() || this.connection.getUseUsageAdvisor() || this.connection.getLogSlowQueries());
/*      */ 
/*      */     
/*  322 */     if (this.connection.getAutoGenerateTestcaseScript() || profiling) {
/*  323 */       this.statementId = statementCounter++;
/*      */     }
/*      */     
/*  326 */     if (profiling) {
/*  327 */       this.pointOfOrigin = new Throwable();
/*  328 */       this.profileSQL = this.connection.getProfileSql();
/*  329 */       this.useUsageAdvisor = this.connection.getUseUsageAdvisor();
/*  330 */       this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */     } 
/*      */     
/*  333 */     int maxRowsConn = this.connection.getMaxRows();
/*      */     
/*  335 */     if (maxRowsConn != -1) {
/*  336 */       setMaxRows(maxRowsConn);
/*      */     }
/*      */     
/*  339 */     this.holdResultsOpenOverClose = this.connection.getHoldResultsOpenOverStatementClose();
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
/*      */   public void addBatch(String sql) throws SQLException {
/*  352 */     if (this.batchedArgs == null) {
/*  353 */       this.batchedArgs = new ArrayList();
/*      */     }
/*      */     
/*  356 */     if (sql != null) {
/*  357 */       this.batchedArgs.add(sql);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel() throws SQLException {
/*  367 */     if (!this.isClosed && this.connection != null && this.connection.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */       
/*  370 */       Connection cancelConn = null;
/*  371 */       Statement cancelStmt = null;
/*      */       
/*      */       try {
/*  374 */         cancelConn = this.connection.duplicate();
/*  375 */         cancelStmt = cancelConn.createStatement();
/*  376 */         cancelStmt.execute("KILL QUERY " + this.connection.getIO().getThreadId());
/*      */         
/*  378 */         this.wasCancelled = true;
/*      */       } finally {
/*  380 */         if (cancelStmt != null) {
/*  381 */           cancelStmt.close();
/*      */         }
/*      */         
/*  384 */         if (cancelConn != null) {
/*  385 */           cancelConn.close();
/*      */         }
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
/*      */   protected void checkClosed() throws SQLException {
/*  401 */     if (this.isClosed) {
/*  402 */       throw SQLError.createSQLException(Messages.getString("Statement.49"), "08003", getExceptionInterceptor());
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
/*      */   protected void checkForDml(String sql, char firstStatementChar) throws SQLException {
/*  422 */     if (firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C') {
/*      */ 
/*      */       
/*  425 */       String noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true);
/*      */ 
/*      */       
/*  428 */       if (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER"))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  434 */         throw SQLError.createSQLException(Messages.getString("Statement.57"), "S1009", getExceptionInterceptor());
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
/*      */   protected void checkNullOrEmptyQuery(String sql) throws SQLException {
/*  451 */     if (sql == null) {
/*  452 */       throw SQLError.createSQLException(Messages.getString("Statement.59"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  457 */     if (sql.length() == 0) {
/*  458 */       throw SQLError.createSQLException(Messages.getString("Statement.61"), "S1009", getExceptionInterceptor());
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
/*      */   public void clearBatch() throws SQLException {
/*  473 */     if (this.batchedArgs != null) {
/*  474 */       this.batchedArgs.clear();
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
/*  486 */   public void clearWarnings() throws SQLException { this.warningChain = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  505 */   public void close() throws SQLException { realClose(true, true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeAllOpenResults() throws SQLException {
/*  512 */     if (this.openResults != null) {
/*  513 */       for (Iterator iter = this.openResults.iterator(); iter.hasNext(); ) {
/*  514 */         ResultSetInternalMethods element = (ResultSetInternalMethods)iter.next();
/*      */         
/*      */         try {
/*  517 */           element.realClose(false);
/*  518 */         } catch (SQLException sqlEx) {
/*  519 */           AssertionFailedException.shouldNotHappen(sqlEx);
/*      */         } 
/*      */       } 
/*      */       
/*  523 */       this.openResults.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeOpenResultSet(ResultSet rs) {
/*  528 */     if (this.openResults != null) {
/*  529 */       this.openResults.remove(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getOpenResultSetCount() {
/*  534 */     if (this.openResults != null) {
/*  535 */       return this.openResults.size();
/*      */     }
/*      */     
/*  538 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResultSetInternalMethods createResultSetUsingServerFetch(String sql) throws SQLException {
/*  547 */     PreparedStatement pStmt = this.connection.prepareStatement(sql, this.resultSetType, this.resultSetConcurrency);
/*      */ 
/*      */     
/*  550 */     pStmt.setFetchSize(this.fetchSize);
/*      */     
/*  552 */     if (this.maxRows > -1) {
/*  553 */       pStmt.setMaxRows(this.maxRows);
/*      */     }
/*      */     
/*  556 */     pStmt.execute();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  562 */     ResultSetInternalMethods rs = ((StatementImpl)pStmt).getResultSetInternal();
/*      */ 
/*      */     
/*  565 */     rs.setStatementUsedForFetchingRows((PreparedStatement)pStmt);
/*      */ 
/*      */     
/*  568 */     this.results = rs;
/*      */     
/*  570 */     return rs;
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
/*  581 */   protected boolean createStreamingResultSet() { return (this.resultSetType == 1003 && this.resultSetConcurrency == 1007 && this.fetchSize == Integer.MIN_VALUE); }
/*      */ 
/*      */ 
/*      */   
/*  585 */   private int originalResultSetType = 0;
/*  586 */   private int originalFetchSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableStreamingResults() throws SQLException {
/*  592 */     this.originalResultSetType = this.resultSetType;
/*  593 */     this.originalFetchSize = this.fetchSize;
/*      */     
/*  595 */     setFetchSize(-2147483648);
/*  596 */     setResultSetType(1003);
/*      */   }
/*      */   
/*      */   public void disableStreamingResults() throws SQLException {
/*  600 */     if (this.fetchSize == Integer.MIN_VALUE && this.resultSetType == 1003) {
/*      */       
/*  602 */       setFetchSize(this.originalFetchSize);
/*  603 */       setResultSetType(this.originalResultSetType);
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
/*  622 */   public boolean execute(String sql) throws SQLException { return execute(sql, false); }
/*      */ 
/*      */   
/*      */   private boolean execute(String sql, boolean returnGeneratedKeys) throws SQLException {
/*  626 */     checkClosed();
/*      */     
/*  628 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/*  630 */     synchronized (locallyScopedConn.getMutex()) {
/*  631 */       this.retrieveGeneratedKeys = returnGeneratedKeys;
/*  632 */       this.lastQueryIsOnDupKeyUpdate = false;
/*  633 */       if (returnGeneratedKeys) {
/*  634 */         this.lastQueryIsOnDupKeyUpdate = containsOnDuplicateKeyInString(sql);
/*      */       }
/*  636 */       resetCancelledState();
/*      */       
/*  638 */       checkNullOrEmptyQuery(sql);
/*      */       
/*  640 */       checkClosed();
/*      */       
/*  642 */       char firstNonWsChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
/*      */       
/*  644 */       boolean isSelect = true;
/*      */       
/*  646 */       if (firstNonWsChar != 'S') {
/*  647 */         isSelect = false;
/*      */         
/*  649 */         if (locallyScopedConn.isReadOnly()) {
/*  650 */           throw SQLError.createSQLException(Messages.getString("Statement.27") + Messages.getString("Statement.28"), "S1009", getExceptionInterceptor());
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  657 */       boolean doStreaming = createStreamingResultSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  667 */       if (doStreaming && locallyScopedConn.getNetTimeoutForStreamingResults() > 0)
/*      */       {
/*  669 */         executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + locallyScopedConn.getNetTimeoutForStreamingResults());
/*      */       }
/*      */ 
/*      */       
/*  673 */       if (this.doEscapeProcessing) {
/*  674 */         Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), locallyScopedConn);
/*      */ 
/*      */         
/*  677 */         if (escapedSqlResult instanceof String) {
/*  678 */           sql = (String)escapedSqlResult;
/*      */         } else {
/*  680 */           sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
/*      */         } 
/*      */       } 
/*      */       
/*  684 */       if (this.results != null && 
/*  685 */         !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
/*  686 */         this.results.realClose(false);
/*      */       }
/*      */ 
/*      */       
/*  690 */       if (sql.charAt(0) == '/' && 
/*  691 */         sql.startsWith("/* ping */")) {
/*  692 */         doPingInstead();
/*      */         
/*  694 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  698 */       CachedResultSetMetaData cachedMetaData = null;
/*      */       
/*  700 */       ResultSetInternalMethods rs = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  709 */       this.batchedGeneratedKeys = null;
/*      */       
/*  711 */       if (useServerFetch()) {
/*  712 */         rs = createResultSetUsingServerFetch(sql);
/*      */       } else {
/*  714 */         CancelTask timeoutTask = null;
/*      */         
/*  716 */         String oldCatalog = null;
/*      */         
/*      */         try {
/*  719 */           if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */             
/*  722 */             timeoutTask = new CancelTask(this);
/*  723 */             locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  729 */           if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/*      */             
/*  731 */             oldCatalog = locallyScopedConn.getCatalog();
/*  732 */             locallyScopedConn.setCatalog(this.currentCatalog);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  739 */           Field[] cachedFields = null;
/*      */           
/*  741 */           if (locallyScopedConn.getCacheResultSetMetadata()) {
/*  742 */             cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
/*      */             
/*  744 */             if (cachedMetaData != null) {
/*  745 */               cachedFields = cachedMetaData.fields;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  752 */           if (locallyScopedConn.useMaxRows()) {
/*  753 */             int rowLimit = -1;
/*      */             
/*  755 */             if (isSelect) {
/*  756 */               if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) {
/*  757 */                 rowLimit = this.maxRows;
/*      */               }
/*  759 */               else if (this.maxRows <= 0) {
/*  760 */                 executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */               } else {
/*      */                 
/*  763 */                 executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  769 */               executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  774 */             rs = locallyScopedConn.execSQL(this, sql, rowLimit, null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  779 */             rs = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  785 */           if (timeoutTask != null) {
/*  786 */             if (timeoutTask.caughtWhileCancelling != null) {
/*  787 */               throw timeoutTask.caughtWhileCancelling;
/*      */             }
/*      */             
/*  790 */             timeoutTask.cancel();
/*  791 */             timeoutTask = null;
/*      */           } 
/*      */           
/*  794 */           synchronized (this.cancelTimeoutMutex) {
/*  795 */             if (this.wasCancelled) {
/*  796 */               MySQLStatementCancelledException mySQLStatementCancelledException = null;
/*      */               
/*  798 */               if (this.wasCancelledByTimeout) {
/*  799 */                 MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException();
/*      */               } else {
/*  801 */                 mySQLStatementCancelledException = new MySQLStatementCancelledException();
/*      */               } 
/*      */               
/*  804 */               resetCancelledState();
/*      */               
/*  806 */               throw mySQLStatementCancelledException;
/*      */             } 
/*      */           } 
/*      */         } finally {
/*  810 */           if (timeoutTask != null) {
/*  811 */             timeoutTask.cancel();
/*  812 */             locallyScopedConn.getCancelTimer().purge();
/*      */           } 
/*      */           
/*  815 */           if (oldCatalog != null) {
/*  816 */             locallyScopedConn.setCatalog(oldCatalog);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  821 */       if (rs != null) {
/*  822 */         this.lastInsertId = rs.getUpdateID();
/*      */         
/*  824 */         this.results = rs;
/*      */         
/*  826 */         rs.setFirstCharOfQuery(firstNonWsChar);
/*      */         
/*  828 */         if (rs.reallyResult()) {
/*  829 */           if (cachedMetaData != null) {
/*  830 */             locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
/*      */           
/*      */           }
/*  833 */           else if (this.connection.getCacheResultSetMetadata()) {
/*  834 */             locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  841 */       return (rs != null && rs.reallyResult());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void resetCancelledState() throws SQLException {
/*  846 */     if (this.cancelTimeoutMutex == null) {
/*      */       return;
/*      */     }
/*      */     
/*  850 */     synchronized (this.cancelTimeoutMutex) {
/*  851 */       this.wasCancelled = false;
/*  852 */       this.wasCancelledByTimeout = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(String sql, int returnGeneratedKeys) throws SQLException {
/*  863 */     if (returnGeneratedKeys == 1) {
/*  864 */       checkClosed();
/*      */       
/*  866 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/*  868 */       synchronized (locallyScopedConn.getMutex()) {
/*      */ 
/*      */ 
/*      */         
/*  872 */         boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
/*      */         
/*  874 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/*  877 */           return execute(sql, true);
/*      */         } finally {
/*  879 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  884 */     return execute(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(String sql, int[] generatedKeyIndices) throws SQLException {
/*  892 */     if (generatedKeyIndices != null && generatedKeyIndices.length > 0) {
/*  893 */       checkClosed();
/*      */       
/*  895 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/*  897 */       synchronized (locallyScopedConn.getMutex()) {
/*  898 */         this.retrieveGeneratedKeys = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  903 */         boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
/*      */         
/*  905 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/*  908 */           return execute(sql, true);
/*      */         } finally {
/*  910 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  915 */     return execute(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean execute(String sql, String[] generatedKeyNames) throws SQLException {
/*  923 */     if (generatedKeyNames != null && generatedKeyNames.length > 0) {
/*  924 */       checkClosed();
/*      */       
/*  926 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/*  928 */       synchronized (locallyScopedConn.getMutex()) {
/*  929 */         this.retrieveGeneratedKeys = true;
/*      */ 
/*      */ 
/*      */         
/*  933 */         boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
/*      */         
/*  935 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/*  938 */           return execute(sql, true);
/*      */         } finally {
/*  940 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  945 */     return execute(sql);
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
/*      */   public int[] executeBatch() throws SQLException {
/*  963 */     checkClosed();
/*      */     
/*  965 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/*  967 */     if (locallyScopedConn.isReadOnly()) {
/*  968 */       throw SQLError.createSQLException(Messages.getString("Statement.34") + Messages.getString("Statement.35"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  974 */     if (this.results != null && 
/*  975 */       !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
/*  976 */       this.results.realClose(false);
/*      */     }
/*      */ 
/*      */     
/*  980 */     synchronized (locallyScopedConn.getMutex()) {
/*  981 */       if (this.batchedArgs == null || this.batchedArgs.size() == 0) {
/*  982 */         return new int[0];
/*      */       }
/*      */ 
/*      */       
/*  986 */       int individualStatementTimeout = this.timeoutInMillis;
/*  987 */       this.timeoutInMillis = 0;
/*      */       
/*  989 */       CancelTask timeoutTask = null;
/*      */       
/*      */       try {
/*  992 */         resetCancelledState();
/*      */         
/*  994 */         this.retrieveGeneratedKeys = true;
/*      */         
/*  996 */         int[] updateCounts = null;
/*      */ 
/*      */         
/*  999 */         if (this.batchedArgs != null) {
/* 1000 */           int nbrCommands = this.batchedArgs.size();
/*      */           
/* 1002 */           this.batchedGeneratedKeys = new ArrayList(this.batchedArgs.size());
/*      */           
/* 1004 */           boolean multiQueriesEnabled = locallyScopedConn.getAllowMultiQueries();
/*      */           
/* 1006 */           if (locallyScopedConn.versionMeetsMinimum(4, 1, 1) && (multiQueriesEnabled || (locallyScopedConn.getRewriteBatchedStatements() && nbrCommands > 4)))
/*      */           {
/*      */ 
/*      */             
/* 1010 */             return executeBatchUsingMultiQueries(multiQueriesEnabled, nbrCommands, individualStatementTimeout);
/*      */           }
/*      */           
/* 1013 */           if (locallyScopedConn.getEnableQueryTimeouts() && individualStatementTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */             
/* 1016 */             timeoutTask = new CancelTask(this);
/* 1017 */             locallyScopedConn.getCancelTimer().schedule(timeoutTask, individualStatementTimeout);
/*      */           } 
/*      */ 
/*      */           
/* 1021 */           updateCounts = new int[nbrCommands];
/*      */           
/* 1023 */           for (i = 0; i < nbrCommands; i++) {
/* 1024 */             updateCounts[i] = -3;
/*      */           }
/*      */           
/* 1027 */           SQLException sqlEx = null;
/*      */           
/* 1029 */           int commandIndex = 0;
/*      */           
/* 1031 */           for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
/*      */             try {
/* 1033 */               String sql = (String)this.batchedArgs.get(commandIndex);
/* 1034 */               updateCounts[commandIndex] = executeUpdate(sql, true, true);
/*      */               
/* 1036 */               getBatchedGeneratedKeys(containsOnDuplicateKeyInString(sql) ? 1 : 0);
/* 1037 */             } catch (SQLException ex) {
/* 1038 */               updateCounts[commandIndex] = -3;
/*      */               
/* 1040 */               if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !hasDeadlockOrTimeoutRolledBackTx(ex)) {
/*      */ 
/*      */ 
/*      */                 
/* 1044 */                 sqlEx = ex;
/*      */               } else {
/* 1046 */                 int[] newUpdateCounts = new int[commandIndex];
/*      */                 
/* 1048 */                 if (hasDeadlockOrTimeoutRolledBackTx(ex)) {
/* 1049 */                   for (int i = 0; i < newUpdateCounts.length; i++) {
/* 1050 */                     newUpdateCounts[i] = -3;
/*      */                   }
/*      */                 } else {
/* 1053 */                   System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
/*      */                 } 
/*      */ 
/*      */                 
/* 1057 */                 throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1064 */           if (sqlEx != null) {
/* 1065 */             throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1071 */         if (timeoutTask != null) {
/* 1072 */           if (timeoutTask.caughtWhileCancelling != null) {
/* 1073 */             throw timeoutTask.caughtWhileCancelling;
/*      */           }
/*      */           
/* 1076 */           timeoutTask.cancel();
/*      */           
/* 1078 */           locallyScopedConn.getCancelTimer().purge();
/* 1079 */           timeoutTask = null;
/*      */         } 
/*      */         
/* 1082 */         return (updateCounts != null) ? updateCounts : new int[0];
/*      */       } finally {
/*      */         
/* 1085 */         if (timeoutTask != null) {
/* 1086 */           timeoutTask.cancel();
/*      */           
/* 1088 */           locallyScopedConn.getCancelTimer().purge();
/*      */         } 
/*      */         
/* 1091 */         resetCancelledState();
/*      */         
/* 1093 */         this.timeoutInMillis = individualStatementTimeout;
/*      */         
/* 1095 */         clearBatch();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final boolean hasDeadlockOrTimeoutRolledBackTx(SQLException ex) {
/* 1101 */     int vendorCode = ex.getErrorCode();
/*      */     
/* 1103 */     switch (vendorCode) {
/*      */       case 1206:
/*      */       case 1213:
/* 1106 */         return true;
/*      */       case 1205:
/*      */         try {
/* 1109 */           return !this.connection.versionMeetsMinimum(5, 0, 13);
/* 1110 */         } catch (SQLException sqlEx) {
/*      */           
/* 1112 */           return false;
/*      */         } 
/*      */     } 
/* 1115 */     return false;
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
/*      */   private int[] executeBatchUsingMultiQueries(boolean multiQueriesEnabled, int nbrCommands, int individualStatementTimeout) throws SQLException {
/* 1130 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 1132 */     if (!multiQueriesEnabled) {
/* 1133 */       locallyScopedConn.getIO().enableMultiQueries();
/*      */     }
/*      */     
/* 1136 */     Statement batchStmt = null;
/*      */     
/* 1138 */     CancelTask timeoutTask = null;
/*      */     
/*      */     try {
/* 1141 */       int[] updateCounts = new int[nbrCommands];
/*      */       
/* 1143 */       for (i = 0; i < nbrCommands; i++) {
/* 1144 */         updateCounts[i] = -3;
/*      */       }
/*      */       
/* 1147 */       int commandIndex = 0;
/*      */       
/* 1149 */       StringBuffer queryBuf = new StringBuffer();
/*      */       
/* 1151 */       batchStmt = locallyScopedConn.createStatement();
/*      */       
/* 1153 */       if (locallyScopedConn.getEnableQueryTimeouts() && individualStatementTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */         
/* 1156 */         timeoutTask = new CancelTask((StatementImpl)batchStmt);
/* 1157 */         locallyScopedConn.getCancelTimer().schedule(timeoutTask, individualStatementTimeout);
/*      */       } 
/*      */ 
/*      */       
/* 1161 */       int counter = 0;
/*      */       
/* 1163 */       int numberOfBytesPerChar = 1;
/*      */       
/* 1165 */       String connectionEncoding = locallyScopedConn.getEncoding();
/*      */       
/* 1167 */       if (StringUtils.startsWithIgnoreCase(connectionEncoding, "utf")) {
/* 1168 */         numberOfBytesPerChar = 3;
/* 1169 */       } else if (CharsetMapping.isMultibyteCharset(connectionEncoding)) {
/* 1170 */         numberOfBytesPerChar = 2;
/*      */       } 
/*      */       
/* 1173 */       int escapeAdjust = 1;
/*      */       
/* 1175 */       batchStmt.setEscapeProcessing(this.doEscapeProcessing);
/*      */       
/* 1177 */       if (this.doEscapeProcessing)
/*      */       {
/* 1179 */         escapeAdjust = 2;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1184 */       SQLException sqlEx = null;
/*      */       
/* 1186 */       int argumentSetsInBatchSoFar = 0;
/*      */       
/* 1188 */       for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
/* 1189 */         String nextQuery = (String)this.batchedArgs.get(commandIndex);
/*      */         
/* 1191 */         if (((queryBuf.length() + nextQuery.length()) * numberOfBytesPerChar + 1 + 4) * escapeAdjust + 32 > this.connection.getMaxAllowedPacket()) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/* 1196 */             batchStmt.execute(queryBuf.toString(), 1);
/* 1197 */           } catch (SQLException ex) {
/* 1198 */             sqlEx = handleExceptionForBatch(commandIndex, argumentSetsInBatchSoFar, updateCounts, ex);
/*      */           } 
/*      */ 
/*      */           
/* 1202 */           counter = processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
/*      */ 
/*      */           
/* 1205 */           queryBuf = new StringBuffer();
/* 1206 */           argumentSetsInBatchSoFar = 0;
/*      */         } 
/*      */         
/* 1209 */         queryBuf.append(nextQuery);
/* 1210 */         queryBuf.append(";");
/* 1211 */         argumentSetsInBatchSoFar++;
/*      */       } 
/*      */       
/* 1214 */       if (queryBuf.length() > 0) {
/*      */         try {
/* 1216 */           batchStmt.execute(queryBuf.toString(), 1);
/* 1217 */         } catch (SQLException ex) {
/* 1218 */           sqlEx = handleExceptionForBatch(commandIndex - 1, argumentSetsInBatchSoFar, updateCounts, ex);
/*      */         } 
/*      */ 
/*      */         
/* 1222 */         counter = processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
/*      */       } 
/*      */ 
/*      */       
/* 1226 */       if (timeoutTask != null) {
/* 1227 */         if (timeoutTask.caughtWhileCancelling != null) {
/* 1228 */           throw timeoutTask.caughtWhileCancelling;
/*      */         }
/*      */         
/* 1231 */         timeoutTask.cancel();
/*      */         
/* 1233 */         locallyScopedConn.getCancelTimer().purge();
/*      */         
/* 1235 */         timeoutTask = null;
/*      */       } 
/*      */       
/* 1238 */       if (sqlEx != null) {
/* 1239 */         throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1244 */       return (updateCounts != null) ? updateCounts : new int[0];
/*      */     } finally {
/* 1246 */       if (timeoutTask != null) {
/* 1247 */         timeoutTask.cancel();
/*      */         
/* 1249 */         locallyScopedConn.getCancelTimer().purge();
/*      */       } 
/*      */       
/* 1252 */       resetCancelledState();
/*      */       
/*      */       try {
/* 1255 */         if (batchStmt != null) {
/* 1256 */           batchStmt.close();
/*      */         }
/*      */       } finally {
/* 1259 */         if (!multiQueriesEnabled) {
/* 1260 */           locallyScopedConn.getIO().disableMultiQueries();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int processMultiCountsAndKeys(StatementImpl batchedStatement, int updateCountCounter, int[] updateCounts) throws SQLException {
/* 1269 */     updateCounts[updateCountCounter++] = batchedStatement.getUpdateCount();
/*      */     
/* 1271 */     boolean doGenKeys = (this.batchedGeneratedKeys != null);
/*      */     
/* 1273 */     byte[][] row = (byte[][])null;
/*      */     
/* 1275 */     if (doGenKeys) {
/* 1276 */       long generatedKey = batchedStatement.getLastInsertID();
/*      */       
/* 1278 */       row = new byte[1][];
/* 1279 */       row[0] = Long.toString(generatedKey).getBytes();
/* 1280 */       this.batchedGeneratedKeys.add(new ByteArrayRow(row, getExceptionInterceptor()));
/*      */     } 
/*      */ 
/*      */     
/* 1284 */     while (batchedStatement.getMoreResults() || batchedStatement.getUpdateCount() != -1) {
/* 1285 */       updateCounts[updateCountCounter++] = batchedStatement.getUpdateCount();
/*      */       
/* 1287 */       if (doGenKeys) {
/* 1288 */         long generatedKey = batchedStatement.getLastInsertID();
/*      */         
/* 1290 */         row = new byte[1][];
/* 1291 */         row[0] = Long.toString(generatedKey).getBytes();
/* 1292 */         this.batchedGeneratedKeys.add(new ByteArrayRow(row, getExceptionInterceptor()));
/*      */       } 
/*      */     } 
/*      */     
/* 1296 */     return updateCountCounter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SQLException handleExceptionForBatch(int endOfBatchIndex, int numValuesPerBatch, int[] updateCounts, SQLException ex) throws BatchUpdateException {
/*      */     SQLException sqlEx;
/* 1304 */     for (int j = endOfBatchIndex; j > endOfBatchIndex - numValuesPerBatch; j--) {
/* 1305 */       updateCounts[j] = -3;
/*      */     }
/*      */     
/* 1308 */     if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !hasDeadlockOrTimeoutRolledBackTx(ex)) {
/*      */ 
/*      */ 
/*      */       
/* 1312 */       sqlEx = ex;
/*      */     } else {
/* 1314 */       int[] newUpdateCounts = new int[endOfBatchIndex];
/* 1315 */       System.arraycopy(updateCounts, 0, newUpdateCounts, 0, endOfBatchIndex);
/*      */ 
/*      */       
/* 1318 */       throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1323 */     return sqlEx;
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
/*      */   public ResultSet executeQuery(String sql) throws SQLException {
/* 1339 */     checkClosed();
/*      */     
/* 1341 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 1343 */     synchronized (locallyScopedConn.getMutex()) {
/* 1344 */       this.retrieveGeneratedKeys = false;
/*      */       
/* 1346 */       resetCancelledState();
/*      */       
/* 1348 */       checkNullOrEmptyQuery(sql);
/*      */       
/* 1350 */       boolean doStreaming = createStreamingResultSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1360 */       if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0)
/*      */       {
/* 1362 */         executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());
/*      */       }
/*      */ 
/*      */       
/* 1366 */       if (this.doEscapeProcessing) {
/* 1367 */         Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, locallyScopedConn.serverSupportsConvertFn(), this.connection);
/*      */ 
/*      */         
/* 1370 */         if (escapedSqlResult instanceof String) {
/* 1371 */           sql = (String)escapedSqlResult;
/*      */         } else {
/* 1373 */           sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
/*      */         } 
/*      */       } 
/*      */       
/* 1377 */       char firstStatementChar = StringUtils.firstNonWsCharUc(sql, findStartOfStatement(sql));
/*      */ 
/*      */       
/* 1380 */       if (sql.charAt(0) == '/' && 
/* 1381 */         sql.startsWith("/* ping */")) {
/* 1382 */         doPingInstead();
/*      */         
/* 1384 */         return this.results;
/*      */       } 
/*      */ 
/*      */       
/* 1388 */       checkForDml(sql, firstStatementChar);
/*      */       
/* 1390 */       if (this.results != null && 
/* 1391 */         !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
/* 1392 */         this.results.realClose(false);
/*      */       }
/*      */ 
/*      */       
/* 1396 */       CachedResultSetMetaData cachedMetaData = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1405 */       if (useServerFetch()) {
/* 1406 */         this.results = createResultSetUsingServerFetch(sql);
/*      */         
/* 1408 */         return this.results;
/*      */       } 
/*      */       
/* 1411 */       CancelTask timeoutTask = null;
/*      */       
/* 1413 */       String oldCatalog = null;
/*      */       
/*      */       try {
/* 1416 */         if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */           
/* 1419 */           timeoutTask = new CancelTask(this);
/* 1420 */           locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
/*      */         } 
/*      */ 
/*      */         
/* 1424 */         if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/* 1425 */           oldCatalog = locallyScopedConn.getCatalog();
/* 1426 */           locallyScopedConn.setCatalog(this.currentCatalog);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1433 */         Field[] cachedFields = null;
/*      */         
/* 1435 */         if (locallyScopedConn.getCacheResultSetMetadata()) {
/* 1436 */           cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
/*      */           
/* 1438 */           if (cachedMetaData != null) {
/* 1439 */             cachedFields = cachedMetaData.fields;
/*      */           }
/*      */         } 
/*      */         
/* 1443 */         if (locallyScopedConn.useMaxRows()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1448 */           if (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1) {
/* 1449 */             this.results = locallyScopedConn.execSQL(this, sql, this.maxRows, null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields);
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1455 */             if (this.maxRows <= 0) {
/* 1456 */               executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */             } else {
/*      */               
/* 1459 */               executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
/*      */             } 
/*      */ 
/*      */             
/* 1463 */             this.results = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1469 */             if (oldCatalog != null) {
/* 1470 */               locallyScopedConn.setCatalog(oldCatalog);
/*      */             }
/*      */           } 
/*      */         } else {
/* 1474 */           this.results = locallyScopedConn.execSQL(this, sql, -1, null, this.resultSetType, this.resultSetConcurrency, doStreaming, this.currentCatalog, cachedFields);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1480 */         if (timeoutTask != null) {
/* 1481 */           if (timeoutTask.caughtWhileCancelling != null) {
/* 1482 */             throw timeoutTask.caughtWhileCancelling;
/*      */           }
/*      */           
/* 1485 */           timeoutTask.cancel();
/*      */           
/* 1487 */           locallyScopedConn.getCancelTimer().purge();
/*      */           
/* 1489 */           timeoutTask = null;
/*      */         } 
/*      */         
/* 1492 */         synchronized (this.cancelTimeoutMutex) {
/* 1493 */           if (this.wasCancelled) {
/* 1494 */             MySQLStatementCancelledException mySQLStatementCancelledException = null;
/*      */             
/* 1496 */             if (this.wasCancelledByTimeout) {
/* 1497 */               MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException();
/*      */             } else {
/* 1499 */               mySQLStatementCancelledException = new MySQLStatementCancelledException();
/*      */             } 
/*      */             
/* 1502 */             resetCancelledState();
/*      */             
/* 1504 */             throw mySQLStatementCancelledException;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1508 */         if (timeoutTask != null) {
/* 1509 */           timeoutTask.cancel();
/*      */           
/* 1511 */           locallyScopedConn.getCancelTimer().purge();
/*      */         } 
/*      */         
/* 1514 */         if (oldCatalog != null) {
/* 1515 */           locallyScopedConn.setCatalog(oldCatalog);
/*      */         }
/*      */       } 
/*      */       
/* 1519 */       this.lastInsertId = this.results.getUpdateID();
/*      */       
/* 1521 */       if (cachedMetaData != null) {
/* 1522 */         locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
/*      */       
/*      */       }
/* 1525 */       else if (this.connection.getCacheResultSetMetadata()) {
/* 1526 */         locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1531 */       return this.results;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doPingInstead() throws SQLException {
/* 1536 */     if (this.pingTarget != null) {
/* 1537 */       this.pingTarget.doPing();
/*      */     } else {
/* 1539 */       this.connection.ping();
/*      */     } 
/*      */     
/* 1542 */     ResultSetInternalMethods fakeSelectOneResultSet = generatePingResultSet();
/* 1543 */     this.results = fakeSelectOneResultSet;
/*      */   }
/*      */   
/*      */   protected ResultSetInternalMethods generatePingResultSet() throws SQLException {
/* 1547 */     Field[] fields = { new Field(null, "1", -5, true) };
/* 1548 */     ArrayList rows = new ArrayList();
/* 1549 */     byte[] colVal = { 49 };
/*      */     
/* 1551 */     rows.add(new ByteArrayRow(new byte[][] { colVal }, getExceptionInterceptor()));
/*      */     
/* 1553 */     return (ResultSetInternalMethods)DatabaseMetaData.buildResultSet(fields, rows, this.connection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1559 */   protected void executeSimpleNonQuery(MySQLConnection c, String nonQuery) throws SQLException { c.execSQL(this, nonQuery, -1, null, 1003, 1007, false, this.currentCatalog, null, false).close(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1581 */   public int executeUpdate(String sql) throws SQLException { return executeUpdate(sql, false, false); }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int executeUpdate(String sql, boolean isBatch, boolean returnGeneratedKeys) throws SQLException {
/* 1586 */     checkClosed();
/*      */     
/* 1588 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 1590 */     char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
/*      */ 
/*      */     
/* 1593 */     ResultSetInternalMethods rs = null;
/*      */     
/* 1595 */     synchronized (locallyScopedConn.getMutex()) {
/* 1596 */       this.retrieveGeneratedKeys = returnGeneratedKeys;
/*      */       
/* 1598 */       resetCancelledState();
/*      */       
/* 1600 */       checkNullOrEmptyQuery(sql);
/*      */       
/* 1602 */       if (this.doEscapeProcessing) {
/* 1603 */         Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.connection.serverSupportsConvertFn(), this.connection);
/*      */ 
/*      */         
/* 1606 */         if (escapedSqlResult instanceof String) {
/* 1607 */           sql = (String)escapedSqlResult;
/*      */         } else {
/* 1609 */           sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
/*      */         } 
/*      */       } 
/*      */       
/* 1613 */       if (locallyScopedConn.isReadOnly()) {
/* 1614 */         throw SQLError.createSQLException(Messages.getString("Statement.42") + Messages.getString("Statement.43"), "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1620 */       if (StringUtils.startsWithIgnoreCaseAndWs(sql, "select")) {
/* 1621 */         throw SQLError.createSQLException(Messages.getString("Statement.46"), "01S03", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1626 */       if (this.results != null && 
/* 1627 */         !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
/* 1628 */         this.results.realClose(false);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1636 */       CancelTask timeoutTask = null;
/*      */       
/* 1638 */       String oldCatalog = null;
/*      */       
/*      */       try {
/* 1641 */         if (locallyScopedConn.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
/*      */ 
/*      */           
/* 1644 */           timeoutTask = new CancelTask(this);
/* 1645 */           locallyScopedConn.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
/*      */         } 
/*      */ 
/*      */         
/* 1649 */         if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/* 1650 */           oldCatalog = locallyScopedConn.getCatalog();
/* 1651 */           locallyScopedConn.setCatalog(this.currentCatalog);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1657 */         if (locallyScopedConn.useMaxRows()) {
/* 1658 */           executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */         }
/*      */ 
/*      */         
/* 1662 */         rs = locallyScopedConn.execSQL(this, sql, -1, null, 1003, 1007, false, this.currentCatalog, null, isBatch);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1669 */         if (timeoutTask != null) {
/* 1670 */           if (timeoutTask.caughtWhileCancelling != null) {
/* 1671 */             throw timeoutTask.caughtWhileCancelling;
/*      */           }
/*      */           
/* 1674 */           timeoutTask.cancel();
/*      */           
/* 1676 */           locallyScopedConn.getCancelTimer().purge();
/*      */           
/* 1678 */           timeoutTask = null;
/*      */         } 
/*      */         
/* 1681 */         synchronized (this.cancelTimeoutMutex) {
/* 1682 */           if (this.wasCancelled) {
/* 1683 */             MySQLStatementCancelledException mySQLStatementCancelledException = null;
/*      */             
/* 1685 */             if (this.wasCancelledByTimeout) {
/* 1686 */               MySQLTimeoutException mySQLTimeoutException = new MySQLTimeoutException();
/*      */             } else {
/* 1688 */               mySQLStatementCancelledException = new MySQLStatementCancelledException();
/*      */             } 
/*      */             
/* 1691 */             resetCancelledState();
/*      */             
/* 1693 */             throw mySQLStatementCancelledException;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1697 */         if (timeoutTask != null) {
/* 1698 */           timeoutTask.cancel();
/*      */           
/* 1700 */           locallyScopedConn.getCancelTimer().purge();
/*      */         } 
/*      */         
/* 1703 */         if (oldCatalog != null) {
/* 1704 */           locallyScopedConn.setCatalog(oldCatalog);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1709 */     this.results = rs;
/*      */     
/* 1711 */     rs.setFirstCharOfQuery(firstStatementChar);
/*      */     
/* 1713 */     this.updateCount = rs.getUpdateCount();
/*      */     
/* 1715 */     int truncatedUpdateCount = 0;
/*      */     
/* 1717 */     if (this.updateCount > 2147483647L) {
/* 1718 */       truncatedUpdateCount = Integer.MAX_VALUE;
/*      */     } else {
/* 1720 */       truncatedUpdateCount = (int)this.updateCount;
/*      */     } 
/*      */     
/* 1723 */     this.lastInsertId = rs.getUpdateID();
/*      */     
/* 1725 */     return truncatedUpdateCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeUpdate(String sql, int returnGeneratedKeys) throws SQLException {
/* 1734 */     if (returnGeneratedKeys == 1) {
/* 1735 */       checkClosed();
/*      */       
/* 1737 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/* 1739 */       synchronized (locallyScopedConn.getMutex()) {
/*      */ 
/*      */ 
/*      */         
/* 1743 */         boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
/*      */         
/* 1745 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/* 1748 */           return executeUpdate(sql, false, true);
/*      */         } finally {
/* 1750 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1755 */     return executeUpdate(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeUpdate(String sql, int[] generatedKeyIndices) throws SQLException {
/* 1763 */     if (generatedKeyIndices != null && generatedKeyIndices.length > 0) {
/* 1764 */       checkClosed();
/*      */       
/* 1766 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/* 1768 */       synchronized (locallyScopedConn.getMutex()) {
/*      */ 
/*      */ 
/*      */         
/* 1772 */         boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
/*      */         
/* 1774 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/* 1777 */           return executeUpdate(sql, false, true);
/*      */         } finally {
/* 1779 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1784 */     return executeUpdate(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int executeUpdate(String sql, String[] generatedKeyNames) throws SQLException {
/* 1792 */     if (generatedKeyNames != null && generatedKeyNames.length > 0) {
/* 1793 */       checkClosed();
/*      */       
/* 1795 */       MySQLConnection locallyScopedConn = this.connection;
/*      */       
/* 1797 */       synchronized (locallyScopedConn.getMutex()) {
/*      */ 
/*      */ 
/*      */         
/* 1801 */         boolean readInfoMsgState = this.connection.isReadInfoMsgEnabled();
/*      */         
/* 1803 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */         
/*      */         try {
/* 1806 */           return executeUpdate(sql, false, true);
/*      */         } finally {
/* 1808 */           locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1813 */     return executeUpdate(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Calendar getCalendarInstanceForSessionOrNew() {
/* 1821 */     if (this.connection != null) {
/* 1822 */       return this.connection.getCalendarInstanceForSessionOrNew();
/*      */     }
/*      */     
/* 1825 */     return new GregorianCalendar();
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
/* 1838 */   public Connection getConnection() throws SQLException { return this.connection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1850 */   public int getFetchDirection() { return 1000; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1862 */   public int getFetchSize() { return this.fetchSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSet getGeneratedKeys() throws SQLException {
/* 1875 */     if (!this.retrieveGeneratedKeys) {
/* 1876 */       throw SQLError.createSQLException(Messages.getString("Statement.GeneratedKeysNotRequested"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/* 1879 */     if (this.batchedGeneratedKeys == null) {
/* 1880 */       if (this.lastQueryIsOnDupKeyUpdate) {
/* 1881 */         return getGeneratedKeysInternal(1);
/*      */       }
/* 1883 */       return getGeneratedKeysInternal();
/*      */     } 
/*      */     
/* 1886 */     Field[] fields = new Field[1];
/* 1887 */     fields[0] = new Field("", "GENERATED_KEY", -5, 17);
/* 1888 */     fields[0].setConnection(this.connection);
/*      */     
/* 1890 */     return ResultSetImpl.getInstance(this.currentCatalog, fields, new RowDataStatic(this.batchedGeneratedKeys), this.connection, this, false);
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
/*      */   protected ResultSet getGeneratedKeysInternal() throws SQLException {
/* 1902 */     int numKeys = getUpdateCount();
/* 1903 */     return getGeneratedKeysInternal(numKeys);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ResultSet getGeneratedKeysInternal(int numKeys) throws SQLException {
/* 1908 */     Field[] fields = new Field[1];
/* 1909 */     fields[0] = new Field("", "GENERATED_KEY", -5, 17);
/* 1910 */     fields[0].setConnection(this.connection);
/* 1911 */     fields[0].setUseOldNameMetadata(true);
/*      */     
/* 1913 */     ArrayList rowSet = new ArrayList();
/*      */     
/* 1915 */     long beginAt = getLastInsertID();
/*      */     
/* 1917 */     if (beginAt < 0L) {
/* 1918 */       fields[0].setUnsigned();
/*      */     }
/*      */     
/* 1921 */     if (this.results != null) {
/* 1922 */       String serverInfo = this.results.getServerInfo();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1928 */       if (numKeys > 0 && this.results.getFirstCharOfQuery() == 'R' && serverInfo != null && serverInfo.length() > 0)
/*      */       {
/* 1930 */         numKeys = getRecordCountFromInfo(serverInfo);
/*      */       }
/*      */       
/* 1933 */       if (beginAt != 0L && numKeys > 0) {
/* 1934 */         for (int i = 0; i < numKeys; i++) {
/* 1935 */           byte[][] row = new byte[1][];
/* 1936 */           if (beginAt > 0L) {
/* 1937 */             row[0] = Long.toString(beginAt).getBytes();
/*      */           } else {
/* 1939 */             byte[] asBytes = new byte[8];
/* 1940 */             asBytes[7] = (byte)(int)(beginAt & 0xFFL);
/* 1941 */             asBytes[6] = (byte)(int)(beginAt >>> 8);
/* 1942 */             asBytes[5] = (byte)(int)(beginAt >>> 16);
/* 1943 */             asBytes[4] = (byte)(int)(beginAt >>> 24);
/* 1944 */             asBytes[3] = (byte)(int)(beginAt >>> 32);
/* 1945 */             asBytes[2] = (byte)(int)(beginAt >>> 40);
/* 1946 */             asBytes[1] = (byte)(int)(beginAt >>> 48);
/* 1947 */             asBytes[0] = (byte)(int)(beginAt >>> 56);
/*      */             
/* 1949 */             BigInteger val = new BigInteger(true, asBytes);
/*      */             
/* 1951 */             row[0] = val.toString().getBytes();
/*      */           } 
/* 1953 */           rowSet.add(new ByteArrayRow(row, getExceptionInterceptor()));
/* 1954 */           beginAt += this.connection.getAutoIncrementIncrement();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1959 */     ResultSetImpl gkRs = ResultSetImpl.getInstance(this.currentCatalog, fields, new RowDataStatic(rowSet), this.connection, this, false);
/*      */ 
/*      */     
/* 1962 */     this.openResults.add(gkRs);
/*      */     
/* 1964 */     return gkRs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1973 */   protected int getId() { return this.statementId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1990 */   public long getLastInsertID() { return this.lastInsertId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLongUpdateCount() {
/* 2006 */     if (this.results == null) {
/* 2007 */       return -1L;
/*      */     }
/*      */     
/* 2010 */     if (this.results.reallyResult()) {
/* 2011 */       return -1L;
/*      */     }
/*      */     
/* 2014 */     return this.updateCount;
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
/* 2029 */   public int getMaxFieldSize() { return this.maxFieldSize; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxRows() {
/* 2043 */     if (this.maxRows <= 0) {
/* 2044 */       return 0;
/*      */     }
/*      */     
/* 2047 */     return this.maxRows;
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
/* 2060 */   public boolean getMoreResults() { return getMoreResults(1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getMoreResults(int current) throws SQLException {
/* 2068 */     if (this.results == null) {
/* 2069 */       return false;
/*      */     }
/*      */     
/* 2072 */     boolean streamingMode = createStreamingResultSet();
/*      */     
/* 2074 */     if (streamingMode && 
/* 2075 */       this.results.reallyResult()) {
/* 2076 */       while (this.results.next());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2081 */     ResultSetInternalMethods nextResultSet = this.results.getNextResultSet();
/*      */     
/* 2083 */     switch (current) {
/*      */       
/*      */       case 1:
/* 2086 */         if (this.results != null) {
/* 2087 */           if (!streamingMode) {
/* 2088 */             this.results.close();
/*      */           }
/*      */           
/* 2091 */           this.results.clearNextResult();
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 2098 */         if (this.results != null) {
/* 2099 */           if (!streamingMode) {
/* 2100 */             this.results.close();
/*      */           }
/*      */           
/* 2103 */           this.results.clearNextResult();
/*      */         } 
/*      */         
/* 2106 */         closeAllOpenResults();
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 2111 */         if (!this.connection.getDontTrackOpenResources()) {
/* 2112 */           this.openResults.add(this.results);
/*      */         }
/*      */         
/* 2115 */         this.results.clearNextResult();
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 2120 */         throw SQLError.createSQLException(Messages.getString("Statement.19"), "S1009", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2125 */     this.results = nextResultSet;
/*      */     
/* 2127 */     if (this.results == null) {
/* 2128 */       this.updateCount = -1L;
/* 2129 */       this.lastInsertId = -1L;
/* 2130 */     } else if (this.results.reallyResult()) {
/* 2131 */       this.updateCount = -1L;
/* 2132 */       this.lastInsertId = -1L;
/*      */     } else {
/* 2134 */       this.updateCount = this.results.getUpdateCount();
/* 2135 */       this.lastInsertId = this.results.getUpdateID();
/*      */     } 
/*      */     
/* 2138 */     return (this.results != null && this.results.reallyResult());
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
/* 2153 */   public int getQueryTimeout() { return this.timeoutInMillis / 1000; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRecordCountFromInfo(String serverInfo) throws SQLException {
/* 2165 */     StringBuffer recordsBuf = new StringBuffer();
/* 2166 */     int recordsCount = 0;
/* 2167 */     int duplicatesCount = 0;
/*      */     
/* 2169 */     char c = Character.MIN_VALUE;
/*      */     
/* 2171 */     int length = serverInfo.length();
/* 2172 */     int i = 0;
/*      */     
/* 2174 */     for (; i < length; i++) {
/* 2175 */       c = serverInfo.charAt(i);
/*      */       
/* 2177 */       if (Character.isDigit(c)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 2182 */     recordsBuf.append(c);
/* 2183 */     i++;
/*      */     
/* 2185 */     for (; i < length; i++) {
/* 2186 */       c = serverInfo.charAt(i);
/*      */       
/* 2188 */       if (!Character.isDigit(c)) {
/*      */         break;
/*      */       }
/*      */       
/* 2192 */       recordsBuf.append(c);
/*      */     } 
/*      */     
/* 2195 */     recordsCount = Integer.parseInt(recordsBuf.toString());
/*      */     
/* 2197 */     StringBuffer duplicatesBuf = new StringBuffer();
/*      */     
/* 2199 */     for (; i < length; i++) {
/* 2200 */       c = serverInfo.charAt(i);
/*      */       
/* 2202 */       if (Character.isDigit(c)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 2207 */     duplicatesBuf.append(c);
/* 2208 */     i++;
/*      */     
/* 2210 */     for (; i < length; i++) {
/* 2211 */       c = serverInfo.charAt(i);
/*      */       
/* 2213 */       if (!Character.isDigit(c)) {
/*      */         break;
/*      */       }
/*      */       
/* 2217 */       duplicatesBuf.append(c);
/*      */     } 
/*      */     
/* 2220 */     duplicatesCount = Integer.parseInt(duplicatesBuf.toString());
/*      */     
/* 2222 */     return recordsCount - duplicatesCount;
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
/* 2235 */   public ResultSet getResultSet() throws SQLException { return (this.results != null && this.results.reallyResult()) ? this.results : null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2248 */   public int getResultSetConcurrency() { return this.resultSetConcurrency; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2255 */   public int getResultSetHoldability() { return 1; }
/*      */ 
/*      */ 
/*      */   
/* 2259 */   protected ResultSetInternalMethods getResultSetInternal() throws SQLException { return this.results; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2271 */   public int getResultSetType() { return this.resultSetType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUpdateCount() {
/* 2285 */     if (this.results == null) {
/* 2286 */       return -1;
/*      */     }
/*      */     
/* 2289 */     if (this.results.reallyResult()) {
/* 2290 */       return -1;
/*      */     }
/*      */     
/* 2293 */     int truncatedUpdateCount = 0;
/*      */     
/* 2295 */     if (this.results.getUpdateCount() > 2147483647L) {
/* 2296 */       truncatedUpdateCount = Integer.MAX_VALUE;
/*      */     } else {
/* 2298 */       truncatedUpdateCount = (int)this.results.getUpdateCount();
/*      */     } 
/*      */     
/* 2301 */     return truncatedUpdateCount;
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
/*      */   public SQLWarning getWarnings() throws SQLException {
/* 2326 */     checkClosed();
/*      */     
/* 2328 */     if (this.connection != null && !this.connection.isClosed() && this.connection.versionMeetsMinimum(4, 1, 0)) {
/*      */       
/* 2330 */       SQLWarning pendingWarningsFromServer = SQLError.convertShowWarningsToSQLWarnings(this.connection);
/*      */ 
/*      */       
/* 2333 */       if (this.warningChain != null) {
/* 2334 */         this.warningChain.setNextWarning(pendingWarningsFromServer);
/*      */       } else {
/* 2336 */         this.warningChain = pendingWarningsFromServer;
/*      */       } 
/*      */       
/* 2339 */       return this.warningChain;
/*      */     } 
/*      */     
/* 2342 */     return this.warningChain;
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
/* 2356 */     if (this.isClosed) {
/*      */       return;
/*      */     }
/*      */     
/* 2360 */     if (this.useUsageAdvisor && 
/* 2361 */       !calledExplicitly) {
/* 2362 */       String message = Messages.getString("Statement.63") + Messages.getString("Statement.64");
/*      */ 
/*      */       
/* 2365 */       this.eventSink.consumeEvent(new ProfilerEvent(false, "", this.currentCatalog, this.connectionId, getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2375 */     if (closeOpenResults) {
/* 2376 */       closeOpenResults = !this.holdResultsOpenOverClose;
/*      */     }
/*      */     
/* 2379 */     if (closeOpenResults) {
/* 2380 */       if (this.results != null) {
/*      */         
/*      */         try {
/* 2383 */           this.results.close();
/* 2384 */         } catch (Exception ex) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2389 */       closeAllOpenResults();
/*      */     } 
/*      */     
/* 2392 */     if (this.connection != null) {
/* 2393 */       if (this.maxRowsChanged) {
/* 2394 */         this.connection.unsetMaxRows(this);
/*      */       }
/*      */       
/* 2397 */       if (!this.connection.getDontTrackOpenResources()) {
/* 2398 */         this.connection.unregisterStatement(this);
/*      */       }
/*      */     } 
/*      */     
/* 2402 */     this.isClosed = true;
/*      */     
/* 2404 */     this.results = null;
/* 2405 */     this.connection = null;
/* 2406 */     this.warningChain = null;
/* 2407 */     this.openResults = null;
/* 2408 */     this.batchedGeneratedKeys = null;
/* 2409 */     this.localInfileInputStream = null;
/* 2410 */     this.pingTarget = null;
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
/*      */   public void setCursorName(String name) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2446 */   public void setEscapeProcessing(boolean enable) throws SQLException { this.doEscapeProcessing = enable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2463 */     switch (direction) {
/*      */       case 1000:
/*      */       case 1001:
/*      */       case 1002:
/*      */         break;
/*      */       
/*      */       default:
/* 2470 */         throw SQLError.createSQLException(Messages.getString("Statement.5"), "S1009", getExceptionInterceptor());
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
/*      */   public void setFetchSize(int rows) throws SQLException {
/* 2491 */     if ((rows < 0 && rows != Integer.MIN_VALUE) || (this.maxRows != 0 && this.maxRows != -1 && rows > getMaxRows()))
/*      */     {
/*      */       
/* 2494 */       throw SQLError.createSQLException(Messages.getString("Statement.7"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2499 */     this.fetchSize = rows;
/*      */   }
/*      */ 
/*      */   
/* 2503 */   public void setHoldResultsOpenOverClose(boolean holdResultsOpenOverClose) throws SQLException { this.holdResultsOpenOverClose = holdResultsOpenOverClose; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxFieldSize(int max) throws SQLException {
/* 2516 */     if (max < 0) {
/* 2517 */       throw SQLError.createSQLException(Messages.getString("Statement.11"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2522 */     int maxBuf = (this.connection != null) ? this.connection.getMaxAllowedPacket() : MysqlIO.getMaxBuf();
/*      */ 
/*      */     
/* 2525 */     if (max > maxBuf) {
/* 2526 */       throw SQLError.createSQLException(Messages.getString("Statement.13", new Object[] { Constants.longValueOf(maxBuf) }), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2532 */     this.maxFieldSize = max;
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
/*      */   public void setMaxRows(int max) throws SQLException {
/* 2547 */     if (max > 50000000 || max < 0) {
/* 2548 */       throw SQLError.createSQLException(Messages.getString("Statement.15") + max + " > " + 50000000 + ".", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2555 */     if (max == 0) {
/* 2556 */       max = -1;
/*      */     }
/*      */     
/* 2559 */     this.maxRows = max;
/* 2560 */     this.maxRowsChanged = true;
/*      */     
/* 2562 */     if (this.maxRows == -1) {
/* 2563 */       this.connection.unsetMaxRows(this);
/* 2564 */       this.maxRowsChanged = false;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2571 */       this.connection.maxRowsChanged(this);
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
/*      */   public void setQueryTimeout(int seconds) throws SQLException {
/* 2585 */     if (seconds < 0) {
/* 2586 */       throw SQLError.createSQLException(Messages.getString("Statement.21"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2591 */     this.timeoutInMillis = seconds * 1000;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2601 */   void setResultSetConcurrency(int concurrencyFlag) throws SQLException { this.resultSetConcurrency = concurrencyFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2611 */   void setResultSetType(int typeFlag) throws SQLException { this.resultSetType = typeFlag; }
/*      */ 
/*      */   
/*      */   protected void getBatchedGeneratedKeys(Statement batchedStatement) throws SQLException {
/* 2615 */     if (this.retrieveGeneratedKeys) {
/* 2616 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 2619 */         rs = batchedStatement.getGeneratedKeys();
/*      */         
/* 2621 */         while (rs.next()) {
/* 2622 */           this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor()));
/*      */         } 
/*      */       } finally {
/*      */         
/* 2626 */         if (rs != null) {
/* 2627 */           rs.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void getBatchedGeneratedKeys(int maxKeys) throws SQLException {
/* 2634 */     if (this.retrieveGeneratedKeys) {
/* 2635 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 2638 */         if (maxKeys == 0) {
/* 2639 */           rs = getGeneratedKeysInternal();
/*      */         } else {
/* 2641 */           rs = getGeneratedKeysInternal(maxKeys);
/*      */         } 
/* 2643 */         while (rs.next()) {
/* 2644 */           this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor()));
/*      */         } 
/*      */       } finally {
/*      */         
/* 2648 */         if (rs != null) {
/* 2649 */           rs.close();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2660 */   private boolean useServerFetch() { return (this.connection.isCursorFetchEnabled() && this.fetchSize > 0 && this.resultSetConcurrency == 1007 && this.resultSetType == 1003); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2666 */   public boolean isClosed() { return this.isClosed; }
/*      */ 
/*      */   
/*      */   private boolean isPoolable = true;
/*      */   private InputStream localInfileInputStream;
/*      */   
/* 2672 */   public boolean isPoolable() { return this.isPoolable; }
/*      */ 
/*      */ 
/*      */   
/* 2676 */   public void setPoolable(boolean poolable) throws SQLException { this.isPoolable = poolable; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWrapperFor(Class iface) throws SQLException {
/* 2695 */     checkClosed();
/*      */ 
/*      */ 
/*      */     
/* 2699 */     return iface.isInstance(this);
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
/*      */   public Object unwrap(Class iface) throws SQLException {
/*      */     try {
/* 2720 */       return Util.cast(iface, this);
/* 2721 */     } catch (ClassCastException cce) {
/* 2722 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int findStartOfStatement(String sql) throws SQLException {
/* 2728 */     int statementStartPos = 0;
/*      */     
/* 2730 */     if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
/* 2731 */       statementStartPos = sql.indexOf("*/");
/*      */       
/* 2733 */       if (statementStartPos == -1) {
/* 2734 */         statementStartPos = 0;
/*      */       } else {
/* 2736 */         statementStartPos += 2;
/*      */       } 
/* 2738 */     } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
/*      */       
/* 2740 */       statementStartPos = sql.indexOf('\n');
/*      */       
/* 2742 */       if (statementStartPos == -1) {
/* 2743 */         statementStartPos = sql.indexOf('\r');
/*      */         
/* 2745 */         if (statementStartPos == -1) {
/* 2746 */           statementStartPos = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2751 */     return statementStartPos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2757 */   public InputStream getLocalInfileInputStream() { return this.localInfileInputStream; }
/*      */ 
/*      */ 
/*      */   
/* 2761 */   public void setLocalInfileInputStream(InputStream stream) { this.localInfileInputStream = stream; }
/*      */ 
/*      */ 
/*      */   
/* 2765 */   public void setPingTarget(PingTarget pingTarget) { this.pingTarget = pingTarget; }
/*      */ 
/*      */ 
/*      */   
/* 2769 */   public ExceptionInterceptor getExceptionInterceptor() { return this.exceptionInterceptor; }
/*      */ 
/*      */ 
/*      */   
/* 2773 */   protected boolean containsOnDuplicateKeyInString(String sql) throws SQLException { return (getOnDuplicateKeyLocation(sql) != -1); }
/*      */ 
/*      */ 
/*      */   
/* 2777 */   protected int getOnDuplicateKeyLocation(String sql) throws SQLException { return StringUtils.indexOfIgnoreCaseRespectMarker(0, sql, " ON DUPLICATE KEY UPDATE ", "\"'`", "\"'`", !this.connection.isNoBackslashEscapesSet()); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\StatementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */