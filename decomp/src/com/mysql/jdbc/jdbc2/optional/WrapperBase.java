/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.ExceptionInterceptor;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
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
/*     */ abstract class WrapperBase
/*     */ {
/*     */   protected MysqlPooledConnection pooledConnection;
/*     */   protected Map unwrappedInterfaces;
/*     */   protected ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   protected void checkAndFireConnectionError(SQLException sqlEx) throws SQLException {
/*  58 */     if (this.pooledConnection != null && 
/*  59 */       "08S01".equals(sqlEx.getSQLState()))
/*     */     {
/*  61 */       this.pooledConnection.callConnectionEventListeners(1, sqlEx);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  66 */     throw sqlEx;
/*     */   }
/*     */   protected WrapperBase(MysqlPooledConnection pooledConnection) {
/*  69 */     this.unwrappedInterfaces = null;
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.pooledConnection = pooledConnection;
/*  74 */     this.exceptionInterceptor = this.pooledConnection.getExceptionInterceptor();
/*     */   }
/*     */   
/*     */   protected class ConnectionErrorFiringInvocationHandler implements InvocationHandler { public ConnectionErrorFiringInvocationHandler(Object toInvokeOn) {
/*  78 */       this.invokeOn = null;
/*     */ 
/*     */       
/*  81 */       this.invokeOn = toInvokeOn;
/*     */     }
/*     */     Object invokeOn;
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*  86 */       Object result = null;
/*     */       
/*     */       try {
/*  89 */         result = method.invoke(this.invokeOn, args);
/*     */         
/*  91 */         if (result != null) {
/*  92 */           result = proxyIfInterfaceIsJdbc(result, result.getClass());
/*     */         }
/*     */       }
/*  95 */       catch (InvocationTargetException e) {
/*  96 */         if (e.getTargetException() instanceof SQLException) {
/*  97 */           WrapperBase.this.checkAndFireConnectionError((SQLException)e.getTargetException());
/*     */         } else {
/*     */           
/* 100 */           throw e;
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object proxyIfInterfaceIsJdbc(Object toProxy, Class clazz) {
/* 116 */       Class[] interfaces = clazz.getInterfaces();
/*     */       
/* 118 */       int i = 0; if (i < interfaces.length) {
/* 119 */         String packageName = interfaces[i].getPackage().getName();
/*     */         
/* 121 */         if ("java.sql".equals(packageName) || "javax.sql".equals(packageName))
/*     */         {
/* 123 */           return Proxy.newProxyInstance(toProxy.getClass().getClassLoader(), interfaces, new ConnectionErrorFiringInvocationHandler(WrapperBase.this, toProxy));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 128 */         return proxyIfInterfaceIsJdbc(toProxy, interfaces[i]);
/*     */       } 
/*     */       
/* 131 */       return toProxy;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\WrapperBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */