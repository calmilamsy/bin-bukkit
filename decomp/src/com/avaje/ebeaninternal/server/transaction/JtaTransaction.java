/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.LogLevel;
/*     */ import java.sql.SQLException;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.sql.DataSource;
/*     */ import javax.transaction.UserTransaction;
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
/*     */ public class JtaTransaction
/*     */   extends JdbcTransaction
/*     */ {
/*     */   private UserTransaction userTransaction;
/*     */   private DataSource dataSource;
/*     */   private boolean commmitted = false;
/*     */   private boolean newTransaction = false;
/*     */   
/*     */   public JtaTransaction(String id, boolean explicit, LogLevel logLevel, UserTransaction utx, DataSource ds, TransactionManager manager) {
/*  49 */     super(id, explicit, logLevel, null, manager);
/*  50 */     this.userTransaction = utx;
/*  51 */     this.dataSource = ds;
/*     */     
/*     */     try {
/*  54 */       this.newTransaction = (this.userTransaction.getStatus() == 6);
/*  55 */       if (this.newTransaction) {
/*  56 */         this.userTransaction.begin();
/*     */       }
/*  58 */     } catch (Exception e) {
/*  59 */       throw new PersistenceException(e);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  64 */       this.connection = this.dataSource.getConnection();
/*  65 */       if (this.connection == null) {
/*  66 */         throw new PersistenceException("The DataSource returned a null connection.");
/*     */       }
/*  68 */       if (this.connection.getAutoCommit()) {
/*  69 */         this.connection.setAutoCommit(false);
/*     */       }
/*     */     }
/*  72 */     catch (SQLException e) {
/*  73 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() {
/*  81 */     if (this.commmitted) {
/*  82 */       throw new PersistenceException("This transaction has already been committed.");
/*     */     }
/*     */     try {
/*     */       try {
/*  86 */         if (this.newTransaction) {
/*  87 */           this.userTransaction.commit();
/*     */         }
/*  89 */         notifyCommit();
/*     */       } finally {
/*  91 */         close();
/*     */       } 
/*  93 */     } catch (Exception e) {
/*  94 */       throw new PersistenceException(e);
/*     */     } 
/*  96 */     this.commmitted = true;
/*     */   }
/*     */ 
/*     */   
/* 100 */   public void rollback() { rollback(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Throwable e) {
/* 107 */     if (!this.commmitted) {
/*     */       try {
/*     */         try {
/* 110 */           if (this.userTransaction != null) {
/* 111 */             if (this.newTransaction) {
/* 112 */               this.userTransaction.rollback();
/*     */             } else {
/* 114 */               this.userTransaction.setRollbackOnly();
/*     */             } 
/*     */           }
/* 117 */           notifyRollback(e);
/*     */         } finally {
/* 119 */           close();
/*     */         } 
/* 121 */       } catch (Exception ex) {
/* 122 */         throw new PersistenceException(ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close() {
/* 132 */     if (this.connection != null) {
/* 133 */       this.connection.close();
/* 134 */       this.connection = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\JtaTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */