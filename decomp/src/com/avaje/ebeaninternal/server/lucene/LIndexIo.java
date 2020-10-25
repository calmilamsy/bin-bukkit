/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.transaction.IndexEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.store.Directory;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LIndexIo
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger(LIndexIo.class.getName());
/*     */   
/*     */   private final LuceneIndexManager manager;
/*     */   
/*     */   private final String indexDir;
/*     */   private final LIndex index;
/*     */   private final Analyzer analyzer;
/*     */   private final IndexWriter.MaxFieldLength maxFieldLength;
/*     */   private final Class<?> beanType;
/*     */   private final OrmQueryDetail ormQueryDetail;
/*     */   private final Directory directory;
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   private final IndexWriter indexWriter;
/*     */   private final LIndexIoSearcher ioSearcher;
/*     */   private final HoldAwareIndexDeletionPolicy commitDeletionPolicy;
/*     */   private final String[] updateProps;
/*     */   private final Object writeMonitor;
/*     */   private final Object workQueueMonitor;
/*     */   private final ArrayList<LIndexWork> workQueue;
/*     */   private final ArrayList<Runnable> notifyCommitRunnables;
/*     */   private long lastUpdateTime;
/*     */   private long queueCommitStart;
/*     */   private int queueCommitCount;
/*     */   private int totalCommitCount;
/*     */   private long totalCommitNanos;
/*     */   private long totalPostCommitNanos;
/*     */   
/*     */   public LIndexIo(LuceneIndexManager manager, String indexDir, LIndex index, String[] updateProps) throws IOException {
/*  82 */     this.writeMonitor = new Object();
/*     */     
/*  84 */     this.workQueueMonitor = new Object();
/*     */     
/*  86 */     this.workQueue = new ArrayList();
/*     */     
/*  88 */     this.notifyCommitRunnables = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.manager = manager;
/* 104 */     this.indexDir = indexDir;
/* 105 */     this.index = index;
/* 106 */     this.updateProps = updateProps;
/* 107 */     this.analyzer = index.getAnalyzer();
/* 108 */     this.maxFieldLength = index.getMaxFieldLength();
/* 109 */     this.beanType = index.getBeanType();
/* 110 */     this.ormQueryDetail = index.getOrmQueryDetail();
/* 111 */     this.directory = createDirectory();
/* 112 */     this.beanDescriptor = index.getBeanDescriptor();
/*     */     
/* 114 */     this.commitDeletionPolicy = new HoldAwareIndexDeletionPolicy(indexDir);
/* 115 */     this.indexWriter = createIndexWriter();
/*     */     
/* 117 */     this.ioSearcher = createIoSearcher();
/*     */   }
/*     */ 
/*     */   
/* 121 */   public LIndexVersion getLastestVersion() { return this.ioSearcher.getLastestVersion(); }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public long getLastVersion() { return this.commitDeletionPolicy.getLastVersion(); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) { return this.commitDeletionPolicy.obtainLastIndexCommitIfNewer(remoteIndexVersion); }
/*     */ 
/*     */ 
/*     */   
/* 133 */   public File getIndexDir() { return new File(this.indexDir); }
/*     */ 
/*     */ 
/*     */   
/*     */   public LIndexFileInfo getLocalFile(String fileName) {
/* 138 */     File f = new File(this.indexDir, fileName);
/* 139 */     return new LIndexFileInfo(f);
/*     */   }
/*     */ 
/*     */   
/* 143 */   public void refresh(boolean nearRealTime) { this.ioSearcher.refresh(nearRealTime); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LIndexFileInfo getFile(long remoteIndexVersion, String fileName) {
/* 149 */     this.commitDeletionPolicy.touch(remoteIndexVersion);
/*     */     
/* 151 */     File f = new File(this.indexDir, fileName);
/* 152 */     return new LIndexFileInfo(f);
/*     */   }
/*     */ 
/*     */   
/* 156 */   public void releaseIndexCommit(long remoteIndexVersion) { this.commitDeletionPolicy.releaseIndexCommit(remoteIndexVersion); }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 160 */     synchronized (this.writeMonitor) {
/*     */       try {
/* 162 */         if (this.queueCommitStart > 0L) {
/* 163 */           this.indexWriter.commit();
/*     */         }
/* 165 */       } catch (Exception e) {
/* 166 */         String msg = "Error committing queued changes for IndexWriter for " + this.indexDir;
/* 167 */         logger.log(Level.SEVERE, msg, e);
/*     */         
/* 169 */         e.printStackTrace();
/*     */       } finally {
/*     */         try {
/* 172 */           this.indexWriter.close();
/* 173 */         } catch (Exception e) {
/* 174 */           String msg = "Error closing IndexWriter for " + this.indexDir;
/* 175 */           logger.log(Level.SEVERE, msg, e);
/*     */           
/* 177 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void manage(LuceneIndexManager indexManager) {
/* 184 */     processWorkQueue();
/* 185 */     commit(false);
/*     */   }
/*     */   
/*     */   protected void addWorkToQueue(LIndexWork work) {
/* 189 */     synchronized (this.workQueueMonitor) {
/* 190 */       this.workQueue.add(work);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processWorkQueue() {
/* 196 */     synchronized (this.workQueueMonitor) {
/* 197 */       if (!this.workQueue.isEmpty()) {
/*     */         
/* 199 */         LIndexWork.WorkType maxWorkType = null;
/*     */         
/* 201 */         for (i = 0; i < this.workQueue.size(); i++) {
/* 202 */           LIndexWork work = (LIndexWork)this.workQueue.get(i);
/* 203 */           if (maxWorkType == null || maxWorkType.ordinal() < work.getWorkType().ordinal()) {
/* 204 */             maxWorkType = work.getWorkType();
/*     */           }
/*     */         } 
/* 207 */         List<LIndexWork> workQueueClone = (List)this.workQueue.clone();
/* 208 */         this.workQueue.clear();
/*     */         
/* 210 */         Callable<Integer> workCallable = getWorkCallable(maxWorkType, workQueueClone);
/* 211 */         FutureTask<Integer> ft = new FutureTask<Integer>(workCallable);
/*     */         
/* 213 */         for (int i = 0; i < workQueueClone.size(); i++) {
/* 214 */           ((LIndexWork)workQueueClone.get(i)).getFuture().setTask(ft);
/*     */         }
/*     */         
/* 217 */         this.manager.getServer().getBackgroundExecutor().execute(ft);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Callable<Integer> getWorkCallable(LIndexWork.WorkType maxWorkType, List<LIndexWork> workQueueClone) {
/* 224 */     switch (maxWorkType) {
/*     */       case REBUILD:
/* 226 */         return newRebuildCallable(workQueueClone);
/*     */       case QUERY_UPDATE:
/* 228 */         return newQueryUpdateCallable(workQueueClone);
/*     */       case TXN_UPDATE:
/* 230 */         return newTxnUpdateCallable(workQueueClone);
/*     */     } 
/*     */     
/* 233 */     throw new IllegalStateException("Unknown workType " + maxWorkType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Callable<Integer> newTxnUpdateCallable(List<LIndexWork> workQueueClone) {
/* 239 */     final List<LIndexWork> updates = workQueueClone;
/*     */     
/* 241 */     return new Callable<Integer>()
/*     */       {
/* 243 */         public String toString() { return "TxnUpdate"; }
/*     */ 
/*     */ 
/*     */         
/*     */         public Integer call() throws Exception {
/* 248 */           int totalDocs = 0;
/*     */           
/* 250 */           for (int i = 0; i < updates.size(); i++) {
/* 251 */             LIndexWork lIndexWork = (LIndexWork)updates.get(i);
/* 252 */             IndexUpdates indexUpdates = lIndexWork.getIndexUpdates();
/* 253 */             LIndexDeltaHandler h = LIndexIo.this.createDeltaHandler(indexUpdates);
/* 254 */             totalDocs += h.process();
/*     */           } 
/*     */           
/* 257 */           LIndexIo.this.queueCommit(updates);
/*     */           
/* 259 */           return Integer.valueOf(totalDocs);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/* 265 */   private Callable<Integer> newRebuildCallable(List<LIndexWork> workQueueClone) { return new QueryUpdater(true, workQueueClone, null); }
/*     */ 
/*     */ 
/*     */   
/* 269 */   private Callable<Integer> newQueryUpdateCallable(List<LIndexWork> workQueueClone) { return new QueryUpdater(false, workQueueClone, null); }
/*     */   
/*     */   class QueryUpdater
/*     */     extends Object
/*     */     implements Callable<Integer> {
/*     */     private final boolean rebuild;
/*     */     private final List<LIndexWork> workQueueClone;
/*     */     
/*     */     private QueryUpdater(boolean rebuild, List<LIndexWork> workQueueClone) {
/* 278 */       this.rebuild = rebuild;
/* 279 */       this.workQueueClone = workQueueClone;
/*     */     }
/*     */ 
/*     */     
/* 283 */     public String toString() { return this.rebuild ? "Rebuild" : "QueryUpdate"; }
/*     */ 
/*     */     
/*     */     public Integer call() throws Exception {
/* 287 */       if (this.rebuild) {
/* 288 */         return Integer.valueOf(LIndexIo.this.rebuildIndex(this.workQueueClone));
/*     */       }
/* 290 */       return Integer.valueOf(LIndexIo.this.updateIndex(this.workQueueClone));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LIndexDeltaHandler createDeltaHandler(IndexUpdates indexUpdates) {
/* 297 */     LIndexSearch search = getIndexSearch();
/* 298 */     IndexWriter indexWriter = this.indexWriter;
/* 299 */     DocFieldWriter docFieldWriter = this.index.createDocFieldWriter();
/* 300 */     return new LIndexDeltaHandler(this.index, search, indexWriter, this.analyzer, this.beanDescriptor, docFieldWriter, indexUpdates);
/*     */   }
/*     */ 
/*     */   
/* 304 */   public LIndexSearch getIndexSearch() { return this.ioSearcher.getIndexSearch(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void queueCommit(List<LIndexWork> workQueueClone) {
/* 321 */     synchronized (this.workQueueMonitor) {
/* 322 */       if (this.queueCommitStart == 0L) {
/* 323 */         this.queueCommitStart = System.currentTimeMillis();
/*     */       }
/* 325 */       this.queueCommitCount++;
/*     */       
/* 327 */       for (LIndexWork w : workQueueClone)
/*     */       {
/* 329 */         this.notifyCommitRunnables.add(w.getFuture().getCommitRunnable());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addNotifyCommitRunnable(Runnable r) {
/* 335 */     synchronized (this.workQueueMonitor) {
/* 336 */       this.notifyCommitRunnables.add(r);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected long getQueueCommitStart(boolean reset) {
/* 341 */     synchronized (this.workQueueMonitor) {
/* 342 */       long start = this.queueCommitStart;
/* 343 */       if (reset) {
/* 344 */         this.queueCommitStart = 0L;
/* 345 */         this.queueCommitCount = 0;
/*     */       } 
/* 347 */       return start;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean commit(boolean force) {
/* 357 */     synchronized (this.writeMonitor) {
/*     */       
/* 359 */       long start = 0L;
/* 360 */       long count = 0L;
/* 361 */       ArrayList<Runnable> notifyRunnables = new ArrayList<Runnable>();
/*     */       
/* 363 */       synchronized (this.workQueueMonitor) {
/* 364 */         start = this.queueCommitStart;
/* 365 */         count = this.queueCommitCount;
/* 366 */         this.queueCommitStart = 0L;
/* 367 */         this.queueCommitCount = 0;
/* 368 */         notifyRunnables.addAll(this.notifyCommitRunnables);
/* 369 */         this.notifyCommitRunnables.clear();
/*     */       } 
/*     */       
/*     */       try {
/* 373 */         if (!force && start == 0L) {
/*     */ 
/*     */ 
/*     */           
/* 377 */           if (!notifyRunnables.isEmpty())
/*     */           {
/* 379 */             for (int i = 0; i < notifyRunnables.size(); i++) {
/* 380 */               addNotifyCommitRunnable((Runnable)notifyRunnables.get(i));
/*     */             }
/*     */           }
/* 383 */           return false;
/*     */         } 
/* 385 */         if (logger.isLoggable(Level.INFO)) {
/*     */           String delayMsg;
/* 387 */           if (this.queueCommitStart > 0L) {
/* 388 */             long delay = System.currentTimeMillis() - start;
/* 389 */             delayMsg = " queueDelayMillis:" + delay + " queueCount:" + count;
/*     */           } else {
/* 391 */             delayMsg = "";
/*     */           } 
/* 393 */           String m = "Lucene commit " + this.indexDir + delayMsg;
/* 394 */           logger.info(m);
/*     */         } 
/* 396 */         long nanoStart = System.nanoTime();
/*     */ 
/*     */         
/* 399 */         this.indexWriter.commit();
/*     */ 
/*     */         
/* 402 */         long nanoCommit = System.nanoTime();
/* 403 */         long nanoCommitExe = nanoCommit - nanoStart;
/*     */ 
/*     */         
/* 406 */         this.ioSearcher.postCommit();
/*     */         
/* 408 */         for (int i = 0; i < notifyRunnables.size(); i++) {
/* 409 */           ((Runnable)notifyRunnables.get(i)).run();
/*     */         }
/*     */         
/* 412 */         long nanoPostCommitExe = System.nanoTime() - nanoCommitExe;
/*     */         
/* 414 */         this.totalCommitCount++;
/* 415 */         this.totalCommitNanos += nanoCommitExe;
/* 416 */         this.totalPostCommitNanos += nanoPostCommitExe;
/*     */         
/* 418 */         IndexEvent indexEvent = new IndexEvent(true, this.index.getName());
/* 419 */         this.manager.notifyCluster(indexEvent);
/*     */         
/* 421 */         return true;
/*     */       }
/* 423 */       catch (IOException e) {
/* 424 */         String msg = "Error committing changes on index " + this.indexDir;
/* 425 */         throw new PersistenceLuceneException(msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int rebuildIndex(List<LIndexWork> workQueueClone) throws IOException {
/* 432 */     synchronized (this.writeMonitor) {
/*     */       
/* 434 */       logger.info("Lucene rebuild " + this.indexDir);
/*     */ 
/*     */       
/*     */       try {
/* 438 */         this.indexWriter.deleteAll();
/* 439 */         this.lastUpdateTime = System.currentTimeMillis();
/* 440 */         SpiQuery<?> query = createQuery();
/*     */         
/* 442 */         WriteListener writeListener = new WriteListener(this.index, this.indexWriter, false, null);
/* 443 */         query.setListener(writeListener);
/*     */         
/* 445 */         this.manager.getServer().findList(query, null);
/*     */         
/* 447 */         return writeListener.getCount();
/*     */       } finally {
/*     */         
/* 450 */         queueCommit(workQueueClone);
/* 451 */         commit(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int updateIndex(List<LIndexWork> workQueueClone) throws IOException {
/* 458 */     synchronized (this.writeMonitor) {
/*     */       
/* 460 */       logger.info("Lucene update " + this.indexDir);
/*     */       
/*     */       try {
/* 463 */         long updateTime = System.currentTimeMillis();
/* 464 */         SpiQuery<?> query = createUpdateQuery();
/* 465 */         this.lastUpdateTime = updateTime;
/*     */         
/* 467 */         WriteListener writeListener = new WriteListener(this.index, this.indexWriter, true, null);
/* 468 */         query.setListener(writeListener);
/*     */         
/* 470 */         this.manager.getServer().findList(query, null);
/*     */         
/* 472 */         return writeListener.getCount();
/*     */       } finally {
/*     */         
/* 475 */         queueCommit(workQueueClone);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SpiQuery<?> createUpdateQuery() {
/* 482 */     SpiQuery<?> q = createQuery();
/* 483 */     Junction<?> disjunction = q.where().disjunction();
/*     */     
/* 485 */     Timestamp lastUpdate = new Timestamp(this.lastUpdateTime);
/*     */     
/* 487 */     for (int i = 0; i < this.updateProps.length; i++) {
/* 488 */       disjunction.ge(this.updateProps[i], lastUpdate);
/*     */     }
/*     */     
/* 491 */     return q;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SpiQuery<?> createQuery() {
/* 496 */     SpiEbeanServer server = this.manager.getServer();
/* 497 */     SpiQuery<?> query = (SpiQuery)server.createQuery(this.beanType);
/* 498 */     query.setUseIndex(Query.UseIndex.NO);
/* 499 */     query.getDetail().tuneFetchProperties(this.ormQueryDetail);
/*     */     
/* 501 */     return query;
/*     */   }
/*     */   
/*     */   private Directory createDirectory() throws IOException {
/* 505 */     File dir = new File(this.indexDir);
/* 506 */     return FSDirectory.open(dir);
/*     */   }
/*     */   
/*     */   private IndexWriter createIndexWriter() {
/*     */     try {
/* 511 */       boolean create = true;
/* 512 */       return new IndexWriter(this.directory, this.analyzer, create, this.commitDeletionPolicy, this.maxFieldLength);
/* 513 */     } catch (IOException e) {
/* 514 */       String msg = "Error getting Lucene IndexWriter for " + this.indexDir;
/* 515 */       throw new PersistenceLuceneException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 521 */   private LIndexIoSearcher createIoSearcher() { return new LIndexIoSearcherDefault(this.indexWriter, this.index.getName()); }
/*     */ 
/*     */   
/*     */   private static class WriteListener
/*     */     implements QueryListener
/*     */   {
/*     */     private final boolean updateMode;
/*     */     private final LIndex index;
/*     */     private final BeanDescriptor beanDescriptor;
/*     */     
/*     */     private WriteListener(LIndex index, IndexWriter indexWriter, boolean updateMode) {
/* 532 */       this.document = new Document();
/*     */ 
/*     */ 
/*     */       
/* 536 */       this.updateMode = updateMode;
/* 537 */       this.index = index;
/* 538 */       this.beanDescriptor = index.getBeanDescriptor();
/* 539 */       this.indexWriter = indexWriter;
/* 540 */       this.docFieldWriter = index.createDocFieldWriter();
/*     */     }
/*     */     private final IndexWriter indexWriter; private final DocFieldWriter docFieldWriter; private final Document document; private int count;
/*     */     public void process(Object bean) {
/*     */       try {
/* 545 */         if (this.updateMode) {
/* 546 */           Object id = this.beanDescriptor.getId(bean);
/* 547 */           Term term = this.index.createIdTerm(id);
/* 548 */           this.indexWriter.deleteDocuments(term);
/*     */         } 
/* 550 */         this.docFieldWriter.writeValue(bean, this.document);
/* 551 */         this.indexWriter.addDocument(this.document);
/* 552 */         this.count++;
/* 553 */       } catch (Exception e) {
/* 554 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 559 */     public int getCount() { return this.count; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexIo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */