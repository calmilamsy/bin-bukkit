/*    */ package com.avaje.ebeaninternal.server.lib.sql;
/*    */ 
/*    */ import com.avaje.ebean.config.DataSourceConfig;
/*    */ import java.util.List;
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
/*    */ public final class DataSourceGlobalManager
/*    */ {
/* 29 */   private static final DataSourceManager manager = new DataSourceManager();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   public static boolean isShuttingDown() { return manager.isShuttingDown(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public static void shutdown() { manager.shutdown(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static List<DataSourcePool> getPools() { return manager.getPools(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static DataSourcePool getDataSource(String name) { return manager.getDataSource(name); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public static DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) { return manager.getDataSource(name, dsConfig); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceGlobalManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */