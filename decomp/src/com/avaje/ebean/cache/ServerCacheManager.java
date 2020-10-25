package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheManager {
  void init(EbeanServer paramEbeanServer);
  
  boolean isBeanCaching(Class<?> paramClass);
  
  ServerCache getBeanCache(Class<?> paramClass);
  
  ServerCache getQueryCache(Class<?> paramClass);
  
  void clear(Class<?> paramClass);
  
  void clearAll();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\cache\ServerCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */