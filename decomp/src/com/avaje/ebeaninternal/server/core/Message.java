/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import java.text.MessageFormat;
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
/*    */ public class Message
/*    */ {
/*    */   private static final String bundle = "com.avaje.ebeaninternal.api.message";
/*    */   
/*    */   public static String msg(String key, Object arg) {
/* 37 */     Object[] args = new Object[1];
/* 38 */     args[0] = arg;
/* 39 */     return MessageFormat.format(getPattern(key), args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String msg(String key, Object arg, Object arg2) {
/* 46 */     Object[] args = new Object[2];
/* 47 */     args[0] = arg;
/* 48 */     args[1] = arg2;
/* 49 */     return MessageFormat.format(getPattern(key), args);
/*    */   }
/*    */   
/*    */   public static String msg(String key, Object arg, Object arg2, Object arg3) {
/* 53 */     Object[] args = new Object[3];
/* 54 */     args[0] = arg;
/* 55 */     args[1] = arg2;
/* 56 */     args[2] = arg3;
/* 57 */     return MessageFormat.format(getPattern(key), args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public static String msg(String key, Object[] args) { return MessageFormat.format(getPattern(key), args); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public static String msg(String key) { return MessageFormat.format(getPattern(key), new Object[0]); }
/*    */ 
/*    */   
/*    */   private static String getPattern(String key) {
/*    */     try {
/* 76 */       ResourceBundle myResources = ResourceBundle.getBundle("com.avaje.ebeaninternal.api.message");
/* 77 */       return myResources.getString(key);
/* 78 */     } catch (MissingResourceException e) {
/* 79 */       return "MissingResource com.avaje.ebeaninternal.api.message:" + key;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */