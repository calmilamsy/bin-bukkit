package com.avaje.ebean.event;

import java.util.Set;

public interface BeanPersistController {
  int getExecutionOrder();
  
  boolean isRegisterFor(Class<?> paramClass);
  
  boolean preInsert(BeanPersistRequest<?> paramBeanPersistRequest);
  
  boolean preUpdate(BeanPersistRequest<?> paramBeanPersistRequest);
  
  boolean preDelete(BeanPersistRequest<?> paramBeanPersistRequest);
  
  void postInsert(BeanPersistRequest<?> paramBeanPersistRequest);
  
  void postUpdate(BeanPersistRequest<?> paramBeanPersistRequest);
  
  void postDelete(BeanPersistRequest<?> paramBeanPersistRequest);
  
  void postLoad(Object paramObject, Set<String> paramSet);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanPersistController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */