/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class GiveCommand extends VanillaCommand {
/*    */   public GiveCommand() {
/* 13 */     super("give");
/* 14 */     this.description = "Gives the specified player a certain amount of items";
/* 15 */     this.usageMessage = "/give <player> <item> [amount]";
/* 16 */     setPermission("bukkit.command.give");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 21 */     if (!testPermission(sender)) return true; 
/* 22 */     if (args.length < 2 || args.length > 3) {
/* 23 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 24 */       return false;
/*    */     } 
/*    */     
/* 27 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */     
/* 29 */     if (player != null) {
/* 30 */       Material material = Material.matchMaterial(args[1]);
/*    */       
/* 32 */       if (material != null) {
/* 33 */         Command.broadcastCommandMessage(sender, "Giving " + player.getName() + " some " + material.getId() + "(" + material + ")");
/*    */         
/* 35 */         int amount = 1;
/*    */         
/* 37 */         if (args.length >= 3) {
/*    */           try {
/* 39 */             amount = Integer.parseInt(args[2]);
/* 40 */           } catch (NumberFormatException ex) {}
/*    */           
/* 42 */           if (amount < 1) amount = 1; 
/* 43 */           if (amount > 64) amount = 64;
/*    */         
/*    */         } 
/* 46 */         player.getInventory().addItem(new ItemStack[] { new ItemStack(material, amount) });
/*    */       } else {
/* 48 */         sender.sendMessage("There's no item called " + args[1]);
/*    */       } 
/*    */     } else {
/* 51 */       sender.sendMessage("Can't find user " + args[0]);
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public boolean matches(String input) { return input.startsWith("give "); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\GiveCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */