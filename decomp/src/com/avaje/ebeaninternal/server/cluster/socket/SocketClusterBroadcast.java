/*     */ package com.avaje.ebeaninternal.server.cluster.socket;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterBroadcast;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.DataHolder;
/*     */ import com.avaje.ebeaninternal.server.cluster.SerialiseTransactionHelper;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketClusterBroadcast
/*     */   implements ClusterBroadcast
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(SocketClusterBroadcast.class.getName());
/*     */   
/*     */   private final SocketClient local;
/*     */   
/*     */   private final HashMap<String, SocketClient> clientMap;
/*     */   
/*     */   private final SocketClusterListener listener;
/*     */   
/*     */   private SocketClient[] members;
/*     */   
/*     */   private ClusterManager clusterManager;
/*     */   
/*  56 */   private final TxnSerialiseHelper txnSerialiseHelper = new TxnSerialiseHelper();
/*     */   
/*  58 */   private final AtomicInteger txnOutgoing = new AtomicInteger();
/*  59 */   private final AtomicInteger txnIncoming = new AtomicInteger();
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketClusterBroadcast() {
/*  64 */     String localHostPort = GlobalProperties.get("ebean.cluster.local", null);
/*  65 */     String members = GlobalProperties.get("ebean.cluster.members", null);
/*     */     
/*  67 */     logger.info("Clustering using Sockets local[" + localHostPort + "] members[" + members + "]");
/*     */     
/*  69 */     this.local = new SocketClient(parseFullName(localHostPort));
/*  70 */     this.clientMap = new HashMap();
/*     */     
/*  72 */     String[] memArray = StringHelper.delimitedToArray(members, ",", false);
/*  73 */     for (int i = 0; i < memArray.length; i++) {
/*  74 */       InetSocketAddress member = parseFullName(memArray[i]);
/*  75 */       SocketClient client = new SocketClient(member);
/*  76 */       if (!this.local.getHostPort().equalsIgnoreCase(client.getHostPort()))
/*     */       {
/*  78 */         this.clientMap.put(client.getHostPort(), client);
/*     */       }
/*     */     } 
/*     */     
/*  82 */     this.members = (SocketClient[])this.clientMap.values().toArray(new SocketClient[this.clientMap.size()]);
/*  83 */     this.listener = new SocketClusterListener(this, this.local.getPort());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketClusterStatus getStatus() {
/*  92 */     int currentGroupSize = 0;
/*  93 */     for (int i = 0; i < this.members.length; i++) {
/*  94 */       if (this.members[i].isOnline()) {
/*  95 */         currentGroupSize++;
/*     */       }
/*     */     } 
/*  98 */     int txnIn = this.txnIncoming.get();
/*  99 */     int txnOut = this.txnOutgoing.get();
/*     */     
/* 101 */     return new SocketClusterStatus(currentGroupSize, txnIn, txnOut);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startup(ClusterManager clusterManager) {
/* 106 */     this.clusterManager = clusterManager;
/*     */     try {
/* 108 */       this.listener.startListening();
/* 109 */       register();
/*     */     }
/* 111 */     catch (IOException e) {
/* 112 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 117 */     deregister();
/* 118 */     this.listener.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void register() {
/* 126 */     SocketClusterMessage h = SocketClusterMessage.register(this.local.getHostPort(), true);
/*     */     
/* 128 */     for (int i = 0; i < this.members.length; i++) {
/* 129 */       boolean online = this.members[i].register(h);
/*     */       
/* 131 */       String msg = "Cluster Member [" + this.members[i].getHostPort() + "] online[" + online + "]";
/* 132 */       logger.info(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setMemberOnline(String fullName, boolean online) throws IOException {
/* 137 */     synchronized (this.clientMap) {
/* 138 */       String msg = "Cluster Member [" + fullName + "] online[" + online + "]";
/* 139 */       logger.info(msg);
/* 140 */       SocketClient member = (SocketClient)this.clientMap.get(fullName);
/* 141 */       member.setOnline(online);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void send(SocketClient client, SocketClusterMessage msg) {
/*     */     try {
/* 150 */       client.send(msg);
/*     */     }
/* 152 */     catch (Exception ex) {
/* 153 */       logger.log(Level.SEVERE, "Error sending message", ex);
/*     */       try {
/* 155 */         client.reconnect();
/* 156 */       } catch (IOException e) {
/* 157 */         logger.log(Level.SEVERE, "Error trying to reconnect", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcast(RemoteTransactionEvent remoteTransEvent) {
/*     */     try {
/* 168 */       this.txnOutgoing.incrementAndGet();
/* 169 */       DataHolder dataHolder = this.txnSerialiseHelper.createDataHolder(remoteTransEvent);
/* 170 */       SocketClusterMessage msg = SocketClusterMessage.transEvent(dataHolder);
/* 171 */       broadcast(msg);
/* 172 */     } catch (Exception e) {
/* 173 */       String msg = "Error sending RemoteTransactionEvent " + remoteTransEvent + " to cluster members.";
/* 174 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void broadcast(SocketClusterMessage msg) {
/* 180 */     for (int i = 0; i < this.members.length; i++) {
/* 181 */       send(this.members[i], msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deregister() {
/* 190 */     SocketClusterMessage h = SocketClusterMessage.register(this.local.getHostPort(), false);
/* 191 */     broadcast(h);
/*     */     
/* 193 */     for (int i = 0; i < this.members.length; i++) {
/* 194 */       this.members[i].disconnect();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean process(SocketConnection request) throws IOException, ClassNotFoundException {
/*     */     try {
/* 204 */       SocketClusterMessage h = (SocketClusterMessage)request.readObject();
/*     */       
/* 206 */       if (h.isRegisterEvent()) {
/* 207 */         setMemberOnline(h.getRegisterHost(), h.isRegister());
/*     */       } else {
/*     */         
/* 210 */         this.txnIncoming.incrementAndGet();
/* 211 */         DataHolder dataHolder = h.getDataHolder();
/* 212 */         RemoteTransactionEvent transEvent = this.txnSerialiseHelper.read(dataHolder);
/* 213 */         transEvent.run();
/*     */       } 
/*     */       
/* 216 */       if (h.isRegisterEvent() && !h.isRegister())
/*     */       {
/* 218 */         return true;
/*     */       }
/* 220 */       return false;
/*     */     }
/* 222 */     catch (InterruptedIOException e) {
/* 223 */       String msg = "Timeout waiting for message";
/* 224 */       logger.log(Level.INFO, msg, e);
/*     */       try {
/* 226 */         request.disconnect();
/* 227 */       } catch (IOException ex) {
/* 228 */         logger.log(Level.INFO, "Error disconnecting after timeout", ex);
/*     */       } 
/* 230 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InetSocketAddress parseFullName(String hostAndPort) {
/*     */     try {
/* 241 */       hostAndPort = hostAndPort.trim();
/* 242 */       int colonPos = hostAndPort.indexOf(":");
/* 243 */       if (colonPos == -1) {
/* 244 */         String msg = "No colon \":\" in " + hostAndPort;
/* 245 */         throw new IllegalArgumentException(msg);
/*     */       } 
/* 247 */       String host = hostAndPort.substring(0, colonPos);
/* 248 */       String sPort = hostAndPort.substring(colonPos + 1, hostAndPort.length());
/* 249 */       int port = Integer.parseInt(sPort);
/*     */       
/* 251 */       return new InetSocketAddress(host, port);
/*     */     }
/* 253 */     catch (Exception ex) {
/* 254 */       throw new RuntimeException("Error parsing [" + hostAndPort + "] for the form [host:port]", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class TxnSerialiseHelper
/*     */     extends SerialiseTransactionHelper
/*     */   {
/* 262 */     public SpiEbeanServer getEbeanServer(String serverName) { return (SpiEbeanServer)SocketClusterBroadcast.this.clusterManager.getServer(serverName); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketClusterBroadcast.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */