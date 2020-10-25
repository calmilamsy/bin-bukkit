/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class EbeanServerFactory
/*     */ {
/*  55 */   private static final Logger logger = Logger.getLogger(EbeanServerFactory.class.getName());
/*     */   
/*  57 */   private static BootupEbeanManager serverFactory = createServerFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static EbeanServer create(String name) { return serverFactory.createServer(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EbeanServer create(ServerConfig config) {
/*  74 */     if (config.getName() == null) {
/*  75 */       throw new PersistenceException("The name is null (it is required)");
/*     */     }
/*     */     
/*  78 */     EbeanServer server = serverFactory.createServer(config);
/*     */     
/*  80 */     if (config.isDefaultServer()) {
/*  81 */       GlobalProperties.setSkipPrimaryServer(true);
/*     */     }
/*  83 */     if (config.isRegister()) {
/*  84 */       Ebean.register(server, config.isDefaultServer());
/*     */     }
/*     */     
/*  87 */     return server;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BootupEbeanManager createServerFactory() {
/*  93 */     dflt = "com.avaje.ebeaninternal.server.core.DefaultServerFactory";
/*  94 */     String implClassName = GlobalProperties.get("ebean.serverfactory", dflt);
/*     */     
/*  96 */     int delaySecs = GlobalProperties.getInt("ebean.start.delay", 0);
/*  97 */     if (delaySecs > 0) {
/*     */       
/*     */       try {
/*     */         
/* 101 */         String m = "Ebean sleeping " + delaySecs + " seconds due to ebean.start.delay";
/* 102 */         logger.log(Level.INFO, m);
/* 103 */         Thread.sleep((delaySecs * 1000));
/*     */       }
/* 105 */       catch (InterruptedException e) {
/* 106 */         String m = "Interrupting debug.start.delay of " + delaySecs;
/* 107 */         logger.log(Level.SEVERE, m, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 112 */       return (BootupEbeanManager)ClassUtil.newInstance(implClassName);
/* 113 */     } catch (Exception ex) {
/* 114 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\EbeanServerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */