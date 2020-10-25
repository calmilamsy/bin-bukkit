/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.event.BeanPersistController;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;
/*     */ import com.avaje.ebeaninternal.server.core.PstmtBatch;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultPersistExecute
/*     */   implements PersistExecute
/*     */ {
/*     */   private final ExeCallableSql exeCallableSql;
/*     */   private final ExeUpdateSql exeUpdateSql;
/*     */   private final ExeOrmUpdate exeOrmUpdate;
/*     */   private final int defaultBatchSize;
/*     */   private final boolean defaultBatchGenKeys;
/*     */   private final boolean validate;
/*     */   
/*     */   public DefaultPersistExecute(boolean validate, Binder binder, PstmtBatch pstmtBatch) {
/*  63 */     this.validate = validate;
/*  64 */     this.exeOrmUpdate = new ExeOrmUpdate(binder, pstmtBatch);
/*  65 */     this.exeUpdateSql = new ExeUpdateSql(binder, pstmtBatch);
/*  66 */     this.exeCallableSql = new ExeCallableSql(binder, pstmtBatch);
/*     */     
/*  68 */     this.defaultBatchGenKeys = GlobalProperties.getBoolean("batch.getgeneratedkeys", true);
/*  69 */     this.defaultBatchSize = GlobalProperties.getInt("batch.size", 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public BatchControl createBatchControl(SpiTransaction t) { return new BatchControl(t, this.defaultBatchSize, this.defaultBatchGenKeys); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void executeInsertBean(PersistRequestBean<T> request) {
/*  83 */     BeanManager<T> mgr = request.getBeanManager();
/*  84 */     BeanPersister persister = mgr.getBeanPersister();
/*     */     
/*  86 */     BeanPersistController controller = request.getBeanController();
/*  87 */     if (controller == null || controller.preInsert(request)) {
/*  88 */       if (this.validate) {
/*  89 */         request.validate();
/*     */       }
/*  91 */       persister.insert(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void executeUpdateBean(PersistRequestBean<T> request) {
/* 102 */     BeanManager<T> mgr = request.getBeanManager();
/* 103 */     BeanPersister persister = mgr.getBeanPersister();
/*     */     
/* 105 */     BeanPersistController controller = request.getBeanController();
/* 106 */     if (controller == null || controller.preUpdate(request)) {
/* 107 */       if (this.validate) {
/* 108 */         request.validate();
/*     */       }
/* 110 */       persister.update(request);
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
/*     */   public <T> void executeDeleteBean(PersistRequestBean<T> request) {
/* 122 */     BeanManager<T> mgr = request.getBeanManager();
/* 123 */     BeanPersister persister = mgr.getBeanPersister();
/*     */     
/* 125 */     BeanPersistController controller = request.getBeanController();
/* 126 */     if (controller == null || controller.preDelete(request))
/*     */     {
/* 128 */       persister.delete(request);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public int executeOrmUpdate(PersistRequestOrmUpdate request) { return this.exeOrmUpdate.execute(request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public int executeSqlUpdate(PersistRequestUpdateSql request) { return this.exeUpdateSql.execute(request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public int executeSqlCallable(PersistRequestCallableSql request) { return this.exeCallableSql.execute(request); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\DefaultPersistExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */