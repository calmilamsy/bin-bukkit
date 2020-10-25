/*     */ package com.avaje.ebean.enhance.asm.commons;
/*     */ 
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
/*     */ public abstract class MethodAdviceAdapter
/*     */   extends GeneratorAdapter
/*     */   implements Opcodes
/*     */ {
/*     */   protected int methodAccess;
/*     */   protected String methodName;
/*     */   protected String methodDesc;
/*     */   
/*     */   protected MethodAdviceAdapter(MethodVisitor mv, int access, String name, String desc) {
/*  69 */     super(mv, access, name, desc);
/*  70 */     this.methodAccess = access;
/*  71 */     this.methodDesc = desc;
/*  72 */     this.methodName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCode() {
/*  77 */     this.mv.visitCode();
/*  78 */     onMethodEnter();
/*     */   }
/*     */ 
/*     */   
/*  82 */   public void visitLabel(Label label) { this.mv.visitLabel(label); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInsn(int opcode) {
/*  87 */     switch (opcode) {
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/*     */       case 177:
/*     */       case 191:
/*  95 */         onMethodExit(opcode);
/*     */         break;
/*     */     } 
/*  98 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */ 
/*     */   
/* 102 */   public void visitVarInsn(int opcode, int var) { super.visitVarInsn(opcode, var); }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void visitFieldInsn(int opcode, String owner, String name, String desc) { this.mv.visitFieldInsn(opcode, owner, name, desc); }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void visitIntInsn(int opcode, int operand) { this.mv.visitIntInsn(opcode, operand); }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public void visitLdcInsn(Object cst) { this.mv.visitLdcInsn(cst); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void visitMultiANewArrayInsn(String desc, int dims) { this.mv.visitMultiANewArrayInsn(desc, dims); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void visitTypeInsn(int opcode, String type) { this.mv.visitTypeInsn(opcode, type); }
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void visitMethodInsn(int opcode, String owner, String name, String desc) { this.mv.visitMethodInsn(opcode, owner, name, desc); }
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void visitJumpInsn(int opcode, Label label) { this.mv.visitJumpInsn(opcode, label); }
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) { this.mv.visitLookupSwitchInsn(dflt, keys, labels); }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) { this.mv.visitTableSwitchInsn(min, max, dflt, labels); }
/*     */   
/*     */   protected void onMethodEnter() {}
/*     */   
/*     */   protected void onMethodExit(int opcode) {}
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\commons\MethodAdviceAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */