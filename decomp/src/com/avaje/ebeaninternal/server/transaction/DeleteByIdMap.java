/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeleteByIdMap
/*    */ {
/* 36 */   private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap();
/*    */ 
/*    */   
/* 39 */   public String toString() { return this.beanMap.toString(); }
/*    */ 
/*    */   
/*    */   public void notifyCache() {
/* 43 */     for (BeanPersistIds deleteIds : this.beanMap.values()) {
/* 44 */       BeanDescriptor<?> d = deleteIds.getBeanDescriptor();
/* 45 */       List<Serializable> idValues = deleteIds.getDeleteIds();
/* 46 */       if (idValues != null) {
/* 47 */         d.queryCacheClear();
/* 48 */         for (int i = 0; i < idValues.size(); i++) {
/* 49 */           d.cacheRemove(idValues.get(i));
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 57 */   public boolean isEmpty() { return this.beanMap.isEmpty(); }
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Collection<BeanPersistIds> values() { return this.beanMap.values(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(BeanDescriptor<?> desc, Object id) {
/* 69 */     BeanPersistIds r = getPersistIds(desc);
/* 70 */     r.addId(PersistRequest.Type.DELETE, (Serializable)id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addList(BeanDescriptor<?> desc, List<Object> idList) {
/* 78 */     BeanPersistIds r = getPersistIds(desc);
/* 79 */     for (int i = 0; i < idList.size(); i++) {
/* 80 */       r.addId(PersistRequest.Type.DELETE, (Serializable)idList.get(i));
/*    */     }
/*    */   }
/*    */   
/*    */   private BeanPersistIds getPersistIds(BeanDescriptor<?> desc) {
/* 85 */     String beanType = desc.getFullName();
/* 86 */     BeanPersistIds r = (BeanPersistIds)this.beanMap.get(beanType);
/* 87 */     if (r == null) {
/* 88 */       r = new BeanPersistIds(desc);
/* 89 */       this.beanMap.put(beanType, r);
/*    */     } 
/* 91 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\DeleteByIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */