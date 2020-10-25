/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
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
/*     */ public class FailoverConnectionProxy
/*     */   extends LoadBalancingConnectionProxy
/*     */ {
/*     */   boolean failedOver;
/*     */   boolean hasTriedMaster;
/*     */   private long masterFailTimeMillis;
/*     */   boolean preferSlaveDuringFailover;
/*     */   private String primaryHostPortSpec;
/*     */   private long queriesBeforeRetryMaster;
/*     */   long queriesIssuedFailedOver;
/*     */   private int secondsBeforeRetryMaster;
/*     */   
/*     */   class FailoverInvocationHandler
/*     */     extends LoadBalancingConnectionProxy.ConnectionErrorFiringInvocationHandler
/*     */   {
/*  40 */     public FailoverInvocationHandler(Object toInvokeOn) { super(FailoverConnectionProxy.this, toInvokeOn); }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*  45 */       String methodName = method.getName();
/*     */       
/*  47 */       if (FailoverConnectionProxy.this.failedOver && methodName.indexOf("execute") != -1) {
/*  48 */         FailoverConnectionProxy.this.queriesIssuedFailedOver++;
/*     */       }
/*     */       
/*  51 */       return super.invoke(proxy, method, args);
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
/*     */   FailoverConnectionProxy(List<String> hosts, Properties props) throws SQLException {
/*  66 */     super(hosts, props);
/*  67 */     ConnectionPropertiesImpl connectionProps = new ConnectionPropertiesImpl();
/*  68 */     connectionProps.initializeProperties(props);
/*     */     
/*  70 */     this.queriesBeforeRetryMaster = connectionProps.getQueriesBeforeRetryMaster();
/*  71 */     this.secondsBeforeRetryMaster = connectionProps.getSecondsBeforeRetryMaster();
/*  72 */     this.preferSlaveDuringFailover = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  77 */   protected LoadBalancingConnectionProxy.ConnectionErrorFiringInvocationHandler createConnectionProxy(Object toProxy) { return new FailoverInvocationHandler(toProxy); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dealWithInvocationException(InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
/*  85 */     Throwable t = e.getTargetException();
/*     */     
/*  87 */     if (t != null) {
/*  88 */       if (this.failedOver) {
/*  89 */         createPrimaryConnection();
/*     */         
/*  91 */         if (this.currentConn != null) {
/*  92 */           throw t;
/*     */         }
/*     */       } 
/*     */       
/*  96 */       failOver();
/*     */       
/*  98 */       throw t;
/*     */     } 
/*     */     
/* 101 */     throw e;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 106 */     String methodName = method.getName();
/*     */     
/* 108 */     if ("setPreferSlaveDuringFailover".equals(methodName))
/* 109 */     { this.preferSlaveDuringFailover = ((Boolean)args[0]).booleanValue(); }
/* 110 */     else if ("clearHasTriedMaster".equals(methodName))
/* 111 */     { this.hasTriedMaster = false; }
/* 112 */     else { if ("hasTriedMaster".equals(methodName))
/* 113 */         return Boolean.valueOf(this.hasTriedMaster); 
/* 114 */       if ("isMasterConnection".equals(methodName))
/* 115 */         return Boolean.valueOf(!this.failedOver); 
/* 116 */       if ("isSlaveConnection".equals(methodName))
/* 117 */         return Boolean.valueOf(this.failedOver); 
/* 118 */       if ("setReadOnly".equals(methodName)) {
/* 119 */         if (this.failedOver) {
/* 120 */           return null;
/*     */         }
/* 122 */       } else if ("setAutoCommit".equals(methodName) && this.failedOver && shouldFallBack() && Boolean.TRUE.equals(args[0]) && this.failedOver) {
/*     */         
/* 124 */         createPrimaryConnection();
/*     */         
/* 126 */         return invoke(proxy, method, args, this.failedOver);
/*     */       }  }
/*     */     
/* 129 */     return invoke(proxy, method, args, this.failedOver);
/*     */   }
/*     */   
/*     */   private void createPrimaryConnection() throws SQLException {
/*     */     try {
/* 134 */       this.currentConn = createConnectionForHost(this.primaryHostPortSpec);
/* 135 */       this.failedOver = false;
/* 136 */       this.hasTriedMaster = true;
/*     */ 
/*     */       
/* 139 */       this.queriesIssuedFailedOver = 0L;
/* 140 */     } catch (SQLException sqlEx) {
/* 141 */       this.failedOver = true;
/*     */       
/* 143 */       if (this.currentConn != null) {
/* 144 */         this.currentConn.getLog().logWarn("Connection to primary host failed", sqlEx);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void invalidateCurrentConnection() throws SQLException {
/* 150 */     if (!this.failedOver) {
/* 151 */       this.failedOver = true;
/* 152 */       this.queriesIssuedFailedOver = 0L;
/* 153 */       this.masterFailTimeMillis = System.currentTimeMillis();
/*     */     } 
/* 155 */     super.invalidateCurrentConnection();
/*     */   }
/*     */   
/*     */   protected void pickNewConnection() throws SQLException {
/* 159 */     if (this.primaryHostPortSpec == null) {
/* 160 */       this.primaryHostPortSpec = (String)this.hostList.remove(0);
/*     */     }
/*     */     
/* 163 */     if (this.currentConn == null || (this.failedOver && shouldFallBack())) {
/* 164 */       createPrimaryConnection();
/*     */       
/* 166 */       if (this.currentConn != null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     failOver();
/*     */   }
/*     */   
/*     */   private void failOver() throws SQLException {
/* 175 */     if (this.failedOver) {
/* 176 */       Iterator<Map.Entry<String, ConnectionImpl>> iter = this.liveConnections.entrySet().iterator();
/*     */       
/* 178 */       while (iter.hasNext()) {
/* 179 */         Map.Entry<String, ConnectionImpl> entry = (Map.Entry)iter.next();
/* 180 */         ((ConnectionImpl)entry.getValue()).close();
/*     */       } 
/*     */       
/* 183 */       this.liveConnections.clear();
/*     */     } 
/*     */     
/* 186 */     super.pickNewConnection();
/*     */     
/* 188 */     if (this.currentConn.getFailOverReadOnly()) {
/* 189 */       this.currentConn.setReadOnly(true);
/*     */     } else {
/* 191 */       this.currentConn.setReadOnly(false);
/*     */     } 
/*     */     
/* 194 */     this.failedOver = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldFallBack() {
/* 205 */     long secondsSinceFailedOver = (System.currentTimeMillis() - this.masterFailTimeMillis) / 1000L;
/*     */     
/* 207 */     if (secondsSinceFailedOver >= this.secondsBeforeRetryMaster) {
/*     */       
/* 209 */       this.masterFailTimeMillis = System.currentTimeMillis();
/*     */       
/* 211 */       return true;
/* 212 */     }  if (this.queriesBeforeRetryMaster != 0L && this.queriesIssuedFailedOver >= this.queriesBeforeRetryMaster) {
/* 213 */       return true;
/*     */     }
/*     */     
/* 216 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\FailoverConnectionProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */