/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class BetweenExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 2078918165221454910L;
/*    */   private static final String BETWEEN = " between ";
/*    */   private final Object valueHigh;
/*    */   private final Object valueLow;
/*    */   
/*    */   BetweenExpression(FilterExprPath pathPrefix, String propertyName, Object valLo, Object valHigh) {
/* 21 */     super(pathPrefix, propertyName);
/* 22 */     this.valueLow = valLo;
/* 23 */     this.valueHigh = valHigh;
/*    */   }
/*    */ 
/*    */   
/* 27 */   public boolean isLuceneResolvable(Set<String> indexedProperties) { return indexedProperties.contains(getPropertyName()); }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 35 */     request.addBindValue(this.valueLow);
/* 36 */     request.addBindValue(this.valueHigh);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void addSql(SpiExpressionRequest request) { request.append(getPropertyName()).append(" between ").append(" ? and ? "); }
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 45 */     hc = BetweenExpression.class.getName().hashCode();
/* 46 */     return hc * 31 + this.propName.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */   
/*    */   public int queryBindHash() {
/* 55 */     hc = this.valueLow.hashCode();
/* 56 */     return hc * 31 + this.valueHigh.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\BetweenExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */