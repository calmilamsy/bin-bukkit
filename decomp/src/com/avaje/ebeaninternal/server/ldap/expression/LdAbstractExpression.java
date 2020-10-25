/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
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
/*    */ public abstract class LdAbstractExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 4072786211853856174L;
/*    */   protected final String propertyName;
/*    */   
/* 41 */   protected LdAbstractExpression(String propertyName) { this.propertyName = propertyName; }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String nextParam(SpiExpressionRequest request) {
/* 46 */     int pos = request.nextParameter();
/* 47 */     return "{" + (pos - 1) + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/* 52 */     if (this.propertyName != null) {
/* 53 */       ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.propertyName);
/* 54 */       if (elProp != null && elProp.containsMany()) {
/* 55 */         manyWhereJoin.add(elProp);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 62 */   protected ElPropertyValue getElProp(SpiExpressionRequest request) { return request.getBeanDescriptor().getElGetValue(this.propertyName); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdAbstractExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */