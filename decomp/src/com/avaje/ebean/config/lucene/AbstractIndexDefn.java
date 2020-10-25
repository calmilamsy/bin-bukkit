/*    */ package com.avaje.ebean.config.lucene;
/*    */ 
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.document.Fieldable;
/*    */ import org.apache.lucene.index.IndexWriter;
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
/*    */ public abstract class AbstractIndexDefn<T>
/*    */   extends Object
/*    */   implements IndexDefn<T>
/*    */ {
/* 37 */   public Analyzer getAnalyzer() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public int getMaxBufferedDocs() { return 0; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public IndexWriter.MaxFieldLength getMaxFieldLength() { return IndexWriter.MaxFieldLength.UNLIMITED; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public double getRAMBufferSizeMB() { return 0.0D; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public int getTermIndexInterval() { return 0; }
/*    */   
/*    */   public void visitCreatedField(Fieldable field) {}
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\AbstractIndexDefn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */