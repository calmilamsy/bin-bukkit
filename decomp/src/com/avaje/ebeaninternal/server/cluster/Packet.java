/*     */ package com.avaje.ebeaninternal.server.cluster;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
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
/*     */ public class Packet
/*     */ {
/*     */   public static final short TYPE_MESSAGES = 1;
/*     */   public static final short TYPE_TRANSEVENT = 2;
/*     */   protected short packetType;
/*     */   protected long packetId;
/*     */   protected long timestamp;
/*     */   protected String serverName;
/*     */   protected ByteArrayOutputStream buffer;
/*     */   protected DataOutputStream dataOut;
/*     */   protected byte[] bytes;
/*     */   private int messageCount;
/*     */   private int resendCount;
/*     */   
/*  91 */   public static Packet forWrite(short packetType, long packetId, long timestamp, String serverName) throws IOException { return new Packet(true, packetType, packetId, timestamp, serverName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Packet readHeader(DataInput dataInput) throws IOException {
/*  99 */     short packetType = dataInput.readShort();
/* 100 */     long packetId = dataInput.readLong();
/* 101 */     long timestamp = dataInput.readLong();
/* 102 */     String serverName = dataInput.readUTF();
/*     */     
/* 104 */     return new Packet(false, packetType, packetId, timestamp, serverName);
/*     */   }
/*     */   
/*     */   protected Packet(boolean write, short packetType, long packetId, long timestamp, String serverName) throws IOException {
/* 108 */     this.packetType = packetType;
/* 109 */     this.packetId = packetId;
/* 110 */     this.timestamp = timestamp;
/* 111 */     this.serverName = serverName;
/* 112 */     if (write) {
/* 113 */       this.buffer = new ByteArrayOutputStream();
/* 114 */       this.dataOut = new DataOutputStream(this.buffer);
/* 115 */       writeHeader();
/*     */     } else {
/* 117 */       this.buffer = null;
/* 118 */       this.dataOut = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeHeader() throws IOException {
/* 123 */     this.dataOut.writeShort(this.packetType);
/* 124 */     this.dataOut.writeLong(this.packetId);
/* 125 */     this.dataOut.writeLong(this.timestamp);
/* 126 */     this.dataOut.writeUTF(this.serverName);
/*     */   }
/*     */ 
/*     */   
/* 130 */   public int incrementResendCount() { return this.resendCount++; }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public short getPacketType() { return this.packetType; }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public long getPacketId() { return this.packetId; }
/*     */ 
/*     */ 
/*     */   
/* 142 */   public long getTimestamp() { return this.timestamp; }
/*     */ 
/*     */ 
/*     */   
/* 146 */   public String getServerName() { return this.serverName; }
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void writeEof() throws IOException { this.dataOut.writeBoolean(false); }
/*     */ 
/*     */   
/*     */   public void read(DataInput dataInput) throws IOException {
/* 154 */     boolean more = dataInput.readBoolean();
/* 155 */     while (more) {
/* 156 */       int msgType = dataInput.readInt();
/* 157 */       readMessage(dataInput, msgType);
/*     */       
/* 159 */       more = dataInput.readBoolean();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readMessage(DataInput dataInput, int msgType) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeBinaryMessage(BinaryMessage msg, int maxPacketSize) throws IOException {
/* 178 */     byte[] bytes = msg.getByteArray();
/*     */     
/* 180 */     if (this.messageCount > 0 && bytes.length + this.buffer.size() > maxPacketSize) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       this.dataOut.writeBoolean(false);
/* 186 */       return false;
/*     */     } 
/* 188 */     this.messageCount++;
/*     */     
/* 190 */     this.dataOut.writeBoolean(true);
/* 191 */     this.dataOut.write(bytes);
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */   
/* 196 */   public int getSize() { return getBytes().length; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/* 203 */     if (this.bytes == null) {
/* 204 */       this.bytes = this.buffer.toByteArray();
/* 205 */       this.buffer = null;
/* 206 */       this.dataOut = null;
/*     */     } 
/* 208 */     return this.bytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\Packet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */