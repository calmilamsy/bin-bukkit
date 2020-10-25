package com.avaje.ebean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ExpressionList<T> extends Serializable {
  Query<T> query();
  
  Query<T> order(String paramString);
  
  OrderBy<T> order();
  
  OrderBy<T> orderBy();
  
  Query<T> orderBy(String paramString);
  
  Query<T> setOrderBy(String paramString);
  
  QueryIterator<T> findIterate();
  
  void findVisit(QueryResultVisitor<T> paramQueryResultVisitor);
  
  List<T> findList();
  
  List<Object> findIds();
  
  int findRowCount();
  
  Set<T> findSet();
  
  Map<?, T> findMap();
  
  <K> Map<K, T> findMap(String paramString, Class<K> paramClass);
  
  T findUnique();
  
  FutureRowCount<T> findFutureRowCount();
  
  FutureIds<T> findFutureIds();
  
  FutureList<T> findFutureList();
  
  PagingList<T> findPagingList(int paramInt);
  
  ExpressionList<T> filterMany(String paramString);
  
  Query<T> select(String paramString);
  
  Query<T> join(String paramString);
  
  Query<T> join(String paramString1, String paramString2);
  
  Query<T> setFirstRow(int paramInt);
  
  Query<T> setMaxRows(int paramInt);
  
  Query<T> setBackgroundFetchAfter(int paramInt);
  
  Query<T> setMapKey(String paramString);
  
  Query<T> setListener(QueryListener<T> paramQueryListener);
  
  Query<T> setUseCache(boolean paramBoolean);
  
  ExpressionList<T> having();
  
  ExpressionList<T> where();
  
  ExpressionList<T> add(Expression paramExpression);
  
  ExpressionList<T> eq(String paramString, Object paramObject);
  
  ExpressionList<T> ne(String paramString, Object paramObject);
  
  ExpressionList<T> ieq(String paramString1, String paramString2);
  
  ExpressionList<T> between(String paramString, Object paramObject1, Object paramObject2);
  
  ExpressionList<T> betweenProperties(String paramString1, String paramString2, Object paramObject);
  
  ExpressionList<T> gt(String paramString, Object paramObject);
  
  ExpressionList<T> ge(String paramString, Object paramObject);
  
  ExpressionList<T> lt(String paramString, Object paramObject);
  
  ExpressionList<T> le(String paramString, Object paramObject);
  
  ExpressionList<T> isNull(String paramString);
  
  ExpressionList<T> isNotNull(String paramString);
  
  ExpressionList<T> exampleLike(Object paramObject);
  
  ExpressionList<T> iexampleLike(Object paramObject);
  
  ExpressionList<T> like(String paramString1, String paramString2);
  
  ExpressionList<T> ilike(String paramString1, String paramString2);
  
  ExpressionList<T> startsWith(String paramString1, String paramString2);
  
  ExpressionList<T> istartsWith(String paramString1, String paramString2);
  
  ExpressionList<T> endsWith(String paramString1, String paramString2);
  
  ExpressionList<T> iendsWith(String paramString1, String paramString2);
  
  ExpressionList<T> contains(String paramString1, String paramString2);
  
  ExpressionList<T> lucene(String paramString1, String paramString2);
  
  ExpressionList<T> icontains(String paramString1, String paramString2);
  
  ExpressionList<T> in(String paramString, Query<?> paramQuery);
  
  ExpressionList<T> in(String paramString, Object... paramVarArgs);
  
  ExpressionList<T> in(String paramString, Collection<?> paramCollection);
  
  ExpressionList<T> idIn(List<?> paramList);
  
  ExpressionList<T> idEq(Object paramObject);
  
  ExpressionList<T> allEq(Map<String, Object> paramMap);
  
  ExpressionList<T> raw(String paramString, Object paramObject);
  
  ExpressionList<T> raw(String paramString, Object[] paramArrayOfObject);
  
  ExpressionList<T> raw(String paramString);
  
  ExpressionList<T> and(Expression paramExpression1, Expression paramExpression2);
  
  ExpressionList<T> or(Expression paramExpression1, Expression paramExpression2);
  
  ExpressionList<T> not(Expression paramExpression);
  
  Junction<T> conjunction();
  
  Junction<T> disjunction();
  
  ExpressionList<T> endJunction();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\ExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */