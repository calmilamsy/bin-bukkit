/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import java.util.logging.Logger;
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
/*     */ public class ClassUtil
/*     */ {
/*  32 */   private static final Logger logger = Logger.getLogger(ClassUtil.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean preferContext = true;
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static Class<?> forName(String name) throws ClassNotFoundException { return forName(name, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> forName(String name, Class<?> caller) throws ClassNotFoundException {
/*  48 */     if (caller == null) {
/*  49 */       caller = ClassUtil.class;
/*     */     }
/*  51 */     ClassLoadContext ctx = ClassLoadContext.of(caller, preferContext);
/*     */     
/*  53 */     return ctx.forName(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getClassLoader(Class<?> caller, boolean preferContext) {
/*  59 */     if (caller == null) {
/*  60 */       caller = ClassUtil.class;
/*     */     }
/*  62 */     ClassLoadContext ctx = ClassLoadContext.of(caller, preferContext);
/*  63 */     ClassLoader classLoader = ctx.getDefault(preferContext);
/*  64 */     if (ctx.isAmbiguous()) {
/*  65 */       logger.info("Ambigous ClassLoader (Context vs Caller) chosen " + classLoader);
/*     */     }
/*  67 */     return classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static boolean isPresent(String className) { return isPresent(className, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPresent(String className, Class<?> caller) {
/*     */     try {
/*  82 */       forName(className, caller);
/*  83 */       return true;
/*  84 */     } catch (Throwable ex) {
/*     */       
/*  86 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static Object newInstance(String className) { return newInstance(className, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(String className, Class<?> caller) {
/*     */     try {
/* 103 */       Class<?> cls = forName(className, caller);
/* 104 */       return cls.newInstance();
/* 105 */     } catch (Exception e) {
/* 106 */       String msg = "Error constructing " + className;
/* 107 */       throw new IllegalArgumentException(msg, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\ClassUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */