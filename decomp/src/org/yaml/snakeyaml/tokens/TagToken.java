/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public final class TagToken
/*    */   extends Token
/*    */ {
/*    */   private final TagTuple value;
/*    */   
/*    */   public TagToken(TagTuple value, Mark startMark, Mark endMark) {
/* 28 */     super(startMark, endMark);
/* 29 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 33 */   public TagTuple getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected String getArguments() { return "value=[" + this.value.getHandle() + ", " + this.value.getSuffix() + "]"; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public Token.ID getTokenId() { return Token.ID.Tag; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\TagToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */