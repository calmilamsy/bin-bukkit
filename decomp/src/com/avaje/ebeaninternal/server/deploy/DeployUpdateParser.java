/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployUpdateParser
/*    */   extends DeployParser
/*    */ {
/*    */   private final BeanDescriptor<?> beanDescriptor;
/*    */   
/* 15 */   public DeployUpdateParser(BeanDescriptor<?> beanDescriptor) { this.beanDescriptor = beanDescriptor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Set<String> getIncludes() { return null; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String convertWord() {
/* 28 */     String dbWord = getDeployWord(this.word);
/*    */     
/* 30 */     if (dbWord != null) {
/* 31 */       return dbWord;
/*    */     }
/*    */     
/* 34 */     return convertSubword(0, this.word, null);
/*    */   }
/*    */ 
/*    */   
/*    */   private String convertSubword(int start, String currentWord, StringBuilder localBuffer) {
/* 39 */     int dotPos = currentWord.indexOf('.', start);
/* 40 */     if (start == 0 && dotPos == -1) {
/* 41 */       return currentWord;
/*    */     }
/* 43 */     if (start == 0) {
/* 44 */       localBuffer = new StringBuilder();
/*    */     }
/* 46 */     if (dotPos == -1) {
/*    */       
/* 48 */       localBuffer.append(currentWord.substring(start));
/* 49 */       return localBuffer.toString();
/*    */     } 
/*    */ 
/*    */     
/* 53 */     localBuffer.append(currentWord.substring(start, dotPos + 1));
/*    */     
/* 55 */     if (dotPos == currentWord.length() - 1)
/*    */     {
/* 57 */       return localBuffer.toString();
/*    */     }
/*    */ 
/*    */     
/* 61 */     start = dotPos + 1;
/* 62 */     String remainder = currentWord.substring(start, currentWord.length());
/*    */ 
/*    */     
/* 65 */     String dbWord = getDeployWord(remainder);
/* 66 */     if (dbWord != null) {
/*    */       
/* 68 */       localBuffer.append(dbWord);
/* 69 */       return localBuffer.toString();
/*    */     } 
/*    */     
/* 72 */     return convertSubword(start, currentWord, localBuffer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 78 */     if (expression.equalsIgnoreCase(this.beanDescriptor.getName())) {
/* 79 */       return this.beanDescriptor.getBaseTable();
/*    */     }
/*    */     
/* 82 */     ElPropertyDeploy elProp = this.beanDescriptor.getElPropertyDeploy(expression);
/* 83 */     if (elProp != null) {
/* 84 */       return elProp.getDbColumn();
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployUpdateParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */