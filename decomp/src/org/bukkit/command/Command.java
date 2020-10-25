/*     */ package org.bukkit.command;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Command
/*     */ {
/*     */   private final String name;
/*     */   private String nextLabel;
/*     */   private String label;
/*     */   private List<String> aliases;
/*     */   private List<String> activeAliases;
/*     */   private CommandMap commandMap;
/*     */   protected String description;
/*     */   protected String usageMessage;
/*     */   private String permission;
/*     */   
/*  26 */   protected Command(String name) { this(name, "", "/" + name, new ArrayList()); }
/*     */   protected Command(String name, String description, String usageMessage, List<String> aliases) {
/*     */     this.commandMap = null;
/*     */     this.description = "";
/*  30 */     this.name = name;
/*  31 */     this.nextLabel = name;
/*  32 */     this.label = name;
/*  33 */     this.description = description;
/*  34 */     this.usageMessage = usageMessage;
/*  35 */     this.aliases = aliases;
/*  36 */     this.activeAliases = new ArrayList(aliases);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public String getPermission() { return this.permission; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void setPermission(String permission) { this.permission = permission; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean testPermission(CommandSender target) {
/*  85 */     if (this.permission == null || this.permission.length() == 0 || target.hasPermission(this.permission)) {
/*  86 */       return true;
/*     */     }
/*     */     
/*  89 */     target.sendMessage(ChatColor.RED + "I'm sorry, Dave. I'm afraid I can't do that.");
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public String getLabel() { return this.label; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setLabel(String name) {
/* 110 */     this.nextLabel = name;
/* 111 */     if (!isRegistered()) {
/* 112 */       this.label = name;
/* 113 */       return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean register(CommandMap commandMap) {
/* 126 */     if (allowChangesFrom(commandMap)) {
/* 127 */       this.commandMap = commandMap;
/* 128 */       return true;
/*     */     } 
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unregister(CommandMap commandMap) {
/* 141 */     if (allowChangesFrom(commandMap)) {
/* 142 */       this.commandMap = null;
/* 143 */       this.activeAliases = new ArrayList(this.aliases);
/* 144 */       this.label = this.nextLabel;
/* 145 */       return true;
/*     */     } 
/*     */     
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 153 */   private boolean allowChangesFrom(CommandMap commandMap) { return (null == this.commandMap || this.commandMap == commandMap); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public boolean isRegistered() { return (null != this.commandMap); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public List<String> getAliases() { return this.activeAliases; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public String getDescription() { return this.description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public String getUsage() { return this.usageMessage; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Command setAliases(List<String> aliases) {
/* 199 */     this.aliases = aliases;
/* 200 */     if (!isRegistered()) {
/* 201 */       this.activeAliases = new ArrayList(aliases);
/*     */     }
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Command setDescription(String description) {
/* 213 */     this.description = description;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Command setUsage(String usage) {
/* 224 */     this.usageMessage = usage;
/* 225 */     return this;
/*     */   }
/*     */   
/*     */   public static void broadcastCommandMessage(CommandSender source, String message) {
/* 229 */     Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
/* 230 */     String result = source.getName() + ": " + message;
/* 231 */     String colored = ChatColor.GRAY + "(" + result + ")";
/*     */     
/* 233 */     if (!(source instanceof ConsoleCommandSender)) {
/* 234 */       source.sendMessage(message);
/*     */     }
/*     */     
/* 237 */     for (Permissible user : users) {
/* 238 */       if (user instanceof CommandSender) {
/* 239 */         CommandSender target = (CommandSender)user;
/*     */         
/* 241 */         if (target instanceof ConsoleCommandSender) {
/* 242 */           target.sendMessage(result); continue;
/* 243 */         }  if (target != source)
/* 244 */           target.sendMessage(colored); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\Command.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */