/*     */ package com.avaje.ebean.text;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class PathProperties
/*     */ {
/*     */   private final Map<String, Props> pathMap;
/*     */   private final Props rootProps;
/*     */   
/*  57 */   public static PathProperties parse(String source) { return PathPropertiesParser.parse(source); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathProperties() {
/*  64 */     this.rootProps = new Props(this, null, null, null);
/*  65 */     this.pathMap = new LinkedHashMap();
/*  66 */     this.pathMap.put(null, this.rootProps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathProperties(PathProperties orig) {
/*  73 */     this.rootProps = orig.rootProps.copy(this);
/*  74 */     this.pathMap = new LinkedHashMap(orig.pathMap.size());
/*  75 */     Set<Map.Entry<String, Props>> entrySet = orig.pathMap.entrySet();
/*  76 */     for (Map.Entry<String, Props> e : entrySet) {
/*  77 */       this.pathMap.put(e.getKey(), ((Props)e.getValue()).copy(this));
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
/*     */ 
/*     */ 
/*     */   
/*  92 */   public PathProperties copy() { return new PathProperties(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public boolean isEmpty() { return this.pathMap.isEmpty(); }
/*     */ 
/*     */ 
/*     */   
/* 103 */   public String toString() { return this.pathMap.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPath(String path) {
/* 110 */     Props props = (Props)this.pathMap.get(path);
/* 111 */     return (props != null && !props.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> get(String path) {
/* 118 */     Props props = (Props)this.pathMap.get(path);
/* 119 */     return (props == null) ? null : props.getProperties();
/*     */   }
/*     */   
/*     */   public void addToPath(String path, String property) {
/* 123 */     Props props = (Props)this.pathMap.get(path);
/* 124 */     if (props == null) {
/* 125 */       props = new Props(this, null, path, null);
/* 126 */       this.pathMap.put(path, props);
/*     */     } 
/* 128 */     props.getProperties().add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public void put(String path, Set<String> properties) { this.pathMap.put(path, new Props(this, null, path, properties, null)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> remove(String path) {
/* 142 */     Props props = (Props)this.pathMap.remove(path);
/* 143 */     return (props == null) ? null : props.getProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public Set<String> getPaths() { return new LinkedHashSet(this.pathMap.keySet()); }
/*     */ 
/*     */ 
/*     */   
/* 154 */   public Collection<Props> getPathProps() { return this.pathMap.values(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Query<?> query) {
/* 162 */     for (Map.Entry<String, Props> entry : this.pathMap.entrySet()) {
/* 163 */       String path = (String)entry.getKey();
/* 164 */       String props = ((Props)entry.getValue()).getPropertiesAsString();
/*     */       
/* 166 */       if (path == null || path.length() == 0) {
/* 167 */         query.select(props); continue;
/*     */       } 
/* 169 */       query.fetch(path, props);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 175 */   protected Props getRootProperties() { return this.rootProps; }
/*     */ 
/*     */   
/*     */   public static class Props
/*     */   {
/*     */     private final PathProperties owner;
/*     */     
/*     */     private final String parentPath;
/*     */     
/*     */     private final String path;
/*     */     private final Set<String> propSet;
/*     */     
/*     */     private Props(PathProperties owner, String parentPath, String path, Set<String> propSet) {
/* 188 */       this.owner = owner;
/* 189 */       this.path = path;
/* 190 */       this.parentPath = parentPath;
/* 191 */       this.propSet = propSet;
/*     */     }
/*     */ 
/*     */     
/* 195 */     private Props(PathProperties owner, String parentPath, String path) { this(owner, parentPath, path, new LinkedHashSet()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     public Props copy(PathProperties newOwner) { return new Props(newOwner, this.parentPath, this.path, new LinkedHashSet(this.propSet)); }
/*     */ 
/*     */ 
/*     */     
/* 206 */     public String getPath() { return this.path; }
/*     */ 
/*     */ 
/*     */     
/* 210 */     public String toString() { return this.propSet.toString(); }
/*     */ 
/*     */ 
/*     */     
/* 214 */     public boolean isEmpty() { return this.propSet.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     public Set<String> getProperties() { return this.propSet; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPropertiesAsString() {
/* 229 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 231 */       Iterator<String> it = this.propSet.iterator();
/* 232 */       boolean hasNext = it.hasNext();
/* 233 */       while (hasNext) {
/* 234 */         sb.append((String)it.next());
/* 235 */         hasNext = it.hasNext();
/* 236 */         if (hasNext) {
/* 237 */           sb.append(",");
/*     */         }
/*     */       } 
/* 240 */       return sb.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     protected Props getParent() { return (Props)this.owner.pathMap.get(this.parentPath); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Props addChild(String subpath) {
/* 255 */       subpath = subpath.trim();
/* 256 */       addProperty(subpath);
/*     */ 
/*     */       
/* 259 */       String p = (this.path == null) ? subpath : (this.path + "." + subpath);
/* 260 */       Props nested = new Props(this.owner, this.path, p);
/* 261 */       this.owner.pathMap.put(p, nested);
/* 262 */       return nested;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     protected void addProperty(String property) { this.propSet.add(property.trim()); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\text\PathProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */