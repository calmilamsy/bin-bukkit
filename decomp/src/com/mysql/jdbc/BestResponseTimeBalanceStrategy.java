/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public class BestResponseTimeBalanceStrategy
/*     */   implements BalanceStrategy
/*     */ {
/*     */   public void destroy() {}
/*     */   
/*     */   public void init(Connection conn, Properties props) throws SQLException {}
/*     */   
/*     */   public ConnectionImpl pickConnection(LoadBalancingConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries) throws SQLException {
/*  53 */     Map<String, Long> blackList = proxy.getGlobalBlacklist();
/*     */     
/*  55 */     SQLException ex = null;
/*     */     
/*  57 */     for (int attempts = 0; attempts < numRetries; ) {
/*  58 */       long minResponseTime = Float.MAX_VALUE;
/*     */       
/*  60 */       int bestHostIndex = 0;
/*     */ 
/*     */       
/*  63 */       if (blackList.size() == configuredHosts.size()) {
/*  64 */         blackList = proxy.getGlobalBlacklist();
/*     */       }
/*     */       
/*  67 */       for (i = 0; i < responseTimes.length; i++) {
/*  68 */         long candidateResponseTime = responseTimes[i];
/*     */         
/*  70 */         if (candidateResponseTime < minResponseTime && !blackList.containsKey(configuredHosts.get(i))) {
/*     */           
/*  72 */           if (candidateResponseTime == 0L) {
/*  73 */             bestHostIndex = i;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/*  78 */           bestHostIndex = i;
/*  79 */           minResponseTime = candidateResponseTime;
/*     */         } 
/*     */       } 
/*     */       
/*  83 */       String bestHost = (String)configuredHosts.get(bestHostIndex);
/*     */       
/*  85 */       ConnectionImpl conn = (ConnectionImpl)liveConnections.get(bestHost);
/*     */       
/*  87 */       if (conn == null) {
/*     */         try {
/*  89 */           conn = proxy.createConnectionForHost(bestHost);
/*  90 */         } catch (SQLException sqlEx) {
/*  91 */           ex = sqlEx;
/*     */           
/*  93 */           if (proxy.shouldExceptionTriggerFailover(sqlEx)) {
/*  94 */             proxy.addToGlobalBlacklist(bestHost);
/*  95 */             blackList.put(bestHost, null);
/*     */ 
/*     */             
/*  98 */             if (blackList.size() == configuredHosts.size()) {
/*  99 */               attempts++;
/*     */               try {
/* 101 */                 Thread.sleep(250L);
/* 102 */               } catch (InterruptedException e) {}
/*     */               
/* 104 */               blackList = proxy.getGlobalBlacklist();
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 109 */           throw sqlEx;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 114 */       return conn;
/*     */     } 
/*     */     
/* 117 */     if (ex != null) {
/* 118 */       throw ex;
/*     */     }
/*     */     
/* 121 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\BestResponseTimeBalanceStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */