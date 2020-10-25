/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
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
/*    */ public class ExportedProperty
/*    */ {
/*    */   private final String foreignDbColumn;
/*    */   private final BeanProperty property;
/*    */   private final boolean embedded;
/*    */   
/*    */   public ExportedProperty(boolean embedded, String foreignDbColumn, BeanProperty property) {
/* 39 */     this.embedded = embedded;
/* 40 */     this.foreignDbColumn = InternString.intern(foreignDbColumn);
/* 41 */     this.property = property;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean isEmbedded() { return this.embedded; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public Object getValue(Object bean) { return this.property.getValue(bean); }
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
/* 68 */   public String getForeignDbColumn() { return this.foreignDbColumn; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\ExportedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */