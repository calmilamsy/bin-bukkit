/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Savepoint;
/*      */ import java.sql.Statement;
/*      */ import java.util.Calendar;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LoadBalancedMySQLConnection
/*      */   implements MySQLConnection
/*      */ {
/*      */   protected LoadBalancingConnectionProxy proxy;
/*      */   
/*   46 */   public LoadBalancingConnectionProxy getProxy() { return this.proxy; }
/*      */ 
/*      */ 
/*      */   
/*   50 */   protected MySQLConnection getActiveMySQLConnection() { return this.proxy.currentConn; }
/*      */ 
/*      */ 
/*      */   
/*   54 */   public LoadBalancedMySQLConnection(LoadBalancingConnectionProxy proxy) { this.proxy = proxy; }
/*      */ 
/*      */ 
/*      */   
/*   58 */   public void abortInternal() throws SQLException { getActiveMySQLConnection().abortInternal(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   63 */   public void changeUser(String userName, String newPassword) throws SQLException { getActiveMySQLConnection().changeUser(userName, newPassword); }
/*      */ 
/*      */ 
/*      */   
/*   67 */   public void checkClosed() throws SQLException { getActiveMySQLConnection().checkClosed(); }
/*      */ 
/*      */ 
/*      */   
/*   71 */   public void clearHasTriedMaster() throws SQLException { getActiveMySQLConnection().clearHasTriedMaster(); }
/*      */ 
/*      */ 
/*      */   
/*   75 */   public void clearWarnings() throws SQLException { getActiveMySQLConnection().clearWarnings(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   87 */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndexes); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  105 */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyColNames); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException { return getActiveMySQLConnection().clientPrepareStatement(sql); }
/*      */ 
/*      */ 
/*      */   
/*  115 */   public void close() throws SQLException { getActiveMySQLConnection().close(); }
/*      */ 
/*      */ 
/*      */   
/*  119 */   public void commit() throws SQLException { getActiveMySQLConnection().commit(); }
/*      */ 
/*      */ 
/*      */   
/*  123 */   public void createNewIO(boolean isForReconnect) throws SQLException { getActiveMySQLConnection().createNewIO(isForReconnect); }
/*      */ 
/*      */ 
/*      */   
/*  127 */   public Statement createStatement() throws SQLException { return getActiveMySQLConnection().createStatement(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException { return getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   public void dumpTestcaseQuery(String query) { getActiveMySQLConnection().dumpTestcaseQuery(query); }
/*      */ 
/*      */ 
/*      */   
/*  148 */   public Connection duplicate() throws SQLException { return getActiveMySQLConnection().duplicate(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata, boolean isBatch) throws SQLException { return getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata, isBatch); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, Buffer packet, int resultSetType, int resultSetConcurrency, boolean streamResults, String catalog, Field[] cachedMetadata) throws SQLException { return getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public String extractSqlFromPacket(String possibleSqlQuery, Buffer queryPacket, int endOfQueryPacketPosition) throws SQLException { return getActiveMySQLConnection().extractSqlFromPacket(possibleSqlQuery, queryPacket, endOfQueryPacketPosition); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  177 */   public String exposeAsXml() throws SQLException { return getActiveMySQLConnection().exposeAsXml(); }
/*      */ 
/*      */ 
/*      */   
/*  181 */   public boolean getAllowLoadLocalInfile() { return getActiveMySQLConnection().getAllowLoadLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/*  185 */   public boolean getAllowMultiQueries() { return getActiveMySQLConnection().getAllowMultiQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  189 */   public boolean getAllowNanAndInf() { return getActiveMySQLConnection().getAllowNanAndInf(); }
/*      */ 
/*      */ 
/*      */   
/*  193 */   public boolean getAllowUrlInLocalInfile() { return getActiveMySQLConnection().getAllowUrlInLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/*  197 */   public boolean getAlwaysSendSetIsolation() { return getActiveMySQLConnection().getAlwaysSendSetIsolation(); }
/*      */ 
/*      */ 
/*      */   
/*  201 */   public boolean getAutoClosePStmtStreams() { return getActiveMySQLConnection().getAutoClosePStmtStreams(); }
/*      */ 
/*      */ 
/*      */   
/*  205 */   public boolean getAutoDeserialize() { return getActiveMySQLConnection().getAutoDeserialize(); }
/*      */ 
/*      */ 
/*      */   
/*  209 */   public boolean getAutoGenerateTestcaseScript() { return getActiveMySQLConnection().getAutoGenerateTestcaseScript(); }
/*      */ 
/*      */ 
/*      */   
/*  213 */   public boolean getAutoReconnectForPools() { return getActiveMySQLConnection().getAutoReconnectForPools(); }
/*      */ 
/*      */ 
/*      */   
/*  217 */   public boolean getAutoSlowLog() { return getActiveMySQLConnection().getAutoSlowLog(); }
/*      */ 
/*      */ 
/*      */   
/*  221 */   public int getBlobSendChunkSize() { return getActiveMySQLConnection().getBlobSendChunkSize(); }
/*      */ 
/*      */ 
/*      */   
/*  225 */   public boolean getBlobsAreStrings() { return getActiveMySQLConnection().getBlobsAreStrings(); }
/*      */ 
/*      */ 
/*      */   
/*  229 */   public boolean getCacheCallableStatements() { return getActiveMySQLConnection().getCacheCallableStatements(); }
/*      */ 
/*      */ 
/*      */   
/*  233 */   public boolean getCacheCallableStmts() { return getActiveMySQLConnection().getCacheCallableStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  237 */   public boolean getCachePrepStmts() { return getActiveMySQLConnection().getCachePrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  241 */   public boolean getCachePreparedStatements() { return getActiveMySQLConnection().getCachePreparedStatements(); }
/*      */ 
/*      */ 
/*      */   
/*  245 */   public boolean getCacheResultSetMetadata() { return getActiveMySQLConnection().getCacheResultSetMetadata(); }
/*      */ 
/*      */ 
/*      */   
/*  249 */   public boolean getCacheServerConfiguration() { return getActiveMySQLConnection().getCacheServerConfiguration(); }
/*      */ 
/*      */ 
/*      */   
/*  253 */   public int getCallableStatementCacheSize() { return getActiveMySQLConnection().getCallableStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  257 */   public int getCallableStmtCacheSize() { return getActiveMySQLConnection().getCallableStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  261 */   public boolean getCapitalizeTypeNames() { return getActiveMySQLConnection().getCapitalizeTypeNames(); }
/*      */ 
/*      */ 
/*      */   
/*  265 */   public String getCharacterSetResults() throws SQLException { return getActiveMySQLConnection().getCharacterSetResults(); }
/*      */ 
/*      */ 
/*      */   
/*  269 */   public String getClientCertificateKeyStorePassword() throws SQLException { return getActiveMySQLConnection().getClientCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  274 */   public String getClientCertificateKeyStoreType() throws SQLException { return getActiveMySQLConnection().getClientCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/*  278 */   public String getClientCertificateKeyStoreUrl() throws SQLException { return getActiveMySQLConnection().getClientCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/*  282 */   public String getClientInfoProvider() throws SQLException { return getActiveMySQLConnection().getClientInfoProvider(); }
/*      */ 
/*      */ 
/*      */   
/*  286 */   public String getClobCharacterEncoding() throws SQLException { return getActiveMySQLConnection().getClobCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  290 */   public boolean getClobberStreamingResults() { return getActiveMySQLConnection().getClobberStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/*  294 */   public boolean getCompensateOnDuplicateKeyUpdateCounts() { return getActiveMySQLConnection().getCompensateOnDuplicateKeyUpdateCounts(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  299 */   public int getConnectTimeout() { return getActiveMySQLConnection().getConnectTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  303 */   public String getConnectionCollation() throws SQLException { return getActiveMySQLConnection().getConnectionCollation(); }
/*      */ 
/*      */ 
/*      */   
/*  307 */   public String getConnectionLifecycleInterceptors() throws SQLException { return getActiveMySQLConnection().getConnectionLifecycleInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/*  311 */   public boolean getContinueBatchOnError() { return getActiveMySQLConnection().getContinueBatchOnError(); }
/*      */ 
/*      */ 
/*      */   
/*  315 */   public boolean getCreateDatabaseIfNotExist() { return getActiveMySQLConnection().getCreateDatabaseIfNotExist(); }
/*      */ 
/*      */ 
/*      */   
/*  319 */   public int getDefaultFetchSize() { return getActiveMySQLConnection().getDefaultFetchSize(); }
/*      */ 
/*      */ 
/*      */   
/*  323 */   public boolean getDontTrackOpenResources() { return getActiveMySQLConnection().getDontTrackOpenResources(); }
/*      */ 
/*      */ 
/*      */   
/*  327 */   public boolean getDumpMetadataOnColumnNotFound() { return getActiveMySQLConnection().getDumpMetadataOnColumnNotFound(); }
/*      */ 
/*      */ 
/*      */   
/*  331 */   public boolean getDumpQueriesOnException() { return getActiveMySQLConnection().getDumpQueriesOnException(); }
/*      */ 
/*      */ 
/*      */   
/*  335 */   public boolean getDynamicCalendars() { return getActiveMySQLConnection().getDynamicCalendars(); }
/*      */ 
/*      */ 
/*      */   
/*  339 */   public boolean getElideSetAutoCommits() { return getActiveMySQLConnection().getElideSetAutoCommits(); }
/*      */ 
/*      */ 
/*      */   
/*  343 */   public boolean getEmptyStringsConvertToZero() { return getActiveMySQLConnection().getEmptyStringsConvertToZero(); }
/*      */ 
/*      */ 
/*      */   
/*  347 */   public boolean getEmulateLocators() { return getActiveMySQLConnection().getEmulateLocators(); }
/*      */ 
/*      */ 
/*      */   
/*  351 */   public boolean getEmulateUnsupportedPstmts() { return getActiveMySQLConnection().getEmulateUnsupportedPstmts(); }
/*      */ 
/*      */ 
/*      */   
/*  355 */   public boolean getEnablePacketDebug() { return getActiveMySQLConnection().getEnablePacketDebug(); }
/*      */ 
/*      */ 
/*      */   
/*  359 */   public boolean getEnableQueryTimeouts() { return getActiveMySQLConnection().getEnableQueryTimeouts(); }
/*      */ 
/*      */ 
/*      */   
/*  363 */   public String getEncoding() throws SQLException { return getActiveMySQLConnection().getEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  367 */   public String getExceptionInterceptors() throws SQLException { return getActiveMySQLConnection().getExceptionInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/*  371 */   public boolean getExplainSlowQueries() { return getActiveMySQLConnection().getExplainSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  375 */   public boolean getFailOverReadOnly() { return getActiveMySQLConnection().getFailOverReadOnly(); }
/*      */ 
/*      */ 
/*      */   
/*  379 */   public boolean getFunctionsNeverReturnBlobs() { return getActiveMySQLConnection().getFunctionsNeverReturnBlobs(); }
/*      */ 
/*      */ 
/*      */   
/*  383 */   public boolean getGatherPerfMetrics() { return getActiveMySQLConnection().getGatherPerfMetrics(); }
/*      */ 
/*      */ 
/*      */   
/*  387 */   public boolean getGatherPerformanceMetrics() { return getActiveMySQLConnection().getGatherPerformanceMetrics(); }
/*      */ 
/*      */ 
/*      */   
/*  391 */   public boolean getGenerateSimpleParameterMetadata() { return getActiveMySQLConnection().getGenerateSimpleParameterMetadata(); }
/*      */ 
/*      */ 
/*      */   
/*  395 */   public boolean getIgnoreNonTxTables() { return getActiveMySQLConnection().getIgnoreNonTxTables(); }
/*      */ 
/*      */ 
/*      */   
/*  399 */   public boolean getIncludeInnodbStatusInDeadlockExceptions() { return getActiveMySQLConnection().getIncludeInnodbStatusInDeadlockExceptions(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  404 */   public int getInitialTimeout() { return getActiveMySQLConnection().getInitialTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  408 */   public boolean getInteractiveClient() { return getActiveMySQLConnection().getInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/*  412 */   public boolean getIsInteractiveClient() { return getActiveMySQLConnection().getIsInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/*  416 */   public boolean getJdbcCompliantTruncation() { return getActiveMySQLConnection().getJdbcCompliantTruncation(); }
/*      */ 
/*      */ 
/*      */   
/*  420 */   public boolean getJdbcCompliantTruncationForReads() { return getActiveMySQLConnection().getJdbcCompliantTruncationForReads(); }
/*      */ 
/*      */ 
/*      */   
/*  424 */   public String getLargeRowSizeThreshold() throws SQLException { return getActiveMySQLConnection().getLargeRowSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/*  428 */   public int getLoadBalanceBlacklistTimeout() { return getActiveMySQLConnection().getLoadBalanceBlacklistTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  432 */   public int getLoadBalancePingTimeout() { return getActiveMySQLConnection().getLoadBalancePingTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  436 */   public String getLoadBalanceStrategy() throws SQLException { return getActiveMySQLConnection().getLoadBalanceStrategy(); }
/*      */ 
/*      */ 
/*      */   
/*  440 */   public boolean getLoadBalanceValidateConnectionOnSwapServer() { return getActiveMySQLConnection().getLoadBalanceValidateConnectionOnSwapServer(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  445 */   public String getLocalSocketAddress() throws SQLException { return getActiveMySQLConnection().getLocalSocketAddress(); }
/*      */ 
/*      */ 
/*      */   
/*  449 */   public int getLocatorFetchBufferSize() { return getActiveMySQLConnection().getLocatorFetchBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/*  453 */   public boolean getLogSlowQueries() { return getActiveMySQLConnection().getLogSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  457 */   public boolean getLogXaCommands() { return getActiveMySQLConnection().getLogXaCommands(); }
/*      */ 
/*      */ 
/*      */   
/*  461 */   public String getLogger() throws SQLException { return getActiveMySQLConnection().getLogger(); }
/*      */ 
/*      */ 
/*      */   
/*  465 */   public String getLoggerClassName() throws SQLException { return getActiveMySQLConnection().getLoggerClassName(); }
/*      */ 
/*      */ 
/*      */   
/*  469 */   public boolean getMaintainTimeStats() { return getActiveMySQLConnection().getMaintainTimeStats(); }
/*      */ 
/*      */ 
/*      */   
/*  473 */   public int getMaxAllowedPacket() { return getActiveMySQLConnection().getMaxAllowedPacket(); }
/*      */ 
/*      */ 
/*      */   
/*  477 */   public int getMaxQuerySizeToLog() { return getActiveMySQLConnection().getMaxQuerySizeToLog(); }
/*      */ 
/*      */ 
/*      */   
/*  481 */   public int getMaxReconnects() { return getActiveMySQLConnection().getMaxReconnects(); }
/*      */ 
/*      */ 
/*      */   
/*  485 */   public int getMaxRows() { return getActiveMySQLConnection().getMaxRows(); }
/*      */ 
/*      */ 
/*      */   
/*  489 */   public int getMetadataCacheSize() { return getActiveMySQLConnection().getMetadataCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  493 */   public int getNetTimeoutForStreamingResults() { return getActiveMySQLConnection().getNetTimeoutForStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/*  497 */   public boolean getNoAccessToProcedureBodies() { return getActiveMySQLConnection().getNoAccessToProcedureBodies(); }
/*      */ 
/*      */ 
/*      */   
/*  501 */   public boolean getNoDatetimeStringSync() { return getActiveMySQLConnection().getNoDatetimeStringSync(); }
/*      */ 
/*      */ 
/*      */   
/*  505 */   public boolean getNoTimezoneConversionForTimeType() { return getActiveMySQLConnection().getNoTimezoneConversionForTimeType(); }
/*      */ 
/*      */ 
/*      */   
/*  509 */   public boolean getNullCatalogMeansCurrent() { return getActiveMySQLConnection().getNullCatalogMeansCurrent(); }
/*      */ 
/*      */ 
/*      */   
/*  513 */   public boolean getNullNamePatternMatchesAll() { return getActiveMySQLConnection().getNullNamePatternMatchesAll(); }
/*      */ 
/*      */ 
/*      */   
/*  517 */   public boolean getOverrideSupportsIntegrityEnhancementFacility() { return getActiveMySQLConnection().getOverrideSupportsIntegrityEnhancementFacility(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  522 */   public int getPacketDebugBufferSize() { return getActiveMySQLConnection().getPacketDebugBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/*  526 */   public boolean getPadCharsWithSpace() { return getActiveMySQLConnection().getPadCharsWithSpace(); }
/*      */ 
/*      */ 
/*      */   
/*  530 */   public boolean getParanoid() { return getActiveMySQLConnection().getParanoid(); }
/*      */ 
/*      */ 
/*      */   
/*  534 */   public String getPasswordCharacterEncoding() throws SQLException { return getActiveMySQLConnection().getPasswordCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  538 */   public boolean getPedantic() { return getActiveMySQLConnection().getPedantic(); }
/*      */ 
/*      */ 
/*      */   
/*  542 */   public boolean getPinGlobalTxToPhysicalConnection() { return getActiveMySQLConnection().getPinGlobalTxToPhysicalConnection(); }
/*      */ 
/*      */ 
/*      */   
/*  546 */   public boolean getPopulateInsertRowWithDefaultValues() { return getActiveMySQLConnection().getPopulateInsertRowWithDefaultValues(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  551 */   public int getPrepStmtCacheSize() { return getActiveMySQLConnection().getPrepStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  555 */   public int getPrepStmtCacheSqlLimit() { return getActiveMySQLConnection().getPrepStmtCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/*  559 */   public int getPreparedStatementCacheSize() { return getActiveMySQLConnection().getPreparedStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  563 */   public int getPreparedStatementCacheSqlLimit() { return getActiveMySQLConnection().getPreparedStatementCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/*  567 */   public boolean getProcessEscapeCodesForPrepStmts() { return getActiveMySQLConnection().getProcessEscapeCodesForPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  571 */   public boolean getProfileSQL() { return getActiveMySQLConnection().getProfileSQL(); }
/*      */ 
/*      */ 
/*      */   
/*  575 */   public boolean getProfileSql() { return getActiveMySQLConnection().getProfileSql(); }
/*      */ 
/*      */ 
/*      */   
/*  579 */   public String getProfilerEventHandler() throws SQLException { return getActiveMySQLConnection().getProfilerEventHandler(); }
/*      */ 
/*      */ 
/*      */   
/*  583 */   public String getPropertiesTransform() throws SQLException { return getActiveMySQLConnection().getPropertiesTransform(); }
/*      */ 
/*      */ 
/*      */   
/*  587 */   public int getQueriesBeforeRetryMaster() { return getActiveMySQLConnection().getQueriesBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/*  591 */   public boolean getQueryTimeoutKillsConnection() { return getActiveMySQLConnection().getQueryTimeoutKillsConnection(); }
/*      */ 
/*      */ 
/*      */   
/*  595 */   public boolean getReconnectAtTxEnd() { return getActiveMySQLConnection().getReconnectAtTxEnd(); }
/*      */ 
/*      */ 
/*      */   
/*  599 */   public boolean getRelaxAutoCommit() { return getActiveMySQLConnection().getRelaxAutoCommit(); }
/*      */ 
/*      */ 
/*      */   
/*  603 */   public int getReportMetricsIntervalMillis() { return getActiveMySQLConnection().getReportMetricsIntervalMillis(); }
/*      */ 
/*      */ 
/*      */   
/*  607 */   public boolean getRequireSSL() { return getActiveMySQLConnection().getRequireSSL(); }
/*      */ 
/*      */ 
/*      */   
/*  611 */   public String getResourceId() throws SQLException { return getActiveMySQLConnection().getResourceId(); }
/*      */ 
/*      */ 
/*      */   
/*  615 */   public int getResultSetSizeThreshold() { return getActiveMySQLConnection().getResultSetSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/*  619 */   public boolean getRetainStatementAfterResultSetClose() { return getActiveMySQLConnection().getRetainStatementAfterResultSetClose(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  624 */   public int getRetriesAllDown() { return getActiveMySQLConnection().getRetriesAllDown(); }
/*      */ 
/*      */ 
/*      */   
/*  628 */   public boolean getRewriteBatchedStatements() { return getActiveMySQLConnection().getRewriteBatchedStatements(); }
/*      */ 
/*      */ 
/*      */   
/*  632 */   public boolean getRollbackOnPooledClose() { return getActiveMySQLConnection().getRollbackOnPooledClose(); }
/*      */ 
/*      */ 
/*      */   
/*  636 */   public boolean getRoundRobinLoadBalance() { return getActiveMySQLConnection().getRoundRobinLoadBalance(); }
/*      */ 
/*      */ 
/*      */   
/*  640 */   public boolean getRunningCTS13() { return getActiveMySQLConnection().getRunningCTS13(); }
/*      */ 
/*      */ 
/*      */   
/*  644 */   public int getSecondsBeforeRetryMaster() { return getActiveMySQLConnection().getSecondsBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/*  648 */   public int getSelfDestructOnPingMaxOperations() { return getActiveMySQLConnection().getSelfDestructOnPingMaxOperations(); }
/*      */ 
/*      */ 
/*      */   
/*  652 */   public int getSelfDestructOnPingSecondsLifetime() { return getActiveMySQLConnection().getSelfDestructOnPingSecondsLifetime(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  657 */   public String getServerTimezone() throws SQLException { return getActiveMySQLConnection().getServerTimezone(); }
/*      */ 
/*      */ 
/*      */   
/*  661 */   public String getSessionVariables() throws SQLException { return getActiveMySQLConnection().getSessionVariables(); }
/*      */ 
/*      */ 
/*      */   
/*  665 */   public int getSlowQueryThresholdMillis() { return getActiveMySQLConnection().getSlowQueryThresholdMillis(); }
/*      */ 
/*      */ 
/*      */   
/*  669 */   public long getSlowQueryThresholdNanos() { return getActiveMySQLConnection().getSlowQueryThresholdNanos(); }
/*      */ 
/*      */ 
/*      */   
/*  673 */   public String getSocketFactory() throws SQLException { return getActiveMySQLConnection().getSocketFactory(); }
/*      */ 
/*      */ 
/*      */   
/*  677 */   public String getSocketFactoryClassName() throws SQLException { return getActiveMySQLConnection().getSocketFactoryClassName(); }
/*      */ 
/*      */ 
/*      */   
/*  681 */   public int getSocketTimeout() { return getActiveMySQLConnection().getSocketTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  685 */   public String getStatementInterceptors() throws SQLException { return getActiveMySQLConnection().getStatementInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/*  689 */   public boolean getStrictFloatingPoint() { return getActiveMySQLConnection().getStrictFloatingPoint(); }
/*      */ 
/*      */ 
/*      */   
/*  693 */   public boolean getStrictUpdates() { return getActiveMySQLConnection().getStrictUpdates(); }
/*      */ 
/*      */ 
/*      */   
/*  697 */   public boolean getTcpKeepAlive() { return getActiveMySQLConnection().getTcpKeepAlive(); }
/*      */ 
/*      */ 
/*      */   
/*  701 */   public boolean getTcpNoDelay() { return getActiveMySQLConnection().getTcpNoDelay(); }
/*      */ 
/*      */ 
/*      */   
/*  705 */   public int getTcpRcvBuf() { return getActiveMySQLConnection().getTcpRcvBuf(); }
/*      */ 
/*      */ 
/*      */   
/*  709 */   public int getTcpSndBuf() { return getActiveMySQLConnection().getTcpSndBuf(); }
/*      */ 
/*      */ 
/*      */   
/*  713 */   public int getTcpTrafficClass() { return getActiveMySQLConnection().getTcpTrafficClass(); }
/*      */ 
/*      */ 
/*      */   
/*  717 */   public boolean getTinyInt1isBit() { return getActiveMySQLConnection().getTinyInt1isBit(); }
/*      */ 
/*      */ 
/*      */   
/*  721 */   public boolean getTraceProtocol() { return getActiveMySQLConnection().getTraceProtocol(); }
/*      */ 
/*      */ 
/*      */   
/*  725 */   public boolean getTransformedBitIsBoolean() { return getActiveMySQLConnection().getTransformedBitIsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/*  729 */   public boolean getTreatUtilDateAsTimestamp() { return getActiveMySQLConnection().getTreatUtilDateAsTimestamp(); }
/*      */ 
/*      */ 
/*      */   
/*  733 */   public String getTrustCertificateKeyStorePassword() throws SQLException { return getActiveMySQLConnection().getTrustCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */   
/*  737 */   public String getTrustCertificateKeyStoreType() throws SQLException { return getActiveMySQLConnection().getTrustCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/*  741 */   public String getTrustCertificateKeyStoreUrl() throws SQLException { return getActiveMySQLConnection().getTrustCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/*  745 */   public boolean getUltraDevHack() { return getActiveMySQLConnection().getUltraDevHack(); }
/*      */ 
/*      */ 
/*      */   
/*  749 */   public boolean getUseAffectedRows() { return getActiveMySQLConnection().getUseAffectedRows(); }
/*      */ 
/*      */ 
/*      */   
/*  753 */   public boolean getUseBlobToStoreUTF8OutsideBMP() { return getActiveMySQLConnection().getUseBlobToStoreUTF8OutsideBMP(); }
/*      */ 
/*      */ 
/*      */   
/*  757 */   public boolean getUseColumnNamesInFindColumn() { return getActiveMySQLConnection().getUseColumnNamesInFindColumn(); }
/*      */ 
/*      */ 
/*      */   
/*  761 */   public boolean getUseCompression() { return getActiveMySQLConnection().getUseCompression(); }
/*      */ 
/*      */ 
/*      */   
/*  765 */   public String getUseConfigs() throws SQLException { return getActiveMySQLConnection().getUseConfigs(); }
/*      */ 
/*      */ 
/*      */   
/*  769 */   public boolean getUseCursorFetch() { return getActiveMySQLConnection().getUseCursorFetch(); }
/*      */ 
/*      */ 
/*      */   
/*  773 */   public boolean getUseDirectRowUnpack() { return getActiveMySQLConnection().getUseDirectRowUnpack(); }
/*      */ 
/*      */ 
/*      */   
/*  777 */   public boolean getUseDynamicCharsetInfo() { return getActiveMySQLConnection().getUseDynamicCharsetInfo(); }
/*      */ 
/*      */ 
/*      */   
/*  781 */   public boolean getUseFastDateParsing() { return getActiveMySQLConnection().getUseFastDateParsing(); }
/*      */ 
/*      */ 
/*      */   
/*  785 */   public boolean getUseFastIntParsing() { return getActiveMySQLConnection().getUseFastIntParsing(); }
/*      */ 
/*      */ 
/*      */   
/*  789 */   public boolean getUseGmtMillisForDatetimes() { return getActiveMySQLConnection().getUseGmtMillisForDatetimes(); }
/*      */ 
/*      */ 
/*      */   
/*  793 */   public boolean getUseHostsInPrivileges() { return getActiveMySQLConnection().getUseHostsInPrivileges(); }
/*      */ 
/*      */ 
/*      */   
/*  797 */   public boolean getUseInformationSchema() { return getActiveMySQLConnection().getUseInformationSchema(); }
/*      */ 
/*      */ 
/*      */   
/*  801 */   public boolean getUseJDBCCompliantTimezoneShift() { return getActiveMySQLConnection().getUseJDBCCompliantTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/*  805 */   public boolean getUseJvmCharsetConverters() { return getActiveMySQLConnection().getUseJvmCharsetConverters(); }
/*      */ 
/*      */ 
/*      */   
/*  809 */   public boolean getUseLegacyDatetimeCode() { return getActiveMySQLConnection().getUseLegacyDatetimeCode(); }
/*      */ 
/*      */ 
/*      */   
/*  813 */   public boolean getUseLocalSessionState() { return getActiveMySQLConnection().getUseLocalSessionState(); }
/*      */ 
/*      */ 
/*      */   
/*  817 */   public boolean getUseLocalTransactionState() { return getActiveMySQLConnection().getUseLocalTransactionState(); }
/*      */ 
/*      */ 
/*      */   
/*  821 */   public boolean getUseNanosForElapsedTime() { return getActiveMySQLConnection().getUseNanosForElapsedTime(); }
/*      */ 
/*      */ 
/*      */   
/*  825 */   public boolean getUseOldAliasMetadataBehavior() { return getActiveMySQLConnection().getUseOldAliasMetadataBehavior(); }
/*      */ 
/*      */ 
/*      */   
/*  829 */   public boolean getUseOldUTF8Behavior() { return getActiveMySQLConnection().getUseOldUTF8Behavior(); }
/*      */ 
/*      */ 
/*      */   
/*  833 */   public boolean getUseOnlyServerErrorMessages() { return getActiveMySQLConnection().getUseOnlyServerErrorMessages(); }
/*      */ 
/*      */ 
/*      */   
/*  837 */   public boolean getUseReadAheadInput() { return getActiveMySQLConnection().getUseReadAheadInput(); }
/*      */ 
/*      */ 
/*      */   
/*  841 */   public boolean getUseSSL() { return getActiveMySQLConnection().getUseSSL(); }
/*      */ 
/*      */ 
/*      */   
/*  845 */   public boolean getUseSSPSCompatibleTimezoneShift() { return getActiveMySQLConnection().getUseSSPSCompatibleTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/*  849 */   public boolean getUseServerPrepStmts() { return getActiveMySQLConnection().getUseServerPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  853 */   public boolean getUseServerPreparedStmts() { return getActiveMySQLConnection().getUseServerPreparedStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  857 */   public boolean getUseSqlStateCodes() { return getActiveMySQLConnection().getUseSqlStateCodes(); }
/*      */ 
/*      */ 
/*      */   
/*  861 */   public boolean getUseStreamLengthsInPrepStmts() { return getActiveMySQLConnection().getUseStreamLengthsInPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  865 */   public boolean getUseTimezone() { return getActiveMySQLConnection().getUseTimezone(); }
/*      */ 
/*      */ 
/*      */   
/*  869 */   public boolean getUseUltraDevWorkAround() { return getActiveMySQLConnection().getUseUltraDevWorkAround(); }
/*      */ 
/*      */ 
/*      */   
/*  873 */   public boolean getUseUnbufferedInput() { return getActiveMySQLConnection().getUseUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */   
/*  877 */   public boolean getUseUnicode() { return getActiveMySQLConnection().getUseUnicode(); }
/*      */ 
/*      */ 
/*      */   
/*  881 */   public boolean getUseUsageAdvisor() { return getActiveMySQLConnection().getUseUsageAdvisor(); }
/*      */ 
/*      */ 
/*      */   
/*  885 */   public String getUtf8OutsideBmpExcludedColumnNamePattern() throws SQLException { return getActiveMySQLConnection().getUtf8OutsideBmpExcludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  890 */   public String getUtf8OutsideBmpIncludedColumnNamePattern() throws SQLException { return getActiveMySQLConnection().getUtf8OutsideBmpIncludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  895 */   public boolean getVerifyServerCertificate() { return getActiveMySQLConnection().getVerifyServerCertificate(); }
/*      */ 
/*      */ 
/*      */   
/*  899 */   public boolean getYearIsDateType() { return getActiveMySQLConnection().getYearIsDateType(); }
/*      */ 
/*      */ 
/*      */   
/*  903 */   public String getZeroDateTimeBehavior() throws SQLException { return getActiveMySQLConnection().getZeroDateTimeBehavior(); }
/*      */ 
/*      */ 
/*      */   
/*  907 */   public void setAllowLoadLocalInfile(boolean property) throws SQLException { getActiveMySQLConnection().setAllowLoadLocalInfile(property); }
/*      */ 
/*      */ 
/*      */   
/*  911 */   public void setAllowMultiQueries(boolean property) throws SQLException { getActiveMySQLConnection().setAllowMultiQueries(property); }
/*      */ 
/*      */ 
/*      */   
/*  915 */   public void setAllowNanAndInf(boolean flag) throws SQLException { getActiveMySQLConnection().setAllowNanAndInf(flag); }
/*      */ 
/*      */ 
/*      */   
/*  919 */   public void setAllowUrlInLocalInfile(boolean flag) throws SQLException { getActiveMySQLConnection().setAllowUrlInLocalInfile(flag); }
/*      */ 
/*      */ 
/*      */   
/*  923 */   public void setAlwaysSendSetIsolation(boolean flag) throws SQLException { getActiveMySQLConnection().setAlwaysSendSetIsolation(flag); }
/*      */ 
/*      */ 
/*      */   
/*  927 */   public void setAutoClosePStmtStreams(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoClosePStmtStreams(flag); }
/*      */ 
/*      */ 
/*      */   
/*  931 */   public void setAutoDeserialize(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoDeserialize(flag); }
/*      */ 
/*      */ 
/*      */   
/*  935 */   public void setAutoGenerateTestcaseScript(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoGenerateTestcaseScript(flag); }
/*      */ 
/*      */ 
/*      */   
/*  939 */   public void setAutoReconnect(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoReconnect(flag); }
/*      */ 
/*      */ 
/*      */   
/*  943 */   public void setAutoReconnectForConnectionPools(boolean property) throws SQLException { getActiveMySQLConnection().setAutoReconnectForConnectionPools(property); }
/*      */ 
/*      */ 
/*      */   
/*  947 */   public void setAutoReconnectForPools(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoReconnectForPools(flag); }
/*      */ 
/*      */ 
/*      */   
/*  951 */   public void setAutoSlowLog(boolean flag) throws SQLException { getActiveMySQLConnection().setAutoSlowLog(flag); }
/*      */ 
/*      */ 
/*      */   
/*  955 */   public void setBlobSendChunkSize(String value) { getActiveMySQLConnection().setBlobSendChunkSize(value); }
/*      */ 
/*      */ 
/*      */   
/*  959 */   public void setBlobsAreStrings(boolean flag) throws SQLException { getActiveMySQLConnection().setBlobsAreStrings(flag); }
/*      */ 
/*      */ 
/*      */   
/*  963 */   public void setCacheCallableStatements(boolean flag) throws SQLException { getActiveMySQLConnection().setCacheCallableStatements(flag); }
/*      */ 
/*      */ 
/*      */   
/*  967 */   public void setCacheCallableStmts(boolean flag) throws SQLException { getActiveMySQLConnection().setCacheCallableStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/*  971 */   public void setCachePrepStmts(boolean flag) throws SQLException { getActiveMySQLConnection().setCachePrepStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/*  975 */   public void setCachePreparedStatements(boolean flag) throws SQLException { getActiveMySQLConnection().setCachePreparedStatements(flag); }
/*      */ 
/*      */ 
/*      */   
/*  979 */   public void setCacheResultSetMetadata(boolean property) throws SQLException { getActiveMySQLConnection().setCacheResultSetMetadata(property); }
/*      */ 
/*      */ 
/*      */   
/*  983 */   public void setCacheServerConfiguration(boolean flag) throws SQLException { getActiveMySQLConnection().setCacheServerConfiguration(flag); }
/*      */ 
/*      */ 
/*      */   
/*  987 */   public void setCallableStatementCacheSize(int size) { getActiveMySQLConnection().setCallableStatementCacheSize(size); }
/*      */ 
/*      */ 
/*      */   
/*  991 */   public void setCallableStmtCacheSize(int cacheSize) { getActiveMySQLConnection().setCallableStmtCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */   
/*  995 */   public void setCapitalizeDBMDTypes(boolean property) throws SQLException { getActiveMySQLConnection().setCapitalizeDBMDTypes(property); }
/*      */ 
/*      */ 
/*      */   
/*  999 */   public void setCapitalizeTypeNames(boolean flag) throws SQLException { getActiveMySQLConnection().setCapitalizeTypeNames(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1003 */   public void setCharacterEncoding(String encoding) { getActiveMySQLConnection().setCharacterEncoding(encoding); }
/*      */ 
/*      */ 
/*      */   
/* 1007 */   public void setCharacterSetResults(String characterSet) { getActiveMySQLConnection().setCharacterSetResults(characterSet); }
/*      */ 
/*      */ 
/*      */   
/* 1011 */   public void setClientCertificateKeyStorePassword(String value) { getActiveMySQLConnection().setClientCertificateKeyStorePassword(value); }
/*      */ 
/*      */ 
/*      */   
/* 1015 */   public void setClientCertificateKeyStoreType(String value) { getActiveMySQLConnection().setClientCertificateKeyStoreType(value); }
/*      */ 
/*      */ 
/*      */   
/* 1019 */   public void setClientCertificateKeyStoreUrl(String value) { getActiveMySQLConnection().setClientCertificateKeyStoreUrl(value); }
/*      */ 
/*      */ 
/*      */   
/* 1023 */   public void setClientInfoProvider(String classname) { getActiveMySQLConnection().setClientInfoProvider(classname); }
/*      */ 
/*      */ 
/*      */   
/* 1027 */   public void setClobCharacterEncoding(String encoding) { getActiveMySQLConnection().setClobCharacterEncoding(encoding); }
/*      */ 
/*      */ 
/*      */   
/* 1031 */   public void setClobberStreamingResults(boolean flag) throws SQLException { getActiveMySQLConnection().setClobberStreamingResults(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1035 */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) throws SQLException { getActiveMySQLConnection().setCompensateOnDuplicateKeyUpdateCounts(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   public void setConnectTimeout(int timeoutMs) { getActiveMySQLConnection().setConnectTimeout(timeoutMs); }
/*      */ 
/*      */ 
/*      */   
/* 1044 */   public void setConnectionCollation(String collation) { getActiveMySQLConnection().setConnectionCollation(collation); }
/*      */ 
/*      */ 
/*      */   
/* 1048 */   public void setConnectionLifecycleInterceptors(String interceptors) { getActiveMySQLConnection().setConnectionLifecycleInterceptors(interceptors); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1053 */   public void setContinueBatchOnError(boolean property) throws SQLException { getActiveMySQLConnection().setContinueBatchOnError(property); }
/*      */ 
/*      */ 
/*      */   
/* 1057 */   public void setCreateDatabaseIfNotExist(boolean flag) throws SQLException { getActiveMySQLConnection().setCreateDatabaseIfNotExist(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1061 */   public void setDefaultFetchSize(int n) { getActiveMySQLConnection().setDefaultFetchSize(n); }
/*      */ 
/*      */ 
/*      */   
/* 1065 */   public void setDetectServerPreparedStmts(boolean property) throws SQLException { getActiveMySQLConnection().setDetectServerPreparedStmts(property); }
/*      */ 
/*      */ 
/*      */   
/* 1069 */   public void setDontTrackOpenResources(boolean flag) throws SQLException { getActiveMySQLConnection().setDontTrackOpenResources(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1073 */   public void setDumpMetadataOnColumnNotFound(boolean flag) throws SQLException { getActiveMySQLConnection().setDumpMetadataOnColumnNotFound(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1077 */   public void setDumpQueriesOnException(boolean flag) throws SQLException { getActiveMySQLConnection().setDumpQueriesOnException(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1081 */   public void setDynamicCalendars(boolean flag) throws SQLException { getActiveMySQLConnection().setDynamicCalendars(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1085 */   public void setElideSetAutoCommits(boolean flag) throws SQLException { getActiveMySQLConnection().setElideSetAutoCommits(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1089 */   public void setEmptyStringsConvertToZero(boolean flag) throws SQLException { getActiveMySQLConnection().setEmptyStringsConvertToZero(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1093 */   public void setEmulateLocators(boolean property) throws SQLException { getActiveMySQLConnection().setEmulateLocators(property); }
/*      */ 
/*      */ 
/*      */   
/* 1097 */   public void setEmulateUnsupportedPstmts(boolean flag) throws SQLException { getActiveMySQLConnection().setEmulateUnsupportedPstmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1101 */   public void setEnablePacketDebug(boolean flag) throws SQLException { getActiveMySQLConnection().setEnablePacketDebug(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1105 */   public void setEnableQueryTimeouts(boolean flag) throws SQLException { getActiveMySQLConnection().setEnableQueryTimeouts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1109 */   public void setEncoding(String property) { getActiveMySQLConnection().setEncoding(property); }
/*      */ 
/*      */ 
/*      */   
/* 1113 */   public void setExceptionInterceptors(String exceptionInterceptors) { getActiveMySQLConnection().setExceptionInterceptors(exceptionInterceptors); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1118 */   public void setExplainSlowQueries(boolean flag) throws SQLException { getActiveMySQLConnection().setExplainSlowQueries(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1122 */   public void setFailOverReadOnly(boolean flag) throws SQLException { getActiveMySQLConnection().setFailOverReadOnly(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1126 */   public void setFunctionsNeverReturnBlobs(boolean flag) throws SQLException { getActiveMySQLConnection().setFunctionsNeverReturnBlobs(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1130 */   public void setGatherPerfMetrics(boolean flag) throws SQLException { getActiveMySQLConnection().setGatherPerfMetrics(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1134 */   public void setGatherPerformanceMetrics(boolean flag) throws SQLException { getActiveMySQLConnection().setGatherPerformanceMetrics(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1138 */   public void setGenerateSimpleParameterMetadata(boolean flag) throws SQLException { getActiveMySQLConnection().setGenerateSimpleParameterMetadata(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1142 */   public void setHoldResultsOpenOverStatementClose(boolean flag) throws SQLException { getActiveMySQLConnection().setHoldResultsOpenOverStatementClose(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1146 */   public void setIgnoreNonTxTables(boolean property) throws SQLException { getActiveMySQLConnection().setIgnoreNonTxTables(property); }
/*      */ 
/*      */ 
/*      */   
/* 1150 */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) throws SQLException { getActiveMySQLConnection().setIncludeInnodbStatusInDeadlockExceptions(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1155 */   public void setInitialTimeout(int property) { getActiveMySQLConnection().setInitialTimeout(property); }
/*      */ 
/*      */ 
/*      */   
/* 1159 */   public void setInteractiveClient(boolean property) throws SQLException { getActiveMySQLConnection().setInteractiveClient(property); }
/*      */ 
/*      */ 
/*      */   
/* 1163 */   public void setIsInteractiveClient(boolean property) throws SQLException { getActiveMySQLConnection().setIsInteractiveClient(property); }
/*      */ 
/*      */ 
/*      */   
/* 1167 */   public void setJdbcCompliantTruncation(boolean flag) throws SQLException { getActiveMySQLConnection().setJdbcCompliantTruncation(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1172 */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) throws SQLException { getActiveMySQLConnection().setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1177 */   public void setLargeRowSizeThreshold(String value) { getActiveMySQLConnection().setLargeRowSizeThreshold(value); }
/*      */ 
/*      */ 
/*      */   
/* 1181 */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) { getActiveMySQLConnection().setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1186 */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) { getActiveMySQLConnection().setLoadBalancePingTimeout(loadBalancePingTimeout); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1191 */   public void setLoadBalanceStrategy(String strategy) { getActiveMySQLConnection().setLoadBalanceStrategy(strategy); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1197 */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) throws SQLException { getActiveMySQLConnection().setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1204 */   public void setLocalSocketAddress(String address) { getActiveMySQLConnection().setLocalSocketAddress(address); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1209 */   public void setLocatorFetchBufferSize(String value) { getActiveMySQLConnection().setLocatorFetchBufferSize(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1214 */   public void setLogSlowQueries(boolean flag) throws SQLException { getActiveMySQLConnection().setLogSlowQueries(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1219 */   public void setLogXaCommands(boolean flag) throws SQLException { getActiveMySQLConnection().setLogXaCommands(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1224 */   public void setLogger(String property) { getActiveMySQLConnection().setLogger(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1229 */   public void setLoggerClassName(String className) { getActiveMySQLConnection().setLoggerClassName(className); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1234 */   public void setMaintainTimeStats(boolean flag) throws SQLException { getActiveMySQLConnection().setMaintainTimeStats(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1239 */   public void setMaxQuerySizeToLog(int sizeInBytes) { getActiveMySQLConnection().setMaxQuerySizeToLog(sizeInBytes); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1244 */   public void setMaxReconnects(int property) { getActiveMySQLConnection().setMaxReconnects(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1249 */   public void setMaxRows(int property) { getActiveMySQLConnection().setMaxRows(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1254 */   public void setMetadataCacheSize(int value) { getActiveMySQLConnection().setMetadataCacheSize(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1259 */   public void setNetTimeoutForStreamingResults(int value) { getActiveMySQLConnection().setNetTimeoutForStreamingResults(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1264 */   public void setNoAccessToProcedureBodies(boolean flag) throws SQLException { getActiveMySQLConnection().setNoAccessToProcedureBodies(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1269 */   public void setNoDatetimeStringSync(boolean flag) throws SQLException { getActiveMySQLConnection().setNoDatetimeStringSync(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1274 */   public void setNoTimezoneConversionForTimeType(boolean flag) throws SQLException { getActiveMySQLConnection().setNoTimezoneConversionForTimeType(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1279 */   public void setNullCatalogMeansCurrent(boolean value) throws SQLException { getActiveMySQLConnection().setNullCatalogMeansCurrent(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1284 */   public void setNullNamePatternMatchesAll(boolean value) throws SQLException { getActiveMySQLConnection().setNullNamePatternMatchesAll(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1289 */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) throws SQLException { getActiveMySQLConnection().setOverrideSupportsIntegrityEnhancementFacility(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1295 */   public void setPacketDebugBufferSize(int size) { getActiveMySQLConnection().setPacketDebugBufferSize(size); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1300 */   public void setPadCharsWithSpace(boolean flag) throws SQLException { getActiveMySQLConnection().setPadCharsWithSpace(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1305 */   public void setParanoid(boolean property) throws SQLException { getActiveMySQLConnection().setParanoid(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1310 */   public void setPasswordCharacterEncoding(String characterSet) { getActiveMySQLConnection().setPasswordCharacterEncoding(characterSet); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1315 */   public void setPedantic(boolean property) throws SQLException { getActiveMySQLConnection().setPedantic(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1320 */   public void setPinGlobalTxToPhysicalConnection(boolean flag) throws SQLException { getActiveMySQLConnection().setPinGlobalTxToPhysicalConnection(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1325 */   public void setPopulateInsertRowWithDefaultValues(boolean flag) throws SQLException { getActiveMySQLConnection().setPopulateInsertRowWithDefaultValues(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1330 */   public void setPrepStmtCacheSize(int cacheSize) { getActiveMySQLConnection().setPrepStmtCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1335 */   public void setPrepStmtCacheSqlLimit(int sqlLimit) { getActiveMySQLConnection().setPrepStmtCacheSqlLimit(sqlLimit); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1340 */   public void setPreparedStatementCacheSize(int cacheSize) { getActiveMySQLConnection().setPreparedStatementCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1345 */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) { getActiveMySQLConnection().setPreparedStatementCacheSqlLimit(cacheSqlLimit); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1351 */   public void setProcessEscapeCodesForPrepStmts(boolean flag) throws SQLException { getActiveMySQLConnection().setProcessEscapeCodesForPrepStmts(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1356 */   public void setProfileSQL(boolean flag) throws SQLException { getActiveMySQLConnection().setProfileSQL(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1361 */   public void setProfileSql(boolean property) throws SQLException { getActiveMySQLConnection().setProfileSql(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1366 */   public void setProfilerEventHandler(String handler) { getActiveMySQLConnection().setProfilerEventHandler(handler); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1371 */   public void setPropertiesTransform(String value) { getActiveMySQLConnection().setPropertiesTransform(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1376 */   public void setQueriesBeforeRetryMaster(int property) { getActiveMySQLConnection().setQueriesBeforeRetryMaster(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1382 */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) throws SQLException { getActiveMySQLConnection().setQueryTimeoutKillsConnection(queryTimeoutKillsConnection); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1388 */   public void setReconnectAtTxEnd(boolean property) throws SQLException { getActiveMySQLConnection().setReconnectAtTxEnd(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1393 */   public void setRelaxAutoCommit(boolean property) throws SQLException { getActiveMySQLConnection().setRelaxAutoCommit(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1398 */   public void setReportMetricsIntervalMillis(int millis) { getActiveMySQLConnection().setReportMetricsIntervalMillis(millis); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1403 */   public void setRequireSSL(boolean property) throws SQLException { getActiveMySQLConnection().setRequireSSL(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1408 */   public void setResourceId(String resourceId) { getActiveMySQLConnection().setResourceId(resourceId); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1413 */   public void setResultSetSizeThreshold(int threshold) { getActiveMySQLConnection().setResultSetSizeThreshold(threshold); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1418 */   public void setRetainStatementAfterResultSetClose(boolean flag) throws SQLException { getActiveMySQLConnection().setRetainStatementAfterResultSetClose(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1423 */   public void setRetriesAllDown(int retriesAllDown) { getActiveMySQLConnection().setRetriesAllDown(retriesAllDown); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1428 */   public void setRewriteBatchedStatements(boolean flag) throws SQLException { getActiveMySQLConnection().setRewriteBatchedStatements(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1433 */   public void setRollbackOnPooledClose(boolean flag) throws SQLException { getActiveMySQLConnection().setRollbackOnPooledClose(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1438 */   public void setRoundRobinLoadBalance(boolean flag) throws SQLException { getActiveMySQLConnection().setRoundRobinLoadBalance(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1443 */   public void setRunningCTS13(boolean flag) throws SQLException { getActiveMySQLConnection().setRunningCTS13(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1448 */   public void setSecondsBeforeRetryMaster(int property) { getActiveMySQLConnection().setSecondsBeforeRetryMaster(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1453 */   public void setSelfDestructOnPingMaxOperations(int maxOperations) { getActiveMySQLConnection().setSelfDestructOnPingMaxOperations(maxOperations); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1459 */   public void setSelfDestructOnPingSecondsLifetime(int seconds) { getActiveMySQLConnection().setSelfDestructOnPingSecondsLifetime(seconds); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1465 */   public void setServerTimezone(String property) { getActiveMySQLConnection().setServerTimezone(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1470 */   public void setSessionVariables(String variables) { getActiveMySQLConnection().setSessionVariables(variables); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1475 */   public void setSlowQueryThresholdMillis(int millis) { getActiveMySQLConnection().setSlowQueryThresholdMillis(millis); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1480 */   public void setSlowQueryThresholdNanos(long nanos) { getActiveMySQLConnection().setSlowQueryThresholdNanos(nanos); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1485 */   public void setSocketFactory(String name) { getActiveMySQLConnection().setSocketFactory(name); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1490 */   public void setSocketFactoryClassName(String property) { getActiveMySQLConnection().setSocketFactoryClassName(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1495 */   public void setSocketTimeout(int property) { getActiveMySQLConnection().setSocketTimeout(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1500 */   public void setStatementInterceptors(String value) { getActiveMySQLConnection().setStatementInterceptors(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1505 */   public void setStrictFloatingPoint(boolean property) throws SQLException { getActiveMySQLConnection().setStrictFloatingPoint(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1510 */   public void setStrictUpdates(boolean property) throws SQLException { getActiveMySQLConnection().setStrictUpdates(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1515 */   public void setTcpKeepAlive(boolean flag) throws SQLException { getActiveMySQLConnection().setTcpKeepAlive(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1520 */   public void setTcpNoDelay(boolean flag) throws SQLException { getActiveMySQLConnection().setTcpNoDelay(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1525 */   public void setTcpRcvBuf(int bufSize) { getActiveMySQLConnection().setTcpRcvBuf(bufSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1530 */   public void setTcpSndBuf(int bufSize) { getActiveMySQLConnection().setTcpSndBuf(bufSize); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1535 */   public void setTcpTrafficClass(int classFlags) { getActiveMySQLConnection().setTcpTrafficClass(classFlags); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1540 */   public void setTinyInt1isBit(boolean flag) throws SQLException { getActiveMySQLConnection().setTinyInt1isBit(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1545 */   public void setTraceProtocol(boolean flag) throws SQLException { getActiveMySQLConnection().setTraceProtocol(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1550 */   public void setTransformedBitIsBoolean(boolean flag) throws SQLException { getActiveMySQLConnection().setTransformedBitIsBoolean(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1555 */   public void setTreatUtilDateAsTimestamp(boolean flag) throws SQLException { getActiveMySQLConnection().setTreatUtilDateAsTimestamp(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1560 */   public void setTrustCertificateKeyStorePassword(String value) { getActiveMySQLConnection().setTrustCertificateKeyStorePassword(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1565 */   public void setTrustCertificateKeyStoreType(String value) { getActiveMySQLConnection().setTrustCertificateKeyStoreType(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1570 */   public void setTrustCertificateKeyStoreUrl(String value) { getActiveMySQLConnection().setTrustCertificateKeyStoreUrl(value); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1575 */   public void setUltraDevHack(boolean flag) throws SQLException { getActiveMySQLConnection().setUltraDevHack(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1580 */   public void setUseAffectedRows(boolean flag) throws SQLException { getActiveMySQLConnection().setUseAffectedRows(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1585 */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) throws SQLException { getActiveMySQLConnection().setUseBlobToStoreUTF8OutsideBMP(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1590 */   public void setUseColumnNamesInFindColumn(boolean flag) throws SQLException { getActiveMySQLConnection().setUseColumnNamesInFindColumn(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1595 */   public void setUseCompression(boolean property) throws SQLException { getActiveMySQLConnection().setUseCompression(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1600 */   public void setUseConfigs(String configs) { getActiveMySQLConnection().setUseConfigs(configs); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1605 */   public void setUseCursorFetch(boolean flag) throws SQLException { getActiveMySQLConnection().setUseCursorFetch(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1610 */   public void setUseDirectRowUnpack(boolean flag) throws SQLException { getActiveMySQLConnection().setUseDirectRowUnpack(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1615 */   public void setUseDynamicCharsetInfo(boolean flag) throws SQLException { getActiveMySQLConnection().setUseDynamicCharsetInfo(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1620 */   public void setUseFastDateParsing(boolean flag) throws SQLException { getActiveMySQLConnection().setUseFastDateParsing(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1625 */   public void setUseFastIntParsing(boolean flag) throws SQLException { getActiveMySQLConnection().setUseFastIntParsing(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1630 */   public void setUseGmtMillisForDatetimes(boolean flag) throws SQLException { getActiveMySQLConnection().setUseGmtMillisForDatetimes(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1635 */   public void setUseHostsInPrivileges(boolean property) throws SQLException { getActiveMySQLConnection().setUseHostsInPrivileges(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1640 */   public void setUseInformationSchema(boolean flag) throws SQLException { getActiveMySQLConnection().setUseInformationSchema(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1645 */   public void setUseJDBCCompliantTimezoneShift(boolean flag) throws SQLException { getActiveMySQLConnection().setUseJDBCCompliantTimezoneShift(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1650 */   public void setUseJvmCharsetConverters(boolean flag) throws SQLException { getActiveMySQLConnection().setUseJvmCharsetConverters(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1655 */   public void setUseLegacyDatetimeCode(boolean flag) throws SQLException { getActiveMySQLConnection().setUseLegacyDatetimeCode(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1660 */   public void setUseLocalSessionState(boolean flag) throws SQLException { getActiveMySQLConnection().setUseLocalSessionState(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1665 */   public void setUseLocalTransactionState(boolean flag) throws SQLException { getActiveMySQLConnection().setUseLocalTransactionState(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1670 */   public void setUseNanosForElapsedTime(boolean flag) throws SQLException { getActiveMySQLConnection().setUseNanosForElapsedTime(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1675 */   public void setUseOldAliasMetadataBehavior(boolean flag) throws SQLException { getActiveMySQLConnection().setUseOldAliasMetadataBehavior(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1680 */   public void setUseOldUTF8Behavior(boolean flag) throws SQLException { getActiveMySQLConnection().setUseOldUTF8Behavior(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1685 */   public void setUseOnlyServerErrorMessages(boolean flag) throws SQLException { getActiveMySQLConnection().setUseOnlyServerErrorMessages(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1690 */   public void setUseReadAheadInput(boolean flag) throws SQLException { getActiveMySQLConnection().setUseReadAheadInput(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1695 */   public void setUseSSL(boolean property) throws SQLException { getActiveMySQLConnection().setUseSSL(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1700 */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) throws SQLException { getActiveMySQLConnection().setUseSSPSCompatibleTimezoneShift(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1705 */   public void setUseServerPrepStmts(boolean flag) throws SQLException { getActiveMySQLConnection().setUseServerPrepStmts(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1710 */   public void setUseServerPreparedStmts(boolean flag) throws SQLException { getActiveMySQLConnection().setUseServerPreparedStmts(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1715 */   public void setUseSqlStateCodes(boolean flag) throws SQLException { getActiveMySQLConnection().setUseSqlStateCodes(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1720 */   public void setUseStreamLengthsInPrepStmts(boolean property) throws SQLException { getActiveMySQLConnection().setUseStreamLengthsInPrepStmts(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1725 */   public void setUseTimezone(boolean property) throws SQLException { getActiveMySQLConnection().setUseTimezone(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1730 */   public void setUseUltraDevWorkAround(boolean property) throws SQLException { getActiveMySQLConnection().setUseUltraDevWorkAround(property); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1735 */   public void setUseUnbufferedInput(boolean flag) throws SQLException { getActiveMySQLConnection().setUseUnbufferedInput(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1740 */   public void setUseUnicode(boolean flag) throws SQLException { getActiveMySQLConnection().setUseUnicode(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1745 */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) throws SQLException { getActiveMySQLConnection().setUseUsageAdvisor(useUsageAdvisorFlag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1750 */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) { getActiveMySQLConnection().setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1756 */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) { getActiveMySQLConnection().setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1762 */   public void setVerifyServerCertificate(boolean flag) throws SQLException { getActiveMySQLConnection().setVerifyServerCertificate(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1767 */   public void setYearIsDateType(boolean flag) throws SQLException { getActiveMySQLConnection().setYearIsDateType(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1772 */   public void setZeroDateTimeBehavior(String behavior) { getActiveMySQLConnection().setZeroDateTimeBehavior(behavior); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1777 */   public boolean useUnbufferedInput() { return getActiveMySQLConnection().useUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1782 */   public StringBuffer generateConnectionCommentBlock(StringBuffer buf) { return getActiveMySQLConnection().generateConnectionCommentBlock(buf); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1787 */   public int getActiveStatementCount() { return getActiveMySQLConnection().getActiveStatementCount(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1792 */   public boolean getAutoCommit() { return getActiveMySQLConnection().getAutoCommit(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1797 */   public int getAutoIncrementIncrement() { return getActiveMySQLConnection().getAutoIncrementIncrement(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1802 */   public CachedResultSetMetaData getCachedMetaData(String sql) { return getActiveMySQLConnection().getCachedMetaData(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1807 */   public Calendar getCalendarInstanceForSessionOrNew() { return getActiveMySQLConnection().getCalendarInstanceForSessionOrNew(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1812 */   public Timer getCancelTimer() { return getActiveMySQLConnection().getCancelTimer(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1817 */   public String getCatalog() throws SQLException { return getActiveMySQLConnection().getCatalog(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1822 */   public String getCharacterSetMetadata() throws SQLException { return getActiveMySQLConnection().getCharacterSetMetadata(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1828 */   public SingleByteCharsetConverter getCharsetConverter(String javaEncodingName) throws SQLException { return getActiveMySQLConnection().getCharsetConverter(javaEncodingName); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1833 */   public String getCharsetNameForIndex(int charsetIndex) throws SQLException { return getActiveMySQLConnection().getCharsetNameForIndex(charsetIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1838 */   public TimeZone getDefaultTimeZone() { return getActiveMySQLConnection().getDefaultTimeZone(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1843 */   public String getErrorMessageEncoding() throws SQLException { return getActiveMySQLConnection().getErrorMessageEncoding(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1848 */   public ExceptionInterceptor getExceptionInterceptor() { return getActiveMySQLConnection().getExceptionInterceptor(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1853 */   public int getHoldability() { return getActiveMySQLConnection().getHoldability(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1858 */   public String getHost() throws SQLException { return getActiveMySQLConnection().getHost(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1863 */   public long getId() { return getActiveMySQLConnection().getId(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1868 */   public long getIdleFor() { return getActiveMySQLConnection().getIdleFor(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1873 */   public MysqlIO getIO() throws SQLException { return getActiveMySQLConnection().getIO(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1878 */   public MySQLConnection getLoadBalanceSafeProxy() { return getActiveMySQLConnection().getLoadBalanceSafeProxy(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1883 */   public Log getLog() throws SQLException { return getActiveMySQLConnection().getLog(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1888 */   public int getMaxBytesPerChar(String javaCharsetName) throws SQLException { return getActiveMySQLConnection().getMaxBytesPerChar(javaCharsetName); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1893 */   public DatabaseMetaData getMetaData() throws SQLException { return getActiveMySQLConnection().getMetaData(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1898 */   public Statement getMetadataSafeStatement() throws SQLException { return getActiveMySQLConnection().getMetadataSafeStatement(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1903 */   public Object getMutex() throws SQLException { return getActiveMySQLConnection().getMutex(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1908 */   public int getNetBufferLength() { return getActiveMySQLConnection().getNetBufferLength(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1913 */   public Properties getProperties() { return getActiveMySQLConnection().getProperties(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1918 */   public boolean getRequiresEscapingEncoder() { return getActiveMySQLConnection().getRequiresEscapingEncoder(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1923 */   public String getServerCharacterEncoding() throws SQLException { return getActiveMySQLConnection().getServerCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1928 */   public int getServerMajorVersion() { return getActiveMySQLConnection().getServerMajorVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1933 */   public int getServerMinorVersion() { return getActiveMySQLConnection().getServerMinorVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1938 */   public int getServerSubMinorVersion() { return getActiveMySQLConnection().getServerSubMinorVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1943 */   public TimeZone getServerTimezoneTZ() { return getActiveMySQLConnection().getServerTimezoneTZ(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1948 */   public String getServerVariable(String variableName) { return getActiveMySQLConnection().getServerVariable(variableName); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1953 */   public String getServerVersion() throws SQLException { return getActiveMySQLConnection().getServerVersion(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1958 */   public Calendar getSessionLockedCalendar() { return getActiveMySQLConnection().getSessionLockedCalendar(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1963 */   public String getStatementComment() throws SQLException { return getActiveMySQLConnection().getStatementComment(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1968 */   public List getStatementInterceptorsInstances() { return getActiveMySQLConnection().getStatementInterceptorsInstances(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1973 */   public int getTransactionIsolation() { return getActiveMySQLConnection().getTransactionIsolation(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1978 */   public Map getTypeMap() throws SQLException { return getActiveMySQLConnection().getTypeMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1983 */   public String getURL() throws SQLException { return getActiveMySQLConnection().getURL(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1988 */   public String getUser() throws SQLException { return getActiveMySQLConnection().getUser(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1993 */   public Calendar getUtcCalendar() { return getActiveMySQLConnection().getUtcCalendar(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1998 */   public SQLWarning getWarnings() throws SQLException { return getActiveMySQLConnection().getWarnings(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2003 */   public boolean hasSameProperties(Connection c) { return getActiveMySQLConnection().hasSameProperties(c); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2008 */   public boolean hasTriedMaster() { return getActiveMySQLConnection().hasTriedMaster(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2013 */   public void incrementNumberOfPreparedExecutes() throws SQLException { getActiveMySQLConnection().incrementNumberOfPreparedExecutes(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2018 */   public void incrementNumberOfPrepares() throws SQLException { getActiveMySQLConnection().incrementNumberOfPrepares(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2023 */   public void incrementNumberOfResultSetsCreated() throws SQLException { getActiveMySQLConnection().incrementNumberOfResultSetsCreated(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2028 */   public void initializeExtension(Extension ex) throws SQLException { getActiveMySQLConnection().initializeExtension(ex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2035 */   public void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSetInternalMethods resultSet) throws SQLException { getActiveMySQLConnection().initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2041 */   public void initializeSafeStatementInterceptors() throws SQLException { getActiveMySQLConnection().initializeSafeStatementInterceptors(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2046 */   public boolean isAbonormallyLongQuery(long millisOrNanos) { return getActiveMySQLConnection().isAbonormallyLongQuery(millisOrNanos); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2051 */   public boolean isClientTzUTC() { return getActiveMySQLConnection().isClientTzUTC(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2056 */   public boolean isCursorFetchEnabled() { return getActiveMySQLConnection().isCursorFetchEnabled(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2061 */   public boolean isInGlobalTx() { return getActiveMySQLConnection().isInGlobalTx(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2066 */   public boolean isMasterConnection() { return getActiveMySQLConnection().isMasterConnection(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2071 */   public boolean isNoBackslashEscapesSet() { return getActiveMySQLConnection().isNoBackslashEscapesSet(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2076 */   public boolean isReadInfoMsgEnabled() { return getActiveMySQLConnection().isReadInfoMsgEnabled(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2081 */   public boolean isReadOnly() { return getActiveMySQLConnection().isReadOnly(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2086 */   public boolean isRunningOnJDK13() { return getActiveMySQLConnection().isRunningOnJDK13(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2091 */   public boolean isSameResource(Connection otherConnection) { return getActiveMySQLConnection().isSameResource(otherConnection); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2096 */   public boolean isServerTzUTC() { return getActiveMySQLConnection().isServerTzUTC(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2101 */   public boolean lowerCaseTableNames() { return getActiveMySQLConnection().lowerCaseTableNames(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2106 */   public void maxRowsChanged(Statement stmt) { getActiveMySQLConnection().maxRowsChanged(stmt); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2111 */   public String nativeSQL(String sql) { return getActiveMySQLConnection().nativeSQL(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2116 */   public boolean parserKnowsUnicode() { return getActiveMySQLConnection().parserKnowsUnicode(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2121 */   public void ping() throws SQLException { getActiveMySQLConnection().ping(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2127 */   public void pingInternal(boolean checkForClosedConnection, int timeoutMillis) throws SQLException { getActiveMySQLConnection().pingInternal(checkForClosedConnection, timeoutMillis); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2135 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2142 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2148 */   public CallableStatement prepareCall(String sql) throws SQLException { return getActiveMySQLConnection().prepareCall(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2155 */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2162 */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2169 */   public PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2176 */   public PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndexes); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2183 */   public PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql, autoGenKeyColNames); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2189 */   public PreparedStatement prepareStatement(String sql) throws SQLException { return getActiveMySQLConnection().prepareStatement(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2195 */   public void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException { getActiveMySQLConnection().realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2202 */   public void recachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException { getActiveMySQLConnection().recachePreparedStatement(pstmt); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2207 */   public void registerQueryExecutionTime(long queryTimeMs) { getActiveMySQLConnection().registerQueryExecutionTime(queryTimeMs); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2212 */   public void registerStatement(Statement stmt) { getActiveMySQLConnection().registerStatement(stmt); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2217 */   public void releaseSavepoint(Savepoint arg0) throws SQLException { getActiveMySQLConnection().releaseSavepoint(arg0); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2222 */   public void reportNumberOfTablesAccessed(int numTablesAccessed) { getActiveMySQLConnection().reportNumberOfTablesAccessed(numTablesAccessed); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2228 */   public void reportQueryTime(long millisOrNanos) { getActiveMySQLConnection().reportQueryTime(millisOrNanos); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2233 */   public void resetServerState() throws SQLException { getActiveMySQLConnection().resetServerState(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2238 */   public void rollback() throws SQLException { getActiveMySQLConnection().rollback(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2243 */   public void rollback(Savepoint savepoint) throws SQLException { getActiveMySQLConnection().rollback(savepoint); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2250 */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2257 */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2264 */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2271 */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndexes); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2278 */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyColNames); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2285 */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException { return getActiveMySQLConnection().serverPrepareStatement(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2290 */   public boolean serverSupportsConvertFn() { return getActiveMySQLConnection().serverSupportsConvertFn(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2295 */   public void setAutoCommit(boolean autoCommitFlag) throws SQLException { getActiveMySQLConnection().setAutoCommit(autoCommitFlag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2300 */   public void setCatalog(String catalog) { getActiveMySQLConnection().setCatalog(catalog); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2305 */   public void setFailedOver(boolean flag) throws SQLException { getActiveMySQLConnection().setFailedOver(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2310 */   public void setHoldability(int arg0) { getActiveMySQLConnection().setHoldability(arg0); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2315 */   public void setInGlobalTx(boolean flag) throws SQLException { getActiveMySQLConnection().setInGlobalTx(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2320 */   public void setPreferSlaveDuringFailover(boolean flag) throws SQLException { getActiveMySQLConnection().setPreferSlaveDuringFailover(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2325 */   public void setProxy(MySQLConnection proxy) { getActiveMySQLConnection().setProxy(proxy); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2330 */   public void setReadInfoMsgEnabled(boolean flag) throws SQLException { getActiveMySQLConnection().setReadInfoMsgEnabled(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2335 */   public void setReadOnly(boolean readOnlyFlag) throws SQLException { getActiveMySQLConnection().setReadOnly(readOnlyFlag); }
/*      */ 
/*      */ 
/*      */   
/* 2339 */   public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException { getActiveMySQLConnection().setReadOnlyInternal(readOnlyFlag); }
/*      */ 
/*      */ 
/*      */   
/* 2343 */   public Savepoint setSavepoint() throws SQLException { return getActiveMySQLConnection().setSavepoint(); }
/*      */ 
/*      */ 
/*      */   
/* 2347 */   public Savepoint setSavepoint(String name) throws SQLException { return getActiveMySQLConnection().setSavepoint(name); }
/*      */ 
/*      */ 
/*      */   
/* 2351 */   public void setStatementComment(String comment) { getActiveMySQLConnection().setStatementComment(comment); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2356 */   public void setTransactionIsolation(int level) { getActiveMySQLConnection().setTransactionIsolation(level); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2361 */   public void shutdownServer() throws SQLException { getActiveMySQLConnection().shutdownServer(); }
/*      */ 
/*      */ 
/*      */   
/* 2365 */   public boolean storesLowerCaseTableName() { return getActiveMySQLConnection().storesLowerCaseTableName(); }
/*      */ 
/*      */ 
/*      */   
/* 2369 */   public boolean supportsIsolationLevel() { return getActiveMySQLConnection().supportsIsolationLevel(); }
/*      */ 
/*      */ 
/*      */   
/* 2373 */   public boolean supportsQuotedIdentifiers() { return getActiveMySQLConnection().supportsQuotedIdentifiers(); }
/*      */ 
/*      */ 
/*      */   
/* 2377 */   public boolean supportsTransactions() { return getActiveMySQLConnection().supportsTransactions(); }
/*      */ 
/*      */ 
/*      */   
/* 2381 */   public void throwConnectionClosedException() throws SQLException { getActiveMySQLConnection().throwConnectionClosedException(); }
/*      */ 
/*      */ 
/*      */   
/* 2385 */   public void transactionBegun() throws SQLException { getActiveMySQLConnection().transactionBegun(); }
/*      */ 
/*      */ 
/*      */   
/* 2389 */   public void transactionCompleted() throws SQLException { getActiveMySQLConnection().transactionCompleted(); }
/*      */ 
/*      */ 
/*      */   
/* 2393 */   public void unregisterStatement(Statement stmt) { getActiveMySQLConnection().unregisterStatement(stmt); }
/*      */ 
/*      */ 
/*      */   
/* 2397 */   public void unSafeStatementInterceptors() throws SQLException { getActiveMySQLConnection().unSafeStatementInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 2401 */   public void unsetMaxRows(Statement stmt) { getActiveMySQLConnection().unsetMaxRows(stmt); }
/*      */ 
/*      */ 
/*      */   
/* 2405 */   public boolean useAnsiQuotedIdentifiers() { return getActiveMySQLConnection().useAnsiQuotedIdentifiers(); }
/*      */ 
/*      */ 
/*      */   
/* 2409 */   public boolean useMaxRows() { return getActiveMySQLConnection().useMaxRows(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2414 */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException { return getActiveMySQLConnection().versionMeetsMinimum(major, minor, subminor); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2419 */   public boolean isClosed() { return getActiveMySQLConnection().isClosed(); }
/*      */ 
/*      */ 
/*      */   
/* 2423 */   public boolean getHoldResultsOpenOverStatementClose() { return getActiveMySQLConnection().getHoldResultsOpenOverStatementClose(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2428 */   public String getLoadBalanceConnectionGroup() throws SQLException { return getActiveMySQLConnection().getLoadBalanceConnectionGroup(); }
/*      */ 
/*      */ 
/*      */   
/* 2432 */   public boolean getLoadBalanceEnableJMX() { return getActiveMySQLConnection().getLoadBalanceEnableJMX(); }
/*      */ 
/*      */ 
/*      */   
/* 2436 */   public String getLoadBalanceExceptionChecker() throws SQLException { return getActiveMySQLConnection().getLoadBalanceExceptionChecker(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2441 */   public String getLoadBalanceSQLExceptionSubclassFailover() throws SQLException { return getActiveMySQLConnection().getLoadBalanceSQLExceptionSubclassFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2446 */   public String getLoadBalanceSQLStateFailover() throws SQLException { return getActiveMySQLConnection().getLoadBalanceSQLStateFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2451 */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) { getActiveMySQLConnection().setLoadBalanceConnectionGroup(loadBalanceConnectionGroup); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2457 */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) throws SQLException { getActiveMySQLConnection().setLoadBalanceEnableJMX(loadBalanceEnableJMX); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2464 */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) { getActiveMySQLConnection().setLoadBalanceExceptionChecker(loadBalanceExceptionChecker); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2471 */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) { getActiveMySQLConnection().setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2478 */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) { getActiveMySQLConnection().setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2484 */   public boolean shouldExecutionTriggerServerSwapAfter(String SQL) { return false; }
/*      */ 
/*      */ 
/*      */   
/* 2488 */   public boolean isProxySet() { return getActiveMySQLConnection().isProxySet(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2493 */   public String getLoadBalanceAutoCommitStatementRegex() throws SQLException { return getActiveMySQLConnection().getLoadBalanceAutoCommitStatementRegex(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2498 */   public int getLoadBalanceAutoCommitStatementThreshold() { return getActiveMySQLConnection().getLoadBalanceAutoCommitStatementThreshold(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2504 */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) { getActiveMySQLConnection().setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2511 */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) { getActiveMySQLConnection().setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2517 */   public void setTypeMap(Map map) throws SQLException { getActiveMySQLConnection().setTypeMap(map); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\LoadBalancedMySQLConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */