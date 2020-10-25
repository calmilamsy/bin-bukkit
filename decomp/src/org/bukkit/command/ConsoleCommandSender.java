/*    */ package org.bukkit.command;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.permissions.PermissibleBase;
/*    */ import org.bukkit.permissions.Permission;
/*    */ import org.bukkit.permissions.PermissionAttachment;
/*    */ import org.bukkit.permissions.PermissionAttachmentInfo;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ConsoleCommandSender implements CommandSender {
/*    */   private final Server server;
/*    */   private final PermissibleBase perm;
/*    */   
/*    */   public ConsoleCommandSender(Server server) {
/* 17 */     this.perm = new PermissibleBase(this);
/*    */ 
/*    */     
/* 20 */     this.server = server;
/*    */   }
/*    */ 
/*    */   
/* 24 */   public void sendMessage(String message) { System.out.println(ChatColor.stripColor(message)); }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean isOp() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void setOp(boolean value) { throw new UnsupportedOperationException("Cannot change operator status of server console"); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean isPlayer() { return false; }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public Server getServer() { return this.server; }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public boolean isPermissionSet(String name) { return this.perm.isPermissionSet(name); }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public boolean isPermissionSet(Permission perm) { return this.perm.isPermissionSet(perm); }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public boolean hasPermission(String name) { return this.perm.hasPermission(name); }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public boolean hasPermission(Permission perm) { return this.perm.hasPermission(perm); }
/*    */ 
/*    */ 
/*    */   
/* 60 */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) { return this.perm.addAttachment(plugin, name, value); }
/*    */ 
/*    */ 
/*    */   
/* 64 */   public PermissionAttachment addAttachment(Plugin plugin) { return this.perm.addAttachment(plugin); }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) { return this.perm.addAttachment(plugin, name, value, ticks); }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) { return this.perm.addAttachment(plugin, ticks); }
/*    */ 
/*    */ 
/*    */   
/* 76 */   public void removeAttachment(PermissionAttachment attachment) { this.perm.removeAttachment(attachment); }
/*    */ 
/*    */ 
/*    */   
/* 80 */   public void recalculatePermissions() { this.perm.recalculatePermissions(); }
/*    */ 
/*    */ 
/*    */   
/* 84 */   public Set<PermissionAttachmentInfo> getEffectivePermissions() { return this.perm.getEffectivePermissions(); }
/*    */ 
/*    */ 
/*    */   
/* 88 */   public String getName() { return "CONSOLE"; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\ConsoleCommandSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */