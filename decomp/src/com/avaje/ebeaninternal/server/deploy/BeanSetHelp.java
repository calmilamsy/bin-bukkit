/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanSet;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanSetHelp<T>
/*     */   extends Object
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*     */   public BeanSetHelp(BeanPropertyAssocMany<T> many) {
/*  31 */     this.many = many;
/*  32 */     this.targetDescriptor = many.getTargetDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanSetHelp() {
/*  39 */     this.many = null;
/*  40 */     this.targetDescriptor = null;
/*     */   }
/*     */ 
/*     */   
/*  44 */   public void setLoader(BeanCollectionLoader loader) { this.loader = loader; }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public Iterator<?> getIterator(Object collection) { return ((Set)collection).iterator(); }
/*     */ 
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  52 */     if (bc instanceof BeanSet) {
/*  53 */       BeanSet<?> beanSet = (BeanSet)bc;
/*  54 */       if (beanSet.getActualSet() == null) {
/*  55 */         beanSet.setActualSet(new LinkedHashSet());
/*     */       }
/*  57 */       return beanSet;
/*  58 */     }  if (bc instanceof Set) {
/*  59 */       return new VanillaAdd((Set)bc, null);
/*     */     }
/*     */     
/*  62 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class VanillaAdd
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final Set set;
/*     */ 
/*     */ 
/*     */     
/*  74 */     private VanillaAdd(Set<?> set) { this.set = set; }
/*     */ 
/*     */ 
/*     */     
/*  78 */     public void addBean(Object bean) { this.set.add(bean); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public void add(BeanCollection<?> collection, Object bean) { collection.internalAdd(bean); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object copyCollection(Object source, CopyContext ctx, int maxDepth, Object parentBean) {
/*  88 */     if (!(source instanceof Set)) {
/*  89 */       return null;
/*     */     }
/*  91 */     Set<T> s = ctx.isVanillaMode() ? new LinkedHashSet() : new BeanSet();
/*     */     
/*  93 */     if (!(source instanceof BeanSet)) {
/*  94 */       s.addAll((Set)source);
/*  95 */       return s;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     BeanSet<?> bc = (BeanSet)source;
/* 100 */     if (!bc.isPopulated()) {
/* 101 */       if (ctx.isVanillaMode() || parentBean == null) {
/* 102 */         return null;
/*     */       }
/* 104 */       return createReference(parentBean, this.many.getName());
/*     */     } 
/*     */     
/* 107 */     Set<?> actual = bc.getActualSet();
/* 108 */     for (Object sourceDetail : actual) {
/* 109 */       Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
/* 110 */       s.add(destDetail);
/*     */     } 
/* 112 */     return s;
/*     */   }
/*     */ 
/*     */   
/* 116 */   public Object createEmpty(boolean vanilla) { return vanilla ? new LinkedHashSet() : new BeanSet(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public BeanCollection<T> createReference(Object parentBean, String propertyName) { return new BeanSet(this.loader, parentBean, propertyName); }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/* 126 */     ArrayList<InvalidValue> errs = null;
/*     */     
/* 128 */     Set<?> set = (Set)manyValue;
/* 129 */     Iterator<?> i = set.iterator();
/* 130 */     while (i.hasNext()) {
/* 131 */       Object detailBean = i.next();
/* 132 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/* 133 */       if (invalid != null) {
/* 134 */         if (errs == null) {
/* 135 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 137 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/* 140 */     return errs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 145 */     BeanSet<?> newBeanSet = (BeanSet)server.findSet(query, t);
/* 146 */     refresh(newBeanSet, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 151 */     BeanSet<?> newBeanSet = (BeanSet)bc;
/*     */     
/* 153 */     Set<?> current = (Set)this.many.getValueUnderlying(parentBean);
/*     */     
/* 155 */     newBeanSet.setModifyListening(this.many.getModifyListenMode());
/* 156 */     if (current == null) {
/*     */       
/* 158 */       this.many.setValue(parentBean, newBeanSet);
/*     */     }
/* 160 */     else if (current instanceof BeanSet) {
/*     */       
/* 162 */       BeanSet<?> currentBeanSet = (BeanSet)current;
/* 163 */       currentBeanSet.setActualSet(newBeanSet.getActualSet());
/* 164 */       currentBeanSet.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 168 */       this.many.setValue(parentBean, newBeanSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     Set<?> set;
/* 175 */     if (collection instanceof BeanCollection) {
/* 176 */       BeanSet<?> bc = (BeanSet)collection;
/* 177 */       if (!bc.isPopulated()) {
/* 178 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 181 */           bc.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 186 */       set = bc.getActualSet();
/*     */     } else {
/* 188 */       set = (Set)collection;
/*     */     } 
/*     */     
/* 191 */     int count = 0;
/* 192 */     ctx.beginAssocMany(name);
/* 193 */     Iterator<?> it = set.iterator();
/* 194 */     while (it.hasNext()) {
/* 195 */       Object detailBean = it.next();
/* 196 */       if (count++ > 0) {
/* 197 */         ctx.appendComma();
/*     */       }
/* 199 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 201 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanSetHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */