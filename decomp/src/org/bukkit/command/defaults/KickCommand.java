/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KickCommand extends VanillaCommand {
/*    */   public KickCommand() {
/* 11 */     super("kick");
/* 12 */     this.description = "Removes the specified player from the server";
/* 13 */     this.usageMessage = "/kick <player>";
/* 14 */     setPermission("bukkit.command.kick");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 19 */     if (!testPermission(sender)) return true; 
/* 20 */     if (args.length < 1) {
/* 21 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 22 */       return false;
/*    */     } 
/*    */     
/* 25 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */     
/* 27 */     if (player != null) {
/* 28 */       Command.broadcastCommandMessage(sender, "Kicking " + player.getName());
/* 29 */       player.kickPlayer("Kicked by admin");
/*    */     } else {
/* 31 */       sender.sendMessage("Can't find user " + args[0] + ". No kick.");
/*    */     } 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public boolean matches(String input) { return input.startsWith("kick "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\KickCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */