/*    */ package org.bukkit.fillr;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class Fillr
/*    */   extends JavaPlugin {
/*    */   public static final String NAME = "Fillr";
/*    */   public static final String VERSION = "1.0";
/*    */   public static final String DIRECTORY = "plugins";
/*    */   
/*    */   public void onDisable() {}
/*    */   
/*    */   public void onEnable() {}
/*    */   
/*    */   public void onLoad() {}
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
/* 20 */     if (commandLabel.equalsIgnoreCase("check")) {
/* 21 */       (new Checker()).check(sender);
/* 22 */       return true;
/* 23 */     }  if (commandLabel.equalsIgnoreCase("updateAll")) {
/* 24 */       (new Updater(getServer())).updateAll(sender);
/* 25 */       return true;
/* 26 */     }  if (commandLabel.equalsIgnoreCase("update")) {
/* 27 */       if (args.length == 0) {
/* 28 */         sender.sendMessage("Usage is /update <name>");
/*    */       } else {
/* 30 */         (new Updater(getServer())).update(args[0], sender);
/*    */       } 
/*    */       
/* 33 */       return true;
/* 34 */     }  if (commandLabel.equalsIgnoreCase("get")) {
/* 35 */       if (args.length == 0) {
/* 36 */         sender.sendMessage("Usage is /get <name>");
/*    */       } else {
/*    */         try {
/* 39 */           (new Getter(getServer())).get(args[0], sender);
/* 40 */         } catch (Exception e) {
/* 41 */           sender.sendMessage("There was an error downloading " + args[0]);
/*    */         } 
/*    */       } 
/*    */       
/* 45 */       return true;
/*    */     } 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\Fillr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */