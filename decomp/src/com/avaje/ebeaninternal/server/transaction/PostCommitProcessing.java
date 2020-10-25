/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventBeans;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public final class PostCommitProcessing
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(PostCommitProcessing.class.getName());
/*     */ 
/*     */   
/*     */   private final ClusterManager clusterManager;
/*     */ 
/*     */   
/*     */   private final LuceneIndexManager luceneIndexManager;
/*     */ 
/*     */   
/*     */   private final SpiTransaction transaction;
/*     */ 
/*     */   
/*     */   private final TransactionEvent event;
/*     */   
/*     */   private final String serverName;
/*     */   
/*     */   private final TransactionManager manager;
/*     */   
/*     */   private final List<PersistRequestBean<?>> persistBeanRequests;
/*     */   
/*     */   private final BeanPersistIdMap beanPersistIdMap;
/*     */   
/*     */   private final BeanDeltaMap beanDeltaMap;
/*     */   
/*     */   private final RemoteTransactionEvent remoteTransactionEvent;
/*     */   
/*     */   private final DeleteByIdMap deleteByIdMap;
/*     */ 
/*     */   
/*     */   public PostCommitProcessing(ClusterManager clusterManager, LuceneIndexManager luceneIndexManager, TransactionManager manager, SpiTransaction transaction, TransactionEvent event) {
/*  75 */     this.clusterManager = clusterManager;
/*  76 */     this.luceneIndexManager = luceneIndexManager;
/*  77 */     this.manager = manager;
/*  78 */     this.serverName = manager.getServerName();
/*  79 */     this.transaction = transaction;
/*  80 */     this.event = event;
/*  81 */     this.deleteByIdMap = event.getDeleteByIdMap();
/*  82 */     this.persistBeanRequests = createPersistBeanRequests();
/*     */     
/*  84 */     this.beanPersistIdMap = createBeanPersistIdMap();
/*  85 */     this.beanDeltaMap = new BeanDeltaMap(event.getBeanDeltas());
/*     */     
/*  87 */     this.remoteTransactionEvent = createRemoteTransactionEvent();
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyLocalCacheIndex() {
/*  92 */     if (this.luceneIndexManager.isLuceneAvailable()) {
/*  93 */       this.luceneIndexManager.processEvent(this.remoteTransactionEvent, this.transaction);
/*     */     }
/*     */ 
/*     */     
/*  97 */     processTableEvents(this.event.getEventTables());
/*     */ 
/*     */     
/* 100 */     this.event.notifyCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processTableEvents(TransactionEventTable tableEvents) {
/* 109 */     if (tableEvents != null && !tableEvents.isEmpty()) {
/*     */       
/* 111 */       BeanDescriptorManager dm = this.manager.getBeanDescriptorManager();
/* 112 */       for (TransactionEventTable.TableIUD tableIUD : tableEvents.values()) {
/* 113 */         dm.cacheNotify(tableIUD);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void notifyCluster() {
/* 119 */     if (this.remoteTransactionEvent != null && !this.remoteTransactionEvent.isEmpty()) {
/*     */       
/* 121 */       if (this.manager.getClusterDebugLevel() > 0 || logger.isLoggable(Level.FINE)) {
/* 122 */         logger.info("Cluster Send: " + this.remoteTransactionEvent.toString());
/*     */       }
/*     */       
/* 125 */       this.clusterManager.broadcast(this.remoteTransactionEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Runnable notifyPersistListeners() {
/* 130 */     return new Runnable()
/*     */       {
/* 132 */         public void run() { PostCommitProcessing.this.localPersistListenersNotify(); }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private void localPersistListenersNotify() {
/* 138 */     if (this.persistBeanRequests != null) {
/* 139 */       for (int i = 0; i < this.persistBeanRequests.size(); i++) {
/* 140 */         ((PersistRequestBean)this.persistBeanRequests.get(i)).notifyLocalPersistListener();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private List<PersistRequestBean<?>> createPersistBeanRequests() {
/* 146 */     TransactionEventBeans eventBeans = this.event.getEventBeans();
/* 147 */     if (eventBeans != null) {
/* 148 */       return eventBeans.getRequests();
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private BeanPersistIdMap createBeanPersistIdMap() {
/* 155 */     if (this.persistBeanRequests == null) {
/* 156 */       return null;
/*     */     }
/*     */     
/* 159 */     BeanPersistIdMap m = new BeanPersistIdMap();
/* 160 */     for (int i = 0; i < this.persistBeanRequests.size(); i++) {
/* 161 */       ((PersistRequestBean)this.persistBeanRequests.get(i)).addToPersistMap(m);
/*     */     }
/* 163 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private RemoteTransactionEvent createRemoteTransactionEvent() {
/* 168 */     if (!this.clusterManager.isClustering() && !this.luceneIndexManager.isLuceneAvailable()) {
/* 169 */       return null;
/*     */     }
/*     */     
/* 172 */     RemoteTransactionEvent remoteTransactionEvent = new RemoteTransactionEvent(this.serverName);
/*     */     
/* 174 */     if (this.beanDeltaMap != null) {
/* 175 */       for (BeanDeltaList deltaList : this.beanDeltaMap.deltaLists()) {
/* 176 */         remoteTransactionEvent.addBeanDeltaList(deltaList);
/*     */       }
/*     */     }
/*     */     
/* 180 */     if (this.beanPersistIdMap != null) {
/* 181 */       for (BeanPersistIds beanPersist : this.beanPersistIdMap.values()) {
/* 182 */         remoteTransactionEvent.addBeanPersistIds(beanPersist);
/*     */       }
/*     */     }
/*     */     
/* 186 */     if (this.deleteByIdMap != null) {
/* 187 */       remoteTransactionEvent.setDeleteByIdMap(this.deleteByIdMap);
/*     */     }
/*     */     
/* 190 */     TransactionEventTable eventTables = this.event.getEventTables();
/* 191 */     if (eventTables != null && !eventTables.isEmpty()) {
/* 192 */       for (TransactionEventTable.TableIUD tableIUD : eventTables.values()) {
/* 193 */         remoteTransactionEvent.addTableIUD(tableIUD);
/*     */       }
/*     */     }
/*     */     
/* 197 */     Set<IndexInvalidate> indexInvalidations = this.event.getIndexInvalidations();
/* 198 */     if (indexInvalidations != null) {
/* 199 */       for (IndexInvalidate indexInvalidate : indexInvalidations) {
/* 200 */         remoteTransactionEvent.addIndexInvalidate(indexInvalidate);
/*     */       }
/*     */     }
/*     */     
/* 204 */     return remoteTransactionEvent;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\PostCommitProcessing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */