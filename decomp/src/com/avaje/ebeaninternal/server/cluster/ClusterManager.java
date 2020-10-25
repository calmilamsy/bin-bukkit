/*     */ package com.avaje.ebeaninternal.server.cluster;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.cluster.mcast.McastClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.socket.SocketClusterBroadcast;
/*     */ import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterFactory;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ClusterManager
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(ClusterManager.class.getName());
/*     */   
/*     */   private final ClusterBroadcast broadcast;
/*     */   
/*  42 */   private final ConcurrentHashMap<String, EbeanServer> serverMap = new ConcurrentHashMap();
/*     */   
/*     */   private LuceneClusterListener luceneListener;
/*     */   
/*     */   private LuceneClusterIndexSync luceneIndexSync;
/*     */   
/*     */   private boolean started;
/*     */ 
/*     */   
/*     */   public ClusterManager() {
/*  52 */     String clusterType = GlobalProperties.get("ebean.cluster.type", null);
/*  53 */     if (clusterType == null || clusterType.trim().length() == 0) {
/*     */       
/*  55 */       this.broadcast = null;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  60 */       SLuceneClusterFactory sLuceneClusterFactory = new SLuceneClusterFactory();
/*     */       
/*  62 */       int lucenePort = GlobalProperties.getInt("ebean.cluster.lucene.port", 9991);
/*  63 */       this.luceneListener = sLuceneClusterFactory.createListener(this, lucenePort);
/*     */       
/*  65 */       String masterHostPort = GlobalProperties.get("ebean.cluster.lucene.masterHostPort", null);
/*  66 */       this.luceneIndexSync = sLuceneClusterFactory.createIndexSync();
/*  67 */       this.luceneIndexSync.setMasterHost(masterHostPort);
/*  68 */       this.luceneIndexSync.setMode((masterHostPort == null) ? LuceneClusterIndexSync.Mode.MASTER_MODE : LuceneClusterIndexSync.Mode.SLAVE_MODE);
/*     */ 
/*     */       
/*  71 */       logger.info("... luceneListener using [" + lucenePort + "]");
/*     */       try {
/*  73 */         if ("mcast".equalsIgnoreCase(clusterType)) {
/*  74 */           this.broadcast = new McastClusterManager();
/*     */         }
/*  76 */         else if ("socket".equalsIgnoreCase(clusterType)) {
/*  77 */           this.broadcast = new SocketClusterBroadcast();
/*     */         } else {
/*     */           
/*  80 */           logger.info("Clustering using [" + clusterType + "]");
/*  81 */           this.broadcast = (ClusterBroadcast)ClassUtil.newInstance(clusterType);
/*     */         }
/*     */       
/*  84 */       } catch (Exception e) {
/*  85 */         String msg = "Error initialising ClusterManager type [" + clusterType + "]";
/*  86 */         logger.log(Level.SEVERE, msg, e);
/*  87 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerServer(EbeanServer server) {
/*  93 */     synchronized (this.serverMap) {
/*  94 */       if (!this.started) {
/*  95 */         startup();
/*     */       }
/*  97 */       this.serverMap.put(server.getName(), server);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 102 */   public LuceneClusterIndexSync getLuceneClusterIndexSync() { return this.luceneIndexSync; }
/*     */ 
/*     */   
/*     */   public EbeanServer getServer(String name) {
/* 106 */     synchronized (this.serverMap) {
/* 107 */       return (EbeanServer)this.serverMap.get(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startup() {
/* 112 */     this.started = true;
/* 113 */     if (this.broadcast != null) {
/* 114 */       this.broadcast.startup(this);
/*     */     }
/* 116 */     if (this.luceneListener != null) {
/* 117 */       this.luceneListener.startup();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public boolean isClustering() { return (this.broadcast != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcast(RemoteTransactionEvent remoteTransEvent) {
/* 132 */     if (this.broadcast != null) {
/* 133 */       this.broadcast.broadcast(remoteTransEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 141 */     if (this.luceneListener != null) {
/* 142 */       this.luceneListener.shutdown();
/*     */     }
/* 144 */     if (this.broadcast != null) {
/* 145 */       logger.info("ClusterManager shutdown ");
/* 146 */       this.broadcast.shutdown();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\ClusterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */