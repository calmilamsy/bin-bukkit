package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;

public interface SpiExpressionFactory extends ExpressionFactory {
  ExpressionFactory createExpressionFactory(FilterExprPath paramFilterExprPath);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */