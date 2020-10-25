/*    */ package com.mysql.jdbc.interceptors;
/*    */ 
/*    */ import com.mysql.jdbc.Connection;
/*    */ import com.mysql.jdbc.ResultSetInternalMethods;
/*    */ import com.mysql.jdbc.Statement;
/*    */ import com.mysql.jdbc.StatementInterceptor;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class SessionAssociationInterceptor
/*    */   implements StatementInterceptor
/*    */ {
/*    */   protected String currentSessionKey;
/* 15 */   protected static ThreadLocal sessionLocal = new ThreadLocal();
/*    */ 
/*    */   
/* 18 */   public static final void setSessionKey(String key) { sessionLocal.set(key); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final void resetSessionKey() { sessionLocal.set(null); }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public static final String getSessionKey() { return (String)sessionLocal.get(); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public boolean executeTopLevelOnly() { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(Connection conn, Properties props) throws SQLException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection) throws SQLException { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
/* 47 */     String key = getSessionKey();
/*    */     
/* 49 */     if (key != null && !key.equals(this.currentSessionKey)) {
/* 50 */       PreparedStatement pstmt = connection.clientPrepareStatement("SET @mysql_proxy_session=?");
/*    */       
/*    */       try {
/* 53 */         pstmt.setString(1, key);
/* 54 */         pstmt.execute();
/*    */       } finally {
/* 56 */         pstmt.close();
/*    */       } 
/*    */       
/* 59 */       this.currentSessionKey = key;
/*    */     } 
/*    */     
/* 62 */     return null;
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\interceptors\SessionAssociationInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */