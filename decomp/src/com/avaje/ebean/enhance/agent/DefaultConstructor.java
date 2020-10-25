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
/*    */ 
/*    */ public class DefaultConstructor
/*    */ {
/*    */   public static void add(ClassVisitor cw, ClassMeta classMeta) {
/* 40 */     if (classMeta.isLog(3)) {
/* 41 */       classMeta.log("... adding default constructor, super class: " + classMeta.getSuperClassName());
/*    */     }
/*    */     
/* 44 */     MethodVisitor underlyingMV = cw.visitMethod(1, "<init>", "()V", null, null);
/*    */     
/* 46 */     ConstructorAdapter mv = new ConstructorAdapter(underlyingMV, classMeta, "()V");
/*    */     
/* 48 */     mv.visitCode();
/* 49 */     Label l0 = new Label();
/* 50 */     mv.visitLabel(l0);
/* 51 */     mv.visitLineNumber(1, l0);
/* 52 */     mv.visitVarInsn(25, 0);
/* 53 */     mv.visitMethodInsn(183, classMeta.getSuperClassName(), "<init>", "()V");
/* 54 */     Label l1 = new Label();
/* 55 */     mv.visitLabel(l1);
/* 56 */     mv.visitLineNumber(2, l1);
/* 57 */     mv.visitInsn(177);
/*    */     
/* 59 */     Label l2 = new Label();
/* 60 */     mv.visitLabel(l2);
/* 61 */     mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l2, 0);
/* 62 */     mv.visitMaxs(1, 1);
/* 63 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\DefaultConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */