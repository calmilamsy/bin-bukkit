/*    */ package org.yaml.snakeyaml.scanner;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SimpleKey
/*    */ {
/*    */   private int tokenNumber;
/*    */   private boolean required;
/*    */   private int index;
/*    */   private int line;
/*    */   private int column;
/*    */   private Mark mark;
/*    */   
/*    */   public SimpleKey(int tokenNumber, boolean required, int index, int line, int column, Mark mark) {
/* 38 */     this.tokenNumber = tokenNumber;
/* 39 */     this.required = required;
/* 40 */     this.index = index;
/* 41 */     this.line = line;
/* 42 */     this.column = column;
/* 43 */     this.mark = mark;
/*    */   }
/*    */ 
/*    */   
/* 47 */   public int getTokenNumber() { return this.tokenNumber; }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public int getColumn() { return this.column; }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public Mark getMark() { return this.mark; }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public int getIndex() { return this.index; }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public int getLine() { return this.line; }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public boolean isRequired() { return this.required; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String toString() { return "SimpleKey - tokenNumber=" + this.tokenNumber + " required=" + this.required + " index=" + this.index + " line=" + this.line + " column=" + this.column; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\scanner\SimpleKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */