/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.apache.lucene.index.IndexReader;
/*    */ import org.apache.lucene.search.IndexSearcher;
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
/*    */ public class LIndexSearch
/*    */ {
/* 30 */   private static final Logger logger = Logger.getLogger(LIndexSearch.class.getName());
/*    */   
/*    */   private final IndexSearcher indexSearcher;
/*    */   
/*    */   private final IndexReader indexReader;
/*    */   
/*    */   private int refCount;
/*    */   
/*    */   private boolean markForClose;
/*    */   
/*    */   private boolean closed;
/*    */   
/*    */   public LIndexSearch(IndexSearcher indexSearcher, IndexReader indexReader) {
/* 43 */     this.indexSearcher = indexSearcher;
/* 44 */     this.indexReader = indexReader;
/*    */   }
/*    */ 
/*    */   
/* 48 */   public IndexSearcher getIndexSearcher() { return this.indexSearcher; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public IndexReader getIndexReader() { return this.indexReader; }
/*    */ 
/*    */   
/*    */   public boolean isOpenAcquire() {
/* 56 */     synchronized (this) {
/* 57 */       if (this.markForClose) {
/* 58 */         return false;
/*    */       }
/* 60 */       this.refCount++;
/* 61 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void releaseClose() {
/* 66 */     synchronized (this) {
/* 67 */       this.refCount--;
/* 68 */       closeIfMarked();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void markForClose() {
/* 73 */     synchronized (this) {
/* 74 */       this.markForClose = true;
/* 75 */       closeIfMarked();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void closeIfMarked() {
/* 81 */     if (this.markForClose && this.refCount <= 0 && !this.closed) {
/*    */       
/* 83 */       this.closed = true;
/*    */       try {
/* 85 */         this.indexSearcher.close();
/* 86 */       } catch (Exception e) {
/* 87 */         logger.log(Level.WARNING, "Error when closing indexSearcher", e);
/*    */       } 
/*    */       try {
/* 90 */         this.indexReader.close();
/* 91 */       } catch (Exception e) {
/* 92 */         logger.log(Level.WARNING, "Error when closing indexReader", e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexSearch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */