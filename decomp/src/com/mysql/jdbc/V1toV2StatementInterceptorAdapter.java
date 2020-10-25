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
/*    */ public class V1toV2StatementInterceptorAdapter
/*    */   implements StatementInterceptorV2
/*    */ {
/*    */   private final StatementInterceptor toProxy;
/*    */   
/* 29 */   public V1toV2StatementInterceptorAdapter(StatementInterceptor toProxy) { this.toProxy = toProxy; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException { return this.toProxy.postProcess(sql, interceptedStatement, originalResultSet, connection); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void destroy() { this.toProxy.destroy(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean executeTopLevelOnly() { return this.toProxy.executeTopLevelOnly(); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void init(Connection conn, Properties props) throws SQLException { this.toProxy.init(conn, props); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException { return this.toProxy.preProcess(sql, interceptedStatement, connection); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\V1toV2StatementInterceptorAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */