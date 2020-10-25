/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlUpdate;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultSqlUpdate
/*     */   implements Serializable, SpiSqlUpdate
/*     */ {
/*     */   private static final long serialVersionUID = -6493829438421253102L;
/*     */   private final EbeanServer server;
/*     */   private final BindParams bindParams;
/*     */   private final String sql;
/*     */   private String label;
/*     */   private int timeout;
/*     */   private boolean isAutoTableMod;
/*     */   private int addPos;
/*     */   
/*     */   public DefaultSqlUpdate(EbeanServer server, String sql, BindParams bindParams) {
/* 118 */     this.label = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.isAutoTableMod = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.server = server;
/* 146 */     this.sql = sql;
/* 147 */     this.bindParams = bindParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public DefaultSqlUpdate(EbeanServer server, String sql) { this(server, sql, new BindParams()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public DefaultSqlUpdate(String sql) { this(null, sql, new BindParams()); }
/*     */ 
/*     */   
/*     */   public int execute() {
/* 166 */     if (this.server != null) {
/* 167 */       return this.server.execute(this);
/*     */     }
/*     */     
/* 170 */     return Ebean.execute(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 175 */   public boolean isAutoTableMod() { return this.isAutoTableMod; }
/*     */ 
/*     */   
/*     */   public SqlUpdate setAutoTableMod(boolean isAutoTableMod) {
/* 179 */     this.isAutoTableMod = isAutoTableMod;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 184 */   public String getLabel() { return this.label; }
/*     */ 
/*     */   
/*     */   public SqlUpdate setLabel(String label) {
/* 188 */     this.label = label;
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 193 */   public String getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public int getTimeout() { return this.timeout; }
/*     */ 
/*     */   
/*     */   public SqlUpdate setTimeout(int secs) {
/* 201 */     this.timeout = secs;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 206 */   public SqlUpdate addParameter(Object value) { return setParameter(++this.addPos, value); }
/*     */ 
/*     */   
/*     */   public SqlUpdate setParameter(int position, Object value) {
/* 210 */     this.bindParams.setParameter(position, value);
/* 211 */     return this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNull(int position, int jdbcType) {
/* 215 */     this.bindParams.setNullParameter(position, jdbcType);
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNullParameter(int position, int jdbcType) {
/* 220 */     this.bindParams.setNullParameter(position, jdbcType);
/* 221 */     return this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setParameter(String name, Object param) {
/* 225 */     this.bindParams.setParameter(name, param);
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNull(String name, int jdbcType) {
/* 230 */     this.bindParams.setNullParameter(name, jdbcType);
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNullParameter(String name, int jdbcType) {
/* 235 */     this.bindParams.setNullParameter(name, jdbcType);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public BindParams getBindParams() { return this.bindParams; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DefaultSqlUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */