/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.ResultSet;
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
/*    */ public class RsetDataReaderIndexed
/*    */   extends RsetDataReader
/*    */ {
/*    */   private final int[] rsetIndexPositions;
/*    */   
/*    */   public RsetDataReaderIndexed(ResultSet rset, int[] rsetIndexPositions, boolean rowNumberIncluded) {
/* 29 */     super(rset);
/* 30 */     if (!rowNumberIncluded) {
/* 31 */       this.rsetIndexPositions = rsetIndexPositions;
/*    */     } else {
/* 33 */       this.rsetIndexPositions = new int[rsetIndexPositions.length + 1];
/* 34 */       for (int i = 0; i < rsetIndexPositions.length; i++)
/*    */       {
/* 36 */         this.rsetIndexPositions[i + 1] = rsetIndexPositions[i] + 1;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int pos() {
/* 43 */     int i = this.pos++;
/* 44 */     return this.rsetIndexPositions[i];
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\RsetDataReaderIndexed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */