/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.type.DataReader;
/*    */ import java.sql.ResultSet;
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
/*    */ public class CQueryPlanLucene
/*    */   extends CQueryPlan
/*    */ {
/*    */   private final OrmQueryRequest<?> request;
/*    */   
/*    */   public CQueryPlanLucene(OrmQueryRequest<?> request, SqlTree sqlTree) {
/* 34 */     super(request, null, sqlTree, false, "", getLuceneDescription(request));
/* 35 */     this.request = request;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean isLucene() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public DataReader createDataReader(ResultSet rset) { return new LuceneIndexDataReader(this.request); }
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getLuceneDescription(OrmQueryRequest<?> request) {
/* 50 */     LuceneOrmQueryRequest req = request.getLuceneOrmQueryRequest();
/* 51 */     String description = req.getDescription();
/* 52 */     String sortDesc = req.getSortDesc();
/* 53 */     BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
/*    */     
/* 55 */     StringBuilder sb = new StringBuilder();
/* 56 */     sb.append("lucene query from ").append(beanDescriptor.getName());
/* 57 */     sb.append(" ").append(description);
/* 58 */     if (sortDesc != null && sortDesc.length() > 0) {
/* 59 */       sb.append(" order by ").append(sortDesc);
/*    */     }
/* 61 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryPlanLucene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */