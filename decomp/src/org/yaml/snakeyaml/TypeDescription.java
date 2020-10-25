/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class TypeDescription
/*     */ {
/*     */   private final Class<? extends Object> type;
/*     */   private Tag tag;
/*     */   private boolean root;
/*     */   private Map<String, Class<? extends Object>> listProperties;
/*     */   private Map<String, Class<? extends Object>> keyProperties;
/*     */   private Map<String, Class<? extends Object>> valueProperties;
/*     */   
/*     */   public TypeDescription(Class<? extends Object> clazz, Tag tag) {
/*  37 */     this.type = clazz;
/*  38 */     this.tag = tag;
/*  39 */     this.listProperties = new HashMap();
/*  40 */     this.keyProperties = new HashMap();
/*  41 */     this.valueProperties = new HashMap();
/*     */   }
/*     */ 
/*     */   
/*  45 */   public TypeDescription(Class<? extends Object> clazz, String tag) { this(clazz, new Tag(tag)); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public TypeDescription(Class<? extends Object> clazz) { this(clazz, (Tag)null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public Tag getTag() { return this.tag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public void setTag(Tag tag) { this.tag = tag; }
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void setTag(String tag) { setTag(new Tag(tag)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public Class<? extends Object> getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public boolean isRoot() { return this.root; }
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
/* 102 */   public void setRoot(boolean root) { this.root = root; }
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
/* 114 */   public void putListPropertyType(String property, Class<? extends Object> type) { this.listProperties.put(property, type); }
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
/* 125 */   public Class<? extends Object> getListPropertyType(String property) { return (Class)this.listProperties.get(property); }
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
/*     */   public void putMapPropertyType(String property, Class<? extends Object> key, Class<? extends Object> value) {
/* 140 */     this.keyProperties.put(property, key);
/* 141 */     this.valueProperties.put(property, value);
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
/* 152 */   public Class<? extends Object> getMapKeyType(String property) { return (Class)this.keyProperties.get(property); }
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
/* 163 */   public Class<? extends Object> getMapValueType(String property) { return (Class)this.valueProperties.get(property); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public String toString() { return "TypeDescription for " + getType() + " (tag='" + getTag() + "')"; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\TypeDescription.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */