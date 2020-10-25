/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
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
/*    */ public class ScalarTypeURI
/*    */   extends ScalarTypeBaseVarchar<URI>
/*    */ {
/* 33 */   public ScalarTypeURI() { super(URI.class); }
/*    */ 
/*    */ 
/*    */   
/*    */   public URI convertFromDbString(String dbValue) {
/*    */     try {
/* 39 */       return new URI(dbValue);
/* 40 */     } catch (URISyntaxException e) {
/* 41 */       throw new RuntimeException("Error with URI [" + dbValue + "] " + e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public String convertToDbString(URI beanValue) { return beanValue.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public String formatValue(URI v) { return v.toString(); }
/*    */ 
/*    */   
/*    */   public URI parse(String value) {
/*    */     try {
/* 56 */       return new URI(value);
/* 57 */     } catch (URISyntaxException e) {
/* 58 */       throw new TextException("Error with URI [" + value + "] ", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeURI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */