/*     */ package com.mysql.jdbc.jmx;
/*     */ 
/*     */ import com.mysql.jdbc.ConnectionGroupManager;
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.sql.SQLException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
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
/*     */ public class LoadBalanceConnectionGroupManager
/*     */   implements LoadBalanceConnectionGroupManagerMBean
/*     */ {
/*     */   private boolean isJmxRegistered = false;
/*     */   
/*     */   public void registerJmx() {
/*  44 */     if (this.isJmxRegistered) {
/*     */       return;
/*     */     }
/*  47 */     MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
/*     */     try {
/*  49 */       ObjectName name = new ObjectName("com.mysql.jdbc.jmx:type=LoadBalanceConnectionGroupManager");
/*  50 */       mbs.registerMBean(this, name);
/*  51 */       this.isJmxRegistered = true;
/*  52 */     } catch (Exception e) {
/*  53 */       throw SQLError.createSQLException("Uable to register load-balance management bean with JMX", null, e, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHost(String group, String host, boolean forExisting) {
/*     */     try {
/*  60 */       ConnectionGroupManager.addHost(group, host, forExisting);
/*  61 */     } catch (Exception e) {
/*  62 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  67 */   public int getActiveHostCount(String group) { return ConnectionGroupManager.getActiveHostCount(group); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public long getActiveLogicalConnectionCount(String group) { return ConnectionGroupManager.getActiveLogicalConnectionCount(group); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public long getActivePhysicalConnectionCount(String group) { return ConnectionGroupManager.getActivePhysicalConnectionCount(group); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int getTotalHostCount(String group) { return ConnectionGroupManager.getTotalHostCount(group); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public long getTotalLogicalConnectionCount(String group) { return ConnectionGroupManager.getTotalLogicalConnectionCount(group); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public long getTotalPhysicalConnectionCount(String group) { return ConnectionGroupManager.getTotalPhysicalConnectionCount(group); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public long getTotalTransactionCount(String group) { return ConnectionGroupManager.getTotalTransactionCount(group); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public void removeHost(String group, String host) throws SQLException { ConnectionGroupManager.removeHost(group, host); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public String getActiveHostsList(String group) { return ConnectionGroupManager.getActiveHostLists(group); }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public String getRegisteredConnectionGroups() { return ConnectionGroupManager.getRegisteredConnectionGroups(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void stopNewConnectionsToHost(String group, String host) throws SQLException { ConnectionGroupManager.removeHost(group, host); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jmx\LoadBalanceConnectionGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */