/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebean.BackgroundExecutor;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.config.lucene.IndexDefn;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.transaction.IndexEvent;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultLuceneIndexManager
/*     */   implements LuceneIndexManager, Runnable
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(DefaultLuceneIndexManager.class.getName());
/*     */   
/*     */   private final ConcurrentHashMap<String, LIndex> indexMap;
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */   
/*     */   private final LuceneClusterIndexSync clusterIndexSync;
/*     */   
/*     */   private final BackgroundExecutor backgroundExecutor;
/*     */   
/*     */   private final Analyzer defaultAnalyzer;
/*     */   
/*     */   private final String baseDir;
/*     */   
/*     */   private final LIndexFactory indexFactory;
/*     */   
/*     */   private final boolean luceneAvailable;
/*     */   
/*     */   private final Query.UseIndex defaultUseIndex;
/*     */   
/*     */   private final String serverName;
/*     */   
/*     */   private SpiEbeanServer server;
/*     */   
/*     */   private Thread thread;
/*     */   
/*     */   private long manageFreqMillis;
/*     */ 
/*     */   
/*     */   public DefaultLuceneIndexManager(ClusterManager clusterManager, BackgroundExecutor backgroundExecutor, Analyzer defaultAnalyzer, String baseDir, String serverName, Query.UseIndex defaultUseIndex) {
/*  74 */     this.manageFreqMillis = 100L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     this.luceneAvailable = true;
/*  83 */     this.serverName = serverName;
/*  84 */     this.clusterManager = clusterManager;
/*  85 */     this.clusterIndexSync = clusterManager.getLuceneClusterIndexSync();
/*  86 */     this.backgroundExecutor = backgroundExecutor;
/*  87 */     this.defaultUseIndex = defaultUseIndex;
/*  88 */     this.defaultAnalyzer = defaultAnalyzer;
/*  89 */     this.baseDir = baseDir + File.separator + serverName + File.separator;
/*  90 */     this.indexMap = new ConcurrentHashMap();
/*  91 */     this.indexFactory = new LIndexFactory(this);
/*     */     
/*  93 */     this.thread = new Thread(this, "Ebean-" + serverName + "-LuceneManager");
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyCluster(IndexEvent event) {
/*  98 */     if (this.clusterIndexSync != null && this.clusterIndexSync.isMaster()) {
/*     */       
/* 100 */       System.out.println("-- notifyCluster commit ... ");
/*     */       
/* 102 */       RemoteTransactionEvent e = new RemoteTransactionEvent(this.serverName);
/* 103 */       e.addIndexEvent(event);
/* 104 */       this.clusterManager.broadcast(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void execute(LIndexSync indexSync) {
/* 109 */     if (this.clusterIndexSync != null) {
/* 110 */       IndexSynchRun r = new IndexSynchRun(this.clusterIndexSync, indexSync, null);
/* 111 */       this.backgroundExecutor.execute(r);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEvent(IndexEvent indexEvent) {
/* 117 */     if (this.clusterIndexSync == null) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     String masterHost = this.clusterIndexSync.getMasterHost();
/* 122 */     if (masterHost == null) {
/* 123 */       logger.warning("Master got IndexEvent " + indexEvent + " ?");
/*     */     } else {
/*     */       
/* 126 */       String idxName = indexEvent.getIndexName();
/* 127 */       if (idxName != null) {
/* 128 */         LIndex index = getIndex(idxName);
/* 129 */         if (index == null) {
/* 130 */           logger.warning("Can't find Lucene Index [" + idxName + "]");
/*     */         } else {
/* 132 */           index.queueSync(masterHost);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEvent(RemoteTransactionEvent txnEvent, SpiTransaction localTransaction) {
/* 140 */     Collection<IndexUpdates> events = IndexUpdatesBuilder.create(this.server, txnEvent);
/* 141 */     for (IndexUpdates e : events) {
/* 142 */       BeanDescriptor<?> beanDescriptor = e.getBeanDescriptor();
/* 143 */       LIndex luceneIndex = beanDescriptor.getLuceneIndex();
/* 144 */       if (luceneIndex != null) {
/* 145 */         LIndexUpdateFuture future = luceneIndex.process(e);
/* 146 */         if (localTransaction != null) {
/* 147 */           localTransaction.addIndexUpdateFuture(future);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 155 */   public LuceneClusterIndexSync getClusterIndexSync() { return this.clusterIndexSync; }
/*     */ 
/*     */ 
/*     */   
/* 159 */   public boolean isLuceneAvailable() { return this.luceneAvailable; }
/*     */ 
/*     */ 
/*     */   
/* 163 */   public Query.UseIndex getDefaultUseIndex() { return this.defaultUseIndex; }
/*     */ 
/*     */ 
/*     */   
/* 167 */   public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException { return this.indexFactory.create(indexDefn, descriptor); }
/*     */ 
/*     */ 
/*     */   
/* 171 */   public SpiEbeanServer getServer() { return this.server; }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public void setServer(SpiEbeanServer server) { this.server = server; }
/*     */ 
/*     */ 
/*     */   
/* 179 */   public Analyzer getDefaultAnalyzer() { return this.defaultAnalyzer; }
/*     */ 
/*     */   
/*     */   public void addIndex(LIndex index) throws IOException {
/* 183 */     synchronized (this.indexMap) {
/* 184 */       this.indexMap.put(index.getName(), index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 189 */   public LIndex getIndex(String name) { return (LIndex)this.indexMap.get(name); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public String getIndexDirectory(String indexName) { return this.baseDir + indexName; }
/*     */ 
/*     */   
/*     */   public void start() {
/* 197 */     this.thread.setDaemon(true);
/* 198 */     this.thread.start();
/* 199 */     logger.info("Lucene Manager started");
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 204 */     this.shutdown = true;
/* 205 */     synchronized (this.thread) {
/*     */       
/*     */       try {
/* 208 */         this.thread.wait(20000L);
/* 209 */       } catch (InterruptedException e) {
/* 210 */         logger.info("InterruptedException:" + e);
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     if (!this.shutdownComplete) {
/* 215 */       String msg = "WARNING: Shutdown of Lucene Manager did not complete?";
/* 216 */       System.err.println(msg);
/* 217 */       logger.warning(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireOnStartup() {
/* 222 */     if (this.clusterIndexSync != null && !this.clusterIndexSync.isMaster()) {
/* 223 */       String masterHost = this.clusterIndexSync.getMasterHost();
/* 224 */       if (masterHost != null) {
/* 225 */         for (LIndex index : this.indexMap.values()) {
/* 226 */           index.queueSync(masterHost);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 234 */     fireOnStartup();
/*     */     
/* 236 */     while (!this.shutdown) {
/* 237 */       synchronized (this.indexMap) {
/* 238 */         long start = System.currentTimeMillis();
/* 239 */         for (LIndex idx : this.indexMap.values()) {
/* 240 */           idx.manage(this);
/*     */         }
/* 242 */         long exeTime = System.currentTimeMillis() - start;
/* 243 */         long sleepMillis = this.manageFreqMillis - exeTime;
/* 244 */         if (sleepMillis > 0L) {
/*     */           try {
/* 246 */             Thread.sleep(sleepMillis);
/* 247 */           } catch (InterruptedException e) {
/* 248 */             logger.log(Level.INFO, "Interrupted", e);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 253 */     this.shutdownComplete = true;
/* 254 */     synchronized (this.thread) {
/* 255 */       this.thread.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class IndexSynchRun
/*     */     implements Runnable
/*     */   {
/*     */     private final LuceneClusterIndexSync clusterIndexSync;
/*     */     private final LIndex index;
/*     */     private final String masterHost;
/*     */     
/*     */     private IndexSynchRun(LuceneClusterIndexSync clusterIndexSync, LIndexSync indexSync) {
/* 267 */       this.clusterIndexSync = clusterIndexSync;
/* 268 */       this.index = indexSync.getIndex();
/* 269 */       this.masterHost = indexSync.getMasterHost();
/*     */     }
/*     */     
/*     */     public void run() {
/* 273 */       success = false;
/*     */       try {
/* 275 */         this.clusterIndexSync.sync(this.index, this.masterHost);
/* 276 */         success = true;
/* 277 */       } catch (IOException e) {
/* 278 */         String msg = "Failed to sync Lucene index " + this.index;
/* 279 */         logger.log(Level.SEVERE, msg, e);
/*     */       } finally {
/* 281 */         this.index.syncFinished(success);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\DefaultLuceneIndexManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */