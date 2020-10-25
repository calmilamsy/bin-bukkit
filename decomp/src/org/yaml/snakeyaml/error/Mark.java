/*     */ package org.yaml.snakeyaml.error;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Mark
/*     */ {
/*     */   private String name;
/*     */   private int line;
/*     */   private int column;
/*     */   private String buffer;
/*     */   private int pointer;
/*     */   
/*     */   public Mark(String name, int index, int line, int column, String buffer, int pointer) {
/*  36 */     this.name = name;
/*  37 */     this.line = line;
/*  38 */     this.column = column;
/*  39 */     this.buffer = buffer;
/*  40 */     this.pointer = pointer;
/*     */   }
/*     */ 
/*     */   
/*  44 */   private boolean isLineBreak(char ch) { return Constant.NULL_OR_LINEBR.has(ch); }
/*     */ 
/*     */   
/*     */   public String get_snippet(int indent, int max_length) {
/*  48 */     if (this.buffer == null) {
/*  49 */       return null;
/*     */     }
/*  51 */     float half = (max_length / 2 - 1);
/*  52 */     int start = this.pointer;
/*  53 */     String head = "";
/*  54 */     while (start > 0 && !isLineBreak(this.buffer.charAt(start - 1))) {
/*  55 */       start--;
/*  56 */       if ((this.pointer - start) > half) {
/*  57 */         head = " ... ";
/*  58 */         start += 5;
/*     */         break;
/*     */       } 
/*     */     } 
/*  62 */     String tail = "";
/*  63 */     int end = this.pointer;
/*  64 */     while (end < this.buffer.length() && !isLineBreak(this.buffer.charAt(end))) {
/*  65 */       end++;
/*  66 */       if ((end - this.pointer) > half) {
/*  67 */         tail = " ... ";
/*  68 */         end -= 5;
/*     */         break;
/*     */       } 
/*     */     } 
/*  72 */     String snippet = this.buffer.substring(start, end);
/*  73 */     StringBuilder result = new StringBuilder();
/*  74 */     for (i = 0; i < indent; i++) {
/*  75 */       result.append(" ");
/*     */     }
/*  77 */     result.append(head);
/*  78 */     result.append(snippet);
/*  79 */     result.append(tail);
/*  80 */     result.append("\n");
/*  81 */     for (int i = 0; i < indent + this.pointer - start + head.length(); i++) {
/*  82 */       result.append(" ");
/*     */     }
/*  84 */     result.append("^");
/*  85 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*  89 */   public String get_snippet() { return get_snippet(4, 75); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  94 */     String snippet = get_snippet();
/*  95 */     StringBuilder where = new StringBuilder(" in \"");
/*  96 */     where.append(this.name);
/*  97 */     where.append("\", line ");
/*  98 */     where.append(this.line + 1);
/*  99 */     where.append(", column ");
/* 100 */     where.append(this.column + 1);
/* 101 */     if (snippet != null) {
/* 102 */       where.append(":\n");
/* 103 */       where.append(snippet);
/*     */     } 
/* 105 */     return where.toString();
/*     */   }
/*     */ 
/*     */   
/* 109 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public int getLine() { return this.line; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public int getColumn() { return this.column; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\error\Mark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */