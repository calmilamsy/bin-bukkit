/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4072786211853856174L;
/*    */   protected final String propName;
/*    */   protected final FilterExprPath pathPrefix;
/*    */   
/*    */   protected AbstractExpression(FilterExprPath pathPrefix, String propName) {
/* 44 */     this.pathPrefix = pathPrefix;
/* 45 */     this.propName = propName;
/*    */   }
/*    */   
/*    */   protected String getPropertyName() {
/* 49 */     if (this.pathPrefix == null) {
/* 50 */       return this.propName;
/*    */     }
/* 52 */     String path = this.pathPrefix.getPath();
/* 53 */     if (path == null || path.length() == 0) {
/* 54 */       return this.propName;
/*    */     }
/* 56 */     return path + "." + this.propName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 67 */     String propertyName = getPropertyName();
/* 68 */     if (propertyName != null) {
/* 69 */       ElPropertyDeploy elProp = desc.getElPropertyDeploy(propertyName);
/* 70 */       if (elProp != null && elProp.containsMany()) {
/* 71 */         manyWhereJoin.add(elProp);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ElPropertyValue getElProp(SpiExpressionRequest request) {
/* 78 */     String propertyName = getPropertyName();
/* 79 */     return request.getBeanDescriptor().getElGetValue(propertyName);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\AbstractExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */