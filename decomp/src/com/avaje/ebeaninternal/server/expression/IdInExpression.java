/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebeaninternal.api.ManyWhereJoins;
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*    */ import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
/*    */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IdInExpression
/*    */   implements SpiExpression
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<?> idList;
/*    */   
/* 26 */   public IdInExpression(List<?> idList) { this.idList = idList; }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean isLuceneResolvable(LuceneResolvableRequest req) { return false; }
/*    */ 
/*    */   
/* 33 */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void addBindValues(SpiExpressionRequest request) {
/* 43 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 44 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 45 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 47 */     for (int i = 0; i < this.idList.size(); i++) {
/* 48 */       idBinder.addIdInBindValue(request, this.idList.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSqlNoAlias(SpiExpressionRequest request) {
/* 57 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 58 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 59 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 61 */     request.append(descriptor.getIdBinder().getBindIdInSql(null));
/* 62 */     String inClause = idBinder.getIdInValueExpr(this.idList.size());
/* 63 */     request.append(inClause);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSql(SpiExpressionRequest request) {
/* 68 */     DefaultExpressionRequest r = (DefaultExpressionRequest)request;
/* 69 */     BeanDescriptor<?> descriptor = r.getBeanDescriptor();
/* 70 */     IdBinder idBinder = descriptor.getIdBinder();
/*    */     
/* 72 */     request.append(descriptor.getIdBinderInLHSSql());
/* 73 */     String inClause = idBinder.getIdInValueExpr(this.idList.size());
/* 74 */     request.append(inClause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int queryAutoFetchHash() {
/* 83 */     hc = IdInExpression.class.getName().hashCode();
/* 84 */     return hc * 31 + this.idList.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 89 */   public int queryPlanHash(BeanQueryRequest<?> request) { return queryAutoFetchHash(); }
/*    */ 
/*    */ 
/*    */   
/* 93 */   public int queryBindHash() { return this.idList.hashCode(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\IdInExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */