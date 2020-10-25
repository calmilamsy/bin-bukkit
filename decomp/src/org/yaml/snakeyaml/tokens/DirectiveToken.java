/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DirectiveToken<T>
/*    */   extends Token
/*    */ {
/*    */   private final String name;
/*    */   private final List<T> value;
/*    */   
/*    */   public DirectiveToken(String name, List<T> value, Mark startMark, Mark endMark) {
/* 32 */     super(startMark, endMark);
/* 33 */     this.name = name;
/* 34 */     if (value != null && value.size() != 2) {
/* 35 */       throw new YAMLException("Two strings must be provided instead of " + String.valueOf(value.size()));
/*    */     }
/*    */     
/* 38 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/* 42 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public List<T> getValue() { return this.value; }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getArguments() {
/* 51 */     if (this.value != null) {
/* 52 */       return "name=" + this.name + ", value=[" + this.value.get(0) + ", " + this.value.get(1) + "]";
/*    */     }
/* 54 */     return "name=" + this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public Token.ID getTokenId() { return Token.ID.Directive; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\tokens\DirectiveToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */