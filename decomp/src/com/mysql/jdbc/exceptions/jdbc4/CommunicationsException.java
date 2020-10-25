/*    */ package com.mysql.jdbc.exceptions.jdbc4;
/*    */ 
/*    */ import com.mysql.jdbc.MySQLConnection;
/*    */ import com.mysql.jdbc.SQLError;
/*    */ import com.mysql.jdbc.StreamingNotifiable;
/*    */ import java.sql.SQLRecoverableException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommunicationsException
/*    */   extends SQLRecoverableException
/*    */   implements StreamingNotifiable
/*    */ {
/*    */   private String exceptionMessage;
/*    */   private boolean streamingResultSetInPlay;
/*    */   
/*    */   public CommunicationsException(MySQLConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException) {
/* 52 */     this.streamingResultSetInPlay = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     this.exceptionMessage = SQLError.createLinkFailureMessageBasedOnHeuristics(conn, lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException, this.streamingResultSetInPlay);
/*    */ 
/*    */     
/* 61 */     if (underlyingException != null) {
/* 62 */       initCause(underlyingException);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String getMessage() { return this.exceptionMessage; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   public String getSQLState() { return "08S01"; }
/*    */ 
/*    */ 
/*    */   
/* 85 */   public void setWasStreamingResults() { this.streamingResultSetInPlay = true; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\exceptions\jdbc4\CommunicationsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */