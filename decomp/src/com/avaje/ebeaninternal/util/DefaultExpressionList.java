/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.FutureIds;
/*     */ import com.avaje.ebean.FutureList;
/*     */ import com.avaje.ebean.FutureRowCount;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebean.PagingList;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.QueryIterator;
/*     */ import com.avaje.ebean.QueryListener;
/*     */ import com.avaje.ebean.QueryResultVisitor;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class DefaultExpressionList<T>
/*     */   extends Object
/*     */   implements SpiExpressionList<T>
/*     */ {
/*     */   private static final long serialVersionUID = -6992345500247035947L;
/*     */   private final ArrayList<SpiExpression> list;
/*     */   private final Query<T> query;
/*     */   private final ExpressionList<T> parentExprList;
/*     */   private ExpressionFactory expr;
/*     */   private final String exprLang;
/*     */   private final String listAndStart;
/*     */   private final String listAndEnd;
/*     */   private final String listAndJoin;
/*     */   
/*  52 */   public DefaultExpressionList(Query<T> query, ExpressionList<T> parentExprList) { this(query, query.getExpressionFactory(), parentExprList); }
/*     */   
/*     */   public DefaultExpressionList(Query<T> query, ExpressionFactory expr, ExpressionList<T> parentExprList) {
/*     */     this.list = new ArrayList();
/*  56 */     this.query = query;
/*  57 */     this.expr = expr;
/*  58 */     this.exprLang = expr.getLang();
/*  59 */     this.parentExprList = parentExprList;
/*     */     
/*  61 */     if ("ldap".equals(this.exprLang)) {
/*     */       
/*  63 */       this.listAndStart = "(&";
/*  64 */       this.listAndEnd = ")";
/*  65 */       this.listAndJoin = "";
/*     */     } else {
/*     */       
/*  68 */       this.listAndStart = "";
/*  69 */       this.listAndEnd = "";
/*  70 */       this.listAndJoin = " and ";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  75 */   public void trimPath(int prefixTrim) { throw new RuntimeException("Only allowed on FilterExpressionList"); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public List<SpiExpression> internalList() { return this.list; }
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable(LuceneResolvableRequest req) {
/*  83 */     for (int i = 0; i < this.list.size(); i++) {
/*  84 */       if (!((SpiExpression)this.list.get(i)).isLuceneResolvable(req)) {
/*  85 */         return false;
/*     */       }
/*     */     } 
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, SpiLuceneExpr.ExprOccur occur) {
/*  93 */     LuceneQueryList queryList = new LuceneQueryList(occur);
/*  94 */     for (int i = 0; i < this.list.size(); i++) {
/*  95 */       SpiLuceneExpr query = ((SpiExpression)this.list.get(i)).createLuceneExpr(request);
/*  96 */       queryList.add(query);
/*     */     } 
/*  98 */     return queryList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void setExpressionFactory(ExpressionFactory expr) { this.expr = expr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultExpressionList<T> copy(Query<T> query) {
/* 120 */     DefaultExpressionList<T> copy = new DefaultExpressionList<T>(query, this.expr, null);
/* 121 */     copy.list.addAll(this.list);
/* 122 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
/* 130 */     for (int i = 0; i < this.list.size(); i++) {
/* 131 */       ((SpiExpression)this.list.get(i)).containsMany(desc, whereManyJoins);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 136 */   public ExpressionList<T> endJunction() { return (this.parentExprList == null) ? this : this.parentExprList; }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public Query<T> query() { return this.query; }
/*     */ 
/*     */ 
/*     */   
/* 144 */   public ExpressionList<T> where() { return this.query.where(); }
/*     */ 
/*     */ 
/*     */   
/* 148 */   public OrderBy<T> order() { return this.query.order(); }
/*     */ 
/*     */ 
/*     */   
/* 152 */   public OrderBy<T> orderBy() { return this.query.order(); }
/*     */ 
/*     */ 
/*     */   
/* 156 */   public Query<T> order(String orderByClause) { return this.query.order(orderByClause); }
/*     */ 
/*     */ 
/*     */   
/* 160 */   public Query<T> orderBy(String orderBy) { return this.query.order(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 164 */   public Query<T> setOrderBy(String orderBy) { return this.query.order(orderBy); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public FutureIds<T> findFutureIds() { return this.query.findFutureIds(); }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public FutureRowCount<T> findFutureRowCount() { return this.query.findFutureRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public FutureList<T> findFutureList() { return this.query.findFutureList(); }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public PagingList<T> findPagingList(int pageSize) { return this.query.findPagingList(pageSize); }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public int findRowCount() { return this.query.findRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public List<Object> findIds() { return this.query.findIds(); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void findVisit(QueryResultVisitor<T> visitor) { this.query.findVisit(visitor); }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public QueryIterator<T> findIterate() { return this.query.findIterate(); }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public List<T> findList() { return this.query.findList(); }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public Set<T> findSet() { return this.query.findSet(); }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public Map<?, T> findMap() { return this.query.findMap(); }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) { return this.query.findMap(keyProperty, keyType); }
/*     */ 
/*     */ 
/*     */   
/* 217 */   public T findUnique() { return (T)this.query.findUnique(); }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public ExpressionList<T> filterMany(String prop) { return this.query.filterMany(prop); }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public Query<T> select(String fetchProperties) { return this.query.select(fetchProperties); }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public Query<T> join(String assocProperties) { return this.query.fetch(assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public Query<T> join(String assocProperty, String assocProperties) { return this.query.fetch(assocProperty, assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 237 */   public Query<T> setFirstRow(int firstRow) { return this.query.setFirstRow(firstRow); }
/*     */ 
/*     */ 
/*     */   
/* 241 */   public Query<T> setMaxRows(int maxRows) { return this.query.setMaxRows(maxRows); }
/*     */ 
/*     */ 
/*     */   
/* 245 */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) { return this.query.setBackgroundFetchAfter(backgroundFetchAfter); }
/*     */ 
/*     */ 
/*     */   
/* 249 */   public Query<T> setMapKey(String mapKey) { return this.query.setMapKey(mapKey); }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public Query<T> setListener(QueryListener<T> queryListener) { return this.query.setListener(queryListener); }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public Query<T> setUseCache(boolean useCache) { return this.query.setUseCache(useCache); }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public ExpressionList<T> having() { return this.query.having(); }
/*     */ 
/*     */   
/*     */   public ExpressionList<T> add(Expression expr) {
/* 265 */     this.list.add((SpiExpression)expr);
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 270 */   public boolean isEmpty() { return this.list.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String buildSql(SpiExpressionRequest request) {
/* 275 */     request.append(this.listAndStart);
/* 276 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 277 */       SpiExpression expression = (SpiExpression)this.list.get(i);
/* 278 */       if (i > 0) {
/* 279 */         request.append(this.listAndJoin);
/*     */       }
/* 281 */       expression.addSql(request);
/*     */     } 
/* 283 */     request.append(this.listAndEnd);
/* 284 */     return request.getSql();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<Object> buildBindValues(SpiExpressionRequest request) {
/* 289 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 290 */       SpiExpression expression = (SpiExpression)this.list.get(i);
/* 291 */       expression.addBindValues(request);
/*     */     } 
/* 293 */     return request.getBindValues();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 301 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 302 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 303 */       SpiExpression expression = (SpiExpression)this.list.get(i);
/* 304 */       hash = hash * 31 + expression.queryAutoFetchHash();
/*     */     } 
/* 306 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 314 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 315 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 316 */       SpiExpression expression = (SpiExpression)this.list.get(i);
/* 317 */       hash = hash * 31 + expression.queryPlanHash(request);
/*     */     } 
/* 319 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/* 326 */     int hash = DefaultExpressionList.class.getName().hashCode();
/* 327 */     for (int i = 0, size = this.list.size(); i < size; i++) {
/* 328 */       SpiExpression expression = (SpiExpression)this.list.get(i);
/* 329 */       hash = hash * 31 + expression.queryBindHash();
/*     */     } 
/* 331 */     return hash;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> eq(String propertyName, Object value) {
/* 335 */     add(this.expr.eq(propertyName, value));
/* 336 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ieq(String propertyName, String value) {
/* 340 */     add(this.expr.ieq(propertyName, value));
/* 341 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ne(String propertyName, Object value) {
/* 345 */     add(this.expr.ne(propertyName, value));
/* 346 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> allEq(Map<String, Object> propertyMap) {
/* 350 */     add(this.expr.allEq(propertyMap));
/* 351 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> and(Expression expOne, Expression expTwo) {
/* 355 */     add(this.expr.and(expOne, expTwo));
/* 356 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> between(String propertyName, Object value1, Object value2) {
/* 360 */     add(this.expr.between(propertyName, value1, value2));
/* 361 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) {
/* 365 */     add(this.expr.betweenProperties(lowProperty, highProperty, value));
/* 366 */     return this;
/*     */   }
/*     */   
/*     */   public Junction<T> conjunction() {
/* 370 */     Junction<T> conjunction = this.expr.conjunction(this.query, this);
/* 371 */     add(conjunction);
/* 372 */     return conjunction;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> contains(String propertyName, String value) {
/* 376 */     add(this.expr.contains(propertyName, value));
/* 377 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> lucene(String propertyName, String value) {
/* 381 */     add(this.expr.lucene(propertyName, value));
/* 382 */     return this;
/*     */   }
/*     */   
/*     */   public Junction<T> disjunction() {
/* 386 */     Junction<T> disjunction = this.expr.disjunction(this.query, this);
/* 387 */     add(disjunction);
/* 388 */     return disjunction;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> endsWith(String propertyName, String value) {
/* 392 */     add(this.expr.endsWith(propertyName, value));
/* 393 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ge(String propertyName, Object value) {
/* 397 */     add(this.expr.ge(propertyName, value));
/* 398 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> gt(String propertyName, Object value) {
/* 402 */     add(this.expr.gt(propertyName, value));
/* 403 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> icontains(String propertyName, String value) {
/* 407 */     add(this.expr.icontains(propertyName, value));
/* 408 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idIn(List<?> idList) {
/* 412 */     add(this.expr.idIn(idList));
/* 413 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> idEq(Object value) {
/* 417 */     add(this.expr.idEq(value));
/* 418 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iendsWith(String propertyName, String value) {
/* 422 */     add(this.expr.iendsWith(propertyName, value));
/* 423 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> ilike(String propertyName, String value) {
/* 427 */     add(this.expr.ilike(propertyName, value));
/* 428 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Query<?> subQuery) {
/* 432 */     add(this.expr.in(propertyName, subQuery));
/* 433 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Collection<?> values) {
/* 437 */     add(this.expr.in(propertyName, values));
/* 438 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> in(String propertyName, Object... values) {
/* 442 */     add(this.expr.in(propertyName, values));
/* 443 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNotNull(String propertyName) {
/* 447 */     add(this.expr.isNotNull(propertyName));
/* 448 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> isNull(String propertyName) {
/* 452 */     add(this.expr.isNull(propertyName));
/* 453 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> istartsWith(String propertyName, String value) {
/* 457 */     add(this.expr.istartsWith(propertyName, value));
/* 458 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> le(String propertyName, Object value) {
/* 462 */     add(this.expr.le(propertyName, value));
/* 463 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> exampleLike(Object example) {
/* 467 */     add(this.expr.exampleLike(example));
/* 468 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> iexampleLike(Object example) {
/* 472 */     add(this.expr.iexampleLike(example));
/* 473 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> like(String propertyName, String value) {
/* 477 */     add(this.expr.like(propertyName, value));
/* 478 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> lt(String propertyName, Object value) {
/* 482 */     add(this.expr.lt(propertyName, value));
/* 483 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> not(Expression exp) {
/* 487 */     add(this.expr.not(exp));
/* 488 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> or(Expression expOne, Expression expTwo) {
/* 492 */     add(this.expr.or(expOne, expTwo));
/* 493 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object value) {
/* 497 */     add(this.expr.raw(raw, value));
/* 498 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw, Object[] values) {
/* 502 */     add(this.expr.raw(raw, values));
/* 503 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> raw(String raw) {
/* 507 */     add(this.expr.raw(raw));
/* 508 */     return this;
/*     */   }
/*     */   
/*     */   public ExpressionList<T> startsWith(String propertyName, String value) {
/* 512 */     add(this.expr.startsWith(propertyName, value));
/* 513 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\DefaultExpressionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */