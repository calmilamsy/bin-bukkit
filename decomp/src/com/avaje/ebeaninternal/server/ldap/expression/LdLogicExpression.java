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
/*    */ abstract class LdLogicExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 616860781960645251L;
/*    */   static final String AND = "&";
/*    */   static final String OR = "|";
/*    */   private final SpiExpression expOne;
/*    */   private final SpiExpression expTwo;
/*    */   private final String joinType;
/*    */   
/*    */   static class And
/*    */     extends LdLogicExpression
/*    */   {
/*    */     private static final long serialVersionUID = -3832889676798526445L;
/*    */     
/* 28 */     And(Expression expOne, Expression expTwo) { super("&", expOne, expTwo); }
/*    */   }
/*    */ 
/*    */   
/*    */   static class Or
/*    */     extends LdLogicExpression
/*    */   {
/*    */     private static final long serialVersionUID = -6871993143194094810L;
/*    */     
/* 37 */     Or(Expression expOne, Expression expTwo) { super("|", expOne, expTwo); }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   LdLogicExpression(String joinType, Expression expOne, Expression expTwo) {
/* 48 */     this.joinType = joinType;
/* 49 */     this.expOne = (SpiExpression)expOne;
/* 50 */     this.expTwo = (SpiExpression)expTwo;
/*    */   }
/*    */ 
/*    */   
/* 54 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return (this.expOne.isLuceneResolvable(req) && this.expTwo.isLuceneResolvable(req)); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 62 */     this.expOne.containsMany(desc, manyWhereJoin);
/* 63 */     this.expTwo.containsMany(desc, manyWhereJoin);
/*    */   }
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 67 */     this.expOne.addBindValues(request);
/* 68 */     this.expTwo.addBindValues(request);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 73 */     request.append("(");
/* 74 */     request.append(this.joinType);
/* 75 */     this.expOne.addSql(request);
/* 76 */     this.expTwo.addSql(request);
/* 77 */     request.append(") ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 84 */     hc = LdLogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/* 85 */     hc = hc * 31 + this.expOne.queryAutoFetchHash();
/* 86 */     return hc * 31 + this.expTwo.queryAutoFetchHash();
/*    */   }
/*    */ 
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 91 */     hc = LdLogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/* 92 */     hc = hc * 31 + this.expOne.queryPlanHash(request);
/* 93 */     return hc * 31 + this.expTwo.queryPlanHash(request);
/*    */   }
/*    */ 
/*    */   
/*    */   public int queryBindHash() {
/* 98 */     hc = this.expOne.queryBindHash();
/* 99 */     return hc * 31 + this.expTwo.queryBindHash();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdLogicExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */