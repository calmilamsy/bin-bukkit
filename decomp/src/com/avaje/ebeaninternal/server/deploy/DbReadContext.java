package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.util.Map;

public interface DbReadContext {
  int getParentState();
  
  void propagateState(Object paramObject);
  
  DataReader getDataReader();
  
  boolean isVanillaMode();
  
  boolean isRawSql();
  
  ReferenceOptions getReferenceOptionsFor(BeanPropertyAssocOne<?> paramBeanPropertyAssocOne);
  
  void setCurrentPrefix(String paramString, Map<String, String> paramMap);
  
  boolean isAutoFetchProfiling();
  
  void profileBean(EntityBeanIntercept paramEntityBeanIntercept, String paramString);
  
  PersistenceContext getPersistenceContext();
  
  void register(String paramString, EntityBeanIntercept paramEntityBeanIntercept);
  
  void register(String paramString, BeanCollection<?> paramBeanCollection);
  
  BeanPropertyAssocMany<?> getManyProperty();
  
  void setLoadedBean(Object paramObject1, Object paramObject2);
  
  void setLoadedManyBean(Object paramObject);
  
  SpiQuery.Mode getQueryMode();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DbReadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */