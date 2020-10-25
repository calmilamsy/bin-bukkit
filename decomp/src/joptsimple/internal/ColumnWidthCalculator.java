/*    */ package joptsimple.internal;
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
/*    */ class ColumnWidthCalculator
/*    */ {
/*    */   int calculate(int totalWidth, int numberOfColumns) {
/* 34 */     if (numberOfColumns == 1) {
/* 35 */       return totalWidth;
/*    */     }
/* 37 */     int remainder = totalWidth % numberOfColumns;
/* 38 */     if (remainder == numberOfColumns - 1)
/* 39 */       return totalWidth / numberOfColumns; 
/* 40 */     return totalWidth / numberOfColumns - 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\ColumnWidthCalculator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */