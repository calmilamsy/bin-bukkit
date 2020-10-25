/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanToDbMap<B, D>
/*     */   extends Object
/*     */ {
/*     */   final HashMap<B, D> keyMap;
/*     */   final HashMap<D, B> valueMap;
/*     */   final boolean allowNulls;
/*     */   
/*  48 */   public BeanToDbMap() { this(false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanToDbMap(boolean allowNulls) {
/*  60 */     this.allowNulls = allowNulls;
/*  61 */     this.keyMap = new HashMap();
/*  62 */     this.valueMap = new HashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanToDbMap<B, D> add(B beanValue, D dbValue) {
/*  69 */     this.keyMap.put(beanValue, dbValue);
/*  70 */     this.valueMap.put(dbValue, beanValue);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D getDbValue(B beanValue) {
/*  78 */     if (beanValue == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     D dbValue = (D)this.keyMap.get(beanValue);
/*  82 */     if (dbValue == null && !this.allowNulls) {
/*  83 */       String msg = "DB value for " + beanValue + " not found in " + this.valueMap;
/*  84 */       throw new IllegalArgumentException(msg);
/*     */     } 
/*  86 */     return dbValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B getBeanValue(D dbValue) {
/*  93 */     if (dbValue == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     B beanValue = (B)this.valueMap.get(dbValue);
/*  97 */     if (beanValue == null && !this.allowNulls) {
/*  98 */       String msg = "Bean value for " + dbValue + " not found in " + this.valueMap;
/*  99 */       throw new IllegalArgumentException(msg);
/*     */     } 
/* 101 */     return beanValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\BeanToDbMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */