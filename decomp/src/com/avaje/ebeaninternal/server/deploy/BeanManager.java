/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
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
/*    */ public class BeanManager<T>
/*    */   extends Object
/*    */ {
/*    */   private final BeanPersister persister;
/*    */   private final BeanDescriptor<T> descriptor;
/*    */   
/*    */   public BeanManager(BeanDescriptor<T> descriptor, BeanPersister persister) {
/* 34 */     this.descriptor = descriptor;
/* 35 */     this.persister = persister;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public BeanPersister getBeanPersister() { return this.persister; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public BeanDescriptor<T> getBeanDescriptor() { return this.descriptor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean isLdapEntityType() { return this.descriptor.isLdapEntityType(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */