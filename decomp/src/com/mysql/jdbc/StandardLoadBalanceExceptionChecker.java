/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class StandardLoadBalanceExceptionChecker
/*     */   implements LoadBalanceExceptionChecker
/*     */ {
/*     */   private List sqlStateList;
/*     */   private List sqlExClassList;
/*     */   
/*     */   public boolean shouldExceptionTriggerFailover(SQLException ex) {
/*  39 */     String sqlState = ex.getSQLState();
/*     */     
/*  41 */     if (sqlState != null) {
/*  42 */       if (sqlState.startsWith("08"))
/*     */       {
/*  44 */         return true;
/*     */       }
/*  46 */       if (this.sqlStateList != null)
/*     */       {
/*  48 */         for (Iterator i = this.sqlStateList.iterator(); i.hasNext();) {
/*  49 */           if (sqlState.startsWith(i.next().toString())) {
/*  50 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  57 */     if (ex instanceof CommunicationsException) {
/*  58 */       return true;
/*     */     }
/*  60 */     if (this.sqlExClassList != null)
/*     */     {
/*  62 */       for (Iterator i = this.sqlExClassList.iterator(); i.hasNext();) {
/*  63 */         if (((Class)i.next()).isInstance(ex)) {
/*  64 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(Connection conn, Properties props) throws SQLException {
/*  79 */     configureSQLStateList(props.getProperty("loadBalanceSQLStateFailover", null));
/*  80 */     configureSQLExceptionSubclassList(props.getProperty("loadBalanceSQLExceptionSubclassFailover", null));
/*     */   }
/*     */ 
/*     */   
/*     */   private void configureSQLStateList(String sqlStates) {
/*  85 */     if (sqlStates == null || "".equals(sqlStates)) {
/*     */       return;
/*     */     }
/*  88 */     List states = StringUtils.split(sqlStates, ",", true);
/*  89 */     List newStates = new ArrayList();
/*  90 */     Iterator i = states.iterator();
/*     */     
/*  92 */     while (i.hasNext()) {
/*  93 */       String state = i.next().toString();
/*  94 */       if (state.length() > 0) {
/*  95 */         newStates.add(state);
/*     */       }
/*     */     } 
/*  98 */     if (newStates.size() > 0) {
/*  99 */       this.sqlStateList = newStates;
/*     */     }
/*     */   }
/*     */   
/*     */   private void configureSQLExceptionSubclassList(String sqlExClasses) {
/* 104 */     if (sqlExClasses == null || "".equals(sqlExClasses)) {
/*     */       return;
/*     */     }
/* 107 */     List classes = StringUtils.split(sqlExClasses, ",", true);
/* 108 */     List newClasses = new ArrayList();
/* 109 */     Iterator i = classes.iterator();
/*     */     
/* 111 */     while (i.hasNext()) {
/* 112 */       String exClass = i.next().toString();
/*     */       try {
/* 114 */         Class c = Class.forName(exClass);
/* 115 */         newClasses.add(c);
/* 116 */       } catch (Exception e) {}
/*     */     } 
/*     */ 
/*     */     
/* 120 */     if (newClasses.size() > 0)
/* 121 */       this.sqlExClassList = newClasses; 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\StandardLoadBalanceExceptionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */