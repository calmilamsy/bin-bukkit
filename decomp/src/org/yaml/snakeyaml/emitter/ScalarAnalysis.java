/*    */ package org.yaml.snakeyaml.emitter;
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
/*    */ public final class ScalarAnalysis
/*    */ {
/*    */   public String scalar;
/*    */   public boolean empty;
/*    */   public boolean multiline;
/*    */   public boolean allowFlowPlain;
/*    */   public boolean allowBlockPlain;
/*    */   public boolean allowSingleQuoted;
/*    */   public boolean allowDoubleQuoted;
/*    */   public boolean allowBlock;
/*    */   
/*    */   public ScalarAnalysis(String scalar, boolean empty, boolean multiline, boolean allowFlowPlain, boolean allowBlockPlain, boolean allowSingleQuoted, boolean allowDoubleQuoted, boolean allowBlock) {
/* 35 */     this.scalar = scalar;
/* 36 */     this.empty = empty;
/* 37 */     this.multiline = multiline;
/* 38 */     this.allowFlowPlain = allowFlowPlain;
/* 39 */     this.allowBlockPlain = allowBlockPlain;
/* 40 */     this.allowSingleQuoted = allowSingleQuoted;
/* 41 */     this.allowDoubleQuoted = allowDoubleQuoted;
/* 42 */     this.allowBlock = allowBlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\emitter\ScalarAnalysis.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */