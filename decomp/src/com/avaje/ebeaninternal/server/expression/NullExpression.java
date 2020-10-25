/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NullExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4246991057451128269L;
/*    */   private final boolean notNull;
/*    */   
/*    */   NullExpression(FilterExprPath pathPrefix, String propertyName, boolean notNull) {
/* 20 */     super(pathPrefix, propertyName);
/* 21 */     this.notNull = notNull;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 44 */     String propertyName = getPropertyName();
/*    */     
/* 46 */     String nullExpr = this.notNull ? " is not null " : " is null ";
/*    */     
/* 48 */     ElPropertyValue prop = getElProp(request);
/* 49 */     if (prop != null && prop.isAssocId()) {
/* 50 */       request.append(prop.getAssocOneIdExpr(propertyName, nullExpr));
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     request.append(propertyName).append(nullExpr);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 61 */     hc = NullExpression.class.getName().hashCode();
/* 62 */     hc = hc * 31 + (this.notNull ? 1 : 0);
/* 63 */     return hc * 31 + this.propName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public int queryBindHash() { return this.notNull ? 1 : 0; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\NullExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */