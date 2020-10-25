/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanList;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanListHelp<T>
/*     */   extends Object
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*     */   public BeanListHelp(BeanPropertyAssocMany<T> many) {
/*  27 */     this.many = many;
/*  28 */     this.targetDescriptor = many.getTargetDescriptor();
/*     */   }
/*     */   
/*     */   public BeanListHelp() {
/*  32 */     this.many = null;
/*  33 */     this.targetDescriptor = null;
/*     */   }
/*     */ 
/*     */   
/*  37 */   public void setLoader(BeanCollectionLoader loader) { this.loader = loader; }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public void add(BeanCollection<?> collection, Object bean) { collection.internalAdd(bean); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  47 */     if (bc instanceof BeanList) {
/*     */       
/*  49 */       BeanList<?> bl = (BeanList)bc;
/*  50 */       if (bl.getActualList() == null) {
/*  51 */         bl.setActualList(new ArrayList());
/*     */       }
/*  53 */       return bl;
/*  54 */     }  if (bc instanceof List) {
/*  55 */       return new VanillaAdd((List)bc, null);
/*     */     }
/*     */     
/*  58 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class VanillaAdd
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final List list;
/*     */ 
/*     */     
/*  69 */     private VanillaAdd(List<?> list) { this.list = list; }
/*     */ 
/*     */ 
/*     */     
/*  73 */     public void addBean(Object bean) { this.list.add(bean); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public Iterator<?> getIterator(Object collection) { return ((List)collection).iterator(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object copyCollection(Object source, CopyContext ctx, int maxDepth, Object parentBean) {
/*  83 */     if (!(source instanceof List)) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     List<T> l = ctx.isVanillaMode() ? new ArrayList() : new BeanList();
/*     */     
/*  89 */     if (!(source instanceof BeanList)) {
/*  90 */       l.addAll((List)source);
/*  91 */       return l;
/*     */     } 
/*     */     
/*  94 */     BeanList<?> bl = (BeanList)source;
/*  95 */     if (!bl.isPopulated()) {
/*  96 */       if (ctx.isVanillaMode() || parentBean == null) {
/*  97 */         return null;
/*     */       }
/*  99 */       return createReference(parentBean, this.many.getName());
/*     */     } 
/*     */     
/* 102 */     List<?> actualList = bl.getActualList();
/* 103 */     for (int i = 0; i < actualList.size(); i++) {
/* 104 */       Object sourceDetail = actualList.get(i);
/* 105 */       Object destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
/* 106 */       l.add(destDetail);
/*     */     } 
/* 108 */     return l;
/*     */   }
/*     */ 
/*     */   
/* 112 */   public Object createEmpty(boolean vanilla) { return vanilla ? new ArrayList() : new BeanList(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public BeanCollection<T> createReference(Object parentBean, String propertyName) { return new BeanList(this.loader, parentBean, propertyName); }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/* 122 */     ArrayList<InvalidValue> errs = null;
/*     */     
/* 124 */     List<?> l = (List)manyValue;
/* 125 */     for (int i = 0; i < l.size(); i++) {
/* 126 */       Object detailBean = l.get(i);
/* 127 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/* 128 */       if (invalid != null) {
/* 129 */         if (errs == null) {
/* 130 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 132 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/* 135 */     return errs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 140 */     BeanList<?> newBeanList = (BeanList)server.findList(query, t);
/* 141 */     refresh(newBeanList, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 146 */     BeanList<?> newBeanList = (BeanList)bc;
/*     */     
/* 148 */     List<?> currentList = (List)this.many.getValueUnderlying(parentBean);
/*     */     
/* 150 */     newBeanList.setModifyListening(this.many.getModifyListenMode());
/*     */     
/* 152 */     if (currentList == null) {
/*     */       
/* 154 */       this.many.setValue(parentBean, newBeanList);
/*     */     }
/* 156 */     else if (currentList instanceof BeanList) {
/*     */       
/* 158 */       BeanList<?> currentBeanList = (BeanList)currentList;
/* 159 */       currentBeanList.setActualList(newBeanList.getActualList());
/* 160 */       currentBeanList.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 164 */       this.many.setValue(parentBean, newBeanList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     List<?> list;
/* 171 */     if (collection instanceof BeanCollection) {
/* 172 */       BeanList<?> beanList = (BeanList)collection;
/* 173 */       if (!beanList.isPopulated()) {
/* 174 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 177 */           beanList.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 182 */       list = beanList.getActualList();
/*     */     } else {
/* 184 */       list = (List)collection;
/*     */     } 
/*     */     
/* 187 */     ctx.beginAssocMany(name);
/* 188 */     for (int j = 0; j < list.size(); j++) {
/* 189 */       if (j > 0) {
/* 190 */         ctx.appendComma();
/*     */       }
/* 192 */       Object detailBean = list.get(j);
/* 193 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 195 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanListHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */