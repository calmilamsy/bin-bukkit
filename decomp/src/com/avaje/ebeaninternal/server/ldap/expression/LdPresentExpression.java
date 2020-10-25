/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ class LdPresentExpression
/*    */   extends LdAbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = -4221300142054382003L;
/*    */   
/* 15 */   public LdPresentExpression(String propertyName) { super(propertyName); }
/*    */ 
/*    */ 
/*    */   
/* 19 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public String getPropertyName() { return this.propertyName; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 36 */     String parsed = request.parseDeploy(this.propertyName);
/* 37 */     request.append("(").append(parsed).append("=*").append(")");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 45 */     hc = LdPresentExpression.class.getName().hashCode();
/* 46 */     return hc * 31 + this.propertyName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public int queryBindHash() { return 1; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdPresentExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */