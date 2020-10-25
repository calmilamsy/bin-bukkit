/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Label;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodNewInstance
/*    */ {
/*    */   public static void addMethod(ClassVisitor cv, ClassMeta classMeta) {
/* 39 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_newInstance", "()Ljava/lang/Object;", null, null);
/* 40 */     mv.visitCode();
/* 41 */     Label l0 = new Label();
/* 42 */     mv.visitLabel(l0);
/* 43 */     mv.visitLineNumber(10, l0);
/* 44 */     mv.visitTypeInsn(187, classMeta.getClassName());
/* 45 */     mv.visitInsn(89);
/* 46 */     mv.visitMethodInsn(183, classMeta.getClassName(), "<init>", "()V");
/* 47 */     mv.visitInsn(176);
/*    */     
/* 49 */     Label l1 = new Label();
/* 50 */     mv.visitLabel(l1);
/* 51 */     mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l1, 0);
/* 52 */     mv.visitMaxs(2, 1);
/* 53 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodNewInstance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */