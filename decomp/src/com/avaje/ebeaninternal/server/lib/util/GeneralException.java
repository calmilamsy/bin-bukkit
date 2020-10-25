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
/*    */ public class GeneralException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 5783084420007103280L;
/*    */   
/* 28 */   public GeneralException(Exception cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public GeneralException(String s, Exception cause) { super(s, cause); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public GeneralException(String s) { super(s); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\GeneralException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */