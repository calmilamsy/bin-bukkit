/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import com.mysql.jdbc.Connection;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.ConnectionPoolDataSource;
/*    */ import javax.sql.PooledConnection;
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
/*    */ public class MysqlConnectionPoolDataSource
/*    */   extends MysqlDataSource
/*    */   implements ConnectionPoolDataSource
/*    */ {
/*    */   public PooledConnection getPooledConnection() throws SQLException {
/* 59 */     Connection connection = getConnection();
/* 60 */     return MysqlPooledConnection.getInstance((Connection)connection);
/*    */   }
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
/*    */   public PooledConnection getPooledConnection(String s, String s1) throws SQLException {
/* 80 */     Connection connection = getConnection(s, s1);
/* 81 */     return MysqlPooledConnection.getInstance((Connection)connection);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\MysqlConnectionPoolDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */