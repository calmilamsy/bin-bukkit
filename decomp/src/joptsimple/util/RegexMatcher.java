/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import joptsimple.ValueConversionException;
/*    */ import joptsimple.ValueConverter;
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
/*    */ public class RegexMatcher
/*    */   extends Object
/*    */   implements ValueConverter<String>
/*    */ {
/*    */   private final Pattern pattern;
/*    */   
/* 55 */   public RegexMatcher(String pattern, int flags) { this.pattern = Pattern.compile(pattern, flags); }
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
/* 67 */   public static ValueConverter<String> regex(String pattern) { return new RegexMatcher(pattern, false); }
/*    */ 
/*    */   
/*    */   public String convert(String value) {
/* 71 */     if (!this.pattern.matcher(value).matches()) {
/* 72 */       throw new ValueConversionException("Value [" + value + "] did not match regex [" + this.pattern.pattern() + ']');
/*    */     }
/*    */ 
/*    */     
/* 76 */     return value;
/*    */   }
/*    */ 
/*    */   
/* 80 */   public Class<String> valueType() { return String.class; }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public String valuePattern() { return this.pattern.pattern(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimpl\\util\RegexMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */