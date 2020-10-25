/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeployPropertyParserMap
/*    */   extends DeployParser
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   
/* 14 */   public DeployPropertyParserMap(Map<String, String> map) { this.map = map; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public Set<String> getIncludes() { return null; }
/*    */ 
/*    */   
/*    */   public String convertWord() {
/* 25 */     String r = getDeployWord(this.word);
/* 26 */     return (r == null) ? this.word : r;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDeployWord(String expression) {
/* 32 */     String deployExpr = (String)this.map.get(expression);
/* 33 */     if (deployExpr == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     return deployExpr;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployPropertyParserMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */