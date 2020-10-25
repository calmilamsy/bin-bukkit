/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ReflectiveStatementInterceptorAdapter
/*    */   implements StatementInterceptorV2
/*    */ {
/*    */   private final StatementInterceptor toProxy;
/*    */   final Method v2PostProcessMethod;
/*    */   
/*    */   public ReflectiveStatementInterceptorAdapter(StatementInterceptor toProxy) {
/* 35 */     this.toProxy = toProxy;
/* 36 */     this.v2PostProcessMethod = getV2PostProcessMethod(toProxy.getClass());
/*    */   }
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
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException {
/*    */     try {
/* 58 */       return (ResultSetInternalMethods)this.v2PostProcessMethod.invoke(this.toProxy, new Object[] { sql, interceptedStatement, originalResultSet, connection, new Integer(warningCount), noIndexUsed ? Boolean.TRUE : Boolean.FALSE, noGoodIndexUsed ? Boolean.TRUE : Boolean.FALSE, statementException });
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 63 */     catch (IllegalArgumentException e) {
/* 64 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 65 */       sqlEx.initCause(e);
/*    */       
/* 67 */       throw sqlEx;
/* 68 */     } catch (IllegalAccessException e) {
/* 69 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 70 */       sqlEx.initCause(e);
/*    */       
/* 72 */       throw sqlEx;
/* 73 */     } catch (InvocationTargetException e) {
/* 74 */       SQLException sqlEx = new SQLException("Unable to reflectively invoke interceptor");
/* 75 */       sqlEx.initCause(e);
/*    */       
/* 77 */       throw sqlEx;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException { return this.toProxy.preProcess(sql, interceptedStatement, connection); }
/*    */ 
/*    */   
/*    */   public static final Method getV2PostProcessMethod(Class toProxyClass) {
/*    */     try {
/* 89 */       return toProxyClass.getMethod("postProcess", new Class[] { String.class, Statement.class, ResultSetInternalMethods.class, Connection.class, int.class, boolean.class, boolean.class, SQLException.class });
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 94 */     catch (SecurityException e) {
/* 95 */       return null;
/* 96 */     } catch (NoSuchMethodException e) {
/* 97 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ReflectiveStatementInterceptorAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */