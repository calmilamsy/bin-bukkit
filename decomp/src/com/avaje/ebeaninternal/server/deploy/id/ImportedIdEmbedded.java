/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.util.ValueUtil;
/*     */ import java.sql.SQLException;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImportedIdEmbedded
/*     */   implements ImportedId
/*     */ {
/*     */   final BeanPropertyAssoc<?> owner;
/*     */   final BeanPropertyAssocOne<?> foreignAssocOne;
/*     */   final ImportedIdSimple[] imported;
/*     */   
/*     */   public ImportedIdEmbedded(BeanPropertyAssoc<?> owner, BeanPropertyAssocOne<?> foreignAssocOne, ImportedIdSimple[] imported) {
/*  29 */     this.owner = owner;
/*  30 */     this.foreignAssocOne = foreignAssocOne;
/*  31 */     this.imported = imported;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFkeys(String name) {
/*  36 */     BeanProperty[] embeddedProps = this.foreignAssocOne.getProperties();
/*     */     
/*  38 */     for (int i = 0; i < this.imported.length; i++) {
/*  39 */       String n = name + "." + this.foreignAssocOne.getName() + "." + embeddedProps[i].getName();
/*  40 */       BeanFkeyProperty fkey = new BeanFkeyProperty(null, n, (this.imported[i]).localDbColumn, this.foreignAssocOne.getDeployOrder());
/*  41 */       this.owner.getBeanDescriptor().add(fkey);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  46 */   public boolean isScalar() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public String getLogicalName() { return this.owner.getName() + "." + this.foreignAssocOne.getName(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public String getDbColumn() { return null; }
/*     */ 
/*     */   
/*     */   public void sqlAppend(DbSqlContext ctx) {
/*  59 */     for (int i = 0; i < this.imported.length; i++) {
/*  60 */       ctx.appendColumn((this.imported[i]).localDbColumn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request) {
/*  66 */     for (int i = 0; i < this.imported.length; i++) {
/*  67 */       request.appendColumn((this.imported[i]).localDbColumn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, Object bean) {
/*  73 */     Object embeddedId = null;
/*  74 */     if (bean != null) {
/*  75 */       embeddedId = this.foreignAssocOne.getValue(bean);
/*     */     }
/*     */     
/*  78 */     if (embeddedId == null) {
/*  79 */       for (int i = 0; i < this.imported.length; i++) {
/*  80 */         if ((this.imported[i]).owner.isDbUpdatable()) {
/*  81 */           request.appendColumnIsNull((this.imported[i]).localDbColumn);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/*  86 */       for (int i = 0; i < this.imported.length; i++) {
/*  87 */         if ((this.imported[i]).owner.isDbUpdatable()) {
/*  88 */           Object value = (this.imported[i]).foreignProperty.getValue(embeddedId);
/*  89 */           if (value == null) {
/*  90 */             request.appendColumnIsNull((this.imported[i]).localDbColumn);
/*     */           } else {
/*  92 */             request.appendColumn((this.imported[i]).localDbColumn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 100 */     Object id = this.foreignAssocOne.getValue(bean);
/* 101 */     Object oldId = this.foreignAssocOne.getValue(oldValues);
/*     */     
/* 103 */     return !ValueUtil.areEqual(id, oldId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/* 108 */     Object embeddedId = null;
/*     */     
/* 110 */     if (bean != null) {
/* 111 */       embeddedId = this.foreignAssocOne.getValue(bean);
/*     */     }
/*     */     
/* 114 */     if (embeddedId == null) {
/* 115 */       for (int i = 0; i < this.imported.length; i++) {
/* 116 */         if ((this.imported[i]).owner.isUpdateable()) {
/* 117 */           request.bind(null, (this.imported[i]).foreignProperty, (this.imported[i]).localDbColumn, true);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 122 */       for (int i = 0; i < this.imported.length; i++) {
/* 123 */         if ((this.imported[i]).owner.isUpdateable()) {
/* 124 */           Object scalarValue = (this.imported[i]).foreignProperty.getValue(embeddedId);
/* 125 */           request.bind(scalarValue, (this.imported[i]).foreignProperty, (this.imported[i]).localDbColumn, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildImport(IntersectionRow row, Object other) {
/* 133 */     Object embeddedId = this.foreignAssocOne.getValue(other);
/* 134 */     if (embeddedId == null) {
/* 135 */       String msg = "Foreign Key value null?";
/* 136 */       throw new PersistenceException(msg);
/*     */     } 
/*     */     
/* 139 */     for (int i = 0; i < this.imported.length; i++) {
/* 140 */       Object scalarValue = (this.imported[i]).foreignProperty.getValue(embeddedId);
/* 141 */       row.put((this.imported[i]).localDbColumn, scalarValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty findMatchImport(String matchDbColumn) {
/* 151 */     BeanProperty p = null;
/* 152 */     for (int i = 0; i < this.imported.length; i++) {
/* 153 */       p = this.imported[i].findMatchImport(matchDbColumn);
/* 154 */       if (p != null) {
/* 155 */         return p;
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return p;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\ImportedIdEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */