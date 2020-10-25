package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface LoadBeanContext extends LoadSecondaryQuery {
  void configureQuery(SpiQuery<?> paramSpiQuery, String paramString);
  
  String getFullPath();
  
  PersistenceContext getPersistenceContext();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  int getBatchSize();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadBeanContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */