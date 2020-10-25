/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*     */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdatePlan
/*     */   implements SpiUpdatePlan
/*     */ {
/*  42 */   public static final UpdatePlan EMPTY_SET_CLAUSE = new UpdatePlan();
/*     */ 
/*     */   
/*     */   private final Integer key;
/*     */ 
/*     */   
/*     */   private final ConcurrencyMode mode;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */ 
/*     */   
/*     */   private final Bindable set;
/*     */   
/*     */   private final Set<String> properties;
/*     */   
/*     */   private final boolean checkIncludes;
/*     */   
/*     */   private final long timeCreated;
/*     */   
/*     */   private final boolean emptySetClause;
/*     */   
/*     */   private Long timeLastUsed;
/*     */ 
/*     */   
/*  67 */   public UpdatePlan(ConcurrencyMode mode, String sql, Bindable set) { this(null, mode, sql, set, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePlan(Integer key, ConcurrencyMode mode, String sql, Bindable set, Set<String> properties) {
/*  76 */     this.emptySetClause = false;
/*  77 */     this.key = key;
/*  78 */     this.mode = mode;
/*  79 */     this.sql = sql;
/*  80 */     this.set = set;
/*  81 */     this.properties = properties;
/*  82 */     this.checkIncludes = (properties != null);
/*  83 */     this.timeCreated = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UpdatePlan() {
/*  90 */     this.emptySetClause = true;
/*  91 */     this.key = Integer.valueOf(0);
/*  92 */     this.mode = ConcurrencyMode.NONE;
/*  93 */     this.sql = null;
/*  94 */     this.set = null;
/*  95 */     this.properties = null;
/*  96 */     this.checkIncludes = false;
/*  97 */     this.timeCreated = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public boolean isEmptySetClause() { return this.emptySetClause; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindSet(DmlHandler bind, Object bean) throws SQLException {
/* 110 */     this.set.dmlBind(bind, this.checkIncludes, bean);
/*     */ 
/*     */     
/* 113 */     Long touched = Long.valueOf(System.currentTimeMillis());
/* 114 */     this.timeLastUsed = touched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public long getTimeCreated() { return this.timeCreated; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public Long getTimeLastUsed() { return this.timeLastUsed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public Integer getKey() { return this.key; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public ConcurrencyMode getMode() { return this.mode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public String getSql() { return this.sql; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public Bindable getSet() { return this.set; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public Set<String> getProperties() { return this.properties; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\UpdatePlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */