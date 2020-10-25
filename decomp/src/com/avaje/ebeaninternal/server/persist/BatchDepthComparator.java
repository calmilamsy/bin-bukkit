/*    */ package com.avaje.ebeaninternal.server.persist;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ public class BatchDepthComparator
/*    */   extends Object
/*    */   implements Comparator<BatchedBeanHolder>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 264611821665757991L;
/*    */   
/*    */   public int compare(BatchedBeanHolder o1, BatchedBeanHolder o2) {
/* 40 */     BatchedBeanHolder b1 = o1;
/* 41 */     BatchedBeanHolder b2 = o2;
/*    */     
/* 43 */     if (b1.getOrder() < b2.getOrder()) {
/* 44 */       return -1;
/*    */     }
/* 46 */     if (b1.getOrder() == b2.getOrder()) {
/* 47 */       return 0;
/*    */     }
/* 49 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\BatchDepthComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */