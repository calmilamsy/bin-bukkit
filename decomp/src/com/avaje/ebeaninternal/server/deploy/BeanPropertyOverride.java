/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.InternString;
/*    */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
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
/*    */ public class BeanPropertyOverride
/*    */ {
/*    */   private final String dbColumn;
/*    */   private final String sqlFormulaSelect;
/*    */   private final String sqlFormulaJoin;
/*    */   
/* 40 */   public BeanPropertyOverride(String dbColumn) { this(dbColumn, null, null); }
/*    */ 
/*    */   
/*    */   public BeanPropertyOverride(String dbColumn, String sqlFormulaSelect, String sqlFormulaJoin) {
/* 44 */     this.dbColumn = InternString.intern(dbColumn);
/* 45 */     this.sqlFormulaSelect = InternString.intern(sqlFormulaSelect);
/* 46 */     this.sqlFormulaJoin = InternString.intern(sqlFormulaJoin);
/*    */   }
/*    */ 
/*    */   
/* 50 */   public String getDbColumn() { return this.dbColumn; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String getSqlFormulaSelect() { return this.sqlFormulaSelect; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public String getSqlFormulaJoin() { return this.sqlFormulaJoin; }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public String replace(String src, String srcDbColumn) { return StringHelper.replaceString(src, srcDbColumn, this.dbColumn); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyOverride.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */