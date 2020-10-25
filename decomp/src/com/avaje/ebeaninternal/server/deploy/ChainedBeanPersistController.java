/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanPersistController;
/*     */ import com.avaje.ebean.event.BeanPersistRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ChainedBeanPersistController
/*     */   implements BeanPersistController
/*     */ {
/*  14 */   private static final Sorter SORTER = new Sorter(null);
/*     */ 
/*     */   
/*     */   private final List<BeanPersistController> list;
/*     */ 
/*     */   
/*     */   private final BeanPersistController[] chain;
/*     */ 
/*     */   
/*  23 */   public ChainedBeanPersistController(BeanPersistController c1, BeanPersistController c2) { this(addList(c1, c2)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<BeanPersistController> addList(BeanPersistController c1, BeanPersistController c2) {
/*  30 */     ArrayList<BeanPersistController> addList = new ArrayList<BeanPersistController>(2);
/*  31 */     addList.add(c1);
/*  32 */     addList.add(c2);
/*  33 */     return addList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistController(List<BeanPersistController> list) {
/*  41 */     this.list = list;
/*  42 */     BeanPersistController[] c = (BeanPersistController[])list.toArray(new BeanPersistController[list.size()]);
/*  43 */     Arrays.sort(c, SORTER);
/*  44 */     this.chain = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistController register(BeanPersistController c) {
/*  51 */     if (this.list.contains(c)) {
/*  52 */       return this;
/*     */     }
/*  54 */     ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
/*  55 */     newList.addAll(this.list);
/*  56 */     newList.add(c);
/*     */     
/*  58 */     return new ChainedBeanPersistController(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistController deregister(BeanPersistController c) {
/*  66 */     if (!this.list.contains(c)) {
/*  67 */       return this;
/*     */     }
/*  69 */     ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
/*  70 */     newList.addAll(this.list);
/*  71 */     newList.remove(c);
/*     */     
/*  73 */     return new ChainedBeanPersistController(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public int getExecutionOrder() { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean isRegisterFor(Class<?> cls) { return false; }
/*     */ 
/*     */   
/*     */   public void postDelete(BeanPersistRequest<?> request) {
/*  92 */     for (int i = 0; i < this.chain.length; i++) {
/*  93 */       this.chain[i].postDelete(request);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postInsert(BeanPersistRequest<?> request) {
/*  98 */     for (int i = 0; i < this.chain.length; i++) {
/*  99 */       this.chain[i].postInsert(request);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postLoad(Object bean, Set<String> includedProperties) {
/* 104 */     for (int i = 0; i < this.chain.length; i++) {
/* 105 */       this.chain[i].postLoad(bean, includedProperties);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postUpdate(BeanPersistRequest<?> request) {
/* 110 */     for (int i = 0; i < this.chain.length; i++) {
/* 111 */       this.chain[i].postUpdate(request);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean preDelete(BeanPersistRequest<?> request) {
/* 116 */     for (int i = 0; i < this.chain.length; i++) {
/* 117 */       if (!this.chain[i].preDelete(request)) {
/* 118 */         return false;
/*     */       }
/*     */     } 
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public boolean preInsert(BeanPersistRequest<?> request) {
/* 125 */     for (int i = 0; i < this.chain.length; i++) {
/* 126 */       if (!this.chain[i].preInsert(request)) {
/* 127 */         return false;
/*     */       }
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public boolean preUpdate(BeanPersistRequest<?> request) {
/* 134 */     for (int i = 0; i < this.chain.length; i++) {
/* 135 */       if (!this.chain[i].preUpdate(request)) {
/* 136 */         return false;
/*     */       }
/*     */     } 
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   private static class Sorter
/*     */     extends Object
/*     */     implements Comparator<BeanPersistController>
/*     */   {
/*     */     private Sorter() {}
/*     */     
/*     */     public int compare(BeanPersistController o1, BeanPersistController o2) {
/* 149 */       int i1 = o1.getExecutionOrder();
/* 150 */       int i2 = o2.getExecutionOrder();
/* 151 */       return (i1 < i2) ? -1 : ((i1 == i2) ? 0 : 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ChainedBeanPersistController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */