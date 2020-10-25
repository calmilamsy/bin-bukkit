/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
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
/*     */ public class FinalizableReferenceQueue
/*     */ {
/*  92 */   private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
/*     */   
/*     */   private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
/*     */   
/*     */   private static final Method startFinalizer;
/*     */   final ReferenceQueue<Object> queue;
/*     */   final boolean threadStarted;
/*     */   
/*     */   static  {
/* 101 */     finalizer = loadFinalizer(new FinalizerLoader[] { new SystemLoader(), new DecoupledLoader(), new DirectLoader() });
/*     */     
/* 103 */     startFinalizer = getStartFinalizer(finalizer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FinalizableReferenceQueue() {
/* 123 */     boolean threadStarted = false;
/*     */     try {
/* 125 */       referenceQueue = (ReferenceQueue)startFinalizer.invoke(null, new Object[] { FinalizableReference.class, this });
/*     */       
/* 127 */       threadStarted = true;
/* 128 */     } catch (IllegalAccessException e) {
/*     */       
/* 130 */       throw new AssertionError(e);
/* 131 */     } catch (Throwable t) {
/* 132 */       logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", t);
/*     */ 
/*     */       
/* 135 */       referenceQueue = new ReferenceQueue();
/*     */     } 
/*     */     
/* 138 */     this.queue = referenceQueue;
/* 139 */     this.threadStarted = threadStarted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cleanUp() {
/* 149 */     if (this.threadStarted) {
/*     */       return;
/*     */     }
/*     */     
/*     */     Reference<?> reference;
/* 154 */     while ((reference = this.queue.poll()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       reference.clear();
/*     */       try {
/* 161 */         ((FinalizableReference)reference).finalizeReferent();
/* 162 */       } catch (Throwable t) {
/* 163 */         logger.log(Level.SEVERE, "Error cleaning up after reference.", t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> loadFinalizer(FinalizerLoader... loaders) {
/* 175 */     for (FinalizerLoader loader : loaders) {
/* 176 */       Class<?> finalizer = loader.loadFinalizer();
/* 177 */       if (finalizer != null) {
/* 178 */         return finalizer;
/*     */       }
/*     */     } 
/*     */     
/* 182 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface FinalizerLoader
/*     */   {
/*     */     Class<?> loadFinalizer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SystemLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     public Class<?> loadFinalizer() {
/*     */       ClassLoader systemLoader;
/*     */       try {
/* 207 */         systemLoader = ClassLoader.getSystemClassLoader();
/* 208 */       } catch (SecurityException e) {
/* 209 */         logger.info("Not allowed to access system class loader.");
/* 210 */         return null;
/*     */       } 
/* 212 */       if (systemLoader != null) {
/*     */         try {
/* 214 */           return systemLoader.loadClass("com.google.common.base.internal.Finalizer");
/* 215 */         } catch (ClassNotFoundException e) {
/*     */           
/* 217 */           return null;
/*     */         } 
/*     */       }
/* 220 */       return null;
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
/*     */ 
/*     */   
/*     */   static class DecoupledLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Google Collections to your system class path.";
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
/*     */     public Class<?> loadFinalizer() {
/*     */       try {
/* 254 */         ClassLoader finalizerLoader = newLoader(getBaseUrl());
/* 255 */         return finalizerLoader.loadClass("com.google.common.base.internal.Finalizer");
/* 256 */       } catch (Exception e) {
/* 257 */         logger.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Google Collections to your system class path.", e);
/* 258 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     URL getBaseUrl() throws IOException {
/* 267 */       String finalizerPath = "com.google.common.base.internal.Finalizer".replace('.', '/') + ".class";
/* 268 */       URL finalizerUrl = getClass().getClassLoader().getResource(finalizerPath);
/* 269 */       if (finalizerUrl == null) {
/* 270 */         throw new FileNotFoundException(finalizerPath);
/*     */       }
/*     */ 
/*     */       
/* 274 */       String urlString = finalizerUrl.toString();
/* 275 */       if (!urlString.endsWith(finalizerPath)) {
/* 276 */         throw new IOException("Unsupported path style: " + urlString);
/*     */       }
/* 278 */       urlString = urlString.substring(0, urlString.length() - finalizerPath.length());
/*     */       
/* 280 */       return new URL(finalizerUrl, urlString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 285 */     URLClassLoader newLoader(URL base) { return new URLClassLoader(new URL[] { base }); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DirectLoader
/*     */     implements FinalizerLoader
/*     */   {
/*     */     public Class<?> loadFinalizer() {
/*     */       try {
/* 297 */         return Class.forName("com.google.common.base.internal.Finalizer");
/* 298 */       } catch (ClassNotFoundException e) {
/* 299 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Method getStartFinalizer(Class<?> finalizer) {
/*     */     try {
/* 309 */       return finalizer.getMethod("startFinalizer", new Class[] { Class.class, Object.class });
/* 310 */     } catch (NoSuchMethodException e) {
/* 311 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\FinalizableReferenceQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */