/*     */ package com.avaje.ebean.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class JsonWriteOptions
/*     */ {
/*     */   protected String callback;
/*     */   protected JsonValueAdapter valueAdapter;
/*     */   protected Map<String, JsonWriteBeanVisitor<?>> visitorMap;
/*     */   protected PathProperties pathProperties;
/*     */   
/*     */   public JsonWriteOptions copy() {
/* 104 */     JsonWriteOptions copy = new JsonWriteOptions();
/* 105 */     copy.callback = this.callback;
/* 106 */     copy.valueAdapter = this.valueAdapter;
/* 107 */     copy.pathProperties = this.pathProperties;
/* 108 */     if (this.visitorMap != null) {
/* 109 */       copy.visitorMap = new HashMap(this.visitorMap);
/*     */     }
/* 111 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public String getCallback() { return this.callback; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setCallback(String callback) {
/* 125 */     this.callback = callback;
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public JsonValueAdapter getValueAdapter() { return this.valueAdapter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setValueAdapter(JsonValueAdapter valueAdapter) {
/* 140 */     this.valueAdapter = valueAdapter;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public JsonWriteOptions setRootPathVisitor(JsonWriteBeanVisitor<?> visitor) { return setPathVisitor(null, visitor); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setPathVisitor(String path, JsonWriteBeanVisitor<?> visitor) {
/* 155 */     if (this.visitorMap == null) {
/* 156 */       this.visitorMap = new HashMap();
/*     */     }
/* 158 */     this.visitorMap.put(path, visitor);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonWriteOptions setPathProperties(String path, Set<String> propertiesToInclude) {
/* 169 */     if (this.pathProperties == null) {
/* 170 */       this.pathProperties = new PathProperties();
/*     */     }
/* 172 */     this.pathProperties.put(path, propertiesToInclude);
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public JsonWriteOptions setPathProperties(String path, String propertiesToInclude) { return setPathProperties(path, parseProps(propertiesToInclude)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public JsonWriteOptions setRootPathProperties(String propertiesToInclude) { return setPathProperties(null, parseProps(propertiesToInclude)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public JsonWriteOptions setRootPathProperties(Set<String> propertiesToInclude) { return setPathProperties(null, propertiesToInclude); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> parseProps(String propertiesToInclude) {
/* 208 */     LinkedHashSet<String> props = new LinkedHashSet<String>();
/*     */     
/* 210 */     String[] split = propertiesToInclude.split(",");
/* 211 */     for (int i = 0; i < split.length; i++) {
/* 212 */       String s = split[i].trim();
/* 213 */       if (s.length() > 0) {
/* 214 */         props.add(s);
/*     */       }
/*     */     } 
/* 217 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public Map<String, JsonWriteBeanVisitor<?>> getVisitorMap() { return this.visitorMap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public void setPathProperties(PathProperties pathProperties) { this.pathProperties = pathProperties; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public PathProperties getPathProperties() { return this.pathProperties; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\json\JsonWriteOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */