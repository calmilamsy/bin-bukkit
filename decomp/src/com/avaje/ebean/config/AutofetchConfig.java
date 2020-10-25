/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutofetchConfig
/*     */ {
/*   8 */   private AutofetchMode mode = AutofetchMode.DEFAULT_ONIFEMPTY;
/*     */   
/*     */   private boolean queryTuning = false;
/*     */   
/*     */   private boolean queryTuningAddVersion = false;
/*     */   
/*     */   private boolean profiling = false;
/*     */   
/*  16 */   private int profilingMin = 1;
/*     */   
/*  18 */   private int profilingBase = 10;
/*     */   
/*  20 */   private double profilingRate = 0.05D;
/*     */   
/*     */   private boolean useFileLogging = false;
/*     */   
/*     */   private String logDirectory;
/*     */   
/*  26 */   private int profileUpdateFrequency = 60;
/*     */   
/*  28 */   private int garbageCollectionWait = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public AutofetchMode getMode() { return this.mode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public void setMode(AutofetchMode mode) { this.mode = mode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public boolean isQueryTuning() { return this.queryTuning; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public void setQueryTuning(boolean queryTuning) { this.queryTuning = queryTuning; }
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
/*  72 */   public boolean isQueryTuningAddVersion() { return this.queryTuningAddVersion; }
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
/*  84 */   public void setQueryTuningAddVersion(boolean queryTuningAddVersion) { this.queryTuningAddVersion = queryTuningAddVersion; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isProfiling() { return this.profiling; }
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
/* 102 */   public void setProfiling(boolean profiling) { this.profiling = profiling; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int getProfilingMin() { return this.profilingMin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setProfilingMin(int profilingMin) { this.profilingMin = profilingMin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public int getProfilingBase() { return this.profilingBase; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void setProfilingBase(int profilingBase) { this.profilingBase = profilingBase; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public double getProfilingRate() { return this.profilingRate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setProfilingRate(double profilingRate) { this.profilingRate = profilingRate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public boolean isUseFileLogging() { return this.useFileLogging; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public void setUseFileLogging(boolean useFileLogging) { this.useFileLogging = useFileLogging; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public String getLogDirectory() { return this.logDirectory; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getLogDirectoryWithEval() { return GlobalProperties.evaluateExpressions(this.logDirectory); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public void setLogDirectory(String logDirectory) { this.logDirectory = logDirectory; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public int getProfileUpdateFrequency() { return this.profileUpdateFrequency; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public void setProfileUpdateFrequency(int profileUpdateFrequency) { this.profileUpdateFrequency = profileUpdateFrequency; }
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
/* 219 */   public int getGarbageCollectionWait() { return this.garbageCollectionWait; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public void setGarbageCollectionWait(int garbageCollectionWait) { this.garbageCollectionWait = garbageCollectionWait; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadSettings(ConfigPropertyMap p) {
/* 235 */     this.logDirectory = p.get("autofetch.logDirectory", null);
/* 236 */     this.queryTuning = p.getBoolean("autofetch.querytuning", false);
/* 237 */     this.queryTuningAddVersion = p.getBoolean("autofetch.queryTuningAddVersion", false);
/*     */     
/* 239 */     this.profiling = p.getBoolean("autofetch.profiling", false);
/* 240 */     this.mode = (AutofetchMode)p.getEnum(AutofetchMode.class, "autofetch.implicitmode", AutofetchMode.DEFAULT_ONIFEMPTY);
/*     */     
/* 242 */     this.profilingMin = p.getInt("autofetch.profiling.min", 1);
/* 243 */     this.profilingBase = p.getInt("autofetch.profiling.base", 10);
/*     */     
/* 245 */     String rate = p.get("autofetch.profiling.rate", "0.05");
/* 246 */     this.profilingRate = Double.parseDouble(rate);
/*     */     
/* 248 */     this.useFileLogging = p.getBoolean("autofetch.useFileLogging", this.profiling);
/* 249 */     this.profileUpdateFrequency = p.getInt("autofetch.profiling.updatefrequency", 60);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\AutofetchConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */