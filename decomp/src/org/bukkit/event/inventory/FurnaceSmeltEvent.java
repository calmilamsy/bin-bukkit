/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class FurnaceSmeltEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/*    */   private Block furnace;
/*    */   private ItemStack source;
/*    */   private ItemStack result;
/*    */   private boolean cancelled;
/*    */   
/*    */   public FurnaceSmeltEvent(Block furnace, ItemStack source, ItemStack result) {
/* 18 */     super(Event.Type.FURNACE_SMELT);
/*    */     
/* 20 */     this.furnace = furnace;
/* 21 */     this.source = source;
/* 22 */     this.result = result;
/* 23 */     this.cancelled = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Block getFurnace() { return this.furnace; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public ItemStack getSource() { return this.source; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public ItemStack getResult() { return this.result; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public void setResult(ItemStack result) { this.result = result; }
/*    */ 
/*    */ 
/*    */   
/* 63 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\inventory\FurnaceSmeltEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */