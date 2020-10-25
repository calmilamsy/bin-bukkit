/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
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
/*    */ public class ReflectionBasedCompoundTypeProperty
/*    */   implements CompoundTypeProperty
/*    */ {
/* 29 */   private static final Object[] NO_ARGS = new Object[0];
/*    */   
/*    */   private final Method reader;
/*    */   
/*    */   private final String name;
/*    */   
/*    */   private final Class<?> propertyType;
/*    */   
/*    */   public ReflectionBasedCompoundTypeProperty(String name, Method reader, Class<?> propertyType) {
/* 38 */     this.name = name;
/* 39 */     this.reader = reader;
/* 40 */     this.propertyType = propertyType;
/*    */   }
/*    */ 
/*    */   
/* 44 */   public String toString() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public int getDbType() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue(Object valueObject) {
/*    */     try {
/* 58 */       return this.reader.invoke(valueObject, NO_ARGS);
/* 59 */     } catch (Exception e) {
/* 60 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 65 */   public Class<?> getPropertyType() { return this.propertyType; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedCompoundTypeProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */