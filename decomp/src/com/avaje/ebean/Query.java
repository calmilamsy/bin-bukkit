/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Query<T>
/*     */   extends Serializable
/*     */ {
/*     */   Query<T> setUseIndex(UseIndex paramUseIndex);
/*     */   
/*     */   UseIndex getUseIndex();
/*     */   
/*     */   Type getType();
/*     */   
/*     */   RawSql getRawSql();
/*     */   
/*     */   Query<T> setRawSql(RawSql paramRawSql);
/*     */   
/*     */   void cancel();
/*     */   
/*     */   Query<T> copy();
/*     */   
/*     */   ExpressionFactory getExpressionFactory();
/*     */   
/*     */   boolean isAutofetchTuned();
/*     */   
/*     */   Query<T> setAutofetch(boolean paramBoolean);
/*     */   
/*     */   Query<T> setQuery(String paramString);
/*     */   
/*     */   Query<T> select(String paramString);
/*     */   
/*     */   Query<T> fetch(String paramString1, String paramString2);
/*     */   
/*     */   Query<T> join(String paramString1, String paramString2);
/*     */   
/*     */   Query<T> fetch(String paramString1, String paramString2, FetchConfig paramFetchConfig);
/*     */   
/*     */   Query<T> join(String paramString1, String paramString2, JoinConfig paramJoinConfig);
/*     */   
/*     */   Query<T> fetch(String paramString);
/*     */   
/*     */   Query<T> join(String paramString);
/*     */   
/*     */   Query<T> fetch(String paramString, FetchConfig paramFetchConfig);
/*     */   
/*     */   Query<T> join(String paramString, JoinConfig paramJoinConfig);
/*     */   
/*     */   List<Object> findIds();
/*     */   
/*     */   QueryIterator<T> findIterate();
/*     */   
/*     */   void findVisit(QueryResultVisitor<T> paramQueryResultVisitor);
/*     */   
/*     */   List<T> findList();
/*     */   
/*     */   Set<T> findSet();
/*     */   
/*     */   Map<?, T> findMap();
/*     */   
/*     */   <K> Map<K, T> findMap(String paramString, Class<K> paramClass);
/*     */   
/*     */   T findUnique();
/*     */   
/*     */   int findRowCount();
/*     */   
/*     */   FutureRowCount<T> findFutureRowCount();
/*     */   
/*     */   FutureIds<T> findFutureIds();
/*     */   
/*     */   FutureList<T> findFutureList();
/*     */   
/*     */   PagingList<T> findPagingList(int paramInt);
/*     */   
/*     */   Query<T> setParameter(String paramString, Object paramObject);
/*     */   
/*     */   Query<T> setParameter(int paramInt, Object paramObject);
/*     */   
/*     */   Query<T> setListener(QueryListener<T> paramQueryListener);
/*     */   
/*     */   Query<T> setId(Object paramObject);
/*     */   
/*     */   Query<T> where(String paramString);
/*     */   
/*     */   Query<T> where(Expression paramExpression);
/*     */   
/*     */   ExpressionList<T> where();
/*     */   
/*     */   ExpressionList<T> filterMany(String paramString);
/*     */   
/*     */   ExpressionList<T> having();
/*     */   
/*     */   Query<T> having(String paramString);
/*     */   
/*     */   Query<T> having(Expression paramExpression);
/*     */   
/*     */   Query<T> orderBy(String paramString);
/*     */   
/*     */   Query<T> order(String paramString);
/*     */   
/*     */   OrderBy<T> order();
/*     */   
/*     */   OrderBy<T> orderBy();
/*     */   
/*     */   Query<T> setOrder(OrderBy<T> paramOrderBy);
/*     */   
/*     */   Query<T> setOrderBy(OrderBy<T> paramOrderBy);
/*     */   
/*     */   Query<T> setDistinct(boolean paramBoolean);
/*     */   
/*     */   Query<T> setVanillaMode(boolean paramBoolean);
/*     */   
/*     */   int getFirstRow();
/*     */   
/*     */   Query<T> setFirstRow(int paramInt);
/*     */   
/*     */   int getMaxRows();
/*     */   
/*     */   Query<T> setMaxRows(int paramInt);
/*     */   
/*     */   Query<T> setBackgroundFetchAfter(int paramInt);
/*     */   
/*     */   Query<T> setMapKey(String paramString);
/*     */   
/*     */   Query<T> setUseCache(boolean paramBoolean);
/*     */   
/*     */   Query<T> setUseQueryCache(boolean paramBoolean);
/*     */   
/*     */   Query<T> setReadOnly(boolean paramBoolean);
/*     */   
/*     */   Query<T> setLoadBeanCache(boolean paramBoolean);
/*     */   
/*     */   Query<T> setTimeout(int paramInt);
/*     */   
/*     */   Query<T> setBufferFetchSizeHint(int paramInt);
/*     */   
/*     */   String getGeneratedSql();
/*     */   
/*     */   public enum UseIndex
/*     */   {
/* 298 */     NO, DEFAULT, YES_IDS, YES_OBJECTS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/* 309 */     BEAN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     LIST,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     SET,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     MAP,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     ID_LIST,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     ROWCOUNT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     SUBQUERY;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Query.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */