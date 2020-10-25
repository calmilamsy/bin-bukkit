/*     */ package com.avaje.ebeaninternal.server.lucene;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.index.Term;
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
/*     */ public final class LIndexFieldId
/*     */   extends LIndexFieldBase
/*     */ {
/*     */   private final IdBinder idBinder;
/*     */   
/*     */   public LIndexFieldId(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property, IdBinder idBinder) {
/*  37 */     super(queryAnalyzer, fieldName, 0, property, fieldFactory);
/*  38 */     this.idBinder = idBinder;
/*     */   }
/*     */   
/*     */   public Term createTerm(Object id) {
/*  42 */     String termVal = this.idBinder.writeTerm(id);
/*  43 */     return new Term(this.fieldName, termVal);
/*     */   }
/*     */   
/*     */   public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {
/*  47 */     if (this.propertyName != null && isIndexed()) {
/*  48 */       resolvePropertyNames.add(this.propertyName);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
/*  53 */     if (this.propertyName != null && isStored()) {
/*  54 */       restorePropertyNames.add(this.propertyName);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSortableProperty() {
/*  59 */     if (isIndexed() && !isTokenized())
/*     */     {
/*     */       
/*  62 */       return this.propertyName;
/*     */     }
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readValue(Document doc, Object bean) {
/*  70 */     String v = doc.get(this.fieldName);
/*  71 */     if (v != null) {
/*  72 */       Object id = this.idBinder.readTerm(v);
/*  73 */       this.property.elSetValue(bean, id, true, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DocFieldWriter createDocFieldWriter() {
/*  79 */     Field f = (Field)this.fieldFactory.createFieldable();
/*  80 */     return new Writer(this.property, f, this.idBinder);
/*     */   }
/*     */   
/*     */   private static class Writer
/*     */     implements DocFieldWriter {
/*     */     private final IdBinder idBinder;
/*     */     private final ElPropertyValue property;
/*     */     private final Field field;
/*     */     
/*     */     Writer(ElPropertyValue property, Field field, IdBinder idBinder) {
/*  90 */       this.property = property;
/*  91 */       this.field = field;
/*  92 */       this.idBinder = idBinder;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeValue(Object bean, Document document) {
/*  97 */       Object value = this.property.elGetValue(bean);
/*     */       
/*  99 */       if (value != null) {
/*     */ 
/*     */         
/* 102 */         System.out.println("- write " + this.field.name() + " " + value);
/*     */         
/* 104 */         String writeTerm = this.idBinder.writeTerm(value);
/* 105 */         this.field.setValue(writeTerm);
/* 106 */         document.add(this.field);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFieldId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */