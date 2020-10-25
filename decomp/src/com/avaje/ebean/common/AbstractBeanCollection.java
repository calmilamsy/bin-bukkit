/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.BeanCollectionTouched;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public abstract class AbstractBeanCollection<E>
/*     */   extends Object
/*     */   implements BeanCollection<E>
/*     */ {
/*     */   private static final long serialVersionUID = 3365725236140187588L;
/*     */   protected int state;
/*     */   protected BeanCollectionLoader loader;
/*     */   protected ExpressionList<?> filterMany;
/*     */   protected int loaderIndex;
/*     */   protected String ebeanServerName;
/*     */   protected BeanCollectionTouched beanCollectionTouched;
/*     */   protected Future<Integer> fetchFuture;
/*     */   protected final Object ownerBean;
/*     */   protected final String propertyName;
/*     */   protected boolean finishedFetch = true;
/*     */   protected boolean hasMoreRows;
/*     */   protected ModifyHolder<E> modifyHolder;
/*     */   protected BeanCollection.ModifyListenMode modifyListenMode;
/*     */   protected boolean modifyAddListening;
/*     */   protected boolean modifyRemoveListening;
/*     */   protected boolean modifyListening;
/*     */   
/*     */   public AbstractBeanCollection() {
/* 100 */     this.ownerBean = null;
/* 101 */     this.propertyName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractBeanCollection(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
/* 108 */     this.loader = loader;
/* 109 */     this.ebeanServerName = loader.getName();
/* 110 */     this.ownerBean = ownerBean;
/* 111 */     this.propertyName = propertyName;
/*     */     
/* 113 */     if (ownerBean instanceof EntityBean) {
/* 114 */       EntityBeanIntercept ebi = ((EntityBean)ownerBean)._ebean_getIntercept();
/* 115 */       int parentState = ebi.getState();
/* 116 */       if (parentState != 0) {
/* 117 */         this.state = parentState;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 123 */   public Object getOwnerBean() { return this.ownerBean; }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public String getPropertyName() { return this.propertyName; }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public int getLoaderIndex() { return this.loaderIndex; }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public ExpressionList<?> getFilterMany() { return this.filterMany; }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public void setFilterMany(ExpressionList<?> filterMany) { this.filterMany = filterMany; }
/*     */ 
/*     */   
/*     */   protected void lazyLoadCollection(boolean onlyIds) {
/* 143 */     if (this.loader == null) {
/* 144 */       this.loader = (BeanCollectionLoader)Ebean.getServer(this.ebeanServerName);
/*     */     }
/* 146 */     if (this.loader == null) {
/* 147 */       String msg = "Lazy loading but LazyLoadEbeanServer is null? The LazyLoadEbeanServer needs to be set after deserialization to support lazy loading.";
/*     */ 
/*     */       
/* 150 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/* 153 */     this.loader.loadMany(this, onlyIds);
/* 154 */     checkEmptyLazyLoad();
/*     */   }
/*     */   
/*     */   protected void touched() {
/* 158 */     if (this.beanCollectionTouched != null) {
/*     */       
/* 160 */       this.beanCollectionTouched.notifyTouched(this);
/* 161 */       this.beanCollectionTouched = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 166 */   public void setBeanCollectionTouched(BeanCollectionTouched notify) { this.beanCollectionTouched = notify; }
/*     */ 
/*     */   
/*     */   public void setLoader(int beanLoaderIndex, BeanCollectionLoader loader) {
/* 170 */     this.loaderIndex = beanLoaderIndex;
/* 171 */     this.loader = loader;
/* 172 */     this.ebeanServerName = loader.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public boolean isSharedInstance() { return (this.state == 3); }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void setSharedInstance() { this.state = 3; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public boolean isReadOnly() { return (this.state >= 2); }
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 190 */     if (this.state == 3) {
/* 191 */       if (!readOnly) {
/* 192 */         String msg = "This collection is a sharedInstance and must always be read only";
/* 193 */         throw new IllegalStateException(msg);
/*     */       } 
/*     */     } else {
/* 196 */       this.state = readOnly ? 2 : 0;
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
/*     */ 
/*     */ 
/*     */   
/* 211 */   public boolean hasMoreRows() { return this.hasMoreRows; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public void setHasMoreRows(boolean hasMoreRows) { this.hasMoreRows = hasMoreRows; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public boolean isFinishedFetch() { return this.finishedFetch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public void setFinishedFetch(boolean finishedFetch) { this.finishedFetch = finishedFetch; }
/*     */ 
/*     */ 
/*     */   
/* 240 */   public void setBackgroundFetch(Future<Integer> fetchFuture) { this.fetchFuture = fetchFuture; }
/*     */ 
/*     */   
/*     */   public void backgroundFetchWait(long wait, TimeUnit timeUnit) {
/* 244 */     if (this.fetchFuture != null) {
/*     */       try {
/* 246 */         this.fetchFuture.get(wait, timeUnit);
/* 247 */       } catch (Exception e) {
/* 248 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void backgroundFetchWait() {
/* 254 */     if (this.fetchFuture != null) {
/*     */       try {
/* 256 */         this.fetchFuture.get();
/* 257 */       } catch (Exception e) {
/* 258 */         throw new PersistenceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkReadOnly() {
/* 265 */     if (this.state >= 2) {
/* 266 */       String msg = "This collection is in ReadOnly mode";
/* 267 */       throw new IllegalStateException(msg);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModifyListening(BeanCollection.ModifyListenMode mode) {
/* 283 */     this.modifyListenMode = mode;
/* 284 */     this.modifyAddListening = BeanCollection.ModifyListenMode.ALL.equals(mode);
/* 285 */     this.modifyRemoveListening = (this.modifyAddListening || BeanCollection.ModifyListenMode.REMOVALS.equals(mode));
/* 286 */     this.modifyListening = (this.modifyRemoveListening || this.modifyAddListening);
/* 287 */     if (this.modifyListening)
/*     */     {
/* 289 */       this.modifyHolder = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public BeanCollection.ModifyListenMode getModifyListenMode() { return this.modifyListenMode; }
/*     */ 
/*     */   
/*     */   protected ModifyHolder<E> getModifyHolder() {
/* 301 */     if (this.modifyHolder == null) {
/* 302 */       this.modifyHolder = new ModifyHolder();
/*     */     }
/* 304 */     return this.modifyHolder;
/*     */   }
/*     */   
/*     */   public void modifyAddition(E bean) {
/* 308 */     if (this.modifyAddListening) {
/* 309 */       getModifyHolder().modifyAddition(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public void modifyRemoval(Object bean) {
/* 314 */     if (this.modifyRemoveListening) {
/* 315 */       getModifyHolder().modifyRemoval(bean);
/*     */     }
/*     */   }
/*     */   
/*     */   public void modifyReset() {
/* 320 */     if (this.modifyHolder != null) {
/* 321 */       this.modifyHolder.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<E> getModifyAdditions() {
/* 326 */     if (this.modifyHolder == null) {
/* 327 */       return null;
/*     */     }
/* 329 */     return this.modifyHolder.getModifyAdditions();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<E> getModifyRemovals() {
/* 334 */     if (this.modifyHolder == null) {
/* 335 */       return null;
/*     */     }
/* 337 */     return this.modifyHolder.getModifyRemovals();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\AbstractBeanCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */