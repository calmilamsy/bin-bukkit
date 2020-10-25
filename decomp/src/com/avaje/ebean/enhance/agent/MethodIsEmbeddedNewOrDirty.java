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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodIsEmbeddedNewOrDirty
/*    */   implements Opcodes, EnhanceConstants
/*    */ {
/*    */   public static void addMethod(ClassVisitor cv, ClassMeta classMeta) {
/* 33 */     String className = classMeta.getClassName();
/*    */ 
/*    */ 
/*    */     
/* 37 */     MethodVisitor mv = cv.visitMethod(1, "_ebean_isEmbeddedNewOrDirty", "()Z", null, null);
/* 38 */     mv.visitCode();
/*    */     
/* 40 */     Label labelBegin = null;
/*    */     
/* 42 */     Label labelNext = null;
/*    */     
/* 44 */     List<FieldMeta> allFields = classMeta.getAllFields();
/* 45 */     for (int i = 0; i < allFields.size(); i++) {
/* 46 */       FieldMeta fieldMeta = (FieldMeta)allFields.get(i);
/* 47 */       if (fieldMeta.isEmbedded()) {
/*    */         
/* 49 */         Label l0 = labelNext;
/* 50 */         if (l0 == null) {
/* 51 */           l0 = new Label();
/*    */         }
/* 53 */         if (labelBegin == null) {
/* 54 */           labelBegin = l0;
/*    */         }
/*    */         
/* 57 */         mv.visitLabel(l0);
/* 58 */         mv.visitLineNumber(0, l0);
/* 59 */         mv.visitVarInsn(25, 0);
/* 60 */         mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 61 */         mv.visitVarInsn(25, 0);
/* 62 */         fieldMeta.appendSwitchGet(mv, classMeta, false);
/* 63 */         mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "isEmbeddedNewOrDirty", "(Ljava/lang/Object;)Z");
/*    */         
/* 65 */         labelNext = new Label();
/* 66 */         mv.visitJumpInsn(153, labelNext);
/* 67 */         mv.visitInsn(4);
/* 68 */         mv.visitInsn(172);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 73 */     if (labelNext == null) {
/* 74 */       labelNext = new Label();
/*    */     }
/* 76 */     if (labelBegin == null) {
/* 77 */       labelBegin = labelNext;
/*    */     }
/* 79 */     mv.visitLabel(labelNext);
/* 80 */     mv.visitLineNumber(1, labelNext);
/* 81 */     mv.visitInsn(3);
/* 82 */     mv.visitInsn(172);
/*    */     
/* 84 */     Label l3 = new Label();
/* 85 */     mv.visitLabel(l3);
/* 86 */     mv.visitLocalVariable("this", "L" + className + ";", null, labelBegin, l3, 0);
/* 87 */     mv.visitMaxs(2, 1);
/* 88 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodIsEmbeddedNewOrDirty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */