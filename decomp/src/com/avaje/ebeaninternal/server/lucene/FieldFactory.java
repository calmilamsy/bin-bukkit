/*    */ package com.avaje.ebeaninternal.server.lucene;
/*    */ 
/*    */ import org.apache.lucene.document.Field;
/*    */ import org.apache.lucene.document.Fieldable;
/*    */ import org.apache.lucene.document.NumericField;
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
/*    */ public class FieldFactory
/*    */ {
/*    */   private final boolean numericField;
/*    */   private final String fieldName;
/*    */   private final Field.Store store;
/*    */   private final Field.Index index;
/*    */   private final float boost;
/*    */   private final int precisionStep;
/*    */   
/* 41 */   public static FieldFactory numeric(String fieldName, Field.Store store, Field.Index index, float boost, int precisionStep) { return new FieldFactory(true, fieldName, store, index, boost, precisionStep); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public static FieldFactory normal(String fieldName, Field.Store store, Field.Index index, float boost) { return new FieldFactory(false, fieldName, store, index, boost, false); }
/*    */ 
/*    */   
/*    */   private FieldFactory(boolean numericField, String fieldName, Field.Store store, Field.Index index, float boost, int precisionStep) {
/* 49 */     this.numericField = numericField;
/* 50 */     this.fieldName = fieldName;
/* 51 */     this.store = store;
/* 52 */     this.index = index;
/* 53 */     this.boost = boost;
/* 54 */     this.precisionStep = precisionStep;
/*    */   }
/*    */ 
/*    */   
/* 58 */   public Fieldable createFieldable() { return this.numericField ? createNumericField() : createNormalField(); }
/*    */ 
/*    */   
/*    */   private Fieldable createNormalField() {
/* 62 */     Field f = new Field(this.fieldName, "", this.store, this.index);
/* 63 */     if (this.boost > 0.0F) {
/* 64 */       f.setBoost(this.boost);
/*    */     }
/* 66 */     return f;
/*    */   }
/*    */   
/*    */   private Fieldable createNumericField() {
/* 70 */     boolean indexed = this.index.isIndexed();
/* 71 */     NumericField f = new NumericField(this.fieldName, this.precisionStep, this.store, indexed);
/* 72 */     if (this.boost > 0.0F) {
/* 73 */       f.setBoost(this.boost);
/*    */     }
/* 75 */     return f;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\lucene\FieldFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */