package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Expression;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

public interface SpiExpression extends Expression {
  boolean isLuceneResolvable(LuceneResolvableRequest paramLuceneResolvableRequest);
  
  SpiLuceneExpr createLuceneExpr(SpiExpressionRequest paramSpiExpressionRequest);
  
  void containsMany(BeanDescriptor<?> paramBeanDescriptor, ManyWhereJoins paramManyWhereJoins);
  
  int queryAutoFetchHash();
  
  int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
  
  int queryBindHash();
  
  void addSql(SpiExpressionRequest paramSpiExpressionRequest);
  
  void addBindValues(SpiExpressionRequest paramSpiExpressionRequest);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */