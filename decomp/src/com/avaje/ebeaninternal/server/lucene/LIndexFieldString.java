/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.StringReader;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.TokenStream;
/*     */ import org.apache.lucene.document.Document;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LIndexFieldString
/*     */   extends LIndexFieldBase
/*     */ {
/*     */   private final Analyzer indexAnalyzer;
/*     */   
/*     */   public LIndexFieldString(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property, Analyzer indexAnalyzer) {
/*  38 */     super(queryAnalyzer, fieldName, 0, property, fieldFactory);
/*  39 */     this.indexAnalyzer = indexAnalyzer;
/*     */   }
/*     */   
/*     */   public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {
/*  43 */     if (this.propertyName != null && isIndexed()) {
/*  44 */       resolvePropertyNames.add(this.propertyName);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
/*  49 */     if (this.propertyName != null && isStored()) {
/*  50 */       restorePropertyNames.add(this.propertyName);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSortableProperty() {
/*  55 */     if (isIndexed() && !isTokenized())
/*     */     {
/*     */       
/*  58 */       return this.propertyName;
/*     */     }
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readValue(Document doc, Object bean) {
/*  66 */     Object v = doc.get(this.fieldName);
/*  67 */     if (v != null) {
/*  68 */       v = this.scalarType.luceneFromIndexValue(v);
/*     */     }
/*  70 */     this.property.elSetValue(bean, v, true, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public DocFieldWriter createDocFieldWriter() {
/*  75 */     Field f = (Field)this.fieldFactory.createFieldable();
/*  76 */     return new Writer(this.property, this.scalarType, f, this.indexAnalyzer);
/*     */   }
/*     */   
/*     */   private static class Writer
/*     */     implements DocFieldWriter {
/*     */     private final ElPropertyValue property;
/*     */     private final ScalarType<?> scalarType;
/*     */     private final Field field;
/*     */     private final Analyzer indexAnalyzer;
/*     */     
/*     */     Writer(ElPropertyValue property, ScalarType<?> scalarType, Field field, Analyzer indexAnalyzer) {
/*  87 */       this.property = property;
/*  88 */       this.scalarType = scalarType;
/*  89 */       this.field = field;
/*  90 */       this.indexAnalyzer = indexAnalyzer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeValue(Object bean, Document document) {
/*  95 */       Object value = this.property.elGetValue(bean);
/*     */       
/*  97 */       if (value != null) {
/*     */ 
/*     */         
/* 100 */         System.out.println("- write " + this.field.name() + " " + value);
/*     */         
/* 102 */         String s = (String)this.scalarType.luceneToIndexValue(value);
/* 103 */         if (this.indexAnalyzer == null) {
/* 104 */           this.field.setValue(s);
/*     */         } else {
/*     */           
/* 107 */           StringReader sr = new StringReader(s);
/* 108 */           TokenStream tokenStream = this.indexAnalyzer.tokenStream(this.field.name(), sr);
/* 109 */           this.field.setTokenStream(tokenStream);
/*     */         } 
/* 111 */         document.add(this.field);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFieldString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */