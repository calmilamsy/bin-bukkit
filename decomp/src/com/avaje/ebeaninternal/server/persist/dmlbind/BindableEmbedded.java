/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
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
/*     */ public class BindableEmbedded
/*     */   implements Bindable
/*     */ {
/*     */   private final Bindable[] items;
/*     */   private final BeanPropertyAssocOne<?> embProp;
/*     */   
/*     */   public BindableEmbedded(BeanPropertyAssocOne<?> embProp, List<Bindable> list) {
/*  41 */     this.embProp = embProp;
/*  42 */     this.items = (Bindable[])list.toArray(new Bindable[list.size()]);
/*     */   }
/*     */ 
/*     */   
/*  46 */   public String toString() { return "BindableEmbedded " + this.embProp + " items:" + Arrays.toString(this.items); }
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) { dmlAppend(request, checkIncludes); }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  54 */     if (checkIncludes && !request.isIncluded(this.embProp)) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     for (int i = 0; i < this.items.length; i++) {
/*  59 */       this.items[i].dmlAppend(request, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object origBean) {
/*  67 */     if (checkIncludes && !request.isIncludedWhere(this.embProp)) {
/*     */       return;
/*     */     }
/*  70 */     Object embBean = this.embProp.getValue(origBean);
/*  71 */     Object oldValues = getOldValue(embBean);
/*     */     
/*  73 */     for (int i = 0; i < this.items.length; i++) {
/*  74 */       this.items[i].dmlWhere(request, false, oldValues);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/*  79 */     if (request.hasChanged(this.embProp)) {
/*  80 */       list.add(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/*  87 */     if (checkIncludes && !bindRequest.isIncluded(this.embProp)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  92 */     Object embBean = this.embProp.getValue(bean);
/*     */     
/*  94 */     for (int i = 0; i < this.items.length; i++) {
/*  95 */       this.items[i].dmlBind(bindRequest, false, embBean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/* 102 */     if (checkIncludes && !bindRequest.isIncludedWhere(this.embProp)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 107 */     Object embBean = this.embProp.getValue(bean);
/* 108 */     Object oldEmbBean = getOldValue(embBean);
/*     */     
/* 110 */     for (int i = 0; i < this.items.length; i++) {
/* 111 */       this.items[i].dmlBindWhere(bindRequest, false, oldEmbBean);
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
/*     */   private Object getOldValue(Object embBean) {
/* 123 */     Object oldValues = null;
/*     */     
/* 125 */     if (embBean instanceof EntityBean)
/*     */     {
/* 127 */       oldValues = ((EntityBean)embBean)._ebean_getIntercept().getOldValues();
/*     */     }
/*     */     
/* 130 */     if (oldValues == null)
/*     */     {
/*     */       
/* 133 */       oldValues = embBean;
/*     */     }
/*     */     
/* 136 */     return oldValues;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */