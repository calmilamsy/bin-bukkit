/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.rmi.server.UID;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Savepoint;
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
/*     */ public class MysqlSavepoint
/*     */   implements Savepoint
/*     */ {
/*     */   private String savepointName;
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */   
/*     */   private static String getUniqueId() {
/*  42 */     uidStr = (new UID()).toString();
/*     */     
/*  44 */     int uidLength = uidStr.length();
/*     */     
/*  46 */     StringBuffer safeString = new StringBuffer(uidLength);
/*     */     
/*  48 */     for (int i = 0; i < uidLength; i++) {
/*  49 */       char c = uidStr.charAt(i);
/*     */       
/*  51 */       if (Character.isLetter(c) || Character.isDigit(c)) {
/*  52 */         safeString.append(c);
/*     */       } else {
/*  54 */         safeString.append('_');
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return safeString.toString();
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
/*  74 */   MysqlSavepoint(ExceptionInterceptor exceptionInterceptor) throws SQLException { this(getUniqueId(), exceptionInterceptor); }
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
/*     */   MysqlSavepoint(String name, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*  87 */     if (name == null || name.length() == 0) {
/*  88 */       throw SQLError.createSQLException("Savepoint name can not be NULL or empty", "S1009", exceptionInterceptor);
/*     */     }
/*     */ 
/*     */     
/*  92 */     this.savepointName = name;
/*     */     
/*  94 */     this.exceptionInterceptor = exceptionInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public int getSavepointId() throws SQLException { throw SQLError.createSQLException("Only named savepoints are supported.", "S1C00", this.exceptionInterceptor); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public String getSavepointName() { return this.savepointName; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MysqlSavepoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */