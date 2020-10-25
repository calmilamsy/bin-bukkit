/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanPersistController;
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
/*    */ public class PersistControllerManager
/*    */ {
/* 34 */   private static final Logger logger = Logger.getLogger(PersistControllerManager.class.getName());
/*    */ 
/*    */   
/*    */   private final List<BeanPersistController> list;
/*    */ 
/*    */   
/* 40 */   public PersistControllerManager(BootupClasses bootupClasses) { this.list = bootupClasses.getBeanPersistControllers(); }
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
/*    */   public void addPersistControllers(DeployBeanDescriptor<?> deployDesc) {
/* 52 */     for (int i = 0; i < this.list.size(); i++) {
/* 53 */       BeanPersistController c = (BeanPersistController)this.list.get(i);
/* 54 */       if (c.isRegisterFor(deployDesc.getBeanType())) {
/* 55 */         logger.fine("BeanPersistController on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
/* 56 */         deployDesc.addPersistController(c);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\PersistControllerManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */