/*    */ package com.avaje.ebeaninternal.server.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
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
/*    */ public class LuceneExprResponse
/*    */   implements SpiLuceneExpr
/*    */ {
/*    */   private final Query query;
/*    */   private final String description;
/*    */   
/*    */   public LuceneExprResponse(Query query, String description) {
/* 33 */     this.query = query;
/* 34 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/* 38 */   public Query mergeLuceneQuery() { return this.query; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String getDescription() { return this.description; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\expression\LuceneExprResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */