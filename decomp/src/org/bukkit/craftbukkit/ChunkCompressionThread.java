/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.zip.Deflater;
/*     */ import net.minecraft.server.EntityPlayer;
/*     */ import net.minecraft.server.Packet;
/*     */ import net.minecraft.server.Packet51MapChunk;
/*     */ 
/*     */ public final class ChunkCompressionThread
/*     */   implements Runnable
/*     */ {
/*  14 */   private static final ChunkCompressionThread instance = new ChunkCompressionThread();
/*     */   
/*     */   private static boolean isRunning = false;
/*  17 */   private final int QUEUE_CAPACITY = 10240;
/*  18 */   private final HashMap<EntityPlayer, Integer> queueSizePerPlayer = new HashMap();
/*  19 */   private final BlockingQueue<QueuedPacket> packetQueue = new LinkedBlockingQueue('⠀');
/*     */   
/*  21 */   private final int CHUNK_SIZE = 81920;
/*  22 */   private final int REDUCED_DEFLATE_THRESHOLD = 20480;
/*  23 */   private final int DEFLATE_LEVEL_CHUNKS = 6;
/*  24 */   private final int DEFLATE_LEVEL_PARTS = 1;
/*     */   
/*  26 */   private final Deflater deflater = new Deflater();
/*  27 */   private byte[] deflateBuffer = new byte[82020];
/*     */   
/*     */   public static void startThread() {
/*  30 */     if (!isRunning) {
/*  31 */       isRunning = true;
/*  32 */       (new Thread(instance)).start();
/*     */     } 
/*     */   }
/*     */   public void run() {
/*     */     while (true) {
/*     */       
/*     */       try { while (true)
/*  39 */           handleQueuedPacket((QueuedPacket)this.packetQueue.take());  break; }
/*  40 */       catch (InterruptedException ie) {  }
/*  41 */       catch (Exception e)
/*  42 */       { e.printStackTrace(); }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleQueuedPacket(QueuedPacket queuedPacket) {
/*  48 */     addToPlayerQueueSize(queuedPacket.player, -1);
/*     */     
/*  50 */     if (queuedPacket.compress) {
/*  51 */       handleMapChunk(queuedPacket);
/*     */     }
/*  53 */     sendToNetworkQueue(queuedPacket);
/*     */   }
/*     */   
/*     */   private void handleMapChunk(QueuedPacket queuedPacket) {
/*  57 */     Packet51MapChunk packet = (Packet51MapChunk)queuedPacket.packet;
/*     */ 
/*     */     
/*  60 */     if (packet.g != null) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     int dataSize = packet.rawData.length;
/*  65 */     if (this.deflateBuffer.length < dataSize + 100) {
/*  66 */       this.deflateBuffer = new byte[dataSize + 100];
/*     */     }
/*     */     
/*  69 */     this.deflater.reset();
/*  70 */     this.deflater.setLevel((dataSize < 20480) ? 1 : 6);
/*  71 */     this.deflater.setInput(packet.rawData);
/*  72 */     this.deflater.finish();
/*  73 */     int size = this.deflater.deflate(this.deflateBuffer);
/*  74 */     if (size == 0) {
/*  75 */       size = this.deflater.deflate(this.deflateBuffer);
/*     */     }
/*     */ 
/*     */     
/*  79 */     packet.g = new byte[size];
/*  80 */     packet.h = size;
/*  81 */     System.arraycopy(this.deflateBuffer, 0, packet.g, 0, size);
/*     */   }
/*     */ 
/*     */   
/*  85 */   private void sendToNetworkQueue(QueuedPacket queuedPacket) { queuedPacket.player.netServerHandler.networkManager.queue(queuedPacket.packet); }
/*     */ 
/*     */   
/*     */   public static void sendPacket(EntityPlayer player, Packet packet) {
/*  89 */     if (packet instanceof Packet51MapChunk) {
/*     */       
/*  91 */       instance.addQueuedPacket(new QueuedPacket(player, packet, true));
/*     */     } else {
/*     */       
/*  94 */       instance.addQueuedPacket(new QueuedPacket(player, packet, false));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addToPlayerQueueSize(EntityPlayer player, int amount) {
/*  99 */     synchronized (this.queueSizePerPlayer) {
/* 100 */       Integer count = (Integer)this.queueSizePerPlayer.get(player);
/* 101 */       amount += ((count == null) ? 0 : count.intValue());
/* 102 */       if (amount == 0) {
/* 103 */         this.queueSizePerPlayer.remove(player);
/*     */       } else {
/* 105 */         this.queueSizePerPlayer.put(player, Integer.valueOf(amount));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getPlayerQueueSize(EntityPlayer player) {
/* 111 */     synchronized (instance.queueSizePerPlayer) {
/* 112 */       Integer count = (Integer)instance.queueSizePerPlayer.get(player);
/* 113 */       return (count == null) ? 0 : count.intValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addQueuedPacket(QueuedPacket task) {
/* 118 */     addToPlayerQueueSize(task.player, 1);
/*     */     
/*     */     while (true) {
/*     */       try {
/* 122 */         this.packetQueue.put(task);
/*     */         return;
/* 124 */       } catch (InterruptedException e) {}
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class QueuedPacket
/*     */   {
/*     */     final EntityPlayer player;
/*     */     final Packet packet;
/*     */     final boolean compress;
/*     */     
/*     */     QueuedPacket(EntityPlayer player, Packet packet, boolean compress) {
/* 135 */       this.player = player;
/* 136 */       this.packet = packet;
/* 137 */       this.compress = compress;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\ChunkCompressionThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */