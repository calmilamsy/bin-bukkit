/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class MessageControl
/*    */   implements Message
/*    */ {
/*    */   public static final short TYPE_JOIN = 1;
/*    */   public static final short TYPE_LEAVE = 2;
/*    */   public static final short TYPE_PING = 3;
/*    */   public static final short TYPE_JOINRESPONSE = 7;
/*    */   public static final short TYPE_PINGRESPONSE = 8;
/*    */   private final short controlType;
/*    */   private final String fromHostPort;
/*    */   
/*    */   public static MessageControl readBinaryMessage(DataInput dataInput) throws IOException {
/* 41 */     short controlType = dataInput.readShort();
/* 42 */     String hostPort = dataInput.readUTF();
/* 43 */     return new MessageControl(controlType, hostPort);
/*    */   }
/*    */   
/*    */   public MessageControl(short controlType, String helloFromHostPort) {
/* 47 */     this.controlType = controlType;
/* 48 */     this.fromHostPort = helloFromHostPort;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 53 */     switch (this.controlType) { case 1:
/* 54 */         return "Join " + this.fromHostPort;
/* 55 */       case 2: return "Leave " + this.fromHostPort;
/* 56 */       case 3: return "Ping " + this.fromHostPort;
/* 57 */       case 8: return "PingResponse " + this.fromHostPort; }
/*    */ 
/*    */     
/* 60 */     throw new RuntimeException("Invalid controlType " + this.controlType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public boolean isControlMessage() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public short getControlType() { return this.controlType; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public String getToHostPort() { return "*"; }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public String getFromHostPort() { return this.fromHostPort; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 82 */     BinaryMessage m = new BinaryMessage(this.fromHostPort.length() * 2 + 10);
/*    */     
/* 84 */     DataOutputStream os = m.getOs();
/* 85 */     os.writeInt(0);
/* 86 */     os.writeShort(this.controlType);
/* 87 */     os.writeUTF(this.fromHostPort);
/* 88 */     os.flush();
/*    */     
/* 90 */     msgList.add(m);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cluster\mcast\MessageControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */