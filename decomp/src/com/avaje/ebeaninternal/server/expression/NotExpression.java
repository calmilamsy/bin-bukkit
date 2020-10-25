/*    */ package com.avaje.ebeaninternal.server.expression;
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
/*    */ final class NotExpression
/*    */   implements SpiExpression, LuceneAwareExpression
/*    */ {
/*    */   private static final long serialVersionUID = 5648926732402355781L;
/*    */   private static final String NOT = "not (";
/*    */   private final SpiExpression exp;
/*    */   
/* 22 */   NotExpression(Expression exp) { this.exp = (SpiExpression)exp; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) { this.exp.containsMany(desc, manyWhereJoin); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public void addBindValues(SpiExpressionRequest request) { this.exp.addBindValues(request); }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 49 */     request.append("not (");
/* 50 */     this.exp.addSql(request);
/* 51 */     request.append(") ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 58 */     hc = NotExpression.class.getName().hashCode();
/* 59 */     return hc * 31 + this.exp.queryAutoFetchHash();
/*    */   }
/*    */ 
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 64 */     hc = NotExpression.class.getName().hashCode();
/* 65 */     return hc * 31 + this.exp.queryPlanHash(request);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public int queryBindHash() { return this.exp.queryBindHash(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\NotExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */