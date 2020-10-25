/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class ReflectionBasedScalarTypeConverter
/*    */   implements ScalarTypeConverter
/*    */ {
/* 30 */   private static final Object[] NO_ARGS = new Object[0];
/*    */   
/*    */   private final Constructor<?> constructor;
/*    */   
/*    */   private final Method reader;
/*    */   
/*    */   public ReflectionBasedScalarTypeConverter(Constructor<?> constructor, Method reader) {
/* 37 */     this.constructor = constructor;
/* 38 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public Object getNullValue() { return null; }
/*    */ 
/*    */   
/*    */   public Object unwrapValue(Object beanType) {
/* 46 */     if (beanType == null) {
/* 47 */       return null;
/*    */     }
/*    */     try {
/* 50 */       return this.reader.invoke(beanType, NO_ARGS);
/* 51 */     } catch (Exception e) {
/* 52 */       String msg = "Error invoking read method " + this.reader.getName() + " on " + beanType.getClass().getName();
/*    */       
/* 54 */       throw new RuntimeException(msg);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object wrapValue(Object scalarType) {
/*    */     try {
/* 60 */       return this.constructor.newInstance(new Object[] { scalarType });
/* 61 */     } catch (Exception e) {
/* 62 */       String msg = "Error invoking constructor " + this.constructor + " with " + scalarType;
/* 63 */       throw new RuntimeException(msg);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedScalarTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */