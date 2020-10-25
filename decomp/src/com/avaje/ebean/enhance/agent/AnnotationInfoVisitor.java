/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.AnnotationVisitor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotationInfoVisitor
/*    */   implements AnnotationVisitor
/*    */ {
/*    */   final AnnotationVisitor av;
/*    */   final AnnotationInfo info;
/*    */   final String prefix;
/*    */   
/*    */   public AnnotationInfoVisitor(String prefix, AnnotationInfo info, AnnotationVisitor av) {
/* 17 */     this.av = av;
/* 18 */     this.info = info;
/* 19 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */   
/* 23 */   public void visit(String name, Object value) { this.info.add(this.prefix, name, value); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public AnnotationVisitor visitAnnotation(String name, String desc) { return create(name); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public AnnotationVisitor visitArray(String name) { return create(name); }
/*    */ 
/*    */   
/*    */   private AnnotationInfoVisitor create(String name) {
/* 36 */     String newPrefix = (this.prefix == null) ? name : (this.prefix + "." + name);
/* 37 */     return new AnnotationInfoVisitor(newPrefix, this.info, this.av);
/*    */   }
/*    */ 
/*    */   
/* 41 */   public void visitEnd() { this.av.visitEnd(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void visitEnum(String name, String desc, String value) {
/* 46 */     this.info.addEnum(this.prefix, name, desc, value);
/* 47 */     this.av.visitEnum(name, desc, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\AnnotationInfoVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */