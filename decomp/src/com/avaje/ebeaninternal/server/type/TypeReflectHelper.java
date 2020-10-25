/*    */ package com.avaje.ebeaninternal.server.type;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeReflectHelper
/*    */ {
/*    */   public static Class<?>[] getParams(Class<?> cls, Class<?> matchRawType) {
/* 29 */     Type[] types = getParamType(cls, matchRawType);
/* 30 */     Class[] result = new Class[types.length];
/* 31 */     for (int i = 0; i < result.length; i++) {
/* 32 */       result[i] = getClass(types[i]);
/*    */     }
/* 34 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class<?> getClass(Type type) {
/* 39 */     if (type instanceof ParameterizedType) {
/* 40 */       return getClass(((ParameterizedType)type).getRawType());
/*    */     }
/*    */     
/* 43 */     return (Class)type;
/*    */   }
/*    */ 
/*    */   
/*    */   private static Type[] getParamType(Class<?> cls, Class<?> matchRawType) {
/* 48 */     Type[] gis = cls.getGenericInterfaces();
/* 49 */     for (int i = 0; i < gis.length; i++) {
/* 50 */       Type type = gis[i];
/* 51 */       if (type instanceof ParameterizedType) {
/* 52 */         ParameterizedType paramType = (ParameterizedType)type;
/* 53 */         Type rawType = paramType.getRawType();
/* 54 */         if (rawType.equals(matchRawType))
/*    */         {
/* 56 */           return paramType.getActualTypeArguments();
/*    */         }
/*    */       } 
/*    */     } 
/* 60 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\TypeReflectHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */