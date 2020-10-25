/*     */ package jline;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class History {
/*  18 */   private List history = new ArrayList();
/*     */   
/*  20 */   private PrintWriter output = null;
/*     */   
/*  22 */   private int maxSize = 500;
/*     */   
/*  24 */   private int currentIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public History() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public History(File historyFile) throws IOException { setHistoryFile(historyFile); }
/*     */ 
/*     */   
/*     */   public void setHistoryFile(File historyFile) throws IOException {
/*  41 */     if (historyFile.isFile()) {
/*  42 */       load(new FileInputStream(historyFile));
/*     */     }
/*     */     
/*  45 */     setOutput(new PrintWriter(new FileWriter(historyFile), true));
/*  46 */     flushBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public void load(InputStream in) throws IOException { load(new InputStreamReader(in)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(Reader reader) throws IOException {
/*  60 */     BufferedReader breader = new BufferedReader(reader);
/*  61 */     List lines = new ArrayList();
/*     */     
/*     */     String line;
/*  64 */     while ((line = breader.readLine()) != null) {
/*  65 */       lines.add(line);
/*     */     }
/*     */     
/*  68 */     for (Iterator i = lines.iterator(); i.hasNext();) {
/*  69 */       addToHistory((String)i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  74 */   public int size() { return this.history.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  81 */     this.history.clear();
/*  82 */     this.currentIndex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToHistory(String buffer) {
/*  91 */     if (this.history.size() != 0 && buffer.equals(this.history.get(this.history.size() - 1))) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     this.history.add(buffer);
/*     */     
/*  98 */     while (this.history.size() > getMaxSize()) {
/*  99 */       this.history.remove(0);
/*     */     }
/*     */     
/* 102 */     this.currentIndex = this.history.size();
/*     */     
/* 104 */     if (getOutput() != null) {
/* 105 */       getOutput().println(buffer);
/* 106 */       getOutput().flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushBuffer() {
/* 114 */     if (getOutput() != null) {
/* 115 */       for (Iterator i = this.history.iterator(); i.hasNext(); getOutput().println((String)i.next()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       getOutput().flush();
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
/*     */   public boolean moveToLastEntry() {
/* 132 */     int lastEntry = this.history.size() - 1;
/* 133 */     if (lastEntry >= 0 && lastEntry != this.currentIndex) {
/* 134 */       this.currentIndex = this.history.size() - 1;
/* 135 */       return true;
/*     */     } 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public void moveToEnd() { this.currentIndex = this.history.size(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public int getMaxSize() { return this.maxSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public void setOutput(PrintWriter output) { this.output = output; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public PrintWriter getOutput() { return this.output; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public int getCurrentIndex() { return this.currentIndex; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String current() {
/* 189 */     if (this.currentIndex >= this.history.size()) {
/* 190 */       return "";
/*     */     }
/*     */     
/* 193 */     return (String)this.history.get(this.currentIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean previous() {
/* 202 */     if (this.currentIndex <= 0) {
/* 203 */       return false;
/*     */     }
/*     */     
/* 206 */     this.currentIndex--;
/*     */     
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next() {
/* 217 */     if (this.currentIndex >= this.history.size()) {
/* 218 */       return false;
/*     */     }
/*     */     
/* 221 */     this.currentIndex++;
/*     */     
/* 223 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public List getHistoryList() { return Collections.unmodifiableList(this.history); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public String toString() { return this.history.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveToFirstEntry() {
/* 248 */     if (this.history.size() > 0 && this.currentIndex != 0) {
/* 249 */       this.currentIndex = 0;
/* 250 */       return true;
/*     */     } 
/*     */     
/* 253 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\History.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */