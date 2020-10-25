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
/*    */ public final class AliasToken
/*    */   extends Token
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public AliasToken(String value, Mark startMark, Mark endMark) {
/* 28 */     super(startMark, endMark);
/* 29 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 33 */   public String getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected String getArguments() { return "value=" + this.value; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public Token.ID getTokenId() { return Token.ID.Alias; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\AliasToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */