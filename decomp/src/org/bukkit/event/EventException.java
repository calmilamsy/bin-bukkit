/*    */ package org.bukkit.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 3532808232324183999L;
/*    */   private final Throwable cause;
/*    */   
/* 13 */   public EventException(Throwable throwable) { this.cause = throwable; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   public EventException() { this.cause = null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventException(Throwable cause, String message) {
/* 27 */     super(message);
/* 28 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventException(String message) {
/* 35 */     super(message);
/* 36 */     this.cause = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public Throwable getCause() { return this.cause; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\EventException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */