/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParamTypeUtil
/*    */ {
/*    */   public static Class<?> findParamType(Class<?> cls, Class<?> matchType) {
/* 25 */     Type paramType = matchByInterfaces(cls, matchType);
/* 26 */     if (paramType == null) {
/*    */       
/* 28 */       Type genericSuperclass = cls.getGenericSuperclass();
/* 29 */       if (genericSuperclass != null) {
/* 30 */         paramType = matchParamType(genericSuperclass, matchType);
/*    */       }
/*    */     } 
/*    */     
/* 34 */     if (paramType instanceof Class)
/*    */     {
/* 36 */       return (Class)paramType;
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Type matchParamType(Type type, Class<?> matchType) {
/* 47 */     if (type instanceof ParameterizedType) {
/* 48 */       ParameterizedType pt = (ParameterizedType)type;
/* 49 */       Type rawType = pt.getRawType();
/* 50 */       boolean isAssignable = matchType.isAssignableFrom((Class)rawType);
/* 51 */       if (isAssignable) {
/*    */         
/* 53 */         Type[] typeArguments = pt.getActualTypeArguments();
/* 54 */         if (typeArguments.length != 1) {
/* 55 */           String m = "Expecting only 1 generic paramater but got " + typeArguments.length + " for " + type;
/* 56 */           throw new RuntimeException(m);
/*    */         } 
/* 58 */         return typeArguments[0];
/*    */       } 
/*    */     } 
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Type matchByInterfaces(Class<?> cls, Class<?> matchType) {
/* 69 */     Type[] gis = cls.getGenericInterfaces();
/* 70 */     for (int i = 0; i < gis.length; i++) {
/* 71 */       Type match = matchParamType(gis[i], matchType);
/* 72 */       if (match != null) {
/* 73 */         return match;
/*    */       }
/*    */     } 
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ParamTypeUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */