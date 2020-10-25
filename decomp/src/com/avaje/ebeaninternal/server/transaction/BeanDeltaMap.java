/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class BeanDeltaMap
/*    */ {
/* 31 */   private Map<String, BeanDeltaList> deltaMap = new HashMap();
/*    */ 
/*    */   
/*    */   public BeanDeltaMap() {}
/*    */   
/*    */   public BeanDeltaMap(List<BeanDelta> deltaBeans) {
/* 37 */     if (deltaBeans != null) {
/* 38 */       for (int i = 0; i < deltaBeans.size(); i++) {
/* 39 */         BeanDelta deltaBean = (BeanDelta)deltaBeans.get(i);
/* 40 */         addBeanDelta(deltaBean);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 46 */   public String toString() { return this.deltaMap.values().toString(); }
/*    */ 
/*    */   
/*    */   public void addBeanDelta(BeanDelta beanDelta) {
/* 50 */     BeanDescriptor<?> d = beanDelta.getBeanDescriptor();
/* 51 */     BeanDeltaList list = getDeltaBeanList(d);
/* 52 */     list.add(beanDelta);
/*    */   }
/*    */ 
/*    */   
/* 56 */   public Collection<BeanDeltaList> deltaLists() { return this.deltaMap.values(); }
/*    */ 
/*    */   
/*    */   private BeanDeltaList getDeltaBeanList(BeanDescriptor<?> d) {
/* 60 */     BeanDeltaList deltaList = (BeanDeltaList)this.deltaMap.get(d.getFullName());
/* 61 */     if (deltaList == null) {
/* 62 */       deltaList = new BeanDeltaList(d);
/* 63 */       this.deltaMap.put(d.getFullName(), deltaList);
/*    */     } 
/* 65 */     return deltaList;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */