/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
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
/*     */ public final class GlobalProperties
/*     */ {
/*     */   private static PropertyMap globalMap;
/*     */   private static boolean skipPrimaryServer;
/*     */   
/*  23 */   public static void setSkipPrimaryServer(boolean skip) { skipPrimaryServer = skip; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   public static boolean isSkipPrimaryServer() { return skipPrimaryServer; }
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
/*  45 */   public static String evaluateExpressions(String val) { return getPropertyMap().eval(val); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static void evaluateExpressions() { getPropertyMap().evaluateProperties(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static void setServletContext(ServletContext servletContext) { PropertyMapLoader.setServletContext(servletContext); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static ServletContext getServletContext() { return PropertyMapLoader.getServletContext(); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initPropertyMap() {
/*  75 */     fileName = System.getenv("EBEAN_PROPS_FILE");
/*  76 */     if (fileName == null) {
/*  77 */       fileName = System.getProperty("ebean.props.file");
/*  78 */       if (fileName == null) {
/*  79 */         fileName = "ebean.properties";
/*     */       }
/*     */     } 
/*     */     
/*  83 */     globalMap = PropertyMapLoader.load(null, fileName);
/*  84 */     if (globalMap == null)
/*     */     {
/*     */       
/*  87 */       globalMap = new PropertyMap();
/*     */     }
/*     */     
/*  90 */     String loaderCn = globalMap.get("ebean.properties.loader");
/*  91 */     if (loaderCn != null) {
/*     */       
/*     */       try {
/*     */         
/*  95 */         Runnable r = (Runnable)ClassUtil.newInstance(loaderCn);
/*  96 */         r.run();
/*  97 */       } catch (Exception e) {
/*  98 */         String m = "Error creating or running properties loader " + loaderCn;
/*  99 */         throw new RuntimeException(m, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PropertyMap getPropertyMap() {
/* 109 */     if (globalMap == null) {
/* 110 */       initPropertyMap();
/*     */     }
/*     */     
/* 113 */     return globalMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static String get(String key, String defaultValue) { return getPropertyMap().get(key, defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static int getInt(String key, int defaultValue) { return getPropertyMap().getInt(key, defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static boolean getBoolean(String key, boolean defaultValue) { return getPropertyMap().getBoolean(key, defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static String put(String key, String value) { return getPropertyMap().putEval(key, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putAll(Map<String, String> keyValueMap) {
/* 149 */     for (Map.Entry<String, String> e : keyValueMap.entrySet()) {
/* 150 */       getPropertyMap().putEval((String)e.getKey(), (String)e.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 155 */   public static PropertySource getPropertySource(String name) { return new ConfigPropertyMap(name); }
/*     */   
/*     */   public static interface PropertySource {
/*     */     String getServerName();
/*     */     
/*     */     String get(String param1String1, String param1String2);
/*     */     
/*     */     int getInt(String param1String, int param1Int);
/*     */     
/*     */     boolean getBoolean(String param1String, boolean param1Boolean);
/*     */     
/*     */     <T extends Enum<T>> T getEnum(Class<T> param1Class, String param1String, T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\config\GlobalProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */