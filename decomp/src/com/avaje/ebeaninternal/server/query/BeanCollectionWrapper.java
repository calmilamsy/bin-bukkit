/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.util.BeanCollectionFactory;
/*     */ import com.avaje.ebeaninternal.server.util.BeanCollectionParams;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanCollectionWrapper
/*     */ {
/*     */   private final boolean isMap;
/*     */   private final Query.Type queryType;
/*     */   private final String mapKey;
/*     */   private final BeanCollection<?> beanCollection;
/*     */   private final Collection<Object> collection;
/*     */   private final Map<Object, Object> map;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private int rowCount;
/*     */   
/*     */   public BeanCollectionWrapper(RelationalQueryRequest request) {
/*  84 */     this.desc = null;
/*  85 */     this.queryType = request.getQueryType();
/*  86 */     this.mapKey = request.getQuery().getMapKey();
/*  87 */     this.isMap = Query.Type.MAP.equals(this.queryType);
/*     */     
/*  89 */     this.beanCollection = createBeanCollection(this.queryType);
/*  90 */     this.collection = getCollection(this.isMap);
/*  91 */     this.map = getMap(this.isMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollectionWrapper(OrmQueryRequest<?> request) {
/*  99 */     this.desc = request.getBeanDescriptor();
/* 100 */     this.queryType = request.getQueryType();
/* 101 */     this.mapKey = request.getQuery().getMapKey();
/* 102 */     this.isMap = Query.Type.MAP.equals(this.queryType);
/*     */     
/* 104 */     this.beanCollection = createBeanCollection(this.queryType);
/* 105 */     this.collection = getCollection(this.isMap);
/* 106 */     this.map = getMap(this.isMap);
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
/*     */   public BeanCollectionWrapper(BeanPropertyAssocMany<?> manyProp) {
/* 119 */     this.queryType = manyProp.getManyType().getQueryType();
/* 120 */     this.mapKey = manyProp.getMapKey();
/* 121 */     this.desc = manyProp.getTargetDescriptor();
/* 122 */     this.isMap = Query.Type.MAP.equals(this.queryType);
/*     */     
/* 124 */     this.beanCollection = createBeanCollection(this.queryType);
/* 125 */     this.collection = getCollection(this.isMap);
/* 126 */     this.map = getMap(this.isMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 131 */   private Map<Object, Object> getMap(boolean isMap) { return isMap ? (Map)this.beanCollection : null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private Collection<Object> getCollection(boolean isMap) { return isMap ? null : (Collection)this.beanCollection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public BeanCollection<?> getBeanCollection() { return this.beanCollection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanCollection<?> createBeanCollection(Query.Type manyType) {
/* 150 */     BeanCollectionParams p = new BeanCollectionParams(manyType);
/* 151 */     return BeanCollectionFactory.create(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public boolean isMap() { return this.isMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public int size() { return this.rowCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public void add(Object bean) { add(bean, this.beanCollection); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object bean, Object collection) {
/* 185 */     if (bean == null) {
/*     */       return;
/*     */     }
/* 188 */     this.rowCount++;
/* 189 */     if (this.isMap) {
/* 190 */       Object keyValue = null;
/* 191 */       if (this.mapKey != null) {
/*     */         
/* 193 */         keyValue = this.desc.getValue(bean, this.mapKey);
/*     */       } else {
/*     */         
/* 196 */         keyValue = this.desc.getId(bean);
/*     */       } 
/*     */       
/* 199 */       Map mapColl = (Map)collection;
/* 200 */       mapColl.put(keyValue, bean);
/*     */     } else {
/* 202 */       ((Collection)collection).add(bean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void addToCollection(Object bean) { this.collection.add(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public void addToMap(Object bean, Object key) { this.map.put(key, bean); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\BeanCollectionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */