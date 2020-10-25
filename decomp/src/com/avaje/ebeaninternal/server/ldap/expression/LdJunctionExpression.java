/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
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
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ abstract class LdJunctionExpression<T>
/*     */   extends Object
/*     */   implements Junction<T>, SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = -7422204102750462677L;
/*     */   private final DefaultExpressionList<T> exprList;
/*     */   private final String joinType;
/*     */   
/*     */   static class Conjunction<T>
/*     */     extends LdJunctionExpression<T> {
/*     */     private static final long serialVersionUID = -645619859900030679L;
/*     */     
/*  41 */     Conjunction(Query<T> query, ExpressionList<T> parent) { super("&", query, parent); }
/*     */ 
/*     */ 
/*     */     
/*  45 */     Conjunction(ExpressionFactory exprFactory) { super("&", exprFactory); }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Disjunction<T>
/*     */     extends LdJunctionExpression<T>
/*     */   {
/*     */     private static final long serialVersionUID = -8464470066692221414L;
/*     */     
/*  54 */     Disjunction(Query<T> query, ExpressionList<T> parent) { super("|", query, parent); }
/*     */ 
/*     */ 
/*     */     
/*  58 */     Disjunction(ExpressionFactory exprFactory) { super("|", exprFactory); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LdJunctionExpression(String joinType, Query<T> query, ExpressionList<T> parent) {
/*  68 */     this.joinType = joinType;
/*  69 */     this.exprList = new DefaultExpressionList(query, parent);
/*     */   }
/*     */   
/*     */   LdJunctionExpression(String joinType, ExpressionFactory exprFactory) {
/*  73 */     this.joinType = joinType;
/*  74 */     this.exprList = new DefaultExpressionList(null, exprFactory, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  83 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  88 */     List<SpiExpression> list = this.exprList.internalList();
/*  89 */     for (int i = 0; i < list.size(); i++) {
/*  90 */       ((SpiExpression)list.get(i)).containsMany(desc, manyWhereJoin);
/*     */     }
/*     */   }
/*     */   
/*     */   public Junction<T> add(Expression item) {
/*  95 */     SpiExpression i = (SpiExpression)item;
/*  96 */     this.exprList.add(i);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/* 102 */     List<SpiExpression> list = this.exprList.internalList();
/* 103 */     for (int i = 0; i < list.size(); i++) {
/* 104 */       SpiExpression item = (SpiExpression)list.get(i);
/* 105 */       item.addBindValues(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 111 */     List<SpiExpression> list = this.exprList.internalList();
/* 112 */     if (!list.isEmpty()) {
/* 113 */       request.append("(");
/* 114 */       request.append(this.joinType);
/*     */       
/* 116 */       for (int i = 0; i < list.size(); i++) {
/* 117 */         SpiExpression item = (SpiExpression)list.get(i);
/* 118 */         item.addSql(request);
/*     */       } 
/*     */       
/* 121 */       request.append(") ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 129 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 130 */     hc = hc * 31 + this.joinType.hashCode();
/* 131 */     List<SpiExpression> list = this.exprList.internalList();
/* 132 */     for (int i = 0; i < list.size(); i++) {
/* 133 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryAutoFetchHash();
/*     */     }
/*     */     
/* 136 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 140 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 141 */     hc = hc * 31 + this.joinType.hashCode();
/* 142 */     List<SpiExpression> list = this.exprList.internalList();
/* 143 */     for (int i = 0; i < list.size(); i++) {
/* 144 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryPlanHash(request);
/*     */     }
/*     */     
/* 147 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 151 */     int hc = LdJunctionExpression.class.getName().hashCode();
/* 152 */     List<SpiExpression> list = this.exprList.internalList();
/* 153 */     for (int i = 0; i < list.size(); i++) {
/* 154 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryBindHash();
/*     */     }
/*     */     
/* 157 */     return hc;
/*     */   }
/*     */ 
/*     */   
/* 161 */   public ExpressionList<T> endJunction() { return this.exprList.endJunction(); }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public ExpressionList<T> allEq(Map<String, Object> propertyMap) { return this.exprList.allEq(propertyMap); }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public ExpressionList<T> and(Expression expOne, Expression expTwo) { return this.exprList.and(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public ExpressionList<T> between(String propertyName, Object value1, Object value2) { return this.exprList.between(propertyName, value1, value2); }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) { return this.exprList.betweenProperties(lowProperty, highProperty, value); }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public Junction<T> conjunction() { return this.exprList.conjunction(); }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public ExpressionList<T> contains(String propertyName, String value) { return this.exprList.contains(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public Junction<T> disjunction() { return this.exprList.disjunction(); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public ExpressionList<T> endsWith(String propertyName, String value) { return this.exprList.endsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public ExpressionList<T> eq(String propertyName, Object value) { return this.exprList.eq(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public ExpressionList<T> exampleLike(Object example) { return this.exprList.exampleLike(example); }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public ExpressionList<T> filterMany(String prop) { throw new RuntimeException("filterMany not allowed on Junction expression list"); }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public FutureIds<T> findFutureIds() { return this.exprList.findFutureIds(); }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public FutureList<T> findFutureList() { return this.exprList.findFutureList(); }
/*     */ 
/*     */ 
/*     */   
/* 217 */   public FutureRowCount<T> findFutureRowCount() { return this.exprList.findFutureRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public List<Object> findIds() { return this.exprList.findIds(); }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public void findVisit(QueryResultVisitor<T> visitor) { this.exprList.findVisit(visitor); }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public QueryIterator<T> findIterate() { return this.exprList.findIterate(); }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public List<T> findList() { return this.exprList.findList(); }
/*     */ 
/*     */ 
/*     */   
/* 237 */   public Map<?, T> findMap() { return this.exprList.findMap(); }
/*     */ 
/*     */ 
/*     */   
/* 241 */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) { return this.exprList.findMap(keyProperty, keyType); }
/*     */ 
/*     */ 
/*     */   
/* 245 */   public PagingList<T> findPagingList(int pageSize) { return this.exprList.findPagingList(pageSize); }
/*     */ 
/*     */ 
/*     */   
/* 249 */   public int findRowCount() { return this.exprList.findRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public Set<T> findSet() { return this.exprList.findSet(); }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public T findUnique() { return (T)this.exprList.findUnique(); }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public ExpressionList<T> ge(String propertyName, Object value) { return this.exprList.ge(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 265 */   public ExpressionList<T> gt(String propertyName, Object value) { return this.exprList.gt(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 269 */   public ExpressionList<T> having() { throw new RuntimeException("having() not allowed on Junction expression list"); }
/*     */ 
/*     */ 
/*     */   
/* 273 */   public ExpressionList<T> icontains(String propertyName, String value) { return this.exprList.icontains(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 277 */   public ExpressionList<T> idEq(Object value) { return this.exprList.idEq(value); }
/*     */ 
/*     */ 
/*     */   
/* 281 */   public ExpressionList<T> idIn(List<?> idValues) { return this.exprList.idIn(idValues); }
/*     */ 
/*     */ 
/*     */   
/* 285 */   public ExpressionList<T> iendsWith(String propertyName, String value) { return this.exprList.iendsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 289 */   public ExpressionList<T> ieq(String propertyName, String value) { return this.exprList.ieq(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 293 */   public ExpressionList<T> iexampleLike(Object example) { return this.exprList.iexampleLike(example); }
/*     */ 
/*     */ 
/*     */   
/* 297 */   public ExpressionList<T> ilike(String propertyName, String value) { return this.exprList.ilike(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 301 */   public ExpressionList<T> in(String propertyName, Collection<?> values) { return this.exprList.in(propertyName, values); }
/*     */ 
/*     */ 
/*     */   
/* 305 */   public ExpressionList<T> in(String propertyName, Object... values) { return this.exprList.in(propertyName, values); }
/*     */ 
/*     */ 
/*     */   
/* 309 */   public ExpressionList<T> in(String propertyName, Query<?> subQuery) { return this.exprList.in(propertyName, subQuery); }
/*     */ 
/*     */ 
/*     */   
/* 313 */   public ExpressionList<T> isNotNull(String propertyName) { return this.exprList.isNotNull(propertyName); }
/*     */ 
/*     */ 
/*     */   
/* 317 */   public ExpressionList<T> isNull(String propertyName) { return this.exprList.isNull(propertyName); }
/*     */ 
/*     */ 
/*     */   
/* 321 */   public ExpressionList<T> istartsWith(String propertyName, String value) { return this.exprList.istartsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 325 */   public Query<T> join(String assocProperty, String assocProperties) { return this.exprList.join(assocProperty, assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 329 */   public Query<T> join(String assocProperties) { return this.exprList.join(assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 333 */   public ExpressionList<T> le(String propertyName, Object value) { return this.exprList.le(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 337 */   public ExpressionList<T> like(String propertyName, String value) { return this.exprList.like(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 341 */   public ExpressionList<T> lt(String propertyName, Object value) { return this.exprList.lt(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 345 */   public ExpressionList<T> lucene(String propertyName, String value) { return this.exprList.lucene(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 349 */   public ExpressionList<T> ne(String propertyName, Object value) { return this.exprList.ne(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 353 */   public ExpressionList<T> not(Expression exp) { return this.exprList.not(exp); }
/*     */ 
/*     */ 
/*     */   
/* 357 */   public ExpressionList<T> or(Expression expOne, Expression expTwo) { return this.exprList.or(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */   
/* 361 */   public OrderBy<T> order() { return this.exprList.order(); }
/*     */ 
/*     */ 
/*     */   
/* 365 */   public Query<T> order(String orderByClause) { return this.exprList.order(orderByClause); }
/*     */ 
/*     */ 
/*     */   
/* 369 */   public OrderBy<T> orderBy() { return this.exprList.orderBy(); }
/*     */ 
/*     */ 
/*     */   
/* 373 */   public Query<T> orderBy(String orderBy) { return this.exprList.orderBy(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 377 */   public Query<T> query() { return this.exprList.query(); }
/*     */ 
/*     */ 
/*     */   
/* 381 */   public ExpressionList<T> raw(String raw, Object value) { return this.exprList.raw(raw, value); }
/*     */ 
/*     */ 
/*     */   
/* 385 */   public ExpressionList<T> raw(String raw, Object[] values) { return this.exprList.raw(raw, values); }
/*     */ 
/*     */ 
/*     */   
/* 389 */   public ExpressionList<T> raw(String raw) { return this.exprList.raw(raw); }
/*     */ 
/*     */ 
/*     */   
/* 393 */   public Query<T> select(String properties) { return this.exprList.select(properties); }
/*     */ 
/*     */ 
/*     */   
/* 397 */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) { return this.exprList.setBackgroundFetchAfter(backgroundFetchAfter); }
/*     */ 
/*     */ 
/*     */   
/* 401 */   public Query<T> setFirstRow(int firstRow) { return this.exprList.setFirstRow(firstRow); }
/*     */ 
/*     */ 
/*     */   
/* 405 */   public Query<T> setListener(QueryListener<T> queryListener) { return this.exprList.setListener(queryListener); }
/*     */ 
/*     */ 
/*     */   
/* 409 */   public Query<T> setMapKey(String mapKey) { return this.exprList.setMapKey(mapKey); }
/*     */ 
/*     */ 
/*     */   
/* 413 */   public Query<T> setMaxRows(int maxRows) { return this.exprList.setMaxRows(maxRows); }
/*     */ 
/*     */ 
/*     */   
/* 417 */   public Query<T> setOrderBy(String orderBy) { return this.exprList.setOrderBy(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 421 */   public Query<T> setUseCache(boolean useCache) { return this.exprList.setUseCache(useCache); }
/*     */ 
/*     */ 
/*     */   
/* 425 */   public ExpressionList<T> startsWith(String propertyName, String value) { return this.exprList.startsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 429 */   public ExpressionList<T> where() { return this.exprList.where(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdJunctionExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */