/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropertyMapLoader
/*     */ {
/*  21 */   private static Logger logger = Logger.getLogger(PropertyMapLoader.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static ServletContext servletContext;
/*     */ 
/*     */ 
/*     */   
/*  29 */   public static ServletContext getServletContext() { return servletContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static void setServletContext(ServletContext servletContext) { PropertyMapLoader.servletContext = servletContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyMap load(PropertyMap p, String fileName) {
/*  47 */     InputStream is = findInputStream(fileName);
/*  48 */     if (is == null) {
/*  49 */       logger.severe(fileName + " not found");
/*  50 */       return p;
/*     */     } 
/*  52 */     return load(p, is);
/*     */   }
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
/*     */   private static PropertyMap load(PropertyMap p, InputStream in) {
/*  65 */     Properties props = new Properties();
/*     */     try {
/*  67 */       props.load(in);
/*  68 */       in.close();
/*  69 */     } catch (IOException e) {
/*  70 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  73 */     if (p == null) {
/*  74 */       p = new PropertyMap();
/*     */     }
/*     */ 
/*     */     
/*  78 */     Iterator<Map.Entry<Object, Object>> it = props.entrySet().iterator();
/*  79 */     while (it.hasNext()) {
/*  80 */       Map.Entry<Object, Object> entry = (Map.Entry)it.next();
/*  81 */       String key = ((String)entry.getKey()).toLowerCase();
/*  82 */       String val = (String)entry.getValue();
/*  83 */       if (val != null) {
/*  84 */         val = val.trim();
/*     */       }
/*  86 */       p.put(key, val);
/*     */     } 
/*     */     
/*  89 */     p.evaluateProperties();
/*     */     
/*  91 */     String otherProps = p.remove("load.properties");
/*  92 */     if (otherProps == null) {
/*  93 */       otherProps = p.remove("load.properties.override");
/*     */     }
/*  95 */     if (otherProps != null) {
/*  96 */       otherProps = otherProps.replace("\\", "/");
/*  97 */       InputStream is = findInputStream(otherProps);
/*  98 */       if (is != null) {
/*  99 */         logger.fine("loading properties from " + otherProps);
/* 100 */         load(p, is);
/*     */       } else {
/* 102 */         logger.severe("load.properties " + otherProps + " not found.");
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static InputStream findInputStream(String fileName) {
/* 114 */     if (fileName == null) {
/* 115 */       throw new NullPointerException("fileName is null?");
/*     */     }
/*     */     
/* 118 */     if (servletContext == null) {
/* 119 */       logger.fine("No servletContext so not looking in WEB-INF for " + fileName);
/*     */     }
/*     */     else {
/*     */       
/* 123 */       InputStream in = servletContext.getResourceAsStream("/WEB-INF/" + fileName);
/* 124 */       if (in != null) {
/* 125 */         logger.fine(fileName + " found in WEB-INF");
/* 126 */         return in;
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 131 */       File f = new File(fileName);
/*     */       
/* 133 */       if (f.exists()) {
/* 134 */         logger.fine(fileName + " found in file system");
/* 135 */         return new FileInputStream(f);
/*     */       } 
/* 137 */       InputStream in = findInClassPath(fileName);
/* 138 */       if (in != null) {
/* 139 */         logger.fine(fileName + " found in classpath");
/*     */       }
/* 141 */       return in;
/*     */     
/*     */     }
/* 144 */     catch (FileNotFoundException ex) {
/*     */ 
/*     */       
/* 147 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 152 */   private static InputStream findInClassPath(String fileName) { return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\PropertyMapLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */