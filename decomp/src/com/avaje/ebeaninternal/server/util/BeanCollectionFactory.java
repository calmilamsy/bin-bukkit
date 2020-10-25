/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.common.BeanMap;
/*    */ import com.avaje.ebean.common.BeanSet;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.LinkedHashSet;
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
/*    */ public class BeanCollectionFactory
/*    */ {
/*    */   private static final int defaultListInitialCapacity = 20;
/*    */   private static final int defaultSetInitialCapacity = 32;
/*    */   private static final int defaultMapInitialCapacity = 32;
/*    */   
/*    */   private static class BeanCollectionFactoryHolder
/*    */   {
/* 41 */     private static BeanCollectionFactory me = new BeanCollectionFactory(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BeanCollectionFactory() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public static BeanCollection<?> create(BeanCollectionParams params) { return me.createMany(params); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BeanCollection<?> createMany(BeanCollectionParams params) {
/* 62 */     Query.Type manyType = params.getManyType();
/* 63 */     switch (manyType) {
/*    */       case MAP:
/* 65 */         return createMap(params);
/*    */       case LIST:
/* 67 */         return createList(params);
/*    */       case SET:
/* 69 */         return createSet(params);
/*    */     } 
/*    */     
/* 72 */     throw new RuntimeException("Invalid Arg " + manyType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   private BeanMap createMap(BeanCollectionParams params) { return new BeanMap(new LinkedHashMap(32)); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 86 */   private BeanSet createSet(BeanCollectionParams params) { return new BeanSet(new LinkedHashSet(32)); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   private BeanList createList(BeanCollectionParams params) { return new BeanList(new ArrayList(20)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\BeanCollectionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */