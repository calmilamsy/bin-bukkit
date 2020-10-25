/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundType;
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.Arrays;
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
/*    */ public class ReflectionBasedCompoundType
/*    */   implements CompoundType
/*    */ {
/*    */   private final Constructor<?> constructor;
/*    */   private final ReflectionBasedCompoundTypeProperty[] props;
/*    */   
/*    */   public ReflectionBasedCompoundType(Constructor<?> constructor, ReflectionBasedCompoundTypeProperty[] props) {
/* 36 */     this.constructor = constructor;
/* 37 */     this.props = props;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public String toString() { return "ReflectionBasedCompoundType " + this.constructor + " " + Arrays.toString(this.props); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object create(Object[] propertyValues) {
/*    */     try {
/* 47 */       return this.constructor.newInstance(propertyValues);
/* 48 */     } catch (Exception e) {
/* 49 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 54 */   public CompoundTypeProperty[] getProperties() { return this.props; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public Class<?> getPropertyType(int i) { return this.props[i].getPropertyType(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public Class<?> getCompoundType() { return this.constructor.getDeclaringClass(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\reflect\ReflectionBasedCompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */