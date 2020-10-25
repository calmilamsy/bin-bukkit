/*    */ package com.avaje.ebean.config;
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
/*    */ public class MatchingNamingConvention
/*    */   extends AbstractNamingConvention
/*    */ {
/*    */   public MatchingNamingConvention() {}
/*    */   
/* 49 */   public MatchingNamingConvention(String sequenceFormat) { super(sequenceFormat); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String getColumnFromProperty(Class<?> beanClass, String propertyName) { return propertyName; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public TableName getTableNameByConvention(Class<?> beanClass) { return new TableName(getCatalog(), getSchema(), beanClass.getSimpleName()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   public String getPropertyFromColumn(Class<?> beanClass, String dbColumnName) { return dbColumnName; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\MatchingNamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */