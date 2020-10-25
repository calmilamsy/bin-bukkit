/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import joptsimple.internal.Objects;
/*     */ import joptsimple.internal.Reflection;
/*     */ import joptsimple.internal.ReflectionException;
/*     */ import joptsimple.internal.Strings;
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
/*     */ public abstract class ArgumentAcceptingOptionSpec<V>
/*     */   extends AbstractOptionSpec<V>
/*     */ {
/*     */   private static final char NIL_VALUE_SEPARATOR = '\000';
/*     */   private final boolean argumentRequired;
/*     */   private ValueConverter<V> converter;
/*  64 */   private String argumentDescription = "";
/*  65 */   private String valueSeparator = String.valueOf(false);
/*  66 */   private final List<V> defaultValues = new ArrayList();
/*     */   
/*     */   ArgumentAcceptingOptionSpec(String option, boolean argumentRequired) {
/*  69 */     super(option);
/*     */     
/*  71 */     this.argumentRequired = argumentRequired;
/*     */   }
/*     */   
/*     */   ArgumentAcceptingOptionSpec(Collection<String> options, boolean argumentRequired, String description) {
/*  75 */     super(options, description);
/*     */     
/*  77 */     this.argumentRequired = argumentRequired;
/*     */   }
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
/* 111 */   public final <T> ArgumentAcceptingOptionSpec<T> ofType(Class<T> argumentType) { return withValuesConvertedBy(Reflection.findConverter(argumentType)); }
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
/*     */   public final <T> ArgumentAcceptingOptionSpec<T> withValuesConvertedBy(ValueConverter<T> aConverter) {
/* 129 */     if (aConverter == null) {
/* 130 */       throw new NullPointerException("illegal null converter");
/*     */     }
/* 132 */     this.converter = aConverter;
/* 133 */     return this;
/*     */   }
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
/*     */   public final ArgumentAcceptingOptionSpec<V> describedAs(String description) {
/* 146 */     this.argumentDescription = description;
/* 147 */     return this;
/*     */   }
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
/*     */   public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(char separator) {
/* 174 */     if (separator == '\000') {
/* 175 */       throw new IllegalArgumentException("cannot use U+0000 as separator");
/*     */     }
/* 177 */     this.valueSeparator = String.valueOf(separator);
/* 178 */     return this;
/*     */   }
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
/*     */   public ArgumentAcceptingOptionSpec<V> defaultsTo(V value, V... values) {
/* 192 */     addDefaultValue(value);
/* 193 */     for (V each : values) {
/* 194 */       addDefaultValue(each);
/*     */     }
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   private void addDefaultValue(V value) {
/* 200 */     Objects.ensureNotNull(value);
/* 201 */     this.defaultValues.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
/* 208 */     if (Strings.isNullOrEmpty(detectedArgument)) {
/* 209 */       detectOptionArgument(parser, arguments, detectedOptions);
/*     */     } else {
/* 211 */       addArguments(detectedOptions, detectedArgument);
/*     */     } 
/*     */   }
/*     */   protected void addArguments(OptionSet detectedOptions, String detectedArgument) {
/* 215 */     StringTokenizer lexer = new StringTokenizer(detectedArgument, this.valueSeparator);
/* 216 */     if (!lexer.hasMoreTokens()) {
/* 217 */       detectedOptions.addWithArgument(this, detectedArgument);
/*     */     } else {
/* 219 */       while (lexer.hasMoreTokens()) {
/* 220 */         detectedOptions.addWithArgument(this, lexer.nextToken());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet);
/*     */ 
/*     */   
/*     */   protected final V convert(String argument) {
/* 230 */     if (this.converter == null) {
/* 231 */       return (V)argument;
/*     */     }
/*     */     try {
/* 234 */       return (V)this.converter.convert(argument);
/*     */     }
/* 236 */     catch (ReflectionException ex) {
/* 237 */       throw new OptionArgumentConversionException(options(), argument, this.converter.valueType(), ex);
/*     */     }
/* 239 */     catch (ValueConversionException ex) {
/* 240 */       throw new OptionArgumentConversionException(options(), argument, this.converter.valueType(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canConvertArgument(String argument) {
/* 245 */     StringTokenizer lexer = new StringTokenizer(argument, this.valueSeparator);
/*     */     
/*     */     try {
/* 248 */       while (lexer.hasMoreTokens())
/* 249 */         convert(lexer.nextToken()); 
/* 250 */       return true;
/*     */     }
/* 252 */     catch (OptionException ignored) {
/* 253 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 258 */   protected boolean isArgumentOfNumberType() { return (this.converter != null && Number.class.isAssignableFrom(this.converter.valueType())); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   boolean acceptsArguments() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   boolean requiresArgument() { return this.argumentRequired; }
/*     */ 
/*     */ 
/*     */   
/* 272 */   String argumentDescription() { return this.argumentDescription; }
/*     */ 
/*     */   
/*     */   String typeIndicator() {
/* 276 */     if (this.converter == null) {
/* 277 */       return null;
/*     */     }
/* 279 */     String pattern = this.converter.valuePattern();
/* 280 */     return (pattern == null) ? this.converter.valueType().getName() : pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 285 */   List<V> defaultValues() { return Collections.unmodifiableList(this.defaultValues); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 290 */     if (!super.equals(that)) {
/* 291 */       return false;
/*     */     }
/* 293 */     ArgumentAcceptingOptionSpec<?> other = (ArgumentAcceptingOptionSpec)that;
/* 294 */     return (requiresArgument() == other.requiresArgument());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 299 */   public int hashCode() { return super.hashCode() ^ (this.argumentRequired ? 0 : 1); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\ArgumentAcceptingOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */