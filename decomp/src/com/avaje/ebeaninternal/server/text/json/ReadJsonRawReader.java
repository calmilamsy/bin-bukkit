/*     */ package com.avaje.ebeaninternal.server.text.json;
/*     */ 
/*     */ import com.avaje.ebean.text.json.JsonElement;
/*     */ import com.avaje.ebean.text.json.JsonElementArray;
/*     */ import com.avaje.ebean.text.json.JsonElementBoolean;
/*     */ import com.avaje.ebean.text.json.JsonElementNull;
/*     */ import com.avaje.ebean.text.json.JsonElementNumber;
/*     */ import com.avaje.ebean.text.json.JsonElementObject;
/*     */ import com.avaje.ebean.text.json.JsonElementString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadJsonRawReader
/*     */ {
/*     */   private final ReadJsonContext ctx;
/*     */   
/*  37 */   public ReadJsonRawReader(ReadJsonContext ctx) { this.ctx = ctx; }
/*     */ 
/*     */ 
/*     */   
/*  41 */   public JsonElement readUnknownValue() { return readValue(); }
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonElement readValue() {
/*  46 */     this.ctx.ignoreWhiteSpace();
/*     */     
/*  48 */     char c = this.ctx.nextChar();
/*     */     
/*  50 */     switch (c) {
/*     */       case '{':
/*  52 */         return readObject();
/*     */       
/*     */       case '[':
/*  55 */         return readArray();
/*     */       
/*     */       case '"':
/*  58 */         return readString();
/*     */     } 
/*     */     
/*  61 */     return readUnquoted(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonElement readArray() {
/*  67 */     JsonElementArray a = new JsonElementArray();
/*     */     
/*     */     do {
/*  70 */       JsonElement value = readValue();
/*  71 */       a.add(value);
/*  72 */     } while (this.ctx.readArrayNext());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonElement readObject() {
/*  82 */     JsonElementObject o = new JsonElementObject();
/*     */ 
/*     */     
/*  85 */     while (this.ctx.readKeyNext()) {
/*     */ 
/*     */ 
/*     */       
/*  89 */       String key = this.ctx.getTokenKey();
/*  90 */       JsonElement value = readValue();
/*     */       
/*  92 */       o.put(key, value);
/*     */       
/*  94 */       if (!this.ctx.readValueNext()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return o;
/*     */   }
/*     */   
/*     */   private JsonElement readString() {
/* 104 */     String s = this.ctx.readQuotedValue();
/* 105 */     return new JsonElementString(s);
/*     */   }
/*     */   
/*     */   private JsonElement readUnquoted(char c) {
/* 109 */     String s = this.ctx.readUnquotedValue(c);
/* 110 */     if ("null".equals(s)) {
/* 111 */       return JsonElementNull.NULL;
/*     */     }
/* 113 */     if ("true".equals(s)) {
/* 114 */       return JsonElementBoolean.TRUE;
/*     */     }
/* 116 */     if ("false".equals(s)) {
/* 117 */       return JsonElementBoolean.FALSE;
/*     */     }
/*     */     
/* 120 */     return new JsonElementNumber(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonRawReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */