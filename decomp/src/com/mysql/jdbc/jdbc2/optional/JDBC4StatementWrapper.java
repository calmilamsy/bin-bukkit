/*     */ package com.mysql.jdbc.jdbc2.optional;
/*     */ 
/*     */ import com.mysql.jdbc.SQLError;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4StatementWrapper
/*     */   extends StatementWrapper
/*     */ {
/*  61 */   public JDBC4StatementWrapper(ConnectionWrapper c, MysqlPooledConnection conn, Statement toWrap) { super(c, conn, toWrap); }
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*     */     try {
/*  66 */       super.close();
/*     */     } finally {
/*  68 */       this.unwrappedInterfaces = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/*     */     try {
/*  74 */       if (this.wrappedStmt != null) {
/*  75 */         return this.wrappedStmt.isClosed();
/*     */       }
/*  77 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/*  80 */     catch (SQLException sqlEx) {
/*  81 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/*  84 */       return false;
/*     */     } 
/*     */   }
/*     */   public void setPoolable(boolean poolable) throws SQLException {
/*     */     try {
/*  89 */       if (this.wrappedStmt != null) {
/*  90 */         this.wrappedStmt.setPoolable(poolable);
/*     */       } else {
/*  92 */         throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */       }
/*     */     
/*  95 */     } catch (SQLException sqlEx) {
/*  96 */       checkAndFireConnectionError(sqlEx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPoolable() throws SQLException {
/*     */     try {
/* 102 */       if (this.wrappedStmt != null) {
/* 103 */         return this.wrappedStmt.isPoolable();
/*     */       }
/* 105 */       throw SQLError.createSQLException("Statement already closed", "S1009", this.exceptionInterceptor);
/*     */     
/*     */     }
/* 108 */     catch (SQLException sqlEx) {
/* 109 */       checkAndFireConnectionError(sqlEx);
/*     */ 
/*     */       
/* 112 */       return false;
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
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 139 */     boolean isInstance = iface.isInstance(this);
/*     */     
/* 141 */     if (isInstance) {
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     String interfaceClassName = iface.getName();
/*     */     
/* 147 */     return (interfaceClassName.equals("com.mysql.jdbc.Statement") || interfaceClassName.equals("java.sql.Statement") || interfaceClassName.equals("java.sql.Wrapper"));
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
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 174 */       if ("java.sql.Statement".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName()))
/*     */       {
/* 176 */         return (T)iface.cast(this);
/*     */       }
/*     */       
/* 179 */       if (this.unwrappedInterfaces == null) {
/* 180 */         this.unwrappedInterfaces = new HashMap();
/*     */       }
/*     */       
/* 183 */       Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
/*     */       
/* 185 */       if (cachedUnwrapped == null) {
/* 186 */         cachedUnwrapped = Proxy.newProxyInstance(this.wrappedStmt.getClass().getClassLoader(), new Class[] { iface }, new WrapperBase.ConnectionErrorFiringInvocationHandler(this, this.wrappedStmt));
/*     */ 
/*     */ 
/*     */         
/* 190 */         this.unwrappedInterfaces.put(iface, cachedUnwrapped);
/*     */       } 
/*     */       
/* 193 */       return (T)iface.cast(cachedUnwrapped);
/* 194 */     } catch (ClassCastException cce) {
/* 195 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\jdbc2\optional\JDBC4StatementWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */