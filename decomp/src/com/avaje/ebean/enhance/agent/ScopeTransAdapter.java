/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
/*     */ import com.avaje.ebean.enhance.asm.Type;
/*     */ import com.avaje.ebean.enhance.asm.commons.MethodAdviceAdapter;
/*     */ import java.util.ArrayList;
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
/*     */ public class ScopeTransAdapter
/*     */   extends MethodAdviceAdapter
/*     */   implements EnhanceConstants
/*     */ {
/*  22 */   private static final Type txScopeType = Type.getType("Lcom/avaje/ebean/TxScope;");
/*  23 */   private static final Type scopeTransType = Type.getType("Lcom/avaje/ebeaninternal/api/ScopeTrans;");
/*  24 */   private static final Type helpScopeTrans = Type.getType("Lcom/avaje/ebeaninternal/api/HelpScopeTrans;");
/*     */   
/*     */   private final AnnotationInfo annotationInfo;
/*     */   
/*     */   private final ClassAdapterTransactional owner;
/*     */   
/*     */   private boolean transactional;
/*     */   
/*     */   private int posTxScope;
/*     */   private int posScopeTrans;
/*     */   
/*     */   public ScopeTransAdapter(ClassAdapterTransactional owner, MethodVisitor mv, int access, String name, String desc) {
/*  36 */     super(mv, access, name, desc);
/*  37 */     this.owner = owner;
/*     */ 
/*     */     
/*  40 */     AnnotationInfo parentInfo = owner.classAnnotationInfo;
/*     */ 
/*     */     
/*  43 */     AnnotationInfo interfaceInfo = owner.getInterfaceTransactionalInfo(name, desc);
/*  44 */     if (parentInfo == null) {
/*  45 */       parentInfo = interfaceInfo;
/*     */     } else {
/*  47 */       parentInfo.setParent(interfaceInfo);
/*     */     } 
/*     */ 
/*     */     
/*  51 */     this.annotationInfo = new AnnotationInfo(parentInfo);
/*     */ 
/*     */ 
/*     */     
/*  55 */     this.transactional = (parentInfo != null);
/*     */   }
/*     */ 
/*     */   
/*  59 */   public boolean isTransactional() { return this.transactional; }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  64 */     if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
/*  65 */       this.transactional = true;
/*     */     }
/*  67 */     AnnotationVisitor av = super.visitAnnotation(desc, visible);
/*  68 */     return new AnnotationInfoVisitor(null, this.annotationInfo, av);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTxType(Object txType) {
/*  73 */     this.mv.visitVarInsn(25, this.posTxScope);
/*  74 */     this.mv.visitLdcInsn(txType.toString());
/*  75 */     this.mv.visitMethodInsn(184, "com/avaje/ebean/TxType", "valueOf", "(Ljava/lang/String;)Lcom/avaje/ebean/TxType;");
/*  76 */     this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setType", "(Lcom/avaje/ebean/TxType;)Lcom/avaje/ebean/TxScope;");
/*  77 */     this.mv.visitInsn(87);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTxIsolation(Object txIsolation) {
/*  82 */     this.mv.visitVarInsn(25, this.posTxScope);
/*  83 */     this.mv.visitLdcInsn(txIsolation.toString());
/*  84 */     this.mv.visitMethodInsn(184, "com/avaje/ebean/TxIsolation", "valueOf", "(Ljava/lang/String;)Lcom/avaje/ebean/TxIsolation;");
/*  85 */     this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setIsolation", "(Lcom/avaje/ebean/TxIsolation;)Lcom/avaje/ebean/TxScope;");
/*  86 */     this.mv.visitInsn(87);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setServerName(Object serverName) {
/*  91 */     this.mv.visitVarInsn(25, this.posTxScope);
/*  92 */     this.mv.visitLdcInsn(serverName.toString());
/*  93 */     this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setServerName", "(Ljava/lang/String;)Lcom/avaje/ebean/TxScope;");
/*  94 */     this.mv.visitInsn(87);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setReadOnly(Object readOnlyObj) {
/*  99 */     boolean readOnly = ((Boolean)readOnlyObj).booleanValue();
/* 100 */     this.mv.visitVarInsn(25, this.posTxScope);
/* 101 */     if (readOnly) {
/* 102 */       this.mv.visitInsn(4);
/*     */     } else {
/* 104 */       this.mv.visitInsn(3);
/*     */     } 
/* 106 */     this.mv.visitMethodInsn(182, "com/avaje/ebean/TxScope", "setReadOnly", "(Z)Lcom/avaje/ebean/TxScope;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNoRollbackFor(Object noRollbackFor) {
/* 114 */     ArrayList<?> list = (ArrayList)noRollbackFor;
/*     */     
/* 116 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 118 */       Type throwType = (Type)list.get(i);
/*     */       
/* 120 */       this.mv.visitVarInsn(25, this.posTxScope);
/* 121 */       this.mv.visitLdcInsn(throwType);
/* 122 */       this.mv.visitMethodInsn(182, txScopeType.getInternalName(), "setNoRollbackFor", "(Ljava/lang/Class;)Lcom/avaje/ebean/TxScope;");
/* 123 */       this.mv.visitInsn(87);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setRollbackFor(Object rollbackFor) {
/* 132 */     ArrayList<?> list = (ArrayList)rollbackFor;
/*     */     
/* 134 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 136 */       Type throwType = (Type)list.get(i);
/*     */       
/* 138 */       this.mv.visitVarInsn(25, this.posTxScope);
/* 139 */       this.mv.visitLdcInsn(throwType);
/* 140 */       this.mv.visitMethodInsn(182, txScopeType.getInternalName(), "setRollbackFor", "(Ljava/lang/Class;)Lcom/avaje/ebean/TxScope;");
/* 141 */       this.mv.visitInsn(87);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMethodEnter() {
/* 148 */     if (!this.transactional) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 153 */     this.owner.transactionalMethod(this.methodName, this.methodDesc, this.annotationInfo);
/*     */     
/* 155 */     this.posTxScope = newLocal(txScopeType);
/* 156 */     this.posScopeTrans = newLocal(scopeTransType);
/*     */     
/* 158 */     this.mv.visitTypeInsn(187, txScopeType.getInternalName());
/* 159 */     this.mv.visitInsn(89);
/* 160 */     this.mv.visitMethodInsn(183, txScopeType.getInternalName(), "<init>", "()V");
/* 161 */     this.mv.visitVarInsn(58, this.posTxScope);
/*     */     
/* 163 */     Object txType = this.annotationInfo.getValue("type");
/* 164 */     if (txType != null) {
/* 165 */       setTxType(txType);
/*     */     }
/*     */     
/* 168 */     Object txIsolation = this.annotationInfo.getValue("isolation");
/* 169 */     if (txIsolation != null) {
/* 170 */       setTxIsolation(txIsolation);
/*     */     }
/*     */     
/* 173 */     Object readOnly = this.annotationInfo.getValue("readOnly");
/* 174 */     if (readOnly != null) {
/* 175 */       setReadOnly(readOnly);
/*     */     }
/*     */     
/* 178 */     Object noRollbackFor = this.annotationInfo.getValue("noRollbackFor");
/* 179 */     if (noRollbackFor != null) {
/* 180 */       setNoRollbackFor(noRollbackFor);
/*     */     }
/*     */     
/* 183 */     Object rollbackFor = this.annotationInfo.getValue("rollbackFor");
/* 184 */     if (rollbackFor != null) {
/* 185 */       setRollbackFor(rollbackFor);
/*     */     }
/*     */     
/* 188 */     Object serverName = this.annotationInfo.getValue("serverName");
/* 189 */     if (serverName != null && !serverName.equals("")) {
/* 190 */       setServerName(serverName);
/*     */     }
/*     */     
/* 193 */     this.mv.visitVarInsn(25, this.posTxScope);
/* 194 */     this.mv.visitMethodInsn(184, helpScopeTrans.getInternalName(), "createScopeTrans", "(" + txScopeType.getDescriptor() + ")" + scopeTransType.getDescriptor());
/*     */     
/* 196 */     this.mv.visitVarInsn(58, this.posScopeTrans);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMethodExit(int opcode) {
/* 202 */     if (!this.transactional) {
/*     */       return;
/*     */     }
/*     */     
/* 206 */     if (opcode == 177) {
/* 207 */       visitInsn(1);
/*     */     }
/* 209 */     else if (opcode == 176 || opcode == 191) {
/* 210 */       dup();
/*     */     } else {
/*     */       
/* 213 */       if (opcode == 173 || opcode == 175) {
/* 214 */         dup2();
/*     */       } else {
/* 216 */         dup();
/*     */       } 
/* 218 */       box(Type.getReturnType(this.methodDesc));
/*     */     } 
/* 220 */     visitIntInsn(17, opcode);
/* 221 */     loadLocal(this.posScopeTrans);
/*     */     
/* 223 */     visitMethodInsn(184, helpScopeTrans.getInternalName(), "onExitScopeTrans", "(Ljava/lang/Object;I" + scopeTransType.getDescriptor() + ")V");
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ScopeTransAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */