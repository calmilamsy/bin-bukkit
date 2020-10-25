package com.avaje.ebean.bean;

public interface PersistenceContext {
  void put(Object paramObject1, Object paramObject2);
  
  Object putIfAbsent(Object paramObject1, Object paramObject2);
  
  Object get(Class<?> paramClass, Object paramObject);
  
  void clear();
  
  void clear(Class<?> paramClass);
  
  void clear(Class<?> paramClass, Object paramObject);
  
  int size(Class<?> paramClass);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\PersistenceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */