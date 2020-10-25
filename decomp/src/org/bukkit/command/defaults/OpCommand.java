/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class OpCommand extends VanillaCommand {
/*    */   public OpCommand() {
/* 12 */     super("op");
/* 13 */     this.description = "Gives the specified player operator status";
/* 14 */     this.usageMessage = "/op <player>";
/* 15 */     setPermission("bukkit.command.op.give");
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
/* 26 */     Command.broadcastCommandMessage(sender, "Oping " + args[0]);
/*    */     
/* 28 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 29 */     player.setOp(true);
/*    */     
/* 31 */     if (player instanceof Player) {
/* 32 */       ((Player)player).sendMessage(ChatColor.YELLOW + "You are now op!");
/*    */     }
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean matches(String input) { return input.startsWith("op "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\OpCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */