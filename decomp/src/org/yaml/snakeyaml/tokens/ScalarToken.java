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
/*    */ public final class ScalarToken
/*    */   extends Token
/*    */ {
/*    */   private final String value;
/*    */   private final boolean plain;
/*    */   private final char style;
/*    */   
/* 30 */   public ScalarToken(String value, Mark startMark, Mark endMark, boolean plain) { this(value, plain, startMark, endMark, false); }
/*    */ 
/*    */   
/*    */   public ScalarToken(String value, boolean plain, Mark startMark, Mark endMark, char style) {
/* 34 */     super(startMark, endMark);
/* 35 */     this.value = value;
/* 36 */     this.plain = plain;
/* 37 */     this.style = style;
/*    */   }
/*    */ 
/*    */   
/* 41 */   public boolean getPlain() { return this.plain; }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public String getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public char getStyle() { return this.style; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   protected String getArguments() { return "value=" + this.value + ", plain=" + this.plain + ", style=" + this.style; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public Token.ID getTokenId() { return Token.ID.Scalar; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\ScalarToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */