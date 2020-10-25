/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidPluginException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -8242141640709409542L;
/*    */   private final Throwable cause;
/*    */   
/* 16 */   public InvalidPluginException(Throwable throwable) { this.cause = throwable; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public InvalidPluginException() { this.cause = null; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Throwable getCause() { return this.cause; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\plugin\InvalidPluginException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */