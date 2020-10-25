/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import com.avaje.ebeaninternal.server.util.BindParamsParser;
/*     */ import java.sql.PreparedStatement;
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
/*     */ public class ExeOrmUpdate
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(ExeOrmUpdate.class.getName());
/*     */ 
/*     */   
/*     */   private final Binder binder;
/*     */ 
/*     */   
/*     */   private final PstmtFactory pstmtFactory;
/*     */ 
/*     */   
/*     */   public ExeOrmUpdate(Binder binder, PstmtBatch pstmtBatch) {
/*  53 */     this.pstmtFactory = new PstmtFactory(pstmtBatch);
/*  54 */     this.binder = binder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int execute(PersistRequestOrmUpdate request) {
/*  62 */     SpiTransaction t = request.getTransaction();
/*     */     
/*  64 */     batchThisRequest = t.isBatchThisRequest();
/*     */     
/*  66 */     pstmt = null;
/*     */     
/*     */     try {
/*  69 */       pstmt = bindStmt(request, batchThisRequest);
/*     */       
/*  71 */       if (batchThisRequest) {
/*  72 */         PstmtBatch pstmtBatch = request.getPstmtBatch();
/*  73 */         if (pstmtBatch != null) {
/*  74 */           pstmtBatch.addBatch(pstmt);
/*     */         } else {
/*  76 */           pstmt.addBatch();
/*     */         } 
/*     */         
/*  79 */         return -1;
/*     */       } 
/*     */       
/*  82 */       SpiUpdate<?> ormUpdate = request.getOrmUpdate();
/*  83 */       if (ormUpdate.getTimeout() > 0) {
/*  84 */         pstmt.setQueryTimeout(ormUpdate.getTimeout());
/*     */       }
/*     */       
/*  87 */       int rowCount = pstmt.executeUpdate();
/*  88 */       request.checkRowCount(rowCount);
/*  89 */       request.postExecute();
/*  90 */       return rowCount;
/*     */ 
/*     */     
/*     */     }
/*  94 */     catch (SQLException ex) {
/*  95 */       SpiUpdate<?> ormUpdate = request.getOrmUpdate();
/*  96 */       String msg = "Error executing: " + ormUpdate.getGeneratedSql();
/*  97 */       throw new PersistenceException(msg, ex);
/*     */     } finally {
/*     */       
/* 100 */       if (!batchThisRequest && pstmt != null) {
/*     */         try {
/* 102 */           pstmt.close();
/* 103 */         } catch (SQLException e) {
/* 104 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translate(PersistRequestOrmUpdate request, String sql) {
/* 115 */     BeanDescriptor<?> descriptor = request.getBeanDescriptor();
/* 116 */     return descriptor.convertOrmUpdateToSql(sql);
/*     */   }
/*     */   
/*     */   private PreparedStatement bindStmt(PersistRequestOrmUpdate request, boolean batchThisRequest) throws SQLException {
/*     */     PreparedStatement pstmt;
/* 121 */     SpiUpdate<?> ormUpdate = request.getOrmUpdate();
/* 122 */     SpiTransaction t = request.getTransaction();
/*     */     
/* 124 */     String sql = ormUpdate.getUpdateStatement();
/*     */ 
/*     */ 
/*     */     
/* 128 */     sql = translate(request, sql);
/*     */     
/* 130 */     BindParams bindParams = ormUpdate.getBindParams();
/*     */ 
/*     */     
/* 133 */     sql = BindParamsParser.parse(bindParams, sql);
/*     */     
/* 135 */     ormUpdate.setGeneratedSql(sql);
/*     */     
/* 137 */     boolean logSql = request.isLogSql();
/*     */ 
/*     */     
/* 140 */     if (batchThisRequest) {
/* 141 */       pstmt = this.pstmtFactory.getPstmt(t, logSql, sql, request);
/*     */     } else {
/*     */       
/* 144 */       if (logSql) {
/* 145 */         t.logInternal(sql);
/*     */       }
/* 147 */       pstmt = this.pstmtFactory.getPstmt(t, sql);
/*     */     } 
/*     */     
/* 150 */     String bindLog = null;
/* 151 */     if (!bindParams.isEmpty()) {
/* 152 */       bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
/*     */     }
/*     */     
/* 155 */     request.setBindLog(bindLog);
/*     */     
/* 157 */     return pstmt;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\ExeOrmUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */