/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Opcodes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterceptField
/*     */   implements Opcodes, EnhanceConstants
/*     */ {
/*     */   public static void addField(ClassVisitor cv, boolean transientInternalFields) {
/*  38 */     int access = '\004' + (transientInternalFields ? 128 : 0);
/*  39 */     FieldVisitor f1 = cv.visitField(access, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
/*  40 */     f1.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGetterSetter(ClassVisitor cv, String className) {
/*  54 */     String lClassName = "L" + className + ";";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_getIntercept", "()Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
/*  60 */     mv.visitCode();
/*  61 */     Label l0 = new Label();
/*  62 */     mv.visitLabel(l0);
/*  63 */     mv.visitLineNumber(1, l0);
/*  64 */     mv.visitVarInsn(25, 0);
/*  65 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/*  66 */     mv.visitInsn(176);
/*  67 */     Label l1 = new Label();
/*  68 */     mv.visitLabel(l1);
/*  69 */     mv.visitLocalVariable("this", lClassName, null, l0, l1, 0);
/*  70 */     mv.visitMaxs(0, 0);
/*  71 */     mv.visitEnd();
/*     */     
/*  73 */     addInitInterceptMethod(cv, className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addInitInterceptMethod(ClassVisitor cv, String className) {
/*  96 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_intercept", "()Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
/*  97 */     mv.visitCode();
/*  98 */     Label l0 = new Label();
/*  99 */     mv.visitLabel(l0);
/* 100 */     mv.visitLineNumber(1, l0);
/* 101 */     mv.visitVarInsn(25, 0);
/* 102 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 103 */     Label l1 = new Label();
/* 104 */     mv.visitJumpInsn(199, l1);
/* 105 */     Label l2 = new Label();
/* 106 */     mv.visitLabel(l2);
/* 107 */     mv.visitLineNumber(2, l2);
/* 108 */     mv.visitVarInsn(25, 0);
/* 109 */     mv.visitTypeInsn(187, "com/avaje/ebean/bean/EntityBeanIntercept");
/* 110 */     mv.visitInsn(89);
/* 111 */     mv.visitVarInsn(25, 0);
/* 112 */     mv.visitMethodInsn(183, "com/avaje/ebean/bean/EntityBeanIntercept", "<init>", "(Ljava/lang/Object;)V");
/* 113 */     mv.visitFieldInsn(181, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 114 */     mv.visitLabel(l1);
/* 115 */     mv.visitLineNumber(3, l1);
/* 116 */     mv.visitFrame(3, 0, null, 0, null);
/* 117 */     mv.visitVarInsn(25, 0);
/* 118 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 119 */     mv.visitInsn(176);
/* 120 */     Label l3 = new Label();
/* 121 */     mv.visitLabel(l3);
/* 122 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
/* 123 */     mv.visitMaxs(4, 1);
/* 124 */     mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\InterceptField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */