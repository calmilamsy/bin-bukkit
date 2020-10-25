/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Label;
/*     */ import com.avaje.ebean.enhance.asm.MethodAdapter;
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
/*     */ public class MethodFieldAdapter
/*     */   extends MethodAdapter
/*     */   implements Opcodes
/*     */ {
/*     */   final ClassMeta meta;
/*     */   final String className;
/*     */   final String methodDescription;
/*     */   boolean transientAnnotation = false;
/*     */   
/*     */   public MethodFieldAdapter(MethodVisitor mv, ClassMeta meta, String methodDescription) {
/*  28 */     super(mv);
/*  29 */     this.meta = meta;
/*  30 */     this.className = meta.getClassName();
/*  31 */     this.methodDescription = methodDescription;
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
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  43 */     if (desc.equals("Ljavax/persistence/Transient;")) {
/*  44 */       this.transientAnnotation = true;
/*     */     }
/*  46 */     return super.visitAnnotation(desc, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) { super.visitLocalVariable(name, desc, signature, start, end, index); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public void visitMethodInsn(int opcode, String owner, String name, String desc) { super.visitMethodInsn(opcode, owner, name, desc); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  63 */     if (this.transientAnnotation) {
/*     */       
/*  65 */       super.visitFieldInsn(opcode, owner, name, desc);
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     if (opcode == 178 || opcode == 179) {
/*  70 */       if (this.meta.isLog(3)) {
/*  71 */         this.meta.log(" ... info: skip static field " + owner + " " + name + " in " + this.methodDescription);
/*     */       }
/*  73 */       super.visitFieldInsn(opcode, owner, name, desc);
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     if (!this.meta.isFieldPersistent(name)) {
/*  78 */       if (this.meta.isLog(2)) {
/*  79 */         this.meta.log(" ... info: non-persistent field " + owner + " " + name + " in " + this.methodDescription);
/*     */       }
/*  81 */       super.visitFieldInsn(opcode, owner, name, desc);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  86 */     if (opcode == 180) {
/*  87 */       String methodName = "_ebean_get_" + name;
/*  88 */       String methodDesc = "()" + desc;
/*  89 */       if (this.meta.isLog(4)) {
/*  90 */         this.meta.log("GETFIELD method:" + this.methodDescription + " field:" + name + " > " + methodName + " " + methodDesc);
/*     */       }
/*     */       
/*  93 */       super.visitMethodInsn(182, this.className, methodName, methodDesc);
/*     */     }
/*  95 */     else if (opcode == 181) {
/*  96 */       String methodName = "_ebean_set_" + name;
/*  97 */       String methodDesc = "(" + desc + ")V";
/*  98 */       if (this.meta.isLog(4)) {
/*  99 */         this.meta.log("PUTFIELD method:" + this.methodDescription + " field:" + name + " > " + methodName + " " + methodDesc);
/*     */       }
/*     */       
/* 102 */       super.visitMethodInsn(182, this.className, methodName, methodDesc);
/*     */     } else {
/*     */       
/* 105 */       this.meta.log("Warning adapting method:" + this.methodDescription + "; unexpected static access to a persistent field?? " + name + " opCode not GETFIELD or PUTFIELD??  opCode:" + opcode + "");
/*     */ 
/*     */ 
/*     */       
/* 109 */       super.visitFieldInsn(opcode, owner, name, desc);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodFieldAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */