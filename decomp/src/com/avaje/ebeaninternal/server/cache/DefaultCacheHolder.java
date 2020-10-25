/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.annotation.CacheTuning;
/*     */ import com.avaje.ebean.cache.ServerCache;
/*     */ import com.avaje.ebean.cache.ServerCacheFactory;
/*     */ import com.avaje.ebean.cache.ServerCacheOptions;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class DefaultCacheHolder {
/*     */   private final ConcurrentHashMap<Class<?>, ServerCache> concMap;
/*     */   private final HashMap<Class<?>, ServerCache> synchMap;
/*     */   private final Object monitor;
/*     */   
/*     */   public DefaultCacheHolder(ServerCacheFactory cacheFactory, ServerCacheOptions defaultOptions, boolean useBeanTuning) {
/*  17 */     this.concMap = new ConcurrentHashMap();
/*     */     
/*  19 */     this.synchMap = new HashMap();
/*     */     
/*  21 */     this.monitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     this.cacheFactory = cacheFactory;
/*  44 */     this.defaultOptions = defaultOptions;
/*  45 */     this.useBeanTuning = useBeanTuning;
/*     */   }
/*     */   
/*     */   private final ServerCacheFactory cacheFactory;
/*     */   private final ServerCacheOptions defaultOptions;
/*     */   private final boolean useBeanTuning;
/*     */   
/*  52 */   public ServerCacheOptions getDefaultOptions() { return this.defaultOptions; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCache getCache(Class<?> beanType) {
/*  60 */     ServerCache cache = (ServerCache)this.concMap.get(beanType);
/*  61 */     if (cache != null) {
/*  62 */       return cache;
/*     */     }
/*  64 */     synchronized (this.monitor) {
/*  65 */       cache = (ServerCache)this.synchMap.get(beanType);
/*  66 */       if (cache == null) {
/*  67 */         ServerCacheOptions options = getCacheOptions(beanType);
/*  68 */         cache = this.cacheFactory.createCache(beanType, options);
/*  69 */         this.synchMap.put(beanType, cache);
/*  70 */         this.concMap.put(beanType, cache);
/*     */       } 
/*  72 */       return cache;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public boolean isCaching(Class<?> beanType) { return this.concMap.containsKey(beanType); }
/*     */ 
/*     */   
/*     */   public void clearAll() {
/*  84 */     Iterator<ServerCache> it = this.concMap.values().iterator();
/*  85 */     while (it.hasNext()) {
/*  86 */       ServerCache serverCache = (ServerCache)it.next();
/*  87 */       serverCache.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerCacheOptions getCacheOptions(Class<?> beanType) {
/*  96 */     if (this.useBeanTuning) {
/*     */       
/*  98 */       CacheTuning cacheTuning = (CacheTuning)beanType.getAnnotation(CacheTuning.class);
/*  99 */       if (cacheTuning != null) {
/* 100 */         ServerCacheOptions o = new ServerCacheOptions(cacheTuning);
/* 101 */         o.applyDefaults(this.defaultOptions);
/* 102 */         return o;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return this.defaultOptions.copy();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cache\DefaultCacheHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */