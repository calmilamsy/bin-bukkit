/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class KillCommand extends VanillaCommand {
/*    */   public KillCommand() {
/* 10 */     super("kill");
/* 11 */     this.description = "Commits suicide, only usable as a player";
/* 12 */     this.usageMessage = "/kill";
/* 13 */     setPermission("bukkit.command.kill");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 18 */     if (!testPermission(sender)) return true;
/*    */     
/* 20 */     if (sender instanceof Player) {
/* 21 */       Player player = (Player)sender;
/*    */       
/* 23 */       EntityDamageEvent ede = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 'Ϩ');
/* 24 */       Bukkit.getPluginManager().callEvent(ede);
/* 25 */       if (ede.isCancelled()) return true;
/*    */       
/* 27 */       player.damage(ede.getDamage());
/*    */     } else {
/* 29 */       sender.sendMessage("You can only perform this command as a player");
/*    */     } 
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean matches(String input) { return input.startsWith("kill"); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\defaults\KillCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */