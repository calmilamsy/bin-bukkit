/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class MeCommand extends VanillaCommand {
/*    */   public MeCommand() {
/*  9 */     super("me");
/* 10 */     this.description = "Performs the specified action in chat";
/* 11 */     this.usageMessage = "/me <action>";
/* 12 */     setPermission("bukkit.command.me");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 17 */     if (!testPermission(sender)) return true; 
/* 18 */     if (args.length < 1) {
/* 19 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     String message = "";
/*    */     
/* 25 */     for (int i = 0; i < args.length; i++) {
/* 26 */       if (i > 0) message = message + " "; 
/* 27 */       message = message + args[i];
/*    */     } 
/*    */     
/* 30 */     Bukkit.broadcastMessage("* " + sender.getName() + " " + message);
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean matches(String input) { return input.startsWith("me "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\MeCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */