/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MiniAdmin
/*     */ {
/*     */   private Connection conn;
/*     */   
/*     */   public MiniAdmin(Connection conn) throws SQLException {
/*  55 */     if (conn == null) {
/*  56 */       throw SQLError.createSQLException(Messages.getString("MiniAdmin.0"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (!(conn instanceof Connection)) {
/*  61 */       throw SQLError.createSQLException(Messages.getString("MiniAdmin.1"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
/*     */     }
/*     */ 
/*     */     
/*  65 */     this.conn = (Connection)conn;
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
/*  78 */   public MiniAdmin(String jdbcUrl) throws SQLException { this(jdbcUrl, new Properties()); }
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
/*  94 */   public MiniAdmin(String jdbcUrl, Properties props) throws SQLException { this.conn = (Connection)(new Driver()).connect(jdbcUrl, props); }
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
/* 108 */   public void shutdown() throws SQLException { this.conn.shutdownServer(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\MiniAdmin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */