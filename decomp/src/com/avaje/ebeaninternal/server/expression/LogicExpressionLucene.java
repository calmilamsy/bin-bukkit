/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import org.apache.lucene.search.BooleanClause;
/*    */ import org.apache.lucene.search.BooleanQuery;
/*    */ import org.apache.lucene.search.Query;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogicExpressionLucene
/*    */ {
/*    */   public SpiLuceneExpr addLuceneQuery(String joinType, SpiExpressionRequest request, SpiExpression expOne, SpiExpression expTwo) {
/* 34 */     SpiLuceneExpr e1 = expOne.createLuceneExpr(request);
/* 35 */     SpiLuceneExpr e2 = expTwo.createLuceneExpr(request);
/*    */     
/* 37 */     Query q1 = e1.mergeLuceneQuery();
/* 38 */     Query q2 = e2.mergeLuceneQuery();
/*    */     
/* 40 */     BooleanQuery q = new BooleanQuery();
/* 41 */     BooleanClause.Occur occur = " or ".equals(joinType) ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
/*    */     
/* 43 */     q.add(q1, occur);
/* 44 */     q.add(q2, occur);
/*    */     
/* 46 */     String desc = e1.getDescription() + joinType + e2.getDescription();
/* 47 */     return new LuceneExprResponse(q, desc);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LogicExpressionLucene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */