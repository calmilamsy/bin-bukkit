package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.util.ArrayList;

public interface SpiExpressionRequest {
  LIndex getLuceneIndex();
  
  String parseDeploy(String paramString);
  
  BeanDescriptor<?> getBeanDescriptor();
  
  SpiOrmQueryRequest<?> getQueryRequest();
  
  SpiExpressionRequest append(String paramString);
  
  void addBindValue(Object paramObject);
  
  String getSql();
  
  ArrayList<Object> getBindValues();
  
  int nextParameter();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiExpressionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */