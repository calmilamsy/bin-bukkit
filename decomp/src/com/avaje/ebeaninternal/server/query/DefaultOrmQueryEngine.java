/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.event.BeanFinder;
/*     */ import com.avaje.ebeaninternal.api.BeanIdList;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.CopyBeanCollection;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryEngine;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
/*     */ import com.avaje.ebeaninternal.server.deploy.CopyContext;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultOrmQueryEngine
/*     */   implements OrmQueryEngine
/*     */ {
/*     */   private final CQueryEngine queryEngine;
/*     */   
/*  53 */   public DefaultOrmQueryEngine(BeanDescriptorManager descMgr, CQueryEngine queryEngine) { this.queryEngine = queryEngine; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public <T> int findRowCount(OrmQueryRequest<T> request) { return this.queryEngine.findRowCount(request); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public <T> BeanIdList findIds(OrmQueryRequest<T> request) { return this.queryEngine.findIds(request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request) {
/*  71 */     SpiTransaction t = request.getTransaction();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     t.flushBatch();
/*     */     
/*  78 */     return this.queryEngine.findIterate(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> BeanCollection<T> findMany(OrmQueryRequest<T> request) {
/*  83 */     SpiQuery<T> query = request.getQuery();
/*  84 */     if (query.isUseQueryCache() || query.isLoadBeanCache())
/*     */     {
/*     */       
/*  87 */       query.setSharedInstance();
/*     */     }
/*     */     
/*  90 */     BeanCollection<T> result = null;
/*     */     
/*  92 */     SpiTransaction t = request.getTransaction();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     t.flushBatch();
/*     */     
/*  99 */     BeanFinder<T> finder = request.getBeanFinder();
/* 100 */     if (finder != null) {
/*     */       
/* 102 */       result = finder.findMany(request);
/*     */     } else {
/* 104 */       result = this.queryEngine.findMany(request);
/*     */     } 
/*     */     
/* 107 */     if (query.isLoadBeanCache()) {
/*     */       
/* 109 */       BeanDescriptor<T> descriptor = request.getBeanDescriptor();
/* 110 */       Collection<T> c = result.getActualDetails();
/* 111 */       Iterator<T> it = c.iterator();
/* 112 */       while (it.hasNext()) {
/* 113 */         T bean = (T)it.next();
/* 114 */         descriptor.cachePut(bean, query.isSharedInstance());
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     if (query.isSharedInstance() && !result.isEmpty()) {
/* 119 */       if (query.isUseQueryCache())
/*     */       {
/* 121 */         request.putToQueryCache(result);
/*     */       }
/* 123 */       if (Boolean.FALSE.equals(query.isReadOnly())) {
/*     */         
/* 125 */         CopyContext ctx = new CopyContext(request.isVanillaMode(), false);
/* 126 */         result = (new CopyBeanCollection(result, request.getBeanDescriptor(), ctx, 5)).copy();
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T findId(OrmQueryRequest<T> request) {
/* 139 */     T result = null;
/*     */     
/* 141 */     SpiTransaction t = request.getTransaction();
/*     */     
/* 143 */     if (t.isBatchFlushOnQuery())
/*     */     {
/*     */ 
/*     */       
/* 147 */       t.flushBatch();
/*     */     }
/*     */     
/* 150 */     BeanFinder<T> finder = request.getBeanFinder();
/* 151 */     if (finder != null) {
/* 152 */       result = (T)finder.find(request);
/*     */     } else {
/* 154 */       result = (T)this.queryEngine.find(request);
/*     */     } 
/*     */     
/* 157 */     if (result != null && request.isUseBeanCache()) {
/* 158 */       BeanDescriptor<T> descriptor = request.getBeanDescriptor();
/* 159 */       descriptor.cachePut(result, request.isUseBeanCacheReadOnly());
/*     */     } 
/*     */     
/* 162 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\DefaultOrmQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */