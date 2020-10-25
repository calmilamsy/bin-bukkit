/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*     */ import com.avaje.ebeaninternal.server.cluster.PacketTransactionEvent;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
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
/*     */ public class McastListener
/*     */   implements Runnable
/*     */ {
/*  45 */   private static final Logger logger = Logger.getLogger(McastListener.class.getName());
/*     */ 
/*     */   
/*     */   private final McastClusterManager owner;
/*     */ 
/*     */   
/*     */   private final McastPacketControl packetControl;
/*     */ 
/*     */   
/*     */   private final MulticastSocket sock;
/*     */   
/*     */   private final Thread listenerThread;
/*     */   
/*     */   private final String localSenderHostPort;
/*     */   
/*     */   private final InetAddress group;
/*     */   
/*     */   private final boolean debugIgnore;
/*     */   
/*     */   private DatagramPacket pack;
/*     */   
/*     */   private byte[] receiveBuffer;
/*     */   
/*     */   private long totalPacketsReceived;
/*     */   
/*     */   private long totalBytesReceived;
/*     */   
/*     */   private long totalTxnEventsReceived;
/*     */ 
/*     */   
/*     */   public McastListener(McastClusterManager owner, McastPacketControl packetControl, int port, String address, int bufferSize, int timeout, String localSenderHostPort, boolean disableLoopback, int ttl, InetAddress mcastBindAddress) {
/*  76 */     this.debugIgnore = GlobalProperties.getBoolean("ebean.debug.mcast.ignore", false);
/*     */     
/*  78 */     this.owner = owner;
/*  79 */     this.packetControl = packetControl;
/*  80 */     this.localSenderHostPort = localSenderHostPort;
/*  81 */     this.receiveBuffer = new byte[bufferSize];
/*  82 */     this.listenerThread = new Thread(this, "EbeanClusterMcastListener");
/*     */     
/*  84 */     String msg = "Cluster Multicast Listening address[" + address + "] port[" + port + "] disableLoopback[" + disableLoopback + "]";
/*  85 */     if (ttl >= 0) {
/*  86 */       msg = msg + " ttl[" + ttl + "]";
/*     */     }
/*  88 */     if (mcastBindAddress != null) {
/*  89 */       msg = msg + " mcastBindAddress[" + mcastBindAddress + "]";
/*     */     }
/*  91 */     logger.info(msg);
/*     */     
/*     */     try {
/*  94 */       this.group = InetAddress.getByName(address);
/*  95 */       this.sock = new MulticastSocket(port);
/*  96 */       this.sock.setSoTimeout(timeout);
/*     */       
/*  98 */       if (disableLoopback) {
/*  99 */         this.sock.setLoopbackMode(disableLoopback);
/*     */       }
/*     */       
/* 102 */       if (mcastBindAddress != null)
/*     */       {
/* 104 */         this.sock.setInterface(mcastBindAddress);
/*     */       }
/*     */       
/* 107 */       if (ttl >= 0) {
/* 108 */         this.sock.setTimeToLive(ttl);
/*     */       }
/* 110 */       this.sock.setReuseAddress(true);
/* 111 */       this.pack = new DatagramPacket(this.receiveBuffer, this.receiveBuffer.length);
/* 112 */       this.sock.joinGroup(this.group);
/*     */     }
/* 114 */     catch (Exception e) {
/* 115 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startListening() {
/* 120 */     this.listenerThread.setDaemon(true);
/* 121 */     this.listenerThread.start();
/*     */     
/* 123 */     logger.info("Cluster Multicast Listener up and joined Group");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 131 */     this.shutdown = true;
/* 132 */     synchronized (this.listenerThread) {
/*     */       
/*     */       try {
/* 135 */         this.listenerThread.wait(20000L);
/* 136 */       } catch (InterruptedException e) {
/* 137 */         logger.info("InterruptedException:" + e);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     if (!this.shutdownComplete) {
/* 142 */       String msg = "WARNING: Shutdown of McastListener did not complete?";
/* 143 */       System.err.println(msg);
/* 144 */       logger.warning(msg);
/*     */     } 
/*     */     
/*     */     try {
/* 148 */       this.sock.leaveGroup(this.group);
/* 149 */     } catch (IOException e) {
/*     */       
/* 151 */       e.printStackTrace();
/* 152 */       String msg = "Error leaving Multicast group";
/* 153 */       logger.log(Level.INFO, msg, e);
/*     */     } 
/*     */     try {
/* 156 */       this.sock.close();
/* 157 */     } catch (Exception e) {
/*     */       
/* 159 */       e.printStackTrace();
/* 160 */       String msg = "Error closing Multicast socket";
/* 161 */       logger.log(Level.INFO, msg, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void run() {
/* 166 */     while (!this.shutdown) {
/*     */       try {
/* 168 */         this.pack.setLength(this.receiveBuffer.length);
/* 169 */         this.sock.receive(this.pack);
/*     */         
/* 171 */         InetSocketAddress senderAddr = (InetSocketAddress)this.pack.getSocketAddress();
/*     */         
/* 173 */         String senderHostPort = senderAddr.getAddress().getHostAddress() + ":" + senderAddr.getPort();
/*     */         
/* 175 */         if (senderHostPort.equals(this.localSenderHostPort)) {
/* 176 */           if (this.debugIgnore || logger.isLoggable(Level.FINE)) {
/* 177 */             logger.info("Ignoring message as sent by localSender: " + this.localSenderHostPort);
/*     */           }
/*     */           continue;
/*     */         } 
/* 181 */         byte[] data = this.pack.getData();
/*     */ 
/*     */         
/* 184 */         ByteArrayInputStream bi = new ByteArrayInputStream(data);
/* 185 */         DataInputStream dataInput = new DataInputStream(bi);
/*     */         
/* 187 */         this.totalPacketsReceived++;
/* 188 */         this.totalBytesReceived += this.pack.getLength();
/*     */         
/* 190 */         Packet header = Packet.readHeader(dataInput);
/*     */         
/* 192 */         long packetId = header.getPacketId();
/* 193 */         boolean ackMsg = (packetId == 0L);
/*     */         
/* 195 */         boolean processThisPacket = (ackMsg || this.packetControl.isProcessPacket(senderHostPort, header.getPacketId()));
/*     */         
/* 197 */         if (!processThisPacket) {
/* 198 */           if (this.debugIgnore || logger.isLoggable(Level.FINE))
/* 199 */             logger.info("Already processed packet: " + header.getPacketId() + " type:" + header.getPacketType() + " len:" + data.length); 
/*     */           continue;
/*     */         } 
/* 202 */         if (logger.isLoggable(Level.FINER)) {
/* 203 */           logger.info("Incoming packet:" + header.getPacketId() + " type:" + header.getPacketType() + " len:" + data.length);
/*     */         }
/* 205 */         processPacket(senderHostPort, header, dataInput);
/*     */ 
/*     */       
/*     */       }
/* 209 */       catch (SocketTimeoutException e) {
/* 210 */         if (logger.isLoggable(Level.FINE)) {
/* 211 */           logger.log(Level.FINE, "timeout", e);
/*     */         }
/* 213 */         this.packetControl.onListenerTimeout();
/*     */       }
/* 215 */       catch (IOException e) {
/* 216 */         logger.log(Level.INFO, "error ?", e);
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     this.shutdownComplete = true;
/*     */     
/* 222 */     synchronized (this.listenerThread) {
/* 223 */       this.listenerThread.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processPacket(String senderHostPort, Packet header, DataInput dataInput) {
/*     */     try {
/* 229 */       switch (header.getPacketType()) {
/*     */         case 1:
/* 231 */           this.packetControl.processMessagesPacket(senderHostPort, header, dataInput, this.totalPacketsReceived, this.totalBytesReceived, this.totalTxnEventsReceived);
/*     */           return;
/*     */ 
/*     */         
/*     */         case 2:
/* 236 */           this.totalTxnEventsReceived++;
/* 237 */           processTransactionEventPacket(header, dataInput);
/*     */           return;
/*     */       } 
/*     */       
/* 241 */       String msg = "Unknown Packet type:" + header.getPacketType();
/* 242 */       logger.log(Level.SEVERE, msg);
/*     */     
/*     */     }
/* 245 */     catch (IOException e) {
/*     */       
/* 247 */       String msg = "Error reading Packet " + header.getPacketId() + " type:" + header.getPacketType();
/* 248 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processTransactionEventPacket(Packet header, DataInput dataInput) throws IOException {
/* 254 */     SpiEbeanServer server = this.owner.getEbeanServer(header.getServerName());
/*     */     
/* 256 */     PacketTransactionEvent tranEventPacket = PacketTransactionEvent.forRead(header, server);
/* 257 */     tranEventPacket.read(dataInput);
/*     */     
/* 259 */     server.remoteTransactionEvent(tranEventPacket.getEvent());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\McastListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */