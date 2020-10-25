/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*     */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*     */ import java.sql.SQLException;
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
/*     */ public class RowDataDynamic
/*     */   implements RowData
/*     */ {
/*     */   private int columnCount;
/*     */   private Field[] metadata;
/*     */   private int index;
/*     */   private MysqlIO io;
/*     */   private boolean isAfterEnd;
/*     */   private boolean noMoreRows;
/*     */   private boolean isBinaryEncoded;
/*     */   private ResultSetRow nextRow;
/*     */   private ResultSetImpl owner;
/*     */   private boolean streamerClosed;
/*     */   private boolean wasEmpty;
/*     */   private boolean useBufferRowExplicit;
/*     */   private boolean moreResultsExisted;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   class OperationNotSupportedException
/*     */     extends SQLException
/*     */   {
/*  45 */     OperationNotSupportedException() { super(Messages.getString("RowDataDynamic.10"), "S1009"); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RowDataDynamic(MysqlIO io, int colCount, Field[] fields, boolean isBinaryEncoded) throws SQLException {
/*  54 */     this.index = -1;
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.isAfterEnd = false;
/*     */     
/*  60 */     this.noMoreRows = false;
/*     */     
/*  62 */     this.isBinaryEncoded = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     this.streamerClosed = false;
/*     */     
/*  70 */     this.wasEmpty = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.io = io;
/*  95 */     this.columnCount = colCount;
/*  96 */     this.isBinaryEncoded = isBinaryEncoded;
/*  97 */     this.metadata = fields;
/*  98 */     this.exceptionInterceptor = this.io.getExceptionInterceptor();
/*  99 */     this.useBufferRowExplicit = MysqlIO.useBufferRowExplicit(this.metadata);
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
/* 111 */   public void addRow(ResultSetRow row) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public void afterLast() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public void beforeFirst() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public void beforeLast() throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 156 */     Object mutex = this;
/*     */     
/* 158 */     MySQLConnection conn = null;
/*     */     
/* 160 */     if (this.owner != null) {
/* 161 */       conn = this.owner.connection;
/*     */       
/* 163 */       if (conn != null) {
/* 164 */         mutex = conn.getMutex();
/*     */       }
/*     */     } 
/*     */     
/* 168 */     boolean hadMore = false;
/* 169 */     int howMuchMore = 0;
/*     */     
/* 171 */     synchronized (mutex) {
/*     */       
/* 173 */       while (next() != null) {
/* 174 */         hadMore = true;
/* 175 */         howMuchMore++;
/*     */         
/* 177 */         if (howMuchMore % 100 == 0) {
/* 178 */           Thread.yield();
/*     */         }
/*     */       } 
/*     */       
/* 182 */       if (conn != null) {
/* 183 */         if (!conn.getClobberStreamingResults() && conn.getNetTimeoutForStreamingResults() > 0) {
/*     */           
/* 185 */           String oldValue = conn.getServerVariable("net_write_timeout");
/*     */ 
/*     */           
/* 188 */           if (oldValue == null || oldValue.length() == 0) {
/* 189 */             oldValue = "60";
/*     */           }
/*     */           
/* 192 */           this.io.clearInputStream();
/*     */           
/* 194 */           Statement stmt = null;
/*     */           
/*     */           try {
/* 197 */             stmt = conn.createStatement();
/* 198 */             ((StatementImpl)stmt).executeSimpleNonQuery(conn, "SET net_write_timeout=" + oldValue);
/*     */           } finally {
/* 200 */             if (stmt != null) {
/* 201 */               stmt.close();
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 206 */         if (conn.getUseUsageAdvisor() && 
/* 207 */           hadMore) {
/*     */           
/* 209 */           ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(conn);
/*     */ 
/*     */           
/* 212 */           eventSink.consumeEvent(new ProfilerEvent(false, "", (this.owner.owningStatement == null) ? "N/A" : this.owner.owningStatement.currentCatalog, this.owner.connectionId, (this.owner.owningStatement == null) ? -1 : this.owner.owningStatement.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, null, Messages.getString("RowDataDynamic.2") + howMuchMore + Messages.getString("RowDataDynamic.3") + Messages.getString("RowDataDynamic.4") + Messages.getString("RowDataDynamic.5") + Messages.getString("RowDataDynamic.6") + this.owner.pointOfOrigin));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     this.metadata = null;
/* 244 */     this.owner = null;
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
/*     */   public ResultSetRow getAt(int ind) throws SQLException {
/* 257 */     notSupported();
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentRowNumber() throws SQLException {
/* 270 */     notSupported();
/*     */     
/* 272 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 279 */   public ResultSetInternalMethods getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws SQLException {
/* 290 */     boolean hasNext = (this.nextRow != null);
/*     */     
/* 292 */     if (!hasNext && !this.streamerClosed) {
/* 293 */       this.io.closeStreamer(this);
/* 294 */       this.streamerClosed = true;
/*     */     } 
/*     */     
/* 297 */     return hasNext;
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
/* 308 */   public boolean isAfterLast() throws SQLException { return this.isAfterEnd; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 319 */   public boolean isBeforeFirst() throws SQLException { return (this.index < 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public boolean isDynamic() throws SQLException { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() throws SQLException {
/* 342 */     notSupported();
/*     */     
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirst() throws SQLException {
/* 355 */     notSupported();
/*     */     
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLast() throws SQLException {
/* 368 */     notSupported();
/*     */     
/* 370 */     return false;
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
/* 382 */   public void moveRowRelative(int rows) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
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
/* 395 */     nextRecord();
/*     */     
/* 397 */     if (this.nextRow == null && !this.streamerClosed && !this.moreResultsExisted) {
/* 398 */       this.io.closeStreamer(this);
/* 399 */       this.streamerClosed = true;
/*     */     } 
/*     */     
/* 402 */     if (this.nextRow != null && 
/* 403 */       this.index != Integer.MAX_VALUE) {
/* 404 */       this.index++;
/*     */     }
/*     */ 
/*     */     
/* 408 */     return this.nextRow;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextRecord() throws SQLException {
/*     */     try {
/* 415 */       if (!this.noMoreRows) {
/* 416 */         this.nextRow = this.io.nextRow(this.metadata, this.columnCount, this.isBinaryEncoded, 1007, true, this.useBufferRowExplicit, true, null);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 421 */         if (this.nextRow == null) {
/* 422 */           this.noMoreRows = true;
/* 423 */           this.isAfterEnd = true;
/* 424 */           this.moreResultsExisted = this.io.tackOnMoreStreamingResults(this.owner);
/*     */           
/* 426 */           if (this.index == -1) {
/* 427 */             this.wasEmpty = true;
/*     */           }
/*     */         } 
/*     */       } else {
/* 431 */         this.isAfterEnd = true;
/*     */       } 
/* 433 */     } catch (SQLException sqlEx) {
/* 434 */       if (sqlEx instanceof StreamingNotifiable) {
/* 435 */         ((StreamingNotifiable)sqlEx).setWasStreamingResults();
/*     */       }
/*     */ 
/*     */       
/* 439 */       throw sqlEx;
/* 440 */     } catch (Exception ex) {
/* 441 */       String exceptionType = ex.getClass().getName();
/* 442 */       String exceptionMessage = ex.getMessage();
/*     */       
/* 444 */       exceptionMessage = exceptionMessage + Messages.getString("RowDataDynamic.7");
/* 445 */       exceptionMessage = exceptionMessage + Util.stackTraceToString(ex);
/*     */       
/* 447 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("RowDataDynamic.8") + exceptionType + Messages.getString("RowDataDynamic.9") + exceptionMessage, "S1000", this.exceptionInterceptor);
/*     */ 
/*     */ 
/*     */       
/* 451 */       sqlEx.initCause(ex);
/*     */       
/* 453 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 458 */   private void notSupported() throws SQLException { throw new OperationNotSupportedException(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   public void removeRow(int ind) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 482 */   public void setCurrentRow(int rowNumber) throws SQLException { notSupported(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 489 */   public void setOwner(ResultSetImpl rs) { this.owner = rs; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 498 */   public int size() throws SQLException { return -1; }
/*     */ 
/*     */ 
/*     */   
/* 502 */   public boolean wasEmpty() throws SQLException { return this.wasEmpty; }
/*     */ 
/*     */ 
/*     */   
/* 506 */   public void setMetadata(Field[] metadata) { this.metadata = metadata; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\RowDataDynamic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */