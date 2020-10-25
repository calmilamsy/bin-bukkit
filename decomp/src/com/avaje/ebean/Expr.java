/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ public class Expr
/*     */ {
/*  69 */   public static Expression eq(String propertyName, Object value) { return Ebean.getExpressionFactory().eq(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static Expression ne(String propertyName, Object value) { return Ebean.getExpressionFactory().ne(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static Expression ieq(String propertyName, String value) { return Ebean.getExpressionFactory().ieq(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static Expression between(String propertyName, Object value1, Object value2) { return Ebean.getExpressionFactory().between(propertyName, value1, value2); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static Expression gt(String propertyName, Object value) { return Ebean.getExpressionFactory().gt(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static Expression ge(String propertyName, Object value) { return Ebean.getExpressionFactory().ge(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static Expression lt(String propertyName, Object value) { return Ebean.getExpressionFactory().lt(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static Expression le(String propertyName, Object value) { return Ebean.getExpressionFactory().le(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static Expression isNull(String propertyName) { return Ebean.getExpressionFactory().isNull(propertyName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public static Expression isNotNull(String propertyName) { return Ebean.getExpressionFactory().isNotNull(propertyName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static ExampleExpression iexampleLike(Object example) { return Ebean.getExpressionFactory().iexampleLike(example); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static ExampleExpression exampleLike(Object example) { return Ebean.getExpressionFactory().exampleLike(example); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) { return Ebean.getExpressionFactory().exampleLike(example, caseInsensitive, likeType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static Expression like(String propertyName, String value) { return Ebean.getExpressionFactory().like(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public static Expression ilike(String propertyName, String value) { return Ebean.getExpressionFactory().ilike(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public static Expression startsWith(String propertyName, String value) { return Ebean.getExpressionFactory().startsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public static Expression istartsWith(String propertyName, String value) { return Ebean.getExpressionFactory().istartsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static Expression endsWith(String propertyName, String value) { return Ebean.getExpressionFactory().endsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public static Expression iendsWith(String propertyName, String value) { return Ebean.getExpressionFactory().iendsWith(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public static Expression contains(String propertyName, String value) { return Ebean.getExpressionFactory().contains(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public static Expression icontains(String propertyName, String value) { return Ebean.getExpressionFactory().icontains(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public static Expression in(String propertyName, Object[] values) { return Ebean.getExpressionFactory().in(propertyName, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   public static Expression in(String propertyName, Query<?> subQuery) { return Ebean.getExpressionFactory().in(propertyName, subQuery); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static Expression in(String propertyName, Collection<?> values) { return Ebean.getExpressionFactory().in(propertyName, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public static Expression idEq(Object value) { return Ebean.getExpressionFactory().idEq(value); }
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
/* 260 */   public static Expression allEq(Map<String, Object> propertyMap) { return Ebean.getExpressionFactory().allEq(propertyMap); }
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
/* 271 */   public static Expression raw(String raw, Object value) { return Ebean.getExpressionFactory().raw(raw, value); }
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
/* 282 */   public static Expression raw(String raw, Object[] values) { return Ebean.getExpressionFactory().raw(raw, values); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   public static Expression raw(String raw) { return Ebean.getExpressionFactory().raw(raw); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public static Expression and(Expression expOne, Expression expTwo) { return Ebean.getExpressionFactory().and(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 305 */   public static Expression or(Expression expOne, Expression expTwo) { return Ebean.getExpressionFactory().or(expOne, expTwo); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 313 */   public static Expression not(Expression exp) { return Ebean.getExpressionFactory().not(exp); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 321 */   public static <T> Junction<T> conjunction(Query<T> query) { return Ebean.getExpressionFactory().conjunction(query); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 329 */   public static <T> Junction<T> disjunction(Query<T> query) { return Ebean.getExpressionFactory().disjunction(query); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\Expr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */