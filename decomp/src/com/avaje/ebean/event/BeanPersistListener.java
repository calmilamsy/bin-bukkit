package com.avaje.ebean.event;

import java.util.Set;

public interface BeanPersistListener<T> {
  boolean inserted(T paramT);
  
  boolean updated(T paramT, Set<String> paramSet);
  
  boolean deleted(T paramT);
  
  void remoteInsert(Object paramObject);
  
  void remoteUpdate(Object paramObject);
  
  void remoteDelete(Object paramObject);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanPersistListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */