/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerChatEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   private String message;
/* 16 */   private String format = "<%1$s> %2$s";
/*    */   
/*    */   private final Set<Player> recipients;
/*    */   
/* 20 */   public PlayerChatEvent(Player player, String message) { this(Event.Type.PLAYER_CHAT, player, message); }
/*    */ 
/*    */   
/*    */   protected PlayerChatEvent(Event.Type type, Player player, String message) {
/* 24 */     super(type, player);
/* 25 */     this.recipients = new HashSet(Arrays.asList(player.getServer().getOnlinePlayers()));
/* 26 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/* 30 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public String getMessage() { return this.message; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public void setMessage(String message) { this.message = message; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public void setPlayer(Player player) { this.player = player; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public String getFormat() { return this.format; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFormat(String format) {
/*    */     try {
/* 82 */       String.format(format, new Object[] { this.player, this.message });
/* 83 */     } catch (RuntimeException ex) {
/* 84 */       ex.fillInStackTrace();
/* 85 */       throw ex;
/*    */     } 
/*    */     
/* 88 */     this.format = format;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 97 */   public Set<Player> getRecipients() { return this.recipients; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerChatEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */