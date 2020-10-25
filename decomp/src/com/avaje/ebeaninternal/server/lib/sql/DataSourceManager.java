/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class DataSourceManager
/*     */   implements DataSourceNotify
/*     */ {
/*  39 */   private static final Logger logger = Logger.getLogger(DataSourceManager.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private final Hashtable<String, DataSourcePool> dsMap = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final Object monitor = new Object();
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
/*  81 */   private final DataSourceAlertListener alertlistener = createAlertListener();
/*     */ 
/*     */   
/*  84 */   private final int dbUpFreqInSecs = GlobalProperties.getInt("datasource.heartbeatfreq", 30);
/*  85 */   private final int dbDownFreqInSecs = GlobalProperties.getInt("datasource.deadbeatfreq", 10);
/*  86 */   private final BackgroundRunnable dbChecker = new BackgroundRunnable(new Checker(this, null), this.dbUpFreqInSecs);
/*     */   public DataSourceManager() {
/*     */     try {
/*  89 */       BackgroundThread.add(this.dbChecker);
/*     */     }
/*  91 */     catch (Exception e) {
/*  92 */       logger.log(Level.SEVERE, null, e);
/*     */     } 
/*     */   }
/*     */   private boolean shuttingDown;
/*     */   
/*     */   private DataSourceAlertListener createAlertListener() throws DataSourceException {
/*  98 */     String alertCN = GlobalProperties.get("datasource.alert.class", null);
/*  99 */     if (alertCN == null) {
/* 100 */       return new SimpleAlerter();
/*     */     }
/*     */     
/*     */     try {
/* 104 */       return (DataSourceAlertListener)ClassUtil.newInstance(alertCN, getClass());
/*     */     }
/* 106 */     catch (Exception ex) {
/* 107 */       throw new DataSourceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataSourceUp(String dataSourceName) {
/* 117 */     this.dbChecker.setFreqInSecs(this.dbUpFreqInSecs);
/*     */     
/* 119 */     if (this.alertlistener != null) {
/* 120 */       this.alertlistener.dataSourceUp(dataSourceName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataSourceDown(String dataSourceName) {
/* 129 */     this.dbChecker.setFreqInSecs(this.dbDownFreqInSecs);
/*     */     
/* 131 */     if (this.alertlistener != null) {
/* 132 */       this.alertlistener.dataSourceDown(dataSourceName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyWarning(String subject, String msg) {
/* 140 */     if (this.alertlistener != null) {
/* 141 */       this.alertlistener.warning(subject, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 149 */     synchronized (this.monitor) {
/* 150 */       return this.shuttingDown;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 159 */     synchronized (this.monitor) {
/*     */       
/* 161 */       this.shuttingDown = true;
/*     */       
/* 163 */       Iterator<DataSourcePool> it = this.dsMap.values().iterator();
/* 164 */       while (it.hasNext()) {
/*     */         try {
/* 166 */           DataSourcePool ds = (DataSourcePool)it.next();
/* 167 */           ds.shutdown();
/*     */         }
/* 169 */         catch (DataSourceException e) {
/*     */           
/* 171 */           logger.log(Level.SEVERE, null, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DataSourcePool> getPools() {
/* 181 */     synchronized (this.monitor) {
/*     */       
/* 183 */       ArrayList<DataSourcePool> list = new ArrayList<DataSourcePool>();
/* 184 */       list.addAll(this.dsMap.values());
/* 185 */       return list;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public DataSourcePool getDataSource(String name) { return getDataSource(name, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) {
/* 199 */     if (name == null) {
/* 200 */       throw new IllegalArgumentException("name not defined");
/*     */     }
/*     */     
/* 203 */     synchronized (this.monitor) {
/* 204 */       DataSourcePool pool = (DataSourcePool)this.dsMap.get(name);
/* 205 */       if (pool == null) {
/* 206 */         if (dsConfig == null) {
/* 207 */           dsConfig = new DataSourceConfig();
/* 208 */           dsConfig.loadSettings(name);
/*     */         } 
/* 210 */         pool = new DataSourcePool(this, name, dsConfig);
/* 211 */         this.dsMap.put(name, pool);
/*     */       } 
/* 213 */       return pool;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDataSource() {
/* 223 */     synchronized (this.monitor) {
/* 224 */       if (!isShuttingDown()) {
/* 225 */         Iterator<DataSourcePool> it = this.dsMap.values().iterator();
/* 226 */         while (it.hasNext()) {
/* 227 */           DataSourcePool ds = (DataSourcePool)it.next();
/* 228 */           ds.checkDataSource();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Checker
/*     */     implements Runnable
/*     */   {
/*     */     private Checker() {}
/*     */     
/* 240 */     public void run() { DataSourceManager.this.checkDataSource(); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */