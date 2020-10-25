/*     */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
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
/*     */ public class SLuceneClusterSocketListener
/*     */   implements Runnable, LuceneClusterListener
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(SLuceneClusterSocketListener.class.getName());
/*     */   
/*     */   private final int port;
/*     */   
/*     */   private final int listenTimeout = 60000;
/*     */   
/*     */   private final ServerSocket serverListenSocket;
/*     */   private final Thread listenerThread;
/*     */   
/*     */   public SLuceneClusterSocketListener(ClusterManager clusterManager, int port) {
/*  48 */     this.listenTimeout = 60000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.clusterManager = clusterManager;
/*  82 */     this.threadPool = ThreadPoolManager.getThreadPool("EbeanClusterLuceneListener");
/*  83 */     this.port = port;
/*     */     
/*     */     try {
/*  86 */       this.serverListenSocket = new ServerSocket(port);
/*  87 */       this.serverListenSocket.setSoTimeout(60000);
/*  88 */       this.listenerThread = new Thread(this, "EbeanClusterLuceneListener");
/*     */     }
/*  90 */     catch (IOException e) {
/*  91 */       String msg = "Error starting cluster socket listener on port " + port;
/*  92 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */   private final ThreadPool threadPool;
/*     */   private final ClusterManager clusterManager;
/*     */   private boolean doingShutdown;
/*     */   private boolean isActive;
/*     */   
/* 100 */   public int getPort() { return this.port; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startup() {
/* 107 */     this.listenerThread.setDaemon(true);
/* 108 */     this.listenerThread.start();
/*     */     
/* 110 */     String msg = "Cluster Lucene Listening address[todo] port[" + this.port + "]";
/* 111 */     logger.info(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 118 */     this.doingShutdown = true;
/*     */     try {
/* 120 */       if (this.isActive) {
/* 121 */         synchronized (this.listenerThread) {
/*     */           try {
/* 123 */             this.listenerThread.wait(1000L);
/* 124 */           } catch (InterruptedException e) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 130 */       this.listenerThread.interrupt();
/* 131 */       this.serverListenSocket.close();
/* 132 */     } catch (IOException e) {
/* 133 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 143 */     while (!this.doingShutdown) {
/*     */       try {
/* 145 */         synchronized (this.listenerThread) {
/* 146 */           Socket clientSocket = this.serverListenSocket.accept();
/*     */           
/* 148 */           this.isActive = true;
/*     */           
/* 150 */           Runnable request = new SLuceneClusterSocketRequest(this.clusterManager, clientSocket);
/* 151 */           this.threadPool.assign(request, true);
/*     */           
/* 153 */           this.isActive = false;
/*     */         } 
/* 155 */       } catch (SocketException e) {
/* 156 */         if (this.doingShutdown) {
/* 157 */           String msg = "doingShutdown and accept threw:" + e.getMessage();
/* 158 */           logger.info(msg);
/*     */           continue;
/*     */         } 
/* 161 */         logger.log(Level.SEVERE, null, e);
/*     */       
/*     */       }
/* 164 */       catch (InterruptedIOException e) {
/*     */ 
/*     */         
/* 167 */         logger.fine("Possibly expected due to accept timeout?" + e.getMessage());
/*     */       }
/* 169 */       catch (IOException e) {
/*     */         
/* 171 */         logger.log(Level.SEVERE, null, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SLuceneClusterSocketListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */