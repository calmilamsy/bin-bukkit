/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class SaveOffCommand extends VanillaCommand {
/*    */   public SaveOffCommand() {
/* 10 */     super("save-off");
/* 11 */     this.description = "Disables server autosaving";
/* 12 */     this.usageMessage = "/save-off";
/* 13 */     setPermission("bukkit.command.save.disable");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true;
/*    */     
/* 20 */     Command.broadcastCommandMessage(sender, "Disabling level saving..");
/*    */     
/* 22 */     for (World world : Bukkit.getWorlds()) {
/* 23 */       world.setAutoSave(false);
/*    */     }
/*    */     
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public boolean matches(String input) { return input.startsWith("save-off"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\SaveOffCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */