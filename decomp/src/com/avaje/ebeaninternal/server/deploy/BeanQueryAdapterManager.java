/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanQueryAdapter;
/*    */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
/*    */ import java.util.List;
/*    */ import java.util.logging.Logger;
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
/*    */ public class BeanQueryAdapterManager
/*    */ {
/* 34 */   private static final Logger logger = Logger.getLogger(BeanQueryAdapterManager.class.getName());
/*    */ 
/*    */   
/*    */   private final List<BeanQueryAdapter> list;
/*    */ 
/*    */   
/* 40 */   public BeanQueryAdapterManager(BootupClasses bootupClasses) { this.list = bootupClasses.getBeanQueryAdapters(); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int getRegisterCount() { return this.list.size(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addQueryAdapter(DeployBeanDescriptor<?> deployDesc) {
/* 52 */     for (int i = 0; i < this.list.size(); i++) {
/* 53 */       BeanQueryAdapter c = (BeanQueryAdapter)this.list.get(i);
/* 54 */       if (c.isRegisterFor(deployDesc.getBeanType())) {
/* 55 */         logger.fine("BeanQueryAdapter on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
/* 56 */         deployDesc.addQueryAdapter(c);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanQueryAdapterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */