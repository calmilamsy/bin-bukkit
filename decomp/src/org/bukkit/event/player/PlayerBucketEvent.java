/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public abstract class PlayerBucketEvent extends PlayerEvent implements Cancellable {
/*    */   private ItemStack itemStack;
/*    */   private boolean cancelled = false;
/*    */   private Block blockClicked;
/*    */   private BlockFace blockFace;
/*    */   private Material bucket;
/*    */   
/*    */   public PlayerBucketEvent(Event.Type type, Player who, Block blockClicked, BlockFace blockFace, Material bucket, ItemStack itemInHand) {
/* 19 */     super(type, who);
/* 20 */     this.blockClicked = blockClicked;
/* 21 */     this.blockFace = blockFace;
/* 22 */     this.itemStack = itemInHand;
/* 23 */     this.bucket = bucket;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Material getBucket() { return this.bucket; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public ItemStack getItemStack() { return this.itemStack; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   public void setItemStack(ItemStack itemStack) { this.itemStack = itemStack; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public Block getBlockClicked() { return this.blockClicked; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public BlockFace getBlockFace() { return this.blockFace; }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public boolean isCancelled() { return this.cancelled; }
/*    */ 
/*    */ 
/*    */   
/* 77 */   public void setCancelled(boolean cancel) { this.cancelled = cancel; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\event\player\PlayerBucketEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */