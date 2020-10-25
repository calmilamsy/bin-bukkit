/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
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
/*     */ public class GenerateDmlRequest
/*     */ {
/*     */   private static final String IS_NULL = " is null";
/*     */   private final boolean emptyStringAsNull;
/*     */   private final StringBuilder sb;
/*     */   private final Set<String> includeProps;
/*     */   private final Set<String> includeWhereProps;
/*     */   private final Object oldValues;
/*     */   private StringBuilder insertBindBuffer;
/*     */   private String prefix;
/*     */   private String prefix2;
/*     */   private int insertMode;
/*     */   private int bindColumnCount;
/*     */   
/*  36 */   public GenerateDmlRequest(boolean emptyStringAsNull, Set<String> includeProps, Object oldValues) { this(emptyStringAsNull, includeProps, includeProps, oldValues); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GenerateDmlRequest(boolean emptyStringAsNull, Set<String> includeProps, Set<String> includeWhereProps, Object oldValues) {
/*     */     this.sb = new StringBuilder(100);
/*  43 */     this.emptyStringAsNull = emptyStringAsNull;
/*  44 */     this.includeProps = includeProps;
/*  45 */     this.includeWhereProps = includeWhereProps;
/*  46 */     this.oldValues = oldValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public GenerateDmlRequest(boolean emptyStringAsNull) { this(emptyStringAsNull, null, null, null); }
/*     */ 
/*     */   
/*     */   public GenerateDmlRequest append(String s) {
/*  57 */     this.sb.append(s);
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*  62 */   public boolean isDbNull(Object v) { return (v == null || (this.emptyStringAsNull && v instanceof String && ((String)v).length() == 0)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean isIncluded(BeanProperty prop) { return (this.includeProps == null || this.includeProps.contains(prop.getName())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public boolean isIncludedWhere(BeanProperty prop) { return (this.includeWhereProps == null || this.includeWhereProps.contains(prop.getName())); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void appendColumnIsNull(String column) { appendColumn(column, " is null"); }
/*     */ 
/*     */   
/*     */   public void appendColumn(String column) {
/*  84 */     String bind = (this.insertMode > 0) ? "?" : "=?";
/*  85 */     appendColumn(column, bind);
/*     */   }
/*     */ 
/*     */   
/*  89 */   public void appendColumn(String column, String suffik) { appendColumn(column, "", suffik); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendColumn(String column, String expr, String suffik) {
/*  94 */     this.bindColumnCount++;
/*     */     
/*  96 */     this.sb.append(this.prefix);
/*  97 */     this.sb.append(column);
/*  98 */     this.sb.append(expr);
/*  99 */     if (this.insertMode > 0) {
/* 100 */       if (this.insertMode++ > 1) {
/* 101 */         this.insertBindBuffer.append(",");
/*     */       }
/* 103 */       this.insertBindBuffer.append(suffik);
/*     */     } else {
/* 105 */       this.sb.append(suffik);
/*     */     } 
/*     */     
/* 108 */     if (this.prefix2 != null) {
/* 109 */       this.prefix = this.prefix2;
/* 110 */       this.prefix2 = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 115 */   public int getBindColumnCount() { return this.bindColumnCount; }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public String getInsertBindBuffer() { return this.insertBindBuffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public String toString() { return this.sb.toString(); }
/*     */ 
/*     */   
/*     */   public void setWhereMode() {
/* 127 */     this.prefix = " and ";
/* 128 */     this.prefix2 = " and ";
/*     */   }
/*     */   
/*     */   public void setWhereIdMode() {
/* 132 */     this.prefix = "";
/* 133 */     this.prefix2 = " and ";
/*     */   }
/*     */   
/*     */   public void setInsertSetMode() {
/* 137 */     this.insertBindBuffer = new StringBuilder(100);
/* 138 */     this.insertMode = 1;
/* 139 */     this.prefix = "";
/* 140 */     this.prefix2 = ", ";
/*     */   }
/*     */   
/*     */   public void setUpdateSetMode() {
/* 144 */     this.prefix = "";
/* 145 */     this.prefix2 = ", ";
/*     */   }
/*     */ 
/*     */   
/* 149 */   public Object getOldValues() { return this.oldValues; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\GenerateDmlRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */