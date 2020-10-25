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
/*    */ public class CreateObjectException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539736L;
/*    */   
/* 29 */   public CreateObjectException(Exception cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public CreateObjectException(String s, Exception cause) { super(s, cause); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public CreateObjectException(String s) { super(s); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\CreateObjectException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */