/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.text.BreakIterator;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class Column
/*     */ {
/*  43 */   static final Comparator<Column> BY_HEIGHT = new Comparator<Column>() {
/*     */       public int compare(Column first, Column second) {
/*  45 */         if (first.height() < second.height())
/*  46 */           return -1; 
/*  47 */         return (first.height() == second.height()) ? 0 : 1;
/*     */       }
/*     */     };
/*     */   
/*     */   private final String header;
/*     */   private final List<String> data;
/*     */   private final int width;
/*     */   private int height;
/*     */   
/*     */   Column(String header, int width) {
/*  57 */     this.header = header;
/*  58 */     this.width = Math.max(width, header.length());
/*  59 */     this.data = new LinkedList();
/*  60 */     this.height = 0;
/*     */   }
/*     */   
/*     */   int addCells(Object cellCandidate) {
/*  64 */     int originalHeight = this.height;
/*     */     
/*  66 */     String source = String.valueOf(cellCandidate).trim();
/*  67 */     for (String eachPiece : source.split(System.getProperty("line.separator"))) {
/*  68 */       processNextEmbeddedLine(eachPiece);
/*     */     }
/*  70 */     return this.height - originalHeight;
/*     */   }
/*     */   
/*     */   private void processNextEmbeddedLine(String line) {
/*  74 */     BreakIterator words = BreakIterator.getLineInstance(Locale.US);
/*  75 */     words.setText(line);
/*     */     
/*  77 */     StringBuilder nextCell = new StringBuilder();
/*     */     
/*  79 */     int start = words.first(); int end;
/*  80 */     for (end = words.next(); end != -1; start = end, end = words.next()) {
/*  81 */       nextCell = processNextWord(line, nextCell, start, end);
/*     */     }
/*  83 */     if (nextCell.length() > 0)
/*  84 */       addCell(nextCell.toString()); 
/*     */   }
/*     */   
/*     */   private StringBuilder processNextWord(String source, StringBuilder nextCell, int start, int end) {
/*  88 */     StringBuilder augmented = nextCell;
/*     */     
/*  90 */     String word = source.substring(start, end);
/*  91 */     if (augmented.length() + word.length() > this.width) {
/*  92 */       addCell(augmented.toString());
/*  93 */       augmented = (new StringBuilder("  ")).append(word);
/*     */     } else {
/*     */       
/*  96 */       augmented.append(word);
/*     */     } 
/*  98 */     return augmented;
/*     */   }
/*     */   
/*     */   void addCell(String newCell) {
/* 102 */     this.data.add(newCell);
/* 103 */     this.height++;
/*     */   }
/*     */   
/*     */   void writeHeaderOn(StringBuilder buffer, boolean appendSpace) {
/* 107 */     buffer.append(this.header).append(Strings.repeat(' ', this.width - this.header.length()));
/*     */     
/* 109 */     if (appendSpace)
/* 110 */       buffer.append(' '); 
/*     */   }
/*     */   
/*     */   void writeSeparatorOn(StringBuilder buffer, boolean appendSpace) {
/* 114 */     buffer.append(Strings.repeat('-', this.header.length())).append(Strings.repeat(' ', this.width - this.header.length()));
/* 115 */     if (appendSpace)
/* 116 */       buffer.append(' '); 
/*     */   }
/*     */   
/*     */   void writeCellOn(int index, StringBuilder buffer, boolean appendSpace) {
/* 120 */     if (index < this.data.size()) {
/* 121 */       String item = (String)this.data.get(index);
/*     */       
/* 123 */       buffer.append(item).append(Strings.repeat(' ', this.width - item.length()));
/* 124 */       if (appendSpace) {
/* 125 */         buffer.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 130 */   int height() { return this.height; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\Column.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */