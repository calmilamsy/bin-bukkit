package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheFactory {
  void init(EbeanServer paramEbeanServer);
  
  ServerCache createCache(Class<?> paramClass, ServerCacheOptions paramServerCacheOptions);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\cache\ServerCacheFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */