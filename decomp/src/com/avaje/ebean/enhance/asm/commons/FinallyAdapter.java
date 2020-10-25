/*    */ package com.avaje.ebean.enhance.asm.commons;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.Label;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*    */ 
/*    */ public class FinallyAdapter
/*    */   extends AdviceAdapter {
/*    */   private String name;
/*  9 */   private Label startFinally = new Label();
/*    */   
/*    */   public FinallyAdapter(MethodVisitor mv, int acc, String name, String desc) {
/* 12 */     super(mv, acc, name, desc);
/* 13 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void visitCode() {
/* 17 */     super.visitCode();
/* 18 */     this.mv.visitLabel(this.startFinally);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitMaxs(int maxStack, int maxLocals) {
/* 23 */     Label endFinally = new Label();
/* 24 */     this.mv.visitTryCatchBlock(this.startFinally, endFinally, endFinally, null);
/* 25 */     this.mv.visitLabel(endFinally);
/* 26 */     onFinally(191);
/* 27 */     this.mv.visitInsn(191);
/* 28 */     this.mv.visitMaxs(maxStack, maxLocals);
/*    */   }
/*    */   
/*    */   protected void onMethodExit(int opcode) {
/* 32 */     if (opcode != 191) {
/* 33 */       onFinally(opcode);
/*    */     }
/*    */   }
/*    */   
/*    */   private void onFinally(int opcode) {
/* 38 */     this.mv.visitFieldInsn(178, "java/lang/System", "err", "Ljava/io/PrintStream;");
/* 39 */     this.mv.visitLdcInsn("Exiting " + this.name);
/* 40 */     this.mv.visitMethodInsn(182, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\commons\FinallyAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */