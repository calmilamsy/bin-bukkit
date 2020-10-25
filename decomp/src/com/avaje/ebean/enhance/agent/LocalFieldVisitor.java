/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Attribute;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.EmptyVisitor;
/*    */ import com.avaje.ebean.enhance.asm.FieldVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalFieldVisitor
/*    */   implements FieldVisitor
/*    */ {
/* 14 */   private static final EmptyVisitor emptyVisitor = new EmptyVisitor();
/*    */ 
/*    */   
/*    */   private final FieldVisitor fv;
/*    */ 
/*    */   
/*    */   private final FieldMeta fieldMeta;
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalFieldVisitor(FieldMeta fieldMeta) {
/* 25 */     this.fv = null;
/* 26 */     this.fieldMeta = fieldMeta;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalFieldVisitor(ClassVisitor cv, FieldVisitor fv, FieldMeta fieldMeta) {
/* 36 */     this.fv = fv;
/* 37 */     this.fieldMeta = fieldMeta;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public boolean isPersistentSetter(String methodDesc) { return this.fieldMeta.isPersistentSetter(methodDesc); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public boolean isPersistentGetter(String methodDesc) { return this.fieldMeta.isPersistentGetter(methodDesc); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String getName() { return this.fieldMeta.getFieldName(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public FieldMeta getFieldMeta() { return this.fieldMeta; }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 63 */     this.fieldMeta.addAnnotationDesc(desc);
/* 64 */     if (this.fv != null) {
/* 65 */       return this.fv.visitAnnotation(desc, visible);
/*    */     }
/* 67 */     return emptyVisitor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitAttribute(Attribute attr) {
/* 72 */     if (this.fv != null) {
/* 73 */       this.fv.visitAttribute(attr);
/*    */     }
/*    */   }
/*    */   
/*    */   public void visitEnd() {
/* 78 */     if (this.fv != null)
/* 79 */       this.fv.visitEnd(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\LocalFieldVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */