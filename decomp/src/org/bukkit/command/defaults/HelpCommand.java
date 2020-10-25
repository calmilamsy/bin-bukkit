/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class HelpCommand extends VanillaCommand {
/*    */   public HelpCommand() {
/*  7 */     super("help");
/*  8 */     this.description = "Shows the help menu";
/*  9 */     this.usageMessage = "/help";
/* 10 */     setPermission("bukkit.command.help");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 15 */     if (!testPermission(sender)) return true;
/*    */     
/* 17 */     sender.sendMessage("help  or  ?               shows this message");
/* 18 */     sender.sendMessage("kick <player>             removes a player from the server");
/* 19 */     sender.sendMessage("ban <player>              bans a player from the server");
/* 20 */     sender.sendMessage("pardon <player>           pardons a banned player so that they can connect again");
/* 21 */     sender.sendMessage("ban-ip <ip>               bans an IP address from the server");
/* 22 */     sender.sendMessage("pardon-ip <ip>            pardons a banned IP address so that they can connect again");
/* 23 */     sender.sendMessage("op <player>               turns a player into an op");
/* 24 */     sender.sendMessage("deop <player>             removes op status from a player");
/* 25 */     sender.sendMessage("tp <player1> <player2>    moves one player to the same location as another player");
/* 26 */     sender.sendMessage("give <player> <id> [num]  gives a player a resource");
/* 27 */     sender.sendMessage("tell <player> <message>   sends a private message to a player");
/* 28 */     sender.sendMessage("stop                      gracefully stops the server");
/* 29 */     sender.sendMessage("save-all                  forces a server-wide level save");
/* 30 */     sender.sendMessage("save-off                  disables terrain saving (useful for backup scripts)");
/* 31 */     sender.sendMessage("save-on                   re-enables terrain saving");
/* 32 */     sender.sendMessage("list                      lists all currently connected players");
/* 33 */     sender.sendMessage("say <message>             broadcasts a message to all players");
/* 34 */     sender.sendMessage("time <add|set> <amount>   adds to or sets the world time (0-24000)");
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public boolean matches(String input) { return (input.startsWith("help") || input.startsWith("?")); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\HelpCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */