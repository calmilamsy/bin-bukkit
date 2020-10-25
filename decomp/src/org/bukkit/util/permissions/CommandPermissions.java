/*     */ package org.bukkit.util.permissions;
/*     */ 
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
/*     */ 
/*     */ 
/*     */ public final class CommandPermissions
/*     */ {
/*     */   private static final String ROOT = "bukkit.command";
/*     */   private static final String PREFIX = "bukkit.command.";
/*     */   
/*     */   private static Permission registerWhitelist(Permission parent) {
/*  13 */     Permission whitelist = DefaultPermissions.registerPermission("bukkit.command.whitelist", "Allows the user to modify the server whitelist", PermissionDefault.OP, parent);
/*     */     
/*  15 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.add", "Allows the user to add a player to the server whitelist", whitelist);
/*  16 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.remove", "Allows the user to remove a player from the server whitelist", whitelist);
/*  17 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.reload", "Allows the user to reload the server whitelist", whitelist);
/*  18 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.enable", "Allows the user to enable the server whitelist", whitelist);
/*  19 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.disable", "Allows the user to disable the server whitelist", whitelist);
/*  20 */     DefaultPermissions.registerPermission("bukkit.command.whitelist.list", "Allows the user to list all the users on the server whitelist", whitelist);
/*     */     
/*  22 */     whitelist.recalculatePermissibles();
/*     */     
/*  24 */     return whitelist;
/*     */   }
/*     */   
/*     */   private static Permission registerBan(Permission parent) {
/*  28 */     Permission ban = DefaultPermissions.registerPermission("bukkit.command.ban", "Allows the user to ban people", PermissionDefault.OP, parent);
/*     */     
/*  30 */     DefaultPermissions.registerPermission("bukkit.command.ban.player", "Allows the user to ban players", ban);
/*  31 */     DefaultPermissions.registerPermission("bukkit.command.ban.ip", "Allows the user to ban IP addresses", ban);
/*     */     
/*  33 */     ban.recalculatePermissibles();
/*     */     
/*  35 */     return ban;
/*     */   }
/*     */   
/*     */   private static Permission registerUnban(Permission parent) {
/*  39 */     Permission unban = DefaultPermissions.registerPermission("bukkit.command.unban", "Allows the user to unban people", PermissionDefault.OP, parent);
/*     */     
/*  41 */     DefaultPermissions.registerPermission("bukkit.command.unban.player", "Allows the user to unban players", unban);
/*  42 */     DefaultPermissions.registerPermission("bukkit.command.unban.ip", "Allows the user to unban IP addresses", unban);
/*     */     
/*  44 */     unban.recalculatePermissibles();
/*     */     
/*  46 */     return unban;
/*     */   }
/*     */   
/*     */   private static Permission registerOp(Permission parent) {
/*  50 */     Permission op = DefaultPermissions.registerPermission("bukkit.command.op", "Allows the user to change operators", PermissionDefault.OP, parent);
/*     */     
/*  52 */     DefaultPermissions.registerPermission("bukkit.command.op.give", "Allows the user to give a player operator status", op);
/*  53 */     DefaultPermissions.registerPermission("bukkit.command.op.take", "Allows the user to take a players operator status", op);
/*     */     
/*  55 */     op.recalculatePermissibles();
/*     */     
/*  57 */     return op;
/*     */   }
/*     */   
/*     */   private static Permission registerSave(Permission parent) {
/*  61 */     Permission save = DefaultPermissions.registerPermission("bukkit.command.save", "Allows the user to save the worlds", PermissionDefault.OP, parent);
/*     */     
/*  63 */     DefaultPermissions.registerPermission("bukkit.command.save.enable", "Allows the user to enable automatic saving", save);
/*  64 */     DefaultPermissions.registerPermission("bukkit.command.save.disable", "Allows the user to disable automatic saving", save);
/*  65 */     DefaultPermissions.registerPermission("bukkit.command.save.perform", "Allows the user to perform a manual save", save);
/*     */     
/*  67 */     save.recalculatePermissibles();
/*     */     
/*  69 */     return save;
/*     */   }
/*     */   
/*     */   private static Permission registerTime(Permission parent) {
/*  73 */     Permission time = DefaultPermissions.registerPermission("bukkit.command.time", "Allows the user to alter the time", PermissionDefault.OP, parent);
/*     */     
/*  75 */     DefaultPermissions.registerPermission("bukkit.command.time.add", "Allows the user to fast-forward time", time);
/*  76 */     DefaultPermissions.registerPermission("bukkit.command.time.set", "Allows the user to change the time", time);
/*     */     
/*  78 */     time.recalculatePermissibles();
/*     */     
/*  80 */     return time;
/*     */   }
/*     */   
/*     */   public static Permission registerPermissions(Permission parent) {
/*  84 */     Permission commands = DefaultPermissions.registerPermission("bukkit.command", "Gives the user the ability to use all Craftbukkit commands", parent);
/*     */     
/*  86 */     registerWhitelist(commands);
/*  87 */     registerBan(commands);
/*  88 */     registerUnban(commands);
/*  89 */     registerOp(commands);
/*  90 */     registerSave(commands);
/*  91 */     registerTime(commands);
/*     */     
/*  93 */     DefaultPermissions.registerPermission("bukkit.command.kill", "Allows the user to commit suicide", PermissionDefault.TRUE, commands);
/*  94 */     DefaultPermissions.registerPermission("bukkit.command.me", "Allows the user to perform a chat action", PermissionDefault.TRUE, commands);
/*  95 */     DefaultPermissions.registerPermission("bukkit.command.tell", "Allows the user to privately message another player", PermissionDefault.TRUE, commands);
/*  96 */     DefaultPermissions.registerPermission("bukkit.command.say", "Allows the user to talk as the console", PermissionDefault.OP, commands);
/*  97 */     DefaultPermissions.registerPermission("bukkit.command.give", "Allows the user to give items to players", PermissionDefault.OP, commands);
/*  98 */     DefaultPermissions.registerPermission("bukkit.command.teleport", "Allows the user to teleport players", PermissionDefault.OP, commands);
/*  99 */     DefaultPermissions.registerPermission("bukkit.command.kick", "Allows the user to kick players", PermissionDefault.OP, commands);
/* 100 */     DefaultPermissions.registerPermission("bukkit.command.stop", "Allows the user to stop the server", PermissionDefault.OP, commands);
/* 101 */     DefaultPermissions.registerPermission("bukkit.command.list", "Allows the user to list all online players", PermissionDefault.OP, commands);
/* 102 */     DefaultPermissions.registerPermission("bukkit.command.help", "Allows the user to view the vanilla help menu", PermissionDefault.OP, commands);
/* 103 */     DefaultPermissions.registerPermission("bukkit.command.plugins", "Allows the user to view the list of plugins running on this server", PermissionDefault.TRUE, commands);
/* 104 */     DefaultPermissions.registerPermission("bukkit.command.reload", "Allows the user to reload the server settings", PermissionDefault.OP, commands);
/* 105 */     DefaultPermissions.registerPermission("bukkit.command.version", "Allows the user to view the version of the server", PermissionDefault.TRUE, commands);
/*     */     
/* 107 */     commands.recalculatePermissibles();
/*     */     
/* 109 */     return commands;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\permissions\CommandPermissions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */