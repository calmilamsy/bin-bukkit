/*    */ package org.bukkit.command;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultipleCommandAlias
/*    */   extends Command
/*    */ {
/*    */   private Command[] commands;
/*    */   
/*    */   public MultipleCommandAlias(String name, Command[] commands) {
/* 11 */     super(name);
/* 12 */     this.commands = commands;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/* 17 */     boolean result = false;
/*    */     
/* 19 */     for (Command command : this.commands) {
/* 20 */       result |= command.execute(sender, commandLabel, args);
/*    */     }
/*    */     
/* 23 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\MultipleCommandAlias.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */