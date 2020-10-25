/*    */ package com.avaje.ebeaninternal.server.subclass;
/*    */ 
/*    */ import com.avaje.ebean.enhance.agent.ClassMeta;
/*    */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
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
/*    */ public class MethodWriteReplace
/*    */   implements Opcodes, EnhanceConstants
/*    */ {
/*    */   public static void add(ClassVisitor cv, ClassMeta classMeta) {
/* 45 */     MethodVisitor mv = cv.visitMethod(2, "writeReplace", "()Ljava/lang/Object;", null, new String[] { "java/io/ObjectStreamException" });
/*    */ 
/*    */     
/* 48 */     mv.visitCode();
/* 49 */     Label l0 = new Label();
/* 50 */     mv.visitLabel(l0);
/* 51 */     mv.visitLineNumber(1, l0);
/* 52 */     mv.visitVarInsn(25, 0);
/* 53 */     mv.visitFieldInsn(180, classMeta.getClassName(), "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/* 54 */     mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "writeReplaceIntercept", "()Ljava/lang/Object;");
/*    */     
/* 56 */     mv.visitInsn(176);
/* 57 */     Label l1 = new Label();
/* 58 */     mv.visitLabel(l1);
/* 59 */     mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l1, 0);
/* 60 */     mv.visitMaxs(0, 0);
/* 61 */     mv.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\subclass\MethodWriteReplace.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */