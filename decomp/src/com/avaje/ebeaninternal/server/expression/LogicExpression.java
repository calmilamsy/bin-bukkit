/*     */ package com.avaje.ebeaninternal.server.expression;
/*     */ 
/*     */ import com.avaje.ebean.Expression;
/*     */ import com.avaje.ebean.event.BeanQueryRequest;
/*     */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*     */ import com.avaje.ebeaninternal.api.SpiExpression;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*     */ 
/*     */ 
/*     */ abstract class LogicExpression
/*     */   implements SpiExpression
/*     */ {
/*     */   private static final long serialVersionUID = 616860781960645251L;
/*     */   static final String AND = " and ";
/*     */   static final String OR = " or ";
/*     */   private final SpiExpression expOne;
/*     */   private final SpiExpression expTwo;
/*     */   private final String joinType;
/*     */   
/*     */   static class And
/*     */     extends LogicExpression
/*     */   {
/*     */     private static final long serialVersionUID = -3832889676798526444L;
/*     */     
/*  28 */     And(Expression expOne, Expression expTwo) { super(" and ", expOne, expTwo); }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Or
/*     */     extends LogicExpression
/*     */   {
/*     */     private static final long serialVersionUID = -6871993143194094819L;
/*     */     
/*  37 */     Or(Expression expOne, Expression expTwo) { super(" or ", expOne, expTwo); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LogicExpression(String joinType, Expression expOne, Expression expTwo) {
/*  48 */     this.joinType = joinType;
/*  49 */     this.expOne = (SpiExpression)expOne;
/*  50 */     this.expTwo = (SpiExpression)expTwo;
/*     */   }
/*     */ 
/*     */   
/*  54 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return (this.expOne.isLuceneResolvable(req) && this.expTwo.isLuceneResolvable(req)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return (new LogicExpressionLucene()).addLuceneQuery(this.joinType, request, this.expOne, this.expTwo); }
/*     */ 
/*     */   
/*     */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
/*  63 */     this.expOne.containsMany(desc, manyWhereJoin);
/*  64 */     this.expTwo.containsMany(desc, manyWhereJoin);
/*     */   }
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request) {
/*  68 */     this.expOne.addBindValues(request);
/*  69 */     this.expTwo.addBindValues(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  74 */     request.append("(");
/*  75 */     this.expOne.addSql(request);
/*  76 */     request.append(this.joinType);
/*  77 */     this.expTwo.addSql(request);
/*  78 */     request.append(") ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryAutoFetchHash() {
/*  85 */     hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/*  86 */     hc = hc * 31 + this.expOne.queryAutoFetchHash();
/*  87 */     return hc * 31 + this.expTwo.queryAutoFetchHash();
/*     */   }
/*     */ 
/*     */   
/*     */   public int queryPlanHash(BeanQueryRequest<?> request) {
/*  92 */     hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
/*  93 */     hc = hc * 31 + this.expOne.queryPlanHash(request);
/*  94 */     return hc * 31 + this.expTwo.queryPlanHash(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public int queryBindHash() {
/*  99 */     hc = this.expOne.queryBindHash();
/* 100 */     return hc * 31 + this.expTwo.queryBindHash();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LogicExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */