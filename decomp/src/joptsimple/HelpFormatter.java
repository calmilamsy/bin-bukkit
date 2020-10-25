/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import joptsimple.internal.Classes;
/*     */ import joptsimple.internal.ColumnarData;
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
/*     */ class HelpFormatter
/*     */   implements OptionSpecVisitor
/*     */ {
/*  50 */   private final ColumnarData grid = new ColumnarData(new String[] { "Option", "Description" });
/*     */ 
/*     */   
/*     */   String format(Map<String, AbstractOptionSpec<?>> options) {
/*  54 */     if (options.isEmpty()) {
/*  55 */       return "No options specified";
/*     */     }
/*  57 */     this.grid.clear();
/*     */     
/*  59 */     Comparator<AbstractOptionSpec<?>> comparator = new Comparator<AbstractOptionSpec<?>>()
/*     */       {
/*     */         public int compare(AbstractOptionSpec<?> first, AbstractOptionSpec<?> second) {
/*  62 */           return ((String)first.options().iterator().next()).compareTo((String)second.options().iterator().next());
/*     */         }
/*     */       };
/*     */     
/*  66 */     Set<AbstractOptionSpec<?>> sorted = new TreeSet<AbstractOptionSpec<?>>(comparator);
/*  67 */     sorted.addAll(options.values());
/*     */     
/*  69 */     for (AbstractOptionSpec<?> each : sorted) {
/*  70 */       each.accept(this);
/*     */     }
/*  72 */     return this.grid.format();
/*     */   }
/*     */ 
/*     */   
/*  76 */   void addHelpLineFor(AbstractOptionSpec<?> spec, String additionalInfo) { this.grid.addRow(new Object[] { createOptionDisplay(spec) + additionalInfo, createDescriptionDisplay(spec) }); }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void visit(NoArgumentOptionSpec spec) { addHelpLineFor(spec, ""); }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void visit(RequiredArgumentOptionSpec<?> spec) { visit(spec, '<', '>'); }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public void visit(OptionalArgumentOptionSpec<?> spec) { visit(spec, '[', ']'); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void visit(AlternativeLongOptionSpec spec) { addHelpLineFor(spec, ' ' + Strings.surround(spec.argumentDescription(), '<', '>')); }
/*     */ 
/*     */   
/*     */   private void visit(ArgumentAcceptingOptionSpec<?> spec, char begin, char end) {
/*  96 */     String argDescription = spec.argumentDescription();
/*  97 */     String typeIndicator = typeIndicator(spec);
/*  98 */     StringBuilder collector = new StringBuilder();
/*     */     
/* 100 */     if (typeIndicator.length() > 0) {
/* 101 */       collector.append(typeIndicator);
/*     */       
/* 103 */       if (argDescription.length() > 0) {
/* 104 */         collector.append(": ").append(argDescription);
/*     */       }
/* 106 */     } else if (argDescription.length() > 0) {
/* 107 */       collector.append(argDescription);
/*     */     } 
/* 109 */     String helpLine = (collector.length() == 0) ? "" : (' ' + Strings.surround(collector.toString(), begin, end));
/*     */ 
/*     */     
/* 112 */     addHelpLineFor(spec, helpLine);
/*     */   }
/*     */   
/*     */   private String createOptionDisplay(AbstractOptionSpec<?> spec) {
/* 116 */     StringBuilder buffer = new StringBuilder();
/*     */     
/* 118 */     for (Iterator<String> iter = spec.options().iterator(); iter.hasNext(); ) {
/* 119 */       String option = (String)iter.next();
/* 120 */       buffer.append((option.length() > 1) ? "--" : ParserRules.HYPHEN);
/* 121 */       buffer.append(option);
/*     */       
/* 123 */       if (iter.hasNext()) {
/* 124 */         buffer.append(", ");
/*     */       }
/*     */     } 
/* 127 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   private String createDescriptionDisplay(AbstractOptionSpec<?> spec) {
/* 131 */     List<?> defaultValues = spec.defaultValues();
/* 132 */     if (defaultValues.isEmpty()) {
/* 133 */       return spec.description();
/*     */     }
/* 135 */     String defaultValuesDisplay = createDefaultValuesDisplay(defaultValues);
/* 136 */     return spec.description() + ' ' + Strings.surround("default: " + defaultValuesDisplay, '(', ')');
/*     */   }
/*     */ 
/*     */   
/* 140 */   private String createDefaultValuesDisplay(List<?> defaultValues) { return (defaultValues.size() == 1) ? defaultValues.get(0).toString() : defaultValues.toString(); }
/*     */ 
/*     */   
/*     */   private static String typeIndicator(ArgumentAcceptingOptionSpec<?> spec) {
/* 144 */     String indicator = spec.typeIndicator();
/* 145 */     return (indicator == null || String.class.getName().equals(indicator)) ? "" : Classes.shortNameOf(indicator);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\HelpFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */