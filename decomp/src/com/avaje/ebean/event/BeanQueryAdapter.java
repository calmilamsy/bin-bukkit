package com.avaje.ebean.event;

public interface BeanQueryAdapter {
  boolean isRegisterFor(Class<?> paramClass);
  
  int getExecutionOrder();
  
  void preQuery(BeanQueryRequest<?> paramBeanQueryRequest);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanQueryAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */