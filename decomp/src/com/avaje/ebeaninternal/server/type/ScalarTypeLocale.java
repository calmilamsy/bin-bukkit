/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public class ScalarTypeLocale
/*    */   extends ScalarTypeBaseVarchar<Locale>
/*    */ {
/* 31 */   public ScalarTypeLocale() { super(Locale.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public int getLength() { return 20; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public Locale convertFromDbString(String dbValue) { return parse(dbValue); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public String convertToDbString(Locale beanValue) { return beanValue.toString(); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String formatValue(Locale t) { return t.toString(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public Locale parse(String value) {
/* 55 */     int pos1 = -1;
/* 56 */     int pos2 = -1;
/*    */     
/* 58 */     for (i = 0; i < value.length(); i++) {
/* 59 */       char c = value.charAt(i);
/* 60 */       if (c == '_') {
/* 61 */         if (pos1 > -1) {
/* 62 */           pos2 = i;
/*    */           break;
/*    */         } 
/* 65 */         pos1 = i;
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     if (pos1 == -1) {
/* 70 */       return new Locale(value);
/*    */     }
/* 72 */     String language = value.substring(0, pos1);
/* 73 */     if (pos2 == -1) {
/* 74 */       String country = value.substring(pos1 + 1);
/* 75 */       return new Locale(language, country);
/*    */     } 
/* 77 */     String country = value.substring(pos1 + 1, pos2);
/* 78 */     String variant = value.substring(pos2 + 1);
/* 79 */     return new Locale(language, country, variant);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLocale.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */