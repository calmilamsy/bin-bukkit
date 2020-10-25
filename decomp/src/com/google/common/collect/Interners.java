/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.FinalizableReferenceQueue;
/*     */ import com.google.common.base.FinalizableWeakReference;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ @Beta
/*     */ public final class Interners
/*     */ {
/*     */   public static <E> Interner<E> newStrongInterner() {
/*  44 */     map = (new MapMaker()).makeMap();
/*  45 */     return new Interner<E>() {
/*     */         public E intern(E sample) {
/*  47 */           E canonical = (E)map.putIfAbsent(Preconditions.checkNotNull(sample), sample);
/*  48 */           return (canonical == null) ? sample : canonical;
/*     */         }
/*     */       };
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
/*  61 */   public static <E> Interner<E> newWeakInterner() { return new WeakInterner(null); }
/*     */   
/*     */   private static class WeakInterner<E>
/*     */     extends Object implements Interner<E> {
/*  65 */     private final ConcurrentMap<InternReference, InternReference> map = (new MapMaker()).makeMap();
/*     */     
/*     */     public E intern(final E sample) {
/*     */       E canonical;
/*  69 */       final int hashCode = sample.hashCode();
/*     */ 
/*     */ 
/*     */       
/*  73 */       Object fakeReference = new Object()
/*     */         {
/*  75 */           public int hashCode() { return hashCode; }
/*     */           
/*     */           public boolean equals(Object object) {
/*  78 */             if (object.hashCode() != hashCode) {
/*  79 */               return false;
/*     */             }
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
/*  92 */             Interners.WeakInterner<E>.InternReference that = (Interners.WeakInterner.InternReference)object;
/*  93 */             return sample.equals(that.get());
/*     */           }
/*     */         };
/*     */ 
/*     */       
/*  98 */       InternReference existingRef = (InternReference)this.map.get(fakeReference);
/*  99 */       if (existingRef != null) {
/* 100 */         E canonical = (E)existingRef.get();
/* 101 */         if (canonical != null) {
/* 102 */           return canonical;
/*     */         }
/*     */       } 
/*     */       
/* 106 */       InternReference newRef = new InternReference(sample, hashCode);
/*     */       do {
/* 108 */         InternReference sneakyRef = (InternReference)this.map.putIfAbsent(newRef, newRef);
/* 109 */         if (sneakyRef == null) {
/* 110 */           return sample;
/*     */         }
/* 112 */         canonical = (E)sneakyRef.get();
/* 113 */       } while (canonical == null);
/* 114 */       return canonical;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     private static final FinalizableReferenceQueue frq = new FinalizableReferenceQueue();
/*     */     
/*     */     private WeakInterner() {}
/*     */     
/*     */     class InternReference
/*     */       extends FinalizableWeakReference<E> {
/*     */       InternReference(E key, int hash) {
/* 127 */         super(key, frq);
/* 128 */         this.hashCode = hash;
/*     */       }
/*     */       final int hashCode;
/* 131 */       public void finalizeReferent() { Interners.WeakInterner.this.map.remove(this); }
/*     */       
/*     */       public E get() {
/* 134 */         E referent = (E)super.get();
/* 135 */         if (referent == null) {
/* 136 */           finalizeReferent();
/*     */         }
/* 138 */         return referent;
/*     */       }
/*     */       
/* 141 */       public int hashCode() { return this.hashCode; }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean equals(Object object) {
/* 146 */         if (object == this) {
/* 147 */           return true;
/*     */         }
/* 149 */         if (object instanceof InternReference) {
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
/* 163 */           InternReference that = (InternReference)object;
/*     */           
/* 165 */           if (that.hashCode != this.hashCode) {
/* 166 */             return false;
/*     */           }
/* 168 */           E referent = (E)super.get();
/* 169 */           return (referent != null && referent.equals(that.get()));
/*     */         } 
/* 171 */         return object.equals(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\Interners.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */