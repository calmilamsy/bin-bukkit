/*    */ package com.avaje.ebeaninternal.server.jdbc;
/*    */ 
/*    */ import com.avaje.ebean.config.PstmtDelegate;
/*    */ import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
/*    */ import java.sql.PreparedStatement;
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
/*    */ public class StandardPstmtDelegate
/*    */   implements PstmtDelegate
/*    */ {
/* 40 */   public PreparedStatement unwrap(PreparedStatement pstmt) { return ((ExtendedPreparedStatement)pstmt).getDelegate(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\jdbc\StandardPstmtDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */