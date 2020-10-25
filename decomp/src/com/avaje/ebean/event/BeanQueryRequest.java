package com.avaje.ebean.event;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;

public interface BeanQueryRequest<T> {
  EbeanServer getEbeanServer();
  
  Transaction getTransaction();
  
  Query<T> getQuery();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */