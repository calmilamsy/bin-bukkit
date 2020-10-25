/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BindableEncryptedProperty
/*     */   implements Bindable
/*     */ {
/*     */   private final BeanProperty prop;
/*     */   private final boolean bindEncryptDataFirst;
/*     */   
/*     */   public BindableEncryptedProperty(BeanProperty prop, boolean bindEncryptDataFirst) {
/*  40 */     this.prop = prop;
/*  41 */     this.bindEncryptDataFirst = bindEncryptDataFirst;
/*     */   }
/*     */ 
/*     */   
/*  45 */   public String toString() { return this.prop.toString(); }
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/*  49 */     if (request.hasChanged(this.prop)) {
/*  50 */       list.add(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/*  56 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  61 */     request.appendColumn(this.prop.getDbColumn(), this.prop.getDbBind());
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  66 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     request.appendColumn(this.prop.getDbColumn(), "=", this.prop.getDbBind());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/*  79 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     if (bean == null || request.isDbNull(this.prop.getValue(bean))) {
/*  84 */       request.appendColumnIsNull(this.prop.getDbColumn());
/*     */     }
/*     */     else {
/*     */       
/*  88 */       request.appendColumn("? = ", this.prop.getDecryptSql());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  98 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/* 101 */     Object value = null;
/* 102 */     if (bean != null) {
/* 103 */       value = this.prop.getValue(bean);
/*     */     }
/*     */ 
/*     */     
/* 107 */     String encryptKeyValue = this.prop.getEncryptKey().getStringValue();
/*     */     
/* 109 */     if (!this.bindEncryptDataFirst)
/*     */     {
/* 111 */       request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
/*     */     }
/* 113 */     request.bindNoLog(value, this.prop, this.prop.getName(), true);
/*     */     
/* 115 */     if (this.bindEncryptDataFirst)
/*     */     {
/* 117 */       request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/* 129 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/* 132 */     Object value = null;
/* 133 */     if (bean != null) {
/* 134 */       value = this.prop.getValue(bean);
/*     */     }
/*     */ 
/*     */     
/* 138 */     String encryptKeyValue = this.prop.getEncryptKey().getStringValue();
/*     */     
/* 140 */     request.bind(value, this.prop, this.prop.getName(), false);
/* 141 */     request.bindNoLog(encryptKeyValue, 12, this.prop.getName() + "=****");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableEncryptedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */