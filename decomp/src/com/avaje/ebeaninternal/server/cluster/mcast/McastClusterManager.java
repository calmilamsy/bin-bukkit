/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterBroadcast;
/*     */ import com.avaje.ebeaninternal.server.cluster.ClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*     */ import com.avaje.ebeaninternal.server.cluster.PacketWriter;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class McastClusterManager
/*     */   implements ClusterBroadcast, Runnable
/*     */ {
/*  60 */   private static final Logger logger = Logger.getLogger(McastClusterManager.class.getName());
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
/*     */   
/*  94 */   private final ArrayList<MessageResend> resendMessages = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final ArrayList<MessageControl> controlMessages = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private final OutgoingPacketsCache outgoingPacketsCache = new OutgoingPacketsCache();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private final IncomingPacketsLastAck incomingPacketsLastAck = new IncomingPacketsLastAck();
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
/* 157 */   private int currentGroupSize = -1;
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
/* 172 */   private long lastStatusTime = System.currentTimeMillis();
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
/* 195 */   private long managerSleepMillis = GlobalProperties.getInt("ebean.cluster.mcast.managerSleepMillis", 80);
/* 196 */   private int lastSendTimeFreqMillis = 1000 * GlobalProperties.getInt("ebean.cluster.mcast.pingFrequencySecs", 300);
/* 197 */   private int lastStatusTimeFreqMillis = 1000 * GlobalProperties.getInt("ebean.cluster.mcast.statusFrequencySecs", 600); private ClusterManager clusterManager; private final Thread managerThread; private final McastPacketControl packageControl; private final McastListener listener; private final McastSender localSender;
/*     */   private final String localSenderHostPort;
/*     */   private final PacketWriter packetWriter;
/* 200 */   private final int maxResendOutgoing = GlobalProperties.getInt("ebean.cluster.mcast.maxResendOutgoing", 200); private boolean sendWithNoMembers; private long minAcked;
/*     */   public McastClusterManager() {
/* 202 */     int maxResendIncoming = GlobalProperties.getInt("ebean.cluster.mcast.maxResendIncoming", 50);
/*     */ 
/*     */     
/* 205 */     int port = GlobalProperties.getInt("ebean.cluster.mcast.listen.port", 0);
/* 206 */     String addr = GlobalProperties.get("ebean.cluster.mcast.listen.address", null);
/*     */     
/* 208 */     int sendPort = GlobalProperties.getInt("ebean.cluster.mcast.send.port", 0);
/* 209 */     String sendAddr = GlobalProperties.get("ebean.cluster.mcast.send.address", null);
/*     */ 
/*     */ 
/*     */     
/* 213 */     int maxSendPacketSize = GlobalProperties.getInt("ebean.cluster.mcast.send.maxPacketSize", 1500);
/*     */     
/* 215 */     this.sendWithNoMembers = GlobalProperties.getBoolean("ebean.cluster.mcast.send.sendWithNoMembers", true);
/*     */ 
/*     */ 
/*     */     
/* 219 */     boolean disableLoopback = GlobalProperties.getBoolean("ebean.cluster.mcast.listen.disableLoopback", false);
/* 220 */     int ttl = GlobalProperties.getInt("ebean.cluster.mcast.listen.ttl", -1);
/* 221 */     int timeout = GlobalProperties.getInt("ebean.cluster.mcast.listen.timeout", 1000);
/* 222 */     int bufferSize = GlobalProperties.getInt("ebean.cluster.mcast.listen.bufferSize", 65500);
/*     */     
/* 224 */     String mcastAddr = GlobalProperties.get("ebean.cluster.mcast.listen.mcastAddress", null);
/*     */     
/* 226 */     InetAddress mcastAddress = null;
/* 227 */     if (mcastAddr != null) {
/*     */       try {
/* 229 */         mcastAddress = InetAddress.getByName(mcastAddr);
/* 230 */       } catch (UnknownHostException e) {
/* 231 */         String msg = "Error getting Multicast InetAddress for " + mcastAddr;
/* 232 */         throw new RuntimeException(msg, e);
/*     */       } 
/*     */     }
/*     */     
/* 236 */     if (port == 0 || addr == null) {
/* 237 */       String msg = "One of these Multicast settings has not been set. ebean.cluster.mcast.listen.port=" + port + ", ebean.cluster.mcast.listen.address=" + addr;
/*     */ 
/*     */       
/* 240 */       throw new IllegalArgumentException(msg);
/*     */     } 
/*     */     
/* 243 */     this.managerThread = new Thread(this, "EbeanClusterMcastManager");
/*     */     
/* 245 */     this.packetWriter = new PacketWriter(maxSendPacketSize);
/* 246 */     this.localSender = new McastSender(port, addr, sendPort, sendAddr);
/* 247 */     this.localSenderHostPort = this.localSender.getSenderHostPort();
/*     */     
/* 249 */     this.packageControl = new McastPacketControl(this, this.localSenderHostPort, maxResendIncoming);
/*     */     
/* 251 */     this.listener = new McastListener(this, this.packageControl, port, addr, bufferSize, timeout, this.localSenderHostPort, disableLoopback, ttl, mcastAddress);
/*     */   }
/*     */   private long minAckedFromListener; private long lastSendTime; private long totalTxnEventsSent; private long totalTxnEventsReceived; private long totalPacketsSent;
/*     */   private long totalBytesSent;
/*     */   private long totalPacketsResent;
/*     */   private long totalBytesResent;
/*     */   private long totalPacketsReceived;
/*     */   private long totalBytesReceived;
/*     */   
/*     */   protected void fromListenerTimeoutNoMembers() {
/* 261 */     synchronized (this.managerThread) {
/* 262 */       this.currentGroupSize = 0;
/*     */     } 
/*     */   }
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
/*     */   protected void fromListener(long newMinAcked, MessageControl msgControl, MessageResend msgResend, int groupSize, long totalPacketsReceived, long totalBytesReceived, long totalTxnEventsReceived) {
/* 281 */     synchronized (this.managerThread) {
/* 282 */       if (newMinAcked > this.minAckedFromListener) {
/* 283 */         this.minAckedFromListener = newMinAcked;
/*     */       }
/* 285 */       if (msgControl != null) {
/* 286 */         this.controlMessages.add(msgControl);
/*     */       }
/* 288 */       if (msgResend != null) {
/* 289 */         this.resendMessages.add(msgResend);
/*     */       }
/*     */       
/* 292 */       this.currentGroupSize = groupSize;
/*     */ 
/*     */       
/* 295 */       this.totalPacketsReceived = totalPacketsReceived;
/* 296 */       this.totalBytesReceived = totalBytesReceived;
/* 297 */       this.totalTxnEventsReceived = totalTxnEventsReceived;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public McastStatus getStatus(boolean reset) {
/* 305 */     synchronized (this.managerThread) {
/*     */       
/* 307 */       long currentPacketId = this.packetWriter.currentPacketId();
/* 308 */       String lastAcks = this.incomingPacketsLastAck.toString();
/*     */       
/* 310 */       return new McastStatus(this.currentGroupSize, this.outgoingPacketsCache.size(), currentPacketId, this.minAcked, lastAcks, this.totalTxnEventsSent, this.totalTxnEventsReceived, this.totalPacketsSent, this.totalPacketsResent, this.totalPacketsReceived, this.totalBytesSent, this.totalBytesResent, this.totalBytesReceived);
/*     */     } 
/*     */   }
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
/*     */   public void run() {
/*     */     while (true) {
/*     */       try {
/* 326 */         Thread.sleep(this.managerSleepMillis);
/*     */         
/* 328 */         synchronized (this.managerThread)
/*     */         {
/* 330 */           handleControlMessages();
/*     */           
/* 332 */           handleResendMessages();
/*     */           
/* 334 */           if (this.currentGroupSize == 0) {
/*     */             
/* 336 */             int trimmedCount = this.outgoingPacketsCache.trimAll();
/* 337 */             if (trimmedCount > 0) {
/* 338 */               logger.fine("Cluster has no other members. Trimmed " + trimmedCount);
/*     */             }
/*     */           }
/* 341 */           else if (this.minAckedFromListener > this.minAcked) {
/*     */             
/* 343 */             this.outgoingPacketsCache.trimAcknowledgedMessages(this.minAckedFromListener);
/* 344 */             this.minAcked = this.minAckedFromListener;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 353 */           AckResendMessages ackResendMessages = this.packageControl.getAckResendMessages(this.incomingPacketsLastAck);
/*     */           
/* 355 */           if (ackResendMessages.size() > 0)
/*     */           {
/*     */             
/* 358 */             if (sendMessages(false, ackResendMessages.getMessages()))
/*     */             {
/* 360 */               this.incomingPacketsLastAck.updateLastAck(ackResendMessages);
/*     */             }
/*     */           }
/*     */           
/* 364 */           if (this.lastSendTime < System.currentTimeMillis() - this.lastSendTimeFreqMillis)
/*     */           {
/* 366 */             sendPing();
/*     */           }
/*     */           
/* 369 */           if (this.lastStatusTimeFreqMillis > 0 && 
/* 370 */             this.lastStatusTime < System.currentTimeMillis() - this.lastStatusTimeFreqMillis) {
/* 371 */             McastStatus status = getStatus(false);
/* 372 */             logger.info("Cluster Status: " + status.getSummary());
/* 373 */             this.lastStatusTime = System.currentTimeMillis();
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 378 */       } catch (Exception e) {
/* 379 */         String msg = "Error with Cluster Mcast Manager thread";
/* 380 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleResendMessages() {
/* 390 */     if (this.resendMessages.size() > 0) {
/*     */       
/* 392 */       TreeSet<Long> s = new TreeSet<Long>();
/* 393 */       for (i = 0; i < this.resendMessages.size(); i++) {
/* 394 */         MessageResend resendMsg = (MessageResend)this.resendMessages.get(i);
/* 395 */         s.addAll(resendMsg.getResendPacketIds());
/*     */       } 
/*     */       
/* 398 */       this.totalPacketsResent += s.size();
/*     */       
/* 400 */       Iterator<Long> it = s.iterator();
/* 401 */       while (it.hasNext()) {
/* 402 */         Long resendPacketId = (Long)it.next();
/* 403 */         Packet packet = this.outgoingPacketsCache.getPacket(resendPacketId);
/* 404 */         if (packet == null) {
/* 405 */           String msg = "Cluster unable to resend packet[" + resendPacketId + "] as it is no longer in the outgoingPacketsCache";
/* 406 */           logger.log(Level.SEVERE, msg); continue;
/*     */         } 
/* 408 */         int resendCount = packet.incrementResendCount();
/* 409 */         if (resendCount <= this.maxResendOutgoing) {
/* 410 */           resendPacket(packet); continue;
/*     */         } 
/* 412 */         String msg = "Cluster maxResendOutgoing [" + this.maxResendOutgoing + "] hit for packet " + resendPacketId + ". We will not try to send it anymore, removing it from the outgoingPacketsCache.";
/*     */         
/* 414 */         logger.log(Level.SEVERE, msg);
/* 415 */         this.outgoingPacketsCache.remove(packet);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resendPacket(Packet packet) {
/*     */     try {
/* 427 */       this.totalPacketsResent++;
/* 428 */       this.totalBytesResent += this.localSender.sendPacket(packet);
/* 429 */     } catch (IOException e) {
/* 430 */       String msg = "Error trying to resend packet " + packet.getPacketId();
/* 431 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleControlMessages() {
/* 440 */     boolean pingReponse = false;
/* 441 */     boolean joinReponse = false;
/*     */     
/* 443 */     for (int i = 0; i < this.controlMessages.size(); i++) {
/* 444 */       MessageControl message = (MessageControl)this.controlMessages.get(i);
/*     */       
/* 446 */       short type = message.getControlType();
/* 447 */       switch (type) {
/*     */         
/*     */         case 1:
/* 450 */           logger.info("Cluster member Joined [" + message.getFromHostPort() + "]");
/* 451 */           joinReponse = true;
/*     */           break;
/*     */         
/*     */         case 7:
/* 455 */           logger.info("Cluster member Online [" + message.getFromHostPort() + "]");
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 460 */           pingReponse = true;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 470 */           this.incomingPacketsLastAck.remove(message.getFromHostPort());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 477 */     this.controlMessages.clear();
/*     */     
/* 479 */     if (joinReponse) {
/* 480 */       sendJoinResponse();
/*     */     }
/* 482 */     if (pingReponse) {
/* 483 */       sendPingResponse();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 491 */     sendLeave();
/* 492 */     this.listener.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startup(ClusterManager clusterManager) {
/* 499 */     this.clusterManager = clusterManager;
/* 500 */     this.listener.startListening();
/*     */     
/* 502 */     this.managerThread.setDaemon(true);
/* 503 */     this.managerThread.start();
/*     */     
/* 505 */     sendJoin();
/*     */   }
/*     */ 
/*     */   
/* 509 */   protected SpiEbeanServer getEbeanServer(String serverName) { return (SpiEbeanServer)this.clusterManager.getServer(serverName); }
/*     */ 
/*     */ 
/*     */   
/* 513 */   private void sendJoin() { sendControlMessage(true, (short)1); }
/*     */ 
/*     */ 
/*     */   
/* 517 */   private void sendLeave() { sendControlMessage(false, (short)2); }
/*     */ 
/*     */ 
/*     */   
/* 521 */   private void sendJoinResponse() { sendControlMessage(true, (short)7); }
/*     */ 
/*     */ 
/*     */   
/* 525 */   private void sendPingResponse() { sendControlMessage(true, (short)8); }
/*     */ 
/*     */ 
/*     */   
/* 529 */   private void sendPing() { sendControlMessage(true, (short)3); }
/*     */ 
/*     */ 
/*     */   
/* 533 */   private void sendControlMessage(boolean requiresAck, short controlType) { sendMessage(requiresAck, new MessageControl(controlType, this.localSenderHostPort)); }
/*     */ 
/*     */   
/*     */   private void sendMessage(boolean requiresAck, Message msg) {
/* 537 */     ArrayList<Message> messages = new ArrayList<Message>(true);
/* 538 */     messages.add(msg);
/* 539 */     sendMessages(requiresAck, messages);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean sendMessages(boolean requiresAck, List<? extends Message> messages) {
/* 544 */     synchronized (this.managerThread) {
/*     */ 
/*     */       
/* 547 */       List<Packet> packets = this.packetWriter.write(requiresAck, messages);
/* 548 */       sendPackets(requiresAck, packets);
/* 549 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sendPackets(boolean requiresAck, List<Packet> packets) throws IOException {
/* 560 */     if (this.currentGroupSize == 0 && !this.sendWithNoMembers)
/*     */     {
/* 562 */       return false;
/*     */     }
/*     */     
/* 565 */     if (requiresAck)
/*     */     {
/* 567 */       this.outgoingPacketsCache.registerPackets(packets);
/*     */     }
/* 569 */     this.totalPacketsSent += packets.size();
/* 570 */     this.totalBytesSent += this.localSender.sendPackets(packets);
/*     */     
/* 572 */     this.lastSendTime = System.currentTimeMillis();
/*     */     
/* 574 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcast(RemoteTransactionEvent remoteTransEvent) {
/* 583 */     synchronized (this.managerThread) {
/*     */       try {
/* 585 */         List<Packet> packets = this.packetWriter.write(remoteTransEvent);
/* 586 */         if (sendPackets(true, packets)) {
/* 587 */           this.totalTxnEventsSent++;
/*     */         }
/* 589 */       } catch (IOException e) {
/* 590 */         String msg = "Error sending RemoteTransactionEvent " + remoteTransEvent;
/* 591 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setManagerSleepMillis(long managerSleepMillis) {
/* 600 */     synchronized (this.managerThread) {
/* 601 */       this.managerSleepMillis = managerSleepMillis;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getManagerSleepMillis() {
/* 609 */     synchronized (this.managerThread) {
/* 610 */       return this.managerSleepMillis;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\McastClusterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */