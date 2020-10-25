/*    */ package com.avaje.ebeaninternal.server.deploy.id;
/*    */ 
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
/*    */ public class IdBinderFactory
/*    */ {
/* 30 */   private static final IdBinderEmpty EMPTY = new IdBinderEmpty();
/*    */   
/*    */   private final boolean idInExpandedForm;
/*    */ 
/*    */   
/* 35 */   public IdBinderFactory(boolean idInExpandedForm) { this.idInExpandedForm = idInExpandedForm; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdBinder createIdBinder(BeanProperty[] uids) {
/* 43 */     if (uids.length == 0)
/*    */     {
/* 45 */       return EMPTY;
/*    */     }
/* 47 */     if (uids.length == 1) {
/* 48 */       if (uids[0].isEmbedded()) {
/* 49 */         return new IdBinderEmbedded(this.idInExpandedForm, (BeanPropertyAssocOne)uids[0]);
/*    */       }
/* 51 */       return new IdBinderSimple(uids[0]);
/*    */     } 
/*    */ 
/*    */     
/* 55 */     return new IdBinderMultiple(uids);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\id\IdBinderFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */