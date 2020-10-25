/*    */ package com.avaje.ebeaninternal.server.lib.cron;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class HelloWorld
/*    */   implements Runnable
/*    */ {
/* 30 */   private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());
/*    */ 
/*    */   
/* 33 */   public String toString() { return "Hello World"; }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 38 */       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS ");
/* 39 */       String now = sdf.format(new Date());
/* 40 */       logger.info("Hello World " + now + "  ... sleeping 20 secs");
/*    */       
/* 42 */       Thread.sleep(20000L);
/* 43 */       logger.info("Hello World finished.");
/*    */     }
/* 45 */     catch (InterruptedException ex) {
/* 46 */       logger.log(Level.SEVERE, "", ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\cron\HelloWorld.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */