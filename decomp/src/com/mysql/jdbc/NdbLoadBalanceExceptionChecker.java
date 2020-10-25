/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class NdbLoadBalanceExceptionChecker
/*    */   extends StandardLoadBalanceExceptionChecker
/*    */ {
/*  9 */   public boolean shouldExceptionTriggerFailover(SQLException ex) { return (super.shouldExceptionTriggerFailover(ex) || checkNdbException(ex)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   private boolean checkNdbException(SQLException ex) { return (ex.getMessage().startsWith("Lock wait timeout exceeded") || (ex.getMessage().startsWith("Got temporary error") && ex.getMessage().endsWith("from NDB"))); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\NdbLoadBalanceExceptionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */