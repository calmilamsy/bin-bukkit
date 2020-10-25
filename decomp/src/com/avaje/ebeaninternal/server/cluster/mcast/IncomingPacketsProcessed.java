/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class IncomingPacketsProcessed
/*     */ {
/*     */   private final ConcurrentHashMap<String, GotAllPoint> mapByMember;
/*     */   private final int maxResendIncoming;
/*     */   
/*     */   public IncomingPacketsProcessed(int maxResendIncoming) {
/*  46 */     this.mapByMember = new ConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     this.maxResendIncoming = maxResendIncoming;
/*     */   }
/*     */ 
/*     */   
/*  55 */   public void removeMember(String memberKey) { this.mapByMember.remove(memberKey); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProcessPacket(String memberKey, long packetId) {
/*  64 */     GotAllPoint memberPackets = getMemberPackets(memberKey);
/*  65 */     return memberPackets.processPacket(packetId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AckResendMessages getAckResendMessages(IncomingPacketsLastAck lastAck) {
/*  76 */     AckResendMessages response = new AckResendMessages();
/*     */     
/*  78 */     for (GotAllPoint member : this.mapByMember.values()) {
/*     */       
/*  80 */       MessageAck lastAckMessage = lastAck.getLastAck(member.getMemberKey());
/*     */       
/*  82 */       member.addAckResendMessages(response, lastAckMessage);
/*     */     } 
/*     */     
/*  85 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GotAllPoint getMemberPackets(String memberKey) {
/*  93 */     GotAllPoint memberGotAllPoint = (GotAllPoint)this.mapByMember.get(memberKey);
/*  94 */     if (memberGotAllPoint == null) {
/*  95 */       memberGotAllPoint = new GotAllPoint(memberKey, this.maxResendIncoming);
/*  96 */       this.mapByMember.put(memberKey, memberGotAllPoint);
/*     */     } 
/*  98 */     return memberGotAllPoint;
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
/*     */   public static class GotAllPoint
/*     */   {
/* 111 */     private static final Logger logger = Logger.getLogger(GotAllPoint.class.getName());
/*     */     
/*     */     private final String memberKey;
/*     */     
/*     */     private final int maxResendIncoming;
/*     */     
/*     */     private long gotAllPoint;
/*     */     private long gotMaxPoint;
/*     */     private ArrayList<Long> outOfOrderList;
/*     */     private HashMap<Long, Integer> resendCountMap;
/*     */     
/*     */     public GotAllPoint(String memberKey, int maxResendIncoming) {
/* 123 */       this.outOfOrderList = new ArrayList();
/*     */       
/* 125 */       this.resendCountMap = new HashMap();
/*     */ 
/*     */       
/* 128 */       this.memberKey = memberKey;
/* 129 */       this.maxResendIncoming = maxResendIncoming;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addAckResendMessages(AckResendMessages response, MessageAck lastAckMessage) {
/* 137 */       synchronized (this) {
/* 138 */         if (lastAckMessage == null || lastAckMessage.getGotAllPacketId() < this.gotAllPoint)
/*     */         {
/*     */ 
/*     */           
/* 142 */           response.add(new MessageAck(this.memberKey, this.gotAllPoint));
/*     */         }
/*     */         
/* 145 */         if (getMissingPacketCount() > 0) {
/*     */           
/* 147 */           List<Long> missingPackets = getMissingPackets();
/* 148 */           response.add(new MessageResend(this.memberKey, missingPackets));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 154 */     public String getMemberKey() { return this.memberKey; }
/*     */ 
/*     */     
/*     */     public long getGotAllPoint() {
/* 158 */       synchronized (this) {
/* 159 */         return this.gotAllPoint;
/*     */       } 
/*     */     }
/*     */     
/*     */     public long getGotMaxPoint() {
/* 164 */       synchronized (this) {
/* 165 */         return this.gotMaxPoint;
/*     */       } 
/*     */     }
/*     */     
/*     */     private int getMissingPacketCount() {
/* 170 */       if (this.gotMaxPoint <= this.gotAllPoint) {
/* 171 */         if (!this.resendCountMap.isEmpty()) {
/* 172 */           this.resendCountMap.clear();
/*     */         }
/* 174 */         return 0;
/*     */       } 
/* 176 */       return (int)(this.gotMaxPoint - this.gotAllPoint) - this.outOfOrderList.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Long> getMissingPackets() {
/* 181 */       synchronized (this) {
/* 182 */         ArrayList<Long> missingList = new ArrayList<Long>();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 187 */         boolean lostPacket = false;
/*     */         long i;
/* 189 */         for (i = this.gotAllPoint + 1L; i < this.gotMaxPoint; i++) {
/* 190 */           Long packetId = Long.valueOf(i);
/* 191 */           if (!this.outOfOrderList.contains(packetId)) {
/* 192 */             if (incrementResendCount(packetId)) {
/*     */               
/* 194 */               missingList.add(packetId);
/*     */             } else {
/* 196 */               lostPacket = true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 201 */         if (lostPacket) {
/* 202 */           checkOutOfOrderList();
/*     */         }
/*     */         
/* 205 */         return missingList;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean incrementResendCount(Long packetId) {
/* 213 */       Integer resendCount = (Integer)this.resendCountMap.get(packetId);
/* 214 */       if (resendCount != null) {
/* 215 */         int i = resendCount.intValue() + 1;
/* 216 */         if (i > this.maxResendIncoming) {
/*     */           
/* 218 */           logger.warning("Exceeded maxResendIncoming[" + this.maxResendIncoming + "] for packet[" + packetId + "]. Giving up on requesting it.");
/* 219 */           this.resendCountMap.remove(packetId);
/* 220 */           this.outOfOrderList.add(packetId);
/* 221 */           return false;
/*     */         } 
/* 223 */         resendCount = Integer.valueOf(i);
/* 224 */         this.resendCountMap.put(packetId, resendCount);
/*     */       } else {
/* 226 */         this.resendCountMap.put(packetId, ONE);
/*     */       } 
/* 228 */       return true;
/*     */     }
/*     */     
/* 231 */     private static final Integer ONE = Integer.valueOf(1);
/*     */     
/*     */     public boolean processPacket(long packetId) {
/* 234 */       synchronized (this) {
/*     */         
/* 236 */         if (this.gotAllPoint == 0L) {
/* 237 */           this.gotAllPoint = packetId;
/* 238 */           return true;
/*     */         } 
/* 240 */         if (packetId <= this.gotAllPoint)
/*     */         {
/* 242 */           return false;
/*     */         }
/*     */         
/* 245 */         if (!this.resendCountMap.isEmpty()) {
/* 246 */           this.resendCountMap.remove(Long.valueOf(packetId));
/*     */         }
/*     */         
/* 249 */         if (packetId == this.gotAllPoint + 1L) {
/* 250 */           this.gotAllPoint = packetId;
/*     */         } else {
/* 252 */           if (packetId > this.gotMaxPoint) {
/* 253 */             this.gotMaxPoint = packetId;
/*     */           }
/* 255 */           this.outOfOrderList.add(Long.valueOf(packetId));
/*     */         } 
/* 257 */         checkOutOfOrderList();
/* 258 */         return true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void checkOutOfOrderList() {
/*     */       boolean continueCheck;
/* 264 */       if (this.outOfOrderList.size() == 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       do {
/* 270 */         continueCheck = false;
/* 271 */         long nextPoint = this.gotAllPoint + 1L;
/*     */         
/* 273 */         Iterator<Long> it = this.outOfOrderList.iterator();
/* 274 */         while (it.hasNext()) {
/* 275 */           Long id = (Long)it.next();
/* 276 */           if (id.longValue() == nextPoint) {
/*     */             
/* 278 */             it.remove();
/* 279 */             this.gotAllPoint = nextPoint;
/* 280 */             continueCheck = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 284 */       } while (continueCheck);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\IncomingPacketsProcessed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */