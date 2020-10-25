/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
/*     */ 
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
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
/*     */ 
/*     */ class LdSimpleExpression
/*     */   extends LdAbstractExpression
/*     */ {
/*     */   private static final long serialVersionUID = 4091359751840929075L;
/*     */   private final Op type;
/*     */   private final Object value;
/*     */   
/*     */   final enum Op
/*     */   {
/*     */     EQ, NOT_EQ, LT, LT_EQ, GT, GT_EQ;
/*     */     
/*     */     static  {
/*     */       // Byte code:
/*     */       //   0: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$1
/*     */       //   3: dup
/*     */       //   4: ldc 'EQ'
/*     */       //   6: iconst_0
/*     */       //   7: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   10: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   13: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$2
/*     */       //   16: dup
/*     */       //   17: ldc 'NOT_EQ'
/*     */       //   19: iconst_1
/*     */       //   20: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   23: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.NOT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   26: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$3
/*     */       //   29: dup
/*     */       //   30: ldc 'LT'
/*     */       //   32: iconst_2
/*     */       //   33: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   36: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.LT : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   39: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$4
/*     */       //   42: dup
/*     */       //   43: ldc 'LT_EQ'
/*     */       //   45: iconst_3
/*     */       //   46: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   49: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.LT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   52: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$5
/*     */       //   55: dup
/*     */       //   56: ldc 'GT'
/*     */       //   58: iconst_4
/*     */       //   59: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   62: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.GT : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   65: new com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op$6
/*     */       //   68: dup
/*     */       //   69: ldc 'GT_EQ'
/*     */       //   71: iconst_5
/*     */       //   72: invokespecial <init> : (Ljava/lang/String;I)V
/*     */       //   75: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.GT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   78: bipush #6
/*     */       //   80: anewarray com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op
/*     */       //   83: dup
/*     */       //   84: iconst_0
/*     */       //   85: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   88: aastore
/*     */       //   89: dup
/*     */       //   90: iconst_1
/*     */       //   91: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.NOT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   94: aastore
/*     */       //   95: dup
/*     */       //   96: iconst_2
/*     */       //   97: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.LT : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   100: aastore
/*     */       //   101: dup
/*     */       //   102: iconst_3
/*     */       //   103: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.LT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   106: aastore
/*     */       //   107: dup
/*     */       //   108: iconst_4
/*     */       //   109: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.GT : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   112: aastore
/*     */       //   113: dup
/*     */       //   114: iconst_5
/*     */       //   115: getstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.GT_EQ : Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   118: aastore
/*     */       //   119: putstatic com/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op.$VALUES : [Lcom/avaje/ebeaninternal/server/ldap/expression/LdSimpleExpression$Op;
/*     */       //   122: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #16	-> 0
/*     */       //   #21	-> 13
/*     */       //   #26	-> 26
/*     */       //   #31	-> 39
/*     */       //   #36	-> 52
/*     */       //   #41	-> 65
/*     */       //   #15	-> 78
/*     */     }
/*     */   }
/*     */   
/*     */   public LdSimpleExpression(String propertyName, Op type, Object value) {
/*  53 */     super(propertyName);
/*  54 */     this.type = type;
/*  55 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*  59 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public String getPropertyName() { return this.propertyName; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  72 */     ElPropertyValue prop = getElProp(request);
/*  73 */     if (prop != null) {
/*  74 */       if (prop.isAssocId()) {
/*  75 */         Object[] ids = prop.getAssocOneIdValues(this.value);
/*  76 */         if (ids != null) {
/*  77 */           for (int i = 0; i < ids.length; i++) {
/*  78 */             request.addBindValue(ids[i]);
/*     */           }
/*     */         }
/*     */         return;
/*     */       } 
/*  83 */       ScalarType<?> scalarType = prop.getBeanProperty().getScalarType();
/*  84 */       Object v = scalarType.toJdbcType(this.value);
/*  85 */       request.addBindValue(v);
/*     */     } else {
/*  87 */       request.addBindValue(this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  92 */     ElPropertyValue prop = getElProp(request);
/*  93 */     if (prop != null && 
/*  94 */       prop.isAssocId()) {
/*  95 */       String rawExpr = prop.getAssocOneIdExpr(this.propertyName, this.type.toString());
/*  96 */       String parsed = request.parseDeploy(rawExpr);
/*  97 */       request.append(parsed);
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     String parsed = request.parseDeploy(this.propertyName);
/*     */     
/* 103 */     request.append("(").append(parsed).append("").append(this.type.toString()).append(nextParam(request)).append(")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/* 111 */     hc = LdSimpleExpression.class.getName().hashCode();
/* 112 */     hc = hc * 31 + this.propertyName.hashCode();
/* 113 */     return hc * 31 + this.type.name().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public int queryBindHash() { return this.value.hashCode(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdSimpleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */