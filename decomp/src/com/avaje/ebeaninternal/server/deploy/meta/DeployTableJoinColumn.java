/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
/*     */ import javax.persistence.JoinColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployTableJoinColumn
/*     */ {
/*     */   String localDbColumn;
/*     */   String foreignDbColumn;
/*     */   boolean insertable;
/*     */   boolean updateable;
/*     */   
/*  53 */   public DeployTableJoinColumn(String localDbColumn, String foreignDbColumn) { this(localDbColumn, foreignDbColumn, true, true); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoinColumn(String localDbColumn, String foreignDbColumn, boolean insertable, boolean updateable) {
/*  60 */     this.localDbColumn = nullEmptyString(localDbColumn);
/*  61 */     this.foreignDbColumn = nullEmptyString(foreignDbColumn);
/*  62 */     this.insertable = insertable;
/*  63 */     this.updateable = updateable;
/*     */   }
/*     */   
/*     */   public DeployTableJoinColumn(boolean order, JoinColumn jc, BeanTable beanTable) {
/*  67 */     this(jc.referencedColumnName(), jc.name(), jc.insertable(), jc.updatable());
/*  68 */     setReferencedColumn(beanTable);
/*  69 */     if (!order) {
/*  70 */       reverse();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setReferencedColumn(BeanTable beanTable) {
/*  75 */     if (this.localDbColumn == null) {
/*  76 */       BeanProperty[] idProperties = beanTable.getIdProperties();
/*  77 */       if (idProperties.length == 1) {
/*  78 */         this.localDbColumn = idProperties[0].getDbColumn();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoinColumn reverse() {
/*  87 */     String temp = this.localDbColumn;
/*  88 */     this.localDbColumn = this.foreignDbColumn;
/*  89 */     this.foreignDbColumn = temp;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String nullEmptyString(String s) {
/*  97 */     if ("".equals(s)) {
/*  98 */       return null;
/*     */     }
/* 100 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoinColumn copy(boolean reverse) {
/* 108 */     if (reverse) {
/* 109 */       return new DeployTableJoinColumn(this.foreignDbColumn, this.localDbColumn, this.insertable, this.updateable);
/*     */     }
/*     */     
/* 112 */     return new DeployTableJoinColumn(this.localDbColumn, this.foreignDbColumn, this.insertable, this.updateable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String toString() { return this.localDbColumn + " = " + this.foreignDbColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public boolean hasNullColumn() { return (this.localDbColumn == null || this.foreignDbColumn == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNonNullColumn() {
/* 138 */     if (this.localDbColumn == null && this.foreignDbColumn == null) {
/* 139 */       throw new IllegalStateException("expecting only one null column?");
/*     */     }
/* 141 */     if (this.localDbColumn != null && this.foreignDbColumn != null) {
/* 142 */       throw new IllegalStateException("expecting one null column?");
/*     */     }
/* 144 */     if (this.localDbColumn != null) {
/* 145 */       return this.localDbColumn;
/*     */     }
/* 147 */     return this.foreignDbColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public boolean isInsertable() { return this.insertable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public boolean isUpdateable() { return this.updateable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public String getForeignDbColumn() { return this.foreignDbColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public void setForeignDbColumn(String foreignDbColumn) { this.foreignDbColumn = foreignDbColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public String getLocalDbColumn() { return this.localDbColumn; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public void setLocalDbColumn(String localDbColumn) { this.localDbColumn = localDbColumn; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployTableJoinColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */