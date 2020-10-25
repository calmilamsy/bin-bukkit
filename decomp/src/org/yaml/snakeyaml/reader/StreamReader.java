/*     */ package org.yaml.snakeyaml.reader;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.error.Mark;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.scanner.Constant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamReader
/*     */ {
/*     */   private String name;
/*     */   private final Reader stream;
/*     */   private int pointer;
/*     */   private boolean eof;
/*  37 */   static final Pattern NON_PRINTABLE = Pattern.compile("[^\t\n\r -~ -퟿-￼]"); private final StringBuilder buffer; private int index; private int line;
/*     */   private int column;
/*     */   
/*     */   public StreamReader(String stream) {
/*  41 */     this.pointer = 0;
/*  42 */     this.eof = true;
/*     */     
/*  44 */     this.index = 0;
/*  45 */     this.line = 0;
/*  46 */     this.column = 0;
/*     */ 
/*     */     
/*  49 */     this.name = "<string>";
/*  50 */     this.buffer = new StringBuilder();
/*  51 */     checkPrintable(stream);
/*  52 */     this.buffer.append(stream);
/*  53 */     this.stream = null;
/*  54 */     this.eof = true; } public StreamReader(Reader reader) { this.pointer = 0; this.eof = true;
/*     */     this.index = 0;
/*     */     this.line = 0;
/*     */     this.column = 0;
/*  58 */     this.name = "<reader>";
/*  59 */     this.buffer = new StringBuilder();
/*  60 */     this.stream = reader;
/*  61 */     this.eof = false; }
/*     */ 
/*     */   
/*     */   void checkPrintable(CharSequence data) {
/*  65 */     Matcher em = NON_PRINTABLE.matcher(data);
/*  66 */     if (em.find()) {
/*  67 */       int position = this.index + this.buffer.length() - this.pointer + em.start();
/*  68 */       throw new ReaderException(this.name, position, em.group().charAt(0), "special characters are not allowed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  74 */   public Mark getMark() { return new Mark(this.name, this.index, this.line, this.column, this.buffer.toString(), this.pointer); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public void forward() { forward(1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forward(int length) {
/*  88 */     if (this.pointer + length + 1 >= this.buffer.length()) {
/*  89 */       update(length + 1);
/*     */     }
/*  91 */     char ch = Character.MIN_VALUE;
/*  92 */     for (int i = 0; i < length; i++) {
/*  93 */       ch = this.buffer.charAt(this.pointer);
/*  94 */       this.pointer++;
/*  95 */       this.index++;
/*  96 */       if (Constant.LINEBR.has(ch) || (ch == '\r' && this.buffer.charAt(this.pointer) != '\n')) {
/*  97 */         this.line++;
/*  98 */         this.column = 0;
/*  99 */       } else if (ch != '﻿') {
/* 100 */         this.column++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 106 */   public char peek() { return peek(0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char peek(int index) {
/* 116 */     if (this.pointer + index + 1 > this.buffer.length()) {
/* 117 */       update(index + 1);
/*     */     }
/* 119 */     return this.buffer.charAt(this.pointer + index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String prefix(int length) {
/* 129 */     if (this.pointer + length >= this.buffer.length()) {
/* 130 */       update(length);
/*     */     }
/* 132 */     if (this.pointer + length > this.buffer.length()) {
/* 133 */       return this.buffer.substring(this.pointer, this.buffer.length());
/*     */     }
/* 135 */     return this.buffer.substring(this.pointer, this.pointer + length);
/*     */   }
/*     */ 
/*     */   
/*     */   private void update(int length) {
/* 140 */     this.buffer.delete(0, this.pointer);
/* 141 */     this.pointer = 0;
/* 142 */     while (this.buffer.length() < length) {
/* 143 */       String rawData = null;
/* 144 */       if (!this.eof) {
/* 145 */         char[] data = new char[1024];
/* 146 */         int converted = -2;
/*     */         try {
/* 148 */           converted = this.stream.read(data);
/* 149 */         } catch (IOException ioe) {
/* 150 */           throw new YAMLException(ioe);
/*     */         } 
/* 152 */         if (converted == -1) {
/* 153 */           this.eof = true;
/*     */         } else {
/* 155 */           rawData = new String(data, false, converted);
/*     */         } 
/*     */       } 
/* 158 */       if (rawData != null) {
/* 159 */         checkPrintable(rawData);
/* 160 */         this.buffer.append(rawData);
/*     */       } 
/* 162 */       if (this.eof) {
/* 163 */         this.buffer.append(false);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 170 */   public int getColumn() { return this.column; }
/*     */ 
/*     */ 
/*     */   
/* 174 */   public Charset getEncoding() { return Charset.forName(((UnicodeReader)this.stream).getEncoding()); }
/*     */ 
/*     */ 
/*     */   
/* 178 */   public int getIndex() { return this.index; }
/*     */ 
/*     */ 
/*     */   
/* 182 */   public int getLine() { return this.line; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\reader\StreamReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */