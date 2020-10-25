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
/*     */ public class BindableProperty
/*     */   implements Bindable
/*     */ {
/*     */   protected final BeanProperty prop;
/*     */   
/*  37 */   public BindableProperty(BeanProperty prop) { this.prop = prop; }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public String toString() { return this.prop.toString(); }
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/*  45 */     if (request.hasChanged(this.prop)) {
/*  46 */       list.add(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  51 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  55 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/*  58 */     request.appendColumn(this.prop.getDbColumn());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/*  65 */     if (checkIncludes && !request.isIncludedWhere(this.prop)) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     if (bean == null || request.isDbNull(this.prop.getValue(bean))) {
/*  70 */       request.appendColumnIsNull(this.prop.getDbColumn());
/*     */     } else {
/*     */       
/*  73 */       request.appendColumn(this.prop.getDbColumn());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  79 */     if (checkIncludes && !request.isIncluded(this.prop)) {
/*     */       return;
/*     */     }
/*  82 */     dmlBind(request, bean, true);
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
/*  86 */     if (checkIncludes && !request.isIncludedWhere(this.prop)) {
/*     */       return;
/*     */     }
/*  89 */     dmlBind(request, bean, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/*  95 */     Object value = null;
/*  96 */     if (bean != null) {
/*  97 */       value = this.prop.getValue(bean);
/*     */     }
/*     */     
/* 100 */     request.bind(value, this.prop, this.prop.getName(), bindNull);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */