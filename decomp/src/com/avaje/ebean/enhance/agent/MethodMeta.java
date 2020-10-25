/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ 
/*    */ public class MethodMeta
/*    */ {
/*    */   final int access;
/*    */   final String name;
/*    */   final String desc;
/*    */   final AnnotationInfo annotationInfo;
/*    */   
/*    */   public MethodMeta(AnnotationInfo classAnnotationInfo, int access, String name, String desc) {
/* 12 */     this.annotationInfo = new AnnotationInfo(classAnnotationInfo);
/* 13 */     this.access = access;
/* 14 */     this.name = name;
/* 15 */     this.desc = desc;
/*    */   }
/*    */ 
/*    */   
/* 19 */   public String toString() { return this.name + " " + this.desc; }
/*    */ 
/*    */   
/*    */   public boolean isMatch(String methodName, String methodDesc) {
/* 23 */     if (this.name.equals(methodName) && this.desc.equals(methodDesc)) {
/* 24 */       return true;
/*    */     }
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/* 30 */   public AnnotationInfo getAnnotationInfo() { return this.annotationInfo; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\MethodMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */