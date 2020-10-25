/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImmutableCompoundTypeBuilder
/*     */ {
/*  38 */   private static ThreadLocal<ImmutableCompoundTypeBuilder> local = new ThreadLocal<ImmutableCompoundTypeBuilder>()
/*     */     {
/*  40 */       protected ImmutableCompoundTypeBuilder initialValue() { return new ImmutableCompoundTypeBuilder(); }
/*     */     };
/*     */ 
/*     */   
/*  44 */   private Map<Class<?>, Entry> entryMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static void clear() { ((ImmutableCompoundTypeBuilder)local.get()).entryMap.clear(); }
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
/*  62 */   public static Object set(CtCompoundType<?> ct, String propName, Object value) { return ((ImmutableCompoundTypeBuilder)local.get()).setValue(ct, propName, value); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object setValue(CtCompoundType<?> ct, String propName, Object value) {
/*  67 */     Entry e = getEntry(ct);
/*  68 */     Object compoundValue = e.set(propName, value);
/*  69 */     if (compoundValue != null) {
/*  70 */       removeEntry(ct);
/*     */     }
/*  72 */     return compoundValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private void removeEntry(CtCompoundType<?> ct) { this.entryMap.remove(ct.getCompoundTypeClass()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Entry getEntry(CtCompoundType<?> ct) {
/*  86 */     Entry e = (Entry)this.entryMap.get(ct.getCompoundTypeClass());
/*  87 */     if (e == null) {
/*  88 */       e = new Entry(ct, null);
/*  89 */       this.entryMap.put(ct.getCompoundTypeClass(), e);
/*     */     } 
/*  91 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Entry
/*     */   {
/*     */     private final CtCompoundType<?> ct;
/*     */     
/*     */     private final Map<String, Object> valueMap;
/*     */ 
/*     */     
/*     */     private Entry(CtCompoundType<?> ct) {
/* 104 */       this.ct = ct;
/* 105 */       this.valueMap = new HashMap();
/*     */     }
/*     */ 
/*     */     
/*     */     private Object set(String propName, Object value) {
/* 110 */       this.valueMap.put(propName, value);
/*     */ 
/*     */ 
/*     */       
/* 114 */       return this.ct.create(this.valueMap);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ImmutableCompoundTypeBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */