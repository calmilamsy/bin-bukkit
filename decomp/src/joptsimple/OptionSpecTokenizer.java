/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OptionSpecTokenizer
/*     */ {
/*     */   private static final char POSIXLY_CORRECT_MARKER = '+';
/*     */   private String specification;
/*     */   private int index;
/*     */   
/*     */   OptionSpecTokenizer(String specification) {
/*  45 */     if (specification == null) {
/*  46 */       throw new NullPointerException("null option specification");
/*     */     }
/*  48 */     this.specification = specification;
/*     */   }
/*     */ 
/*     */   
/*  52 */   boolean hasMore() { return (this.index < this.specification.length()); }
/*     */   
/*     */   AbstractOptionSpec<?> next() {
/*     */     AbstractOptionSpec<?> spec;
/*  56 */     if (!hasMore()) {
/*  57 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*  60 */     String optionCandidate = String.valueOf(this.specification.charAt(this.index));
/*  61 */     this.index++;
/*     */ 
/*     */     
/*  64 */     if ("W".equals(optionCandidate)) {
/*  65 */       spec = handleReservedForExtensionsToken();
/*     */       
/*  67 */       if (spec != null) {
/*  68 */         return spec;
/*     */       }
/*     */     } 
/*  71 */     ParserRules.ensureLegalOption(optionCandidate);
/*     */     
/*  73 */     if (hasMore()) {
/*  74 */       spec = (this.specification.charAt(this.index) == ':') ? handleArgumentAcceptingOption(optionCandidate) : new NoArgumentOptionSpec(optionCandidate);
/*     */     }
/*     */     else {
/*     */       
/*  78 */       spec = new NoArgumentOptionSpec<?>(optionCandidate);
/*     */     } 
/*  80 */     return spec;
/*     */   }
/*     */   
/*     */   void configure(OptionParser parser) {
/*  84 */     adjustForPosixlyCorrect(parser);
/*     */     
/*  86 */     while (hasMore())
/*  87 */       parser.recognize(next()); 
/*     */   }
/*     */   
/*     */   private void adjustForPosixlyCorrect(OptionParser parser) {
/*  91 */     if ('+' == this.specification.charAt(0)) {
/*  92 */       parser.posixlyCorrect(true);
/*  93 */       this.specification = this.specification.substring(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> handleReservedForExtensionsToken() {
/*  98 */     if (!hasMore()) {
/*  99 */       return new NoArgumentOptionSpec("W");
/*     */     }
/* 101 */     if (this.specification.charAt(this.index) == ';') {
/* 102 */       this.index++;
/* 103 */       return new AlternativeLongOptionSpec();
/*     */     } 
/*     */     
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> handleArgumentAcceptingOption(String candidate) {
/* 110 */     this.index++;
/*     */     
/* 112 */     if (hasMore() && this.specification.charAt(this.index) == ':') {
/* 113 */       this.index++;
/* 114 */       return new OptionalArgumentOptionSpec(candidate);
/*     */     } 
/*     */     
/* 117 */     return new RequiredArgumentOptionSpec(candidate);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\OptionSpecTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */