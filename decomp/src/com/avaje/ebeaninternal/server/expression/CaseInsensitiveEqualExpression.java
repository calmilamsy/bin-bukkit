/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ 
/*    */ class CaseInsensitiveEqualExpression
/*    */   extends AbstractExpression
/*    */   implements LuceneAwareExpression
/*    */ {
/*    */   private static final long serialVersionUID = -6406036750998971064L;
/*    */   private final String value;
/*    */   
/*    */   CaseInsensitiveEqualExpression(FilterExprPath pathPrefix, String propertyName, String value) {
/* 17 */     super(pathPrefix, propertyName);
/* 18 */     this.value = value.toLowerCase();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 23 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return true; }
/*    */ 
/*    */ 
/*    */   
/*    */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
/* 28 */     String propertyName = getPropertyName();
/* 29 */     return (new CaseInsensitiveEqualExpressionLucene()).createLuceneExpr(request, propertyName, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 34 */     ElPropertyValue prop = getElProp(request);
/* 35 */     if (prop != null && prop.isDbEncrypted()) {
/*    */       
/* 37 */       String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/* 38 */       request.addBindValue(encryptKey);
/*    */     } 
/*    */     
/* 41 */     request.addBindValue(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 46 */     String propertyName = getPropertyName();
/* 47 */     String pname = propertyName;
/*    */     
/* 49 */     ElPropertyValue prop = getElProp(request);
/* 50 */     if (prop != null && prop.isDbEncrypted()) {
/* 51 */       pname = prop.getBeanProperty().getDecryptProperty(propertyName);
/*    */     }
/*    */     
/* 54 */     request.append("lower(").append(pname).append(") =? ");
/*    */   }
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 58 */     hc = CaseInsensitiveEqualExpression.class.getName().hashCode();
/* 59 */     return hc * 31 + this.propName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public int queryBindHash() { return this.value.hashCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\CaseInsensitiveEqualExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */