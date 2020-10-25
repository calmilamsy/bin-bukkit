/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Label;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Opcodes;
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
/*    */ public class MarkerField
/*    */   implements Opcodes, EnhanceConstants
/*    */ {
/*    */   public static final String _EBEAN_MARKER = "_EBEAN_MARKER";
/*    */   
/*    */   public static String addField(ClassVisitor cv, String className) {
/* 28 */     String cn = className.replace('/', '.');
/*    */     
/* 30 */     FieldVisitor fv = cv.visitField(10, "_EBEAN_MARKER", "Ljava/lang/String;", null, cn);
/* 31 */     fv.visitEnd();
/*    */     
/* 33 */     return cn;
/*    */   }
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
/*    */   public static void addGetMarker(ClassVisitor cv, String className) {
/* 50 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_getMarker", "()Ljava/lang/String;", null, null);
/* 51 */     mv.visitCode();
/* 52 */     Label l0 = new Label();
/* 53 */     mv.visitLabel(l0);
/* 54 */     mv.visitLineNumber(1, l0);
/* 55 */     mv.visitFieldInsn(178, className, "_EBEAN_MARKER", "Ljava/lang/String;");
/* 56 */     mv.visitInsn(176);
/* 57 */     Label l1 = new Label();
/* 58 */     mv.visitLabel(l1);
/* 59 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l1, 0);
/* 60 */     mv.visitMaxs(1, 1);
/* 61 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MarkerField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */