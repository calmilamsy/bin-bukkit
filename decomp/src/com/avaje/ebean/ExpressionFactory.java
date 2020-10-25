package com.avaje.ebean;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ExpressionFactory {
  String getLang();
  
  Expression eq(String paramString, Object paramObject);
  
  Expression ne(String paramString, Object paramObject);
  
  Expression ieq(String paramString1, String paramString2);
  
  Expression between(String paramString, Object paramObject1, Object paramObject2);
  
  Expression betweenProperties(String paramString1, String paramString2, Object paramObject);
  
  Expression gt(String paramString, Object paramObject);
  
  Expression ge(String paramString, Object paramObject);
  
  Expression lt(String paramString, Object paramObject);
  
  Expression le(String paramString, Object paramObject);
  
  Expression isNull(String paramString);
  
  Expression isNotNull(String paramString);
  
  ExampleExpression iexampleLike(Object paramObject);
  
  ExampleExpression exampleLike(Object paramObject);
  
  ExampleExpression exampleLike(Object paramObject, boolean paramBoolean, LikeType paramLikeType);
  
  Expression like(String paramString1, String paramString2);
  
  Expression ilike(String paramString1, String paramString2);
  
  Expression startsWith(String paramString1, String paramString2);
  
  Expression istartsWith(String paramString1, String paramString2);
  
  Expression endsWith(String paramString1, String paramString2);
  
  Expression iendsWith(String paramString1, String paramString2);
  
  Expression contains(String paramString1, String paramString2);
  
  Expression lucene(String paramString);
  
  Expression lucene(String paramString1, String paramString2);
  
  Expression icontains(String paramString1, String paramString2);
  
  Expression in(String paramString, Object[] paramArrayOfObject);
  
  Expression in(String paramString, Query<?> paramQuery);
  
  Expression in(String paramString, Collection<?> paramCollection);
  
  Expression idEq(Object paramObject);
  
  Expression idIn(List<?> paramList);
  
  Expression allEq(Map<String, Object> paramMap);
  
  Expression raw(String paramString, Object paramObject);
  
  Expression raw(String paramString, Object[] paramArrayOfObject);
  
  Expression raw(String paramString);
  
  Expression and(Expression paramExpression1, Expression paramExpression2);
  
  Expression or(Expression paramExpression1, Expression paramExpression2);
  
  Expression not(Expression paramExpression);
  
  <T> Junction<T> conjunction(Query<T> paramQuery);
  
  <T> Junction<T> disjunction(Query<T> paramQuery);
  
  <T> Junction<T> conjunction(Query<T> paramQuery, ExpressionList<T> paramExpressionList);
  
  <T> Junction<T> disjunction(Query<T> paramQuery, ExpressionList<T> paramExpressionList);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\ExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */