/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CallStack
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8590644046907438579L;
/*     */   private final String zeroHash;
/*     */   private final String pathHash;
/*     */   private final StackTraceElement[] callStack;
/*     */   private static final int radix = 64;
/*     */   private static final int mask = 63;
/*     */   
/*     */   public CallStack(StackTraceElement[] callStack) {
/*  48 */     this.callStack = callStack;
/*  49 */     this.zeroHash = enc(callStack[0].hashCode());
/*  50 */     int hc = 0;
/*  51 */     for (int i = 1; i < callStack.length; i++) {
/*  52 */       hc = 31 * hc + callStack[i].hashCode();
/*     */     }
/*  54 */     this.pathHash = enc(hc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public StackTraceElement getFirstStackTraceElement() { return this.callStack[0]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public StackTraceElement[] getCallStack() { return this.callStack; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public String getZeroHash() { return this.zeroHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public String getPathHash() { return this.pathHash; }
/*     */ 
/*     */ 
/*     */   
/*  87 */   public String toString() { return this.zeroHash + ":" + this.pathHash + ":" + this.callStack[0]; }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public String getOriginKey(int queryHash) { return this.zeroHash + "." + enc(queryHash) + "." + this.pathHash; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String enc(int i) {
/* 102 */     char[] buf = new char[32];
/* 103 */     int charPos = 32;
/*     */     do {
/* 105 */       buf[--charPos] = intToBase64[i & 0x3F];
/* 106 */       i >>>= 6;
/* 107 */     } while (i != 0);
/*     */     
/* 109 */     return new String(buf, charPos, 32 - charPos);
/*     */   }
/*     */   private static final char[] intToBase64 = { 
/* 112 */       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\CallStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */