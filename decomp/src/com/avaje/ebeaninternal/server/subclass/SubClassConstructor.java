/*    */ package com.avaje.ebeaninternal.server.subclass;
/*    */ 
/*    */ import com.avaje.ebean.enhance.agent.ClassMeta;
/*    */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*    */ import com.avaje.ebean.enhance.agent.VisitMethodParams;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Label;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Opcodes;
/*    */ 
/*    */ public class SubClassConstructor
/*    */   implements Opcodes, EnhanceConstants {
/*    */   public static void addDefault(ClassVisitor cv, ClassMeta meta) {
/* 14 */     VisitMethodParams params = new VisitMethodParams(cv, true, "<init>", "()V", null, null);
/* 15 */     add(params, meta);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void add(VisitMethodParams params, ClassMeta meta) {
/* 20 */     String className = meta.getClassName();
/* 21 */     String superClassName = meta.getSuperClassName();
/*    */     
/* 23 */     if (params.forcePublic() && 
/* 24 */       meta.isLog(0)) {
/* 25 */       meta.log(" forcing ACC_PUBLIC ");
/*    */     }
/*    */ 
/*    */     
/* 29 */     MethodVisitor mv = params.visitMethod();
/*    */     
/* 31 */     mv.visitCode();
/* 32 */     Label l0 = new Label();
/* 33 */     mv.visitLabel(l0);
/* 34 */     mv.visitLineNumber(17, l0);
/* 35 */     mv.visitVarInsn(25, 0);
/* 36 */     mv.visitMethodInsn(183, superClassName, "<init>", "()V");
/* 37 */     Label l1 = new Label();
/* 38 */     mv.visitLabel(l1);
/* 39 */     mv.visitLineNumber(18, l1);
/* 40 */     mv.visitVarInsn(25, 0);
/* 41 */     mv.visitTypeInsn(187, "com/avaje/ebean/bean/EntityBeanIntercept");
/* 42 */     mv.visitInsn(89);
/* 43 */     mv.visitVarInsn(25, 0);
/* 44 */     mv.visitMethodInsn(183, "com/avaje/ebean/bean/EntityBeanIntercept", "<init>", "(Ljava/lang/Object;)V");
/* 45 */     mv.visitFieldInsn(181, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 46 */     Label l2 = new Label();
/* 47 */     mv.visitLabel(l2);
/* 48 */     mv.visitLineNumber(19, l2);
/* 49 */     mv.visitInsn(177);
/* 50 */     Label l3 = new Label();
/* 51 */     mv.visitLabel(l3);
/* 52 */     mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
/* 53 */     mv.visitMaxs(4, 1);
/* 54 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\SubClassConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */