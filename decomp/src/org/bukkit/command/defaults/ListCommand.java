/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ListCommand
/*    */   extends VanillaCommand {
/*    */   public ListCommand() {
/* 10 */     super("list");
/* 11 */     this.description = "Lists all online players";
/* 12 */     this.usageMessage = "/list";
/* 13 */     setPermission("bukkit.command.list");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true;
/*    */     
/* 20 */     String players = "";
/*    */     
/* 22 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 23 */       if (players.length() > 0) {
/* 24 */         players = players + ", ";
/*    */       }
/*    */       
/* 27 */       players = players + player.getDisplayName();
/*    */     } 
/*    */     
/* 30 */     sender.sendMessage("Connected players: " + players);
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean matches(String input) { return input.startsWith("list"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\ListCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */