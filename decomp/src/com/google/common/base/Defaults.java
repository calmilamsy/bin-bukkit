/*    */ package com.google.common.base;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class Defaults
/*    */ {
/* 30 */   private static final Map<Class<?>, Object> DEFAULTS = new HashMap(16);
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static <T> void put(Class<T> type, T value) { DEFAULTS.put(type, value); }
/*    */ 
/*    */   
/*    */   static  {
/* 38 */     put(boolean.class, Boolean.valueOf(false));
/* 39 */     put(char.class, Character.valueOf(false));
/* 40 */     put(byte.class, Byte.valueOf((byte)0));
/* 41 */     put(short.class, Short.valueOf((short)0));
/* 42 */     put(int.class, Integer.valueOf(0));
/* 43 */     put(long.class, Long.valueOf(0L));
/* 44 */     put(float.class, Float.valueOf(0.0F));
/* 45 */     put(double.class, Double.valueOf(0.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public static <T> T defaultValue(Class<T> type) { return (T)DEFAULTS.get(type); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Defaults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */