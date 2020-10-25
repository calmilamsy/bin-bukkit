/*     */ package com.google.common.base;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
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
/*     */ @Beta
/*     */ public final class Throwables
/*     */ {
/*     */   public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> declaredType) throws X {
/*  58 */     if (declaredType.isInstance(throwable)) {
/*  59 */       throw (Throwable)declaredType.cast(throwable);
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
/*     */ 
/*     */   
/*     */   public static void propagateIfPossible(@Nullable Throwable throwable) {
/*  78 */     propagateIfInstanceOf(throwable, Error.class);
/*  79 */     propagateIfInstanceOf(throwable, RuntimeException.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> declaredType) throws X {
/* 103 */     propagateIfInstanceOf(throwable, declaredType);
/* 104 */     propagateIfPossible(throwable);
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
/*     */   public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> declaredType1, Class<X2> declaredType2) throws X1, X2 {
/* 124 */     Preconditions.checkNotNull(declaredType2);
/* 125 */     propagateIfInstanceOf(throwable, declaredType1);
/* 126 */     propagateIfPossible(throwable, declaredType2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RuntimeException propagate(Throwable throwable) {
/* 154 */     propagateIfPossible((Throwable)Preconditions.checkNotNull(throwable));
/* 155 */     throw new RuntimeException(throwable);
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
/*     */   public static Throwable getRootCause(Throwable throwable) {
/*     */     Throwable cause;
/* 169 */     while ((cause = throwable.getCause()) != null) {
/* 170 */       throwable = cause;
/*     */     }
/* 172 */     return throwable;
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
/*     */   
/*     */   public static List<Throwable> getCausalChain(Throwable throwable) {
/* 193 */     Preconditions.checkNotNull(throwable);
/* 194 */     List<Throwable> causes = new ArrayList<Throwable>(4);
/* 195 */     while (throwable != null) {
/* 196 */       causes.add(throwable);
/* 197 */       throwable = throwable.getCause();
/*     */     } 
/* 199 */     return Collections.unmodifiableList(causes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStackTraceAsString(Throwable throwable) {
/* 210 */     StringWriter stringWriter = new StringWriter();
/* 211 */     throwable.printStackTrace(new PrintWriter(stringWriter));
/* 212 */     return stringWriter.toString();
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
/*     */   public static Exception throwCause(Exception exception, boolean combineStackTraces) throws Exception {
/* 229 */     Throwable cause = exception.getCause();
/* 230 */     if (cause == null) {
/* 231 */       throw exception;
/*     */     }
/* 233 */     if (combineStackTraces) {
/* 234 */       StackTraceElement[] causeTrace = cause.getStackTrace();
/* 235 */       StackTraceElement[] outerTrace = exception.getStackTrace();
/* 236 */       StackTraceElement[] combined = new StackTraceElement[causeTrace.length + outerTrace.length];
/*     */       
/* 238 */       System.arraycopy(causeTrace, 0, combined, 0, causeTrace.length);
/* 239 */       System.arraycopy(outerTrace, 0, combined, causeTrace.length, outerTrace.length);
/*     */       
/* 241 */       cause.setStackTrace(combined);
/*     */     } 
/* 243 */     if (cause instanceof Exception) {
/* 244 */       throw (Exception)cause;
/*     */     }
/* 246 */     if (cause instanceof Error) {
/* 247 */       throw (Error)cause;
/*     */     }
/*     */     
/* 250 */     throw exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\base\Throwables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */