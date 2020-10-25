/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidDescriptionException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 5721389122281775894L;
/*    */   private final Throwable cause;
/*    */   private final String message;
/*    */   
/* 17 */   public InvalidDescriptionException(Throwable throwable) { this(throwable, "Invalid plugin.yml"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public InvalidDescriptionException(String message) { this(null, message); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidDescriptionException(Throwable throwable, String message) {
/* 36 */     this.cause = null;
/* 37 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public InvalidDescriptionException() { this(null, "Invalid plugin.yml"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public Throwable getCause() { return this.cause; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public String getMessage() { return this.message; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\InvalidDescriptionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */