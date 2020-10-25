/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.IOException;
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
/*    */ public class BeanDeltaList
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   private final List<BeanDelta> deltaBeans;
/*    */   
/*    */   public BeanDeltaList(BeanDescriptor<?> beanDescriptor) {
/* 33 */     this.deltaBeans = new ArrayList();
/*    */ 
/*    */     
/* 36 */     this.beanDescriptor = beanDescriptor;
/*    */   }
/*    */ 
/*    */   
/* 40 */   public String toString() { return this.deltaBeans.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public BeanDescriptor<?> getBeanDescriptor() { return this.beanDescriptor; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void add(BeanDelta b) { this.deltaBeans.add(b); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public List<BeanDelta> getDeltaBeans() { return this.deltaBeans; }
/*    */ 
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
/* 56 */     for (int i = 0; i < this.deltaBeans.size(); i++)
/* 57 */       ((BeanDelta)this.deltaBeans.get(i)).writeBinaryMessage(msgList); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */