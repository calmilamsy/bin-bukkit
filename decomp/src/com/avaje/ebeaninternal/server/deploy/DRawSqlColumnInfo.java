/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DRawSqlColumnInfo
/*    */ {
/*    */   final String name;
/*    */   final String label;
/*    */   final String propertyName;
/*    */   final boolean scalarProperty;
/*    */   
/*    */   public DRawSqlColumnInfo(String name, String label, String propertyName, boolean scalarProperty) {
/* 17 */     this.name = name;
/* 18 */     this.label = label;
/* 19 */     this.propertyName = propertyName;
/* 20 */     this.scalarProperty = scalarProperty;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public String getLabel() { return this.label; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public String getPropertyName() { return this.propertyName; }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isScalarProperty() { return this.scalarProperty; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public String toString() { return "name:" + this.name + " label:" + this.label + " prop:" + this.propertyName; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\DRawSqlColumnInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */