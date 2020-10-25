/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.ClassVisitor;
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
/*     */ public class MethodPropertyChangeListener
/*     */   implements Opcodes, EnhanceConstants
/*     */ {
/*     */   public static void addMethod(ClassVisitor cv, ClassMeta classMeta) {
/*  21 */     addAddListenerMethod(cv, classMeta);
/*  22 */     addAddPropertyListenerMethod(cv, classMeta);
/*  23 */     addRemoveListenerMethod(cv, classMeta);
/*  24 */     addRemovePropertyListenerMethod(cv, classMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean alreadyExisting(ClassMeta classMeta, String method, String desc) {
/*  29 */     if (classMeta.isExistingMethod(method, desc)) {
/*  30 */       if (classMeta.isLog(1)) {
/*  31 */         classMeta.log("Existing method... " + method + desc + "  - not adding Ebean's implementation");
/*     */       }
/*  33 */       return true;
/*     */     } 
/*  35 */     return false;
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
/*     */   private static void addAddListenerMethod(ClassVisitor cv, ClassMeta classMeta) {
/*  49 */     String desc = "(Ljava/beans/PropertyChangeListener;)V";
/*     */     
/*  51 */     if (alreadyExisting(classMeta, "addPropertyChangeListener", desc)) {
/*     */       return;
/*     */     }
/*     */     
/*  55 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/*  59 */     MethodVisitor mv = cv.visitMethod(1, "addPropertyChangeListener", desc, null, null);
/*  60 */     mv.visitCode();
/*  61 */     Label l0 = new Label();
/*  62 */     mv.visitLabel(l0);
/*  63 */     mv.visitLineNumber(1, l0);
/*  64 */     mv.visitVarInsn(25, 0);
/*  65 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/*  66 */     mv.visitVarInsn(25, 1);
/*  67 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "addPropertyChangeListener", "(Ljava/beans/PropertyChangeListener;)V");
/*  68 */     Label l1 = new Label();
/*  69 */     mv.visitLabel(l1);
/*  70 */     mv.visitLineNumber(2, l1);
/*  71 */     mv.visitInsn(177);
/*  72 */     Label l2 = new Label();
/*  73 */     mv.visitLabel(l2);
/*  74 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
/*  75 */     mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l2, 1);
/*  76 */     mv.visitMaxs(2, 2);
/*  77 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addAddPropertyListenerMethod(ClassVisitor cv, ClassMeta classMeta) {
/*  82 */     String desc = "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V";
/*     */     
/*  84 */     if (alreadyExisting(classMeta, "addPropertyChangeListener", desc)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/*  93 */     MethodVisitor mv = cv.visitMethod(1, "addPropertyChangeListener", desc, null, null);
/*  94 */     mv.visitCode();
/*  95 */     Label l0 = new Label();
/*  96 */     mv.visitLabel(l0);
/*  97 */     mv.visitLineNumber(1, l0);
/*  98 */     mv.visitVarInsn(25, 0);
/*  99 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 100 */     mv.visitVarInsn(25, 1);
/* 101 */     mv.visitVarInsn(25, 2);
/* 102 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "addPropertyChangeListener", "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V");
/* 103 */     Label l1 = new Label();
/* 104 */     mv.visitLabel(l1);
/* 105 */     mv.visitLineNumber(2, l1);
/* 106 */     mv.visitInsn(177);
/* 107 */     Label l2 = new Label();
/* 108 */     mv.visitLabel(l2);
/* 109 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
/* 110 */     mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l2, 1);
/* 111 */     mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l2, 2);
/* 112 */     mv.visitMaxs(3, 3);
/* 113 */     mv.visitEnd();
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
/*     */   private static void addRemoveListenerMethod(ClassVisitor cv, ClassMeta classMeta) {
/* 127 */     String desc = "(Ljava/beans/PropertyChangeListener;)V";
/*     */     
/* 129 */     if (alreadyExisting(classMeta, "removePropertyChangeListener", desc)) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/* 137 */     MethodVisitor mv = cv.visitMethod(1, "removePropertyChangeListener", desc, null, null);
/* 138 */     mv.visitCode();
/* 139 */     Label l0 = new Label();
/* 140 */     mv.visitLabel(l0);
/* 141 */     mv.visitLineNumber(1, l0);
/* 142 */     mv.visitVarInsn(25, 0);
/* 143 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 144 */     mv.visitVarInsn(25, 1);
/* 145 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "removePropertyChangeListener", "(Ljava/beans/PropertyChangeListener;)V");
/* 146 */     Label l1 = new Label();
/* 147 */     mv.visitLabel(l1);
/* 148 */     mv.visitLineNumber(2, l1);
/* 149 */     mv.visitInsn(177);
/* 150 */     Label l2 = new Label();
/* 151 */     mv.visitLabel(l2);
/* 152 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
/* 153 */     mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l2, 1);
/* 154 */     mv.visitMaxs(2, 2);
/* 155 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addRemovePropertyListenerMethod(ClassVisitor cv, ClassMeta classMeta) {
/* 160 */     String desc = "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V";
/*     */     
/* 162 */     if (alreadyExisting(classMeta, "removePropertyChangeListener", desc)) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     String className = classMeta.getClassName();
/*     */ 
/*     */ 
/*     */     
/* 170 */     MethodVisitor mv = cv.visitMethod(1, "removePropertyChangeListener", desc, null, null);
/* 171 */     mv.visitCode();
/* 172 */     Label l0 = new Label();
/* 173 */     mv.visitLabel(l0);
/* 174 */     mv.visitLineNumber(1, l0);
/* 175 */     mv.visitVarInsn(25, 0);
/* 176 */     mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 177 */     mv.visitVarInsn(25, 1);
/* 178 */     mv.visitVarInsn(25, 2);
/* 179 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "removePropertyChangeListener", "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V");
/* 180 */     Label l1 = new Label();
/* 181 */     mv.visitLabel(l1);
/* 182 */     mv.visitLineNumber(2, l1);
/* 183 */     mv.visitInsn(177);
/* 184 */     Label l2 = new Label();
/* 185 */     mv.visitLabel(l2);
/* 186 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
/* 187 */     mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l2, 1);
/* 188 */     mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l2, 2);
/* 189 */     mv.visitMaxs(3, 3);
/* 190 */     mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodPropertyChangeListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */