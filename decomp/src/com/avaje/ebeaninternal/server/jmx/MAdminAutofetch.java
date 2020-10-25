/*     */ package com.avaje.ebeaninternal.server.jmx;
/*     */ 
/*     */ import com.avaje.ebean.AdminAutofetch;
/*     */ import com.avaje.ebean.config.AutofetchMode;
/*     */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MAdminAutofetch
/*     */   implements MAdminAutofetchMBean, AdminAutofetch
/*     */ {
/*     */   final Logger logger;
/*     */   final AutoFetchManager autoFetchManager;
/*     */   final String modeOptions;
/*     */   
/*     */   public MAdminAutofetch(AutoFetchManager autoFetchListener) {
/*  19 */     this.logger = Logger.getLogger(MAdminAutofetch.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  26 */     this.autoFetchManager = autoFetchListener;
/*  27 */     this.modeOptions = AutofetchMode.DEFAULT_OFF + ", " + AutofetchMode.DEFAULT_ON + ", " + AutofetchMode.DEFAULT_ONIFEMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public boolean isQueryTuning() { return this.autoFetchManager.isQueryTuning(); }
/*     */ 
/*     */ 
/*     */   
/*  37 */   public void setQueryTuning(boolean enable) { this.autoFetchManager.setQueryTuning(enable); }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public boolean isProfiling() { return this.autoFetchManager.isProfiling(); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public void setProfiling(boolean enable) { this.autoFetchManager.setProfiling(enable); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public String getModeOptions() { return this.modeOptions; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public String getMode() { return this.autoFetchManager.getMode().name(); }
/*     */ 
/*     */   
/*     */   public void setMode(String implicitMode) {
/*     */     try {
/*  58 */       AutofetchMode mode = AutofetchMode.valueOf(implicitMode);
/*  59 */       this.autoFetchManager.setMode(mode);
/*  60 */     } catch (Exception e) {
/*  61 */       this.logger.info("Invalid implicit mode attempted " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  66 */   public String collectUsageViaGC() { return this.autoFetchManager.collectUsageViaGC(-1L); }
/*     */ 
/*     */ 
/*     */   
/*  70 */   public double getProfilingRate() { return this.autoFetchManager.getProfilingRate(); }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void setProfilingRate(double rate) { this.autoFetchManager.setProfilingRate(rate); }
/*     */ 
/*     */ 
/*     */   
/*  78 */   public int getProfilingMin() { return this.autoFetchManager.getProfilingMin(); }
/*     */ 
/*     */ 
/*     */   
/*  82 */   public int getProfilingBase() { return this.autoFetchManager.getProfilingBase(); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void setProfilingMin(int profilingMin) { this.autoFetchManager.setProfilingMin(profilingMin); }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setProfilingBase(int profilingMax) { this.autoFetchManager.setProfilingBase(profilingMax); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public String updateTunedQueryInfo() { return this.autoFetchManager.updateTunedQueryInfo(); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public int clearProfilingInfo() { return this.autoFetchManager.clearProfilingInfo(); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public int clearTunedQueryInfo() { return this.autoFetchManager.clearTunedQueryInfo(); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void clearQueryStatistics() { this.autoFetchManager.clearQueryStatistics(); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int getTotalProfileSize() { return this.autoFetchManager.getTotalProfileSize(); }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public int getTotalTunedQueryCount() { return this.autoFetchManager.getTotalTunedQueryCount(); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int getTotalTunedQuerySize() { return this.autoFetchManager.getTotalTunedQuerySize(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\jmx\MAdminAutofetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */