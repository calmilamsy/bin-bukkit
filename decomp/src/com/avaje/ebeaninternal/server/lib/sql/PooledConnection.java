/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebeaninternal.jdbc.ConnectionDelegator;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class PooledConnection
/*     */   extends ConnectionDelegator
/*     */ {
/*  57 */   private static final Logger logger = Logger.getLogger(PooledConnection.class.getName());
/*     */   
/*  59 */   private static String IDLE_CONNECTION_ACCESSED_ERROR = "Pooled Connection has been accessed whilst idle in the pool, via method: ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int STATUS_IDLE = 88;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int STATUS_ACTIVE = 89;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int STATUS_ENDED = 87;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final DataSourcePool pool;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Connection connection;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long creationTime;
/*     */ 
/*     */ 
/*     */   
/*     */   final PstmtCache pstmtCache;
/*     */ 
/*     */ 
/*     */   
/* 102 */   final Object pstmtMonitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   int status = 88;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean longRunning;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hadErrors;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long startUseTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long lastUseTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String lastStatement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int pstmtHitCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int pstmtMissCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String createdByMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StackTraceElement[] stackTrace;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int maxStackTrace;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int slotId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean resetIsolationReadOnlyRequired;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledConnection(DataSourcePool pool, int uniqueId, Connection connection) throws SQLException
/*     */   {
/* 174 */     super(connection);
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
/* 605 */     this.resetIsolationReadOnlyRequired = false; this.pool = pool; this.connection = connection; this.name = pool.getName() + "." + uniqueId; this.pstmtCache = new PstmtCache(this.name, pool.getPstmtCacheSize()); this.maxStackTrace = pool.getMaxStackTraceSize(); this.creationTime = System.currentTimeMillis(); this.lastUseTime = this.creationTime; } public int getSlotId() { return this.slotId; } public void setSlotId(int slotId) { this.slotId = slotId; } public DataSourcePool getDataSourcePool() { return this.pool; } public long getCreationTime() { return this.creationTime; } public String getName() { return this.name; } public String toString() { return this.name; } public String getDescription() { return "name[" + this.name + "] startTime[" + getStartUseTime() + "] stmt[" + getLastStatement() + "] createdBy[" + getCreatedByMethod() + "]"; } protected PooledConnection(String name) { super(null); this.resetIsolationReadOnlyRequired = false; this.name = name; this.pool = null; this.connection = null; this.pstmtCache = null; this.maxStackTrace = 0; this.creationTime = System.currentTimeMillis(); this.lastUseTime = this.creationTime; }
/*     */   public String getStatistics() { return "name[" + this.name + "] startTime[" + getStartUseTime() + "] pstmtHits[" + this.pstmtHitCounter + "] pstmtMiss[" + this.pstmtMissCounter + "] " + this.pstmtCache.getDescription(); }
/*     */   public boolean isLongRunning() { return this.longRunning; }
/*     */   public void setLongRunning(boolean longRunning) { this.longRunning = longRunning; } public void closeConnectionFully(boolean logErrors) { String msg = "Closing Connection[" + getName() + "]" + " psReuse[" + this.pstmtHitCounter + "] psCreate[" + this.pstmtMissCounter + "] psSize[" + this.pstmtCache.size() + "]"; logger.info(msg); try { if (this.connection.isClosed()) { msg = "Closing Connection[" + getName() + "] that is already closed?"; logger.log(Level.SEVERE, msg); return; }  } catch (SQLException ex) { if (logErrors) { msg = "Error when fully closing connection [" + getName() + "]"; logger.log(Level.SEVERE, msg, ex); }  }  try { Iterator<ExtendedPreparedStatement> psi = this.pstmtCache.values().iterator(); while (psi.hasNext()) { ExtendedPreparedStatement ps = (ExtendedPreparedStatement)psi.next(); ps.closeDestroy(); }  } catch (SQLException ex) { if (logErrors)
/*     */         logger.log(Level.WARNING, "Error when closing connection Statements", ex);  }  try { this.connection.close(); } catch (SQLException ex) { if (logErrors) { msg = "Error when fully closing connection [" + getName() + "]"; logger.log(Level.SEVERE, msg, ex); }
/*     */        }
/*     */      } public PstmtCache getPstmtCache() { return this.pstmtCache; } public Statement createStatement() throws SQLException { if (this.status == 88)
/*     */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");  try { return this.connection.createStatement(); }
/*     */     catch (SQLException ex) { addError(ex); throw ex; }
/*     */      } public Statement createStatement(int resultSetType, int resultSetConcurreny) throws SQLException { if (this.status == 88)
/*     */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");  try { return this.connection.createStatement(resultSetType, resultSetConcurreny); }
/*     */     catch (SQLException ex) { addError(ex); throw ex; }
/* 617 */      } public void setReadOnly(boolean readOnly) { this.resetIsolationReadOnlyRequired = true;
/* 618 */     this.connection.setReadOnly(readOnly); } protected void returnPreparedStatement(ExtendedPreparedStatement pstmt) { synchronized (this.pstmtMonitor) { ExtendedPreparedStatement alreadyInCache = this.pstmtCache.get(pstmt.getCacheKey()); if (alreadyInCache == null) { this.pstmtCache.put(pstmt.getCacheKey(), pstmt); } else { try { pstmt.closeDestroy(); } catch (SQLException e) { logger.log(Level.SEVERE, "Error closing Pstmt", e); }  }  }  } public PreparedStatement prepareStatement(String sql, int returnKeysFlag) throws SQLException { String cacheKey = sql + returnKeysFlag; return prepareStatement(sql, true, returnKeysFlag, cacheKey); } public PreparedStatement prepareStatement(String sql) throws SQLException { return prepareStatement(sql, false, 0, sql); }
/*     */   private PreparedStatement prepareStatement(String sql, boolean useFlag, int flag, String cacheKey) throws SQLException { if (this.status == 88) { String m = IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()"; throw new SQLException(m); }  try { synchronized (this.pstmtMonitor) { PreparedStatement actualPstmt; this.lastStatement = sql; ExtendedPreparedStatement pstmt = this.pstmtCache.remove(cacheKey); if (pstmt != null) { this.pstmtHitCounter++; return pstmt; }  this.pstmtMissCounter++; if (useFlag) { actualPstmt = this.connection.prepareStatement(sql, flag); } else { actualPstmt = this.connection.prepareStatement(sql); }  return new ExtendedPreparedStatement(this, actualPstmt, sql, cacheKey); }  } catch (SQLException ex) { addError(ex); throw ex; }  }
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurreny) throws SQLException { if (this.status == 88)
/*     */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()");  try { this.pstmtMissCounter++; this.lastStatement = sql; return this.connection.prepareStatement(sql, resultSetType, resultSetConcurreny); }
/*     */     catch (SQLException ex) { addError(ex); throw ex; }
/*     */      }
/*     */   protected void resetForUse() { this.status = 89; this.startUseTime = System.currentTimeMillis(); this.createdByMethod = null; this.lastStatement = null; this.hadErrors = false; this.longRunning = false; }
/*     */   public void addError(Throwable e) { this.hadErrors = true; }
/* 626 */   public void setTransactionIsolation(int level) { if (this.status == 88) {
/* 627 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTransactionIsolation()");
/*     */     }
/*     */     
/* 630 */     try { this.resetIsolationReadOnlyRequired = true;
/* 631 */       this.connection.setTransactionIsolation(level); }
/* 632 */     catch (SQLException ex)
/* 633 */     { addError(ex);
/* 634 */       throw ex; }  } public boolean hadErrors() { return this.hadErrors; } public void close() { if (this.status == 88)
/*     */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "close()");  if (this.hadErrors && !this.pool.validateConnection(this)) { closeConnectionFully(false); this.pool.checkDataSource(); return; }  try { if (this.connection.getAutoCommit() != this.pool.getAutoCommit())
/*     */         this.connection.setAutoCommit(this.pool.getAutoCommit());  if (this.resetIsolationReadOnlyRequired) { resetIsolationReadOnly(); this.resetIsolationReadOnlyRequired = false; }  this.lastUseTime = System.currentTimeMillis(); this.status = 88; this.pool.returnConnection(this); } catch (Exception ex) { closeConnectionFully(false); this.pool.checkDataSource(); }  } private void resetIsolationReadOnly() { if (this.connection.getTransactionIsolation() != this.pool.getTransactionIsolation())
/*     */       this.connection.setTransactionIsolation(this.pool.getTransactionIsolation());  if (this.connection.isReadOnly())
/*     */       this.connection.setReadOnly(false);  } protected void finalize() { try { if (this.connection != null && !this.connection.isClosed()) { String msg = "Closing Connection[" + getName() + "] on finalize()."; logger.warning(msg); closeConnectionFully(false); }
/*     */        }
/*     */     catch (Exception e) { logger.log(Level.SEVERE, null, e); }
/*     */      super.finalize(); } public long getStartUseTime() { return this.startUseTime; }
/*     */   public long getLastUsedTime() { return this.lastUseTime; }
/*     */   public String getLastStatement() { return this.lastStatement; }
/*     */   protected void setLastStatement(String lastStatement) { this.lastStatement = lastStatement; if (logger.isLoggable(Level.FINER))
/*     */       logger.finer(".setLastStatement[" + lastStatement + "]");  }
/* 646 */   public void clearWarnings() { if (this.status == 88) {
/* 647 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "clearWarnings()");
/*     */     }
/* 649 */     this.connection.clearWarnings(); }
/*     */ 
/*     */   
/*     */   public void commit() {
/* 653 */     if (this.status == 88) {
/* 654 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "commit()");
/*     */     }
/*     */     try {
/* 657 */       this.status = 87;
/* 658 */       this.connection.commit();
/* 659 */     } catch (SQLException ex) {
/* 660 */       addError(ex);
/* 661 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getAutoCommit() {
/* 666 */     if (this.status == 88) {
/* 667 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getAutoCommit()");
/*     */     }
/* 669 */     return this.connection.getAutoCommit();
/*     */   }
/*     */   
/*     */   public String getCatalog() {
/* 673 */     if (this.status == 88) {
/* 674 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getCatalog()");
/*     */     }
/* 676 */     return this.connection.getCatalog();
/*     */   }
/*     */   
/*     */   public DatabaseMetaData getMetaData() throws SQLException {
/* 680 */     if (this.status == 88) {
/* 681 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getMetaData()");
/*     */     }
/* 683 */     return this.connection.getMetaData();
/*     */   }
/*     */   
/*     */   public int getTransactionIsolation() {
/* 687 */     if (this.status == 88) {
/* 688 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTransactionIsolation()");
/*     */     }
/* 690 */     return this.connection.getTransactionIsolation();
/*     */   }
/*     */   
/*     */   public Map<String, Class<?>> getTypeMap() throws SQLException {
/* 694 */     if (this.status == 88) {
/* 695 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTypeMap()");
/*     */     }
/* 697 */     return this.connection.getTypeMap();
/*     */   }
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 701 */     if (this.status == 88) {
/* 702 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getWarnings()");
/*     */     }
/* 704 */     return this.connection.getWarnings();
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 708 */     if (this.status == 88) {
/* 709 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isClosed()");
/*     */     }
/* 711 */     return this.connection.isClosed();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 715 */     if (this.status == 88) {
/* 716 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isReadOnly()");
/*     */     }
/* 718 */     return this.connection.isReadOnly();
/*     */   }
/*     */   
/*     */   public String nativeSQL(String sql) throws SQLException {
/* 722 */     if (this.status == 88) {
/* 723 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "nativeSQL()");
/*     */     }
/* 725 */     this.lastStatement = sql;
/* 726 */     return this.connection.nativeSQL(sql);
/*     */   }
/*     */   
/*     */   public CallableStatement prepareCall(String sql) throws SQLException {
/* 730 */     if (this.status == 88) {
/* 731 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
/*     */     }
/* 733 */     this.lastStatement = sql;
/* 734 */     return this.connection.prepareCall(sql);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurreny) throws SQLException {
/* 739 */     if (this.status == 88) {
/* 740 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
/*     */     }
/* 742 */     this.lastStatement = sql;
/* 743 */     return this.connection.prepareCall(sql, resultSetType, resultSetConcurreny);
/*     */   }
/*     */   
/*     */   public void rollback() {
/* 747 */     if (this.status == 88) {
/* 748 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "rollback()");
/*     */     }
/*     */     try {
/* 751 */       this.status = 87;
/* 752 */       this.connection.rollback();
/* 753 */     } catch (SQLException ex) {
/* 754 */       addError(ex);
/* 755 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAutoCommit(boolean autoCommit) {
/* 760 */     if (this.status == 88) {
/* 761 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setAutoCommit()");
/*     */     }
/*     */     try {
/* 764 */       this.connection.setAutoCommit(autoCommit);
/* 765 */     } catch (SQLException ex) {
/* 766 */       addError(ex);
/* 767 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCatalog(String catalog) {
/* 772 */     if (this.status == 88) {
/* 773 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setCatalog()");
/*     */     }
/* 775 */     this.connection.setCatalog(catalog);
/*     */   }
/*     */   
/*     */   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
/* 779 */     if (this.status == 88) {
/* 780 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTypeMap()");
/*     */     }
/* 782 */     this.connection.setTypeMap(map);
/*     */   }
/*     */   
/*     */   public Savepoint setSavepoint() throws SQLException {
/*     */     try {
/* 787 */       return this.connection.setSavepoint();
/* 788 */     } catch (SQLException ex) {
/* 789 */       addError(ex);
/* 790 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Savepoint setSavepoint(String savepointName) throws SQLException {
/*     */     try {
/* 796 */       return this.connection.setSavepoint(savepointName);
/* 797 */     } catch (SQLException ex) {
/* 798 */       addError(ex);
/* 799 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rollback(Savepoint sp) throws SQLException {
/*     */     try {
/* 805 */       this.connection.rollback(sp);
/* 806 */     } catch (SQLException ex) {
/* 807 */       addError(ex);
/* 808 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseSavepoint(Savepoint sp) throws SQLException {
/*     */     try {
/* 814 */       this.connection.releaseSavepoint(sp);
/* 815 */     } catch (SQLException ex) {
/* 816 */       addError(ex);
/* 817 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHoldability(int i) {
/*     */     try {
/* 823 */       this.connection.setHoldability(i);
/* 824 */     } catch (SQLException ex) {
/* 825 */       addError(ex);
/* 826 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getHoldability() {
/*     */     try {
/* 832 */       return this.connection.getHoldability();
/* 833 */     } catch (SQLException ex) {
/* 834 */       addError(ex);
/* 835 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Statement createStatement(int i, int x, int y) throws SQLException {
/*     */     try {
/* 841 */       return this.connection.createStatement(i, x, y);
/* 842 */     } catch (SQLException ex) {
/* 843 */       addError(ex);
/* 844 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, int i, int x, int y) throws SQLException {
/*     */     try {
/* 850 */       return this.connection.prepareStatement(s, i, x, y);
/* 851 */     } catch (SQLException ex) {
/* 852 */       addError(ex);
/* 853 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, int[] i) throws SQLException {
/*     */     try {
/* 859 */       return this.connection.prepareStatement(s, i);
/* 860 */     } catch (SQLException ex) {
/* 861 */       addError(ex);
/* 862 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, String[] s2) throws SQLException {
/*     */     try {
/* 868 */       return this.connection.prepareStatement(s, s2);
/* 869 */     } catch (SQLException ex) {
/* 870 */       addError(ex);
/* 871 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public CallableStatement prepareCall(String s, int i, int x, int y) throws SQLException {
/*     */     try {
/* 877 */       return this.connection.prepareCall(s, i, x, y);
/* 878 */     } catch (SQLException ex) {
/* 879 */       addError(ex);
/* 880 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreatedByMethod() {
/* 891 */     if (this.createdByMethod != null) {
/* 892 */       return this.createdByMethod;
/*     */     }
/* 894 */     if (this.stackTrace == null) {
/* 895 */       return null;
/*     */     }
/*     */     
/* 898 */     for (int j = 0; j < this.stackTrace.length; ) {
/* 899 */       String methodLine = this.stackTrace[j].toString();
/* 900 */       if (skipElement(methodLine)) {
/*     */         j++; continue;
/*     */       } 
/* 903 */       this.createdByMethod = methodLine;
/* 904 */       return this.createdByMethod;
/*     */     } 
/*     */ 
/*     */     
/* 908 */     return null;
/*     */   }
/*     */   
/*     */   private boolean skipElement(String methodLine) {
/* 912 */     if (methodLine.startsWith("java.lang."))
/* 913 */       return true; 
/* 914 */     if (methodLine.startsWith("java.util."))
/* 915 */       return true; 
/* 916 */     if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.CallableQuery.<init>"))
/*     */     {
/* 918 */       return true; } 
/* 919 */     if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.Callable"))
/*     */     {
/* 921 */       return false; } 
/* 922 */     if (methodLine.startsWith("com.avaje.ebeaninternal")) {
/* 923 */       return true;
/*     */     }
/* 925 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 933 */   protected void setStackTrace(StackTraceElement[] stackTrace) { this.stackTrace = stackTrace; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTraceElement[] getStackTrace() {
/* 942 */     if (this.stackTrace == null) {
/* 943 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 947 */     ArrayList<StackTraceElement> filteredList = new ArrayList<StackTraceElement>();
/* 948 */     boolean include = false;
/* 949 */     for (int i = 0; i < this.stackTrace.length; i++) {
/* 950 */       if (!include && !skipElement(this.stackTrace[i].toString())) {
/* 951 */         include = true;
/*     */       }
/* 953 */       if (include && filteredList.size() < this.maxStackTrace) {
/* 954 */         filteredList.add(this.stackTrace[i]);
/*     */       }
/*     */     } 
/* 957 */     return (StackTraceElement[])filteredList.toArray(new StackTraceElement[filteredList.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\PooledConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */