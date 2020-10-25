/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class DeopCommand extends VanillaCommand {
/*    */   public DeopCommand() {
/* 12 */     super("deop");
/* 13 */     this.description = "Takes the specified player's operator status";
/* 14 */     this.usageMessage = "/deop <player>";
/* 15 */     setPermission("bukkit.command.op.take");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 20 */     if (!testPermission(sender)) return true; 
/* 21 */     if (args.length != 1) {
/* 22 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     Command.broadcastCommandMessage(sender, "De-opping " + args[0]);
/*    */     
/* 28 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 29 */     player.setOp(false);
/*    */     
/* 31 */     if (player instanceof Player) {
/* 32 */       ((Player)player).sendMessage(ChatColor.YELLOW + "You are no longer op!");
/*    */     }
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean matches(String input) { return input.startsWith("deop "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\DeopCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */