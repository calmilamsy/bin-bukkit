/*     */ package com.avaje.ebeaninternal.server.lucene.cluster;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class SocketClient
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(SocketClient.class.getName()); private final InetSocketAddress address; private boolean keepAlive; private final String hostPort;
/*     */   private Socket socket;
/*     */   
/*     */   public SocketClient(InetSocketAddress address) {
/*  42 */     this.keepAlive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.address = address;
/*  58 */     this.hostPort = address.getHostName() + ":" + address.getPort();
/*     */   }
/*     */   private OutputStream os; private InputStream is; private DataInput dataInput; private DataOutput dataOutput;
/*     */   
/*  62 */   public String getHostPort() { return this.hostPort; }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public int getPort() { return this.address.getPort(); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public OutputStream getOutputStream() { return this.os; }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public InputStream getInputStream() { return this.is; }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public DataInput getDataInput() { return this.dataInput; }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public DataOutput getDataOutput() { return this.dataOutput; }
/*     */ 
/*     */   
/*     */   public void reconnect() throws IOException {
/*  86 */     disconnect();
/*  87 */     connect();
/*     */   }
/*     */   
/*     */   public void connect() throws IOException {
/*  91 */     if (this.socket != null) {
/*  92 */       throw new IllegalStateException("Already got a socket connection?");
/*     */     }
/*  94 */     Socket s = new Socket();
/*  95 */     s.setKeepAlive(this.keepAlive);
/*  96 */     s.connect(this.address);
/*     */     
/*  98 */     this.socket = s;
/*  99 */     this.os = this.socket.getOutputStream();
/* 100 */     this.is = this.socket.getInputStream();
/*     */   }
/*     */   
/*     */   public void initData() throws IOException {
/* 104 */     this.dataOutput = new DataOutputStream(this.os);
/* 105 */     this.dataInput = new DataInputStream(this.is);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect() throws IOException {
/* 110 */     if (this.socket != null) {
/*     */       
/*     */       try {
/* 113 */         this.socket.close();
/* 114 */       } catch (IOException e) {
/* 115 */         String msg = "Error disconnecting from Cluster member " + this.hostPort;
/* 116 */         logger.log(Level.INFO, msg, e);
/*     */       } 
/*     */       
/* 119 */       this.os = null;
/* 120 */       this.socket = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InetSocketAddress parseHostPort(String hostAndPort) {
/*     */     try {
/* 130 */       hostAndPort = hostAndPort.trim();
/* 131 */       int colonPos = hostAndPort.indexOf(":");
/* 132 */       if (colonPos == -1) {
/* 133 */         String msg = "No colon \":\" in " + hostAndPort;
/* 134 */         throw new IllegalArgumentException(msg);
/*     */       } 
/* 136 */       String host = hostAndPort.substring(0, colonPos);
/* 137 */       String sPort = hostAndPort.substring(colonPos + 1, hostAndPort.length());
/* 138 */       int port = Integer.parseInt(sPort);
/*     */       
/* 140 */       return new InetSocketAddress(host, port);
/*     */     }
/* 142 */     catch (Exception ex) {
/* 143 */       throw new RuntimeException("Error parsing [" + hostAndPort + "] for the form [host:port]", ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\cluster\SocketClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */