/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ 
/*    */ public class VersionCommand extends Command {
/*    */   public VersionCommand(String name) {
/* 14 */     super(name);
/*    */     
/* 16 */     this.description = "Gets the version of this server including any plugins in use";
/* 17 */     this.usageMessage = "/version [plugin name]";
/* 18 */     setPermission("bukkit.command.version");
/* 19 */     setAliases(Arrays.asList(new String[] { "ver", "about" }));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 24 */     if (!testPermission(sender)) return true;
/*    */     
/* 26 */     if (args.length == 0) {
/* 27 */       sender.sendMessage("This server is running " + ChatColor.GREEN + Bukkit.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + Bukkit.getVersion());
/* 28 */       sender.sendMessage("This server is also sporting some funky dev build of Bukkit!");
/*    */     } else {
/* 30 */       StringBuilder name = new StringBuilder();
/*    */       
/* 32 */       for (String arg : args) {
/* 33 */         if (name.length() > 0) {
/* 34 */           name.append(' ');
/*    */         }
/*    */         
/* 37 */         name.append(arg);
/*    */       } 
/*    */       
/* 40 */       Plugin plugin = Bukkit.getPluginManager().getPlugin(name.toString());
/*    */       
/* 42 */       if (plugin != null) {
/* 43 */         PluginDescriptionFile desc = plugin.getDescription();
/* 44 */         sender.sendMessage(ChatColor.GREEN + desc.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + desc.getVersion());
/*    */         
/* 46 */         if (desc.getDescription() != null) {
/* 47 */           sender.sendMessage(desc.getDescription());
/*    */         }
/*    */         
/* 50 */         if (desc.getWebsite() != null) {
/* 51 */           sender.sendMessage("Website: " + ChatColor.GREEN + desc.getWebsite());
/*    */         }
/*    */         
/* 54 */         if (!desc.getAuthors().isEmpty()) {
/* 55 */           if (desc.getAuthors().size() == 1) {
/* 56 */             sender.sendMessage("Author: " + getAuthors(desc));
/*    */           } else {
/* 58 */             sender.sendMessage("Authors: " + getAuthors(desc));
/*    */           } 
/*    */         }
/*    */       } else {
/* 62 */         sender.sendMessage("This server is not running any plugin by that name.");
/* 63 */         sender.sendMessage("Use /plugins to get a list of plugins.");
/*    */       } 
/*    */     } 
/* 66 */     return true;
/*    */   }
/*    */   
/*    */   private String getAuthors(PluginDescriptionFile desc) {
/* 70 */     StringBuilder result = new StringBuilder();
/* 71 */     ArrayList<String> authors = desc.getAuthors();
/*    */     
/* 73 */     for (int i = 0; i < authors.size(); i++) {
/* 74 */       if (result.length() > 0) {
/* 75 */         result.append(ChatColor.WHITE);
/*    */         
/* 77 */         if (i < authors.size() - 1) {
/* 78 */           result.append(", ");
/*    */         } else {
/* 80 */           result.append(" and ");
/*    */         } 
/*    */       } 
/*    */       
/* 84 */       result.append(ChatColor.GREEN);
/* 85 */       result.append((String)authors.get(i));
/*    */     } 
/*    */     
/* 88 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\VersionCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */