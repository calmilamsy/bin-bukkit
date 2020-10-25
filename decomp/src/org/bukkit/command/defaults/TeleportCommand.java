/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TeleportCommand extends VanillaCommand {
/*    */   public TeleportCommand() {
/* 11 */     super("tp");
/* 12 */     this.description = "Teleports the given player to another player";
/* 13 */     this.usageMessage = "/tp <player> <target>";
/* 14 */     setPermission("bukkit.command.teleport");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 19 */     if (!testPermission(sender)) return true; 
/* 20 */     if (args.length != 2) {
/* 21 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 22 */       return false;
/*    */     } 
/*    */     
/* 25 */     Player victim = Bukkit.getPlayerExact(args[0]);
/* 26 */     Player target = Bukkit.getPlayerExact(args[1]);
/*    */     
/* 28 */     if (victim == null) {
/* 29 */       sender.sendMessage("Can't find user " + args[0] + ". No tp.");
/* 30 */     } else if (target == null) {
/* 31 */       sender.sendMessage("Can't find user " + args[1] + ". No tp.");
/*    */     } else {
/* 33 */       Command.broadcastCommandMessage(sender, "Teleporting " + victim.getName() + " to " + target.getName());
/* 34 */       victim.teleport(target);
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean matches(String input) { return input.startsWith("tp "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\TeleportCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */