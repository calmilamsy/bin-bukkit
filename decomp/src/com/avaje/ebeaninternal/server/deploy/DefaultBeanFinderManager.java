/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class DefaultBeanFinderManager
/*    */   implements BeanFinderManager
/*    */ {
/* 34 */   HashMap<Class<?>, BeanFinder<?>> registerFor = new HashMap();
/*    */ 
/*    */   
/*    */   public int createBeanFinders(List<Class<?>> finderClassList) {
/* 38 */     for (Class<?> cls : finderClassList) {
/* 39 */       Class<?> entityType = getEntityClass(cls);
/*    */       try {
/* 41 */         BeanFinder<?> beanFinder = (BeanFinder)cls.newInstance();
/* 42 */         this.registerFor.put(entityType, beanFinder);
/*    */       }
/* 44 */       catch (Exception ex) {
/* 45 */         throw new PersistenceException(ex);
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return this.registerFor.size();
/*    */   }
/*    */ 
/*    */   
/* 53 */   public int getRegisterCount() { return this.registerFor.size(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public <T> BeanFinder<T> getBeanFinder(Class<T> entityType) { return (BeanFinder)this.registerFor.get(entityType); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Class<?> getEntityClass(Class<?> controller) {
/* 72 */     Class<?> cls = ParamTypeUtil.findParamType(controller, BeanFinder.class);
/*    */     
/* 74 */     if (cls == null) {
/* 75 */       String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
/* 76 */       throw new PersistenceException(msg);
/*    */     } 
/* 78 */     return cls;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DefaultBeanFinderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */