/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class BindableIdEmbedded
/*     */   implements BindableId
/*     */ {
/*     */   private final BeanPropertyAssocOne<?> embId;
/*     */   private final BeanProperty[] props;
/*     */   private final MatchedImportedProperty[] matches;
/*     */   
/*     */   public BindableIdEmbedded(BeanPropertyAssocOne<?> embId, BeanDescriptor<?> desc) {
/*  46 */     this.embId = embId;
/*  47 */     this.props = embId.getTargetDescriptor().propertiesBaseScalar();
/*  48 */     this.matches = MatchedImportedProperty.build(this.props, desc);
/*     */   }
/*     */ 
/*     */   
/*  52 */   public boolean isConcatenated() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   public String getIdentityColumn() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public String toString() { return this.embId + " props:" + Arrays.toString(this.props); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, true); }
/*     */ 
/*     */ 
/*     */   
/*  77 */   public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException { dmlBind(request, checkIncludes, bean, false); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
/*  83 */     if (checkIncludes && !bindRequest.isIncluded(this.embId)) {
/*     */       return;
/*     */     }
/*     */     
/*  87 */     Object idValue = this.embId.getValue(bean);
/*     */     
/*  89 */     for (int i = 0; i < this.props.length; i++) {
/*     */       
/*  91 */       Object value = this.props[i].getValue(idValue);
/*  92 */       bindRequest.bind(value, this.props[i], this.props[i].getDbColumn(), bindNull);
/*     */     } 
/*     */     
/*  95 */     bindRequest.setIdValue(idValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
/* 102 */     if (checkIncludes && !request.isIncluded(this.embId)) {
/*     */       return;
/*     */     }
/* 105 */     dmlAppend(request, false);
/*     */   }
/*     */ 
/*     */   
/* 109 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/* 113 */     if (checkIncludes && !request.isIncluded(this.embId)) {
/*     */       return;
/*     */     }
/* 116 */     for (int i = 0; i < this.props.length; i++) {
/* 117 */       request.appendColumn(this.props[i].getDbColumn());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
/* 123 */     if (this.matches == null) {
/* 124 */       String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
/*     */ 
/*     */       
/* 127 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 130 */     Object bean = persist.getBean();
/*     */ 
/*     */     
/* 133 */     Object newId = this.embId.createEmbeddedId();
/*     */ 
/*     */     
/* 136 */     for (int i = 0; i < this.matches.length; i++) {
/* 137 */       this.matches[i].populate(bean, newId);
/*     */     }
/*     */ 
/*     */     
/* 141 */     this.embId.setValueIntercept(bean, newId);
/* 142 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableIdEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */