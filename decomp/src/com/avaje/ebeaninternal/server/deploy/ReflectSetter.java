/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
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
/*    */ public class ReflectSetter
/*    */ {
/* 39 */   public static BeanReflectSetter create(DeployBeanProperty prop) { return new NeverCalled(prop.getFullBeanName()); }
/*    */ 
/*    */   
/*    */   public static class NeverCalled
/*    */     implements BeanReflectSetter
/*    */   {
/*    */     private final String property;
/*    */     
/* 47 */     public NeverCalled(String property) { this.property = property; }
/*    */ 
/*    */ 
/*    */     
/* 51 */     public void set(Object bean, Object value) { throw new RuntimeException("Should never be called on " + this.property); }
/*    */ 
/*    */ 
/*    */     
/* 55 */     public void setIntercept(Object bean, Object value) { throw new RuntimeException("Should never be called on " + this.property); }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ReflectSetter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */