/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.OrderBy;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionList;
/*     */ import com.avaje.ebeaninternal.api.SpiLuceneExpr;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.lucene.LLuceneSortResolve;
/*     */ import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ import org.apache.lucene.search.MatchAllDocsQuery;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.Sort;
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
/*     */ public class CQueryPredicatesLuceneResolve
/*     */ {
/*  44 */   private static final Logger logger = Logger.getLogger(CQueryPredicatesLuceneResolve.class.getName());
/*     */ 
/*     */   
/*     */   private final OrmQueryRequest<?> request;
/*     */ 
/*     */   
/*     */   private final SpiQuery<?> query;
/*     */   
/*     */   private final BindParams bindParams;
/*     */ 
/*     */   
/*     */   public CQueryPredicatesLuceneResolve(OrmQueryRequest<?> request) {
/*  56 */     this.request = request;
/*  57 */     this.query = request.getQuery();
/*  58 */     this.bindParams = this.query.getBindParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLuceneResolvable() {
/*  70 */     LIndex luceneIndex = this.request.getLuceneIndex();
/*  71 */     if (luceneIndex == null)
/*     */     {
/*  73 */       return false;
/*     */     }
/*  75 */     if (this.bindParams != null)
/*     */     {
/*  77 */       return false;
/*     */     }
/*  79 */     if (this.query.getHavingExpressions() != null)
/*     */     {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     LuceneResolvableRequest req = new LuceneResolvableRequest(this.request.getBeanDescriptor(), luceneIndex);
/*     */     
/*  86 */     LLuceneSortResolve lucenSortResolve = new LLuceneSortResolve(req, this.query.getOrderBy());
/*     */     
/*  88 */     if (!lucenSortResolve.isResolved()) {
/*  89 */       logger.info("Lucene Index can't support sort/orderBy of [" + lucenSortResolve.getUnsortableField() + "]");
/*  90 */       return false;
/*     */     } 
/*     */     
/*  93 */     Sort luceneSort = lucenSortResolve.getSort();
/*     */     
/*  95 */     OrderBy<?> orderBy = this.query.getOrderBy();
/*  96 */     String sortDesc = (orderBy == null) ? "" : orderBy.toStringFormat();
/*     */     
/*  98 */     SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
/*  99 */     if (whereExp == null) {
/*     */       
/* 101 */       MatchAllDocsQuery q = new MatchAllDocsQuery();
/* 102 */       this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest(q, luceneSort, "MatchAllDocs", sortDesc));
/* 103 */       return true;
/*     */     } 
/*     */     
/* 106 */     if (!whereExp.isLuceneResolvable(req))
/*     */     {
/* 108 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 113 */       DefaultExpressionRequest whereReq = new DefaultExpressionRequest(this.request, luceneIndex);
/* 114 */       SpiLuceneExpr luceneExpr = whereExp.createLuceneExpr(whereReq, SpiLuceneExpr.ExprOccur.MUST);
/*     */       
/* 116 */       Query luceneQuery = luceneExpr.mergeLuceneQuery();
/* 117 */       String luceneDesc = luceneExpr.getDescription();
/* 118 */       this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest(luceneQuery, luceneSort, luceneDesc, sortDesc));
/* 119 */       return true;
/*     */     }
/* 121 */     catch (PersistenceLuceneParseException e) {
/*     */       
/* 123 */       String msg = "Failed to parse the Query using Lucene";
/* 124 */       throw new PersistenceException(msg, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryPredicatesLuceneResolve.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */