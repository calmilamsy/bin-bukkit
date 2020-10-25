/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.emitter.ScalarAnalysis;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public class DumperOptions
/*     */ {
/*     */   public enum ScalarStyle
/*     */   {
/*  40 */     DOUBLE_QUOTED(new Character(34)), SINGLE_QUOTED(new Character(39)), LITERAL(new Character(124)),
/*  41 */     FOLDED(new Character(62)), PLAIN(null);
/*     */     
/*     */     private Character styleChar;
/*     */     
/*  45 */     ScalarStyle(Character style) { this.styleChar = style; }
/*     */ 
/*     */ 
/*     */     
/*  49 */     public Character getChar() { return this.styleChar; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     public String toString() { return "Scalar style: '" + this.styleChar + "'"; }
/*     */ 
/*     */     
/*     */     public static ScalarStyle createStyle(Character style) {
/*  58 */       if (style == null) {
/*  59 */         return PLAIN;
/*     */       }
/*  61 */       switch (style.charValue()) {
/*     */         case '"':
/*  63 */           return DOUBLE_QUOTED;
/*     */         case '\'':
/*  65 */           return SINGLE_QUOTED;
/*     */         case '|':
/*  67 */           return LITERAL;
/*     */         case '>':
/*  69 */           return FOLDED;
/*     */       } 
/*  71 */       throw new YAMLException("Unknown scalar style character: " + style);
/*     */     }
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
/*     */   public enum FlowStyle
/*     */   {
/*  85 */     FLOW(Boolean.TRUE), BLOCK(Boolean.FALSE), AUTO(null);
/*     */     
/*     */     private Boolean styleBoolean;
/*     */ 
/*     */     
/*  90 */     FlowStyle(Boolean flowStyle) { this.styleBoolean = flowStyle; }
/*     */ 
/*     */ 
/*     */     
/*  94 */     public Boolean getStyleBoolean() { return this.styleBoolean; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     public String toString() { return "Flow style: '" + this.styleBoolean + "'"; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum LineBreak
/*     */   {
/* 107 */     WIN("\r\n"), MAC("\r"), UNIX("\n");
/*     */     
/*     */     private String lineBreak;
/*     */ 
/*     */     
/* 112 */     LineBreak(String lineBreak) { this.lineBreak = lineBreak; }
/*     */ 
/*     */ 
/*     */     
/* 116 */     public String getString() { return this.lineBreak; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     public String toString() { return "Line break: " + name(); }
/*     */ 
/*     */     
/*     */     public static LineBreak getPlatformLineBreak() {
/* 125 */       platformLineBreak = System.getProperty("line.separator");
/* 126 */       for (LineBreak lb : values()) {
/* 127 */         if (lb.lineBreak.equals(platformLineBreak)) {
/* 128 */           return lb;
/*     */         }
/*     */       } 
/* 131 */       return UNIX;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Version
/*     */   {
/* 139 */     V1_0(new Integer[] { null, (new Integer[2][0] = Integer.valueOf(1)).valueOf(0) }), V1_1(new Integer[] { null, (new Integer[2][0] = Integer.valueOf(1)).valueOf(1) });
/*     */     
/*     */     private Integer[] version;
/*     */ 
/*     */     
/* 144 */     Version(Integer[] version) { this.version = version; }
/*     */ 
/*     */ 
/*     */     
/* 148 */     public Integer[] getArray() { return this.version; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     public String toString() { return "Version: " + this.version[0] + "." + this.version[1]; }
/*     */   }
/*     */ 
/*     */   
/* 157 */   private ScalarStyle defaultStyle = ScalarStyle.PLAIN;
/* 158 */   private FlowStyle defaultFlowStyle = FlowStyle.AUTO;
/*     */   private boolean canonical = false;
/*     */   private boolean allowUnicode = true;
/*     */   private boolean allowReadOnlyProperties = false;
/* 162 */   private int indent = 2;
/* 163 */   private int bestWidth = 80;
/* 164 */   private LineBreak lineBreak = LineBreak.UNIX;
/*     */   private boolean explicitStart = false;
/*     */   private boolean explicitEnd = false;
/* 167 */   private Tag explicitRoot = null;
/* 168 */   private Version version = null;
/* 169 */   private Map<String, String> tags = null;
/* 170 */   private Boolean prettyFlow = Boolean.valueOf(false);
/*     */ 
/*     */   
/* 173 */   public boolean isAllowUnicode() { return this.allowUnicode; }
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
/* 185 */   public void setAllowUnicode(boolean allowUnicode) { this.allowUnicode = allowUnicode; }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public ScalarStyle getDefaultScalarStyle() { return this.defaultStyle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultScalarStyle(ScalarStyle defaultStyle) {
/* 200 */     if (defaultStyle == null) {
/* 201 */       throw new NullPointerException("Use ScalarStyle enum.");
/*     */     }
/* 203 */     this.defaultStyle = defaultStyle;
/*     */   }
/*     */   
/*     */   public void setIndent(int indent) {
/* 207 */     if (indent < 1) {
/* 208 */       throw new YAMLException("Indent must be at least 1");
/*     */     }
/* 210 */     if (indent > 10) {
/* 211 */       throw new YAMLException("Indent must be at most 10");
/*     */     }
/* 213 */     this.indent = indent;
/*     */   }
/*     */ 
/*     */   
/* 217 */   public int getIndent() { return this.indent; }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public void setVersion(Version version) { this.version = version; }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public Version getVersion() { return this.version; }
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
/* 236 */   public void setCanonical(boolean canonical) { this.canonical = canonical; }
/*     */ 
/*     */ 
/*     */   
/* 240 */   public boolean isCanonical() { return this.canonical; }
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
/* 251 */   public void setPrettyFlow(boolean prettyFlow) { this.prettyFlow = Boolean.valueOf(prettyFlow); }
/*     */ 
/*     */ 
/*     */   
/* 255 */   public boolean isPrettyFlow() { return this.prettyFlow.booleanValue(); }
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
/* 267 */   public void setWidth(int bestWidth) { this.bestWidth = bestWidth; }
/*     */ 
/*     */ 
/*     */   
/* 271 */   public int getWidth() { return this.bestWidth; }
/*     */ 
/*     */ 
/*     */   
/* 275 */   public LineBreak getLineBreak() { return this.lineBreak; }
/*     */ 
/*     */   
/*     */   public void setDefaultFlowStyle(FlowStyle defaultFlowStyle) {
/* 279 */     if (defaultFlowStyle == null) {
/* 280 */       throw new NullPointerException("Use FlowStyle enum.");
/*     */     }
/* 282 */     this.defaultFlowStyle = defaultFlowStyle;
/*     */   }
/*     */ 
/*     */   
/* 286 */   public FlowStyle getDefaultFlowStyle() { return this.defaultFlowStyle; }
/*     */ 
/*     */ 
/*     */   
/* 290 */   public Tag getExplicitRoot() { return this.explicitRoot; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public void setExplicitRoot(String expRoot) { setExplicitRoot(new Tag(expRoot)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExplicitRoot(Tag expRoot) {
/* 309 */     if (expRoot == null) {
/* 310 */       throw new NullPointerException("Root tag must be specified.");
/*     */     }
/* 312 */     this.explicitRoot = expRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineBreak(LineBreak lineBreak) {
/* 321 */     if (lineBreak == null) {
/* 322 */       throw new NullPointerException("Specify line break.");
/*     */     }
/* 324 */     this.lineBreak = lineBreak;
/*     */   }
/*     */ 
/*     */   
/* 328 */   public boolean isExplicitStart() { return this.explicitStart; }
/*     */ 
/*     */ 
/*     */   
/* 332 */   public void setExplicitStart(boolean explicitStart) { this.explicitStart = explicitStart; }
/*     */ 
/*     */ 
/*     */   
/* 336 */   public boolean isExplicitEnd() { return this.explicitEnd; }
/*     */ 
/*     */ 
/*     */   
/* 340 */   public void setExplicitEnd(boolean explicitEnd) { this.explicitEnd = explicitEnd; }
/*     */ 
/*     */ 
/*     */   
/* 344 */   public Map<String, String> getTags() { return this.tags; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 349 */   public void setTags(Map<String, String> tags) { this.tags = tags; }
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
/* 362 */   public ScalarStyle calculateScalarStyle(ScalarAnalysis analysis, ScalarStyle style) { return style; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 372 */   public boolean isAllowReadOnlyProperties() { return this.allowReadOnlyProperties; }
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
/* 384 */   public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) { this.allowReadOnlyProperties = allowReadOnlyProperties; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\DumperOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */