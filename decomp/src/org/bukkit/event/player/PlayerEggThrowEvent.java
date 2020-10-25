/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.Egg;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ 
/*    */ public class PlayerEggThrowEvent
/*    */   extends PlayerEvent
/*    */ {
/*    */   private Egg egg;
/*    */   private boolean hatching;
/*    */   private CreatureType hatchType;
/*    */   private byte numHatches;
/*    */   
/*    */   public PlayerEggThrowEvent(Player player, Egg egg, boolean hatching, byte numHatches, CreatureType hatchType) {
/* 17 */     super(Event.Type.PLAYER_EGG_THROW, player);
/* 18 */     this.egg = egg;
/* 19 */     this.hatching = hatching;
/* 20 */     this.numHatches = numHatches;
/* 21 */     this.hatchType = hatchType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   public Egg getEgg() { return this.egg; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean isHatching() { return this.hatching; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setHatching(boolean hatching) { this.hatching = hatching; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public CreatureType getHatchType() { return CreatureType.fromName(this.hatchType.getName()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 68 */   public void setHatchType(CreatureType hatchType) { this.hatchType = hatchType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   public byte getNumHatches() { return this.numHatches; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 94 */   public void setNumHatches(byte numHatches) { this.numHatches = numHatches; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerEggThrowEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */