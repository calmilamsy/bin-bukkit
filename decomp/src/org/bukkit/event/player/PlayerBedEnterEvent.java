/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerBedEnterEvent
/*    */   extends PlayerEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancel = false;
/*    */   private Block bed;
/*    */   
/*    */   public PlayerBedEnterEvent(Player who, Block bed) {
/* 16 */     super(Event.Type.PLAYER_BED_ENTER, who);
/* 17 */     this.bed = bed;
/*    */   }
/*    */ 
/*    */   
/* 21 */   public boolean isCancelled() { return this.cancel; }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public void setCancelled(boolean cancel) { this.cancel = cancel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Block getBed() { return this.bed; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerBedEnterEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */