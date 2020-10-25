/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ final class ImmutableEnumSet<E>
/*     */   extends ImmutableSet<E>
/*     */ {
/*     */   private final Set<E> delegate;
/*     */   private int hashCode;
/*     */   
/*  57 */   ImmutableEnumSet(Set<E> delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public UnmodifiableIterator<E> iterator() { return Iterators.unmodifiableIterator(this.delegate.iterator()); }
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int size() { return this.delegate.size(); }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean contains(Object object) { return this.delegate.contains(object); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public boolean containsAll(Collection<?> collection) { return this.delegate.containsAll(collection); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public boolean isEmpty() { return this.delegate.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public Object[] toArray() { return this.delegate.toArray(); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public <T> T[] toArray(T[] array) { return (T[])this.delegate.toArray(array); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public boolean equals(Object object) { return (object == this || this.delegate.equals(object)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  95 */     int result = this.hashCode;
/*  96 */     return (result == 0) ? (this.hashCode = this.delegate.hashCode()) : result;
/*     */   }
/*     */ 
/*     */   
/* 100 */   public String toString() { return this.delegate.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   Object writeReplace() { return new EnumSerializedForm((EnumSet)this.delegate); }
/*     */ 
/*     */   
/*     */   private static class EnumSerializedForm<E extends Enum<E>>
/*     */     extends Object
/*     */     implements Serializable
/*     */   {
/*     */     final EnumSet<E> delegate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 116 */     EnumSerializedForm(EnumSet<E> delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */     
/* 120 */     Object readResolve() { return new ImmutableEnumSet(this.delegate.clone()); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableEnumSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */