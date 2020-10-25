/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Fieldable;
/*     */ import org.apache.lucene.queryParser.QueryParser;
/*     */ import org.apache.lucene.util.Version;
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
/*     */ public abstract class LIndexFieldBase
/*     */   implements LIndexField
/*     */ {
/*     */   protected final Analyzer queryAnalyzer;
/*     */   protected final String fieldName;
/*     */   protected final String propertyName;
/*     */   protected final int luceneType;
/*     */   protected final int sortType;
/*     */   protected final ElPropertyValue property;
/*     */   protected final ScalarType<?> scalarType;
/*     */   protected final FieldFactory fieldFactory;
/*     */   protected final boolean indexed;
/*     */   protected final boolean stored;
/*     */   protected final boolean tokenized;
/*     */   
/*     */   public LIndexFieldBase(Analyzer queryAnalyzer, String fieldName, int luceneType, ElPropertyValue property, FieldFactory fieldFactory) {
/*  58 */     this.queryAnalyzer = queryAnalyzer;
/*  59 */     this.fieldName = fieldName;
/*  60 */     this.luceneType = luceneType;
/*  61 */     this.sortType = getSortType(luceneType);
/*  62 */     this.property = property;
/*  63 */     this.fieldFactory = fieldFactory;
/*     */     
/*  65 */     Fieldable fieldPrototype = fieldFactory.createFieldable();
/*  66 */     this.indexed = fieldPrototype.isIndexed();
/*  67 */     this.stored = fieldPrototype.isStored();
/*  68 */     this.tokenized = fieldPrototype.isTokenized();
/*     */     
/*  70 */     if (property == null) {
/*  71 */       this.scalarType = null;
/*  72 */       this.propertyName = null;
/*     */     } else {
/*  74 */       this.scalarType = property.getBeanProperty().getScalarType();
/*  75 */       this.propertyName = SplitName.add(property.getElPrefix(), property.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  80 */   public String toString() { return this.propertyName; }
/*     */ 
/*     */   
/*     */   public void addIndexRequiredPropertyNames(Set<String> requiredPropertyNames) {
/*  84 */     if (this.propertyName != null) {
/*  85 */       requiredPropertyNames.add(this.propertyName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  90 */   public int getSortType() { return this.sortType; }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public QueryParser createQueryParser() { return new QueryParser(Version.LUCENE_30, this.fieldName, this.queryAnalyzer); }
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getName() { return this.fieldName; }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public boolean isIndexed() { return this.indexed; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public boolean isStored() { return this.stored; }
/*     */ 
/*     */ 
/*     */   
/* 110 */   public boolean isTokenized() { return this.tokenized; }
/*     */ 
/*     */ 
/*     */   
/* 114 */   public boolean isBeanProperty() { return (this.property != null); }
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int getPropertyOrder() { return (this.property == null) ? 0 : this.property.getDeployOrder(); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   public ElPropertyValue getElBeanProperty() { return this.property; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readValue(Document doc, Object bean) {
/* 127 */     Object v = readIndexValue(doc);
/* 128 */     if (v != null) {
/* 129 */       v = this.scalarType.luceneFromIndexValue(v);
/*     */     }
/* 131 */     this.property.elSetValue(bean, v, true, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readIndexValue(Document doc) {
/* 136 */     String s = doc.get(this.fieldName);
/* 137 */     if (s == null) {
/* 138 */       return null;
/*     */     }
/*     */     
/* 141 */     switch (this.luceneType) {
/*     */       case 1:
/* 143 */         return Integer.valueOf(Integer.parseInt(s));
/*     */       
/*     */       case 2:
/* 146 */         return Long.valueOf(Long.parseLong(s));
/*     */       
/*     */       case 5:
/* 149 */         return Long.valueOf(Long.parseLong(s));
/*     */       
/*     */       case 6:
/* 152 */         return Long.valueOf(Long.parseLong(s));
/*     */       
/*     */       case 3:
/* 155 */         return Double.valueOf(Double.parseDouble(s));
/*     */       
/*     */       case 4:
/* 158 */         return Float.valueOf(Float.parseFloat(s));
/*     */     } 
/*     */     
/* 161 */     throw new RuntimeException("Unhandled type " + this.luceneType);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSortType(int luceneType) {
/* 166 */     switch (luceneType) {
/*     */       case 1:
/* 168 */         return 4;
/*     */       case 2:
/* 170 */         return 6;
/*     */       case 5:
/* 172 */         return 6;
/*     */       case 6:
/* 174 */         return 6;
/*     */       case 3:
/* 176 */         return 7;
/*     */       case 4:
/* 178 */         return 5;
/*     */       case 0:
/* 180 */         return 3;
/*     */     } 
/*     */     
/* 183 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFieldBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */