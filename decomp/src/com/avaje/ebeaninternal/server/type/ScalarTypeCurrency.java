/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.Currency;
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
/*    */ public class ScalarTypeCurrency
/*    */   extends ScalarTypeBaseVarchar<Currency>
/*    */ {
/* 30 */   public ScalarTypeCurrency() { super(Currency.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public int getLength() { return 3; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public Currency convertFromDbString(String dbValue) { return Currency.getInstance(dbValue); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public String convertToDbString(Currency beanValue) { return beanValue.getCurrencyCode(); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   public String formatValue(Currency v) { return v.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 53 */   public Currency parse(String value) { return Currency.getInstance(value); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCurrency.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */