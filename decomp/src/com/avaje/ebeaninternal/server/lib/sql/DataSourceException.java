/*    */ package com.avaje.ebeaninternal.server.lib.sql;
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
/*    */ public class DataSourceException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539844L;
/*    */   
/* 30 */   public DataSourceException(Exception cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public DataSourceException(String s, Exception cause) { super(s, cause); }
/*    */ 
/*    */ 
/*    */   
/* 38 */   public DataSourceException(String s) { super(s); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */