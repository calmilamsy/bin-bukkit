/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
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
/*     */ 
/*     */ public class ExeUpdateSql
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(ExeUpdateSql.class.getName());
/*     */   
/*     */   private final Binder binder;
/*     */   
/*     */   private final PstmtFactory pstmtFactory;
/*     */   private final PstmtBatch pstmtBatch;
/*     */   private int defaultBatchSize;
/*     */   
/*     */   public ExeUpdateSql(Binder binder, PstmtBatch pstmtBatch) {
/*  52 */     this.defaultBatchSize = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.binder = binder;
/*  59 */     this.pstmtBatch = pstmtBatch;
/*  60 */     this.pstmtFactory = new PstmtFactory(pstmtBatch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int execute(PersistRequestUpdateSql request) {
/*  68 */     SpiTransaction t = request.getTransaction();
/*     */     
/*  70 */     batchThisRequest = t.isBatchThisRequest();
/*     */     
/*  72 */     pstmt = null;
/*     */     
/*     */     try {
/*  75 */       pstmt = bindStmt(request, batchThisRequest);
/*     */       
/*  77 */       if (batchThisRequest) {
/*  78 */         if (this.pstmtBatch != null) {
/*  79 */           this.pstmtBatch.addBatch(pstmt);
/*     */         } else {
/*  81 */           pstmt.addBatch();
/*     */         } 
/*     */         
/*  84 */         return -1;
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
/*  95 */       throw new PersistenceException(ex);
/*     */     } finally {
/*     */       
/*  98 */       if (!batchThisRequest && pstmt != null) {
/*     */         try {
/* 100 */           pstmt.close();
/* 101 */         } catch (SQLException e) {
/* 102 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PreparedStatement bindStmt(PersistRequestUpdateSql request, boolean batchThisRequest) throws SQLException {
/*     */     PreparedStatement pstmt;
/* 111 */     SpiSqlUpdate updateSql = request.getUpdateSql();
/* 112 */     SpiTransaction t = request.getTransaction();
/*     */     
/* 114 */     String sql = updateSql.getSql();
/*     */     
/* 116 */     BindParams bindParams = updateSql.getBindParams();
/*     */ 
/*     */     
/* 119 */     sql = BindParamsParser.parse(bindParams, sql);
/*     */     
/* 121 */     boolean logSql = request.isLogSql();
/*     */ 
/*     */     
/* 124 */     if (batchThisRequest) {
/* 125 */       pstmt = this.pstmtFactory.getPstmt(t, logSql, sql, request);
/* 126 */       if (this.pstmtBatch != null) {
/*     */         
/* 128 */         int batchSize = t.getBatchSize();
/* 129 */         if (batchSize < 1) {
/* 130 */           batchSize = this.defaultBatchSize;
/*     */         }
/* 132 */         this.pstmtBatch.setBatchSize(pstmt, batchSize);
/*     */       } 
/*     */     } else {
/*     */       
/* 136 */       if (logSql) {
/* 137 */         t.logInternal(sql);
/*     */       }
/* 139 */       pstmt = this.pstmtFactory.getPstmt(t, sql);
/*     */     } 
/*     */     
/* 142 */     if (updateSql.getTimeout() > 0) {
/* 143 */       pstmt.setQueryTimeout(updateSql.getTimeout());
/*     */     }
/*     */     
/* 146 */     String bindLog = null;
/* 147 */     if (!bindParams.isEmpty()) {
/* 148 */       bindLog = this.binder.bind(bindParams, new DataBind(pstmt));
/*     */     }
/*     */     
/* 151 */     request.setBindLog(bindLog);
/*     */ 
/*     */     
/* 154 */     parseUpdate(sql, request);
/*     */     
/* 156 */     return pstmt;
/*     */   }
/*     */ 
/*     */   
/*     */   private void determineType(String word1, String word2, String word3, PersistRequestUpdateSql request) {
/* 161 */     if (word1.equalsIgnoreCase("UPDATE")) {
/* 162 */       request.setType(PersistRequestUpdateSql.SqlType.SQL_UPDATE, word2, "UpdateSql");
/*     */     }
/* 164 */     else if (word1.equalsIgnoreCase("DELETE")) {
/* 165 */       request.setType(PersistRequestUpdateSql.SqlType.SQL_DELETE, word3, "DeleteSql");
/*     */     }
/* 167 */     else if (word1.equalsIgnoreCase("INSERT")) {
/* 168 */       request.setType(PersistRequestUpdateSql.SqlType.SQL_INSERT, word3, "InsertSql");
/*     */     } else {
/*     */       
/* 171 */       request.setType(PersistRequestUpdateSql.SqlType.SQL_UNKNOWN, null, "UnknownSql");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseUpdate(String sql, PersistRequestUpdateSql request) {
/*     */     String thirdWord;
/* 178 */     int start = ltrim(sql);
/*     */     
/* 180 */     int[] pos = new int[3];
/* 181 */     int spaceCount = 0;
/*     */     
/* 183 */     int len = sql.length();
/* 184 */     for (int i = start; i < len; i++) {
/* 185 */       char c = sql.charAt(i);
/* 186 */       if (Character.isWhitespace(c)) {
/* 187 */         pos[spaceCount] = i;
/* 188 */         spaceCount++;
/* 189 */         if (spaceCount > 2) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     String firstWord = sql.substring(0, pos[0]);
/* 196 */     String secWord = sql.substring(pos[0] + 1, pos[1]);
/*     */     
/* 198 */     if (pos[2] == 0) {
/*     */       
/* 200 */       thirdWord = sql.substring(pos[1] + 1);
/*     */     } else {
/* 202 */       thirdWord = sql.substring(pos[1] + 1, pos[2]);
/*     */     } 
/*     */     
/* 205 */     determineType(firstWord, secWord, thirdWord, request);
/*     */   }
/*     */   
/*     */   private int ltrim(String s) {
/* 209 */     int len = s.length();
/* 210 */     int i = 0;
/* 211 */     for (i = 0; i < len; i++) {
/* 212 */       if (!Character.isWhitespace(s.charAt(i))) {
/* 213 */         return i;
/*     */       }
/*     */     } 
/* 216 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\ExeUpdateSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */