/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
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
/*     */ public abstract class DeployParser
/*     */ {
/*     */   protected static final char SINGLE_QUOTE = '\'';
/*     */   protected static final char COLON = ':';
/*     */   protected static final char UNDERSCORE = '_';
/*     */   protected static final char PERIOD = '.';
/*     */   protected boolean encrypted;
/*     */   protected String source;
/*     */   protected StringBuilder sb;
/*     */   protected int sourceLength;
/*     */   protected int pos;
/*     */   protected String word;
/*     */   protected char wordTerminator;
/*     */   
/*     */   protected abstract String convertWord();
/*     */   
/*     */   public abstract String getDeployWord(String paramString);
/*     */   
/*     */   public abstract Set<String> getIncludes();
/*     */   
/*  55 */   public void setEncrypted(boolean encrytped) { this.encrypted = encrytped; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String parse(String source) {
/*  60 */     if (source == null) {
/*  61 */       return source;
/*     */     }
/*     */     
/*  64 */     this.pos = -1;
/*  65 */     this.source = source;
/*  66 */     this.sourceLength = source.length();
/*  67 */     this.sb = new StringBuilder(source.length() + 20);
/*     */     
/*  69 */     while (nextWord()) {
/*  70 */       String deployWord = convertWord();
/*  71 */       this.sb.append(deployWord);
/*  72 */       if (this.pos < this.sourceLength) {
/*  73 */         this.sb.append(this.wordTerminator);
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return this.sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean nextWord() {
/*  82 */     if (!findWordStart()) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     StringBuilder wordBuffer = new StringBuilder();
/*  87 */     wordBuffer.append(this.source.charAt(this.pos));
/*  88 */     while (++this.pos < this.sourceLength) {
/*  89 */       char ch = this.source.charAt(this.pos);
/*  90 */       if (isWordPart(ch)) {
/*  91 */         wordBuffer.append(ch); continue;
/*     */       } 
/*  93 */       this.wordTerminator = ch;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.word = wordBuffer.toString();
/*     */     
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   private boolean findWordStart() {
/* 104 */     while (++this.pos < this.sourceLength) {
/* 105 */       char ch = this.source.charAt(this.pos);
/* 106 */       if (ch == '\'') {
/*     */ 
/*     */         
/* 109 */         this.sb.append(ch);
/* 110 */         readLiteral(); continue;
/* 111 */       }  if (ch == ':') {
/*     */ 
/*     */         
/* 114 */         this.sb.append(ch);
/* 115 */         readNamedParameter(); continue;
/* 116 */       }  if (isWordStart(ch))
/*     */       {
/*     */         
/* 119 */         return true;
/*     */       }
/* 121 */       this.sb.append(ch);
/*     */     } 
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readLiteral() {
/* 132 */     while (++this.pos < this.sourceLength) {
/* 133 */       char ch = this.source.charAt(this.pos);
/* 134 */       this.sb.append(ch);
/* 135 */       if (ch == '\'') {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readNamedParameter() {
/* 146 */     while (++this.pos < this.sourceLength) {
/* 147 */       char ch = this.source.charAt(this.pos);
/* 148 */       this.sb.append(ch);
/* 149 */       if (Character.isWhitespace(ch))
/*     */         break; 
/* 151 */       if (ch == ',') {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWordPart(char ch) {
/* 161 */     if (Character.isLetterOrDigit(ch)) {
/* 162 */       return true;
/*     */     }
/* 164 */     if (ch == '_') {
/* 165 */       return true;
/*     */     }
/* 167 */     if (ch == '.') {
/* 168 */       return true;
/*     */     }
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWordStart(char ch) {
/* 176 */     if (Character.isLetter(ch)) {
/* 177 */       return true;
/*     */     }
/*     */     
/* 180 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DeployParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */