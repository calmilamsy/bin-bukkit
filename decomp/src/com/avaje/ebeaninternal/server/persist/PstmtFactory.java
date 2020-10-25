/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
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
/*     */ 
/*     */ public class PstmtFactory
/*     */ {
/*     */   private final PstmtBatch pstmtBatch;
/*     */   
/*  43 */   public PstmtFactory(PstmtBatch pstmtBatch) { this.pstmtBatch = pstmtBatch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement getCstmt(SpiTransaction t, String sql) throws SQLException {
/*  50 */     Connection conn = t.getInternalConnection();
/*  51 */     return conn.prepareCall(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement getPstmt(SpiTransaction t, String sql) throws SQLException {
/*  58 */     Connection conn = t.getInternalConnection();
/*  59 */     return conn.prepareStatement(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement getPstmt(SpiTransaction t, boolean logSql, String sql, BatchPostExecute batchExe) throws SQLException {
/*  68 */     BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
/*  69 */     PreparedStatement stmt = batch.getStmt(sql, batchExe);
/*     */     
/*  71 */     if (stmt != null) {
/*  72 */       return stmt;
/*     */     }
/*     */     
/*  75 */     if (logSql) {
/*  76 */       t.logInternal(sql);
/*     */     }
/*     */     
/*  79 */     Connection conn = t.getInternalConnection();
/*  80 */     stmt = conn.prepareStatement(sql);
/*     */     
/*  82 */     if (this.pstmtBatch != null) {
/*  83 */       this.pstmtBatch.setBatchSize(stmt, t.getBatchControl().getBatchSize());
/*     */     }
/*     */     
/*  86 */     BatchedPstmt bs = new BatchedPstmt(stmt, false, sql, this.pstmtBatch, false);
/*  87 */     batch.addStmt(bs, batchExe);
/*  88 */     return stmt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement getCstmt(SpiTransaction t, boolean logSql, String sql, BatchPostExecute batchExe) throws SQLException {
/*  97 */     BatchedPstmtHolder batch = t.getBatchControl().getPstmtHolder();
/*  98 */     CallableStatement stmt = (CallableStatement)batch.getStmt(sql, batchExe);
/*     */     
/* 100 */     if (stmt != null) {
/* 101 */       return stmt;
/*     */     }
/*     */     
/* 104 */     if (logSql) {
/* 105 */       t.logInternal(sql);
/*     */     }
/*     */     
/* 108 */     Connection conn = t.getInternalConnection();
/* 109 */     stmt = conn.prepareCall(sql);
/*     */     
/* 111 */     BatchedPstmt bs = new BatchedPstmt(stmt, false, sql, this.pstmtBatch, false);
/* 112 */     batch.addStmt(bs, batchExe);
/* 113 */     return stmt;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\PstmtFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */