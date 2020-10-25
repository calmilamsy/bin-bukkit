/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbReadContext;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class BackgroundIdFetch
/*     */   extends Object
/*     */   implements Callable<Integer>
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(BackgroundIdFetch.class.getName());
/*     */ 
/*     */   
/*     */   private final ResultSet rset;
/*     */ 
/*     */   
/*     */   private final PreparedStatement pstmt;
/*     */ 
/*     */   
/*     */   private final SpiTransaction transaction;
/*     */ 
/*     */   
/*     */   private final DbReadContext ctx;
/*     */ 
/*     */   
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   
/*     */   private final BeanIdList idList;
/*     */ 
/*     */   
/*     */   public BackgroundIdFetch(SpiTransaction transaction, ResultSet rset, PreparedStatement pstmt, DbReadContext ctx, BeanDescriptor<?> beanDescriptor, BeanIdList idList) {
/*  61 */     this.ctx = ctx;
/*  62 */     this.transaction = transaction;
/*  63 */     this.rset = rset;
/*  64 */     this.pstmt = pstmt;
/*  65 */     this.beanDescriptor = beanDescriptor;
/*  66 */     this.idList = idList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer call() {
/*     */     try {
/*  74 */       int startSize = this.idList.getIdList().size();
/*  75 */       int rowsRead = 0;
/*  76 */       while (this.rset.next()) {
/*  77 */         Object idValue = this.beanDescriptor.getIdBinder().read(this.ctx);
/*  78 */         this.idList.add(idValue);
/*  79 */         this.ctx.getDataReader().resetColumnPosition();
/*  80 */         rowsRead++;
/*     */       } 
/*     */       
/*  83 */       if (logger.isLoggable(Level.INFO)) {
/*  84 */         logger.info("BG FetchIds read:" + rowsRead + " total:" + (startSize + rowsRead));
/*     */       }
/*     */       
/*  87 */       return Integer.valueOf(rowsRead);
/*     */     }
/*  89 */     catch (Exception e) {
/*  90 */       logger.log(Level.SEVERE, null, e);
/*  91 */       return Integer.valueOf(0);
/*     */     } finally {
/*     */       
/*     */       try {
/*  95 */         close();
/*  96 */       } catch (Exception e) {
/*  97 */         logger.log(Level.SEVERE, null, e);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 103 */         this.transaction.rollback();
/* 104 */       } catch (Exception e) {
/* 105 */         logger.log(Level.SEVERE, null, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void close() {
/*     */     try {
/* 113 */       if (this.rset != null) {
/* 114 */         this.rset.close();
/*     */       }
/* 116 */     } catch (SQLException e) {
/* 117 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */     try {
/* 120 */       if (this.pstmt != null) {
/* 121 */         this.pstmt.close();
/*     */       }
/* 123 */     } catch (SQLException e) {
/* 124 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\BackgroundIdFetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */