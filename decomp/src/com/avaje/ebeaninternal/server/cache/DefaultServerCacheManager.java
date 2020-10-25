/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.cache.ServerCache;
/*    */ import com.avaje.ebean.cache.ServerCacheFactory;
/*    */ import com.avaje.ebean.cache.ServerCacheManager;
/*    */ import com.avaje.ebean.cache.ServerCacheOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultServerCacheManager
/*    */   implements ServerCacheManager
/*    */ {
/*    */   private final DefaultCacheHolder beanCache;
/*    */   private final DefaultCacheHolder queryCache;
/*    */   private final ServerCacheFactory cacheFactory;
/*    */   
/*    */   public DefaultServerCacheManager(ServerCacheFactory cacheFactory, ServerCacheOptions defaultBeanOptions, ServerCacheOptions defaultQueryOptions) {
/* 25 */     this.cacheFactory = cacheFactory;
/* 26 */     this.beanCache = new DefaultCacheHolder(cacheFactory, defaultBeanOptions, true);
/* 27 */     this.queryCache = new DefaultCacheHolder(cacheFactory, defaultQueryOptions, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void init(EbeanServer server) { this.cacheFactory.init(server); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear(Class<?> beanType) {
/* 40 */     if (isBeanCaching(beanType)) {
/* 41 */       getBeanCache(beanType).clear();
/*    */     }
/* 43 */     if (isQueryCaching(beanType)) {
/* 44 */       getQueryCache(beanType).clear();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearAll() {
/* 50 */     this.beanCache.clearAll();
/* 51 */     this.queryCache.clearAll();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public ServerCache getQueryCache(Class<?> beanType) { return this.queryCache.getCache(beanType); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public ServerCache getBeanCache(Class<?> beanType) { return this.beanCache.getCache(beanType); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public boolean isBeanCaching(Class<?> beanType) { return this.beanCache.isCaching(beanType); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public boolean isQueryCaching(Class<?> beanType) { return this.queryCache.isCaching(beanType); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cache\DefaultServerCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */