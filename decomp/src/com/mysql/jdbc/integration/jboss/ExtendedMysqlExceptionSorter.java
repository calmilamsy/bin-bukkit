/*    */ package com.mysql.jdbc.integration.jboss;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.jboss.resource.adapter.jdbc.vendor.MySQLExceptionSorter;
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
/*    */ public final class ExtendedMysqlExceptionSorter
/*    */   extends MySQLExceptionSorter
/*    */ {
/*    */   public boolean isExceptionFatal(SQLException ex) {
/* 45 */     String sqlState = ex.getSQLState();
/*    */     
/* 47 */     if (sqlState != null && sqlState.startsWith("08")) {
/* 48 */       return true;
/*    */     }
/*    */     
/* 51 */     return super.isExceptionFatal(ex);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\integration\jboss\ExtendedMysqlExceptionSorter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */