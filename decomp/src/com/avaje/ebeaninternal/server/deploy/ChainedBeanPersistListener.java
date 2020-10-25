/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanPersistListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChainedBeanPersistListener<T>
/*     */   extends Object
/*     */   implements BeanPersistListener<T>
/*     */ {
/*     */   private final List<BeanPersistListener<T>> list;
/*     */   private final BeanPersistListener<T>[] chain;
/*     */   
/*  21 */   public ChainedBeanPersistListener(BeanPersistListener<T> c1, BeanPersistListener<T> c2) { this(addList(c1, c2)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> List<BeanPersistListener<T>> addList(BeanPersistListener<T> c1, BeanPersistListener<T> c2) {
/*  28 */     ArrayList<BeanPersistListener<T>> addList = new ArrayList<BeanPersistListener<T>>(2);
/*  29 */     addList.add(c1);
/*  30 */     addList.add(c2);
/*  31 */     return addList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistListener(List<BeanPersistListener<T>> list) {
/*  40 */     this.list = list;
/*  41 */     this.chain = (BeanPersistListener[])list.toArray(new BeanPersistListener[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistListener<T> register(BeanPersistListener<T> c) {
/*  48 */     if (this.list.contains(c)) {
/*  49 */       return this;
/*     */     }
/*  51 */     List<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
/*  52 */     newList.addAll(this.list);
/*  53 */     newList.add(c);
/*     */     
/*  55 */     return new ChainedBeanPersistListener(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChainedBeanPersistListener<T> deregister(BeanPersistListener<T> c) {
/*  63 */     if (!this.list.contains(c)) {
/*  64 */       return this;
/*     */     }
/*  66 */     ArrayList<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
/*  67 */     newList.addAll(this.list);
/*  68 */     newList.remove(c);
/*     */     
/*  70 */     return new ChainedBeanPersistListener(newList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleted(T bean) {
/*  76 */     boolean notifyCluster = false;
/*  77 */     for (int i = 0; i < this.chain.length; i++) {
/*  78 */       if (this.chain[i].deleted(bean)) {
/*  79 */         notifyCluster = true;
/*     */       }
/*     */     } 
/*  82 */     return notifyCluster;
/*     */   }
/*     */   
/*     */   public boolean inserted(T bean) {
/*  86 */     boolean notifyCluster = false;
/*  87 */     for (int i = 0; i < this.chain.length; i++) {
/*  88 */       if (this.chain[i].inserted(bean)) {
/*  89 */         notifyCluster = true;
/*     */       }
/*     */     } 
/*  92 */     return notifyCluster;
/*     */   }
/*     */   
/*     */   public void remoteDelete(Object id) {
/*  96 */     for (int i = 0; i < this.chain.length; i++) {
/*  97 */       this.chain[i].remoteDelete(id);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remoteInsert(Object id) {
/* 102 */     for (int i = 0; i < this.chain.length; i++) {
/* 103 */       this.chain[i].remoteInsert(id);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remoteUpdate(Object id) {
/* 108 */     for (int i = 0; i < this.chain.length; i++) {
/* 109 */       this.chain[i].remoteUpdate(id);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean updated(T bean, Set<String> updatedProperties) {
/* 114 */     boolean notifyCluster = false;
/* 115 */     for (int i = 0; i < this.chain.length; i++) {
/* 116 */       if (this.chain[i].updated(bean, updatedProperties)) {
/* 117 */         notifyCluster = true;
/*     */       }
/*     */     } 
/* 120 */     return notifyCluster;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ChainedBeanPersistListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */