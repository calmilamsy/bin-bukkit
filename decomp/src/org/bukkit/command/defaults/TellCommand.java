/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TellCommand
/*    */   extends VanillaCommand {
/*    */   public TellCommand() {
/* 11 */     super("tell");
/* 12 */     this.description = "Sends a private message to the given player";
/* 13 */     this.usageMessage = "/tell <player> <message>";
/* 14 */     setPermission("bukkit.command.tell");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 19 */     if (!testPermission(sender)) return true; 
/* 20 */     if (args.length < 2) {
/* 21 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 22 */       return false;
/*    */     } 
/*    */     
/* 25 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */     
/* 27 */     if (player == null) {
/* 28 */       sender.sendMessage("There's no player by that name online.");
/*    */     } else {
/* 30 */       String message = "";
/*    */       
/* 32 */       for (i = 1; i < args.length; i++) {
/* 33 */         if (i > 1) message = message + " "; 
/* 34 */         message = message + args[i];
/*    */       } 
/*    */       
/* 37 */       String result = ChatColor.GRAY + sender.getName() + " whispers " + message;
/*    */       
/* 39 */       if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 40 */         Bukkit.getLogger().info("[" + sender.getName() + "->" + player.getName() + "] " + message);
/* 41 */         Bukkit.getLogger().info(result);
/*    */       } 
/*    */       
/* 44 */       player.sendMessage(result);
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean matches(String input) { return input.startsWith("tell "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\TellCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */