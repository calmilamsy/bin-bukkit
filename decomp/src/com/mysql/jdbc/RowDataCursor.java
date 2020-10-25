/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RowDataCursor
/*     */   implements RowData
/*     */ {
/*     */   private static final int BEFORE_START_OF_ROWS = -1;
/*     */   private List fetchedRows;
/*     */   private int currentPositionInEntireResult;
/*     */   private int currentPositionInFetchedRows;
/*     */   private ResultSetImpl owner;
/*     */   private boolean lastRowFetched;
/*     */   private Field[] metadata;
/*     */   private MysqlIO mysql;
/*     */   private long statementIdOnServer;
/*     */   private ServerPreparedStatement prepStmt;
/*     */   private static final int SERVER_STATUS_LAST_ROW_SENT = 128;
/*     */   private boolean firstFetchCompleted;
/*     */   private boolean wasEmpty;
/*     */   private boolean useBufferRowExplicit;
/*     */   
/*     */   public RowDataCursor(MysqlIO ioChannel, ServerPreparedStatement creatingStatement, Field[] metadata) {
/*  50 */     this.currentPositionInEntireResult = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     this.currentPositionInFetchedRows = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.lastRowFetched = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.firstFetchCompleted = false;
/*     */     
/* 101 */     this.wasEmpty = false;
/*     */     
/* 103 */     this.useBufferRowExplicit = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     this.currentPositionInEntireResult = -1;
/* 118 */     this.metadata = metadata;
/* 119 */     this.mysql = ioChannel;
/* 120 */     this.statementIdOnServer = creatingStatement.getServerStatementId();
/* 121 */     this.prepStmt = creatingStatement;
/* 122 */     this.useBufferRowExplicit = MysqlIO.useBufferRowExplicit(this.metadata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public boolean isAfterLast() { return (this.lastRowFetched && this.currentPositionInFetchedRows > this.fetchedRows.size()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow getAt(int ind) throws SQLException {
/* 146 */     notSupported();
/*     */     
/* 148 */     return null;
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
/* 159 */   public boolean isBeforeFirst() { return (this.currentPositionInEntireResult < 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public void setCurrentRow(int rowNumber) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public int getCurrentRowNumber() throws SQLException { return this.currentPositionInEntireResult + 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public boolean isDynamic() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public boolean isEmpty() { return (isBeforeFirst() && isAfterLast()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public boolean isFirst() { return (this.currentPositionInEntireResult == 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public boolean isLast() { return (this.lastRowFetched && this.currentPositionInFetchedRows == this.fetchedRows.size() - 1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public void addRow(ResultSetRow row) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public void afterLast() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public void beforeFirst() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public void beforeLast() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 282 */     this.metadata = null;
/* 283 */     this.owner = null;
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
/*     */   public boolean hasNext() {
/* 295 */     if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
/* 296 */       return false;
/*     */     }
/*     */     
/* 299 */     if (this.owner != null && this.owner.owningStatement != null) {
/* 300 */       int maxRows = this.owner.owningStatement.maxRows;
/*     */       
/* 302 */       if (maxRows != -1 && this.currentPositionInEntireResult + 1 > maxRows) {
/* 303 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 307 */     if (this.currentPositionInEntireResult != -1) {
/*     */ 
/*     */       
/* 310 */       if (this.currentPositionInFetchedRows < this.fetchedRows.size() - 1)
/* 311 */         return true; 
/* 312 */       if (this.currentPositionInFetchedRows == this.fetchedRows.size() && this.lastRowFetched)
/*     */       {
/*     */         
/* 315 */         return false;
/*     */       }
/*     */       
/* 318 */       fetchMoreRows();
/*     */       
/* 320 */       return (this.fetchedRows.size() > 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     fetchMoreRows();
/*     */     
/* 328 */     return (this.fetchedRows.size() > 0);
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
/* 340 */   public void moveRowRelative(int rows) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetRow next() throws SQLException {
/* 351 */     if (this.fetchedRows == null && this.currentPositionInEntireResult != -1) {
/* 352 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000", this.mysql.getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     if (!hasNext()) {
/* 359 */       return null;
/*     */     }
/*     */     
/* 362 */     this.currentPositionInEntireResult++;
/* 363 */     this.currentPositionInFetchedRows++;
/*     */ 
/*     */     
/* 366 */     if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
/* 367 */       return null;
/*     */     }
/*     */     
/* 370 */     if (this.currentPositionInFetchedRows > this.fetchedRows.size() - 1) {
/* 371 */       fetchMoreRows();
/* 372 */       this.currentPositionInFetchedRows = 0;
/*     */     } 
/*     */     
/* 375 */     ResultSetRow row = (ResultSetRow)this.fetchedRows.get(this.currentPositionInFetchedRows);
/*     */ 
/*     */     
/* 378 */     row.setMetadata(this.metadata);
/*     */     
/* 380 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fetchMoreRows() throws SQLException {
/* 387 */     if (this.lastRowFetched) {
/* 388 */       this.fetchedRows = new ArrayList(false);
/*     */       
/*     */       return;
/*     */     } 
/* 392 */     synchronized (this.owner.connection.getMutex()) {
/* 393 */       boolean oldFirstFetchCompleted = this.firstFetchCompleted;
/*     */       
/* 395 */       if (!this.firstFetchCompleted) {
/* 396 */         this.firstFetchCompleted = true;
/*     */       }
/*     */       
/* 399 */       int numRowsToFetch = this.owner.getFetchSize();
/*     */       
/* 401 */       if (numRowsToFetch == 0) {
/* 402 */         numRowsToFetch = this.prepStmt.getFetchSize();
/*     */       }
/*     */       
/* 405 */       if (numRowsToFetch == Integer.MIN_VALUE)
/*     */       {
/*     */ 
/*     */         
/* 409 */         numRowsToFetch = 1;
/*     */       }
/*     */       
/* 412 */       this.fetchedRows = this.mysql.fetchRowsViaCursor(this.fetchedRows, this.statementIdOnServer, this.metadata, numRowsToFetch, this.useBufferRowExplicit);
/*     */ 
/*     */       
/* 415 */       this.currentPositionInFetchedRows = -1;
/*     */       
/* 417 */       if ((this.mysql.getServerStatus() & 0x80) != 0) {
/* 418 */         this.lastRowFetched = true;
/*     */         
/* 420 */         if (!oldFirstFetchCompleted && this.fetchedRows.size() == 0) {
/* 421 */           this.wasEmpty = true;
/*     */         }
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
/* 436 */   public void removeRow(int ind) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 445 */   public int size() throws SQLException { return -1; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextRecord() throws SQLException {}
/*     */ 
/*     */ 
/*     */   
/* 453 */   private void notSupported() throws SQLException { throw new OperationNotSupportedException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 462 */   public void setOwner(ResultSetImpl rs) { this.owner = rs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 471 */   public ResultSetInternalMethods getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */   
/* 475 */   public boolean wasEmpty() { return this.wasEmpty; }
/*     */ 
/*     */ 
/*     */   
/* 479 */   public void setMetadata(Field[] metadata) { this.metadata = metadata; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\RowDataCursor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */