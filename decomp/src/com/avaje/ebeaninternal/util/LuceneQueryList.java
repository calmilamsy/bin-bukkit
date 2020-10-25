/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*    */ import java.util.ArrayList;
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
/*    */ public class LuceneQueryList
/*    */   implements SpiLuceneExpr
/*    */ {
/*    */   private final SpiLuceneExpr.ExprOccur localOccur;
/*    */   private final ArrayList<SpiLuceneExpr> list;
/*    */   private String description;
/*    */   
/*    */   public LuceneQueryList(SpiLuceneExpr.ExprOccur loccur) {
/* 35 */     this.list = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.localOccur = loccur;
/*    */   }
/*    */ 
/*    */   
/* 44 */   public void add(SpiLuceneExpr q) { this.list.add(q); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public ArrayList<SpiLuceneExpr> getList() { return this.list; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String getDescription() { return this.description; }
/*    */ 
/*    */ 
/*    */   
/*    */   public Query mergeLuceneQuery() {
/* 57 */     BooleanClause.Occur luceneOccur = getLuceneOccur();
/*    */     
/* 59 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 61 */     BooleanQuery bq = new BooleanQuery();
/* 62 */     for (int i = 0; i < this.list.size(); i++) {
/* 63 */       SpiLuceneExpr luceneExpr = (SpiLuceneExpr)this.list.get(i);
/* 64 */       Query lucQuery = luceneExpr.mergeLuceneQuery();
/* 65 */       bq.add(lucQuery, luceneOccur);
/*    */       
/* 67 */       if (i > 0) {
/* 68 */         sb.append(" ").append(luceneOccur).append(" ");
/*    */       }
/* 70 */       sb.append(luceneExpr.getDescription());
/*    */     } 
/*    */     
/* 73 */     this.description = sb.toString();
/* 74 */     return bq;
/*    */   }
/*    */   
/*    */   private BooleanClause.Occur getLuceneOccur() {
/* 78 */     switch (this.localOccur) {
/*    */       case MUST:
/* 80 */         return BooleanClause.Occur.MUST;
/*    */       case MUST_NOT:
/* 82 */         return BooleanClause.Occur.MUST_NOT;
/*    */       case SHOULD:
/* 84 */         return BooleanClause.Occur.SHOULD;
/*    */     } 
/*    */     
/* 87 */     throw new RuntimeException("Invalid type " + this.localOccur);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninterna\\util\LuceneQueryList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */