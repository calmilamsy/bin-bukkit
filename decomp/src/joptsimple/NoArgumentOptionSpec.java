/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ class NoArgumentOptionSpec
/*    */   extends AbstractOptionSpec<Void>
/*    */ {
/* 41 */   NoArgumentOptionSpec(String option) { this(Collections.singletonList(option), ""); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   NoArgumentOptionSpec(Collection<String> options, String description) { super(options, description); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) { detectedOptions.add(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   boolean acceptsArguments() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   boolean requiresArgument() { return false; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   void accept(OptionSpecVisitor visitor) { visitor.visit(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   protected Void convert(String argument) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   List<Void> defaultValues() { return Collections.emptyList(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\NoArgumentOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */