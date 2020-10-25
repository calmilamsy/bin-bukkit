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
/*    */ public class OptionSpecBuilder
/*    */   extends NoArgumentOptionSpec
/*    */ {
/*    */   private final OptionParser parser;
/*    */   
/*    */   OptionSpecBuilder(OptionParser parser, Collection<String> options, String description) {
/* 65 */     super(options, description);
/*    */     
/* 67 */     this.parser = parser;
/* 68 */     attachToParser();
/*    */   }
/*    */ 
/*    */   
/* 72 */   private void attachToParser() { this.parser.recognize(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArgumentAcceptingOptionSpec<String> withRequiredArg() {
/* 81 */     ArgumentAcceptingOptionSpec<String> newSpec = new RequiredArgumentOptionSpec<String>(options(), description());
/*    */     
/* 83 */     this.parser.recognize(newSpec);
/*    */     
/* 85 */     return newSpec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArgumentAcceptingOptionSpec<String> withOptionalArg() {
/* 95 */     ArgumentAcceptingOptionSpec<String> newSpec = new OptionalArgumentOptionSpec<String>(options(), description());
/*    */     
/* 97 */     this.parser.recognize(newSpec);
/*    */     
/* 99 */     return newSpec;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionSpecBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */