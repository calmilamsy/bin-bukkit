/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Messages
/*     */ {
/*     */   private static final String BUNDLE_NAME = "com.mysql.jdbc.LocalizedErrorMessages";
/*     */   private static final ResourceBundle RESOURCE_BUNDLE;
/*     */   
/*     */   static  {
/*  46 */     temp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       temp = ResourceBundle.getBundle("com.mysql.jdbc.LocalizedErrorMessages", Locale.getDefault(), Messages.class.getClassLoader());
/*     */     }
/*  57 */     catch (Throwable t) {
/*     */       try {
/*  59 */         temp = ResourceBundle.getBundle("com.mysql.jdbc.LocalizedErrorMessages");
/*  60 */       } catch (Throwable t2) {
/*  61 */         RuntimeException rt = new RuntimeException("Can't load resource bundle due to underlying exception " + t.toString());
/*     */ 
/*     */         
/*  64 */         rt.initCause(t2);
/*     */         
/*  66 */         throw rt;
/*     */       } 
/*     */     } finally {
/*  69 */       RESOURCE_BUNDLE = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(String key) {
/*  81 */     if (RESOURCE_BUNDLE == null) {
/*  82 */       throw new RuntimeException("Localized messages from resource bundle 'com.mysql.jdbc.LocalizedErrorMessages' not loaded during initialization of driver.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  88 */       if (key == null) {
/*  89 */         throw new IllegalArgumentException("Message key can not be null");
/*     */       }
/*     */ 
/*     */       
/*  93 */       String message = RESOURCE_BUNDLE.getString(key);
/*     */       
/*  95 */       if (message == null) {
/*  96 */         message = "Missing error message for key '" + key + "'";
/*     */       }
/*     */       
/*  99 */       return message;
/* 100 */     } catch (MissingResourceException e) {
/* 101 */       return '!' + key + '!';
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 106 */   public static String getString(String key, Object[] args) { return MessageFormat.format(getString(key), args); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\Messages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */