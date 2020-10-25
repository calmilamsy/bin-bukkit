/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
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
/*     */ public class JDBC4CommentClientInfoProvider
/*     */   implements JDBC4ClientInfoProvider
/*     */ {
/*     */   private Properties clientInfo;
/*     */   
/*  52 */   public void initialize(Connection conn, Properties configurationProps) throws SQLException { this.clientInfo = new Properties(); }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public void destroy() { this.clientInfo = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public Properties getClientInfo(Connection conn) throws SQLException { return this.clientInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public String getClientInfo(Connection conn, String name) throws SQLException { return this.clientInfo.getProperty(name); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientInfo(Connection conn, Properties properties) throws SQLException {
/*  71 */     this.clientInfo = new Properties();
/*     */     
/*  73 */     Enumeration propNames = properties.propertyNames();
/*     */     
/*  75 */     while (propNames.hasMoreElements()) {
/*  76 */       String name = (String)propNames.nextElement();
/*     */       
/*  78 */       this.clientInfo.put(name, properties.getProperty(name));
/*     */     } 
/*     */     
/*  81 */     setComment(conn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientInfo(Connection conn, String name, String value) throws SQLClientInfoException {
/*  86 */     this.clientInfo.setProperty(name, value);
/*  87 */     setComment(conn);
/*     */   }
/*     */   
/*     */   private void setComment(Connection conn) {
/*  91 */     StringBuffer commentBuf = new StringBuffer();
/*  92 */     Iterator elements = this.clientInfo.entrySet().iterator();
/*     */     
/*  94 */     while (elements.hasNext()) {
/*  95 */       if (commentBuf.length() > 0) {
/*  96 */         commentBuf.append(", ");
/*     */       }
/*     */       
/*  99 */       Map.Entry entry = (Map.Entry)elements.next();
/* 100 */       commentBuf.append("" + entry.getKey());
/* 101 */       commentBuf.append("=");
/* 102 */       commentBuf.append("" + entry.getValue());
/*     */     } 
/*     */     
/* 105 */     ((Connection)conn).setStatementComment(commentBuf.toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\JDBC4CommentClientInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.4
 */