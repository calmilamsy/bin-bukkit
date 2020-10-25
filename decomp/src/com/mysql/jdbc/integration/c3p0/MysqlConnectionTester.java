/*     */ package com.mysql.jdbc.integration.c3p0;
/*     */ 
/*     */ import com.mchange.v2.c3p0.C3P0ProxyConnection;
/*     */ import com.mchange.v2.c3p0.QueryConnectionTester;
/*     */ import com.mysql.jdbc.Connection;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ public final class MysqlConnectionTester
/*     */   implements QueryConnectionTester
/*     */ {
/*     */   private static final long serialVersionUID = 3256444690067896368L;
/*  48 */   private static final Object[] NO_ARGS_ARRAY = new Object[0];
/*     */   
/*     */   private Method pingMethod;
/*     */   
/*     */   public MysqlConnectionTester() {
/*     */     try {
/*  54 */       this.pingMethod = Connection.class.getMethod("ping", (Class[])null);
/*     */     }
/*  56 */     catch (Exception ex) {}
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
/*     */   public int activeCheckConnection(Connection con) {
/*     */     try {
/*  70 */       if (this.pingMethod != null) {
/*  71 */         if (con instanceof Connection) {
/*     */ 
/*     */           
/*  74 */           ((Connection)con).ping();
/*     */         } else {
/*     */           
/*  77 */           C3P0ProxyConnection castCon = (C3P0ProxyConnection)con;
/*  78 */           castCon.rawConnectionOperation(this.pingMethod, C3P0ProxyConnection.RAW_CONNECTION, NO_ARGS_ARRAY);
/*     */         } 
/*     */       } else {
/*     */         
/*  82 */         pingStatement = null;
/*     */         
/*     */         try {
/*  85 */           pingStatement = con.createStatement();
/*  86 */           pingStatement.executeQuery("SELECT 1").close();
/*     */         } finally {
/*  88 */           if (pingStatement != null) {
/*  89 */             pingStatement.close();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  94 */       return 0;
/*  95 */     } catch (Exception ex) {
/*  96 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int statusOnException(Connection arg0, Throwable throwable) {
/* 107 */     if (throwable instanceof com.mysql.jdbc.CommunicationsException || "com.mysql.jdbc.exceptions.jdbc4.CommunicationsException".equals(throwable.getClass().getName()))
/*     */     {
/*     */       
/* 110 */       return -1;
/*     */     }
/*     */     
/* 113 */     if (throwable instanceof SQLException) {
/* 114 */       String sqlState = ((SQLException)throwable).getSQLState();
/*     */       
/* 116 */       if (sqlState != null && sqlState.startsWith("08")) {
/* 117 */         return -1;
/*     */       }
/*     */       
/* 120 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public int activeCheckConnection(Connection arg0, String arg1) { return 0; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\integration\c3p0\MysqlConnectionTester.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */