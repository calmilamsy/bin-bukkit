/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ class InExpression
/*     */   extends AbstractExpression
/*     */ {
/*     */   private static final long serialVersionUID = 3150665801693551260L;
/*     */   private final Object[] values;
/*     */   
/*     */   InExpression(FilterExprPath pathPrefix, String propertyName, Collection<?> coll) {
/*  18 */     super(pathPrefix, propertyName);
/*  19 */     this.values = coll.toArray(new Object[coll.size()]);
/*     */   }
/*     */   
/*     */   InExpression(FilterExprPath pathPrefix, String propertyName, Object[] array) {
/*  23 */     super(pathPrefix, propertyName);
/*  24 */     this.values = array;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  29 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  33 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  38 */     ElPropertyValue prop = getElProp(request);
/*  39 */     if (prop != null && !prop.isAssocId()) {
/*  40 */       prop = null;
/*     */     }
/*     */     
/*  43 */     for (int i = 0; i < this.values.length; i++) {
/*  44 */       if (prop == null) {
/*  45 */         request.addBindValue(this.values[i]);
/*     */       }
/*     */       else {
/*     */         
/*  49 */         Object[] ids = prop.getAssocOneIdValues(this.values[i]);
/*  50 */         if (ids != null) {
/*  51 */           for (int j = 0; j < ids.length; j++) {
/*  52 */             request.addBindValue(ids[j]);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  61 */     if (this.values.length == 0) {
/*     */       
/*  63 */       request.append("1=0");
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     String propertyName = getPropertyName();
/*     */     
/*  69 */     ElPropertyValue prop = getElProp(request);
/*  70 */     if (prop != null && !prop.isAssocId()) {
/*  71 */       prop = null;
/*     */     }
/*     */     
/*  74 */     if (prop != null) {
/*  75 */       request.append(prop.getAssocIdInExpr(propertyName));
/*  76 */       String inClause = prop.getAssocIdInValueExpr(this.values.length);
/*  77 */       request.append(inClause);
/*     */     } else {
/*     */       
/*  80 */       request.append(propertyName);
/*  81 */       request.append(" in (?");
/*  82 */       for (int i = 1; i < this.values.length; i++) {
/*  83 */         request.append(", ").append("?");
/*     */       }
/*     */       
/*  86 */       request.append(" ) ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/*  94 */     hc = InExpression.class.getName().hashCode() + 31 * this.values.length;
/*  95 */     return hc * 31 + this.propName.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/* 104 */     int hc = 0;
/* 105 */     for (int i = 1; i < this.values.length; i++) {
/* 106 */       hc = 31 * hc + this.values[i].hashCode();
/*     */     }
/* 108 */     return hc;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\InExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */