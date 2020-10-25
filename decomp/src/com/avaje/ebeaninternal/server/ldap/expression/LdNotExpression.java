/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.Expression;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class LdNotExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 5648926732402355782L;
/*    */   private static final String NOT = "!";
/*    */   private final SpiExpression exp;
/*    */   
/* 22 */   LdNotExpression(Expression exp) { this.exp = (SpiExpression)exp; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return this.exp.isLuceneResolvable(req); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) { this.exp.containsMany(desc, manyWhereJoin); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public void addBindValues(SpiExpressionRequest request) { this.exp.addBindValues(request); }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 42 */     request.append("(");
/* 43 */     request.append("!");
/* 44 */     this.exp.addSql(request);
/* 45 */     request.append(")");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 52 */     hc = LdNotExpression.class.getName().hashCode();
/* 53 */     return hc * 31 + this.exp.queryAutoFetchHash();
/*    */   }
/*    */ 
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 58 */     hc = LdNotExpression.class.getName().hashCode();
/* 59 */     return hc * 31 + this.exp.queryPlanHash(request);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public int queryBindHash() { return this.exp.queryBindHash(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdNotExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */