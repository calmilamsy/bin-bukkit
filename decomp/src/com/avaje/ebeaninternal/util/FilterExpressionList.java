/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.FutureIds;
/*     */ import com.avaje.ebean.FutureList;
/*     */ import com.avaje.ebean.FutureRowCount;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebeaninternal.server.expression.FilterExprPath;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class FilterExpressionList<T>
/*     */   extends DefaultExpressionList<T>
/*     */ {
/*     */   private static final long serialVersionUID = 2226895827150099020L;
/*     */   private final Query<T> rootQuery;
/*     */   private final FilterExprPath pathPrefix;
/*     */   private String notAllowedMessage;
/*     */   
/*     */   public FilterExpressionList(FilterExprPath pathPrefix, ExpressionFactory expr, Query<T> rootQuery) {
/*  48 */     super(null, expr, null);
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
/*  61 */     this.notAllowedMessage = "This method is not allowed on a filter";
/*     */     this.pathPrefix = pathPrefix;
/*     */     this.rootQuery = rootQuery;
/*  64 */   } public ExpressionList<T> filterMany(String prop) { return this.rootQuery.filterMany(prop); }
/*     */   public void trimPath(int prefixTrim) { this.pathPrefix.trimPath(prefixTrim); }
/*     */   public FilterExprPath getPathPrefix() { return this.pathPrefix; }
/*     */   
/*  68 */   public FutureIds<T> findFutureIds() { return this.rootQuery.findFutureIds(); }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public FutureList<T> findFutureList() { return this.rootQuery.findFutureList(); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public FutureRowCount<T> findFutureRowCount() { return this.rootQuery.findFutureRowCount(); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public List<T> findList() { return this.rootQuery.findList(); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public Map<?, T> findMap() { return this.rootQuery.findMap(); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public PagingList<T> findPagingList(int pageSize) { return this.rootQuery.findPagingList(pageSize); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public int findRowCount() { return this.rootQuery.findRowCount(); }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public Set<T> findSet() { return this.rootQuery.findSet(); }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public T findUnique() { return (T)this.rootQuery.findUnique(); }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public ExpressionList<T> having() { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public ExpressionList<T> idEq(Object value) { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public ExpressionList<T> idIn(List<?> idValues) { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 116 */   public Query<T> join(String assocProperty, String assocProperties) { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 120 */   public Query<T> join(String assocProperties) { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public OrderBy<T> order() { return this.rootQuery.order(); }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public Query<T> order(String orderByClause) { return this.rootQuery.order(orderByClause); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public Query<T> orderBy(String orderBy) { return this.rootQuery.orderBy(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public Query<T> query() { return this.rootQuery; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public Query<T> select(String properties) { throw new PersistenceException(this.notAllowedMessage); }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) { return this.rootQuery.setBackgroundFetchAfter(backgroundFetchAfter); }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public Query<T> setFirstRow(int firstRow) { return this.rootQuery.setFirstRow(firstRow); }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public Query<T> setListener(QueryListener<T> queryListener) { return this.rootQuery.setListener(queryListener); }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public Query<T> setMapKey(String mapKey) { return this.rootQuery.setMapKey(mapKey); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public Query<T> setMaxRows(int maxRows) { return this.rootQuery.setMaxRows(maxRows); }
/*     */ 
/*     */ 
/*     */   
/* 164 */   public Query<T> setUseCache(boolean useCache) { return this.rootQuery.setUseCache(useCache); }
/*     */ 
/*     */ 
/*     */   
/* 168 */   public ExpressionList<T> where() { return this.rootQuery.where(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\FilterExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */