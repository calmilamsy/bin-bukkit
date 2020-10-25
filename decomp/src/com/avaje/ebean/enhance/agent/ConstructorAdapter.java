/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.MethodAdapter;
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
/*    */ public class ConstructorAdapter
/*    */   extends MethodAdapter
/*    */   implements EnhanceConstants, Opcodes
/*    */ {
/*    */   private final ClassMeta meta;
/*    */   private final String className;
/*    */   private final String constructorDesc;
/*    */   private boolean constructorInitializationDone;
/*    */   
/*    */   public ConstructorAdapter(MethodVisitor mv, ClassMeta meta, String constructorDesc) {
/* 27 */     super(mv);
/* 28 */     this.meta = meta;
/* 29 */     this.className = meta.getClassName();
/* 30 */     this.constructorDesc = constructorDesc;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 36 */     super.visitMethodInsn(opcode, owner, name, desc);
/* 37 */     addInitialisationIfRequired(opcode, owner, name, desc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInitialisationIfRequired(int opcode, String owner, String name, String desc) {
/* 49 */     if (opcode == 183 && name.equals("<init>") && desc.equals("()V"))
/* 50 */       if (this.meta.isSuperClassEntity()) {
/* 51 */         if (this.meta.isLog(3)) {
/* 52 */           this.meta.log("... skipping intercept <init> ... handled by super class... CONSTRUCTOR:" + this.constructorDesc);
/*    */         }
/*    */       }
/* 55 */       else if (owner.equals(this.meta.getClassName())) {
/* 56 */         if (this.meta.isLog(3)) {
/* 57 */           this.meta.log("... skipping intercept <init> ... handled by other constructor... CONSTRUCTOR:" + this.constructorDesc);
/*    */         }
/*    */       }
/* 60 */       else if (owner.equals(this.meta.getSuperClassName())) {
/* 61 */         if (this.meta.isLog(2)) {
/* 62 */           this.meta.log("... adding intercept <init> in CONSTRUCTOR:" + this.constructorDesc + " OWNER/SUPER:" + owner);
/*    */         }
/*    */         
/* 65 */         if (this.constructorInitializationDone)
/*    */         {
/*    */           
/* 68 */           String msg = "Error in Enhancement. Only expecting to add <init> of intercept object once but it is trying to add it twice for " + this.meta.getClassName() + " CONSTRUCTOR:" + this.constructorDesc + " OWNER:" + owner;
/*    */ 
/*    */           
/* 71 */           System.err.println(msg);
/*    */         }
/*    */         else
/*    */         {
/* 75 */           visitVarInsn(25, 0);
/* 76 */           visitTypeInsn(187, "com/avaje/ebean/bean/EntityBeanIntercept");
/* 77 */           visitInsn(89);
/* 78 */           visitVarInsn(25, 0);
/*    */           
/* 80 */           super.visitMethodInsn(183, "com/avaje/ebean/bean/EntityBeanIntercept", "<init>", "(Ljava/lang/Object;)V");
/* 81 */           visitFieldInsn(181, this.className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
/*    */           
/* 83 */           this.constructorInitializationDone = true;
/*    */         }
/*    */       
/* 86 */       } else if (this.meta.isLog(3)) {
/* 87 */         this.meta.log("... skipping intercept <init> ... incorrect type " + owner);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ConstructorAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */