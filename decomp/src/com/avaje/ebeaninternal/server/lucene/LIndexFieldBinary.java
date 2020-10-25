/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*    */ import java.util.Set;
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.document.Document;
/*    */ import org.apache.lucene.document.Field;
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
/*    */ public final class LIndexFieldBinary
/*    */   extends LIndexFieldBase
/*    */ {
/* 34 */   public LIndexFieldBinary(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property) { super(queryAnalyzer, fieldName, 7, property, fieldFactory); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
/* 46 */     if (this.propertyName != null && isStored()) {
/* 47 */       restorePropertyNames.add(this.propertyName);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String getSortableProperty() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readValue(Document doc, Object bean) {
/* 59 */     Object v = doc.get(this.fieldName);
/* 60 */     if (v != null) {
/* 61 */       v = this.scalarType.luceneFromIndexValue(v);
/*    */     }
/* 63 */     this.property.elSetValue(bean, v, true, false);
/*    */   }
/*    */   
/*    */   public DocFieldWriter createDocFieldWriter() {
/* 67 */     Field f = (Field)this.fieldFactory.createFieldable();
/* 68 */     return new Writer(this.property, this.scalarType, f, null);
/*    */   }
/*    */   
/*    */   private static class Writer
/*    */     implements DocFieldWriter {
/*    */     private final ElPropertyValue property;
/*    */     private final ScalarType<?> scalarType;
/*    */     private final Field field;
/*    */     
/*    */     private Writer(ElPropertyValue property, ScalarType<?> scalarType, Field field) {
/* 78 */       this.property = property;
/* 79 */       this.scalarType = scalarType;
/* 80 */       this.field = field;
/*    */     }
/*    */     
/*    */     public void writeValue(Object bean, Document document) {
/* 84 */       Object value = this.property.elGetValue(bean);
/*    */       
/* 86 */       if (value != null) {
/*    */ 
/*    */ 
/*    */         
/* 90 */         byte[] s = (byte[])this.scalarType.luceneToIndexValue(value);
/* 91 */         this.field.setValue(s);
/* 92 */         document.add(this.field);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\LIndexFieldBinary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */