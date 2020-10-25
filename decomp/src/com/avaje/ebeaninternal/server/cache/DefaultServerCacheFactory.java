/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.cache.ServerCache;
/*    */ import com.avaje.ebean.cache.ServerCacheFactory;
/*    */ import com.avaje.ebean.cache.ServerCacheOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultServerCacheFactory
/*    */   implements ServerCacheFactory
/*    */ {
/*    */   private EbeanServer ebeanServer;
/*    */   
/* 17 */   public void init(EbeanServer ebeanServer) { this.ebeanServer = ebeanServer; }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerCache createCache(Class<?> beanType, ServerCacheOptions cacheOptions) {
/* 22 */     ServerCache cache = new DefaultServerCache("Bean:" + beanType, cacheOptions);
/* 23 */     cache.init(this.ebeanServer);
/* 24 */     return cache;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\cache\DefaultServerCacheFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */