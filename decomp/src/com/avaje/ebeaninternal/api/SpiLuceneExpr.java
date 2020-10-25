/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
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
/*    */ public interface SpiLuceneExpr
/*    */ {
/*    */   Query mergeLuceneQuery();
/*    */   
/*    */   String getDescription();
/*    */   
/*    */   public enum ExprOccur
/*    */   {
/* 34 */     MUST,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     SHOULD,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     MUST_NOT;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\SpiLuceneExpr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */