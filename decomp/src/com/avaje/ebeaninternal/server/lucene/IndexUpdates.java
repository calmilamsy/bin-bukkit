/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.TransactionEventTable;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
/*    */ import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IndexUpdates
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   private List<TransactionEventTable.TableIUD> tableList;
/*    */   private BeanPersistIds deleteIds;
/*    */   private BeanPersistIds beanPersistIds;
/*    */   private BeanDeltaList deltaList;
/*    */   private boolean invalidate;
/*    */   
/* 45 */   public IndexUpdates(BeanDescriptor<?> beanDescriptor) { this.beanDescriptor = beanDescriptor; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean isInvalidate() { return this.invalidate; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public void setInvalidate(boolean invalidate) { this.invalidate = invalidate; }
/*    */ 
/*    */   
/*    */   public void addTableIUD(TransactionEventTable.TableIUD tableIud) {
/* 61 */     if (this.tableList == null) {
/* 62 */       this.tableList = new ArrayList(4);
/*    */     }
/* 64 */     this.tableList.add(tableIud);
/*    */   }
/*    */ 
/*    */   
/* 68 */   public List<TransactionEventTable.TableIUD> getTableList() { return this.tableList; }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public void setTableList(List<TransactionEventTable.TableIUD> tableList) { this.tableList = tableList; }
/*    */ 
/*    */ 
/*    */   
/* 76 */   public BeanPersistIds getBeanPersistIds() { return this.beanPersistIds; }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public void setBeanPersistIds(BeanPersistIds beanPersistIds) { this.beanPersistIds = beanPersistIds; }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public BeanPersistIds getDeleteIds() { return this.deleteIds; }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public void setDeleteIds(BeanPersistIds deleteIds) { this.deleteIds = deleteIds; }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public BeanDeltaList getDeltaList() { return this.deltaList; }
/*    */ 
/*    */ 
/*    */   
/* 96 */   public void setDeltaList(BeanDeltaList deltaList) { this.deltaList = deltaList; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\IndexUpdates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */