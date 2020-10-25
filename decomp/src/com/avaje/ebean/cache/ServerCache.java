package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCache {
  void init(EbeanServer paramEbeanServer);
  
  ServerCacheOptions getOptions();
  
  void setOptions(ServerCacheOptions paramServerCacheOptions);
  
  Object get(Object paramObject);
  
  Object put(Object paramObject1, Object paramObject2);
  
  Object putIfAbsent(Object paramObject1, Object paramObject2);
  
  Object remove(Object paramObject);
  
  void clear();
  
  int size();
  
  int getHitRatio();
  
  ServerCacheStatistics getStatistics(boolean paramBoolean);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\cache\ServerCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */