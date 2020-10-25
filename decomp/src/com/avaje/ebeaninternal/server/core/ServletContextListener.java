/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.Ebean;
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
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
/*    */ public class ServletContextListener
/*    */   implements ServletContextListener
/*    */ {
/* 41 */   private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 47 */   public void contextDestroyed(ServletContextEvent event) { ShutdownManager.shutdown(); }
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
/*    */   public void contextInitialized(ServletContextEvent event) {
/*    */     try {
/* 60 */       ServletContext servletContext = event.getServletContext();
/* 61 */       GlobalProperties.setServletContext(servletContext);
/*    */       
/* 63 */       if (servletContext != null) {
/* 64 */         String servletRealPath = servletContext.getRealPath("");
/* 65 */         GlobalProperties.put("servlet.realpath", servletRealPath);
/* 66 */         logger.info("servlet.realpath=[" + servletRealPath + "]");
/*    */       } 
/*    */       
/* 69 */       Ebean.getServer(null);
/*    */     }
/* 71 */     catch (Exception ex) {
/* 72 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\ServletContextListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */