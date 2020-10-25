/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
/*     */ import java.util.ArrayList;
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
/*     */ public class DeleteUnloadedForeignKeys
/*     */ {
/*     */   private final List<BeanPropertyAssocOne<?>> propList;
/*     */   private final SpiEbeanServer server;
/*     */   private final PersistRequestBean<?> request;
/*     */   private Object beanWithForeignKeys;
/*     */   
/*     */   public DeleteUnloadedForeignKeys(SpiEbeanServer server, PersistRequestBean<?> request) {
/*  45 */     this.propList = new ArrayList(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     this.server = server;
/*  55 */     this.request = request;
/*     */   }
/*     */ 
/*     */   
/*  59 */   public boolean isEmpty() { return this.propList.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public void add(BeanPropertyAssocOne<?> prop) { this.propList.add(prop); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryForeignKeys() {
/*  72 */     BeanDescriptor<?> descriptor = this.request.getBeanDescriptor();
/*  73 */     SpiQuery<?> q = (SpiQuery)this.server.createQuery(descriptor.getBeanType());
/*     */     
/*  75 */     Object id = this.request.getBeanId();
/*     */     
/*  77 */     StringBuilder sb = new StringBuilder(30);
/*  78 */     for (i = 0; i < this.propList.size(); i++) {
/*  79 */       sb.append(((BeanPropertyAssocOne)this.propList.get(i)).getName()).append(",");
/*     */     }
/*     */ 
/*     */     
/*  83 */     q.setPersistenceContext(new DefaultPersistenceContext());
/*  84 */     q.setAutofetch(false);
/*  85 */     q.select(sb.toString());
/*  86 */     q.where().idEq(id);
/*     */     
/*  88 */     SpiTransaction t = this.request.getTransaction();
/*  89 */     if (t.isLogSummary()) {
/*  90 */       t.logInternal("-- Ebean fetching foreign key values for delete of " + descriptor.getName() + " id:" + id);
/*     */     }
/*  92 */     this.beanWithForeignKeys = this.server.findUnique(q, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteCascade() {
/* 101 */     for (int i = 0; i < this.propList.size(); i++) {
/* 102 */       BeanPropertyAssocOne<?> prop = (BeanPropertyAssocOne)this.propList.get(i);
/* 103 */       Object detailBean = prop.getValue(this.beanWithForeignKeys);
/*     */ 
/*     */       
/* 106 */       if (detailBean != null && prop.hasId(detailBean))
/* 107 */         this.server.delete(detailBean, this.request.getTransaction()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\DeleteUnloadedForeignKeys.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */