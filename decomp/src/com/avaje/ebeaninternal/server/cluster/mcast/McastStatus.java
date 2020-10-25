/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class McastStatus
/*     */ {
/*     */   private final long totalTxnEventsSent;
/*     */   private final long totalTxnEventsReceived;
/*     */   private final long totalPacketsSent;
/*     */   private final long totalPacketsResent;
/*     */   private final long totalPacketsReceived;
/*     */   private final long totalBytesSent;
/*     */   private final long totalBytesResent;
/*     */   private final long totalBytesReceived;
/*     */   private final int currentGroupSize;
/*     */   private final int outgoingPacketsCacheSize;
/*     */   private final long currentPacketId;
/*     */   private final long minAckedPacketId;
/*     */   private final String lastOutgoingAcks;
/*     */   
/*     */   public String getSummary() {
/*  53 */     StringBuilder sb = new StringBuilder(80);
/*  54 */     sb.append("txnOut:").append(this.totalTxnEventsSent).append("; ");
/*  55 */     sb.append("txnIn:").append(this.totalTxnEventsReceived).append("; ");
/*  56 */     sb.append("outPackets:").append(this.totalPacketsSent).append("; ");
/*  57 */     sb.append("outBytes:").append(this.totalBytesSent).append("; ");
/*  58 */     sb.append("inPackets:").append(this.totalPacketsReceived).append("; ");
/*  59 */     sb.append("inBytes:").append(this.totalBytesReceived).append("; ");
/*  60 */     sb.append("resentPackets:").append(this.totalPacketsResent).append("; ");
/*  61 */     sb.append("resentBytes:").append(this.totalBytesResent).append("; ");
/*  62 */     sb.append("groupSize:").append(this.currentGroupSize).append("; ");
/*  63 */     sb.append("cache:").append(this.outgoingPacketsCacheSize).append("; ");
/*  64 */     sb.append("currentPacket:").append(this.currentPacketId).append("; ");
/*  65 */     sb.append("minAckedPacket:").append(this.minAckedPacketId).append("; ");
/*  66 */     sb.append("lastAck:").append(this.lastOutgoingAcks).append("; ");
/*     */     
/*  68 */     return sb.toString();
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
/*     */   public McastStatus(int currentGroupSize, int outgoingPacketsCacheSize, long currentPacketId, long minAckedPacketId, String lastOutgoingAcks, long totalTransEventsSent, long totalTransEventsReceived, long totalPacketsSent, long totalPacketsResent, long totalPacketsReceived, long totalBytesSent, long totalBytesResent, long totalBytesReceived) {
/*  85 */     this.currentGroupSize = currentGroupSize;
/*  86 */     this.outgoingPacketsCacheSize = outgoingPacketsCacheSize;
/*  87 */     this.currentPacketId = currentPacketId;
/*  88 */     this.minAckedPacketId = minAckedPacketId;
/*  89 */     this.lastOutgoingAcks = lastOutgoingAcks;
/*  90 */     this.totalTxnEventsSent = totalTransEventsSent;
/*  91 */     this.totalTxnEventsReceived = totalTransEventsReceived;
/*  92 */     this.totalPacketsSent = totalPacketsSent;
/*  93 */     this.totalPacketsResent = totalPacketsResent;
/*  94 */     this.totalPacketsReceived = totalPacketsReceived;
/*     */     
/*  96 */     this.totalBytesSent = totalBytesSent;
/*  97 */     this.totalBytesResent = totalBytesResent;
/*  98 */     this.totalBytesReceived = totalBytesReceived;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public long getTotalTxnEventsReceived() { return this.totalTxnEventsReceived; }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public long getTotalPacketsReceived() { return this.totalPacketsReceived; }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public long getTotalBytesSent() { return this.totalBytesSent; }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public long getTotalBytesResent() { return this.totalBytesResent; }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public long getTotalBytesReceived() { return this.totalBytesReceived; }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public String getLastOutgoingAcks() { return this.lastOutgoingAcks; }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public int getOutgoingPacketsCacheSize() { return this.outgoingPacketsCacheSize; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public long getCurrentPacketId() { return this.currentPacketId; }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public long getMinAckedPacketId() { return this.minAckedPacketId; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public long getTotalTxnEventsSent() { return this.totalTxnEventsSent; }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public long getTotalPacketsSent() { return this.totalPacketsSent; }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public long getTotalPacketsResent() { return this.totalPacketsResent; }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public long getCurrentGroupSize() { return this.currentGroupSize; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\McastStatus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */