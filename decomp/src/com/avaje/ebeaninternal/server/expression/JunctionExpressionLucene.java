/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpression;
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JunctionExpressionLucene
/*    */ {
/*    */   public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, List<SpiExpression> list, boolean disjunction) {
/* 42 */     BooleanClause.Occur occur = disjunction ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
/*    */     
/* 44 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 46 */     BooleanQuery bq = new BooleanQuery();
/* 47 */     for (int i = 0; i < list.size(); i++) {
/* 48 */       SpiLuceneExpr luceneExpr = ((SpiExpression)list.get(i)).createLuceneExpr(request);
/* 49 */       Query query = luceneExpr.mergeLuceneQuery();
/* 50 */       bq.add(query, occur);
/*    */       
/* 52 */       if (i > 0) {
/* 53 */         sb.append(" ").append(occur).append(" ");
/*    */       }
/* 55 */       sb.append(luceneExpr.getDescription());
/*    */     } 
/*    */     
/* 58 */     return new LuceneExprResponse(bq, sb.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\JunctionExpressionLucene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */