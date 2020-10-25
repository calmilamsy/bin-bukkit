/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.CompoundTypeProperty;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CtCompoundProperty
/*    */ {
/*    */   private final String relativeName;
/*    */   private final CtCompoundProperty parent;
/*    */   private final CtCompoundType<?> compoundType;
/*    */   private final CompoundTypeProperty property;
/*    */   
/*    */   public CtCompoundProperty(String relativeName, CtCompoundProperty parent, CtCompoundType<?> ctType, CompoundTypeProperty<?, ?> property) {
/* 44 */     this.relativeName = relativeName;
/* 45 */     this.parent = parent;
/* 46 */     this.compoundType = ctType;
/* 47 */     this.property = property;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String getRelativeName() { return this.relativeName; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public String getPropertyName() { return this.property.getName(); }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public String toString() { return this.relativeName; }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue(Object valueObject) {
/* 70 */     if (valueObject == null) {
/* 71 */       return null;
/*    */     }
/* 73 */     if (this.parent != null) {
/* 74 */       valueObject = this.parent.getValue(valueObject);
/*    */     }
/* 76 */     return this.property.getValue(valueObject);
/*    */   }
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
/*    */   public Object setValue(Object bean, Object value) {
/* 90 */     Object compoundValue = ImmutableCompoundTypeBuilder.set(this.compoundType, this.property.getName(), value);
/*    */     
/* 92 */     if (compoundValue != null && this.parent != null)
/*    */     {
/* 94 */       return this.parent.setValue(bean, compoundValue);
/*    */     }
/*    */     
/* 97 */     return compoundValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\CtCompoundProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */