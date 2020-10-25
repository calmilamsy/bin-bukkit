/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.util.ValueUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImportedIdSimple
/*     */   extends Object
/*     */   implements ImportedId, Comparable<ImportedIdSimple>
/*     */ {
/*     */   private static final class EntryComparator
/*     */     extends Object
/*     */     implements Comparator<ImportedIdSimple>
/*     */   {
/*     */     private EntryComparator() {}
/*     */     
/*  30 */     public int compare(ImportedIdSimple o1, ImportedIdSimple o2) { return o1.compareTo(o2); }
/*     */   }
/*     */ 
/*     */   
/*  34 */   private static final EntryComparator COMPARATOR = new EntryComparator(null);
/*     */   
/*     */   protected final BeanPropertyAssoc<?> owner;
/*     */   
/*     */   protected final String localDbColumn;
/*     */   
/*     */   protected final String logicalName;
/*     */   
/*     */   protected final BeanProperty foreignProperty;
/*     */   
/*     */   protected final int position;
/*     */   
/*     */   public ImportedIdSimple(BeanPropertyAssoc<?> owner, String localDbColumn, BeanProperty foreignProperty, int position) {
/*  47 */     this.owner = owner;
/*  48 */     this.localDbColumn = InternString.intern(localDbColumn);
/*  49 */     this.foreignProperty = foreignProperty;
/*  50 */     this.position = position;
/*  51 */     this.logicalName = InternString.intern(owner.getName() + "." + foreignProperty.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImportedIdSimple[] sort(List<ImportedIdSimple> list) {
/*  59 */     ImportedIdSimple[] importedIds = (ImportedIdSimple[])list.toArray(new ImportedIdSimple[list.size()]);
/*     */ 
/*     */     
/*  62 */     Arrays.sort(importedIds, COMPARATOR);
/*  63 */     return importedIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public boolean equals(Object obj) { return (obj == this); }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public int compareTo(ImportedIdSimple other) { return (this.position < other.position) ? -1 : ((this.position == other.position) ? 0 : 1); }
/*     */ 
/*     */   
/*     */   public void addFkeys(String name) {
/*  77 */     BeanFkeyProperty fkey = new BeanFkeyProperty(null, name + "." + this.foreignProperty.getName(), this.localDbColumn, this.owner.getDeployOrder());
/*  78 */     this.owner.getBeanDescriptor().add(fkey);
/*     */   }
/*     */ 
/*     */   
/*  82 */   public boolean isScalar() { return true; }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String getLogicalName() { return this.logicalName; }
/*     */ 
/*     */ 
/*     */   
/*  90 */   public String getDbColumn() { return this.localDbColumn; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   private Object getIdValue(Object bean) { return this.foreignProperty.getValueWithInheritance(bean); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildImport(IntersectionRow row, Object other) {
/*  99 */     Object value = getIdValue(other);
/* 100 */     if (value == null) {
/* 101 */       String msg = "Foreign Key value null?";
/* 102 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/* 105 */     row.put(this.localDbColumn, value);
/*     */   }
/*     */ 
/*     */   
/* 109 */   public void sqlAppend(DbSqlContext ctx) { ctx.appendColumn(this.localDbColumn); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void dmlAppend(GenerateDmlRequest request) { request.appendColumn(this.localDbColumn); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, Object bean) {
/* 119 */     if (this.owner.isDbUpdatable()) {
/* 120 */       Object value = null;
/* 121 */       if (bean != null) {
/* 122 */         value = getIdValue(bean);
/*     */       }
/* 124 */       if (value == null) {
/* 125 */         request.appendColumnIsNull(this.localDbColumn);
/*     */       } else {
/* 127 */         request.appendColumn(this.localDbColumn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 134 */     Object id = getIdValue(bean);
/*     */     
/* 136 */     if (oldValues != null) {
/* 137 */       Object oldId = getIdValue(oldValues);
/* 138 */       return !ValueUtil.areEqual(id, oldId);
/*     */     } 
/*     */     
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/* 146 */     Object value = null;
/* 147 */     if (bean != null) {
/* 148 */       value = getIdValue(bean);
/*     */     }
/* 150 */     request.bind(value, this.foreignProperty, this.localDbColumn, bindNull);
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanProperty findMatchImport(String matchDbColumn) {
/* 155 */     if (matchDbColumn.equals(this.localDbColumn)) {
/* 156 */       return this.foreignProperty;
/*     */     }
/* 158 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\ImportedIdSimple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */