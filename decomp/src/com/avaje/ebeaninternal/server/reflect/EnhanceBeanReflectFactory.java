/*    */ package com.avaje.ebeaninternal.server.reflect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EnhanceBeanReflectFactory
/*    */   implements BeanReflectFactory
/*    */ {
/* 10 */   public BeanReflect create(Class<?> vanillaType, Class<?> entityBeanType) { return new EnhanceBeanReflect(vanillaType, entityBeanType); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\reflect\EnhanceBeanReflectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */