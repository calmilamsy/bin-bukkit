/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonReadBeanVisitor;
/*     */ import com.avaje.ebean.text.json.JsonReadOptions;
/*     */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.util.ArrayStack;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class ReadJsonContext
/*     */ {
/*     */   private final ReadJsonSource src;
/*     */   private final Map<String, JsonReadBeanVisitor<?>> visitorMap;
/*     */   private final JsonValueAdapter valueAdapter;
/*     */   private final PathStack pathStack;
/*     */   private final ArrayStack<ReadBeanState> beanState;
/*     */   private ReadBeanState currentState;
/*     */   private char tokenStart;
/*     */   private String tokenKey;
/*     */   
/*     */   public ReadJsonContext(ReadJsonSource src, JsonValueAdapter dfltValueAdapter, JsonReadOptions options) {
/*  58 */     this.src = src;
/*  59 */     this.beanState = new ArrayStack();
/*  60 */     if (options == null) {
/*  61 */       this.valueAdapter = dfltValueAdapter;
/*  62 */       this.visitorMap = null;
/*  63 */       this.pathStack = null;
/*     */     } else {
/*  65 */       this.valueAdapter = getValueAdapter(dfltValueAdapter, options.getValueAdapter());
/*  66 */       this.visitorMap = options.getVisitorMap();
/*  67 */       this.pathStack = (this.visitorMap == null || this.visitorMap.isEmpty()) ? null : new PathStack();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  72 */   private JsonValueAdapter getValueAdapter(JsonValueAdapter dfltValueAdapter, JsonValueAdapter valueAdapter) { return (valueAdapter == null) ? dfltValueAdapter : valueAdapter; }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public JsonValueAdapter getValueAdapter() { return this.valueAdapter; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public char getToken() { return this.tokenStart; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public String getTokenKey() { return this.tokenKey; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean isTokenKey() { return ('"' == this.tokenStart); }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public boolean isTokenObjectEnd() { return ('}' == this.tokenStart); }
/*     */ 
/*     */   
/*     */   public boolean readObjectBegin() {
/*  96 */     readNextToken();
/*  97 */     if ('{' == this.tokenStart)
/*  98 */       return true; 
/*  99 */     if ('n' == this.tokenStart)
/* 100 */       return false; 
/* 101 */     if (']' == this.tokenStart)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     throw new RuntimeException("Expected object begin at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readKeyNext() {
/* 109 */     readNextToken();
/* 110 */     if ('"' == this.tokenStart)
/* 111 */       return true; 
/* 112 */     if ('}' == this.tokenStart) {
/* 113 */       return false;
/*     */     }
/* 115 */     throw new RuntimeException("Expected '\"' or '}' at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readValueNext() {
/* 119 */     readNextToken();
/* 120 */     if (',' == this.tokenStart)
/* 121 */       return true; 
/* 122 */     if ('}' == this.tokenStart) {
/* 123 */       return false;
/*     */     }
/* 125 */     throw new RuntimeException("Expected ',' or '}' at " + this.src.getErrorHelp() + " but got " + this.tokenStart);
/*     */   }
/*     */   
/*     */   public boolean readArrayBegin() {
/* 129 */     readNextToken();
/* 130 */     if ('[' == this.tokenStart)
/* 131 */       return true; 
/* 132 */     if ('n' == this.tokenStart) {
/* 133 */       return false;
/*     */     }
/* 135 */     throw new RuntimeException("Expected array begin at " + this.src.getErrorHelp());
/*     */   }
/*     */   
/*     */   public boolean readArrayNext() {
/* 139 */     readNextToken();
/* 140 */     if (',' == this.tokenStart) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (']' == this.tokenStart) {
/* 144 */       return false;
/*     */     }
/* 146 */     throw new RuntimeException("Expected ',' or ']' at " + this.src.getErrorHelp());
/*     */   }
/*     */ 
/*     */   
/*     */   public String readScalarValue() {
/* 151 */     ignoreWhiteSpace();
/*     */     
/* 153 */     char prevChar = this.src.nextChar("EOF reading scalarValue?");
/* 154 */     if ('"' == prevChar) {
/* 155 */       return readQuotedValue();
/*     */     }
/* 157 */     return readUnquotedValue(prevChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readNextToken() {
/* 165 */     ignoreWhiteSpace();
/*     */     
/* 167 */     this.tokenStart = this.src.nextChar("EOF finding next token");
/* 168 */     switch (this.tokenStart) {
/*     */       case '"':
/* 170 */         internalReadKey(); break;
/*     */       case '{':
/*     */       case '}':
/*     */       case '[':
/*     */       case ']':
/*     */       case ',':
/*     */       case ':':
/*     */         break;
/*     */       case 'n':
/* 179 */         internalReadNull();
/*     */         break;
/*     */       
/*     */       default:
/* 183 */         throw new RuntimeException("Unexpected tokenStart[" + this.tokenStart + "] " + this.src.getErrorHelp());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String readQuotedValue() {
/* 191 */     boolean escape = false;
/* 192 */     StringBuilder sb = new StringBuilder();
/*     */     
/*     */     while (true) {
/* 195 */       char ch = this.src.nextChar("EOF reading quoted value");
/* 196 */       if (escape) {
/*     */         
/* 198 */         sb.append(ch);
/*     */         continue;
/*     */       } 
/* 201 */       switch (ch) {
/*     */         
/*     */         case '\\':
/* 204 */           escape = true;
/*     */           continue;
/*     */         case '"':
/* 207 */           return sb.toString();
/*     */       } 
/*     */       
/* 210 */       sb.append(ch);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String readUnquotedValue(char c) {
/* 217 */     String v = readUnquotedValueRaw(c);
/* 218 */     if ("null".equals(v)) {
/* 219 */       return null;
/*     */     }
/* 221 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String readUnquotedValueRaw(char c) {
/* 227 */     StringBuilder sb = new StringBuilder();
/* 228 */     sb.append(c);
/*     */     
/*     */     while (true) {
/* 231 */       this.tokenStart = this.src.nextChar("EOF reading unquoted value");
/* 232 */       switch (this.tokenStart) {
/*     */         case ',':
/* 234 */           this.src.back();
/* 235 */           return sb.toString();
/*     */         
/*     */         case '}':
/* 238 */           this.src.back();
/* 239 */           return sb.toString();
/*     */         
/*     */         case ' ':
/* 242 */           return sb.toString();
/*     */         
/*     */         case '\t':
/* 245 */           return sb.toString();
/*     */         
/*     */         case '\r':
/* 248 */           return sb.toString();
/*     */         
/*     */         case '\n':
/* 251 */           return sb.toString();
/*     */       } 
/*     */       
/* 254 */       sb.append(this.tokenStart);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void internalReadNull() {
/* 263 */     StringBuilder sb = new StringBuilder(4);
/* 264 */     sb.append(this.tokenStart);
/* 265 */     for (int i = 0; i < 3; i++) {
/* 266 */       char c = this.src.nextChar("EOF reading null ");
/* 267 */       sb.append(c);
/*     */     } 
/* 269 */     if (!"null".equals(sb.toString())) {
/* 270 */       throw new TextException("Expected 'null' but got " + sb.toString() + " " + this.src.getErrorHelp());
/*     */     }
/*     */   }
/*     */   
/*     */   private void internalReadKey() {
/* 275 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 277 */       char c = this.src.nextChar("EOF reading key");
/* 278 */       if ('"' == c) {
/* 279 */         this.tokenKey = sb.toString();
/*     */         break;
/*     */       } 
/* 282 */       sb.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 286 */     ignoreWhiteSpace();
/*     */     
/* 288 */     char c = this.src.nextChar("EOF reading ':'");
/* 289 */     if (':' != c) {
/* 290 */       throw new TextException("Expected to find colon after key at " + (this.src.pos() - 1) + " but found [" + c + "]" + this.src.getErrorHelp());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 295 */   protected void ignoreWhiteSpace() { this.src.ignoreWhiteSpace(); }
/*     */ 
/*     */   
/*     */   public void pushBean(Object bean, String path, BeanDescriptor<?> beanDescriptor) {
/* 299 */     this.currentState = new ReadBeanState(bean, beanDescriptor, null);
/* 300 */     this.beanState.push(this.currentState);
/* 301 */     if (this.pathStack != null) {
/* 302 */       this.pathStack.pushPathKey(path);
/*     */     }
/*     */   }
/*     */   
/*     */   public ReadBeanState popBeanState() {
/* 307 */     if (this.pathStack != null) {
/* 308 */       String path = (String)this.pathStack.peekWithNull();
/* 309 */       JsonReadBeanVisitor<?> beanVisitor = (JsonReadBeanVisitor)this.visitorMap.get(path);
/* 310 */       if (beanVisitor != null) {
/* 311 */         this.currentState.visit(beanVisitor);
/*     */       }
/* 313 */       this.pathStack.pop();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     ReadBeanState s = this.currentState;
/*     */     
/* 321 */     this.beanState.pop();
/* 322 */     this.currentState = (ReadBeanState)this.beanState.peekWithNull();
/* 323 */     return s;
/*     */   }
/*     */ 
/*     */   
/* 327 */   public void setProperty(String propertyName) { this.currentState.setLoaded(propertyName); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement readUnmappedJson(String key) {
/* 339 */     ReadJsonRawReader rawReader = new ReadJsonRawReader(this);
/* 340 */     JsonElement rawJsonValue = rawReader.readUnknownValue();
/* 341 */     if (this.visitorMap != null) {
/* 342 */       this.currentState.addUnmappedJson(key, rawJsonValue);
/*     */     }
/* 344 */     return rawJsonValue;
/*     */   }
/*     */   
/*     */   protected char nextChar() {
/* 348 */     this.tokenStart = this.src.nextChar("EOF getting nextChar for raw json");
/* 349 */     return this.tokenStart;
/*     */   }
/*     */   
/*     */   public static class ReadBeanState
/*     */     implements PropertyChangeListener {
/*     */     private final Object bean;
/*     */     private final BeanDescriptor<?> beanDescriptor;
/*     */     private final EntityBeanIntercept ebi;
/*     */     private final Set<String> loadedProps;
/*     */     private Map<String, JsonElement> unmapped;
/*     */     
/*     */     private ReadBeanState(Object bean, BeanDescriptor<?> beanDescriptor) {
/* 361 */       this.bean = bean;
/* 362 */       this.beanDescriptor = beanDescriptor;
/* 363 */       if (bean instanceof EntityBean) {
/* 364 */         this.ebi = ((EntityBean)bean)._ebean_getIntercept();
/* 365 */         this.loadedProps = new HashSet();
/*     */       } else {
/* 367 */         this.ebi = null;
/* 368 */         this.loadedProps = null;
/*     */       } 
/*     */     }
/*     */     
/* 372 */     public String toString() { return this.bean.getClass().getSimpleName() + " loaded:" + this.loadedProps; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLoaded(String propertyName) {
/* 379 */       if (this.ebi != null) {
/* 380 */         this.loadedProps.add(propertyName);
/*     */       }
/*     */     }
/*     */     
/*     */     private void addUnmappedJson(String key, JsonElement value) {
/* 385 */       if (this.unmapped == null) {
/* 386 */         this.unmapped = new LinkedHashMap();
/*     */       }
/* 388 */       this.unmapped.put(key, value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private <T> void visit(JsonReadBeanVisitor<T> beanVisitor) {
/* 395 */       if (this.ebi != null) {
/* 396 */         this.ebi.addPropertyChangeListener(this);
/*     */       }
/* 398 */       beanVisitor.visit(this.bean, this.unmapped);
/* 399 */       if (this.ebi != null) {
/* 400 */         this.ebi.removePropertyChangeListener(this);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setLoadedState() {
/* 405 */       if (this.ebi != null)
/*     */       {
/* 407 */         this.beanDescriptor.setLoadedProps(this.ebi, this.loadedProps);
/*     */       }
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/* 412 */       String propName = evt.getPropertyName();
/* 413 */       this.loadedProps.add(propName);
/*     */     }
/*     */ 
/*     */     
/* 417 */     public Object getBean() { return this.bean; }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */