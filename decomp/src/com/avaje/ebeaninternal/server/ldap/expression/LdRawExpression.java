/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
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
/*    */ class LdRawExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 7973903141340334607L;
/*    */   private final String rawExpr;
/*    */   private final Object[] values;
/*    */   
/*    */   LdRawExpression(String rawExpr, Object[] values) {
/* 21 */     this.rawExpr = rawExpr;
/* 22 */     this.values = values;
/*    */   }
/*    */ 
/*    */   
/* 26 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 38 */     if (this.values != null) {
/* 39 */       for (int i = 0; i < this.values.length; i++) {
/* 40 */         request.addBindValue(this.values[i]);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 46 */   public void addSql(SpiExpressionRequest request) { request.append(this.rawExpr); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 53 */     hc = LdRawExpression.class.getName().hashCode();
/* 54 */     return hc * 31 + this.rawExpr.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public int queryBindHash() { return this.rawExpr.hashCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdRawExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */