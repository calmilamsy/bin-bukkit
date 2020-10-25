/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public abstract class SerialiseTransactionHelper
/*    */ {
/* 38 */   private final PacketWriter packetWriter = new PacketWriter(2147483647);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract SpiEbeanServer getEbeanServer(String paramString);
/*    */ 
/*    */ 
/*    */   
/*    */   public DataHolder createDataHolder(RemoteTransactionEvent transEvent) throws IOException {
/* 48 */     List<Packet> packetList = this.packetWriter.write(transEvent);
/* 49 */     if (packetList.size() != 1) {
/* 50 */       throw new RuntimeException("Always expecting 1 Packet but got " + packetList.size());
/*    */     }
/* 52 */     byte[] data = ((Packet)packetList.get(0)).getBytes();
/* 53 */     return new DataHolder(data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RemoteTransactionEvent read(DataHolder dataHolder) throws IOException {
/* 61 */     ByteArrayInputStream bi = new ByteArrayInputStream(dataHolder.getData());
/* 62 */     DataInputStream dataInput = new DataInputStream(bi);
/*    */     
/* 64 */     Packet header = Packet.readHeader(dataInput);
/*    */     
/* 66 */     SpiEbeanServer server = getEbeanServer(header.getServerName());
/*    */     
/* 68 */     PacketTransactionEvent tranEventPacket = PacketTransactionEvent.forRead(header, server);
/* 69 */     tranEventPacket.read(dataInput);
/*    */     
/* 71 */     return tranEventPacket.getEvent();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\SerialiseTransactionHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */