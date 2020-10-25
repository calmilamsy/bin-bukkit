/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
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
/*    */ public final class BeanPersistIdMap
/*    */ {
/* 35 */   private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap();
/*    */ 
/*    */   
/* 38 */   public String toString() { return this.beanMap.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean isEmpty() { return this.beanMap.isEmpty(); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public Collection<BeanPersistIds> values() { return this.beanMap.values(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(BeanDescriptor<?> desc, PersistRequest.Type type, Object id) {
/* 54 */     BeanPersistIds r = getPersistIds(desc);
/* 55 */     r.addId(type, (Serializable)id);
/*    */   }
/*    */   
/*    */   private BeanPersistIds getPersistIds(BeanDescriptor<?> desc) {
/* 59 */     String beanType = desc.getFullName();
/* 60 */     BeanPersistIds r = (BeanPersistIds)this.beanMap.get(beanType);
/* 61 */     if (r == null) {
/* 62 */       r = new BeanPersistIds(desc);
/* 63 */       this.beanMap.put(beanType, r);
/*    */     } 
/* 65 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\transaction\BeanPersistIdMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */