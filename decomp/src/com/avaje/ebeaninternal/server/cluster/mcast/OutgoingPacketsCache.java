/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutgoingPacketsCache
/*    */ {
/* 41 */   private final Map<Long, Packet> packetMap = new TreeMap();
/*    */ 
/*    */   
/* 44 */   public int size() { return this.packetMap.size(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public Packet getPacket(Long packetId) { return (Packet)this.packetMap.get(packetId); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String toString() { return this.packetMap.keySet().toString(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void remove(Packet packet) { this.packetMap.remove(Long.valueOf(packet.getPacketId())); }
/*    */ 
/*    */   
/*    */   public void registerPackets(List<Packet> packets) {
/* 63 */     for (int i = 0; i < packets.size(); i++) {
/* 64 */       Packet p = (Packet)packets.get(i);
/* 65 */       this.packetMap.put(Long.valueOf(p.getPacketId()), p);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int trimAll() {
/* 70 */     int size = this.packetMap.size();
/* 71 */     this.packetMap.clear();
/* 72 */     return size;
/*    */   }
/*    */   
/*    */   public void trimAcknowledgedMessages(long minAcked) {
/* 76 */     Iterator<Long> it = this.packetMap.keySet().iterator();
/* 77 */     while (it.hasNext()) {
/* 78 */       Long pktId = (Long)it.next();
/* 79 */       if (minAcked >= pktId.longValue())
/* 80 */         it.remove(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\OutgoingPacketsCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */