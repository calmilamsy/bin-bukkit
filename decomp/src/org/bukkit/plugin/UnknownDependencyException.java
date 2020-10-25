/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownDependencyException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 5721389371901775894L;
/*    */   private final Throwable cause;
/*    */   private final String message;
/*    */   
/* 18 */   public UnknownDependencyException(Throwable throwable) { this(throwable, "Unknown dependency"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public UnknownDependencyException(String message) { this(null, message); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnknownDependencyException(Throwable throwable, String message) {
/* 37 */     this.cause = null;
/* 38 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public UnknownDependencyException() { this(null, "Unknown dependency"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public Throwable getCause() { return this.cause; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public String getMessage() { return this.message; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\UnknownDependencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */