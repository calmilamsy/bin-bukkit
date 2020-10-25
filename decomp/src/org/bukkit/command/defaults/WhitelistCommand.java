/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class WhitelistCommand extends VanillaCommand {
/*    */   public WhitelistCommand() {
/* 11 */     super("whitelist");
/* 12 */     this.description = "Prevents the specified player from using this server";
/* 13 */     this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true;
/*    */     
/* 20 */     if (args.length == 1) {
/* 21 */       if (args[0].equalsIgnoreCase("reload")) {
/* 22 */         if (badPerm(sender, "reload")) return true;
/*    */         
/* 24 */         Bukkit.reloadWhitelist();
/* 25 */         Command.broadcastCommandMessage(sender, "Reloaded white-list from file");
/* 26 */         return true;
/* 27 */       }  if (args[0].equalsIgnoreCase("on")) {
/* 28 */         if (badPerm(sender, "enable")) return true;
/*    */         
/* 30 */         Bukkit.setWhitelist(true);
/* 31 */         Command.broadcastCommandMessage(sender, "Turned on white-listing");
/* 32 */         return true;
/* 33 */       }  if (args[0].equalsIgnoreCase("off")) {
/* 34 */         if (badPerm(sender, "disable")) return true;
/*    */         
/* 36 */         Bukkit.setWhitelist(false);
/* 37 */         Command.broadcastCommandMessage(sender, "Turned off white-listing");
/* 38 */         return true;
/* 39 */       }  if (args[0].equalsIgnoreCase("list")) {
/* 40 */         if (badPerm(sender, "list")) return true;
/*    */         
/* 42 */         String result = "";
/*    */         
/* 44 */         for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
/* 45 */           if (result.length() > 0) {
/* 46 */             result = result + " ";
/*    */           }
/*    */           
/* 49 */           result = result + player.getName();
/*    */         } 
/*    */         
/* 52 */         sender.sendMessage("White-listed players: " + result);
/* 53 */         return true;
/*    */       } 
/* 55 */     } else if (args.length == 2) {
/* 56 */       if (args[0].equalsIgnoreCase("add")) {
/* 57 */         if (badPerm(sender, "add")) return true;
/*    */         
/* 59 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
/*    */         
/* 61 */         Command.broadcastCommandMessage(sender, "Added " + args[1] + " to white-list");
/* 62 */         return true;
/* 63 */       }  if (args[0].equalsIgnoreCase("remove")) {
/* 64 */         if (badPerm(sender, "remove")) return true;
/*    */         
/* 66 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);
/*    */         
/* 68 */         Command.broadcastCommandMessage(sender, "Removed " + args[1] + " from white-list");
/* 69 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     sender.sendMessage(ChatColor.RED + "Correct command usage:\n" + this.usageMessage);
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   private boolean badPerm(CommandSender sender, String perm) {
/* 78 */     if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
/* 79 */       sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action.");
/* 80 */       return true;
/*    */     } 
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public boolean matches(String input) { return input.startsWith("whitelist "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\WhitelistCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */