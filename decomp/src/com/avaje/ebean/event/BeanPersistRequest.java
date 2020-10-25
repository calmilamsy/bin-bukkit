package com.avaje.ebean.event;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import java.util.Set;

public interface BeanPersistRequest<T> {
  EbeanServer getEbeanServer();
  
  Transaction getTransaction();
  
  Set<String> getLoadedProperties();
  
  Set<String> getUpdatedProperties();
  
  T getBean();
  
  T getOldValues();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanPersistRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */