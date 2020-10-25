/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.util.Iterator;
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
/*    */ public abstract class IterateBlock
/*    */ {
/*    */   DatabaseMetaData.IteratorWithCleanup iteratorWithCleanup;
/*    */   Iterator javaIterator;
/*    */   boolean stopIterating;
/*    */   
/*    */   IterateBlock(DatabaseMetaData.IteratorWithCleanup i) {
/* 35 */     this.stopIterating = false;
/*    */ 
/*    */     
/* 38 */     this.iteratorWithCleanup = i;
/* 39 */     this.javaIterator = null;
/*    */   }
/*    */   IterateBlock(Iterator i) {
/*    */     this.stopIterating = false;
/* 43 */     this.javaIterator = i;
/* 44 */     this.iteratorWithCleanup = null;
/*    */   }
/*    */   
/*    */   public void doForAll() throws SQLException {
/* 48 */     if (this.iteratorWithCleanup != null) {
/*    */       try {
/* 50 */         while (this.iteratorWithCleanup.hasNext()) {
/* 51 */           forEach(this.iteratorWithCleanup.next());
/*    */           
/* 53 */           if (this.stopIterating) {
/*    */             break;
/*    */           }
/*    */         } 
/*    */       } finally {
/* 58 */         this.iteratorWithCleanup.close();
/*    */       } 
/*    */     } else {
/* 61 */       while (this.javaIterator.hasNext()) {
/* 62 */         forEach(this.javaIterator.next());
/*    */         
/* 64 */         if (this.stopIterating) {
/*    */           break;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   abstract void forEach(Object paramObject) throws SQLException;
/*    */   
/* 74 */   public final boolean fullIteration() { return !this.stopIterating; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\IterateBlock.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */