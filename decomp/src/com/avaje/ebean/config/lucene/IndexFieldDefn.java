/*     */ package com.avaje.ebean.config.lucene;
/*     */ 
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Field;
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
/*     */ public class IndexFieldDefn
/*     */ {
/*     */   protected final String name;
/*     */   protected String propertyName;
/*     */   protected Field.Index index;
/*     */   protected Field.Store store;
/*     */   protected Sortable sortable;
/*     */   protected int precisionStep;
/*     */   protected float boost;
/*     */   protected Analyzer queryAnalyzer;
/*     */   protected Analyzer indexAnalyzer;
/*     */   protected String[] properties;
/*     */   
/*     */   public enum Sortable
/*     */   {
/*  32 */     YES,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     DEFAULT;
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
/*     */   public IndexFieldDefn(String name) {
/*  51 */     this.precisionStep = -1;
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
/*  62 */     this.name = name;
/*  63 */     this.propertyName = name;
/*     */   }
/*     */   
/*     */   public IndexFieldDefn(String name, Field.Store store, Field.Index index, Sortable sortable) {
/*  67 */     this(name);
/*  68 */     this.store = store;
/*  69 */     this.index = index;
/*  70 */     this.sortable = sortable;
/*     */   }
/*     */ 
/*     */   
/*  74 */   public String toString() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn copyField(String name) {
/*  85 */     IndexFieldDefn copy = new IndexFieldDefn(name, this.store, this.index, this.sortable);
/*  86 */     copy.setPropertyName(name);
/*     */     
/*  88 */     copy.setIndexAnalyzer(this.indexAnalyzer);
/*  89 */     copy.setQueryAnalyzer(this.queryAnalyzer);
/*  90 */     copy.setPrecisionStep(this.precisionStep);
/*  91 */     copy.setBoost(this.boost);
/*     */     
/*  93 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn copyFieldConcat(String fieldName, String[] properties) {
/* 100 */     IndexFieldDefn copy = copyField(fieldName);
/* 101 */     copy.setPropertyName(null);
/* 102 */     copy.setPropertyNames(properties);
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public String getPropertyName() { return this.propertyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setPropertyName(String propertyName) {
/* 124 */     this.propertyName = propertyName;
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public Field.Index getIndex() { return this.index; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setIndex(Field.Index index) {
/* 139 */     this.index = index;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public Field.Store getStore() { return this.store; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setStore(Field.Store store) {
/* 154 */     this.store = store;
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public Sortable getSortable() { return this.sortable; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setSortable(Sortable sortable) {
/* 169 */     this.sortable = sortable;
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public int getPrecisionStep() { return this.precisionStep; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setPrecisionStep(int precisionStep) {
/* 184 */     this.precisionStep = precisionStep;
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public float getBoost() { return this.boost; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public void setBoost(float boost) { this.boost = boost; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public Analyzer getQueryAnalyzer() { return this.queryAnalyzer; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public Analyzer getIndexAnalyzer() { return this.indexAnalyzer; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setQueryAnalyzer(Analyzer queryAnalyzer) {
/* 220 */     this.queryAnalyzer = queryAnalyzer;
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setIndexAnalyzer(Analyzer indexAnalyzer) {
/* 228 */     this.indexAnalyzer = indexAnalyzer;
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexFieldDefn setBothAnalyzers(Analyzer analyzer) {
/* 236 */     this.queryAnalyzer = analyzer;
/* 237 */     this.indexAnalyzer = analyzer;
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public String[] getPropertyNames() { return this.properties; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public void setPropertyNames(String[] properties) { this.properties = properties; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\lucene\IndexFieldDefn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */