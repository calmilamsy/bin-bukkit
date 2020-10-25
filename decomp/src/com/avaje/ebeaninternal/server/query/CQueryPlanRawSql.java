/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import com.avaje.ebean.RawSql;
/*    */ import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ import com.avaje.ebeaninternal.server.type.DataReader;
/*    */ import com.avaje.ebeaninternal.server.type.RsetDataReaderIndexed;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.List;
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
/*    */ public class CQueryPlanRawSql
/*    */   extends CQueryPlan
/*    */ {
/*    */   private final int[] rsetIndexPositions;
/*    */   
/*    */   public CQueryPlanRawSql(OrmQueryRequest<?> request, SqlLimitResponse sqlRes, SqlTree sqlTree, String logWhereSql) {
/* 37 */     super(request, sqlRes, sqlTree, true, logWhereSql, null);
/*    */     
/* 39 */     this.rsetIndexPositions = createIndexPositions(request, sqlTree);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public DataReader createDataReader(ResultSet rset) { return new RsetDataReaderIndexed(rset, this.rsetIndexPositions, isRowNumberIncluded()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int[] createIndexPositions(OrmQueryRequest<?> request, SqlTree sqlTree) {
/* 50 */     List<String> chain = sqlTree.buildSelectExpressionChain();
/* 51 */     RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
/*    */     
/* 53 */     int[] indexPositions = new int[chain.size()];
/*    */     
/* 55 */     for (int i = 0; i < chain.size(); i++) {
/* 56 */       String expr = (String)chain.get(i);
/* 57 */       int indexPos = 1 + columnMapping.getIndexPosition(expr);
/* 58 */       indexPositions[i] = indexPos;
/*    */     } 
/*    */     
/* 61 */     return indexPositions;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\CQueryPlanRawSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */