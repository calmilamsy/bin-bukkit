/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
/*     */ import com.avaje.ebean.text.json.JsonWriteOptions;
/*     */ import com.avaje.ebean.text.json.JsonWriter;
/*     */ import com.avaje.ebeaninternal.server.util.ArrayStack;
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
/*     */ public class WriteJsonContext
/*     */   implements JsonWriter
/*     */ {
/*     */   private final WriteJsonBuffer buffer;
/*     */   private final boolean pretty;
/*     */   private final JsonValueAdapter valueAdapter;
/*     */   private final ArrayStack<Object> parentBeans;
/*     */   private final PathProperties pathProperties;
/*     */   private final Map<String, JsonWriteBeanVisitor<?>> visitorMap;
/*     */   private final String callback;
/*     */   private final PathStack pathStack;
/*     */   private WriteBeanState beanState;
/*     */   private int depthOffset;
/*     */   boolean assocOne;
/*     */   
/*     */   public WriteJsonContext(WriteJsonBuffer buffer, boolean pretty, JsonValueAdapter dfltValueAdapter, JsonWriteOptions options, String requestCallback) {
/*  43 */     this.parentBeans = new ArrayStack();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     this.buffer = buffer;
/*  63 */     this.pretty = pretty;
/*  64 */     this.pathStack = new PathStack();
/*  65 */     this.callback = getCallback(requestCallback, options);
/*  66 */     if (options == null) {
/*  67 */       this.valueAdapter = dfltValueAdapter;
/*  68 */       this.visitorMap = null;
/*  69 */       this.pathProperties = null;
/*     */     } else {
/*     */       
/*  72 */       this.valueAdapter = getValueAdapter(dfltValueAdapter, options.getValueAdapter());
/*  73 */       this.visitorMap = emptyToNull(options.getVisitorMap());
/*  74 */       this.pathProperties = emptyToNull(options.getPathProperties());
/*     */     } 
/*     */     
/*  77 */     if (this.callback != null) {
/*  78 */       buffer.append(requestCallback).append("(");
/*     */     }
/*     */   }
/*     */   
/*     */   public void end() {
/*  83 */     if (this.callback != null) {
/*  84 */       this.buffer.append(")");
/*     */     }
/*     */   }
/*     */   
/*     */   private <MK, MV> Map<MK, MV> emptyToNull(Map<MK, MV> m) {
/*  89 */     if (m == null || m.isEmpty()) {
/*  90 */       return null;
/*     */     }
/*  92 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathProperties emptyToNull(PathProperties m) {
/*  97 */     if (m == null || m.isEmpty()) {
/*  98 */       return null;
/*     */     }
/* 100 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getCallback(String requestCallback, JsonWriteOptions options) {
/* 105 */     if (requestCallback != null) {
/* 106 */       return requestCallback;
/*     */     }
/* 108 */     if (options != null) {
/* 109 */       return options.getCallback();
/*     */     }
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 115 */   private JsonValueAdapter getValueAdapter(JsonValueAdapter dfltValueAdapter, JsonValueAdapter valueAdapter) { return (valueAdapter == null) ? dfltValueAdapter : valueAdapter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getIncludeProperties() {
/* 123 */     if (this.pathProperties != null) {
/* 124 */       String path = (String)this.pathStack.peekWithNull();
/* 125 */       return this.pathProperties.get(path);
/*     */     } 
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public JsonWriteBeanVisitor<?> getBeanVisitor() {
/* 131 */     if (this.visitorMap != null) {
/* 132 */       String path = (String)this.pathStack.peekWithNull();
/* 133 */       return (JsonWriteBeanVisitor)this.visitorMap.get(path);
/*     */     } 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 139 */   public String getJson() { return this.buffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendIndent() {
/* 144 */     this.buffer.append("\n");
/* 145 */     int depth = this.depthOffset + this.parentBeans.size();
/* 146 */     for (int i = 0; i < depth; i++) {
/* 147 */       this.buffer.append("    ");
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendObjectBegin() {
/* 152 */     if (this.pretty && !this.assocOne) {
/* 153 */       appendIndent();
/*     */     }
/* 155 */     this.buffer.append("{");
/*     */   }
/*     */   
/* 158 */   public void appendObjectEnd() { this.buffer.append("}"); }
/*     */ 
/*     */   
/*     */   public void appendArrayBegin() {
/* 162 */     if (this.pretty) {
/* 163 */       appendIndent();
/*     */     }
/* 165 */     this.buffer.append("[");
/* 166 */     this.depthOffset++;
/*     */   }
/*     */   
/*     */   public void appendArrayEnd() {
/* 170 */     this.depthOffset--;
/* 171 */     if (this.pretty) {
/* 172 */       appendIndent();
/*     */     }
/* 174 */     this.buffer.append("]");
/*     */   }
/*     */ 
/*     */   
/* 178 */   public void appendComma() { this.buffer.append(","); }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public void addDepthOffset(int offset) { this.depthOffset += offset; }
/*     */ 
/*     */   
/*     */   public void beginAssocOneIsNull(String key) {
/* 186 */     this.depthOffset++;
/* 187 */     internalAppendKeyBegin(key);
/* 188 */     appendNull();
/* 189 */     this.depthOffset--;
/*     */   }
/*     */   
/*     */   public void beginAssocOne(String key) {
/* 193 */     this.pathStack.pushPathKey(key);
/*     */     
/* 195 */     internalAppendKeyBegin(key);
/* 196 */     this.assocOne = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endAssocOne() {
/* 201 */     this.pathStack.pop();
/* 202 */     this.assocOne = false;
/*     */   }
/*     */   
/*     */   public Boolean includeMany(String key) {
/* 206 */     if (this.pathProperties != null) {
/* 207 */       String fullPath = this.pathStack.peekFullPath(key);
/* 208 */       return Boolean.valueOf(this.pathProperties.hasPath(fullPath));
/*     */     } 
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginAssocMany(String key) {
/* 215 */     this.pathStack.pushPathKey(key);
/*     */     
/* 217 */     this.depthOffset--;
/* 218 */     internalAppendKeyBegin(key);
/* 219 */     this.depthOffset++;
/* 220 */     this.buffer.append("[");
/*     */   }
/*     */ 
/*     */   
/*     */   public void endAssocMany() {
/* 225 */     this.pathStack.pop();
/*     */     
/* 227 */     if (this.pretty) {
/* 228 */       this.depthOffset--;
/* 229 */       appendIndent();
/* 230 */       this.depthOffset++;
/*     */     } 
/* 232 */     this.buffer.append("]");
/*     */   }
/*     */   
/*     */   private void internalAppendKeyBegin(String key) {
/* 236 */     if (!this.beanState.isFirstKey()) {
/* 237 */       this.buffer.append(",");
/*     */     }
/* 239 */     if (this.pretty) {
/* 240 */       appendIndent();
/*     */     }
/* 242 */     appendKeyWithComma(key, false);
/*     */   }
/*     */ 
/*     */   
/* 246 */   public void appendKey(String key) { appendKeyWithComma(key, true); }
/*     */ 
/*     */   
/*     */   private void appendKeyWithComma(String key, boolean withComma) {
/* 250 */     if (withComma && 
/* 251 */       !this.beanState.isFirstKey()) {
/* 252 */       this.buffer.append(",");
/*     */     }
/*     */     
/* 255 */     this.buffer.append("\"");
/* 256 */     if (key == null) {
/* 257 */       this.buffer.append("null");
/*     */     } else {
/* 259 */       this.buffer.append(key);
/*     */     } 
/* 261 */     this.buffer.append("\":");
/*     */   }
/*     */   
/*     */   public void appendKeyValue(String key, String escapedValue) {
/* 265 */     appendKey(key);
/* 266 */     this.buffer.append(escapedValue);
/*     */   }
/*     */   
/*     */   public void appendNull(String key) {
/* 270 */     appendKey(key);
/* 271 */     this.buffer.append("null");
/*     */   }
/*     */ 
/*     */   
/* 275 */   public void appendNull() { this.buffer.append("null"); }
/*     */ 
/*     */ 
/*     */   
/* 279 */   public JsonValueAdapter getValueAdapter() { return this.valueAdapter; }
/*     */ 
/*     */ 
/*     */   
/* 283 */   public String toString() { return this.buffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/* 287 */   public void popParentBean() { this.parentBeans.pop(); }
/*     */ 
/*     */ 
/*     */   
/* 291 */   public void pushParentBean(Object parentBean) { this.parentBeans.push(parentBean); }
/*     */ 
/*     */   
/*     */   public void popParentBeanMany() {
/* 295 */     this.parentBeans.pop();
/* 296 */     this.depthOffset--;
/*     */   }
/*     */   
/*     */   public void pushParentBeanMany(Object parentBean) {
/* 300 */     this.parentBeans.push(parentBean);
/* 301 */     this.depthOffset++;
/*     */   }
/*     */   
/*     */   public boolean isParentBean(Object bean) {
/* 305 */     if (this.parentBeans.isEmpty()) {
/* 306 */       return false;
/*     */     }
/* 308 */     return (bean == this.parentBeans.peek());
/*     */   }
/*     */ 
/*     */   
/*     */   public WriteBeanState pushBeanState(Object bean) {
/* 313 */     WriteBeanState newState = new WriteBeanState(bean);
/* 314 */     WriteBeanState prevState = this.beanState;
/* 315 */     this.beanState = newState;
/* 316 */     return prevState;
/*     */   }
/*     */ 
/*     */   
/* 320 */   public void pushPreviousState(WriteBeanState previousState) { this.beanState = previousState; }
/*     */ 
/*     */ 
/*     */   
/* 324 */   public boolean isReferenceBean() { return this.beanState.isReferenceBean(); }
/*     */ 
/*     */ 
/*     */   
/* 328 */   public boolean includedProp(String name) { return this.beanState.includedProp(name); }
/*     */ 
/*     */ 
/*     */   
/* 332 */   public Set<String> getLoadedProps() { return this.beanState.getLoadedProps(); }
/*     */ 
/*     */   
/*     */   public static class WriteBeanState
/*     */   {
/*     */     private final EntityBeanIntercept ebi;
/*     */     
/*     */     private final Set<String> loadedProps;
/*     */     private final boolean referenceBean;
/*     */     private boolean firstKeyOut;
/*     */     
/*     */     public WriteBeanState(Object bean) {
/* 344 */       if (bean instanceof EntityBean) {
/* 345 */         this.ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 346 */         this.loadedProps = this.ebi.getLoadedProps();
/* 347 */         this.referenceBean = this.ebi.isReference();
/*     */       } else {
/* 349 */         this.ebi = null;
/* 350 */         this.loadedProps = null;
/* 351 */         this.referenceBean = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 356 */     public Set<String> getLoadedProps() { return this.loadedProps; }
/*     */ 
/*     */     
/*     */     public boolean includedProp(String name) {
/* 360 */       if (this.loadedProps == null || this.loadedProps.contains(name)) {
/* 361 */         return true;
/*     */       }
/* 363 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 367 */     public boolean isReferenceBean() { return this.referenceBean; }
/*     */ 
/*     */     
/*     */     public boolean isFirstKey() {
/* 371 */       if (!this.firstKeyOut) {
/* 372 */         this.firstKeyOut = true;
/* 373 */         return true;
/*     */       } 
/* 375 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */