package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SpiOrmQueryRequest<T> {
  SpiQuery<T> getQuery();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  void initTransIfRequired();
  
  void endTransIfRequired();
  
  void rollbackTransIfRequired();
  
  Object findId();
  
  int findRowCount();
  
  List<Object> findIds();
  
  void findVisit(QueryResultVisitor<T> paramQueryResultVisitor);
  
  QueryIterator<T> findIterate();
  
  List<T> findList();
  
  Set<?> findSet();
  
  Map<?, ?> findMap();
  
  T getFromPersistenceContextOrCache();
  
  BeanCollection<T> getFromQueryCache();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\SpiOrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */