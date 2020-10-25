/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryId
/*    */ {
/*    */   public BindableId createId(BeanDescriptor<?> desc) {
/* 40 */     BeanProperty[] uids = desc.propertiesId();
/* 41 */     if (uids.length == 1) {
/* 42 */       if (!uids[0].isEmbedded()) {
/* 43 */         return new BindableIdScalar(uids[0]);
/*    */       }
/*    */       
/* 46 */       BeanPropertyAssocOne<?> embId = (BeanPropertyAssocOne)uids[0];
/* 47 */       return new BindableIdEmbedded(embId, desc);
/*    */     } 
/*    */ 
/*    */     
/* 51 */     return new BindableIdMap(uids, desc);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */