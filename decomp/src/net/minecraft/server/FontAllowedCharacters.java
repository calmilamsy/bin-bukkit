/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ 
/*    */ public class FontAllowedCharacters
/*    */ {
/*    */   private static String a() {
/* 10 */     String str = "";
/*    */     try {
/* 12 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(FontAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
/* 13 */       String str1 = "";
/* 14 */       while ((str1 = bufferedReader.readLine()) != null) {
/* 15 */         if (!str1.startsWith("#")) {
/* 16 */           str = str + str1;
/*    */         }
/*    */       } 
/* 19 */       bufferedReader.close();
/* 20 */     } catch (Exception exception) {}
/*    */     
/* 22 */     return str;
/*    */   }
/*    */ 
/*    */   
/* 26 */   public static final String allowedCharacters = a();
/*    */   public static final char[] b = { 
/* 28 */       '/', '\n', '\r', '\t', Character.MIN_VALUE, '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\FontAllowedCharacters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */