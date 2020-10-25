/*    */ package com.avaje.ebeaninternal.server.type;
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
/*    */ public class EscapeJson
/*    */ {
/*    */   public static String escapeQuote(String value) {
/* 28 */     StringBuilder sb = new StringBuilder(value.length() + 2);
/* 29 */     sb.append("\"");
/* 30 */     escape(value, sb);
/* 31 */     sb.append("\"");
/* 32 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String escape(String s) {
/* 40 */     if (s == null) {
/* 41 */       return null;
/*    */     }
/* 43 */     StringBuilder sb = new StringBuilder();
/* 44 */     escape(s, sb);
/* 45 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void escape(String s, StringBuilder sb) {
/* 54 */     for (int i = 0; i < s.length(); i++) {
/* 55 */       char ch = s.charAt(i);
/* 56 */       switch (ch) {
/*    */         case '"':
/* 58 */           sb.append("\\\"");
/*    */           break;
/*    */         case '\\':
/* 61 */           sb.append("\\\\");
/*    */           break;
/*    */         case '\b':
/* 64 */           sb.append("\\b");
/*    */           break;
/*    */         case '\f':
/* 67 */           sb.append("\\f");
/*    */           break;
/*    */         case '\n':
/* 70 */           sb.append("\\n");
/*    */           break;
/*    */         case '\r':
/* 73 */           sb.append("\\r");
/*    */           break;
/*    */         case '\t':
/* 76 */           sb.append("\\t");
/*    */           break;
/*    */         case '/':
/* 79 */           sb.append("\\/");
/*    */           break;
/*    */         default:
/* 82 */           if ((ch >= '\000' && ch <= '\037') || (ch >= '' && ch <= '') || (ch >= ' ' && ch <= '⃿')) {
/*    */ 
/*    */             
/* 85 */             String hs = Integer.toHexString(ch);
/* 86 */             sb.append("\\u");
/* 87 */             for (int j = 0; j < 4 - hs.length(); j++) {
/* 88 */               sb.append('0');
/*    */             }
/* 90 */             sb.append(hs.toUpperCase()); break;
/*    */           } 
/* 92 */           sb.append(ch);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\type\EscapeJson.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */