/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.ValidationException;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.event.BeanPersistController;
/*     */ import com.avaje.ebean.event.BeanPersistListener;
/*     */ import com.avaje.ebean.event.BeanPersistRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.api.TransactionEvent;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.BatchControl;
/*     */ import com.avaje.ebeaninternal.server.persist.PersistExecute;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIdMap;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ import javax.persistence.OptimisticLockException;
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
/*     */ public class PersistRequestBean<T>
/*     */   extends PersistRequest
/*     */   implements BeanPersistRequest<T>
/*     */ {
/*     */   protected final BeanManager<T> beanManager;
/*     */   protected final BeanDescriptor<T> beanDescriptor;
/*     */   protected final BeanPersistListener<T> beanPersistListener;
/*     */   protected final BeanPersistController controller;
/*     */   protected final EntityBeanIntercept intercept;
/*     */   protected final Object parentBean;
/*     */   protected final boolean isDirty;
/*     */   protected final boolean vanilla;
/*     */   protected final T bean;
/*     */   protected T oldValues;
/*     */   protected ConcurrencyMode concurrencyMode;
/*     */   protected final Set<String> loadedProps;
/*     */   protected Object idValue;
/*     */   protected Integer beanHash;
/*     */   protected Integer beanIdentityHash;
/*     */   protected final Set<String> changedProps;
/*     */   protected boolean notifyCache;
/*     */   private boolean statelessUpdate;
/*     */   private boolean deleteMissingChildren;
/*     */   private boolean updateNullProperties;
/*     */   
/*     */   public PersistRequestBean(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, SpiTransaction t, PersistExecute persistExecute, Set<String> updateProps, ConcurrencyMode concurrencyMode) {
/* 121 */     super(server, t, persistExecute);
/* 122 */     this.beanManager = mgr;
/* 123 */     this.beanDescriptor = mgr.getBeanDescriptor();
/* 124 */     this.beanPersistListener = this.beanDescriptor.getPersistListener();
/* 125 */     this.bean = bean;
/* 126 */     this.parentBean = parentBean;
/*     */     
/* 128 */     this.controller = this.beanDescriptor.getPersistController();
/* 129 */     this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
/*     */     
/* 131 */     this.concurrencyMode = concurrencyMode;
/* 132 */     this.loadedProps = updateProps;
/* 133 */     this.changedProps = updateProps;
/*     */     
/* 135 */     this.vanilla = true;
/* 136 */     this.isDirty = true;
/* 137 */     this.oldValues = bean;
/* 138 */     if (bean instanceof EntityBean) {
/* 139 */       this.intercept = ((EntityBean)bean)._ebean_getIntercept();
/*     */     } else {
/* 141 */       this.intercept = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistRequestBean(SpiEbeanServer server, T bean, Object parentBean, BeanManager<T> mgr, SpiTransaction t, PersistExecute persistExecute) {
/* 149 */     super(server, t, persistExecute);
/* 150 */     this.beanManager = mgr;
/* 151 */     this.beanDescriptor = mgr.getBeanDescriptor();
/* 152 */     this.beanPersistListener = this.beanDescriptor.getPersistListener();
/* 153 */     this.bean = bean;
/* 154 */     this.parentBean = parentBean;
/*     */     
/* 156 */     this.controller = this.beanDescriptor.getPersistController();
/* 157 */     this.concurrencyMode = this.beanDescriptor.getConcurrencyMode();
/*     */     
/* 159 */     if (bean instanceof EntityBean) {
/* 160 */       this.intercept = ((EntityBean)bean)._ebean_getIntercept();
/* 161 */       if (this.intercept.isReference())
/*     */       {
/*     */         
/* 164 */         this.concurrencyMode = ConcurrencyMode.NONE;
/*     */       }
/*     */       
/* 167 */       this.isDirty = this.intercept.isDirty();
/* 168 */       if (!this.isDirty) {
/* 169 */         this.changedProps = this.intercept.getChangedProps();
/*     */       } else {
/*     */         
/* 172 */         Set<String> beanChangedProps = this.intercept.getChangedProps();
/* 173 */         Set<String> dirtyEmbedded = this.beanDescriptor.getDirtyEmbeddedProperties(bean);
/* 174 */         this.changedProps = mergeChangedProperties(beanChangedProps, dirtyEmbedded);
/*     */       } 
/* 176 */       this.loadedProps = this.intercept.getLoadedProps();
/* 177 */       this.oldValues = this.intercept.getOldValues();
/* 178 */       this.vanilla = false;
/*     */     }
/*     */     else {
/*     */       
/* 182 */       this.vanilla = true;
/* 183 */       this.isDirty = true;
/* 184 */       this.loadedProps = null;
/* 185 */       this.changedProps = null;
/* 186 */       this.intercept = null;
/*     */ 
/*     */       
/* 189 */       if (this.concurrencyMode.equals(ConcurrencyMode.ALL)) {
/* 190 */         this.concurrencyMode = ConcurrencyMode.NONE;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> mergeChangedProperties(Set<String> beanChangedProps, Set<String> embChanged) {
/* 199 */     if (embChanged == null)
/* 200 */       return beanChangedProps; 
/* 201 */     if (beanChangedProps == null) {
/* 202 */       return embChanged;
/*     */     }
/* 204 */     beanChangedProps.addAll(embChanged);
/* 205 */     return beanChangedProps;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public boolean isNotify(TransactionEvent txnEvent) { return (this.notifyCache || isNotifyPersistListener() || this.beanDescriptor.isNotifyLucene(txnEvent)); }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public boolean isNotifyCache() { return this.notifyCache; }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public boolean isNotifyPersistListener() { return (this.beanPersistListener != null); }
/*     */ 
/*     */   
/*     */   public void notifyCache() {
/* 222 */     if (this.notifyCache) {
/* 223 */       if (this.type == PersistRequest.Type.INSERT) {
/* 224 */         this.beanDescriptor.queryCacheClear();
/*     */       } else {
/*     */         
/* 227 */         this.beanDescriptor.cacheRemove(this.idValue);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 233 */   public void pauseIndexInvalidate() { this.transaction.getEvent().pauseIndexInvalidate(this.beanDescriptor.getBeanType()); }
/*     */ 
/*     */ 
/*     */   
/* 237 */   public void resumeIndexInvalidate() { this.transaction.getEvent().resumeIndexInvalidate(this.beanDescriptor.getBeanType()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public void addToPersistMap(BeanPersistIdMap beanPersistMap) { beanPersistMap.add(this.beanDescriptor, this.type, this.idValue); }
/*     */ 
/*     */   
/*     */   public boolean notifyLocalPersistListener() {
/* 246 */     if (this.beanPersistListener == null) {
/* 247 */       return false;
/*     */     }
/*     */     
/* 250 */     switch (this.type) {
/*     */       case INSERT:
/* 252 */         return this.beanPersistListener.inserted(this.bean);
/*     */       
/*     */       case UPDATE:
/* 255 */         return this.beanPersistListener.updated(this.bean, getUpdatedProperties());
/*     */       
/*     */       case DELETE:
/* 258 */         return this.beanPersistListener.deleted(this.bean);
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   public boolean isParent(Object o) { return (o == this.parentBean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getBeanHash() {
/* 277 */     if (this.beanHash == null) {
/* 278 */       Object id = this.beanDescriptor.getId(this.bean);
/* 279 */       int hc = 31 * this.bean.getClass().getName().hashCode();
/* 280 */       if (id != null) {
/* 281 */         hc += id.hashCode();
/*     */       }
/* 283 */       this.beanHash = Integer.valueOf(hc);
/*     */     } 
/* 285 */     return this.beanHash;
/*     */   }
/*     */   
/*     */   private Integer getBeanIdentityHash() {
/* 289 */     if (this.beanIdentityHash == null) {
/* 290 */       this.beanIdentityHash = Integer.valueOf(System.identityHashCode(this.bean));
/*     */     }
/* 292 */     return this.beanIdentityHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVanillaBean() {
/* 303 */     if (this.intercept == null) {
/* 304 */       this.transaction.registerBean(getBeanIdentityHash());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegisteredVanillaBean() {
/* 313 */     if (this.intercept == null && this.transaction != null) {
/* 314 */       return this.transaction.isRegisteredBean(getBeanIdentityHash());
/*     */     }
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public void registerBean() { this.transaction.registerBean(getBeanHash()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 332 */   public void unregisterBean() { this.transaction.unregisterBean(getBeanHash()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegistered() {
/* 339 */     if (this.transaction == null) {
/* 340 */       return false;
/*     */     }
/* 342 */     return this.transaction.isRegisteredBean(getBeanHash());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(PersistRequest.Type type) {
/* 351 */     this.type = type;
/* 352 */     if (type == PersistRequest.Type.DELETE || type == PersistRequest.Type.UPDATE) {
/* 353 */       if (this.oldValues == null) {
/* 354 */         this.oldValues = this.bean;
/*     */       }
/* 356 */       this.notifyCache = this.beanDescriptor.isCacheNotify(false);
/*     */     } else {
/* 358 */       this.notifyCache = this.beanDescriptor.isCacheNotify(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 363 */   public BeanManager<T> getBeanManager() { return this.beanManager; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   public BeanDescriptor<T> getBeanDescriptor() { return this.beanDescriptor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 377 */   public boolean isStatelessUpdate() { return this.statelessUpdate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 385 */   public boolean isDeleteMissingChildren() { return this.deleteMissingChildren; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 393 */   public boolean isUpdateNullProperties() { return this.updateNullProperties; }
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
/*     */   public void setStatelessUpdate(boolean statelessUpdate, boolean deleteMissingChildren, boolean updateNullProperties) {
/* 405 */     this.statelessUpdate = statelessUpdate;
/* 406 */     this.deleteMissingChildren = deleteMissingChildren;
/* 407 */     this.updateNullProperties = updateNullProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 415 */   public boolean isDirty() { return this.isDirty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 422 */   public ConcurrencyMode getConcurrencyMode() { return this.concurrencyMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadedProps(Set<String> additionalProps) {
/* 430 */     if (this.intercept != null) {
/* 431 */       this.intercept.setLoadedProps(additionalProps);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 436 */   public Set<String> getLoadedProperties() { return this.loadedProps; }
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
/* 448 */   public String getFullName() { return this.beanDescriptor.getFullName(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 455 */   public T getBean() { return (T)this.bean; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 462 */   public Object getBeanId() { return this.beanDescriptor.getId(this.bean); }
/*     */ 
/*     */ 
/*     */   
/* 466 */   public BeanDelta createDeltaBean() { return new BeanDelta(this.beanDescriptor, getBeanId()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 474 */   public T getOldValues() { return (T)this.oldValues; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 482 */   public Object getParentBean() { return this.parentBean; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 490 */   public BeanPersistController getBeanController() { return this.controller; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 497 */   public EntityBeanIntercept getEntityBeanIntercept() { return this.intercept; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 505 */     InvalidValue errs = this.beanDescriptor.validate(false, this.bean);
/* 506 */     if (errs != null) {
/* 507 */       throw new ValidationException(errs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoadedProperty(BeanProperty prop) {
/* 516 */     if (this.loadedProps == null) {
/* 517 */       return true;
/*     */     }
/* 519 */     return this.loadedProps.contains(prop.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeNow() {
/* 525 */     switch (this.type) {
/*     */       case INSERT:
/* 527 */         this.persistExecute.executeInsertBean(this);
/* 528 */         return -1;
/*     */       
/*     */       case UPDATE:
/* 531 */         this.persistExecute.executeUpdateBean(this);
/* 532 */         return -1;
/*     */       
/*     */       case DELETE:
/* 535 */         this.persistExecute.executeDeleteBean(this);
/* 536 */         return -1;
/*     */     } 
/*     */     
/* 539 */     throw new RuntimeException("Invalid type " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeOrQueue() {
/* 546 */     boolean batch = this.transaction.isBatchThisRequest();
/*     */     
/* 548 */     BatchControl control = this.transaction.getBatchControl();
/* 549 */     if (control != null) {
/* 550 */       return control.executeOrQueue(this, batch);
/*     */     }
/* 552 */     if (batch) {
/* 553 */       control = this.persistExecute.createBatchControl(this.transaction);
/* 554 */       return control.executeOrQueue(this, batch);
/*     */     } 
/*     */     
/* 557 */     return executeNow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeneratedKey(Object idValue) {
/* 566 */     if (idValue != null) {
/*     */ 
/*     */ 
/*     */       
/* 570 */       idValue = this.beanDescriptor.convertSetId(idValue, this.bean);
/*     */ 
/*     */       
/* 573 */       this.idValue = idValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 582 */   public void setBoundId(Object idValue) { this.idValue = idValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void checkRowCount(int rowCount) throws SQLException {
/* 589 */     if (rowCount != 1) {
/* 590 */       String m = Message.msg("persist.conc2", "" + rowCount);
/* 591 */       throw new OptimisticLockException(m, null, this.bean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postExecute() {
/* 600 */     if (this.controller != null) {
/* 601 */       controllerPost();
/*     */     }
/*     */     
/* 604 */     if (this.intercept != null)
/*     */     {
/* 606 */       this.intercept.setLoaded();
/*     */     }
/*     */     
/* 609 */     addEvent();
/*     */     
/* 611 */     if (isLogSummary()) {
/* 612 */       logSummary();
/*     */     }
/*     */   }
/*     */   
/*     */   private void controllerPost() {
/* 617 */     switch (this.type) {
/*     */       case INSERT:
/* 619 */         this.controller.postInsert(this);
/*     */         break;
/*     */       case UPDATE:
/* 622 */         this.controller.postUpdate(this);
/*     */         break;
/*     */       case DELETE:
/* 625 */         this.controller.postDelete(this);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logSummary() {
/* 634 */     String name = this.beanDescriptor.getName();
/* 635 */     switch (this.type) {
/*     */       case INSERT:
/* 637 */         this.transaction.logInternal("Inserted [" + name + "] [" + this.idValue + "]");
/*     */         break;
/*     */       case UPDATE:
/* 640 */         this.transaction.logInternal("Updated [" + name + "] [" + this.idValue + "]");
/*     */         break;
/*     */       case DELETE:
/* 643 */         this.transaction.logInternal("Deleted [" + name + "] [" + this.idValue + "]");
/*     */         break;
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
/*     */   private void addEvent() {
/* 656 */     TransactionEvent event = this.transaction.getEvent();
/* 657 */     if (event != null) {
/* 658 */       event.add(this);
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
/*     */   
/*     */   public ConcurrencyMode determineConcurrencyMode() {
/* 671 */     if (this.loadedProps != null)
/*     */     {
/* 673 */       if (this.concurrencyMode.equals(ConcurrencyMode.VERSION)) {
/*     */         
/* 675 */         BeanProperty prop = this.beanDescriptor.firstVersionProperty();
/* 676 */         if (prop == null || !this.loadedProps.contains(prop.getName()))
/*     */         {
/*     */           
/* 679 */           this.concurrencyMode = ConcurrencyMode.ALL;
/*     */         }
/*     */       } 
/*     */     }
/* 683 */     return this.concurrencyMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 693 */   public boolean isDynamicUpdateSql() { return ((!this.vanilla && this.beanDescriptor.isUpdateChangesOnly()) || this.loadedProps != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenerateDmlRequest createGenerateDmlRequest(boolean emptyStringAsNull) {
/* 704 */     if (this.beanDescriptor.isUpdateChangesOnly()) {
/* 705 */       return new GenerateDmlRequest(emptyStringAsNull, this.changedProps, this.loadedProps, this.oldValues);
/*     */     }
/* 707 */     return new GenerateDmlRequest(emptyStringAsNull, this.loadedProps, this.loadedProps, this.oldValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getUpdatedProperties() {
/* 716 */     if (this.changedProps != null) {
/* 717 */       return this.changedProps;
/*     */     }
/* 719 */     return this.loadedProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 728 */   public boolean hasChanged(BeanProperty prop) { return this.changedProps.contains(prop.getName()); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\PersistRequestBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */