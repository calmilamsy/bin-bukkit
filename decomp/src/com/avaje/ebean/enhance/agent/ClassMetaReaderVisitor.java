/*     */ package com.avaje.ebean.enhance.agent;
/*     */ 
/*     */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*     */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*     */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*     */ import com.avaje.ebean.enhance.asm.MethodVisitor;
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
/*     */ public class ClassMetaReaderVisitor
/*     */   extends EmptyVisitor
/*     */   implements EnhanceConstants
/*     */ {
/*     */   private final ClassMeta classMeta;
/*     */   private final boolean readMethodMeta;
/*     */   
/*     */   public ClassMetaReaderVisitor(boolean readMethodMeta, EnhanceContext context) {
/*  25 */     this.readMethodMeta = readMethodMeta;
/*  26 */     this.classMeta = context.createClassMeta();
/*     */   }
/*     */ 
/*     */   
/*  30 */   public ClassMeta getClassMeta() { return this.classMeta; }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public boolean isLog(int level) { return this.classMeta.isLog(level); }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public void log(String msg) { this.classMeta.log(msg); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  46 */     this.classMeta.setClassName(name, superName);
/*  47 */     super.visit(version, access, name, signature, superName, interfaces);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  53 */     this.classMeta.addClassAnnotation(desc);
/*     */     
/*  55 */     AnnotationVisitor av = super.visitAnnotation(desc, visible);
/*     */     
/*  57 */     if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;"))
/*     */     {
/*     */       
/*  60 */       return new AnnotationInfoVisitor(null, this.classMeta.getAnnotationInfo(), av);
/*     */     }
/*     */     
/*  63 */     return av;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/*  73 */     if ((access & 0x8) != 0) {
/*     */       
/*  75 */       if (isLog(2)) {
/*  76 */         log("Skip static field " + name);
/*     */       }
/*  78 */       return super.visitField(access, name, desc, signature, value);
/*     */     } 
/*  80 */     if ((access & 0x80) != 0) {
/*  81 */       if (isLog(2)) {
/*  82 */         log("Skip transient field " + name);
/*     */       }
/*     */       
/*  85 */       return super.visitField(access, name, desc, signature, value);
/*     */     } 
/*  87 */     return this.classMeta.createLocalFieldVisitor(name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  95 */     boolean staticAccess = ((access & 0x8) != 0);
/*     */     
/*  97 */     MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
/*  98 */     if (!staticAccess && this.readMethodMeta) {
/*  99 */       return this.classMeta.createMethodVisitor(mv, access, name, desc);
/*     */     }
/*     */ 
/*     */     
/* 103 */     return mv;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\ClassMetaReaderVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */