package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public interface LoadContext {
  int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> paramOrmQueryRequest, int paramInt);
  
  void executeSecondaryQueries(OrmQueryRequest<?> paramOrmQueryRequest, int paramInt);
  
  void registerSecondaryQueries(SpiQuery<?> paramSpiQuery);
  
  boolean isUseAutofetchManager();
  
  ObjectGraphNode getObjectGraphNode(String paramString);
  
  int getParentState();
  
  PersistenceContext getPersistenceContext();
  
  void setPersistenceContext(PersistenceContext paramPersistenceContext);
  
  void register(String paramString, EntityBeanIntercept paramEntityBeanIntercept);
  
  void register(String paramString, BeanCollection<?> paramBeanCollection);
  
  LoadBeanContext getBeanContext(String paramString);
  
  LoadManyContext getManyContext(String paramString);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\LoadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */