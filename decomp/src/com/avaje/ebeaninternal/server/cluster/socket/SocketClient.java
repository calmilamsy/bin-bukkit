/*     */ package com.avaje.ebeaninternal.server.cluster.socket;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
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
/*     */ class SocketClient
/*     */ {
/*  34 */   private static final Logger logger = Logger.getLogger(SocketClient.class.getName());
/*     */   
/*     */   private final InetSocketAddress address;
/*     */   
/*     */   private final String hostPort;
/*     */   
/*     */   private boolean online;
/*     */   
/*     */   private Socket socket;
/*     */   
/*     */   private OutputStream os;
/*     */   
/*     */   private ObjectOutputStream oos;
/*     */ 
/*     */   
/*     */   public SocketClient(InetSocketAddress address) {
/*  50 */     this.address = address;
/*  51 */     this.hostPort = address.getHostName() + ":" + address.getPort();
/*     */   }
/*     */ 
/*     */   
/*  55 */   public String getHostPort() { return this.hostPort; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int getPort() { return this.address.getPort(); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public boolean isOnline() { return this.online; }
/*     */ 
/*     */   
/*     */   public void setOnline(boolean online) throws IOException {
/*  67 */     if (online) {
/*  68 */       setOnline();
/*     */     } else {
/*  70 */       disconnect();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setOnline() throws IOException {
/*  79 */     connect();
/*  80 */     this.online = true;
/*     */   }
/*     */   
/*     */   public void reconnect() throws IOException {
/*  84 */     disconnect();
/*  85 */     connect();
/*     */   }
/*     */   
/*     */   private void connect() throws IOException {
/*  89 */     if (this.socket != null) {
/*  90 */       throw new IllegalStateException("Already got a socket connection?");
/*     */     }
/*  92 */     Socket s = new Socket();
/*  93 */     s.setKeepAlive(true);
/*  94 */     s.connect(this.address);
/*     */     
/*  96 */     this.socket = s;
/*  97 */     this.os = this.socket.getOutputStream();
/*     */   }
/*     */   
/*     */   public void disconnect() throws IOException {
/* 101 */     this.online = false;
/* 102 */     if (this.socket != null) {
/*     */       
/*     */       try {
/* 105 */         this.socket.close();
/* 106 */       } catch (IOException e) {
/* 107 */         String msg = "Error disconnecting from Cluster member " + this.hostPort;
/* 108 */         logger.log(Level.INFO, msg, e);
/*     */       } 
/*     */       
/* 111 */       this.os = null;
/* 112 */       this.oos = null;
/* 113 */       this.socket = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean register(SocketClusterMessage registerMsg) {
/*     */     try {
/* 120 */       setOnline();
/* 121 */       send(registerMsg);
/* 122 */       return true;
/* 123 */     } catch (IOException e) {
/* 124 */       disconnect();
/* 125 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean send(SocketClusterMessage msg) {
/* 131 */     if (this.online) {
/* 132 */       writeObject(msg);
/* 133 */       return true;
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(Object object) throws IOException {
/* 142 */     if (this.oos == null) {
/* 143 */       this.oos = new ObjectOutputStream(this.os);
/*     */     }
/* 145 */     this.oos.writeObject(object);
/* 146 */     this.oos.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */