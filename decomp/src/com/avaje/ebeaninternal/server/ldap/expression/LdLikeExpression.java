/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ 
/*    */ class LdLikeExpression
/*    */   extends LdAbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4091359751840929076L;
/*    */   private final String value;
/*    */   
/*    */   public LdLikeExpression(String propertyName, String value) {
/* 15 */     super(propertyName);
/* 16 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String getPropertyName() { return this.propertyName; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/*    */     String escapedValue;
/* 38 */     if (this.value == null) {
/* 39 */       escapedValue = "*";
/*    */     } else {
/* 41 */       escapedValue = LdEscape.forLike(this.value);
/*    */     } 
/*    */     
/* 44 */     String parsed = request.parseDeploy(this.propertyName);
/*    */     
/* 46 */     request.append("(").append(parsed).append("=").append(escapedValue).append(")");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 54 */     hc = LdLikeExpression.class.getName().hashCode();
/* 55 */     return hc * 31 + this.propertyName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public int queryBindHash() { return this.value.hashCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdLikeExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */