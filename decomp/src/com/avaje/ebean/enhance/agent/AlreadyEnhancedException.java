/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlreadyEnhancedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -831705721822834774L;
/*    */   final String className;
/*    */   
/* 16 */   public AlreadyEnhancedException(String className) { this.className = className; }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public String getClassName() { return this.className; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\agent\AlreadyEnhancedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */