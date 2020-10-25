/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ public abstract class Token
/*    */ {
/*    */   private final Mark startMark;
/*    */   private final Mark endMark;
/*    */   
/*    */   public enum ID
/*    */   {
/* 27 */     Alias, Anchor, BlockEnd, BlockEntry, BlockMappingStart, BlockSequenceStart, Directive, DocumentEnd, DocumentStart, FlowEntry, FlowMappingEnd, FlowMappingStart, FlowSequenceEnd, FlowSequenceStart, Key, Scalar, StreamEnd, StreamStart, Tag, Value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Token(Mark startMark, Mark endMark) {
/* 34 */     if (startMark == null || endMark == null) {
/* 35 */       throw new YAMLException("Token requires marks.");
/*    */     }
/* 37 */     this.startMark = startMark;
/* 38 */     this.endMark = endMark;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public String toString() { return "<" + getClass().getName() + "(" + getArguments() + ")>"; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public Mark getStartMark() { return this.startMark; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public Mark getEndMark() { return this.endMark; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   protected String getArguments() { return ""; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract ID getTokenId();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 72 */     if (obj instanceof Token) {
/* 73 */       return toString().equals(obj.toString());
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\Token.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */