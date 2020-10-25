/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.cluster.Packet;
/*     */ import com.avaje.ebeaninternal.server.cluster.PacketMessages;
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ public class McastPacketControl
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(McastPacketControl.class.getName());
/*     */   
/*     */   private final String localSenderHostPort;
/*     */   private final McastClusterManager owner;
/*     */   
/*     */   public McastPacketControl(McastClusterManager owner, String localSenderHostPort, int maxResendIncoming) {
/*  46 */     this.groupMembers = new HashSet();
/*     */     
/*  48 */     this.outgoingPacketsAcked = new OutgoingPacketsAcked();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.owner = owner;
/*  54 */     this.localSenderHostPort = localSenderHostPort;
/*  55 */     this.incomingPacketsProcessed = new IncomingPacketsProcessed(maxResendIncoming);
/*     */   }
/*     */   
/*     */   private final HashSet<String> groupMembers;
/*     */   private final OutgoingPacketsAcked outgoingPacketsAcked;
/*     */   private final IncomingPacketsProcessed incomingPacketsProcessed;
/*     */   
/*     */   protected void onListenerTimeout() {
/*  63 */     if (this.groupMembers.size() == 0) {
/*  64 */       this.owner.fromListenerTimeoutNoMembers();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processMessagesPacket(String senderHostPort, Packet header, DataInput dataInput, long totalPacketsReceived, long totalBytesReceived, long totalTransEventsReceived) throws IOException {
/*  71 */     PacketMessages packetMessages = PacketMessages.forRead(header);
/*  72 */     packetMessages.read(dataInput);
/*  73 */     List<Message> messages = packetMessages.getMessages();
/*     */     
/*  75 */     if (logger.isLoggable(Level.FINER)) {
/*  76 */       logger.finer("INCOMING Messages " + messages);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     MessageControl control = null;
/*  83 */     MessageAck ack = null;
/*  84 */     MessageResend resend = null;
/*     */ 
/*     */     
/*  87 */     for (i = 0; i < messages.size(); i++) {
/*  88 */       Message message = (Message)messages.get(i);
/*  89 */       if (message.isControlMessage()) {
/*     */         
/*  91 */         control = (MessageControl)message;
/*     */       }
/*  93 */       else if (this.localSenderHostPort.equals(message.getToHostPort())) {
/*  94 */         if (message instanceof MessageAck) {
/*  95 */           ack = (MessageAck)message;
/*  96 */         } else if (message instanceof MessageResend) {
/*  97 */           resend = (MessageResend)message;
/*     */         } else {
/*  99 */           logger.log(Level.SEVERE, "Expecting a MessageAck or MessageResend but got a " + message.getClass().getName());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 105 */     if (control != null) {
/* 106 */       if (control.getControlType() == 2) {
/* 107 */         this.groupMembers.remove(senderHostPort);
/* 108 */         logger.info("Cluster member leaving [" + senderHostPort + "] " + this.groupMembers.size() + " other members left");
/*     */         
/* 110 */         this.outgoingPacketsAcked.removeMember(senderHostPort);
/* 111 */         this.incomingPacketsProcessed.removeMember(senderHostPort);
/*     */       } else {
/* 113 */         this.groupMembers.add(senderHostPort);
/*     */       } 
/*     */     }
/*     */     
/* 117 */     long newMin = 0L;
/* 118 */     if (ack != null) {
/* 119 */       newMin = this.outgoingPacketsAcked.receivedAck(senderHostPort, ack);
/*     */     }
/*     */     
/* 122 */     if (newMin > 0L || control != null || resend != null) {
/* 123 */       int groupSize = this.groupMembers.size();
/*     */       
/* 125 */       this.owner.fromListener(newMin, control, resend, groupSize, totalPacketsReceived, totalBytesReceived, totalTransEventsReceived);
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
/* 136 */   public boolean isProcessPacket(String memberKey, long packetId) { return this.incomingPacketsProcessed.isProcessPacket(memberKey, packetId); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public AckResendMessages getAckResendMessages(IncomingPacketsLastAck lastAck) { return this.incomingPacketsProcessed.getAckResendMessages(lastAck); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\McastPacketControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */