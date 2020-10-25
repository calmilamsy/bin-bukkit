/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import java.io.IOException;
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
/*     */ public class RemoteTransactionEvent
/*     */   implements Serializable, Runnable
/*     */ {
/*     */   private static final long serialVersionUID = 757920022500956949L;
/*     */   private List<BeanPersistIds> beanPersistList;
/*     */   private List<TransactionEventTable.TableIUD> tableList;
/*     */   private List<BeanDeltaList> beanDeltaLists;
/*     */   private BeanDeltaMap beanDeltaMap;
/*     */   private List<IndexEvent> indexEventList;
/*     */   private Set<IndexInvalidate> indexInvalidations;
/*     */   private DeleteByIdMap deleteByIdMap;
/*     */   private String serverName;
/*     */   private SpiEbeanServer server;
/*     */   
/*     */   public RemoteTransactionEvent(String serverName) {
/*  37 */     this.beanPersistList = new ArrayList();
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
/*  56 */     this.serverName = serverName;
/*     */   }
/*     */   public RemoteTransactionEvent(SpiEbeanServer server) {
/*     */     this.beanPersistList = new ArrayList();
/*  60 */     this.server = server;
/*     */   }
/*     */ 
/*     */   
/*  64 */   public void run() { this.server.remoteTransactionEvent(this); }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  68 */     StringBuilder sb = new StringBuilder();
/*  69 */     if (this.beanDeltaMap != null) {
/*  70 */       sb.append(this.beanDeltaMap);
/*     */     }
/*  72 */     sb.append(this.beanPersistList);
/*  73 */     if (this.tableList != null) {
/*  74 */       sb.append(this.tableList);
/*     */     }
/*  76 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/*  81 */     if (this.indexInvalidations != null) {
/*  82 */       for (IndexInvalidate indexInvalidate : this.indexInvalidations) {
/*  83 */         indexInvalidate.writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  87 */     if (this.tableList != null) {
/*  88 */       for (int i = 0; i < this.tableList.size(); i++) {
/*  89 */         ((TransactionEventTable.TableIUD)this.tableList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  93 */     if (this.deleteByIdMap != null) {
/*  94 */       for (BeanPersistIds deleteIds : this.deleteByIdMap.values()) {
/*  95 */         deleteIds.writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/*  99 */     if (this.beanPersistList != null) {
/* 100 */       for (int i = 0; i < this.beanPersistList.size(); i++) {
/* 101 */         ((BeanPersistIds)this.beanPersistList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/* 105 */     if (this.beanDeltaLists != null) {
/* 106 */       for (int i = 0; i < this.beanDeltaLists.size(); i++) {
/* 107 */         ((BeanDeltaList)this.beanDeltaLists.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */     
/* 111 */     if (this.indexEventList != null) {
/* 112 */       for (int i = 0; i < this.indexEventList.size(); i++) {
/* 113 */         ((IndexEvent)this.indexEventList.get(i)).writeBinaryMessage(msgList);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 119 */   public boolean isEmpty() { return (this.beanPersistList.isEmpty() && (this.tableList == null || this.tableList.isEmpty())); }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void addBeanPersistIds(BeanPersistIds beanPersist) { this.beanPersistList.add(beanPersist); }
/*     */ 
/*     */   
/*     */   public void addIndexInvalidate(IndexInvalidate indexInvalidate) {
/* 127 */     if (this.indexInvalidations == null) {
/* 128 */       this.indexInvalidations = new HashSet();
/*     */     }
/* 130 */     this.indexInvalidations.add(indexInvalidate);
/*     */   }
/*     */   
/*     */   public void addTableIUD(TransactionEventTable.TableIUD tableIud) {
/* 134 */     if (this.tableList == null) {
/* 135 */       this.tableList = new ArrayList(4);
/*     */     }
/* 137 */     this.tableList.add(tableIud);
/*     */   }
/*     */   
/*     */   public void addBeanDeltaList(BeanDeltaList deltaList) {
/* 141 */     if (this.beanDeltaLists == null) {
/* 142 */       this.beanDeltaLists = new ArrayList();
/*     */     }
/* 144 */     this.beanDeltaLists.add(deltaList);
/*     */   }
/*     */   
/*     */   public void addBeanDelta(BeanDelta beanDelta) {
/* 148 */     if (this.beanDeltaMap == null) {
/* 149 */       this.beanDeltaMap = new BeanDeltaMap();
/*     */     }
/* 151 */     this.beanDeltaMap.addBeanDelta(beanDelta);
/*     */   }
/*     */   
/*     */   public void addIndexEvent(IndexEvent indexEvent) {
/* 155 */     if (this.indexEventList == null) {
/* 156 */       this.indexEventList = new ArrayList(2);
/*     */     }
/* 158 */     this.indexEventList.add(indexEvent);
/*     */   }
/*     */ 
/*     */   
/* 162 */   public String getServerName() { return this.serverName; }
/*     */ 
/*     */ 
/*     */   
/* 166 */   public SpiEbeanServer getServer() { return this.server; }
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setServer(SpiEbeanServer server) { this.server = server; }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public DeleteByIdMap getDeleteByIdMap() { return this.deleteByIdMap; }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void setDeleteByIdMap(DeleteByIdMap deleteByIdMap) { this.deleteByIdMap = deleteByIdMap; }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public Set<IndexInvalidate> getIndexInvalidations() { return this.indexInvalidations; }
/*     */ 
/*     */ 
/*     */   
/* 186 */   public List<IndexEvent> getIndexEventList() { return this.indexEventList; }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public List<TransactionEventTable.TableIUD> getTableIUDList() { return this.tableList; }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public List<BeanPersistIds> getBeanPersistList() { return this.beanPersistList; }
/*     */ 
/*     */   
/*     */   public List<BeanDeltaList> getBeanDeltaLists() {
/* 198 */     if (this.beanDeltaMap != null) {
/* 199 */       this.beanDeltaLists.addAll(this.beanDeltaMap.deltaLists());
/*     */     }
/* 201 */     return this.beanDeltaLists;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\RemoteTransactionEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */