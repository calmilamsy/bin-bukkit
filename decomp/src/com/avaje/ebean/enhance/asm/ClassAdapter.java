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
/*     */ public class ClassAdapter
/*     */   implements ClassVisitor
/*     */ {
/*     */   protected ClassVisitor cv;
/*     */   
/*  52 */   public ClassAdapter(ClassVisitor cv) { this.cv = cv; }
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
/*  63 */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) { this.cv.visit(version, access, name, signature, superName, interfaces); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void visitSource(String source, String debug) { this.cv.visitSource(source, debug); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public void visitOuterClass(String owner, String name, String desc) { this.cv.visitOuterClass(owner, name, desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) { return this.cv.visitAnnotation(desc, visible); }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void visitAttribute(Attribute attr) { this.cv.visitAttribute(attr); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public void visitInnerClass(String name, String outerName, String innerName, int access) { this.cv.visitInnerClass(name, outerName, innerName, access); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) { return this.cv.visitField(access, name, desc, signature, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) { return this.cv.visitMethod(access, name, desc, signature, exceptions); }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public void visitEnd() { this.cv.visitEnd(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\ClassAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */