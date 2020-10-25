/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import com.mysql.jdbc.log.Log;
/*    */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class ProfilerEventHandlerFactory
/*    */ {
/* 39 */   private static final Map CONNECTIONS_TO_SINKS = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Connection ownerConnection;
/*    */ 
/*    */ 
/*    */   
/*    */   private Log log;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ProfilerEventHandler getInstance(MySQLConnection conn) throws SQLException {
/* 54 */     ProfilerEventHandler handler = (ProfilerEventHandler)CONNECTIONS_TO_SINKS.get(conn);
/*    */ 
/*    */     
/* 57 */     if (handler == null) {
/* 58 */       handler = (ProfilerEventHandler)Util.getInstance(conn.getProfilerEventHandler(), new Class[0], new Object[0], conn.getExceptionInterceptor());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 63 */       conn.initializeExtension(handler);
/*    */       
/* 65 */       CONNECTIONS_TO_SINKS.put(conn, handler);
/*    */     } 
/*    */     
/* 68 */     return handler;
/*    */   }
/*    */   
/*    */   public static void removeInstance(Connection conn) {
/* 72 */     ProfilerEventHandler handler = (ProfilerEventHandler)CONNECTIONS_TO_SINKS.remove(conn);
/*    */     
/* 74 */     if (handler != null)
/* 75 */       handler.destroy(); 
/*    */   }
/*    */   private ProfilerEventHandlerFactory(Connection conn) {
/*    */     this.ownerConnection = null;
/*    */     this.log = null;
/* 80 */     this.ownerConnection = conn;
/*    */     
/*    */     try {
/* 83 */       this.log = this.ownerConnection.getLog();
/* 84 */     } catch (SQLException sqlEx) {
/* 85 */       throw new RuntimeException("Unable to get logger from connection");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ProfilerEventHandlerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */