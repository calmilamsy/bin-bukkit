/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlFutureList;
/*     */ import com.avaje.ebean.SqlQuery;
/*     */ import com.avaje.ebean.SqlQueryListener;
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlQuery;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultRelationalQuery
/*     */   implements SpiSqlQuery
/*     */ {
/*     */   private static final long serialVersionUID = -1098305779779591068L;
/*     */   private EbeanServer server;
/*     */   private SqlQueryListener queryListener;
/*     */   private String query;
/*     */   private int firstRow;
/*     */   private int maxRows;
/*     */   private int timeout;
/*     */   private boolean futureFetch;
/*     */   private boolean cancelled;
/*     */   private PreparedStatement pstmt;
/*     */   private int backgroundFetchAfter;
/*     */   private int bufferFetchSizeHint;
/*     */   private String mapKey;
/*     */   private BindParams bindParams;
/*     */   
/*     */   public DefaultRelationalQuery(EbeanServer server, String query) {
/*  61 */     this.bindParams = new BindParams();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     this.server = server;
/*  68 */     this.query = query;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setQuery(String query) {
/*  72 */     this.query = query;
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  77 */   public List<SqlRow> findList() { return this.server.findList(this, null); }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public Set<SqlRow> findSet() { return this.server.findSet(this, null); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public Map<?, SqlRow> findMap() { return this.server.findMap(this, null); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public SqlRow findUnique() { return this.server.findUnique(this, null); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public SqlFutureList findFutureList() { return this.server.findFutureList(this, null); }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setParameter(int position, Object value) {
/*  97 */     this.bindParams.setParameter(position, value);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultRelationalQuery setParameter(String paramName, Object value) {
/* 102 */     this.bindParams.setParameter(paramName, value);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public SqlQueryListener getListener() { return this.queryListener; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setListener(SqlQueryListener queryListener) {
/* 122 */     this.queryListener = queryListener;
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 127 */   public String toString() { return "SqlQuery [" + this.query + "]"; }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public int getFirstRow() { return this.firstRow; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setFirstRow(int firstRow) {
/* 135 */     this.firstRow = firstRow;
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 140 */   public int getMaxRows() { return this.maxRows; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setMaxRows(int maxRows) {
/* 144 */     this.maxRows = maxRows;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 149 */   public String getMapKey() { return this.mapKey; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setMapKey(String mapKey) {
/* 153 */     this.mapKey = mapKey;
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 158 */   public int getBackgroundFetchAfter() { return this.backgroundFetchAfter; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setBackgroundFetchAfter(int backgroundFetchAfter) {
/* 162 */     this.backgroundFetchAfter = backgroundFetchAfter;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 167 */   public int getTimeout() { return this.timeout; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setTimeout(int secs) {
/* 171 */     this.timeout = secs;
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/* 176 */   public BindParams getBindParams() { return this.bindParams; }
/*     */ 
/*     */   
/*     */   public DefaultRelationalQuery setBufferFetchSizeHint(int bufferFetchSizeHint) {
/* 180 */     this.bufferFetchSizeHint = bufferFetchSizeHint;
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 186 */   public int getBufferFetchSizeHint() { return this.bufferFetchSizeHint; }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public String getQuery() { return this.query; }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public boolean isFutureFetch() { return this.futureFetch; }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void setFutureFetch(boolean futureFetch) { this.futureFetch = futureFetch; }
/*     */ 
/*     */   
/*     */   public void setPreparedStatement(PreparedStatement pstmt) {
/* 202 */     synchronized (this) {
/* 203 */       this.pstmt = pstmt;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 208 */     synchronized (this) {
/* 209 */       this.cancelled = true;
/* 210 */       if (this.pstmt != null) {
/*     */         try {
/* 212 */           this.pstmt.cancel();
/* 213 */         } catch (SQLException e) {
/* 214 */           String msg = "Error cancelling query";
/* 215 */           throw new PersistenceException(msg, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 222 */     synchronized (this) {
/* 223 */       return this.cancelled;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\DefaultRelationalQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */