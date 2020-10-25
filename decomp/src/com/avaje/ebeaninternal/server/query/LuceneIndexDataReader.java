/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndex;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexField;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexFields;
/*     */ import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
/*     */ import com.avaje.ebeaninternal.server.type.DataReader;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Array;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import javax.persistence.PersistenceException;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.ScoreDoc;
/*     */ import org.apache.lucene.search.Sort;
/*     */ import org.apache.lucene.search.TopFieldDocs;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LuceneIndexDataReader
/*     */   implements DataReader
/*     */ {
/*     */   private final LIndexFields indexFieldDefn;
/*     */   private LIndexField[] readFields;
/*     */   private ScoreDoc[] scoreDocs;
/*     */   private LIndexSearch indexSearch;
/*     */   private IndexSearcher searcher;
/*     */   private int maxReadRows;
/*     */   private int colIndex;
/*     */   private int rowIndex;
/*     */   private Document currentDoc;
/*     */   
/*     */   public LuceneIndexDataReader(OrmQueryRequest<?> request) {
/*  68 */     LIndex luceneIndex = request.getLuceneIndex();
/*     */     
/*  70 */     this.indexFieldDefn = luceneIndex.getIndexFieldDefn();
/*  71 */     this.readFields = this.indexFieldDefn.getReadFields();
/*  72 */     this.indexSearch = luceneIndex.getIndexSearch();
/*  73 */     this.searcher = this.indexSearch.getIndexSearcher();
/*     */     
/*  75 */     LuceneOrmQueryRequest luceneRequest = request.getLuceneOrmQueryRequest();
/*     */     
/*  77 */     int maxRows = request.getQuery().getMaxRows();
/*  78 */     if (maxRows < 1) {
/*  79 */       maxRows = 100;
/*     */     }
/*  81 */     Query luceneQuery = luceneRequest.getLuceneQuery();
/*  82 */     Sort luceneSort = luceneRequest.getLuceneSort();
/*     */     try {
/*     */       TopFieldDocs topFieldDocs;
/*  85 */       if (luceneSort == null) {
/*  86 */         topFieldDocs = this.searcher.search(luceneQuery, null, maxRows);
/*     */       } else {
/*  88 */         topFieldDocs = this.searcher.search(luceneQuery, null, maxRows, luceneSort);
/*     */       } 
/*     */       
/*  91 */       this.scoreDocs = topFieldDocs.scoreDocs;
/*  92 */       this.maxReadRows = this.scoreDocs.length;
/*     */     }
/*  94 */     catch (IOException e) {
/*  95 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] readFieldAsBytes() {
/*     */     try {
/* 101 */       String fieldName = this.readFields[this.colIndex++].getName();
/* 102 */       return this.currentDoc.getBinaryValue(fieldName);
/*     */     }
/* 104 */     catch (Exception e) {
/* 105 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String readFieldAsString() {
/*     */     try {
/* 111 */       String fieldName = this.readFields[this.colIndex++].getName();
/* 112 */       return this.currentDoc.get(fieldName);
/*     */     }
/* 114 */     catch (Exception e) {
/* 115 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 120 */   public void close() throws SQLException { this.indexSearch.releaseClose(); }
/*     */ 
/*     */ 
/*     */   
/* 124 */   public Array getArray() throws SQLException { throw new PersistenceException("Not Supported yet"); }
/*     */ 
/*     */   
/*     */   public BigDecimal getBigDecimal() throws SQLException {
/* 128 */     String s = readFieldAsString();
/* 129 */     return (s == null) ? null : new BigDecimal(s);
/*     */   }
/*     */ 
/*     */   
/* 133 */   public byte[] getBinaryBytes() { return readFieldAsBytes(); }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public byte[] getBlobBytes() { return readFieldAsBytes(); }
/*     */ 
/*     */   
/*     */   public Boolean getBoolean() throws SQLException {
/* 141 */     String s = readFieldAsString();
/* 142 */     return (s == null) ? null : Boolean.valueOf(s);
/*     */   }
/*     */   
/*     */   public Byte getByte() throws SQLException {
/* 146 */     byte[] bytes = readFieldAsBytes();
/* 147 */     return (bytes == null) ? null : Byte.valueOf(bytes[0]);
/*     */   }
/*     */ 
/*     */   
/* 151 */   public byte[] getBytes() { return readFieldAsBytes(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() throws SQLException {
/* 156 */     Long longVal = getLong();
/* 157 */     return (longVal == null) ? null : new Date(longVal.longValue());
/*     */   }
/*     */   
/*     */   public Double getDouble() throws SQLException {
/* 161 */     String s = readFieldAsString();
/* 162 */     return (s == null) ? null : Double.valueOf(s);
/*     */   }
/*     */   
/*     */   public Float getFloat() throws SQLException {
/* 166 */     String s = readFieldAsString();
/* 167 */     return (s == null) ? null : Float.valueOf(s);
/*     */   }
/*     */   
/*     */   public Integer getInt() throws SQLException {
/* 171 */     String s = readFieldAsString();
/* 172 */     return (s == null) ? null : Integer.valueOf(s);
/*     */   }
/*     */   
/*     */   public Long getLong() throws SQLException {
/* 176 */     String s = readFieldAsString();
/* 177 */     return (s == null) ? null : Long.valueOf(s);
/*     */   }
/*     */   
/*     */   public Short getShort() throws SQLException {
/* 181 */     String s = readFieldAsString();
/* 182 */     return (s == null) ? null : Short.valueOf(s);
/*     */   }
/*     */ 
/*     */   
/* 186 */   public String getString() { return readFieldAsString(); }
/*     */ 
/*     */ 
/*     */   
/* 190 */   public String getStringClob() { return readFieldAsString(); }
/*     */ 
/*     */ 
/*     */   
/* 194 */   public String getStringFromStream() { return readFieldAsString(); }
/*     */ 
/*     */   
/*     */   public Time getTime() throws SQLException {
/* 198 */     String s = readFieldAsString();
/* 199 */     return (s == null) ? null : Time.valueOf(s);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp() throws SQLException {
/* 203 */     Long longVal = getLong();
/* 204 */     return (longVal == null) ? null : new Timestamp(longVal.longValue());
/*     */   }
/*     */ 
/*     */   
/* 208 */   public void incrementPos(int increment) { this.colIndex += increment; }
/*     */ 
/*     */   
/*     */   public boolean next() throws SQLException {
/* 212 */     if (this.rowIndex >= this.maxReadRows) {
/* 213 */       return false;
/*     */     }
/*     */     try {
/* 216 */       int docIndex = (this.scoreDocs[this.rowIndex++]).doc;
/* 217 */       this.currentDoc = this.searcher.doc(docIndex);
/* 218 */       return true;
/*     */     }
/* 220 */     catch (Exception e) {
/* 221 */       throw new PersistenceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 226 */   public void resetColumnPosition() throws SQLException { this.colIndex = 0; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\query\LuceneIndexDataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */