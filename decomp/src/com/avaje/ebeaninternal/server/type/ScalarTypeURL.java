/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ public class ScalarTypeURL
/*    */   extends ScalarTypeBaseVarchar<URL>
/*    */ {
/* 33 */   public ScalarTypeURL() { super(URL.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public URL convertFromDbString(String dbValue) {
/*    */     try {
/* 40 */       return new URL(dbValue);
/* 41 */     } catch (MalformedURLException e) {
/* 42 */       throw new RuntimeException("Error with URL [" + dbValue + "] " + e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public String convertToDbString(URL beanValue) { return formatValue(beanValue); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String formatValue(URL v) { return v.toString(); }
/*    */ 
/*    */   
/*    */   public URL parse(String value) {
/*    */     try {
/* 57 */       return new URL(value);
/* 58 */     } catch (MalformedURLException e) {
/* 59 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeURL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */