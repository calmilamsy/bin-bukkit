/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ModifyCollection<E>
/*     */   extends Object
/*     */   implements Collection<E>
/*     */ {
/*     */   protected final BeanCollection<E> owner;
/*     */   protected final Collection<E> c;
/*     */   
/*     */   public ModifyCollection(BeanCollection<E> owner, Collection<E> c) {
/*  48 */     this.owner = owner;
/*  49 */     this.c = c;
/*     */   }
/*     */   
/*     */   public boolean add(E o) {
/*  53 */     if (this.c.add(o)) {
/*  54 */       this.owner.modifyAddition(o);
/*  55 */       return true;
/*     */     } 
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> collection) {
/*  61 */     boolean changed = false;
/*  62 */     Iterator<? extends E> it = collection.iterator();
/*  63 */     while (it.hasNext()) {
/*  64 */       E o = (E)it.next();
/*  65 */       if (this.c.add(o)) {
/*  66 */         this.owner.modifyAddition(o);
/*  67 */         changed = true;
/*     */       } 
/*     */     } 
/*  70 */     return changed;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public void clear() { this.c.clear(); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public boolean contains(Object o) { return this.c.contains(o); }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public boolean containsAll(Collection<?> collection) { return this.c.containsAll(collection); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public boolean isEmpty() { return this.c.isEmpty(); }
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/*  90 */     Iterator<E> it = this.c.iterator();
/*  91 */     return new ModifyIterator(this.owner, it);
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/*  95 */     if (this.c.remove(o)) {
/*  96 */       this.owner.modifyRemoval(o);
/*  97 */       return true;
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> collection) {
/* 103 */     boolean changed = false;
/* 104 */     Iterator<?> it = collection.iterator();
/* 105 */     while (it.hasNext()) {
/* 106 */       Object o = it.next();
/* 107 */       if (this.c.remove(o)) {
/* 108 */         this.owner.modifyRemoval(o);
/* 109 */         changed = true;
/*     */       } 
/*     */     } 
/* 112 */     return changed;
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> collection) {
/* 116 */     boolean changed = false;
/* 117 */     Iterator<?> it = this.c.iterator();
/* 118 */     while (it.hasNext()) {
/* 119 */       Object o = it.next();
/* 120 */       if (!collection.contains(o)) {
/* 121 */         it.remove();
/* 122 */         this.owner.modifyRemoval(o);
/* 123 */         changed = true;
/*     */       } 
/*     */     } 
/* 126 */     return changed;
/*     */   }
/*     */ 
/*     */   
/* 130 */   public int size() { return this.c.size(); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public Object[] toArray() { return this.c.toArray(); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public <T> T[] toArray(T[] a) { return (T[])this.c.toArray(a); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\common\ModifyCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */