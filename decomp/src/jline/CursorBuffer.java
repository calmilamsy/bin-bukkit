/*     */ package jline;
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
/*     */ public class CursorBuffer
/*     */ {
/*  16 */   public int cursor = 0;
/*     */   
/*  18 */   StringBuffer buffer = new StringBuffer();
/*     */   
/*     */   private boolean overtyping = false;
/*     */ 
/*     */   
/*  23 */   public int length() { return this.buffer.length(); }
/*     */ 
/*     */   
/*     */   public char current() {
/*  27 */     if (this.cursor <= 0) {
/*  28 */       return Character.MIN_VALUE;
/*     */     }
/*     */     
/*  31 */     return this.buffer.charAt(this.cursor - 1);
/*     */   }
/*     */   
/*     */   public boolean clearBuffer() {
/*  35 */     if (this.buffer.length() == 0) {
/*  36 */       return false;
/*     */     }
/*     */     
/*  39 */     this.buffer.delete(0, this.buffer.length());
/*  40 */     this.cursor = 0;
/*  41 */     return true;
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
/*     */   public void write(char c) {
/*  53 */     this.buffer.insert(this.cursor++, c);
/*  54 */     if (isOvertyping() && this.cursor < this.buffer.length()) {
/*  55 */       this.buffer.deleteCharAt(this.cursor);
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
/*     */   public void write(String str) {
/*  67 */     if (this.buffer.length() == 0) {
/*  68 */       this.buffer.append(str);
/*     */     } else {
/*  70 */       this.buffer.insert(this.cursor, str);
/*     */     } 
/*     */     
/*  73 */     this.cursor += str.length();
/*     */     
/*  75 */     if (isOvertyping() && this.cursor < this.buffer.length()) {
/*  76 */       this.buffer.delete(this.cursor, this.cursor + str.length());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  81 */   public String toString() { return this.buffer.toString(); }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isOvertyping() { return this.overtyping; }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public void setOvertyping(boolean b) { this.overtyping = b; }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public StringBuffer getBuffer() { return this.buffer; }
/*     */ 
/*     */   
/*     */   public void setBuffer(StringBuffer buffer) {
/*  97 */     buffer.setLength(0);
/*  98 */     buffer.append(this.buffer.toString());
/*     */     
/* 100 */     this.buffer = buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\CursorBuffer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */