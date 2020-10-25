/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class SayCommand
/*    */   extends VanillaCommand {
/*    */   public SayCommand() {
/* 10 */     super("say");
/* 11 */     this.description = "Broadcasts the given message as the console";
/* 12 */     this.usageMessage = "/say <message>";
/* 13 */     setPermission("bukkit.command.say");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true; 
/* 19 */     if (args.length == 0) {
/* 20 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 21 */       return false;
/*    */     } 
/*    */     
/* 24 */     String message = "";
/*    */     
/* 26 */     for (int i = 0; i < args.length; i++) {
/* 27 */       if (i > 0) message = message + " "; 
/* 28 */       message = message + args[i];
/*    */     } 
/*    */     
/* 31 */     if (!(sender instanceof org.bukkit.command.ConsoleCommandSender)) {
/* 32 */       Bukkit.getLogger().info("[" + sender.getName() + "] " + message);
/*    */     }
/*    */     
/* 35 */     Bukkit.broadcastMessage("[Server] " + message);
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public boolean matches(String input) { return input.startsWith("say "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\SayCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */