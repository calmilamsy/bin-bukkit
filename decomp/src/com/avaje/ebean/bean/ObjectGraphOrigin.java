/*    */ package com.avaje.ebean.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ObjectGraphOrigin
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 410937765287968707L;
/*    */   private final CallStack callStack;
/*    */   private final String key;
/*    */   private final String beanType;
/*    */   
/*    */   public ObjectGraphOrigin(int queryHash, CallStack callStack, String beanType) {
/* 45 */     this.callStack = callStack;
/* 46 */     this.beanType = beanType;
/* 47 */     this.key = callStack.getOriginKey(queryHash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String getKey() { return this.key; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public String getBeanType() { return this.beanType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public CallStack getCallStack() { return this.callStack; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public String getFirstStackElement() { return this.callStack.getFirstStackTraceElement().toString(); }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public String toString() { return this.key + " " + this.beanType + " " + this.callStack.getFirstStackTraceElement(); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\bean\ObjectGraphOrigin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */