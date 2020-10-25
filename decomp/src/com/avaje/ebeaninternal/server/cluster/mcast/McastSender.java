/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.List;
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
/*     */ public class McastSender
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(McastSender.class.getName());
/*     */ 
/*     */   
/*     */   private final int port;
/*     */   
/*     */   private final InetAddress inetAddress;
/*     */   
/*     */   private final DatagramSocket sock;
/*     */   
/*     */   private final InetSocketAddress sendAddr;
/*     */   
/*     */   private final String senderHostPort;
/*     */ 
/*     */   
/*     */   public McastSender(int port, String address, int sendPort, String sendAddress) {
/*     */     try {
/*  56 */       this.port = port;
/*  57 */       this.inetAddress = InetAddress.getByName(address);
/*     */       
/*  59 */       InetAddress sendInetAddress = null;
/*  60 */       if (sendAddress != null) {
/*  61 */         sendInetAddress = InetAddress.getByName(sendAddress);
/*     */       } else {
/*  63 */         sendInetAddress = InetAddress.getLocalHost();
/*     */       } 
/*     */       
/*  66 */       if (sendPort > 0) {
/*  67 */         this.sock = new DatagramSocket(sendPort, sendInetAddress);
/*     */       } else {
/*  69 */         this.sock = new DatagramSocket(new InetSocketAddress(sendInetAddress, false));
/*     */       } 
/*     */       
/*  72 */       String msg = "Cluster Multicast Sender on[" + sendInetAddress.getHostAddress() + ":" + this.sock.getLocalPort() + "]";
/*  73 */       logger.info(msg);
/*     */       
/*  75 */       this.sendAddr = new InetSocketAddress(sendInetAddress, this.sock.getLocalPort());
/*  76 */       this.senderHostPort = sendInetAddress.getHostAddress() + ":" + this.sock.getLocalPort();
/*     */     }
/*  78 */     catch (Exception e) {
/*  79 */       String msg = "McastSender port:" + port + " sendPort:" + sendPort + " " + address;
/*  80 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public InetSocketAddress getAddress() { return this.sendAddr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getSenderHostPort() { return this.senderHostPort; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sendPacket(Packet packet) throws IOException {
/* 106 */     byte[] pktBytes = packet.getBytes();
/*     */     
/* 108 */     if (logger.isLoggable(Level.FINE)) {
/* 109 */       logger.fine("OUTGOING packet: " + packet.getPacketId() + " size:" + pktBytes.length);
/*     */     }
/*     */     
/* 112 */     if (pktBytes.length > 65507) {
/* 113 */       logger.warning("OUTGOING packet: " + packet.getPacketId() + " size:" + pktBytes.length + " likely to be truncated using UDP with a MAXIMUM length of 65507");
/*     */     }
/*     */ 
/*     */     
/* 117 */     DatagramPacket pack = new DatagramPacket(pktBytes, pktBytes.length, this.inetAddress, this.port);
/* 118 */     this.sock.send(pack);
/*     */     
/* 120 */     return pktBytes.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sendPackets(List<Packet> packets) throws IOException {
/* 128 */     int totalBytes = 0;
/* 129 */     for (int i = 0; i < packets.size(); i++) {
/* 130 */       totalBytes += sendPacket((Packet)packets.get(i));
/*     */     }
/* 132 */     return totalBytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\McastSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */