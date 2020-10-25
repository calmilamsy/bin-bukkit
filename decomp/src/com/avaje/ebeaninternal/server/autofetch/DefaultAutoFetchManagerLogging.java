/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.config.AutofetchConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import com.avaje.ebeaninternal.server.transaction.log.SimpleLogger;
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
/*     */ public class DefaultAutoFetchManagerLogging
/*     */ {
/*  23 */   private static final Logger logger = Logger.getLogger(DefaultAutoFetchManagerLogging.class.getName());
/*     */   
/*     */   private final SimpleLogger fileLogger;
/*     */   
/*     */   private final DefaultAutoFetchManager manager;
/*     */   
/*     */   private final boolean useFileLogger;
/*     */   
/*     */   private final boolean traceUsageCollection;
/*     */ 
/*     */   
/*     */   public DefaultAutoFetchManagerLogging(ServerConfig serverConfig, DefaultAutoFetchManager profileListener) {
/*  35 */     this.manager = profileListener;
/*     */     
/*  37 */     AutofetchConfig autofetchConfig = serverConfig.getAutofetchConfig();
/*     */     
/*  39 */     this.traceUsageCollection = GlobalProperties.getBoolean("ebean.autofetch.traceUsageCollection", false);
/*  40 */     this.useFileLogger = autofetchConfig.isUseFileLogging();
/*     */     
/*  42 */     if (!this.useFileLogger) {
/*  43 */       this.fileLogger = null;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  49 */       String baseDir = serverConfig.getLoggingDirectoryWithEval();
/*  50 */       this.fileLogger = new SimpleLogger(baseDir, "autofetch", true, "csv");
/*     */     } 
/*     */     
/*  53 */     int updateFreqInSecs = autofetchConfig.getProfileUpdateFrequency();
/*     */     
/*  55 */     BackgroundThread.add(updateFreqInSecs, new UpdateProfile(null));
/*     */   }
/*     */   
/*     */   private final class UpdateProfile
/*     */     implements Runnable {
/*  60 */     public void run() { DefaultAutoFetchManagerLogging.this.manager.updateTunedQueryInfo(); }
/*     */     
/*     */     private UpdateProfile() {} }
/*     */   
/*     */   public void logError(Level level, String msg, Throwable e) {
/*  65 */     if (this.useFileLogger) {
/*  66 */       String errMsg = (e == null) ? "" : e.getMessage();
/*  67 */       this.fileLogger.log("\"Error\",\"" + msg + " " + errMsg + "\",,,,");
/*     */     } 
/*  69 */     logger.log(level, msg, e);
/*     */   }
/*     */ 
/*     */   
/*  73 */   public void logToJavaLogger(String msg) { logger.info(msg); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void logSummary(String summaryInfo) {
/*  78 */     String msg = "\"Summary\",\"" + summaryInfo + "\",,,,";
/*     */     
/*  80 */     if (this.useFileLogger) {
/*  81 */       this.fileLogger.log(msg);
/*     */     }
/*  83 */     logger.fine(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void logChanged(TunedQueryInfo tunedFetch, OrmQueryDetail newQueryDetail) {
/*  88 */     String msg = tunedFetch.getLogOutput(newQueryDetail);
/*     */     
/*  90 */     if (this.useFileLogger) {
/*  91 */       this.fileLogger.log(msg);
/*     */     } else {
/*  93 */       logger.fine(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logNew(TunedQueryInfo tunedFetch) {
/*  99 */     String msg = tunedFetch.getLogOutput(null);
/*     */     
/* 101 */     if (this.useFileLogger) {
/* 102 */       this.fileLogger.log(msg);
/*     */     } else {
/* 104 */       logger.fine(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 109 */   public boolean isTraceUsageCollection() { return this.traceUsageCollection; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\autofetch\DefaultAutoFetchManagerLogging.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */