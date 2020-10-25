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
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
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
/*      */ public class ReplicationConnection
/*      */   implements Connection, PingTarget
/*      */ {
/*      */   protected Connection currentConnection;
/*      */   protected Connection masterConnection;
/*      */   protected Connection slavesConnection;
/*      */   
/*      */   protected ReplicationConnection() {}
/*      */   
/*      */   public ReplicationConnection(Properties masterProperties, Properties slaveProperties) throws SQLException {
/*   56 */     NonRegisteringDriver driver = new NonRegisteringDriver();
/*      */     
/*   58 */     StringBuffer masterUrl = new StringBuffer("jdbc:mysql://");
/*   59 */     StringBuffer slaveUrl = new StringBuffer("jdbc:mysql:loadbalance://");
/*      */     
/*   61 */     String masterHost = masterProperties.getProperty("HOST");
/*      */ 
/*      */     
/*   64 */     if (masterHost != null) {
/*   65 */       masterUrl.append(masterHost);
/*      */     }
/*      */     
/*   68 */     int numHosts = Integer.parseInt(slaveProperties.getProperty("NUM_HOSTS"));
/*      */ 
/*      */     
/*   71 */     for (i = 1; i <= numHosts; i++) {
/*   72 */       String slaveHost = slaveProperties.getProperty("HOST." + i);
/*      */ 
/*      */       
/*   75 */       if (slaveHost != null) {
/*   76 */         if (i > 1) {
/*   77 */           slaveUrl.append(',');
/*      */         }
/*   79 */         slaveUrl.append(slaveHost);
/*      */       } 
/*      */     } 
/*      */     
/*   83 */     String masterDb = masterProperties.getProperty("DBNAME");
/*      */ 
/*      */     
/*   86 */     masterUrl.append("/");
/*      */     
/*   88 */     if (masterDb != null) {
/*   89 */       masterUrl.append(masterDb);
/*      */     }
/*      */     
/*   92 */     String slaveDb = slaveProperties.getProperty("DBNAME");
/*      */ 
/*      */     
/*   95 */     slaveUrl.append("/");
/*      */     
/*   97 */     if (slaveDb != null) {
/*   98 */       slaveUrl.append(slaveDb);
/*      */     }
/*      */     
/*  101 */     slaveProperties.setProperty("roundRobinLoadBalance", "true");
/*      */     
/*  103 */     this.masterConnection = (Connection)driver.connect(masterUrl.toString(), masterProperties);
/*      */     
/*  105 */     this.slavesConnection = (Connection)driver.connect(slaveUrl.toString(), slaveProperties);
/*      */     
/*  107 */     this.slavesConnection.setReadOnly(true);
/*      */     
/*  109 */     this.currentConnection = this.masterConnection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   public void clearWarnings() { this.currentConnection.clearWarnings(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  127 */     this.masterConnection.close();
/*  128 */     this.slavesConnection.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   public void commit() { this.currentConnection.commit(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement() throws SQLException {
/*  146 */     Statement stmt = this.currentConnection.createStatement();
/*  147 */     ((Statement)stmt).setPingTarget(this);
/*      */     
/*  149 */     return stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/*  159 */     Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency);
/*      */ 
/*      */     
/*  162 */     ((Statement)stmt).setPingTarget(this);
/*      */     
/*  164 */     return stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*  175 */     Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */ 
/*      */     
/*  178 */     ((Statement)stmt).setPingTarget(this);
/*      */     
/*  180 */     return stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   public boolean getAutoCommit() throws SQLException { return this.currentConnection.getAutoCommit(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  198 */   public String getCatalog() throws SQLException { return this.currentConnection.getCatalog(); }
/*      */ 
/*      */ 
/*      */   
/*  202 */   public Connection getCurrentConnection() { return this.currentConnection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  211 */   public int getHoldability() throws SQLException { return this.currentConnection.getHoldability(); }
/*      */ 
/*      */ 
/*      */   
/*  215 */   public Connection getMasterConnection() { return this.masterConnection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   public DatabaseMetaData getMetaData() throws SQLException { return this.currentConnection.getMetaData(); }
/*      */ 
/*      */ 
/*      */   
/*  228 */   public Connection getSlavesConnection() { return this.slavesConnection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   public int getTransactionIsolation() throws SQLException { return this.currentConnection.getTransactionIsolation(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   public Map getTypeMap() throws SQLException { return this.currentConnection.getTypeMap(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  255 */   public SQLWarning getWarnings() throws SQLException { return this.currentConnection.getWarnings(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   public boolean isClosed() throws SQLException { return this.currentConnection.isClosed(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  273 */   public boolean isReadOnly() throws SQLException { return (this.currentConnection == this.slavesConnection); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  282 */   public String nativeSQL(String sql) throws SQLException { return this.currentConnection.nativeSQL(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   public CallableStatement prepareCall(String sql) throws SQLException { return this.currentConnection.prepareCall(sql); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  301 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/*  323 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
/*      */     
/*  325 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  327 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
/*  337 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, autoGeneratedKeys);
/*      */     
/*  339 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  341 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  351 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
/*      */ 
/*      */     
/*  354 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  356 */     return pstmt;
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
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*  368 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*      */ 
/*      */     
/*  371 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  373 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
/*  383 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnIndexes);
/*      */     
/*  385 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  387 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
/*  398 */     PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnNames);
/*      */     
/*  400 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  402 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  412 */   public void releaseSavepoint(Savepoint savepoint) throws SQLException { this.currentConnection.releaseSavepoint(savepoint); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  421 */   public void rollback() { this.currentConnection.rollback(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  430 */   public void rollback(Savepoint savepoint) throws SQLException { this.currentConnection.rollback(savepoint); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  440 */   public void setAutoCommit(boolean autoCommit) throws SQLException { this.currentConnection.setAutoCommit(autoCommit); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  449 */   public void setCatalog(String catalog) throws SQLException { this.currentConnection.setCatalog(catalog); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  459 */   public void setHoldability(int holdability) throws SQLException { this.currentConnection.setHoldability(holdability); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnly) throws SQLException {
/*  468 */     if (readOnly) {
/*  469 */       if (this.currentConnection != this.slavesConnection) {
/*  470 */         switchToSlavesConnection();
/*      */       }
/*      */     }
/*  473 */     else if (this.currentConnection != this.masterConnection) {
/*  474 */       switchToMasterConnection();
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
/*  485 */   public Savepoint setSavepoint() throws SQLException { return this.currentConnection.setSavepoint(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  494 */   public Savepoint setSavepoint(String name) throws SQLException { return this.currentConnection.setSavepoint(name); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  504 */   public void setTransactionIsolation(int level) throws SQLException { this.currentConnection.setTransactionIsolation(level); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  515 */   public void setTypeMap(Map arg0) throws SQLException { this.currentConnection.setTypeMap(arg0); }
/*      */ 
/*      */ 
/*      */   
/*  519 */   private void switchToMasterConnection() { swapConnections(this.masterConnection, this.slavesConnection); }
/*      */ 
/*      */ 
/*      */   
/*  523 */   private void switchToSlavesConnection() { swapConnections(this.slavesConnection, this.masterConnection); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void swapConnections(Connection switchToConnection, Connection switchFromConnection) throws SQLException {
/*  538 */     String switchFromCatalog = switchFromConnection.getCatalog();
/*  539 */     String switchToCatalog = switchToConnection.getCatalog();
/*      */     
/*  541 */     if (switchToCatalog != null && !switchToCatalog.equals(switchFromCatalog)) {
/*  542 */       switchToConnection.setCatalog(switchFromCatalog);
/*  543 */     } else if (switchFromCatalog != null) {
/*  544 */       switchToConnection.setCatalog(switchFromCatalog);
/*      */     } 
/*      */     
/*  547 */     boolean switchToAutoCommit = switchToConnection.getAutoCommit();
/*  548 */     boolean switchFromConnectionAutoCommit = switchFromConnection.getAutoCommit();
/*      */     
/*  550 */     if (switchFromConnectionAutoCommit != switchToAutoCommit) {
/*  551 */       switchToConnection.setAutoCommit(switchFromConnectionAutoCommit);
/*      */     }
/*      */     
/*  554 */     int switchToIsolation = switchToConnection.getTransactionIsolation();
/*      */ 
/*      */     
/*  557 */     int switchFromIsolation = switchFromConnection.getTransactionIsolation();
/*      */     
/*  559 */     if (switchFromIsolation != switchToIsolation) {
/*  560 */       switchToConnection.setTransactionIsolation(switchFromIsolation);
/*      */     }
/*      */ 
/*      */     
/*  564 */     this.currentConnection = switchToConnection;
/*      */   }
/*      */   
/*      */   public void doPing() {
/*  568 */     if (this.masterConnection != null) {
/*  569 */       this.masterConnection.ping();
/*      */     }
/*      */     
/*  572 */     if (this.slavesConnection != null) {
/*  573 */       this.slavesConnection.ping();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeUser(String userName, String newPassword) throws SQLException {
/*  579 */     this.masterConnection.changeUser(userName, newPassword);
/*  580 */     this.slavesConnection.changeUser(userName, newPassword);
/*      */   }
/*      */   
/*      */   public void clearHasTriedMaster() {
/*  584 */     this.masterConnection.clearHasTriedMaster();
/*  585 */     this.slavesConnection.clearHasTriedMaster();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
/*  591 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql);
/*  592 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  594 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*  599 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyIndex);
/*  600 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  602 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  607 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
/*  608 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  610 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*  615 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyIndexes);
/*  616 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  618 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*  624 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*  625 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  627 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*  632 */     PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyColNames);
/*  633 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  635 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*  639 */   public int getActiveStatementCount() throws SQLException { return this.currentConnection.getActiveStatementCount(); }
/*      */ 
/*      */ 
/*      */   
/*  643 */   public long getIdleFor() { return this.currentConnection.getIdleFor(); }
/*      */ 
/*      */ 
/*      */   
/*  647 */   public Log getLog() throws SQLException { return this.currentConnection.getLog(); }
/*      */ 
/*      */ 
/*      */   
/*  651 */   public String getServerCharacterEncoding() throws SQLException { return this.currentConnection.getServerCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  655 */   public TimeZone getServerTimezoneTZ() { return this.currentConnection.getServerTimezoneTZ(); }
/*      */ 
/*      */ 
/*      */   
/*  659 */   public String getStatementComment() throws SQLException { return this.currentConnection.getStatementComment(); }
/*      */ 
/*      */ 
/*      */   
/*  663 */   public boolean hasTriedMaster() throws SQLException { return this.currentConnection.hasTriedMaster(); }
/*      */ 
/*      */ 
/*      */   
/*  667 */   public void initializeExtension(Extension ex) throws SQLException { this.currentConnection.initializeExtension(ex); }
/*      */ 
/*      */ 
/*      */   
/*  671 */   public boolean isAbonormallyLongQuery(long millisOrNanos) { return this.currentConnection.isAbonormallyLongQuery(millisOrNanos); }
/*      */ 
/*      */ 
/*      */   
/*  675 */   public boolean isInGlobalTx() throws SQLException { return this.currentConnection.isInGlobalTx(); }
/*      */ 
/*      */ 
/*      */   
/*  679 */   public boolean isMasterConnection() throws SQLException { return this.currentConnection.isMasterConnection(); }
/*      */ 
/*      */ 
/*      */   
/*  683 */   public boolean isNoBackslashEscapesSet() throws SQLException { return this.currentConnection.isNoBackslashEscapesSet(); }
/*      */ 
/*      */ 
/*      */   
/*  687 */   public boolean lowerCaseTableNames() throws SQLException { return this.currentConnection.lowerCaseTableNames(); }
/*      */ 
/*      */ 
/*      */   
/*  691 */   public boolean parserKnowsUnicode() throws SQLException { return this.currentConnection.parserKnowsUnicode(); }
/*      */ 
/*      */   
/*      */   public void ping() {
/*  695 */     this.masterConnection.ping();
/*  696 */     this.slavesConnection.ping();
/*      */   }
/*      */ 
/*      */   
/*  700 */   public void reportQueryTime(long millisOrNanos) { this.currentConnection.reportQueryTime(millisOrNanos); }
/*      */ 
/*      */ 
/*      */   
/*  704 */   public void resetServerState() { this.currentConnection.resetServerState(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
/*  709 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql);
/*  710 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  712 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*  717 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyIndex);
/*  718 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  720 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  725 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
/*  726 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  728 */     return pstmt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*  734 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*  735 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  737 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*  742 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyIndexes);
/*  743 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  745 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*  750 */     PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyColNames);
/*  751 */     ((Statement)pstmt).setPingTarget(this);
/*      */     
/*  753 */     return pstmt;
/*      */   }
/*      */ 
/*      */   
/*  757 */   public void setFailedOver(boolean flag) throws SQLException { this.currentConnection.setFailedOver(flag); }
/*      */ 
/*      */ 
/*      */   
/*  761 */   public void setPreferSlaveDuringFailover(boolean flag) throws SQLException { this.currentConnection.setPreferSlaveDuringFailover(flag); }
/*      */ 
/*      */   
/*      */   public void setStatementComment(String comment) throws SQLException {
/*  765 */     this.masterConnection.setStatementComment(comment);
/*  766 */     this.slavesConnection.setStatementComment(comment);
/*      */   }
/*      */ 
/*      */   
/*  770 */   public void shutdownServer() { this.currentConnection.shutdownServer(); }
/*      */ 
/*      */ 
/*      */   
/*  774 */   public boolean supportsIsolationLevel() throws SQLException { return this.currentConnection.supportsIsolationLevel(); }
/*      */ 
/*      */ 
/*      */   
/*  778 */   public boolean supportsQuotedIdentifiers() throws SQLException { return this.currentConnection.supportsQuotedIdentifiers(); }
/*      */ 
/*      */ 
/*      */   
/*  782 */   public boolean supportsTransactions() throws SQLException { return this.currentConnection.supportsTransactions(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  787 */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException { return this.currentConnection.versionMeetsMinimum(major, minor, subminor); }
/*      */ 
/*      */ 
/*      */   
/*  791 */   public String exposeAsXml() throws SQLException { return this.currentConnection.exposeAsXml(); }
/*      */ 
/*      */ 
/*      */   
/*  795 */   public boolean getAllowLoadLocalInfile() throws SQLException { return this.currentConnection.getAllowLoadLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/*  799 */   public boolean getAllowMultiQueries() throws SQLException { return this.currentConnection.getAllowMultiQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  803 */   public boolean getAllowNanAndInf() throws SQLException { return this.currentConnection.getAllowNanAndInf(); }
/*      */ 
/*      */ 
/*      */   
/*  807 */   public boolean getAllowUrlInLocalInfile() throws SQLException { return this.currentConnection.getAllowUrlInLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/*  811 */   public boolean getAlwaysSendSetIsolation() throws SQLException { return this.currentConnection.getAlwaysSendSetIsolation(); }
/*      */ 
/*      */ 
/*      */   
/*  815 */   public boolean getAutoClosePStmtStreams() throws SQLException { return this.currentConnection.getAutoClosePStmtStreams(); }
/*      */ 
/*      */ 
/*      */   
/*  819 */   public boolean getAutoDeserialize() throws SQLException { return this.currentConnection.getAutoDeserialize(); }
/*      */ 
/*      */ 
/*      */   
/*  823 */   public boolean getAutoGenerateTestcaseScript() throws SQLException { return this.currentConnection.getAutoGenerateTestcaseScript(); }
/*      */ 
/*      */ 
/*      */   
/*  827 */   public boolean getAutoReconnectForPools() throws SQLException { return this.currentConnection.getAutoReconnectForPools(); }
/*      */ 
/*      */ 
/*      */   
/*  831 */   public boolean getAutoSlowLog() throws SQLException { return this.currentConnection.getAutoSlowLog(); }
/*      */ 
/*      */ 
/*      */   
/*  835 */   public int getBlobSendChunkSize() throws SQLException { return this.currentConnection.getBlobSendChunkSize(); }
/*      */ 
/*      */ 
/*      */   
/*  839 */   public boolean getBlobsAreStrings() throws SQLException { return this.currentConnection.getBlobsAreStrings(); }
/*      */ 
/*      */ 
/*      */   
/*  843 */   public boolean getCacheCallableStatements() throws SQLException { return this.currentConnection.getCacheCallableStatements(); }
/*      */ 
/*      */ 
/*      */   
/*  847 */   public boolean getCacheCallableStmts() throws SQLException { return this.currentConnection.getCacheCallableStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  851 */   public boolean getCachePrepStmts() throws SQLException { return this.currentConnection.getCachePrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/*  855 */   public boolean getCachePreparedStatements() throws SQLException { return this.currentConnection.getCachePreparedStatements(); }
/*      */ 
/*      */ 
/*      */   
/*  859 */   public boolean getCacheResultSetMetadata() throws SQLException { return this.currentConnection.getCacheResultSetMetadata(); }
/*      */ 
/*      */ 
/*      */   
/*  863 */   public boolean getCacheServerConfiguration() throws SQLException { return this.currentConnection.getCacheServerConfiguration(); }
/*      */ 
/*      */ 
/*      */   
/*  867 */   public int getCallableStatementCacheSize() throws SQLException { return this.currentConnection.getCallableStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  871 */   public int getCallableStmtCacheSize() throws SQLException { return this.currentConnection.getCallableStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/*  875 */   public boolean getCapitalizeTypeNames() throws SQLException { return this.currentConnection.getCapitalizeTypeNames(); }
/*      */ 
/*      */ 
/*      */   
/*  879 */   public String getCharacterSetResults() throws SQLException { return this.currentConnection.getCharacterSetResults(); }
/*      */ 
/*      */ 
/*      */   
/*  883 */   public String getClientCertificateKeyStorePassword() throws SQLException { return this.currentConnection.getClientCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */   
/*  887 */   public String getClientCertificateKeyStoreType() throws SQLException { return this.currentConnection.getClientCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/*  891 */   public String getClientCertificateKeyStoreUrl() throws SQLException { return this.currentConnection.getClientCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/*  895 */   public String getClientInfoProvider() throws SQLException { return this.currentConnection.getClientInfoProvider(); }
/*      */ 
/*      */ 
/*      */   
/*  899 */   public String getClobCharacterEncoding() throws SQLException { return this.currentConnection.getClobCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  903 */   public boolean getClobberStreamingResults() throws SQLException { return this.currentConnection.getClobberStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/*  907 */   public int getConnectTimeout() throws SQLException { return this.currentConnection.getConnectTimeout(); }
/*      */ 
/*      */ 
/*      */   
/*  911 */   public String getConnectionCollation() throws SQLException { return this.currentConnection.getConnectionCollation(); }
/*      */ 
/*      */ 
/*      */   
/*  915 */   public String getConnectionLifecycleInterceptors() throws SQLException { return this.currentConnection.getConnectionLifecycleInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/*  919 */   public boolean getContinueBatchOnError() throws SQLException { return this.currentConnection.getContinueBatchOnError(); }
/*      */ 
/*      */ 
/*      */   
/*  923 */   public boolean getCreateDatabaseIfNotExist() throws SQLException { return this.currentConnection.getCreateDatabaseIfNotExist(); }
/*      */ 
/*      */ 
/*      */   
/*  927 */   public int getDefaultFetchSize() throws SQLException { return this.currentConnection.getDefaultFetchSize(); }
/*      */ 
/*      */ 
/*      */   
/*  931 */   public boolean getDontTrackOpenResources() throws SQLException { return this.currentConnection.getDontTrackOpenResources(); }
/*      */ 
/*      */ 
/*      */   
/*  935 */   public boolean getDumpMetadataOnColumnNotFound() throws SQLException { return this.currentConnection.getDumpMetadataOnColumnNotFound(); }
/*      */ 
/*      */ 
/*      */   
/*  939 */   public boolean getDumpQueriesOnException() throws SQLException { return this.currentConnection.getDumpQueriesOnException(); }
/*      */ 
/*      */ 
/*      */   
/*  943 */   public boolean getDynamicCalendars() throws SQLException { return this.currentConnection.getDynamicCalendars(); }
/*      */ 
/*      */ 
/*      */   
/*  947 */   public boolean getElideSetAutoCommits() throws SQLException { return this.currentConnection.getElideSetAutoCommits(); }
/*      */ 
/*      */ 
/*      */   
/*  951 */   public boolean getEmptyStringsConvertToZero() throws SQLException { return this.currentConnection.getEmptyStringsConvertToZero(); }
/*      */ 
/*      */ 
/*      */   
/*  955 */   public boolean getEmulateLocators() throws SQLException { return this.currentConnection.getEmulateLocators(); }
/*      */ 
/*      */ 
/*      */   
/*  959 */   public boolean getEmulateUnsupportedPstmts() throws SQLException { return this.currentConnection.getEmulateUnsupportedPstmts(); }
/*      */ 
/*      */ 
/*      */   
/*  963 */   public boolean getEnablePacketDebug() throws SQLException { return this.currentConnection.getEnablePacketDebug(); }
/*      */ 
/*      */ 
/*      */   
/*  967 */   public boolean getEnableQueryTimeouts() throws SQLException { return this.currentConnection.getEnableQueryTimeouts(); }
/*      */ 
/*      */ 
/*      */   
/*  971 */   public String getEncoding() throws SQLException { return this.currentConnection.getEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  975 */   public boolean getExplainSlowQueries() throws SQLException { return this.currentConnection.getExplainSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/*  979 */   public boolean getFailOverReadOnly() throws SQLException { return this.currentConnection.getFailOverReadOnly(); }
/*      */ 
/*      */ 
/*      */   
/*  983 */   public boolean getFunctionsNeverReturnBlobs() throws SQLException { return this.currentConnection.getFunctionsNeverReturnBlobs(); }
/*      */ 
/*      */ 
/*      */   
/*  987 */   public boolean getGatherPerfMetrics() throws SQLException { return this.currentConnection.getGatherPerfMetrics(); }
/*      */ 
/*      */ 
/*      */   
/*  991 */   public boolean getGatherPerformanceMetrics() throws SQLException { return this.currentConnection.getGatherPerformanceMetrics(); }
/*      */ 
/*      */ 
/*      */   
/*  995 */   public boolean getGenerateSimpleParameterMetadata() throws SQLException { return this.currentConnection.getGenerateSimpleParameterMetadata(); }
/*      */ 
/*      */ 
/*      */   
/*  999 */   public boolean getHoldResultsOpenOverStatementClose() throws SQLException { return this.currentConnection.getHoldResultsOpenOverStatementClose(); }
/*      */ 
/*      */ 
/*      */   
/* 1003 */   public boolean getIgnoreNonTxTables() throws SQLException { return this.currentConnection.getIgnoreNonTxTables(); }
/*      */ 
/*      */ 
/*      */   
/* 1007 */   public boolean getIncludeInnodbStatusInDeadlockExceptions() throws SQLException { return this.currentConnection.getIncludeInnodbStatusInDeadlockExceptions(); }
/*      */ 
/*      */ 
/*      */   
/* 1011 */   public int getInitialTimeout() throws SQLException { return this.currentConnection.getInitialTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 1015 */   public boolean getInteractiveClient() throws SQLException { return this.currentConnection.getInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/* 1019 */   public boolean getIsInteractiveClient() throws SQLException { return this.currentConnection.getIsInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/* 1023 */   public boolean getJdbcCompliantTruncation() throws SQLException { return this.currentConnection.getJdbcCompliantTruncation(); }
/*      */ 
/*      */ 
/*      */   
/* 1027 */   public boolean getJdbcCompliantTruncationForReads() throws SQLException { return this.currentConnection.getJdbcCompliantTruncationForReads(); }
/*      */ 
/*      */ 
/*      */   
/* 1031 */   public String getLargeRowSizeThreshold() throws SQLException { return this.currentConnection.getLargeRowSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/* 1035 */   public String getLoadBalanceStrategy() throws SQLException { return this.currentConnection.getLoadBalanceStrategy(); }
/*      */ 
/*      */ 
/*      */   
/* 1039 */   public String getLocalSocketAddress() throws SQLException { return this.currentConnection.getLocalSocketAddress(); }
/*      */ 
/*      */ 
/*      */   
/* 1043 */   public int getLocatorFetchBufferSize() throws SQLException { return this.currentConnection.getLocatorFetchBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1047 */   public boolean getLogSlowQueries() throws SQLException { return this.currentConnection.getLogSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/* 1051 */   public boolean getLogXaCommands() throws SQLException { return this.currentConnection.getLogXaCommands(); }
/*      */ 
/*      */ 
/*      */   
/* 1055 */   public String getLogger() throws SQLException { return this.currentConnection.getLogger(); }
/*      */ 
/*      */ 
/*      */   
/* 1059 */   public String getLoggerClassName() throws SQLException { return this.currentConnection.getLoggerClassName(); }
/*      */ 
/*      */ 
/*      */   
/* 1063 */   public boolean getMaintainTimeStats() throws SQLException { return this.currentConnection.getMaintainTimeStats(); }
/*      */ 
/*      */ 
/*      */   
/* 1067 */   public int getMaxQuerySizeToLog() throws SQLException { return this.currentConnection.getMaxQuerySizeToLog(); }
/*      */ 
/*      */ 
/*      */   
/* 1071 */   public int getMaxReconnects() throws SQLException { return this.currentConnection.getMaxReconnects(); }
/*      */ 
/*      */ 
/*      */   
/* 1075 */   public int getMaxRows() throws SQLException { return this.currentConnection.getMaxRows(); }
/*      */ 
/*      */ 
/*      */   
/* 1079 */   public int getMetadataCacheSize() throws SQLException { return this.currentConnection.getMetadataCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1083 */   public int getNetTimeoutForStreamingResults() throws SQLException { return this.currentConnection.getNetTimeoutForStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/* 1087 */   public boolean getNoAccessToProcedureBodies() throws SQLException { return this.currentConnection.getNoAccessToProcedureBodies(); }
/*      */ 
/*      */ 
/*      */   
/* 1091 */   public boolean getNoDatetimeStringSync() throws SQLException { return this.currentConnection.getNoDatetimeStringSync(); }
/*      */ 
/*      */ 
/*      */   
/* 1095 */   public boolean getNoTimezoneConversionForTimeType() throws SQLException { return this.currentConnection.getNoTimezoneConversionForTimeType(); }
/*      */ 
/*      */ 
/*      */   
/* 1099 */   public boolean getNullCatalogMeansCurrent() throws SQLException { return this.currentConnection.getNullCatalogMeansCurrent(); }
/*      */ 
/*      */ 
/*      */   
/* 1103 */   public boolean getNullNamePatternMatchesAll() throws SQLException { return this.currentConnection.getNullNamePatternMatchesAll(); }
/*      */ 
/*      */ 
/*      */   
/* 1107 */   public boolean getOverrideSupportsIntegrityEnhancementFacility() throws SQLException { return this.currentConnection.getOverrideSupportsIntegrityEnhancementFacility(); }
/*      */ 
/*      */ 
/*      */   
/* 1111 */   public int getPacketDebugBufferSize() throws SQLException { return this.currentConnection.getPacketDebugBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1115 */   public boolean getPadCharsWithSpace() throws SQLException { return this.currentConnection.getPadCharsWithSpace(); }
/*      */ 
/*      */ 
/*      */   
/* 1119 */   public boolean getParanoid() throws SQLException { return this.currentConnection.getParanoid(); }
/*      */ 
/*      */ 
/*      */   
/* 1123 */   public boolean getPedantic() throws SQLException { return this.currentConnection.getPedantic(); }
/*      */ 
/*      */ 
/*      */   
/* 1127 */   public boolean getPinGlobalTxToPhysicalConnection() throws SQLException { return this.currentConnection.getPinGlobalTxToPhysicalConnection(); }
/*      */ 
/*      */ 
/*      */   
/* 1131 */   public boolean getPopulateInsertRowWithDefaultValues() throws SQLException { return this.currentConnection.getPopulateInsertRowWithDefaultValues(); }
/*      */ 
/*      */ 
/*      */   
/* 1135 */   public int getPrepStmtCacheSize() throws SQLException { return this.currentConnection.getPrepStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1139 */   public int getPrepStmtCacheSqlLimit() throws SQLException { return this.currentConnection.getPrepStmtCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/* 1143 */   public int getPreparedStatementCacheSize() throws SQLException { return this.currentConnection.getPreparedStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1147 */   public int getPreparedStatementCacheSqlLimit() throws SQLException { return this.currentConnection.getPreparedStatementCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/* 1151 */   public boolean getProcessEscapeCodesForPrepStmts() throws SQLException { return this.currentConnection.getProcessEscapeCodesForPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1155 */   public boolean getProfileSQL() throws SQLException { return this.currentConnection.getProfileSQL(); }
/*      */ 
/*      */ 
/*      */   
/* 1159 */   public boolean getProfileSql() throws SQLException { return this.currentConnection.getProfileSql(); }
/*      */ 
/*      */ 
/*      */   
/* 1163 */   public String getProfilerEventHandler() throws SQLException { return this.currentConnection.getProfilerEventHandler(); }
/*      */ 
/*      */ 
/*      */   
/* 1167 */   public String getPropertiesTransform() throws SQLException { return this.currentConnection.getPropertiesTransform(); }
/*      */ 
/*      */ 
/*      */   
/* 1171 */   public int getQueriesBeforeRetryMaster() throws SQLException { return this.currentConnection.getQueriesBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/* 1175 */   public boolean getReconnectAtTxEnd() throws SQLException { return this.currentConnection.getReconnectAtTxEnd(); }
/*      */ 
/*      */ 
/*      */   
/* 1179 */   public boolean getRelaxAutoCommit() throws SQLException { return this.currentConnection.getRelaxAutoCommit(); }
/*      */ 
/*      */ 
/*      */   
/* 1183 */   public int getReportMetricsIntervalMillis() throws SQLException { return this.currentConnection.getReportMetricsIntervalMillis(); }
/*      */ 
/*      */ 
/*      */   
/* 1187 */   public boolean getRequireSSL() throws SQLException { return this.currentConnection.getRequireSSL(); }
/*      */ 
/*      */ 
/*      */   
/* 1191 */   public String getResourceId() throws SQLException { return this.currentConnection.getResourceId(); }
/*      */ 
/*      */ 
/*      */   
/* 1195 */   public int getResultSetSizeThreshold() throws SQLException { return this.currentConnection.getResultSetSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/* 1199 */   public boolean getRewriteBatchedStatements() throws SQLException { return this.currentConnection.getRewriteBatchedStatements(); }
/*      */ 
/*      */ 
/*      */   
/* 1203 */   public boolean getRollbackOnPooledClose() throws SQLException { return this.currentConnection.getRollbackOnPooledClose(); }
/*      */ 
/*      */ 
/*      */   
/* 1207 */   public boolean getRoundRobinLoadBalance() throws SQLException { return this.currentConnection.getRoundRobinLoadBalance(); }
/*      */ 
/*      */ 
/*      */   
/* 1211 */   public boolean getRunningCTS13() throws SQLException { return this.currentConnection.getRunningCTS13(); }
/*      */ 
/*      */ 
/*      */   
/* 1215 */   public int getSecondsBeforeRetryMaster() throws SQLException { return this.currentConnection.getSecondsBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/* 1219 */   public int getSelfDestructOnPingMaxOperations() throws SQLException { return this.currentConnection.getSelfDestructOnPingMaxOperations(); }
/*      */ 
/*      */ 
/*      */   
/* 1223 */   public int getSelfDestructOnPingSecondsLifetime() throws SQLException { return this.currentConnection.getSelfDestructOnPingSecondsLifetime(); }
/*      */ 
/*      */ 
/*      */   
/* 1227 */   public String getServerTimezone() throws SQLException { return this.currentConnection.getServerTimezone(); }
/*      */ 
/*      */ 
/*      */   
/* 1231 */   public String getSessionVariables() throws SQLException { return this.currentConnection.getSessionVariables(); }
/*      */ 
/*      */ 
/*      */   
/* 1235 */   public int getSlowQueryThresholdMillis() throws SQLException { return this.currentConnection.getSlowQueryThresholdMillis(); }
/*      */ 
/*      */ 
/*      */   
/* 1239 */   public long getSlowQueryThresholdNanos() { return this.currentConnection.getSlowQueryThresholdNanos(); }
/*      */ 
/*      */ 
/*      */   
/* 1243 */   public String getSocketFactory() throws SQLException { return this.currentConnection.getSocketFactory(); }
/*      */ 
/*      */ 
/*      */   
/* 1247 */   public String getSocketFactoryClassName() throws SQLException { return this.currentConnection.getSocketFactoryClassName(); }
/*      */ 
/*      */ 
/*      */   
/* 1251 */   public int getSocketTimeout() throws SQLException { return this.currentConnection.getSocketTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 1255 */   public String getStatementInterceptors() throws SQLException { return this.currentConnection.getStatementInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 1259 */   public boolean getStrictFloatingPoint() throws SQLException { return this.currentConnection.getStrictFloatingPoint(); }
/*      */ 
/*      */ 
/*      */   
/* 1263 */   public boolean getStrictUpdates() throws SQLException { return this.currentConnection.getStrictUpdates(); }
/*      */ 
/*      */ 
/*      */   
/* 1267 */   public boolean getTcpKeepAlive() throws SQLException { return this.currentConnection.getTcpKeepAlive(); }
/*      */ 
/*      */ 
/*      */   
/* 1271 */   public boolean getTcpNoDelay() throws SQLException { return this.currentConnection.getTcpNoDelay(); }
/*      */ 
/*      */ 
/*      */   
/* 1275 */   public int getTcpRcvBuf() throws SQLException { return this.currentConnection.getTcpRcvBuf(); }
/*      */ 
/*      */ 
/*      */   
/* 1279 */   public int getTcpSndBuf() throws SQLException { return this.currentConnection.getTcpSndBuf(); }
/*      */ 
/*      */ 
/*      */   
/* 1283 */   public int getTcpTrafficClass() throws SQLException { return this.currentConnection.getTcpTrafficClass(); }
/*      */ 
/*      */ 
/*      */   
/* 1287 */   public boolean getTinyInt1isBit() throws SQLException { return this.currentConnection.getTinyInt1isBit(); }
/*      */ 
/*      */ 
/*      */   
/* 1291 */   public boolean getTraceProtocol() throws SQLException { return this.currentConnection.getTraceProtocol(); }
/*      */ 
/*      */ 
/*      */   
/* 1295 */   public boolean getTransformedBitIsBoolean() throws SQLException { return this.currentConnection.getTransformedBitIsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 1299 */   public boolean getTreatUtilDateAsTimestamp() throws SQLException { return this.currentConnection.getTreatUtilDateAsTimestamp(); }
/*      */ 
/*      */ 
/*      */   
/* 1303 */   public String getTrustCertificateKeyStorePassword() throws SQLException { return this.currentConnection.getTrustCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */   
/* 1307 */   public String getTrustCertificateKeyStoreType() throws SQLException { return this.currentConnection.getTrustCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/* 1311 */   public String getTrustCertificateKeyStoreUrl() throws SQLException { return this.currentConnection.getTrustCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/* 1315 */   public boolean getUltraDevHack() throws SQLException { return this.currentConnection.getUltraDevHack(); }
/*      */ 
/*      */ 
/*      */   
/* 1319 */   public boolean getUseBlobToStoreUTF8OutsideBMP() throws SQLException { return this.currentConnection.getUseBlobToStoreUTF8OutsideBMP(); }
/*      */ 
/*      */ 
/*      */   
/* 1323 */   public boolean getUseCompression() throws SQLException { return this.currentConnection.getUseCompression(); }
/*      */ 
/*      */ 
/*      */   
/* 1327 */   public String getUseConfigs() throws SQLException { return this.currentConnection.getUseConfigs(); }
/*      */ 
/*      */ 
/*      */   
/* 1331 */   public boolean getUseCursorFetch() throws SQLException { return this.currentConnection.getUseCursorFetch(); }
/*      */ 
/*      */ 
/*      */   
/* 1335 */   public boolean getUseDirectRowUnpack() throws SQLException { return this.currentConnection.getUseDirectRowUnpack(); }
/*      */ 
/*      */ 
/*      */   
/* 1339 */   public boolean getUseDynamicCharsetInfo() throws SQLException { return this.currentConnection.getUseDynamicCharsetInfo(); }
/*      */ 
/*      */ 
/*      */   
/* 1343 */   public boolean getUseFastDateParsing() throws SQLException { return this.currentConnection.getUseFastDateParsing(); }
/*      */ 
/*      */ 
/*      */   
/* 1347 */   public boolean getUseFastIntParsing() throws SQLException { return this.currentConnection.getUseFastIntParsing(); }
/*      */ 
/*      */ 
/*      */   
/* 1351 */   public boolean getUseGmtMillisForDatetimes() throws SQLException { return this.currentConnection.getUseGmtMillisForDatetimes(); }
/*      */ 
/*      */ 
/*      */   
/* 1355 */   public boolean getUseHostsInPrivileges() throws SQLException { return this.currentConnection.getUseHostsInPrivileges(); }
/*      */ 
/*      */ 
/*      */   
/* 1359 */   public boolean getUseInformationSchema() throws SQLException { return this.currentConnection.getUseInformationSchema(); }
/*      */ 
/*      */ 
/*      */   
/* 1363 */   public boolean getUseJDBCCompliantTimezoneShift() throws SQLException { return this.currentConnection.getUseJDBCCompliantTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/* 1367 */   public boolean getUseJvmCharsetConverters() throws SQLException { return this.currentConnection.getUseJvmCharsetConverters(); }
/*      */ 
/*      */ 
/*      */   
/* 1371 */   public boolean getUseLegacyDatetimeCode() throws SQLException { return this.currentConnection.getUseLegacyDatetimeCode(); }
/*      */ 
/*      */ 
/*      */   
/* 1375 */   public boolean getUseLocalSessionState() throws SQLException { return this.currentConnection.getUseLocalSessionState(); }
/*      */ 
/*      */ 
/*      */   
/* 1379 */   public boolean getUseNanosForElapsedTime() throws SQLException { return this.currentConnection.getUseNanosForElapsedTime(); }
/*      */ 
/*      */ 
/*      */   
/* 1383 */   public boolean getUseOldAliasMetadataBehavior() throws SQLException { return this.currentConnection.getUseOldAliasMetadataBehavior(); }
/*      */ 
/*      */ 
/*      */   
/* 1387 */   public boolean getUseOldUTF8Behavior() throws SQLException { return this.currentConnection.getUseOldUTF8Behavior(); }
/*      */ 
/*      */ 
/*      */   
/* 1391 */   public boolean getUseOnlyServerErrorMessages() throws SQLException { return this.currentConnection.getUseOnlyServerErrorMessages(); }
/*      */ 
/*      */ 
/*      */   
/* 1395 */   public boolean getUseReadAheadInput() throws SQLException { return this.currentConnection.getUseReadAheadInput(); }
/*      */ 
/*      */ 
/*      */   
/* 1399 */   public boolean getUseSSL() throws SQLException { return this.currentConnection.getUseSSL(); }
/*      */ 
/*      */ 
/*      */   
/* 1403 */   public boolean getUseSSPSCompatibleTimezoneShift() throws SQLException { return this.currentConnection.getUseSSPSCompatibleTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/* 1407 */   public boolean getUseServerPrepStmts() throws SQLException { return this.currentConnection.getUseServerPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1411 */   public boolean getUseServerPreparedStmts() throws SQLException { return this.currentConnection.getUseServerPreparedStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1415 */   public boolean getUseSqlStateCodes() throws SQLException { return this.currentConnection.getUseSqlStateCodes(); }
/*      */ 
/*      */ 
/*      */   
/* 1419 */   public boolean getUseStreamLengthsInPrepStmts() throws SQLException { return this.currentConnection.getUseStreamLengthsInPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1423 */   public boolean getUseTimezone() throws SQLException { return this.currentConnection.getUseTimezone(); }
/*      */ 
/*      */ 
/*      */   
/* 1427 */   public boolean getUseUltraDevWorkAround() throws SQLException { return this.currentConnection.getUseUltraDevWorkAround(); }
/*      */ 
/*      */ 
/*      */   
/* 1431 */   public boolean getUseUnbufferedInput() throws SQLException { return this.currentConnection.getUseUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */   
/* 1435 */   public boolean getUseUnicode() throws SQLException { return this.currentConnection.getUseUnicode(); }
/*      */ 
/*      */ 
/*      */   
/* 1439 */   public boolean getUseUsageAdvisor() throws SQLException { return this.currentConnection.getUseUsageAdvisor(); }
/*      */ 
/*      */ 
/*      */   
/* 1443 */   public String getUtf8OutsideBmpExcludedColumnNamePattern() throws SQLException { return this.currentConnection.getUtf8OutsideBmpExcludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */   
/* 1447 */   public String getUtf8OutsideBmpIncludedColumnNamePattern() throws SQLException { return this.currentConnection.getUtf8OutsideBmpIncludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */   
/* 1451 */   public boolean getVerifyServerCertificate() throws SQLException { return this.currentConnection.getVerifyServerCertificate(); }
/*      */ 
/*      */ 
/*      */   
/* 1455 */   public boolean getYearIsDateType() throws SQLException { return this.currentConnection.getYearIsDateType(); }
/*      */ 
/*      */ 
/*      */   
/* 1459 */   public String getZeroDateTimeBehavior() throws SQLException { return this.currentConnection.getZeroDateTimeBehavior(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowLoadLocalInfile(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowMultiQueries(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowNanAndInf(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowUrlInLocalInfile(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAlwaysSendSetIsolation(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoClosePStmtStreams(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoDeserialize(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoGenerateTestcaseScript(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnect(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnectForConnectionPools(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnectForPools(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoSlowLog(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlobSendChunkSize(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlobsAreStrings(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheCallableStatements(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheCallableStmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCachePrepStmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCachePreparedStatements(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheResultSetMetadata(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheServerConfiguration(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallableStatementCacheSize(int size) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallableStmtCacheSize(int cacheSize) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCapitalizeDBMDTypes(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCapitalizeTypeNames(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterEncoding(String encoding) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterSetResults(String characterSet) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStorePassword(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStoreType(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStoreUrl(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientInfoProvider(String classname) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClobCharacterEncoding(String encoding) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClobberStreamingResults(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectTimeout(int timeoutMs) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionCollation(String collation) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionLifecycleInterceptors(String interceptors) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContinueBatchOnError(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCreateDatabaseIfNotExist(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultFetchSize(int n) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDetectServerPreparedStmts(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDontTrackOpenResources(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDumpMetadataOnColumnNotFound(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDumpQueriesOnException(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDynamicCalendars(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setElideSetAutoCommits(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmptyStringsConvertToZero(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmulateLocators(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmulateUnsupportedPstmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnablePacketDebug(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableQueryTimeouts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExplainSlowQueries(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFailOverReadOnly(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFunctionsNeverReturnBlobs(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGatherPerfMetrics(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGatherPerformanceMetrics(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGenerateSimpleParameterMetadata(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHoldResultsOpenOverStatementClose(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoreNonTxTables(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialTimeout(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInteractiveClient(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIsInteractiveClient(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncation(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLargeRowSizeThreshold(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoadBalanceStrategy(String strategy) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalSocketAddress(String address) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocatorFetchBufferSize(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogSlowQueries(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogXaCommands(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogger(String property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoggerClassName(String className) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaintainTimeStats(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxQuerySizeToLog(int sizeInBytes) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxReconnects(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxRows(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMetadataCacheSize(int value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNetTimeoutForStreamingResults(int value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAccessToProcedureBodies(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoDatetimeStringSync(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoTimezoneConversionForTimeType(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullCatalogMeansCurrent(boolean value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullNamePatternMatchesAll(boolean value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPacketDebugBufferSize(int size) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadCharsWithSpace(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParanoid(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPedantic(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPinGlobalTxToPhysicalConnection(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPopulateInsertRowWithDefaultValues(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSize(int cacheSize) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSqlLimit(int sqlLimit) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSize(int cacheSize) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProcessEscapeCodesForPrepStmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSQL(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSql(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfilerEventHandler(String handler) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertiesTransform(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueriesBeforeRetryMaster(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReconnectAtTxEnd(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRelaxAutoCommit(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReportMetricsIntervalMillis(int millis) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRequireSSL(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResourceId(String resourceId) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResultSetSizeThreshold(int threshold) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRetainStatementAfterResultSetClose(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRewriteBatchedStatements(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollbackOnPooledClose(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRoundRobinLoadBalance(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRunningCTS13(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecondsBeforeRetryMaster(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelfDestructOnPingMaxOperations(int maxOperations) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelfDestructOnPingSecondsLifetime(int seconds) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerTimezone(String property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSessionVariables(String variables) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlowQueryThresholdMillis(int millis) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlowQueryThresholdNanos(long nanos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketFactory(String name) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketFactoryClassName(String property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketTimeout(int property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStatementInterceptors(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictFloatingPoint(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictUpdates(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpKeepAlive(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpNoDelay(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpRcvBuf(int bufSize) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpSndBuf(int bufSize) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTcpTrafficClass(int classFlags) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTinyInt1isBit(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTraceProtocol(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransformedBitIsBoolean(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTreatUtilDateAsTimestamp(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStorePassword(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreType(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreUrl(String value) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUltraDevHack(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseCompression(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseConfigs(String configs) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseCursorFetch(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseDirectRowUnpack(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseDynamicCharsetInfo(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseFastDateParsing(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseFastIntParsing(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseGmtMillisForDatetimes(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseHostsInPrivileges(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseInformationSchema(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJDBCCompliantTimezoneShift(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJvmCharsetConverters(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseLegacyDatetimeCode(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseLocalSessionState(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseNanosForElapsedTime(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOldAliasMetadataBehavior(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOldUTF8Behavior(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOnlyServerErrorMessages(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseReadAheadInput(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSSL(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseServerPrepStmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseServerPreparedStmts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSqlStateCodes(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseStreamLengthsInPrepStmts(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseTimezone(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUltraDevWorkAround(boolean property) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUnbufferedInput(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUnicode(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerifyServerCertificate(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setYearIsDateType(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZeroDateTimeBehavior(String behavior) throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2329 */   public boolean useUnbufferedInput() throws SQLException { return this.currentConnection.useUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */   
/* 2333 */   public boolean isSameResource(Connection c) { return this.currentConnection.isSameResource(c); }
/*      */ 
/*      */ 
/*      */   
/* 2337 */   public void setInGlobalTx(boolean flag) throws SQLException { this.currentConnection.setInGlobalTx(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2341 */   public boolean getUseColumnNamesInFindColumn() throws SQLException { return this.currentConnection.getUseColumnNamesInFindColumn(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseColumnNamesInFindColumn(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/* 2349 */   public boolean getUseLocalTransactionState() throws SQLException { return this.currentConnection.getUseLocalTransactionState(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseLocalTransactionState(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/* 2358 */   public boolean getCompensateOnDuplicateKeyUpdateCounts() throws SQLException { return this.currentConnection.getCompensateOnDuplicateKeyUpdateCounts(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/* 2367 */   public boolean getUseAffectedRows() throws SQLException { return this.currentConnection.getUseAffectedRows(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseAffectedRows(boolean flag) throws SQLException {}
/*      */ 
/*      */ 
/*      */   
/* 2376 */   public String getPasswordCharacterEncoding() throws SQLException { return this.currentConnection.getPasswordCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/* 2380 */   public void setPasswordCharacterEncoding(String characterSet) throws SQLException { this.currentConnection.setPasswordCharacterEncoding(characterSet); }
/*      */ 
/*      */ 
/*      */   
/* 2384 */   public int getAutoIncrementIncrement() throws SQLException { return this.currentConnection.getAutoIncrementIncrement(); }
/*      */ 
/*      */ 
/*      */   
/* 2388 */   public int getLoadBalanceBlacklistTimeout() throws SQLException { return this.currentConnection.getLoadBalanceBlacklistTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 2392 */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) throws SQLException { this.currentConnection.setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout); }
/*      */ 
/*      */ 
/*      */   
/* 2396 */   public int getLoadBalancePingTimeout() throws SQLException { return this.currentConnection.getLoadBalancePingTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 2400 */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) throws SQLException { this.currentConnection.setLoadBalancePingTimeout(loadBalancePingTimeout); }
/*      */ 
/*      */ 
/*      */   
/* 2404 */   public boolean getLoadBalanceValidateConnectionOnSwapServer() throws SQLException { return this.currentConnection.getLoadBalanceValidateConnectionOnSwapServer(); }
/*      */ 
/*      */ 
/*      */   
/* 2408 */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) throws SQLException { this.currentConnection.setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer); }
/*      */ 
/*      */ 
/*      */   
/* 2412 */   public int getRetriesAllDown() throws SQLException { return this.currentConnection.getRetriesAllDown(); }
/*      */ 
/*      */ 
/*      */   
/* 2416 */   public void setRetriesAllDown(int retriesAllDown) throws SQLException { this.currentConnection.setRetriesAllDown(retriesAllDown); }
/*      */ 
/*      */ 
/*      */   
/* 2420 */   public ExceptionInterceptor getExceptionInterceptor() { return this.currentConnection.getExceptionInterceptor(); }
/*      */ 
/*      */ 
/*      */   
/* 2424 */   public String getExceptionInterceptors() throws SQLException { return this.currentConnection.getExceptionInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 2428 */   public void setExceptionInterceptors(String exceptionInterceptors) throws SQLException { this.currentConnection.setExceptionInterceptors(exceptionInterceptors); }
/*      */ 
/*      */ 
/*      */   
/* 2432 */   public boolean getQueryTimeoutKillsConnection() throws SQLException { return this.currentConnection.getQueryTimeoutKillsConnection(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2437 */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) throws SQLException { this.currentConnection.setQueryTimeoutKillsConnection(queryTimeoutKillsConnection); }
/*      */ 
/*      */ 
/*      */   
/* 2441 */   public boolean hasSameProperties(Connection c) { return (this.masterConnection.hasSameProperties(c) && this.slavesConnection.hasSameProperties(c)); }
/*      */ 
/*      */ 
/*      */   
/*      */   public Properties getProperties() {
/* 2446 */     Properties props = new Properties();
/* 2447 */     props.putAll(this.masterConnection.getProperties());
/* 2448 */     props.putAll(this.slavesConnection.getProperties());
/*      */     
/* 2450 */     return props;
/*      */   }
/*      */ 
/*      */   
/* 2454 */   public String getHost() throws SQLException { return this.currentConnection.getHost(); }
/*      */ 
/*      */ 
/*      */   
/* 2458 */   public void setProxy(MySQLConnection proxy) { this.currentConnection.setProxy(proxy); }
/*      */ 
/*      */ 
/*      */   
/* 2462 */   public boolean getRetainStatementAfterResultSetClose() throws SQLException { return this.currentConnection.getRetainStatementAfterResultSetClose(); }
/*      */ 
/*      */ 
/*      */   
/* 2466 */   public int getMaxAllowedPacket() throws SQLException { return this.currentConnection.getMaxAllowedPacket(); }
/*      */ 
/*      */ 
/*      */   
/* 2470 */   public String getLoadBalanceConnectionGroup() throws SQLException { return this.currentConnection.getLoadBalanceConnectionGroup(); }
/*      */ 
/*      */ 
/*      */   
/* 2474 */   public boolean getLoadBalanceEnableJMX() throws SQLException { return this.currentConnection.getLoadBalanceEnableJMX(); }
/*      */ 
/*      */ 
/*      */   
/* 2478 */   public String getLoadBalanceExceptionChecker() throws SQLException { return this.currentConnection.getLoadBalanceExceptionChecker(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2483 */   public String getLoadBalanceSQLExceptionSubclassFailover() throws SQLException { return this.currentConnection.getLoadBalanceSQLExceptionSubclassFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2488 */   public String getLoadBalanceSQLStateFailover() throws SQLException { return this.currentConnection.getLoadBalanceSQLStateFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2493 */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) throws SQLException { this.currentConnection.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2499 */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) throws SQLException { this.currentConnection.setLoadBalanceEnableJMX(loadBalanceEnableJMX); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2506 */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) throws SQLException { this.currentConnection.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2513 */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) throws SQLException { this.currentConnection.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2520 */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) throws SQLException { this.currentConnection.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2526 */   public String getLoadBalanceAutoCommitStatementRegex() throws SQLException { return this.currentConnection.getLoadBalanceAutoCommitStatementRegex(); }
/*      */ 
/*      */ 
/*      */   
/* 2530 */   public int getLoadBalanceAutoCommitStatementThreshold() throws SQLException { return this.currentConnection.getLoadBalanceAutoCommitStatementThreshold(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2535 */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) throws SQLException { this.currentConnection.setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2541 */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) throws SQLException { this.currentConnection.setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ReplicationConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */