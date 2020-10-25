/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParser
/*    */   extends DeployParser
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   private final Set<String> includes;
/*    */   
/*    */   public DeployPropertyParser(BeanDescriptor<?> beanDescriptor) {
/* 20 */     this.includes = new HashSet();
/*    */ 
/*    */     
/* 23 */     this.beanDescriptor = beanDescriptor;
/*    */   }
/*    */ 
/*    */   
/* 27 */   public Set<String> getIncludes() { return this.includes; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 32 */     ElPropertyDeploy elProp = this.beanDescriptor.getElPropertyDeploy(expression);
/* 33 */     if (elProp == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     addIncludes(elProp.getElPrefix());
/* 37 */     return elProp.getElPlaceholder(this.encrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String convertWord() {
/* 43 */     String r = getDeployWord(this.word);
/* 44 */     return (r == null) ? this.word : r;
/*    */   }
/*    */   
/*    */   private void addIncludes(String prefix) {
/* 48 */     if (prefix != null)
/* 49 */       this.includes.add(prefix); 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */