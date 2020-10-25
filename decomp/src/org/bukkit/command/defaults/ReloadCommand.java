/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ReloadCommand extends Command {
/*    */   public ReloadCommand(String name) {
/* 11 */     super(name);
/* 12 */     this.description = "Reloads the server configuration and plugins";
/* 13 */     this.usageMessage = "/reload";
/* 14 */     setPermission("bukkit.command.reload");
/* 15 */     setAliases(Arrays.asList(new String[] { "rl" }));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 20 */     if (!testPermission(sender)) return true;
/*    */     
/* 22 */     Bukkit.reload();
/* 23 */     sender.sendMessage(ChatColor.GREEN + "Reload complete.");
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\ReloadCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */