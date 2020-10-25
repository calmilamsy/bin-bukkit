/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.LikeType;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ 
/*     */ 
/*     */ class LikeExpression
/*     */   extends AbstractExpression
/*     */   implements LuceneAwareExpression
/*     */ {
/*     */   private static final long serialVersionUID = -5398151809111172380L;
/*     */   private final String val;
/*     */   private final boolean caseInsensitive;
/*     */   private final LikeType type;
/*     */   
/*     */   LikeExpression(FilterExprPath pathPrefix, String propertyName, String value, boolean caseInsensitive, LikeType type) {
/*  21 */     super(pathPrefix, propertyName);
/*  22 */     this.caseInsensitive = caseInsensitive;
/*  23 */     this.type = type;
/*  24 */     this.val = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable(LuceneResolvableRequest req) {
/*  29 */     String propertyName = getPropertyName();
/*  30 */     if (LikeType.ENDS_WITH.equals(this.type)) {
/*  31 */       return false;
/*     */     }
/*  33 */     if (req.indexContains(propertyName)) {
/*  34 */       return true;
/*     */     }
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
/*  41 */     String propertyName = getPropertyName();
/*     */     
/*  43 */     return (new LikeExpressionLucene()).createLuceneExpr(request, propertyName, this.type, this.caseInsensitive, this.val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  48 */     ElPropertyValue prop = getElProp(request);
/*  49 */     if (prop != null && prop.isDbEncrypted()) {
/*     */       
/*  51 */       String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
/*  52 */       request.addBindValue(encryptKey);
/*     */     } 
/*     */     
/*  55 */     String bindValue = getValue(this.val, this.caseInsensitive, this.type);
/*  56 */     request.addBindValue(bindValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  61 */     String propertyName = getPropertyName();
/*  62 */     String pname = propertyName;
/*     */     
/*  64 */     ElPropertyValue prop = getElProp(request);
/*  65 */     if (prop != null && prop.isDbEncrypted()) {
/*  66 */       pname = prop.getBeanProperty().getDecryptProperty(propertyName);
/*     */     }
/*  68 */     if (this.caseInsensitive) {
/*  69 */       request.append("lower(").append(pname).append(")");
/*     */     } else {
/*  71 */       request.append(pname);
/*     */     } 
/*  73 */     if (this.type.equals(LikeType.EQUAL_TO)) {
/*  74 */       request.append(" = ? ");
/*     */     } else {
/*  76 */       request.append(" like ? ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/*  84 */     hc = LikeExpression.class.getName().hashCode();
/*  85 */     hc = hc * 31 + (this.caseInsensitive ? 0 : 1);
/*  86 */     return hc * 31 + this.propName.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */ 
/*     */   
/*  95 */   public int queryBindHash() { return this.val.hashCode(); }
/*     */ 
/*     */   
/*     */   private static String getValue(String value, boolean caseInsensitive, LikeType type) {
/*  99 */     if (caseInsensitive) {
/* 100 */       value = value.toLowerCase();
/*     */     }
/* 102 */     switch (type) {
/*     */       case RAW:
/* 104 */         return value;
/*     */       case STARTS_WITH:
/* 106 */         return value + "%";
/*     */       case ENDS_WITH:
/* 108 */         return "%" + value;
/*     */       case CONTAINS:
/* 110 */         return "%" + value + "%";
/*     */       case EQUAL_TO:
/* 112 */         return value;
/*     */     } 
/*     */     
/* 115 */     throw new RuntimeException("LikeType " + type + " missed?");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LikeExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */