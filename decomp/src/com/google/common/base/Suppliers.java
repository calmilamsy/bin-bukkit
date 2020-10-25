/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nullable;
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
/*     */ public final class Suppliers
/*     */ {
/*     */   public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
/*  49 */     Preconditions.checkNotNull(function);
/*  50 */     Preconditions.checkNotNull(supplier);
/*  51 */     return new SupplierComposition(function, supplier);
/*     */   }
/*     */   
/*     */   private static class SupplierComposition<F, T> extends Object implements Supplier<T>, Serializable {
/*     */     final Function<? super F, T> function;
/*     */     final Supplier<F> supplier;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
/*  60 */       this.function = function;
/*  61 */       this.supplier = supplier;
/*     */     }
/*     */     
/*  64 */     public T get() { return (T)this.function.apply(this.supplier.get()); }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static <T> Supplier<T> memoize(Supplier<T> delegate) { return new MemoizingSupplier((Supplier)Preconditions.checkNotNull(delegate)); }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class MemoizingSupplier<T>
/*     */     extends Object implements Supplier<T>, Serializable {
/*     */     final Supplier<T> delegate;
/*     */     boolean initialized;
/*     */     T value;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*  90 */     MemoizingSupplier(Supplier<T> delegate) { this.delegate = delegate; }
/*     */ 
/*     */     
/*     */     public T get() {
/*  94 */       if (!this.initialized) {
/*  95 */         this.value = this.delegate.get();
/*  96 */         this.initialized = true;
/*     */       } 
/*  98 */       return (T)this.value;
/*     */     }
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
/*     */   @Beta
/* 125 */   public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> delegate, long duration, TimeUnit unit) { return new ExpiringMemoizingSupplier(delegate, duration, unit); }
/*     */   
/*     */   @VisibleForTesting
/*     */   static class ExpiringMemoizingSupplier<T>
/*     */     extends Object implements Supplier<T>, Serializable {
/*     */     final Supplier<T> delegate;
/*     */     final long durationNanos;
/*     */     boolean initialized;
/*     */     T value;
/*     */     long expirationNanos;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ExpiringMemoizingSupplier(Supplier<T> delegate, long duration, TimeUnit unit) {
/* 138 */       this.delegate = (Supplier)Preconditions.checkNotNull(delegate);
/* 139 */       this.durationNanos = unit.toNanos(duration);
/* 140 */       Preconditions.checkArgument((duration > 0L));
/*     */     }
/*     */     
/*     */     public T get() {
/* 144 */       if (!this.initialized || System.nanoTime() - this.expirationNanos >= 0L) {
/* 145 */         this.value = this.delegate.get();
/* 146 */         this.initialized = true;
/* 147 */         this.expirationNanos = System.nanoTime() + this.durationNanos;
/*     */       } 
/* 149 */       return (T)this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static <T> Supplier<T> ofInstance(@Nullable T instance) { return new SupplierOfInstance(instance); }
/*     */   
/*     */   private static class SupplierOfInstance<T>
/*     */     extends Object
/*     */     implements Supplier<T>, Serializable {
/*     */     final T instance;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 167 */     SupplierOfInstance(T instance) { this.instance = instance; }
/*     */ 
/*     */     
/* 170 */     public T get() { return (T)this.instance; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public static <T> Supplier<T> synchronizedSupplier(Supplier<T> delegate) { return new ThreadSafeSupplier((Supplier)Preconditions.checkNotNull(delegate)); }
/*     */   
/*     */   private static class ThreadSafeSupplier<T>
/*     */     extends Object
/*     */     implements Supplier<T>, Serializable {
/*     */     final Supplier<T> delegate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 188 */     ThreadSafeSupplier(Supplier<T> delegate) { this.delegate = delegate; }
/*     */     
/*     */     public T get() {
/* 191 */       synchronized (this.delegate) {
/* 192 */         return (T)this.delegate.get();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Suppliers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */