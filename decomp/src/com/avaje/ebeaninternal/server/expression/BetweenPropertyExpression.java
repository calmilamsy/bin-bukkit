/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BetweenPropertyExpression
/*     */   implements SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = 2078918165221454910L;
/*     */   private static final String BETWEEN = " between ";
/*     */   private final FilterExprPath pathPrefix;
/*     */   private final String lowProperty;
/*     */   private final String highProperty;
/*     */   private final Object value;
/*     */   
/*     */   BetweenPropertyExpression(FilterExprPath pathPrefix, String lowProperty, String highProperty, Object value) {
/*  48 */     this.pathPrefix = pathPrefix;
/*  49 */     this.lowProperty = lowProperty;
/*  50 */     this.highProperty = highProperty;
/*  51 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*  55 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */   
/*     */   protected String name(String propName) {
/*  63 */     if (this.pathPrefix == null) {
/*  64 */       return propName;
/*     */     }
/*  66 */     String path = this.pathPrefix.getPath();
/*  67 */     if (path == null || path.length() == 0) {
/*  68 */       return propName;
/*     */     }
/*  70 */     return path + "." + propName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  78 */     ElPropertyDeploy elProp = desc.getElPropertyDeploy(name(this.lowProperty));
/*  79 */     if (elProp != null && elProp.containsMany()) {
/*  80 */       manyWhereJoin.add(elProp);
/*     */     }
/*     */     
/*  83 */     elProp = desc.getElPropertyDeploy(name(this.highProperty));
/*  84 */     if (elProp != null && elProp.containsMany()) {
/*  85 */       manyWhereJoin.add(elProp);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  90 */   public void addBindValues(SpiExpressionRequest request) { request.addBindValue(this.value); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public void addSql(SpiExpressionRequest request) { request.append(" ? ").append(" between ").append(name(this.lowProperty)).append(" and ").append(name(this.highProperty)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 101 */     hc = BetweenPropertyExpression.class.getName().hashCode();
/* 102 */     hc = hc * 31 + this.lowProperty.hashCode();
/* 103 */     return hc * 31 + this.highProperty.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public int queryBindHash() { return this.value.hashCode(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\BetweenPropertyExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */