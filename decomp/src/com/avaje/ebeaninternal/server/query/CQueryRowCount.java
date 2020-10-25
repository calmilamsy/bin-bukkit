/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.type.DataBind;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
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
/*     */ public class CQueryRowCount
/*     */ {
/*  43 */   private static final Logger logger = Logger.getLogger(CQueryRowCount.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> desc;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */ 
/*     */ 
/*     */   
/*     */   private final CQueryPredicates predicates;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private ResultSet rset;
/*     */ 
/*     */   
/*     */   private PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private String bindLog;
/*     */ 
/*     */   
/*     */   private long startNano;
/*     */ 
/*     */   
/*     */   private int executionTimeMicros;
/*     */ 
/*     */   
/*     */   private int rowCount;
/*     */ 
/*     */ 
/*     */   
/*     */   public CQueryRowCount(OrmQueryRequest<?> request, CQueryPredicates predicates, String sql) {
/*  86 */     this.request = request;
/*  87 */     this.query = request.getQuery();
/*  88 */     this.sql = sql;
/*     */     
/*  90 */     this.query.setGeneratedSql(sql);
/*     */     
/*  92 */     this.desc = request.getBeanDescriptor();
/*  93 */     this.predicates = predicates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSummary() {
/* 101 */     StringBuilder sb = new StringBuilder();
/* 102 */     sb.append("FindRowCount exeMicros[").append(this.executionTimeMicros).append("] rows[").append(this.rowCount).append("] type[").append(this.desc.getFullName()).append("] predicates[").append(this.predicates.getLogWhereSql()).append("] bind[").append(this.bindLog).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public String getBindLog() { return this.bindLog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public String getGeneratedSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public SpiOrmQueryRequest<?> getQueryRequest() { return this.request; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findRowCount() throws SQLException {
/* 134 */     this.startNano = System.nanoTime();
/*     */     
/*     */     try {
/* 137 */       SpiTransaction t = this.request.getTransaction();
/* 138 */       Connection conn = t.getInternalConnection();
/* 139 */       this.pstmt = conn.prepareStatement(this.sql);
/*     */       
/* 141 */       if (this.query.getTimeout() > 0) {
/* 142 */         this.pstmt.setQueryTimeout(this.query.getTimeout());
/*     */       }
/*     */       
/* 145 */       this.bindLog = this.predicates.bind(new DataBind(this.pstmt));
/*     */       
/* 147 */       this.rset = this.pstmt.executeQuery();
/*     */       
/* 149 */       if (!this.rset.next()) {
/* 150 */         throw new PersistenceException("Expecting 1 row but got none?");
/*     */       }
/*     */       
/* 153 */       this.rowCount = this.rset.getInt(1);
/*     */       
/* 155 */       long exeNano = System.nanoTime() - this.startNano;
/* 156 */       this.executionTimeMicros = (int)exeNano / 1000;
/*     */       
/* 158 */       return this.rowCount;
/*     */     } finally {
/*     */       
/* 161 */       close();
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
/*     */   private void close() {
/*     */     try {
/* 174 */       if (this.rset != null) {
/* 175 */         this.rset.close();
/* 176 */         this.rset = null;
/*     */       } 
/* 178 */     } catch (SQLException e) {
/* 179 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */     try {
/* 182 */       if (this.pstmt != null) {
/* 183 */         this.pstmt.close();
/* 184 */         this.pstmt = null;
/*     */       } 
/* 186 */     } catch (SQLException e) {
/* 187 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryRowCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */