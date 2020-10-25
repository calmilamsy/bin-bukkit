/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassLoadContext
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(ClassLoadContext.class.getName());
/*     */   
/*     */   private final ClassLoader callerLoader;
/*     */   
/*     */   private final ClassLoader contextLoader;
/*     */   
/*     */   private final boolean preferContext;
/*     */   
/*     */   private boolean ambiguous;
/*     */ 
/*     */   
/*  47 */   public static ClassLoadContext of(Class<?> caller, boolean preferContext) { return new ClassLoadContext(caller, preferContext); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ClassLoadContext(Class<?> caller, boolean preferContext) {
/*  55 */     if (caller == null) {
/*  56 */       throw new IllegalArgumentException("caller is null");
/*     */     }
/*  58 */     this.callerLoader = caller.getClassLoader();
/*  59 */     this.contextLoader = Thread.currentThread().getContextClassLoader();
/*  60 */     this.preferContext = preferContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<?> forName(String name) throws ClassNotFoundException {
/*  65 */     ClassLoader defaultLoader = getDefault(this.preferContext);
/*     */     
/*     */     try {
/*  68 */       return Class.forName(name, true, defaultLoader);
/*  69 */     } catch (ClassNotFoundException e) {
/*  70 */       if (this.callerLoader == defaultLoader) {
/*  71 */         throw e;
/*     */       }
/*  73 */       return Class.forName(name, true, this.callerLoader);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getDefault(boolean preferContext) {
/*  88 */     if (this.contextLoader == null) {
/*  89 */       if (logger.isLoggable(Level.FINE)) {
/*  90 */         logger.fine("No Context ClassLoader, using " + this.callerLoader.getClass().getName());
/*     */       }
/*  92 */       return this.callerLoader;
/*     */     } 
/*  94 */     if (this.contextLoader == this.callerLoader) {
/*  95 */       if (logger.isLoggable(Level.FINE)) {
/*  96 */         logger.fine("Context and Caller ClassLoader's same instance of " + this.contextLoader.getClass().getName());
/*     */       }
/*  98 */       return this.callerLoader;
/*     */     } 
/*     */     
/* 101 */     if (isChild(this.contextLoader, this.callerLoader)) {
/* 102 */       if (logger.isLoggable(Level.FINE)) {
/* 103 */         logger.info("Caller ClassLoader " + this.callerLoader.getClass().getName() + " child of ContextLoader " + this.contextLoader.getClass().getName());
/*     */       }
/*     */       
/* 106 */       return this.callerLoader;
/*     */     } 
/* 108 */     if (isChild(this.callerLoader, this.contextLoader)) {
/* 109 */       if (logger.isLoggable(Level.FINE)) {
/* 110 */         logger.info("Context ClassLoader " + this.contextLoader.getClass().getName() + " child of Caller ClassLoader " + this.callerLoader.getClass().getName());
/*     */       }
/*     */       
/* 113 */       return this.contextLoader;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     logger.info("Ambiguous ClassLoader choice preferContext:" + preferContext + " Context:" + this.contextLoader.getClass().getName() + " Caller:" + this.callerLoader.getClass().getName());
/*     */     
/* 119 */     this.ambiguous = true;
/* 120 */     return preferContext ? this.contextLoader : this.callerLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public boolean isAmbiguous() { return this.ambiguous; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public ClassLoader getCallerLoader() { return this.callerLoader; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public ClassLoader getContextLoader() { return this.contextLoader; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public ClassLoader getThisLoader() { return getClass().getClassLoader(); }
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
/*     */   private boolean isChild(ClassLoader loader1, ClassLoader loader2) {
/* 170 */     for (; loader2 != null; loader2 = loader2.getParent()) {
/* 171 */       if (loader2 == loader1) {
/* 172 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\api\ClassLoadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */