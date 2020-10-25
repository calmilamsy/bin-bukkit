/*    */ package com.avaje.ebean.event;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BeanPersistAdapter
/*    */   implements BeanPersistController
/*    */ {
/*    */   public abstract boolean isRegisterFor(Class<?> paramClass);
/*    */   
/* 45 */   public int getExecutionOrder() { return 10; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public boolean preDelete(BeanPersistRequest<?> request) { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public boolean preInsert(BeanPersistRequest<?> request) { return true; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public boolean preUpdate(BeanPersistRequest<?> request) { return true; }
/*    */   
/*    */   public void postDelete(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postInsert(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postUpdate(BeanPersistRequest<?> request) {}
/*    */   
/*    */   public void postLoad(Object bean, Set<String> includedProperties) {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\event\BeanPersistAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */