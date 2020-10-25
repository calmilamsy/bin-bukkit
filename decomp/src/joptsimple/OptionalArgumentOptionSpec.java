/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ class OptionalArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/* 39 */   OptionalArgumentOptionSpec(String option) { super(option, false); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   OptionalArgumentOptionSpec(Collection<String> options, String description) { super(options, false, description); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 48 */     if (arguments.hasMore()) {
/* 49 */       String nextArgument = arguments.peek();
/*    */       
/* 51 */       if (!parser.looksLikeAnOption(nextArgument)) {
/* 52 */         handleOptionArgument(parser, detectedOptions, arguments);
/* 53 */       } else if (isArgumentOfNumberType() && canConvertArgument(nextArgument)) {
/* 54 */         addArguments(detectedOptions, arguments.next());
/*    */       } else {
/* 56 */         detectedOptions.add(this);
/*    */       } 
/*    */     } else {
/* 59 */       detectedOptions.add(this);
/*    */     } 
/*    */   }
/*    */   private void handleOptionArgument(OptionParser parser, OptionSet detectedOptions, ArgumentList arguments) {
/* 63 */     if (parser.posixlyCorrect()) {
/* 64 */       detectedOptions.add(this);
/* 65 */       parser.noMoreOptions();
/*    */     } else {
/*    */       
/* 68 */       addArguments(detectedOptions, arguments.next());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 73 */   void accept(OptionSpecVisitor visitor) { visitor.visit(this); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionalArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */