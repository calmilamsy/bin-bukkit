/*    */ package com.avaje.ebeaninternal.server.persist.dml;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersister;
/*    */ import com.avaje.ebeaninternal.server.persist.BeanPersisterFactory;
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
/*    */ public class DmlBeanPersisterFactory
/*    */   implements BeanPersisterFactory
/*    */ {
/*    */   private final MetaFactory metaFactory;
/*    */   
/* 35 */   public DmlBeanPersisterFactory(DatabasePlatform dbPlatform) { this.metaFactory = new MetaFactory(dbPlatform); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BeanPersister create(BeanDescriptor<?> desc) {
/* 44 */     UpdateMeta updMeta = this.metaFactory.createUpdate(desc);
/* 45 */     DeleteMeta delMeta = this.metaFactory.createDelete(desc);
/* 46 */     InsertMeta insMeta = this.metaFactory.createInsert(desc);
/*    */     
/* 48 */     return new DmlBeanPersister(updMeta, insMeta, delMeta);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\persist\dml\DmlBeanPersisterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */