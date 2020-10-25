/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleTextParser
/*     */ {
/*     */   private final String oql;
/*     */   private final char[] chars;
/*     */   private final int eof;
/*     */   private int pos;
/*     */   private String word;
/*     */   private String lowerWord;
/*     */   private int openParenthesisCount;
/*     */   
/*     */   public SimpleTextParser(String oql) {
/*  16 */     this.oql = oql;
/*  17 */     this.chars = oql.toCharArray();
/*  18 */     this.eof = oql.length();
/*     */   }
/*     */ 
/*     */   
/*  22 */   public String getOql() { return this.oql; }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public String getWord() { return this.word; }
/*     */ 
/*     */   
/*     */   public String peekNextWord() {
/*  30 */     int origPos = this.pos;
/*  31 */     String nw = nextWordInternal();
/*  32 */     this.pos = origPos;
/*  33 */     return nw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatch(String lowerMatch, String nextWordMatch) {
/*  41 */     if (isMatch(lowerMatch)) {
/*  42 */       String nw = peekNextWord();
/*  43 */       if (nw != null) {
/*  44 */         nw = nw.toLowerCase();
/*  45 */         return nw.equals(nextWordMatch);
/*     */       } 
/*     */     } 
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*  52 */   public boolean isFinished() { return (this.word == null); }
/*     */ 
/*     */   
/*     */   public int findWordLower(String lowerMatch, int afterPos) {
/*  56 */     this.pos = afterPos;
/*  57 */     return findWordLower(lowerMatch);
/*     */   }
/*     */   
/*     */   public int findWordLower(String lowerMatch) {
/*     */     do {
/*  62 */       if (nextWord() == null) {
/*  63 */         return -1;
/*     */       }
/*  65 */     } while (!lowerMatch.equals(this.lowerWord));
/*  66 */     return this.pos - this.lowerWord.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean isMatch(String lowerMatch) { return lowerMatch.equals(this.lowerWord); }
/*     */ 
/*     */   
/*     */   public String nextWord() {
/*  79 */     this.word = nextWordInternal();
/*  80 */     if (this.word != null) {
/*  81 */       this.lowerWord = this.word.toLowerCase();
/*     */     }
/*  83 */     return this.word;
/*     */   }
/*     */   
/*     */   private String nextWordInternal() {
/*  87 */     trimLeadingWhitespace();
/*  88 */     if (this.pos >= this.eof) {
/*  89 */       return null;
/*     */     }
/*  91 */     int start = this.pos;
/*  92 */     if (this.chars[this.pos] == '(') {
/*  93 */       moveToClose();
/*     */     } else {
/*  95 */       moveToEndOfWord();
/*     */     } 
/*  97 */     return this.oql.substring(start, this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveToClose() {
/* 102 */     this.pos++;
/* 103 */     this.openParenthesisCount = 0;
/*     */     
/* 105 */     for (; this.pos < this.eof; this.pos++) {
/* 106 */       char c = this.chars[this.pos];
/* 107 */       if (c == '(') {
/*     */         
/* 109 */         this.openParenthesisCount++;
/*     */       }
/* 111 */       else if (c == ')') {
/* 112 */         if (this.openParenthesisCount > 0) {
/*     */           
/* 114 */           this.openParenthesisCount--;
/*     */         } else {
/*     */           
/* 117 */           this.pos++;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveToEndOfWord() {
/* 125 */     char c = this.chars[this.pos];
/* 126 */     boolean isOperator = isOperator(c);
/* 127 */     for (; this.pos < this.eof; this.pos++) {
/* 128 */       c = this.chars[this.pos];
/* 129 */       if (isWordTerminator(c, isOperator)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWordTerminator(char c, boolean isOperator) {
/* 136 */     if (Character.isWhitespace(c)) {
/* 137 */       return true;
/*     */     }
/* 139 */     if (isOperator(c)) {
/* 140 */       return !isOperator;
/*     */     }
/* 142 */     if (c == '(') {
/* 143 */       return true;
/*     */     }
/*     */     
/* 146 */     return isOperator;
/*     */   }
/*     */   
/*     */   private boolean isOperator(char c) {
/* 150 */     switch (c) {
/*     */       case '<':
/* 152 */         return true;
/*     */       case '>':
/* 154 */         return true;
/*     */       case '=':
/* 156 */         return true;
/*     */       case '!':
/* 158 */         return true;
/*     */     } 
/*     */     
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void trimLeadingWhitespace() {
/* 166 */     for (; this.pos < this.eof; this.pos++) {
/* 167 */       char c = this.chars[this.pos];
/* 168 */       if (!Character.isWhitespace(c))
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\querydefn\SimpleTextParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */