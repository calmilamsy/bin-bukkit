/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ 
/*    */ public class PlayerBedLeaveEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   private Block bed;
/*    */   
/*    */   public PlayerBedLeaveEvent(Player who, Block bed) {
/* 14 */     super(Event.Type.PLAYER_BED_LEAVE, who);
/* 15 */     this.bed = bed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   public Block getBed() { return this.bed; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerBedLeaveEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */