/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class FurnaceBurnEvent
/*    */   extends Event
/*    */   implements Cancellable {
/*    */   private Block furnace;
/*    */   private ItemStack fuel;
/*    */   private int burnTime;
/*    */   private boolean cancelled;
/*    */   private boolean burning;
/*    */   
/*    */   public FurnaceBurnEvent(Block furnace, ItemStack fuel, int burnTime) {
/* 18 */     super(Event.Type.FURNACE_BURN);
/*    */     
/* 20 */     this.furnace = furnace;
/* 21 */     this.fuel = fuel;
/* 22 */     this.burnTime = burnTime;
/* 23 */     this.cancelled = false;
/* 24 */     this.burning = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Block getFurnace() { return this.furnace; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 42 */   public ItemStack getFuel() { return this.fuel; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public int getBurnTime() { return this.burnTime; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public void setBurnTime(int burnTime) { this.burnTime = burnTime; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public boolean isBurning() { return this.burning; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 78 */   public void setBurning(boolean burning) { this.burning = burning; }
/*    */ 
/*    */ 
/*    */   
/* 82 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\inventory\FurnaceBurnEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */