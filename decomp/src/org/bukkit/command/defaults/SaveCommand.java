/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class SaveCommand extends VanillaCommand {
/*    */   public SaveCommand() {
/* 10 */     super("save-all");
/* 11 */     this.description = "Saves the server to disk";
/* 12 */     this.usageMessage = "/save-all";
/* 13 */     setPermission("bukkit.command.save.perform");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true;
/*    */     
/* 20 */     Command.broadcastCommandMessage(sender, "Forcing save..");
/*    */     
/* 22 */     Bukkit.savePlayers();
/*    */     
/* 24 */     for (World world : Bukkit.getWorlds()) {
/* 25 */       world.save();
/*    */     }
/*    */     
/* 28 */     Command.broadcastCommandMessage(sender, "Save complete.");
/*    */     
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public boolean matches(String input) { return input.startsWith("save-all"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\SaveCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */