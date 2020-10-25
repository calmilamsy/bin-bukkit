/*     */ package com.google.common.base.internal;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class Finalizer
/*     */   extends Thread
/*     */ {
/*  51 */   private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WeakReference<Class<?>> finalizableReferenceClassReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PhantomReference<Object> frqReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceQueue<Object> startFinalizer(Class<?> finalizableReferenceClass, Object frq) {
/*  77 */     if (!finalizableReferenceClass.getName().equals("com.google.common.base.FinalizableReference")) {
/*  78 */       throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
/*     */     }
/*     */ 
/*     */     
/*  82 */     Finalizer finalizer = new Finalizer(finalizableReferenceClass, frq);
/*  83 */     finalizer.start();
/*  84 */     return finalizer.queue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   private final ReferenceQueue<Object> queue = new ReferenceQueue();
/*     */   
/*  91 */   private static final Field inheritableThreadLocals = getInheritableThreadLocalsField();
/*     */ 
/*     */ 
/*     */   
/*     */   private Finalizer(Class<?> finalizableReferenceClass, Object frq) {
/*  96 */     super(Finalizer.class.getName());
/*     */     
/*  98 */     this.finalizableReferenceClassReference = new WeakReference(finalizableReferenceClass);
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.frqReference = new PhantomReference(frq, this.queue);
/*     */     
/* 104 */     setDaemon(true);
/*     */     
/*     */     try {
/* 107 */       if (inheritableThreadLocals != null) {
/* 108 */         inheritableThreadLocals.set(this, null);
/*     */       }
/* 110 */     } catch (Throwable t) {
/* 111 */       logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", t);
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
/*     */   public void run() {
/*     */     try {
/*     */       while (true) {
/*     */         try {
/*     */           while (true)
/* 127 */             cleanUp(this.queue.remove());  break;
/* 128 */         } catch (InterruptedException e) {}
/*     */       } 
/* 130 */     } catch (ShutDown shutDown) {
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cleanUp(Reference<?> reference) throws ShutDown {
/* 137 */     Method finalizeReferentMethod = getFinalizeReferentMethod();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 143 */       reference.clear();
/*     */       
/* 145 */       if (reference == this.frqReference)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 150 */         throw new ShutDown(null);
/*     */       }
/*     */       
/*     */       try {
/* 154 */         finalizeReferentMethod.invoke(reference, new Object[0]);
/* 155 */       } catch (Throwable t) {
/* 156 */         logger.log(Level.SEVERE, "Error cleaning up after reference.", t);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 163 */     while ((reference = this.queue.poll()) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method getFinalizeReferentMethod() throws ShutDown {
/* 170 */     Class<?> finalizableReferenceClass = (Class)this.finalizableReferenceClassReference.get();
/*     */     
/* 172 */     if (finalizableReferenceClass == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       throw new ShutDown(null);
/*     */     }
/*     */     try {
/* 184 */       return finalizableReferenceClass.getMethod("finalizeReferent", new Class[0]);
/* 185 */     } catch (NoSuchMethodException e) {
/* 186 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field getInheritableThreadLocalsField() {
/*     */     try {
/* 192 */       inheritableThreadLocals = Thread.class.getDeclaredField("inheritableThreadLocals");
/*     */       
/* 194 */       inheritableThreadLocals.setAccessible(true);
/* 195 */       return inheritableThreadLocals;
/* 196 */     } catch (Throwable t) {
/* 197 */       logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
/*     */ 
/*     */       
/* 200 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ShutDown extends Exception {
/*     */     private ShutDown() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\internal\Finalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */