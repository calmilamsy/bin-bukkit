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
/*    */ class RequiredArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/* 39 */   RequiredArgumentOptionSpec(String option) { super(option, true); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   RequiredArgumentOptionSpec(Collection<String> options, String description) { super(options, true, description); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 48 */     if (!arguments.hasMore()) {
/* 49 */       throw new OptionMissingRequiredArgumentException(options());
/*    */     }
/* 51 */     addArguments(detectedOptions, arguments.next());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 56 */   void accept(OptionSpecVisitor visitor) { visitor.visit(this); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\RequiredArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */