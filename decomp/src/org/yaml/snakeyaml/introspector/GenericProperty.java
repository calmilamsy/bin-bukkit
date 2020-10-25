/*    */ package org.yaml.snakeyaml.introspector;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
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
/*    */ public abstract class GenericProperty
/*    */   extends Property
/*    */ {
/*    */   private Type genType;
/*    */   private boolean actualClassesChecked;
/*    */   private Class<?>[] actualClasses;
/*    */   
/*    */   public GenericProperty(String name, Class<?> aClass, Type aType) {
/* 28 */     super(name, aClass);
/* 29 */     this.genType = aType;
/* 30 */     this.actualClassesChecked = (aType == null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?>[] getActualTypeArguments() {
/* 37 */     if (!this.actualClassesChecked) {
/* 38 */       if (this.genType instanceof ParameterizedType) {
/* 39 */         ParameterizedType parameterizedType = (ParameterizedType)this.genType;
/* 40 */         Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
/* 41 */         if (actualTypeArguments.length > 0) {
/* 42 */           this.actualClasses = new Class[actualTypeArguments.length];
/* 43 */           for (int i = 0; i < actualTypeArguments.length; i++) {
/* 44 */             if (actualTypeArguments[i] instanceof Class) {
/* 45 */               this.actualClasses[i] = (Class)actualTypeArguments[i];
/*    */             } else {
/* 47 */               this.actualClasses = null;
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         } 
/* 52 */       } else if (this.genType instanceof GenericArrayType) {
/* 53 */         Type componentType = ((GenericArrayType)this.genType).getGenericComponentType();
/* 54 */         if (componentType instanceof Class) {
/* 55 */           this.actualClasses = new Class[] { (Class)componentType };
/*    */         }
/* 57 */       } else if (this.genType instanceof Class) {
/* 58 */         Class<?> classType = (Class)this.genType;
/* 59 */         if (classType.isArray()) {
/* 60 */           this.actualClasses = new Class[1];
/* 61 */           this.actualClasses[0] = getType().getComponentType();
/*    */         } 
/*    */       } 
/* 64 */       this.actualClassesChecked = true;
/*    */     } 
/* 66 */     return this.actualClasses;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\introspector\GenericProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */