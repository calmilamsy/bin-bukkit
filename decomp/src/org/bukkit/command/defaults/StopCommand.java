/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class StopCommand extends VanillaCommand {
/*    */   public StopCommand() {
/*  9 */     super("stop");
/* 10 */     this.description = "Stops the server";
/* 11 */     this.usageMessage = "/stop";
/* 12 */     setPermission("bukkit.command.stop");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 17 */     if (!testPermission(sender)) return true;
/*    */     
/* 19 */     Command.broadcastCommandMessage(sender, "Stopping the server..");
/* 20 */     Bukkit.shutdown();
/*    */     
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public boolean matches(String input) { return input.startsWith("stop"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\StopCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */