/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class BanIpCommand extends VanillaCommand {
/*    */   public BanIpCommand() {
/* 10 */     super("ban-ip");
/* 11 */     this.description = "Prevents the specified IP address from using this server";
/* 12 */     this.usageMessage = "/ban-ip <address>";
/* 13 */     setPermission("bukkit.command.ban.ip");
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
/* 24 */     Bukkit.banIP(args[0]);
/* 25 */     Command.broadcastCommandMessage(sender, "Banning ip " + args[0]);
/*    */     
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public boolean matches(String input) { return input.startsWith("ban-ip "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\BanIpCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */