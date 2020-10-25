/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class IdExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = -3065936341718489842L;
/*    */   private final Object value;
/*    */   
/* 23 */   IdExpression(Object value) { this.value = value; }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 54 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 55 */     Object[] bindIdValues = r.getBeanDescriptor().getBindIdValues(this.value);
/* 56 */     for (int i = 0; i < bindIdValues.length; i++) {
/* 57 */       request.addBindValue(bindIdValues[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 63 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 64 */     String idSql = r.getBeanDescriptor().getIdBinderIdSql();
/*    */     
/* 66 */     request.append(idSql).append(" ");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public int queryAutoFetchHash() { return IdExpression.class.getName().hashCode(); }
/*    */ 
/*    */ 
/*    */   
/* 79 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 83 */   public int queryBindHash() { return this.value.hashCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\IdExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */