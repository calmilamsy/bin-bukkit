/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Label;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Opcodes;
/*    */ import java.util.List;
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
/*    */ public class MethodSetEmbeddedLoaded
/*    */   implements Opcodes, EnhanceConstants
/*    */ {
/*    */   public static void addMethod(ClassVisitor cv, ClassMeta classMeta) {
/* 27 */     String className = classMeta.getClassName();
/*    */ 
/*    */ 
/*    */     
/* 31 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_setEmbeddedLoaded", "()V", null, null);
/* 32 */     mv.visitCode();
/*    */     
/* 34 */     Label labelBegin = null;
/* 35 */     List<FieldMeta> allFields = classMeta.getAllFields();
/* 36 */     for (i = 0; i < allFields.size(); i++) {
/* 37 */       FieldMeta fieldMeta = (FieldMeta)allFields.get(i);
/* 38 */       if (fieldMeta.isEmbedded()) {
/*    */         
/* 40 */         Label l0 = new Label();
/* 41 */         if (labelBegin == null) {
/* 42 */           labelBegin = l0;
/*    */         }
/*    */         
/* 45 */         mv.visitLabel(l0);
/* 46 */         mv.visitLineNumber(0, l0);
/* 47 */         mv.visitVarInsn(25, 0);
/* 48 */         mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 49 */         mv.visitVarInsn(25, 0);
/* 50 */         fieldMeta.appendSwitchGet(mv, classMeta, false);
/* 51 */         mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "setEmbeddedLoaded", "(Ljava/lang/Object;)V");
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     Label l2 = new Label();
/* 56 */     if (labelBegin == null) {
/* 57 */       labelBegin = l2;
/*    */     }
/* 59 */     mv.visitLabel(l2);
/* 60 */     mv.visitLineNumber(1, l2);
/* 61 */     mv.visitInsn(177);
/* 62 */     Label l3 = new Label();
/* 63 */     mv.visitLabel(l3);
/* 64 */     mv.visitLocalVariable("this", "L" + className + ";", null, labelBegin, l3, 0);
/* 65 */     mv.visitMaxs(2, 1);
/* 66 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodSetEmbeddedLoaded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */