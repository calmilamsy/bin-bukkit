/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.common.BeanMap;
/*    */ import com.avaje.ebean.common.BeanSet;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.CopyContext;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public class CopyBeanCollection<T>
/*    */   extends Object
/*    */ {
/*    */   private final BeanCollection<T> bc;
/*    */   private final BeanDescriptor<T> desc;
/*    */   private final CopyContext ctx;
/*    */   private final int maxDepth;
/*    */   
/*    */   public CopyBeanCollection(BeanCollection<T> bc, BeanDescriptor<T> desc, CopyContext ctx, int maxDepth) {
/* 46 */     this.bc = bc;
/* 47 */     this.desc = desc;
/* 48 */     this.ctx = ctx;
/* 49 */     this.maxDepth = maxDepth;
/*    */   }
/*    */   
/*    */   public BeanCollection<T> copy() {
/* 53 */     if (this.bc instanceof BeanList) {
/* 54 */       return copyList();
/*    */     }
/* 56 */     if (this.bc instanceof BeanSet) {
/* 57 */       return copySet();
/*    */     }
/* 59 */     if (this.bc instanceof BeanMap) {
/* 60 */       return copyMap();
/*    */     }
/*    */     
/* 63 */     String msg = "Invalid beanCollection type " + this.bc.getClass().getName();
/* 64 */     throw new RuntimeException(msg);
/*    */   }
/*    */ 
/*    */   
/*    */   private BeanCollection<T> copyList() {
/* 69 */     BeanList<T> newList = new BeanList<T>();
/* 70 */     List<T> actualList = ((BeanList)this.bc).getActualList();
/*    */     
/* 72 */     for (int i = 0; i < actualList.size(); i++) {
/* 73 */       T t = (T)actualList.get(i);
/* 74 */       newList.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
/*    */     } 
/* 76 */     return newList;
/*    */   }
/*    */   
/*    */   private BeanCollection<T> copySet() {
/* 80 */     BeanSet<T> newSet = new BeanSet<T>();
/* 81 */     Set<T> actualSet = ((BeanSet)this.bc).getActualSet();
/* 82 */     for (T t : actualSet) {
/* 83 */       newSet.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
/*    */     }
/* 85 */     return newSet;
/*    */   }
/*    */ 
/*    */   
/*    */   private BeanCollection<T> copyMap() {
/* 90 */     BeanMap<Object, T> newMap = new BeanMap<Object, T>();
/* 91 */     Map<Object, T> actualMap = ((BeanMap)this.bc).getActualMap();
/* 92 */     Iterator<Map.Entry<Object, T>> iterator = actualMap.entrySet().iterator();
/* 93 */     while (iterator.hasNext()) {
/* 94 */       Map.Entry<Object, T> entry = (Map.Entry)iterator.next();
/* 95 */       newMap.put(entry.getKey(), this.desc.createCopy(entry.getValue(), this.ctx, this.maxDepth));
/*    */     } 
/* 97 */     return newMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\CopyBeanCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */