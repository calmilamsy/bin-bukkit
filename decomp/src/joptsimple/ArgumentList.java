/*    */ package joptsimple;
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
/*    */ class ArgumentList
/*    */ {
/*    */   private final String[] arguments;
/*    */   private int currentIndex;
/*    */   
/* 41 */   ArgumentList(String... arguments) { this.arguments = (String[])arguments.clone(); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   boolean hasMore() { return (this.currentIndex < this.arguments.length); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   String next() { return this.arguments[this.currentIndex++]; }
/*    */ 
/*    */ 
/*    */   
/* 53 */   String peek() { return this.arguments[this.currentIndex]; }
/*    */ 
/*    */   
/*    */   void treatNextAsLongOption() {
/* 57 */     if ('-' != this.arguments[this.currentIndex].charAt(0))
/* 58 */       this.arguments[this.currentIndex] = "--" + this.arguments[this.currentIndex]; 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\ArgumentList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */