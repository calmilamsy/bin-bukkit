/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class TransactionLogBuffer
/*     */ {
/*     */   private final String transactionId;
/*     */   private final ArrayList<LogEntry> buffer;
/*     */   private final int maxSize;
/*     */   private int currentSize;
/*     */   
/*     */   public TransactionLogBuffer(int maxSize, String transactionId) {
/*  50 */     this.maxSize = maxSize;
/*  51 */     this.transactionId = transactionId;
/*  52 */     this.buffer = new ArrayList(maxSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public TransactionLogBuffer newBuffer() { return new TransactionLogBuffer(this.maxSize, this.transactionId); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public String getTransactionId() { return this.transactionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(String msg) {
/*  73 */     this.buffer.add(new LogEntry(msg)); return 
/*  74 */       (++this.currentSize >= this.maxSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public boolean isEmpty() { return this.buffer.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public List<LogEntry> messages() { return this.buffer; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class LogEntry
/*     */   {
/*     */     private final long timestamp;
/*     */ 
/*     */     
/*     */     private final String msg;
/*     */ 
/*     */ 
/*     */     
/*     */     public LogEntry(String msg) {
/* 103 */       this.timestamp = System.currentTimeMillis();
/* 104 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     public long getTimestamp() { return this.timestamp; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     public String getMsg() { return this.msg; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */