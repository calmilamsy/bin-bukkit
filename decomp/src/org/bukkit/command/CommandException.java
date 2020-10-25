/*    */ package org.bukkit.command;
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
/*    */ public class CommandException
/*    */   extends RuntimeException
/*    */ {
/*    */   public CommandException() {}
/*    */   
/* 18 */   public CommandException(String msg) { super(msg); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public CommandException(String msg, Throwable cause) { super(msg, cause); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\CommandException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */