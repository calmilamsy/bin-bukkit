/*    */ package com.avaje.ebeaninternal.server.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URLClassLoader;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultClassPathReader
/*    */   implements ClassPathReader
/*    */ {
/* 16 */   private static final Logger logger = Logger.getLogger(DefaultClassPathReader.class.getName());
/*    */ 
/*    */   
/*    */   public Object[] readPath(ClassLoader classLoader) {
/* 20 */     if (classLoader instanceof URLClassLoader) {
/*    */       
/* 22 */       URLClassLoader ucl = (URLClassLoader)classLoader;
/* 23 */       return ucl.getURLs();
/*    */     } 
/*    */ 
/*    */     
/*    */     try {
/* 28 */       Method method = classLoader.getClass().getMethod("getClassPath", new Class[0]);
/* 29 */       if (method != null) {
/* 30 */         logger.info("Using getClassPath() method on classLoader[" + classLoader.getClass() + "]");
/* 31 */         String s = method.invoke(classLoader, new Object[0]).toString();
/* 32 */         return s.split(File.pathSeparator);
/*    */       } 
/* 34 */     } catch (NoSuchMethodException e) {
/*    */     
/* 36 */     } catch (Exception e) {
/* 37 */       throw new RuntimeException("Unexpected Error trying to read classpath from classloader", e);
/*    */     } 
/*    */ 
/*    */     
/*    */     try {
/* 42 */       Method method = classLoader.getClass().getMethod("getClasspath", new Class[0]);
/* 43 */       if (method != null) {
/* 44 */         logger.info("Using getClasspath() method on classLoader[" + classLoader.getClass() + "]");
/* 45 */         String s = method.invoke(classLoader, new Object[0]).toString();
/*    */         
/* 47 */         return s.split(File.pathSeparator);
/*    */       } 
/* 49 */     } catch (NoSuchMethodException e) {
/*    */     
/* 51 */     } catch (Exception e) {
/* 52 */       throw new RuntimeException("Unexpected Error trying to read classpath from classloader", e);
/*    */     } 
/*    */     
/* 55 */     String imsg = "Unsure how to read classpath from classLoader [" + classLoader.getClass() + "]";
/* 56 */     logger.info(imsg);
/*    */     
/* 58 */     String msg = "Using java.class.path system property to search for entity beans";
/* 59 */     logger.warning(msg);
/*    */     
/* 61 */     return System.getProperty("java.class.path", "").split(File.pathSeparator);
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\serve\\util\DefaultClassPathReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */