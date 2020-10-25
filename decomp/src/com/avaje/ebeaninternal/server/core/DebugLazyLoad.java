/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugLazyLoad
/*     */ {
/*     */   private final String[] ignoreList;
/*     */   private final boolean debug;
/*     */   
/*     */   public DebugLazyLoad(boolean lazyLoadDebug) {
/*  18 */     this.ignoreList = buildLazyLoadIgnoreList();
/*  19 */     this.debug = lazyLoadDebug;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   public boolean isDebug() { return this.debug; }
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
/*     */   public StackTraceElement getStackTraceElement(Class<?> beanType) {
/*  41 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/*  42 */     for (int i = 0; i < stackTrace.length; i++) {
/*  43 */       if (isStackLine(stackTrace[i], beanType)) {
/*  44 */         return stackTrace[i];
/*     */       }
/*     */     } 
/*  47 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isStackLine(StackTraceElement element, Class<?> beanType) {
/*  55 */     String stackClass = element.getClassName();
/*     */     
/*  57 */     if (isBeanClass(beanType, stackClass)) {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     for (int i = 0; i < this.ignoreList.length; i++) {
/*  62 */       if (stackClass.startsWith(this.ignoreList[i])) {
/*  63 */         return false;
/*     */       }
/*     */     } 
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBeanClass(Class<?> beanType, String stackClass) {
/*  74 */     if (stackClass.startsWith(beanType.getName())) {
/*  75 */       return true;
/*     */     }
/*  77 */     Class<?> superCls = beanType.getSuperclass();
/*  78 */     if (superCls.equals(Object.class)) {
/*  79 */       return false;
/*     */     }
/*  81 */     return isBeanClass(superCls, stackClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] buildLazyLoadIgnoreList() {
/*  92 */     List<String> ignore = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */     
/*  96 */     ignore.add("com.avaje.ebean");
/*  97 */     ignore.add("java");
/*  98 */     ignore.add("sun.reflect");
/*  99 */     ignore.add("org.codehaus.groovy.runtime.");
/*     */     
/* 101 */     String extraIgnore = GlobalProperties.get("debug.lazyload.ignore", null);
/* 102 */     if (extraIgnore != null) {
/* 103 */       String[] split = extraIgnore.split(",");
/* 104 */       for (int i = 0; i < split.length; i++) {
/* 105 */         ignore.add(split[i].trim());
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return (String[])ignore.toArray(new String[ignore.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\core\DebugLazyLoad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */