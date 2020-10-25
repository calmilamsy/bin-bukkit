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
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AllEqualsExpression
/*     */   implements SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = -8691773558205937025L;
/*     */   private final Map<String, Object> propMap;
/*     */   private final FilterExprPath pathPrefix;
/*     */   
/*     */   AllEqualsExpression(FilterExprPath pathPrefix, Map<String, Object> propMap) {
/*  26 */     this.pathPrefix = pathPrefix;
/*  27 */     this.propMap = propMap;
/*     */   }
/*     */ 
/*     */   
/*  31 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  35 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */   
/*     */   protected String name(String propName) {
/*  39 */     if (this.pathPrefix == null) {
/*  40 */       return propName;
/*     */     }
/*  42 */     String path = this.pathPrefix.getPath();
/*  43 */     if (path == null || path.length() == 0) {
/*  44 */       return propName;
/*     */     }
/*  46 */     return path + "." + propName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  52 */     if (this.propMap != null) {
/*  53 */       Iterator<String> it = this.propMap.keySet().iterator();
/*  54 */       while (it.hasNext()) {
/*  55 */         String propertyName = (String)it.next();
/*  56 */         ElPropertyDeploy elProp = desc.getElPropertyDeploy(name(propertyName));
/*  57 */         if (elProp != null && elProp.containsMany()) {
/*  58 */           manyWhereJoin.add(elProp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  66 */     if (this.propMap.isEmpty()) {
/*     */       return;
/*     */     }
/*  69 */     Iterator<Object> it = this.propMap.values().iterator();
/*  70 */     while (it.hasNext()) {
/*  71 */       Object value = it.next();
/*  72 */       if (value != null) {
/*  73 */         request.addBindValue(value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  82 */     if (this.propMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     request.append("(");
/*     */     
/*  88 */     Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
/*  89 */     Iterator<Map.Entry<String, Object>> it = entries.iterator();
/*     */     
/*  91 */     int count = 0;
/*  92 */     while (it.hasNext()) {
/*  93 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/*  94 */       Object value = entry.getValue();
/*  95 */       String propName = (String)entry.getKey();
/*     */       
/*  97 */       if (count > 0) {
/*  98 */         request.append("and ");
/*     */       }
/*     */       
/* 101 */       request.append(name(propName));
/* 102 */       if (value == null) {
/* 103 */         request.append(" is null ");
/*     */       } else {
/* 105 */         request.append(" = ? ");
/*     */       } 
/* 107 */       count++;
/*     */     } 
/* 109 */     request.append(")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 120 */     int hc = AllEqualsExpression.class.getName().hashCode();
/* 121 */     Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
/* 122 */     Iterator<Map.Entry<String, Object>> it = entries.iterator();
/*     */     
/* 124 */     while (it.hasNext()) {
/* 125 */       Map.Entry<String, Object> entry = (Map.Entry)it.next();
/* 126 */       Object value = entry.getValue();
/* 127 */       String propName = (String)entry.getKey();
/*     */       
/* 129 */       hc = hc * 31 + propName.hashCode();
/* 130 */       hc = hc * 31 + ((value == null) ? 0 : 1);
/*     */     } 
/*     */     
/* 133 */     return hc;
/*     */   }
/*     */ 
/*     */   
/* 137 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public int queryBindHash() { return queryAutoFetchHash(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\AllEqualsExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */