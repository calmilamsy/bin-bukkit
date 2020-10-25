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
/*    */ 
/*    */ 
/*    */ public final class FlowSequenceStartToken
/*    */   extends Token
/*    */ {
/* 27 */   public FlowSequenceStartToken(Mark startMark, Mark endMark) { super(startMark, endMark); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Token.ID getTokenId() { return Token.ID.FlowSequenceStart; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\FlowSequenceStartToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */