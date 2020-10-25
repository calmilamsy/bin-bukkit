/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class PlayerEvent
/*    */   extends Event
/*    */ {
/*    */   protected Player player;
/*    */   
/*    */   public PlayerEvent(Event.Type type, Player who) {
/* 13 */     super(type);
/* 14 */     this.player = who;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public final Player getPlayer() { return this.player; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */