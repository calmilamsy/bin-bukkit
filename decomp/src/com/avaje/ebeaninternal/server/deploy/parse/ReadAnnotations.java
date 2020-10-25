/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
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
/*    */ public class ReadAnnotations
/*    */ {
/*    */   public void readInitial(DeployBeanInfo<?> info) {
/*    */     try {
/* 40 */       (new AnnotationClass(info)).parse();
/* 41 */       (new AnnotationFields(info)).parse();
/*    */     }
/* 43 */     catch (RuntimeException e) {
/* 44 */       String msg = "Error reading annotations for " + info;
/* 45 */       throw new RuntimeException(msg, e);
/*    */     } 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readAssociations(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
/*    */     try {
/* 64 */       (new AnnotationAssocOnes(info, factory)).parse();
/* 65 */       (new AnnotationAssocManys(info, factory)).parse();
/*    */ 
/*    */ 
/*    */       
/* 69 */       (new AnnotationSql(info)).parse();
/*    */     }
/* 71 */     catch (RuntimeException e) {
/* 72 */       String msg = "Error reading annotations for " + info;
/* 73 */       throw new RuntimeException(msg, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\parse\ReadAnnotations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */