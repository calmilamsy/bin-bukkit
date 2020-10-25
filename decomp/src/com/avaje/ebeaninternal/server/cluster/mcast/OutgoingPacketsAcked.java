/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutgoingPacketsAcked
/*     */ {
/*     */   private long minimumGotAllPacketId;
/*  29 */   private Map<String, GroupMemberAck> recievedByMap = new HashMap();
/*     */   
/*     */   public int getGroupSize() {
/*  32 */     synchronized (this) {
/*  33 */       return this.recievedByMap.size();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getMinimumGotAllPacketId() {
/*  38 */     synchronized (this) {
/*  39 */       return this.minimumGotAllPacketId;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeMember(String groupMember) {
/*  44 */     synchronized (this) {
/*  45 */       this.recievedByMap.remove(groupMember);
/*  46 */       resetGotAllMin();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean resetGotAllMin() {
/*     */     long tempMin;
/*  53 */     if (this.recievedByMap.isEmpty()) {
/*     */       
/*  55 */       tempMin = Float.MAX_VALUE;
/*     */     } else {
/*  57 */       tempMin = Float.MAX_VALUE;
/*     */     } 
/*     */     
/*  60 */     for (GroupMemberAck groupMemAck : this.recievedByMap.values()) {
/*  61 */       long memberMin = groupMemAck.getGotAllPacketId();
/*  62 */       if (memberMin < tempMin)
/*     */       {
/*  64 */         tempMin = memberMin;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     if (tempMin != this.minimumGotAllPacketId) {
/*  69 */       this.minimumGotAllPacketId = tempMin;
/*  70 */       return true;
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long receivedAck(String groupMember, MessageAck ack) {
/*  78 */     synchronized (this) {
/*     */       
/*  80 */       boolean checkMin = false;
/*     */       
/*  82 */       GroupMemberAck groupMemberAck = (GroupMemberAck)this.recievedByMap.get(groupMember);
/*  83 */       if (groupMemberAck == null) {
/*     */         
/*  85 */         groupMemberAck = new GroupMemberAck(null);
/*  86 */         groupMemberAck.setIfBigger(ack.getGotAllPacketId());
/*  87 */         this.recievedByMap.put(groupMember, groupMemberAck);
/*  88 */         checkMin = true;
/*     */       } else {
/*  90 */         checkMin = (groupMemberAck.getGotAllPacketId() == this.minimumGotAllPacketId);
/*     */         
/*  92 */         groupMemberAck.setIfBigger(ack.getGotAllPacketId());
/*     */       } 
/*     */       
/*  95 */       boolean minChanged = false;
/*     */ 
/*     */       
/*  98 */       if (checkMin || this.minimumGotAllPacketId == 0L)
/*     */       {
/* 100 */         minChanged = resetGotAllMin();
/*     */       }
/*     */ 
/*     */       
/* 104 */       return minChanged ? this.minimumGotAllPacketId : 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class GroupMemberAck
/*     */   {
/*     */     private long gotAllPacketId;
/*     */ 
/*     */     
/*     */     private GroupMemberAck() {}
/*     */     
/* 116 */     private long getGotAllPacketId() { return this.gotAllPacketId; }
/*     */ 
/*     */     
/*     */     private void setIfBigger(long newGotAll) {
/* 120 */       if (newGotAll > this.gotAllPacketId)
/* 121 */         this.gotAllPacketId = newGotAll; 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\OutgoingPacketsAcked.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */