/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class PardonIpCommand extends VanillaCommand {
/*    */   public PardonIpCommand() {
/* 10 */     super("pardon-ip");
/* 11 */     this.description = "Allows the specified IP address to use this server";
/* 12 */     this.usageMessage = "/pardon-ip <address>";
/* 13 */     setPermission("bukkit.command.unban.ip");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true; 
/* 19 */     if (args.length != 1) {
/* 20 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 21 */       return false;
/*    */     } 
/*    */     
/* 24 */     Bukkit.unbanIP(args[0]);
/* 25 */     Command.broadcastCommandMessage(sender, "Pardoning ip " + args[0]);
/*    */     
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public boolean matches(String input) { return input.startsWith("pardon-ip "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\PardonIpCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */