package com.avaje.ebean.event;

import com.avaje.ebean.bean.BeanCollection;

public interface BeanFinder<T> {
  T find(BeanQueryRequest<T> paramBeanQueryRequest);
  
  BeanCollection<T> findMany(BeanQueryRequest<T> paramBeanQueryRequest);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */