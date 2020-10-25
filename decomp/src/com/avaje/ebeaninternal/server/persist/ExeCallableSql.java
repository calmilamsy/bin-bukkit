/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiCallableSql;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class ExeCallableSql
/*     */ {
/*  42 */   private static final Logger logger = Logger.getLogger(ExeCallableSql.class.getName());
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final PstmtFactory pstmtFactory;
/*     */   
/*     */   public ExeCallableSql(Binder binder, PstmtBatch pstmtBatch) {
/*  49 */     this.binder = binder;
/*     */     
/*  51 */     this.pstmtFactory = new PstmtFactory(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int execute(PersistRequestCallableSql request) {
/*  59 */     SpiTransaction t = request.getTransaction();
/*     */     
/*  61 */     batchThisRequest = t.isBatchThisRequest();
/*     */     
/*  63 */     cstmt = null;
/*     */     
/*     */     try {
/*  66 */       cstmt = bindStmt(request, batchThisRequest);
/*     */       
/*  68 */       if (batchThisRequest) {
/*  69 */         cstmt.addBatch();
/*     */         
/*  71 */         return -1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  76 */       int rowCount = request.executeUpdate();
/*  77 */       request.postExecute();
/*  78 */       return rowCount;
/*     */ 
/*     */     
/*     */     }
/*  82 */     catch (SQLException ex) {
/*  83 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/*  86 */       if (!batchThisRequest && cstmt != null) {
/*     */         try {
/*  88 */           cstmt.close();
/*  89 */         } catch (SQLException e) {
/*  90 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CallableStatement bindStmt(PersistRequestCallableSql request, boolean batchThisRequest) throws SQLException {
/*     */     CallableStatement cstmt;
/*  99 */     SpiCallableSql callableSql = request.getCallableSql();
/* 100 */     SpiTransaction t = request.getTransaction();
/*     */     
/* 102 */     String sql = callableSql.getSql();
/*     */     
/* 104 */     BindParams bindParams = callableSql.getBindParams();
/*     */ 
/*     */     
/* 107 */     sql = BindParamsParser.parse(bindParams, sql);
/*     */     
/* 109 */     boolean logSql = request.isLogSql();
/*     */ 
/*     */     
/* 112 */     if (batchThisRequest) {
/* 113 */       cstmt = this.pstmtFactory.getCstmt(t, logSql, sql, request);
/*     */     } else {
/*     */       
/* 116 */       if (logSql) {
/* 117 */         t.logInternal(sql);
/*     */       }
/* 119 */       cstmt = this.pstmtFactory.getCstmt(t, sql);
/*     */     } 
/*     */     
/* 122 */     if (callableSql.getTimeout() > 0) {
/* 123 */       cstmt.setQueryTimeout(callableSql.getTimeout());
/*     */     }
/*     */     
/* 126 */     String bindLog = null;
/* 127 */     if (!bindParams.isEmpty()) {
/* 128 */       bindLog = this.binder.bind(bindParams, new DataBind(cstmt));
/*     */     }
/*     */     
/* 131 */     request.setBindLog(bindLog);
/*     */ 
/*     */     
/* 134 */     request.setBound(bindParams, cstmt);
/*     */     
/* 136 */     return cstmt;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\ExeCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */