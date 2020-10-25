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
/*     */ public class MethodAdapter
/*     */   implements MethodVisitor
/*     */ {
/*     */   protected MethodVisitor mv;
/*     */   
/*  53 */   public MethodAdapter(MethodVisitor mv) { this.mv = mv; }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public AnnotationVisitor visitAnnotationDefault() { return this.mv.visitAnnotationDefault(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) { return this.mv.visitAnnotation(desc, visible); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) { return this.mv.visitParameterAnnotation(parameter, desc, visible); }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void visitAttribute(Attribute attr) { this.mv.visitAttribute(attr); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void visitCode() { this.mv.visitCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) { this.mv.visitFrame(type, nLocal, local, nStack, stack); }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public void visitInsn(int opcode) { this.mv.visitInsn(opcode); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void visitIntInsn(int opcode, int operand) { this.mv.visitIntInsn(opcode, operand); }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void visitVarInsn(int opcode, int var) { this.mv.visitVarInsn(opcode, var); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void visitTypeInsn(int opcode, String type) { this.mv.visitTypeInsn(opcode, type); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void visitFieldInsn(int opcode, String owner, String name, String desc) { this.mv.visitFieldInsn(opcode, owner, name, desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public void visitMethodInsn(int opcode, String owner, String name, String desc) { this.mv.visitMethodInsn(opcode, owner, name, desc); }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public void visitJumpInsn(int opcode, Label label) { this.mv.visitJumpInsn(opcode, label); }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void visitLabel(Label label) { this.mv.visitLabel(label); }
/*     */ 
/*     */ 
/*     */   
/* 136 */   public void visitLdcInsn(Object cst) { this.mv.visitLdcInsn(cst); }
/*     */ 
/*     */ 
/*     */   
/* 140 */   public void visitIincInsn(int var, int increment) { this.mv.visitIincInsn(var, increment); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) { this.mv.visitTableSwitchInsn(min, max, dflt, labels); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) { this.mv.visitLookupSwitchInsn(dflt, keys, labels); }
/*     */ 
/*     */ 
/*     */   
/* 161 */   public void visitMultiANewArrayInsn(String desc, int dims) { this.mv.visitMultiANewArrayInsn(desc, dims); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) { this.mv.visitTryCatchBlock(start, end, handler, type); }
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
/* 181 */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) { this.mv.visitLocalVariable(name, desc, signature, start, end, index); }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void visitLineNumber(int line, Label start) { this.mv.visitLineNumber(line, start); }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public void visitMaxs(int maxStack, int maxLocals) { this.mv.visitMaxs(maxStack, maxLocals); }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void visitEnd() { this.mv.visitEnd(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\MethodAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */