/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
/*     */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
/*     */ import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
/*     */ import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class IndexUpdatesBuilder
/*     */ {
/*     */   private final SpiEbeanServer server;
/*     */   private final Map<String, IndexUpdates> map;
/*     */   private final RemoteTransactionEvent txnEvent;
/*     */   
/*  46 */   public static Collection<IndexUpdates> create(SpiEbeanServer server, RemoteTransactionEvent txnEvent) { return (new IndexUpdatesBuilder(server, txnEvent)).create(); }
/*     */ 
/*     */   
/*     */   private IndexUpdatesBuilder(SpiEbeanServer server, RemoteTransactionEvent txnEvent) {
/*  50 */     this.map = new HashMap();
/*  51 */     this.server = server;
/*  52 */     this.txnEvent = txnEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<IndexUpdates> create() {
/*  57 */     Set<IndexInvalidate> indexInvalidations = this.txnEvent.getIndexInvalidations();
/*  58 */     if (indexInvalidations != null) {
/*  59 */       for (IndexInvalidate indexInvalidate : indexInvalidations) {
/*  60 */         LIndex index = this.server.getLuceneIndexManager().getIndex(indexInvalidate.getIndexName());
/*  61 */         BeanDescriptor<?> d = index.getBeanDescriptor();
/*  62 */         getEventByType(d).setInvalidate(true);
/*     */       } 
/*     */     }
/*     */     
/*  66 */     List<TransactionEventTable.TableIUD> tableIUDList = this.txnEvent.getTableIUDList();
/*  67 */     if (tableIUDList != null) {
/*  68 */       for (int i = 0; i < tableIUDList.size(); i++) {
/*  69 */         TransactionEventTable.TableIUD tableIUD = (TransactionEventTable.TableIUD)tableIUDList.get(i);
/*  70 */         List<BeanDescriptor<?>> descList = this.server.getBeanDescriptors(tableIUD.getTable());
/*  71 */         if (descList != null) {
/*  72 */           for (int j = 0; j < descList.size(); j++) {
/*  73 */             BeanDescriptor<?> d = (BeanDescriptor)descList.get(j);
/*  74 */             getEventByType(d).addTableIUD(tableIUD);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  80 */     DeleteByIdMap deleteByIdMap = this.txnEvent.getDeleteByIdMap();
/*  81 */     if (deleteByIdMap != null) {
/*  82 */       for (BeanPersistIds delIds : deleteByIdMap.values()) {
/*  83 */         getEventByType(delIds.getBeanDescriptor()).setDeleteIds(delIds);
/*     */       }
/*     */     }
/*     */     
/*  87 */     List<BeanPersistIds> beanPersistList = this.txnEvent.getBeanPersistList();
/*  88 */     if (beanPersistList != null) {
/*  89 */       for (int i = 0; i < beanPersistList.size(); i++) {
/*  90 */         BeanPersistIds b = (BeanPersistIds)beanPersistList.get(i);
/*  91 */         getEventByType(b.getBeanDescriptor()).setBeanPersistIds(b);
/*     */       } 
/*     */     }
/*     */     
/*  95 */     List<BeanDeltaList> beanDeltaLists = this.txnEvent.getBeanDeltaLists();
/*  96 */     if (beanDeltaLists != null) {
/*  97 */       for (int i = 0; i < beanDeltaLists.size(); i++) {
/*  98 */         BeanDeltaList d = (BeanDeltaList)beanDeltaLists.get(i);
/*  99 */         getEventByType(d.getBeanDescriptor()).setDeltaList(d);
/*     */       } 
/*     */     }
/*     */     
/* 103 */     return this.map.values();
/*     */   }
/*     */ 
/*     */   
/*     */   private IndexUpdates getEventByType(BeanDescriptor<?> d) {
/* 108 */     String beanDescKey = d.getBeanType().getName();
/* 109 */     IndexUpdates eventByType = (IndexUpdates)this.map.get(beanDescKey);
/* 110 */     if (eventByType == null) {
/* 111 */       eventByType = new IndexUpdates(d);
/* 112 */       this.map.put(beanDescKey, eventByType);
/*     */     } 
/* 114 */     return eventByType;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\IndexUpdatesBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */