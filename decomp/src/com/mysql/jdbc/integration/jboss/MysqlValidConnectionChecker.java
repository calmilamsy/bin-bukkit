/*    */ package com.mysql.jdbc.integration.jboss;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import org.jboss.resource.adapter.jdbc.ValidConnectionChecker;
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
/*    */ public final class MysqlValidConnectionChecker
/*    */   implements ValidConnectionChecker, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8909421133577519177L;
/*    */   
/*    */   public SQLException isValidConnection(Connection conn) {
/* 58 */     Statement pingStatement = null;
/*    */     
/*    */     try {
/* 61 */       pingStatement = conn.createStatement();
/*    */       
/* 63 */       pingStatement.executeQuery("/* ping */ SELECT 1").close();
/*    */       
/* 65 */       return null;
/* 66 */     } catch (SQLException sqlEx) {
/* 67 */       return sqlEx;
/*    */     } finally {
/* 69 */       if (pingStatement != null)
/*    */         try {
/* 71 */           pingStatement.close();
/* 72 */         } catch (SQLException sqlEx) {} 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\integration\jboss\MysqlValidConnectionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */