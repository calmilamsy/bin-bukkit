/*    */ package com.avaje.ebeaninternal.server.lib.util;
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
/*    */ public class NotFoundException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539845L;
/*    */   
/* 29 */   public NotFoundException(Exception cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public NotFoundException(String s, Exception cause) { super(s, cause); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public NotFoundException(String s) { super(s); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\NotFoundException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */