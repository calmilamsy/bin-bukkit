/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ 
/*     */ class SimpleExpression
/*     */   extends AbstractExpression implements LuceneAwareExpression {
/*     */   private static final long serialVersionUID = -382881395755603790L;
/*     */   private final Op type;
/*     */   private final Object value;
/*     */   
/*     */   enum Op {
/*  18 */     EQ(" = ? ", " = "),
/*  19 */     NOT_EQ(" <> ? ", " <> "),
/*  20 */     LT(" < ? ", " < "),
/*  21 */     LT_EQ(" <= ? ", " <= "),
/*  22 */     GT(" > ? ", " > "),
/*  23 */     GT_EQ(" >= ? ", " >= ");
/*     */     String exp;
/*     */     String shortDesc;
/*     */     
/*     */     Op(String exp, String shortDesc) {
/*  28 */       this.exp = exp;
/*  29 */       this.shortDesc = shortDesc;
/*     */     }
/*     */ 
/*     */     
/*  33 */     public String bind() { return this.exp; }
/*     */ 
/*     */ 
/*     */     
/*  37 */     public String shortDesc() { return this.shortDesc; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleExpression(FilterExprPath pathPrefix, String propertyName, Op type, Object value) {
/*  46 */     super(pathPrefix, propertyName);
/*  47 */     this.type = type;
/*  48 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable(LuceneResolvableRequest req) {
/*  53 */     String propertyName = getPropertyName();
/*     */     
/*  55 */     if (!req.indexContains(propertyName)) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     ElPropertyValue prop = req.getBeanDescriptor().getElGetValue(propertyName);
/*  60 */     if (prop == null) {
/*  61 */       return false;
/*     */     }
/*  63 */     BeanProperty beanProperty = prop.getBeanProperty();
/*  64 */     ScalarType<?> scalarType = beanProperty.getScalarType();
/*  65 */     if (scalarType == null)
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     int luceneType = scalarType.getLuceneType();
/*  70 */     if (7 == luceneType) {
/*  71 */       return false;
/*     */     }
/*  73 */     if (0 == luceneType) {
/*  74 */       if (Op.EQ.equals(this.type) || Op.NOT_EQ.equals(this.type)) {
/*  75 */         return true;
/*     */       }
/*  77 */       return false;
/*     */     } 
/*  79 */     if (Op.NOT_EQ.equals(this.type)) {
/*  80 */       return false;
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
/*  87 */     String propertyName = getPropertyName();
/*     */     
/*  89 */     ElPropertyValue prop = getElProp(request);
/*     */     
/*  91 */     return (new SimpleExpressionLucene()).addLuceneQuery(request, this.type, propertyName, this.value, prop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  97 */     ElPropertyValue prop = getElProp(request);
/*  98 */     if (prop != null) {
/*  99 */       if (prop.isAssocId()) {
/* 100 */         Object[] ids = prop.getAssocOneIdValues(this.value);
/* 101 */         if (ids != null) {
/* 102 */           for (int i = 0; i < ids.length; i++) {
/* 103 */             request.addBindValue(ids[i]);
/*     */           }
/*     */         }
/*     */         return;
/*     */       } 
/* 108 */       if (prop.isDbEncrypted()) {
/*     */         
/* 110 */         String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/* 111 */         request.addBindValue(encryptKey);
/* 112 */       } else if (prop.isLocalEncrypted()) {
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     request.addBindValue(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/* 124 */     String propertyName = getPropertyName();
/*     */     
/* 126 */     ElPropertyValue prop = getElProp(request);
/* 127 */     if (prop != null) {
/* 128 */       if (prop.isAssocId()) {
/* 129 */         request.append(prop.getAssocOneIdExpr(propertyName, this.type.bind()));
/*     */         return;
/*     */       } 
/* 132 */       if (prop.isDbEncrypted()) {
/* 133 */         String dsql = prop.getBeanProperty().getDecryptSql();
/* 134 */         request.append(dsql).append(this.type.bind());
/*     */         return;
/*     */       } 
/*     */     } 
/* 138 */     request.append(propertyName).append(this.type.bind());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 146 */     hc = SimpleExpression.class.getName().hashCode();
/* 147 */     hc = hc * 31 + this.propName.hashCode();
/* 148 */     return hc * 31 + this.type.name().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 153 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */ 
/*     */   
/* 157 */   public int queryBindHash() { return this.value.hashCode(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\SimpleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */