/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanMap;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class BeanMapHelp<T>
/*     */   extends Object
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private final BeanProperty beanProperty;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*  34 */   public BeanMapHelp(BeanDescriptor<T> targetDescriptor, String mapKey) { this(null, targetDescriptor, mapKey); }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public BeanMapHelp(BeanPropertyAssocMany<T> many) { this(many, many.getTargetDescriptor(), many.getMapKey()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanMapHelp(BeanPropertyAssocMany<T> many, BeanDescriptor<T> targetDescriptor, String mapKey) {
/*  45 */     this.many = many;
/*  46 */     this.targetDescriptor = targetDescriptor;
/*     */     
/*  48 */     this.beanProperty = targetDescriptor.getBeanProperty(mapKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public Iterator<?> getIterator(Object collection) { return ((Map)collection).values().iterator(); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public void setLoader(BeanCollectionLoader loader) { this.loader = loader; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  65 */     if (mapKey == null) {
/*  66 */       mapKey = this.many.getMapKey();
/*     */     }
/*  68 */     BeanProperty beanProp = this.targetDescriptor.getBeanProperty(mapKey);
/*     */     
/*  70 */     if (bc instanceof BeanMap) {
/*  71 */       BeanMap<Object, Object> bm = (BeanMap)bc;
/*  72 */       Map<Object, Object> actualMap = bm.getActualMap();
/*  73 */       if (actualMap == null) {
/*  74 */         actualMap = new LinkedHashMap<Object, Object>();
/*  75 */         bm.setActualMap(actualMap);
/*     */       } 
/*  77 */       return new Adder(beanProp, actualMap);
/*     */     } 
/*  79 */     if (bc instanceof Map) {
/*  80 */       return new Adder(beanProp, (Map)bc);
/*     */     }
/*     */     
/*  83 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Adder
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final BeanProperty beanProperty;
/*     */     private final Map<Object, Object> map;
/*     */     
/*     */     Adder(BeanProperty beanProperty, Map<Object, Object> map) {
/*  94 */       this.beanProperty = beanProperty;
/*  95 */       this.map = map;
/*     */     }
/*     */     
/*     */     public void addBean(Object bean) {
/*  99 */       Object keyValue = this.beanProperty.getValue(bean);
/* 100 */       this.map.put(keyValue, bean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object copyCollection(Object source, CopyContext ctx, int maxDepth, Object parentBean) {
/* 107 */     if (!(source instanceof Map)) {
/* 108 */       return null;
/*     */     }
/* 110 */     Map<Object, Object> m = ctx.isVanillaMode() ? new LinkedHashMap() : new BeanMap();
/*     */     
/* 112 */     Map<?, ?> sourceMap = (Map)source;
/* 113 */     if (!(source instanceof BeanMap)) {
/* 114 */       for (Map.Entry<?, ?> entry : sourceMap.entrySet()) {
/* 115 */         m.put(entry.getKey(), entry.getValue());
/*     */       }
/* 117 */       return m;
/*     */     } 
/*     */     
/* 120 */     BeanMap<?, ?> bc = (BeanMap)source;
/* 121 */     if (!bc.isPopulated()) {
/* 122 */       if (ctx.isVanillaMode() || parentBean == null) {
/* 123 */         return null;
/*     */       }
/* 125 */       return createReference(parentBean, this.many.getName());
/*     */     } 
/*     */ 
/*     */     
/* 129 */     Map<?, ?> actual = bc.getActualMap();
/* 130 */     for (Map.Entry<?, ?> entry : actual.entrySet()) {
/* 131 */       Object sourceDetail = entry.getValue();
/* 132 */       Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
/* 133 */       m.put(entry.getKey(), destDetail);
/*     */     } 
/*     */     
/* 136 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public Object createEmpty(boolean vanilla) { return vanilla ? new LinkedHashMap() : new BeanMap(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(BeanCollection<?> collection, Object bean) {
/* 149 */     Object keyValue = this.beanProperty.getValueIntercept(bean);
/*     */     
/* 151 */     Map<Object, Object> map = (Map)collection;
/* 152 */     map.put(keyValue, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public BeanCollection<T> createReference(Object parentBean, String propertyName) { return new BeanMap(this.loader, parentBean, propertyName); }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/* 163 */     ArrayList<InvalidValue> errs = null;
/*     */     
/* 165 */     Map<?, ?> m = (Map)manyValue;
/* 166 */     Iterator<?> it = m.values().iterator();
/* 167 */     while (it.hasNext()) {
/* 168 */       Object detailBean = it.next();
/* 169 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/* 170 */       if (invalid != null) {
/* 171 */         if (errs == null) {
/* 172 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 174 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return errs;
/*     */   }
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 182 */     BeanMap<?, ?> newBeanMap = (BeanMap)server.findMap(query, t);
/* 183 */     refresh(newBeanMap, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 188 */     BeanMap<?, ?> newBeanMap = (BeanMap)bc;
/* 189 */     Map<?, ?> current = (Map)this.many.getValueUnderlying(parentBean);
/*     */     
/* 191 */     newBeanMap.setModifyListening(this.many.getModifyListenMode());
/* 192 */     if (current == null) {
/*     */       
/* 194 */       this.many.setValue(parentBean, newBeanMap);
/*     */     }
/* 196 */     else if (current instanceof BeanMap) {
/*     */       
/* 198 */       BeanMap<?, ?> currentBeanMap = (BeanMap)current;
/* 199 */       currentBeanMap.setActualMap(newBeanMap.getActualMap());
/* 200 */       currentBeanMap.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 204 */       this.many.setValue(parentBean, newBeanMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     Map<?, ?> map;
/* 211 */     if (collection instanceof BeanCollection) {
/* 212 */       BeanMap<?, ?> bc = (BeanMap)collection;
/* 213 */       if (!bc.isPopulated()) {
/* 214 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 217 */           bc.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 222 */       map = bc.getActualMap();
/*     */     } else {
/* 224 */       map = (Map)collection;
/*     */     } 
/*     */     
/* 227 */     int count = 0;
/* 228 */     ctx.beginAssocMany(name);
/* 229 */     Iterator<?> it = map.entrySet().iterator();
/* 230 */     while (it.hasNext()) {
/* 231 */       Map.Entry<?, ?> entry = (Map.Entry)it.next();
/* 232 */       if (count++ > 0) {
/* 233 */         ctx.appendComma();
/*     */       }
/*     */       
/* 236 */       Object detailBean = entry.getValue();
/* 237 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 239 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanMapHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */