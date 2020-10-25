/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import org.apache.lucene.search.Query;
/*    */ import org.apache.lucene.search.Sort;
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
/*    */ public class LuceneOrmQueryRequest
/*    */ {
/*    */   private final Query luceneQuery;
/*    */   private final Sort luceneSort;
/*    */   private final String description;
/*    */   private final String sortDesc;
/*    */   
/*    */   public LuceneOrmQueryRequest(Query luceneQuery, Sort luceneSort, String description, String sortDesc) {
/* 36 */     this.luceneQuery = luceneQuery;
/* 37 */     this.luceneSort = luceneSort;
/* 38 */     this.description = description;
/* 39 */     this.sortDesc = sortDesc;
/*    */   }
/*    */ 
/*    */   
/* 43 */   public Query getLuceneQuery() { return this.luceneQuery; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public Sort getLuceneSort() { return this.luceneSort; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public String getDescription() { return this.description; }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String getSortDesc() { return this.sortDesc; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\LuceneOrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */