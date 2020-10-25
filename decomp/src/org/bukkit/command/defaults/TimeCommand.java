/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class TimeCommand extends VanillaCommand {
/*    */   public TimeCommand() {
/* 11 */     super("time");
/* 12 */     this.description = "Changes the time on each world";
/* 13 */     this.usageMessage = "/time set <value>\n/time add <value>";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (args.length != 2) {
/* 19 */       sender.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage:\n" + this.usageMessage);
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     int value = 0;
/*    */     
/*    */     try {
/* 26 */       value = Integer.parseInt(args[1]);
/* 27 */     } catch (NumberFormatException ex) {
/* 28 */       sender.sendMessage("Unable to convert time value, " + args[1]);
/* 29 */       return true;
/*    */     } 
/*    */     
/* 32 */     if (args[0].equalsIgnoreCase("add")) {
/* 33 */       if (!sender.hasPermission("bukkit.command.time.add")) {
/* 34 */         sender.sendMessage(ChatColor.RED + "You don't have permission to add to the time");
/*    */       } else {
/* 36 */         for (World world : Bukkit.getWorlds()) {
/* 37 */           world.setFullTime(world.getFullTime() + value);
/*    */         }
/*    */         
/* 40 */         Command.broadcastCommandMessage(sender, "Added " + value + " to time");
/*    */       } 
/* 42 */     } else if (args[0].equalsIgnoreCase("set")) {
/* 43 */       if (!sender.hasPermission("bukkit.command.time.set")) {
/* 44 */         sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
/*    */       } else {
/* 46 */         for (World world : Bukkit.getWorlds()) {
/* 47 */           world.setTime(value);
/*    */         }
/*    */         
/* 50 */         Command.broadcastCommandMessage(sender, "Set time to " + value);
/*    */       } 
/*    */     } else {
/* 53 */       sender.sendMessage("Unknown method, use either \"add\" or \"set\"");
/* 54 */       return true;
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean matches(String input) { return input.startsWith("time "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\TimeCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */