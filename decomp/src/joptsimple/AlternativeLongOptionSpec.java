/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class AlternativeLongOptionSpec
/*    */   extends ArgumentAcceptingOptionSpec<String>
/*    */ {
/*    */   AlternativeLongOptionSpec() {
/* 40 */     super(Collections.singletonList("W"), true, "Alternative form of long options");
/*    */     
/* 42 */     describedAs("opt=value");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 47 */     if (!arguments.hasMore()) {
/* 48 */       throw new OptionMissingRequiredArgumentException(options());
/*    */     }
/* 50 */     arguments.treatNextAsLongOption();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 55 */   void accept(OptionSpecVisitor visitor) { visitor.visit(this); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\AlternativeLongOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */