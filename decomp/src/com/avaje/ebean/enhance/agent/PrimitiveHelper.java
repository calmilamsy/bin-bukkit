/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.Type;
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
/*    */ public class PrimitiveHelper
/*    */ {
/* 29 */   private static Type INTEGER_OBJECT = Type.getType(Integer.class);
/* 30 */   private static Type SHORT_OBJECT = Type.getType(Short.class);
/* 31 */   private static Type CHARACTER_OBJECT = Type.getType(Character.class);
/* 32 */   private static Type LONG_OBJECT = Type.getType(Long.class);
/* 33 */   private static Type DOUBLE_OBJECT = Type.getType(Double.class);
/* 34 */   private static Type FLOAT_OBJECT = Type.getType(Float.class);
/* 35 */   private static Type BYTE_OBJECT = Type.getType(Byte.class);
/* 36 */   private static Type BOOLEAN_OBJECT = Type.getType(Boolean.class);
/*    */ 
/*    */   
/*    */   public static Type getObjectWrapper(Type primativeAsmType) {
/* 40 */     int sort = primativeAsmType.getSort();
/* 41 */     switch (sort) { case 5:
/* 42 */         return INTEGER_OBJECT;
/* 43 */       case 4: return SHORT_OBJECT;
/* 44 */       case 2: return CHARACTER_OBJECT;
/* 45 */       case 7: return LONG_OBJECT;
/* 46 */       case 8: return DOUBLE_OBJECT;
/* 47 */       case 6: return FLOAT_OBJECT;
/* 48 */       case 3: return BYTE_OBJECT;
/* 49 */       case 1: return BOOLEAN_OBJECT; }
/*    */ 
/*    */     
/* 52 */     throw new RuntimeException("Expected primative? " + primativeAsmType);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\PrimitiveHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */