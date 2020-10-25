/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import com.avaje.ebean.TxIsolation;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransactionManager
/*     */ {
/*     */   private final LuceneIndexManager luceneIndexManager;
/*     */   private final BeanDescriptorManager beanDescriptorManager;
/*     */   private LogLevel logLevel;
/*     */   private final TransactionLogManager transLogger;
/*     */   private final String prefix;
/*     */   private final String externalTransPrefix;
/*     */   private final DataSource dataSource;
/*     */   private final OnQueryOnly onQueryOnly;
/*  54 */   private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
/*     */   private final boolean defaultBatchMode;
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   private final ClusterManager clusterManager;
/*     */   private final int commitDebugLevel;
/*     */   private final String serverName;
/*     */   private AtomicLong transactionCounter;
/*     */   private int clusterDebugLevel;
/*     */   
/*     */   public enum OnQueryOnly {
/*  64 */     ROLLBACK,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     CLOSE_ON_READCOMMITTED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     COMMIT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionManager(ClusterManager clusterManager, LuceneIndexManager luceneIndexManager, BackgroundExecutor backgroundExecutor, ServerConfig config, BeanDescriptorManager descMgr) {
/* 122 */     this.transactionCounter = new AtomicLong(1000L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.beanDescriptorManager = descMgr;
/* 133 */     this.clusterManager = clusterManager;
/* 134 */     this.luceneIndexManager = luceneIndexManager;
/* 135 */     this.serverName = config.getName();
/*     */     
/* 137 */     this.logLevel = config.getLoggingLevel();
/* 138 */     this.transLogger = new TransactionLogManager(config);
/* 139 */     this.backgroundExecutor = backgroundExecutor;
/* 140 */     this.dataSource = config.getDataSource();
/*     */ 
/*     */     
/* 143 */     this.commitDebugLevel = GlobalProperties.getInt("ebean.commit.debuglevel", 0);
/* 144 */     this.clusterDebugLevel = GlobalProperties.getInt("ebean.cluster.debuglevel", 0);
/*     */     
/* 146 */     this.defaultBatchMode = config.isPersistBatching();
/*     */     
/* 148 */     this.prefix = GlobalProperties.get("transaction.prefix", "");
/* 149 */     this.externalTransPrefix = GlobalProperties.get("transaction.prefix", "e");
/*     */     
/* 151 */     String value = GlobalProperties.get("transaction.onqueryonly", "ROLLBACK").toUpperCase().trim();
/* 152 */     this.onQueryOnly = getOnQueryOnly(value, this.dataSource);
/*     */   }
/*     */ 
/*     */   
/* 156 */   public void shutdown() { this.transLogger.shutdown(); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public BeanDescriptorManager getBeanDescriptorManager() { return this.beanDescriptorManager; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public LogLevel getTransactionLogLevel() { return this.logLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public void setTransactionLogLevel(LogLevel logLevel) { this.logLevel = logLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OnQueryOnly getOnQueryOnly(String onQueryOnly, DataSource ds) {
/* 192 */     if (onQueryOnly.equals("COMMIT")) {
/* 193 */       return OnQueryOnly.COMMIT;
/*     */     }
/* 195 */     if (onQueryOnly.startsWith("CLOSE")) {
/* 196 */       if (!isReadCommitedIsolation(ds)) {
/* 197 */         String m = "transaction.queryonlyclose is true but the transaction Isolation Level is not READ_COMMITTED";
/* 198 */         throw new PersistenceException(m);
/*     */       } 
/* 200 */       return OnQueryOnly.CLOSE_ON_READCOMMITTED;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return OnQueryOnly.ROLLBACK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isReadCommitedIsolation(DataSource ds) {
/* 212 */     c = null;
/*     */     try {
/* 214 */       c = ds.getConnection();
/*     */       
/* 216 */       int isolationLevel = c.getTransactionIsolation();
/* 217 */       return (isolationLevel == 2);
/*     */     }
/* 219 */     catch (SQLException ex) {
/* 220 */       String m = "Errored trying to determine the default Isolation Level";
/* 221 */       throw new PersistenceException(m, ex);
/*     */     } finally {
/*     */       
/*     */       try {
/* 225 */         if (c != null) {
/* 226 */           c.close();
/*     */         }
/* 228 */       } catch (SQLException ex) {
/* 229 */         logger.log(Level.SEVERE, "closing connection", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 235 */   public String getServerName() { return this.serverName; }
/*     */ 
/*     */ 
/*     */   
/* 239 */   public DataSource getDataSource() { return this.dataSource; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public int getClusterDebugLevel() { return this.clusterDebugLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public void setClusterDebugLevel(int clusterDebugLevel) { this.clusterDebugLevel = clusterDebugLevel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public OnQueryOnly getOnQueryOnly() { return this.onQueryOnly; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   public TransactionLogManager getLogger() { return this.transLogger; }
/*     */ 
/*     */   
/*     */   public void log(TransactionLogBuffer logBuffer) {
/* 271 */     if (!logBuffer.isEmpty()) {
/* 272 */       this.transLogger.log(logBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   public SpiTransaction wrapExternalConnection(Connection c) { return wrapExternalConnection(this.externalTransPrefix + c.hashCode(), c); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction wrapExternalConnection(String id, Connection c) {
/* 289 */     ExternalJdbcTransaction t = new ExternalJdbcTransaction(id, true, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */     
/* 293 */     if (this.defaultBatchMode) {
/* 294 */       t.setBatchMode(true);
/*     */     }
/*     */     
/* 297 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpiTransaction createTransaction(boolean explicit, int isolationLevel) {
/*     */     try {
/* 305 */       long id = this.transactionCounter.incrementAndGet();
/*     */       
/* 307 */       Connection c = this.dataSource.getConnection();
/*     */       
/* 309 */       JdbcTransaction t = new JdbcTransaction(this.prefix + id, explicit, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */       
/* 313 */       if (this.defaultBatchMode) {
/* 314 */         t.setBatchMode(true);
/*     */       }
/* 316 */       if (isolationLevel > -1) {
/* 317 */         c.setTransactionIsolation(isolationLevel);
/*     */       }
/*     */       
/* 320 */       if (this.commitDebugLevel >= 3) {
/* 321 */         String msg = "Transaction [" + t.getId() + "] begin";
/* 322 */         if (isolationLevel > -1) {
/* 323 */           TxIsolation txi = TxIsolation.fromLevel(isolationLevel);
/* 324 */           msg = msg + " isolationLevel[" + txi + "]";
/*     */         } 
/* 326 */         logger.info(msg);
/*     */       } 
/*     */       
/* 329 */       return t;
/*     */     }
/* 331 */     catch (SQLException ex) {
/* 332 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SpiTransaction createQueryTransaction() {
/*     */     try {
/* 338 */       long id = this.transactionCounter.incrementAndGet();
/* 339 */       Connection c = this.dataSource.getConnection();
/*     */       
/* 341 */       JdbcTransaction t = new JdbcTransaction(this.prefix + id, false, this.logLevel, c, this);
/*     */ 
/*     */ 
/*     */       
/* 345 */       if (this.defaultBatchMode) {
/* 346 */         t.setBatchMode(true);
/*     */       }
/*     */       
/* 349 */       if (this.commitDebugLevel >= 3) {
/* 350 */         logger.info("Transaction [" + t.getId() + "] begin - queryOnly");
/*     */       }
/*     */       
/* 353 */       return t;
/*     */     }
/* 355 */     catch (SQLException ex) {
/* 356 */       throw new PersistenceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfRollback(SpiTransaction transaction, Throwable cause) {
/*     */     try {
/* 366 */       if (transaction.isLogSummary() || this.commitDebugLevel >= 1) {
/* 367 */         String msg = "Rollback";
/* 368 */         if (cause != null) {
/* 369 */           msg = msg + " error: " + formatThrowable(cause);
/*     */         }
/* 371 */         if (transaction.isLogSummary()) {
/* 372 */           transaction.logInternal(msg);
/*     */         }
/*     */         
/* 375 */         if (this.commitDebugLevel >= 1) {
/* 376 */           logger.info("Transaction [" + transaction.getId() + "] " + msg);
/*     */         }
/*     */       } 
/*     */       
/* 380 */       log(transaction.getLogBuffer());
/*     */     }
/* 382 */     catch (Exception ex) {
/* 383 */       String m = "Potentially Transaction Log incomplete due to error:";
/* 384 */       logger.log(Level.SEVERE, m, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfQueryOnly(boolean onCommit, SpiTransaction transaction, Throwable cause) {
/*     */     try {
/* 394 */       if (this.commitDebugLevel >= 2) {
/*     */         String msg;
/* 396 */         if (onCommit) {
/* 397 */           msg = "Commit queryOnly";
/*     */         } else {
/*     */           
/* 400 */           msg = "Rollback queryOnly";
/* 401 */           if (cause != null) {
/* 402 */             msg = msg + " error: " + formatThrowable(cause);
/*     */           }
/*     */         } 
/* 405 */         if (transaction.isLogSummary()) {
/* 406 */           transaction.logInternal(msg);
/*     */         }
/* 408 */         logger.info("Transaction [" + transaction.getId() + "] " + msg);
/*     */       } 
/*     */       
/* 411 */       log(transaction.getLogBuffer());
/*     */     }
/* 413 */     catch (Exception ex) {
/* 414 */       String m = "Potentially Transaction Log incomplete due to error:";
/* 415 */       logger.log(Level.SEVERE, m, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatThrowable(Throwable e) {
/* 420 */     if (e == null) {
/* 421 */       return "";
/*     */     }
/* 423 */     StringBuilder sb = new StringBuilder();
/* 424 */     formatThrowable(e, sb);
/* 425 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatThrowable(Throwable e, StringBuilder sb) {
/* 430 */     sb.append(e.toString());
/* 431 */     StackTraceElement[] stackTrace = e.getStackTrace();
/* 432 */     if (stackTrace.length > 0) {
/* 433 */       sb.append(" stack0: ");
/* 434 */       sb.append(stackTrace[0]);
/*     */     } 
/* 436 */     Throwable cause = e.getCause();
/* 437 */     if (cause != null) {
/* 438 */       sb.append(" cause: ");
/* 439 */       formatThrowable(cause, sb);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOfCommit(SpiTransaction transaction) {
/*     */     try {
/* 450 */       log(transaction.getLogBuffer());
/*     */       
/* 452 */       PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, transaction, transaction.getEvent());
/*     */       
/* 454 */       postCommit.notifyLocalCacheIndex();
/* 455 */       postCommit.notifyCluster();
/*     */ 
/*     */       
/* 458 */       this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
/*     */       
/* 460 */       if (this.commitDebugLevel >= 1) {
/* 461 */         logger.info("Transaction [" + transaction.getId() + "] commit");
/*     */       }
/* 463 */     } catch (Exception ex) {
/* 464 */       String m = "NotifyOfCommit failed. Cache/Lucene potentially not notified.";
/* 465 */       logger.log(Level.SEVERE, m, ex);
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
/*     */ 
/*     */   
/*     */   public void externalModification(TransactionEventTable tableEvents) {
/* 481 */     TransactionEvent event = new TransactionEvent();
/* 482 */     event.add(tableEvents);
/*     */     
/* 484 */     PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, null, event);
/*     */ 
/*     */     
/* 487 */     postCommit.notifyLocalCacheIndex();
/*     */     
/* 489 */     this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remoteTransactionEvent(RemoteTransactionEvent remoteEvent) {
/* 498 */     if (this.clusterDebugLevel > 0 || logger.isLoggable(Level.FINE)) {
/* 499 */       logger.info("Cluster Received: " + remoteEvent.toString());
/*     */     }
/*     */     
/* 502 */     this.luceneIndexManager.processEvent(remoteEvent, null);
/*     */     
/* 504 */     List<TransactionEventTable.TableIUD> tableIUDList = remoteEvent.getTableIUDList();
/* 505 */     if (tableIUDList != null) {
/* 506 */       for (int i = 0; i < tableIUDList.size(); i++) {
/* 507 */         TransactionEventTable.TableIUD tableIUD = (TransactionEventTable.TableIUD)tableIUDList.get(i);
/* 508 */         this.beanDescriptorManager.cacheNotify(tableIUD);
/*     */       } 
/*     */     }
/*     */     
/* 512 */     List<BeanPersistIds> beanPersistList = remoteEvent.getBeanPersistList();
/* 513 */     if (beanPersistList != null) {
/* 514 */       for (int i = 0; i < beanPersistList.size(); i++) {
/* 515 */         BeanPersistIds beanPersist = (BeanPersistIds)beanPersistList.get(i);
/* 516 */         beanPersist.notifyCacheAndListener();
/*     */       } 
/*     */     }
/*     */     
/* 520 */     List<IndexEvent> indexEventList = remoteEvent.getIndexEventList();
/* 521 */     if (indexEventList != null)
/* 522 */       for (int i = 0; i < indexEventList.size(); i++) {
/* 523 */         IndexEvent indexEvent = (IndexEvent)indexEventList.get(i);
/* 524 */         this.luceneIndexManager.processEvent(indexEvent);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */