/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.primitives.Primitives;
/*     */ import java.util.Map;
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
/*     */ public final class ImmutableClassToInstanceMap<B>
/*     */   extends ForwardingMap<Class<? extends B>, B>
/*     */   implements ClassToInstanceMap<B>
/*     */ {
/*     */   private final ImmutableMap<Class<? extends B>, B> delegate;
/*     */   
/*  37 */   public static <B> Builder<B> builder() { return new Builder(); }
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
/*     */   public static final class Builder<B>
/*     */     extends Object
/*     */   {
/*  56 */     private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T extends B> Builder<B> put(Class<T> type, T value) {
/*  64 */       this.mapBuilder.put(type, value);
/*  65 */       return this;
/*     */     }
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
/*     */     public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
/*  79 */       for (Map.Entry<? extends Class<? extends T>, ? extends T> entry : map.entrySet()) {
/*  80 */         Class<? extends T> type = (Class)entry.getKey();
/*  81 */         T value = (T)entry.getValue();
/*  82 */         this.mapBuilder.put(type, cast(type, value));
/*     */       } 
/*  84 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  88 */     private static <B, T extends B> T cast(Class<T> type, B value) { return (T)Primitives.wrap(type).cast(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     public ImmutableClassToInstanceMap<B> build() { return new ImmutableClassToInstanceMap(this.mapBuilder.build(), null); }
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
/*     */   public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
/* 118 */     if (map instanceof ImmutableClassToInstanceMap) {
/* 119 */       return (ImmutableClassToInstanceMap)map;
/*     */     }
/* 121 */     return (new Builder()).putAll(map).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> delegate) { this.delegate = delegate; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   protected Map<Class<? extends B>, B> delegate() { return this.delegate; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public <T extends B> T getInstance(Class<T> type) { return (T)this.delegate.get(type); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public <T extends B> T putInstance(Class<T> type, T value) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\ImmutableClassToInstanceMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */