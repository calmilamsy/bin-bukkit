/*     */ package com.avaje.ebean.enhance.asm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmptyVisitor
/*     */   implements ClassVisitor, FieldVisitor, MethodVisitor, AnnotationVisitor
/*     */ {
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {}
/*     */   
/*     */   public void visitSource(String source, String debug) {}
/*     */   
/*     */   public void visitOuterClass(String owner, String name, String desc) {}
/*     */   
/*  69 */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String name, String outerName, String innerName, int access) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) { return this; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */   
/* 107 */   public AnnotationVisitor visitAnnotationDefault() { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInsn(int opcode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLabel(Label label) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object cst) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLineNumber(int line, Label start) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMaxs(int maxStack, int maxLocals) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String name, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String name, String desc, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public AnnotationVisitor visitAnnotation(String name, String desc) { return this; }
/*     */ 
/*     */ 
/*     */   
/* 230 */   public AnnotationVisitor visitArray(String name) { return this; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\EmptyVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */