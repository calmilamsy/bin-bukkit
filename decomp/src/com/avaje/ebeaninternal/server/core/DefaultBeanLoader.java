/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanContext;
/*     */ import com.avaje.ebeaninternal.api.LoadBeanRequest;
/*     */ import com.avaje.ebeaninternal.api.LoadManyContext;
/*     */ import com.avaje.ebeaninternal.api.LoadManyRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class DefaultBeanLoader
/*     */ {
/*  53 */   private static final Logger logger = Logger.getLogger(DefaultBeanLoader.class.getName());
/*     */   
/*     */   private final DebugLazyLoad debugLazyLoad;
/*     */   
/*     */   private final DefaultServer server;
/*     */   
/*     */   protected DefaultBeanLoader(DefaultServer server, DebugLazyLoad debugLazyLoad) {
/*  60 */     this.server = server;
/*  61 */     this.debugLazyLoad = debugLazyLoad;
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
/*     */   private int getBatchSize(int batchListSize, int requestedBatchSize) {
/*  75 */     if (batchListSize == requestedBatchSize) {
/*  76 */       return batchListSize;
/*     */     }
/*  78 */     if (batchListSize == 1)
/*     */     {
/*  80 */       return 1;
/*     */     }
/*  82 */     if (requestedBatchSize <= 5)
/*     */     {
/*  84 */       return 5;
/*     */     }
/*  86 */     if (batchListSize <= 10 || requestedBatchSize <= 10)
/*     */     {
/*     */       
/*  89 */       return 10;
/*     */     }
/*  91 */     if (batchListSize <= 20 || requestedBatchSize <= 20)
/*     */     {
/*     */       
/*  94 */       return 20;
/*     */     }
/*  96 */     if (batchListSize <= 50) {
/*  97 */       return 50;
/*     */     }
/*  99 */     return requestedBatchSize;
/*     */   }
/*     */ 
/*     */   
/* 103 */   public void refreshMany(Object parentBean, String propertyName) { refreshMany(parentBean, propertyName, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadMany(LoadManyRequest loadRequest) {
/* 109 */     List<BeanCollection<?>> batch = loadRequest.getBatch();
/*     */     
/* 111 */     int batchSize = getBatchSize(batch.size(), loadRequest.getBatchSize());
/*     */     
/* 113 */     LoadManyContext ctx = loadRequest.getLoadContext();
/* 114 */     BeanPropertyAssocMany<?> many = ctx.getBeanProperty();
/*     */     
/* 116 */     PersistenceContext pc = ctx.getPersistenceContext();
/*     */     
/* 118 */     ArrayList<Object> idList = new ArrayList<Object>(batchSize);
/*     */     
/* 120 */     for (i = 0; i < batch.size(); i++) {
/* 121 */       BeanCollection<?> bc = (BeanCollection)batch.get(i);
/* 122 */       Object ownerBean = bc.getOwnerBean();
/* 123 */       Object id = many.getParentId(ownerBean);
/* 124 */       idList.add(id);
/*     */     } 
/* 126 */     int extraIds = batchSize - batch.size();
/* 127 */     if (extraIds > 0) {
/* 128 */       Object firstId = idList.get(0);
/* 129 */       for (int i = 0; i < extraIds; i++) {
/* 130 */         idList.add(firstId);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     BeanDescriptor<?> desc = ctx.getBeanDescriptor();
/*     */     
/* 136 */     String idProperty = desc.getIdBinder().getIdProperty();
/*     */     
/* 138 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(desc.getBeanType());
/* 139 */     query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
/* 140 */     query.setLazyLoadManyPath(many.getName());
/* 141 */     query.setPersistenceContext(pc);
/* 142 */     query.select(idProperty);
/* 143 */     query.fetch(many.getName());
/*     */     
/* 145 */     if (idList.size() == 1) {
/* 146 */       query.where().idEq(idList.get(0));
/*     */     } else {
/* 148 */       query.where().idIn(idList);
/*     */     } 
/*     */     
/* 151 */     String mode = loadRequest.isLazy() ? "+lazy" : "+query";
/* 152 */     query.setLoadDescription(mode, loadRequest.getDescription());
/*     */ 
/*     */     
/* 155 */     ctx.configureQuery(query);
/*     */     
/* 157 */     if (loadRequest.isOnlyIds())
/*     */     {
/* 159 */       query.fetch(many.getName(), many.getTargetIdProperty());
/*     */     }
/*     */     
/* 162 */     this.server.findList(query, loadRequest.getTransaction());
/*     */ 
/*     */ 
/*     */     
/* 166 */     for (int i = 0; i < batch.size(); i++) {
/* 167 */       if (((BeanCollection)batch.get(i)).checkEmptyLazyLoad() && 
/* 168 */         logger.isLoggable(Level.FINE)) {
/* 169 */         logger.fine("BeanCollection after load was empty. Owner:" + ((BeanCollection)batch.get(i)).getOwnerBean());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadMany(BeanCollection<?> bc, LoadManyContext ctx, boolean onlyIds) {
/* 177 */     Object parentBean = bc.getOwnerBean();
/* 178 */     String propertyName = bc.getPropertyName();
/*     */     
/* 180 */     ObjectGraphNode node = (ctx == null) ? null : ctx.getObjectGraphNode();
/*     */     
/* 182 */     loadManyInternal(parentBean, propertyName, null, false, node, onlyIds);
/*     */     
/* 184 */     if (this.server.getAdminLogging().isDebugLazyLoad()) {
/*     */       
/* 186 */       Class<?> cls = parentBean.getClass();
/* 187 */       BeanDescriptor<?> desc = this.server.getBeanDescriptor(cls);
/* 188 */       BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany)desc.getBeanProperty(propertyName);
/*     */       
/* 190 */       StackTraceElement cause = this.debugLazyLoad.getStackTraceElement(cls);
/*     */       
/* 192 */       String msg = "debug.lazyLoad " + many.getManyType() + " [" + desc + "][" + propertyName + "]";
/* 193 */       if (cause != null) {
/* 194 */         msg = msg + " at: " + cause;
/*     */       }
/* 196 */       System.err.println(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 201 */   public void refreshMany(Object parentBean, String propertyName, Transaction t) { loadManyInternal(parentBean, propertyName, t, true, null, false); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadManyInternal(Object parentBean, String propertyName, Transaction t, boolean refresh, ObjectGraphNode node, boolean onlyIds) {
/* 206 */     boolean vanilla = !(parentBean instanceof EntityBean);
/*     */     
/* 208 */     EntityBeanIntercept ebi = null;
/* 209 */     DefaultPersistenceContext defaultPersistenceContext = null;
/* 210 */     BeanCollection<?> beanCollection = null;
/* 211 */     ExpressionList<?> filterMany = null;
/*     */     
/* 213 */     if (!vanilla) {
/* 214 */       ebi = ((EntityBean)parentBean)._ebean_getIntercept();
/* 215 */       defaultPersistenceContext = ebi.getPersistenceContext();
/*     */     } 
/*     */     
/* 218 */     BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 219 */     BeanPropertyAssocMany<?> many = (BeanPropertyAssocMany)parentDesc.getBeanProperty(propertyName);
/*     */     
/* 221 */     Object currentValue = many.getValueUnderlying(parentBean);
/* 222 */     if (currentValue instanceof BeanCollection) {
/* 223 */       beanCollection = (BeanCollection)currentValue;
/* 224 */       filterMany = beanCollection.getFilterMany();
/*     */     } 
/*     */     
/* 227 */     Object parentId = parentDesc.getId(parentBean);
/*     */     
/* 229 */     if (defaultPersistenceContext == null) {
/* 230 */       defaultPersistenceContext = new DefaultPersistenceContext();
/* 231 */       defaultPersistenceContext.put(parentId, parentBean);
/*     */     } 
/*     */     
/* 234 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(parentDesc.getBeanType());
/*     */     
/* 236 */     if (refresh) {
/*     */       
/* 238 */       Object emptyCollection = many.createEmpty(vanilla);
/* 239 */       many.setValue(parentBean, emptyCollection);
/* 240 */       if (!vanilla && ebi != null && ebi.isSharedInstance()) {
/* 241 */         ((BeanCollection)emptyCollection).setSharedInstance();
/*     */       }
/* 243 */       query.setLoadDescription("+refresh", null);
/*     */     } else {
/* 245 */       query.setLoadDescription("+lazy", null);
/*     */     } 
/*     */     
/* 248 */     if (node != null)
/*     */     {
/* 250 */       query.setParentNode(node);
/*     */     }
/*     */     
/* 253 */     String idProperty = parentDesc.getIdBinder().getIdProperty();
/* 254 */     query.select(idProperty);
/*     */     
/* 256 */     if (onlyIds) {
/* 257 */       query.fetch(many.getName(), many.getTargetIdProperty());
/*     */     } else {
/* 259 */       query.fetch(many.getName());
/*     */     } 
/* 261 */     if (filterMany != null) {
/* 262 */       query.setFilterMany(many.getName(), filterMany);
/*     */     }
/*     */     
/* 265 */     query.where().idEq(parentId);
/* 266 */     query.setMode(SpiQuery.Mode.LAZYLOAD_MANY);
/* 267 */     query.setLazyLoadManyPath(many.getName());
/* 268 */     query.setPersistenceContext(defaultPersistenceContext);
/* 269 */     query.setVanillaMode(vanilla);
/*     */     
/* 271 */     if (ebi != null) {
/* 272 */       if (ebi.isSharedInstance()) {
/* 273 */         query.setSharedInstance();
/* 274 */       } else if (ebi.isReadOnly()) {
/* 275 */         query.setReadOnly(true);
/*     */       } 
/*     */     }
/*     */     
/* 279 */     this.server.findUnique(query, t);
/*     */     
/* 281 */     if (beanCollection != null && 
/* 282 */       beanCollection.checkEmptyLazyLoad() && 
/* 283 */       logger.isLoggable(Level.FINE)) {
/* 284 */       logger.fine("BeanCollection after load was empty. Owner:" + beanCollection.getOwnerBean());
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
/*     */   public void loadBean(LoadBeanRequest loadRequest) {
/* 296 */     List<EntityBeanIntercept> batch = loadRequest.getBatch();
/*     */     
/* 298 */     if (batch.size() == 0) {
/* 299 */       throw new RuntimeException("Nothing in batch?");
/*     */     }
/*     */ 
/*     */     
/* 303 */     int batchSize = getBatchSize(batch.size(), loadRequest.getBatchSize());
/*     */     
/* 305 */     LoadBeanContext ctx = loadRequest.getLoadContext();
/* 306 */     BeanDescriptor<?> desc = ctx.getBeanDescriptor();
/*     */     
/* 308 */     Class<?> beanType = desc.getBeanType();
/*     */     
/* 310 */     EntityBeanIntercept[] ebis = (EntityBeanIntercept[])batch.toArray(new EntityBeanIntercept[batch.size()]);
/* 311 */     ArrayList<Object> idList = new ArrayList<Object>(batchSize);
/*     */     
/* 313 */     for (i = 0; i < batch.size(); i++) {
/* 314 */       Object bean = ((EntityBeanIntercept)batch.get(i)).getOwner();
/* 315 */       Object id = desc.getId(bean);
/* 316 */       idList.add(id);
/*     */     } 
/* 318 */     int extraIds = batchSize - batch.size();
/* 319 */     if (extraIds > 0) {
/*     */ 
/*     */       
/* 322 */       Object firstId = idList.get(0);
/* 323 */       for (int i = 0; i < extraIds; i++)
/*     */       {
/* 325 */         idList.add(firstId);
/*     */       }
/*     */     } 
/*     */     
/* 329 */     PersistenceContext persistenceContext = ctx.getPersistenceContext();
/*     */ 
/*     */     
/* 332 */     for (i = 0; i < ebis.length; i++) {
/* 333 */       Object parentBean = ebis[i].getParentBean();
/* 334 */       if (parentBean != null) {
/*     */         
/* 336 */         BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 337 */         Object parentId = parentDesc.getId(parentBean);
/* 338 */         persistenceContext.put(parentId, parentBean);
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(beanType);
/*     */     
/* 344 */     query.setMode(SpiQuery.Mode.LAZYLOAD_BEAN);
/* 345 */     query.setPersistenceContext(persistenceContext);
/*     */     
/* 347 */     String mode = loadRequest.isLazy() ? "+lazy" : "+query";
/* 348 */     query.setLoadDescription(mode, loadRequest.getDescription());
/*     */     
/* 350 */     ctx.configureQuery(query, loadRequest.getLazyLoadProperty());
/*     */ 
/*     */     
/* 353 */     query.setUseCache(false);
/* 354 */     if (idList.size() == 1) {
/* 355 */       query.where().idEq(idList.get(0));
/*     */     } else {
/* 357 */       query.where().idIn(idList);
/*     */     } 
/*     */     
/* 360 */     List<?> list = this.server.findList(query, loadRequest.getTransaction());
/*     */     
/* 362 */     if (desc.calculateUseCache(null)) {
/* 363 */       for (int i = 0; i < list.size(); i++) {
/* 364 */         desc.cachePutObject(list.get(i));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 370 */   public void refresh(Object bean) { refreshBeanInternal(bean, SpiQuery.Mode.REFRESH_BEAN); }
/*     */ 
/*     */ 
/*     */   
/* 374 */   public void loadBean(EntityBeanIntercept ebi) { refreshBeanInternal(ebi.getOwner(), SpiQuery.Mode.LAZYLOAD_BEAN); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshBeanInternal(Object bean, SpiQuery.Mode mode) {
/* 379 */     boolean vanilla = !(bean instanceof EntityBean);
/*     */     
/* 381 */     EntityBeanIntercept ebi = null;
/* 382 */     DefaultPersistenceContext defaultPersistenceContext = null;
/*     */     
/* 384 */     if (!vanilla) {
/* 385 */       ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 386 */       defaultPersistenceContext = ebi.getPersistenceContext();
/*     */     } 
/*     */     
/* 389 */     BeanDescriptor<?> desc = this.server.getBeanDescriptor(bean.getClass());
/* 390 */     Object id = desc.getId(bean);
/*     */     
/* 392 */     if (defaultPersistenceContext == null) {
/*     */       
/* 394 */       defaultPersistenceContext = new DefaultPersistenceContext();
/* 395 */       defaultPersistenceContext.put(id, bean);
/* 396 */       if (ebi != null) {
/* 397 */         ebi.setPersistenceContext(defaultPersistenceContext);
/*     */       }
/*     */     } 
/*     */     
/* 401 */     SpiQuery<?> query = (SpiQuery)this.server.createQuery(desc.getBeanType());
/*     */     
/* 403 */     if (ebi != null) {
/* 404 */       if (desc.refreshFromCache(ebi, id)) {
/*     */         return;
/*     */       }
/* 407 */       if (desc.lazyLoadMany(ebi)) {
/*     */         return;
/*     */       }
/*     */       
/* 411 */       Object parentBean = ebi.getParentBean();
/* 412 */       if (parentBean != null) {
/*     */         
/* 414 */         BeanDescriptor<?> parentDesc = this.server.getBeanDescriptor(parentBean.getClass());
/* 415 */         Object parentId = parentDesc.getId(parentBean);
/* 416 */         defaultPersistenceContext.putIfAbsent(parentId, parentBean);
/*     */       } 
/*     */       
/* 419 */       query.setLazyLoadProperty(ebi.getLazyLoadProperty());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     query.setUsageProfiling(false);
/* 426 */     query.setPersistenceContext(defaultPersistenceContext);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     query.setMode(mode);
/* 432 */     query.setId(id);
/* 433 */     query.setUseCache(false);
/* 434 */     query.setVanillaMode(vanilla);
/*     */     
/* 436 */     if (ebi != null) {
/* 437 */       if (ebi.isSharedInstance()) {
/* 438 */         query.setSharedInstance();
/* 439 */       } else if (ebi.isReadOnly()) {
/* 440 */         query.setReadOnly(true);
/*     */       } 
/*     */     }
/*     */     
/* 444 */     Object dbBean = query.findUnique();
/*     */     
/* 446 */     if (dbBean == null) {
/* 447 */       String msg = "Bean not found during lazy load or refresh. id[" + id + "] type[" + desc.getBeanType() + "]";
/* 448 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/* 451 */     if (desc.calculateUseCache(null) && !vanilla)
/* 452 */       desc.cachePutObject(dbBean); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultBeanLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */