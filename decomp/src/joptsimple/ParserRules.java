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
/*    */ final class ParserRules
/*    */ {
/*    */   static final char HYPHEN_CHAR = '-';
/* 39 */   static final String HYPHEN = String.valueOf('-');
/*    */   static final String DOUBLE_HYPHEN = "--";
/*    */   static final String OPTION_TERMINATOR = "--";
/*    */   static final String RESERVED_FOR_EXTENSIONS = "W";
/*    */   
/*    */   static  {
/* 45 */     new ParserRules();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   static boolean isShortOptionToken(String argument) { return (argument.startsWith(HYPHEN) && !HYPHEN.equals(argument) && !isLongOptionToken(argument)); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   static boolean isLongOptionToken(String argument) { return (argument.startsWith("--") && !isOptionTerminator(argument)); }
/*    */ 
/*    */ 
/*    */   
/* 63 */   static boolean isOptionTerminator(String argument) { return "--".equals(argument); }
/*    */ 
/*    */   
/*    */   static void ensureLegalOption(String option) {
/* 67 */     if (option.startsWith(HYPHEN)) {
/* 68 */       throw new IllegalOptionSpecificationException(String.valueOf(option));
/*    */     }
/* 70 */     for (int i = 0; i < option.length(); i++)
/* 71 */       ensureLegalOptionCharacter(option.charAt(i)); 
/*    */   }
/*    */   
/*    */   static void ensureLegalOptions(Collection<String> options) {
/* 75 */     for (String each : options)
/* 76 */       ensureLegalOption(each); 
/*    */   }
/*    */   
/*    */   private static void ensureLegalOptionCharacter(char option) {
/* 80 */     if (!Character.isLetterOrDigit(option) && !isAllowedPunctuation(option))
/* 81 */       throw new IllegalOptionSpecificationException(String.valueOf(option)); 
/*    */   }
/*    */   
/*    */   private static boolean isAllowedPunctuation(char option) {
/* 85 */     String allowedPunctuation = "?.-";
/* 86 */     return (allowedPunctuation.indexOf(option) != -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\ParserRules.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */