/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDelta;
/*     */ import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
/*     */ import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class TransactionEvent
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7230903304106097120L;
/*     */   private boolean local = true;
/*     */   private boolean invalidateAll;
/*     */   private TransactionEventTable eventTables;
/*     */   private TransactionEventBeans eventBeans;
/*     */   private List<BeanDelta> beanDeltas;
/*     */   private DeleteByIdMap deleteByIdMap;
/*     */   private Set<IndexInvalidate> indexInvalidations;
/*     */   private Set<String> pauseIndexInvalidate;
/*     */   
/*  77 */   public void setInvalidateAll(boolean isInvalidateAll) { this.invalidateAll = isInvalidateAll; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isInvalidateAll() { return this.invalidateAll; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseIndexInvalidate(Class<?> beanType) {
/*  92 */     if (this.pauseIndexInvalidate == null) {
/*  93 */       this.pauseIndexInvalidate = new HashSet();
/*     */     }
/*  95 */     this.pauseIndexInvalidate.add(beanType.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeIndexInvalidate(Class<?> beanType) {
/* 102 */     if (this.pauseIndexInvalidate != null) {
/* 103 */       this.pauseIndexInvalidate.remove(beanType.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addIndexInvalidate(IndexInvalidate indexEvent) {
/* 111 */     if (this.pauseIndexInvalidate != null && this.pauseIndexInvalidate.contains(indexEvent.getIndexName())) {
/* 112 */       System.out.println("--- IGNORE Invalidate on " + indexEvent.getIndexName());
/*     */       return;
/*     */     } 
/* 115 */     if (this.indexInvalidations == null) {
/* 116 */       this.indexInvalidations = new HashSet();
/*     */     }
/* 118 */     this.indexInvalidations.add(indexEvent);
/*     */   }
/*     */   
/*     */   public void addDeleteById(BeanDescriptor<?> desc, Object id) {
/* 122 */     if (this.deleteByIdMap == null) {
/* 123 */       this.deleteByIdMap = new DeleteByIdMap();
/*     */     }
/* 125 */     this.deleteByIdMap.add(desc, id);
/*     */   }
/*     */   
/*     */   public void addDeleteByIdList(BeanDescriptor<?> desc, List<Object> idList) {
/* 129 */     if (this.deleteByIdMap == null) {
/* 130 */       this.deleteByIdMap = new DeleteByIdMap();
/*     */     }
/* 132 */     this.deleteByIdMap.addList(desc, idList);
/*     */   }
/*     */ 
/*     */   
/* 136 */   public DeleteByIdMap getDeleteByIdMap() { return this.deleteByIdMap; }
/*     */ 
/*     */   
/*     */   public void addBeanDelta(BeanDelta delta) {
/* 140 */     if (this.beanDeltas == null) {
/* 141 */       this.beanDeltas = new ArrayList();
/*     */     }
/* 143 */     this.beanDeltas.add(delta);
/*     */   }
/*     */ 
/*     */   
/* 147 */   public List<BeanDelta> getBeanDeltas() { return this.beanDeltas; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public boolean isLocal() { return this.local; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public TransactionEventBeans getEventBeans() { return this.eventBeans; }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public TransactionEventTable getEventTables() { return this.eventTables; }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public Set<IndexInvalidate> getIndexInvalidations() { return this.indexInvalidations; }
/*     */ 
/*     */   
/*     */   public void add(String tableName, boolean inserts, boolean updates, boolean deletes) {
/* 174 */     if (this.eventTables == null) {
/* 175 */       this.eventTables = new TransactionEventTable();
/*     */     }
/* 177 */     this.eventTables.add(tableName, inserts, updates, deletes);
/*     */   }
/*     */   
/*     */   public void add(TransactionEventTable table) {
/* 181 */     if (this.eventTables == null) {
/* 182 */       this.eventTables = new TransactionEventTable();
/*     */     }
/* 184 */     this.eventTables.add(table);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(PersistRequestBean<?> request) {
/* 192 */     if (request.isNotify(this)) {
/*     */       
/* 194 */       if (this.eventBeans == null) {
/* 195 */         this.eventBeans = new TransactionEventBeans();
/*     */       }
/* 197 */       this.eventBeans.add(request);
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
/*     */   
/*     */   public void notifyCache() {
/* 210 */     if (this.eventBeans != null) {
/* 211 */       this.eventBeans.notifyCache();
/*     */     }
/* 213 */     if (this.deleteByIdMap != null)
/* 214 */       this.deleteByIdMap.notifyCache(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\TransactionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */