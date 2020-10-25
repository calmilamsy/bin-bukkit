/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.server.query.CQuery;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InQueryExpression
/*    */   extends AbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = 666990277309851644L;
/*    */   private final SpiQuery<?> subQuery;
/*    */   private CQuery<?> compiledSubQuery;
/*    */   
/*    */   public InQueryExpression(FilterExprPath pathPrefix, String propertyName, SpiQuery<?> subQuery) {
/* 27 */     super(pathPrefix, propertyName);
/* 28 */     this.subQuery = subQuery;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 41 */     hc = InQueryExpression.class.getName().hashCode();
/* 42 */     hc = hc * 31 + this.propName.hashCode();
/* 43 */     return hc * 31 + this.subQuery.queryAutofetchHash();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryPlanHash(BeanQueryRequest<?> request) {
/* 51 */     this.compiledSubQuery = compileSubQuery(request);
/*    */     
/* 53 */     hc = InQueryExpression.class.getName().hashCode();
/* 54 */     hc = hc * 31 + this.propName.hashCode();
/* 55 */     return hc * 31 + this.subQuery.queryPlanHash(request);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private CQuery<?> compileSubQuery(BeanQueryRequest<?> queryRequest) {
/* 64 */     SpiEbeanServer ebeanServer = (SpiEbeanServer)queryRequest.getEbeanServer();
/* 65 */     return ebeanServer.compileQuery(this.subQuery, queryRequest.getTransaction());
/*    */   }
/*    */ 
/*    */   
/* 69 */   public int queryBindHash() { return this.subQuery.queryBindHash(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 74 */     String subSelect = this.compiledSubQuery.getGeneratedSql();
/* 75 */     subSelect = subSelect.replace('\n', ' ');
/*    */     
/* 77 */     String propertyName = getPropertyName();
/* 78 */     request.append(" (");
/* 79 */     request.append(propertyName);
/* 80 */     request.append(") in (");
/* 81 */     request.append(subSelect);
/* 82 */     request.append(") ");
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 87 */     List<Object> bindParams = this.compiledSubQuery.getPredicates().getWhereExprBindValues();
/*    */     
/* 89 */     if (bindParams == null) {
/*    */       return;
/*    */     }
/*    */     
/* 93 */     for (int i = 0; i < bindParams.size(); i++)
/* 94 */       request.addBindValue(bindParams.get(i)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\InQueryExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */