/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import com.mysql.jdbc.log.LogFactory;
/*      */ import com.mysql.jdbc.log.NullLogger;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import com.mysql.jdbc.util.LRUCache;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.nio.charset.UnsupportedCharsetException;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Stack;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectionImpl
/*      */   extends ConnectionPropertiesImpl
/*      */   implements MySQLConnection
/*      */ {
/*      */   private static final String JDBC_LOCAL_CHARACTER_SET_RESULTS = "jdbc.local.character_set_results";
/*      */   
/*   82 */   public String getHost() { return this.host; }
/*      */ 
/*      */   
/*   85 */   private MySQLConnection proxy = null;
/*      */ 
/*      */   
/*   88 */   public boolean isProxySet() { return (this.proxy != null); }
/*      */ 
/*      */ 
/*      */   
/*   92 */   public void setProxy(MySQLConnection proxy) { this.proxy = proxy; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   private MySQLConnection getProxy() { return (this.proxy != null) ? this.proxy : this; }
/*      */ 
/*      */ 
/*      */   
/*  103 */   public MySQLConnection getLoadBalanceSafeProxy() { return getProxy(); }
/*      */ 
/*      */   
/*      */   class ExceptionInterceptorChain
/*      */     implements ExceptionInterceptor
/*      */   {
/*      */     List interceptors;
/*      */     
/*  111 */     ExceptionInterceptorChain(String interceptorClasses) throws SQLException { this.interceptors = Util.loadExtensions(this$0, ConnectionImpl.this.props, interceptorClasses, "Connection.BadExceptionInterceptor", this); }
/*      */ 
/*      */     
/*      */     public SQLException interceptException(SQLException sqlEx, Connection conn) {
/*  115 */       if (this.interceptors != null) {
/*  116 */         Iterator iter = this.interceptors.iterator();
/*      */         
/*  118 */         while (iter.hasNext()) {
/*  119 */           sqlEx = ((ExceptionInterceptor)iter.next()).interceptException(sqlEx, ConnectionImpl.this);
/*      */         }
/*      */       } 
/*      */       
/*  123 */       return sqlEx;
/*      */     }
/*      */     
/*      */     public void destroy() {
/*  127 */       if (this.interceptors != null) {
/*  128 */         Iterator iter = this.interceptors.iterator();
/*      */         
/*  130 */         while (iter.hasNext()) {
/*  131 */           ((ExceptionInterceptor)iter.next()).destroy();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void init(Connection conn, Properties props) throws SQLException {
/*  138 */       if (this.interceptors != null) {
/*  139 */         Iterator iter = this.interceptors.iterator();
/*      */         
/*  141 */         while (iter.hasNext()) {
/*  142 */           ((ExceptionInterceptor)iter.next()).init(conn, props);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class CompoundCacheKey
/*      */   {
/*      */     String componentOne;
/*      */ 
/*      */     
/*      */     String componentTwo;
/*      */     
/*      */     int hashCode;
/*      */ 
/*      */     
/*      */     CompoundCacheKey(String partOne, String partTwo) {
/*  161 */       this.componentOne = partOne;
/*  162 */       this.componentTwo = partTwo;
/*      */ 
/*      */ 
/*      */       
/*  166 */       this.hashCode = (((this.componentOne != null) ? this.componentOne : "") + this.componentTwo).hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  176 */       if (obj instanceof CompoundCacheKey) {
/*  177 */         CompoundCacheKey another = (CompoundCacheKey)obj;
/*      */         
/*  179 */         boolean firstPartEqual = false;
/*      */         
/*  181 */         if (this.componentOne == null) {
/*  182 */           firstPartEqual = (another.componentOne == null);
/*      */         } else {
/*  184 */           firstPartEqual = this.componentOne.equals(another.componentOne);
/*      */         } 
/*      */ 
/*      */         
/*  188 */         return (firstPartEqual && this.componentTwo.equals(another.componentTwo));
/*      */       } 
/*      */ 
/*      */       
/*  192 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  201 */     public int hashCode() { return this.hashCode; }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   private static final Object CHARSET_CONVERTER_NOT_AVAILABLE_MARKER = new Object();
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map charsetMap;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String DEFAULT_LOGGER_CLASS = "com.mysql.jdbc.log.StandardLogger";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int HISTOGRAM_BUCKETS = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String LOGGER_INSTANCE_NAME = "MySQL";
/*      */ 
/*      */ 
/*      */   
/*  229 */   private static Map mapTransIsolationNameToValue = null;
/*      */ 
/*      */   
/*  232 */   private static final Log NULL_LOGGER = new NullLogger("MySQL");
/*      */   
/*      */   private static Map roundRobinStatsMap;
/*      */   
/*  236 */   private static final Map serverCollationByUrl = new HashMap();
/*      */   
/*  238 */   private static final Map serverConfigByUrl = new HashMap();
/*      */   
/*      */   private long queryTimeCount;
/*      */   
/*      */   private double queryTimeSum;
/*      */   
/*      */   private double queryTimeSumSquares;
/*      */   
/*      */   private double queryTimeMean;
/*      */   
/*      */   private Timer cancelTimer;
/*      */   
/*      */   private List connectionLifecycleInterceptors;
/*      */   private static final Constructor JDBC_4_CONNECTION_CTOR;
/*      */   private static final int DEFAULT_RESULT_SET_TYPE = 1003;
/*      */   private static final int DEFAULT_RESULT_SET_CONCURRENCY = 1007;
/*      */   
/*      */   static  {
/*  256 */     mapTransIsolationNameToValue = new HashMap(8);
/*  257 */     mapTransIsolationNameToValue.put("READ-UNCOMMITED", Constants.integerValueOf(1));
/*      */     
/*  259 */     mapTransIsolationNameToValue.put("READ-UNCOMMITTED", Constants.integerValueOf(1));
/*      */     
/*  261 */     mapTransIsolationNameToValue.put("READ-COMMITTED", Constants.integerValueOf(2));
/*      */     
/*  263 */     mapTransIsolationNameToValue.put("REPEATABLE-READ", Constants.integerValueOf(4));
/*      */     
/*  265 */     mapTransIsolationNameToValue.put("SERIALIZABLE", Constants.integerValueOf(8));
/*      */ 
/*      */     
/*  268 */     if (Util.isJdbc4()) {
/*      */       try {
/*  270 */         JDBC_4_CONNECTION_CTOR = Class.forName("com.mysql.jdbc.JDBC4Connection").getConstructor(new Class[] { String.class, int.class, Properties.class, String.class, String.class });
/*      */ 
/*      */       
/*      */       }
/*  274 */       catch (SecurityException e) {
/*  275 */         throw new RuntimeException(e);
/*  276 */       } catch (NoSuchMethodException e) {
/*  277 */         throw new RuntimeException(e);
/*  278 */       } catch (ClassNotFoundException e) {
/*  279 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*  282 */       JDBC_4_CONNECTION_CTOR = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static SQLException appendMessageToException(SQLException sqlEx, String messageToAppend, ExceptionInterceptor interceptor) {
/*  288 */     String origMessage = sqlEx.getMessage();
/*  289 */     String sqlState = sqlEx.getSQLState();
/*  290 */     int vendorErrorCode = sqlEx.getErrorCode();
/*      */     
/*  292 */     StringBuffer messageBuf = new StringBuffer(origMessage.length() + messageToAppend.length());
/*      */     
/*  294 */     messageBuf.append(origMessage);
/*  295 */     messageBuf.append(messageToAppend);
/*      */     
/*  297 */     SQLException sqlExceptionWithNewMessage = SQLError.createSQLException(messageBuf.toString(), sqlState, vendorErrorCode, interceptor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  307 */       Method getStackTraceMethod = null;
/*  308 */       Method setStackTraceMethod = null;
/*  309 */       Object theStackTraceAsObject = null;
/*      */       
/*  311 */       Class stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
/*      */       
/*  313 */       Class stackTraceElementArrayClass = Array.newInstance(stackTraceElementClass, new int[] { 0 }).getClass();
/*      */ 
/*      */       
/*  316 */       getStackTraceMethod = Throwable.class.getMethod("getStackTrace", new Class[0]);
/*      */ 
/*      */       
/*  319 */       setStackTraceMethod = Throwable.class.getMethod("setStackTrace", new Class[] { stackTraceElementArrayClass });
/*      */ 
/*      */       
/*  322 */       if (getStackTraceMethod != null && setStackTraceMethod != null) {
/*  323 */         theStackTraceAsObject = getStackTraceMethod.invoke(sqlEx, new Object[0]);
/*      */         
/*  325 */         setStackTraceMethod.invoke(sqlExceptionWithNewMessage, new Object[] { theStackTraceAsObject });
/*      */       }
/*      */     
/*  328 */     } catch (NoClassDefFoundError noClassDefFound) {
/*      */     
/*  330 */     } catch (NoSuchMethodException noSuchMethodEx) {
/*      */     
/*  332 */     } catch (Throwable catchAll) {}
/*      */ 
/*      */ 
/*      */     
/*  336 */     return sqlExceptionWithNewMessage;
/*      */   }
/*      */   
/*      */   public Timer getCancelTimer() {
/*  340 */     if (this.cancelTimer == null) {
/*  341 */       boolean createdNamedTimer = false;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  346 */         Constructor ctr = Timer.class.getConstructor(new Class[] { String.class, boolean.class });
/*      */         
/*  348 */         this.cancelTimer = (Timer)ctr.newInstance(new Object[] { "MySQL Statement Cancellation Timer", Boolean.TRUE });
/*  349 */         createdNamedTimer = true;
/*  350 */       } catch (Throwable t) {
/*  351 */         createdNamedTimer = false;
/*      */       } 
/*      */       
/*  354 */       if (!createdNamedTimer) {
/*  355 */         this.cancelTimer = new Timer(true);
/*      */       }
/*      */     } 
/*      */     
/*  359 */     return this.cancelTimer;
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
/*      */   protected static Connection getInstance(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo, String url) throws SQLException {
/*  373 */     if (!Util.isJdbc4()) {
/*  374 */       return new ConnectionImpl(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);
/*      */     }
/*      */ 
/*      */     
/*  378 */     return (Connection)Util.handleNewInstance(JDBC_4_CONNECTION_CTOR, new Object[] { hostToConnectTo, Constants.integerValueOf(portToConnectTo), info, databaseToConnectTo, url }, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getNextRoundRobinHostIndex(String url, List hostList) {
/*  389 */     int indexRange = hostList.size();
/*      */     
/*  391 */     return (int)(Math.random() * indexRange);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean nullSafeCompare(String s1, String s2) {
/*  397 */     if (s1 == null && s2 == null) {
/*  398 */       return true;
/*      */     }
/*      */     
/*  401 */     if (s1 == null && s2 != null) {
/*  402 */       return false;
/*      */     }
/*      */     
/*  405 */     return s1.equals(s2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoCommit = true;
/*      */ 
/*      */   
/*      */   private Map cachedPreparedStatementParams;
/*      */ 
/*      */   
/*  417 */   private String characterSetMetadata = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  423 */   private String characterSetResultsOnServer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  430 */   private Map charsetConverterMap = new HashMap(CharsetMapping.getNumberOfCharsetsConfigured());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map charsetToNumBytesMap;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  440 */   private long connectionCreationTimeMillis = 0L;
/*      */ 
/*      */   
/*      */   private long connectionId;
/*      */ 
/*      */   
/*  446 */   private String database = null;
/*      */ 
/*      */   
/*  449 */   private DatabaseMetaData dbmd = null;
/*      */ 
/*      */   
/*      */   private TimeZone defaultTimeZone;
/*      */ 
/*      */   
/*      */   private ProfilerEventHandler eventSink;
/*      */ 
/*      */   
/*      */   private Throwable forceClosedReason;
/*      */ 
/*      */   
/*      */   private Throwable forcedClosedLocation;
/*      */ 
/*      */   
/*      */   private boolean hasIsolationLevels = false;
/*      */ 
/*      */   
/*      */   private boolean hasQuotedIdentifiers = false;
/*      */   
/*  469 */   private String host = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  475 */   private String[] indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
/*      */ 
/*      */   
/*  478 */   private MysqlIO io = null;
/*      */ 
/*      */   
/*      */   private boolean isClientTzUTC = false;
/*      */ 
/*      */   
/*      */   private boolean isClosed = true;
/*      */ 
/*      */   
/*      */   private boolean isInGlobalTx = false;
/*      */ 
/*      */   
/*      */   private boolean isRunningOnJDK13 = false;
/*      */   
/*  492 */   private int isolationLevel = 2;
/*      */ 
/*      */   
/*      */   private boolean isServerTzUTC = false;
/*      */   
/*  497 */   private long lastQueryFinishedTime = 0L;
/*      */ 
/*      */   
/*  500 */   private Log log = NULL_LOGGER;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  506 */   private long longestQueryTimeMs = 0L;
/*      */ 
/*      */   
/*      */   private boolean lowerCaseTableNames = false;
/*      */ 
/*      */   
/*  512 */   private long masterFailTimeMillis = 0L;
/*      */   
/*  514 */   private long maximumNumberTablesAccessed = 0L;
/*      */ 
/*      */   
/*      */   private boolean maxRowsChanged = false;
/*      */ 
/*      */   
/*      */   private long metricsLastReportedMs;
/*      */   
/*  522 */   private long minimumNumberTablesAccessed = Float.MAX_VALUE;
/*      */ 
/*      */   
/*  525 */   private final Object mutex = new Object();
/*      */ 
/*      */   
/*  528 */   private String myURL = null;
/*      */ 
/*      */   
/*      */   private boolean needsPing = false;
/*      */   
/*  533 */   private int netBufferLength = 16384;
/*      */   
/*      */   private boolean noBackslashEscapes = false;
/*      */   
/*  537 */   private long numberOfPreparedExecutes = 0L;
/*      */   
/*  539 */   private long numberOfPrepares = 0L;
/*      */   
/*  541 */   private long numberOfQueriesIssued = 0L;
/*      */   
/*  543 */   private long numberOfResultSetsCreated = 0L;
/*      */   
/*      */   private long[] numTablesMetricsHistBreakpoints;
/*      */   
/*      */   private int[] numTablesMetricsHistCounts;
/*      */   
/*  549 */   private long[] oldHistBreakpoints = null;
/*      */   
/*  551 */   private int[] oldHistCounts = null;
/*      */ 
/*      */   
/*      */   private Map openStatements;
/*      */ 
/*      */   
/*      */   private LRUCache parsedCallableStatementCache;
/*      */   
/*      */   private boolean parserKnowsUnicode = false;
/*      */   
/*  561 */   private String password = null;
/*      */ 
/*      */   
/*      */   private long[] perfMetricsHistBreakpoints;
/*      */ 
/*      */   
/*      */   private int[] perfMetricsHistCounts;
/*      */   
/*      */   private Throwable pointOfOrigin;
/*      */   
/*  571 */   private int port = 3306;
/*      */ 
/*      */   
/*  574 */   protected Properties props = null;
/*      */ 
/*      */   
/*      */   private boolean readInfoMsg = false;
/*      */ 
/*      */   
/*      */   private boolean readOnly = false;
/*      */ 
/*      */   
/*      */   protected LRUCache resultSetMetadataCache;
/*      */ 
/*      */   
/*  586 */   private TimeZone serverTimezoneTZ = null;
/*      */ 
/*      */   
/*  589 */   private Map serverVariables = null;
/*      */   
/*  591 */   private long shortestQueryTimeMs = Float.MAX_VALUE;
/*      */ 
/*      */   
/*      */   private Map statementsUsingMaxRows;
/*      */   
/*  596 */   private double totalQueryTimeMs = 0.0D;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean transactionsSupported = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map typeMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useAnsiQuotes = false;
/*      */ 
/*      */   
/*  611 */   private String user = null;
/*      */ 
/*      */   
/*      */   private boolean useServerPreparedStmts = false;
/*      */ 
/*      */   
/*      */   private LRUCache serverSideStatementCheckCache;
/*      */ 
/*      */   
/*      */   private LRUCache serverSideStatementCache;
/*      */ 
/*      */   
/*      */   private Calendar sessionCalendar;
/*      */   
/*      */   private Calendar utcCalendar;
/*      */   
/*      */   private String origHostToConnectTo;
/*      */   
/*      */   private int origPortToConnectTo;
/*      */   
/*      */   private String origDatabaseToConnectTo;
/*      */   
/*  633 */   private String errorMessageEncoding = "Cp1252";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean usePlatformCharsetConverters;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasTriedMasterFlag = false;
/*      */ 
/*      */ 
/*      */   
/*  646 */   private String statementComment = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean storesLowerCaseTableName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List statementInterceptors;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean requiresEscapingEncoder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String hostPortPair;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ConnectionImpl(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo, String url) throws SQLException {
/*  687 */     this.charsetToNumBytesMap = new HashMap();
/*      */     
/*  689 */     this.connectionCreationTimeMillis = System.currentTimeMillis();
/*  690 */     this.pointOfOrigin = new Throwable();
/*      */     
/*  692 */     if (databaseToConnectTo == null) {
/*  693 */       databaseToConnectTo = "";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  700 */     this.origHostToConnectTo = hostToConnectTo;
/*  701 */     this.origPortToConnectTo = portToConnectTo;
/*  702 */     this.origDatabaseToConnectTo = databaseToConnectTo;
/*      */     
/*      */     try {
/*  705 */       java.sql.Blob.class.getMethod("truncate", new Class[] { long.class });
/*      */       
/*  707 */       this.isRunningOnJDK13 = false;
/*  708 */     } catch (NoSuchMethodException nsme) {
/*  709 */       this.isRunningOnJDK13 = true;
/*      */     } 
/*      */     
/*  712 */     this.sessionCalendar = new GregorianCalendar();
/*  713 */     this.utcCalendar = new GregorianCalendar();
/*  714 */     this.utcCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  726 */     this.log = LogFactory.getLogger(getLogger(), "MySQL", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */     
/*  730 */     this.defaultTimeZone = Util.getDefaultTimeZone();
/*      */     
/*  732 */     if ("GMT".equalsIgnoreCase(this.defaultTimeZone.getID())) {
/*  733 */       this.isClientTzUTC = true;
/*      */     } else {
/*  735 */       this.isClientTzUTC = false;
/*      */     } 
/*      */     
/*  738 */     this.openStatements = new HashMap();
/*  739 */     this.serverVariables = new HashMap();
/*      */     
/*  741 */     if (NonRegisteringDriver.isHostPropertiesList(hostToConnectTo)) {
/*  742 */       Properties hostSpecificProps = NonRegisteringDriver.expandHostKeyValues(hostToConnectTo);
/*      */       
/*  744 */       Enumeration<?> propertyNames = hostSpecificProps.propertyNames();
/*      */       
/*  746 */       while (propertyNames.hasMoreElements()) {
/*  747 */         String propertyName = propertyNames.nextElement().toString();
/*  748 */         String propertyValue = hostSpecificProps.getProperty(propertyName);
/*      */         
/*  750 */         info.setProperty(propertyName, propertyValue);
/*      */       }
/*      */     
/*      */     }
/*  754 */     else if (hostToConnectTo == null) {
/*  755 */       this.host = "localhost";
/*  756 */       this.hostPortPair = this.host + ":" + portToConnectTo;
/*      */     } else {
/*  758 */       this.host = hostToConnectTo;
/*      */       
/*  760 */       if (hostToConnectTo.indexOf(":") == -1) {
/*  761 */         this.hostPortPair = this.host + ":" + portToConnectTo;
/*      */       } else {
/*  763 */         this.hostPortPair = this.host;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  768 */     this.port = portToConnectTo;
/*      */     
/*  770 */     this.database = databaseToConnectTo;
/*  771 */     this.myURL = url;
/*  772 */     this.user = info.getProperty("user");
/*  773 */     this.password = info.getProperty("password");
/*      */ 
/*      */     
/*  776 */     if (this.user == null || this.user.equals("")) {
/*  777 */       this.user = "";
/*      */     }
/*      */     
/*  780 */     if (this.password == null) {
/*  781 */       this.password = "";
/*      */     }
/*      */     
/*  784 */     this.props = info;
/*      */ 
/*      */ 
/*      */     
/*  788 */     initializeDriverProperties(info);
/*      */ 
/*      */     
/*      */     try {
/*  792 */       this.dbmd = getMetaData(false, false);
/*  793 */       initializeSafeStatementInterceptors();
/*  794 */       createNewIO(false);
/*  795 */       unSafeStatementInterceptors();
/*  796 */     } catch (SQLException ex) {
/*  797 */       cleanup(ex);
/*      */ 
/*      */       
/*  800 */       throw ex;
/*  801 */     } catch (Exception ex) {
/*  802 */       cleanup(ex);
/*      */       
/*  804 */       StringBuffer mesg = new StringBuffer('');
/*      */       
/*  806 */       if (!getParanoid()) {
/*  807 */         mesg.append("Cannot connect to MySQL server on ");
/*  808 */         mesg.append(this.host);
/*  809 */         mesg.append(":");
/*  810 */         mesg.append(this.port);
/*  811 */         mesg.append(".\n\n");
/*  812 */         mesg.append("Make sure that there is a MySQL server ");
/*  813 */         mesg.append("running on the machine/port you are trying ");
/*  814 */         mesg.append("to connect to and that the machine this software is running on ");
/*      */ 
/*      */         
/*  817 */         mesg.append("is able to connect to this host/port (i.e. not firewalled). ");
/*      */         
/*  819 */         mesg.append("Also make sure that the server has not been started with the --skip-networking ");
/*      */ 
/*      */         
/*  822 */         mesg.append("flag.\n\n");
/*      */       } else {
/*  824 */         mesg.append("Unable to connect to database.");
/*      */       } 
/*      */       
/*  827 */       SQLException sqlEx = SQLError.createSQLException(mesg.toString(), "08S01", getExceptionInterceptor());
/*      */ 
/*      */       
/*  830 */       sqlEx.initCause(ex);
/*      */       
/*  832 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void unSafeStatementInterceptors() {
/*  838 */     ArrayList unSafedStatementInterceptors = new ArrayList(this.statementInterceptors.size());
/*      */     
/*  840 */     for (int i = 0; i < this.statementInterceptors.size(); i++) {
/*  841 */       NoSubInterceptorWrapper wrappedInterceptor = (NoSubInterceptorWrapper)this.statementInterceptors.get(i);
/*      */       
/*  843 */       unSafedStatementInterceptors.add(wrappedInterceptor.getUnderlyingInterceptor());
/*      */     } 
/*      */     
/*  846 */     this.statementInterceptors = unSafedStatementInterceptors;
/*      */     
/*  848 */     if (this.io != null) {
/*  849 */       this.io.setStatementInterceptors(this.statementInterceptors);
/*      */     }
/*      */   }
/*      */   
/*      */   public void initializeSafeStatementInterceptors() {
/*  854 */     this.isClosed = false;
/*      */     
/*  856 */     List unwrappedInterceptors = Util.loadExtensions(this, this.props, getStatementInterceptors(), "MysqlIo.BadStatementInterceptor", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */     
/*  860 */     this.statementInterceptors = new ArrayList(unwrappedInterceptors.size());
/*      */     
/*  862 */     for (int i = 0; i < unwrappedInterceptors.size(); i++) {
/*  863 */       Object interceptor = unwrappedInterceptors.get(i);
/*      */ 
/*      */ 
/*      */       
/*  867 */       if (interceptor instanceof StatementInterceptor) {
/*  868 */         if (ReflectiveStatementInterceptorAdapter.getV2PostProcessMethod(interceptor.getClass()) != null) {
/*  869 */           this.statementInterceptors.add(new NoSubInterceptorWrapper(new ReflectiveStatementInterceptorAdapter((StatementInterceptor)interceptor)));
/*      */         } else {
/*  871 */           this.statementInterceptors.add(new NoSubInterceptorWrapper(new V1toV2StatementInterceptorAdapter((StatementInterceptor)interceptor)));
/*      */         } 
/*      */       } else {
/*  874 */         this.statementInterceptors.add(new NoSubInterceptorWrapper((StatementInterceptorV2)interceptor));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  882 */   public List getStatementInterceptorsInstances() { return this.statementInterceptors; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addToHistogram(int[] histogramCounts, long[] histogramBreakpoints, long value, int numberOfTimes, long currentLowerBound, long currentUpperBound) {
/*  888 */     if (histogramCounts == null) {
/*  889 */       createInitialHistogram(histogramBreakpoints, currentLowerBound, currentUpperBound);
/*      */     } else {
/*      */       
/*  892 */       for (int i = 0; i < 20; i++) {
/*  893 */         if (histogramBreakpoints[i] >= value) {
/*  894 */           histogramCounts[i] = histogramCounts[i] + numberOfTimes;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addToPerformanceHistogram(long value, int numberOfTimes) {
/*  903 */     checkAndCreatePerformanceHistogram();
/*      */     
/*  905 */     addToHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, value, numberOfTimes, (this.shortestQueryTimeMs == Float.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addToTablesAccessedHistogram(long value, int numberOfTimes) {
/*  912 */     checkAndCreateTablesAccessedHistogram();
/*      */     
/*  914 */     addToHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, value, numberOfTimes, (this.minimumNumberTablesAccessed == Float.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
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
/*      */   private void buildCollationMapping() {
/*  929 */     if (versionMeetsMinimum(4, 1, 0)) {
/*      */       
/*  931 */       TreeMap sortedCollationMap = null;
/*      */       
/*  933 */       if (getCacheServerConfiguration()) {
/*  934 */         synchronized (serverConfigByUrl) {
/*  935 */           sortedCollationMap = (TreeMap)serverCollationByUrl.get(getURL());
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  940 */       Statement stmt = null;
/*  941 */       ResultSet results = null;
/*      */       
/*      */       try {
/*  944 */         if (sortedCollationMap == null) {
/*  945 */           sortedCollationMap = new TreeMap();
/*      */           
/*  947 */           stmt = getMetadataSafeStatement();
/*      */           
/*  949 */           results = stmt.executeQuery("SHOW COLLATION");
/*      */ 
/*      */           
/*  952 */           while (results.next()) {
/*  953 */             String charsetName = results.getString(2);
/*  954 */             Integer charsetIndex = Constants.integerValueOf(results.getInt(3));
/*      */             
/*  956 */             sortedCollationMap.put(charsetIndex, charsetName);
/*      */           } 
/*      */           
/*  959 */           if (getCacheServerConfiguration()) {
/*  960 */             synchronized (serverConfigByUrl) {
/*  961 */               serverCollationByUrl.put(getURL(), sortedCollationMap);
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  969 */         int highestIndex = ((Integer)sortedCollationMap.lastKey()).intValue();
/*      */ 
/*      */         
/*  972 */         if (CharsetMapping.INDEX_TO_CHARSET.length > highestIndex) {
/*  973 */           highestIndex = CharsetMapping.INDEX_TO_CHARSET.length;
/*      */         }
/*      */         
/*  976 */         this.indexToCharsetMapping = new String[highestIndex + 1];
/*      */         
/*  978 */         for (i = 0; i < CharsetMapping.INDEX_TO_CHARSET.length; i++) {
/*  979 */           this.indexToCharsetMapping[i] = CharsetMapping.INDEX_TO_CHARSET[i];
/*      */         }
/*      */         
/*  982 */         Iterator indexIter = sortedCollationMap.entrySet().iterator();
/*  983 */         while (indexIter.hasNext()) {
/*  984 */           Map.Entry indexEntry = (Map.Entry)indexIter.next();
/*      */           
/*  986 */           String mysqlCharsetName = (String)indexEntry.getValue();
/*      */           
/*  988 */           this.indexToCharsetMapping[((Integer)indexEntry.getKey()).intValue()] = CharsetMapping.getJavaEncodingForMysqlEncoding(mysqlCharsetName, this);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  993 */       catch (SQLException e) {
/*  994 */         throw e;
/*      */       } finally {
/*  996 */         if (results != null) {
/*      */           try {
/*  998 */             results.close();
/*  999 */           } catch (SQLException sqlE) {}
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1004 */         if (stmt != null) {
/*      */           try {
/* 1006 */             stmt.close();
/* 1007 */           } catch (SQLException sqlE) {}
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1015 */       this.indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canHandleAsServerPreparedStatement(String sql) throws SQLException {
/* 1021 */     if (sql == null || sql.length() == 0) {
/* 1022 */       return true;
/*      */     }
/*      */     
/* 1025 */     if (!this.useServerPreparedStmts) {
/* 1026 */       return false;
/*      */     }
/*      */     
/* 1029 */     if (getCachePreparedStatements()) {
/* 1030 */       synchronized (this.serverSideStatementCheckCache) {
/* 1031 */         Boolean flag = (Boolean)this.serverSideStatementCheckCache.get(sql);
/*      */         
/* 1033 */         if (flag != null) {
/* 1034 */           return flag.booleanValue();
/*      */         }
/*      */         
/* 1037 */         boolean canHandle = canHandleAsServerPreparedStatementNoCache(sql);
/*      */         
/* 1039 */         if (sql.length() < getPreparedStatementCacheSqlLimit()) {
/* 1040 */           this.serverSideStatementCheckCache.put(sql, canHandle ? Boolean.TRUE : Boolean.FALSE);
/*      */         }
/*      */ 
/*      */         
/* 1044 */         return canHandle;
/*      */       } 
/*      */     }
/*      */     
/* 1048 */     return canHandleAsServerPreparedStatementNoCache(sql);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canHandleAsServerPreparedStatementNoCache(String sql) throws SQLException {
/* 1055 */     if (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "CALL")) {
/* 1056 */       return false;
/*      */     }
/*      */     
/* 1059 */     boolean canHandleAsStatement = true;
/*      */     
/* 1061 */     if (!versionMeetsMinimum(5, 0, 7) && (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "SELECT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "DELETE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "INSERT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "REPLACE"))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1079 */       int currentPos = 0;
/* 1080 */       int statementLength = sql.length();
/* 1081 */       int lastPosToLook = statementLength - 7;
/* 1082 */       boolean allowBackslashEscapes = !this.noBackslashEscapes;
/* 1083 */       char quoteChar = this.useAnsiQuotes ? '"' : '\'';
/* 1084 */       boolean foundLimitWithPlaceholder = false;
/*      */       
/* 1086 */       while (currentPos < lastPosToLook) {
/* 1087 */         int limitStart = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, sql, "LIMIT ", quoteChar, allowBackslashEscapes);
/*      */ 
/*      */ 
/*      */         
/* 1091 */         if (limitStart == -1) {
/*      */           break;
/*      */         }
/*      */         
/* 1095 */         currentPos = limitStart + 7;
/*      */         
/* 1097 */         while (currentPos < statementLength) {
/* 1098 */           char c = sql.charAt(currentPos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1105 */           if (!Character.isDigit(c) && !Character.isWhitespace(c) && c != ',' && c != '?') {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 1110 */           if (c == '?') {
/* 1111 */             foundLimitWithPlaceholder = true;
/*      */             
/*      */             break;
/*      */           } 
/* 1115 */           currentPos++;
/*      */         } 
/*      */       } 
/*      */       
/* 1119 */       canHandleAsStatement = !foundLimitWithPlaceholder;
/* 1120 */     } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "CREATE TABLE")) {
/* 1121 */       canHandleAsStatement = false;
/* 1122 */     } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "DO")) {
/* 1123 */       canHandleAsStatement = false;
/* 1124 */     } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "SET")) {
/* 1125 */       canHandleAsStatement = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     return canHandleAsStatement;
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
/*      */   public void changeUser(String userName, String newPassword) throws SQLException {
/* 1148 */     if (userName == null || userName.equals("")) {
/* 1149 */       userName = "";
/*      */     }
/*      */     
/* 1152 */     if (newPassword == null) {
/* 1153 */       newPassword = "";
/*      */     }
/*      */     
/* 1156 */     this.io.changeUser(userName, newPassword, this.database);
/* 1157 */     this.user = userName;
/* 1158 */     this.password = newPassword;
/*      */     
/* 1160 */     if (versionMeetsMinimum(4, 1, 0)) {
/* 1161 */       configureClientCharacterSet(true);
/*      */     }
/*      */     
/* 1164 */     setSessionVariables();
/*      */     
/* 1166 */     setupServerForTruncationChecks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1173 */   private boolean characterSetNamesMatches(String mysqlEncodingName) throws SQLException { return (mysqlEncodingName != null && mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_client")) && mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_connection"))); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkAndCreatePerformanceHistogram() {
/* 1179 */     if (this.perfMetricsHistCounts == null) {
/* 1180 */       this.perfMetricsHistCounts = new int[20];
/*      */     }
/*      */     
/* 1183 */     if (this.perfMetricsHistBreakpoints == null) {
/* 1184 */       this.perfMetricsHistBreakpoints = new long[20];
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkAndCreateTablesAccessedHistogram() {
/* 1189 */     if (this.numTablesMetricsHistCounts == null) {
/* 1190 */       this.numTablesMetricsHistCounts = new int[20];
/*      */     }
/*      */     
/* 1193 */     if (this.numTablesMetricsHistBreakpoints == null) {
/* 1194 */       this.numTablesMetricsHistBreakpoints = new long[20];
/*      */     }
/*      */   }
/*      */   
/*      */   public void checkClosed() {
/* 1199 */     if (this.isClosed) {
/* 1200 */       throwConnectionClosedException();
/*      */     }
/*      */   }
/*      */   
/*      */   public void throwConnectionClosedException() {
/* 1205 */     StringBuffer messageBuf = new StringBuffer("No operations allowed after connection closed.");
/*      */ 
/*      */     
/* 1208 */     if (this.forcedClosedLocation != null || this.forceClosedReason != null) {
/* 1209 */       messageBuf.append("Connection was implicitly closed by the driver.");
/*      */     }
/*      */ 
/*      */     
/* 1213 */     SQLException ex = SQLError.createSQLException(messageBuf.toString(), "08003", getExceptionInterceptor());
/*      */ 
/*      */     
/* 1216 */     if (this.forceClosedReason != null) {
/* 1217 */       ex.initCause(this.forceClosedReason);
/*      */     }
/*      */     
/* 1220 */     throw ex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkServerEncoding() {
/* 1231 */     if (getUseUnicode() && getEncoding() != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1236 */     String serverEncoding = (String)this.serverVariables.get("character_set");
/*      */ 
/*      */     
/* 1239 */     if (serverEncoding == null)
/*      */     {
/* 1241 */       serverEncoding = (String)this.serverVariables.get("character_set_server");
/*      */     }
/*      */ 
/*      */     
/* 1245 */     String mappedServerEncoding = null;
/*      */     
/* 1247 */     if (serverEncoding != null) {
/* 1248 */       mappedServerEncoding = CharsetMapping.getJavaEncodingForMysqlEncoding(serverEncoding.toUpperCase(Locale.ENGLISH), this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1256 */     if (!getUseUnicode() && mappedServerEncoding != null) {
/* 1257 */       SingleByteCharsetConverter converter = getCharsetConverter(mappedServerEncoding);
/*      */       
/* 1259 */       if (converter != null) {
/* 1260 */         setUseUnicode(true);
/* 1261 */         setEncoding(mappedServerEncoding);
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1271 */     if (serverEncoding != null) {
/* 1272 */       if (mappedServerEncoding == null)
/*      */       {
/*      */         
/* 1275 */         if (Character.isLowerCase(serverEncoding.charAt(0))) {
/* 1276 */           char[] ach = serverEncoding.toCharArray();
/* 1277 */           ach[0] = Character.toUpperCase(serverEncoding.charAt(0));
/* 1278 */           setEncoding(new String(ach));
/*      */         } 
/*      */       }
/*      */       
/* 1282 */       if (mappedServerEncoding == null) {
/* 1283 */         throw SQLError.createSQLException("Unknown character encoding on server '" + serverEncoding + "', use 'characterEncoding=' property " + " to provide correct mapping", "01S00", getExceptionInterceptor());
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
/*      */       try {
/* 1295 */         "abc".getBytes(mappedServerEncoding);
/* 1296 */         setEncoding(mappedServerEncoding);
/* 1297 */         setUseUnicode(true);
/* 1298 */       } catch (UnsupportedEncodingException UE) {
/* 1299 */         throw SQLError.createSQLException("The driver can not map the character encoding '" + getEncoding() + "' that your server is using " + "to a character encoding your JVM understands. You " + "can specify this mapping manually by adding \"useUnicode=true\" " + "as well as \"characterEncoding=[an_encoding_your_jvm_understands]\" " + "to your JDBC URL.", "0S100", getExceptionInterceptor());
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
/*      */   private void checkTransactionIsolationLevel() {
/* 1319 */     String txIsolationName = null;
/*      */     
/* 1321 */     if (versionMeetsMinimum(4, 0, 3)) {
/* 1322 */       txIsolationName = "tx_isolation";
/*      */     } else {
/* 1324 */       txIsolationName = "transaction_isolation";
/*      */     } 
/*      */     
/* 1327 */     String s = (String)this.serverVariables.get(txIsolationName);
/*      */     
/* 1329 */     if (s != null) {
/* 1330 */       Integer intTI = (Integer)mapTransIsolationNameToValue.get(s);
/*      */       
/* 1332 */       if (intTI != null) {
/* 1333 */         this.isolationLevel = intTI.intValue();
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
/*      */   public void abortInternal() {
/* 1345 */     if (this.io != null) {
/*      */       try {
/* 1347 */         this.io.forceClose();
/* 1348 */       } catch (Throwable t) {}
/*      */ 
/*      */       
/* 1351 */       this.io = null;
/*      */     } 
/*      */     
/* 1354 */     this.isClosed = true;
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
/*      */   private void cleanup(Throwable whyCleanedUp) {
/*      */     try {
/* 1367 */       if (this.io != null && !isClosed()) {
/* 1368 */         realClose(false, false, false, whyCleanedUp);
/* 1369 */       } else if (this.io != null) {
/* 1370 */         this.io.forceClose();
/*      */       } 
/* 1372 */     } catch (SQLException sqlEx) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1377 */     this.isClosed = true;
/*      */   }
/*      */ 
/*      */   
/* 1381 */   public void clearHasTriedMaster() { this.hasTriedMasterFlag = false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWarnings() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1406 */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException { return clientPrepareStatement(sql, 1003, 1007); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/* 1416 */     PreparedStatement pStmt = clientPrepareStatement(sql);
/*      */     
/* 1418 */     ((PreparedStatement)pStmt).setRetrieveGeneratedKeys((autoGenKeyIndex == 1));
/*      */ 
/*      */     
/* 1421 */     return pStmt;
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
/* 1439 */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, boolean processEscapeCodesIfNeeded) throws SQLException {
/* 1447 */     checkClosed();
/*      */     
/* 1449 */     String nativeSql = (processEscapeCodesIfNeeded && getProcessEscapeCodesForPrepStmts()) ? nativeSQL(sql) : sql;
/*      */     
/* 1451 */     PreparedStatement pStmt = null;
/*      */     
/* 1453 */     if (getCachePreparedStatements()) {
/* 1454 */       synchronized (this.cachedPreparedStatementParams) {
/* 1455 */         PreparedStatement.ParseInfo pStmtInfo = (PreparedStatement.ParseInfo)this.cachedPreparedStatementParams.get(nativeSql);
/*      */ 
/*      */         
/* 1458 */         if (pStmtInfo == null) {
/* 1459 */           pStmt = PreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, this.database);
/*      */ 
/*      */           
/* 1462 */           PreparedStatement.ParseInfo parseInfo = pStmt.getParseInfo();
/*      */           
/* 1464 */           if (parseInfo.statementLength < getPreparedStatementCacheSqlLimit()) {
/* 1465 */             if (this.cachedPreparedStatementParams.size() >= getPreparedStatementCacheSize()) {
/* 1466 */               Iterator oldestIter = this.cachedPreparedStatementParams.keySet().iterator();
/*      */               
/* 1468 */               long lruTime = Float.MAX_VALUE;
/* 1469 */               String oldestSql = null;
/*      */               
/* 1471 */               while (oldestIter.hasNext()) {
/* 1472 */                 String sqlKey = (String)oldestIter.next();
/* 1473 */                 PreparedStatement.ParseInfo lruInfo = (PreparedStatement.ParseInfo)this.cachedPreparedStatementParams.get(sqlKey);
/*      */ 
/*      */                 
/* 1476 */                 if (lruInfo.lastUsed < lruTime) {
/* 1477 */                   lruTime = lruInfo.lastUsed;
/* 1478 */                   oldestSql = sqlKey;
/*      */                 } 
/*      */               } 
/*      */               
/* 1482 */               if (oldestSql != null) {
/* 1483 */                 this.cachedPreparedStatementParams.remove(oldestSql);
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/* 1488 */             this.cachedPreparedStatementParams.put(nativeSql, pStmt.getParseInfo());
/*      */           } 
/*      */         } else {
/*      */           
/* 1492 */           pStmtInfo.lastUsed = System.currentTimeMillis();
/* 1493 */           pStmt = new PreparedStatement(getLoadBalanceSafeProxy(), nativeSql, this.database, pStmtInfo);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1498 */       pStmt = PreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, this.database);
/*      */     } 
/*      */ 
/*      */     
/* 1502 */     pStmt.setResultSetType(resultSetType);
/* 1503 */     pStmt.setResultSetConcurrency(resultSetConcurrency);
/*      */     
/* 1505 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/* 1514 */     PreparedStatement pStmt = (PreparedStatement)clientPrepareStatement(sql);
/*      */     
/* 1516 */     pStmt.setRetrieveGeneratedKeys((autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 1520 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/* 1528 */     PreparedStatement pStmt = (PreparedStatement)clientPrepareStatement(sql);
/*      */     
/* 1530 */     pStmt.setRetrieveGeneratedKeys((autoGenKeyColNames != null && autoGenKeyColNames.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 1534 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1540 */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/* 1556 */     if (this.connectionLifecycleInterceptors != null) {
/* 1557 */       (new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
/*      */           void forEach(Object each) throws SQLException {
/* 1559 */             ((ConnectionLifecycleInterceptor)each).close();
/*      */           }
/*      */         }).doForAll();
/*      */     }
/*      */     
/* 1564 */     realClose(true, true, false, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void closeAllOpenStatements() {
/* 1574 */     SQLException postponedException = null;
/*      */     
/* 1576 */     if (this.openStatements != null) {
/* 1577 */       List currentlyOpenStatements = new ArrayList();
/*      */ 
/*      */ 
/*      */       
/* 1581 */       Iterator iter = this.openStatements.keySet().iterator();
/* 1582 */       while (iter.hasNext()) {
/* 1583 */         currentlyOpenStatements.add(iter.next());
/*      */       }
/*      */       
/* 1586 */       int numStmts = currentlyOpenStatements.size();
/*      */       
/* 1588 */       for (int i = 0; i < numStmts; i++) {
/* 1589 */         StatementImpl stmt = (StatementImpl)currentlyOpenStatements.get(i);
/*      */         
/*      */         try {
/* 1592 */           stmt.realClose(false, true);
/* 1593 */         } catch (SQLException sqlEx) {
/* 1594 */           postponedException = sqlEx;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1599 */       if (postponedException != null) {
/* 1600 */         throw postponedException;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closeStatement(Statement stmt) {
/* 1606 */     if (stmt != null) {
/*      */       try {
/* 1608 */         stmt.close();
/* 1609 */       } catch (SQLException sqlEx) {}
/*      */ 
/*      */ 
/*      */       
/* 1613 */       stmt = null;
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
/*      */   public void commit() {
/* 1632 */     synchronized (getMutex()) {
/* 1633 */       checkClosed();
/*      */       
/*      */       try {
/* 1636 */         if (this.connectionLifecycleInterceptors != null) {
/* 1637 */           IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */             {
/*      */               void forEach(Object each) throws SQLException {
/* 1640 */                 if (!((ConnectionLifecycleInterceptor)each).commit()) {
/* 1641 */                   this.stopIterating = true;
/*      */                 }
/*      */               }
/*      */             };
/*      */           
/* 1646 */           iter.doForAll();
/*      */           
/* 1648 */           if (!iter.fullIteration()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1654 */         if (this.autoCommit && !getRelaxAutoCommit())
/* 1655 */           throw SQLError.createSQLException("Can't call commit when autocommit=true", getExceptionInterceptor()); 
/* 1656 */         if (this.transactionsSupported) {
/* 1657 */           if (getUseLocalTransactionState() && versionMeetsMinimum(5, 0, 0) && 
/* 1658 */             !this.io.inTransactionOnServer()) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1663 */           execSQL(null, "commit", -1, null, 1003, 1007, false, this.database, null, false);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1669 */       catch (SQLException sqlException) {
/* 1670 */         if ("08S01".equals(sqlException.getSQLState()))
/*      */         {
/* 1672 */           throw SQLError.createSQLException("Communications link failure during commit(). Transaction resolution unknown.", "08007", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1679 */         throw sqlException;
/*      */       } finally {
/* 1681 */         this.needsPing = getReconnectAtTxEnd();
/*      */       } 
/*      */       return;
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
/*      */   private void configureCharsetProperties() {
/* 1695 */     if (getEncoding() != null) {
/*      */       
/*      */       try {
/*      */         
/* 1699 */         String testString = "abc";
/* 1700 */         testString.getBytes(getEncoding());
/* 1701 */       } catch (UnsupportedEncodingException UE) {
/*      */         
/* 1703 */         String oldEncoding = getEncoding();
/*      */         
/* 1705 */         setEncoding(CharsetMapping.getJavaEncodingForMysqlEncoding(oldEncoding, this));
/*      */ 
/*      */         
/* 1708 */         if (getEncoding() == null) {
/* 1709 */           throw SQLError.createSQLException("Java does not support the MySQL character encoding  encoding '" + oldEncoding + "'.", "01S00", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1716 */           String testString = "abc";
/* 1717 */           testString.getBytes(getEncoding());
/* 1718 */         } catch (UnsupportedEncodingException encodingEx) {
/* 1719 */           throw SQLError.createSQLException("Unsupported character encoding '" + getEncoding() + "'.", "01S00", getExceptionInterceptor());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean configureClientCharacterSet(boolean dontCheckServerMatch) throws SQLException {
/* 1741 */     String realJavaEncoding = getEncoding();
/* 1742 */     boolean characterSetAlreadyConfigured = false;
/*      */     
/*      */     try {
/* 1745 */       if (versionMeetsMinimum(4, 1, 0)) {
/* 1746 */         characterSetAlreadyConfigured = true;
/*      */         
/* 1748 */         setUseUnicode(true);
/*      */         
/* 1750 */         configureCharsetProperties();
/* 1751 */         realJavaEncoding = getEncoding();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1759 */           if (this.props != null && this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex") != null) {
/* 1760 */             this.io.serverCharsetIndex = Integer.parseInt(this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex"));
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1765 */           String serverEncodingToSet = CharsetMapping.INDEX_TO_CHARSET[this.io.serverCharsetIndex];
/*      */ 
/*      */           
/* 1768 */           if (serverEncodingToSet == null || serverEncodingToSet.length() == 0) {
/* 1769 */             if (realJavaEncoding != null) {
/*      */               
/* 1771 */               setEncoding(realJavaEncoding);
/*      */             } else {
/* 1773 */               throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000", getExceptionInterceptor());
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1782 */           if (versionMeetsMinimum(4, 1, 0) && "ISO8859_1".equalsIgnoreCase(serverEncodingToSet))
/*      */           {
/* 1784 */             serverEncodingToSet = "Cp1252";
/*      */           }
/*      */           
/* 1787 */           setEncoding(serverEncodingToSet);
/*      */         }
/* 1789 */         catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
/* 1790 */           if (realJavaEncoding != null) {
/*      */             
/* 1792 */             setEncoding(realJavaEncoding);
/*      */           } else {
/* 1794 */             throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000", getExceptionInterceptor());
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1802 */         if (getEncoding() == null)
/*      */         {
/* 1804 */           setEncoding("ISO8859_1");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1811 */         if (getUseUnicode()) {
/* 1812 */           if (realJavaEncoding != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1818 */             if (realJavaEncoding.equalsIgnoreCase("UTF-8") || realJavaEncoding.equalsIgnoreCase("UTF8")) {
/*      */ 
/*      */ 
/*      */               
/* 1822 */               boolean utf8mb4Supported = versionMeetsMinimum(5, 5, 2);
/* 1823 */               boolean useutf8mb4 = false;
/*      */               
/* 1825 */               if (utf8mb4Supported) {
/* 1826 */                 useutf8mb4 = (this.io.serverCharsetIndex == 45 && this.io.serverCharsetIndex == 45);
/*      */               }
/*      */               
/* 1829 */               if (!getUseOldUTF8Behavior()) {
/* 1830 */                 if (dontCheckServerMatch || !characterSetNamesMatches("utf8") || (utf8mb4Supported && !characterSetNamesMatches("utf8mb4")))
/*      */                 {
/* 1832 */                   execSQL(null, "SET NAMES " + (useutf8mb4 ? "utf8mb4" : "utf8"), -1, null, 1003, 1007, false, this.database, null, false);
/*      */                 
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/* 1838 */                 execSQL(null, "SET NAMES latin1", -1, null, 1003, 1007, false, this.database, null, false);
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1844 */               setEncoding(realJavaEncoding);
/*      */             } else {
/* 1846 */               String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(realJavaEncoding.toUpperCase(Locale.ENGLISH), this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1861 */               if (mysqlEncodingName != null)
/*      */               {
/* 1863 */                 if (dontCheckServerMatch || !characterSetNamesMatches(mysqlEncodingName)) {
/* 1864 */                   execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, null, false);
/*      */                 }
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1875 */               setEncoding(realJavaEncoding);
/*      */             } 
/* 1877 */           } else if (getEncoding() != null) {
/*      */ 
/*      */ 
/*      */             
/* 1881 */             String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(getEncoding().toUpperCase(Locale.ENGLISH), this);
/*      */ 
/*      */ 
/*      */             
/* 1885 */             if (getUseOldUTF8Behavior()) {
/* 1886 */               mysqlEncodingName = "latin1";
/*      */             }
/*      */             
/* 1889 */             if (dontCheckServerMatch || !characterSetNamesMatches(mysqlEncodingName)) {
/* 1890 */               execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, null, false);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1896 */             realJavaEncoding = getEncoding();
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
/* 1907 */         String onServer = null;
/* 1908 */         boolean isNullOnServer = false;
/*      */         
/* 1910 */         if (this.serverVariables != null) {
/* 1911 */           onServer = (String)this.serverVariables.get("character_set_results");
/*      */           
/* 1913 */           isNullOnServer = (onServer == null || "NULL".equalsIgnoreCase(onServer) || onServer.length() == 0);
/*      */         } 
/*      */         
/* 1916 */         if (getCharacterSetResults() == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1923 */           if (!isNullOnServer) {
/* 1924 */             execSQL(null, "SET character_set_results = NULL", -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1929 */             if (!this.usingCachedConfig) {
/* 1930 */               this.serverVariables.put("jdbc.local.character_set_results", null);
/*      */             }
/*      */           }
/* 1933 */           else if (!this.usingCachedConfig) {
/* 1934 */             this.serverVariables.put("jdbc.local.character_set_results", onServer);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1939 */           if (getUseOldUTF8Behavior()) {
/* 1940 */             execSQL(null, "SET NAMES latin1", -1, null, 1003, 1007, false, this.database, null, false);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1945 */           String charsetResults = getCharacterSetResults();
/* 1946 */           String mysqlEncodingName = null;
/*      */           
/* 1948 */           if ("UTF-8".equalsIgnoreCase(charsetResults) || "UTF8".equalsIgnoreCase(charsetResults)) {
/*      */             
/* 1950 */             mysqlEncodingName = "utf8";
/*      */           } else {
/* 1952 */             mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(charsetResults.toUpperCase(Locale.ENGLISH), this);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1961 */           if (!mysqlEncodingName.equalsIgnoreCase((String)this.serverVariables.get("character_set_results"))) {
/*      */             
/* 1963 */             StringBuffer setBuf = new StringBuffer("SET character_set_results = ".length() + mysqlEncodingName.length());
/*      */ 
/*      */             
/* 1966 */             setBuf.append("SET character_set_results = ").append(mysqlEncodingName);
/*      */ 
/*      */             
/* 1969 */             execSQL(null, setBuf.toString(), -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1974 */             if (!this.usingCachedConfig) {
/* 1975 */               this.serverVariables.put("jdbc.local.character_set_results", mysqlEncodingName);
/*      */             
/*      */             }
/*      */           }
/* 1979 */           else if (!this.usingCachedConfig) {
/* 1980 */             this.serverVariables.put("jdbc.local.character_set_results", onServer);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1985 */         if (getConnectionCollation() != null) {
/* 1986 */           StringBuffer setBuf = new StringBuffer("SET collation_connection = ".length() + getConnectionCollation().length());
/*      */ 
/*      */           
/* 1989 */           setBuf.append("SET collation_connection = ").append(getConnectionCollation());
/*      */ 
/*      */           
/* 1992 */           execSQL(null, setBuf.toString(), -1, null, 1003, 1007, false, this.database, null, false);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1999 */         realJavaEncoding = getEncoding();
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 2007 */       setEncoding(realJavaEncoding);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2015 */       CharsetEncoder enc = Charset.forName(getEncoding()).newEncoder();
/* 2016 */       CharBuffer cbuf = CharBuffer.allocate(1);
/* 2017 */       ByteBuffer bbuf = ByteBuffer.allocate(1);
/*      */       
/* 2019 */       cbuf.put("¥");
/* 2020 */       cbuf.position(0);
/* 2021 */       enc.encode(cbuf, bbuf, true);
/* 2022 */       if (bbuf.get(0) == 92) {
/* 2023 */         this.requiresEscapingEncoder = true;
/*      */       } else {
/* 2025 */         cbuf.clear();
/* 2026 */         bbuf.clear();
/*      */         
/* 2028 */         cbuf.put("₩");
/* 2029 */         cbuf.position(0);
/* 2030 */         enc.encode(cbuf, bbuf, true);
/* 2031 */         if (bbuf.get(0) == 92) {
/* 2032 */           this.requiresEscapingEncoder = true;
/*      */         }
/*      */       } 
/* 2035 */     } catch (UnsupportedCharsetException ucex) {
/*      */       
/*      */       try {
/* 2038 */         byte[] bbuf = (new String("¥")).getBytes(getEncoding());
/* 2039 */         if (bbuf[0] == 92) {
/* 2040 */           this.requiresEscapingEncoder = true;
/*      */         } else {
/* 2042 */           bbuf = (new String("₩")).getBytes(getEncoding());
/* 2043 */           if (bbuf[0] == 92) {
/* 2044 */             this.requiresEscapingEncoder = true;
/*      */           }
/*      */         } 
/* 2047 */       } catch (UnsupportedEncodingException ueex) {
/* 2048 */         throw SQLError.createSQLException("Unable to use encoding: " + getEncoding(), "S1000", ueex, getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2054 */     return characterSetAlreadyConfigured;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void configureTimezone() {
/* 2065 */     String configuredTimeZoneOnServer = (String)this.serverVariables.get("timezone");
/*      */ 
/*      */     
/* 2068 */     if (configuredTimeZoneOnServer == null) {
/* 2069 */       configuredTimeZoneOnServer = (String)this.serverVariables.get("time_zone");
/*      */ 
/*      */       
/* 2072 */       if ("SYSTEM".equalsIgnoreCase(configuredTimeZoneOnServer)) {
/* 2073 */         configuredTimeZoneOnServer = (String)this.serverVariables.get("system_time_zone");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2078 */     String canoncicalTimezone = getServerTimezone();
/*      */     
/* 2080 */     if ((getUseTimezone() || !getUseLegacyDatetimeCode()) && configuredTimeZoneOnServer != null) {
/*      */       
/* 2082 */       if (canoncicalTimezone == null || StringUtils.isEmptyOrWhitespaceOnly(canoncicalTimezone)) {
/*      */         try {
/* 2084 */           canoncicalTimezone = TimeUtil.getCanoncialTimezone(configuredTimeZoneOnServer, getExceptionInterceptor());
/*      */ 
/*      */           
/* 2087 */           if (canoncicalTimezone == null) {
/* 2088 */             throw SQLError.createSQLException("Can't map timezone '" + configuredTimeZoneOnServer + "' to " + " canonical timezone.", "S1009", getExceptionInterceptor());
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2093 */         catch (IllegalArgumentException iae) {
/* 2094 */           throw SQLError.createSQLException(iae.getMessage(), "S1000", getExceptionInterceptor());
/*      */         } 
/*      */       }
/*      */     } else {
/*      */       
/* 2099 */       canoncicalTimezone = getServerTimezone();
/*      */     } 
/*      */     
/* 2102 */     if (canoncicalTimezone != null && canoncicalTimezone.length() > 0) {
/* 2103 */       this.serverTimezoneTZ = TimeZone.getTimeZone(canoncicalTimezone);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2110 */       if (!canoncicalTimezone.equalsIgnoreCase("GMT") && this.serverTimezoneTZ.getID().equals("GMT"))
/*      */       {
/* 2112 */         throw SQLError.createSQLException("No timezone mapping entry for '" + canoncicalTimezone + "'", "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2117 */       if ("GMT".equalsIgnoreCase(this.serverTimezoneTZ.getID())) {
/* 2118 */         this.isServerTzUTC = true;
/*      */       } else {
/* 2120 */         this.isServerTzUTC = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createInitialHistogram(long[] breakpoints, long lowerBound, long upperBound) {
/* 2128 */     double bucketSize = (upperBound - lowerBound) / 20.0D * 1.25D;
/*      */     
/* 2130 */     if (bucketSize < 1.0D) {
/* 2131 */       bucketSize = 1.0D;
/*      */     }
/*      */     
/* 2134 */     for (int i = 0; i < 20; i++) {
/* 2135 */       breakpoints[i] = lowerBound;
/* 2136 */       lowerBound = (long)(lowerBound + bucketSize);
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
/*      */   public void createNewIO(boolean isForReconnect) throws SQLException {
/* 2159 */     synchronized (this.mutex) {
/* 2160 */       Properties mergedProps = exposeAsProperties(this.props);
/*      */       
/* 2162 */       if (!getHighAvailability()) {
/* 2163 */         connectOneTryOnly(isForReconnect, mergedProps);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 2168 */       connectWithRetries(isForReconnect, mergedProps);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void connectWithRetries(boolean isForReconnect, Properties mergedProps) throws SQLException {
/* 2174 */     double timeout = getInitialTimeout();
/* 2175 */     boolean connectionGood = false;
/*      */     
/* 2177 */     Exception connectionException = null;
/*      */     
/* 2179 */     int attemptCount = 0;
/* 2180 */     for (; attemptCount < getMaxReconnects() && !connectionGood; attemptCount++) {
/*      */       try {
/* 2182 */         if (this.io != null) {
/* 2183 */           this.io.forceClose();
/*      */         }
/*      */         
/* 2186 */         coreConnect(mergedProps);
/* 2187 */         pingInternal(false, 0);
/* 2188 */         this.connectionId = this.io.getThreadId();
/* 2189 */         this.isClosed = false;
/*      */ 
/*      */         
/* 2192 */         boolean oldAutoCommit = getAutoCommit();
/* 2193 */         int oldIsolationLevel = this.isolationLevel;
/* 2194 */         boolean oldReadOnly = isReadOnly();
/* 2195 */         String oldCatalog = getCatalog();
/*      */         
/* 2197 */         this.io.setStatementInterceptors(this.statementInterceptors);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2202 */         initializePropsFromServer();
/*      */         
/* 2204 */         if (isForReconnect) {
/*      */           
/* 2206 */           setAutoCommit(oldAutoCommit);
/*      */           
/* 2208 */           if (this.hasIsolationLevels) {
/* 2209 */             setTransactionIsolation(oldIsolationLevel);
/*      */           }
/*      */           
/* 2212 */           setCatalog(oldCatalog);
/* 2213 */           setReadOnly(oldReadOnly);
/*      */         } 
/*      */         
/* 2216 */         connectionGood = true;
/*      */         
/*      */         break;
/* 2219 */       } catch (Exception EEE) {
/* 2220 */         connectionException = EEE;
/* 2221 */         connectionGood = false;
/*      */ 
/*      */         
/* 2224 */         if (connectionGood) {
/*      */           break;
/*      */         }
/*      */         
/* 2228 */         if (attemptCount > 0) {
/*      */           try {
/* 2230 */             Thread.sleep((long)timeout * 1000L);
/* 2231 */           } catch (InterruptedException EEE) {
/*      */             InterruptedException IE;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2237 */     if (!connectionGood) {
/*      */       
/* 2239 */       SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnectWithRetries", new Object[] { new Integer(getMaxReconnects()) }), "08001", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */       
/* 2243 */       chainedEx.initCause(connectionException);
/*      */       
/* 2245 */       throw chainedEx;
/*      */     } 
/*      */     
/* 2248 */     if (getParanoid() && !getHighAvailability()) {
/* 2249 */       this.password = null;
/* 2250 */       this.user = null;
/*      */     } 
/*      */     
/* 2253 */     if (isForReconnect) {
/*      */ 
/*      */ 
/*      */       
/* 2257 */       Iterator statementIter = this.openStatements.values().iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2269 */       Stack serverPreparedStatements = null;
/*      */       
/* 2271 */       while (statementIter.hasNext()) {
/* 2272 */         Object statementObj = statementIter.next();
/*      */         
/* 2274 */         if (statementObj instanceof ServerPreparedStatement) {
/* 2275 */           if (serverPreparedStatements == null) {
/* 2276 */             serverPreparedStatements = new Stack();
/*      */           }
/*      */           
/* 2279 */           serverPreparedStatements.add(statementObj);
/*      */         } 
/*      */       } 
/*      */       
/* 2283 */       if (serverPreparedStatements != null) {
/* 2284 */         while (!serverPreparedStatements.isEmpty()) {
/* 2285 */           ((ServerPreparedStatement)serverPreparedStatements.pop()).rePrepare();
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void coreConnect(Properties mergedProps) throws SQLException, IOException {
/* 2294 */     int newPort = 3306;
/* 2295 */     String newHost = "localhost";
/*      */     
/* 2297 */     String protocol = mergedProps.getProperty("PROTOCOL");
/*      */     
/* 2299 */     if (protocol != null) {
/*      */ 
/*      */       
/* 2302 */       if ("tcp".equalsIgnoreCase(protocol)) {
/* 2303 */         newHost = normalizeHost(mergedProps.getProperty("HOST"));
/* 2304 */         newPort = parsePortNumber(mergedProps.getProperty("PORT", "3306"));
/* 2305 */       } else if ("pipe".equalsIgnoreCase(protocol)) {
/* 2306 */         setSocketFactoryClassName(NamedPipeSocketFactory.class.getName());
/*      */         
/* 2308 */         String path = mergedProps.getProperty("PATH");
/*      */         
/* 2310 */         if (path != null) {
/* 2311 */           mergedProps.setProperty("namedPipePath", path);
/*      */         }
/*      */       } else {
/*      */         
/* 2315 */         newHost = normalizeHost(mergedProps.getProperty("HOST"));
/* 2316 */         newPort = parsePortNumber(mergedProps.getProperty("PORT", "3306"));
/*      */       } 
/*      */     } else {
/*      */       
/* 2320 */       String[] parsedHostPortPair = NonRegisteringDriver.parseHostPortPair(this.hostPortPair);
/*      */       
/* 2322 */       newHost = parsedHostPortPair[0];
/*      */       
/* 2324 */       newHost = normalizeHost(newHost);
/*      */       
/* 2326 */       if (parsedHostPortPair[true] != null) {
/* 2327 */         newPort = parsePortNumber(parsedHostPortPair[1]);
/*      */       }
/*      */     } 
/*      */     
/* 2331 */     this.port = newPort;
/* 2332 */     this.host = newHost;
/*      */     
/* 2334 */     this.io = new MysqlIO(newHost, newPort, mergedProps, getSocketFactoryClassName(), getProxy(), getSocketTimeout(), this.largeRowSizeThreshold.getValueAsInt());
/*      */ 
/*      */ 
/*      */     
/* 2338 */     this.io.doHandshake(this.user, this.password, this.database);
/*      */   }
/*      */ 
/*      */   
/*      */   private String normalizeHost(String host) {
/* 2343 */     if (host == null || StringUtils.isEmptyOrWhitespaceOnly(host)) {
/* 2344 */       return "localhost";
/*      */     }
/*      */     
/* 2347 */     return host;
/*      */   }
/*      */   
/*      */   private int parsePortNumber(String portAsString) throws SQLException {
/* 2351 */     int portNumber = 3306;
/*      */     try {
/* 2353 */       portNumber = Integer.parseInt(portAsString);
/*      */     }
/* 2355 */     catch (NumberFormatException nfe) {
/* 2356 */       throw SQLError.createSQLException("Illegal connection port value '" + portAsString + "'", "01S00", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2362 */     return portNumber;
/*      */   }
/*      */ 
/*      */   
/*      */   private void connectOneTryOnly(boolean isForReconnect, Properties mergedProps) throws SQLException {
/* 2367 */     Exception connectionNotEstablishedBecause = null;
/*      */ 
/*      */     
/*      */     try {
/* 2371 */       coreConnect(mergedProps);
/* 2372 */       this.connectionId = this.io.getThreadId();
/* 2373 */       this.isClosed = false;
/*      */ 
/*      */       
/* 2376 */       boolean oldAutoCommit = getAutoCommit();
/* 2377 */       int oldIsolationLevel = this.isolationLevel;
/* 2378 */       boolean oldReadOnly = isReadOnly();
/* 2379 */       String oldCatalog = getCatalog();
/*      */       
/* 2381 */       this.io.setStatementInterceptors(this.statementInterceptors);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2386 */       initializePropsFromServer();
/*      */       
/* 2388 */       if (isForReconnect) {
/*      */         
/* 2390 */         setAutoCommit(oldAutoCommit);
/*      */         
/* 2392 */         if (this.hasIsolationLevels) {
/* 2393 */           setTransactionIsolation(oldIsolationLevel);
/*      */         }
/*      */         
/* 2396 */         setCatalog(oldCatalog);
/*      */         
/* 2398 */         setReadOnly(oldReadOnly);
/*      */       } 
/*      */       
/*      */       return;
/* 2402 */     } catch (Exception EEE) {
/* 2403 */       if (this.io != null) {
/* 2404 */         this.io.forceClose();
/*      */       }
/*      */       
/* 2407 */       connectionNotEstablishedBecause = EEE;
/*      */       
/* 2409 */       if (EEE instanceof SQLException) {
/* 2410 */         throw (SQLException)EEE;
/*      */       }
/*      */       
/* 2413 */       SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnect"), "08001", getExceptionInterceptor());
/*      */ 
/*      */       
/* 2416 */       chainedEx.initCause(connectionNotEstablishedBecause);
/*      */       
/* 2418 */       throw chainedEx;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createPreparedStatementCaches() {
/* 2423 */     int cacheSize = getPreparedStatementCacheSize();
/*      */     
/* 2425 */     this.cachedPreparedStatementParams = new HashMap(cacheSize);
/*      */     
/* 2427 */     if (getUseServerPreparedStmts()) {
/* 2428 */       this.serverSideStatementCheckCache = new LRUCache(cacheSize);
/*      */       
/* 2430 */       this.serverSideStatementCache = new LRUCache(cacheSize) {
/*      */           protected boolean removeEldestEntry(Map.Entry eldest) {
/* 2432 */             if (this.maxElements <= 1) {
/* 2433 */               return false;
/*      */             }
/*      */             
/* 2436 */             boolean removeIt = super.removeEldestEntry(eldest);
/*      */             
/* 2438 */             if (removeIt) {
/* 2439 */               ServerPreparedStatement ps = (ServerPreparedStatement)eldest.getValue();
/*      */               
/* 2441 */               ps.isCached = false;
/* 2442 */               ps.setClosed(false);
/*      */               
/*      */               try {
/* 2445 */                 ps.close();
/* 2446 */               } catch (SQLException sqlEx) {}
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2451 */             return removeIt;
/*      */           }
/*      */         };
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
/* 2467 */   public Statement createStatement() throws SQLException { return createStatement(1003, 1007); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/* 2485 */     checkClosed();
/*      */     
/* 2487 */     StatementImpl stmt = new StatementImpl(getLoadBalanceSafeProxy(), this.database);
/* 2488 */     stmt.setResultSetType(resultSetType);
/* 2489 */     stmt.setResultSetConcurrency(resultSetConcurrency);
/*      */     
/* 2491 */     return stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 2500 */     if (getPedantic() && 
/* 2501 */       resultSetHoldability != 1) {
/* 2502 */       throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2508 */     return createStatement(resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */   
/* 2512 */   public void dumpTestcaseQuery(String query) { System.err.println(query); }
/*      */ 
/*      */ 
/*      */   
/* 2516 */   public Connection duplicate() throws SQLException { return new ConnectionImpl(this.origHostToConnectTo, this.origPortToConnectTo, this.props, this.origDatabaseToConnectTo, this.myURL); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2570 */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata) throws SQLException { return execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata, false); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata, boolean isBatch) throws SQLException {
/* 2585 */     synchronized (this.mutex) {
/* 2586 */       long queryStartTime = 0L;
/*      */       
/* 2588 */       int endOfQueryPacketPosition = 0;
/*      */       
/* 2590 */       if (packet != null) {
/* 2591 */         endOfQueryPacketPosition = packet.getPosition();
/*      */       }
/*      */       
/* 2594 */       if (getGatherPerformanceMetrics()) {
/* 2595 */         queryStartTime = System.currentTimeMillis();
/*      */       }
/*      */       
/* 2598 */       this.lastQueryFinishedTime = 0L;
/*      */       
/* 2600 */       if (getHighAvailability() && (this.autoCommit || getAutoReconnectForPools()) && this.needsPing && !isBatch) {
/*      */         
/*      */         try {
/*      */           
/* 2604 */           pingInternal(false, 0);
/*      */           
/* 2606 */           this.needsPing = false;
/* 2607 */         } catch (Exception Ex) {
/* 2608 */           createNewIO(true);
/*      */         } 
/*      */       }
/*      */       
/*      */       try {
/* 2613 */         if (packet == null) {
/* 2614 */           String encoding = null;
/*      */           
/* 2616 */           if (getUseUnicode()) {
/* 2617 */             encoding = getEncoding();
/*      */           }
/*      */           
/* 2620 */           return this.io.sqlQueryDirect(callingStatement, sql, encoding, null, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2626 */         return this.io.sqlQueryDirect(callingStatement, null, null, packet, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
/*      */ 
/*      */       
/*      */       }
/* 2630 */       catch (SQLException sqlE) {
/*      */ 
/*      */         
/* 2633 */         if (getDumpQueriesOnException()) {
/* 2634 */           String extractedSql = extractSqlFromPacket(sql, packet, endOfQueryPacketPosition);
/*      */           
/* 2636 */           StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
/*      */           
/* 2638 */           messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
/*      */           
/* 2640 */           messageBuf.append(extractedSql);
/* 2641 */           messageBuf.append("\n\n");
/*      */           
/* 2643 */           sqlE = appendMessageToException(sqlE, messageBuf.toString(), getExceptionInterceptor());
/*      */         } 
/*      */         
/* 2646 */         if (getHighAvailability()) {
/* 2647 */           this.needsPing = true;
/*      */         } else {
/* 2649 */           String sqlState = sqlE.getSQLState();
/*      */           
/* 2651 */           if (sqlState != null && sqlState.equals("08S01"))
/*      */           {
/*      */             
/* 2654 */             cleanup(sqlE);
/*      */           }
/*      */         } 
/*      */         
/* 2658 */         throw sqlE;
/* 2659 */       } catch (Exception ex) {
/* 2660 */         if (getHighAvailability()) {
/* 2661 */           this.needsPing = true;
/* 2662 */         } else if (ex instanceof IOException) {
/* 2663 */           cleanup(ex);
/*      */         } 
/*      */         
/* 2666 */         SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnexpectedException"), "S1000", getExceptionInterceptor());
/*      */ 
/*      */         
/* 2669 */         sqlEx.initCause(ex);
/*      */         
/* 2671 */         throw sqlEx;
/*      */       } finally {
/* 2673 */         if (getMaintainTimeStats()) {
/* 2674 */           this.lastQueryFinishedTime = System.currentTimeMillis();
/*      */         }
/*      */ 
/*      */         
/* 2678 */         if (getGatherPerformanceMetrics()) {
/* 2679 */           long queryTime = System.currentTimeMillis() - queryStartTime;
/*      */ 
/*      */           
/* 2682 */           registerQueryExecutionTime(queryTime);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String extractSqlFromPacket(String possibleSqlQuery, Buffer queryPacket, int endOfQueryPacketPosition) throws SQLException {
/* 2692 */     String extractedSql = null;
/*      */     
/* 2694 */     if (possibleSqlQuery != null) {
/* 2695 */       if (possibleSqlQuery.length() > getMaxQuerySizeToLog()) {
/* 2696 */         StringBuffer truncatedQueryBuf = new StringBuffer(possibleSqlQuery.substring(0, getMaxQuerySizeToLog()));
/*      */         
/* 2698 */         truncatedQueryBuf.append(Messages.getString("MysqlIO.25"));
/* 2699 */         extractedSql = truncatedQueryBuf.toString();
/*      */       } else {
/* 2701 */         extractedSql = possibleSqlQuery;
/*      */       } 
/*      */     }
/*      */     
/* 2705 */     if (extractedSql == null) {
/*      */ 
/*      */ 
/*      */       
/* 2709 */       int extractPosition = endOfQueryPacketPosition;
/*      */       
/* 2711 */       boolean truncated = false;
/*      */       
/* 2713 */       if (endOfQueryPacketPosition > getMaxQuerySizeToLog()) {
/* 2714 */         extractPosition = getMaxQuerySizeToLog();
/* 2715 */         truncated = true;
/*      */       } 
/*      */       
/* 2718 */       extractedSql = new String(queryPacket.getByteBuffer(), 5, extractPosition - 5);
/*      */ 
/*      */       
/* 2721 */       if (truncated) {
/* 2722 */         extractedSql = extractedSql + Messages.getString("MysqlIO.25");
/*      */       }
/*      */     } 
/*      */     
/* 2726 */     return extractedSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finalize() {
/* 2737 */     cleanup(null);
/*      */     
/* 2739 */     super.finalize();
/*      */   }
/*      */   
/*      */   public StringBuffer generateConnectionCommentBlock(StringBuffer buf) {
/* 2743 */     buf.append("/* conn id ");
/* 2744 */     buf.append(getId());
/* 2745 */     buf.append(" clock: ");
/* 2746 */     buf.append(System.currentTimeMillis());
/* 2747 */     buf.append(" */ ");
/*      */     
/* 2749 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActiveStatementCount() {
/* 2755 */     if (this.openStatements != null) {
/* 2756 */       synchronized (this.openStatements) {
/* 2757 */         return this.openStatements.size();
/*      */       } 
/*      */     }
/*      */     
/* 2761 */     return 0;
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
/* 2773 */   public boolean getAutoCommit() { return this.autoCommit; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCalendarInstanceForSessionOrNew() {
/* 2781 */     if (getDynamicCalendars()) {
/* 2782 */       return Calendar.getInstance();
/*      */     }
/*      */     
/* 2785 */     return getSessionLockedCalendar();
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
/* 2800 */   public String getCatalog() { return this.database; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2807 */   public String getCharacterSetMetadata() { return this.characterSetMetadata; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SingleByteCharsetConverter getCharsetConverter(String javaEncodingName) throws SQLException {
/* 2820 */     if (javaEncodingName == null) {
/* 2821 */       return null;
/*      */     }
/*      */     
/* 2824 */     if (this.usePlatformCharsetConverters) {
/* 2825 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2829 */     SingleByteCharsetConverter converter = null;
/*      */     
/* 2831 */     synchronized (this.charsetConverterMap) {
/* 2832 */       Object asObject = this.charsetConverterMap.get(javaEncodingName);
/*      */ 
/*      */       
/* 2835 */       if (asObject == CHARSET_CONVERTER_NOT_AVAILABLE_MARKER) {
/* 2836 */         return null;
/*      */       }
/*      */       
/* 2839 */       converter = (SingleByteCharsetConverter)asObject;
/*      */       
/* 2841 */       if (converter == null) {
/*      */         try {
/* 2843 */           converter = SingleByteCharsetConverter.getInstance(javaEncodingName, this);
/*      */ 
/*      */           
/* 2846 */           if (converter == null) {
/* 2847 */             this.charsetConverterMap.put(javaEncodingName, CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
/*      */           } else {
/*      */             
/* 2850 */             this.charsetConverterMap.put(javaEncodingName, converter);
/*      */           } 
/* 2852 */         } catch (UnsupportedEncodingException unsupEncEx) {
/* 2853 */           this.charsetConverterMap.put(javaEncodingName, CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
/*      */ 
/*      */           
/* 2856 */           converter = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2861 */     return converter;
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
/*      */   public String getCharsetNameForIndex(int charsetIndex) throws SQLException {
/* 2876 */     String charsetName = null;
/*      */     
/* 2878 */     if (getUseOldUTF8Behavior()) {
/* 2879 */       return getEncoding();
/*      */     }
/*      */     
/* 2882 */     if (charsetIndex != -1) {
/*      */       try {
/* 2884 */         charsetName = this.indexToCharsetMapping[charsetIndex];
/*      */         
/* 2886 */         if ("sjis".equalsIgnoreCase(charsetName) || "MS932".equalsIgnoreCase(charsetName))
/*      */         {
/*      */           
/* 2889 */           if (CharsetMapping.isAliasForSjis(getEncoding())) {
/* 2890 */             charsetName = getEncoding();
/*      */           }
/*      */         }
/* 2893 */       } catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
/* 2894 */         throw SQLError.createSQLException("Unknown character set index for field '" + charsetIndex + "' received from server.", "S1000", getExceptionInterceptor());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2901 */       if (charsetName == null) {
/* 2902 */         charsetName = getEncoding();
/*      */       }
/*      */     } else {
/* 2905 */       charsetName = getEncoding();
/*      */     } 
/*      */     
/* 2908 */     return charsetName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2917 */   public TimeZone getDefaultTimeZone() { return this.defaultTimeZone; }
/*      */ 
/*      */ 
/*      */   
/* 2921 */   public String getErrorMessageEncoding() { return this.errorMessageEncoding; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2928 */   public int getHoldability() { return 2; }
/*      */ 
/*      */ 
/*      */   
/* 2932 */   public long getId() { return this.connectionId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getIdleFor() {
/* 2944 */     if (this.lastQueryFinishedTime == 0L) {
/* 2945 */       return 0L;
/*      */     }
/*      */     
/* 2948 */     long now = System.currentTimeMillis();
/* 2949 */     return now - this.lastQueryFinishedTime;
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
/*      */   public MysqlIO getIO() throws SQLException {
/* 2962 */     if (this.io == null || this.isClosed) {
/* 2963 */       throw SQLError.createSQLException("Operation not allowed on closed connection", "08003", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2968 */     return this.io;
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
/* 2980 */   public Log getLog() throws SQLException { return this.log; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxBytesPerChar(String javaCharsetName) throws SQLException {
/* 2986 */     String charset = CharsetMapping.getMysqlEncodingForJavaEncoding(javaCharsetName, this);
/*      */ 
/*      */     
/* 2989 */     if (this.io.serverCharsetIndex == 33 && versionMeetsMinimum(5, 5, 3) && javaCharsetName.equalsIgnoreCase("UTF-8"))
/*      */     {
/* 2991 */       charset = "utf8";
/*      */     }
/*      */     
/* 2994 */     if (versionMeetsMinimum(4, 1, 0)) {
/* 2995 */       Map mapToCheck = null;
/*      */       
/* 2997 */       if (!getUseDynamicCharsetInfo()) {
/* 2998 */         mapToCheck = CharsetMapping.STATIC_CHARSET_TO_NUM_BYTES_MAP;
/*      */       } else {
/* 3000 */         mapToCheck = this.charsetToNumBytesMap;
/*      */         
/* 3002 */         synchronized (this.charsetToNumBytesMap) {
/* 3003 */           if (this.charsetToNumBytesMap.isEmpty()) {
/*      */             
/* 3005 */             Statement stmt = null;
/* 3006 */             ResultSet rs = null;
/*      */             
/*      */             try {
/* 3009 */               stmt = getMetadataSafeStatement();
/*      */               
/* 3011 */               rs = stmt.executeQuery("SHOW CHARACTER SET");
/*      */               
/* 3013 */               while (rs.next()) {
/* 3014 */                 this.charsetToNumBytesMap.put(rs.getString("Charset"), Constants.integerValueOf(rs.getInt("Maxlen")));
/*      */               }
/*      */ 
/*      */               
/* 3018 */               rs.close();
/* 3019 */               rs = null;
/*      */               
/* 3021 */               stmt.close();
/*      */               
/* 3023 */               stmt = null;
/*      */             } finally {
/* 3025 */               if (rs != null) {
/* 3026 */                 rs.close();
/* 3027 */                 rs = null;
/*      */               } 
/*      */               
/* 3030 */               if (stmt != null) {
/* 3031 */                 stmt.close();
/* 3032 */                 stmt = null;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3042 */       Integer mbPerChar = (Integer)mapToCheck.get(charset);
/*      */       
/* 3044 */       if (mbPerChar != null) {
/* 3045 */         return mbPerChar.intValue();
/*      */       }
/*      */       
/* 3048 */       return 1;
/*      */     } 
/*      */     
/* 3051 */     return 1;
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
/* 3065 */   public DatabaseMetaData getMetaData() throws SQLException { return getMetaData(true, true); }
/*      */ 
/*      */   
/*      */   private DatabaseMetaData getMetaData(boolean checkClosed, boolean checkForInfoSchema) throws SQLException {
/* 3069 */     if (checkClosed) {
/* 3070 */       checkClosed();
/*      */     }
/*      */     
/* 3073 */     return DatabaseMetaData.getInstance(getLoadBalanceSafeProxy(), this.database, checkForInfoSchema);
/*      */   }
/*      */   
/*      */   public Statement getMetadataSafeStatement() throws SQLException {
/* 3077 */     Statement stmt = createStatement();
/*      */     
/* 3079 */     if (stmt.getMaxRows() != 0) {
/* 3080 */       stmt.setMaxRows(0);
/*      */     }
/*      */     
/* 3083 */     stmt.setEscapeProcessing(false);
/*      */     
/* 3085 */     if (stmt.getFetchSize() != 0) {
/* 3086 */       stmt.setFetchSize(0);
/*      */     }
/*      */     
/* 3089 */     return stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getMutex() throws SQLException {
/* 3100 */     if (this.io == null) {
/* 3101 */       throwConnectionClosedException();
/*      */     }
/*      */     
/* 3104 */     reportMetricsIfNeeded();
/*      */     
/* 3106 */     return this.mutex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3115 */   public int getNetBufferLength() { return this.netBufferLength; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerCharacterEncoding() {
/* 3124 */     if (this.io.versionMeetsMinimum(4, 1, 0)) {
/* 3125 */       return (String)this.serverVariables.get("character_set_server");
/*      */     }
/* 3127 */     return (String)this.serverVariables.get("character_set");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 3132 */   public int getServerMajorVersion() { return this.io.getServerMajorVersion(); }
/*      */ 
/*      */ 
/*      */   
/* 3136 */   public int getServerMinorVersion() { return this.io.getServerMinorVersion(); }
/*      */ 
/*      */ 
/*      */   
/* 3140 */   public int getServerSubMinorVersion() { return this.io.getServerSubMinorVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3149 */   public TimeZone getServerTimezoneTZ() { return this.serverTimezoneTZ; }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerVariable(String variableName) {
/* 3154 */     if (this.serverVariables != null) {
/* 3155 */       return (String)this.serverVariables.get(variableName);
/*      */     }
/*      */     
/* 3158 */     return null;
/*      */   }
/*      */ 
/*      */   
/* 3162 */   public String getServerVersion() { return this.io.getServerVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3167 */   public Calendar getSessionLockedCalendar() { return this.sessionCalendar; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransactionIsolation() {
/* 3179 */     if (this.hasIsolationLevels && !getUseLocalSessionState()) {
/* 3180 */       Statement stmt = null;
/* 3181 */       ResultSet rs = null;
/*      */       
/*      */       try {
/* 3184 */         stmt = getMetadataSafeStatement();
/*      */         
/* 3186 */         String query = null;
/*      */         
/* 3188 */         int offset = 0;
/*      */         
/* 3190 */         if (versionMeetsMinimum(4, 0, 3)) {
/* 3191 */           query = "SELECT @@session.tx_isolation";
/* 3192 */           offset = 1;
/*      */         } else {
/* 3194 */           query = "SHOW VARIABLES LIKE 'transaction_isolation'";
/* 3195 */           offset = 2;
/*      */         } 
/*      */         
/* 3198 */         rs = stmt.executeQuery(query);
/*      */         
/* 3200 */         if (rs.next()) {
/* 3201 */           String s = rs.getString(offset);
/*      */           
/* 3203 */           if (s != null) {
/* 3204 */             Integer intTI = (Integer)mapTransIsolationNameToValue.get(s);
/*      */ 
/*      */             
/* 3207 */             if (intTI != null) {
/* 3208 */               return intTI.intValue();
/*      */             }
/*      */           } 
/*      */           
/* 3212 */           throw SQLError.createSQLException("Could not map transaction isolation '" + s + " to a valid JDBC level.", "S1000", getExceptionInterceptor());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3218 */         throw SQLError.createSQLException("Could not retrieve transaction isolation level from server", "S1000", getExceptionInterceptor());
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 3223 */         if (rs != null) {
/*      */           try {
/* 3225 */             rs.close();
/* 3226 */           } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3231 */           rs = null;
/*      */         } 
/*      */         
/* 3234 */         if (stmt != null) {
/*      */           try {
/* 3236 */             stmt.close();
/* 3237 */           } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3242 */           stmt = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3247 */     return this.isolationLevel;
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
/*      */   public Map getTypeMap() throws SQLException {
/* 3259 */     if (this.typeMap == null) {
/* 3260 */       this.typeMap = new HashMap();
/*      */     }
/*      */     
/* 3263 */     return this.typeMap;
/*      */   }
/*      */ 
/*      */   
/* 3267 */   public String getURL() { return this.myURL; }
/*      */ 
/*      */ 
/*      */   
/* 3271 */   public String getUser() { return this.user; }
/*      */ 
/*      */ 
/*      */   
/* 3275 */   public Calendar getUtcCalendar() { return this.utcCalendar; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3288 */   public SQLWarning getWarnings() throws SQLException { return null; }
/*      */ 
/*      */ 
/*      */   
/* 3292 */   public boolean hasSameProperties(Connection c) { return this.props.equals(c.getProperties()); }
/*      */ 
/*      */ 
/*      */   
/* 3296 */   public Properties getProperties() { return this.props; }
/*      */ 
/*      */ 
/*      */   
/* 3300 */   public boolean hasTriedMaster() { return this.hasTriedMasterFlag; }
/*      */ 
/*      */   
/*      */   public void incrementNumberOfPreparedExecutes() {
/* 3304 */     if (getGatherPerformanceMetrics()) {
/* 3305 */       this.numberOfPreparedExecutes++;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3310 */       this.numberOfQueriesIssued++;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void incrementNumberOfPrepares() {
/* 3315 */     if (getGatherPerformanceMetrics()) {
/* 3316 */       this.numberOfPrepares++;
/*      */     }
/*      */   }
/*      */   
/*      */   public void incrementNumberOfResultSetsCreated() {
/* 3321 */     if (getGatherPerformanceMetrics()) {
/* 3322 */       this.numberOfResultSetsCreated++;
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
/*      */   private void initializeDriverProperties(Properties info) throws SQLException, IOException {
/* 3337 */     initializeProperties(info);
/*      */     
/* 3339 */     String exceptionInterceptorClasses = getExceptionInterceptors();
/*      */     
/* 3341 */     if (exceptionInterceptorClasses != null && !"".equals(exceptionInterceptorClasses)) {
/* 3342 */       this.exceptionInterceptor = new ExceptionInterceptorChain(exceptionInterceptorClasses);
/* 3343 */       this.exceptionInterceptor.init(this, info);
/*      */     } 
/*      */     
/* 3346 */     this.usePlatformCharsetConverters = getUseJvmCharsetConverters();
/*      */     
/* 3348 */     this.log = LogFactory.getLogger(getLogger(), "MySQL", getExceptionInterceptor());
/*      */     
/* 3350 */     if (getProfileSql() || getUseUsageAdvisor()) {
/* 3351 */       this.eventSink = ProfilerEventHandlerFactory.getInstance(getLoadBalanceSafeProxy());
/*      */     }
/*      */     
/* 3354 */     if (getCachePreparedStatements()) {
/* 3355 */       createPreparedStatementCaches();
/*      */     }
/*      */     
/* 3358 */     if (getNoDatetimeStringSync() && getUseTimezone()) {
/* 3359 */       throw SQLError.createSQLException("Can't enable noDatetimeSync and useTimezone configuration properties at the same time", "01S00", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3365 */     if (getCacheCallableStatements()) {
/* 3366 */       this.parsedCallableStatementCache = new LRUCache(getCallableStatementCacheSize());
/*      */     }
/*      */ 
/*      */     
/* 3370 */     if (getAllowMultiQueries()) {
/* 3371 */       setCacheResultSetMetadata(false);
/*      */     }
/*      */     
/* 3374 */     if (getCacheResultSetMetadata()) {
/* 3375 */       this.resultSetMetadataCache = new LRUCache(getMetadataCacheSize());
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
/*      */   private void initializePropsFromServer() {
/* 3390 */     String connectionInterceptorClasses = getConnectionLifecycleInterceptors();
/*      */     
/* 3392 */     this.connectionLifecycleInterceptors = null;
/*      */     
/* 3394 */     if (connectionInterceptorClasses != null) {
/* 3395 */       this.connectionLifecycleInterceptors = Util.loadExtensions(this, this.props, connectionInterceptorClasses, "Connection.badLifecycleInterceptor", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3400 */     setSessionVariables();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3406 */     if (!versionMeetsMinimum(4, 1, 0)) {
/* 3407 */       setTransformedBitIsBoolean(false);
/*      */     }
/*      */     
/* 3410 */     this.parserKnowsUnicode = versionMeetsMinimum(4, 1, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3415 */     if (getUseServerPreparedStmts() && versionMeetsMinimum(4, 1, 0)) {
/* 3416 */       this.useServerPreparedStmts = true;
/*      */       
/* 3418 */       if (versionMeetsMinimum(5, 0, 0) && !versionMeetsMinimum(5, 0, 3)) {
/* 3419 */         this.useServerPreparedStmts = false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3425 */     this.serverVariables.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3430 */     if (versionMeetsMinimum(3, 21, 22)) {
/* 3431 */       loadServerVariables();
/*      */       
/* 3433 */       if (versionMeetsMinimum(5, 0, 2)) {
/* 3434 */         this.autoIncrementIncrement = getServerVariableAsInt("auto_increment_increment", 1);
/*      */       } else {
/* 3436 */         this.autoIncrementIncrement = 1;
/*      */       } 
/*      */       
/* 3439 */       buildCollationMapping();
/*      */       
/* 3441 */       LicenseConfiguration.checkLicenseType(this.serverVariables);
/*      */       
/* 3443 */       String lowerCaseTables = (String)this.serverVariables.get("lower_case_table_names");
/*      */ 
/*      */       
/* 3446 */       this.lowerCaseTableNames = ("on".equalsIgnoreCase(lowerCaseTables) || "1".equalsIgnoreCase(lowerCaseTables) || "2".equalsIgnoreCase(lowerCaseTables));
/*      */ 
/*      */ 
/*      */       
/* 3450 */       this.storesLowerCaseTableName = ("1".equalsIgnoreCase(lowerCaseTables) || "on".equalsIgnoreCase(lowerCaseTables));
/*      */ 
/*      */       
/* 3453 */       configureTimezone();
/*      */       
/* 3455 */       if (this.serverVariables.containsKey("max_allowed_packet")) {
/* 3456 */         int serverMaxAllowedPacket = getServerVariableAsInt("max_allowed_packet", -1);
/*      */         
/* 3458 */         if (serverMaxAllowedPacket != -1 && (serverMaxAllowedPacket < getMaxAllowedPacket() || getMaxAllowedPacket() <= 0)) {
/*      */           
/* 3460 */           setMaxAllowedPacket(serverMaxAllowedPacket);
/* 3461 */         } else if (serverMaxAllowedPacket == -1 && getMaxAllowedPacket() == -1) {
/* 3462 */           setMaxAllowedPacket(65535);
/*      */         } 
/* 3464 */         int preferredBlobSendChunkSize = getBlobSendChunkSize();
/*      */         
/* 3466 */         int allowedBlobSendChunkSize = Math.min(preferredBlobSendChunkSize, getMaxAllowedPacket()) - 8192 - 11;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3471 */         setBlobSendChunkSize(String.valueOf(allowedBlobSendChunkSize));
/*      */       } 
/*      */       
/* 3474 */       if (this.serverVariables.containsKey("net_buffer_length")) {
/* 3475 */         this.netBufferLength = getServerVariableAsInt("net_buffer_length", 16384);
/*      */       }
/*      */       
/* 3478 */       checkTransactionIsolationLevel();
/*      */       
/* 3480 */       if (!versionMeetsMinimum(4, 1, 0)) {
/* 3481 */         checkServerEncoding();
/*      */       }
/*      */       
/* 3484 */       this.io.checkForCharsetMismatch();
/*      */       
/* 3486 */       if (this.serverVariables.containsKey("sql_mode")) {
/* 3487 */         int sqlMode = 0;
/*      */         
/* 3489 */         String sqlModeAsString = (String)this.serverVariables.get("sql_mode");
/*      */         
/*      */         try {
/* 3492 */           sqlMode = Integer.parseInt(sqlModeAsString);
/* 3493 */         } catch (NumberFormatException nfe) {
/*      */ 
/*      */           
/* 3496 */           sqlMode = 0;
/*      */           
/* 3498 */           if (sqlModeAsString != null) {
/* 3499 */             if (sqlModeAsString.indexOf("ANSI_QUOTES") != -1) {
/* 3500 */               sqlMode |= 0x4;
/*      */             }
/*      */             
/* 3503 */             if (sqlModeAsString.indexOf("NO_BACKSLASH_ESCAPES") != -1) {
/* 3504 */               this.noBackslashEscapes = true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 3509 */         if ((sqlMode & 0x4) > 0) {
/* 3510 */           this.useAnsiQuotes = true;
/*      */         } else {
/* 3512 */           this.useAnsiQuotes = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3517 */     this.errorMessageEncoding = CharsetMapping.getCharacterEncodingForErrorMessages(this);
/*      */ 
/*      */ 
/*      */     
/* 3521 */     boolean overrideDefaultAutocommit = isAutoCommitNonDefaultOnServer();
/*      */     
/* 3523 */     configureClientCharacterSet(false);
/*      */     
/* 3525 */     if (versionMeetsMinimum(3, 23, 15)) {
/* 3526 */       this.transactionsSupported = true;
/*      */       
/* 3528 */       if (!overrideDefaultAutocommit) {
/* 3529 */         setAutoCommit(true);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 3534 */       this.transactionsSupported = false;
/*      */     } 
/*      */ 
/*      */     
/* 3538 */     if (versionMeetsMinimum(3, 23, 36)) {
/* 3539 */       this.hasIsolationLevels = true;
/*      */     } else {
/* 3541 */       this.hasIsolationLevels = false;
/*      */     } 
/*      */     
/* 3544 */     this.hasQuotedIdentifiers = versionMeetsMinimum(3, 23, 6);
/*      */     
/* 3546 */     this.io.resetMaxBuf();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3556 */     if (this.io.versionMeetsMinimum(4, 1, 0)) {
/* 3557 */       String characterSetResultsOnServerMysql = (String)this.serverVariables.get("jdbc.local.character_set_results");
/*      */ 
/*      */       
/* 3560 */       if (characterSetResultsOnServerMysql == null || StringUtils.startsWithIgnoreCaseAndWs(characterSetResultsOnServerMysql, "NULL") || characterSetResultsOnServerMysql.length() == 0) {
/*      */ 
/*      */ 
/*      */         
/* 3564 */         String defaultMetadataCharsetMysql = (String)this.serverVariables.get("character_set_system");
/*      */         
/* 3566 */         String defaultMetadataCharset = null;
/*      */         
/* 3568 */         if (defaultMetadataCharsetMysql != null) {
/* 3569 */           defaultMetadataCharset = CharsetMapping.getJavaEncodingForMysqlEncoding(defaultMetadataCharsetMysql, this);
/*      */         }
/*      */         else {
/*      */           
/* 3573 */           defaultMetadataCharset = "UTF-8";
/*      */         } 
/*      */         
/* 3576 */         this.characterSetMetadata = defaultMetadataCharset;
/*      */       } else {
/* 3578 */         this.characterSetResultsOnServer = CharsetMapping.getJavaEncodingForMysqlEncoding(characterSetResultsOnServerMysql, this);
/*      */ 
/*      */         
/* 3581 */         this.characterSetMetadata = this.characterSetResultsOnServer;
/*      */       } 
/*      */     } else {
/* 3584 */       this.characterSetMetadata = getEncoding();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3591 */     if (versionMeetsMinimum(4, 1, 0) && !versionMeetsMinimum(4, 1, 10) && getAllowMultiQueries())
/*      */     {
/*      */       
/* 3594 */       if (isQueryCacheEnabled()) {
/* 3595 */         setAllowMultiQueries(false);
/*      */       }
/*      */     }
/*      */     
/* 3599 */     if (versionMeetsMinimum(5, 0, 0) && (getUseLocalTransactionState() || getElideSetAutoCommits()) && isQueryCacheEnabled() && !versionMeetsMinimum(6, 0, 10)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3604 */       setUseLocalTransactionState(false);
/* 3605 */       setElideSetAutoCommits(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3612 */     setupServerForTruncationChecks();
/*      */   }
/*      */ 
/*      */   
/* 3616 */   private boolean isQueryCacheEnabled() { return ("ON".equalsIgnoreCase((String)this.serverVariables.get("query_cache_type")) && !"0".equalsIgnoreCase((String)this.serverVariables.get("query_cache_size"))); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getServerVariableAsInt(String variableName, int fallbackValue) throws SQLException {
/*      */     try {
/* 3625 */       return Integer.parseInt((String)this.serverVariables.get(variableName));
/*      */     }
/* 3627 */     catch (NumberFormatException nfe) {
/* 3628 */       getLog().logWarn(Messages.getString("Connection.BadValueInServerVariables", new Object[] { variableName, this.serverVariables.get(variableName), new Integer(fallbackValue) }));
/*      */ 
/*      */       
/* 3631 */       return fallbackValue;
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
/*      */   private boolean isAutoCommitNonDefaultOnServer() {
/* 3644 */     boolean overrideDefaultAutocommit = false;
/*      */     
/* 3646 */     String initConnectValue = (String)this.serverVariables.get("init_connect");
/*      */ 
/*      */     
/* 3649 */     if (versionMeetsMinimum(4, 1, 2) && initConnectValue != null && initConnectValue.length() > 0)
/*      */     {
/* 3651 */       if (!getElideSetAutoCommits()) {
/*      */         
/* 3653 */         ResultSet rs = null;
/* 3654 */         Statement stmt = null;
/*      */         
/*      */         try {
/* 3657 */           stmt = getMetadataSafeStatement();
/*      */           
/* 3659 */           rs = stmt.executeQuery("SELECT @@session.autocommit");
/*      */           
/* 3661 */           if (rs.next()) {
/* 3662 */             this.autoCommit = rs.getBoolean(1);
/* 3663 */             if (this.autoCommit != true) {
/* 3664 */               overrideDefaultAutocommit = true;
/*      */             }
/*      */           } 
/*      */         } finally {
/*      */           
/* 3669 */           if (rs != null) {
/*      */             try {
/* 3671 */               rs.close();
/* 3672 */             } catch (SQLException sqlEx) {}
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 3677 */           if (stmt != null) {
/*      */             try {
/* 3679 */               stmt.close();
/* 3680 */             } catch (SQLException sqlEx) {}
/*      */           
/*      */           }
/*      */         }
/*      */       
/*      */       }
/* 3686 */       else if (getIO().isSetNeededForAutoCommitMode(true)) {
/*      */         
/* 3688 */         this.autoCommit = false;
/* 3689 */         overrideDefaultAutocommit = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3694 */     return overrideDefaultAutocommit;
/*      */   }
/*      */ 
/*      */   
/* 3698 */   public boolean isClientTzUTC() { return this.isClientTzUTC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3707 */   public boolean isClosed() { return this.isClosed; }
/*      */ 
/*      */ 
/*      */   
/* 3711 */   public boolean isCursorFetchEnabled() { return (versionMeetsMinimum(5, 0, 2) && getUseCursorFetch()); }
/*      */ 
/*      */ 
/*      */   
/* 3715 */   public boolean isInGlobalTx() { return this.isInGlobalTx; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3726 */   public boolean isMasterConnection() { return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3736 */   public boolean isNoBackslashEscapesSet() { return this.noBackslashEscapes; }
/*      */ 
/*      */ 
/*      */   
/* 3740 */   public boolean isReadInfoMsgEnabled() { return this.readInfoMsg; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3753 */   public boolean isReadOnly() { return this.readOnly; }
/*      */ 
/*      */ 
/*      */   
/* 3757 */   public boolean isRunningOnJDK13() { return this.isRunningOnJDK13; }
/*      */ 
/*      */   
/*      */   public boolean isSameResource(Connection otherConnection) {
/* 3761 */     if (otherConnection == null) {
/* 3762 */       return false;
/*      */     }
/*      */     
/* 3765 */     boolean directCompare = true;
/*      */     
/* 3767 */     String otherHost = ((ConnectionImpl)otherConnection).origHostToConnectTo;
/* 3768 */     String otherOrigDatabase = ((ConnectionImpl)otherConnection).origDatabaseToConnectTo;
/* 3769 */     String otherCurrentCatalog = ((ConnectionImpl)otherConnection).database;
/*      */     
/* 3771 */     if (!nullSafeCompare(otherHost, this.origHostToConnectTo)) {
/* 3772 */       directCompare = false;
/* 3773 */     } else if (otherHost != null && otherHost.indexOf(',') == -1 && otherHost.indexOf(':') == -1) {
/*      */ 
/*      */       
/* 3776 */       directCompare = (((ConnectionImpl)otherConnection).origPortToConnectTo == this.origPortToConnectTo);
/*      */     } 
/*      */ 
/*      */     
/* 3780 */     if (directCompare) {
/* 3781 */       if (!nullSafeCompare(otherOrigDatabase, this.origDatabaseToConnectTo)) { directCompare = false;
/* 3782 */         directCompare = false; }
/* 3783 */       else if (!nullSafeCompare(otherCurrentCatalog, this.database))
/* 3784 */       { directCompare = false; }
/*      */     
/*      */     }
/*      */     
/* 3788 */     if (directCompare) {
/* 3789 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3793 */     String otherResourceId = ((ConnectionImpl)otherConnection).getResourceId();
/* 3794 */     String myResourceId = getResourceId();
/*      */     
/* 3796 */     if (otherResourceId != null || myResourceId != null) {
/* 3797 */       directCompare = nullSafeCompare(otherResourceId, myResourceId);
/*      */       
/* 3799 */       if (directCompare) {
/* 3800 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3804 */     return false;
/*      */   }
/*      */ 
/*      */   
/* 3808 */   public boolean isServerTzUTC() { return this.isServerTzUTC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean usingCachedConfig = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadServerVariables() {
/* 3822 */     if (getCacheServerConfiguration()) {
/* 3823 */       synchronized (serverConfigByUrl) {
/* 3824 */         Map cachedVariableMap = (Map)serverConfigByUrl.get(getURL());
/*      */         
/* 3826 */         if (cachedVariableMap != null) {
/* 3827 */           this.serverVariables = cachedVariableMap;
/* 3828 */           this.usingCachedConfig = true;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 3835 */     Statement stmt = null;
/* 3836 */     ResultSet results = null;
/*      */     
/*      */     try {
/* 3839 */       stmt = getMetadataSafeStatement();
/*      */       
/* 3841 */       String version = this.dbmd.getDriverVersion();
/*      */       
/* 3843 */       if (version != null && version.indexOf('*') != -1) {
/* 3844 */         StringBuffer buf = new StringBuffer(version.length() + 10);
/*      */         
/* 3846 */         for (int i = 0; i < version.length(); i++) {
/* 3847 */           char c = version.charAt(i);
/*      */           
/* 3849 */           if (c == '*') {
/* 3850 */             buf.append("[star]");
/*      */           } else {
/* 3852 */             buf.append(c);
/*      */           } 
/*      */         } 
/*      */         
/* 3856 */         version = buf.toString();
/*      */       } 
/*      */       
/* 3859 */       String versionComment = (getParanoid() || version == null) ? "" : ("/* " + version + " */");
/*      */ 
/*      */       
/* 3862 */       String query = versionComment + "SHOW VARIABLES";
/*      */       
/* 3864 */       if (versionMeetsMinimum(5, 0, 3)) {
/* 3865 */         query = versionComment + "SHOW VARIABLES WHERE Variable_name ='language'" + " OR Variable_name = 'net_write_timeout'" + " OR Variable_name = 'interactive_timeout'" + " OR Variable_name = 'wait_timeout'" + " OR Variable_name = 'character_set_client'" + " OR Variable_name = 'character_set_connection'" + " OR Variable_name = 'character_set'" + " OR Variable_name = 'character_set_server'" + " OR Variable_name = 'tx_isolation'" + " OR Variable_name = 'transaction_isolation'" + " OR Variable_name = 'character_set_results'" + " OR Variable_name = 'timezone'" + " OR Variable_name = 'time_zone'" + " OR Variable_name = 'system_time_zone'" + " OR Variable_name = 'lower_case_table_names'" + " OR Variable_name = 'max_allowed_packet'" + " OR Variable_name = 'net_buffer_length'" + " OR Variable_name = 'sql_mode'" + " OR Variable_name = 'query_cache_type'" + " OR Variable_name = 'query_cache_size'" + " OR Variable_name = 'init_connect'";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3888 */       results = stmt.executeQuery(query);
/*      */       
/* 3890 */       while (results.next()) {
/* 3891 */         this.serverVariables.put(results.getString(1), results.getString(2));
/*      */       }
/*      */ 
/*      */       
/* 3895 */       if (versionMeetsMinimum(5, 0, 2)) {
/* 3896 */         results = stmt.executeQuery(versionComment + "SELECT @@session.auto_increment_increment");
/*      */         
/* 3898 */         if (results.next()) {
/* 3899 */           this.serverVariables.put("auto_increment_increment", results.getString(1));
/*      */         }
/*      */       } 
/*      */       
/* 3903 */       if (getCacheServerConfiguration()) {
/* 3904 */         synchronized (serverConfigByUrl) {
/* 3905 */           serverConfigByUrl.put(getURL(), this.serverVariables);
/*      */         }
/*      */       
/*      */       }
/*      */     }
/* 3910 */     catch (SQLException e) {
/* 3911 */       throw e;
/*      */     } finally {
/* 3913 */       if (results != null) {
/*      */         try {
/* 3915 */           results.close();
/* 3916 */         } catch (SQLException sqlE) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3921 */       if (stmt != null) {
/*      */         try {
/* 3923 */           stmt.close();
/* 3924 */         } catch (SQLException sqlE) {}
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 3931 */   private int autoIncrementIncrement = 0;
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */   
/* 3934 */   public int getAutoIncrementIncrement() { return this.autoIncrementIncrement; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3943 */   public boolean lowerCaseTableNames() { return this.lowerCaseTableNames; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void maxRowsChanged(Statement stmt) {
/* 3953 */     synchronized (this.mutex) {
/* 3954 */       if (this.statementsUsingMaxRows == null) {
/* 3955 */         this.statementsUsingMaxRows = new HashMap();
/*      */       }
/*      */       
/* 3958 */       this.statementsUsingMaxRows.put(stmt, stmt);
/*      */       
/* 3960 */       this.maxRowsChanged = true;
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
/*      */   public String nativeSQL(String sql) {
/* 3977 */     if (sql == null) {
/* 3978 */       return null;
/*      */     }
/*      */     
/* 3981 */     Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, serverSupportsConvertFn(), getLoadBalanceSafeProxy());
/*      */ 
/*      */ 
/*      */     
/* 3985 */     if (escapedSqlResult instanceof String) {
/* 3986 */       return (String)escapedSqlResult;
/*      */     }
/*      */     
/* 3989 */     return ((EscapeProcessorResult)escapedSqlResult).escapedSql;
/*      */   }
/*      */ 
/*      */   
/*      */   private CallableStatement parseCallableStatement(String sql) throws SQLException {
/* 3994 */     Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, serverSupportsConvertFn(), getLoadBalanceSafeProxy());
/*      */ 
/*      */     
/* 3997 */     boolean isFunctionCall = false;
/* 3998 */     String parsedSql = null;
/*      */     
/* 4000 */     if (escapedSqlResult instanceof EscapeProcessorResult) {
/* 4001 */       parsedSql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
/* 4002 */       isFunctionCall = ((EscapeProcessorResult)escapedSqlResult).callingStoredFunction;
/*      */     } else {
/* 4004 */       parsedSql = (String)escapedSqlResult;
/* 4005 */       isFunctionCall = false;
/*      */     } 
/*      */     
/* 4008 */     return CallableStatement.getInstance(getLoadBalanceSafeProxy(), parsedSql, this.database, isFunctionCall);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4018 */   public boolean parserKnowsUnicode() { return this.parserKnowsUnicode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4028 */   public void ping() { pingInternal(true, 0); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void pingInternal(boolean checkForClosedConnection, int timeoutMillis) throws SQLException {
/* 4033 */     if (checkForClosedConnection) {
/* 4034 */       checkClosed();
/*      */     }
/*      */     
/* 4037 */     long pingMillisLifetime = getSelfDestructOnPingSecondsLifetime();
/* 4038 */     int pingMaxOperations = getSelfDestructOnPingMaxOperations();
/*      */     
/* 4040 */     if ((pingMillisLifetime > 0L && System.currentTimeMillis() - this.connectionCreationTimeMillis > pingMillisLifetime) || (pingMaxOperations > 0 && pingMaxOperations <= this.io.getCommandCount())) {
/*      */ 
/*      */ 
/*      */       
/* 4044 */       close();
/*      */       
/* 4046 */       throw SQLError.createSQLException(Messages.getString("Connection.exceededConnectionLifetime"), "08S01", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4051 */     this.io.sendCommand(14, null, null, false, null, timeoutMillis);
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
/* 4066 */   public CallableStatement prepareCall(String sql) throws SQLException { return prepareCall(sql, 1003, 1007); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 4087 */     if (versionMeetsMinimum(5, 0, 0)) {
/* 4088 */       CallableStatement cStmt = null;
/*      */       
/* 4090 */       if (!getCacheCallableStatements()) {
/*      */         
/* 4092 */         cStmt = parseCallableStatement(sql);
/*      */       } else {
/* 4094 */         synchronized (this.parsedCallableStatementCache) {
/* 4095 */           CompoundCacheKey key = new CompoundCacheKey(getCatalog(), sql);
/*      */           
/* 4097 */           CallableStatement.CallableStatementParamInfo cachedParamInfo = (CallableStatement.CallableStatementParamInfo)this.parsedCallableStatementCache.get(key);
/*      */ 
/*      */           
/* 4100 */           if (cachedParamInfo != null) {
/* 4101 */             cStmt = CallableStatement.getInstance(getLoadBalanceSafeProxy(), cachedParamInfo);
/*      */           } else {
/* 4103 */             cStmt = parseCallableStatement(sql);
/*      */             
/* 4105 */             cachedParamInfo = cStmt.paramInfo;
/*      */             
/* 4107 */             this.parsedCallableStatementCache.put(key, cachedParamInfo);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 4112 */       cStmt.setResultSetType(resultSetType);
/* 4113 */       cStmt.setResultSetConcurrency(resultSetConcurrency);
/*      */       
/* 4115 */       return cStmt;
/*      */     } 
/*      */     
/* 4118 */     throw SQLError.createSQLException("Callable statements not supported.", "S1C00", getExceptionInterceptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 4128 */     if (getPedantic() && 
/* 4129 */       resultSetHoldability != 1) {
/* 4130 */       throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4136 */     return (CallableStatement)prepareCall(sql, resultSetType, resultSetConcurrency);
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
/* 4169 */   public PreparedStatement prepareStatement(String sql) throws SQLException { return prepareStatement(sql, 1003, 1007); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/* 4178 */     PreparedStatement pStmt = prepareStatement(sql);
/*      */     
/* 4180 */     ((PreparedStatement)pStmt).setRetrieveGeneratedKeys((autoGenKeyIndex == 1));
/*      */ 
/*      */     
/* 4183 */     return pStmt;
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
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 4203 */     checkClosed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4209 */     PreparedStatement pStmt = null;
/*      */     
/* 4211 */     boolean canServerPrepare = true;
/*      */     
/* 4213 */     String nativeSql = getProcessEscapeCodesForPrepStmts() ? nativeSQL(sql) : sql;
/*      */     
/* 4215 */     if (this.useServerPreparedStmts && getEmulateUnsupportedPstmts()) {
/* 4216 */       canServerPrepare = canHandleAsServerPreparedStatement(nativeSql);
/*      */     }
/*      */     
/* 4219 */     if (this.useServerPreparedStmts && canServerPrepare) {
/* 4220 */       if (getCachePreparedStatements()) {
/* 4221 */         synchronized (this.serverSideStatementCache) {
/* 4222 */           pStmt = (ServerPreparedStatement)this.serverSideStatementCache.remove(sql);
/*      */           
/* 4224 */           if (pStmt != null) {
/* 4225 */             ((ServerPreparedStatement)pStmt).setClosed(false);
/* 4226 */             pStmt.clearParameters();
/*      */           } 
/*      */           
/* 4229 */           if (pStmt == null) {
/*      */             try {
/* 4231 */               pStmt = ServerPreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
/*      */               
/* 4233 */               if (sql.length() < getPreparedStatementCacheSqlLimit()) {
/* 4234 */                 ((ServerPreparedStatement)pStmt).isCached = true;
/*      */               }
/*      */               
/* 4237 */               pStmt.setResultSetType(resultSetType);
/* 4238 */               pStmt.setResultSetConcurrency(resultSetConcurrency);
/* 4239 */             } catch (SQLException sqlEx) {
/*      */               
/* 4241 */               if (getEmulateUnsupportedPstmts()) {
/* 4242 */                 pStmt = (PreparedStatement)clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
/*      */                 
/* 4244 */                 if (sql.length() < getPreparedStatementCacheSqlLimit()) {
/* 4245 */                   this.serverSideStatementCheckCache.put(sql, Boolean.FALSE);
/*      */                 }
/*      */               } else {
/* 4248 */                 throw sqlEx;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         try {
/* 4255 */           pStmt = ServerPreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
/*      */ 
/*      */           
/* 4258 */           pStmt.setResultSetType(resultSetType);
/* 4259 */           pStmt.setResultSetConcurrency(resultSetConcurrency);
/* 4260 */         } catch (SQLException sqlEx) {
/*      */           
/* 4262 */           if (getEmulateUnsupportedPstmts()) {
/* 4263 */             pStmt = (PreparedStatement)clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
/*      */           } else {
/* 4265 */             throw sqlEx;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 4270 */       pStmt = (PreparedStatement)clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
/*      */     } 
/*      */     
/* 4273 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 4282 */     if (getPedantic() && 
/* 4283 */       resultSetHoldability != 1) {
/* 4284 */       throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4290 */     return prepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/* 4298 */     PreparedStatement pStmt = prepareStatement(sql);
/*      */     
/* 4300 */     ((PreparedStatement)pStmt).setRetrieveGeneratedKeys((autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 4304 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/* 4312 */     PreparedStatement pStmt = prepareStatement(sql);
/*      */     
/* 4314 */     ((PreparedStatement)pStmt).setRetrieveGeneratedKeys((autoGenKeyColNames != null && autoGenKeyColNames.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 4318 */     return pStmt;
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
/*      */   public void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException {
/* 4333 */     SQLException sqlEx = null;
/*      */     
/* 4335 */     if (isClosed()) {
/*      */       return;
/*      */     }
/*      */     
/* 4339 */     this.forceClosedReason = reason;
/*      */     
/*      */     try {
/* 4342 */       if (!skipLocalTeardown) {
/* 4343 */         if (!getAutoCommit() && issueRollback) {
/*      */           try {
/* 4345 */             rollback();
/* 4346 */           } catch (SQLException ex) {
/* 4347 */             sqlEx = ex;
/*      */           } 
/*      */         }
/*      */         
/* 4351 */         reportMetrics();
/*      */         
/* 4353 */         if (getUseUsageAdvisor()) {
/* 4354 */           if (!calledExplicitly) {
/* 4355 */             String message = "Connection implicitly closed by Driver. You should call Connection.close() from your code to free resources more efficiently and avoid resource leaks.";
/*      */             
/* 4357 */             this.eventSink.consumeEvent(new ProfilerEvent(false, "", getCatalog(), getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4365 */           long connectionLifeTime = System.currentTimeMillis() - this.connectionCreationTimeMillis;
/*      */ 
/*      */           
/* 4368 */           if (connectionLifeTime < 500L) {
/* 4369 */             String message = "Connection lifetime of < .5 seconds. You might be un-necessarily creating short-lived connections and should investigate connection pooling to be more efficient.";
/*      */             
/* 4371 */             this.eventSink.consumeEvent(new ProfilerEvent(false, "", getCatalog(), getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 4381 */           closeAllOpenStatements();
/* 4382 */         } catch (SQLException ex) {
/* 4383 */           sqlEx = ex;
/*      */         } 
/*      */         
/* 4386 */         if (this.io != null) {
/*      */           try {
/* 4388 */             this.io.quit();
/* 4389 */           } catch (Exception e) {}
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 4395 */         this.io.forceClose();
/*      */       } 
/*      */       
/* 4398 */       if (this.statementInterceptors != null) {
/* 4399 */         for (int i = 0; i < this.statementInterceptors.size(); i++) {
/* 4400 */           ((StatementInterceptorV2)this.statementInterceptors.get(i)).destroy();
/*      */         }
/*      */       }
/*      */       
/* 4404 */       if (this.exceptionInterceptor != null) {
/* 4405 */         this.exceptionInterceptor.destroy();
/*      */       }
/*      */     } finally {
/* 4408 */       this.openStatements = null;
/* 4409 */       this.io = null;
/* 4410 */       this.statementInterceptors = null;
/* 4411 */       this.exceptionInterceptor = null;
/* 4412 */       ProfilerEventHandlerFactory.removeInstance(this);
/*      */       
/* 4414 */       synchronized (this) {
/* 4415 */         if (this.cancelTimer != null) {
/* 4416 */           this.cancelTimer.cancel();
/*      */         }
/*      */       } 
/*      */       
/* 4420 */       this.isClosed = true;
/*      */     } 
/*      */     
/* 4423 */     if (sqlEx != null) {
/* 4424 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void recachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
/* 4430 */     if (pstmt.isPoolable()) {
/* 4431 */       synchronized (this.serverSideStatementCache) {
/* 4432 */         this.serverSideStatementCache.put(pstmt.originalSql, pstmt);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerQueryExecutionTime(long queryTimeMs) {
/* 4443 */     if (queryTimeMs > this.longestQueryTimeMs) {
/* 4444 */       this.longestQueryTimeMs = queryTimeMs;
/*      */       
/* 4446 */       repartitionPerformanceHistogram();
/*      */     } 
/*      */     
/* 4449 */     addToPerformanceHistogram(queryTimeMs, 1);
/*      */     
/* 4451 */     if (queryTimeMs < this.shortestQueryTimeMs) {
/* 4452 */       this.shortestQueryTimeMs = (queryTimeMs == 0L) ? 1L : queryTimeMs;
/*      */     }
/*      */     
/* 4455 */     this.numberOfQueriesIssued++;
/*      */     
/* 4457 */     this.totalQueryTimeMs += queryTimeMs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerStatement(Statement stmt) {
/* 4467 */     synchronized (this.openStatements) {
/* 4468 */       this.openStatements.put(stmt, stmt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void releaseSavepoint(Savepoint arg0) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repartitionHistogram(int[] histCounts, long[] histBreakpoints, long currentLowerBound, long currentUpperBound) {
/* 4482 */     if (this.oldHistCounts == null) {
/* 4483 */       this.oldHistCounts = new int[histCounts.length];
/* 4484 */       this.oldHistBreakpoints = new long[histBreakpoints.length];
/*      */     } 
/*      */     
/* 4487 */     System.arraycopy(histCounts, 0, this.oldHistCounts, 0, histCounts.length);
/*      */     
/* 4489 */     System.arraycopy(histBreakpoints, 0, this.oldHistBreakpoints, 0, histBreakpoints.length);
/*      */ 
/*      */     
/* 4492 */     createInitialHistogram(histBreakpoints, currentLowerBound, currentUpperBound);
/*      */ 
/*      */     
/* 4495 */     for (int i = 0; i < 20; i++) {
/* 4496 */       addToHistogram(histCounts, histBreakpoints, this.oldHistBreakpoints[i], this.oldHistCounts[i], currentLowerBound, currentUpperBound);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void repartitionPerformanceHistogram() {
/* 4502 */     checkAndCreatePerformanceHistogram();
/*      */     
/* 4504 */     repartitionHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, (this.shortestQueryTimeMs == Float.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repartitionTablesAccessedHistogram() {
/* 4511 */     checkAndCreateTablesAccessedHistogram();
/*      */     
/* 4513 */     repartitionHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, (this.minimumNumberTablesAccessed == Float.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportMetrics() {
/* 4521 */     if (getGatherPerformanceMetrics()) {
/* 4522 */       StringBuffer logMessage = new StringBuffer('Ā');
/*      */       
/* 4524 */       logMessage.append("** Performance Metrics Report **\n");
/* 4525 */       logMessage.append("\nLongest reported query: " + this.longestQueryTimeMs + " ms");
/*      */       
/* 4527 */       logMessage.append("\nShortest reported query: " + this.shortestQueryTimeMs + " ms");
/*      */       
/* 4529 */       logMessage.append("\nAverage query execution time: " + (this.totalQueryTimeMs / this.numberOfQueriesIssued) + " ms");
/*      */ 
/*      */ 
/*      */       
/* 4533 */       logMessage.append("\nNumber of statements executed: " + this.numberOfQueriesIssued);
/*      */       
/* 4535 */       logMessage.append("\nNumber of result sets created: " + this.numberOfResultSetsCreated);
/*      */       
/* 4537 */       logMessage.append("\nNumber of statements prepared: " + this.numberOfPrepares);
/*      */       
/* 4539 */       logMessage.append("\nNumber of prepared statement executions: " + this.numberOfPreparedExecutes);
/*      */ 
/*      */       
/* 4542 */       if (this.perfMetricsHistBreakpoints != null) {
/* 4543 */         logMessage.append("\n\n\tTiming Histogram:\n");
/* 4544 */         int maxNumPoints = 20;
/* 4545 */         int highestCount = Integer.MIN_VALUE;
/*      */         
/* 4547 */         for (i = 0; i < 20; i++) {
/* 4548 */           if (this.perfMetricsHistCounts[i] > highestCount) {
/* 4549 */             highestCount = this.perfMetricsHistCounts[i];
/*      */           }
/*      */         } 
/*      */         
/* 4553 */         if (highestCount == 0) {
/* 4554 */           highestCount = 1;
/*      */         }
/*      */         
/* 4557 */         for (int i = 0; i < 19; i++) {
/*      */           
/* 4559 */           if (i == 0) {
/* 4560 */             logMessage.append("\n\tless than " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
/*      */           }
/*      */           else {
/*      */             
/* 4564 */             logMessage.append("\n\tbetween " + this.perfMetricsHistBreakpoints[i] + " and " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4570 */           logMessage.append("\t");
/*      */           
/* 4572 */           int numPointsToGraph = (int)(maxNumPoints * this.perfMetricsHistCounts[i] / highestCount);
/*      */           
/* 4574 */           for (int j = 0; j < numPointsToGraph; j++) {
/* 4575 */             logMessage.append("*");
/*      */           }
/*      */           
/* 4578 */           if (this.longestQueryTimeMs < this.perfMetricsHistCounts[i + 1]) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 4583 */         if (this.perfMetricsHistBreakpoints[18] < this.longestQueryTimeMs) {
/* 4584 */           logMessage.append("\n\tbetween ");
/* 4585 */           logMessage.append(this.perfMetricsHistBreakpoints[18]);
/*      */           
/* 4587 */           logMessage.append(" and ");
/* 4588 */           logMessage.append(this.perfMetricsHistBreakpoints[19]);
/*      */           
/* 4590 */           logMessage.append(" ms: \t");
/* 4591 */           logMessage.append(this.perfMetricsHistCounts[19]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 4596 */       if (this.numTablesMetricsHistBreakpoints != null) {
/* 4597 */         logMessage.append("\n\n\tTable Join Histogram:\n");
/* 4598 */         int maxNumPoints = 20;
/* 4599 */         int highestCount = Integer.MIN_VALUE;
/*      */         
/* 4601 */         for (i = 0; i < 20; i++) {
/* 4602 */           if (this.numTablesMetricsHistCounts[i] > highestCount) {
/* 4603 */             highestCount = this.numTablesMetricsHistCounts[i];
/*      */           }
/*      */         } 
/*      */         
/* 4607 */         if (highestCount == 0) {
/* 4608 */           highestCount = 1;
/*      */         }
/*      */         
/* 4611 */         for (int i = 0; i < 19; i++) {
/*      */           
/* 4613 */           if (i == 0) {
/* 4614 */             logMessage.append("\n\t" + this.numTablesMetricsHistBreakpoints[i + 1] + " tables or less: \t\t" + this.numTablesMetricsHistCounts[i]);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 4619 */             logMessage.append("\n\tbetween " + this.numTablesMetricsHistBreakpoints[i] + " and " + this.numTablesMetricsHistBreakpoints[i + 1] + " tables: \t" + this.numTablesMetricsHistCounts[i]);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4627 */           logMessage.append("\t");
/*      */           
/* 4629 */           int numPointsToGraph = (int)(maxNumPoints * this.numTablesMetricsHistCounts[i] / highestCount);
/*      */           
/* 4631 */           for (int j = 0; j < numPointsToGraph; j++) {
/* 4632 */             logMessage.append("*");
/*      */           }
/*      */           
/* 4635 */           if (this.maximumNumberTablesAccessed < this.numTablesMetricsHistBreakpoints[i + 1]) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 4640 */         if (this.numTablesMetricsHistBreakpoints[18] < this.maximumNumberTablesAccessed) {
/* 4641 */           logMessage.append("\n\tbetween ");
/* 4642 */           logMessage.append(this.numTablesMetricsHistBreakpoints[18]);
/*      */           
/* 4644 */           logMessage.append(" and ");
/* 4645 */           logMessage.append(this.numTablesMetricsHistBreakpoints[19]);
/*      */           
/* 4647 */           logMessage.append(" tables: ");
/* 4648 */           logMessage.append(this.numTablesMetricsHistCounts[19]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 4653 */       this.log.logInfo(logMessage);
/*      */       
/* 4655 */       this.metricsLastReportedMs = System.currentTimeMillis();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportMetricsIfNeeded() {
/* 4664 */     if (getGatherPerformanceMetrics() && 
/* 4665 */       System.currentTimeMillis() - this.metricsLastReportedMs > getReportMetricsIntervalMillis()) {
/* 4666 */       reportMetrics();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void reportNumberOfTablesAccessed(int numTablesAccessed) {
/* 4672 */     if (numTablesAccessed < this.minimumNumberTablesAccessed) {
/* 4673 */       this.minimumNumberTablesAccessed = numTablesAccessed;
/*      */     }
/*      */     
/* 4676 */     if (numTablesAccessed > this.maximumNumberTablesAccessed) {
/* 4677 */       this.maximumNumberTablesAccessed = numTablesAccessed;
/*      */       
/* 4679 */       repartitionTablesAccessedHistogram();
/*      */     } 
/*      */     
/* 4682 */     addToTablesAccessedHistogram(numTablesAccessed, 1);
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
/*      */   public void resetServerState() {
/* 4694 */     if (!getParanoid() && this.io != null && versionMeetsMinimum(4, 0, 6))
/*      */     {
/* 4696 */       changeUser(this.user, this.password);
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
/*      */   public void rollback() {
/* 4710 */     synchronized (getMutex()) {
/* 4711 */       checkClosed();
/*      */       
/*      */       try {
/* 4714 */         if (this.connectionLifecycleInterceptors != null) {
/* 4715 */           IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */             {
/*      */               void forEach(Object each) throws SQLException {
/* 4718 */                 if (!((ConnectionLifecycleInterceptor)each).rollback()) {
/* 4719 */                   this.stopIterating = true;
/*      */                 }
/*      */               }
/*      */             };
/*      */           
/* 4724 */           iter.doForAll();
/*      */           
/* 4726 */           if (!iter.fullIteration()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */         
/* 4731 */         if (this.autoCommit && !getRelaxAutoCommit()) {
/* 4732 */           throw SQLError.createSQLException("Can't call rollback when autocommit=true", "08003", getExceptionInterceptor());
/*      */         }
/*      */         
/* 4735 */         if (this.transactionsSupported) {
/*      */           try {
/* 4737 */             rollbackNoChecks();
/* 4738 */           } catch (SQLException sqlEx) {
/*      */             
/* 4740 */             if (getIgnoreNonTxTables() && sqlEx.getErrorCode() == 1196) {
/*      */               return;
/*      */             }
/*      */             
/* 4744 */             throw sqlEx;
/*      */           }
/*      */         
/*      */         }
/* 4748 */       } catch (SQLException sqlException) {
/* 4749 */         if ("08S01".equals(sqlException.getSQLState()))
/*      */         {
/* 4751 */           throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007", getExceptionInterceptor());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4756 */         throw sqlException;
/*      */       } finally {
/* 4758 */         this.needsPing = getReconnectAtTxEnd();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback(final Savepoint savepoint) throws SQLException {
/* 4768 */     if (versionMeetsMinimum(4, 0, 14) || versionMeetsMinimum(4, 1, 1)) {
/* 4769 */       synchronized (getMutex()) {
/* 4770 */         checkClosed();
/*      */         
/*      */         try {
/* 4773 */           if (this.connectionLifecycleInterceptors != null) {
/* 4774 */             IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */               {
/*      */                 void forEach(Object each) throws SQLException {
/* 4777 */                   if (!((ConnectionLifecycleInterceptor)each).rollback(savepoint)) {
/* 4778 */                     this.stopIterating = true;
/*      */                   }
/*      */                 }
/*      */               };
/*      */             
/* 4783 */             iter.doForAll();
/*      */             
/* 4785 */             if (!iter.fullIteration()) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */           
/* 4790 */           StringBuffer rollbackQuery = new StringBuffer("ROLLBACK TO SAVEPOINT ");
/*      */           
/* 4792 */           rollbackQuery.append('`');
/* 4793 */           rollbackQuery.append(savepoint.getSavepointName());
/* 4794 */           rollbackQuery.append('`');
/*      */           
/* 4796 */           Statement stmt = null;
/*      */           
/*      */           try {
/* 4799 */             stmt = getMetadataSafeStatement();
/*      */             
/* 4801 */             stmt.executeUpdate(rollbackQuery.toString());
/* 4802 */           } catch (SQLException sqlEx) {
/* 4803 */             int errno = sqlEx.getErrorCode();
/*      */             
/* 4805 */             if (errno == 1181) {
/* 4806 */               String msg = sqlEx.getMessage();
/*      */               
/* 4808 */               if (msg != null) {
/* 4809 */                 int indexOfError153 = msg.indexOf("153");
/*      */                 
/* 4811 */                 if (indexOfError153 != -1) {
/* 4812 */                   throw SQLError.createSQLException("Savepoint '" + savepoint.getSavepointName() + "' does not exist", "S1009", errno, getExceptionInterceptor());
/*      */                 }
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4822 */             if (getIgnoreNonTxTables() && sqlEx.getErrorCode() != 1196)
/*      */             {
/* 4824 */               throw sqlEx;
/*      */             }
/*      */             
/* 4827 */             if ("08S01".equals(sqlEx.getSQLState()))
/*      */             {
/* 4829 */               throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007", getExceptionInterceptor());
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 4834 */             throw sqlEx;
/*      */           } finally {
/* 4836 */             closeStatement(stmt);
/*      */           } 
/*      */         } finally {
/* 4839 */           this.needsPing = getReconnectAtTxEnd();
/*      */         } 
/*      */       } 
/*      */     } else {
/* 4843 */       throw SQLError.notImplemented();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void rollbackNoChecks() {
/* 4848 */     if (getUseLocalTransactionState() && versionMeetsMinimum(5, 0, 0) && 
/* 4849 */       !this.io.inTransactionOnServer()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4854 */     execSQL(null, "rollback", -1, null, 1003, 1007, false, this.database, null, false);
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
/*      */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
/* 4866 */     String nativeSql = getProcessEscapeCodesForPrepStmts() ? nativeSQL(sql) : sql;
/*      */     
/* 4868 */     return ServerPreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, getCatalog(), 1003, 1007);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/* 4878 */     String nativeSql = getProcessEscapeCodesForPrepStmts() ? nativeSQL(sql) : sql;
/*      */     
/* 4880 */     PreparedStatement pStmt = ServerPreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, getCatalog(), 1003, 1007);
/*      */ 
/*      */ 
/*      */     
/* 4884 */     pStmt.setRetrieveGeneratedKeys((autoGenKeyIndex == 1));
/*      */ 
/*      */     
/* 4887 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/* 4895 */     String nativeSql = getProcessEscapeCodesForPrepStmts() ? nativeSQL(sql) : sql;
/*      */     
/* 4897 */     return ServerPreparedStatement.getInstance(getLoadBalanceSafeProxy(), nativeSql, getCatalog(), resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/* 4908 */     if (getPedantic() && 
/* 4909 */       resultSetHoldability != 1) {
/* 4910 */       throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", getExceptionInterceptor());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4916 */     return serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/* 4925 */     PreparedStatement pStmt = (PreparedStatement)serverPrepareStatement(sql);
/*      */     
/* 4927 */     pStmt.setRetrieveGeneratedKeys((autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 4931 */     return pStmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/* 4939 */     PreparedStatement pStmt = (PreparedStatement)serverPrepareStatement(sql);
/*      */     
/* 4941 */     pStmt.setRetrieveGeneratedKeys((autoGenKeyColNames != null && autoGenKeyColNames.length > 0));
/*      */ 
/*      */ 
/*      */     
/* 4945 */     return pStmt;
/*      */   }
/*      */ 
/*      */   
/* 4949 */   public boolean serverSupportsConvertFn() { return versionMeetsMinimum(4, 0, 2); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
/* 4975 */     synchronized (getMutex()) {
/* 4976 */       checkClosed();
/*      */       
/* 4978 */       if (this.connectionLifecycleInterceptors != null) {
/* 4979 */         IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */           {
/*      */             void forEach(Object each) throws SQLException {
/* 4982 */               if (!((ConnectionLifecycleInterceptor)each).setAutoCommit(autoCommitFlag)) {
/* 4983 */                 this.stopIterating = true;
/*      */               }
/*      */             }
/*      */           };
/*      */         
/* 4988 */         iter.doForAll();
/*      */         
/* 4990 */         if (!iter.fullIteration()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/* 4995 */       if (getAutoReconnectForPools()) {
/* 4996 */         setHighAvailability(true);
/*      */       }
/*      */       
/*      */       try {
/* 5000 */         if (this.transactionsSupported) {
/*      */           
/* 5002 */           boolean needsSetOnServer = true;
/*      */           
/* 5004 */           if (getUseLocalSessionState() && this.autoCommit == autoCommitFlag) {
/*      */             
/* 5006 */             needsSetOnServer = false;
/* 5007 */           } else if (!getHighAvailability()) {
/* 5008 */             needsSetOnServer = getIO().isSetNeededForAutoCommitMode(autoCommitFlag);
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
/* 5019 */           this.autoCommit = autoCommitFlag;
/*      */           
/* 5021 */           if (needsSetOnServer) {
/* 5022 */             execSQL(null, autoCommitFlag ? "SET autocommit=1" : "SET autocommit=0", -1, null, 1003, 1007, false, this.database, null, false);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 5030 */           if (!autoCommitFlag && !getRelaxAutoCommit()) {
/* 5031 */             throw SQLError.createSQLException("MySQL Versions Older than 3.23.15 do not support transactions", "08003", getExceptionInterceptor());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 5036 */           this.autoCommit = autoCommitFlag;
/*      */         } 
/*      */       } finally {
/* 5039 */         if (getAutoReconnectForPools()) {
/* 5040 */           setHighAvailability(false);
/*      */         }
/*      */       } 
/*      */       return;
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
/*      */   public void setCatalog(final String catalog) {
/* 5068 */     synchronized (getMutex()) {
/* 5069 */       checkClosed();
/*      */       
/* 5071 */       if (catalog == null) {
/* 5072 */         throw SQLError.createSQLException("Catalog can not be null", "S1009", getExceptionInterceptor());
/*      */       }
/*      */ 
/*      */       
/* 5076 */       if (this.connectionLifecycleInterceptors != null) {
/* 5077 */         IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */           {
/*      */             void forEach(Object each) throws SQLException {
/* 5080 */               if (!((ConnectionLifecycleInterceptor)each).setCatalog(catalog)) {
/* 5081 */                 this.stopIterating = true;
/*      */               }
/*      */             }
/*      */           };
/*      */         
/* 5086 */         iter.doForAll();
/*      */         
/* 5088 */         if (!iter.fullIteration()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/* 5093 */       if (getUseLocalSessionState()) {
/* 5094 */         if (this.lowerCaseTableNames) {
/* 5095 */           if (this.database.equalsIgnoreCase(catalog)) {
/*      */             return;
/*      */           }
/*      */         }
/* 5099 */         else if (this.database.equals(catalog)) {
/*      */           return;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 5105 */       String quotedId = this.dbmd.getIdentifierQuoteString();
/*      */       
/* 5107 */       if (quotedId == null || quotedId.equals(" ")) {
/* 5108 */         quotedId = "";
/*      */       }
/*      */       
/* 5111 */       StringBuffer query = new StringBuffer("USE ");
/* 5112 */       query.append(quotedId);
/* 5113 */       query.append(catalog);
/* 5114 */       query.append(quotedId);
/*      */       
/* 5116 */       execSQL(null, query.toString(), -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5121 */       this.database = catalog;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFailedOver(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHoldability(int arg0) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5141 */   public void setInGlobalTx(boolean flag) throws SQLException { this.isInGlobalTx = flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreferSlaveDuringFailover(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5154 */   public void setReadInfoMsgEnabled(boolean flag) throws SQLException { this.readInfoMsg = flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnlyFlag) throws SQLException {
/* 5168 */     checkClosed();
/*      */     
/* 5170 */     setReadOnlyInternal(readOnlyFlag);
/*      */   }
/*      */ 
/*      */   
/* 5174 */   public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException { this.readOnly = readOnlyFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint() throws SQLException {
/* 5181 */     MysqlSavepoint savepoint = new MysqlSavepoint(getExceptionInterceptor());
/*      */     
/* 5183 */     setSavepoint(savepoint);
/*      */     
/* 5185 */     return savepoint;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSavepoint(MysqlSavepoint savepoint) throws SQLException {
/* 5190 */     if (versionMeetsMinimum(4, 0, 14) || versionMeetsMinimum(4, 1, 1)) {
/* 5191 */       synchronized (getMutex()) {
/* 5192 */         checkClosed();
/*      */         
/* 5194 */         StringBuffer savePointQuery = new StringBuffer("SAVEPOINT ");
/* 5195 */         savePointQuery.append('`');
/* 5196 */         savePointQuery.append(savepoint.getSavepointName());
/* 5197 */         savePointQuery.append('`');
/*      */         
/* 5199 */         Statement stmt = null;
/*      */         
/*      */         try {
/* 5202 */           stmt = getMetadataSafeStatement();
/*      */           
/* 5204 */           stmt.executeUpdate(savePointQuery.toString());
/*      */         } finally {
/* 5206 */           closeStatement(stmt);
/*      */         } 
/*      */       } 
/*      */     } else {
/* 5210 */       throw SQLError.notImplemented();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint(String name) throws SQLException {
/* 5218 */     MysqlSavepoint savepoint = new MysqlSavepoint(name, getExceptionInterceptor());
/*      */     
/* 5220 */     setSavepoint(savepoint);
/*      */     
/* 5222 */     return savepoint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSessionVariables() {
/* 5229 */     if (versionMeetsMinimum(4, 0, 0) && getSessionVariables() != null) {
/* 5230 */       List variablesToSet = StringUtils.split(getSessionVariables(), ",", "\"'", "\"'", false);
/*      */ 
/*      */       
/* 5233 */       int numVariablesToSet = variablesToSet.size();
/*      */       
/* 5235 */       Statement stmt = null;
/*      */       
/*      */       try {
/* 5238 */         stmt = getMetadataSafeStatement();
/*      */         
/* 5240 */         for (int i = 0; i < numVariablesToSet; i++) {
/* 5241 */           String variableValuePair = (String)variablesToSet.get(i);
/*      */           
/* 5243 */           if (variableValuePair.startsWith("@")) {
/* 5244 */             stmt.executeUpdate("SET " + variableValuePair);
/*      */           } else {
/* 5246 */             stmt.executeUpdate("SET SESSION " + variableValuePair);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 5250 */         if (stmt != null) {
/* 5251 */           stmt.close();
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
/*      */   public void setTransactionIsolation(int level) {
/* 5267 */     checkClosed();
/*      */     
/* 5269 */     if (this.hasIsolationLevels) {
/* 5270 */       String sql = null;
/*      */       
/* 5272 */       boolean shouldSendSet = false;
/*      */       
/* 5274 */       if (getAlwaysSendSetIsolation()) {
/* 5275 */         shouldSendSet = true;
/*      */       }
/* 5277 */       else if (level != this.isolationLevel) {
/* 5278 */         shouldSendSet = true;
/*      */       } 
/*      */ 
/*      */       
/* 5282 */       if (getUseLocalSessionState()) {
/* 5283 */         shouldSendSet = (this.isolationLevel != level);
/*      */       }
/*      */       
/* 5286 */       if (shouldSendSet) {
/* 5287 */         switch (level) {
/*      */           case 0:
/* 5289 */             throw SQLError.createSQLException("Transaction isolation level NONE not supported by MySQL", getExceptionInterceptor());
/*      */ 
/*      */           
/*      */           case 2:
/* 5293 */             sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED";
/*      */             break;
/*      */ 
/*      */           
/*      */           case 1:
/* 5298 */             sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/* 5303 */             sql = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ";
/*      */             break;
/*      */ 
/*      */           
/*      */           case 8:
/* 5308 */             sql = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE";
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 5313 */             throw SQLError.createSQLException("Unsupported transaction isolation level '" + level + "'", "S1C00", getExceptionInterceptor());
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 5318 */         execSQL(null, sql, -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5323 */         this.isolationLevel = level;
/*      */       } 
/*      */     } else {
/* 5326 */       throw SQLError.createSQLException("Transaction Isolation Levels are not supported on MySQL versions older than 3.23.36.", "S1C00", getExceptionInterceptor());
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
/* 5342 */   public void setTypeMap(Map map) throws SQLException { this.typeMap = map; }
/*      */ 
/*      */   
/*      */   private void setupServerForTruncationChecks() {
/* 5346 */     if (getJdbcCompliantTruncation() && 
/* 5347 */       versionMeetsMinimum(5, 0, 2)) {
/* 5348 */       String currentSqlMode = (String)this.serverVariables.get("sql_mode");
/*      */ 
/*      */       
/* 5351 */       boolean strictTransTablesIsSet = (StringUtils.indexOfIgnoreCase(currentSqlMode, "STRICT_TRANS_TABLES") != -1);
/*      */       
/* 5353 */       if (currentSqlMode == null || currentSqlMode.length() == 0 || !strictTransTablesIsSet) {
/*      */         
/* 5355 */         StringBuffer commandBuf = new StringBuffer("SET sql_mode='");
/*      */         
/* 5357 */         if (currentSqlMode != null && currentSqlMode.length() > 0) {
/* 5358 */           commandBuf.append(currentSqlMode);
/* 5359 */           commandBuf.append(",");
/*      */         } 
/*      */         
/* 5362 */         commandBuf.append("STRICT_TRANS_TABLES'");
/*      */         
/* 5364 */         execSQL(null, commandBuf.toString(), -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5369 */         setJdbcCompliantTruncation(false);
/* 5370 */       } else if (strictTransTablesIsSet) {
/*      */         
/* 5372 */         setJdbcCompliantTruncation(false);
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
/*      */   public void shutdownServer() {
/*      */     try {
/* 5388 */       this.io.sendCommand(8, null, null, false, null, 0);
/* 5389 */     } catch (Exception ex) {
/* 5390 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnhandledExceptionDuringShutdown"), "S1000", getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */       
/* 5394 */       sqlEx.initCause(ex);
/*      */       
/* 5396 */       throw sqlEx;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5406 */   public boolean supportsIsolationLevel() { return this.hasIsolationLevels; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5415 */   public boolean supportsQuotedIdentifiers() { return this.hasQuotedIdentifiers; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5424 */   public boolean supportsTransactions() { return this.transactionsSupported; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unregisterStatement(Statement stmt) {
/* 5434 */     if (this.openStatements != null) {
/* 5435 */       synchronized (this.openStatements) {
/* 5436 */         this.openStatements.remove(stmt);
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
/*      */   public void unsetMaxRows(Statement stmt) {
/* 5452 */     synchronized (this.mutex) {
/* 5453 */       if (this.statementsUsingMaxRows != null) {
/* 5454 */         Object found = this.statementsUsingMaxRows.remove(stmt);
/*      */         
/* 5456 */         if (found != null && this.statementsUsingMaxRows.size() == 0) {
/*      */           
/* 5458 */           execSQL(null, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.database, null, false);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 5463 */           this.maxRowsChanged = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 5470 */   public boolean useAnsiQuotedIdentifiers() { return this.useAnsiQuotes; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean useMaxRows() {
/* 5479 */     synchronized (this.mutex) {
/* 5480 */       return this.maxRowsChanged;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
/* 5486 */     checkClosed();
/*      */     
/* 5488 */     return this.io.versionMeetsMinimum(major, minor, subminor);
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
/*      */   public CachedResultSetMetaData getCachedMetaData(String sql) {
/* 5506 */     if (this.resultSetMetadataCache != null) {
/* 5507 */       synchronized (this.resultSetMetadataCache) {
/* 5508 */         return (CachedResultSetMetaData)this.resultSetMetadataCache.get(sql);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 5513 */     return null;
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
/*      */   public void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSetInternalMethods resultSet) throws SQLException {
/* 5534 */     if (cachedMetaData == null) {
/*      */ 
/*      */       
/* 5537 */       cachedMetaData = new CachedResultSetMetaData();
/*      */ 
/*      */ 
/*      */       
/* 5541 */       resultSet.buildIndexMapping();
/* 5542 */       resultSet.initializeWithMetadata();
/*      */       
/* 5544 */       if (resultSet instanceof UpdatableResultSet) {
/* 5545 */         ((UpdatableResultSet)resultSet).checkUpdatability();
/*      */       }
/*      */       
/* 5548 */       resultSet.populateCachedMetaData(cachedMetaData);
/*      */       
/* 5550 */       this.resultSetMetadataCache.put(sql, cachedMetaData);
/*      */     } else {
/* 5552 */       resultSet.initializeFromCachedMetaData(cachedMetaData);
/* 5553 */       resultSet.initializeWithMetadata();
/*      */       
/* 5555 */       if (resultSet instanceof UpdatableResultSet) {
/* 5556 */         ((UpdatableResultSet)resultSet).checkUpdatability();
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
/* 5569 */   public String getStatementComment() { return this.statementComment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5581 */   public void setStatementComment(String comment) { this.statementComment = comment; }
/*      */ 
/*      */   
/*      */   public void reportQueryTime(long millisOrNanos) {
/* 5585 */     this.queryTimeCount++;
/* 5586 */     this.queryTimeSum += millisOrNanos;
/* 5587 */     this.queryTimeSumSquares += (millisOrNanos * millisOrNanos);
/* 5588 */     this.queryTimeMean = (this.queryTimeMean * (this.queryTimeCount - 1L) + millisOrNanos) / this.queryTimeCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAbonormallyLongQuery(long millisOrNanos) {
/* 5593 */     if (this.queryTimeCount < 15L) {
/* 5594 */       return false;
/*      */     }
/*      */     
/* 5597 */     double stddev = Math.sqrt((this.queryTimeSumSquares - this.queryTimeSum * this.queryTimeSum / this.queryTimeCount) / (this.queryTimeCount - 1L));
/*      */     
/* 5599 */     return (millisOrNanos > this.queryTimeMean + 5.0D * stddev);
/*      */   }
/*      */ 
/*      */   
/* 5603 */   public void initializeExtension(Extension ex) throws SQLException { ex.init(this, this.props); }
/*      */ 
/*      */   
/*      */   public void transactionBegun() {
/* 5607 */     if (this.connectionLifecycleInterceptors != null) {
/* 5608 */       IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */         {
/*      */           void forEach(Object each) throws SQLException {
/* 5611 */             ((ConnectionLifecycleInterceptor)each).transactionBegun();
/*      */           }
/*      */         };
/*      */       
/* 5615 */       iter.doForAll();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void transactionCompleted() {
/* 5620 */     if (this.connectionLifecycleInterceptors != null) {
/* 5621 */       IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator())
/*      */         {
/*      */           void forEach(Object each) throws SQLException {
/* 5624 */             ((ConnectionLifecycleInterceptor)each).transactionCompleted();
/*      */           }
/*      */         };
/*      */       
/* 5628 */       iter.doForAll();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/* 5633 */   public boolean storesLowerCaseTableName() { return this.storesLowerCaseTableName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 5639 */   public ExceptionInterceptor getExceptionInterceptor() { return this.exceptionInterceptor; }
/*      */ 
/*      */ 
/*      */   
/* 5643 */   public boolean getRequiresEscapingEncoder() { return this.requiresEscapingEncoder; }
/*      */   
/*      */   protected ConnectionImpl() {}
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ConnectionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */