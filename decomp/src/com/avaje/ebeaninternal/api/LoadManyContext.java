package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public interface LoadManyContext extends LoadSecondaryQuery {
  void configureQuery(SpiQuery<?> paramSpiQuery);
  
  String getFullPath();
  
  ObjectGraphNode getObjectGraphNode();
  
  PersistenceContext getPersistenceContext();
  
  int getBatchSize();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  BeanPropertyAssocMany<?> getBeanProperty();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadManyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */