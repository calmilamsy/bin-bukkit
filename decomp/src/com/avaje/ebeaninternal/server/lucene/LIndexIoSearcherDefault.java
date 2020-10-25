/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.lucene.index.IndexCommit;
/*     */ import org.apache.lucene.index.IndexReader;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.store.Directory;
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
/*     */ public class LIndexIoSearcherDefault
/*     */   implements LIndexIoSearcher
/*     */ {
/*  34 */   private static final Logger logger = Logger.getLogger(LIndexIoSearcherDefault.class.getName());
/*     */ 
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final IndexWriter indexWriter;
/*     */ 
/*     */   
/*     */   public LIndexIoSearcherDefault(IndexWriter indexWriter, String name) {
/*  43 */     this.name = name;
/*  44 */     this.indexWriter = indexWriter;
/*  45 */     this.indexSearch = refreshIndexSearch();
/*     */   }
/*     */   
/*     */   public void postCommit() {
/*     */     try {
/*  50 */       refreshIndexSearch();
/*  51 */     } catch (Exception e) {
/*  52 */       String msg = "Error postCommit() refreshing IndexSearcher";
/*  53 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(boolean nearRealTime) {
/*     */     try {
/*  60 */       refreshIndexSearch();
/*  61 */     } catch (Exception e) {
/*  62 */       String msg = "Error refreshing IndexSearch";
/*  63 */       logger.log(Level.SEVERE, msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LIndexVersion getLastestVersion() {
/*  69 */     LIndexSearch s = this.indexSearch;
/*     */     try {
/*  71 */       IndexCommit c = s.getIndexReader().getIndexCommit();
/*  72 */       return new LIndexVersion(c.getGeneration(), c.getVersion());
/*     */     }
/*  74 */     catch (IOException e) {
/*  75 */       throw new PersistenceLuceneException(e);
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
/*     */ 
/*     */   
/*     */   public LIndexSearch getIndexSearch() {
/*  89 */     LIndexSearch s = this.indexSearch;
/*     */     
/*  91 */     if (s.isOpenAcquire()) {
/*  92 */       return s;
/*     */     }
/*     */     
/*  95 */     return refreshIndexSearch();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LIndexSearch refreshIndexSearch() {
/* 101 */     synchronized (this) {
/*     */       IndexReader newReader;
/*     */       
/* 104 */       LIndexSearch currentSearch = this.indexSearch;
/*     */ 
/*     */       
/* 107 */       if (currentSearch == null) {
/*     */         
/* 109 */         newReader = createIndexReader();
/*     */       } else {
/*     */         
/* 112 */         newReader = currentSearch.getIndexReader().reopen();
/*     */         
/* 114 */         if (newReader == currentSearch.getIndexReader())
/*     */         {
/* 116 */           return currentSearch;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 121 */       IndexSearcher searcher = new IndexSearcher(newReader);
/*     */ 
/*     */ 
/*     */       
/* 125 */       LIndexSearch newSearch = new LIndexSearch(searcher, newReader);
/*     */       
/* 127 */       if (currentSearch != null) {
/* 128 */         currentSearch.markForClose();
/*     */       }
/* 130 */       logger.info("Lucene Searcher refreshed " + this.name);
/* 131 */       this.indexSearch = newSearch;
/* 132 */       return newSearch;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IndexReader createIndexReader() {
/*     */     try {
/* 142 */       Directory directory = this.indexWriter.getDirectory();
/* 143 */       return IndexReader.open(directory);
/*     */     }
/* 145 */     catch (IOException e) {
/* 146 */       throw new PersistenceLuceneException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexIoSearcherDefault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */