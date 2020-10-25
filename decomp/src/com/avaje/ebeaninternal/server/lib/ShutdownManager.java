/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShutdownManager
/*     */ {
/*  39 */   private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
/*     */   
/*  41 */   static final Vector<Runnable> runnables = new Vector();
/*     */   
/*     */   static boolean stopping;
/*     */   
/*     */   static BootupEbeanManager serverFactory;
/*     */   
/*  47 */   static final ShutdownHook shutdownHook = new ShutdownHook();
/*     */   
/*     */   static boolean whyShutdown;
/*     */ 
/*     */   
/*     */   static  {
/*  53 */     register();
/*  54 */     whyShutdown = GlobalProperties.getBoolean("debug.shutdown.why", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static void registerServerFactory(BootupEbeanManager factory) { serverFactory = factory; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void touch() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStopping() {
/*  77 */     synchronized (runnables) {
/*  78 */       return stopping;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void deregister() {
/*  90 */     synchronized (runnables) {
/*     */       try {
/*  92 */         Runtime.getRuntime().removeShutdownHook(shutdownHook);
/*  93 */       } catch (IllegalStateException ex) {
/*  94 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/*  95 */           throw ex;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void register() {
/* 105 */     synchronized (runnables) {
/*     */       try {
/* 107 */         Runtime.getRuntime().addShutdownHook(shutdownHook);
/* 108 */       } catch (IllegalStateException ex) {
/* 109 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/* 110 */           throw ex;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 129 */     synchronized (runnables) {
/* 130 */       if (stopping) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 135 */       if (whyShutdown) {
/*     */         try {
/* 137 */           throw new RuntimeException("debug.shutdown.why=true ...");
/* 138 */         } catch (Throwable e) {
/* 139 */           logger.log(Level.WARNING, "Stacktrace showing why shutdown was fired", e);
/*     */         } 
/*     */       }
/*     */       
/* 143 */       stopping = true;
/*     */ 
/*     */       
/* 146 */       deregister();
/*     */ 
/*     */       
/* 149 */       BackgroundThread.shutdown();
/*     */       
/* 151 */       String shutdownRunner = GlobalProperties.get("system.shutdown.runnable", null);
/* 152 */       if (shutdownRunner != null) {
/*     */         try {
/* 154 */           Runnable r = (Runnable)ClassUtil.newInstance(shutdownRunner);
/* 155 */           r.run();
/* 156 */         } catch (Exception e) {
/* 157 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 163 */       Enumeration<Runnable> e = runnables.elements();
/* 164 */       while (e.hasMoreElements()) {
/*     */         try {
/* 166 */           Runnable r = (Runnable)e.nextElement();
/* 167 */           r.run();
/* 168 */         } catch (Exception ex) {
/* 169 */           logger.log(Level.SEVERE, null, ex);
/* 170 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 177 */         if (serverFactory != null) {
/* 178 */           serverFactory.shutdown();
/*     */         }
/*     */         
/* 181 */         ThreadPoolManager.shutdown();
/*     */         
/* 183 */         DataSourceGlobalManager.shutdown();
/*     */       }
/* 185 */       catch (Exception ex) {
/* 186 */         String msg = "Shutdown Exception: " + ex.getMessage();
/* 187 */         System.err.println(msg);
/* 188 */         ex.printStackTrace();
/*     */         try {
/* 190 */           logger.log(Level.SEVERE, null, ex);
/* 191 */         } catch (Exception exc) {
/* 192 */           String ms = "Error Logging error to the Log. It may be shutting down.";
/* 193 */           System.err.println(ms);
/* 194 */           exc.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Runnable runnable) {
/* 206 */     synchronized (runnables) {
/* 207 */       runnables.add(runnable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\ShutdownManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */