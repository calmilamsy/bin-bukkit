/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.Expression;
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
/*     */ 
/*     */ abstract class JunctionExpression<T>
/*     */   extends Object
/*     */   implements Junction<T>, SpiExpression, ExpressionList<T>
/*     */ {
/*     */   private static final long serialVersionUID = -7422204102750462676L;
/*     */   private static final String OR = " or ";
/*     */   private static final String AND = " and ";
/*     */   private final DefaultExpressionList<T> exprList;
/*     */   private final String joinType;
/*     */   
/*     */   static class Conjunction<T>
/*     */     extends JunctionExpression<T>
/*     */   {
/*     */     private static final long serialVersionUID = -645619859900030678L;
/*     */     
/*  44 */     Conjunction(Query<T> query, ExpressionList<T> parent) { super(" and ", query, parent); }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Disjunction<T>
/*     */     extends JunctionExpression<T>
/*     */   {
/*     */     private static final long serialVersionUID = -8464470066692221413L;
/*     */     
/*  53 */     Disjunction(Query<T> query, ExpressionList<T> parent) { super(" or ", query, parent); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JunctionExpression(String joinType, Query<T> query, ExpressionList<T> parent) {
/*  64 */     this.joinType = joinType;
/*  65 */     this.exprList = new DefaultExpressionList(query, parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable(LuceneResolvableRequest req) {
/*  70 */     List<SpiExpression> list = this.exprList.internalList();
/*     */     
/*  72 */     for (int i = 0; i < list.size(); i++) {
/*  73 */       if (!((SpiExpression)list.get(i)).isLuceneResolvable(req)) {
/*  74 */         return false;
/*     */       }
/*     */     } 
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
/*  82 */     boolean disjunction = " or ".equals(this.joinType);
/*  83 */     return (new JunctionExpressionLucene()).createLuceneExpr(request, this.exprList.internalList(), disjunction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  88 */     List<SpiExpression> list = this.exprList.internalList();
/*     */     
/*  90 */     for (int i = 0; i < list.size(); i++) {
/*  91 */       ((SpiExpression)list.get(i)).containsMany(desc, manyWhereJoin);
/*     */     }
/*     */   }
/*     */   
/*     */   public Junction<T> add(Expression item) {
/*  96 */     SpiExpression i = (SpiExpression)item;
/*  97 */     this.exprList.add(i);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/* 103 */     List<SpiExpression> list = this.exprList.internalList();
/*     */     
/* 105 */     for (int i = 0; i < list.size(); i++) {
/* 106 */       SpiExpression item = (SpiExpression)list.get(i);
/* 107 */       item.addBindValues(request);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 113 */     List<SpiExpression> list = this.exprList.internalList();
/*     */     
/* 115 */     if (!list.isEmpty()) {
/* 116 */       request.append("(");
/*     */       
/* 118 */       for (int i = 0; i < list.size(); i++) {
/* 119 */         SpiExpression item = (SpiExpression)list.get(i);
/* 120 */         if (i > 0) {
/* 121 */           request.append(this.joinType);
/*     */         }
/* 123 */         item.addSql(request);
/*     */       } 
/*     */       
/* 126 */       request.append(") ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 134 */     int hc = JunctionExpression.class.getName().hashCode();
/* 135 */     hc = hc * 31 + this.joinType.hashCode();
/*     */     
/* 137 */     List<SpiExpression> list = this.exprList.internalList();
/* 138 */     for (int i = 0; i < list.size(); i++) {
/* 139 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryAutoFetchHash();
/*     */     }
/*     */     
/* 142 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 146 */     int hc = JunctionExpression.class.getName().hashCode();
/* 147 */     hc = hc * 31 + this.joinType.hashCode();
/*     */     
/* 149 */     List<SpiExpression> list = this.exprList.internalList();
/* 150 */     for (int i = 0; i < list.size(); i++) {
/* 151 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryPlanHash(request);
/*     */     }
/*     */     
/* 154 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 158 */     int hc = JunctionExpression.class.getName().hashCode();
/*     */     
/* 160 */     List<SpiExpression> list = this.exprList.internalList();
/* 161 */     for (int i = 0; i < list.size(); i++) {
/* 162 */       hc = hc * 31 + ((SpiExpression)list.get(i)).queryBindHash();
/*     */     }
/*     */     
/* 165 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public ExpressionList<T> endJunction() { return this.exprList.endJunction(); }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public ExpressionList<T> allEq(Map<String, Object> propertyMap) { return this.exprList.allEq(propertyMap); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public ExpressionList<T> and(Expression expOne, Expression expTwo) { return this.exprList.and(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public ExpressionList<T> between(String propertyName, Object value1, Object value2) { return this.exprList.between(propertyName, value1, value2); }
/*     */ 
/*     */ 
/*     */   
/* 186 */   public ExpressionList<T> betweenProperties(String lowProperty, String highProperty, Object value) { return this.exprList.betweenProperties(lowProperty, highProperty, value); }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public Junction<T> conjunction() { return this.exprList.conjunction(); }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public ExpressionList<T> contains(String propertyName, String value) { return this.exprList.contains(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public Junction<T> disjunction() { return this.exprList.disjunction(); }
/*     */ 
/*     */ 
/*     */   
/* 202 */   public ExpressionList<T> endsWith(String propertyName, String value) { return this.exprList.endsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 206 */   public ExpressionList<T> eq(String propertyName, Object value) { return this.exprList.eq(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 210 */   public ExpressionList<T> exampleLike(Object example) { return this.exprList.exampleLike(example); }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public ExpressionList<T> filterMany(String prop) { throw new RuntimeException("filterMany not allowed on Junction expression list"); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public FutureIds<T> findFutureIds() { return this.exprList.findFutureIds(); }
/*     */ 
/*     */ 
/*     */   
/* 222 */   public FutureList<T> findFutureList() { return this.exprList.findFutureList(); }
/*     */ 
/*     */ 
/*     */   
/* 226 */   public FutureRowCount<T> findFutureRowCount() { return this.exprList.findFutureRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 230 */   public List<Object> findIds() { return this.exprList.findIds(); }
/*     */ 
/*     */ 
/*     */   
/* 234 */   public void findVisit(QueryResultVisitor<T> visitor) { this.exprList.findVisit(visitor); }
/*     */ 
/*     */ 
/*     */   
/* 238 */   public QueryIterator<T> findIterate() { return this.exprList.findIterate(); }
/*     */ 
/*     */ 
/*     */   
/* 242 */   public List<T> findList() { return this.exprList.findList(); }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public Map<?, T> findMap() { return this.exprList.findMap(); }
/*     */ 
/*     */ 
/*     */   
/* 250 */   public <K> Map<K, T> findMap(String keyProperty, Class<K> keyType) { return this.exprList.findMap(keyProperty, keyType); }
/*     */ 
/*     */ 
/*     */   
/* 254 */   public PagingList<T> findPagingList(int pageSize) { return this.exprList.findPagingList(pageSize); }
/*     */ 
/*     */ 
/*     */   
/* 258 */   public int findRowCount() { return this.exprList.findRowCount(); }
/*     */ 
/*     */ 
/*     */   
/* 262 */   public Set<T> findSet() { return this.exprList.findSet(); }
/*     */ 
/*     */ 
/*     */   
/* 266 */   public T findUnique() { return (T)this.exprList.findUnique(); }
/*     */ 
/*     */ 
/*     */   
/* 270 */   public ExpressionList<T> ge(String propertyName, Object value) { return this.exprList.ge(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 274 */   public ExpressionList<T> gt(String propertyName, Object value) { return this.exprList.gt(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 278 */   public ExpressionList<T> having() { throw new RuntimeException("having() not allowed on Junction expression list"); }
/*     */ 
/*     */ 
/*     */   
/* 282 */   public ExpressionList<T> icontains(String propertyName, String value) { return this.exprList.icontains(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 286 */   public ExpressionList<T> idEq(Object value) { return this.exprList.idEq(value); }
/*     */ 
/*     */ 
/*     */   
/* 290 */   public ExpressionList<T> idIn(List<?> idValues) { return this.exprList.idIn(idValues); }
/*     */ 
/*     */ 
/*     */   
/* 294 */   public ExpressionList<T> iendsWith(String propertyName, String value) { return this.exprList.iendsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 298 */   public ExpressionList<T> ieq(String propertyName, String value) { return this.exprList.ieq(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 302 */   public ExpressionList<T> iexampleLike(Object example) { return this.exprList.iexampleLike(example); }
/*     */ 
/*     */ 
/*     */   
/* 306 */   public ExpressionList<T> ilike(String propertyName, String value) { return this.exprList.ilike(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 310 */   public ExpressionList<T> in(String propertyName, Collection<?> values) { return this.exprList.in(propertyName, values); }
/*     */ 
/*     */ 
/*     */   
/* 314 */   public ExpressionList<T> in(String propertyName, Object... values) { return this.exprList.in(propertyName, values); }
/*     */ 
/*     */ 
/*     */   
/* 318 */   public ExpressionList<T> in(String propertyName, Query<?> subQuery) { return this.exprList.in(propertyName, subQuery); }
/*     */ 
/*     */ 
/*     */   
/* 322 */   public ExpressionList<T> isNotNull(String propertyName) { return this.exprList.isNotNull(propertyName); }
/*     */ 
/*     */ 
/*     */   
/* 326 */   public ExpressionList<T> isNull(String propertyName) { return this.exprList.isNull(propertyName); }
/*     */ 
/*     */ 
/*     */   
/* 330 */   public ExpressionList<T> istartsWith(String propertyName, String value) { return this.exprList.istartsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 334 */   public Query<T> join(String assocProperty, String assocProperties) { return this.exprList.join(assocProperty, assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 338 */   public Query<T> join(String assocProperties) { return this.exprList.join(assocProperties); }
/*     */ 
/*     */ 
/*     */   
/* 342 */   public ExpressionList<T> le(String propertyName, Object value) { return this.exprList.le(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 346 */   public ExpressionList<T> like(String propertyName, String value) { return this.exprList.like(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 350 */   public ExpressionList<T> lt(String propertyName, Object value) { return this.exprList.lt(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 354 */   public ExpressionList<T> lucene(String propertyName, String value) { return this.exprList.lucene(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 358 */   public ExpressionList<T> ne(String propertyName, Object value) { return this.exprList.ne(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 362 */   public ExpressionList<T> not(Expression exp) { return this.exprList.not(exp); }
/*     */ 
/*     */ 
/*     */   
/* 366 */   public ExpressionList<T> or(Expression expOne, Expression expTwo) { return this.exprList.or(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */   
/* 370 */   public OrderBy<T> order() { return this.exprList.order(); }
/*     */ 
/*     */ 
/*     */   
/* 374 */   public Query<T> order(String orderByClause) { return this.exprList.order(orderByClause); }
/*     */ 
/*     */ 
/*     */   
/* 378 */   public OrderBy<T> orderBy() { return this.exprList.orderBy(); }
/*     */ 
/*     */ 
/*     */   
/* 382 */   public Query<T> orderBy(String orderBy) { return this.exprList.orderBy(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 386 */   public Query<T> query() { return this.exprList.query(); }
/*     */ 
/*     */ 
/*     */   
/* 390 */   public ExpressionList<T> raw(String raw, Object value) { return this.exprList.raw(raw, value); }
/*     */ 
/*     */ 
/*     */   
/* 394 */   public ExpressionList<T> raw(String raw, Object[] values) { return this.exprList.raw(raw, values); }
/*     */ 
/*     */ 
/*     */   
/* 398 */   public ExpressionList<T> raw(String raw) { return this.exprList.raw(raw); }
/*     */ 
/*     */ 
/*     */   
/* 402 */   public Query<T> select(String properties) { return this.exprList.select(properties); }
/*     */ 
/*     */ 
/*     */   
/* 406 */   public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) { return this.exprList.setBackgroundFetchAfter(backgroundFetchAfter); }
/*     */ 
/*     */ 
/*     */   
/* 410 */   public Query<T> setFirstRow(int firstRow) { return this.exprList.setFirstRow(firstRow); }
/*     */ 
/*     */ 
/*     */   
/* 414 */   public Query<T> setListener(QueryListener<T> queryListener) { return this.exprList.setListener(queryListener); }
/*     */ 
/*     */ 
/*     */   
/* 418 */   public Query<T> setMapKey(String mapKey) { return this.exprList.setMapKey(mapKey); }
/*     */ 
/*     */ 
/*     */   
/* 422 */   public Query<T> setMaxRows(int maxRows) { return this.exprList.setMaxRows(maxRows); }
/*     */ 
/*     */ 
/*     */   
/* 426 */   public Query<T> setOrderBy(String orderBy) { return this.exprList.setOrderBy(orderBy); }
/*     */ 
/*     */ 
/*     */   
/* 430 */   public Query<T> setUseCache(boolean useCache) { return this.exprList.setUseCache(useCache); }
/*     */ 
/*     */ 
/*     */   
/* 434 */   public ExpressionList<T> startsWith(String propertyName, String value) { return this.exprList.startsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 438 */   public ExpressionList<T> where() { return this.exprList.where(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\JunctionExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */