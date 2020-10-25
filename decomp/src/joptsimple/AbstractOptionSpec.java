/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ abstract class AbstractOptionSpec<V>
/*     */   extends Object
/*     */   implements OptionSpec<V>
/*     */ {
/*     */   private final List<String> options;
/*     */   private final String description;
/*     */   
/*  45 */   protected AbstractOptionSpec(String option) { this(Collections.singletonList(option), ""); }
/*     */   
/*     */   protected AbstractOptionSpec(Collection<String> options, String description) {
/*     */     this.options = new ArrayList();
/*  49 */     arrangeOptions(options);
/*     */     
/*  51 */     this.description = description;
/*     */   }
/*     */ 
/*     */   
/*  55 */   public final Collection<String> options() { return Collections.unmodifiableCollection(this.options); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public final List<V> values(OptionSet detectedOptions) { return detectedOptions.valuesOf(this); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public final V value(OptionSet detectedOptions) { return (V)detectedOptions.valueOf(this); }
/*     */ 
/*     */   
/*     */   abstract List<V> defaultValues();
/*     */ 
/*     */   
/*  69 */   String description() { return this.description; }
/*     */ 
/*     */   
/*     */   protected abstract V convert(String paramString);
/*     */ 
/*     */   
/*     */   abstract void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString);
/*     */   
/*     */   abstract boolean acceptsArguments();
/*     */   
/*     */   abstract boolean requiresArgument();
/*     */   
/*     */   abstract void accept(OptionSpecVisitor paramOptionSpecVisitor);
/*     */   
/*     */   private void arrangeOptions(Collection<String> unarranged) {
/*  84 */     if (unarranged.size() == 1) {
/*  85 */       this.options.addAll(unarranged);
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     List<String> shortOptions = new ArrayList<String>();
/*  90 */     List<String> longOptions = new ArrayList<String>();
/*     */     
/*  92 */     for (String each : unarranged) {
/*  93 */       if (each.length() == 1) {
/*  94 */         shortOptions.add(each); continue;
/*     */       } 
/*  96 */       longOptions.add(each);
/*     */     } 
/*     */     
/*  99 */     Collections.sort(shortOptions);
/* 100 */     Collections.sort(longOptions);
/*     */     
/* 102 */     this.options.addAll(shortOptions);
/* 103 */     this.options.addAll(longOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 108 */     if (!(that instanceof AbstractOptionSpec)) {
/* 109 */       return false;
/*     */     }
/* 111 */     AbstractOptionSpec<?> other = (AbstractOptionSpec)that;
/* 112 */     return this.options.equals(other.options);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 117 */   public int hashCode() { return this.options.hashCode(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public String toString() { return this.options.toString(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\AbstractOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */