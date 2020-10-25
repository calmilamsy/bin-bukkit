/*    */ package org.bukkit.craftbukkit;
/*    */ public class TextWrapper {
/*    */   private static final int[] characterWidths = { 
/*  4 */       1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6, 8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1 };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final char COLOR_CHAR = '§';
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int CHAT_WINDOW_WIDTH = 320;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int CHAT_STRING_LENGTH = 119;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private static final String allowedChars = FontAllowedCharacters.allowedCharacters;
/*    */   
/*    */   public static String[] wrapText(String text) {
/* 28 */     StringBuilder out = new StringBuilder();
/* 29 */     char colorChar = 'f';
/* 30 */     int lineWidth = 0;
/* 31 */     int lineLength = 0;
/*    */ 
/*    */     
/* 34 */     for (int i = 0; i < text.length(); i++) {
/* 35 */       char ch = text.charAt(i);
/*    */ 
/*    */       
/* 38 */       if (ch == '§' && i < text.length() - 1) {
/*    */         
/* 40 */         if (lineLength + 2 > 119) {
/* 41 */           out.append('\n');
/* 42 */           lineLength = 0;
/* 43 */           if (colorChar != 'f' && colorChar != 'F') {
/* 44 */             out.append('§').append(colorChar);
/* 45 */             lineLength += 2;
/*    */           } 
/*    */         } 
/* 48 */         colorChar = text.charAt(++i);
/* 49 */         out.append('§').append(colorChar);
/* 50 */         lineLength += 2;
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 55 */         int index = allowedChars.indexOf(ch);
/* 56 */         if (index != -1) {
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 61 */           index += 32;
/*    */ 
/*    */ 
/*    */           
/* 65 */           int width = characterWidths[index];
/*    */ 
/*    */           
/* 68 */           if (lineLength + 1 > 119 || lineWidth + width >= 320) {
/* 69 */             out.append('\n');
/* 70 */             lineLength = 0;
/*    */ 
/*    */             
/* 73 */             if (colorChar != 'f' && colorChar != 'F') {
/* 74 */               out.append('§').append(colorChar);
/* 75 */               lineLength += 2;
/*    */             } 
/* 77 */             lineWidth = width;
/*    */           } else {
/* 79 */             lineWidth += width;
/*    */           } 
/* 81 */           out.append(ch);
/* 82 */           lineLength++;
/*    */         } 
/*    */       } 
/*    */     } 
/* 86 */     return out.toString().split("\n");
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\TextWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */