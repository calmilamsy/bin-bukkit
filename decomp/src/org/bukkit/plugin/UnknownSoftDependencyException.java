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
/*    */ public class UnknownSoftDependencyException
/*    */   extends UnknownDependencyException
/*    */ {
/*    */   private static final long serialVersionUID = 5721389371901775899L;
/*    */   
/* 16 */   public UnknownSoftDependencyException(Throwable throwable) { this(throwable, "Unknown soft dependency"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public UnknownSoftDependencyException(String message) { this(null, message); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public UnknownSoftDependencyException(Throwable throwable, String message) { super(throwable, message); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public UnknownSoftDependencyException() { this(null, "Unknown dependency"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\UnknownSoftDependencyException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */