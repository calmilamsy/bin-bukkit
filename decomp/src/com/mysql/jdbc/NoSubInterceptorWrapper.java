/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
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
/*    */ public class NoSubInterceptorWrapper
/*    */   implements StatementInterceptorV2
/*    */ {
/*    */   private final StatementInterceptorV2 underlyingInterceptor;
/*    */   
/*    */   public NoSubInterceptorWrapper(StatementInterceptorV2 underlyingInterceptor) {
/* 34 */     if (underlyingInterceptor == null) {
/* 35 */       throw new RuntimeException("Interceptor to be wrapped can not be NULL");
/*    */     }
/*    */     
/* 38 */     this.underlyingInterceptor = underlyingInterceptor;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public void destroy() { this.underlyingInterceptor.destroy(); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public boolean executeTopLevelOnly() { return this.underlyingInterceptor.executeTopLevelOnly(); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void init(Connection conn, Properties props) throws SQLException { this.underlyingInterceptor.init(conn, props); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException {
/* 58 */     this.underlyingInterceptor.postProcess(sql, interceptedStatement, originalResultSet, connection, warningCount, noIndexUsed, noGoodIndexUsed, statementException);
/*    */ 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
/* 67 */     this.underlyingInterceptor.preProcess(sql, interceptedStatement, connection);
/*    */     
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   
/* 73 */   public StatementInterceptorV2 getUnderlyingInterceptor() { return this.underlyingInterceptor; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\NoSubInterceptorWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */