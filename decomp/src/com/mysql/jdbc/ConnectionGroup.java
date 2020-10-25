/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ConnectionGroup
/*     */ {
/*     */   private String groupName;
/*     */   private long connections;
/*     */   private long activeConnections;
/*     */   private HashMap<Long, LoadBalancingConnectionProxy> connectionProxies;
/*     */   private Set<String> hostList;
/*     */   private boolean isInitialized;
/*     */   private long closedProxyTotalPhysicalConnections;
/*     */   private long closedProxyTotalTransactions;
/*     */   private int activeHosts;
/*     */   private Set<String> closedHosts;
/*     */   
/*     */   ConnectionGroup(String groupName) {
/*  36 */     this.connections = 0L;
/*  37 */     this.activeConnections = 0L;
/*  38 */     this.connectionProxies = new HashMap();
/*  39 */     this.hostList = new HashSet();
/*  40 */     this.isInitialized = false;
/*  41 */     this.closedProxyTotalPhysicalConnections = 0L;
/*  42 */     this.closedProxyTotalTransactions = 0L;
/*  43 */     this.activeHosts = 0;
/*  44 */     this.closedHosts = new HashSet();
/*     */ 
/*     */     
/*  47 */     this.groupName = groupName;
/*     */   }
/*     */ 
/*     */   
/*     */   public long registerConnectionProxy(LoadBalancingConnectionProxy proxy, List<String> localHostList) {
/*     */     long currentConnectionId;
/*  53 */     synchronized (this) {
/*  54 */       if (!this.isInitialized) {
/*  55 */         this.hostList.addAll(localHostList);
/*  56 */         this.isInitialized = true;
/*  57 */         this.activeHosts = localHostList.size();
/*     */       } 
/*  59 */       currentConnectionId = ++this.connections;
/*  60 */       this.connectionProxies.put(new Long(currentConnectionId), proxy);
/*     */     } 
/*  62 */     this.activeConnections++;
/*     */     
/*  64 */     return currentConnectionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String getGroupName() { return this.groupName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public Collection<String> getInitialHosts() { return this.hostList; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public int getActiveHostCount() { return this.activeHosts; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public Collection<String> getClosedHosts() { return this.closedHosts; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public long getTotalLogicalConnectionCount() { return this.connections; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public long getActiveLogicalConnectionCount() { return this.activeConnections; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getActivePhysicalConnectionCount() {
/* 112 */     long connections = 0L;
/* 113 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 114 */     synchronized (this.connectionProxies) {
/* 115 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 117 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 118 */     while (i.hasNext()) {
/* 119 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 120 */       connections += proxy.getActivePhysicalConnectionCount();
/*     */     } 
/*     */     
/* 123 */     return connections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotalPhysicalConnectionCount() {
/* 130 */     long allConnections = this.closedProxyTotalPhysicalConnections;
/* 131 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 132 */     synchronized (this.connectionProxies) {
/* 133 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 135 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 136 */     while (i.hasNext()) {
/* 137 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 138 */       allConnections += proxy.getTotalPhysicalConnectionCount();
/*     */     } 
/*     */     
/* 141 */     return allConnections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotalTransactionCount() {
/* 149 */     long transactions = this.closedProxyTotalTransactions;
/* 150 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 151 */     synchronized (this.connectionProxies) {
/* 152 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/* 154 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 155 */     while (i.hasNext()) {
/* 156 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 157 */       transactions += proxy.getTransactionCount();
/*     */     } 
/*     */     
/* 160 */     return transactions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeConnectionProxy(LoadBalancingConnectionProxy proxy) {
/* 165 */     this.activeConnections--;
/* 166 */     this.connectionProxies.remove(new Long(proxy.getConnectionGroupProxyID()));
/* 167 */     this.closedProxyTotalPhysicalConnections += proxy.getTotalPhysicalConnectionCount();
/* 168 */     this.closedProxyTotalTransactions += proxy.getTransactionCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public void removeHost(String host) { removeHost(host, false); }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public void removeHost(String host, boolean killExistingConnections) throws SQLException { removeHost(host, killExistingConnections, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHost(String host, boolean killExistingConnections, boolean waitForGracefulFailover) throws SQLException {
/* 184 */     if (this.activeHosts == 1) {
/* 185 */       throw SQLError.createSQLException("Cannot remove host, only one configured host active.", null);
/*     */     }
/*     */     
/* 188 */     if (this.hostList.remove(host)) {
/* 189 */       this.activeHosts--;
/*     */     } else {
/* 191 */       throw SQLError.createSQLException("Host is not configured: " + host, null);
/*     */     } 
/*     */     
/* 194 */     if (killExistingConnections) {
/*     */       
/* 196 */       Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 197 */       synchronized (this.connectionProxies) {
/* 198 */         proxyMap.putAll(this.connectionProxies);
/*     */       } 
/*     */       
/* 201 */       Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 202 */       while (i.hasNext()) {
/* 203 */         LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 204 */         if (waitForGracefulFailover) {
/* 205 */           proxy.removeHostWhenNotInUse(host); continue;
/*     */         } 
/* 207 */         proxy.removeHost(host);
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     this.closedHosts.add(host);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 216 */   public void addHost(String host) { addHost(host, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHost(String host, boolean forExisting) throws SQLException {
/* 225 */     synchronized (this) {
/* 226 */       if (this.hostList.add(host)) {
/* 227 */         this.activeHosts++;
/*     */       }
/*     */     } 
/*     */     
/* 231 */     if (!forExisting) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
/* 238 */     synchronized (this.connectionProxies) {
/* 239 */       proxyMap.putAll(this.connectionProxies);
/*     */     } 
/*     */     
/* 242 */     Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
/* 243 */     while (i.hasNext()) {
/* 244 */       LoadBalancingConnectionProxy proxy = (LoadBalancingConnectionProxy)((Map.Entry)i.next()).getValue();
/* 245 */       proxy.addHost(host);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ConnectionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */