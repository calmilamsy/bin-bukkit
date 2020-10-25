/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.MethodVisitor;
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
/*    */ public class VisitMethodParams
/*    */ {
/*    */   private final ClassVisitor cv;
/*    */   private int access;
/*    */   private final String name;
/*    */   private final String desc;
/*    */   private final String signiture;
/*    */   private final String[] exceptions;
/*    */   
/*    */   public VisitMethodParams(ClassVisitor cv, int access, String name, String desc, String signiture, String[] exceptions) {
/* 48 */     this.cv = cv;
/* 49 */     this.access = access;
/* 50 */     this.name = name;
/* 51 */     this.desc = desc;
/* 52 */     this.exceptions = exceptions;
/* 53 */     this.signiture = signiture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean forcePublic() {
/* 60 */     if (this.access != 1) {
/* 61 */       this.access = 1;
/* 62 */       return true;
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public MethodVisitor visitMethod() { return this.cv.visitMethod(this.access, this.name, this.desc, this.signiture, this.exceptions); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 86 */   public String getDesc() { return this.desc; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\VisitMethodParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */