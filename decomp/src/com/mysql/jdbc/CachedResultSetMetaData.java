/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.util.Map;
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
/*    */ public class CachedResultSetMetaData
/*    */ {
/* 32 */   Map columnNameToIndex = null;
/*    */ 
/*    */   
/*    */   Field[] fields;
/*    */ 
/*    */   
/* 38 */   Map fullColumnNameToIndex = null;
/*    */ 
/*    */   
/*    */   ResultSetMetaData metadata;
/*    */ 
/*    */   
/* 44 */   public Map getColumnNameToIndex() { return this.columnNameToIndex; }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public Field[] getFields() { return this.fields; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public Map getFullColumnNameToIndex() { return this.fullColumnNameToIndex; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public ResultSetMetaData getMetadata() { return this.metadata; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\CachedResultSetMetaData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */