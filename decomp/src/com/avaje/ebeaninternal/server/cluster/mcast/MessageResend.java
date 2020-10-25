/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
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
/*    */ public class MessageResend
/*    */   implements Message
/*    */ {
/*    */   private final String toHostPort;
/*    */   private final List<Long> resendPacketIds;
/*    */   
/*    */   public MessageResend(String toHostPort, List<Long> resendPacketIds) {
/* 38 */     this.toHostPort = toHostPort;
/* 39 */     this.resendPacketIds = resendPacketIds;
/*    */   }
/*    */ 
/*    */   
/* 43 */   public MessageResend(String toHostPort) { this(toHostPort, new ArrayList(4)); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public String toString() { return "Resend " + this.toHostPort + " " + this.resendPacketIds; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public boolean isControlMessage() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String getToHostPort() { return this.toHostPort; }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void add(long packetId) { this.resendPacketIds.add(Long.valueOf(packetId)); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public List<Long> getResendPacketIds() { return this.resendPacketIds; }
/*    */ 
/*    */ 
/*    */   
/*    */   public static MessageResend readBinaryMessage(DataInput dataInput) throws IOException {
/* 68 */     String hostPort = dataInput.readUTF();
/*    */     
/* 70 */     MessageResend msg = new MessageResend(hostPort);
/*    */     
/* 72 */     int numberOfPacketIds = dataInput.readInt();
/* 73 */     for (int i = 0; i < numberOfPacketIds; i++) {
/* 74 */       long packetId = dataInput.readLong();
/* 75 */       msg.add(packetId);
/*    */     } 
/*    */     
/* 78 */     return msg;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 83 */     BinaryMessage m = new BinaryMessage(this.toHostPort.length() * 2 + 20);
/*    */     
/* 85 */     DataOutputStream os = m.getOs();
/* 86 */     os.writeInt(9);
/* 87 */     os.writeUTF(this.toHostPort);
/* 88 */     os.writeInt(this.resendPacketIds.size());
/* 89 */     for (int i = 0; i < this.resendPacketIds.size(); i++) {
/* 90 */       Long packetId = (Long)this.resendPacketIds.get(i);
/* 91 */       os.writeLong(packetId.longValue());
/*    */     } 
/* 93 */     os.flush();
/* 94 */     msgList.add(m);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\MessageResend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */