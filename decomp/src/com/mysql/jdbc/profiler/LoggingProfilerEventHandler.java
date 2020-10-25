/*    */ package com.mysql.jdbc.profiler;
/*    */ 
/*    */ import com.mysql.jdbc.Connection;
/*    */ import com.mysql.jdbc.log.Log;
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
/*    */ public class LoggingProfilerEventHandler
/*    */   implements ProfilerEventHandler
/*    */ {
/*    */   private Log log;
/*    */   
/*    */   public void consumeEvent(ProfilerEvent evt) {
/* 44 */     if (evt.eventType == 0) {
/* 45 */       this.log.logWarn(evt);
/*    */     } else {
/* 47 */       this.log.logInfo(evt);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 52 */   public void destroy() { this.log = null; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public void init(Connection conn, Properties props) throws SQLException { this.log = conn.getLog(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\profiler\LoggingProfilerEventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */