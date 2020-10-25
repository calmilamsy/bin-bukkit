/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionFactory;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExpressionFactory
/*     */   implements SpiExpressionFactory
/*     */ {
/*  22 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */   
/*     */   private final FilterExprPath prefix;
/*     */ 
/*     */   
/*  27 */   public DefaultExpressionFactory() { this(null); }
/*     */ 
/*     */ 
/*     */   
/*  31 */   public DefaultExpressionFactory(FilterExprPath prefix) { this.prefix = prefix; }
/*     */ 
/*     */ 
/*     */   
/*  35 */   public ExpressionFactory createExpressionFactory(FilterExprPath prefix) { return new DefaultExpressionFactory(prefix); }
/*     */ 
/*     */ 
/*     */   
/*  39 */   public String getLang() { return "sql"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression eq(String propertyName, Object value) {
/*  46 */     if (value == null) {
/*  47 */       return isNull(propertyName);
/*     */     }
/*  49 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.EQ, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression ne(String propertyName, Object value) {
/*  56 */     if (value == null) {
/*  57 */       return isNotNull(propertyName);
/*     */     }
/*  59 */     return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.NOT_EQ, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression ieq(String propertyName, String value) {
/*  67 */     if (value == null) {
/*  68 */       return isNull(propertyName);
/*     */     }
/*  70 */     return new CaseInsensitiveEqualExpression(this.prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public Expression between(String propertyName, Object value1, Object value2) { return new BetweenExpression(this.prefix, propertyName, value1, value2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public Expression betweenProperties(String lowProperty, String highProperty, Object value) { return new BetweenPropertyExpression(this.prefix, lowProperty, highProperty, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public Expression gt(String propertyName, Object value) { return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public Expression ge(String propertyName, Object value) { return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT_EQ, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public Expression lt(String propertyName, Object value) { return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public Expression le(String propertyName, Object value) { return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT_EQ, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public Expression isNull(String propertyName) { return new NullExpression(this.prefix, propertyName, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public Expression isNotNull(String propertyName) { return new NullExpression(this.prefix, propertyName, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public ExampleExpression iexampleLike(Object example) { return new DefaultExampleExpression(this.prefix, example, true, LikeType.RAW); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public ExampleExpression exampleLike(Object example) { return new DefaultExampleExpression(this.prefix, example, false, LikeType.RAW); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) { return new DefaultExampleExpression(this.prefix, example, caseInsensitive, likeType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public Expression like(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, false, LikeType.RAW); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public Expression ilike(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, true, LikeType.RAW); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public Expression startsWith(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, false, LikeType.STARTS_WITH); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public Expression istartsWith(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, true, LikeType.STARTS_WITH); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public Expression endsWith(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, false, LikeType.ENDS_WITH); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public Expression iendsWith(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, true, LikeType.ENDS_WITH); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public Expression contains(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, false, LikeType.CONTAINS); }
/*     */ 
/*     */ 
/*     */   
/* 214 */   public Expression lucene(String propertyName, String value) { return new LuceneExpression(this.prefix, propertyName, value, true); }
/*     */ 
/*     */ 
/*     */   
/* 218 */   public Expression lucene(String value) { return new LuceneExpression(this.prefix, null, value, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public Expression icontains(String propertyName, String value) { return new LikeExpression(this.prefix, propertyName, value, true, LikeType.CONTAINS); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public Expression in(String propertyName, Object[] values) { return new InExpression(this.prefix, propertyName, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public Expression in(String propertyName, Query<?> subQuery) { return new InQueryExpression(this.prefix, propertyName, (SpiQuery)subQuery); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public Expression in(String propertyName, Collection<?> values) { return new InExpression(this.prefix, propertyName, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public Expression idEq(Object value) { return new IdExpression(value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public Expression idIn(List<?> idList) { return new IdInExpression(idList); }
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
/* 275 */   public Expression allEq(Map<String, Object> propertyMap) { return new AllEqualsExpression(this.prefix, propertyMap); }
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
/* 286 */   public Expression raw(String raw, Object value) { return new RawExpression(raw, new Object[] { value }); }
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
/* 297 */   public Expression raw(String raw, Object[] values) { return new RawExpression(raw, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   public Expression raw(String raw) { return new RawExpression(raw, EMPTY_ARRAY); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public Expression and(Expression expOne, Expression expTwo) { return new LogicExpression.And(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public Expression or(Expression expOne, Expression expTwo) { return new LogicExpression.Or(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public Expression not(Expression exp) { return new NotExpression(exp); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 336 */   public <T> Junction<T> conjunction(Query<T> query) { return new JunctionExpression.Conjunction(query, query.where()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 344 */   public <T> Junction<T> disjunction(Query<T> query) { return new JunctionExpression.Disjunction(query, query.where()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 352 */   public <T> Junction<T> conjunction(Query<T> query, ExpressionList<T> parent) { return new JunctionExpression.Conjunction(query, parent); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 360 */   public <T> Junction<T> disjunction(Query<T> query, ExpressionList<T> parent) { return new JunctionExpression.Disjunction(query, parent); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\DefaultExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */