/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ class ModifyList<E>
/*     */   extends ModifyCollection<E>
/*     */   implements List<E>
/*     */ {
/*     */   private final List<E> list;
/*     */   
/*     */   ModifyList(BeanCollection<E> owner, List<E> list) {
/*  52 */     super(owner, list);
/*  53 */     this.list = list;
/*     */   }
/*     */   
/*     */   public void add(int index, E element) {
/*  57 */     this.list.add(index, element);
/*  58 */     this.owner.modifyAddition(element);
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> co) {
/*  62 */     if (this.list.addAll(index, co)) {
/*  63 */       Iterator<? extends E> it = co.iterator();
/*  64 */       while (it.hasNext()) {
/*  65 */         E o = (E)it.next();
/*  66 */         this.owner.modifyAddition(o);
/*     */       } 
/*  68 */       return true;
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public E get(int index) { return (E)this.list.get(index); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public int indexOf(Object o) { return this.list.indexOf(o); }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public int lastIndexOf(Object o) { return this.list.lastIndexOf(o); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public ListIterator<E> listIterator() { return new ModifyListIterator(this.owner, this.list.listIterator()); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public ListIterator<E> listIterator(int index) { return new ModifyListIterator(this.owner, this.list.listIterator(index)); }
/*     */ 
/*     */   
/*     */   public E remove(int index) {
/*  94 */     E o = (E)this.list.remove(index);
/*  95 */     this.owner.modifyRemoval(o);
/*  96 */     return o;
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/* 100 */     E o = (E)this.list.set(index, element);
/* 101 */     this.owner.modifyAddition(element);
/* 102 */     this.owner.modifyRemoval(o);
/* 103 */     return o;
/*     */   }
/*     */ 
/*     */   
/* 107 */   public List<E> subList(int fromIndex, int toIndex) { return new ModifyList(this.owner, this.list.subList(fromIndex, toIndex)); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\ModifyList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */