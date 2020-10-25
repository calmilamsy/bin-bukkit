/*     */ package com.avaje.ebeaninternal.server.text.csv;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CsvUtilReader
/*     */ {
/*     */   private BufferedReader br;
/*     */   private boolean hasNext;
/*     */   private char separator;
/*     */   private char quotechar;
/*     */   private int skipLines;
/*     */   private boolean linesSkiped;
/*     */   public static final char DEFAULT_SEPARATOR = ',';
/*     */   public static final char DEFAULT_QUOTE_CHARACTER = '"';
/*     */   public static final int DEFAULT_SKIP_LINES = 0;
/*     */   
/*  69 */   public CsvUtilReader(Reader reader) { this(reader, ','); }
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
/*  81 */   public CsvUtilReader(Reader reader, char separator) { this(reader, separator, '"'); }
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
/*  97 */   public CsvUtilReader(Reader reader, char separator, char quotechar) { this(reader, separator, quotechar, 0); }
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
/*     */   public CsvUtilReader(Reader reader, char separator, char quotechar, int line) {
/*     */     this.hasNext = true;
/* 113 */     this.br = new BufferedReader(reader);
/* 114 */     this.separator = separator;
/* 115 */     this.quotechar = quotechar;
/* 116 */     this.skipLines = line;
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
/*     */   public List<String[]> readAll() throws IOException {
/* 131 */     List<String[]> allElements = new ArrayList<String[]>();
/* 132 */     while (this.hasNext) {
/* 133 */       String[] nextLineAsTokens = readNext();
/* 134 */       if (nextLineAsTokens != null)
/* 135 */         allElements.add(nextLineAsTokens); 
/*     */     } 
/* 137 */     return allElements;
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
/*     */   public String[] readNext() throws IOException {
/* 152 */     String nextLine = getNextLine();
/* 153 */     return this.hasNext ? parseLine(nextLine) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNextLine() throws IOException {
/* 164 */     if (!this.linesSkiped) {
/* 165 */       for (int i = 0; i < this.skipLines; i++) {
/* 166 */         this.br.readLine();
/*     */       }
/* 168 */       this.linesSkiped = true;
/*     */     } 
/* 170 */     String nextLine = this.br.readLine();
/* 171 */     if (nextLine == null) {
/* 172 */       this.hasNext = false;
/*     */     }
/* 174 */     return this.hasNext ? nextLine : null;
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
/*     */   private String[] parseLine(String nextLine) throws IOException {
/* 187 */     if (nextLine == null) {
/* 188 */       return null;
/*     */     }
/*     */     
/* 191 */     List<String> tokensOnThisLine = new ArrayList<String>();
/*     */     
/* 193 */     StringBuilder sb = new StringBuilder();
/* 194 */     boolean inQuotes = false;
/*     */     do {
/* 196 */       if (inQuotes) {
/*     */         
/* 198 */         sb.append("\n");
/* 199 */         nextLine = getNextLine();
/* 200 */         if (nextLine == null)
/*     */           break; 
/*     */       } 
/* 203 */       for (int i = 0; i < nextLine.length(); i++) {
/*     */         
/* 205 */         char c = nextLine.charAt(i);
/* 206 */         if (c == this.quotechar) {
/*     */ 
/*     */           
/* 209 */           if (inQuotes && nextLine.length() > i + 1 && nextLine.charAt(i + 1) == this.quotechar) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 214 */             sb.append(nextLine.charAt(i + 1));
/* 215 */             i++;
/*     */           } else {
/* 217 */             inQuotes = !inQuotes;
/*     */             
/* 219 */             if (i > 2 && nextLine.charAt(i - 1) != this.separator && nextLine.length() > i + 1 && nextLine.charAt(i + 1) != this.separator)
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 224 */               sb.append(c);
/*     */             }
/*     */           } 
/* 227 */         } else if (c == this.separator && !inQuotes) {
/* 228 */           tokensOnThisLine.add(sb.toString().trim());
/* 229 */           sb = new StringBuilder();
/*     */         } else {
/* 231 */           sb.append(c);
/*     */         } 
/*     */       } 
/* 234 */     } while (inQuotes);
/* 235 */     tokensOnThisLine.add(sb.toString().trim());
/* 236 */     return (String[])tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public void close() throws IOException { this.br.close(); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\text\csv\CsvUtilReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */