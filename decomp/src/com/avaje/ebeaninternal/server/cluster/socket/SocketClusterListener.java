/*     */ package com.avaje.ebeaninternal.server.cluster.socket;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketClusterListener
/*     */   implements Runnable
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(SocketClusterListener.class.getName());
/*     */   
/*     */   private final int port;
/*     */   
/*     */   private final int listenTimeout = 60000;
/*     */   
/*     */   private final ServerSocket serverListenSocket;
/*     */   private final Thread listenerThread;
/*     */   
/*     */   public SocketClusterListener(SocketClusterBroadcast owner, int port) {
/*  56 */     this.listenTimeout = 60000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.owner = owner;
/*  90 */     this.threadPool = ThreadPoolManager.getThreadPool("EbeanClusterMember");
/*  91 */     this.port = port;
/*     */     
/*     */     try {
/*  94 */       this.serverListenSocket = new ServerSocket(port);
/*  95 */       this.serverListenSocket.setSoTimeout(60000);
/*  96 */       this.listenerThread = new Thread(this, "EbeanClusterListener");
/*     */     }
/*  98 */     catch (IOException e) {
/*  99 */       String msg = "Error starting cluster socket listener on port " + port;
/* 100 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */   private final ThreadPool threadPool;
/*     */   private final SocketClusterBroadcast owner;
/*     */   boolean doingShutdown;
/*     */   boolean isActive;
/*     */   
/* 108 */   public int getPort() { return this.port; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startListening() throws IOException {
/* 115 */     this.listenerThread.setDaemon(true);
/* 116 */     this.listenerThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() throws IOException {
/* 123 */     this.doingShutdown = true;
/*     */     try {
/* 125 */       if (this.isActive) {
/* 126 */         synchronized (this.listenerThread) {
/*     */           try {
/* 128 */             this.listenerThread.wait(1000L);
/* 129 */           } catch (InterruptedException e) {}
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 135 */       this.listenerThread.interrupt();
/* 136 */       this.serverListenSocket.close();
/* 137 */     } catch (IOException e) {
/* 138 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() throws IOException {
/* 148 */     while (!this.doingShutdown) {
/*     */       try {
/* 150 */         synchronized (this.listenerThread) {
/* 151 */           Socket clientSocket = this.serverListenSocket.accept();
/*     */           
/* 153 */           this.isActive = true;
/*     */           
/* 155 */           Runnable request = new RequestProcessor(this.owner, clientSocket);
/* 156 */           this.threadPool.assign(request, true);
/*     */           
/* 158 */           this.isActive = false;
/*     */         } 
/* 160 */       } catch (SocketException e) {
/* 161 */         if (this.doingShutdown) {
/* 162 */           String msg = "doingShutdown and accept threw:" + e.getMessage();
/* 163 */           logger.info(msg);
/*     */           continue;
/*     */         } 
/* 166 */         logger.log(Level.SEVERE, null, e);
/*     */       
/*     */       }
/* 169 */       catch (InterruptedIOException e) {
/*     */ 
/*     */         
/* 172 */         logger.fine("Possibly expected due to accept timeout?" + e.getMessage());
/*     */       }
/* 174 */       catch (IOException e) {
/*     */         
/* 176 */         logger.log(Level.SEVERE, null, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketClusterListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */