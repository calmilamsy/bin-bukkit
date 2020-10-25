/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.config.ServerConfig;
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
/*    */ import com.avaje.ebeaninternal.server.persist.dml.DmlBeanPersisterFactory;
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
/*    */ public class BeanManagerFactory
/*    */ {
/*    */   final BeanPersisterFactory peristerFactory;
/*    */   
/* 36 */   public BeanManagerFactory(ServerConfig config, DatabasePlatform dbPlatform) { this.peristerFactory = new DmlBeanPersisterFactory(dbPlatform); }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> BeanManager<T> create(BeanDescriptor<T> desc) {
/* 41 */     BeanPersister persister = this.peristerFactory.create(desc);
/*    */     
/* 43 */     return new BeanManager(desc, persister);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanManagerFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */