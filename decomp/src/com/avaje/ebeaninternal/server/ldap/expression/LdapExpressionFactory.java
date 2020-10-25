/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
/*     */ 
/*     */ import com.avaje.ebean.ExampleExpression;
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.ExpressionFactory;
/*     */ import com.avaje.ebean.ExpressionList;
/*     */ import com.avaje.ebean.Junction;
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class LdapExpressionFactory
/*     */   implements ExpressionFactory
/*     */ {
/*  41 */   public String getLang() { return "ldap"; }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public ExpressionFactory createExpressionFactory(String path) { return new LdapExpressionFactory(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression allEq(Map<String, Object> propertyMap) {
/*  51 */     Junction conjunction = new LdJunctionExpression.Conjunction(this);
/*     */     
/*  53 */     Iterator<Map.Entry<String, Object>> it = propertyMap.entrySet().iterator();
/*  54 */     while (it.hasNext()) {
/*  55 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/*  56 */       conjunction.add(eq((String)entry.getKey(), entry.getValue()));
/*     */     } 
/*  58 */     return conjunction;
/*     */   }
/*     */ 
/*     */   
/*  62 */   public Expression and(Expression expOne, Expression expTwo) { return new LdLogicExpression.And(expOne, expTwo); }
/*     */ 
/*     */   
/*     */   public Expression between(String propertyName, Object value1, Object value2) {
/*  66 */     Expression e1 = gt(propertyName, value1);
/*  67 */     Expression e2 = lt(propertyName, value2);
/*  68 */     return and(e1, e2);
/*     */   }
/*     */ 
/*     */   
/*  72 */   public Expression betweenProperties(String lowProperty, String highProperty, Object value) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */   
/*     */   public Expression contains(String propertyName, String value) {
/*  76 */     if (!value.endsWith("*")) {
/*  77 */       value = "*" + value + "*";
/*     */     }
/*  79 */     return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
/*     */   }
/*     */ 
/*     */   
/*  83 */   public <T> Junction<T> conjunction(Query<T> query) { return new LdJunctionExpression.Conjunction(query, query.where()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public <T> Junction<T> disjunction(Query<T> query) { return new LdJunctionExpression.Disjunction(query, query.where()); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public <T> Junction<T> conjunction(Query<T> query, ExpressionList<T> parent) { return new LdJunctionExpression.Conjunction(query, parent); }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public <T> Junction<T> disjunction(Query<T> query, ExpressionList<T> parent) { return new LdJunctionExpression.Disjunction(query, parent); }
/*     */ 
/*     */   
/*     */   public Expression endsWith(String propertyName, String value) {
/* 100 */     if (!value.startsWith("*")) {
/* 101 */       value = "*" + value;
/*     */     }
/* 103 */     return new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */   
/* 107 */   public Expression eq(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value); }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public Expression lucene(String propertyName, String value) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public Expression lucene(String value) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public ExampleExpression exampleLike(Object example) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public Expression ge(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT_EQ, value); }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public Expression gt(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT, value); }
/*     */ 
/*     */   
/*     */   public Expression icontains(String propertyName, String value) {
/* 135 */     if (!value.endsWith("*")) {
/* 136 */       value = "*" + value + "*";
/*     */     }
/* 138 */     return new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public Expression idEq(Object value) { return null; }
/*     */ 
/*     */ 
/*     */   
/* 147 */   public Expression idIn(List<?> idList) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */   
/*     */   public Expression iendsWith(String propertyName, String value) {
/* 151 */     if (!value.startsWith("*")) {
/* 152 */       value = "*" + value;
/*     */     }
/* 154 */     return new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */   
/* 158 */   public Expression ieq(String propertyName, String value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value); }
/*     */ 
/*     */ 
/*     */   
/* 162 */   public ExampleExpression iexampleLike(Object example) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public Expression ilike(String propertyName, String value) { return new LdLikeExpression(propertyName, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression in(String propertyName, Collection<?> values) {
/* 172 */     if (values == null || values.isEmpty()) {
/* 173 */       throw new LdapPersistenceException("collection can't be empty for Ldap");
/*     */     }
/*     */     
/* 176 */     Junction disjunction = new LdJunctionExpression.Disjunction(this);
/* 177 */     for (Object v : values) {
/* 178 */       disjunction.add(eq(propertyName, v));
/*     */     }
/*     */     
/* 181 */     return disjunction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression in(String propertyName, Object[] values) {
/* 187 */     if (values == null || values.length == 0) {
/* 188 */       throw new LdapPersistenceException("values can't be empty for Ldap");
/*     */     }
/*     */     
/* 191 */     Junction disjunction = new LdJunctionExpression.Disjunction(this);
/* 192 */     for (Object v : values) {
/* 193 */       disjunction.add(eq(propertyName, v));
/*     */     }
/*     */     
/* 196 */     return disjunction;
/*     */   }
/*     */ 
/*     */   
/* 200 */   public Expression in(String propertyName, Query<?> subQuery) { throw new RuntimeException("Not Implemented"); }
/*     */ 
/*     */ 
/*     */   
/* 204 */   public Expression isNotNull(String propertyName) { return new LdPresentExpression(propertyName); }
/*     */ 
/*     */   
/*     */   public Expression isNull(String propertyName) {
/* 208 */     LdPresentExpression exp = new LdPresentExpression(propertyName);
/* 209 */     return new LdNotExpression(exp);
/*     */   }
/*     */   
/*     */   public Expression istartsWith(String propertyName, String value) {
/* 213 */     if (!value.endsWith("*")) {
/* 214 */       value = value + "*";
/*     */     }
/* 216 */     return new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ 
/*     */   
/* 220 */   public Expression le(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT_EQ, value); }
/*     */ 
/*     */ 
/*     */   
/* 224 */   public Expression like(String propertyName, String value) { return new LdLikeExpression(propertyName, value); }
/*     */ 
/*     */ 
/*     */   
/* 228 */   public Expression lt(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT, value); }
/*     */ 
/*     */ 
/*     */   
/* 232 */   public Expression ne(String propertyName, Object value) { return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.NOT_EQ, value); }
/*     */ 
/*     */ 
/*     */   
/* 236 */   public Expression not(Expression exp) { return new LdNotExpression(exp); }
/*     */ 
/*     */ 
/*     */   
/* 240 */   public Expression or(Expression expOne, Expression expTwo) { return new LdLogicExpression.Or(expOne, expTwo); }
/*     */ 
/*     */   
/*     */   public Expression raw(String raw, Object value) {
/* 244 */     if (value != null) {
/* 245 */       return new LdRawExpression(raw, new Object[] { value });
/*     */     }
/*     */     
/* 248 */     return new LdRawExpression(raw, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public Expression raw(String raw, Object[] values) { return new LdRawExpression(raw, values); }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public Expression raw(String raw) { return new LdRawExpression(raw, null); }
/*     */ 
/*     */   
/*     */   public Expression startsWith(String propertyName, String value) {
/* 261 */     if (!value.endsWith("*")) {
/* 262 */       value = value + "*";
/*     */     }
/* 264 */     return new LdLikeExpression(propertyName, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdapExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */