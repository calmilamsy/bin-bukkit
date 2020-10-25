/*      */ package com.mysql.jdbc.jdbc2.optional;
/*      */ 
/*      */ import com.mysql.jdbc.Connection;
/*      */ import com.mysql.jdbc.ExceptionInterceptor;
/*      */ import com.mysql.jdbc.Extension;
/*      */ import com.mysql.jdbc.MySQLConnection;
/*      */ import com.mysql.jdbc.SQLError;
/*      */ import com.mysql.jdbc.Util;
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import java.lang.reflect.Constructor;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectionWrapper
/*      */   extends WrapperBase
/*      */   implements Connection
/*      */ {
/*   68 */   protected Connection mc = null;
/*      */   
/*   70 */   private String invalidHandleStr = "Logical handle no longer valid";
/*      */   
/*      */   private boolean closed;
/*      */   
/*      */   private boolean isForXa;
/*      */   
/*      */   private static final Constructor JDBC_4_CONNECTION_WRAPPER_CTOR;
/*      */   
/*      */   static  {
/*   79 */     if (Util.isJdbc4()) {
/*      */       try {
/*   81 */         JDBC_4_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper").getConstructor(new Class[] { MysqlPooledConnection.class, Connection.class, boolean.class });
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*   86 */       catch (SecurityException e) {
/*   87 */         throw new RuntimeException(e);
/*   88 */       } catch (NoSuchMethodException e) {
/*   89 */         throw new RuntimeException(e);
/*   90 */       } catch (ClassNotFoundException e) {
/*   91 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } else {
/*   94 */       JDBC_4_CONNECTION_WRAPPER_CTOR = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ConnectionWrapper getInstance(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException {
/*  101 */     if (!Util.isJdbc4()) {
/*  102 */       return new ConnectionWrapper(mysqlPooledConnection, mysqlConnection, forXa);
/*      */     }
/*      */ 
/*      */     
/*  106 */     return (ConnectionWrapper)Util.handleNewInstance(JDBC_4_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlPooledConnection, mysqlConnection, Boolean.valueOf(forXa) }, mysqlPooledConnection.getExceptionInterceptor());
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
/*      */   public ConnectionWrapper(MysqlPooledConnection mysqlPooledConnection, Connection mysqlConnection, boolean forXa) throws SQLException {
/*  125 */     super(mysqlPooledConnection);
/*      */     
/*  127 */     this.mc = mysqlConnection;
/*  128 */     this.closed = false;
/*  129 */     this.isForXa = forXa;
/*      */     
/*  131 */     if (this.isForXa) {
/*  132 */       setInGlobalTx(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoCommit(boolean autoCommit) throws SQLException {
/*  143 */     checkClosed();
/*      */     
/*  145 */     if (autoCommit && isInGlobalTx()) {
/*  146 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  153 */       this.mc.setAutoCommit(autoCommit);
/*  154 */     } catch (SQLException sqlException) {
/*  155 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoCommit() throws SQLException {
/*  166 */     checkClosed();
/*      */     
/*      */     try {
/*  169 */       return this.mc.getAutoCommit();
/*  170 */     } catch (SQLException sqlException) {
/*  171 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  174 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCatalog(String catalog) throws SQLException {
/*  184 */     checkClosed();
/*      */     
/*      */     try {
/*  187 */       this.mc.setCatalog(catalog);
/*  188 */     } catch (SQLException sqlException) {
/*  189 */       checkAndFireConnectionError(sqlException);
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
/*      */   public String getCatalog() throws SQLException {
/*  203 */     checkClosed();
/*      */     
/*      */     try {
/*  206 */       return this.mc.getCatalog();
/*  207 */     } catch (SQLException sqlException) {
/*  208 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  211 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   public boolean isClosed() throws SQLException { return (this.closed || this.mc.isClosed()); }
/*      */ 
/*      */ 
/*      */   
/*  225 */   public boolean isMasterConnection() throws SQLException { return this.mc.isMasterConnection(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHoldability(int arg0) throws SQLException {
/*  232 */     checkClosed();
/*      */     
/*      */     try {
/*  235 */       this.mc.setHoldability(arg0);
/*  236 */     } catch (SQLException sqlException) {
/*  237 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHoldability() throws SQLException {
/*  245 */     checkClosed();
/*      */     
/*      */     try {
/*  248 */       return this.mc.getHoldability();
/*  249 */     } catch (SQLException sqlException) {
/*  250 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  253 */       return 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  263 */   public long getIdleFor() { return this.mc.getIdleFor(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DatabaseMetaData getMetaData() throws SQLException {
/*  276 */     checkClosed();
/*      */     
/*      */     try {
/*  279 */       return this.mc.getMetaData();
/*  280 */     } catch (SQLException sqlException) {
/*  281 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  284 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean readOnly) throws SQLException {
/*  294 */     checkClosed();
/*      */     
/*      */     try {
/*  297 */       this.mc.setReadOnly(readOnly);
/*  298 */     } catch (SQLException sqlException) {
/*  299 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() throws SQLException {
/*  310 */     checkClosed();
/*      */     
/*      */     try {
/*  313 */       return this.mc.isReadOnly();
/*  314 */     } catch (SQLException sqlException) {
/*  315 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  318 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint() throws SQLException {
/*  325 */     checkClosed();
/*      */     
/*  327 */     if (isInGlobalTx()) {
/*  328 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  335 */       return this.mc.setSavepoint();
/*  336 */     } catch (SQLException sqlException) {
/*  337 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  340 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Savepoint setSavepoint(String arg0) throws SQLException {
/*  347 */     checkClosed();
/*      */     
/*  349 */     if (isInGlobalTx()) {
/*  350 */       throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  357 */       return this.mc.setSavepoint(arg0);
/*  358 */     } catch (SQLException sqlException) {
/*  359 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  362 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionIsolation(int level) throws SQLException {
/*  372 */     checkClosed();
/*      */     
/*      */     try {
/*  375 */       this.mc.setTransactionIsolation(level);
/*  376 */     } catch (SQLException sqlException) {
/*  377 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransactionIsolation() throws SQLException {
/*  388 */     checkClosed();
/*      */     
/*      */     try {
/*  391 */       return this.mc.getTransactionIsolation();
/*  392 */     } catch (SQLException sqlException) {
/*  393 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  396 */       return 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTypeMap(Map map) throws SQLException {
/*  407 */     checkClosed();
/*      */     
/*      */     try {
/*  410 */       this.mc.setTypeMap(map);
/*  411 */     } catch (SQLException sqlException) {
/*  412 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getTypeMap() throws SQLException {
/*  423 */     checkClosed();
/*      */     
/*      */     try {
/*  426 */       return this.mc.getTypeMap();
/*  427 */     } catch (SQLException sqlException) {
/*  428 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  431 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/*  441 */     checkClosed();
/*      */     
/*      */     try {
/*  444 */       return this.mc.getWarnings();
/*  445 */     } catch (SQLException sqlException) {
/*  446 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  449 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWarnings() throws SQLException {
/*  460 */     checkClosed();
/*      */     
/*      */     try {
/*  463 */       this.mc.clearWarnings();
/*  464 */     } catch (SQLException sqlException) {
/*  465 */       checkAndFireConnectionError(sqlException);
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
/*  480 */   public void close() throws SQLException { close(true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void commit() throws SQLException {
/*  491 */     checkClosed();
/*      */     
/*  493 */     if (isInGlobalTx()) {
/*  494 */       throw SQLError.createSQLException("Can't call commit() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  502 */       this.mc.commit();
/*  503 */     } catch (SQLException sqlException) {
/*  504 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement() throws SQLException {
/*  515 */     checkClosed();
/*      */     
/*      */     try {
/*  518 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement());
/*      */     }
/*  520 */     catch (SQLException sqlException) {
/*  521 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  524 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
/*  535 */     checkClosed();
/*      */     
/*      */     try {
/*  538 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(resultSetType, resultSetConcurrency));
/*      */     }
/*  540 */     catch (SQLException sqlException) {
/*  541 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  544 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
/*  552 */     checkClosed();
/*      */     
/*      */     try {
/*  555 */       return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(arg0, arg1, arg2));
/*      */     }
/*  557 */     catch (SQLException sqlException) {
/*  558 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  561 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String nativeSQL(String sql) throws SQLException {
/*  571 */     checkClosed();
/*      */     
/*      */     try {
/*  574 */       return this.mc.nativeSQL(sql);
/*  575 */     } catch (SQLException sqlException) {
/*  576 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  579 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql) throws SQLException {
/*  590 */     checkClosed();
/*      */     
/*      */     try {
/*  593 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql));
/*      */     }
/*  595 */     catch (SQLException sqlException) {
/*  596 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  599 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  610 */     checkClosed();
/*      */     
/*      */     try {
/*  613 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql, resultSetType, resultSetConcurrency));
/*      */     }
/*  615 */     catch (SQLException sqlException) {
/*  616 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  619 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
/*  627 */     checkClosed();
/*      */     
/*      */     try {
/*  630 */       return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(arg0, arg1, arg2, arg3));
/*      */     }
/*  632 */     catch (SQLException sqlException) {
/*  633 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  636 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepare(String sql) throws SQLException {
/*  641 */     checkClosed();
/*      */     
/*      */     try {
/*  644 */       return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
/*      */     }
/*  646 */     catch (SQLException sqlException) {
/*  647 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  650 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepare(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  655 */     checkClosed();
/*      */     
/*      */     try {
/*  658 */       return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  661 */     catch (SQLException sqlException) {
/*  662 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  665 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql) throws SQLException {
/*  676 */     checkClosed();
/*      */     
/*      */     try {
/*  679 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql));
/*      */     }
/*  681 */     catch (SQLException sqlException) {
/*  682 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  685 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*  696 */     checkClosed();
/*      */     
/*      */     try {
/*  699 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  702 */     catch (SQLException sqlException) {
/*  703 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  706 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
/*  714 */     checkClosed();
/*      */     
/*      */     try {
/*  717 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1, arg2, arg3));
/*      */     }
/*  719 */     catch (SQLException sqlException) {
/*  720 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  723 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
/*  731 */     checkClosed();
/*      */     
/*      */     try {
/*  734 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  736 */     catch (SQLException sqlException) {
/*  737 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  740 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
/*  748 */     checkClosed();
/*      */     
/*      */     try {
/*  751 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  753 */     catch (SQLException sqlException) {
/*  754 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  757 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
/*  765 */     checkClosed();
/*      */     
/*      */     try {
/*  768 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
/*      */     }
/*  770 */     catch (SQLException sqlException) {
/*  771 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  774 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void releaseSavepoint(Savepoint arg0) throws SQLException {
/*  781 */     checkClosed();
/*      */     
/*      */     try {
/*  784 */       this.mc.releaseSavepoint(arg0);
/*  785 */     } catch (SQLException sqlException) {
/*  786 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback() throws SQLException {
/*  797 */     checkClosed();
/*      */     
/*  799 */     if (isInGlobalTx()) {
/*  800 */       throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  808 */       this.mc.rollback();
/*  809 */     } catch (SQLException sqlException) {
/*  810 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rollback(Savepoint arg0) throws SQLException {
/*  818 */     checkClosed();
/*      */     
/*  820 */     if (isInGlobalTx()) {
/*  821 */       throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  829 */       this.mc.rollback(arg0);
/*  830 */     } catch (SQLException sqlException) {
/*  831 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isSameResource(Connection c) {
/*  836 */     if (c instanceof ConnectionWrapper)
/*  837 */       return this.mc.isSameResource(((ConnectionWrapper)c).mc); 
/*  838 */     if (c instanceof Connection) {
/*  839 */       return this.mc.isSameResource(c);
/*      */     }
/*      */     
/*  842 */     return false;
/*      */   }
/*      */   
/*      */   protected void close(boolean fireClosedEvent) throws SQLException {
/*  846 */     synchronized (this.pooledConnection) {
/*  847 */       if (this.closed) {
/*      */         return;
/*      */       }
/*      */       
/*  851 */       if (!isInGlobalTx() && this.mc.getRollbackOnPooledClose() && !getAutoCommit())
/*      */       {
/*  853 */         rollback();
/*      */       }
/*      */       
/*  856 */       if (fireClosedEvent) {
/*  857 */         this.pooledConnection.callConnectionEventListeners(2, null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  866 */       this.closed = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void checkClosed() throws SQLException {
/*  871 */     if (this.closed) {
/*  872 */       throw SQLError.createSQLException(this.invalidHandleStr, this.exceptionInterceptor);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  877 */   public boolean isInGlobalTx() throws SQLException { return this.mc.isInGlobalTx(); }
/*      */ 
/*      */ 
/*      */   
/*  881 */   public void setInGlobalTx(boolean flag) throws SQLException { this.mc.setInGlobalTx(flag); }
/*      */ 
/*      */   
/*      */   public void ping() throws SQLException {
/*  885 */     if (this.mc != null) {
/*  886 */       this.mc.ping();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void changeUser(String userName, String newPassword) throws SQLException {
/*  892 */     checkClosed();
/*      */     
/*      */     try {
/*  895 */       this.mc.changeUser(userName, newPassword);
/*  896 */     } catch (SQLException sqlException) {
/*  897 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  902 */   public void clearHasTriedMaster() throws SQLException { this.mc.clearHasTriedMaster(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
/*  907 */     checkClosed();
/*      */     
/*      */     try {
/*  910 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
/*      */     }
/*  912 */     catch (SQLException sqlException) {
/*  913 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  916 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*      */     try {
/*  922 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndex));
/*      */     }
/*  924 */     catch (SQLException sqlException) {
/*  925 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  928 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*      */     try {
/*  934 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/*  937 */     catch (SQLException sqlException) {
/*  938 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  941 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*      */     try {
/*  948 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
/*      */     
/*      */     }
/*  951 */     catch (SQLException sqlException) {
/*  952 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  955 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*      */     try {
/*  961 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndexes));
/*      */     }
/*  963 */     catch (SQLException sqlException) {
/*  964 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  967 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*      */     try {
/*  973 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyColNames));
/*      */     }
/*  975 */     catch (SQLException sqlException) {
/*  976 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/*  979 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*  983 */   public int getActiveStatementCount() throws SQLException { return this.mc.getActiveStatementCount(); }
/*      */ 
/*      */ 
/*      */   
/*  987 */   public Log getLog() throws SQLException { return this.mc.getLog(); }
/*      */ 
/*      */ 
/*      */   
/*  991 */   public String getServerCharacterEncoding() throws SQLException { return this.mc.getServerCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/*  995 */   public TimeZone getServerTimezoneTZ() { return this.mc.getServerTimezoneTZ(); }
/*      */ 
/*      */ 
/*      */   
/*  999 */   public String getStatementComment() throws SQLException { return this.mc.getStatementComment(); }
/*      */ 
/*      */ 
/*      */   
/* 1003 */   public boolean hasTriedMaster() throws SQLException { return this.mc.hasTriedMaster(); }
/*      */ 
/*      */ 
/*      */   
/* 1007 */   public boolean isAbonormallyLongQuery(long millisOrNanos) { return this.mc.isAbonormallyLongQuery(millisOrNanos); }
/*      */ 
/*      */ 
/*      */   
/* 1011 */   public boolean isNoBackslashEscapesSet() throws SQLException { return this.mc.isNoBackslashEscapesSet(); }
/*      */ 
/*      */ 
/*      */   
/* 1015 */   public boolean lowerCaseTableNames() throws SQLException { return this.mc.lowerCaseTableNames(); }
/*      */ 
/*      */ 
/*      */   
/* 1019 */   public boolean parserKnowsUnicode() throws SQLException { return this.mc.parserKnowsUnicode(); }
/*      */ 
/*      */ 
/*      */   
/* 1023 */   public void reportQueryTime(long millisOrNanos) { this.mc.reportQueryTime(millisOrNanos); }
/*      */ 
/*      */   
/*      */   public void resetServerState() throws SQLException {
/* 1027 */     checkClosed();
/*      */     
/*      */     try {
/* 1030 */       this.mc.resetServerState();
/* 1031 */     } catch (SQLException sqlException) {
/* 1032 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
/* 1038 */     checkClosed();
/*      */     
/*      */     try {
/* 1041 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql));
/*      */     }
/* 1043 */     catch (SQLException sqlException) {
/* 1044 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1047 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
/*      */     try {
/* 1053 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndex));
/*      */     }
/* 1055 */     catch (SQLException sqlException) {
/* 1056 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1059 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
/*      */     try {
/* 1065 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency));
/*      */     
/*      */     }
/* 1068 */     catch (SQLException sqlException) {
/* 1069 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1072 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
/*      */     try {
/* 1079 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
/*      */     
/*      */     }
/* 1082 */     catch (SQLException sqlException) {
/* 1083 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1086 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
/*      */     try {
/* 1092 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndexes));
/*      */     }
/* 1094 */     catch (SQLException sqlException) {
/* 1095 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1098 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
/*      */     try {
/* 1104 */       return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyColNames));
/*      */     }
/* 1106 */     catch (SQLException sqlException) {
/* 1107 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1110 */       return null;
/*      */     } 
/*      */   }
/*      */   
/* 1114 */   public void setFailedOver(boolean flag) throws SQLException { this.mc.setFailedOver(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1119 */   public void setPreferSlaveDuringFailover(boolean flag) throws SQLException { this.mc.setPreferSlaveDuringFailover(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1123 */   public void setStatementComment(String comment) throws SQLException { this.mc.setStatementComment(comment); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownServer() throws SQLException {
/* 1128 */     checkClosed();
/*      */     
/*      */     try {
/* 1131 */       this.mc.shutdownServer();
/* 1132 */     } catch (SQLException sqlException) {
/* 1133 */       checkAndFireConnectionError(sqlException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1139 */   public boolean supportsIsolationLevel() throws SQLException { return this.mc.supportsIsolationLevel(); }
/*      */ 
/*      */ 
/*      */   
/* 1143 */   public boolean supportsQuotedIdentifiers() throws SQLException { return this.mc.supportsQuotedIdentifiers(); }
/*      */ 
/*      */ 
/*      */   
/* 1147 */   public boolean supportsTransactions() throws SQLException { return this.mc.supportsTransactions(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
/* 1152 */     checkClosed();
/*      */     
/*      */     try {
/* 1155 */       return this.mc.versionMeetsMinimum(major, minor, subminor);
/* 1156 */     } catch (SQLException sqlException) {
/* 1157 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1160 */       return false;
/*      */     } 
/*      */   }
/*      */   public String exposeAsXml() throws SQLException {
/* 1164 */     checkClosed();
/*      */     
/*      */     try {
/* 1167 */       return this.mc.exposeAsXml();
/* 1168 */     } catch (SQLException sqlException) {
/* 1169 */       checkAndFireConnectionError(sqlException);
/*      */ 
/*      */       
/* 1172 */       return null;
/*      */     } 
/*      */   }
/*      */   
/* 1176 */   public boolean getAllowLoadLocalInfile() throws SQLException { return this.mc.getAllowLoadLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/* 1180 */   public boolean getAllowMultiQueries() throws SQLException { return this.mc.getAllowMultiQueries(); }
/*      */ 
/*      */ 
/*      */   
/* 1184 */   public boolean getAllowNanAndInf() throws SQLException { return this.mc.getAllowNanAndInf(); }
/*      */ 
/*      */ 
/*      */   
/* 1188 */   public boolean getAllowUrlInLocalInfile() throws SQLException { return this.mc.getAllowUrlInLocalInfile(); }
/*      */ 
/*      */ 
/*      */   
/* 1192 */   public boolean getAlwaysSendSetIsolation() throws SQLException { return this.mc.getAlwaysSendSetIsolation(); }
/*      */ 
/*      */ 
/*      */   
/* 1196 */   public boolean getAutoClosePStmtStreams() throws SQLException { return this.mc.getAutoClosePStmtStreams(); }
/*      */ 
/*      */ 
/*      */   
/* 1200 */   public boolean getAutoDeserialize() throws SQLException { return this.mc.getAutoDeserialize(); }
/*      */ 
/*      */ 
/*      */   
/* 1204 */   public boolean getAutoGenerateTestcaseScript() throws SQLException { return this.mc.getAutoGenerateTestcaseScript(); }
/*      */ 
/*      */ 
/*      */   
/* 1208 */   public boolean getAutoReconnectForPools() throws SQLException { return this.mc.getAutoReconnectForPools(); }
/*      */ 
/*      */ 
/*      */   
/* 1212 */   public boolean getAutoSlowLog() throws SQLException { return this.mc.getAutoSlowLog(); }
/*      */ 
/*      */ 
/*      */   
/* 1216 */   public int getBlobSendChunkSize() throws SQLException { return this.mc.getBlobSendChunkSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1220 */   public boolean getBlobsAreStrings() throws SQLException { return this.mc.getBlobsAreStrings(); }
/*      */ 
/*      */ 
/*      */   
/* 1224 */   public boolean getCacheCallableStatements() throws SQLException { return this.mc.getCacheCallableStatements(); }
/*      */ 
/*      */ 
/*      */   
/* 1228 */   public boolean getCacheCallableStmts() throws SQLException { return this.mc.getCacheCallableStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1232 */   public boolean getCachePrepStmts() throws SQLException { return this.mc.getCachePrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1236 */   public boolean getCachePreparedStatements() throws SQLException { return this.mc.getCachePreparedStatements(); }
/*      */ 
/*      */ 
/*      */   
/* 1240 */   public boolean getCacheResultSetMetadata() throws SQLException { return this.mc.getCacheResultSetMetadata(); }
/*      */ 
/*      */ 
/*      */   
/* 1244 */   public boolean getCacheServerConfiguration() throws SQLException { return this.mc.getCacheServerConfiguration(); }
/*      */ 
/*      */ 
/*      */   
/* 1248 */   public int getCallableStatementCacheSize() throws SQLException { return this.mc.getCallableStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1252 */   public int getCallableStmtCacheSize() throws SQLException { return this.mc.getCallableStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1256 */   public boolean getCapitalizeTypeNames() throws SQLException { return this.mc.getCapitalizeTypeNames(); }
/*      */ 
/*      */ 
/*      */   
/* 1260 */   public String getCharacterSetResults() throws SQLException { return this.mc.getCharacterSetResults(); }
/*      */ 
/*      */ 
/*      */   
/* 1264 */   public String getClientCertificateKeyStorePassword() throws SQLException { return this.mc.getClientCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */   
/* 1268 */   public String getClientCertificateKeyStoreType() throws SQLException { return this.mc.getClientCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/* 1272 */   public String getClientCertificateKeyStoreUrl() throws SQLException { return this.mc.getClientCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/* 1276 */   public String getClientInfoProvider() throws SQLException { return this.mc.getClientInfoProvider(); }
/*      */ 
/*      */ 
/*      */   
/* 1280 */   public String getClobCharacterEncoding() throws SQLException { return this.mc.getClobCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/* 1284 */   public boolean getClobberStreamingResults() throws SQLException { return this.mc.getClobberStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/* 1288 */   public int getConnectTimeout() throws SQLException { return this.mc.getConnectTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 1292 */   public String getConnectionCollation() throws SQLException { return this.mc.getConnectionCollation(); }
/*      */ 
/*      */ 
/*      */   
/* 1296 */   public String getConnectionLifecycleInterceptors() throws SQLException { return this.mc.getConnectionLifecycleInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 1300 */   public boolean getContinueBatchOnError() throws SQLException { return this.mc.getContinueBatchOnError(); }
/*      */ 
/*      */ 
/*      */   
/* 1304 */   public boolean getCreateDatabaseIfNotExist() throws SQLException { return this.mc.getCreateDatabaseIfNotExist(); }
/*      */ 
/*      */ 
/*      */   
/* 1308 */   public int getDefaultFetchSize() throws SQLException { return this.mc.getDefaultFetchSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1312 */   public boolean getDontTrackOpenResources() throws SQLException { return this.mc.getDontTrackOpenResources(); }
/*      */ 
/*      */ 
/*      */   
/* 1316 */   public boolean getDumpMetadataOnColumnNotFound() throws SQLException { return this.mc.getDumpMetadataOnColumnNotFound(); }
/*      */ 
/*      */ 
/*      */   
/* 1320 */   public boolean getDumpQueriesOnException() throws SQLException { return this.mc.getDumpQueriesOnException(); }
/*      */ 
/*      */ 
/*      */   
/* 1324 */   public boolean getDynamicCalendars() throws SQLException { return this.mc.getDynamicCalendars(); }
/*      */ 
/*      */ 
/*      */   
/* 1328 */   public boolean getElideSetAutoCommits() throws SQLException { return this.mc.getElideSetAutoCommits(); }
/*      */ 
/*      */ 
/*      */   
/* 1332 */   public boolean getEmptyStringsConvertToZero() throws SQLException { return this.mc.getEmptyStringsConvertToZero(); }
/*      */ 
/*      */ 
/*      */   
/* 1336 */   public boolean getEmulateLocators() throws SQLException { return this.mc.getEmulateLocators(); }
/*      */ 
/*      */ 
/*      */   
/* 1340 */   public boolean getEmulateUnsupportedPstmts() throws SQLException { return this.mc.getEmulateUnsupportedPstmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1344 */   public boolean getEnablePacketDebug() throws SQLException { return this.mc.getEnablePacketDebug(); }
/*      */ 
/*      */ 
/*      */   
/* 1348 */   public boolean getEnableQueryTimeouts() throws SQLException { return this.mc.getEnableQueryTimeouts(); }
/*      */ 
/*      */ 
/*      */   
/* 1352 */   public String getEncoding() throws SQLException { return this.mc.getEncoding(); }
/*      */ 
/*      */ 
/*      */   
/* 1356 */   public boolean getExplainSlowQueries() throws SQLException { return this.mc.getExplainSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/* 1360 */   public boolean getFailOverReadOnly() throws SQLException { return this.mc.getFailOverReadOnly(); }
/*      */ 
/*      */ 
/*      */   
/* 1364 */   public boolean getFunctionsNeverReturnBlobs() throws SQLException { return this.mc.getFunctionsNeverReturnBlobs(); }
/*      */ 
/*      */ 
/*      */   
/* 1368 */   public boolean getGatherPerfMetrics() throws SQLException { return this.mc.getGatherPerfMetrics(); }
/*      */ 
/*      */ 
/*      */   
/* 1372 */   public boolean getGatherPerformanceMetrics() throws SQLException { return this.mc.getGatherPerformanceMetrics(); }
/*      */ 
/*      */ 
/*      */   
/* 1376 */   public boolean getGenerateSimpleParameterMetadata() throws SQLException { return this.mc.getGenerateSimpleParameterMetadata(); }
/*      */ 
/*      */ 
/*      */   
/* 1380 */   public boolean getHoldResultsOpenOverStatementClose() throws SQLException { return this.mc.getHoldResultsOpenOverStatementClose(); }
/*      */ 
/*      */ 
/*      */   
/* 1384 */   public boolean getIgnoreNonTxTables() throws SQLException { return this.mc.getIgnoreNonTxTables(); }
/*      */ 
/*      */ 
/*      */   
/* 1388 */   public boolean getIncludeInnodbStatusInDeadlockExceptions() throws SQLException { return this.mc.getIncludeInnodbStatusInDeadlockExceptions(); }
/*      */ 
/*      */ 
/*      */   
/* 1392 */   public int getInitialTimeout() throws SQLException { return this.mc.getInitialTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 1396 */   public boolean getInteractiveClient() throws SQLException { return this.mc.getInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/* 1400 */   public boolean getIsInteractiveClient() throws SQLException { return this.mc.getIsInteractiveClient(); }
/*      */ 
/*      */ 
/*      */   
/* 1404 */   public boolean getJdbcCompliantTruncation() throws SQLException { return this.mc.getJdbcCompliantTruncation(); }
/*      */ 
/*      */ 
/*      */   
/* 1408 */   public boolean getJdbcCompliantTruncationForReads() throws SQLException { return this.mc.getJdbcCompliantTruncationForReads(); }
/*      */ 
/*      */ 
/*      */   
/* 1412 */   public String getLargeRowSizeThreshold() throws SQLException { return this.mc.getLargeRowSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/* 1416 */   public String getLoadBalanceStrategy() throws SQLException { return this.mc.getLoadBalanceStrategy(); }
/*      */ 
/*      */ 
/*      */   
/* 1420 */   public String getLocalSocketAddress() throws SQLException { return this.mc.getLocalSocketAddress(); }
/*      */ 
/*      */ 
/*      */   
/* 1424 */   public int getLocatorFetchBufferSize() throws SQLException { return this.mc.getLocatorFetchBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1428 */   public boolean getLogSlowQueries() throws SQLException { return this.mc.getLogSlowQueries(); }
/*      */ 
/*      */ 
/*      */   
/* 1432 */   public boolean getLogXaCommands() throws SQLException { return this.mc.getLogXaCommands(); }
/*      */ 
/*      */ 
/*      */   
/* 1436 */   public String getLogger() throws SQLException { return this.mc.getLogger(); }
/*      */ 
/*      */ 
/*      */   
/* 1440 */   public String getLoggerClassName() throws SQLException { return this.mc.getLoggerClassName(); }
/*      */ 
/*      */ 
/*      */   
/* 1444 */   public boolean getMaintainTimeStats() throws SQLException { return this.mc.getMaintainTimeStats(); }
/*      */ 
/*      */ 
/*      */   
/* 1448 */   public int getMaxQuerySizeToLog() throws SQLException { return this.mc.getMaxQuerySizeToLog(); }
/*      */ 
/*      */ 
/*      */   
/* 1452 */   public int getMaxReconnects() throws SQLException { return this.mc.getMaxReconnects(); }
/*      */ 
/*      */ 
/*      */   
/* 1456 */   public int getMaxRows() throws SQLException { return this.mc.getMaxRows(); }
/*      */ 
/*      */ 
/*      */   
/* 1460 */   public int getMetadataCacheSize() throws SQLException { return this.mc.getMetadataCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1464 */   public int getNetTimeoutForStreamingResults() throws SQLException { return this.mc.getNetTimeoutForStreamingResults(); }
/*      */ 
/*      */ 
/*      */   
/* 1468 */   public boolean getNoAccessToProcedureBodies() throws SQLException { return this.mc.getNoAccessToProcedureBodies(); }
/*      */ 
/*      */ 
/*      */   
/* 1472 */   public boolean getNoDatetimeStringSync() throws SQLException { return this.mc.getNoDatetimeStringSync(); }
/*      */ 
/*      */ 
/*      */   
/* 1476 */   public boolean getNoTimezoneConversionForTimeType() throws SQLException { return this.mc.getNoTimezoneConversionForTimeType(); }
/*      */ 
/*      */ 
/*      */   
/* 1480 */   public boolean getNullCatalogMeansCurrent() throws SQLException { return this.mc.getNullCatalogMeansCurrent(); }
/*      */ 
/*      */ 
/*      */   
/* 1484 */   public boolean getNullNamePatternMatchesAll() throws SQLException { return this.mc.getNullNamePatternMatchesAll(); }
/*      */ 
/*      */ 
/*      */   
/* 1488 */   public boolean getOverrideSupportsIntegrityEnhancementFacility() throws SQLException { return this.mc.getOverrideSupportsIntegrityEnhancementFacility(); }
/*      */ 
/*      */ 
/*      */   
/* 1492 */   public int getPacketDebugBufferSize() throws SQLException { return this.mc.getPacketDebugBufferSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1496 */   public boolean getPadCharsWithSpace() throws SQLException { return this.mc.getPadCharsWithSpace(); }
/*      */ 
/*      */ 
/*      */   
/* 1500 */   public boolean getParanoid() throws SQLException { return this.mc.getParanoid(); }
/*      */ 
/*      */ 
/*      */   
/* 1504 */   public boolean getPedantic() throws SQLException { return this.mc.getPedantic(); }
/*      */ 
/*      */ 
/*      */   
/* 1508 */   public boolean getPinGlobalTxToPhysicalConnection() throws SQLException { return this.mc.getPinGlobalTxToPhysicalConnection(); }
/*      */ 
/*      */ 
/*      */   
/* 1512 */   public boolean getPopulateInsertRowWithDefaultValues() throws SQLException { return this.mc.getPopulateInsertRowWithDefaultValues(); }
/*      */ 
/*      */ 
/*      */   
/* 1516 */   public int getPrepStmtCacheSize() throws SQLException { return this.mc.getPrepStmtCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1520 */   public int getPrepStmtCacheSqlLimit() throws SQLException { return this.mc.getPrepStmtCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/* 1524 */   public int getPreparedStatementCacheSize() throws SQLException { return this.mc.getPreparedStatementCacheSize(); }
/*      */ 
/*      */ 
/*      */   
/* 1528 */   public int getPreparedStatementCacheSqlLimit() throws SQLException { return this.mc.getPreparedStatementCacheSqlLimit(); }
/*      */ 
/*      */ 
/*      */   
/* 1532 */   public boolean getProcessEscapeCodesForPrepStmts() throws SQLException { return this.mc.getProcessEscapeCodesForPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1536 */   public boolean getProfileSQL() throws SQLException { return this.mc.getProfileSQL(); }
/*      */ 
/*      */ 
/*      */   
/* 1540 */   public boolean getProfileSql() throws SQLException { return this.mc.getProfileSql(); }
/*      */ 
/*      */ 
/*      */   
/* 1544 */   public String getPropertiesTransform() throws SQLException { return this.mc.getPropertiesTransform(); }
/*      */ 
/*      */ 
/*      */   
/* 1548 */   public int getQueriesBeforeRetryMaster() throws SQLException { return this.mc.getQueriesBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/* 1552 */   public boolean getReconnectAtTxEnd() throws SQLException { return this.mc.getReconnectAtTxEnd(); }
/*      */ 
/*      */ 
/*      */   
/* 1556 */   public boolean getRelaxAutoCommit() throws SQLException { return this.mc.getRelaxAutoCommit(); }
/*      */ 
/*      */ 
/*      */   
/* 1560 */   public int getReportMetricsIntervalMillis() throws SQLException { return this.mc.getReportMetricsIntervalMillis(); }
/*      */ 
/*      */ 
/*      */   
/* 1564 */   public boolean getRequireSSL() throws SQLException { return this.mc.getRequireSSL(); }
/*      */ 
/*      */ 
/*      */   
/* 1568 */   public String getResourceId() throws SQLException { return this.mc.getResourceId(); }
/*      */ 
/*      */ 
/*      */   
/* 1572 */   public int getResultSetSizeThreshold() throws SQLException { return this.mc.getResultSetSizeThreshold(); }
/*      */ 
/*      */ 
/*      */   
/* 1576 */   public boolean getRewriteBatchedStatements() throws SQLException { return this.mc.getRewriteBatchedStatements(); }
/*      */ 
/*      */ 
/*      */   
/* 1580 */   public boolean getRollbackOnPooledClose() throws SQLException { return this.mc.getRollbackOnPooledClose(); }
/*      */ 
/*      */ 
/*      */   
/* 1584 */   public boolean getRoundRobinLoadBalance() throws SQLException { return this.mc.getRoundRobinLoadBalance(); }
/*      */ 
/*      */ 
/*      */   
/* 1588 */   public boolean getRunningCTS13() throws SQLException { return this.mc.getRunningCTS13(); }
/*      */ 
/*      */ 
/*      */   
/* 1592 */   public int getSecondsBeforeRetryMaster() throws SQLException { return this.mc.getSecondsBeforeRetryMaster(); }
/*      */ 
/*      */ 
/*      */   
/* 1596 */   public String getServerTimezone() throws SQLException { return this.mc.getServerTimezone(); }
/*      */ 
/*      */ 
/*      */   
/* 1600 */   public String getSessionVariables() throws SQLException { return this.mc.getSessionVariables(); }
/*      */ 
/*      */ 
/*      */   
/* 1604 */   public int getSlowQueryThresholdMillis() throws SQLException { return this.mc.getSlowQueryThresholdMillis(); }
/*      */ 
/*      */ 
/*      */   
/* 1608 */   public long getSlowQueryThresholdNanos() { return this.mc.getSlowQueryThresholdNanos(); }
/*      */ 
/*      */ 
/*      */   
/* 1612 */   public String getSocketFactory() throws SQLException { return this.mc.getSocketFactory(); }
/*      */ 
/*      */ 
/*      */   
/* 1616 */   public String getSocketFactoryClassName() throws SQLException { return this.mc.getSocketFactoryClassName(); }
/*      */ 
/*      */ 
/*      */   
/* 1620 */   public int getSocketTimeout() throws SQLException { return this.mc.getSocketTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 1624 */   public String getStatementInterceptors() throws SQLException { return this.mc.getStatementInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 1628 */   public boolean getStrictFloatingPoint() throws SQLException { return this.mc.getStrictFloatingPoint(); }
/*      */ 
/*      */ 
/*      */   
/* 1632 */   public boolean getStrictUpdates() throws SQLException { return this.mc.getStrictUpdates(); }
/*      */ 
/*      */ 
/*      */   
/* 1636 */   public boolean getTcpKeepAlive() throws SQLException { return this.mc.getTcpKeepAlive(); }
/*      */ 
/*      */ 
/*      */   
/* 1640 */   public boolean getTcpNoDelay() throws SQLException { return this.mc.getTcpNoDelay(); }
/*      */ 
/*      */ 
/*      */   
/* 1644 */   public int getTcpRcvBuf() throws SQLException { return this.mc.getTcpRcvBuf(); }
/*      */ 
/*      */ 
/*      */   
/* 1648 */   public int getTcpSndBuf() throws SQLException { return this.mc.getTcpSndBuf(); }
/*      */ 
/*      */ 
/*      */   
/* 1652 */   public int getTcpTrafficClass() throws SQLException { return this.mc.getTcpTrafficClass(); }
/*      */ 
/*      */ 
/*      */   
/* 1656 */   public boolean getTinyInt1isBit() throws SQLException { return this.mc.getTinyInt1isBit(); }
/*      */ 
/*      */ 
/*      */   
/* 1660 */   public boolean getTraceProtocol() throws SQLException { return this.mc.getTraceProtocol(); }
/*      */ 
/*      */ 
/*      */   
/* 1664 */   public boolean getTransformedBitIsBoolean() throws SQLException { return this.mc.getTransformedBitIsBoolean(); }
/*      */ 
/*      */ 
/*      */   
/* 1668 */   public boolean getTreatUtilDateAsTimestamp() throws SQLException { return this.mc.getTreatUtilDateAsTimestamp(); }
/*      */ 
/*      */ 
/*      */   
/* 1672 */   public String getTrustCertificateKeyStorePassword() throws SQLException { return this.mc.getTrustCertificateKeyStorePassword(); }
/*      */ 
/*      */ 
/*      */   
/* 1676 */   public String getTrustCertificateKeyStoreType() throws SQLException { return this.mc.getTrustCertificateKeyStoreType(); }
/*      */ 
/*      */ 
/*      */   
/* 1680 */   public String getTrustCertificateKeyStoreUrl() throws SQLException { return this.mc.getTrustCertificateKeyStoreUrl(); }
/*      */ 
/*      */ 
/*      */   
/* 1684 */   public boolean getUltraDevHack() throws SQLException { return this.mc.getUltraDevHack(); }
/*      */ 
/*      */ 
/*      */   
/* 1688 */   public boolean getUseBlobToStoreUTF8OutsideBMP() throws SQLException { return this.mc.getUseBlobToStoreUTF8OutsideBMP(); }
/*      */ 
/*      */ 
/*      */   
/* 1692 */   public boolean getUseCompression() throws SQLException { return this.mc.getUseCompression(); }
/*      */ 
/*      */ 
/*      */   
/* 1696 */   public String getUseConfigs() throws SQLException { return this.mc.getUseConfigs(); }
/*      */ 
/*      */ 
/*      */   
/* 1700 */   public boolean getUseCursorFetch() throws SQLException { return this.mc.getUseCursorFetch(); }
/*      */ 
/*      */ 
/*      */   
/* 1704 */   public boolean getUseDirectRowUnpack() throws SQLException { return this.mc.getUseDirectRowUnpack(); }
/*      */ 
/*      */ 
/*      */   
/* 1708 */   public boolean getUseDynamicCharsetInfo() throws SQLException { return this.mc.getUseDynamicCharsetInfo(); }
/*      */ 
/*      */ 
/*      */   
/* 1712 */   public boolean getUseFastDateParsing() throws SQLException { return this.mc.getUseFastDateParsing(); }
/*      */ 
/*      */ 
/*      */   
/* 1716 */   public boolean getUseFastIntParsing() throws SQLException { return this.mc.getUseFastIntParsing(); }
/*      */ 
/*      */ 
/*      */   
/* 1720 */   public boolean getUseGmtMillisForDatetimes() throws SQLException { return this.mc.getUseGmtMillisForDatetimes(); }
/*      */ 
/*      */ 
/*      */   
/* 1724 */   public boolean getUseHostsInPrivileges() throws SQLException { return this.mc.getUseHostsInPrivileges(); }
/*      */ 
/*      */ 
/*      */   
/* 1728 */   public boolean getUseInformationSchema() throws SQLException { return this.mc.getUseInformationSchema(); }
/*      */ 
/*      */ 
/*      */   
/* 1732 */   public boolean getUseJDBCCompliantTimezoneShift() throws SQLException { return this.mc.getUseJDBCCompliantTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/* 1736 */   public boolean getUseJvmCharsetConverters() throws SQLException { return this.mc.getUseJvmCharsetConverters(); }
/*      */ 
/*      */ 
/*      */   
/* 1740 */   public boolean getUseLocalSessionState() throws SQLException { return this.mc.getUseLocalSessionState(); }
/*      */ 
/*      */ 
/*      */   
/* 1744 */   public boolean getUseNanosForElapsedTime() throws SQLException { return this.mc.getUseNanosForElapsedTime(); }
/*      */ 
/*      */ 
/*      */   
/* 1748 */   public boolean getUseOldAliasMetadataBehavior() throws SQLException { return this.mc.getUseOldAliasMetadataBehavior(); }
/*      */ 
/*      */ 
/*      */   
/* 1752 */   public boolean getUseOldUTF8Behavior() throws SQLException { return this.mc.getUseOldUTF8Behavior(); }
/*      */ 
/*      */ 
/*      */   
/* 1756 */   public boolean getUseOnlyServerErrorMessages() throws SQLException { return this.mc.getUseOnlyServerErrorMessages(); }
/*      */ 
/*      */ 
/*      */   
/* 1760 */   public boolean getUseReadAheadInput() throws SQLException { return this.mc.getUseReadAheadInput(); }
/*      */ 
/*      */ 
/*      */   
/* 1764 */   public boolean getUseSSL() throws SQLException { return this.mc.getUseSSL(); }
/*      */ 
/*      */ 
/*      */   
/* 1768 */   public boolean getUseSSPSCompatibleTimezoneShift() throws SQLException { return this.mc.getUseSSPSCompatibleTimezoneShift(); }
/*      */ 
/*      */ 
/*      */   
/* 1772 */   public boolean getUseServerPrepStmts() throws SQLException { return this.mc.getUseServerPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1776 */   public boolean getUseServerPreparedStmts() throws SQLException { return this.mc.getUseServerPreparedStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1780 */   public boolean getUseSqlStateCodes() throws SQLException { return this.mc.getUseSqlStateCodes(); }
/*      */ 
/*      */ 
/*      */   
/* 1784 */   public boolean getUseStreamLengthsInPrepStmts() throws SQLException { return this.mc.getUseStreamLengthsInPrepStmts(); }
/*      */ 
/*      */ 
/*      */   
/* 1788 */   public boolean getUseTimezone() throws SQLException { return this.mc.getUseTimezone(); }
/*      */ 
/*      */ 
/*      */   
/* 1792 */   public boolean getUseUltraDevWorkAround() throws SQLException { return this.mc.getUseUltraDevWorkAround(); }
/*      */ 
/*      */ 
/*      */   
/* 1796 */   public boolean getUseUnbufferedInput() throws SQLException { return this.mc.getUseUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */   
/* 1800 */   public boolean getUseUnicode() throws SQLException { return this.mc.getUseUnicode(); }
/*      */ 
/*      */ 
/*      */   
/* 1804 */   public boolean getUseUsageAdvisor() throws SQLException { return this.mc.getUseUsageAdvisor(); }
/*      */ 
/*      */ 
/*      */   
/* 1808 */   public String getUtf8OutsideBmpExcludedColumnNamePattern() throws SQLException { return this.mc.getUtf8OutsideBmpExcludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */   
/* 1812 */   public String getUtf8OutsideBmpIncludedColumnNamePattern() throws SQLException { return this.mc.getUtf8OutsideBmpIncludedColumnNamePattern(); }
/*      */ 
/*      */ 
/*      */   
/* 1816 */   public boolean getYearIsDateType() throws SQLException { return this.mc.getYearIsDateType(); }
/*      */ 
/*      */ 
/*      */   
/* 1820 */   public String getZeroDateTimeBehavior() throws SQLException { return this.mc.getZeroDateTimeBehavior(); }
/*      */ 
/*      */ 
/*      */   
/* 1824 */   public void setAllowLoadLocalInfile(boolean property) throws SQLException { this.mc.setAllowLoadLocalInfile(property); }
/*      */ 
/*      */ 
/*      */   
/* 1828 */   public void setAllowMultiQueries(boolean property) throws SQLException { this.mc.setAllowMultiQueries(property); }
/*      */ 
/*      */ 
/*      */   
/* 1832 */   public void setAllowNanAndInf(boolean flag) throws SQLException { this.mc.setAllowNanAndInf(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1836 */   public void setAllowUrlInLocalInfile(boolean flag) throws SQLException { this.mc.setAllowUrlInLocalInfile(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1840 */   public void setAlwaysSendSetIsolation(boolean flag) throws SQLException { this.mc.setAlwaysSendSetIsolation(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1844 */   public void setAutoClosePStmtStreams(boolean flag) throws SQLException { this.mc.setAutoClosePStmtStreams(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1848 */   public void setAutoDeserialize(boolean flag) throws SQLException { this.mc.setAutoDeserialize(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1852 */   public void setAutoGenerateTestcaseScript(boolean flag) throws SQLException { this.mc.setAutoGenerateTestcaseScript(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1856 */   public void setAutoReconnect(boolean flag) throws SQLException { this.mc.setAutoReconnect(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1860 */   public void setAutoReconnectForConnectionPools(boolean property) throws SQLException { this.mc.setAutoReconnectForConnectionPools(property); }
/*      */ 
/*      */ 
/*      */   
/* 1864 */   public void setAutoReconnectForPools(boolean flag) throws SQLException { this.mc.setAutoReconnectForPools(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1868 */   public void setAutoSlowLog(boolean flag) throws SQLException { this.mc.setAutoSlowLog(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1872 */   public void setBlobSendChunkSize(String value) throws SQLException { this.mc.setBlobSendChunkSize(value); }
/*      */ 
/*      */ 
/*      */   
/* 1876 */   public void setBlobsAreStrings(boolean flag) throws SQLException { this.mc.setBlobsAreStrings(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1880 */   public void setCacheCallableStatements(boolean flag) throws SQLException { this.mc.setCacheCallableStatements(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1884 */   public void setCacheCallableStmts(boolean flag) throws SQLException { this.mc.setCacheCallableStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1888 */   public void setCachePrepStmts(boolean flag) throws SQLException { this.mc.setCachePrepStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1892 */   public void setCachePreparedStatements(boolean flag) throws SQLException { this.mc.setCachePreparedStatements(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1896 */   public void setCacheResultSetMetadata(boolean property) throws SQLException { this.mc.setCacheResultSetMetadata(property); }
/*      */ 
/*      */ 
/*      */   
/* 1900 */   public void setCacheServerConfiguration(boolean flag) throws SQLException { this.mc.setCacheServerConfiguration(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1904 */   public void setCallableStatementCacheSize(int size) throws SQLException { this.mc.setCallableStatementCacheSize(size); }
/*      */ 
/*      */ 
/*      */   
/* 1908 */   public void setCallableStmtCacheSize(int cacheSize) throws SQLException { this.mc.setCallableStmtCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */   
/* 1912 */   public void setCapitalizeDBMDTypes(boolean property) throws SQLException { this.mc.setCapitalizeDBMDTypes(property); }
/*      */ 
/*      */ 
/*      */   
/* 1916 */   public void setCapitalizeTypeNames(boolean flag) throws SQLException { this.mc.setCapitalizeTypeNames(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1920 */   public void setCharacterEncoding(String encoding) throws SQLException { this.mc.setCharacterEncoding(encoding); }
/*      */ 
/*      */ 
/*      */   
/* 1924 */   public void setCharacterSetResults(String characterSet) throws SQLException { this.mc.setCharacterSetResults(characterSet); }
/*      */ 
/*      */ 
/*      */   
/* 1928 */   public void setClientCertificateKeyStorePassword(String value) throws SQLException { this.mc.setClientCertificateKeyStorePassword(value); }
/*      */ 
/*      */ 
/*      */   
/* 1932 */   public void setClientCertificateKeyStoreType(String value) throws SQLException { this.mc.setClientCertificateKeyStoreType(value); }
/*      */ 
/*      */ 
/*      */   
/* 1936 */   public void setClientCertificateKeyStoreUrl(String value) throws SQLException { this.mc.setClientCertificateKeyStoreUrl(value); }
/*      */ 
/*      */ 
/*      */   
/* 1940 */   public void setClientInfoProvider(String classname) throws SQLException { this.mc.setClientInfoProvider(classname); }
/*      */ 
/*      */ 
/*      */   
/* 1944 */   public void setClobCharacterEncoding(String encoding) throws SQLException { this.mc.setClobCharacterEncoding(encoding); }
/*      */ 
/*      */ 
/*      */   
/* 1948 */   public void setClobberStreamingResults(boolean flag) throws SQLException { this.mc.setClobberStreamingResults(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1952 */   public void setConnectTimeout(int timeoutMs) throws SQLException { this.mc.setConnectTimeout(timeoutMs); }
/*      */ 
/*      */ 
/*      */   
/* 1956 */   public void setConnectionCollation(String collation) throws SQLException { this.mc.setConnectionCollation(collation); }
/*      */ 
/*      */ 
/*      */   
/* 1960 */   public void setConnectionLifecycleInterceptors(String interceptors) throws SQLException { this.mc.setConnectionLifecycleInterceptors(interceptors); }
/*      */ 
/*      */ 
/*      */   
/* 1964 */   public void setContinueBatchOnError(boolean property) throws SQLException { this.mc.setContinueBatchOnError(property); }
/*      */ 
/*      */ 
/*      */   
/* 1968 */   public void setCreateDatabaseIfNotExist(boolean flag) throws SQLException { this.mc.setCreateDatabaseIfNotExist(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1972 */   public void setDefaultFetchSize(int n) throws SQLException { this.mc.setDefaultFetchSize(n); }
/*      */ 
/*      */ 
/*      */   
/* 1976 */   public void setDetectServerPreparedStmts(boolean property) throws SQLException { this.mc.setDetectServerPreparedStmts(property); }
/*      */ 
/*      */ 
/*      */   
/* 1980 */   public void setDontTrackOpenResources(boolean flag) throws SQLException { this.mc.setDontTrackOpenResources(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1984 */   public void setDumpMetadataOnColumnNotFound(boolean flag) throws SQLException { this.mc.setDumpMetadataOnColumnNotFound(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1988 */   public void setDumpQueriesOnException(boolean flag) throws SQLException { this.mc.setDumpQueriesOnException(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1992 */   public void setDynamicCalendars(boolean flag) throws SQLException { this.mc.setDynamicCalendars(flag); }
/*      */ 
/*      */ 
/*      */   
/* 1996 */   public void setElideSetAutoCommits(boolean flag) throws SQLException { this.mc.setElideSetAutoCommits(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2000 */   public void setEmptyStringsConvertToZero(boolean flag) throws SQLException { this.mc.setEmptyStringsConvertToZero(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2004 */   public void setEmulateLocators(boolean property) throws SQLException { this.mc.setEmulateLocators(property); }
/*      */ 
/*      */ 
/*      */   
/* 2008 */   public void setEmulateUnsupportedPstmts(boolean flag) throws SQLException { this.mc.setEmulateUnsupportedPstmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2012 */   public void setEnablePacketDebug(boolean flag) throws SQLException { this.mc.setEnablePacketDebug(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2016 */   public void setEnableQueryTimeouts(boolean flag) throws SQLException { this.mc.setEnableQueryTimeouts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2020 */   public void setEncoding(String property) throws SQLException { this.mc.setEncoding(property); }
/*      */ 
/*      */ 
/*      */   
/* 2024 */   public void setExplainSlowQueries(boolean flag) throws SQLException { this.mc.setExplainSlowQueries(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2028 */   public void setFailOverReadOnly(boolean flag) throws SQLException { this.mc.setFailOverReadOnly(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2032 */   public void setFunctionsNeverReturnBlobs(boolean flag) throws SQLException { this.mc.setFunctionsNeverReturnBlobs(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2036 */   public void setGatherPerfMetrics(boolean flag) throws SQLException { this.mc.setGatherPerfMetrics(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2040 */   public void setGatherPerformanceMetrics(boolean flag) throws SQLException { this.mc.setGatherPerformanceMetrics(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2044 */   public void setGenerateSimpleParameterMetadata(boolean flag) throws SQLException { this.mc.setGenerateSimpleParameterMetadata(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2048 */   public void setHoldResultsOpenOverStatementClose(boolean flag) throws SQLException { this.mc.setHoldResultsOpenOverStatementClose(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2052 */   public void setIgnoreNonTxTables(boolean property) throws SQLException { this.mc.setIgnoreNonTxTables(property); }
/*      */ 
/*      */ 
/*      */   
/* 2056 */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) throws SQLException { this.mc.setIncludeInnodbStatusInDeadlockExceptions(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2060 */   public void setInitialTimeout(int property) throws SQLException { this.mc.setInitialTimeout(property); }
/*      */ 
/*      */ 
/*      */   
/* 2064 */   public void setInteractiveClient(boolean property) throws SQLException { this.mc.setInteractiveClient(property); }
/*      */ 
/*      */ 
/*      */   
/* 2068 */   public void setIsInteractiveClient(boolean property) throws SQLException { this.mc.setIsInteractiveClient(property); }
/*      */ 
/*      */ 
/*      */   
/* 2072 */   public void setJdbcCompliantTruncation(boolean flag) throws SQLException { this.mc.setJdbcCompliantTruncation(flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2077 */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) throws SQLException { this.mc.setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2082 */   public void setLargeRowSizeThreshold(String value) throws SQLException { this.mc.setLargeRowSizeThreshold(value); }
/*      */ 
/*      */ 
/*      */   
/* 2086 */   public void setLoadBalanceStrategy(String strategy) throws SQLException { this.mc.setLoadBalanceStrategy(strategy); }
/*      */ 
/*      */ 
/*      */   
/* 2090 */   public void setLocalSocketAddress(String address) throws SQLException { this.mc.setLocalSocketAddress(address); }
/*      */ 
/*      */ 
/*      */   
/* 2094 */   public void setLocatorFetchBufferSize(String value) throws SQLException { this.mc.setLocatorFetchBufferSize(value); }
/*      */ 
/*      */ 
/*      */   
/* 2098 */   public void setLogSlowQueries(boolean flag) throws SQLException { this.mc.setLogSlowQueries(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2102 */   public void setLogXaCommands(boolean flag) throws SQLException { this.mc.setLogXaCommands(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2106 */   public void setLogger(String property) throws SQLException { this.mc.setLogger(property); }
/*      */ 
/*      */ 
/*      */   
/* 2110 */   public void setLoggerClassName(String className) throws SQLException { this.mc.setLoggerClassName(className); }
/*      */ 
/*      */ 
/*      */   
/* 2114 */   public void setMaintainTimeStats(boolean flag) throws SQLException { this.mc.setMaintainTimeStats(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2118 */   public void setMaxQuerySizeToLog(int sizeInBytes) throws SQLException { this.mc.setMaxQuerySizeToLog(sizeInBytes); }
/*      */ 
/*      */ 
/*      */   
/* 2122 */   public void setMaxReconnects(int property) throws SQLException { this.mc.setMaxReconnects(property); }
/*      */ 
/*      */ 
/*      */   
/* 2126 */   public void setMaxRows(int property) throws SQLException { this.mc.setMaxRows(property); }
/*      */ 
/*      */ 
/*      */   
/* 2130 */   public void setMetadataCacheSize(int value) throws SQLException { this.mc.setMetadataCacheSize(value); }
/*      */ 
/*      */ 
/*      */   
/* 2134 */   public void setNetTimeoutForStreamingResults(int value) throws SQLException { this.mc.setNetTimeoutForStreamingResults(value); }
/*      */ 
/*      */ 
/*      */   
/* 2138 */   public void setNoAccessToProcedureBodies(boolean flag) throws SQLException { this.mc.setNoAccessToProcedureBodies(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2142 */   public void setNoDatetimeStringSync(boolean flag) throws SQLException { this.mc.setNoDatetimeStringSync(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2146 */   public void setNoTimezoneConversionForTimeType(boolean flag) throws SQLException { this.mc.setNoTimezoneConversionForTimeType(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2150 */   public void setNullCatalogMeansCurrent(boolean value) throws SQLException { this.mc.setNullCatalogMeansCurrent(value); }
/*      */ 
/*      */ 
/*      */   
/* 2154 */   public void setNullNamePatternMatchesAll(boolean value) throws SQLException { this.mc.setNullNamePatternMatchesAll(value); }
/*      */ 
/*      */ 
/*      */   
/* 2158 */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) throws SQLException { this.mc.setOverrideSupportsIntegrityEnhancementFacility(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2162 */   public void setPacketDebugBufferSize(int size) throws SQLException { this.mc.setPacketDebugBufferSize(size); }
/*      */ 
/*      */ 
/*      */   
/* 2166 */   public void setPadCharsWithSpace(boolean flag) throws SQLException { this.mc.setPadCharsWithSpace(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2170 */   public void setParanoid(boolean property) throws SQLException { this.mc.setParanoid(property); }
/*      */ 
/*      */ 
/*      */   
/* 2174 */   public void setPedantic(boolean property) throws SQLException { this.mc.setPedantic(property); }
/*      */ 
/*      */ 
/*      */   
/* 2178 */   public void setPinGlobalTxToPhysicalConnection(boolean flag) throws SQLException { this.mc.setPinGlobalTxToPhysicalConnection(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2182 */   public void setPopulateInsertRowWithDefaultValues(boolean flag) throws SQLException { this.mc.setPopulateInsertRowWithDefaultValues(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2186 */   public void setPrepStmtCacheSize(int cacheSize) throws SQLException { this.mc.setPrepStmtCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */   
/* 2190 */   public void setPrepStmtCacheSqlLimit(int sqlLimit) throws SQLException { this.mc.setPrepStmtCacheSqlLimit(sqlLimit); }
/*      */ 
/*      */ 
/*      */   
/* 2194 */   public void setPreparedStatementCacheSize(int cacheSize) throws SQLException { this.mc.setPreparedStatementCacheSize(cacheSize); }
/*      */ 
/*      */ 
/*      */   
/* 2198 */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) throws SQLException { this.mc.setPreparedStatementCacheSqlLimit(cacheSqlLimit); }
/*      */ 
/*      */ 
/*      */   
/* 2202 */   public void setProcessEscapeCodesForPrepStmts(boolean flag) throws SQLException { this.mc.setProcessEscapeCodesForPrepStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2206 */   public void setProfileSQL(boolean flag) throws SQLException { this.mc.setProfileSQL(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2210 */   public void setProfileSql(boolean property) throws SQLException { this.mc.setProfileSql(property); }
/*      */ 
/*      */ 
/*      */   
/* 2214 */   public void setPropertiesTransform(String value) throws SQLException { this.mc.setPropertiesTransform(value); }
/*      */ 
/*      */ 
/*      */   
/* 2218 */   public void setQueriesBeforeRetryMaster(int property) throws SQLException { this.mc.setQueriesBeforeRetryMaster(property); }
/*      */ 
/*      */ 
/*      */   
/* 2222 */   public void setReconnectAtTxEnd(boolean property) throws SQLException { this.mc.setReconnectAtTxEnd(property); }
/*      */ 
/*      */ 
/*      */   
/* 2226 */   public void setRelaxAutoCommit(boolean property) throws SQLException { this.mc.setRelaxAutoCommit(property); }
/*      */ 
/*      */ 
/*      */   
/* 2230 */   public void setReportMetricsIntervalMillis(int millis) throws SQLException { this.mc.setReportMetricsIntervalMillis(millis); }
/*      */ 
/*      */ 
/*      */   
/* 2234 */   public void setRequireSSL(boolean property) throws SQLException { this.mc.setRequireSSL(property); }
/*      */ 
/*      */ 
/*      */   
/* 2238 */   public void setResourceId(String resourceId) throws SQLException { this.mc.setResourceId(resourceId); }
/*      */ 
/*      */ 
/*      */   
/* 2242 */   public void setResultSetSizeThreshold(int threshold) throws SQLException { this.mc.setResultSetSizeThreshold(threshold); }
/*      */ 
/*      */ 
/*      */   
/* 2246 */   public void setRetainStatementAfterResultSetClose(boolean flag) throws SQLException { this.mc.setRetainStatementAfterResultSetClose(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2250 */   public void setRewriteBatchedStatements(boolean flag) throws SQLException { this.mc.setRewriteBatchedStatements(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2254 */   public void setRollbackOnPooledClose(boolean flag) throws SQLException { this.mc.setRollbackOnPooledClose(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2258 */   public void setRoundRobinLoadBalance(boolean flag) throws SQLException { this.mc.setRoundRobinLoadBalance(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2262 */   public void setRunningCTS13(boolean flag) throws SQLException { this.mc.setRunningCTS13(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2266 */   public void setSecondsBeforeRetryMaster(int property) throws SQLException { this.mc.setSecondsBeforeRetryMaster(property); }
/*      */ 
/*      */ 
/*      */   
/* 2270 */   public void setServerTimezone(String property) throws SQLException { this.mc.setServerTimezone(property); }
/*      */ 
/*      */ 
/*      */   
/* 2274 */   public void setSessionVariables(String variables) throws SQLException { this.mc.setSessionVariables(variables); }
/*      */ 
/*      */ 
/*      */   
/* 2278 */   public void setSlowQueryThresholdMillis(int millis) throws SQLException { this.mc.setSlowQueryThresholdMillis(millis); }
/*      */ 
/*      */ 
/*      */   
/* 2282 */   public void setSlowQueryThresholdNanos(long nanos) { this.mc.setSlowQueryThresholdNanos(nanos); }
/*      */ 
/*      */ 
/*      */   
/* 2286 */   public void setSocketFactory(String name) throws SQLException { this.mc.setSocketFactory(name); }
/*      */ 
/*      */ 
/*      */   
/* 2290 */   public void setSocketFactoryClassName(String property) throws SQLException { this.mc.setSocketFactoryClassName(property); }
/*      */ 
/*      */ 
/*      */   
/* 2294 */   public void setSocketTimeout(int property) throws SQLException { this.mc.setSocketTimeout(property); }
/*      */ 
/*      */ 
/*      */   
/* 2298 */   public void setStatementInterceptors(String value) throws SQLException { this.mc.setStatementInterceptors(value); }
/*      */ 
/*      */ 
/*      */   
/* 2302 */   public void setStrictFloatingPoint(boolean property) throws SQLException { this.mc.setStrictFloatingPoint(property); }
/*      */ 
/*      */ 
/*      */   
/* 2306 */   public void setStrictUpdates(boolean property) throws SQLException { this.mc.setStrictUpdates(property); }
/*      */ 
/*      */ 
/*      */   
/* 2310 */   public void setTcpKeepAlive(boolean flag) throws SQLException { this.mc.setTcpKeepAlive(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2314 */   public void setTcpNoDelay(boolean flag) throws SQLException { this.mc.setTcpNoDelay(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2318 */   public void setTcpRcvBuf(int bufSize) throws SQLException { this.mc.setTcpRcvBuf(bufSize); }
/*      */ 
/*      */ 
/*      */   
/* 2322 */   public void setTcpSndBuf(int bufSize) throws SQLException { this.mc.setTcpSndBuf(bufSize); }
/*      */ 
/*      */ 
/*      */   
/* 2326 */   public void setTcpTrafficClass(int classFlags) throws SQLException { this.mc.setTcpTrafficClass(classFlags); }
/*      */ 
/*      */ 
/*      */   
/* 2330 */   public void setTinyInt1isBit(boolean flag) throws SQLException { this.mc.setTinyInt1isBit(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2334 */   public void setTraceProtocol(boolean flag) throws SQLException { this.mc.setTraceProtocol(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2338 */   public void setTransformedBitIsBoolean(boolean flag) throws SQLException { this.mc.setTransformedBitIsBoolean(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2342 */   public void setTreatUtilDateAsTimestamp(boolean flag) throws SQLException { this.mc.setTreatUtilDateAsTimestamp(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2346 */   public void setTrustCertificateKeyStorePassword(String value) throws SQLException { this.mc.setTrustCertificateKeyStorePassword(value); }
/*      */ 
/*      */ 
/*      */   
/* 2350 */   public void setTrustCertificateKeyStoreType(String value) throws SQLException { this.mc.setTrustCertificateKeyStoreType(value); }
/*      */ 
/*      */ 
/*      */   
/* 2354 */   public void setTrustCertificateKeyStoreUrl(String value) throws SQLException { this.mc.setTrustCertificateKeyStoreUrl(value); }
/*      */ 
/*      */ 
/*      */   
/* 2358 */   public void setUltraDevHack(boolean flag) throws SQLException { this.mc.setUltraDevHack(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2362 */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) throws SQLException { this.mc.setUseBlobToStoreUTF8OutsideBMP(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2366 */   public void setUseCompression(boolean property) throws SQLException { this.mc.setUseCompression(property); }
/*      */ 
/*      */ 
/*      */   
/* 2370 */   public void setUseConfigs(String configs) throws SQLException { this.mc.setUseConfigs(configs); }
/*      */ 
/*      */ 
/*      */   
/* 2374 */   public void setUseCursorFetch(boolean flag) throws SQLException { this.mc.setUseCursorFetch(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2378 */   public void setUseDirectRowUnpack(boolean flag) throws SQLException { this.mc.setUseDirectRowUnpack(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2382 */   public void setUseDynamicCharsetInfo(boolean flag) throws SQLException { this.mc.setUseDynamicCharsetInfo(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2386 */   public void setUseFastDateParsing(boolean flag) throws SQLException { this.mc.setUseFastDateParsing(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2390 */   public void setUseFastIntParsing(boolean flag) throws SQLException { this.mc.setUseFastIntParsing(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2394 */   public void setUseGmtMillisForDatetimes(boolean flag) throws SQLException { this.mc.setUseGmtMillisForDatetimes(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2398 */   public void setUseHostsInPrivileges(boolean property) throws SQLException { this.mc.setUseHostsInPrivileges(property); }
/*      */ 
/*      */ 
/*      */   
/* 2402 */   public void setUseInformationSchema(boolean flag) throws SQLException { this.mc.setUseInformationSchema(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2406 */   public void setUseJDBCCompliantTimezoneShift(boolean flag) throws SQLException { this.mc.setUseJDBCCompliantTimezoneShift(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2410 */   public void setUseJvmCharsetConverters(boolean flag) throws SQLException { this.mc.setUseJvmCharsetConverters(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2414 */   public void setUseLocalSessionState(boolean flag) throws SQLException { this.mc.setUseLocalSessionState(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2418 */   public void setUseNanosForElapsedTime(boolean flag) throws SQLException { this.mc.setUseNanosForElapsedTime(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2422 */   public void setUseOldAliasMetadataBehavior(boolean flag) throws SQLException { this.mc.setUseOldAliasMetadataBehavior(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2426 */   public void setUseOldUTF8Behavior(boolean flag) throws SQLException { this.mc.setUseOldUTF8Behavior(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2430 */   public void setUseOnlyServerErrorMessages(boolean flag) throws SQLException { this.mc.setUseOnlyServerErrorMessages(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2434 */   public void setUseReadAheadInput(boolean flag) throws SQLException { this.mc.setUseReadAheadInput(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2438 */   public void setUseSSL(boolean property) throws SQLException { this.mc.setUseSSL(property); }
/*      */ 
/*      */ 
/*      */   
/* 2442 */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) throws SQLException { this.mc.setUseSSPSCompatibleTimezoneShift(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2446 */   public void setUseServerPrepStmts(boolean flag) throws SQLException { this.mc.setUseServerPrepStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2450 */   public void setUseServerPreparedStmts(boolean flag) throws SQLException { this.mc.setUseServerPreparedStmts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2454 */   public void setUseSqlStateCodes(boolean flag) throws SQLException { this.mc.setUseSqlStateCodes(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2458 */   public void setUseStreamLengthsInPrepStmts(boolean property) throws SQLException { this.mc.setUseStreamLengthsInPrepStmts(property); }
/*      */ 
/*      */ 
/*      */   
/* 2462 */   public void setUseTimezone(boolean property) throws SQLException { this.mc.setUseTimezone(property); }
/*      */ 
/*      */ 
/*      */   
/* 2466 */   public void setUseUltraDevWorkAround(boolean property) throws SQLException { this.mc.setUseUltraDevWorkAround(property); }
/*      */ 
/*      */ 
/*      */   
/* 2470 */   public void setUseUnbufferedInput(boolean flag) throws SQLException { this.mc.setUseUnbufferedInput(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2474 */   public void setUseUnicode(boolean flag) throws SQLException { this.mc.setUseUnicode(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2478 */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) throws SQLException { this.mc.setUseUsageAdvisor(useUsageAdvisorFlag); }
/*      */ 
/*      */ 
/*      */   
/* 2482 */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) throws SQLException { this.mc.setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern); }
/*      */ 
/*      */ 
/*      */   
/* 2486 */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) throws SQLException { this.mc.setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern); }
/*      */ 
/*      */ 
/*      */   
/* 2490 */   public void setYearIsDateType(boolean flag) throws SQLException { this.mc.setYearIsDateType(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2494 */   public void setZeroDateTimeBehavior(String behavior) throws SQLException { this.mc.setZeroDateTimeBehavior(behavior); }
/*      */ 
/*      */ 
/*      */   
/* 2498 */   public boolean useUnbufferedInput() throws SQLException { return this.mc.useUnbufferedInput(); }
/*      */ 
/*      */ 
/*      */   
/* 2502 */   public void initializeExtension(Extension ex) throws SQLException { this.mc.initializeExtension(ex); }
/*      */ 
/*      */ 
/*      */   
/* 2506 */   public String getProfilerEventHandler() throws SQLException { return this.mc.getProfilerEventHandler(); }
/*      */ 
/*      */ 
/*      */   
/* 2510 */   public void setProfilerEventHandler(String handler) throws SQLException { this.mc.setProfilerEventHandler(handler); }
/*      */ 
/*      */ 
/*      */   
/* 2514 */   public boolean getVerifyServerCertificate() throws SQLException { return this.mc.getVerifyServerCertificate(); }
/*      */ 
/*      */ 
/*      */   
/* 2518 */   public void setVerifyServerCertificate(boolean flag) throws SQLException { this.mc.setVerifyServerCertificate(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2522 */   public boolean getUseLegacyDatetimeCode() throws SQLException { return this.mc.getUseLegacyDatetimeCode(); }
/*      */ 
/*      */ 
/*      */   
/* 2526 */   public void setUseLegacyDatetimeCode(boolean flag) throws SQLException { this.mc.setUseLegacyDatetimeCode(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2530 */   public int getSelfDestructOnPingMaxOperations() throws SQLException { return this.mc.getSelfDestructOnPingMaxOperations(); }
/*      */ 
/*      */ 
/*      */   
/* 2534 */   public int getSelfDestructOnPingSecondsLifetime() throws SQLException { return this.mc.getSelfDestructOnPingSecondsLifetime(); }
/*      */ 
/*      */ 
/*      */   
/* 2538 */   public void setSelfDestructOnPingMaxOperations(int maxOperations) throws SQLException { this.mc.setSelfDestructOnPingMaxOperations(maxOperations); }
/*      */ 
/*      */ 
/*      */   
/* 2542 */   public void setSelfDestructOnPingSecondsLifetime(int seconds) throws SQLException { this.mc.setSelfDestructOnPingSecondsLifetime(seconds); }
/*      */ 
/*      */ 
/*      */   
/* 2546 */   public boolean getUseColumnNamesInFindColumn() throws SQLException { return this.mc.getUseColumnNamesInFindColumn(); }
/*      */ 
/*      */ 
/*      */   
/* 2550 */   public void setUseColumnNamesInFindColumn(boolean flag) throws SQLException { this.mc.setUseColumnNamesInFindColumn(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2554 */   public boolean getUseLocalTransactionState() throws SQLException { return this.mc.getUseLocalTransactionState(); }
/*      */ 
/*      */ 
/*      */   
/* 2558 */   public void setUseLocalTransactionState(boolean flag) throws SQLException { this.mc.setUseLocalTransactionState(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2562 */   public boolean getCompensateOnDuplicateKeyUpdateCounts() throws SQLException { return this.mc.getCompensateOnDuplicateKeyUpdateCounts(); }
/*      */ 
/*      */ 
/*      */   
/* 2566 */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) throws SQLException { this.mc.setCompensateOnDuplicateKeyUpdateCounts(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2570 */   public boolean getUseAffectedRows() throws SQLException { return this.mc.getUseAffectedRows(); }
/*      */ 
/*      */ 
/*      */   
/* 2574 */   public void setUseAffectedRows(boolean flag) throws SQLException { this.mc.setUseAffectedRows(flag); }
/*      */ 
/*      */ 
/*      */   
/* 2578 */   public String getPasswordCharacterEncoding() throws SQLException { return this.mc.getPasswordCharacterEncoding(); }
/*      */ 
/*      */ 
/*      */   
/* 2582 */   public void setPasswordCharacterEncoding(String characterSet) throws SQLException { this.mc.setPasswordCharacterEncoding(characterSet); }
/*      */ 
/*      */ 
/*      */   
/* 2586 */   public int getAutoIncrementIncrement() throws SQLException { return this.mc.getAutoIncrementIncrement(); }
/*      */ 
/*      */ 
/*      */   
/* 2590 */   public int getLoadBalanceBlacklistTimeout() throws SQLException { return this.mc.getLoadBalanceBlacklistTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 2594 */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) throws SQLException { this.mc.setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout); }
/*      */ 
/*      */   
/* 2597 */   public int getLoadBalancePingTimeout() throws SQLException { return this.mc.getLoadBalancePingTimeout(); }
/*      */ 
/*      */ 
/*      */   
/* 2601 */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) throws SQLException { this.mc.setLoadBalancePingTimeout(loadBalancePingTimeout); }
/*      */ 
/*      */ 
/*      */   
/* 2605 */   public boolean getLoadBalanceValidateConnectionOnSwapServer() throws SQLException { return this.mc.getLoadBalanceValidateConnectionOnSwapServer(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2610 */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) throws SQLException { this.mc.setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer); }
/*      */ 
/*      */ 
/*      */   
/* 2614 */   public void setRetriesAllDown(int retriesAllDown) throws SQLException { this.mc.setRetriesAllDown(retriesAllDown); }
/*      */ 
/*      */ 
/*      */   
/* 2618 */   public int getRetriesAllDown() throws SQLException { return this.mc.getRetriesAllDown(); }
/*      */ 
/*      */ 
/*      */   
/* 2622 */   public ExceptionInterceptor getExceptionInterceptor() { return this.pooledConnection.getExceptionInterceptor(); }
/*      */ 
/*      */ 
/*      */   
/* 2626 */   public String getExceptionInterceptors() throws SQLException { return this.mc.getExceptionInterceptors(); }
/*      */ 
/*      */ 
/*      */   
/* 2630 */   public void setExceptionInterceptors(String exceptionInterceptors) throws SQLException { this.mc.setExceptionInterceptors(exceptionInterceptors); }
/*      */ 
/*      */ 
/*      */   
/* 2634 */   public boolean getQueryTimeoutKillsConnection() throws SQLException { return this.mc.getQueryTimeoutKillsConnection(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2639 */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) throws SQLException { this.mc.setQueryTimeoutKillsConnection(queryTimeoutKillsConnection); }
/*      */ 
/*      */ 
/*      */   
/* 2643 */   public boolean hasSameProperties(Connection c) { return this.mc.hasSameProperties(c); }
/*      */ 
/*      */ 
/*      */   
/* 2647 */   public Properties getProperties() { return this.mc.getProperties(); }
/*      */ 
/*      */ 
/*      */   
/* 2651 */   public String getHost() throws SQLException { return this.mc.getHost(); }
/*      */ 
/*      */ 
/*      */   
/* 2655 */   public void setProxy(MySQLConnection conn) { this.mc.setProxy(conn); }
/*      */ 
/*      */ 
/*      */   
/* 2659 */   public boolean getRetainStatementAfterResultSetClose() throws SQLException { return this.mc.getRetainStatementAfterResultSetClose(); }
/*      */ 
/*      */ 
/*      */   
/* 2663 */   public int getMaxAllowedPacket() throws SQLException { return this.mc.getMaxAllowedPacket(); }
/*      */ 
/*      */ 
/*      */   
/* 2667 */   public String getLoadBalanceConnectionGroup() throws SQLException { return this.mc.getLoadBalanceConnectionGroup(); }
/*      */ 
/*      */ 
/*      */   
/* 2671 */   public boolean getLoadBalanceEnableJMX() throws SQLException { return this.mc.getLoadBalanceEnableJMX(); }
/*      */ 
/*      */ 
/*      */   
/* 2675 */   public String getLoadBalanceExceptionChecker() throws SQLException { return this.mc.getLoadBalanceExceptionChecker(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2680 */   public String getLoadBalanceSQLExceptionSubclassFailover() throws SQLException { return this.mc.getLoadBalanceSQLExceptionSubclassFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2685 */   public String getLoadBalanceSQLStateFailover() throws SQLException { return this.mc.getLoadBalanceSQLStateFailover(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2690 */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) throws SQLException { this.mc.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2696 */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) throws SQLException { this.mc.setLoadBalanceEnableJMX(loadBalanceEnableJMX); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2703 */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) throws SQLException { this.mc.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2710 */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) throws SQLException { this.mc.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2717 */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) throws SQLException { this.mc.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2724 */   public String getLoadBalanceAutoCommitStatementRegex() throws SQLException { return this.mc.getLoadBalanceAutoCommitStatementRegex(); }
/*      */ 
/*      */ 
/*      */   
/* 2728 */   public int getLoadBalanceAutoCommitStatementThreshold() throws SQLException { return this.mc.getLoadBalanceAutoCommitStatementThreshold(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2733 */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) throws SQLException { this.mc.setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2739 */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) throws SQLException { this.mc.setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold); }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\ConnectionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */