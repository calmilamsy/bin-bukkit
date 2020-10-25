package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.ArrayList;

public interface SpiExpressionList<T> extends ExpressionList<T> {
  boolean isLuceneResolvable(LuceneResolvableRequest paramLuceneResolvableRequest);
  
  SpiLuceneExpr createLuceneExpr(SpiExpressionRequest paramSpiExpressionRequest, SpiLuceneExpr.ExprOccur paramExprOccur);
  
  void trimPath(int paramInt);
  
  void setExpressionFactory(ExpressionFactory paramExpressionFactory);
  
  void containsMany(BeanDescriptor<?> paramBeanDescriptor, ManyWhereJoins paramManyWhereJoins);
  
  boolean isEmpty();
  
  String buildSql(SpiExpressionRequest paramSpiExpressionRequest);
  
  ArrayList<Object> buildBindValues(SpiExpressionRequest paramSpiExpressionRequest);
  
  int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */