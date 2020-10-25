/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockDispenseEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled = false;
/*    */   private ItemStack item;
/*    */   private Vector velocity;
/*    */   
/*    */   public BlockDispenseEvent(Block block, ItemStack dispensed, Vector velocity) {
/* 20 */     super(Event.Type.BLOCK_DISPENSE, block);
/* 21 */     this.item = dispensed;
/* 22 */     this.velocity = velocity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public ItemStack getItem() { return this.item.clone(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public void setItem(ItemStack item) { this.item = item; }
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
/* 52 */   public Vector getVelocity() { return this.velocity.clone(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public void setVelocity(Vector vel) { this.velocity = vel; }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 69 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\block\BlockDispenseEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */