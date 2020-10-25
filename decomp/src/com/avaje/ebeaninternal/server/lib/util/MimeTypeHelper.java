/*    */ package com.avaje.ebeaninternal.server.lib.util;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class MimeTypeHelper
/*    */ {
/*    */   public static String getMimeType(String filePath) {
/* 38 */     int lastPeriod = filePath.lastIndexOf(".");
/* 39 */     if (lastPeriod > -1) {
/* 40 */       filePath = filePath.substring(lastPeriod + 1);
/*    */     }
/*    */     
/*    */     try {
/* 44 */       return resources.getString(filePath.toLowerCase());
/*    */     }
/* 46 */     catch (MissingResourceException e) {
/* 47 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   private static ResourceBundle resources = ResourceBundle.getBundle("com.avaje.lib.util.mimetypes");
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\MimeTypeHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */