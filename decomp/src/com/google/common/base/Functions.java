/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ @GwtCompatible
/*     */ public final class Functions
/*     */ {
/*  49 */   public static Function<Object, String> toStringFunction() { return ToStringFunction.INSTANCE; }
/*     */   
/*     */   private enum ToStringFunction
/*     */     implements Function<Object, String>
/*     */   {
/*  54 */     INSTANCE;
/*     */ 
/*     */     
/*  57 */     public String apply(Object o) { return o.toString(); }
/*     */ 
/*     */ 
/*     */     
/*  61 */     public String toString() { return "toString"; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static <E> Function<E, E> identity() { return IdentityFunction.INSTANCE; }
/*     */   
/*     */   private enum IdentityFunction
/*     */     implements Function<Object, Object>
/*     */   {
/*  75 */     INSTANCE;
/*     */ 
/*     */     
/*  78 */     public Object apply(Object o) { return o; }
/*     */ 
/*     */ 
/*     */     
/*  82 */     public String toString() { return "identity"; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static <K, V> Function<K, V> forMap(Map<K, V> map) { return new FunctionForMapNoDefault(map); }
/*     */   
/*     */   private static class FunctionForMapNoDefault<K, V>
/*     */     extends Object
/*     */     implements Function<K, V>, Serializable {
/*     */     final Map<K, V> map;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 100 */     FunctionForMapNoDefault(Map<K, V> map) { this.map = (Map)Preconditions.checkNotNull(map); }
/*     */     
/*     */     public V apply(K key) {
/* 103 */       V result = (V)this.map.get(key);
/* 104 */       Preconditions.checkArgument((result != null || this.map.containsKey(key)), "Key '%s' not present in map", new Object[] { key });
/*     */       
/* 106 */       return result;
/*     */     }
/*     */     public boolean equals(@Nullable Object o) {
/* 109 */       if (o instanceof FunctionForMapNoDefault) {
/* 110 */         FunctionForMapNoDefault<?, ?> that = (FunctionForMapNoDefault)o;
/* 111 */         return this.map.equals(that.map);
/*     */       } 
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     public int hashCode() { return this.map.hashCode(); }
/*     */ 
/*     */     
/* 119 */     public String toString() { return "forMap(" + this.map + ")"; }
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
/* 136 */   public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V defaultValue) { return new ForMapWithDefault(map, defaultValue); }
/*     */   
/*     */   private static class ForMapWithDefault<K, V>
/*     */     extends Object implements Function<K, V>, Serializable {
/*     */     final Map<K, ? extends V> map;
/*     */     final V defaultValue;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     ForMapWithDefault(Map<K, ? extends V> map, V defaultValue) {
/* 145 */       this.map = (Map)Preconditions.checkNotNull(map);
/* 146 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/* 149 */     public V apply(K key) { return (V)(this.map.containsKey(key) ? this.map.get(key) : this.defaultValue); }
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 152 */       if (o instanceof ForMapWithDefault) {
/* 153 */         ForMapWithDefault<?, ?> that = (ForMapWithDefault)o;
/* 154 */         return (this.map.equals(that.map) && Objects.equal(this.defaultValue, that.defaultValue));
/*     */       } 
/*     */       
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     public int hashCode() { return Objects.hashCode(new Object[] { this.map, this.defaultValue }); }
/*     */ 
/*     */     
/* 163 */     public String toString() { return "forMap(" + this.map + ", defaultValue=" + this.defaultValue + ")"; }
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
/* 182 */   public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) { return new FunctionComposition(g, f); }
/*     */   
/*     */   private static class FunctionComposition<A, B, C>
/*     */     extends Object
/*     */     implements Function<A, C>, Serializable {
/*     */     private final Function<B, C> g;
/*     */     private final Function<A, ? extends B> f;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
/* 192 */       this.g = (Function)Preconditions.checkNotNull(g);
/* 193 */       this.f = (Function)Preconditions.checkNotNull(f);
/*     */     }
/*     */     
/* 196 */     public C apply(A a) { return (C)this.g.apply(this.f.apply(a)); }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 199 */       if (obj instanceof FunctionComposition) {
/* 200 */         FunctionComposition<?, ?, ?> that = (FunctionComposition)obj;
/* 201 */         return (this.f.equals(that.f) && this.g.equals(that.g));
/*     */       } 
/* 203 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 207 */     public int hashCode() { return this.f.hashCode() ^ this.g.hashCode(); }
/*     */ 
/*     */     
/* 210 */     public String toString() { return this.g.toString() + "(" + this.f.toString() + ")"; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) { return new PredicateFunction(predicate, null); }
/*     */   
/*     */   private static class PredicateFunction<T>
/*     */     extends Object
/*     */     implements Function<T, Boolean>, Serializable
/*     */   {
/*     */     private final Predicate<T> predicate;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 229 */     private PredicateFunction(Predicate<T> predicate) { this.predicate = (Predicate)Preconditions.checkNotNull(predicate); }
/*     */ 
/*     */ 
/*     */     
/* 233 */     public Boolean apply(T t) { return Boolean.valueOf(this.predicate.apply(t)); }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 236 */       if (obj instanceof PredicateFunction) {
/* 237 */         PredicateFunction<?> that = (PredicateFunction)obj;
/* 238 */         return this.predicate.equals(that.predicate);
/*     */       } 
/* 240 */       return false;
/*     */     }
/*     */     
/* 243 */     public int hashCode() { return this.predicate.hashCode(); }
/*     */ 
/*     */     
/* 246 */     public String toString() { return "forPredicate(" + this.predicate + ")"; }
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
/* 258 */   public static <E> Function<Object, E> constant(@Nullable E value) { return new ConstantFunction(value); }
/*     */   
/*     */   private static class ConstantFunction<E>
/*     */     extends Object
/*     */     implements Function<Object, E>, Serializable {
/*     */     private final E value;
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/* 266 */     public ConstantFunction(@Nullable E value) { this.value = value; }
/*     */ 
/*     */     
/* 269 */     public E apply(@Nullable Object from) { return (E)this.value; }
/*     */     
/*     */     public boolean equals(@Nullable Object obj) {
/* 272 */       if (obj instanceof ConstantFunction) {
/* 273 */         ConstantFunction<?> that = (ConstantFunction)obj;
/* 274 */         return Objects.equal(this.value, that.value);
/*     */       } 
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     public int hashCode() { return (this.value == null) ? 0 : this.value.hashCode(); }
/*     */ 
/*     */     
/* 282 */     public String toString() { return "constant(" + this.value + ")"; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Functions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */