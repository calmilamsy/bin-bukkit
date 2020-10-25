/*    */ package org.bukkit.command;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public final class PluginCommand
/*    */   extends Command
/*    */ {
/*    */   private final Plugin owningPlugin;
/*    */   private CommandExecutor executor;
/*    */   
/*    */   protected PluginCommand(String name, Plugin owner) {
/* 13 */     super(name);
/* 14 */     this.executor = owner;
/* 15 */     this.owningPlugin = owner;
/* 16 */     this.usageMessage = "";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/* 28 */     boolean success = false;
/*    */     
/* 30 */     if (!this.owningPlugin.isEnabled()) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     if (!testPermission(sender)) {
/* 35 */       return true;
/*    */     }
/*    */     
/*    */     try {
/* 39 */       success = this.executor.onCommand(sender, this, commandLabel, args);
/* 40 */     } catch (Throwable ex) {
/* 41 */       throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.owningPlugin.getDescription().getFullName(), ex);
/*    */     } 
/*    */     
/* 44 */     if (!success && this.usageMessage.length() > 0) {
/* 45 */       for (String line : this.usageMessage.replace("<command>", commandLabel).split("\n")) {
/* 46 */         sender.sendMessage(line);
/*    */       }
/*    */     }
/*    */     
/* 50 */     return success;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void setExecutor(CommandExecutor executor) { this.executor = executor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public CommandExecutor getExecutor() { return this.executor; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public Plugin getPlugin() { return this.owningPlugin; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\PluginCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */