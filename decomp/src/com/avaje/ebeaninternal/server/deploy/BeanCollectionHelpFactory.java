/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeanCollectionHelpFactory
/*    */ {
/*    */   public static <T> BeanCollectionHelp<T> create(BeanPropertyAssocMany<T> manyProperty) {
/* 17 */     ManyType manyType = manyProperty.getManyType();
/* 18 */     switch (manyType.getUnderlying()) {
/*    */       case LIST:
/* 20 */         return new BeanListHelp(manyProperty);
/*    */       case SET:
/* 22 */         return new BeanSetHelp(manyProperty);
/*    */       case MAP:
/* 24 */         return new BeanMapHelp(manyProperty);
/*    */     } 
/* 26 */     throw new RuntimeException("Invalid type " + manyType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> BeanCollectionHelp<T> create(OrmQueryRequest<T> request) {
/* 33 */     Query.Type manyType = request.getQuery().getType();
/*    */     
/* 35 */     if (manyType.equals(Query.Type.LIST)) {
/* 36 */       return new BeanListHelp();
/*    */     }
/* 38 */     if (manyType.equals(Query.Type.SET)) {
/* 39 */       return new BeanSetHelp();
/*    */     }
/*    */     
/* 42 */     BeanDescriptor<T> target = request.getBeanDescriptor();
/* 43 */     String mapKey = request.getQuery().getMapKey();
/* 44 */     return new BeanMapHelp(target, mapKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanCollectionHelpFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */