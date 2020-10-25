/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
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
/*     */ 
/*     */ 
/*     */ public final class PersistRequestOrmUpdate
/*     */   extends PersistRequest
/*     */ {
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   private SpiUpdate<?> ormUpdate;
/*     */   private int rowCount;
/*     */   private String bindLog;
/*     */   
/*     */   public PersistRequestOrmUpdate(SpiEbeanServer server, BeanManager<?> mgr, SpiUpdate<?> ormUpdate, SpiTransaction t, PersistExecute persistExecute) {
/*  51 */     super(server, t, persistExecute);
/*  52 */     this.beanDescriptor = mgr.getBeanDescriptor();
/*  53 */     this.ormUpdate = ormUpdate;
/*     */   }
/*     */ 
/*     */   
/*  57 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int executeNow() { return this.persistExecute.executeOrmUpdate(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public int executeOrQueue() { return executeStatement(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public SpiUpdate<?> getOrmUpdate() { return this.ormUpdate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void checkRowCount(int count) throws SQLException { this.rowCount = count; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean useGeneratedKeys() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedKey(Object idValue) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void setBindLog(String bindLog) { this.bindLog = bindLog; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() throws SQLException {
/* 110 */     SpiUpdate.OrmUpdateType ormUpdateType = this.ormUpdate.getOrmUpdateType();
/* 111 */     String tableName = this.ormUpdate.getBaseTable();
/*     */     
/* 113 */     if (this.transaction.isLogSummary()) {
/* 114 */       String m = ormUpdateType + " table[" + tableName + "] rows[" + this.rowCount + "] bind[" + this.bindLog + "]";
/* 115 */       this.transaction.logInternal(m);
/*     */     } 
/*     */     
/* 118 */     if (this.ormUpdate.isNotifyCache())
/*     */     {
/*     */ 
/*     */       
/* 122 */       switch (ormUpdateType) {
/*     */         case INSERT:
/* 124 */           this.transaction.getEvent().add(tableName, true, false, false);
/*     */           break;
/*     */         case UPDATE:
/* 127 */           this.transaction.getEvent().add(tableName, false, true, false);
/*     */           break;
/*     */         case DELETE:
/* 130 */           this.transaction.getEvent().add(tableName, false, false, true);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\PersistRequestOrmUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */