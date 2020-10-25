/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
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
/*     */ public class BatchedBeanHolder
/*     */ {
/*     */   private final BatchControl control;
/*     */   private final String shortDesc;
/*     */   private final int order;
/*     */   private ArrayList<PersistRequest> inserts;
/*     */   private ArrayList<PersistRequest> updates;
/*     */   private ArrayList<PersistRequest> deletes;
/*     */   private HashSet<Integer> beanHashCodes;
/*     */   
/*     */   public BatchedBeanHolder(BatchControl control, BeanDescriptor<?> beanDescriptor, int order) {
/*  70 */     this.beanHashCodes = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.control = control;
/*  77 */     this.shortDesc = beanDescriptor.getName() + ":" + order;
/*  78 */     this.order = order;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public int getOrder() { return this.order; }
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
/*     */   public void executeNow() {
/* 101 */     if (this.inserts != null) {
/* 102 */       this.control.executeNow(this.inserts);
/* 103 */       this.inserts.clear();
/*     */     } 
/* 105 */     if (this.updates != null) {
/* 106 */       this.control.executeNow(this.updates);
/* 107 */       this.updates.clear();
/*     */     } 
/* 109 */     if (this.deletes != null) {
/* 110 */       this.control.executeNow(this.deletes);
/* 111 */       this.deletes.clear();
/*     */     } 
/* 113 */     this.beanHashCodes.clear();
/*     */   }
/*     */ 
/*     */   
/* 117 */   public String toString() { return this.shortDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<PersistRequest> getList(PersistRequestBean<?> request) {
/* 125 */     Integer objHashCode = Integer.valueOf(System.identityHashCode(request.getBean()));
/*     */     
/* 127 */     if (!this.beanHashCodes.add(objHashCode))
/*     */     {
/*     */ 
/*     */       
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     switch (request.getType()) {
/*     */       case INSERT:
/* 136 */         if (this.inserts == null) {
/* 137 */           this.inserts = new ArrayList();
/*     */         }
/* 139 */         return this.inserts;
/*     */       
/*     */       case UPDATE:
/* 142 */         if (this.updates == null) {
/* 143 */           this.updates = new ArrayList();
/*     */         }
/* 145 */         return this.updates;
/*     */       
/*     */       case DELETE:
/* 148 */         if (this.deletes == null) {
/* 149 */           this.deletes = new ArrayList();
/*     */         }
/* 151 */         return this.deletes;
/*     */     } 
/*     */     
/* 154 */     throw new RuntimeException("Invalid type code " + request.getType());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BatchedBeanHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */