/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
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
/*     */ public class CommunicationsException
/*     */   extends SQLException
/*     */   implements StreamingNotifiable
/*     */ {
/*     */   private String exceptionMessage;
/*     */   private boolean streamingResultSetInPlay;
/*     */   private MySQLConnection conn;
/*     */   private long lastPacketSentTimeMs;
/*     */   private long lastPacketReceivedTimeMs;
/*     */   private Exception underlyingException;
/*     */   
/*     */   public CommunicationsException(MySQLConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException) {
/*  45 */     this.exceptionMessage = null;
/*     */     
/*  47 */     this.streamingResultSetInPlay = false;
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
/*  58 */     this.conn = conn;
/*  59 */     this.lastPacketReceivedTimeMs = lastPacketReceivedTimeMs;
/*  60 */     this.lastPacketSentTimeMs = lastPacketSentTimeMs;
/*  61 */     this.underlyingException = underlyingException;
/*     */     
/*  63 */     if (underlyingException != null) {
/*  64 */       initCause(underlyingException);
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
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  78 */     if (this.exceptionMessage == null) {
/*  79 */       this.exceptionMessage = SQLError.createLinkFailureMessageBasedOnHeuristics(this.conn, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, this.underlyingException, this.streamingResultSetInPlay);
/*     */ 
/*     */       
/*  82 */       this.conn = null;
/*  83 */       this.underlyingException = null;
/*     */     } 
/*  85 */     return this.exceptionMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public String getSQLState() { return "08S01"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public void setWasStreamingResults() { this.streamingResultSetInPlay = true; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\CommunicationsException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */