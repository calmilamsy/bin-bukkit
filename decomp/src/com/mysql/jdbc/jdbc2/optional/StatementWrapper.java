/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.ResultSetInternalMethods;
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import com.mysql.jdbc.Statement;
/*     */ import com.mysql.jdbc.Util;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatementWrapper
/*     */   extends WrapperBase
/*     */   implements Statement
/*     */ {
/*     */   private static final Constructor JDBC_4_STATEMENT_WRAPPER_CTOR;
/*     */   protected Statement wrappedStmt;
/*     */   protected ConnectionWrapper wrappedConn;
/*     */   
/*     */   static  {
/*  51 */     if (Util.isJdbc4()) {
/*     */       try {
/*  53 */         JDBC_4_STATEMENT_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4StatementWrapper").getConstructor(new Class[] { ConnectionWrapper.class, MysqlPooledConnection.class, Statement.class });
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  58 */       catch (SecurityException e) {
/*  59 */         throw new RuntimeException(e);
/*  60 */       } catch (NoSuchMethodException e) {
/*  61 */         throw new RuntimeException(e);
/*  62 */       } catch (ClassNotFoundException e) {
/*  63 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/*  66 */       JDBC_4_STATEMENT_WRAPPER_CTOR = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static StatementWrapper getInstance(ConnectionWrapper c, MysqlPooledConnection conn, Statement toWrap) throws SQLException {
/*  73 */     if (!Util.isJdbc4()) {
/*  74 */       return new StatementWrapper(c, conn, toWrap);
/*     */     }
/*     */ 
/*     */     
/*  78 */     return (StatementWrapper)Util.handleNewInstance(JDBC_4_STATEMENT_WRAPPER_CTOR, new Object[] { c, conn, toWrap }, conn.getExceptionInterceptor());
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
/*     */   public StatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, Statement toWrap) {
/*  90 */     super(conn);
/*  91 */     this.wrappedStmt = toWrap;
/*  92 */     this.wrappedConn = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/*     */     try {
/* 102 */       if (this.wrappedStmt != null) {
/* 103 */         return this.wrappedConn;
/*     */       }
/*     */       
/* 106 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 108 */     catch (SQLException sqlEx) {
/* 109 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 112 */       return null;
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
/*     */   public void setCursorName(String name) throws SQLException {
/*     */     try {
/* 125 */       if (this.wrappedStmt != null) {
/* 126 */         this.wrappedStmt.setCursorName(name);
/*     */       } else {
/* 128 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 131 */     } catch (SQLException sqlEx) {
/* 132 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEscapeProcessing(boolean enable) throws SQLException {
/*     */     try {
/* 143 */       if (this.wrappedStmt != null) {
/* 144 */         this.wrappedStmt.setEscapeProcessing(enable);
/*     */       } else {
/* 146 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 149 */     } catch (SQLException sqlEx) {
/* 150 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchDirection(int direction) throws SQLException {
/*     */     try {
/* 161 */       if (this.wrappedStmt != null) {
/* 162 */         this.wrappedStmt.setFetchDirection(direction);
/*     */       } else {
/* 164 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 167 */     } catch (SQLException sqlEx) {
/* 168 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/*     */     try {
/* 179 */       if (this.wrappedStmt != null) {
/* 180 */         return this.wrappedStmt.getFetchDirection();
/*     */       }
/*     */       
/* 183 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 185 */     catch (SQLException sqlEx) {
/* 186 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 189 */       return 1000;
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
/*     */   public void setFetchSize(int rows) throws SQLException {
/*     */     try {
/* 202 */       if (this.wrappedStmt != null) {
/* 203 */         this.wrappedStmt.setFetchSize(rows);
/*     */       } else {
/* 205 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 208 */     } catch (SQLException sqlEx) {
/* 209 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/*     */     try {
/* 220 */       if (this.wrappedStmt != null) {
/* 221 */         return this.wrappedStmt.getFetchSize();
/*     */       }
/*     */       
/* 224 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 226 */     catch (SQLException sqlEx) {
/* 227 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 230 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getGeneratedKeys() throws SQLException {
/*     */     try {
/* 242 */       if (this.wrappedStmt != null) {
/* 243 */         return this.wrappedStmt.getGeneratedKeys();
/*     */       }
/*     */       
/* 246 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 248 */     catch (SQLException sqlEx) {
/* 249 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 252 */       return null;
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
/*     */   public void setMaxFieldSize(int max) throws SQLException {
/*     */     try {
/* 265 */       if (this.wrappedStmt != null) {
/* 266 */         this.wrappedStmt.setMaxFieldSize(max);
/*     */       } else {
/* 268 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 271 */     } catch (SQLException sqlEx) {
/* 272 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFieldSize() throws SQLException {
/*     */     try {
/* 283 */       if (this.wrappedStmt != null) {
/* 284 */         return this.wrappedStmt.getMaxFieldSize();
/*     */       }
/*     */       
/* 287 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 289 */     catch (SQLException sqlEx) {
/* 290 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 293 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRows(int max) throws SQLException {
/*     */     try {
/* 305 */       if (this.wrappedStmt != null) {
/* 306 */         this.wrappedStmt.setMaxRows(max);
/*     */       } else {
/* 308 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 311 */     } catch (SQLException sqlEx) {
/* 312 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRows() throws SQLException {
/*     */     try {
/* 323 */       if (this.wrappedStmt != null) {
/* 324 */         return this.wrappedStmt.getMaxRows();
/*     */       }
/*     */       
/* 327 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 329 */     catch (SQLException sqlEx) {
/* 330 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 333 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults() throws SQLException {
/*     */     try {
/* 345 */       if (this.wrappedStmt != null) {
/* 346 */         return this.wrappedStmt.getMoreResults();
/*     */       }
/*     */       
/* 349 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 351 */     catch (SQLException sqlEx) {
/* 352 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 355 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults(int current) throws SQLException {
/*     */     try {
/* 365 */       if (this.wrappedStmt != null) {
/* 366 */         return this.wrappedStmt.getMoreResults(current);
/*     */       }
/*     */       
/* 369 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 371 */     catch (SQLException sqlEx) {
/* 372 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 375 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueryTimeout(int seconds) throws SQLException {
/*     */     try {
/* 385 */       if (this.wrappedStmt != null) {
/* 386 */         this.wrappedStmt.setQueryTimeout(seconds);
/*     */       } else {
/* 388 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/* 391 */     } catch (SQLException sqlEx) {
/* 392 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueryTimeout() throws SQLException {
/*     */     try {
/* 403 */       if (this.wrappedStmt != null) {
/* 404 */         return this.wrappedStmt.getQueryTimeout();
/*     */       }
/*     */       
/* 407 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 409 */     catch (SQLException sqlEx) {
/* 410 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 413 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/*     */     try {
/* 423 */       if (this.wrappedStmt != null) {
/* 424 */         ResultSet rs = this.wrappedStmt.getResultSet();
/*     */         
/* 426 */         if (rs != null) {
/* 427 */           ((ResultSetInternalMethods)rs).setWrapperStatement(this);
/*     */         }
/* 429 */         return rs;
/*     */       } 
/*     */       
/* 432 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 434 */     catch (SQLException sqlEx) {
/* 435 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 438 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetConcurrency() throws SQLException {
/*     */     try {
/* 448 */       if (this.wrappedStmt != null) {
/* 449 */         return this.wrappedStmt.getResultSetConcurrency();
/*     */       }
/*     */       
/* 452 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 454 */     catch (SQLException sqlEx) {
/* 455 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 458 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetHoldability() throws SQLException {
/*     */     try {
/* 468 */       if (this.wrappedStmt != null) {
/* 469 */         return this.wrappedStmt.getResultSetHoldability();
/*     */       }
/*     */       
/* 472 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 474 */     catch (SQLException sqlEx) {
/* 475 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 478 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetType() throws SQLException {
/*     */     try {
/* 488 */       if (this.wrappedStmt != null) {
/* 489 */         return this.wrappedStmt.getResultSetType();
/*     */       }
/*     */       
/* 492 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 494 */     catch (SQLException sqlEx) {
/* 495 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 498 */       return 1003;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/*     */     try {
/* 508 */       if (this.wrappedStmt != null) {
/* 509 */         return this.wrappedStmt.getUpdateCount();
/*     */       }
/*     */       
/* 512 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 514 */     catch (SQLException sqlEx) {
/* 515 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 518 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/*     */     try {
/* 528 */       if (this.wrappedStmt != null) {
/* 529 */         return this.wrappedStmt.getWarnings();
/*     */       }
/*     */       
/* 532 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 534 */     catch (SQLException sqlEx) {
/* 535 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 538 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/*     */     try {
/* 548 */       if (this.wrappedStmt != null) {
/* 549 */         this.wrappedStmt.addBatch(sql);
/*     */       }
/* 551 */     } catch (SQLException sqlEx) {
/* 552 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws SQLException {
/*     */     try {
/* 563 */       if (this.wrappedStmt != null) {
/* 564 */         this.wrappedStmt.cancel();
/*     */       }
/* 566 */     } catch (SQLException sqlEx) {
/* 567 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBatch() throws SQLException {
/*     */     try {
/* 578 */       if (this.wrappedStmt != null) {
/* 579 */         this.wrappedStmt.clearBatch();
/*     */       }
/* 581 */     } catch (SQLException sqlEx) {
/* 582 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearWarnings() throws SQLException {
/*     */     try {
/* 593 */       if (this.wrappedStmt != null) {
/* 594 */         this.wrappedStmt.clearWarnings();
/*     */       }
/* 596 */     } catch (SQLException sqlEx) {
/* 597 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*     */     try {
/* 608 */       if (this.wrappedStmt != null) {
/* 609 */         this.wrappedStmt.close();
/*     */       }
/* 611 */     } catch (SQLException sqlEx) {
/* 612 */       checkAndFireConnectionError(sqlEx);
/*     */     } finally {
/* 614 */       this.wrappedStmt = null;
/* 615 */       this.pooledConnection = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
/*     */     try {
/* 627 */       if (this.wrappedStmt != null) {
/* 628 */         return this.wrappedStmt.execute(sql, autoGeneratedKeys);
/*     */       }
/*     */       
/* 631 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 633 */     catch (SQLException sqlEx) {
/* 634 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 637 */       return false;
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
/*     */   public boolean execute(String sql, int[] columnIndexes) throws SQLException {
/*     */     try {
/* 650 */       if (this.wrappedStmt != null) {
/* 651 */         return this.wrappedStmt.execute(sql, columnIndexes);
/*     */       }
/*     */       
/* 654 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 656 */     catch (SQLException sqlEx) {
/* 657 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 660 */       return false;
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
/*     */   public boolean execute(String sql, String[] columnNames) throws SQLException {
/*     */     try {
/* 674 */       if (this.wrappedStmt != null) {
/* 675 */         return this.wrappedStmt.execute(sql, columnNames);
/*     */       }
/*     */       
/* 678 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 680 */     catch (SQLException sqlEx) {
/* 681 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 684 */       return false;
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
/*     */   public boolean execute(String sql) throws SQLException {
/*     */     try {
/* 697 */       if (this.wrappedStmt != null) {
/* 698 */         return this.wrappedStmt.execute(sql);
/*     */       }
/*     */       
/* 701 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 703 */     catch (SQLException sqlEx) {
/* 704 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 707 */       return false;
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
/*     */   public int[] executeBatch() throws SQLException {
/*     */     try {
/* 720 */       if (this.wrappedStmt != null) {
/* 721 */         return this.wrappedStmt.executeBatch();
/*     */       }
/*     */       
/* 724 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 726 */     catch (SQLException sqlEx) {
/* 727 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 730 */       return null;
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
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/*     */     try {
/* 743 */       if (this.wrappedStmt != null) {
/*     */         
/* 745 */         ResultSet rs = this.wrappedStmt.executeQuery(sql);
/* 746 */         ((ResultSetInternalMethods)rs).setWrapperStatement(this);
/*     */         
/* 748 */         return rs;
/*     */       } 
/*     */       
/* 751 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 753 */     catch (SQLException sqlEx) {
/* 754 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 757 */       return null;
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
/*     */   public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
/*     */     try {
/* 771 */       if (this.wrappedStmt != null) {
/* 772 */         return this.wrappedStmt.executeUpdate(sql, autoGeneratedKeys);
/*     */       }
/*     */       
/* 775 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 777 */     catch (SQLException sqlEx) {
/* 778 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 781 */       return -1;
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
/*     */   public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
/*     */     try {
/* 794 */       if (this.wrappedStmt != null) {
/* 795 */         return this.wrappedStmt.executeUpdate(sql, columnIndexes);
/*     */       }
/*     */       
/* 798 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 800 */     catch (SQLException sqlEx) {
/* 801 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 804 */       return -1;
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
/*     */   public int executeUpdate(String sql, String[] columnNames) throws SQLException {
/*     */     try {
/* 818 */       if (this.wrappedStmt != null) {
/* 819 */         return this.wrappedStmt.executeUpdate(sql, columnNames);
/*     */       }
/*     */       
/* 822 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 824 */     catch (SQLException sqlEx) {
/* 825 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 828 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/*     */     try {
/* 840 */       if (this.wrappedStmt != null) {
/* 841 */         return this.wrappedStmt.executeUpdate(sql);
/*     */       }
/*     */       
/* 844 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     }
/* 846 */     catch (SQLException sqlEx) {
/* 847 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 850 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableStreamingResults() throws SQLException {
/*     */     try {
/* 857 */       if (this.wrappedStmt != null) {
/* 858 */         ((Statement)this.wrappedStmt).enableStreamingResults();
/*     */       } else {
/*     */         
/* 861 */         throw SQLError.createSQLException("No operations allowed after statement closed", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     
/*     */     }
/* 865 */     catch (SQLException sqlEx) {
/* 866 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\StatementWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */