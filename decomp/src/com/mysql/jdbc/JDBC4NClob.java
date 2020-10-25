/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.NClob;
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
/*    */ public class JDBC4NClob
/*    */   extends Clob
/*    */   implements NClob
/*    */ {
/* 39 */   JDBC4NClob(ExceptionInterceptor exceptionInterceptor) { super(exceptionInterceptor); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   JDBC4NClob(String charDataInit, ExceptionInterceptor exceptionInterceptor) { super(charDataInit, exceptionInterceptor); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4NClob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */